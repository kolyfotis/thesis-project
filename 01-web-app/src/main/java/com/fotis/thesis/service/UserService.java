package com.fotis.thesis.service;

import com.fotis.thesis.entity.User;
import java.util.Optional;

public interface UserService {
Optional<User> findByUsername(String username);
}
