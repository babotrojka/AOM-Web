package dao.jpa;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.user.User;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JPATest {

    @Test
    public void saveArticle() {
        Article a = new Article();
        a.setTitle("Naslov");
        a.setBody("Sadrzaj");
        a.setKeywords("keywords");
        try {
            a.setWritetime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-07-18 13:45:23"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        User s = DAOProvider.getDAO().findUserByNick("Sabo").get(0);
        a.setWrittenBy(s);
        a.setApprovedBy(null);
        a.setCategory(DAOProvider.getDAO().findCategory((long) Constants.IZVJESTAJ_DB_ID));
        a.setCharset("UTF-8");
        a.setVisible(1);
        a.setInternal(0);

        DAOProvider.getDAO().saveArticle(a);
    }
}
