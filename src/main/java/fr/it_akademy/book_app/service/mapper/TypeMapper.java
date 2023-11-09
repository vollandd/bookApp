package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Book;
import fr.it_akademy.book_app.domain.Type;
import fr.it_akademy.book_app.service.dto.BookDTO;
import fr.it_akademy.book_app.service.dto.TypeDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {
    @Mapping(target = "books", source = "books", qualifiedByName = "bookIdSet")
    TypeDTO toDto(Type s);

    @Mapping(target = "removeBook", ignore = true)
    Type toEntity(TypeDTO typeDTO);

    @Named("bookId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BookDTO toDtoBookId(Book book);

    @Named("bookIdSet")
    default Set<BookDTO> toDtoBookIdSet(Set<Book> book) {
        return book.stream().map(this::toDtoBookId).collect(Collectors.toSet());
    }
}
