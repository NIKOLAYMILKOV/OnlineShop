package com.shop.model.users.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class UserRegisterDTO {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;
    private String address;
}
