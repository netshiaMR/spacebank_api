package org.spacebank.co.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Utils {

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}
}
