package hr.aomatica.model.equipment;

import hr.aomatica.constant.Constants;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name="Item.upitZaPosudene",query="select sum(t.quantity) from TransactionEntry as t where t.item_id=:i and t.transaction_id.locked=0"),
        @NamedQuery(name="Item.upitZaPosudeneOsimOveTransakcije",query="select sum(t.quantity) from TransactionEntry as t where t.item_id=:i and t.transaction_id.transaction_id != :trans_id and t.transaction_id.locked=0")

})
@Entity
@Table(name = "equipment__item")
public class Item {
    private Long item_id;
    private Type type_id;
    private String name;
    private Integer quantity = 0;
    private String description = "";
    private String miscdata = "";
    private Date created_at = new Date();
    private Date updated_at = new Date();
    private Integer active = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "type_id", referencedColumnName = "type_id")
    public Type getType_id() {
        return type_id;
    }

    public void setType_id(Type type_id) {
        this.type_id = type_id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Column
    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Item{" +
                "item_id=" + item_id +
                ", type_id=" + type_id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", miscdata='" + miscdata + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", active=" + active +
                '}';
    }
}
