package com.receipe.manager.service;

import com.receipe.manager.exception.RecordsNotFoundException;
import com.receipe.manager.model.SearchReceipeRequest;
import com.receipe.manager.repository.ReceipeRepository;
import com.receipe.manager.repository.model.Receipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReceipeServiceTest {
    @Mock
    private ReceipeRepository receipeRepository;

    @InjectMocks
    private ReceipeService receipeService;

    @Test
    @DisplayName("testing searching receipes with a specified request")
    void givenSearchReceipeRequestToSearchExpectedCorrectResponse() {
        //given
        SearchReceipeRequest request = new SearchReceipeRequest();
        request.setIsVegetarian(true);
        request.setServings(3);
        request.setIngredientsInclude("oil");

        Receipe receipe = new Receipe();
        receipe.setVegetarian(true);
        receipe.setReceipeName("Palak paneer");
        receipe.setIngredients("oil, paneer");

        //when
        when(receipeRepository.search(request.getIsVegetarian(), request.getServings(), request.getIngredientsInclude(), request.getIngredientsExclude(), request.getInstructions()))
                .thenReturn(List.of(receipe));

        List<Receipe> receipes = receipeService.search(request);

        //then
        assertEquals(1, receipes.size());
        assertIterableEquals(
                List.of("Palak paneer"),
                receipes.stream().map(Receipe::getReceipeName).collect(Collectors.toList())
        );

        verify(receipeRepository, times(1)).search(request.getIsVegetarian(), request.getServings(), request.getIngredientsInclude(), request.getIngredientsExclude(), request.getInstructions());
    }

    @Test
    @DisplayName("testing searching receipes with a specified request, got no receipes")
    void givenSearchReceipeRequestToSearchExpectedNoElementsFoundException() {
        //given
        SearchReceipeRequest request = new SearchReceipeRequest();
        request.setIsVegetarian(true);
        request.setServings(3);
        request.setIngredientsInclude("oil");

        //when
        when(receipeRepository.search(request.getIsVegetarian(), request.getServings(), request.getIngredientsInclude(), request.getIngredientsExclude(), request.getInstructions()))
                .thenReturn(List.of());

        //then
        assertThrows(RecordsNotFoundException.class, () -> receipeService.search(request));

        verify(receipeRepository, times(1)).search(request.getIsVegetarian(), request.getServings(), request.getIngredientsInclude(), request.getIngredientsExclude(), request.getInstructions());
    }
}
