package com.fotis.thesis.controller;

import com.fotis.thesis.entity.Car;
import com.fotis.thesis.service.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {

private CarService carService;

public CarController(CarService theCarService) {
  carService = theCarService;
}

@GetMapping()
public String listCars(Model model) {

  List<Car> cars = carService.findAll();

  model.addAttribute("cars", cars);

  return "car-list";
}
}
