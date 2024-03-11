package org.example.mapper;

import org.example.dto.post.PostItemDTO;
import org.example.dto.post.PostCreateDTO;
import org.example.dto.post.PostEditDTO;
import org.example.entities.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "entity.postTags", target = "postTags")
    })
    PostItemDTO postItemDto(PostEntity entity);

    List<PostItemDTO> postEntitiesToDtos(List<PostEntity> entities);

    PostEntity createDtoEntity(PostCreateDTO dto);

    PostEntity editDtoEntity(PostEditDTO dto);
}
