package com.appointmentchecker.service.facade;

import com.appointmentchecker.service.entities.User;
import com.appointmentchecker.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFacade {

    @Autowired
    UserRepository userRepository;

    public User createUser(String userId) {
        User user = new User(userId, null);
        return userRepository.save(user);
    }

    public User getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if(userOptional.isEmpty()) {
            throw new IllegalArgumentException("User invalid");
        }

        return userOptional.get();
    }
}
