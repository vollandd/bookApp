package fr.it_akademy.book_app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class BookMapperTest {

    private BookMapper bookMapper;

    @BeforeEach
    public void setUp() {
        bookMapper = new BookMapperImpl();
    }
}
