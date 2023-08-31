package com.fotis.thesis.service;

import com.fotis.thesis.dao.UserDataNormalizedRepository;
import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.entity.UserDataNormalized;
import com.fotis.thesis.util.UserDataNormalizedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDataNormalizedServiceJpaImpl implements UserDataNormalizedService {
private final UserDataNormalizedRepository userDataNormalizedRepository;
private final UserDataService userDataService;

@Autowired
public UserDataNormalizedServiceJpaImpl(UserDataNormalizedRepository userDataNormalizedRepository,
                                        UserDataService userDataService) {
  this.userDataNormalizedRepository = userDataNormalizedRepository;
  this.userDataService = userDataService;
}

@Override
public List<UserDataNormalized> saveAll(List<UserDataNormalized> userDataNormalized) {
  return userDataNormalizedRepository.saveAll(userDataNormalized);
}

@Override
public List<UserDataNormalized> normalizeUserData(List<UserData> userData) {
  return UserDataNormalizedUtil.normalizeUserData(userData, userDataService, this);
}

@Override
public List<UserDataNormalized> findByUsernameAndFieldName(String username, String fieldName) {
  return userDataNormalizedRepository.findByUsernameAndFieldName(username, fieldName);
}

}
