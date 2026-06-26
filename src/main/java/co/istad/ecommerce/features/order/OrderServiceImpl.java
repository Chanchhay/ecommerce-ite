package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import co.istad.ecommerce.features.order.mapper.OrderMapper;
import co.istad.ecommerce.features.product.Product;
import co.istad.ecommerce.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createNew(CreateOrderRequest orderRequest) {

        List<OrderLine> orderLines = new ArrayList<>();

        final Order order = orderMapper.toOrder(orderRequest);
        order.setIsDelete(false);
        order.setStatus(false);
        order.setCustomerId("OH_YEAH");

        boolean isValidOrder = orderRequest.orderLines().stream().allMatch(orderLineDto -> {
                    Optional<Product> product = productRepository.findByCode((orderLineDto.code()));
                    if (product.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(product.get());
                        orderLine.setQty(orderLine.getQty());
                        orderLine.setUnitPrice(orderLine.getUnitPrice());
                        orderLine.setOrder(order);
                        orderLines.add(orderLine);
                        return true;
                    }
                    return false;
                }
        );

        if (!isValidOrder) throw new ResponseStatusException(HttpStatus.NOT_FOUND ,"Product is not a valid order");

        order.setOrderLines(orderLines);

        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public Page<OrderResponse> getAllOrders(Pageable pageable){
        return orderRepository.findAll(pageable).map(orderMapper::toResponse);
    }

    @Override
    public OrderResponse findById(UUID uuid){
        Order order = orderRepository.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with the id: " + uuid + " is not existed"));
        return orderMapper.toResponse(order);
    }

    @Override
    public void softDelete(UUID uuid){
        Order order = orderRepository.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with the id: " + uuid + " is not existed"));
        order.setIsDelete(true);
        orderRepository.save(order);
    }

    @Override
    public void deleteById(UUID uuid){
        orderRepository.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with the id: " + uuid + " is not existed"));
        orderRepository.deleteById(uuid);
    }

    @Override
    public void updatePaymentStatus(UUID uuid, Boolean status){
        Order order = orderRepository.findById(uuid).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with the id: " + uuid + " is not existed"));
        order.setStatus(status);
    }
}