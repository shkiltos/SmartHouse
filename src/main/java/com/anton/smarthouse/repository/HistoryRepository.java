package com.anton.smarthouse.repository;

import com.anton.smarthouse.model.HistoryItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface HistoryRepository extends MongoRepository<HistoryItemEntity, String> {
//    @Query("{ 'date' : ?0}")
    List<HistoryItemEntity> findTop5ByDeviceIdOrderByDateDesc(String deviceId);
}
