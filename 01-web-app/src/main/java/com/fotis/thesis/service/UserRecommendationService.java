package com.fotis.thesis.service;

import com.fotis.thesis.entity.UserRecommendation;

import java.util.List;

public interface UserRecommendationService {
  List<UserRecommendation> findByUsernameOrderByRelevanceDesc(String userName);
}
