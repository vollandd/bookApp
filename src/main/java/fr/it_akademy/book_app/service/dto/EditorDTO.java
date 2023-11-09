package fr.it_akademy.book_app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.it_akademy.book_app.domain.Editor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EditorDTO implements Serializable {

    private Long id;

    private String editorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EditorDTO)) {
            return false;
        }

        EditorDTO editorDTO = (EditorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, editorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EditorDTO{" +
            "id=" + getId() +
            ", editorName='" + getEditorName() + "'" +
            "}";
    }
}
