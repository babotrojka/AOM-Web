package date;

import hr.aomatica.constant.Constants;
import org.junit.jupiter.api.Test;

import javax.persistence.Table;
import java.text.SimpleDateFormat;

public class DateTest {

    @Test
    public void dateTest() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Constants.DEFAULT_DATE));
    }
}
