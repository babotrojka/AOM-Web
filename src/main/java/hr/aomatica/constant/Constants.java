package hr.aomatica.constant;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Constants {

    /**
     * Id svih izvještaja u bazi
     */
    public static final int IZVJESTAJ_DB_ID = 3;

    /**
     * Broj izvještaja po stranici
     */
    public static final int PAGE_SIZE = 50;

    /**
     * Broj novih članaka po stranici
     */
    public static final int NEWS_PAGE_SIZE = 30;

    /**
     * Broj preporučenih izvještaja
     */
    public static final int PREPORUCENI_IZVJESTAJI = 5;

    /**
     * Default date for db
     */
    public static final Date DEFAULT_DATE = new GregorianCalendar(1999, Calendar.JANUARY, 1, 0, 0, 1).getTime();

    /**
     * Path for initializing properties
     */
    public static final String initPropertiesPath = "/static/properties/init";

    /**
     * Ime lista proizvođača u excel tablici koja služi za učitavanje oružarstva
     */
    public static final String ORUZARSTVO_VENDOR_SHEET = "Proizvođač";

    /**
     * Ime lista grupa opreme u excel tablici koja služi za učitavanje oružarstva
     */
    public static final String ORUZARSTVO_GROUP_SHEET = "Grupa";

    /**
     * Ime lista cijele opreme u excel tablici koja služi za učitavanje oružarstva
     */
    public static final String ORUZARSTVO_INVENTURA_SHEET = "Inventura";

    /**
     * Identifikator članka 'O nama' u bazi
     */
    public static final long ONAMA_CLANAK_ID = 101;

    /**
     * Identifikator članka 'Kratka povijest odsjeka' u bazi
     */
    public static final long POVIJEST_CLANAK_ID = 102;

}
