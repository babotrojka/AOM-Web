package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAO;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.equipment.*;
import hr.aomatica.model.form.ItemForm;
import hr.aomatica.model.form.TransactionForm;
import hr.aomatica.web.servlets.util.ServletUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet(value = "/oruzarstvo/oruzar/*", loadOnStartup = 1)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 50      // 50 MB
)
public class OruzarServlet extends HttpServlet {

    private DAO dao = DAOProvider.getDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if not oruzar or above active redirect
        switch (req.getPathInfo()) {
            case "/uredi" -> urediGet(req, resp);
            case "/uredi_oruzarstvo" -> urediOruzarstvo(req, resp);
            case "/uredi_opremu" -> urediOpremu(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void urediOpremu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item i = dao.findItem(Long.parseLong(req.getParameter("id")));
        req.setAttribute("i", i);
        req.getRequestDispatcher("/WEB-INF/pages/oruzarstvo/urediOpremu.jsp").forward(req, resp);
    }

    private void urediOruzarstvo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Item, Long> items = new HashMap<>();
        dao.findAllItems().forEach(i -> items.put(i, dao.findNumberOfActiveItems(i)));
        req.setAttribute("items", items);
        req.setAttribute("vendors", dao.findAllVendors());
        req.setAttribute("groups", dao.findAllGroups());
        req.setAttribute("types", dao.findAllTypes().stream().map(Type::getName).distinct().collect(Collectors.toList()));
        req.getRequestDispatcher("/WEB-INF/pages/oruzarstvo/urediOruzarstvo.jsp").forward(req, resp);
    }


    private void urediGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("t", dao.findTransaction(Long.parseLong(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/pages/oruzarstvo/urediTransakciju.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/zaduzi" -> zaduzi(req, resp);
            case "/uredi" -> urediPost(req, resp);
            case "/otkazi" -> otkazi(req, resp);
            case "/reset" -> reset(req, resp);
            case "/dodaj_opremu" -> dodajOpremu(req, resp);
            case "/uredi_opremu" -> urediOpremuPost(req, resp);
            case "/izbrisi_opremu" -> izbrisiOpremu(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void izbrisiOpremu(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Item i = dao.findItem(Long.parseLong(req.getParameter("id")));
        dao.deleteItem(i);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/uredi_oruzarstvo",
                "Oprema izbrisana",
                String.format("Komad opreme %s uspješno izbrisana.", i.getName()));
    }

    private void urediOpremuPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Item item = dao.findItem(Long.parseLong(req.getParameter("id")));
        ItemForm form = new ItemForm();
        form.popuniIzModela(item);
        form.azurirajFormu(req);
        form.validiraj();

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath() + "/uredi_oruzarstvo",
                    "Neuspješno uređivanje!",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        form.popuniUModel(item);
        dao.updateItem(item);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/uredi_oruzarstvo",
                "Oprema uspješno uređena!",
                String.format("Oprema %s %s %s %s uspješno uređena.",
                        item.getType_id().getVendor_id().getName(), item.getType_id().getGroup_id().getName(), item.getType_id().getName(), item.getName()));
    }

    private void dodajOpremu(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ItemForm form = new ItemForm();
        form.popuniIzHttpRequesta(req);
        form.validiraj();

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath() + "/uredi_oruzarstvo",
                    "Neuspješno dodavanje!",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        Item item = new Item();
        form.popuniUModel(item);
        dao.saveItem(item);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/uredi_oruzarstvo",
                "Oprema uspješno dodana!",
                String.format("Oprema %s %s %s %s uspješno dodana.",
                        item.getType_id().getVendor_id().getName(), item.getType_id().getGroup_id().getName(), item.getType_id().getName(), item.getName()));
    }

    private void reset(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        deleteOruzarstvo();

        try {
            ucitajOruzarstvo(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + "/oruzarstvo/",
                    "Neuspješno ažuriranje!",
                    String.format("Oružarstvo neuspješno ažurirano. Možda postoji greška u excel tablici. Inače javiti se Sabi!")
            );
            return;
        }

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + "/oruzarstvo/",
                "Oružarstvo ažurirano!",
                String.format("Oružarstvo uspješno ažurirano!")
        );
    }

    private void ucitajOruzarstvo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(req.getPart("excel").getInputStream());
        } catch (IOException e) {
            resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/oruzar");
        }

        Sheet vendors = workbook.getSheet(Constants.ORUZARSTVO_VENDOR_SHEET);
        for(Row row : vendors) {
            if(row.getRowNum() == 0) continue;
            if(row.getCell(0) == null) continue;

            Vendor v = new Vendor();
            v.setName(row.getCell(0).getStringCellValue());
            if(row.getCell(1) != null) v.setDescription(row.getCell(1).getStringCellValue());
            dao.saveEquipmentVendor(v);
        }

        Sheet groups = workbook.getSheet(Constants.ORUZARSTVO_GROUP_SHEET);
        for(Row row : groups) {
            if(row.getRowNum() == 0) continue;
            if(row.getCell(0) == null) continue;

            Group g = new Group();
            g.setName(row.getCell(0).getStringCellValue());
            if(row.getCell(1) != null) g.setDescription(row.getCell(1).getStringCellValue());

            dao.saveEquipmentGroup(g);
        }

        Sheet inventura = workbook.getSheet(Constants.ORUZARSTVO_INVENTURA_SHEET);
        for(Row row : inventura) {
            if (row.getRowNum() == 0) continue;
            if (row.getCell(0) == null) continue;

            Group g = dao.findEquipmentGroupByName(row.getCell(1).getStringCellValue()).get(0);
            Vendor v = dao.findEquipmentVendorByName(row.getCell(2).getStringCellValue()).get(0);

            String type = row.getCell(3).getStringCellValue();
            Type t = new Type();

            List<Type> existing = dao.findEquipmentTypeByNameGroupAndVendor(type, g, v);
            if(existing.size() > 0) {
                t = existing.get(0);
            } else {
                t.setGroup_id(g);
                t.setVendor_id(v);
                t.setName(type);
                dao.saveEquipmentType(t);
            }

            String itemName = row.getCell(4).getStringCellValue();
            Item i = new Item();
            i.setType_id(t);
            i.setName(itemName);
            i.setQuantity(row.getCell(7) == null ? 0 : (int) row.getCell(6).getNumericCellValue());
            if(row.getCell(10) != null) i.setDescription(row.getCell(9).getStringCellValue());

            dao.saveItem(i);
        }

    }

    private void deleteOruzarstvo() {
        dao.deleteAllEquipmentVendors();
        dao.deleteAllEquipmentGroups();
        dao.deleteAllEquipmentTypes();

        dao.deleteAllTransactionEntries();
        dao.deleteAllTransactions();

        dao.deleteAllItems();
    }

    private void urediPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Transaction t = dao.findTransaction(Long.parseLong(req.getParameter("id")));
        TransactionForm form = new TransactionForm();
        form.popuniIzModela(t);
        form.azurirajFormu(req);
        form.validiraj();

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + "/oruzarstvo/",
                    "Neuspješno zaduživanje",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        t.setLocked(1);
        t.setReturneddate(new Date());
        dao.updateTransaction(t);

        Transaction newT = new Transaction();
        form.popuniUModel(newT);
        newT.setTransaction_id(null);
        newT.setCloned_from(t);
        dao.saveTransaction(newT);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + "/oruzarstvo/",
                "Zaduženje uspješno!",
                String.format("Zaduženje za alpinista %s %s uspješno uređeno.", t.getBorrower_id().getFirstname(), t.getBorrower_id().getLastname()));


    }


    private void otkazi(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Transaction t = dao.findTransaction(Long.parseLong(req.getParameter("id")));
        t.setLocked(1);
        t.setReturneddate(new Date());
        dao.updateTransaction(t);

        resp.sendRedirect(req.getContextPath() + "/oruzarstvo/");
    }

    private void zaduzi(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        TransactionForm form = new TransactionForm();
        form.popuniIzHttpRequesta(req);
        form.validiraj();

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + "/oruzarstvo/",
                    "Neuspješno zaduživanje",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        Transaction t = new Transaction();
        form.popuniUModel(t);
        dao.saveTransaction(t);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + "/oruzarstvo/",
                "Zaduženje uspješno!",
                String.format("Zaduženje za alpinista %s %s uspješno dodano.", t.getBorrower_id().getFirstname(), t.getBorrower_id().getLastname()));
    }
}
