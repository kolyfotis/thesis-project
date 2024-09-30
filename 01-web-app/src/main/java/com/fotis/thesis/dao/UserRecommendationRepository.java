package com.fotis.thesis.dao;

import com.fotis.thesis.entity.CompositeKey;
import com.fotis.thesis.entity.UserRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRecommendationRepository extends JpaRepository<UserRecommendation, CompositeKey> {
  List<UserRecommendation> findByUsernameOrderByRelevanceDesc(String username);
}
