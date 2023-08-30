package com.fotis.thesis.pojo;

import lombok.*;
import java.math.BigInteger;

/**
 * POJO Class with Serializable fields for easier parsing of
 * request with username, carId and time spent viewing car
 * */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTimeSpentOnCar {

private String username;
private Integer carId;
private BigInteger timeSpentInMillis;

}
