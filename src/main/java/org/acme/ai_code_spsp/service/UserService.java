package org.acme.ai_code_spsp.service;

import org.acme.ai_code_spsp.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser (String username, String password, String role);
    Optional<User> editPassword(Long id, String newPassword);
    Optional<User> toggleUnlockedStatus(Long id);
    void deleteUser(Long id);
}
