package hr.aomatica.model.form;

import hr.aomatica.dao.DAOProvider;
import hr.aomatica.model.equipment.Group;
import hr.aomatica.model.equipment.Item;
import hr.aomatica.model.equipment.Type;
import hr.aomatica.model.equipment.Vendor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemForm extends Form{

    private String item_id;
    private String type_id;
    private String vendor;
    private String group;
    private String name;
    private String quantity = "0";
    private String description = "";
    private String miscdata = "";
    private String created_at;
    private String updated_at;
    private String active = "1";



    @Override
    public void popuniIzHttpRequesta(HttpServletRequest req) throws IOException, ServletException {
        this.item_id = pripremi(req.getParameter("id"));
        this.type_id = !pripremi(req.getParameter("type")).equals("none") ? pripremi(req.getParameter("type")) : pripremi(req.getParameter("newType"));
        this.vendor = pripremi(req.getParameter("vendor"));
        this.group = pripremi(req.getParameter("group"));
        this.name = pripremi(req.getParameter("name"));
        this.quantity = pripremi(req.getParameter("quantity"));
        this.description = pripremi(req.getParameter("description"));
        this.miscdata = pripremi(req.getParameter("miscdata"));
        this.created_at = req.getParameter("createdAt") == null ? new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) : req.getParameter("createdAt");
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @Override
    public void popuniIzModela(Object o) {
        if(!(o instanceof Item)) return;

        Item i = (Item) o;

        this.item_id = i.getItem_id().toString();
        this.type_id = i.getType_id().getName();
        this.vendor = i.getType_id().getVendor_id().getVendor_id().toString();
        this.group = i.getType_id().getGroup_id().getGroup_id().toString();
        this.name = i.getName();
        this.quantity = i.getQuantity().toString();
        this.description = i.getDescription();
        this.miscdata = i.getMiscdata();
        this.created_at = i.getCreated_at().toString();
        this.updated_at = i.getUpdated_at().toString();
        this.active = i.getActive().toString();
    }

    @Override
    public void popuniUModel(Object o) {
        if (!(o instanceof Item)) return;

        Item i = (Item) o;

        if(!item_id.isEmpty()) i.setItem_id(Long.parseLong(item_id));
        i.setName(name);
        i.setQuantity(Integer.parseInt(quantity));
        i.setDescription(description);
        i.setMiscdata(miscdata);
        try {
            i.setCreated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.created_at));
            i.setUpdated_at(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(this.updated_at));
        } catch (ParseException e) {
            System.err.println("Greška prilikom parsiranja datuma u ItemForm");
        }
        i.setActive(Integer.parseInt(active));

        Vendor v = DAOProvider.getDAO().findVendor(Long.parseLong(vendor));
        Group g = DAOProvider.getDAO().findGroup(Long.parseLong(group));
        List<Type> ts = DAOProvider.getDAO().findEquipmentTypeByNameGroupAndVendor(type_id, g, v);
        if(ts.size() > 0)
            i.setType_id(ts.get(0));
        else {
            Type t = new Type();
            t.setVendor_id(v);
            t.setGroup_id(g);
            t.setName(name);
            i.setType_id(t);
        }
    }

    @Override
    public void azurirajFormu(HttpServletRequest req) throws IOException, ServletException {
        name = req.getParameter("name");
        quantity = req.getParameter("quantity");
        description = req.getParameter("description");
        this.updated_at = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
    }

    @Override
    public void validiraj() {
        if(Integer.parseInt(quantity) <= 0) greske.put("qty", "Količina mora biti veća ili jednaka 0");

        if(type_id.isEmpty()) greske.put("type", "Tip ne smije biti prazan!");
        if(vendor.isEmpty()) greske.put("vendor", "Proizvođač ne smije biti prazan!");
        if(group.isEmpty()) greske.put("group", "Grupa ne smije biti prazna!");

        if(name.isEmpty()) greske.put("name", "Ime ne smije biti prazno!");
    }
}
