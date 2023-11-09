package fr.it_akademy.book_app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeDTO.class);
        TypeDTO typeDTO1 = new TypeDTO();
        typeDTO1.setId(1L);
        TypeDTO typeDTO2 = new TypeDTO();
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO2.setId(typeDTO1.getId());
        assertThat(typeDTO1).isEqualTo(typeDTO2);
        typeDTO2.setId(2L);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
        typeDTO1.setId(null);
        assertThat(typeDTO1).isNotEqualTo(typeDTO2);
    }
}
