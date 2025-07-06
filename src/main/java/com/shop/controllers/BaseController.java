package com.shop.controllers;

import com.shop.exceptions.BadRequestException;
import com.shop.exceptions.NotFoundException;
import com.shop.exceptions.UnauthorisedException;
import com.shop.model.RespMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @ExceptionHandler(BadRequestException.class)
    public RespMessageDTO handleBadRequest(Exception e) {
        RespMessageDTO errorDTO = new RespMessageDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return errorDTO;
    }

    @ExceptionHandler(UnauthorisedException.class)
    public RespMessageDTO handleUnauthorised(Exception e) {
        RespMessageDTO errorDTO = new RespMessageDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        return errorDTO;
    }

    @ExceptionHandler(NotFoundException.class)
    public RespMessageDTO handleNotFound(Exception e) {
        RespMessageDTO errorDTO = new RespMessageDTO();
        errorDTO.setMessage(e.getMessage());
        errorDTO.setStatusCode(HttpStatus.NOT_FOUND.value());
        return errorDTO;
    }
}
