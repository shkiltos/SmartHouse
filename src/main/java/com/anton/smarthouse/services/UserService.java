package com.anton.smarthouse.services;

import com.anton.smarthouse.model.UserEntity;
import com.anton.smarthouse.model.UserSettings;
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

    public UserSettings updateSettings(UserSettings settings) {
        UserEntity user = getUser();
        user.setMaxEnergyConsumption(settings.getMaxEnergyConsumption());
        user.setName(settings.getUserName());
        user.setPicture(settings.getPicture());
        user.setCams(settings.getCams());
        userRepository.save(user);
        return settings;
    }
}
