package fr.it_akademy.book_app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.book_app.domain.Book} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookDTO implements Serializable {

    private Long id;

    private String bookName;

    private AuthorDTO name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public AuthorDTO getName() {
        return name;
    }

    public void setName(AuthorDTO name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookDTO)) {
            return false;
        }

        BookDTO bookDTO = (BookDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookDTO{" +
            "id=" + getId() +
            ", bookName='" + getBookName() + "'" +
            ", name=" + getName() +
            "}";
    }
}
