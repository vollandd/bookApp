package fr.it_akademy.book_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "editor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "authors", "types", "editor" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

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

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        if (this.books != null) {
            this.books.forEach(i -> i.setEditor(null));
        }
        if (books != null) {
            books.forEach(i -> i.setEditor(this));
        }
        this.books = books;
    }

    public Editor books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Editor addBooks(Book book) {
        this.books.add(book);
        book.setEditor(this);
        return this;
    }

    public Editor removeBooks(Book book) {
        this.books.remove(book);
        book.setEditor(null);
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
