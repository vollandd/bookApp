package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Author;
import fr.it_akademy.book_app.domain.Book;
import fr.it_akademy.book_app.domain.Editor;
import fr.it_akademy.book_app.service.dto.AuthorDTO;
import fr.it_akademy.book_app.service.dto.BookDTO;
import fr.it_akademy.book_app.service.dto.EditorDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Book} and its DTO {@link BookDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookMapper extends EntityMapper<BookDTO, Book> {
    @Mapping(target = "authors", source = "authors", qualifiedByName = "authorIdSet")
    @Mapping(target = "editor", source = "editor", qualifiedByName = "editorId")
    BookDTO toDto(Book s);

    @Mapping(target = "removeAuthor", ignore = true)
    Book toEntity(BookDTO bookDTO);

    @Named("authorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AuthorDTO toDtoAuthorId(Author author);

    @Named("authorIdSet")
    default Set<AuthorDTO> toDtoAuthorIdSet(Set<Author> author) {
        return author.stream().map(this::toDtoAuthorId).collect(Collectors.toSet());
    }

    @Named("editorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EditorDTO toDtoEditorId(Editor editor);
}
