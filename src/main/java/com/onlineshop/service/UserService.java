package com.onlineshop.service;

import com.onlineshop.domain.User;
import com.onlineshop.exceptions.UserNotFoundException;
import com.onlineshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    public void createUser(User user){
        userRepository.save(user);
    }
    public void deleteUser(int userId) throws UserNotFoundException{
        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User with ID" + userId + " not found")));

    }

}
