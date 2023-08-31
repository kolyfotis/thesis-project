package com.fotis.thesis.util;

import com.fotis.thesis.entity.User;
import com.fotis.thesis.service.UserService;

import java.util.Optional;

public class UserUtil {
public static Boolean userExists(String username, UserService userService) {
  Optional<User> user = userService.findByUsername(username);
  return user.isPresent();
}
}
