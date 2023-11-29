package com.fotis.thesis.service;

import com.fotis.thesis.dao.UserDataRepository;
import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.util.UserDataNormalizedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
public List<UserData> saveAll(List<UserData> userData) {
  return userDataRepository.saveAll(userData);
}

@Override
public List<UserData> normalizeUserData(List<UserData> userData) {
  return UserDataNormalizedUtil.normalizeUserData(userData, this);
}

@Override
public Optional<UserData> findByUsernameAndFieldNameAndFieldValue(
    String username, String fieldName, String fieldValue) {
  return userDataRepository.findByUsernameAndFieldNameAndFieldValue(username, fieldName, fieldValue);
}

@Override
public List<UserData> findByUsernameAndFieldName(String username, String fieldName) {
  return userDataRepository.findByUsernameAndFieldName(username, fieldName);
}

}
