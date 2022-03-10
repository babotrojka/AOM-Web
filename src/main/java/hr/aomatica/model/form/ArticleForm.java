package hr.aomatica.model.form;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.portal.ArticleCategory;
import hr.aomatica.model.portal.Format;
import hr.aomatica.model.user.User;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleForm extends Form{
    private String id;
    private String title;
    private String body;
    private String keywords;
    private String writetime;
    private String approvetime;
    private String showafter;
    private String hideafter;
    private String writtenBy;
    private String approvedBy;
    private String format;
    private String category;
    private String charset;
    private String visible;
    private String internal;
    private String created_at;
    private String updated_at;

    @Override
    public void popuniIzHttpRequesta(HttpServletRequest req) {
        this.id = pripremi(req.getParameter("id"));
        this.title = pripremi(req.getParameter("naslov"));
        this.body = pripremi(req.getParameter("sadrzaj"));
        this.keywords = pripremi(req.getParameter("kljucneRijeci"));
        this.writetime = pripremi(req.getParameter("datum")).concat(" 00:00:00"); //2021-07-18
        this.approvetime = pripremi(req.getParameter("approveTime"));
        this.showafter = pripremi(req.getParameter("showAfter"));
        this.hideafter = pripremi(req.getParameter("hideAfter"));
        this.writtenBy = pripremi(req.getParameter("autor"));
        this.approvedBy = pripremi(req.getParameter("approvedBy"));
        this.format = pripremi(req.getParameter("format"));
        this.category = String.valueOf(Constants.IZVJESTAJ_DB_ID);
        this.charset = "UTF-8";
        this.visible = req.getParameter("visible") == null ? "0" : req.getParameter("visible");
        this.internal = req.getParameter("internal") == null ? "0" : req.getParameter("internal");
        this.created_at = req.getParameter("createdAt") == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : req.getParameter("createdAt");
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @Override
    public void popuniIzModela(Object o) {
        if(!(o instanceof Article)) throw new RuntimeException();

        Article a = (Article) o;
        if(a.getId()==null) {
            this.id = "";
        } else {
            this.id = a.getId().toString();
        }

        this.title = a.getTitle();
        this.body = a.getBody();
        this.keywords = a.getKeywords();
        this.writetime = a.getWritetime().toString();
        this.approvetime = a.getApprovetime().toString();
        this.showafter = a.getShowafter() == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Constants.DEFAULT_DATE) : a.getShowafter().toString();
        this.hideafter = a.getHideafter() == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Constants.DEFAULT_DATE) : a.getHideafter().toString();
        this.writtenBy = a.getWrittenBy().getNickname();
        this.approvedBy = a.getApprovedBy() == null ? "" : a.getApprovedBy().getId().toString();
        this.format = a.getFormat().getId().toString();
        this.category = a.getCategory().getId().toString();
        this.charset = a.getCharset();
        this.visible = a.getVisible().toString();
        this.internal = a.getInternal().toString();
        this.created_at = a.getCreated_at().toString();
        this.updated_at = a.getUpdated_at().toString();
    }


    @Override
    public void popuniUModel(Object o) {
        if(!(o instanceof Article)) throw new RuntimeException();

        Article a = (Article) o;
        if(this.id.isEmpty()) {
            a.setId(null);
        } else {
            a.setId(Long.valueOf(this.id));
        }

        a.setTitle(this.title);
        a.setBody(this.body);
        a.setKeywords(this.keywords);
        try {
            a.setWritetime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.writetime));
            if(!this.approvetime.isEmpty()) a.setApprovetime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.approvetime));
            if(!this.showafter.isEmpty()) a.setShowafter(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.showafter));
            if(!this.hideafter.isEmpty()) a.setHideafter(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.hideafter));
            if(!this.created_at.isEmpty()) a.setCreated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.created_at));
            if(!this.updated_at.isEmpty()) a.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.updated_at));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        a.setWrittenBy(DAOProvider.getDAO().findUserByNick(writtenBy).get(0));
        a.setApprovedBy(!approvedBy.isEmpty() ? DAOProvider.getDAO().findUser(Long.valueOf(approvedBy)): null);
        a.setFormat(DAOProvider.getDAO().findFormat(Long.parseLong(format)));
        a.setCategory(DAOProvider.getDAO().findCategory(Long.valueOf(category)));
        a.setCharset(this.charset);
        a.setVisible(Integer.valueOf(this.visible));
        a.setInternal(Integer.valueOf(this.internal));
    }

    @Override
    public void azurirajFormu(HttpServletRequest req) {
        this.title = pripremi(req.getParameter("naslov"));
        this.body = pripremi(req.getParameter("sadrzaj"));
        this.keywords = pripremi(req.getParameter("kljucneRijeci"));
        this.writetime = pripremi(req.getParameter("datum")).concat(" 00:00:00");
        this.writtenBy = pripremi(req.getParameter("autor"));
        this.format = pripremi(req.getParameter("format"));
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

        if(this.title.isEmpty()) {
            greske.put("Naslov", "Naslov je obavezan!");
        }

        if(this.body.isEmpty()) {
            greske.put("Sadržaj", "Sadržaj je obavezan!");
        }

        if(this.keywords.isEmpty()) {
            greske.put("ključneRiječi", "Ključne riječi su obavezne!");
        }

        if(this.writetime.isEmpty()) {
            greske.put("Datum", "Datum je obavezan");
        }

        if(this.writtenBy.isEmpty()) {
            greske.put("Autor", "Autor je obavezan");
        } else {
            if(DAOProvider.getDAO().findUserByNick(writtenBy).size() == 0)
                greske.put("Autor", "Član " + writtenBy + " ne postoji. Molim odaberi jednog od članova iz dropdown menija!");
        }
    }

}
