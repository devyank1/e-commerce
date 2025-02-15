package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.OrderItemDTO;
import com.dev.yank.ecommerce.exception.OrderItemNotFoundException;
import com.dev.yank.ecommerce.mapper.OrderItemMapper;
import com.dev.yank.ecommerce.model.OrderItem;
import com.dev.yank.ecommerce.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    // Injection
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    // Methods
    public Page<OrderItemDTO> getOrderItems(Pageable pageable) {
        Page<OrderItem> orderItems = orderItemRepository.findAll(pageable);
        return orderItems.map(orderItemMapper::toDTO);
    }

    public OrderItemDTO getOrderItemById(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("Order Item not found with ID: " + id));
        return orderItemMapper.toDTO(orderItem);
    }

    @Transactional
    public OrderItemDTO createOrderItem(OrderItemDTO newOrderItem) {
        OrderItem orderItem = orderItemMapper.toEntity(newOrderItem);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return orderItemMapper.toDTO(savedOrderItem);
    }

    @Transactional
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO updateOrderItem) {
        OrderItem existingOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("Order Item not found with ID: " + id));

        existingOrderItem.setPrice(updateOrderItem.price());
        existingOrderItem.setQuantity(updateOrderItem.quantity());

        OrderItem updatedOrderItem = orderItemRepository.save(existingOrderItem);
        return orderItemMapper.toDTO(existingOrderItem);
    }

    @Transactional
    public void deleteOrderItem(Long id) {
        if (!orderItemRepository.existsById(id)) {
            throw new OrderItemNotFoundException("Order Item not found with ID: " + id);
        }
    }
}
