package org.example.mapper;

import org.example.dto.tag.TagItemDTO;
import org.example.dto.tag.TagCreateDTO;
import org.example.dto.tag.TagEditDTO;
import org.example.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagItemDTO tagItemDto(TagEntity entity);

    List<TagItemDTO> entitiesToDtos(List<TagEntity> entities);

    TagEntity createDtoToEntity(TagCreateDTO dto);

    TagEntity editDtoToEntity(TagEditDTO dto);
}
