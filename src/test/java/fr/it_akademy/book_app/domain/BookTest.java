package fr.it_akademy.book_app.domain;

import static fr.it_akademy.book_app.domain.AuthorTestSamples.*;
import static fr.it_akademy.book_app.domain.BookTestSamples.*;
import static fr.it_akademy.book_app.domain.EditorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BookTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Book.class);
        Book book1 = getBookSample1();
        Book book2 = new Book();
        assertThat(book1).isNotEqualTo(book2);

        book2.setId(book1.getId());
        assertThat(book1).isEqualTo(book2);

        book2 = getBookSample2();
        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void authorTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Author authorBack = getAuthorRandomSampleGenerator();

        book.addAuthor(authorBack);
        assertThat(book.getAuthors()).containsOnly(authorBack);

        book.removeAuthor(authorBack);
        assertThat(book.getAuthors()).doesNotContain(authorBack);

        book.authors(new HashSet<>(Set.of(authorBack)));
        assertThat(book.getAuthors()).containsOnly(authorBack);

        book.setAuthors(new HashSet<>());
        assertThat(book.getAuthors()).doesNotContain(authorBack);
    }

    @Test
    void editorTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Editor editorBack = getEditorRandomSampleGenerator();

        book.setEditor(editorBack);
        assertThat(book.getEditor()).isEqualTo(editorBack);

        book.editor(null);
        assertThat(book.getEditor()).isNull();
    }
}
