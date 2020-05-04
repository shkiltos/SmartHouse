package com.anton.smarthouse.services;

import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public UserEntity getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser"
                ? (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()
                : null;
    }
}
