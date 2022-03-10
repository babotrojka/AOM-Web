package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.crypt.CrypterProvider;
import hr.aomatica.dao.DAOException;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.images.BucketNames;
import hr.aomatica.images.ImageSaver;
import hr.aomatica.images.ImageSaverProvider;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.user.User;
import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;

@WebServlet("/index.html")
public class Index extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
