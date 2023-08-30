package com.fotis.thesis.util;

import com.fotis.thesis.entity.Car;
import jakarta.persistence.Column;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarUtil {

private static final List<String> TRACKED_FIELDS = List.of(
    "make", "bodyType", "fuelType", "gearbox", "doorNumber"
);

public static Map<String, String> getTrackedColumnNamesAndValues(Car car) {
  Map<String, String> columnNames = new HashMap<>();

  Class<Car> carClass = Car.class;
  Field[] fields = carClass.getDeclaredFields();

  for (Field field : fields) {
    if (TRACKED_FIELDS.contains(field.getName())) {
      Column column = field.getAnnotation(Column.class);
      field.setAccessible(true);

      try {
        columnNames.put(column.name(), String.valueOf(field.get(car)));
      } catch (IllegalAccessException e) {
        System.out.printf("ERROR: CarUtil::getTrackedColumnNamesAndValues(): %s", e.getMessage());
      }
    }
  }

  return columnNames;
}
}
