package com.shop.services;

import com.shop.exceptions.BadRequestException;
import com.shop.exceptions.UnauthorisedException;
import com.shop.model.users.UserEntity;
import com.shop.model.users.dtos.UserLoginDTO;
import com.shop.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    public static final String LOGGED = "logged";
    public static final String USER_ID = "user_id";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public UserEntity authenticate(UserLoginDTO loginUserDTO, HttpSession session) {
        if (isLogged(session)) {
            throw new BadRequestException("You are already logged in");
        }
        Optional<UserEntity> optionalUser = userRepository.findByUsername(loginUserDTO.getUsername());
        if (optionalUser.isEmpty()) {
            throw new BadRequestException("Wrong credentials");
        }
        UserEntity user = optionalUser.get();
        if (!encoder.matches(loginUserDTO.getPassword(), user.getPassword())) {
            throw new UnauthorisedException("Wrong credentials");
        }

        session.setAttribute(LOGGED, true);
        session.setAttribute(USER_ID, user.getId());
        return user;
    }

    public void validateLogged(HttpSession session) {
        if (!isLogged(session)) {
            throw new UnauthorisedException("Log in first");
        }
    }

    public int getUserIdFromSession(HttpSession session) {
        validateLogged(session);
        return  (int) session.getAttribute(USER_ID);
    }

    private boolean isLogged(HttpSession session) {
        return session.getAttribute(LOGGED) != null && (boolean)session.getAttribute(LOGGED);
    }
}