package fr.it_akademy.book_app.service.impl;

import fr.it_akademy.book_app.domain.Editor;
import fr.it_akademy.book_app.repository.EditorRepository;
import fr.it_akademy.book_app.service.EditorService;
import fr.it_akademy.book_app.service.dto.EditorDTO;
import fr.it_akademy.book_app.service.mapper.EditorMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.book_app.domain.Editor}.
 */
@Service
@Transactional
public class EditorServiceImpl implements EditorService {

    private final Logger log = LoggerFactory.getLogger(EditorServiceImpl.class);

    private final EditorRepository editorRepository;

    private final EditorMapper editorMapper;

    public EditorServiceImpl(EditorRepository editorRepository, EditorMapper editorMapper) {
        this.editorRepository = editorRepository;
        this.editorMapper = editorMapper;
    }

    @Override
    public EditorDTO save(EditorDTO editorDTO) {
        log.debug("Request to save Editor : {}", editorDTO);
        Editor editor = editorMapper.toEntity(editorDTO);
        editor = editorRepository.save(editor);
        return editorMapper.toDto(editor);
    }

    @Override
    public EditorDTO update(EditorDTO editorDTO) {
        log.debug("Request to update Editor : {}", editorDTO);
        Editor editor = editorMapper.toEntity(editorDTO);
        editor = editorRepository.save(editor);
        return editorMapper.toDto(editor);
    }

    @Override
    public Optional<EditorDTO> partialUpdate(EditorDTO editorDTO) {
        log.debug("Request to partially update Editor : {}", editorDTO);

        return editorRepository
            .findById(editorDTO.getId())
            .map(existingEditor -> {
                editorMapper.partialUpdate(existingEditor, editorDTO);

                return existingEditor;
            })
            .map(editorRepository::save)
            .map(editorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EditorDTO> findAll() {
        log.debug("Request to get all Editors");
        return editorRepository.findAll().stream().map(editorMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EditorDTO> findOne(Long id) {
        log.debug("Request to get Editor : {}", id);
        return editorRepository.findById(id).map(editorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Editor : {}", id);
        editorRepository.deleteById(id);
    }
}
