package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        List<CategoryItemDTO> categories = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryItemDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO dto) {
        try {
            CategoryEntity entity = categoryMapper.categoryCreateDTO(dto);
            categoryRepository.save(entity);
            return new ResponseEntity<>(categoryMapper.categoryItemDTO(entity), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryItemDTO> getById(@PathVariable int categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var result =  categoryMapper.categoryItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CategoryItemDTO> edit(@ModelAttribute CategoryEditDTO dto) {
        var old = categoryRepository.findById(dto.getId()).orElse(null);
        if(old == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        var entity = categoryMapper.categoryEditDTO(dto);
        categoryRepository.save(entity);
        var result = categoryMapper.categoryItemDTO(entity);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        var entity = categoryRepository.findById(id).orElse(null);
        if(entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }  catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
