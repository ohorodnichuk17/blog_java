package org.example.dto.tag;

import lombok.Data;
import org.example.entities.PostTagEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagItemDTO {
    private int id;
    private String name;
    private String urlSlug;
    private String description;
    private List<PostTagEntity> postTags = new ArrayList<>();
}
