package co.istad.ecommerce.model.repository;

import co.istad.ecommerce.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    List<Category> getCategoriesByParentCategoryId(Integer id);
}
