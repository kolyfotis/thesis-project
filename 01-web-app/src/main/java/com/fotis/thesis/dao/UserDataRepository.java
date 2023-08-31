package com.fotis.thesis.dao;

import com.fotis.thesis.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
Optional<UserData> findByUsernameAndFieldNameAndFieldValue(
    String username, String fieldName, String fieldValue);

List<UserData> findByUsernameAndFieldName(String username, String fieldName);
}
