package org.example.controllers;

import org.example.dto.tag.TagCreateDTO;
import org.example.dto.tag.TagEditDTO;
import org.example.dto.tag.TagItemDTO;
import org.example.mapper.TagMapper;
import org.example.entities.TagEntity;
import org.example.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Autowired
    public TagController(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    @GetMapping
    public ResponseEntity<List<TagItemDTO>> getAllTags() {
        List<TagItemDTO> tags = tagRepository.findAll()
                .stream()
                .map(tagMapper::tagItemDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagItemDTO> createTag(@RequestBody TagCreateDTO dto) {
        try {
            TagEntity entity = tagMapper.createDtoToEntity(dto);
            tagRepository.save(entity);
            return new ResponseEntity<>(tagMapper.tagItemDto(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<TagItemDTO> getTagById(@PathVariable int tagId) {
        TagEntity entity = tagRepository.findById(tagId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tagMapper.tagItemDto(entity), HttpStatus.OK);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagItemDTO> updateTag(@PathVariable int tagId, @RequestBody TagEditDTO dto) {
        TagEntity old = tagRepository.findById(tagId).orElse(null);
        if(old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TagEntity entity = tagMapper.editDtoToEntity(dto);
        entity.setId(tagId);
        tagRepository.save(entity);
        return new ResponseEntity<>(tagMapper.tagItemDto(entity), HttpStatus.OK);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable int tagId) {
        TagEntity entity = tagRepository.findById(tagId).orElse(null);
        if(entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            tagRepository.delete(entity);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
