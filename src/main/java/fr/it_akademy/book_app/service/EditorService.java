package fr.it_akademy.book_app.service;

import fr.it_akademy.book_app.service.dto.EditorDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.it_akademy.book_app.domain.Editor}.
 */
public interface EditorService {
    /**
     * Save a editor.
     *
     * @param editorDTO the entity to save.
     * @return the persisted entity.
     */
    EditorDTO save(EditorDTO editorDTO);

    /**
     * Updates a editor.
     *
     * @param editorDTO the entity to update.
     * @return the persisted entity.
     */
    EditorDTO update(EditorDTO editorDTO);

    /**
     * Partially updates a editor.
     *
     * @param editorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EditorDTO> partialUpdate(EditorDTO editorDTO);

    /**
     * Get all the editors.
     *
     * @return the list of entities.
     */
    List<EditorDTO> findAll();

    /**
     * Get the "id" editor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EditorDTO> findOne(Long id);

    /**
     * Delete the "id" editor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
