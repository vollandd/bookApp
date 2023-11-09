package fr.it_akademy.book_app.repository;

import fr.it_akademy.book_app.domain.Type;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TypeRepositoryWithBagRelationships {
    Optional<Type> fetchBagRelationships(Optional<Type> type);

    List<Type> fetchBagRelationships(List<Type> types);

    Page<Type> fetchBagRelationships(Page<Type> types);
}
