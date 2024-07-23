package com.appointmentchecker.service.repositories;

import com.appointmentchecker.service.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
