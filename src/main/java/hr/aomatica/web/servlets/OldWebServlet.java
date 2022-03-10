package hr.aomatica.web.servlets;

import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet koji preusmjerava zahtjeve za izvještajima i novostima sa starog weba na novi web
 */
@WebServlet("/portal/*")
public class OldWebServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/article/show" -> vratiIzvjestaj(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void vratiIzvjestaj(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getContextPath() + "/izvjestaji/" + req.getParameter("id"));
    }
}
