package fr.it_akademy.book_app.domain;

import static fr.it_akademy.book_app.domain.BookTestSamples.*;
import static fr.it_akademy.book_app.domain.TypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import fr.it_akademy.book_app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Type.class);
        Type type1 = getTypeSample1();
        Type type2 = new Type();
        assertThat(type1).isNotEqualTo(type2);

        type2.setId(type1.getId());
        assertThat(type1).isEqualTo(type2);

        type2 = getTypeSample2();
        assertThat(type1).isNotEqualTo(type2);
    }

    @Test
    void bookTest() throws Exception {
        Type type = getTypeRandomSampleGenerator();
        Book bookBack = getBookRandomSampleGenerator();

        type.addBook(bookBack);
        assertThat(type.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getTypes()).containsOnly(type);

        type.removeBook(bookBack);
        assertThat(type.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getTypes()).doesNotContain(type);

        type.books(new HashSet<>(Set.of(bookBack)));
        assertThat(type.getBooks()).containsOnly(bookBack);
        assertThat(bookBack.getTypes()).containsOnly(type);

        type.setBooks(new HashSet<>());
        assertThat(type.getBooks()).doesNotContain(bookBack);
        assertThat(bookBack.getTypes()).doesNotContain(type);
    }
}
