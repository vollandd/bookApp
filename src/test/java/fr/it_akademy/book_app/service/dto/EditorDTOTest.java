package fr.it_akademy.book_app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EditorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EditorDTO.class);
        EditorDTO editorDTO1 = new EditorDTO();
        editorDTO1.setId(1L);
        EditorDTO editorDTO2 = new EditorDTO();
        assertThat(editorDTO1).isNotEqualTo(editorDTO2);
        editorDTO2.setId(editorDTO1.getId());
        assertThat(editorDTO1).isEqualTo(editorDTO2);
        editorDTO2.setId(2L);
        assertThat(editorDTO1).isNotEqualTo(editorDTO2);
        editorDTO1.setId(null);
        assertThat(editorDTO1).isNotEqualTo(editorDTO2);
    }
}
