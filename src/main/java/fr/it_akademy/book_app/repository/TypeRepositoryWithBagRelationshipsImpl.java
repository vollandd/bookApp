package fr.it_akademy.book_app.repository;

import fr.it_akademy.book_app.domain.Type;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class TypeRepositoryWithBagRelationshipsImpl implements TypeRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Type> fetchBagRelationships(Optional<Type> type) {
        return type.map(this::fetchBooks);
    }

    @Override
    public Page<Type> fetchBagRelationships(Page<Type> types) {
        return new PageImpl<>(fetchBagRelationships(types.getContent()), types.getPageable(), types.getTotalElements());
    }

    @Override
    public List<Type> fetchBagRelationships(List<Type> types) {
        return Optional.of(types).map(this::fetchBooks).orElse(Collections.emptyList());
    }

    Type fetchBooks(Type result) {
        return entityManager
            .createQuery("select type from Type type left join fetch type.books where type.id = :id", Type.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Type> fetchBooks(List<Type> types) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, types.size()).forEach(index -> order.put(types.get(index).getId(), index));
        List<Type> result = entityManager
            .createQuery("select type from Type type left join fetch type.books where type in :types", Type.class)
            .setParameter("types", types)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
