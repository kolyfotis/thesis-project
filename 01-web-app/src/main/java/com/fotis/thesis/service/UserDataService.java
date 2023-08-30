package com.fotis.thesis.service;

import com.fotis.thesis.entity.UserData;

import java.util.Optional;

public interface UserDataService {
UserData save(UserData userData);

Optional<UserData> findByUsernameAndFieldNameAndFieldValue(
    String username, String fieldName, String fieldValue);
}