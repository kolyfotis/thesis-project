package com.fotis.thesis.service;

import com.fotis.thesis.dao.UserDataRepository;
import com.fotis.thesis.entity.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataServiceJpaImpl implements UserDataService {

private final UserDataRepository userDataRepository;

@Autowired
public UserDataServiceJpaImpl(UserDataRepository userDataRepository) {
  this.userDataRepository = userDataRepository;
}

@Override
public UserData save(UserData userData) {
  return userDataRepository.save(userData);
}

@Override
public Optional<UserData> findByUsernameAndFieldNameAndFieldValue(
    String username, String fieldName, String fieldValue) {
  return userDataRepository.findByUsernameAndFieldNameAndFieldValue(username, fieldName, fieldValue);
}

}
