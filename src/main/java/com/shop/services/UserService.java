package com.shop.services;

import com.shop.exceptions.BadRequestException;
import com.shop.model.users.UserEntity;
import com.shop.model.users.dtos.UserLoginDTO;
import com.shop.model.users.dtos.UserRegisterDTO;
import com.shop.model.users.dtos.UserRespDTO;
import com.shop.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationService authenticationService;

    public UserRespDTO registerUser(UserRegisterDTO registerUserDTO) {
        validateRegister(registerUserDTO);
        if (userRepository.existsByUsername(registerUserDTO.getUsername())) {
            throw new BadRequestException("User with that username already exists");
        }
        UserEntity user = modelMapper.map(registerUserDTO, UserEntity.class);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        UserEntity userWithId = userRepository.save(user);
        return modelMapper.map(userWithId, UserRespDTO.class);
    }

    public UserRespDTO loginUser(UserLoginDTO loginUserDTO, HttpSession session) {
        validateUsernameAndPassword(loginUserDTO.getUsername(), loginUserDTO.getPassword());
        UserEntity u = authenticationService.authenticate(loginUserDTO, session);
        return modelMapper.map(u, UserRespDTO.class);
    }

    public void logoutUser(HttpSession session) {
        session.invalidate();
    }

    public void deleteUser(HttpSession session) {
        int id = authenticationService.getUserIdFromSession(session);
        userRepository.deleteById(id);
    }

    private void validateUsernameAndPassword(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new BadRequestException("Username is mandatory");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException("Password is mandatory");
        }
    }

    private void validateRegister(UserRegisterDTO dto) {
        validateUsernameAndPassword(dto.getUsername(), dto.getPassword());

        if (dto.getConfirmPassword() == null || dto.getConfirmPassword().isBlank()) {
            throw new BadRequestException("Confirm password is mandatory");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BadRequestException("Password and conformation password do not match");
        }
        if (dto.getEmail() == null || dto.getEmail().isBlank()) {
            throw new BadRequestException("Email is mandatory");
        }
    }
}
