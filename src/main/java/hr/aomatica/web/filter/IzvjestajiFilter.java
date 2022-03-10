package hr.aomatica.web.filter;

import hr.aomatica.web.servlets.util.ServletUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/izvjestaji/*")
public class IzvjestajiFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) servletRequest;
        if (r.getMethod().equalsIgnoreCase("POST") && r.getSession().getAttribute("currentUserID") == null) {
            ServletUtil.redirectWithErrorMessage(r, (HttpServletResponse) servletResponse, r.getContextPath(), "Nemate ovlast!", "Nedozvoljena radnja!");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
