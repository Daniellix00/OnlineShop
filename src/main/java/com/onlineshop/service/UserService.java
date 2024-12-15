package com.onlineshop.service;

import com.onlineshop.domain.User;
import com.onlineshop.exceptions.UserNotFoundException;
import com.onlineshop.exceptions.UsernameNotFoundException;
import com.onlineshop.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(int userId) throws UserNotFoundException {
        userRepository.delete(userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID" + userId + " not found")));

    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User witk ID" + userId + "not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
    }
}
