package fr.it_akademy.book_app.service.mapper;

import fr.it_akademy.book_app.domain.Type;
import fr.it_akademy.book_app.service.dto.TypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Type} and its DTO {@link TypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TypeMapper extends EntityMapper<TypeDTO, Type> {}
