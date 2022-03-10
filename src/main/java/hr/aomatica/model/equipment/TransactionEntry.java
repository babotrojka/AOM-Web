package hr.aomatica.model.equipment;

import javax.persistence.*;

@Entity
@IdClass(TransactionEntryId.class)
@Table(name = "equipment__transactionentry")
public class TransactionEntry {
    private Transaction transaction_id;
    private Item item_id;
    private Integer quantity;
    private String miscdata = "";

    @Id
    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")
    public Transaction getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Transaction transaction_id) {
        this.transaction_id = transaction_id;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    public Item getItem_id() {
        return item_id;
    }

    public void setItem_id(Item item_id) {
        this.item_id = item_id;
    }

    @Column
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column
    public String getMiscdata() {
        return miscdata;
    }

    public void setMiscdata(String miscdata) {
        this.miscdata = miscdata;
    }
}
