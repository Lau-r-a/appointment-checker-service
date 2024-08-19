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

    public User createOrUpdateUser(String userId, String accessToken) {
        User user = new User(userId, accessToken);
        return userRepository.save(user);
    }

    public User getUserById(String userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        return userOptional.orElse(null);
    }

    public String getUserIdByToken(String accessToken) {
        User user = userRepository.findByAccessToken(accessToken);
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
