package com.receipe.manager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.receipe.manager.model.SearchReceipeRequest;
import com.receipe.manager.repository.model.Receipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceipeControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Testing creating a receipe")
    public void givenReceipeDetailsToCreateReceipeExpectedCorrectResponse() throws Exception {
        //given
        Receipe receipe = new Receipe();
        receipe.setReceipeName("Creamy Garlic Pasta");
        receipe.setVegetarian(true);
        receipe.setInstructions("saute in the pan");
        receipe.setIngredients("pasta,garlic,tomatoes,oil");
        receipe.setServings(4);

        //then
        mockMvc.perform(post("/api/v1/receipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(receipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receipeName").value("Creamy Garlic Pasta"))
                .andExpect(jsonPath("$.vegetarian").value(true))
                .andExpect(jsonPath("$.servings").value(4));
    }


    @Test
    @DisplayName("Testing getting receipes from DB")
    public void gettingReceipesFromDbExpectedCorrectResponse() throws Exception {
        //when & then
        mockMvc.perform(get("/api/v1/receipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @DisplayName("Testing getting receipes from DB with a search request")
    public void gettingReceipesWithASearchRequestFromDbExpectedCorrectResponse() throws Exception {
        //given
        SearchReceipeRequest searchReceipeRequest = new SearchReceipeRequest();
        searchReceipeRequest.setServings(2);
        searchReceipeRequest.setIngredientsExclude("butter");
        searchReceipeRequest.setInstructions("saute");

        //when & then
        mockMvc.perform(get("/api/v1/receipes/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(searchReceipeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("Testing getting receipes from DB with a search request and get no receipes found exception")
    public void gettingReceipesWithASearchRequestFromDbExpectedException() throws Exception {
        //given
        SearchReceipeRequest searchReceipeRequest = new SearchReceipeRequest();
        searchReceipeRequest.setServings(2);
        searchReceipeRequest.setIngredientsExclude("butter");
        searchReceipeRequest.setInstructions("10");

        //when & then
        mockMvc.perform(get("/api/v1/receipes/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(searchReceipeRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("No receipes found in DB with the specified search criteria"));
    }

    @Test
    @DisplayName("Testing updating a receipe")
    public void givenReceipeDetailsToUpdateReceipeExpectedCorrectResponse() throws Exception {
        //given
        Receipe receipe = new Receipe();
        receipe.setId(1);
        receipe.setReceipeName("Creamy Garlic Pasta");
        receipe.setVegetarian(true);
        receipe.setInstructions("saute in the pan for 20 mins");
        receipe.setIngredients("pasta,garlic,tomatoes,oil");
        receipe.setServings(4);

        //then
        mockMvc.perform(put("/api/v1/receipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(receipe)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Testing updating a receipe getting exception")
    public void givenReceipeDetailsToUpdateExpectedException() throws Exception {
        //given
        Receipe receipe = new Receipe();
        receipe.setId(5);
        receipe.setReceipeName("Creamy Garlic Pasta");
        receipe.setVegetarian(true);
        receipe.setInstructions("saute in the pan for 20 mins");
        receipe.setIngredients("pasta,garlic,tomatoes,oil");
        receipe.setServings(4);

        //then
        mockMvc.perform(put("/api/v1/receipes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(receipe)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Testing deleting a receipe")
    public void givenReceipeIdToDeleteReceipeExpectedCorrectResponse() throws Exception {
        mockMvc.perform(delete("/api/v1/receipes/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Testing deleting a receipe getting exception")
    public void givenReceipeIdToDeleteReceipeExpectedException() throws Exception {
        mockMvc.perform(delete("/api/v1/receipes/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
