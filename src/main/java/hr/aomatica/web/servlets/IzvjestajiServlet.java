package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.form.ArticleForm;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.user.User;
import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/izvjestaji/*")
public class IzvjestajiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("active", "izvjestaji");
        switch (req.getPathInfo()) {
            case "/" -> getIzvjestaji(req, resp);
            case "/search" -> search(req, resp);
            case "/uredi" -> edit(req, resp);
            case "/osobni" -> getOsobniIzvjestaji(req, resp);
            default -> {
                long id = -1;
                try {
                    id = Long.parseLong(req.getPathInfo().substring(1));
                } catch (NumberFormatException e) {
                    resp.sendRedirect(req.getContextPath());
                    return;
                }
                Article a = DAOProvider.getDAO().findArticle(id);
                req.setAttribute("izvjestaj", a);
                req.setAttribute("izvjestajiBar", DAOProvider.getDAO().findLimitedArticlesOfCategory(Constants.IZVJESTAJ_DB_ID, Constants.PREPORUCENI_IZVJESTAJI, 0));
                req.setAttribute("title", a.getTitle());
                req.getRequestDispatcher("/WEB-INF/pages/single_izvjestaj.jsp").forward(req, resp);
            }
        }
    }

    private void getOsobniIzvjestaji(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if(req.getSession().getAttribute("currentUserID") == null) resp.sendRedirect(req.getContextPath());

        User currentUser = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        req.setAttribute("izvjestaji", DAOProvider.getDAO().findPersonalArticlesOfCategory(Constants.IZVJESTAJ_DB_ID, currentUser));

        req.setAttribute("cLength", (int) 300);
        req.getRequestDispatcher("/WEB-INF/pages/izvjestaji.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        req.setAttribute("i", DAOProvider.getDAO().findArticle(id));
        req.setAttribute("formati", DAOProvider.getDAO().findAllFormats());
        req.setAttribute("korisnici", DAOProvider.getDAO().findAllUsers());
        req.getRequestDispatcher("/WEB-INF/pages/uredi_izvjestaje.jsp").forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter");
        req.setAttribute("izvjestaji", DAOProvider.getDAO().findFilteredArticlesOfCategory(Constants.IZVJESTAJ_DB_ID, filter));
        req.setAttribute("cLength", (int) 200);
        req.getRequestDispatcher("/WEB-INF/pages/izvjestaji.jsp").forward(req, resp);
    }

    private void getIzvjestaji(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int trenutnaStranica = req.getParameter("stranica") == null ? 1 : Integer.parseInt(req.getParameter("stranica"));
        req.setAttribute("trenutnaStranica", trenutnaStranica);
        long brojIzvjestaja = DAOProvider.getDAO().findNumberByCategory(Constants.IZVJESTAJ_DB_ID);
        req.setAttribute("brojStranica", Math.ceil(brojIzvjestaja / (double) Constants.PAGE_SIZE)); //long from dao

        req.setAttribute("izvjestaji", DAOProvider.getDAO().findLimitedArticlesOfCategory(Constants.IZVJESTAJ_DB_ID, Constants.PAGE_SIZE, (trenutnaStranica - 1) * Constants.PAGE_SIZE));

        req.setAttribute("cLength", (int) 200);
        req.getRequestDispatcher("/WEB-INF/pages/izvjestaji.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo().substring(1)) {
            case "dodaj" -> dodajIzvjestaj(req, resp);
            case "uredi" -> urediIzvjestaj(req, resp);
            case "izbrisi" -> izbrisiIzvjestaj(req, resp);
            default -> {
                ServletUtil.redirectResourceDoesntExist(req, resp);
            }
        }
    }

    private void izbrisiIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Article article = DAOProvider.getDAO().findArticle(id);
        DAOProvider.getDAO().deleteArticle(article);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Izvještaj izbrisan",
                String.format("Izvještaj %s od člana %s uspješno izbrisan.", article.getTitle(), article.getWrittenBy().getNickname()));
    }

    private void urediIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleForm form = new ArticleForm();
        form.popuniIzModela(DAOProvider.getDAO().findArticle(Long.parseLong(req.getParameter("id"))));
        form.azurirajFormu(req);

        form.validiraj();
        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath(),
                    "Neuspješno uređivanje izvještaja",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }


        Article article = new Article();
        form.popuniUModel(article);
        DAOProvider.getDAO().updateArticle(article);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Izvještaj uređen",
                String.format("Izvještaj %s od člana %s uspješno uređen.", article.getTitle(), article.getWrittenBy().getNickname()));
    }


    private void dodajIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ArticleForm form = new ArticleForm();
        form.popuniIzHttpRequesta(req);
        form.validiraj();

        if(form.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath(),
                    "Neuspješno dodavanje izvještaja",
                    ServletUtil.getErrorStringFromMap(form.dohvatiGreske()));
            return;
        }

        Article article = new Article();
        form.popuniUModel(article);

        User currentUser = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        if(currentUser.getPrivileges().equals("admin") || currentUser.getPrivileges().equals("editor") ) {
            article.setVisible(1);
            article.setApprovedBy(currentUser);
        } else {
            //obavijesti admina o novom izvještaju
        }

        DAOProvider.getDAO().saveArticle(article);

        String message = String.format("Izvještaj %s od člana %s uspješno dodan.", article.getTitle(), article.getWrittenBy().getNickname())
                    + (currentUser.getPrivileges().equals("admin") || currentUser.getPrivileges().equals("editor") ?
                    "" :
                    "Molim pričekati dok editor ili admin potvrde da izvještaj postane vidljiv!");

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath(),
                "Izvještaj dodan",
                message);

    }
}
