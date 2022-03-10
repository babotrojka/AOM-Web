package hr.aomatica.web.servlets.error;

import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/error-500/")
public class Error500Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtil.redirectWithErrorMessage(
                req,
                resp,
                req.getContextPath(),
                "Greška na stranici!",
                "Žao mi je, ali dogodila se greška. Molim javi se Sabi!"
        );
    }
}
