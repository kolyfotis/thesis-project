package com.fotis.thesis.service;

import com.fotis.thesis.entity.Car;
import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.pojo.UserTimeSpentOnCar;
import com.fotis.thesis.util.CarUtil;
import com.fotis.thesis.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserTimeSpentServiceJpaImpl implements UserTimeSpentService {
private final CarService carService;
private final UserDataService userDataService;
private final UserService userService;

public static final BigInteger MINIMUM_TIME_TO_TRACK_IN_MILLISECONDS = BigInteger.valueOf(5 * 1000);
public static final BigInteger MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS = BigInteger.valueOf(15 * 60 * 1000);

@Autowired
public UserTimeSpentServiceJpaImpl(CarService carService, UserDataService userDataService, UserService userService) {
  this.carService = carService;
  this.userDataService = userDataService;
  this.userService = userService;
}


/**
 * <p>Saves the user's time spent for each tracked field-value pair using the UserDataService.</p>
 * <p>Validates the username. Ensures that the minimum time spent is more that the minimum time required
 * to track user's time spent, and that the maximum time spent does not exceed the maximum acceptable time to track.</p>
 * <p>Retrieves the car, and a Map with the tracked field-value pairs.</p>
 * <p>Iterates over the Map and checks for each entry if the username, fieldName and fieldValue combination already
 * exists in the user data table. If it does, it will add the current time spent to the time spent field and update
 * the row. If it doesn't it will insert a new user data row.</p>
 * <p>Finally calls the normalizeUserData method with the User data that changed to calculate the new values, and
 * then the saveAll method to save the new values</p>
 *
 * @param   userTimeSpentOnCar  a POJO that contains username, carId and time spent viewing the car in milliseconds
 * @see     com.fotis.thesis.util.CarUtil#getTrackedColumnNamesAndValues(Car)
 * @see     com.fotis.thesis.service.UserDataServiceJpaImpl#findByUsernameAndFieldNameAndFieldValue(String, String, String)
 *
 * */
@Override
public void saveUserData(UserTimeSpentOnCar userTimeSpentOnCar) {
  String username = userTimeSpentOnCar.getUsername();
  BigInteger timeSpentInMillis = userTimeSpentOnCar.getTimeSpentInMillis();
  Boolean userExists = UserUtil.userExists(username, userService);

  if (timeSpentInMillis.compareTo(MINIMUM_TIME_TO_TRACK_IN_MILLISECONDS) < 0
      || !userExists) {
    return;
  }

  if (timeSpentInMillis.compareTo(MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS) > 0) {
    timeSpentInMillis = MAXIMUM_TIME_TO_TRACK_IN_MILLISECONDS;
  }

  Optional<Car> car = carService.findById(userTimeSpentOnCar.getCarId());

  if (car.isPresent()) {
    Map<String, String> trackedColumnNames = CarUtil.getTrackedColumnNamesAndValues(car.get());
    List<UserData> userDataList = new ArrayList<>();

    for (Map.Entry<String, String> column : trackedColumnNames.entrySet()) {
      String fieldName = column.getKey();
      String fieldValue = column.getValue();

      Optional<UserData> userDataRow = userDataService.findByUsernameAndFieldNameAndFieldValue(
          username, fieldName, fieldValue
      );

      if (userDataRow.isPresent()) {
        userDataRow.get().setTimeSpent(userDataRow.get().getTimeSpent().add(timeSpentInMillis));
        userDataList.add(userDataService.save(userDataRow.get()));
      } else {
        UserData userData = new UserData(0, username, fieldName, fieldValue, timeSpentInMillis, BigDecimal.ZERO);
        userDataList.add(userDataService.save(userData));
      }
    }

    userDataService.saveAll(userDataService.normalizeUserData(userDataList));
  }
}
}
