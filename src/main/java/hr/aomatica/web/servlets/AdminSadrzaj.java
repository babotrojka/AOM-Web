package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.portal.News;
import hr.aomatica.model.user.User;
import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/sadrzaj/*")
public class AdminSadrzaj extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "", "/" -> sadrzajGet(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void sadrzajGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Article> articles = DAOProvider.getDAO().findNonConfirmedAndNonVisibleArticlesOfCategory(Constants.IZVJESTAJ_DB_ID);
        List<News> news = DAOProvider.getDAO().findNonConfirmedAndNonVisibleNews();

        boolean hasContent = articles.size() > 0 || news.size() > 0;
        req.setAttribute("imaSadrzaja", hasContent);
        if(hasContent) {
            req.setAttribute("izvjestaji", articles);
            req.setAttribute("novosti", news);
        }

        req.getRequestDispatcher("/WEB-INF/pages/admin/sadrzaj.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/vidljivIzvjestaj" -> vidljivIzvjestaj(req, resp);
            case "/potvrdiIzvjestaj" -> potvrdiIzvjestaj(req, resp);
            case "/izbrisiIzvjestaj" -> izbrisiIzvjestaj(req, resp);
            case "/vidljivaNovost" -> vidljivaNovost(req, resp);
            case "/potvrdiNovost" -> potvrdiNovost(req, resp);
            case "/izbrisiNovost" -> izbrisiNovost(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void izbrisiNovost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        News news = DAOProvider.getDAO().findNews(id);
        DAOProvider.getDAO().deleteNews(news);

        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }

    private void izbrisiIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        Article article = DAOProvider.getDAO().findArticle(id);
        DAOProvider.getDAO().deleteArticle(article);

        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }

    private void vidljivaNovost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        News newsToConfirm = DAOProvider.getDAO().findNews(Long.parseLong(req.getParameter("id")));
        newsToConfirm.setVisible(1);
        DAOProvider.getDAO().updateNews(newsToConfirm);
        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }

    private void vidljivIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Article articleToConfirm = DAOProvider.getDAO().findArticle(Long.parseLong(req.getParameter("id")));
        articleToConfirm.setVisible(1);
        DAOProvider.getDAO().updateArticle(articleToConfirm);
        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }

    private void potvrdiNovost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        News newsToConfirm = DAOProvider.getDAO().findNews(Long.parseLong(req.getParameter("id")));
        newsToConfirm.setApprovedBy(currentUser);
        newsToConfirm.setApprovetime(new Date());
        newsToConfirm.setVisible(1);
        DAOProvider.getDAO().updateNews(newsToConfirm);
        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }

    private void potvrdiIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        Article articleToConfirm = DAOProvider.getDAO().findArticle(Long.parseLong(req.getParameter("id")));
        articleToConfirm.setApprovedBy(currentUser);
        articleToConfirm.setApprovetime(new Date());
        articleToConfirm.setVisible(1);
        DAOProvider.getDAO().updateArticle(articleToConfirm);
        resp.sendRedirect(req.getContextPath() + req.getServletPath() + "/");
    }
}
