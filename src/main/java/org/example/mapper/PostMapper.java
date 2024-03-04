package org.example.mapper;

import org.example.dto.PostItemDTO;
import org.example.entities.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "entity.postTags", target = "postTags")
    })
    PostItemDTO postEntityToDto(PostEntity entity);

}
