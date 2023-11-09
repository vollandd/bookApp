package fr.it_akademy.book_app.web.rest;

import fr.it_akademy.book_app.repository.TypeRepository;
import fr.it_akademy.book_app.service.TypeService;
import fr.it_akademy.book_app.service.dto.TypeDTO;
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
 * REST controller for managing {@link fr.it_akademy.book_app.domain.Type}.
 */
@RestController
@RequestMapping("/api/types")
public class TypeResource {

    private final Logger log = LoggerFactory.getLogger(TypeResource.class);

    private static final String ENTITY_NAME = "type";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeService typeService;

    private final TypeRepository typeRepository;

    public TypeResource(TypeService typeService, TypeRepository typeRepository) {
        this.typeService = typeService;
        this.typeRepository = typeRepository;
    }

    /**
     * {@code POST  /types} : Create a new type.
     *
     * @param typeDTO the typeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeDTO, or with status {@code 400 (Bad Request)} if the type has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO) throws URISyntaxException {
        log.debug("REST request to save Type : {}", typeDTO);
        if (typeDTO.getId() != null) {
            throw new BadRequestAlertException("A new type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeDTO result = typeService.save(typeDTO);
        return ResponseEntity
            .created(new URI("/api/types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /types/:id} : Updates an existing type.
     *
     * @param id the id of the typeDTO to save.
     * @param typeDTO the typeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDTO,
     * or with status {@code 400 (Bad Request)} if the typeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeDTO> updateType(@PathVariable(value = "id", required = false) final Long id, @RequestBody TypeDTO typeDTO)
        throws URISyntaxException {
        log.debug("REST request to update Type : {}, {}", id, typeDTO);
        if (typeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeDTO result = typeService.update(typeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /types/:id} : Partial updates given fields of an existing type, field will ignore if it is null
     *
     * @param id the id of the typeDTO to save.
     * @param typeDTO the typeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeDTO,
     * or with status {@code 400 (Bad Request)} if the typeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the typeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeDTO> partialUpdateType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeDTO typeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Type partially : {}, {}", id, typeDTO);
        if (typeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeDTO> result = typeService.partialUpdate(typeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /types} : get all the types.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of types in body.
     */
    @GetMapping("")
    public List<TypeDTO> getAllTypes(@RequestParam(required = false, defaultValue = "true") boolean eagerload) {
        log.debug("REST request to get all Types");
        return typeService.findAll();
    }

    /**
     * {@code GET  /types/:id} : get the "id" type.
     *
     * @param id the id of the typeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeDTO> getType(@PathVariable Long id) {
        log.debug("REST request to get Type : {}", id);
        Optional<TypeDTO> typeDTO = typeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeDTO);
    }

    /**
     * {@code DELETE  /types/:id} : delete the "id" type.
     *
     * @param id the id of the typeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        log.debug("REST request to delete Type : {}", id);
        typeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
