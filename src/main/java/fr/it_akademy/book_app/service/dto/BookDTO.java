package fr.it_akademy.book_app.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link fr.it_akademy.book_app.domain.Book} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookDTO implements Serializable {

    private Long id;

    private String bookName;

    private Set<TypeDTO> types = new HashSet<>();

    private Set<AuthorDTO> authors = new HashSet<>();

    private EditorDTO editor;

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

    public Set<TypeDTO> getTypes() {
        return types;
    }

    public void setTypes(Set<TypeDTO> types) {
        this.types = types;
    }

    public Set<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<AuthorDTO> authors) {
        this.authors = authors;
    }

    public EditorDTO getEditor() {
        return editor;
    }

    public void setEditor(EditorDTO editor) {
        this.editor = editor;
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
            ", types=" + getTypes() +
            ", authors=" + getAuthors() +
            ", editor=" + getEditor() +
            "}";
    }
}
