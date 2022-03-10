package hr.aomatica.web.servlets;


import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/drustvo/")
public class ODrustvu extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("active", "drustvo");
        req.setAttribute("onama", DAOProvider.getDAO().findArticle(Constants.ONAMA_CLANAK_ID));
        req.setAttribute("povijest", DAOProvider.getDAO().findArticle(Constants.POVIJEST_CLANAK_ID));
        req.getRequestDispatcher("/WEB-INF/pages/drustvo.jsp").forward(req, resp);
    }
}
