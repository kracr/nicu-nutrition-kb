package com.inicu.postgres.serviceImpl;

import java.io.FileReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.mail.Message.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.ChartExpectancyDataJson;
import com.inicu.models.ChartGraphDataJson;
import com.inicu.models.ChartMasterJson;
import com.inicu.models.GestationObj;
import com.inicu.models.GrowthChartJSON;
import com.inicu.models.GrowthChartMainJSON;
import com.inicu.models.InterGrowthChartExpectancyDataJson;
import com.inicu.models.InterGrowthChartGraphDataJson;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.RefFenton;
import com.inicu.postgres.entities.VwGrowthChat;
import com.inicu.postgres.service.GrowthChatService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.InicuDatabaseExeption;
import com.inicu.postgres.utility.InterpolationUtilities;

import au.com.bytecode.opencsv.CSVReader;

@Repository
public class GrowthChatServiceImp implements GrowthChatService {

	@Autowired
	UserServiceDAO userService;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	InicuDatabaseExeption databaseException;

	@SuppressWarnings("unchecked")
	public ChartMasterJson getGraphData(String uhid) {
		ChartMasterJson masterJson = new ChartMasterJson();
		GrowthChartMainJSON mainJson = new GrowthChartMainJSON();
		List<GrowthChartJSON> json = new ArrayList<GrowthChartJSON>();
		if (!BasicUtils.isEmpty(uhid)) {
			Date doa = new Date();
			Integer doaWeek = 0;
			Integer doaDay = 0;
			Integer week = 0;
			Integer day = 0;
			String query2 = "select b from BabyDetail b where uhid='" + uhid + "'";
			List<BabyDetail> resultset2 = userService.executeQuery(query2);

			for (BabyDetail r : resultset2) {
				if (!BasicUtils.isEmpty(r.getGestationdaysbylmp()))
					doaDay = r.getGestationdaysbylmp();
				if (!BasicUtils.isEmpty(r.getGestationweekbylmp()))
					doaWeek = r.getGestationweekbylmp();
				if (!BasicUtils.isEmpty(r.getDateofadmission()))
					doa = r.getDateofadmission();
			}

			String query = "Select v from VwGrowthChat v where uhid='" + uhid + "'";
			List<VwGrowthChat> resultset = userService.executeQuery(query);

			if (!BasicUtils.isEmpty(resultset)) {
				for (VwGrowthChat v : resultset) {
					GrowthChartJSON j = new GrowthChartJSON();
					Date visit = new Date();
					GestationObj gest = new GestationObj();

					Long timeDiff = 0L;
					Long dayDiff = 0L;
					Float weight = v.getCurrentdateweight();
					if (weight != null && !BasicUtils.isEmpty(weight) && weight != 0.0
							&& !String.valueOf(weight).equalsIgnoreCase("NaN")) {
						j.setWeight(String.valueOf(weight));
					}
					Float height = v.getCurrentdateheight();
					if (height != null && !BasicUtils.isEmpty(height) && height != 0.0
							&& !String.valueOf(height).equalsIgnoreCase("NaN")) {
						j.setHeight(String.valueOf(height));
					}
					Float head = v.getCurrentdateheadcircum();
					if (head != null && !BasicUtils.isEmpty(head) && head != 0.0
							&& !String.valueOf(head).equalsIgnoreCase("NaN")) {
						j.setHeadcircum(String.valueOf(head));
					}
					visit = v.getVisitdate();
					timeDiff = visit.getTime() - doa.getTime();
					dayDiff = timeDiff / (1000 * 60 * 60 * 24);

					System.out.println(dayDiff);
					week = doaWeek;
					day = doaDay;
					if (dayDiff != 0) {
						week = doaWeek + (int) (dayDiff / 7);
						day = doaDay + (int) (dayDiff % 7);
					}
					if (doaDay >= 7) {
						week = week + 1;
						day = doaDay + (day % 7);
					}

					/*
					 * commented on 12-5 by Sourabh creating problem and no sense for the code. if
					 * (day <= 0) { week = week - 1; day = 10 - Math.abs(day); }
					 */

					while (day < 0) {
						week = week - 1;
						day = 7 + day;
					}

					String a = week + "." + day;
					Float b = Float.parseFloat(a);
					if (b >= 0 && day >= 0) { // to disallow negative values..
						String daysInWeek = Double.toString(day / 7.0);
						if (daysInWeek.length() > 4) {
							daysInWeek = daysInWeek.substring(0, 4);
						}
						gest.setDays(day);
						gest.setWeeks(week + Double.parseDouble(daysInWeek));
						j.setGestation(gest);
						j.setGestationIdToSort(b);
						json.add(j);
					}
				}
				// sorting list in asending order..
				Collections.sort(json, new Comparator<GrowthChartJSON>() {
					public int compare(GrowthChartJSON s1, GrowthChartJSON s2) {
						// notice the cast to (Integer) to invoke compareTo
						return ((Integer) s1.getGestationIdToSort().compareTo(s2.getGestationIdToSort()));
					}
				});
			}
			mainJson.setUhid(uhid);
			mainJson.setListGraph(json);
			// mainJson.setListGraph(json1);
			masterJson.setPlotData(mainJson);

			// set graph data

			// fetch gender of the patient.

			// #27-04-2017 @Sourabh Verma Repeated fetch, data from same table.
			/*
			 * //String fetchGender =
			 * "Select gender FROM kalawati.baby_detail WHERE uhid='"+uhid.trim( )+"'";
			 * String fetchGender =
			 * "Select obj FROM BabyDetail obj WHERE uhid='"+uhid.trim()+"'";
			 * List<BabyDetail> result =
			 * userService.getListFromMappedObjNativeQuery(fetchGender);
			 */
			if (!BasicUtils.isEmpty(resultset2)) {
				String gender = resultset2.get(0).getGender() + "";
				if (!BasicUtils.isEmpty(gender)) {
					CSVReader reader;
					if (gender.equalsIgnoreCase("Male")) {
						// fetch male files
						try {

							String weightDir = BasicConstants.WORKING_DIR + "/charts/fenton_boy_weight.csv";
							reader = new CSVReader(new FileReader(weightDir), ',', '"', 0);
							ChartGraphDataJson graphDataJson = new ChartGraphDataJson();
							List<ChartExpectancyDataJson> weightExpecData = new ArrayList<>();
							List<String[]> weightRows = reader.readAll();
							weightExpecData = getDataFromCSV(weightRows);
							graphDataJson.setWeight(weightExpecData);

							List<ChartExpectancyDataJson> lengthExpecData = new ArrayList<>();
							String heightDir = BasicConstants.WORKING_DIR + "/charts/fenton_boy_length.csv";
							reader = new CSVReader(new FileReader(heightDir), ',', '"', 0);
							List<String[]> lengthRows = reader.readAll();
							lengthExpecData = getDataFromCSV(lengthRows);
							graphDataJson.setLength(lengthExpecData);

							List<ChartExpectancyDataJson> cirExpecData = new ArrayList<>();
							List<String[]> cirRows = reader.readAll();
							String headCir = BasicConstants.WORKING_DIR + "/charts/fenton_boy_cir.csv";
							reader = new CSVReader(new FileReader(headCir), ',', '"', 0);
							cirRows = reader.readAll();
							cirExpecData = getDataFromCSV(cirRows);
							graphDataJson.setHeadCircum(cirExpecData);

							masterJson.setGraphData(graphDataJson);

							// Inter-Growth Chart CSV read
							InterGrowthChartGraphDataJson interGrowthGraphDataJson = new InterGrowthChartGraphDataJson();

							List<InterGrowthChartExpectancyDataJson> interGrowthWeightExpecData = new ArrayList<>();
							weightDir = BasicConstants.WORKING_DIR + "/charts/intergrowth_boy_weight.csv";
							reader = new CSVReader(new FileReader(weightDir), ',', '"', 0);
							List<String[]> interGrowthWeightRows = reader.readAll();
							interGrowthWeightExpecData = getInterGrowthChartDataFromCSV(interGrowthWeightRows);
							interGrowthGraphDataJson.setWeight(interGrowthWeightExpecData);

							List<InterGrowthChartExpectancyDataJson> interGrowthLengthExpecData = new ArrayList<>();
							heightDir = BasicConstants.WORKING_DIR + "/charts/intergrowth_boy_length.csv";
							reader = new CSVReader(new FileReader(heightDir), ',', '"', 0);
							List<String[]> interGrowthLengthRows = reader.readAll();
							interGrowthLengthExpecData = getInterGrowthChartDataFromCSV(interGrowthLengthRows);
							interGrowthGraphDataJson.setLength(interGrowthLengthExpecData);

							List<InterGrowthChartExpectancyDataJson> interGrowthCirExpecData = new ArrayList<>();
							List<String[]> interGrowthCirRows = reader.readAll();
							headCir = BasicConstants.WORKING_DIR + "/charts/intergrowth_boy_circum.csv";
							reader = new CSVReader(new FileReader(headCir), ',', '"', 0);
							interGrowthCirRows = reader.readAll();
							interGrowthCirExpecData = getInterGrowthChartDataFromCSV(interGrowthCirRows);
							interGrowthGraphDataJson.setHeadCircum(interGrowthCirExpecData);

							masterJson.setInterGrowthGraphData(interGrowthGraphDataJson);

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {
						// adding details as female
						try {
							String weightDir = BasicConstants.WORKING_DIR + "/charts/fenton_girl_weight.csv";
							reader = new CSVReader(new FileReader(weightDir), ',', '"', 0);
							ChartGraphDataJson graphDataJson = new ChartGraphDataJson();
							List<ChartExpectancyDataJson> weightExpecData = new ArrayList<>();
							List<String[]> weightRows = reader.readAll();
							weightExpecData = getDataFromCSV(weightRows);
							graphDataJson.setWeight(weightExpecData);

							List<ChartExpectancyDataJson> lengthExpecData = new ArrayList<>();
							String heightDir = BasicConstants.WORKING_DIR + "/charts/fenton_girl_length.csv";
							reader = new CSVReader(new FileReader(heightDir), ',', '"', 0);
							List<String[]> lengthRows = reader.readAll();
							lengthExpecData = getDataFromCSV(lengthRows);
							graphDataJson.setLength(lengthExpecData);

							List<ChartExpectancyDataJson> cirExpecData = new ArrayList<>();
							List<String[]> cirRows = reader.readAll();
							String headCir = BasicConstants.WORKING_DIR + "/charts/fenton_girl_cir.csv";
							reader = new CSVReader(new FileReader(headCir), ',', '"', 0);
							cirRows = reader.readAll();
							cirExpecData = getDataFromCSV(cirRows);
							graphDataJson.setHeadCircum(cirExpecData);

							masterJson.setGraphData(graphDataJson);

							// Inter-Growth Chart CSV read
							InterGrowthChartGraphDataJson interGrowthGraphDataJson = new InterGrowthChartGraphDataJson();

							List<InterGrowthChartExpectancyDataJson> interGrowthWeightExpecData = new ArrayList<>();
							weightDir = BasicConstants.WORKING_DIR + "/charts/intergrowth_girl_weight.csv";
							reader = new CSVReader(new FileReader(weightDir), ',', '"', 0);
							List<String[]> interGrowthWeightRows = reader.readAll();
							interGrowthWeightExpecData = getInterGrowthChartDataFromCSV(interGrowthWeightRows);
							interGrowthGraphDataJson.setWeight(interGrowthWeightExpecData);

							List<InterGrowthChartExpectancyDataJson> interGrowthLengthExpecData = new ArrayList<>();
							heightDir = BasicConstants.WORKING_DIR + "/charts/intergrowth_girl_length.csv";
							reader = new CSVReader(new FileReader(heightDir), ',', '"', 0);
							List<String[]> interGrowthLengthRows = reader.readAll();
							interGrowthLengthExpecData = getInterGrowthChartDataFromCSV(interGrowthLengthRows);
							interGrowthGraphDataJson.setLength(interGrowthLengthExpecData);

							List<InterGrowthChartExpectancyDataJson> interGrowthCirExpecData = new ArrayList<>();
							List<String[]> interGrowthCirRows = reader.readAll();
							headCir = BasicConstants.WORKING_DIR + "/charts/intergrowth_girl_circum.csv";
							reader = new CSVReader(new FileReader(headCir), ',', '"', 0);
							interGrowthCirRows = reader.readAll();
							interGrowthCirExpecData = getInterGrowthChartDataFromCSV(interGrowthCirRows);
							interGrowthGraphDataJson.setHeadCircum(interGrowthCirExpecData);

							masterJson.setInterGrowthGraphData(interGrowthGraphDataJson);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return masterJson;
	}

	private List<ChartExpectancyDataJson> getDataFromCSV(List<String[]> rows) {
		List<ChartExpectancyDataJson> listExpecData = new ArrayList<>();
		for (int i = 0; i < rows.size(); i += 5) {
			ChartExpectancyDataJson expecData = new ChartExpectancyDataJson();
			GestationObj gestObj = new GestationObj();
			gestObj.setWeeks(rows.get(i)[0].toString());
			expecData.setFifty(rows.get(i + 2)[2].toString());
			expecData.setGestation(gestObj);
			expecData.setNinety(rows.get(i + 3)[2].toString());
			expecData.setNinetySeven(rows.get(i + 4)[2].toString());
			expecData.setTen(rows.get(i + 1)[2].toString());
			expecData.setThree(rows.get(i)[2].toString());
			listExpecData.add(expecData);
		}
		return listExpecData;
	}

	private List<InterGrowthChartExpectancyDataJson> getInterGrowthChartDataFromCSV(List<String[]> rows) {
		List<InterGrowthChartExpectancyDataJson> listExpecData = new ArrayList<>();
		for (int i = 0; i < rows.size(); i++) {
			InterGrowthChartExpectancyDataJson expecData = new InterGrowthChartExpectancyDataJson();
			String[] rowObj = rows.get(i);

			GestationObj gestObj = new GestationObj();

			// days will be in decimal (no of day/7)
			int weeks = Integer.parseInt(rowObj[0].substring(0, rowObj[0].indexOf(".")));
			int days = Integer.parseInt(rowObj[0].substring(rowObj[0].indexOf(".") + 1));

			String daysInWeek = Double.toString(days / 7.0);
			if (daysInWeek.length() > 4) {
				daysInWeek = daysInWeek.substring(0, 4);
			}

			gestObj.setWeeks(weeks + Double.parseDouble(daysInWeek));
			gestObj.setDays(days);
			expecData.setGestation(gestObj);

			expecData.setThree(rowObj[1].toString());
			expecData.setFive(rowObj[2].toString());
			expecData.setTen(rowObj[3].toString());
			expecData.setFifty(rowObj[4].toString());
			expecData.setNinety(rowObj[5].toString());
			expecData.setNinetyFive(rowObj[6].toString());
			expecData.setNinetySeven(rowObj[7].toString());

			listExpecData.add(expecData);
		}
		return listExpecData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double getFentonCentile(String gender, String gestWeek, String paramType, double paramValue) {

		try {
			int i = 0;
			double[] dataX = new double[5];
			double[] dataY = new double[5];
			DecimalFormat df = new DecimalFormat("#.##");
			InterpolationUtilities interpolationObj = new InterpolationUtilities();
	
			if (paramType.equalsIgnoreCase("weight")) {
				paramValue /= 1000;
			}
	
			String query = "Select v from RefFenton v where gender='" + gender + "' and age='" + gestWeek + "'";
			List<RefFenton> resultset = inicuDao.getListFromMappedObjQuery(query);
	
			if (!BasicUtils.isEmpty(resultset)) {
				Iterator<RefFenton> itr = resultset.iterator();
				while (itr.hasNext()) {
					RefFenton obj = itr.next();
					// dataY[i] = Double.parseDouble(obj.getPercentile());
					dataY[i] = obj.getPercentile();
					if (paramType.equalsIgnoreCase("weight")) {
						dataX[i] = Double.parseDouble(obj.getWeight().toString());
					} else if (paramType.equalsIgnoreCase("length")) {
						dataX[i] = Double.parseDouble(obj.getHeight().toString());
					} else if (paramType.equalsIgnoreCase("head")) {
						dataX[i] = Double.parseDouble(obj.getHeadcircum().toString());
					}
	
					if (dataY[i] == 3 && dataX[i] > paramValue) {
						return 2;
					} else if (dataY[i] == 97 && dataX[i] < paramValue) {
						return 100;
					}
					i++;
				}
			}
			return Double.valueOf(df.format(interpolationObj.poly_interpolate(dataX, dataY, paramValue, 3)));
		}
		catch (Exception e) {
			e.printStackTrace();
			String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
			return 0f;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public String updateCentileDetails() {
		long startTime = System.currentTimeMillis();
		String uhidNotDone = "";

		// logic here
		List<BabyDetail> resultset = inicuDao.getListFromMappedObjQuery("SELECT obj from BabyDetail as obj");
		if (!BasicUtils.isEmpty(resultset)) {
			for (BabyDetail babyObj : resultset) {
				try {
					if (BasicUtils.isEmpty(babyObj.getGender()) || BasicUtils.isEmpty(babyObj.getGestationweekbylmp())
							|| babyObj.getGestationweekbylmp() < 23 || babyObj.getGestationweekbylmp() > 45) {
						uhidNotDone += babyObj.getUhid() + ", ";
						continue;
					}
					String gender = babyObj.getGender().toLowerCase();
					String gestWeek = Integer.toString(babyObj.getGestationweekbylmp());
					Double response = null;
					String level = "";

					if (!BasicUtils.isEmpty(babyObj.getBirthweight())
							&& BasicUtils.isEmpty(babyObj.getWeight_centile())) {
						response = getFentonCentile(gender, gestWeek, "weight", babyObj.getBirthweight());

						if (!BasicUtils.isEmpty(response)) {
							if (response < 10) {
								level = "SGA";
							} else if (response < 97) {
								level = "AGA";
							} else {
								level = "LGA";
							}

							babyObj.setWeight_centile(response.floatValue());
							babyObj.setWeight_galevel(level);
						}
					}

					if (!BasicUtils.isEmpty(babyObj.getBirthlength())
							&& BasicUtils.isEmpty(babyObj.getLength_centile())) {
						response = getFentonCentile(gender, gestWeek, "length", babyObj.getBirthlength());

						if (!BasicUtils.isEmpty(response)) {
							if (response < 10) {
								level = "SGA";
							} else if (response < 97) {
								level = "AGA";
							} else {
								level = "LGA";
							}

							babyObj.setLength_centile(response.floatValue());
							babyObj.setLength_galevel(level);
						}
					}

					if (!BasicUtils.isEmpty(babyObj.getBirthheadcircumference())
							&& BasicUtils.isEmpty(babyObj.getHc_centile())) {
						response = getFentonCentile(gender, gestWeek, "head", babyObj.getBirthheadcircumference());

						if (!BasicUtils.isEmpty(response)) {
							if (response < 10) {
								level = "SGA";
							} else if (response < 97) {
								level = "AGA";
							} else {
								level = "LGA";
							}

							babyObj.setHc_centile(response.floatValue());
							babyObj.setHc_galevel(level);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
					databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "admin",
							babyObj.getUhid(), "update centile", BasicUtils.convertErrorStacktoString(e));
				}
			}

			try {
				// updating/saving all calculated details in DB
				inicuDao.saveMultipleObject(resultset);
			} catch (Exception e) {
				e.printStackTrace();
				String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
				databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "admin", "",
						"update centile", BasicUtils.convertErrorStacktoString(e));
			}
		}

		return "Problematic UHIDs: " + uhidNotDone + " Time taken: " + (System.currentTimeMillis() - startTime) / 1000
				+ " secs";
	}

}
