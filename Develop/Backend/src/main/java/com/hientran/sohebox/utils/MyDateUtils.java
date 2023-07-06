package com.hientran.sohebox.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class MyDateUtils {

	private static final int MAX_HOUR_INDEX_23 = 23;

	private static final int MAX_SECOND = 59;

	private static final int MAX_MINUTE = 59;

	public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static final String REPORT_DATE_FORMAT = "yyyyMMdd_HHmm";

	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * 
	 * Get date with time is 0:0:0
	 *
	 * @param aDate
	 * @return
	 */
	public static Date getDateMin(Date aDate) {
		return changeDateTime(aDate, 0, 0, 0);
	}

	/**
	 * 
	 * Get date with time is 23:59:59
	 *
	 * @param aDate
	 * @return
	 */
	public static Date getDateMax(Date aDate) {
		return changeDateTime(aDate, MAX_HOUR_INDEX_23, MAX_MINUTE, MAX_SECOND);
	}

	/**
	 * 
	 * Change time hh:mm:ss of a date
	 *
	 * @param aDate
	 * @param hour
	 * @param minute
	 * @param second
	 * @return
	 */
	public static Date changeDateTime(Date aDate, int hour, int minute, int second) {
		// Declare result
		Date result = null;

		if (aDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(aDate);
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			calendar.set(Calendar.MILLISECOND, 0);
			result = calendar.getTime();
		}

		// Return
		return result;
	}

	/**
	 * 
	 * Convert String to Date
	 *
	 * @param sDate
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String sDate, String pattern) {
		Date date = null;
		if (StringUtils.isNotBlank(sDate)) {
			try {
				date = DateUtils.parseDate(sDate, new String[] { pattern });
			} catch (ParseException e) {
				date = null;
			}
		}

		return date;
	}

	/**
	 * 
	 * Convert data to string
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		String formattedDate = "";
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formattedDate = formatter.format(date);
		}

		return formattedDate;
	}

	/**
	 * Compare two date
	 *
	 * @param date1 Date 1
	 * @param date2 Date 2
	 * @return true If they are the same value
	 * 
	 */
	public static boolean compareDateTime(Date date1, Date date2) {
		boolean result = false;

		if (date1 == null) {
			if (date2 == null) {
				result = true;
			}
		} else {
			if (date2 != null) {
				result = date1.compareTo(date2) == 0 ? true : false;
			}
		}

		return result;
	}

	/**
	 * 
	 * Format duration time text
	 *
	 * @param totalSecond
	 * @return
	 */
	public static String formatDate(Long totalSecond) {
		String result = "";
		int numberOfSeconds = 0;
		int numberOfMinutes = 0;
		int numberOfHours = 0;
		int numberOfDays = 0;

		// Calculate time
		numberOfDays = totalSecond.intValue() / 86400;
		numberOfHours = (totalSecond.intValue() % 86400) / 3600;
		numberOfMinutes = ((totalSecond.intValue() % 86400) % 3600) / 60;
		numberOfSeconds = ((totalSecond.intValue() % 86400) % 3600) % 60;

		// Format text
		switch (numberOfDays) {
		case 0:
			break;
		case 1:
			result = result + numberOfDays + " day";
			break;
		default:
			result = result + numberOfDays + " days";
			break;
		}

		switch (numberOfHours) {
		case 0:
			break;
		case 1:
			result = result + " " + numberOfHours + " hour";
			break;
		default:
			result = result + " " + numberOfHours + " hours";
			break;
		}

		switch (numberOfMinutes) {
		case 0:
			break;
		case 1:
			result = result + " " + numberOfMinutes + " minute";
			break;
		default:
			result = result + " " + numberOfMinutes + " minutes";
			break;
		}

		switch (numberOfSeconds) {
		case 0:
			break;
		case 1:
			result = result + " " + numberOfSeconds + " second";
			break;
		default:
			result = result + " " + numberOfSeconds + " seconds";
			break;
		}

		// Return
		return result;
	}

	/**
	 * Add/minus date by days
	 */
	public static Date addMinusDate(Date date, int days) {
		Date result = null;

		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			result = cal.getTime();
		}
		return result;
	}

	/**
	 * Add/minus date by days
	 */
	public static Date addMinusSecond(Date date, int second) {
		Date result = null;

		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, second);
			result = cal.getTime();
		}
		return result;
	}
}
