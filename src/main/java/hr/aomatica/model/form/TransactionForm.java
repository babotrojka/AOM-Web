package hr.aomatica.model.form;

import com.google.protobuf.MapEntry;
import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.equipment.Item;
import hr.aomatica.model.equipment.Transaction;
import hr.aomatica.model.equipment.TransactionEntry;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.user.User;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionForm extends Form {
    private String transaction_id;
    private String user;
    private String lender;
    private String borrower;
    private String valid;
    private String locked;
    private String miscdata;
    private String startdate;
    private String returnuntil;
    private String returneddate;
    private String cloned_from;
    private String created_at;
    private String updated_at;

    private Map<String, String> entries;


    @Override
    public void popuniIzHttpRequesta(HttpServletRequest req) {
        this.transaction_id = pripremi(req.getParameter("id"));
        this.user = req.getSession().getAttribute("currentUserNick").toString();
        this.lender = req.getSession().getAttribute("currentUserNick").toString();
        this.borrower = pripremi(req.getParameter("borrower"));
        this.valid = "1";
        this.locked = "0";
        this.miscdata = pripremi(req.getParameter("miscdata"));
        this.startdate = req.getParameter("startdate") == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : req.getParameter("startdate");
        this.returnuntil = pripremi(req.getParameter("returnUntil")).concat(" 00:00:00");
        this.returneddate = "";
        this.cloned_from = null;
        this.created_at = req.getParameter("createdAt") == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : req.getParameter("createdAt");
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        this.entries = Arrays.stream(req.getParameterValues("oprema")).collect(Collectors.toMap(v -> v, req::getParameter));
    }

    @Override
    public void popuniIzModela(Object o) {
        if(!(o instanceof Transaction)) throw new RuntimeException();

        Transaction t = (Transaction) o;

        this.transaction_id = t.getTransaction_id().toString();
        this.user = t.getUser_id().getNickname();
        this.lender = t.getLender_id().getNickname();
        this.borrower = t.getBorrower_id().getNickname();
        this.valid = t.getValid().toString();
        this.locked = t.getLocked().toString();
        this.miscdata = t.getMiscdata();
        this.startdate = t.getStartdate().toString();
        this.returnuntil = t.getReturnuntil().toString();
        this.returneddate = t.getReturneddate() == null ? "" : t.getReturneddate().toString();
        this.cloned_from = t.getCloned_from() == null ? null : t.getCloned_from().getTransaction_id().toString();
        this.created_at = t.getCreated_at().toString();
        this.updated_at = t.getUpdated_at().toString();

        this.entries = t.getEntries().stream().collect(Collectors.toMap(e -> e.getItem_id().toString(), e -> e.getQuantity().toString()));
    }

    @Override
    public void popuniUModel(Object o) {
        if(!(o instanceof Transaction)) throw new RuntimeException();
        Transaction t = (Transaction) o;

        if(this.transaction_id.isEmpty()) {
            t.setTransaction_id(null);
        } else {
            t.setTransaction_id(Long.valueOf(this.transaction_id));
        }

        t.setUser_id(DAOProvider.getDAO().findUserByNick(this.user).get(0));
        t.setLender_id(DAOProvider.getDAO().findUserByNick(this.lender).get(0));
        t.setBorrower_id(DAOProvider.getDAO().findUserByNick(this.borrower).get(0));
        t.setValid(Integer.valueOf(this.valid));
        t.setLocked(Integer.valueOf(this.locked));
        t.setMiscdata(this.miscdata);
        try {
            t.setStartdate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.startdate));
            t.setReturnuntil(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.returnuntil));
            if (!this.returneddate.isEmpty())
                t.setReturneddate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.returneddate));
            t.setCreated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.created_at));
            t.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.updated_at));
        } catch (ParseException e) {
            System.err.println("Greška prilikom parsiranja datuma u TransactionForm");
        }

        if(this.cloned_from != null) t.setCloned_from(DAOProvider.getDAO().findTransaction(Long.parseLong(this.cloned_from)));
        t.setEntries(this.entries.entrySet().stream().map(mapEntry -> {
            TransactionEntry e = new TransactionEntry();
            e.setTransaction_id(t);
            e.setItem_id(DAOProvider.getDAO().findItem(Long.parseLong(mapEntry.getKey())));
            e.setMiscdata("");
            e.setQuantity(Integer.valueOf(mapEntry.getValue()));
            return e;
        }).collect(Collectors.toList()));
    }

    @Override
    public void azurirajFormu(HttpServletRequest req) {
        this.returnuntil = pripremi(req.getParameter("returnUntil")).concat(" 00:00:00");
        this.entries = Arrays.stream(req.getParameterValues("oprema")).collect(Collectors.toMap(v -> v, req::getParameter));
    }

    @Override
    public void validiraj() {
        greske.clear();
        if(!this.transaction_id.isEmpty()) {
            try {
                Long.parseLong(this.transaction_id);
            } catch(NumberFormatException ex) {
                greske.put("id", "Vrijednost identifikatora nije valjana.");
            }
        }

        if(this.borrower.isEmpty()) {
            greske.put("Alpinist", "Alpinist kojem se posuđuje oprema je obavezan!");
        }

        try {
            if(this.returnuntil.isEmpty())
                greske.put("Datum_povrata", "Datum povrata je obavezan!");
            else {
                if(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.returnuntil).compareTo(new Date()) < 0)
                    greske.put("Datum_povrata", "Datum povrata mora biti poslije datuma posudbe");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(this.entries.isEmpty()) greske.put("Oprema", "Mora se posuditi barem jedan komad opreme!");
        for(Map.Entry<String, String> e : entries.entrySet()) {
            Item i = DAOProvider.getDAO().findItem(Long.parseLong(e.getKey()));
            long activeItems;
            if (transaction_id.isEmpty())
                activeItems = DAOProvider.getDAO().findNumberOfActiveItems(i);
            else
                activeItems = DAOProvider.getDAO().findNumberOfActiveItemsNotFromThisTransaction(i, Long.parseLong(this.transaction_id));
            if(Integer.parseInt(e.getValue()) > (i.getQuantity() - activeItems)) {
                greske.put("Kriva količina", "Komada opreme " + i.getName() + " nema dovoljno u oružarstvu. Treba urediti oružarstvo ako je potrebno!");
            }
        }
    }
}