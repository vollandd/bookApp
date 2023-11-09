package fr.it_akademy.book_app.web.rest;

import fr.it_akademy.book_app.repository.EditorRepository;
import fr.it_akademy.book_app.service.EditorService;
import fr.it_akademy.book_app.service.dto.EditorDTO;
import fr.it_akademy.book_app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link fr.it_akademy.book_app.domain.Editor}.
 */
@RestController
@RequestMapping("/api/editors")
public class EditorResource {

    private final Logger log = LoggerFactory.getLogger(EditorResource.class);

    private static final String ENTITY_NAME = "editor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EditorService editorService;

    private final EditorRepository editorRepository;

    public EditorResource(EditorService editorService, EditorRepository editorRepository) {
        this.editorService = editorService;
        this.editorRepository = editorRepository;
    }

    /**
     * {@code POST  /editors} : Create a new editor.
     *
     * @param editorDTO the editorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new editorDTO, or with status {@code 400 (Bad Request)} if the editor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EditorDTO> createEditor(@RequestBody EditorDTO editorDTO) throws URISyntaxException {
        log.debug("REST request to save Editor : {}", editorDTO);
        if (editorDTO.getId() != null) {
            throw new BadRequestAlertException("A new editor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EditorDTO result = editorService.save(editorDTO);
        return ResponseEntity
            .created(new URI("/api/editors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /editors/:id} : Updates an existing editor.
     *
     * @param id the id of the editorDTO to save.
     * @param editorDTO the editorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated editorDTO,
     * or with status {@code 400 (Bad Request)} if the editorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the editorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EditorDTO> updateEditor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EditorDTO editorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Editor : {}, {}", id, editorDTO);
        if (editorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, editorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!editorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EditorDTO result = editorService.update(editorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, editorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /editors/:id} : Partial updates given fields of an existing editor, field will ignore if it is null
     *
     * @param id the id of the editorDTO to save.
     * @param editorDTO the editorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated editorDTO,
     * or with status {@code 400 (Bad Request)} if the editorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the editorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the editorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EditorDTO> partialUpdateEditor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EditorDTO editorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Editor partially : {}, {}", id, editorDTO);
        if (editorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, editorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!editorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EditorDTO> result = editorService.partialUpdate(editorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, editorDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /editors} : get all the editors.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of editors in body.
     */
    @GetMapping("")
    public List<EditorDTO> getAllEditors() {
        log.debug("REST request to get all Editors");
        return editorService.findAll();
    }

    /**
     * {@code GET  /editors/:id} : get the "id" editor.
     *
     * @param id the id of the editorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the editorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EditorDTO> getEditor(@PathVariable Long id) {
        log.debug("REST request to get Editor : {}", id);
        Optional<EditorDTO> editorDTO = editorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(editorDTO);
    }

    /**
     * {@code DELETE  /editors/:id} : delete the "id" editor.
     *
     * @param id the id of the editorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEditor(@PathVariable Long id) {
        log.debug("REST request to delete Editor : {}", id);
        editorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
