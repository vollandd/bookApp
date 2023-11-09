package fr.it_akademy.book_app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.book_app.domain.Type} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TypeDTO implements Serializable {

    private Long id;

    private String nameType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TypeDTO)) {
            return false;
        }

        TypeDTO typeDTO = (TypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, typeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TypeDTO{" +
            "id=" + getId() +
            ", nameType='" + getNameType() + "'" +
            "}";
    }
}
