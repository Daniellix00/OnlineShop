package com.onlineshop.service;

import com.onlineshop.domain.User;
import com.onlineshop.exceptions.EmailAlreadyExistsException;
import com.onlineshop.exceptions.UserNotFoundException;
import com.onlineshop.exceptions.UsernameNotFoundException;
import com.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public User registerUser(User user) {

        //sprawdzam czy uzytkownik o podanym adresie email istenieje
        if (userRepository.existByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException("Email is already in use");
        }
        //Kodowanie hasla
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        user.setRoles(Collections.singletonList("ROLE_USER"));
        return userRepository.save(user);

    }
    public void deleteUser(int userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));

        // Usuwamy użytkownika z bazy
        userRepository.delete(user);

        // Jeśli użytkownik jest zalogowany, usuwamy jego sesję i wylogowujemy go
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getName().equals(user.getUsername())){
            // Jeśli użytkownik jest zalogowany, wyloguj go natychmiast po usunięciu konta
            SecurityContextHolder.clearContext();
        }

    }
    public User getUserById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User witk ID" + userId + "not found"));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            // Zabezpieczenie przed próbą logowania zwykłego użytkownika jako admin
            return new org.springframework.security.core.userdetails.User(
                    "admin",
                    passwordEncoder.encode("secretPassword"),  // Zaszyfrowane hasło admina
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // Jeśli to nie jest admin, to szukamy użytkownika w bazie
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
