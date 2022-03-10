package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.form.UserForm;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.portal.News;
import hr.aomatica.model.user.UGroup;
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


@WebServlet("/admin/*")
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getSession().getAttribute("currentUserPrivileges").equals("admin")) {
            resp.sendRedirect(req.getContextPath());
        }

        switch (req.getPathInfo().substring(1)) {
            case "korisnici" -> korisniciGet(req, resp);
            default -> {
                ServletUtil.redirectResourceDoesntExist(req, resp);
            }
        }
    }

    private void korisniciGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", DAOProvider.getDAO().findAllUsers());
        req.setAttribute("ugroups", DAOProvider.getDAO().findAllUGroups());

        req.getRequestDispatcher("/WEB-INF/pages/admin/users.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!req.getSession().getAttribute("currentUserPrivileges").equals("admin")) {
            resp.sendRedirect(req.getContextPath());
        }

        switch (req.getPathInfo().substring(1)) {
            case "dodajKorisnika" -> dodajKorisnika(req, resp);
            case "urediKorisnika" -> urediKorisnika(req, resp);
            case "izbrisiKorisnika" -> izbrisiKorisnika(req, resp);
            case "korisnici" -> korisniciGet(req, resp);
            default -> ServletUtil.redirectResourceDoesntExist(req, resp);
        }
    }

    private void izbrisiKorisnika(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        if(req.getParameter("id").equals(req.getSession().getAttribute("currentUserID"))) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath() + "/korisnici",
                    "Neuspješno brisanje",
                    "Nemoguće izbrisati samog sebe!");
            return;
        }

        User userToDelete = DAOProvider.getDAO().findUser(Long.valueOf(req.getParameter("id")));
        DAOProvider.getDAO().deleteUser(userToDelete);
        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/korisnici",
                "Korisnik izbrisan",
                String.format("Korisnik %s %s uspješno izbrisan.", userToDelete.getFirstname(), userToDelete.getLastname()));
    }


    private void urediKorisnika(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserForm userForm = new UserForm();
        userForm.popuniIzHttpRequesta(req);
        userForm.validirajPostojeci();

        if(userForm.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath() + "/korisnici",
                    "Neuspješno uređivanje",
                    ServletUtil.getErrorStringFromMap(userForm.dohvatiGreske()));
            return;
        }

        User user = new User();
        userForm.popuniUModel(user);
        DAOProvider.getDAO().updateUser(user);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/korisnici",
                "Korisnik uređen",
                String.format("Korisnik %s %s uspješno uređen.", user.getFirstname(), user.getLastname()));
    }


    private void dodajKorisnika(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserForm userForm = new UserForm();
        userForm.popuniIzHttpRequesta(req);
        userForm.validirajNovi();

        if(userForm.imaPogresaka()) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath() + "/korisnici",
                    "Neuspješno dodavanje",
                    ServletUtil.getErrorStringFromMap(userForm.dohvatiGreske()));
            return;
        }

        User user = new User();
        userForm.popuniUModel(user);

        DAOProvider.getDAO().saveUser(user);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath() + "/korisnici",
                "Korisnik dodan",
                String.format("Korisnik %s %s uspješno dodan.", user.getFirstname(), user.getLastname()));

    }
}
