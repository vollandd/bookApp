package fr.it_akademy.book_app.service;

import fr.it_akademy.book_app.service.dto.TypeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link fr.it_akademy.book_app.domain.Type}.
 */
public interface TypeService {
    /**
     * Save a type.
     *
     * @param typeDTO the entity to save.
     * @return the persisted entity.
     */
    TypeDTO save(TypeDTO typeDTO);

    /**
     * Updates a type.
     *
     * @param typeDTO the entity to update.
     * @return the persisted entity.
     */
    TypeDTO update(TypeDTO typeDTO);

    /**
     * Partially updates a type.
     *
     * @param typeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeDTO> partialUpdate(TypeDTO typeDTO);

    /**
     * Get all the types.
     *
     * @return the list of entities.
     */
    List<TypeDTO> findAll();

    /**
     * Get all the types with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" type.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeDTO> findOne(Long id);

    /**
     * Delete the "id" type.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
