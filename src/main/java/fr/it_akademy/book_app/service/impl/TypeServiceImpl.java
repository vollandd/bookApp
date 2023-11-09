package fr.it_akademy.book_app.service.impl;

import fr.it_akademy.book_app.domain.Type;
import fr.it_akademy.book_app.repository.TypeRepository;
import fr.it_akademy.book_app.service.TypeService;
import fr.it_akademy.book_app.service.dto.TypeDTO;
import fr.it_akademy.book_app.service.mapper.TypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy.book_app.domain.Type}.
 */
@Service
@Transactional
public class TypeServiceImpl implements TypeService {

    private final Logger log = LoggerFactory.getLogger(TypeServiceImpl.class);

    private final TypeRepository typeRepository;

    private final TypeMapper typeMapper;

    public TypeServiceImpl(TypeRepository typeRepository, TypeMapper typeMapper) {
        this.typeRepository = typeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public TypeDTO save(TypeDTO typeDTO) {
        log.debug("Request to save Type : {}", typeDTO);
        Type type = typeMapper.toEntity(typeDTO);
        type = typeRepository.save(type);
        return typeMapper.toDto(type);
    }

    @Override
    public TypeDTO update(TypeDTO typeDTO) {
        log.debug("Request to update Type : {}", typeDTO);
        Type type = typeMapper.toEntity(typeDTO);
        type = typeRepository.save(type);
        return typeMapper.toDto(type);
    }

    @Override
    public Optional<TypeDTO> partialUpdate(TypeDTO typeDTO) {
        log.debug("Request to partially update Type : {}", typeDTO);

        return typeRepository
            .findById(typeDTO.getId())
            .map(existingType -> {
                typeMapper.partialUpdate(existingType, typeDTO);

                return existingType;
            })
            .map(typeRepository::save)
            .map(typeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeDTO> findAll() {
        log.debug("Request to get all Types");
        return typeRepository.findAll().stream().map(typeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    public Page<TypeDTO> findAllWithEagerRelationships(Pageable pageable) {
        return typeRepository.findAllWithEagerRelationships(pageable).map(typeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeDTO> findOne(Long id) {
        log.debug("Request to get Type : {}", id);
        return typeRepository.findOneWithEagerRelationships(id).map(typeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Type : {}", id);
        typeRepository.deleteById(id);
    }
}
