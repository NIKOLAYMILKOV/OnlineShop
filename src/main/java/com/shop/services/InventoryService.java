package com.shop.services;

import com.shop.exceptions.BadRequestException;
import com.shop.exceptions.NotFoundException;
import com.shop.model.products.ProductEntity;
import com.shop.model.products.dtos.ProductCreateReqDTO;
import com.shop.model.products.dtos.ProductRespDTO;
import com.shop.model.products.dtos.ProductUpdateReqDTO;
import com.shop.repositories.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    public ProductRespDTO addProduct(ProductCreateReqDTO productReq) {
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

    public ProductRespDTO updateProduct(ProductUpdateReqDTO productUpdateReq, Integer id) {
        if (id == null) {
            throw new BadRequestException("Id is mandatory");
        }

        ProductEntity productEntity = inventoryRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No product with that id"));
        if (productUpdateReq.getName() != null) {
            productEntity.setName(productUpdateReq.getName());
        }
        if (productUpdateReq.getQuantity() != null) {
            productEntity.setQuantity(productUpdateReq.getQuantity());
        }
        if (productUpdateReq.getPrice() != null) {
            productEntity.setPrice(productUpdateReq.getPrice());
        }
        if (productUpdateReq.getLocation() != null) {
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

    private void validateProduct(ProductEntity product) {
        if (product.getName() == null) {
            throw new BadRequestException("Name is mandatory");
        }
        if (product.getPrice() == null) {
            throw new BadRequestException("Price is mandatory");
        }
        if (product.getQuantity() == null) {
            throw new BadRequestException("Quantity is mandatory");
        }
        if (product.getLocation() == null) {
            throw new BadRequestException("Location is mandatory");
        }
        if (product.getLocation().getX() == null) {
            throw new BadRequestException("X coordinate is mandatory");
        }
        if (product.getLocation().getY() == null) {
            throw new BadRequestException("Y coordinate is mandatory");
        }
    }
}