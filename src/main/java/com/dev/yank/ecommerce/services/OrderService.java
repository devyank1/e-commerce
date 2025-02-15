package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.OrderDTO;
import com.dev.yank.ecommerce.exception.OrderNotFoundException;
import com.dev.yank.ecommerce.mapper.OrderMapper;
import com.dev.yank.ecommerce.model.Order;
import com.dev.yank.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    // Injection
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    // Methods
    public List<OrderDTO> getOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDTO).toList();
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(OrderDTO newOrder) {
        Order order = orderMapper.toEntity(newOrder);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDTO(savedOrder);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO updateOrder) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        existingOrder.setOrderDate(updateOrder.orderDate());
        existingOrder.setOrderStatus(updateOrder.orderStatus());
        existingOrder.setTotalAmount(updateOrder.totalAmount());

        Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.toDTO(updatedOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
    }
}
