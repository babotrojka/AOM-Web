package hr.aomatica.model.addressbook;

import javax.persistence.*;

@Entity
@Table(name = "addressbook__email")
public class Email {

    private long id;
    private String email;
    private String comment = "";
    private long main = 0;
    private long entity;
    private long location = 1;

    @Id @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getEmail() {
        return email;
    }

    @Column
    public long getEntity() {
        return entity;
    }

    @Column
    public long getMain() {
        return main;
    }

    @Column
    public String getComment() {
        return comment;
    }

    @Column
    public long getLocation() {
        return location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMain(long main) {
        this.main = main;
    }

    public void setEntity(long entity) {
        this.entity = entity;
    }

    public void setLocation(long location) {
        this.location = location;
    }
}
