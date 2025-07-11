package com.shop.controllers;

import com.shop.model.RespMessageDTO;
import com.shop.model.products.dtos.ProductReqDTO;
import com.shop.model.products.dtos.ProductRespDTO;
import com.shop.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryController extends BaseController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/products")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ProductRespDTO addProduct(@RequestBody ProductReqDTO newProduct) {
        return inventoryService.addProduct(newProduct);
    }

    @GetMapping("/products")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<ProductRespDTO> listProducts() {
        return inventoryService.listProducts();
    }

    @PutMapping("/products/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ProductRespDTO updateProduct(@RequestBody ProductReqDTO newProduct, @PathVariable Integer id) {
        return inventoryService.updateProduct(newProduct, id);
    }

    @DeleteMapping("/products/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RespMessageDTO deleteProduct(@PathVariable Integer id) {
        return inventoryService.delete(id);
    }
}
