package com.receipe.manager.repository;

import com.receipe.manager.repository.model.Receipe;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ReceipeRepository
 * <br>
 * <code>com.receipe.manager.repository.ReceipeRepository</code>
 * <br>
 *
 * @author hemanshu.banga
 */
@Repository
public interface ReceipeRepository extends CrudRepository<Receipe, Integer> {

    /**
     * Method to retrieve all the receipes from the database matching the search request by running a SQL query
     *
     * @param isVegetarian       whether the receipe is vegetarian or not
     * @param servings           number of servings for the receipe
     * @param ingredientsInclude what ingredients are included in the receipe
     * @param ingredientsExclude what ingredients are excluded in the receipe
     * @param instructions       what content is mentioned in the instructions of the receipe
     * @return {@link List<Receipe>} List of receipes matching the search request
     */
    @Query(value = "SELECT DISTINCT r.id, r.receipename, r.servings, r.isvegetarian, " +
            "r.ingredients, r.instructions " +
            "FROM RECEIPE r " +
            "WHERE (:isVegetarian IS NULL OR r.isVegetarian = :isVegetarian) " +
            "AND (:servings IS NULL OR r.servings >= :servings) " +
            "AND (:ingredientsInclude IS NULL OR :ingredientsInclude = '' OR " +
            "r.ingredients REGEXP REPLACE(:ingredientsInclude, ',', '|')) " +
            "AND (:ingredientsExclude IS NULL OR :ingredientsExclude = '' OR NOT r.ingredients REGEXP REPLACE(:ingredientsExclude, ',', '|')) " +
            "AND (:instructions IS NULL OR :instructions = '' OR r.instructions ILIKE %:instructions%) " +
            "ORDER BY r.id", nativeQuery = true)
    List<Receipe> search(@Param("isVegetarian") Boolean isVegetarian,
                         @Param("servings") Integer servings,
                         @Param("ingredientsInclude") String ingredientsInclude,
                         @Param("ingredientsExclude") String ingredientsExclude,
                         @Param("instructions") String instructions);
}
