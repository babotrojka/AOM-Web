package hr.aomatica.crypt;

public class CrypterProvider {

    private static final Crypter crypter = new UpdatableBCrypt(11); //treba update

    public static Crypter getCrypter() {
        return crypter;
    }
}
