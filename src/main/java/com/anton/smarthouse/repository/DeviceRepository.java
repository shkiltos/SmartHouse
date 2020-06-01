package com.anton.smarthouse.repository;

import com.anton.smarthouse.devices.Device;
import com.anton.smarthouse.model.DeviceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, String> {

    List<DeviceEntity> findDeviceEntitiesByUserId(String id);
    List<DeviceEntity> findDeviceEntitiesByUserIdAndType(String id, String type);
}