package org.example.dto;

import lombok.Data;
import org.example.entities.CategoryEntity;
import org.example.entities.PostTagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostItemDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private String meta;
    private String urlSlug;
    private boolean published;
    private String postedOn;
    private String modified;
    private CategoryEntity category;
    private List<PostTagEntity> postTags = new ArrayList<>();

}
