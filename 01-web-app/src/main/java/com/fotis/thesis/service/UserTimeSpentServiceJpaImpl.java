package com.fotis.thesis.service;

import com.fotis.thesis.entity.Car;
import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.pojo.UserTimeSpentOnCar;
import com.fotis.thesis.util.CarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

@Service
public class UserTimeSpentServiceJpaImpl implements UserTimeSpentService {
private final CarService carService;
private final UserDataService userDataService;
public static final BigInteger MINIMUM_TIME_TO_TRACK_IN_MILLISECONDS = BigInteger.valueOf(5 * 1000);
public static final BigInteger MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS = BigInteger.valueOf(15 * 60 * 1000);

@Autowired
public UserTimeSpentServiceJpaImpl(CarService carService, UserDataService userDataService) {
  this.carService = carService;
  this.userDataService = userDataService;
}


/**
 * <p>Saves the user's time spent for each tracked field-value pair using the UserDataService.</p>
 * <p>Ensures that the minimum time spent is more that the minimum time required to track user's time spent,
 * and that the maximum time spent does not exceed the maximum acceptable time to track.</p>
 * <p>Retrieves the car, and a Map with the tracked field-value pairs.</p>
 * <p>Iterates over the Map and checks for each entry if the username, fieldName and fieldValue combination already
 * exists in the user data table. If it does, it will add the current time spent to the time spent field and update
 * the row. If it doesn't it will insert a new user data row.</p>
 *
 * @param   userTimeSpentOnCar  a POJO that contains username, carId and time spent viewing the car in milliseconds
 * @see     com.fotis.thesis.util.CarUtil#getTrackedColumnNamesAndValues(Car)
 * @see     com.fotis.thesis.service.UserDataServiceJpaImpl#findByUsernameAndFieldNameAndFieldValue(String, String, String)
 * */
@Override
public void saveUserData(UserTimeSpentOnCar userTimeSpentOnCar) {
  String username = userTimeSpentOnCar.getUsername();
  BigInteger timeSpentInMillis = userTimeSpentOnCar.getTimeSpentInMillis();
  Optional<Car> car = carService.findById(userTimeSpentOnCar.getCarId());

  if (timeSpentInMillis.compareTo(MINIMUM_TIME_TO_TRACK_IN_MILLISECONDS) < 0) {
    return;
  }

  if (timeSpentInMillis.compareTo(MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS) > 0) {
    timeSpentInMillis = MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS;
  }

  if (car.isPresent()) {
    Map<String, String> trackedColumnNames = CarUtil.getTrackedColumnNamesAndValues(car.get());

    for (Map.Entry<String, String> column : trackedColumnNames.entrySet()) {
      String fieldName = column.getKey();
      String fieldValue = column.getValue();

      Optional<UserData> userDataRow = userDataService.findByUsernameAndFieldNameAndFieldValue(
          username, fieldName, fieldValue
      );

      if (userDataRow.isPresent()) {
        userDataRow.get().setTimeSpent(userDataRow.get().getTimeSpent().add(timeSpentInMillis));
        userDataService.save(userDataRow.get());
      } else {
        UserData userData = new UserData(0, username, fieldName, fieldValue, timeSpentInMillis);
        userDataService.save(userData);
      }
    }

    // TODO: call service to normalize the values and store to the database
  }
}
}
