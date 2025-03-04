package com.receipe.manager.service;

import com.receipe.manager.exception.RecordsNotFoundException;
import com.receipe.manager.model.SearchReceipeRequest;
import com.receipe.manager.repository.ReceipeRepository;
import com.receipe.manager.repository.model.Receipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ReceipeService
 * <br>
 * <code>com.receipe.manager.service.ReceipeService</code>
 * <br>
 *
 * @author hemanshu.banga
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ReceipeService {

    private final ReceipeRepository receipeRepository;

    /**
     * Method to retrieve all the receipes from the database matching the search request
     *
     * @param request {@link SearchReceipeRequest) filter request based on which the search is made into the database
     * @return {@link List<Receipe>} List of receipes matching the search request
     */
    @Transactional(readOnly = true)
    public List<Receipe> search(SearchReceipeRequest request) {
        List<Receipe> receipes = receipeRepository.search(
                        request.getIsVegetarian(),
                        request.getServings(),
                        request.getIngredientsInclude(),
                        request.getIngredientsExclude(),
                        request.getInstructions()).stream()
                .toList();
        if (receipes.isEmpty()) {
            log.info("No receipes found in the database matching the search request");
            throw new RecordsNotFoundException("No receipes found in DB with the specified search criteria");
        }
        return receipes;
    }
}
