package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Author;
import fr.it_akademy.book_app.service.dto.AuthorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Author} and its DTO {@link AuthorDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper extends EntityMapper<AuthorDTO, Author> {}
