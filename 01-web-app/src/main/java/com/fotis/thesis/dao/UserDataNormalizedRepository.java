package com.fotis.thesis.dao;

import com.fotis.thesis.entity.UserDataNormalized;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDataNormalizedRepository extends JpaRepository<UserDataNormalized, Integer> {
List<UserDataNormalized> findByUsernameAndFieldName(String username, String fieldName);
}
