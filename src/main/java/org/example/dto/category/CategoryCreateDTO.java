package org.example.dto.category;

import lombok.Data;

@Data
public class CategoryCreateDTO {
    private String name;
    private String urlSlug;
    private String description;
}
