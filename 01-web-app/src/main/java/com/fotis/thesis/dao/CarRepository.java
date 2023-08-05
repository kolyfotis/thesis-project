package com.fotis.thesis.dao;

import com.fotis.thesis.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository <Car, Integer> {
}
