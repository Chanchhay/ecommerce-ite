package co.istad.ecommerce.config.repository;

import co.istad.ecommerce.features.category.Category;
import org.springframework.data.jpa.domain.Specification;

public class CategorySpecification {
    public static Specification<Category> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Category> descriptionContains(String description) {
        return (root, query, cb) ->
        {
            if (description == null || description.isEmpty()) {
                return null;
            }
            return cb.like(cb.lower(root.get("description")), "%" + description.toLowerCase() + "%");
        };
    }
}