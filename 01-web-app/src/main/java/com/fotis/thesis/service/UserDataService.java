package com.fotis.thesis.service;

import com.fotis.thesis.entity.UserData;

import java.util.List;
import java.util.Optional;

public interface UserDataService {
UserData save(UserData userData);

List<UserData> saveAll(List<UserData> userData);

List<UserData> normalizeUserData(List<UserData> userData);

Optional<UserData> findByUsernameAndFieldNameAndFieldValue(
    String username, String fieldName, String fieldValue);

List<UserData> findByUsernameAndFieldName(String username, String fieldName);

}
