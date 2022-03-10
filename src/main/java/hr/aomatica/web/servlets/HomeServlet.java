package hr.aomatica.web.servlets;

import hr.aomatica.constant.Constants;
import hr.aomatica.crypt.CrypterProvider;
import hr.aomatica.dao.DAOException;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.images.BucketNames;
import hr.aomatica.images.ImageSaver;
import hr.aomatica.images.ImageSaverProvider;
import hr.aomatica.model.user.User;
import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@WebServlet("/home/*")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("active", "novosti");

        req.setAttribute("cLength", (int) 800);

        int trenutnaStranica = req.getParameter("stranica") == null ? 1 : Integer.parseInt(req.getParameter("stranica"));
        req.setAttribute("trenutnaStranica", trenutnaStranica);
        long brojVijesti = DAOProvider.getDAO().findNumberOfNews();
        req.setAttribute("brojStranica", Math.ceil(brojVijesti / (double) Constants.NEWS_PAGE_SIZE));
        req.setAttribute("news", DAOProvider.getDAO().findLimitedNumberOfNews(Constants.NEWS_PAGE_SIZE, (trenutnaStranica - 1) * Constants.NEWS_PAGE_SIZE));

        //slideshow
        ImageSaver saver = ImageSaverProvider.getImageSaver(req.getServletContext());
        List<String> filenames = saver.getFilenamesOfBucket(BucketNames.HOMEPAGE);
        req.setAttribute("mainSlika", filenames.get(0));
        req.setAttribute("homeSlike", filenames.subList(1, filenames.size()));
        req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/login" -> login(req, resp);
            case "/promijeni_lozinku" -> promijeniLozinku(req, resp);
        }

    }

    private void promijeniLozinku(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!String.valueOf(req.getSession().getAttribute("currentUserID")).equals(req.getParameter("id"))) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath(),
                    "Zabranjeno!",
                    "Tko si ti??");
            return;
        }

        User current = DAOProvider.getDAO().findUser(Long.parseLong(String.valueOf(req.getSession().getAttribute("currentUserID"))));
        boolean valid = CrypterProvider.getCrypter().verifyHash(req.getParameter("old_pwd"), current.getPassword()) &&
                req.getParameter("new_pwd").length() >= 6 &&
                req.getParameter("new_pwd").equals(req.getParameter("rep_new_pwd"));

        if(!valid) {
            ServletUtil.redirectWithErrorMessage(
                    req,
                    resp,
                    req.getContextPath() + req.getServletPath(),
                    "Neuspješno mijenjanje lozinke!",
                    "Stara lozinka nije ispravna ili se lozinke ne podudaraju!");
            return;
        }

        current.setPassword(CrypterProvider.getCrypter().hash(req.getParameter("new_pwd")));
        DAOProvider.getDAO().updateUser(current);

        ServletUtil.redirectWithSuccessMessage(
                req,
                resp,
                req.getContextPath() + req.getServletPath(),
                "Lozinka uspješno promijenjena!",
                "Uspješno si promijenio lozinku!");
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String u = req.getParameter("username");
        String p = req.getParameter("lozinka");
        List<User> potential = u.contains("@") ? DAOProvider.getDAO().findUserByEmail(u) : DAOProvider.getDAO().findUserByNick(u);
        Function<String, Boolean> savePwd = pwd -> {
            if(potential.size() == 0) return false;
            User user = potential.get(0);
            user.setPassword(pwd);
            try {
                DAOProvider.getDAO().updateUser(user);
            } catch (DAOException daoException) {
                return false;
            }
            return true;
        };

        try {
            if (potential.size() > 0 && CrypterProvider.getCrypter().verifyAndUpdateHash(p, potential.get(0).getPassword(), savePwd)) {
                User user = potential.get(0);
                req.getSession().setAttribute("currentUserID", user.getId());
                req.getSession().setAttribute("currentUserEmail", user.getEmail());
                req.getSession().setAttribute("currentUserFn", user.getFirstname());
                req.getSession().setAttribute("currentUserLn", user.getLastname());
                req.getSession().setAttribute("currentUserNick", user.getNickname());
                req.getSession().setAttribute("currentUserPrivileges", user.getPrivileges());
                //todo dodaj u tablicu user session
            } else {
                req.setAttribute("messageLogin", "Netočan e-mail, nadimak ili lozinka");
                doGet(req, resp);
                return;
            }
            resp.sendRedirect(req.getContextPath());
        } catch (IllegalArgumentException e ) {
            req.setAttribute("messageLogin", "Netočan e-mail, nadimak ili lozinka");
            doGet(req, resp);
        }
    }

}
