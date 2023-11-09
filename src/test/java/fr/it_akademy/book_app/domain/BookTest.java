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
    void nameTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Author authorBack = getAuthorRandomSampleGenerator();

        book.setName(authorBack);
        assertThat(book.getName()).isEqualTo(authorBack);

        book.name(null);
        assertThat(book.getName()).isNull();
    }

    @Test
    void nameTest() throws Exception {
        Book book = getBookRandomSampleGenerator();
        Editor editorBack = getEditorRandomSampleGenerator();

        book.addName(editorBack);
        assertThat(book.getNames()).containsOnly(editorBack);
        assertThat(editorBack.getBook()).isEqualTo(book);

        book.removeName(editorBack);
        assertThat(book.getNames()).doesNotContain(editorBack);
        assertThat(editorBack.getBook()).isNull();

        book.names(new HashSet<>(Set.of(editorBack)));
        assertThat(book.getNames()).containsOnly(editorBack);
        assertThat(editorBack.getBook()).isEqualTo(book);

        book.setNames(new HashSet<>());
        assertThat(book.getNames()).doesNotContain(editorBack);
        assertThat(editorBack.getBook()).isNull();
    }
}
