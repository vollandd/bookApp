package fr.it_akademy.book_app.repository;

import fr.it_akademy.book_app.domain.Editor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Editor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EditorRepository extends JpaRepository<Editor, Long> {}
