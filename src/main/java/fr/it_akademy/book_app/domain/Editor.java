package fr.it_akademy.book_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Editor.
 */
@Entity
@Table(name = "editor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Editor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "editor_name")
    private String editorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "name", "names" }, allowSetters = true)
    private Book book;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Editor id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditorName() {
        return this.editorName;
    }

    public Editor editorName(String editorName) {
        this.setEditorName(editorName);
        return this;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Editor book(Book book) {
        this.setBook(book);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Editor)) {
            return false;
        }
        return getId() != null && getId().equals(((Editor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Editor{" +
            "id=" + getId() +
            ", editorName='" + getEditorName() + "'" +
            "}";
    }
}
