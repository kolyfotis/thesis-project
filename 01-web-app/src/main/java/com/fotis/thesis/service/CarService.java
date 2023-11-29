package com.fotis.thesis.service;

import com.fotis.thesis.entity.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
List<Car> findAllByOrderByMakeAscModelAsc();

Optional<Car> findById(Integer id);
}
