package hr.aomatica.model.portal;

import hr.aomatica.constant.Constants;
import hr.aomatica.model.user.User;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = "News.broj", query = "select count(id) from News n where n.visible=1"),
        @NamedQuery(name = "News.notConfirmedAndNotVisible", query = "select n from News n where n.approvedBy = null or n.visible = 0 order by n.writetime desc ")
})
@Entity
@Table(name = "portal__news")
public class News {
    private Long id;
    private String title;
    private String body;
    private String keywords;
    private Date writetime;
    private Date approvetime = Constants.DEFAULT_DATE;
    private Date showafter = Constants.DEFAULT_DATE;
    private Date hideafter = Constants.DEFAULT_DATE;
    private User writtenBy;
    private User approvedBy;
    private Format format;
    private NewsCategory category;
    private String charset;
    private Integer visible;
    private Date created_at = Constants.DEFAULT_DATE;
    private Date updated_at = Constants.DEFAULT_DATE;
    private String headerImg;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Column
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column
    public Date getWritetime() {
        return writetime;
    }

    public void setWritetime(Date writetime) {
        this.writetime = writetime;
    }

    @Column
    public Date getApprovetime() {
        return approvetime;
    }


    public void setApprovetime(Date approvetime) {
        this.approvetime = approvetime;
    }

    @Column
    public Date getShowafter() {
        return showafter;
    }

    public void setShowafter(Date showafter) {
        this.showafter = showafter;
    }

    @Column
    public Date getHideafter() {
        return hideafter;
    }

    public void setHideafter(Date hideafter) {
        this.hideafter = hideafter;
    }

    @ManyToOne
    @JoinColumn(name = "writtenBy", referencedColumnName = "id")
    public User getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(User writtenBy) {
        this.writtenBy = writtenBy;
    }
    @ManyToOne
    @JoinColumn(name = "approvedBy", referencedColumnName = "id")
    public User getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(User approvedBy) {
        this.approvedBy = approvedBy;
    }

    @ManyToOne
    @JoinColumn(name = "format", referencedColumnName = "id")
    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @ManyToOne
    @JoinColumn(name = "category", referencedColumnName = "id")
    public NewsCategory getCategory() {
        return category;
    }


    public void setCategory(NewsCategory category) {
        this.category = category;
    }

    @Column
    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Column
    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
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
    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }


    /**
     * Zbog razlike u spremanju u bazu, azuriraju se datumi. Metoda treba biti pozvana prilikom azuriranja
     */
    public void update() {
        if(approvetime == null) approvetime = Constants.DEFAULT_DATE;
        if(showafter == null) showafter = Constants.DEFAULT_DATE;
        if(hideafter == null) hideafter = Constants.DEFAULT_DATE;
        if(created_at == null) created_at = Constants.DEFAULT_DATE;
        if(updated_at == null) updated_at = Constants.DEFAULT_DATE;
    }

}
