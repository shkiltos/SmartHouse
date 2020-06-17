package com.anton.smarthouse.repository;

import com.anton.smarthouse.model.DashboardScheme;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DashboardSchemeRepository extends MongoRepository<DashboardScheme, String> {
    List<DashboardScheme> findAllByUserId(String userId);
}
