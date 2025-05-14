
package com.example.orderservice.service;

import com.example.orderservice.client.UserClient;
import com.example.orderservice.dto.OrderCreatedEvent;
import com.example.orderservice.kafka.OrderProducer;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository, UserClient userClient, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.orderProducer = orderProducer;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order saveOrder(Order order) {
        userClient.getUserById(order.getUserId()); // verifica che l’utente esista
        Order savedOrder = orderRepository.save(order);

        // Invio evento Kafka
        OrderCreatedEvent event = new OrderCreatedEvent();
        event.setId(savedOrder.getId());
        event.setProduct(savedOrder.getProduct());
        event.setQuantity(savedOrder.getQuantity());
        event.setUserId(savedOrder.getUserId());

        orderProducer.send(event);

        return savedOrder;
    }
}
