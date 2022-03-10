package hr.aomatica.crypt;

import java.util.function.Function;

public interface Crypter {

    /**
     * Hashes given password
     * @param password
     * @return
     */
    String hash(String password);

    /**
     * Verifies that given password corresponds to hash
     * @param password
     * @param hash
     * @return
     */
    boolean verifyHash(String password, String hash);

    /**
     * Verifies that given password corresponds to hash and updates hash using updateFunc
     * @param password
     * @param hash
     * @param updateFunc
     * @return
     */
    boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc);
}
