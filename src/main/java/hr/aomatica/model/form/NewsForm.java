package hr.aomatica.model.form;

import hr.aomatica.constant.Constants;
import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.portal.Article;
import hr.aomatica.model.portal.News;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewsForm extends Form {
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
    private String created_at;
    private String updated_at;
    private String headerImg;

    @Override
    public void popuniIzHttpRequesta(HttpServletRequest req) throws IOException, ServletException {
        this.id = pripremi(req.getParameter("id"));
        this.title = pripremi(req.getParameter("naslov"));
        this.body = pripremi(req.getParameter("sadrzaj"));
        this.keywords = pripremi(req.getParameter("kljucneRijeci"));
        this.writetime = pripremi(req.getParameter("datum")).concat(" 00:00:00"); //2021-07-18
        this.approvetime = pripremi(req.getParameter("approveTime"));
        this.showafter = pripremi(req.getParameter("showAfter"));
        this.hideafter = pripremi(req.getParameter("hideAfter"));
        this.writtenBy = req.getSession().getAttribute("currentUserNick").toString();
        this.approvedBy = pripremi(req.getParameter("approvedBy"));
        this.format = pripremi(req.getParameter("format"));
        this.category = pripremi(req.getParameter("kategorija"));
        this.charset = "UTF-8";
        this.visible = req.getParameter("visible") == null ? "0" : req.getParameter("visible");
        this.created_at = req.getParameter("createdAt") == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : req.getParameter("createdAt");
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        this.headerImg = req.getPart("headerImg").getSize() > 0 ? req.getPart("headerImg").getSubmittedFileName() : null;

    }

    @Override
    public void popuniIzModela(Object o) {
        if(!(o instanceof News)) throw new RuntimeException();

        News a = (News) o;
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
        this.created_at = a.getCreated_at().toString();
        this.updated_at = a.getUpdated_at().toString();
        this.headerImg = a.getHeaderImg();
    }

    @Override
    public void popuniUModel(Object o) {
        if(!(o instanceof News)) throw new RuntimeException();
        News a = (News) o;

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
        a.setCategory(DAOProvider.getDAO().findNewsCategory(Long.parseLong(category)));
        a.setCharset(this.charset);
        a.setVisible(Integer.valueOf(this.visible));
        a.setHeaderImg(this.headerImg);
    }

    public void azurirajFormu(HttpServletRequest req) throws IOException, ServletException {
        this.title = pripremi(req.getParameter("naslov"));
        this.body = pripremi(req.getParameter("sadrzaj"));
        this.keywords = pripremi(req.getParameter("kljucneRijeci"));
        this.writetime = pripremi(req.getParameter("datum")).concat(" 00:00:00");
        this.format = pripremi(req.getParameter("format"));
        this.category = pripremi(req.getParameter("kategorija"));
        this.headerImg = req.getPart("headerImg").getSize() > 0 ? req.getPart("headerImg").getSubmittedFileName() : headerImg;
    }


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

        if(this.writetime.isEmpty()) {
            greske.put("Datum", "Datum je obavezan");
        }

        if(this.category.isEmpty()) {
            greske.put("Kategorija", "Kategorija je obavezna");
        }

        if(this.format.isEmpty()) {
            greske.put("Format", "Format je obavezan");
        }

    }

    public void validirajSliku() {
        if(headerImg != null && !headerImg.toLowerCase().endsWith("jpg") && !headerImg.toLowerCase().endsWith("jpeg")
                && !headerImg.toLowerCase().endsWith("png") && !headerImg.toLowerCase().endsWith("gif") && !headerImg.toLowerCase().endsWith("bmp"))
            greske.put("Slika", "Slika nije ispravnog formata (jpg, png, gif, bmp");

    }
}

