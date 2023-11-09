package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Author;
import fr.it_akademy.book_app.domain.Book;
import fr.it_akademy.book_app.service.dto.AuthorDTO;
import fr.it_akademy.book_app.service.dto.BookDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "name", source = "name", qualifiedByName = "authorId")
    BookDTO toDto(Book s);

    @Named("authorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthorDTO toDtoAuthorId(Author author);
}
