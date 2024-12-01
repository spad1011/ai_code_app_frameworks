package org.acme.ai_code_spsp.controller;

import org.acme.ai_code_spsp.entity.User;
import org.acme.ai_code_spsp.service.impl.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/internal/users")
public class UserController {
    private final UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        return ResponseEntity.ok(userServiceImpl.createUser(username, password, role));
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<User> resetPassword(@PathVariable Long id, @RequestParam String newPassword) {
        Optional<User> updatedUser = userServiceImpl.editPassword(id, newPassword);
        return updatedUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/toggle-unlocked")
    public ResponseEntity<User> toggleUnlocked(@PathVariable Long id) {
        Optional<User> updatedUser = userServiceImpl.toggleUnlockedStatus(id);
        return updatedUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
