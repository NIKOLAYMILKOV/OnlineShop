package com.shop.services;

import com.shop.exceptions.NotFoundException;
import com.shop.exceptions.OrderFulfillmentException;
import com.shop.model.order.ItemEntity;
import com.shop.model.order.OrderEntity;
import com.shop.model.order.OrderStatus;
import com.shop.model.order.dtos.OrderCreateResp;
import com.shop.model.order.dtos.OrderReqDTO;
import com.shop.model.order.dtos.OrderStatusDTO;
import com.shop.repositories.InventoryRepository;
import com.shop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    @Transactional
    public OrderCreateResp add(OrderReqDTO orderReqDTO) {
        OrderEntity entity = new OrderEntity();
        for (ItemEntity item : orderReqDTO.getItems()) {
            int updated = inventoryRepository.reserveStock(
                item.getProductName(),
                item.getQuantity()
            );

            if (updated == 0) {
                throw new OrderFulfillmentException("Not enough stock for: " + item.getProductName());
            }

            item.setOrder(entity);
            entity.getItems().add(item);
        }
        entity.setStatus(OrderStatus.SUCCESS);
        orderRepository.save(entity);
        return OrderCreateResp.builder()
            .message("Your order is ready! Please collect it.")
            .status(OrderStatus.SUCCESS)
            .build();
    }

    public OrderStatusDTO getStatus(int id) {
        Optional<OrderEntity> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new NotFoundException("No order with that id");
        }
        return OrderStatusDTO.builder()
            .id(id)
            .status(order.get().getStatus().toString())
            .build();
    }

}
