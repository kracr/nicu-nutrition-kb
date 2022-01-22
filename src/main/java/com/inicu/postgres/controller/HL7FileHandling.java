package com.inicu.postgres.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.KeyValueObj;
import com.inicu.models.PatientInfoAddChildObj;
import com.inicu.models.PatientInfoAdmissonFormObj;
import com.inicu.models.PatientInfoChildDetailsObj;
import com.inicu.models.PersonalDetailsObj;
import com.inicu.models.TimeObj;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class HL7FileHandling extends Thread {

	@Autowired
	private InicuDatabaseExeption databaseException;

	@Autowired
	PatientService patienSerive;

	@Override
	public void run() {

		boolean running = true;
		while (running) {
			try {
				readiNICURecords();
				Thread.sleep(30000);
			} catch (IOException | ParseException | InicuDatabaseExeption | InterruptedException e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID,
						BasicConstants.LOGGED_USER_ID, "", "HL7FileHandling", BasicUtils.convertErrorStacktoString(e));
			}
		}
	}

	public void readiNICURecords() throws IOException, ParseException, InicuDatabaseExeption {

		String directoryName = "";
		boolean isFilesUploaded = false;
		if (!System.getProperty("os.name").startsWith("Windows")) {
			directoryName = "/";

		} else {
			System.out.println("For  Testing Purpose----->");
			directoryName = "D:\\iNICU-DATA-DIR\\iNICURecords";
			readRecordsDirectory(directoryName);

		}
	}

	private Object readRecordsDirectory(String directoryName)
			throws IOException, ParseException, InicuDatabaseExeption {

		File directory = new File(directoryName);
		System.out.println("---------directory---------" + directory.getAbsolutePath());

		File[] direcotryList = directory.listFiles(); // gives list of
														// directories inside
														// iNicuRecords
														// directory
		File[] arrayOfDirecotories;
		int lengthOfDirecotories = (arrayOfDirecotories = direcotryList).length;
		for (int i = 0; i < lengthOfDirecotories; i++) {
			File file = arrayOfDirecotories[i];
			if (file.isDirectory() && file.getName().equalsIgnoreCase(BasicConstants.HL7RECORDS_DIRECTORY_NAME)) {

				System.out.println("Directory HL7 absolute path " + file.getAbsolutePath());
				readHL7RecordsDirecotry(file);

			} else if (file.isDirectory()
					&& file.getName().equalsIgnoreCase(BasicConstants.CSVRecords_DIRECTORY_NAME)) { // reading
																									// csv
																									// files...

				System.out.println("Directory CSV absolute path " + file.getAbsolutePath());
				readCSVRecordsDirectory(file);

			}
		}

		return directory;

	}

	private void readCSVRecordsDirectory(File file) throws IOException, ParseException, InicuDatabaseExeption {
		File directoryCSV = new File(file.getAbsolutePath());
		File[] fListCSV = directoryCSV.listFiles(); // listing .hl7 files
		File[] arrayOfFileCSV;
		int lengthCSV = (arrayOfFileCSV = fListCSV).length;
		for (int csvIndex = 0; csvIndex < lengthCSV; csvIndex++) {
			File fileCSV = arrayOfFileCSV[csvIndex];
			if (fileCSV.isFile()) {
				System.out.println("**************csv File Name***********" + fileCSV.getName());
				readCSVRecord(fileCSV);
			} else {
				System.out.println("Directory found inside CSVRecords Direcotory" + fileCSV.getName());
			}
		}

	}

	private void readCSVRecord(File fileCSV) throws IOException, ParseException, InicuDatabaseExeption {

		FileReader readere = null;
		BufferedReader br = null;
		try {
			readere = new FileReader(fileCSV);
			br = new BufferedReader(readere);
			String line = "";
			int recordNo = 1;
			while ((line = br.readLine()) != null) {
				System.out.println("CSV Record No. :- " + recordNo + ": " + line);
				recordNo = recordNo + 1;
				// line.replace("| |", "|NA|");
				line = line.replace(",,", ",NA,").replace(", ,", ",NA,");
				String[] csvBabyDetails = line.split(",");
				if (csvBabyDetails.length == 11 || csvBabyDetails.length == 12) {
					String uhid = csvBabyDetails[0];
					String babyName = "B/O " + csvBabyDetails[2];
					String motherName = csvBabyDetails[2] + csvBabyDetails[1];
					String fatherName = "";
					String dobStr = csvBabyDetails[3]; // 20150921

					String doaStr = csvBabyDetails[9];
					String gender = csvBabyDetails[4];
					String mobileNo = csvBabyDetails[5];
					String ipOp = csvBabyDetails[6];
					String doctorName = csvBabyDetails[7];
					String dischargeStatus = csvBabyDetails[8];

					String timeOfAdmtnStr = csvBabyDetails[10];
					String criticality = "";
					if (csvBabyDetails.length == 12) {
						criticality = csvBabyDetails[11];
					}

					admitPatientToiNICU(uhid, babyName, motherName, fatherName, criticality, gender, mobileNo, ipOp,
							doctorName, dischargeStatus, timeOfAdmtnStr, dobStr, doaStr,
							BasicConstants.CSVRecords_DIRECTORY_NAME);

				}
			}
			br.close();
			readere.close();
			moveRecordFileToTrash(fileCSV);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		} catch (InicuDatabaseExeption e) {
			e.printStackTrace();
			throw new InicuDatabaseExeption();
		}

		finally {
			br.close();
			readere.close();
		}
	}

	private void readHL7RecordsDirecotry(File file) throws IOException, ParseException, InicuDatabaseExeption {

		File directoryHL7 = new File(file.getAbsolutePath());
		File[] fListHL7 = directoryHL7.listFiles(); // listing .hl7 files
		File[] arrayOfFileHL7;
		int lengthHl7 = (arrayOfFileHL7 = fListHL7).length;
		for (int hl7Index = 0; hl7Index < lengthHl7; hl7Index++) {
			File fileHl7 = arrayOfFileHL7[hl7Index];
			if (fileHl7.isFile()) {
				System.out.println("**************hl7 File Name***********" + fileHl7.getName());
				readHL7Record(fileHl7);
			} else {
				System.out.println("Direcotry found inside HL7Records Directory.." + fileHl7.getName());
			}
		}
	}

	private void readHL7Record(File fileHl7) throws IOException, ParseException, InicuDatabaseExeption {

		FileInputStream fstream = null;
		BufferedReader br = null;
		try {
			fstream = new FileInputStream(fileHl7);

			br = new BufferedReader(new InputStreamReader(fstream));

			String line = "";

			while ((line = br.readLine()) != null) {

				// for(int i=0;i<line.length();i++)

				line = line.replace("||", "|NA|").replace("| |", "|NA|");

				StringTokenizer tokens = new StringTokenizer(line, "|");
				List<String> hl7Data = new ArrayList<String>();
				int index = 0;
				while (tokens.hasMoreTokens()) {
					String nextToken = tokens.nextToken("|").trim();
					hl7Data.add(nextToken);
				}
				if (hl7Data.size() == 12 || hl7Data.size() == 13) {
					String[] hl7BabyDetail = hl7Data.toArray(new String[hl7Data.size()]);

					String uhid = hl7BabyDetail[1];
					String babyName = "B/O " + hl7BabyDetail[2];
					String motherName = hl7BabyDetail[2];
					String fatherName = hl7BabyDetail[3];
					String criticality = "";
					if (hl7BabyDetail.length == 13)
						criticality = hl7BabyDetail[12];
					String gender = hl7BabyDetail[5];
					String mobileNo = hl7BabyDetail[6];
					String ipOp = hl7BabyDetail[7];
					String doctorName = hl7BabyDetail[8];
					String dischargeStatus = hl7BabyDetail[9];
					String timeOfAdmtnStr = hl7BabyDetail[11];

					String dobStr = hl7BabyDetail[4];

					String doaStr = hl7BabyDetail[10];

					admitPatientToiNICU(uhid, babyName, motherName, fatherName, criticality, gender, mobileNo, ipOp,
							doctorName, dischargeStatus, timeOfAdmtnStr, dobStr, doaStr,
							BasicConstants.HL7RECORDS_DIRECTORY_NAME);
				}

			}
			br.close();
			fstream.close();
			// move file to trash foleder.......
			moveRecordFileToTrash(fileHl7);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException();
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ParseException(e.getMessage(), 0);
		} catch (InicuDatabaseExeption e) {
			e.printStackTrace();
			throw new InicuDatabaseExeption();
		}

		finally {
			br.close();
			fstream.close();
		}

	}

	private void admitPatientToiNICU(String uhid, String babyName, String motherName, String fatherName,
			String criticality, String gender, String mobileNo, String ipOp, String doctorName, String dischargeStatus,
			String timeOfAdmtnStr, String dobStr, String doaStr, String whichFile)
			throws ParseException, InicuDatabaseExeption {

		// mapping here array index value to corresponding table field.
		PatientInfoAdmissonFormObj admissionForm = new PatientInfoAdmissonFormObj();
		PatientInfoAddChildObj addChild = new PatientInfoAddChildObj();
		PatientInfoChildDetailsObj childDetails = new PatientInfoChildDetailsObj();
		PersonalDetailsObj personalDetails = new PersonalDetailsObj();

		// check for record data must not be missed.....
		if (uhid != null && !uhid.isEmpty() && !uhid.equalsIgnoreCase("NULL") && dobStr != null && !dobStr.isEmpty()
				&& !dobStr.equalsIgnoreCase("NULL")) {

			if (doaStr != null && !doaStr.equalsIgnoreCase("NULL") && !doaStr.isEmpty() && doaStr.length() >= 8) {
				String doa = doaStr.substring(0, 4) + "-" + doaStr.substring(4, 6) + "-" + doaStr.substring(6, 8);
				admissionForm.setDateOfAdmission(doa + "T00:00:00.000Z");// change
																			// str
																			// to
																			// date
			}
			if (dobStr != null && !dobStr.equalsIgnoreCase("NULL") && !dobStr.isEmpty() && dobStr.length() >= 8) {
				String dob = dobStr.substring(0, 4) + "-" + dobStr.substring(4, 6) + "-" + dobStr.substring(6, 8);
				admissionForm.setDateOfBirth(dob + "T00:00:00.000Z");
			}
			// putting data as admission form basic details form...
			admissionForm.setUhid(uhid);
			admissionForm.setBabyName(babyName);
			admissionForm.setIpopStatus(ipOp);
			// personal details
			personalDetails.setMothersName(motherName);
			personalDetails.setFathersName(fatherName);
			personalDetails.setMobileNumber(mobileNo);

			admissionForm.setPersonalDetails(personalDetails);

			// putting data in addChild admission form
			// nothing to put here
			// putting data in child details form..

			// childDetails.setResidentDoctor(doctorName);
			// childDetails.setSex(gender);
			if (dischargeStatus.contains("Admitted")) { // handle time of
														// admission ..no
														// criticality here
				// childDetails.setIsDischarge(false);
				String[] timeArray = timeOfAdmtnStr.split(":");
				String hours = timeArray[0];
				String min = timeArray[1];
				String sec = timeArray[2];
				TimeObj admTime = new TimeObj();
				admTime.setHours(hours);
				admTime.setMinutes(min);
				admTime.setSeconds(sec);

				if (Integer.parseInt(hours) < 13) {
					admTime.setMeridium("AM");
				} else {
					admTime.setMeridium("PM");
				}
				// admissionForm.setTimeOfAdmission(admTime);

			} else if (dischargeStatus.contains("Discharged")) { // handle time
																	// and
																	// criticality
				// childDetails.setIsDischarge(true);
				String hours = timeOfAdmtnStr.substring(0, 1);
				String min = timeOfAdmtnStr.substring(2, 3);
				String sec = timeOfAdmtnStr.substring(4, 5);
				TimeObj admTime = new TimeObj();
				admTime.setHours(hours);
				admTime.setMinutes(min);
				admTime.setSeconds(sec);
				if (Integer.parseInt(hours) < 13) {
					admTime.setMeridium("AM");
				} else {
					admTime.setMeridium("PM");
				}
				// admissionForm.setTimeOfAdmission(admTime);
				//
				KeyValueObj criticalityObj = new KeyValueObj();
				criticalityObj.setKey("default");
				criticalityObj.setValue(criticality);
				admissionForm.setCriticality(criticalityObj);
			}

			patienSerive.AdmitPatient(admissionForm, addChild, childDetails, doctorName, "admissionForm");
		} else {
			System.out.println("**************" + uhid + " is not a proper hl7 record");
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			BasicUtils.sendMail(receiverArray, Message.RecipientType.TO,
					"from file " + whichFile + " for uhid " + uhid + " data is not proper.",
					"Invalid record on hl7 Records", "");
		}
	}

	private void moveRecordFileToTrash(File file) {

		if (!System.getProperty("os.name").startsWith("Windows")) {
			// fileHl7.renameTo(new
			// File("/test/"+HealthRecordConstants.DRWADHWA_TRASH_FOLDER_NAME+"/"+fileHl7.getName().replace(".txt",
			// "_")+result+".txt"));
		} else {
			System.out.println("For  Testing Purpose----->");

			java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
			String fileName = file.getName();
			int index = fileName.indexOf(".");
			int length = (int) fileName.length();
			String temp = fileName.substring(0, index) + "_" + sqlDate + "_"
					+ BasicConstants.dateFormat24Time.format(sqlDate).replace(":", "_") + "."
					+ fileName.substring(index + 1, length);
			File targetFile = new File(file.getParentFile().getParentFile().getPath() + "\\"
					+ BasicConstants.HL7RECORDS_TRASH_DIRECTORY_NAME + "\\" + temp);
			file.renameTo(targetFile);
			System.out.println("File is moved to trash successful!");

		}
	}
}
