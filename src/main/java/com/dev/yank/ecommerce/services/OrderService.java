package com.dev.yank.ecommerce.services;

import com.dev.yank.ecommerce.dto.OrderDTO;
import com.dev.yank.ecommerce.exception.OrderNotFoundException;
import com.dev.yank.ecommerce.mapper.OrderMapper;
import com.dev.yank.ecommerce.model.Order;
import com.dev.yank.ecommerce.model.enums.OrderStatus;
import com.dev.yank.ecommerce.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    // Injection
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaProducerService kafkaProducerService;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, KafkaProducerService kafkaProducerService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.kafkaProducerService = kafkaProducerService;
    }

    // Methods
    public Page<OrderDTO> getOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::toDTO);
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return orderMapper.toDTO(order);
    }

    // Create a new Order, save it and send to Kafka.
    @Transactional
    public OrderDTO placeOrder(OrderDTO newOrderDTO) {
        Order order = orderMapper.toEntity(newOrderDTO);
        Order savedOrder = orderRepository.save(order);

        // Send event to Kafka
        kafkaProducerService.sendOrderEvent(savedOrder.getId().toString());

        return orderMapper.toDTO(savedOrder);
    }

    @Transactional
    public OrderDTO cancelOrder(Long id) {
        Order existingOrder = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with ID:" + id));

        existingOrder.setOrderStatus(OrderStatus.CANCELLED);
        Order cancelledOrder = orderRepository.save(existingOrder);

        kafkaProducerService.sendOrderEvent("Order " + id + " was CANCELLED.");

        return orderMapper.toDTO(cancelledOrder);
    }
}
