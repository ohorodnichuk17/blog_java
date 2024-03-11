package org.example.dto.post;

import lombok.Data;
import org.example.entities.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

@Data
public class PostCreateDTO {
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private String urlSlug;
    private boolean published;
    private CategoryEntity category;
    private List<Integer> tagIds = new ArrayList<>();
}
