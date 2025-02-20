package com.dev.yank.ecommerce.controller;

import com.dev.yank.ecommerce.dto.OrderItemDTO;
import com.dev.yank.ecommerce.services.OrderItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<Page<OrderItemDTO>> orderItems(Pageable pageable) {
        Page<OrderItemDTO> orderItems = orderItemService.getOrderItems(pageable);
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id) {
        OrderItemDTO order = orderItemService.getOrderItemById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> createOrderItem(@RequestBody OrderItemDTO newOrderItem) {
        OrderItemDTO order = orderItemService.createOrderItem(newOrderItem);
        return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO updateOrderItem) {
        OrderItemDTO updatedOrder = orderItemService.updateOrderItem(id, updateOrderItem);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.noContent().build();
    }
}
