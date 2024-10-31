package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.entities.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findByAccessToken(String accessToken);
}
