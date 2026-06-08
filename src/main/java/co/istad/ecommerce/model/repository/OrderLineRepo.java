package co.istad.ecommerce.model.repository;

import co.istad.ecommerce.model.domain.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepo extends JpaRepository<OrderLine, Integer>
{
    
}
