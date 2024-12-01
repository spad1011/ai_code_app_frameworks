package org.acme.ai_code_spsp.service.impl;

import org.acme.ai_code_spsp.entity.User;
import org.acme.ai_code_spsp.repository.UserRepository;
import org.acme.ai_code_spsp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User createUser(String username, String password, String role) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    public Optional<User> editPassword(Long id, String newPassword) {
        return userRepository.findById(id).map(user -> {
            user.setPassword(passwordEncoder.encode(newPassword));
            return userRepository.save(user);
        });
    }

    public Optional<User> toggleUnlockedStatus(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setUnlocked(!user.isUnlocked());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
