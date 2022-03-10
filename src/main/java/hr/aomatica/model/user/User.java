package hr.aomatica.model.user;

import hr.aomatica.model.portal.Article;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@NamedQueries({
        @NamedQuery(name="User.upitPoEmailu",query="select u from User as u where u.email=:e"),
        @NamedQuery(name="User.upitPoNicku",query="select u from User as u where u.nickname=:n"),
        @NamedQuery(name="User.upitPoImenu",query="select u from User as u where u.firstname=:f and u.lastname=:l")
})
@Entity
@Table(name = "user")
public class User {

    private Long id;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String nickname;
    private String privileges;
    private Integer locked;
    private Date created_at;
    private Date updated_at;
    private List<Article> articleList;


    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    @Column
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    @Column
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column
    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    @Column
    public Integer getLocked() {
        return locked;
    }

    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    @Column
    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date createdAt) {
        this.created_at = createdAt;
    }

    @Column
    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @OneToMany(mappedBy = "writtenBy")
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", privileges='" + privileges + '\'' +
                ", locked=" + locked +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
