package hr.aomatica.model.form;

import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.user.User;
import hr.aomatica.model.util.UserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserForm extends Form {

    private String id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String nickname;
    private String privileges;
    private String locked;
    private String created_at;
    private String updated_at;

    @Override
    public void popuniIzHttpRequesta(HttpServletRequest req) {
        this.id = req.getParameter("id") == null ? "" : req.getParameter("id");
        this.email = pripremi(req.getParameter("email"));
        this.password = UserUtil.getHexEncodedPassword(pripremi(req.getParameter("lozinka")));
        this.firstname = pripremi(req.getParameter("ime"));
        this.lastname = pripremi(req.getParameter("prezime"));
        this.nickname = pripremi(req.getParameter("nadimak"));
        if(this.nickname.isEmpty()) nickname = firstname.concat(" ").concat(lastname);
        this.locked = req.getParameter("zakljucan") == null ? "0" : "1";
        this.privileges = req.getParameter("group");
        this.created_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @Override
    public void popuniIzModela(Object o) {
        if(!(o instanceof User)) throw new RuntimeException();

        User r = (User) o;
        if(r.getId()==null) {
            this.id = "";
        } else {
            this.id = r.getId().toString();
        }

        this.email = r.getEmail();
        this.password = r.getPassword();
        this.firstname = r.getFirstname();
        this.lastname = r.getLastname();
        this.nickname = r.getNickname();
        this.locked = r.getLocked().toString();
        this.created_at = r.getCreated_at().toString();
        this.updated_at = r.getUpdated_at().toString();
    }

    @Override
    public void popuniUModel(Object o) {
        if(!(o instanceof User)) throw new RuntimeException();

        User u = (User) o;
        if(this.id.isEmpty()) {
            u.setId(null);
        } else {
            u.setId(Long.valueOf(this.id));
        }

        u.setEmail(this.email);
        u.setPassword(this.password);
        u.setFirstname(this.firstname);
        u.setLastname(this.lastname);
        u.setNickname(this.nickname);
        u.setPrivileges(this.privileges);
        u.setLocked(Integer.valueOf(this.locked));
        try {
            u.setCreated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.created_at));
            u.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.updated_at));
        } catch (ParseException e) {
            e.printStackTrace(); //todo write to log
        }
    }

    @Override
    public void azurirajFormu(HttpServletRequest req) throws IOException, ServletException {

    }

    @Override
    public void validiraj() {
        greske.clear();
        if(!this.id.isEmpty()) {
            try {
                Long.parseLong(this.id);
            } catch(NumberFormatException ex) {
                greske.put("id", "Vrijednost identifikatora nije valjana.");
            }
        }

        if(this.firstname.isEmpty()) {
            greske.put("firstName", "Ime je obavezno!");
        }

        if(this.lastname.isEmpty()) {
            greske.put("lastName", "Prezime je obavezno!");
        }

        if(this.email.isEmpty()) {
            greske.put("email", "Email je obavezan!");
        } else {
            int l = email.length();
            int p = email.indexOf('@');
            if(l<3 || p==-1 || p==0 || p==l-1) {
                greske.put("email", "Email nije ispravnog formata.");
            }
        }

        if(this.nickname.isEmpty()) {
            greske.put("nick", "Nadimak je obavezan");
        }

        if(this.password.isEmpty()) {
            greske.put("password", "password je obavezan");
        }
    }

    /**
     * Validira novog korisnika
     */
    public void validirajNovi() {
        validiraj();
        if(DAOProvider.getDAO().findUserByEmail(this.email).size() > 0)
            greske.put("email", "Email već postoji.");

        if(DAOProvider.getDAO().findUserByNick(this.nickname).size() > 0)
            greske.put("nadimak", "Nadimak već postoji.");

        if(DAOProvider.getDAO().findUserByFirstAndLast(this.firstname, this.lastname).size() > 0)
            greske.put("firstname", "Korisnik već postoji.");

    }

    public void validirajPostojeci() {
        User existingUser = DAOProvider.getDAO().findUser(Long.valueOf(this.id));
        if(this.password.isEmpty()) this.password = existingUser.getPassword();

        validiraj();

        List<User> result;
        result = DAOProvider.getDAO().findUserByEmail(this.email);
        if(result.size() > 0 && !result.get(0).getId().equals(Long.valueOf(this.id)))
            greske.put("email", "Email već postoji.");

        result = DAOProvider.getDAO().findUserByNick(this.nickname);
        if(result.size() > 0 && !result.get(0).getId().equals(Long.valueOf(this.id)))
            greske.put("nadimak", "Nadimak već postoji.");

        result = DAOProvider.getDAO().findUserByFirstAndLast(this.firstname, this.lastname);
        if(result.size() > 0 && !result.get(0).getId().equals(Long.valueOf(this.id)))
            greske.put("firstname", "Korisnik već postoji.");

        if(!imaPogresaka()) this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }
}
