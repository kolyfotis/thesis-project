package com.fotis.thesis.service;

import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.entity.UserDataNormalized;

import java.util.List;

public interface UserDataNormalizedService {
List<UserDataNormalized> saveAll(List<UserDataNormalized> userDataNormalized);

List<UserDataNormalized> normalizeUserData(List<UserData> userDataNormalized);

List<UserDataNormalized> findByUsernameAndFieldName(String username, String fieldName);
}
