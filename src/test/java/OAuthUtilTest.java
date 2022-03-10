import com.google.api.client.json.jackson2.JacksonFactory;
import org.junit.jupiter.api.Test;

public class OAuthUtilTest {

    @Test
    public void jacksonFactoryTest() {
        System.out.println(JacksonFactory.getDefaultInstance());
    }
}
