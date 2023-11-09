package fr.it_akademy.book_app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class AuthorMapperTest {

    private AuthorMapper authorMapper;

    @BeforeEach
    public void setUp() {
        authorMapper = new AuthorMapperImpl();
    }
}
