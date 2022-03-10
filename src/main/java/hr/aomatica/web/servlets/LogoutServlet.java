package hr.aomatica.web.servlets;

import hr.aomatica.crypt.CrypterProvider;
import hr.aomatica.dao.DAOException;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.user.User;
import hr.aomatica.model.util.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@WebServlet("/logout/*")
public class LogoutServlet extends HttpServlet {

    /**
     * Logout
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath());
    }
}
