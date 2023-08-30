package com.fotis.thesis.controller;

import com.fotis.thesis.entity.Car;
import com.fotis.thesis.service.CarService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {

private final CarService carService;

public CarController(CarService theCarService) {
  carService = theCarService;
}

@GetMapping()
public String listCars(Model model) {

  List<Car> cars = carService.findAll();

  model.addAttribute("cars", cars);

  return "car-list";
}

@GetMapping("/{id}")
public String carDetails(Model model, @PathVariable String id) {

  int parsedId;

  try {
    parsedId = Integer.parseInt(id);
  } catch (NumberFormatException e) {
    parsedId = 0;
  }

  Optional<Car> car = carService.findById(parsedId);

  if (car.isEmpty())
    return "error/404";

  model.addAttribute("car", (
      car.orElseGet(Car::new))
  );

  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  String username = authentication.getName();
  model.addAttribute("userName", username);

  return "car-detail";
}
}
