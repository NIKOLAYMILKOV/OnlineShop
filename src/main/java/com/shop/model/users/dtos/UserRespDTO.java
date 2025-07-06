package com.shop.model.users.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRespDTO {
    private Integer id;
    private String username;
    private String email;
    private String phoneNumber;
    private String address;
}
