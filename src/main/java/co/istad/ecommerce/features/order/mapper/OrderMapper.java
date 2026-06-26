package co.istad.ecommerce.features.order.mapper;

import co.istad.ecommerce.features.order.Order;
import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(CreateOrderRequest createOrderRequest);
    OrderResponse toResponse(Order order);
}
