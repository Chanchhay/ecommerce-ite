package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface OrderService {
    OrderResponse createNew(CreateOrderRequest orderRequest);
    Page<OrderResponse> getAllOrders(Pageable pageable);
    OrderResponse findById(UUID uuid);
    void softDelete(UUID id);
    void deleteById(UUID id);
    void updatePaymentStatus(UUID uuid, Boolean status);
}
