package co.istad.ecommerce.features.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {
    Boolean existsByName(String name);

    Optional<Product> findByCode(String code);
}