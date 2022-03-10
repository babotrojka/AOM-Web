package hr.aomatica.model.equipment;

import java.io.Serializable;
import java.util.Objects;

public class TransactionEntryId implements Serializable {
    private Transaction transaction_id;
    private Item item_id;

    protected TransactionEntryId() {}

    public TransactionEntryId(Transaction transaction_id, Item item_id) {
        this.transaction_id = transaction_id;
        this.item_id = item_id;
    }

    public Transaction getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Transaction transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Item getItem_id() {
        return item_id;
    }

    public void setItem_id(Item item_id) {
        this.item_id = item_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionEntryId)) return false;
        TransactionEntryId that = (TransactionEntryId) o;
        return Objects.equals(transaction_id, that.transaction_id) && Objects.equals(item_id, that.item_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction_id, item_id);
    }
}
