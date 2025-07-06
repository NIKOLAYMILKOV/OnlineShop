package com.shop.controllers;

import com.shop.model.users.dtos.UserLoginDTO;
import com.shop.model.users.dtos.UserRegisterDTO;
import com.shop.model.users.dtos.UserRespDTO;
import com.shop.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/register")
    @ResponseBody
    public ResponseEntity<UserRespDTO> register(@RequestBody UserRegisterDTO registerUserDTO) {
        UserRespDTO responseUserDTO = userService.registerUser(registerUserDTO);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PostMapping("/users/login")
    @ResponseBody
    public ResponseEntity<UserRespDTO> login(@RequestBody UserLoginDTO loginUserDTO, HttpSession session) {
        UserRespDTO responseUserDTO = userService.loginUser(loginUserDTO, session);
        return ResponseEntity.ok().body(responseUserDTO);
    }

    @PostMapping("/users/logout")
    public void logout(HttpSession session) {
        userService.logoutUser(session);
    }

    @DeleteMapping("users/remove")
    public void delete(HttpSession session) {
        userService.deleteUser(session);
    }
}
