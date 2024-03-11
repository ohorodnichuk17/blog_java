package org.example.mapper;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "entity.posts", target = "posts")
    })
    CategoryItemDTO categoryItemDTO(CategoryEntity entity);

    List<CategoryItemDTO> categoryEntitiesToDtos(List<CategoryEntity> entities);

    CategoryEntity categoryCreateDTO(CategoryCreateDTO dto);

    CategoryEntity categoryEditDTO(CategoryEditDTO dto);

}
