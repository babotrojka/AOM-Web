package hr.aomatica.web.servlets.util;

import com.google.api.Http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ServletUtil {

    public static final String BG_SUCCESS = "bg-success";
    public static final String BG_DANGER = "bg-danger";
    public static final String BG_INFO = "bg-info";

    public static void redirectWithMessage(HttpServletRequest req, HttpServletResponse resp, String redirectPath, String bg, String result, String title, String message) throws ServletException, IOException {
        req.setAttribute("bg", bg);
        req.setAttribute("result", result);
        req.setAttribute("title", title);
        req.setAttribute("message", message);
        resp.setHeader("Refresh", "4; URL=" + redirectPath);
        req.getRequestDispatcher("/WEB-INF/pages/message.jsp").forward(req, resp);
    }

    public static void redirectWithSuccessMessage(HttpServletRequest req, HttpServletResponse resp, String redirectPath, String title, String message) throws ServletException, IOException {
        redirectWithMessage(req, resp, redirectPath, BG_SUCCESS, "Uspjeh!", title, message);
    }

    public static void redirectWithErrorMessage(HttpServletRequest req, HttpServletResponse resp, String redirectPath, String title, String message) throws ServletException, IOException {
        redirectWithMessage(req, resp, redirectPath, BG_DANGER, "Greška!", title, message);
    }

    public static void redirectResourceDoesntExist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectWithMessage(req, resp, req.getContextPath(), BG_INFO, "You are Lost!", "Traženi resurs ne postoji!", "Ne znam kako si dospio ovdje, ali vraćam te na naslovnu stranicu.");
    }

    public static String getErrorStringFromMap(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        map.values().forEach(v -> sb.append(v).append("<br>"));
        return sb.toString();
    }
}
