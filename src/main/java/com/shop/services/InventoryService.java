package com.shop.services;

import com.shop.exceptions.BadRequestException;
import com.shop.exceptions.NotFoundException;
import com.shop.model.RespMessageDTO;
import com.shop.model.products.ProductEntity;
import com.shop.model.products.dtos.ProductReqDTO;
import com.shop.model.products.dtos.ProductRespDTO;
import com.shop.repositories.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductRespDTO addProduct(ProductReqDTO productReq) {
        ProductEntity productEntity = modelMapper.map(productReq, ProductEntity.class);
        validateProduct(productEntity);
        if (inventoryRepository.existsByName(productReq.getName())) {
            throw new BadRequestException("Product with that name already exists");
        }
        if (inventoryRepository.existsByLocation(productReq.getLocation())) {
            throw new BadRequestException("Product on that location already exists");
        }

        return modelMapper.map(inventoryRepository.save(productEntity), ProductRespDTO.class);
    }

    public ProductRespDTO updateProduct(ProductReqDTO productUpdateReq, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is mandatory");
        }
        ProductEntity productEntity = inventoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No product with that id"));
        if (productUpdateReq.getName() != null) {
            productEntity.setName(productUpdateReq.getName());
        }
        if (productUpdateReq.getQuantity() != null) {
            if (productUpdateReq.getQuantity() < 0) {
                throw new BadRequestException("Quantity cannot be less then 0");
            }
            if (productUpdateReq.getQuantity() == 0) {
                delete(id);
            }
            productEntity.setQuantity(productUpdateReq.getQuantity());
        }
        if (productUpdateReq.getPrice() != null) {
            if (productUpdateReq.getPrice() < 0) {
                throw new BadRequestException("Price cannot be less then 0");
            }
            productEntity.setPrice(productUpdateReq.getPrice());
        }
        if (productUpdateReq.getLocation() != null ) {
            if (inventoryRepository.existsByLocation(productUpdateReq.getLocation())) {
                throw new BadRequestException("This location is already occupied");
            }
            productEntity.setLocation(productUpdateReq.getLocation());
        }
        return modelMapper.map(inventoryRepository.save(productEntity), ProductRespDTO.class);
    }

    public List<ProductRespDTO> listProducts() {
        List<ProductEntity> products = inventoryRepository.findAll();

        return products.stream()
            .map(productEntity -> modelMapper.map(productEntity, ProductRespDTO.class))
            .toList();
    }

    public RespMessageDTO delete(int id) {
        if (!inventoryRepository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }
        inventoryRepository.deleteById(id);
        return RespMessageDTO.builder()
            .message("Product deleted successfully")
            .statusCode(HttpStatus.OK.value())
            .build();
    }

    private void validateProduct(ProductEntity product) {
        if (product.getName() == null || product.getName().isBlank()) {
            throw new BadRequestException("Name is mandatory");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new BadRequestException("Price is mandatory and cannot be less than 0");
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            throw new BadRequestException("Quantity is mandatory and cannot be less than or equal to 0");
        }
        if (product.getLocation() == null) {
            throw new BadRequestException("Location is mandatory");
        }
        if (product.getLocation().getX() == null || product.getLocation().getX() <= 0) {
            throw new BadRequestException("X coordinate is mandatory and cannot be less than or equal to 0");
        }
        if (product.getLocation().getY() == null || product.getLocation().getY() <= 0) {
            throw new BadRequestException("Y coordinate is mandatory and cannot be less than or equal to 0");
        }
    }
}