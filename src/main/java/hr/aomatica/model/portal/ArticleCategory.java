package hr.aomatica.model.portal;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "portal__category")
public class ArticleCategory {
    private Long id;
    private String category;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleCategory)) return false;
        ArticleCategory that = (ArticleCategory) o;
        return Objects.equals(id, that.id) && Objects.equals(category, that.category) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, description);
    }
}
