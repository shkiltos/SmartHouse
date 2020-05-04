package com.anton.smarthouse.repository;

import com.anton.smarthouse.model.DeviceEntity;
import com.anton.smarthouse.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    UserEntity findByEmail(String email);
}
