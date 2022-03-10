package hr.aomatica.model.equipment;

import hr.aomatica.constant.Constants;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name="Type.upitPoImenuVendoruGrupi",query="select t from Type as t where t.name=:n and t.group_id=:g and t.vendor_id=:v")
})
@Entity
@Table(name = "equipment__type")
public class Type {

    private Long type_id;
    private Group group_id;
    private Vendor vendor_id;
    private String name;
    private String description = "";
    private String picture = "";
    private String web = "";
    private String manual = "";
    private String datasheet = "";
    private String miscdata = "";
    private Date created_at = new Date();
    private Date updated_at = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    public Group getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Group group_id) {
        this.group_id = group_id;
    }

    @ManyToOne
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    public Vendor getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Vendor vendor_id) {
        this.vendor_id = vendor_id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Column
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Column
    public String getManual() {
        return manual;
    }

    public void setManual(String manual) {
        this.manual = manual;
    }

    @Column
    public String getDatasheet() {
        return datasheet;
    }

    public void setDatasheet(String datasheet) {
        this.datasheet = datasheet;
    }

    @Column
    public String getMiscdata() {
        return miscdata;
    }

    public void setMiscdata(String miscdata) {
        this.miscdata = miscdata;
    }

    @Column
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Column
    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type_id=" + type_id +
                ", group_id=" + group_id +
                ", vendor_id=" + vendor_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", web='" + web + '\'' +
                ", manual='" + manual + '\'' +
                ", datasheet='" + datasheet + '\'' +
                ", miscdata='" + miscdata + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
