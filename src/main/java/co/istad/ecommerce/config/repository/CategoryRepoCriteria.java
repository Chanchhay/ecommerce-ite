package co.istad.ecommerce.config.repository;

import co.istad.ecommerce.features.category.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CategoryRepoCriteria {
    private final EntityManager em;

    public List<Category> findAllCategoriesSimpleQuery(String name, String description) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Category> cq = cb.createQuery(Category.class);
        Root<Category> root = cq.from(Category.class);

        List<Predicate> predicates = new ArrayList<>();

        // Safely check EACH parameter
        if (name != null && !name.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (description != null && !description.isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%"));
        }

        // Only apply the WHERE clause if we actually have filters
        if (!predicates.isEmpty()) {
            // Convert list to array and apply OR
            cq.where(cb.or(predicates.toArray(new Predicate[0])));
        }

        // Execute and return
        return em.createQuery(cq).getResultList();
    }
}