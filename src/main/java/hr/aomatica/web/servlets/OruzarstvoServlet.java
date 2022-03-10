package hr.aomatica.web.servlets;

import hr.aomatica.dao.DAO;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.equipment.Item;
import hr.aomatica.model.equipment.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/oruzarstvo/")
public class OruzarstvoServlet extends HttpServlet {

    private DAO dao = DAOProvider.getDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Item, Long> items = new HashMap<>();
        dao.findAllItems().forEach(i -> items.put(i, dao.findNumberOfActiveItems(i)));
        req.setAttribute("items", items);
        req.setAttribute("users", dao.findAllUsers().stream().filter(u -> u.getLocked() == 0).collect(Collectors.toList()));
        List<Transaction> nonReturned = dao.findNonReturnedTransactions();
        nonReturned.sort(Comparator.comparing(Transaction::getReturnuntil));
        req.setAttribute("transactions", nonReturned);
        req.getRequestDispatcher("/WEB-INF/pages/oruzarstvo/oruzar.jsp").forward(req, resp);
    }
}
