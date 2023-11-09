package fr.it_akademy.book_app.domain;

import static fr.it_akademy.book_app.domain.BookTestSamples.*;
import static fr.it_akademy.book_app.domain.EditorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EditorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Editor.class);
        Editor editor1 = getEditorSample1();
        Editor editor2 = new Editor();
        assertThat(editor1).isNotEqualTo(editor2);

        editor2.setId(editor1.getId());
        assertThat(editor1).isEqualTo(editor2);

        editor2 = getEditorSample2();
        assertThat(editor1).isNotEqualTo(editor2);
    }

    @Test
    void bookTest() throws Exception {
        Editor editor = getEditorRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        editor.setBook(bookBack);
        assertThat(editor.getBook()).isEqualTo(bookBack);

        editor.book(null);
        assertThat(editor.getBook()).isNull();
    }
}
