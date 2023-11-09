package fr.it_akademy.book_app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.it_akademy.book_app.IntegrationTest;
import fr.it_akademy.book_app.domain.Editor;
import fr.it_akademy.book_app.repository.EditorRepository;
import fr.it_akademy.book_app.service.dto.EditorDTO;
import fr.it_akademy.book_app.service.mapper.EditorMapper;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EditorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EditorResourceIT {

    private static final String DEFAULT_EDITOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EDITOR_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/editors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EditorRepository editorRepository;

    @Autowired
    private EditorMapper editorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEditorMockMvc;

    private Editor editor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Editor createEntity(EntityManager em) {
        Editor editor = new Editor().editorName(DEFAULT_EDITOR_NAME);
        return editor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Editor createUpdatedEntity(EntityManager em) {
        Editor editor = new Editor().editorName(UPDATED_EDITOR_NAME);
        return editor;
    }

    @BeforeEach
    public void initTest() {
        editor = createEntity(em);
    }

    @Test
    @Transactional
    void createEditor() throws Exception {
        int databaseSizeBeforeCreate = editorRepository.findAll().size();
        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);
        restEditorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(editorDTO)))
            .andExpect(status().isCreated());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeCreate + 1);
        Editor testEditor = editorList.get(editorList.size() - 1);
        assertThat(testEditor.getEditorName()).isEqualTo(DEFAULT_EDITOR_NAME);
    }

    @Test
    @Transactional
    void createEditorWithExistingId() throws Exception {
        // Create the Editor with an existing ID
        editor.setId(1L);
        EditorDTO editorDTO = editorMapper.toDto(editor);

        int databaseSizeBeforeCreate = editorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEditorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(editorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEditors() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        // Get all the editorList
        restEditorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(editor.getId().intValue())))
            .andExpect(jsonPath("$.[*].editorName").value(hasItem(DEFAULT_EDITOR_NAME)));
    }

    @Test
    @Transactional
    void getEditor() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        // Get the editor
        restEditorMockMvc
            .perform(get(ENTITY_API_URL_ID, editor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(editor.getId().intValue()))
            .andExpect(jsonPath("$.editorName").value(DEFAULT_EDITOR_NAME));
    }

    @Test
    @Transactional
    void getNonExistingEditor() throws Exception {
        // Get the editor
        restEditorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEditor() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        int databaseSizeBeforeUpdate = editorRepository.findAll().size();

        // Update the editor
        Editor updatedEditor = editorRepository.findById(editor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEditor are not directly saved in db
        em.detach(updatedEditor);
        updatedEditor.editorName(UPDATED_EDITOR_NAME);
        EditorDTO editorDTO = editorMapper.toDto(updatedEditor);

        restEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, editorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
        Editor testEditor = editorList.get(editorList.size() - 1);
        assertThat(testEditor.getEditorName()).isEqualTo(UPDATED_EDITOR_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, editorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(editorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEditorWithPatch() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        int databaseSizeBeforeUpdate = editorRepository.findAll().size();

        // Update the editor using partial update
        Editor partialUpdatedEditor = new Editor();
        partialUpdatedEditor.setId(editor.getId());

        partialUpdatedEditor.editorName(UPDATED_EDITOR_NAME);

        restEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEditor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEditor))
            )
            .andExpect(status().isOk());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
        Editor testEditor = editorList.get(editorList.size() - 1);
        assertThat(testEditor.getEditorName()).isEqualTo(UPDATED_EDITOR_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEditorWithPatch() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        int databaseSizeBeforeUpdate = editorRepository.findAll().size();

        // Update the editor using partial update
        Editor partialUpdatedEditor = new Editor();
        partialUpdatedEditor.setId(editor.getId());

        partialUpdatedEditor.editorName(UPDATED_EDITOR_NAME);

        restEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEditor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEditor))
            )
            .andExpect(status().isOk());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
        Editor testEditor = editorList.get(editorList.size() - 1);
        assertThat(testEditor.getEditorName()).isEqualTo(UPDATED_EDITOR_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, editorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEditor() throws Exception {
        int databaseSizeBeforeUpdate = editorRepository.findAll().size();
        editor.setId(longCount.incrementAndGet());

        // Create the Editor
        EditorDTO editorDTO = editorMapper.toDto(editor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEditorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(editorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Editor in the database
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEditor() throws Exception {
        // Initialize the database
        editorRepository.saveAndFlush(editor);

        int databaseSizeBeforeDelete = editorRepository.findAll().size();

        // Delete the editor
        restEditorMockMvc
            .perform(delete(ENTITY_API_URL_ID, editor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Editor> editorList = editorRepository.findAll();
        assertThat(editorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
