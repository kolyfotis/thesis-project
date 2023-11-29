package com.fotis.thesis.util;

import com.fotis.thesis.entity.UserData;
import com.fotis.thesis.service.UserDataService;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class UserDataNormalizedUtil {

/**
 * <p>Normalizes userData timeSpentInMillis for each fieldValue (column name).</p>
 * <p>Iterates over userData and for each field name, gets a List of all field values the user has viewed. For example
 * a list of makes that the user has viewed. Also get a List of the normalized values, to edit if and entry already
 * exists or add a new entry to the list if it doesn't already exist.</p>
 * <p>Calculates the summary of the milliseconds the user spent on a field name (eg. make)</p>
 * <p>Iterates over the values of this field that the user has viewed (eg. Audi, BMW)</p>
 * <p>Sets its normalized time spent to the time spent in milliseconds viewing the field value (eg. Audi) divided by
 * the total time spent viewing for the field.</p>
 * <p>Finally returns the normalizedDataList</p>
 *
 * @param   userData                    a list of newly evaluated userData, to be used for evaluating the normalized
 *                                      values for the tracked fields
 * @param   userDataService             an instance of userDataService, used to query the userData entity and find all
 *                                      values for a field name that a user has viewed (e.g. for a make)
 *
 * @see     com.fotis.thesis.service.UserDataServiceJpaImpl#findByUsernameAndFieldNameAndFieldValue(
 *            String, String, String)
 *
 * */
public static List<UserData> normalizeUserData(List<UserData> userData, UserDataService userDataService) {
  List<UserData> normalizedDataList = new ArrayList<>();

  for (UserData fieldName : userData) {
    List<UserData> fieldValues = userDataService.findByUsernameAndFieldName(
        fieldName.getUsername(), fieldName.getFieldName());
    BigInteger sum = fieldValues.stream()
        .map(UserData::getTimeSpent)
        .reduce(BigInteger.ZERO, BigInteger::add);

    for (UserData fieldValue : fieldValues) {

      fieldValue.setNormalizedTimeSpent(new BigDecimal(
          fieldValue.getTimeSpent()).divide(new BigDecimal(sum), 9, RoundingMode.HALF_DOWN));

      normalizedDataList.add(fieldValue);
    }
  }
  return normalizedDataList;
}
}
