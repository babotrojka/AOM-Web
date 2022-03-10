package bcrypt;

import hr.aomatica.crypt.Crypter;
import hr.aomatica.crypt.CrypterProvider;
import hr.aomatica.model.util.UserUtil;
import org.junit.jupiter.api.Test;

public class BcryptTest {


    @Test
    public void test_1() {
        Crypter crypterProvider = CrypterProvider.getCrypter();
    }
}
