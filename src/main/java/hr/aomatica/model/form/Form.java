package hr.aomatica.model.form;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.portal.Article;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class Form {
    /**
     * Mapa s pogreškama. Očekuje se da su ključevi nazivi svojstava a vrijednosti
     * tekstovi pogrešaka.
     */
    Map<String, String> greske = new HashMap<>();

    /**
     * Dohvaća poruku pogreške za traženo svojstvo.
     *
     * @param ime naziv svojstva za koje se traži poruka pogreške
     * @return poruku pogreške ili <code>null</code> ako svojstvo nema pridruženu pogrešku
     */
    public String dohvatiPogresku(String ime) {
        return greske.get(ime);
    }

    /**
     * Provjera ima li barem jedno od svojstava pridruženu pogrešku.
     *
     * @return <code>true</code> ako ima, <code>false</code> inače.
     */
    public boolean imaPogresaka() {
        return !greske.isEmpty();
    }

    /**
     * Provjerava ima li traženo svojstvo pridruženu pogrešku.
     *
     * @param ime naziv svojstva za koje se ispituje postojanje pogreške
     * @return <code>true</code> ako ima, <code>false</code> inače.
     */
    public boolean imaPogresku(String ime) {
        return greske.containsKey(ime);
    }


    public Map<String, String> dohvatiGreske() {
        return greske;
    }

    /**
     * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
     * svojstva ovog formulara.
     *
     * @param req objekt s parametrima
     */
    public abstract void popuniIzHttpRequesta(HttpServletRequest req) throws IOException, ServletException;

    /**
     * Na temelju predanog {@link Article}-a popunjava ovaj formular.
     *
     * @param o objekt koji čuva originalne podatke
     */
    public abstract void popuniIzModela(Object o);

    /**
     * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog
     * objekta. Metodu ne bi trebalo pozivati ako formular prethodno nije
     * validiran i ako nije utvrđeno da nema pogrešaka.
     *
     * @param o domenski objekt koji treba napuniti
     */
    public abstract void popuniUModel(Object o);

    /**
     * Ažurira formu prilikom ažuriranja samog izvještaja
     * @param req
     */
    public abstract void azurirajFormu(HttpServletRequest req) throws IOException, ServletException;


    /**
     * Metoda obavlja validaciju formulara. Formular je prethodno na neki način potrebno
     * napuniti. Metoda provjerava semantičku korektnost svih podataka te po potrebi
     * registrira pogreške u mapu pogrešaka.
     */
    public abstract void validiraj();

    /**
     * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne stringove, što je
     * puno pogodnije za uporabu na webu.
     *
     * @param s string
     * @return primljeni string ako je različit od <code>null</code>, prazan string inače.
     */
    protected String pripremi(String s) {
        if(s==null) return "";
        return s.trim();
    }

}
