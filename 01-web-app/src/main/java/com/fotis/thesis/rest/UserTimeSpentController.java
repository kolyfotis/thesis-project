package com.fotis.thesis.rest;

import com.fotis.thesis.pojo.UserTimeSpentOnCar;
import com.fotis.thesis.service.UserTimeSpentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userTimeSpent")
public class UserTimeSpentController {

private final UserTimeSpentService userTimeSpentService;

public UserTimeSpentController(UserTimeSpentService userTimeSpentService) {
  this.userTimeSpentService = userTimeSpentService;
}

@PostMapping("/viewingCar")
public void parseTimeSpentViewingCar(@RequestBody UserTimeSpentOnCar userTimeSpentOnCar) {
  userTimeSpentService.saveUserData(userTimeSpentOnCar);
}
}
