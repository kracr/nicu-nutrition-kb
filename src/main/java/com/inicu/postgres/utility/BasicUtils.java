package com.inicu.postgres.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import javax.imageio.ImageIO;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.inicu.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.google.gson.Gson;
import com.inicu.postgres.entities.BabyDetail;

@Repository
public class BasicUtils {

	@Autowired
	public static InicuPostgresUtililty postgresUtil;

	@Autowired
	public static InicuDatabaseExeption nicuException;



	private static  final List<String> NameOfTestWithoutSample = new ArrayList<String>(Arrays.asList("x-ray","ecg","kub","mcu","mri","nuclear medical scan","ultrasound - brain (neurosonogram)","usg abdomen","usg","x-ray (chest)","x-ray chest ap","x-ray abdomen", "ct scan"));

	/**
	 * This method returns true if the collection is null or is empty.
	 * 
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty() || collection.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns true of the map is null or is empty.
	 * 
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}


	public static List<String> getNameOfTestWithoutSample() {
		return NameOfTestWithoutSample;
	}
	/**
	 * This method returns true if the object is null.
	 * 
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * 
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * 
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() == 0 || string.trim().equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}

	public static String getIpAddress() {
		String ip = "0.0.0.0";
		try (final DatagramSocket socket = new DatagramSocket()) {
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			ip = socket.getLocalAddress().getHostAddress();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ip;
	}

	/**
	 * This method returns printStackTrace string from Exception.
	 * 
	 * @param Exception
	 * @return String
	 */
	public static String convertErrorStacktoString(Exception exception) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		exception.printStackTrace(printWriter);
		String s = writer.toString();
		return s;
	}

	// public static void main(String[] args) throws Exception {
	// Exception e = new NullPointerException();
	// String[] receiverArray2 = { "sourabh.verma@inicucloud.com",
	// "sourabh_verma@oxyent.com" };
	// sendMail(receiverArray2, RecipientType.TO, convertErrorStacktoString(e),
	// "testing Mail BCC",
	// BasicConstants.COMPANY_ID);
	//
	// HashMap<Message.RecipientType, List<String>> emailMap = new
	// HashMap<Message.RecipientType, List<String>>() {
	// private static final long serialVersionUID = 1L;
	// {
	// put(Message.RecipientType.TO, new ArrayList<String>());
	// put(Message.RecipientType.CC, new ArrayList<String>());
	// }
	// };
	//
	// emailMap.get(Message.RecipientType.TO).add("sourabh.verma@inicucloud.com");
	// emailMap.get(Message.RecipientType.CC).add("sourabh_verma@oxyent.com");
	//
	// BasicUtils.sendMailWithMultipleType(emailMap, convertErrorStacktoString(e),
	// "testing Mail",
	// BasicConstants.COMPANY_ID);
	// }

	public static void sendMail(String[] receiverArray, RecipientType type, String message, String subject,
			String companyId) {
		MimeMessage simpleMessage = getMailSession();
		InternetAddress[] recipientArr = new InternetAddress[receiverArray.length];
		try {
			for (int i = 0; i < receiverArray.length; i++) {
				recipientArr[i] = new InternetAddress(receiverArray[i]);
			}
		} catch (AddressException e) {
			e.printStackTrace();
		}

		try {
			simpleMessage.addRecipients(type, recipientArr);
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}

		mailThread(simpleMessage, message, subject, companyId);
	}

	public static void sendMailWithMultipleType(HashMap<Message.RecipientType, List<String>> recipientMap,
			String message, String subject, String companyId) {
		MimeMessage simpleMessage = getMailSession();

		InternetAddress[] recipientArr = null;
		List<String> receiverList = null;
		for (Message.RecipientType key : recipientMap.keySet()) {
			receiverList = recipientMap.get(key);
			if (!BasicUtils.isEmpty(receiverList)) {
				recipientArr = new InternetAddress[receiverList.size()];

				try {
					for (int i = 0; i < receiverList.size(); i++) {
						recipientArr[i] = new InternetAddress(receiverList.get(i));
					}
				} catch (AddressException e) {
					e.printStackTrace();
				}

				try {
					simpleMessage.addRecipients(key, recipientArr);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		}

		mailThread(simpleMessage, message, subject, companyId);
	}

	public static void sendMailWithMultipleTypeWithAttachment(HashMap<Message.RecipientType, List<String>> recipientMap,
			String message, String subject, String companyId, List<BufferedImage> attachments) {
		MimeMessage simpleMessage = getMailSession();
		InternetAddress[] recipientArr = null;
		List<String> receiverList = null;
		for (Message.RecipientType key : recipientMap.keySet()) {
			receiverList = recipientMap.get(key);
			if (!BasicUtils.isEmpty(receiverList)) {
				recipientArr = new InternetAddress[receiverList.size()];

				try {
					for (int i = 0; i < receiverList.size(); i++) {
						recipientArr[i] = new InternetAddress(receiverList.get(i));
					}
				} catch (AddressException e) {
					e.printStackTrace();
				}

				try {
					simpleMessage.addRecipients(key, recipientArr);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		}
		mailThreadWithAttachments(simpleMessage, message, subject, companyId, attachments);
	}

	private static MimeMessage getMailSession() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "host2006.hostmonster.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session mailSession = Session.getDefaultInstance(props);
		mailSession = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(BasicConstants.MAIL_ID_TO_SEND, BasicConstants.MAIL_PASSWORD);
			}
		});

		return new MimeMessage(mailSession);
	}

	private static void mailThreadWithAttachments(MimeMessage simpleMessage, String message, String subject,
			String companyId, List<BufferedImage> attachments) {
		try {
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			// Now set the actual message
			messageBodyPart.setText(message);
			multipart.addBodyPart(messageBodyPart);
			// Add attachments
			int counter = 1;
			// Add Attachments

			MimeBodyPart imagePart1 = new MimeBodyPart();
			File outputfile1 = new File("Usage_Weekly" + counter + ".png");
			ImageIO.write(attachments.get(0), "png", outputfile1);
			// imagePart1.setHeader("Content-ID", "iNICU0");
			// imagePart1.setFileName("Usage_Weekly" + counter + ".png");
			// imagePart1.setDisposition(MimeBodyPart.INLINE);
			imagePart1.setFileName("Usage_Weekly" + counter + ".png");
			imagePart1.attachFile(outputfile1);
			multipart.addBodyPart(imagePart1);
			counter++;

			MimeBodyPart imagePart2 = new MimeBodyPart();
			File outputfile2 = new File("Usage_Weekly" + counter + ".png");
			ImageIO.write(attachments.get(1), "png", outputfile2);
			// imagePart2.setHeader("Content-ID", "iNICU1");
			// imagePart2.setDisposition(MimeBodyPart.INLINE);
			imagePart2.setFileName("Usage_Weekly" + counter + ".png");
			imagePart2.attachFile(outputfile2);
			multipart.addBodyPart(imagePart2);

			simpleMessage.setFrom(new InternetAddress(BasicConstants.MAIL_ID_TO_SEND));
			simpleMessage.setSubject(subject + " from " + companyId);
			simpleMessage.setContent(multipart);
			Thread mail1 = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Transport.send(simpleMessage);
						System.out.println("Mail1 sent");
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			});

			if (System.getProperty("os.name").startsWith("Windows")) {
				// mail1.start();
			} else {
				mail1.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mailThread(MimeMessage simpleMessage, String message, String subject, String companyId) {
		try {
			simpleMessage.setFrom(new InternetAddress(BasicConstants.MAIL_ID_TO_SEND));
			simpleMessage.setSubject(subject + " from " + companyId);
			simpleMessage.setText(message);
			simpleMessage.setContent(message, "text/html");
			Thread mail1 = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Transport.send(simpleMessage);
						System.out.println("Mail1 sent");
					} catch (MessagingException e) {
						e.printStackTrace();
					}
				}
			});

			if (System.getProperty("os.name").startsWith("Windows")) {
				// mail1.start();
			} else {
				mail1.start();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static String getObjAsJson(Object obj) {
		Gson gson = new Gson();
		String jsonObj = gson.toJson(obj);
		return jsonObj;
	}

	public static ResponseEntity<ResponseMessageWithResponseObject> handleInvalidInputJson(Object object,
			BindingResult bindingResult) {
		String returnMessage = "";
		int index = 1;
		for (FieldError objError : bindingResult.getFieldErrors()) {
			String message = "Error-" + index + ". " + "Parameter " + objError.getField() + " of Object "
					+ objError.getObjectName() + " " + objError.getDefaultMessage() + "\n";
			System.out.println(message);
			returnMessage = returnMessage + message;
			index++;
		}
		ResponseMessageWithResponseObject responseObj = new ResponseMessageWithResponseObject();
		responseObj.setType(BasicConstants.MESSAGE_FAILURE);
		responseObj.setMessage(returnMessage);
		System.out.println("Invalid json " + BasicUtils.getObjAsJson(object));
		return new ResponseEntity<ResponseMessageWithResponseObject>(responseObj,
				HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	public static TimeObj getTimeObjFromStr(String time) {
		TimeObj timeObj = new TimeObj();
		try {
			if (!BasicUtils.isEmpty(time)) {
				String[] timeArr = time.split(BasicConstants.COMMA);
				if (timeArr != null && timeArr.length > 0) {
					if (timeArr[0] != null) {
						timeObj.setHours(timeArr[0]);
					}
					if (timeArr.length >= 1 && timeArr[1] != null) {
						timeObj.setMinutes(timeArr[1]);
					}
					if (timeArr.length >= 2 && timeArr[2] != null) {
						timeObj.setMeridium(timeArr[2]);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println("for **************************" + time);
			ex.printStackTrace();
		}
		return timeObj;
	}

	public static <T> Set<T> setDifference(Set<T> setA, Set<T> setB) {
		Set<T> tmp = new TreeSet<T>(setA);
		tmp.removeAll(setB);
		return tmp;
	}

	public static HashMap<String, TcBRangeObject> getTcBChartData() {

		HashMap<String, TcBRangeObject> tcbChartData = new HashMap<String, TcBRangeObject>() {
			{
				TcBRangeObject hrRange = new TcBRangeObject();
				hrRange.setLow(4);
				hrRange.setMid(5);
				hrRange.setHigh(7);

				put("12", hrRange);

				hrRange.setLow(5);
				hrRange.setMid(6);
				hrRange.setHigh(8);

				put("24", hrRange);

				hrRange.setLow(7);
				hrRange.setMid(9);
				hrRange.setHigh(11);

				put("36", hrRange);

				hrRange.setLow(8.5);
				hrRange.setMid(11);
				hrRange.setHigh(13);

				put("48", hrRange);

				hrRange.setLow(9.5);
				hrRange.setMid(12.5);
				hrRange.setHigh(15);

				put("60", hrRange);

				hrRange.setLow(11);
				hrRange.setMid(13.5);
				hrRange.setHigh(16);

				put("72", hrRange);

				hrRange.setLow(12.5);
				hrRange.setMid(15.5);
				hrRange.setHigh(16.5);

				put("96", hrRange);

				hrRange.setLow(13);
				hrRange.setMid(16);
				hrRange.setHigh(18);

				put("108", hrRange);

				hrRange.setLow(13);
				hrRange.setMid(16);
				hrRange.setHigh(18);

				put("120", hrRange);

				hrRange.setLow(13);
				hrRange.setMid(15.3);
				hrRange.setHigh(17.5);

				put("132", hrRange);

				hrRange.setLow(13);
				hrRange.setMid(15);
				hrRange.setHigh(17.2);

				put("144", hrRange);
			}
		};
		return tcbChartData;
	}

	public static String getBabyAge(Date presentDate, Date dateOfBirth) {
		String age = "";
		long diff = presentDate.getTime() - dateOfBirth.getTime();
		long ageDays = (diff / (24 * 60 * 60 * 1000)) + 1;// days..
		long ageMonth = ageDays / (30);
		// long ageYear =diff/(365*30*24*60*60*1000);
		if (ageMonth > 0)
			age = ageMonth + "months " + (ageDays - (ageMonth * 30)) + "days";
		else
			age = ageDays + "days";

		return age;
	}

	/**
	 * Round to certain number of decimals
	 * 
	 * @param d
	 * @param decimalPlace
	 * @return
	 */
	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}

	/**
	 * 
	 * to get the ordinal value from integer
	 * 
	 * @param i
	 * @return string
	 */
	public static String ordinal(int i) {
		String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
		switch (i % 100) {
		case 11:
		case 12:
		case 13:
			return i + "th";
		default:
			return i + sufixes[i % 10];

		}
	}
	
	//for safely converting the object into string , Long or Timestamp type 
	//Added by ekpreet
	public static String toStringSafe(Object obj) {
		return obj==null?null:obj.toString();
	}
	
	public static Long toLongSafe(Object obj) {
		return obj==null?null:Long.parseLong(obj.toString());
	}

	public static Float toFloatSafe(Object obj) {
		return obj==null?null:Float.parseFloat(obj.toString());
	}

	public static Boolean toBooleanSafe(Object obj) {
		return obj==null?null:Boolean.parseBoolean(obj.toString());
	}

	public static Integer toIntegerSafe(Object obj) {
		return obj==null?null:Integer.parseInt(obj.toString());
	}
	public static Time toTimeSafe(Object obj){
		return obj == null? null: Time.valueOf(obj.toString());
	}
	
	public static Timestamp toTimestampSafe(Object obj) {
		return obj==null?null:Timestamp.valueOf(obj.toString());
	}

	public static Date toDateSafe(Object obj) {
		Date date1 = null;
		if(obj == null){
			return  date1;
		}else{
			try {
				String sDate1 = obj.toString();
				date1 = new SimpleDateFormat("yyyy-MM-dd").parse(sDate1);
			}catch (ParseException e){
				e.printStackTrace();;
			}
		}
		return date1;
	}


	public static String getDayoflife(String dateOfBirth) throws ParseException {
		try {
			long time = System.currentTimeMillis();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-DD");
			Date dob = (Date) formatter.parse(dateOfBirth);
			java.util.Date date = new java.util.Date();
			return String.valueOf((date.getTime() - dob.getTime()));
		}catch (Exception e){
			System.out.println(e);
		}
		return null;
	}

	public static Timestamp getDateTimeFromString(Date date,String time) {
		Date dateOfBirthTemp = date;
		String timeOfBirth =time;

		Timestamp dateOfBirth = null;
		if (!BasicUtils.isEmpty(dateOfBirthTemp)) {
			dateOfBirth=new Timestamp(dateOfBirthTemp.getTime());
			if(!BasicUtils.isEmpty(timeOfBirth)) {
				String[] tobStr = timeOfBirth.split(",");
				if (tobStr[2].equalsIgnoreCase("PM") && Integer.parseInt(tobStr[0]) != 12) {
					dateOfBirth.setHours(12 + Integer.parseInt(tobStr[0]));
				} else if (tobStr[2].equalsIgnoreCase("AM") && Integer.parseInt(tobStr[0]) == 12) {
					dateOfBirth.setHours(0);
				} else {
					dateOfBirth.setHours(Integer.parseInt(tobStr[0]));
				}
				dateOfBirth.setMinutes(Integer.parseInt(tobStr[1]));
			}
		}
		return dateOfBirth;
	}

	public static Timestamp getDateTimeFromTime(Date date, Time time) {
		Date dateOfVisit = date;
		Time timeOfVisit =time;

		System.out.println("Hour :" +timeOfVisit.getHours()+" and Minute :"+timeOfVisit.getMinutes());

		Timestamp dateOfVisitValue = null;
		if (!BasicUtils.isEmpty(dateOfVisit)) {
			dateOfVisitValue=new Timestamp(dateOfVisit.getTime());
			if(!BasicUtils.isEmpty(timeOfVisit)) {
				dateOfVisitValue.setHours(	timeOfVisit.getHours());
				dateOfVisitValue.setMinutes(	timeOfVisit.getMinutes());
			}
		}
		System.out.println("date Of Visit Value:"+dateOfVisitValue);
		System.out.println("date Of Visit Value using the New Timestamp:"+new Timestamp(dateOfVisitValue.getTime()));

		return dateOfVisitValue;
	}

	public static void setAnthropometryTrackerFields(WeightTrackingJSON weightTracking, BabyDetail babyDetail) {

		try {
			Date date = new Date();
			Timestamp originalTodayDate = new Timestamp(date.getTime());

			Date dateOfBirthTemp = babyDetail.getDateofbirth();
			String timeOfBirth = babyDetail.getTimeofbirth();

			if(dateOfBirthTemp!=null) {
				Timestamp dateOfBirth = new Timestamp(dateOfBirthTemp.getTime());

				String[] tobStr = timeOfBirth.split(",");
				if (tobStr[2] == "PM" && Integer.parseInt(tobStr[0]) != 12) {
					dateOfBirth.setHours(12 + Integer.parseInt(tobStr[0]));
				} else if (tobStr[2] == "AM" && Integer.parseInt(tobStr[0]) == 12) {
					dateOfBirth.setHours(0);
				} else {
					dateOfBirth.setHours(Integer.parseInt(tobStr[0]));
				}
				dateOfBirth.setMinutes(Integer.parseInt(tobStr[1]));

				double difference = ((originalTodayDate.getTime()-dateOfBirth.getTime())/86400000)+1;
				int ageOfDays = (int) Math.ceil(difference);

				weightTracking.setDol(String.valueOf(ageOfDays));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getTimeString(String timeStr){
		String[] tobStr = timeStr.split(",");
		return tobStr[0]+":"+tobStr[1]+" "+tobStr[2];
	}


	public static String ConvertToCommaSeperatedString(List<String> list)
	{
		String resultset = "";
		int n = list.size();
		for(int i=0;i<list.size();i++)
		{
			if(i==list.size()-1)
			{
				resultset+=list.get(i);
			}
			else
			{
				resultset+=list.get(i)+",";
			}

		}

		return resultset;

	}

	/*
	* Method to calculate the Maximum value from the arraylist
	* @Params Arraylist of Values
	* @Return Maximum value from the list
	*/
	public static Float maxValueFromList(List<Float> list){
		Float maxValue=Float.MIN_VALUE;
		if(!BasicUtils.isEmpty(list)){
			// find the first value which is not null
			// then iterate over the rest of the array to find the smallest value
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j)!=null && !BasicUtils.isEmpty(list.get(j))) {
					if (list.get(j) >= maxValue) {
						maxValue = list.get(j);
					}
				}
			}
		}
		return maxValue == Float.MIN_VALUE ? null:maxValue;
	}

	/*
	 * Method to calculate the Minimum value from the arraylist
	 * @Params Arraylist of Values
	 * @Return Minimum value from the list
	 */
	public static Float minValueFromList(List<Float> list){
		Float minValue=null;
		if(!BasicUtils.isEmpty(list)){
			// find the first value which is not null
			int i=0;
			while(i<list.size()){
				if(list.get(i)!=null && !BasicUtils.isEmpty(list.get(i))) {
					minValue=list.get(i);
					break;
				}
				i++;
			}

			// then iterate over the rest of the array to find the smallest value
			for (int j = i; j < list.size(); j++) {
				if(list.get(j)!=null && !BasicUtils.isEmpty(list.get(j))) {
					float value=list.get(j);
					if (list.get(j) < minValue)
						minValue = list.get(j);
				}
			}
		}
		return minValue;
	}


	public static float getRoundedValue(double value){
        DecimalFormat df = new DecimalFormat("0.00");
        return Float.parseFloat(df.format(value));
    }


	public static float CalCulateHourDiff(Timestamp creationtime, Timestamp endtime) {
		if(isEmpty(endtime))
		{
			return 0;
		}
		else
		{
			return (endtime.getTime()-creationtime.getTime())/(24*60*60*1000);
		}
	}

	public static Date stringToDate(String date){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parsedDate = dateFormat.parse(date);
			return parsedDate;
		}catch (ParseException e){
			System.out.println(e);
		}
		return null;
	}

	public static Timestamp stringToTimestamp(String date){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date parsedDate = dateFormat.parse(date);
			Timestamp today = new java.sql.Timestamp(parsedDate.getTime());
			return today;
		}catch (ParseException e){
			System.out.println(e);
		}
		return null;
	}

	public static GeneralResponseObject getResonseObject(boolean status,int statusCode,String message,Object data){
		GeneralResponseObject generalResponseObject=new GeneralResponseObject();
		generalResponseObject.setStatus(status);
		generalResponseObject.setStatusCode(statusCode);
		generalResponseObject.setMessage(message);
		generalResponseObject.setData(data);
		return generalResponseObject;
	}

	public static int getOffsetValue(){
		return TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset() - TimeZone.getDefault().getRawOffset();
	}


	public static int getFixedOffsetValue(){
		return BasicConstants.OFFSET_VALUE;
	}

	public static int getTestingOffsetValue(){
		int offsetValue = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
				- TimeZone.getDefault().getRawOffset();

		System.out.println("Check -> Offset Value :"+offsetValue);
		int offset  =  0;
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			System.out.println("System IP Address : " +
					(localhost.getLocalHost()));

			byte[] ipAddr = new byte[] { 127, 0, 0, 1 };
			InetAddress addr = InetAddress.getByAddress("Localhost",ipAddr);

			if (localhost.equals(addr)) {
				System.out.println("same");
				offset = 0;
			} else {
				offset = 19800000;
				System.out.println("not the same");
			}
		}catch (Exception e){
			System.out.println("Caught Exception "+e);
		}
	return offset;
	}

	public static boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
		return !candidate.isBefore(start) && !candidate.isAfter(end);  // Inclusive.
	}
}
