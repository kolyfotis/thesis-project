package com.fotis.thesis.controller;

import com.fotis.thesis.entity.Car;
import com.fotis.thesis.entity.UserRecommendation;
import com.fotis.thesis.service.CarService;
import com.fotis.thesis.service.UserRecommendationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
public class ProfileController {

private final UserRecommendationService userRecommendationService;
private final CarService carService;

public ProfileController(UserRecommendationService theUserRecommendationService,
                         CarService theCarService) {
  userRecommendationService = theUserRecommendationService;
  carService = theCarService;
}

@GetMapping()
public String showProfile(Model model) {

  // get username by authentication
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String username = authentication.getName();
  model.addAttribute("userName", username);

  // fetch the list of recommended cars sorted by relevance
  List<UserRecommendation> userRecommendations =
      userRecommendationService.findByUsernameOrderByRelevanceDesc(username);

  // extract the car IDs (sorted by relevance)
  List<Integer> iDs = userRecommendations.stream()
      .map(UserRecommendation::getCarId)
      .collect(Collectors.toList());

  // load cars from car table (unordered)
  List<Car> unorderedCars = carService.findAllById(iDs);
  List<Car> cars = new ArrayList<>();

  // sort cars based on relevance (iDs List)
  for (Integer id : iDs) {
    cars.add(unorderedCars.stream()
        .filter(x -> x.getId().equals(id)).findFirst().orElse(new Car()));
  }

  model.addAttribute("cars", cars);

  return "profile";
}

}
