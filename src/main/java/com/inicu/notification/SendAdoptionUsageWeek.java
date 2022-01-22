package com.inicu.notification;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.Message.RecipientType;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.inicu.models.AnalyticsUsagePojo;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.UserServiceDAO;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.NotificationEmail;
import com.inicu.postgres.serviceImpl.AnalyticsServiceImpl;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;
import com.inicu.postgres.utility.InicuDatabaseExeption;

@Repository
public class SendAdoptionUsageWeek implements Runnable {

	@Autowired
	AnalyticsServiceImpl analyticsObj;

	@Autowired
	InicuDao inicuDao;

	@Autowired
	InicuDatabaseExeption databaseException;
	
	@Autowired
	UserServiceDAO userServiceDao;

	public SendAdoptionUsageWeek() {
		super();
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void run() {
		System.out.println("in SendAdoptionUsageWeek");
		List<String> branchNameList = userServiceDao.executeQuery(HqlSqlQueryConstants.getBranchList());
		if(!BasicUtils.isEmpty(branchNameList)) {
			for(String branchName : branchNameList) {
	
				List<NotificationEmail> emailList = inicuDao
						.getListFromMappedObjQuery(HqlSqlQueryConstants.getNotificationEmailList() + " where isactive ='true' and branchname = '" + branchName + "'");
				if (!BasicUtils.isEmpty(emailList)) {
					try {
		
						// will configure time-zone with DB
						Date current = new Date();
						current.setHours(9);
						current.setMinutes(0);
						int offset = (TimeZone.getTimeZone("GMT+5:30").getRawOffset() - TimeZone.getDefault().getRawOffset());
						long fromTime = current.getTime() + offset;
		
						String fromDateStr = "" + (fromTime - BasicConstants.WEEK_VALUE);
						String toDateStr = "" + (fromTime);
		
						HashMap<String, LinkedHashMap<Timestamp, Double>> usageList = analyticsObj
								.getAnalyticsUsageGraph(fromDateStr, toDateStr, branchName);
						
						if (!BasicUtils.isEmpty(usageList)) {
							
							LinkedHashMap<Timestamp, Double> nurseUsageList = usageList.get("nurse");
							LinkedHashMap<Timestamp, Double> doctorUsageList = usageList.get("doctor");
		//					System.out.println("nurseUsageList===" + nurseUsageList.size());
		//					System.out.println("doctorUsageList===" + doctorUsageList.size());
							double[] nursingData = new double[nurseUsageList.size()];
							double[] doctorData = new double[nurseUsageList.size()];
							//show in x axis day and date i.e. Mon 26/3
							String[] columnData = new String[nurseUsageList.size()];
							int i = 0;
							for (Entry<Timestamp, Double> entry : nurseUsageList.entrySet()) {
								Timestamp key = entry.getKey();
								Double value = entry.getValue();
								SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEE");
								//Monday is 1 and Sunday is 7						
								String columnKey = simpleDateformat.format(key);							
								columnData[i] = columnKey+ " "+ (key.getDate()) + "/" + (key.getMonth()+1);
								nursingData[i] = value;
								i++;
								System.out.println("---------key--"+key+"--value"+value+"--------");
								// now work with key and value...
							}
							i = 0;
							for (Entry<Timestamp, Double> entry : doctorUsageList.entrySet()) {
								Timestamp key = entry.getKey();
								Double value = entry.getValue();
								doctorData[i] = value;
								i++;
								// now work with key and value...
							}
							
							Timestamp fromDate = new Timestamp(Long.parseLong(fromDateStr));
							Timestamp toDate = new Timestamp(Long.parseLong(toDateStr));
							
							Timestamp tempToDate = new Timestamp(fromDate.getTime() + 24 * 60 * 60 * 1000);
							
//							List<BabyDetail> uhidList = inicuDao
//									.getListFromMappedObjQuery(HqlSqlQueryConstants.getUsageBabyList(fromDate, tempToDate, branchName));
							String query = "SELECT b FROM BabyDetail b where dischargeddate =null and admissionstatus ='true' and activestatus = 'true' and branchname = '" + branchName + "'";
							List<BabyDetail> uhidList = inicuDao.getListFromMappedObjQuery(query);
							
							
							ChartRenderingInfo info = null;
							DefaultCategoryDataset nursingDataset = new DefaultCategoryDataset();
							DefaultCategoryDataset doctorDataset = new DefaultCategoryDataset();
							for (int j = 0; j < nursingData.length; j++) {
								nursingDataset.setValue(nursingData[j], "R1", columnData[j]);
								doctorDataset.setValue(doctorData[j], "R1", columnData[j]);
							}
							JFreeChart nursingChart = null;
							JFreeChart doctorChart = null;
							if(!BasicUtils.isEmpty(uhidList)) {
			
							info = new ChartRenderingInfo(new StandardEntityCollection());
							 nursingChart = ChartFactory.createLineChart("Nurse Adoption Chart", "Week", "Adoption Score",
									nursingDataset, PlotOrientation.VERTICAL, true, true, true);
							 doctorChart = ChartFactory.createLineChart("Doctor Adoption Chart", "Week", "Adoption Score",
									doctorDataset, PlotOrientation.VERTICAL, true, true, true);	
							}else {
								info = new ChartRenderingInfo(new StandardEntityCollection());
								 nursingChart = ChartFactory.createLineChart("Nurse Adoption Chart", "Week", "Adoption Score",
										null, PlotOrientation.VERTICAL, true, true, true);
								 doctorChart = ChartFactory.createLineChart("Doctor Adoption Chart", "Week", "Adoption Score",
										null, PlotOrientation.VERTICAL, true, true, true);	
							}
							BufferedImage nursingChartImage = nursingChart.createBufferedImage(640, 400, info);
							info = new ChartRenderingInfo(new StandardEntityCollection());
							BufferedImage doctorChartImage = doctorChart.createBufferedImage(640, 400, info);
							
							
		
							String mailContent = "Please find attached weekly adoption charts for doctor and nurses"
									+ "\n "
									+ "Regards"
									+ "\n iNICU Team";
							HashMap<Message.RecipientType, List<String>> emailMap = new HashMap<Message.RecipientType, List<String>>() {
								private static final long serialVersionUID = 1L;
								{
									put(Message.RecipientType.TO, new ArrayList<String>());
									put(Message.RecipientType.CC, new ArrayList<String>());
								}
							};
		
							for (NotificationEmail item : emailList) {
								if (item.getEmail_type()) {
									emailMap.get(Message.RecipientType.TO).add(item.getUser_email());
								} else {
									emailMap.get(Message.RecipientType.CC).add(item.getUser_email());
								}
							}
							List<BufferedImage> listOfAttachments = new ArrayList<>();
							listOfAttachments.add(nursingChartImage);
							listOfAttachments.add(doctorChartImage);
							
							
							String fullHospitalName = BasicConstants.COMPANY_ID + " ( " + branchName + " )";
							
							BasicUtils.sendMailWithMultipleTypeWithAttachment(emailMap, mailContent, "Weekly Adoption Usage Report",
									fullHospitalName, listOfAttachments);
		
						}
					} catch (Exception e) {
						e.printStackTrace();
						String[] receiverArray = { BasicConstants.MAIL_ID_RECIEVER };
						databaseException.newException(receiverArray, RecipientType.TO, BasicConstants.COMPANY_ID, "", "",
								"Sending scheduled Usage Sheet Mail", BasicUtils.convertErrorStacktoString(e));
					}
				}
			}
		}
	}

}
