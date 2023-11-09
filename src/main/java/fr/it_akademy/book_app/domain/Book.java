package fr.it_akademy.book_app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Book.
 */
@Entity
@Table(name = "book")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Author name;

    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "book" }, allowSetters = true)
    private Set<Editor> names = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Book id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return this.bookName;
    }

    public Book bookName(String bookName) {
        this.setBookName(bookName);
        return this;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Author getName() {
        return this.name;
    }

    public void setName(Author author) {
        this.name = author;
    }

    public Book name(Author author) {
        this.setName(author);
        return this;
    }

    public Set<Editor> getNames() {
        return this.names;
    }

    public void setNames(Set<Editor> editors) {
        if (this.names != null) {
            this.names.forEach(i -> i.setBook(null));
        }
        if (editors != null) {
            editors.forEach(i -> i.setBook(this));
        }
        this.names = editors;
    }

    public Book names(Set<Editor> editors) {
        this.setNames(editors);
        return this;
    }

    public Book addName(Editor editor) {
        this.names.add(editor);
        editor.setBook(this);
        return this;
    }

    public Book removeName(Editor editor) {
        this.names.remove(editor);
        editor.setBook(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        return getId() != null && getId().equals(((Book) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Book{" +
            "id=" + getId() +
            ", bookName='" + getBookName() + "'" +
            "}";
    }
}
