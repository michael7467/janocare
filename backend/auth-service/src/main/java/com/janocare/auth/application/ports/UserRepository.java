package com.janocare.auth.application.ports;

import com.janocare.auth.domain.entities.User;
import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);
}