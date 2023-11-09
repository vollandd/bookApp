package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Book;
import fr.it_akademy.book_app.domain.Editor;
import fr.it_akademy.book_app.service.dto.BookDTO;
import fr.it_akademy.book_app.service.dto.EditorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Editor} and its DTO {@link EditorDTO}.
 */
@Mapper(componentModel = "spring")
public interface EditorMapper extends EntityMapper<EditorDTO, Editor> {
    @Mapping(target = "book", source = "book", qualifiedByName = "bookId")
    EditorDTO toDto(Editor s);

    @Named("bookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoBookId(Book book);
}
