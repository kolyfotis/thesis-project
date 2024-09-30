package com.fotis.thesis.service;

import com.fotis.thesis.dao.UserRecommendationRepository;
import com.fotis.thesis.entity.UserRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecommendationServiceImpl implements UserRecommendationService {
private final UserRecommendationRepository userRecommendationRepository;

@Autowired
public UserRecommendationServiceImpl(UserRecommendationRepository theUserRecommendationRepository) {
  userRecommendationRepository = theUserRecommendationRepository;
}

@Override
public List<UserRecommendation> findByUsernameOrderByRelevanceDesc(String userName) {
  return userRecommendationRepository.findByUsernameOrderByRelevanceDesc(userName);
}

}
