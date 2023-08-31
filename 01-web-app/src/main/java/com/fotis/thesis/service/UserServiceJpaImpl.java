package com.fotis.thesis.service;

import com.fotis.thesis.dao.UserRepository;
import com.fotis.thesis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceJpaImpl implements UserService {
private final UserRepository userRepository;

@Autowired
UserServiceJpaImpl(UserRepository userRepository) {
  this.userRepository = userRepository;
}

@Override
public Optional<User> findByUsername(String username) {
  return userRepository.findByUsername(username);
}
}
