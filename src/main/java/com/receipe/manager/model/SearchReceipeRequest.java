package com.receipe.manager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchReceipeRequest {
    private Boolean isVegetarian;
    private Integer servings;
    private String ingredientsInclude;
    private String ingredientsExclude;
    private String instructions;
}
