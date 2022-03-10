package hr.aomatica.web.servlets;

import hr.aomatica.dao.DAO;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.images.BucketIDs;
import hr.aomatica.images.ImageSaver;
import hr.aomatica.images.ImageSaverException;
import hr.aomatica.images.ImageSaverProvider;
import hr.aomatica.model.form.NewsForm;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.portal.News;
import hr.aomatica.model.user.User;
import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(value = "/novosti/*", loadOnStartup = 1)
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 50      // 50 MB
)
public class NovostiSevlet extends HttpServlet {
    DAO dao = DAOProvider.getDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("active", "novosti");
        switch (req.getPathInfo()) {
            case "/uredi" -> getUredi(req, resp);
            default -> {
                long id = -1;
                try {
                    id = Long.parseLong(req.getPathInfo().substring(1));
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath());
                    return;
                }
                News n = dao.findNews(id);
                req.setAttribute("izvjestaj", n);
                req.setAttribute("headerImg", n.getHeaderImg());
                req.setAttribute("title", n.getTitle());
                req.getRequestDispatcher("/WEB-INF/pages/single_izvjestaj.jsp").forward(req, resp);
            }
        }
    }

    private void getUredi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        req.setAttribute("n", DAOProvider.getDAO().findNews(id));
        req.setAttribute("formati", DAOProvider.getDAO().findAllFormats());
        req.setAttribute("kategorije", DAOProvider.getDAO().findAllNewsCategories());
        req.getRequestDispatcher("/WEB-INF/pages/uredi_novosti.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/dodaj" -> dodaj(req, resp);
            case "/izbrisi" -> izbrisi(req, resp);
            case "/uredi" -> uredi(req, resp);
            default -> {
                ServletUtil.redirectResourceDoesntExist(req, resp);
            }
        }
    }

    private void uredi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsForm form = new NewsForm();
        News oldNews = dao.findNews(Long.parseLong(req.getParameter("id")));
        form.popuniIzModela(oldNews);
        form.azurirajFormu(req);

        String oldImage = oldNews.getHeaderImg();
        Part image = req.getPart("headerImg");

        form.validiraj();
        if(image.getSize() > 0) {
            form.validirajSliku();
        }

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath(),
                    "Neuspješno uređivanje izvještaja",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        News news = new News();
        form.popuniUModel(news);

        if(image.getSize() > 0) {
            ImageSaver saver = ImageSaverProvider.getImageSaver(req.getServletContext());

            if(oldImage != null) saver.deleteImage(oldImage);

            String fileId;
            try {
                fileId = saver.saveImage(image.getInputStream(), BucketIDs.NOVOST, image.getSubmittedFileName());
            } catch (ImageSaverException e) {
                ServletUtil.redirectWithErrorMessage(
                        req,
                        resp,
                        req.getContextPath(),
                        "Greška prilikom dodavanja slike",
                        e.getMessage());
                return;
            }
            news.setHeaderImg(fileId);
        }
        
        dao.updateNews(news);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Novost uređena",
                String.format("Novost %s uspješno uređena.", news.getTitle()));
    }

    private void izbrisi(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        News news = dao.findNews(id);
        dao.deleteNews(news);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Novost izbrisana",
                String.format("Novost %s uspješno izbrisana.", news.getTitle()));
    }

    private void dodaj(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        NewsForm newsForm = new NewsForm();
        newsForm.popuniIzHttpRequesta(req);
        newsForm.validiraj();
        newsForm.validirajSliku();

        if(newsForm.imaPogresaka()){
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath(),
                    "Neuspješno dodavanje novosti",
                    ServletUtil.getErrorStringFromMap(newsForm.dohvatiGreske()));
            return;
        }

        News news = new News();
        newsForm.popuniUModel(news);

        Part image = req.getPart("headerImg");
        if(image.getSize() > 0) {
            ImageSaver saver = ImageSaverProvider.getImageSaver(req.getServletContext());

            String fileId;
            try {
                fileId = saver.saveImage(image.getInputStream(), BucketIDs.NOVOST, image.getSubmittedFileName());
            } catch (ImageSaverException e) {
                ServletUtil.redirectWithErrorMessage(
                        req,
                        resp,
                        req.getContextPath(),
                        "Greška prilikom dodavanja slike",
                        e.getMessage());
                return;
            }
            news.setHeaderImg(fileId);
        }

        User currentUser = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        if(currentUser.getPrivileges().equals("admin") || currentUser.getPrivileges().equals("editor") ) {
            news.setVisible(1);
            news.setApprovedBy(currentUser);
        } else {
            //obavijesti admina o novom izvještaju
        }

        dao.saveNews(news);

        String message = String.format("Novost %s uspješno dodana. ", news.getTitle())
                + (currentUser.getPrivileges().equals("admin") || currentUser.getPrivileges().equals("editor") ?
                "" :
                "Molim pričekati dok editor ili admin potvrde da novost postane vidljiva!");

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Novost dodana",
                message);

    }
}
