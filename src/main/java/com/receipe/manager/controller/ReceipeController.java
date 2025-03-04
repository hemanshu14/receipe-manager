package com.receipe.manager.controller;

import com.receipe.manager.model.SearchReceipeRequest;
import com.receipe.manager.repository.ReceipeRepository;
import com.receipe.manager.repository.model.Receipe;
import com.receipe.manager.service.ReceipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * ReceipeController
 * <br>
 * <code>com.receipe.manager.controller.ReceipeController</code>
 * <br>
 *
 * @author hemanshu.banga
 */
@RequestMapping("/api/v1/receipes")
@RestController
@RequiredArgsConstructor
public class ReceipeController {
    private final ReceipeRepository receipeRepository;
    private final ReceipeService receipeService;

    /**
     * Method to retrieve all the receipes from the database
     *
     * @return {@link List<Receipe>} list of the receipes
     */
    @GetMapping()
    public ResponseEntity<List<Receipe>> getAllReceipes() {
        List<Receipe> receipes = new ArrayList<>();
        receipeRepository.findAll().forEach(receipes::add);

        return ResponseEntity.ok(receipes);
    }

    /**
     * Method to retrieve all the receipes from the database matching the search request
     *
     * @param request {@link SearchReceipeRequest) filter request based on which the search is made into the database
     * @return {@link List<Receipe>} List of receipes matching the search request
     */
    @PostMapping("/search")
    public ResponseEntity<List<Receipe>> searchAllReceipes(@RequestBody SearchReceipeRequest request) {
        return ResponseEntity.ok(receipeService.search(request));
    }

    /**
     * Method to add a receipe into the database
     *
     * @param receipe {@link Receipe) Receipe details which needs to be added into the database
     * @return {@link ResponseEntity<Receipe>} Receipe added into the database
     */
    @PostMapping
    public ResponseEntity<Receipe> addReceipe(@RequestBody Receipe receipe) {
        return ResponseEntity.ok(receipeRepository.save(receipe));
    }

    /**
     * Method to update a receipe into the database
     *
     * @param receipe {@link Receipe) Receipe details which needs to be updated into the database
     * @return {@link ResponseEntity<Receipe>} Receipe updated into the database
     */
    @PutMapping()
    public ResponseEntity<Receipe> updateReceipe(@RequestBody Receipe receipe) {
        if (!receipeRepository.existsById(receipe.getId())) {
            throw new EntityNotFoundException("Receipe not found with id " + receipe.getId());
        }
        return ResponseEntity.ok(receipeRepository.save(receipe));
    }

    /**
     * Method to delete a receipe from the database
     *
     * @param receipeId Receipe id of the receipe which needs to be deleted from the database
     */
    @DeleteMapping("/{receipeId}")
    public ResponseEntity<Void> deleteReceipe(@PathVariable Integer receipeId) {
        if (!receipeRepository.existsById(receipeId)) {
            throw new EntityNotFoundException("Receipe not found with id " + receipeId);
        }
        receipeRepository.deleteById(receipeId);
        return ResponseEntity.noContent().build();
    }
}
