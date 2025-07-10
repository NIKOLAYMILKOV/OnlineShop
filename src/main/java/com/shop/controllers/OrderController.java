package com.shop.controllers;

import com.shop.exceptions.OrderFulfillmentException;
import com.shop.model.order.OrderStatus;
import com.shop.model.order.dtos.OrderCreateResp;
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
    public OrderCreateResp placeOrder(@RequestBody OrderReqDTO orderReq) {
        return orderService.add(orderReq);
    }

    @GetMapping("/orders/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public OrderStatusDTO getOrderStatus(@PathVariable int id) {
        return orderService.getStatus(id);
    }

    @ExceptionHandler(OrderFulfillmentException.class)
    OrderCreateResp handleOrderFulfillment(Exception e) {
        return OrderCreateResp.builder()
            .message(e.getMessage())
            .status(OrderStatus.FAIL)
            .build();
    }
}
