package hr.aomatica.web.filter;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

@WebFilter("/*")
public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("UTF-8");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if(req.getSession().getAttribute("currentUserID") != null) {
            req.setAttribute("korisnici", DAOProvider.getDAO().findAllUsers());
            req.setAttribute("formati", DAOProvider.getDAO().findAllFormats());
            req.setAttribute("kategorije", DAOProvider.getDAO().findAllNewsCategories());

            if(req.getSession().getAttribute("currentUserPrivileges").equals("admin") || req.getSession().getAttribute("currentUserPrivileges").equals("editor")) {
                req.setAttribute(
                        "nonConfirmed",
                        DAOProvider.getDAO().findNonConfirmedAndNonVisibleNews().size() +
                        DAOProvider.getDAO().findNonConfirmedAndNonVisibleArticlesOfCategory(Constants.IZVJESTAJ_DB_ID).size());
            }
        }


        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
