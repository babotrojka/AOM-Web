package hr.aomatica.model.equipment;

import hr.aomatica.constant.Constants;
import hr.aomatica.model.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries(
        @NamedQuery(name = "Transaction.upitNevracenih", query = "select t from Transaction t where t.locked = 0")
)
@Entity
@Table(name = "equipment__transaction")
public class Transaction {
    private Long transaction_id;
    private User user_id;
    private User lender_id;
    private User borrower_id;
    private Integer valid = 1;
    private Integer locked = 0;
    private String miscdata;
    private Date startdate;
    private Date returnuntil;
    private Date returneddate;
    private Transaction cloned_from;
    private Date created_at;
    private Date updated_at = Constants.DEFAULT_DATE;

    private List<TransactionEntry> entries;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    @ManyToOne
    @JoinColumn(name = "lender_id", referencedColumnName = "id")
    public User getLender_id() {
        return lender_id;
    }

    public void setLender_id(User lender_id) {
        this.lender_id = lender_id;
    }

    @ManyToOne
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    public User getBorrower_id() {
        return borrower_id;
    }

    public void setBorrower_id(User borrower_id) {
        this.borrower_id = borrower_id;
    }

    @Column
    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Column
    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Column
    public String getMiscdata() {
        return miscdata;
    }

    public void setMiscdata(String miscdata) {
        this.miscdata = miscdata;
    }

    @Column
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Column
    public Date getReturnuntil() {
        return returnuntil;
    }

    public void setReturnuntil(Date returnuntil) {
        this.returnuntil = returnuntil;
    }

    @Column
    public Date getReturneddate() {
        return returneddate;
    }

    public void setReturneddate(Date returneddate) {
        this.returneddate = returneddate;
    }

    @ManyToOne
    @JoinColumn(name = "cloned_from", referencedColumnName = "transaction_id")
    public Transaction getCloned_from() {
        return cloned_from;
    }

    public void setCloned_from(Transaction cloned_from) {
        this.cloned_from = cloned_from;
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

    @OneToMany(mappedBy = "transaction_id", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public List<TransactionEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<TransactionEntry> entries) {
        this.entries = entries;
    }
}
