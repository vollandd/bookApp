package fr.it_akademy.book_app.domain;

import static fr.it_akademy.book_app.domain.AuthorTestSamples.*;
import static fr.it_akademy.book_app.domain.BookTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuthorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Author.class);
        Author author1 = getAuthorSample1();
        Author author2 = new Author();
        assertThat(author1).isNotEqualTo(author2);

        author2.setId(author1.getId());
        assertThat(author1).isEqualTo(author2);

        author2 = getAuthorSample2();
        assertThat(author1).isNotEqualTo(author2);
    }

    @Test
    void bookTest() throws Exception {
        Author author = getAuthorRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        author.setBook(bookBack);
        assertThat(author.getBook()).isEqualTo(bookBack);
        assertThat(bookBack.getName()).isEqualTo(author);

        author.book(null);
        assertThat(author.getBook()).isNull();
        assertThat(bookBack.getName()).isNull();
    }
}
