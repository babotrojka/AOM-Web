package hr.aomatica.model.util;

import hr.aomatica.crypt.UpdatableBCrypt;

public class UserUtil {

    private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11); //treba update

    /**
     * Encodes password
     * @param password
     * @return
     */
    public static String getHexEncodedPassword(String password) {
        if(password.equals("")) return "";
        return bcrypt.hash(password);
    }

    public static boolean verifyHexEncodedPassword(String password, String hash) {
        return bcrypt.verifyHash(password, hash);
    }

}
