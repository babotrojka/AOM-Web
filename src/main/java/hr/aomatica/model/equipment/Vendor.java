package hr.aomatica.model.equipment;

import hr.aomatica.constant.Constants;

import javax.persistence.*;
import java.util.Date;


@NamedQueries({
        @NamedQuery(name="Vendor.upitPoImenu",query="select v from Vendor as v where v.name=:n")
})
@Entity
@Table(name = "equipment__vendor")
public class Vendor {
    private Long vendor_id;
    private String name;
    private String description = "";
    private String web = "";
    private String miscdata = "";
    private Date created_at = new Date();
    private Date updated_at = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
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
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
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
}
