package com.shop.controllers;

import com.shop.exceptions.OrderFulfillmentException;
import com.shop.model.order.OrderRouteDTO;
import com.shop.model.order.OrderStatus;
import com.shop.model.order.dtos.OrderCreateRespDTO;
import com.shop.model.order.dtos.OrderReqDTO;
import com.shop.model.order.dtos.OrderStatusDTO;
import com.shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderCreateRespDTO placeOrder(@RequestBody OrderReqDTO orderReq) {
        return orderService.createOrder(orderReq);
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusDTO getOrderStatus(@PathVariable int id) {
        return orderService.getStatus(id);
    }

    @GetMapping("/orders")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderRouteDTO getOrderRoute(@RequestParam("order_id") int orderId) {
        return orderService.getRoute(orderId);
    }

    @ExceptionHandler(OrderFulfillmentException.class)
    OrderCreateRespDTO handleOrderFulfillment(Exception e) {
        return OrderCreateRespDTO.builder()
            .message(e.getMessage())
            .status(OrderStatus.FAIL)
            .build();
    }
}
