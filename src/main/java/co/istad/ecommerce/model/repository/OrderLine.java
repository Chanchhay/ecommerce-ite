package co.istad.ecommerce.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLine extends JpaRepository<OrderLine, Integer> {
}
