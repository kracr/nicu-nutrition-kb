package com.inicu.postgres.utility;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtility {
	/*
	 * public static String SAVE_OPERATION = "SAVE_OPERATION"; public static String
	 * FETCH_OPERATION = "FETCH_OPERATION";
	 */
	// constants are defined here....
	public static DateFormat dateFormatGMT = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss 'GMT'z", Locale.ENGLISH);
	public static SimpleDateFormat dateFormatUTF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
	public static SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	public static SimpleDateFormat dateFormatUI = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
	public static SimpleDateFormat dateFormatAMPM = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
	public static SimpleDateFormat dateFormat24Time = new SimpleDateFormat("hh:mm", Locale.ENGLISH);
	public static SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
	public static SimpleDateFormat timeStampFormatUI = new SimpleDateFormat("yyyy-MM-dd HH:mm a", Locale.ENGLISH);
	// name constants..
	public static final String GMT = "gmt";
	public static final String UTF = "gmt";
	public static final String DB = "db";
	public static final String UI = "ui";
	public static final String AMPM = "ampm";
	public static final String TIME24 = "24Time";
	public static final String TIMESTAMP = "timestamp";

	// time zones ids.... save dates in database in utc time zone and during get
	// convert it to locale time zone.
	public static final String UTC_TIME_ZONE_ID = "UTC";
	public static final String INDIA_TIME_ZONE_ID = "Asia/Calcutta";
	public static final String AMERICA_TIME_ZONE_ID = "America/New_York";

	// specific to client ...
	public static final String CLIENT_TIME_ZONE = "CLIENT_TIME_ZONE";
	private static final String CLIENT_TIMEZONE = "client_time_zone";
	private static final String SERVER_TIMEZONE = "server_time_zone";
	public static final String SERVER_CRUD_OPERATION = "server_crud_operation";
	public static final String CLIENT_CRUD_OPERATION = "client_crud_operation";

	public static String convertFromToFormat(String inputdate, String fromFormat, String toFormat)
			throws ParseException {

		String output = "";
		if (fromFormat.equalsIgnoreCase(GMT)) {
			if (toFormat.equalsIgnoreCase(UTF)) {
				Date date = dateFormatGMT.parse(inputdate);
				dateFormatDB.setTimeZone(TimeZone.getTimeZone("UTC"));
				output = dateFormatDB.format(date);
			}
		}
		return output;
	}

	public static String convertFromToTimeZone(String inputdate, String fromTimeZone, String toTimeZone)
			throws ParseException {

		String output = "";
		if (fromTimeZone.equalsIgnoreCase(CLIENT_TIMEZONE)) {
			if (toTimeZone.equalsIgnoreCase(SERVER_TIMEZONE)) {
				// first parse with time stamp format for client time zone..

				// convert to utc time zone and return
			}
		}
		return output;
	}

	public static DateFormat getDateformatgmt(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatGMT;
	}

	public static SimpleDateFormat getDateformatutf(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatUTF.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatUTF.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatUTF;
	}

	public static SimpleDateFormat getDateformatdb(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatDB.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatDB.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatDB;
	}

	public static SimpleDateFormat getDateformatui(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatUI.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatUI.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatUI;
	}

	public static SimpleDateFormat getDateformatampm(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatAMPM.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatAMPM.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatAMPM;
	}

	public static SimpleDateFormat getDateformat24time(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormat24Time.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormat24Time.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormat24Time;
	}

	public static DateFormat getDateFormatGMT(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			dateFormatGMT.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return dateFormatGMT;
	}

	public static SimpleDateFormat getTimeStampFormat(String operation) {
		if (operation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			timeStampFormat.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			timeStampFormat.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return timeStampFormat;
	}

	public static void getTimeZoneDate(String date, String formatInput, String formatOutput, String operation)
			throws ParseException {

		if (formatInput.equalsIgnoreCase(CalendarUtility.TIMESTAMP)) {

			SimpleDateFormat format = CalendarUtility.getTimeStampFormat(operation);
			Date dateInd = format.parse(date);
			SimpleDateFormat formatServer = CalendarUtility.getDateformatui(operation);
		}
	}

	/*
	 * public static void setDefaultTimeZone(String operation) {
	 * 
	 * if(operation.equalsIgnoreCase(CalendarUtility.SERVER_CRUD_OPERATION)){
	 * TimeZone.setDefault(TimeZone.getTimeZone(UTC_TIME_ZONE_ID)); }else
	 * if(operation.equalsIgnoreCase(CalendarUtility.CLIENT_CRUD_OPERATION)){
	 * TimeZone.setDefault(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID)); } }
	 */

	public static Timestamp getCurrentTimeStamp(String serverCrudOperation) {
		Timestamp currentTimeStamp = null;
		if (serverCrudOperation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			currentTimeStamp = Timestamp
					.valueOf(getTimeStampFormat(CalendarUtility.SERVER_CRUD_OPERATION).format(new Date()));
		} else {
			currentTimeStamp = Timestamp
					.valueOf(getTimeStampFormat(CalendarUtility.CLIENT_CRUD_OPERATION).format(new Date()));
		}
		return currentTimeStamp;
	}

	public static SimpleDateFormat getTimeStampFormatUI(String serverCrudOperation) {
		if (serverCrudOperation.equalsIgnoreCase(SERVER_CRUD_OPERATION)) {
			timeStampFormatUI.setTimeZone(TimeZone.getTimeZone(UTC_TIME_ZONE_ID));
		} else {
			timeStampFormatUI.setTimeZone(TimeZone.getTimeZone(INDIA_TIME_ZONE_ID));
		}
		return timeStampFormatUI;
	}

	public static Calendar getDateTimeFromDateAndTime(java.util.Date date, String time) {

		Calendar cal = Calendar.getInstance();
		String[] timeArr = new String[3];
		if (!BasicUtils.isEmpty(time)) {
			timeArr = time.split(",");
		}
		cal.setTime(date);
		//System.out.println(birthDateCal.getTime());
		if (timeArr.length >= 3 && !BasicUtils.isEmpty(timeArr[1])) {

			if (!BasicUtils.isEmpty(timeArr[2]) && timeArr[2].equalsIgnoreCase("AM")) {
				if (!BasicUtils.isEmpty(timeArr[0])) {
					if (Integer.valueOf(timeArr[0]) == 12) {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]) - 12);
					} else {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]));
					}
				}
			} else if (!BasicUtils.isEmpty(timeArr[2]) && timeArr[2].equalsIgnoreCase("PM")) {
				if (!BasicUtils.isEmpty(timeArr[0])) {
					if (Integer.valueOf(timeArr[0]) == 12) {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]));
					} else {
						cal.set(Calendar.HOUR, Integer.valueOf(timeArr[0]) + 12);
					}
				}
			}
			if (!BasicUtils.isEmpty(timeArr[1]))
				cal.set(Calendar.MINUTE, Integer.valueOf(timeArr[1]));
		} else {
			cal.set(Calendar.HOUR, Integer.valueOf("0"));
			cal.set(Calendar.MINUTE, Integer.valueOf("0"));
			cal.set(Calendar.SECOND, Integer.valueOf("0"));
		}
		return cal;
	}
}
