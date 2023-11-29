package com.fotis.thesis.service;

import com.fotis.thesis.dao.CarRepository;
import com.fotis.thesis.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceJpaImpl implements CarService {
private final CarRepository carRepository;

@Autowired
public CarServiceJpaImpl(CarRepository theCarRepository) {
  carRepository = theCarRepository;
}

@Override
public List<Car> findAllByOrderByMakeAscModelAsc() {
  return carRepository.findAllByOrderByMakeAscModelAsc();
}

@Override
public Optional<Car> findById(Integer id) {
  return carRepository.findById(id);
}
}
