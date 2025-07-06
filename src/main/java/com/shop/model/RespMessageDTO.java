package com.shop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RespMessageDTO {
    private Integer statusCode;
    private String message;
}
