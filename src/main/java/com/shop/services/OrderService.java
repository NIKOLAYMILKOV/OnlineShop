package com.shop.services;

import com.shop.exceptions.NotFoundException;
import com.shop.exceptions.OrderFulfillmentException;
import com.shop.model.OrderLocationEntity;
import com.shop.model.OrderLocationId;
import com.shop.model.location.LocationDTO;
import com.shop.model.location.LocationEntity;
import com.shop.model.order.ItemEntity;
import com.shop.model.order.OrderEntity;
import com.shop.model.order.OrderRouteDTO;
import com.shop.model.order.OrderStatus;
import com.shop.model.order.dtos.OrderCreateRespDTO;
import com.shop.model.order.dtos.OrderReqDTO;
import com.shop.model.order.dtos.OrderStatusDTO;
import com.shop.repositories.InventoryRepository;
import com.shop.repositories.LocationRepository;
import com.shop.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RouteCalculator routeCalculator;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public OrderCreateRespDTO createOrder(OrderReqDTO orderReqDTO) {
        OrderEntity order = new OrderEntity();
        List<LocationEntity> locations = new ArrayList<>();
        for (ItemEntity item : orderReqDTO.getItems()) {
            int updatedRows = inventoryRepository.reserveStock(
                item.getProductName(),
                item.getQuantity()
            );

            if (updatedRows == 0) {
                throw new OrderFulfillmentException("Not enough stock for: " + item.getProductName());
            }

            locations.add(inventoryRepository.findLocationByName(item.getProductName()));
            System.out.println(locations.getLast());

            item.setOrder(order);
            order.getItems().add(item);
        }

        order.setLocations(getRoute(order, locations));

        order.setStatus(OrderStatus.SUCCESS);
        orderRepository.save(order);
        return OrderCreateRespDTO.builder()
            .message("Your order is ready! Please collect it.")
            .status(OrderStatus.SUCCESS)
            .build();
    }

    private List<OrderLocationEntity> getRoute(OrderEntity order, List<LocationEntity> locations) {
        List<LocationEntity> locationEntities = routeCalculator.findBestRoute(locations);
        for (LocationEntity location :locationEntities) {
            Optional<LocationEntity> optionalLocationEntity =
                locationRepository.findByXAndY(location.getX(), location.getY());
            if (optionalLocationEntity.isPresent()) {
                location.setId(optionalLocationEntity.get().getId());
            } else {
                location.setId(locationRepository.save(location).getId());
            }
        }
        locationEntities = locationRepository.saveAll(locationEntities.stream().distinct().toList());
        List<OrderLocationEntity> orderLocationEntities = new ArrayList<>();

        for (int i = 0; i < locationEntities.size(); i++) {
            OrderLocationEntity orderLocationEntity = new OrderLocationEntity();
            orderLocationEntity.setId(
                new OrderLocationId(order.getId(), locationEntities.get(i).getId()));

            orderLocationEntity.setLocation(locationEntities.get(i));
            orderLocationEntity.setOrder(order);
            orderLocationEntity.setStepSequenceNumber(i + 1);

            orderLocationEntities.add(orderLocationEntity);
        }
        return orderLocationEntities;
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

    public OrderRouteDTO getRoute(int id) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findById(id);
        if (optionalOrderEntity.isEmpty()) {
            throw new NotFoundException("Order not found");
        }
        OrderEntity order = optionalOrderEntity.get();
        List<LocationDTO> locations = order.getLocations().stream()
            .sorted(Comparator.comparingInt(OrderLocationEntity::getStepSequenceNumber))
            .map(OrderLocationEntity::getLocation)
                .map(locationEntity -> mapper.map(locationEntity, LocationDTO.class))
            .toList();

        return OrderRouteDTO.builder()
            .orderId(order.getId())
            .status(order.getStatus())
            .visitedLocations(locations)
            .build();
    }
}
