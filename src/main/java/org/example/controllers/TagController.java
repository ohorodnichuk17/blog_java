package org.example.controllers;

import org.example.dto.TagItemDTO;
import org.example.mapper.TagMapper;
import org.example.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
                .map(tagMapper::tagEntityToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
