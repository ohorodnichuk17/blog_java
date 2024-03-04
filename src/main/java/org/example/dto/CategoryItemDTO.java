package org.example.dto;

import lombok.Data;
import org.example.entities.PostEntity;

import java.util.List;

@Data
public class CategoryItemDTO {
    private int id;
    private String name;
    private String urlSlug;
    private String description;
    private List<PostEntity> posts;
}
