package co.istad.ecommerce.features.order;


import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse createNew(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return orderService.createNew(orderRequest);
    }

    @GetMapping
    public Page<OrderResponse> getAllOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "true") boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return orderService.getAllOrders(pageRequest);
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id){
        return orderService.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}/soft-delete")
    public void softDelete(@PathVariable UUID id){
        orderService.softDelete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        orderService.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/status")
    public void updatePaymentStatus(@PathVariable UUID id,@RequestBody Boolean status){
        orderService.updatePaymentStatus(id, status);
    }
}