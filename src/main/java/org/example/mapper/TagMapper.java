package org.example.mapper;

import org.example.dto.TagItemDTO;
import org.example.entities.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TagMapper {

    @Mappings({
            @Mapping(source = "entity.postTags", target = "postTags")
    })
    TagItemDTO tagEntityToDto(TagEntity entity);

}
