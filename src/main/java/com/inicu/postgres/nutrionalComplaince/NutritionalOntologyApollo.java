package com.inicu.postgres.nutrionalComplaince;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.inicu.postgres.entities.OralfeedDetail;

import org.apache.http.client.cache.Resource;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.FBRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasonerFactory;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.inicu.his.data.acquisition.LabReceiver;
import com.inicu.models.CaclulatorDeficitPOJO;
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.NotesDAO;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.AntenatalHistoryDetail;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BabyfeedDetail;
import com.inicu.postgres.entities.BirthToNicu;
import com.inicu.postgres.entities.DeviceBloodgasDetailDetail;
import com.inicu.postgres.entities.NursingBloodGas;
import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.NursingIntakeOutput;
import com.inicu.postgres.entities.NursingVitalparameter;
import com.inicu.postgres.entities.RefNutritioncalculator;
import com.inicu.postgres.entities.SaFeedIntolerance;
import com.inicu.postgres.entities.SaNec;
import com.inicu.postgres.entities.SaRespRds;
import com.inicu.postgres.entities.SaSepsis;
import com.inicu.postgres.entities.SaShock;
import com.inicu.postgres.entities.TestItemResult;
import com.inicu.postgres.service.PatientService;
import com.inicu.postgres.service.TestsService;
import com.inicu.postgres.utility.BasicConstants;
import com.inicu.postgres.utility.BasicUtils;
import com.inicu.postgres.utility.HqlSqlQueryConstants;

import antlr.StringUtils;
import scala.Int;

import org.apache.jena.query.QuerySolution;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */

@Repository
public class NutritionalOntologyApollo extends Thread {

	static final String inputFileName = "NutritionOntology15Jan.owl";
	static Property hasGestationalAgeAtBirth;
	static Property hasFeedIntolerance;
	static Property hasDayOfLife;

	static Property hasInitialFeedingAdvancement;
	static Property hasInitialFeedingVolume;
	static Property hasFinalFeedingAdvancement;
	static Property hasFinalFeedingVolume;

	static Property hasRiskFactor;
	static Property givenFeedVolume;
	static Individual ins;
	static Individual unique;
	static Property hasBaseDeficitValue;//
	static Property hasCordGaspHValue; //
	static Property pressorGiven;//
	static Property hasPNInitialFeedingVolumeOfCHO;
	static Property hasPNFinalFeedingVolumeOfCHO;
	static Property hasPNInitialFeedingAdvancementOfCHO;
	static Property hasPNFinalFeedingAdvancementOfCHO;
	static Property hasPNInitialFeedingVolumeOfProtein;
	static Property hasPNFinalFeedingVolumeOfProtein;
	
	String trackrules;
	static Property hasPNInitialFeedingVolumeOfSodium;
	static Property hasPNFinalFeedingVolumeOfSodium;
	
	static Property hasPNInitialFeedingAdvancementOfPotassium;
	static Property hasPNFinalFeedingAdvancementOfPotassium;
	static Property hasPNInitialFeedingVolumeOfPotassium;
	static Property hasPNFinalFeedingVolumeOfPotassium;
	
	static Property hasPNFeedingVolumeOfCalcium;


	static Property hasParenteralTotalVolume;
	static Property hasParenteralFeed;
	static Property hasPNFeed;;
	static Property hasReversedEndBloodFlow;
	static Property hasFinalEnergyRequirement;

	static Property hasInitialEnergyRequirement;
	static Property hasAbsentEndDiastolicFlow;
	static Property hasResuscitation;
	static Property hasIncreasedLactate;
	static Property hasDiseaseNecrotizingEnterocolitis;
	static Property isStoolPassed;

	
	int diffInDays;


	//static Property hasNecrotizingEnterocolitisStage;

	static long day[];
	@SuppressWarnings("deprecation")

	@Autowired
	PatientDao patientDao;

	@Autowired
	PatientService patientService;

	@Autowired
	TestsService testsService;

	@Autowired
	InicuDao inicuDoa;
	@Autowired
	NotesDAO notesDoa;


	@Autowired
	SettingDao settingDao;

	private static final Logger logger = LoggerFactory.getLogger(LabReceiver.class);
	
	//This is for feed given code
	//*****************************************************************************************
	public HashMap<String,Float> getDeficitFeedCalculatorInput(String uhid, BabyfeedDetail feedList,
 			List<RefNutritioncalculator> nutritionList, Float currentWeight, Timestamp fromDateOffsetTwentFourHour, Timestamp toDateOffsetTwentFourHour, String type) {

		HashMap<String,Float> map = new HashMap<String, Float>();
 		List<NursingIntakeOutput> nursingIntakeOutputList = inicuDoa.getListFromMappedObjQuery(
 				HqlSqlQueryConstants.getNursingIntakeOutputList(uhid, fromDateOffsetTwentFourHour, toDateOffsetTwentFourHour));

 		CaclulatorDeficitPOJO calculator = new CaclulatorDeficitPOJO();
 		String oralFeed = "select obj from NursingIntakeOutput obj where uhid = '"  + uhid  + "' and entry_timestamp >= '"  + fromDateOffsetTwentFourHour  +
 				"' and entry_timestamp <= '"  + toDateOffsetTwentFourHour  + "' order by entry_timestamp desc, creationtime desc";
 		List<NursingIntakeOutput> oralFeedList = notesDoa.getListFromMappedObjNativeQuery(oralFeed);

 		String babyDetail = "Select obj from BabyDetail obj where uhid = '"  + uhid  + "' order by creationtime";
 		List<BabyDetail> babyDetailList = notesDoa.getListFromMappedObjNativeQuery(babyDetail);
        float feedgiven=0;
		HashMap<String, Float> enteral = new HashMap<String, Float>();

 		if (!BasicUtils.isEmpty(oralFeedList)) {
 			for (NursingIntakeOutput oral : oralFeedList) {
 				if(!BasicUtils.isEmpty(oral.getPrimaryFeedValue())) {
 					feedgiven += oral.getPrimaryFeedValue();
 				}
 				if(!BasicUtils.isEmpty(oral.getFormulaValue())) {
 					feedgiven += oral.getFormulaValue();
 				}
 				for (RefNutritioncalculator nutrition : nutritionList) {

 					if (!BasicUtils.isEmpty(oral.getPrimaryFeedType()) && oral.getPrimaryFeedType().equalsIgnoreCase(nutrition.getFeedtypeId()) && !BasicUtils.isEmpty(oral.getPrimaryFeedValue())) {
 						
							if (enteral.get(BasicConstants.ENERGY) != null) {
								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
										 + ((oral.getPrimaryFeedValue() * nutrition.getEnergy()) / 100));
							} else {
								enteral.put(BasicConstants.ENERGY,
										oral.getPrimaryFeedValue() * nutrition.getEnergy() / 100);
							}

 					}
 					
 					if (!BasicUtils.isEmpty(oral.getFormulaType()) && oral.getFormulaType().equalsIgnoreCase(nutrition.getFeedtypeId()) && !BasicUtils.isEmpty(oral.getFormulaValue())) {
 						
							if (enteral.get(BasicConstants.ENERGY) != null) {
								enteral.put(BasicConstants.ENERGY, enteral.get(BasicConstants.ENERGY)
										 + ((oral.getFormulaValue() * nutrition.getEnergy()) / 100));
							} else {
								enteral.put(BasicConstants.ENERGY,
										oral.getFormulaValue() * nutrition.getEnergy() / 100);
							}

 					}
 				}
 			}
 		}
 		
 		map.put("feed", (float)Math.round(feedgiven/currentWeight*100)/100);
 		float duration = (toDateOffsetTwentFourHour.getTime() - fromDateOffsetTwentFourHour.getTime()) / (1000 * 60 * 60);
 		
 		float stay = duration / 24;
 		
 		if(stay >= 1) {
 			stay = 1;
 		}
 		HashMap<String, Float> parental = new HashMap<String, Float>();
 		if(type.equalsIgnoreCase("order")) {
	 		if (!BasicUtils.isEmpty(feedList)) {
	 			BabyfeedDetail FeedParental = feedList;
	 			
	 			if((!BasicUtils.isEmpty(FeedParental.getGirvalue()) && Float.valueOf(FeedParental.getGirvalue()) > 0) || (!BasicUtils.isEmpty(FeedParental.getTotalparenteralvolume()) && FeedParental.getTotalparenteralvolume() > 0)) {
	 				calculator.getEnteralIntake().put(BasicConstants.IRON, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.CALCIUM, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.VITAMINd, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.VITAMINa, (float)0);
	 				calculator.getEnteralIntake().put(BasicConstants.PHOSPHORUS, (float)0);
	 			}
	
	 			if (currentWeight != null && currentWeight != 0) {
	 				Float workingWeight = currentWeight;
	 				Float energyParenteral = null;
	 				if (FeedParental.getAminoacidConc() != null) {
	 					energyParenteral = FeedParental.getAminoacidConc() * 4 * workingWeight;
	 				}
	 				if (FeedParental.getAminoacidConc() != null) {
	 					parental.put(BasicConstants.PROTEIN, FeedParental.getAminoacidConc() * workingWeight);
	 					stay = 1;
	 				}
	 				if (FeedParental.getLipidConc() != null) {
	 					parental.put(BasicConstants.FAT, FeedParental.getLipidConc() * workingWeight);
	 					if (energyParenteral != null)
	 						energyParenteral = energyParenteral  + FeedParental.getLipidConc() * 10 * workingWeight;
	 					else
	 						energyParenteral = FeedParental.getLipidConc() * 10 * workingWeight;
	 				}
	
	 				if (FeedParental.getGirvalue() != null && !FeedParental.getGirvalue().trim().isEmpty()) {
	 					stay = 1;
	 					Float gir = Float.valueOf(FeedParental.getGirvalue());
	 					if(gir > 20) {
	 						if(!BasicUtils.isEmpty(FeedParental.getIsReadymadeSolutionGiven()) && !FeedParental.getIsReadymadeSolutionGiven() && !BasicUtils.isEmpty(FeedParental.getDextroseVolumemlperday()) && !BasicUtils.isEmpty(FeedParental.getCurrentDextroseConcentration())) {
	 							gir = (float) 0.007 * FeedParental.getDextroseVolumemlperday() * FeedParental.getCurrentDextroseConcentration();
	 						}else if(!BasicUtils.isEmpty(FeedParental.getIsReadymadeSolutionGiven()) && FeedParental.getIsReadymadeSolutionGiven() && !BasicUtils.isEmpty(FeedParental.getReadymadeFluidVolume())) {
	 							if(!BasicUtils.isEmpty(FeedParental.getReadymadeDextroseConcentration())) {
		 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * FeedParental.getReadymadeDextroseConcentration();
	 							}
		 						else if(!BasicUtils.isEmpty(FeedParental.getReadymadeFluidType())) {
	 								if(FeedParental.getReadymadeFluidType().contains("D10")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 10;
	 								}else if(FeedParental.getReadymadeFluidType().contains("D25")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 25;
	 								}else if(FeedParental.getReadymadeFluidType().contains("D50")) {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 50;
	 								}else  {
			 							gir = (float) 0.007 * FeedParental.getReadymadeFluidVolume() * 5;
	 								}
	 							}
	 						}
	 					}
	 					if (energyParenteral != null)
	 						energyParenteral = Float.valueOf(energyParenteral  + gir * 4.9 * workingWeight  + "");
	 					else
	 						energyParenteral = Float.valueOf(gir * 4.9 * workingWeight  + "");
	 				}
	 				if(!BasicUtils.isEmpty(FeedParental.getBolusVolume()) && !BasicUtils.isEmpty(FeedParental.getBolusType()) && FeedParental.getBolusType().equalsIgnoreCase("10% dextrose")) {
	 					Float girBolus = (float) 0.007 * FeedParental.getBolusVolume() * 10;
	 					if (energyParenteral != null)
	 						energyParenteral = Float.valueOf(energyParenteral  + girBolus * 4.9 * workingWeight  + "");
	 					else
	 						energyParenteral = Float.valueOf(girBolus * 4.9 * workingWeight  + "");
	 				}
	 				parental.put(BasicConstants.ENERGY, energyParenteral);
	
	 				if (FeedParental.getCalciumVolume() != null && FeedParental.getCalciumVolume() != 0) {
	 					parental.put(BasicConstants.CALCIUM, FeedParental.getCalciumVolume() * 9 * workingWeight);
	 				}
	
	 				
	 				calculator.setParentalIntake(parental);
	 			}
	 		}
 		}
        float energy = 0;
 		if(!BasicUtils.isEmpty(parental.get(BasicConstants.ENERGY))) {
 			energy += parental.get(BasicConstants.ENERGY);
 		}
 		if(!BasicUtils.isEmpty(enteral.get(BasicConstants.ENERGY))) {
 			energy += enteral.get(BasicConstants.ENERGY);
 		}
 		
 		map.put("energy", (float)Math.round(energy/currentWeight*100)/100);
		System.out.println("************************************************************************************************");
		System.out.println(uhid);
		System.out.println(parental.get(BasicConstants.ENERGY));
		System.out.println(enteral.get(BasicConstants.ENERGY));
		System.out.println(energy);
		System.out.println(map);
		System.out.println(currentWeight);
		
 		return map;

	}
	//********************************************************************************************************************8
	
	//This is my code
	
	
	  
	
	
	
	public String convert(String query,String uhid) {
		
		//String query1=data.get("query1").toString();
		query=query.replace("___uhid___","'"+uhid+"'" );
		query=query.replace("___prn___","'"+uhid+"'" );
		query=query.replace("[","");
		query=query.replace("]","");
		query=query.replace("---",":");
		query=query.replace("---",":");
		query=query.replace("___BasicConstants.SCHEMA_NAME___",BasicConstants.SCHEMA_NAME);
		query=query.replace("Rule rule","Rulerule");
		

		return query;
		}
	@Override
	public void run() {
		

		try {
			Properties prop = new Properties();
			String fileName = "query.config";
			try (FileInputStream fis = new FileInputStream(fileName)) {
			    prop.load(fis);
			} catch (FileNotFoundException ex) {
			} catch (IOException ex) {
			}

			
			Date datetime1 = null;
			Connection con = null;
			int countuhid = 0;
			int dayoflife = 0; // this is a field
			Date entrydatetime = null;
			Date dobandtime = null;
			String gestationalweek = null;
			String totalfeed_ml_per_kg_day;
			Double workingweight = (double) 1;
			Dictionary geek = new Hashtable();
			String extnum;
			Property hasBabyID;
			String[] nextLine;
			String ph = null; //
			String be = null; //
			String chest_comp_time = null; //
			
			InputStream inputStream = new FileInputStream(new File("student.yml"));

	    	Yaml yaml = new Yaml();
	    	Map<String, Object> data = yaml.load(inputStream);
	    	
			
			// input ontology and define properties
			String queryCurrentUhidsList = "SELECT uhid from " + BasicConstants.SCHEMA_NAME
					+ ".baby_detail where admissionstatus='t' and gestationweekbylmp is not null and branchname = 'Moti Nagar, Delhi'";
			//System.out.println(conversion("queryCurrentUhidsList",prop));

			List<String> activeUhidsList = patientDao.getListFromNativeQuery(queryCurrentUhidsList);

			for (String uhid : activeUhidsList) { // {
				
			   // String uhid="RSHI.0000040669";{  
			    //String uhid="RNEH.0000016618";{  //Mamastha best case
				countuhid = countuhid + 1;

				OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_LITE_MEM, null);
				// use the FileManager to find the input file
				InputStream in = new FileInputStream(inputFileName);
				model.read(in, "");
				String med = "http://www.childhealthimprints.com/NutritionalGuidelines/";
				org.apache.jena.rdf.model.Resource r = model.getResource(med);
				hasDayOfLife = model.getProperty(med, "hasDayOfLife");
				

				hasFeedIntolerance = model.getProperty(med, "hasFeedIntolerance");
				hasGestationalAgeAtBirth = model.getProperty(med, "hasGestationalAgeAtBirth");

				hasInitialFeedingVolume = model.getProperty(med, "hasInitialFeedingVolume");
				hasFinalFeedingVolume = model.getProperty(med, "hasFinalFeedingVolume");
				hasInitialFeedingAdvancement = model.getProperty(med, "hasInitialFeedingAdvancement");
				hasFinalFeedingAdvancement = model.getProperty(med, "hasFinalFeedingAdvancement");

				hasRiskFactor = model.getProperty(med, "hasRiskFactor");
				givenFeedVolume = model.getProperty(med, "givenFeedVolume");
				hasCordGaspHValue = model.getProperty(med, "hasCordGaspHValue"); //
				hasBaseDeficitValue = model.getProperty(med, "hasBaseDeficitValue"); //
				pressorGiven = model.getProperty(med, "pressorGiven"); //
				model.getProperty(med, "hasRiskFactor");
				Property hasChestCompressionDuration = model.getProperty(med,"hasChestCompressionDuration");  //
				Property hasVomitColor = model.getProperty(med, "hasVomitColor"); //
				Property hasVomitVolume = model.getProperty(med, "hasVomit"); //
				Property hasAbdominalDistension = model.getProperty(med, "hasAbdominalDistension");
				Property hasSkinDiscoloration = model.getProperty(med, "hasSkinDiscoloration");
				Property hasAbdominalTenderness = model.getProperty(med, "hasAbdominalTenderness");
				Property hasVisibleBowelLoop = model.getProperty(med, "hasVisibleBowelLoop");
				Property bloodPresentInStool = model.getProperty(med, "bloodPresentInStool");
				Property hasMetabolicAcidosis = model.getProperty(med, "hasMetabolicAcidosis");
				Property hasApnea = model.getProperty(med, "hasApnea");
				Property hasRespiratoryDistress = model.getProperty(med, "hasRespiratoryDistress");
				Property hasLethargy = model.getProperty(med, "hasLethargy");
				Property haslowPlateletCount = model.getProperty(med, "haslowPlateletCount");
				Property hasGastricAspirate = model.getProperty(med, "hasGastricAspirate");
				Property hasXRayAbdominStatus = model.getProperty(med, "hasX-RayAbdominStatus");
				Property hasGastricAspirateAbnormalColor = model.getProperty(med, "hasGastricAspirateAbnormalColor");
				Property hasLongCapillaryRefillTime = model.getProperty(med, "hasLongCapillaryRefillTime");
				Property hasCentralPeripheralDifference = model.getProperty(med, "hasCentralPeripheralDifference");
				Property hasTechycardia = model.getProperty(med, "hasTechycardia");
				Property hasBloodPressureValue = model.getProperty(med, "hasBloodPressureValue");
				Property hasLowBloodPressure = model.getProperty(med, "hasLowBloodPressure");
				Property hasColdPeripheries = model.getProperty(med, "hasColdPeripheries");
				//Property hasLactateLevel = model.getProperty(med, "hasLactateLevel");
				Property hasFeedIntoleranceSign = model.getProperty(med, "hasFeedInttoleranceSign");
				Property hasFeedMilkType = model.getProperty(med, "hasFeedMilkType");
				Property hasFeedingMethod = model.getProperty(med, "hasFeedingMethod");
				Property hasBirthWeight = model.getProperty(med, "hasBirthWeight");
				//Property hasVariable = model.getProperty(med, "hasVariable");
				Property hasForitifiers = model.getProperty(med, "hasForitifiers");
				Property hasPNInitialFeedingVolumeOfCHO = model.getProperty(med, "hasPNInitialFeedingVolumeOfCHO");
				Property hasPNFinalFeedingVolumeOfCHO = model.getProperty(med, "hasPNFinalFeedingVolumeOfCHO");
				Property hasPNInitialFeedingAdvancementOfCHO = model.getProperty(med,"hasPNInitialFeedingAdvancementOfCHO");
				Property hasPNFinalFeedingAdvancementOfCHO = model.getProperty(med,"hasPNFinalFeedingAdvancementOfCHO");
				Property hasPNInitialFeedingVolumeOfProtein = model.getProperty(med,"hasPNInitialFeedingVolumeOfProtein");
				Property hasPNFinalFeedingVolumeOfProtein = model.getProperty(med, "hasPNFinalFeedingVolumeOfProtein");
				Property hasPNInitialFeedingAdvancementOfProtein = model.getProperty(med,"hasPNInitialFeedingAdvancementOfProtein");
				Property hasPNFinalFeedingAdvancementOfProtein = model.getProperty(med,"hasPNFinalFeedingAdvancementOfProtein");
				Property hasPNInitialFeedingVolumeOfSodium = model.getProperty(med,"hasPNInitialFeedingVolumeOfSodium");
				Property hasPNFinalFeedingVolumeOfSodium = model.getProperty(med, "hasPNFinalFeedingVolumeOfSodium");
				Property hasPNInitialFeedingVolumeOfPotassium = model.getProperty(med,"hasPNInitialFeedingVolumeOfPotassium");
				Property hasPNFinalFeedingVolumeOfPotassium = model.getProperty(med, "hasPNFinalFeedingVolumeOfPotassium");
				Property hasPNInitialFeedingAdvancementOfPotassium = model.getProperty(med,"hasPNInitialFeedingAdvancementOfPotassium");
				Property hasPNFinalFeedingAdvancementOfPotassium = model.getProperty(med,"hasPNFinalFeedingAdvancementOfPotassium");
				Property hasPNFeedingVolumeOfCalcium = model.getProperty(med,"hasPNFeedingVolumeOfCalcium");								
				Property hasFinalEnergyRequirement = model.getProperty(med,"hasFinalEnergyRequirement");
				Property hasInitialEnergyRequirement = model.getProperty(med,"hasInitialEnergyRequirement");
				Property hasParenteralTotalVolume = model.getProperty(med, "hasParenteralTotalVolume");
				Property hasParenteralFeed = model.getProperty(med, "hasParenteralFeed");
				Property hasPNFeed = model.getProperty(med, "hasPNFeed");
				Property hasReversedEndBloodFlow = model.getProperty(med, "hasReversedEndBloodFLow");
				Property hasAbsentEndDiastolicFlow = model.getProperty(med, "hasAbsentEndDiastolicFlow");
				Property isStoolPassed = model.getProperty(med, "isStoolPassed");
				Property hasResuscitation = model.getProperty(med, "hasResuscitation");
				Property hasIncreasedLactate = model.getProperty(med, "hasIncreaedLactate");
				Property hasDiseaseNecrotizingEnterocolitis = model.getProperty(med, "hasDiseaseNecrotizingEnterocolitis");

				
	            //System.out.println(query1);

				//String query1_1 = "select obj from BabyDetail as obj  where uhid='" + uhid + "'"+" FOR JSON AUTO ";
				String query1_1 = "select obj from BabyDetail as obj  where uhid='" + uhid + "'";

				//List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query1").toString(), uhid));
				List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(query1_1);

 				System.out.println(babyDetailList.get(0).getDateofadmission() + " "
						+ babyDetailList.get(0).getTimeofadmission() + " " + babyDetailList.get(0).getTimeofbirth()
						+ " " + babyDetailList.get(0).getDateofbirth() + " "
						+ babyDetailList.get(0).getGestationweekbylmp() + " "
						+ babyDetailList.get(0).getGestationdaysbylmp());
						
				try {
					    Date dateofbirth=babyDetailList.get(0).getDateofbirth();   //trial
						Date tofadmission=babyDetailList.get(0).getDateofadmission();  //trail
				        diffInDays = (int) ((tofadmission.getTime() - dateofbirth.getTime()) / (1000 * 60 * 60 * 24));
					    System.out.println(diffInDays+".difference in days.....");			        }
			    catch(Exception e) {
			            System.out.println("Null pointer exception");
			        }
				Float birthweight = babyDetailList.get(0).getBirthweight();
				String time1 = babyDetailList.get(0).getTimeofbirth();
				gestationalweek = babyDetailList.get(0).getGestationweekbylmp().toString();
	            System.out.println(gestationalweek+"gestationalweek");

				String replacetime = time1.replaceFirst(",", ":");
				String reptime = replacetime.replaceFirst(",", " ");

				Date dobb = babyDetailList.get(0).getDateofbirth();

				String timeofadmission = babyDetailList.get(0).getTimeofadmission();
				Date dateofadmission = babyDetailList.get(0).getDateofadmission();

				// System.out.println(time1+"....time..");
				// System.out.println(dob+"......dob.....");

				SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
				SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
				Date date = (Date) parseFormat.parse(reptime);
				// System.out.println(parseFormat.format(date) + " = " +
				// displayFormat.format(date));

				String startingDate = new SimpleDateFormat("yyyy-MM-dd").format((dobb));

				String startingTime = new SimpleDateFormat("hh:mm:ss").format(date);

				String DateTime = startingDate + " " + startingTime;

				SimpleDateFormat parseFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				datetime1 = (Date) parseFormat1.parse(DateTime);

				String replacetoa = timeofadmission.replaceFirst(",", ":");
				String reptime_toa = replacetoa.replaceFirst(",", " ");
				System.out.println(reptime_toa + "....time..");
				System.out.println(dateofadmission + "......doa.....");

				String startingDate1 = new SimpleDateFormat("yyyy-MM-dd").format((dateofadmission));
				Date date1 = (Date) parseFormat.parse(reptime_toa);

				String startingTime1 = new SimpleDateFormat("hh:mm:ss").format(date1);

				String DateTime1 = startingDate1 + " " + startingTime1;

				SimpleDateFormat parseFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Date doatoa = (Date) parseFormat2.parse(DateTime1); // dateofadmission and time of admission
				System.out.println(doatoa + "....doame..");

				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Date newdate = new Date();
				String entry = formatter.format(newdate);

				Date datetime2 = (Date) formatter.parse(entry);

				long currentdiff = datetime2.getTime() - datetime1.getTime();
				long doa_diff = doatoa.getTime() - datetime1.getTime();
				int doa_DOL = (int) TimeUnit.MILLISECONDS.toDays(doa_diff);

				System.out.println((doa_DOL) + "doa");

				int currentDOL = (int) TimeUnit.MILLISECONDS.toDays(currentdiff);
				System.out.println((currentDOL) + "currentd");
				Individual uniquen;
				int loop;
				if(diffInDays>0) {
					loop = diffInDays;
				}
				else {
					loop=doa_DOL;
				}
				for (long p = loop; p <= currentDOL; p++) {
					if (model.getIndividual(med + uhid + "_" + p) != null)
						uniquen = (Individual) model.getIndividual(med + uhid + "_" + p);
					else
						uniquen = (Individual) model.createIndividual(med + uhid + "_" + p, model
								.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
					model.addLiteral(uniquen, hasPNFeed, ResourceFactory.createTypedLiteral("true"));

					
				}

				//This is feed given code
				//start code 
				//************************************************************************************************************************************
				
				
				String babyQuery = "select distinct(b.uhid),b.dateofadmission,b.dateofbirth,b.dischargeddate,b.timeofadmission,b.gestationweekbylmp,b.gestationdaysbylmp,b.birthweight,b.gender,b.inout_patient_status,b.baby_type,b.timeofbirth,b.episodeid from " + BasicConstants.SCHEMA_NAME + 
			    		".baby_detail as b where b.uhid = '" + uhid + "'";
			
				List<Object[]> activeAssessmentList = inicuDoa.getListFromNativeQuery(convert(data.get("babyQuery").toString(), uhid));
				Iterator<Object[]> itr = activeAssessmentList.iterator();
				
				
				boolean isValidDataPN = true;

				Object[] obj = itr.next();
				Date doa = (Date) obj[1];
				Timestamp dod = new Timestamp(new Date().getTime());
				if(BasicUtils.isEmpty(obj[3])) {
					dod = new Timestamp(new Date().getTime());
				}else {
					dod = (Timestamp) obj[3];
				}
				
				Date dob = (Date) obj[2];
				String tob = (String) obj[11];
				String toa = (String) obj[4];
				
				
				//line number 4523 to 4555
				int offset = TimeZone.getTimeZone(BasicConstants.CLIENT_TIME_ZONE).getRawOffset()
						- TimeZone.getDefault().getRawOffset();
				//Merging date of admission and time of admission
				Timestamp creationTime = new Timestamp(new Date().getTime());
				if (!BasicUtils.isEmpty(doa)) {
					creationTime = new Timestamp(doa.getTime());
					if (!BasicUtils.isEmpty(toa)) {
						if(toa.contains(",")) {
							String[] toaArr = toa.split(",");
							// "10,38,PM"
							if (toaArr[2].equalsIgnoreCase("PM") && !toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
							} else if (toaArr[2].equalsIgnoreCase("AM") && toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(0);
							} else {
								creationTime.setHours(Integer.parseInt(toaArr[0]));
							}
							creationTime.setMinutes(Integer.parseInt(toaArr[1]));
						}else if(toa.contains(":")) {
							String[] toaArr = toa.split(":");
							// "10,38,PM"
							if (toa.contains("PM") && !toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(Integer.parseInt(toaArr[0]) + 12);
							} else if (toa.contains("AM") && toaArr[0].equalsIgnoreCase("12")) {
								creationTime.setHours(0);
							} else {
								creationTime.setHours(Integer.parseInt(toaArr[0]));
							}
							creationTime.setMinutes(0);
						}
					}
					creationTime = new Timestamp(creationTime.getTime());
				}
				
				//4867 to 4878

				Timestamp nextTime = new Timestamp(creationTime.getTime());
				boolean isTodayEntry = false;
				if(creationTime.getHours() >= 8) {
					nextTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
					nextTime.setHours(8);
					nextTime.setMinutes(0);
				}else {
					isTodayEntry = true;
					nextTime = new Timestamp(creationTime.getTime());
					nextTime.setHours(8);
					nextTime.setMinutes(0);
				}
				
				
				int lengthOfStay = 0;
				double los = Math.ceil(((dod.getTime() - creationTime.getTime()) / (1000 * 60 * 60 * 24))) + 1;

				boolean gotFirstOrder = false;

				boolean firstLoop = false;
				//end code
				

				//This is my code
				//*****************************************************************************************************************
				Date modification_time=null;
				float lactateval = 0;
				String query2 = "select  obj from BirthToNicu as obj  WHERE uhid=" + "'" + uhid + "'";
				List<BirthToNicu> birthNicuList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query2").toString(), uhid));
				String chestcomp = null;
				Boolean Resuscitation=null;
				if (!BasicUtils.isEmpty(birthNicuList) && !BasicUtils.isEmpty(birthNicuList.get(0).getChestCompTime()))
					chestcomp = birthNicuList.get(0).getChestCompTime();
				
				if (!BasicUtils.isEmpty(birthNicuList) && !BasicUtils.isEmpty(birthNicuList.get(0).getResuscitation()))
					Resuscitation = birthNicuList.get(0).getResuscitation();
				
				if (!BasicUtils.isEmpty(birthNicuList) && !BasicUtils.isEmpty(birthNicuList.get(0).getModificationtime()))
					modification_time = birthNicuList.get(0).getModificationtime();
				
				if (modification_time != null) {
					long assess = datetime2.getTime() - modification_time.getTime();
					int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
					Individual unique_b = null;
					if (model.getIndividual(med + uhid + "_" + assesstime) != null)
						unique_b = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
					else
						unique_b = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
				
				if(Resuscitation!=null) {
					if(Resuscitation.booleanValue()) {
						model.addLiteral(unique_b, hasResuscitation, ResourceFactory.createTypedLiteral("true"));
				        System.out.println(unique_b+"hasResuscitation..........................................................eeeeee..");
					}
				    else {
							model.addLiteral(unique_b, hasResuscitation, ResourceFactory.createTypedLiteral("false"));

				        }
					}
				}
				
			    
				
				String lactate=null;
				Date entrydate=null;
				Individual unique_n=null;
				String query3 = " Select obj from NursingBloodGas as obj  WHERE uhid=" + "'" + uhid + "'";

				List<NursingBloodGas> nursingBloodGasList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query3").toString(), uhid));
				

				if (!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getModificationtime()))
					entrydate = nursingBloodGasList.get(0).getModificationtime();

				if (!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getPh()))
					ph = nursingBloodGasList.get(0).getPh();

				if (!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getBe()))
					be = nursingBloodGasList.get(0).getBe();

				if (!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getLactate()))
					lactate = nursingBloodGasList.get(0).getLactate();
			        //System.out.println(lactate+"lactateeeded..........................................................eeeeee..");

				
				boolean twin_status = false;
				if (!BasicUtils.isEmpty(babyDetailList) && !BasicUtils.isEmpty(babyDetailList.get(0).getMonoamniotic())
						&& babyDetailList.get(0).getMonoamniotic() == true)
					twin_status = true;

				if (entrydate != null) {
					long cudiff = datetime2.getTime() - entrydate.getTime();
					int nursingtime = (int) TimeUnit.MILLISECONDS.toDays(cudiff);
					if (model.getIndividual(med + uhid + "_" + nursingtime) != null) {
						unique_n = (Individual) model.getIndividual(med + uhid + "_" + nursingtime);
					} else {
						unique_n = (Individual) model.createIndividual(med + uhid + "_" + nursingtime, model.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
					}
				}
				if(lactate!=null){
					lactateval = Float.parseFloat(lactate);
				    if(lactateval>5){
						model.addLiteral(unique_n, hasIncreasedLactate, ResourceFactory.createTypedLiteral("true"));
					}
				}

				
				
				boolean val = false;
				String query4="Select obj from BabyPrescription as obj  WHERE uhid="+"'"+uhid+"'";

				List<BabyPrescription> babyPrescriptionList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query4").toString(), uhid));

				if (!BasicUtils.isEmpty(babyPrescriptionList)
						&& !BasicUtils.isEmpty(babyPrescriptionList.get(0).getMedicationtype())
						&& babyPrescriptionList.get(0).getMedicationtype().equalsIgnoreCase("TYPE0004"))
					val = true;

				Individual unique1;
				Date entry_timestamp = null;
				String vomit_color = null;

				String vomit = null;
				String vomit_DOL = null;
				Float totalTPN = null;
				Boolean stoolpassed=null;
				String query5 = " select  nursingintakeobj from NursingIntakeOutput as nursingintakeobj WHERE uhid="
						+ "'" + uhid + "'" + "order by entry_timestamp";

				List<NursingIntakeOutput> NursingIntakeOutputList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query5").toString(), uhid));
				for (NursingIntakeOutput nursingintakeobj : NursingIntakeOutputList) {

					if (!BasicUtils.isEmpty(nursingintakeobj.getVomit()))
						vomit = nursingintakeobj.getVomit();

					if (!BasicUtils.isEmpty(nursingintakeobj.getVomitColor()))
						vomit_color = nursingintakeobj.getVomitColor();

					if (!BasicUtils.isEmpty(nursingintakeobj.getEntry_timestamp()))
						entry_timestamp = nursingintakeobj.getEntry_timestamp();

					


					if (entry_timestamp != null) {
						long cudiff = datetime2.getTime() - entry_timestamp.getTime();
						int nursingtime = (int) TimeUnit.MILLISECONDS.toDays(cudiff);
						// System.out.println(nursingtime+"nursing time");
						if (model.getIndividual(med + uhid + "_" + nursingtime) != null) {
							unique1 = (Individual) model.getIndividual(med + uhid + "_" + nursingtime);
						} else {
							unique1 = (Individual) model.createIndividual(med + uhid + "_" + nursingtime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						}
						if (vomit_color != null) {
							if ((vomit_color == "blood stained") || (vomit_color == "bloody")) {
								model.addLiteral(unique1, hasVomitColor, ResourceFactory.createTypedLiteral("true"));
							}
						} else if (vomit_color == null) {
							model.addLiteral(unique1, hasVomitColor, ResourceFactory.createTypedLiteral("nil"));
						}
						if (vomit != null) {
							model.addLiteral(unique1, hasVomitVolume, ResourceFactory.createTypedLiteral(vomit));
						}
						//System.out.println(stoolpassed+"stoll..................................................................................................");

						
					}

				}

				String query7="select feedintobj  from SaFeedIntolerance as feedintobj WHERE uhid=" +"'"+uhid+"'"+ "order by assessment_time";
				String abdominSign = null;
				Date assessment_time = null;

				List<SaFeedIntolerance> FeedIntoleranceList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query7").toString(), uhid));
				for (SaFeedIntolerance feedintobj : FeedIntoleranceList) {
					if (!BasicUtils.isEmpty(feedintobj.getAssessmentTime()))
						assessment_time = feedintobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(feedintobj.getAbdominalSigns()))
						abdominSign = feedintobj.getAbdominalSigns();

					if (assessment_time != null) {
						long assess = datetime2.getTime() - assessment_time.getTime();
						int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
						Individual unique_2 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_2 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_2 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (abdominSign != null) {
							if (abdominSign.contains("Erythema")) {
								model.addLiteral(unique_2, hasAbdominalTenderness,
										ResourceFactory.createTypedLiteral("true"));
							}
							if (abdominSign.contains("Abdominal Distension")) {
								model.addLiteral(unique_2, hasAbdominalDistension,
										ResourceFactory.createTypedLiteral("true"));
							}
						}
						String abdomin_distinction = null;
						if (!BasicUtils.isEmpty(feedintobj.getAbdominalDistinctionValue()))
							abdomin_distinction = feedintobj.getAbdominalDistinctionValue();
						Individual unique_3 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_3 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_3 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (abdomin_distinction != null) {
							if (abdomin_distinction.contains("Visible Loops")
									|| (abdomin_distinction.contentEquals("Visible Loops")))
								model.addLiteral(unique_3, hasVisibleBowelLoop,
										ResourceFactory.createTypedLiteral("true"));
						}
					}
				}

				//Date entrydate = null;
				for (NursingBloodGas obj1 : nursingBloodGasList) {
					if (!BasicUtils.isEmpty(obj1.getEntryDate()))
						assessment_time = obj1.getEntryDate();
					if (assessment_time != null) {
						if (be != null) {
							float baseaccess = Float.parseFloat(be);
							long assess1 = datetime2.getTime() - assessment_time.getTime();
							int assesstime1 = (int) TimeUnit.MILLISECONDS.toDays(assess1);
							Individual unique_5 = null;

							if (model.getIndividual(med + uhid + "_" + assesstime1) != null)
								unique_5 = (Individual) model.getIndividual(med + uhid + "_" + assesstime1);
							else
								unique_5 = (Individual) model.createIndividual(med + uhid + "_" + assesstime1,
										model.getOntClass(
												"http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
							if (baseaccess < -6) {
								model.addLiteral(unique_5, hasMetabolicAcidosis,
										ResourceFactory.createTypedLiteral("true"));
							}
						}
					}
				}
				String query8="select nursingepiobj from NursingEpisode as nursingepiobj WHERE uhid="+"'"+uhid+"'"+ "order by modificationtime"; 

				List<NursingEpisode> nursingepisodeList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query8").toString(), uhid));
				for (NursingEpisode nursingepiobj : nursingepisodeList) {

					if (!BasicUtils.isEmpty(nursingepiobj.getModificationtime()))
						assessment_time = nursingepiobj.getModificationtime();
					Boolean apnea = null;
					String apnea1 = "f";
					if (!BasicUtils.isEmpty(nursingepiobj.getApnea()))
						apnea = nursingepiobj.getApnea();
					if (apnea != null)
						apnea1 = apnea.toString();
					if ((assessment_time != null) && apnea != null) {
						long assess11 = datetime2.getTime() - assessment_time.getTime();
						int assesstime11 = (int) TimeUnit.MILLISECONDS.toDays(assess11);
						Individual unique_6 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime11) != null)
							unique_6 = (Individual) model.getIndividual(med + uhid + "_" + assesstime11);
						else
							unique_6 = (Individual) model.createIndividual(med + uhid + "_" + assesstime11, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						// model.addLiteral(unique_6,hasApnea,ResourceFactory.createTypedLiteral(apnea));
						if (apnea1 == "t") {
							model.addLiteral(unique_6, hasApnea, ResourceFactory.createTypedLiteral(apnea));
						}
					}
				}

				
				String resp_system = null;

				String query9="Select saRdsobj from SaRespRds as saRdsobj WHERE uhid="+"'"+uhid+"'"+ "order by assessment_time";
				List<SaRespRds> SaRespRdsList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query9").toString(), uhid));

				for (SaRespRds saRdsobj : SaRespRdsList) {

					if (!BasicUtils.isEmpty(saRdsobj.getAssessmentTime()))
						assessment_time = saRdsobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(saRdsobj.getRespSystemStatus()))
						resp_system = saRdsobj.getRespSystemStatus();

					if ((assessment_time != null) && (resp_system != null)) {
						long assess = datetime2.getTime() - assessment_time.getTime();
						int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
						Individual unique_7 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_7 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_7 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (resp_system == "Yes")
							model.addLiteral(unique_7, hasRespiratoryDistress,
									ResourceFactory.createTypedLiteral("true"));
					}
				}

				String symp = null;
				String query10="Select sasepsisobj from SaSepsis as sasepsisobj WHERE uhid="+"'"+uhid+"'"+ "order by assessment_time";

				List<SaSepsis> SaInfectionSepsisList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query10").toString(), uhid));
				for (SaSepsis sasepsisobj : SaInfectionSepsisList) {

					if (!BasicUtils.isEmpty(sasepsisobj.getAssessmentTime()))
						assessment_time = sasepsisobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(sasepsisobj.getSymptomaticValue()))
						symp = sasepsisobj.getSymptomaticValue();

					if ((assessment_time != null) && (symp != null)) {
						long assess15 = datetime2.getTime() - assessment_time.getTime();
						int assesstime15 = (int) TimeUnit.MILLISECONDS.toDays(assess15);
						Individual unique_8 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime15) != null)
							unique_8 = (Individual) model.getIndividual(med + uhid + "_" + assesstime15);
						else
							unique_8 = (Individual) model.createIndividual(med + uhid + "_" + assesstime15, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (symp.contains("Lethargy")) {
							model.addLiteral(unique_8, hasLethargy, ResourceFactory.createTypedLiteral("true"));
						}
					}
				}

				String query11="Select testitemobj from TestItemResult as testitemobj where prn=" +"'"+uhid+"'"+"AND itemname='PLATELET COUNT' AND itemvalue is not null order by resultdate";
				List<TestItemResult> testresultList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query11").toString(), uhid));
				for (TestItemResult testitemobj : testresultList) {

					if (!BasicUtils.isEmpty(testitemobj.getResultdate()))
						assessment_time = testitemobj.getResultdate();
					String p = testitemobj.getItemvalue();
					Integer platelet = Integer.parseInt(p);

					if ((assessment_time != null) && (platelet != null)) {
						long assess = datetime2.getTime() - assessment_time.getTime();
						int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
						Individual unique_10 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_10 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_10 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));

						if (platelet < 150000) {
							model.addLiteral(unique_10, haslowPlateletCount,
									ResourceFactory.createTypedLiteral("true"));
						}
					}
				}
				String query12="select nursingvitalobj from NursingVitalparameter as nursingvitalobj where uhid=" +"'"+uhid+"'"+" order by entrydate";
				List<NursingVitalparameter> NursingVitalparameterList1 = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query12").toString(), uhid));
				for (NursingVitalparameter nursingvitalobj : NursingVitalparameterList1) {
					String discolor = null;
					if (!BasicUtils.isEmpty(NursingVitalparameterList1))
						assessment_time = nursingvitalobj.getEntryDate();

					if (!BasicUtils.isEmpty(NursingVitalparameterList1))
						discolor = nursingvitalobj.getBaby_color();

					if ((assessment_time != null) && (discolor != null)) {
						long assess = datetime2.getTime() - assessment_time.getTime();
						int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
						Individual unique_11 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_11 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_11 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (discolor == "Cyanosis")
							model.addLiteral(unique_11, hasSkinDiscoloration,
									ResourceFactory.createTypedLiteral("true"));
					}
				}

				Boolean gastric_aspirate;
				String feed_intol = null;
				for (SaFeedIntolerance feedintobj : FeedIntoleranceList) {

					if (!BasicUtils.isEmpty(feedintobj.getAssessmentTime()))
						;
					assessment_time = feedintobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(feedintobj.getGastricAspirate()))
						gastric_aspirate = feedintobj.getGastricAspirate();

					if (!BasicUtils.isEmpty(feedintobj.getFeedIntoleranceStatus()))
						feed_intol = feedintobj.getFeedIntoleranceStatus();

					if ((assessment_time != null) && (feed_intol != null)) {
						long assess = datetime2.getTime() - assessment_time.getTime();
						int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
						Individual unique_12 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime) != null)
							unique_12 = (Individual) model.getIndividual(med + uhid + "_" + assesstime);
						else
							unique_12 = (Individual) model.createIndividual(med + uhid + "_" + assesstime, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						model.addLiteral(unique_12, hasGastricAspirate, ResourceFactory.createTypedLiteral("true"));
						if (feed_intol.contains("Yes")) {
							model.addLiteral(unique_12, hasFeedIntolerance, ResourceFactory.createTypedLiteral("true"));
						}
						if (feed_intol.contains("No")) {
							model.addLiteral(unique_12, hasFeedIntolerance,
									ResourceFactory.createTypedLiteral("false"));
						}
						if (feed_intol.contains("Inactive")) {
							model.addLiteral(unique_12, hasFeedIntolerance,
									ResourceFactory.createTypedLiteral("false"));
						}
					}
				}

				String gastric_aspirate1;
				String aspiratecolor = null;

				List<NursingIntakeOutput> NursingIntakeOutputList1 = patientDao.getListFromMappedObjNativeQuery(query5);
				for (NursingIntakeOutput nursingintakeobj : NursingIntakeOutputList1) {

					if (!BasicUtils.isEmpty(nursingintakeobj.getGastricAspirate()))
						;
					gastric_aspirate1 = nursingintakeobj.getGastricAspirate();

					if (!BasicUtils.isEmpty(nursingintakeobj.getEntry_timestamp()))
						;
					assessment_time = nursingintakeobj.getEntry_timestamp();

					if (!BasicUtils.isEmpty(nursingintakeobj.getAspirateColor()))
						;
					aspiratecolor = nursingintakeobj.getAspirateColor();
					
					if (!BasicUtils.isEmpty(nursingintakeobj.getStoolPassed()))
						stoolpassed = nursingintakeobj.getStoolPassed();

					if ((assessment_time != null) && (aspiratecolor != null)) {
						long assess20 = datetime2.getTime() - assessment_time.getTime();
						int assesstime20 = (int) TimeUnit.MILLISECONDS.toDays(assess20);
						Individual unique_13 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime20) != null)
							unique_13 = (Individual) model.getIndividual(med + uhid + "_" + assesstime20);
						else
							unique_13 = (Individual) model.createIndividual(med + uhid + "_" + assesstime20, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						
						

						if (aspiratecolor.contains("Milky"))
							model.addLiteral(unique_13, hasGastricAspirateAbnormalColor,
									ResourceFactory.createTypedLiteral("false"));
						else
							model.addLiteral(unique_13, hasGastricAspirateAbnormalColor,
									ResourceFactory.createTypedLiteral("true"));
						
					}
					Individual unique_ss;
					if ((assessment_time != null)  && (stoolpassed != null)) {
						long assess20 = datetime2.getTime() - assessment_time.getTime();
						int assesstime20 = (int) TimeUnit.MILLISECONDS.toDays(assess20);
						if (model.getIndividual(med + uhid + "_" + assesstime20) != null)
							unique_ss = (Individual) model.getIndividual(med + uhid + "_" + assesstime20);
						else
							unique_ss = (Individual) model.createIndividual(med + uhid + "_" + assesstime20, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));

						if (stoolpassed.toString().contains("f")) {
							model.addLiteral(unique_ss, isStoolPassed, ResourceFactory.createTypedLiteral("false"));
							//System.out.println(stoolpassed+"stoll.............................................................................................");
						}
					}
					
				}

				Float bp = null;
				String cft = null;
				float centraltemp = 0;
				float peripheraltemp = 0;
				float diff = 0;

				for (NursingVitalparameter nursingvitalobj : NursingVitalparameterList1) {

					if (!BasicUtils.isEmpty(nursingvitalobj.getEntryDate()))
						assessment_time = nursingvitalobj.getEntryDate();

					if (!BasicUtils.isEmpty(nursingvitalobj.getMeaniBp()))
						bp = nursingvitalobj.getMeaniBp();

					if (!BasicUtils.isEmpty(nursingvitalobj.getCft()))
						cft = nursingvitalobj.getCft();

					if (!BasicUtils.isEmpty(nursingvitalobj.getCentraltemp()))
						centraltemp = nursingvitalobj.getCentraltemp();

					if (!BasicUtils.isEmpty(nursingvitalobj.getPeripheraltemp()))
						peripheraltemp = nursingvitalobj.getPeripheraltemp();
					diff = centraltemp - peripheraltemp;

					if (assessment_time != null) {
						long assess21 = datetime2.getTime() - assessment_time.getTime();
						int assesstime21 = (int) TimeUnit.MILLISECONDS.toDays(assess21);
						Individual unique_14 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime21) != null)
							unique_14 = (Individual) model.getIndividual(med + uhid + "_" + assesstime21);
						else
							unique_14 = (Individual) model.createIndividual(med + uhid + "_" + assesstime21, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (cft != null)
							if (cft.equalsIgnoreCase("3") || (cft.equalsIgnoreCase(">3"))) {
								model.addLiteral(unique_14, hasLongCapillaryRefillTime,
										ResourceFactory.createTypedLiteral("true"));
							}
						if (diff != 0) {
							model.addLiteral(unique_14, hasCentralPeripheralDifference,
									ResourceFactory.createTypedLiteral("true"));
						}
						if (bp != null) {
							model.addLiteral(unique_14, hasBloodPressureValue, ResourceFactory.createTypedLiteral(bp));
						}
					}
				}

				for (NursingEpisode nursingepiobj : nursingepisodeList) {

					String techycardia = null;
					if (!BasicUtils.isEmpty(nursingepiobj.getCreationtime()))
						assessment_time = nursingepiobj.getCreationtime();

					if (!BasicUtils.isEmpty(nursingepiobj.getSymptomaticValue()))
						techycardia = nursingepiobj.getSymptomaticValue();

					if ((assessment_time != null) && (techycardia != null)) {
						long assess22 = datetime2.getTime() - assessment_time.getTime();
						int assesstime22 = (int) TimeUnit.MILLISECONDS.toDays(assess22);
						Individual unique_15 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime22) != null)
							unique_15 = (Individual) model.getIndividual(med + uhid + "_" + assesstime22);
						else
							unique_15 = (Individual) model.createIndividual(med + uhid + "_" + assesstime22, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (techycardia.contains("Tachycardia"))
							model.addLiteral(unique_15, hasTechycardia, ResourceFactory.createTypedLiteral("true"));
					}
				}

				String peripheries = null;
				Float lactatevalue = null;
				String query13= "select sashockobj from SaShock as sashockobj where uhid="+"'"+uhid+"'"+ "order by assessment_time";
				List<SaShock> SaShockList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query13").toString(), uhid));
				for (SaShock sashockobj : SaShockList) {

					if (!BasicUtils.isEmpty(sashockobj.getAssessmentTime()))
						assessment_time = sashockobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(sashockobj.getPeripheries()))
						peripheries = sashockobj.getPeripheries();

					if (!BasicUtils.isEmpty(sashockobj.getLactate()))
						lactatevalue = sashockobj.getLactate();

					if (assessment_time != null) {
						long assess23 = datetime2.getTime() - assessment_time.getTime();
						int assesstime23 = (int) TimeUnit.MILLISECONDS.toDays(assess23);
						Individual unique_16 = null;

						if (model.getIndividual(med + uhid + "_" + assesstime23) != null)
							unique_16 = (Individual) model.getIndividual(med + uhid + "_" + assesstime23);
						else
							unique_16 = (Individual) model.createIndividual(med + uhid + "_" + assesstime23, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (peripheries != null)
							if (peripheries.contains("cold"))
								model.addLiteral(unique_16, hasColdPeripheries,
										ResourceFactory.createTypedLiteral("true"));
						//if (lactatevalue != null)
						//	model.addLiteral(unique_16, hasLactateLevel, ResourceFactory.createTypedLiteral("true"));
					}
				}

				String eventstatusNEC = null;
				String necstage=null;
				String query14="select saNecobj from SaNec as saNecobj where uhid="+"'"+uhid+"'"+ "order by assessment_time";

				List<SaNec> SainfectionnecList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query14").toString(), uhid));
				for (SaNec saNecobj : SainfectionnecList) {
					if (!BasicUtils.isEmpty(saNecobj.getAssessmentTime()))
						assessment_time = saNecobj.getAssessmentTime();

					if (!BasicUtils.isEmpty(saNecobj.getEventstatus()))
						eventstatusNEC = saNecobj.getEventstatus();
					
					if (!BasicUtils.isEmpty(saNecobj.getBellStagingValue()))
						necstage = saNecobj.getBellStagingValue();


					if ((assessment_time != null) && (eventstatusNEC != null)) {
						long assess24 = datetime2.getTime() - assessment_time.getTime();

						int assesstime25 = (int) TimeUnit.MILLISECONDS.toDays(assess24);
						Individual unique_17 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime25) != null)
							unique_17 = (Individual) model.getIndividual(med + uhid + "_" + assesstime25);
						else
							unique_17 = (Individual) model.createIndividual(med + uhid + "_" + assesstime25, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						if (eventstatusNEC.contains("yes"))
							model.addLiteral(unique_17, hasDiseaseNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("true"));
						
						if (necstage != null) {
							if ( necstage.contains("IA") || necstage.contains("IB") ){
							model.addLiteral(unique_17, hasDiseaseNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("IA"));
							}
							if ((necstage.contains("IIA")) ||  (necstage.contains("IIB"))){
								model.addLiteral(unique_17, hasDiseaseNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("IIA"));
							}
							if ((necstage.contains("IIIA")) ||  (necstage.contains("IIIB"))){
								model.addLiteral(unique_17, hasDiseaseNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("IIIA"));
							}
						}
					}
				}

				Float totalPN = null;
				Date assessment = null;
				String query15="select feedDetailobj from BabyfeedDetail as feedDetailobj where uhid="+"'"+uhid+"'"+"AND totalparenteralvolume>0 order by entrydatetime";
				List<BabyfeedDetail> BabyfeedDetailList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query15").toString(), uhid));

				for (BabyfeedDetail feedDetailobj : BabyfeedDetailList) {
					if (!BasicUtils.isEmpty(feedDetailobj.getModificationtime()))
						assessment = feedDetailobj.getModificationtime();

					if (!BasicUtils.isEmpty(feedDetailobj.getTotalparenteralvolume()))
						totalPN = feedDetailobj.getTotalparenteralvolume();

					if ((assessment != null) && (totalPN != null)) {
						long assess26 = datetime2.getTime() - assessment.getTime();

						int assesstime27 = (int) TimeUnit.MILLISECONDS.toDays(assess26);
						Individual unique_18 = null;
						if (model.getIndividual(med + uhid + "_" + assesstime27) != null)
							unique_18 = (Individual) model.getIndividual(med + uhid + "_" + assesstime27);

						else
							unique_18 = (Individual) model.createIndividual(med + uhid + "_" + assesstime27, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
						
						if (totalPN != null && totalPN > 0) {
							// System.out.println(totalPN);

							model.addLiteral(unique_18, hasParenteralTotalVolume,
									ResourceFactory.createTypedLiteral(totalPN));

						}
					}
				}

				String umblical = null;
				Date assessment1 = null;
				String umblicaldoppler=null;
				Individual unique_u;
				String query16="select AntenatalHistoryDetailobj from AntenatalHistoryDetail as AntenatalHistoryDetailobj where uhid="+"'"+uhid+"'"+" order by modificationtime ";
				List<AntenatalHistoryDetail> AntenatalHistoryDetailyList = patientDao.getListFromMappedObjNativeQuery(convert(data.get("query16").toString(), uhid));

				for (AntenatalHistoryDetail AntenatalHistoryDetailobj : AntenatalHistoryDetailyList) {
					if (!BasicUtils.isEmpty(AntenatalHistoryDetailobj.getModificationtime()))
						assessment1 = AntenatalHistoryDetailobj.getModificationtime();
					System.out.println(assessment1+"modification.....................................................................");

					if (!BasicUtils.isEmpty(AntenatalHistoryDetailobj.getUmbilicalDoppler()))
						umblical = AntenatalHistoryDetailobj.getUmbilicalDoppler();
					
					if (!BasicUtils.isEmpty(AntenatalHistoryDetailobj.getAbnormalUmbilicalDopplerType()))
						umblicaldoppler = AntenatalHistoryDetailobj.getAbnormalUmbilicalDopplerType();
				
					if ((assessment1 != null)) {
						long assess28 = datetime2.getTime() - assessment1.getTime();

						int assesstime29 = (int) TimeUnit.MILLISECONDS.toDays(assess28);
						if (model.getIndividual(med + uhid + "_" + assesstime29) != null)
							unique_u = (Individual) model.getIndividual(med + uhid + "_" + assesstime29);

						else
							unique_u = (Individual) model.createIndividual(med + uhid + "_" + assesstime29, model
									.getOntClass("http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));
                        if(umblical!=null) {
                    	   if (umblical.contains("Reverse")) {
                    		   model.addLiteral(unique_u, hasReversedEndBloodFlow,ResourceFactory.createTypedLiteral("true"));
                    	   }
                    	   else if (umblical.contains("Absent")) {
                    		   model.addLiteral(unique_u, hasAbsentEndDiastolicFlow,ResourceFactory.createTypedLiteral("true"));
                    		   //System.out.println("Absent............................................................................................");
                    	   }
						}
                        if(umblicaldoppler!=null) {

                    	   if (umblicaldoppler.contains("abnormal")) {
                    		   model.addLiteral(unique_u, hasAbsentEndDiastolicFlow,ResourceFactory.createTypedLiteral("true"));
                    		  // System.out.println("abnormal............................................................................................");							
                    	   }
                       }
					}
				}
				
				
      		   System.out.println("1............................................................................................");							


				OntClass uniqueid = null;

				hasBabyID = model.getProperty(med, "hasBabyID");
				{

					for (long p = doa_DOL; p <= currentDOL; p++) {

						ExtendedIterator classes = model.listClasses();
						while (classes.hasNext()) {
							OntClass cls = (OntClass) classes.next();
							for (Iterator i = cls.listSubClasses(true); i.hasNext();) {
								OntClass c = (OntClass) i.next();
								if (c.getLocalName().contentEquals("Neonate")) {

									if (model.getIndividual(med + uhid + "_" + p) != null)
										unique = (Individual) model.getIndividual(med + uhid + "_" + p);
									else
										unique = (Individual) model.createIndividual(med + uhid + "_" + p,
												model.getOntClass(
														"http://www.childhealthimprints.com/NutritionalGuidelines/#Neonate"));

									if (currentDOL >= 0 & gestationalweek != null & uhid != null) {
										if (model.contains(unique, hasDayOfLife,
												ResourceFactory.createTypedLiteral(p))) {
										} else {
											model.addLiteral(unique, hasDayOfLife,
													ResourceFactory.createTypedLiteral(p));
										}
										if (model.contains(unique, hasGestationalAgeAtBirth, ResourceFactory
												.createTypedLiteral(gestationalweek, XSDDatatype.XSDdouble))) {
										} else {
											model.addLiteral(unique, hasGestationalAgeAtBirth, ResourceFactory
													.createTypedLiteral(gestationalweek, XSDDatatype.XSDdouble));
										}
										if (model.contains(unique, hasBabyID,
												ResourceFactory.createTypedLiteral(uhid))) {
										} else {
											model.addLiteral(unique, hasBabyID,
													ResourceFactory.createTypedLiteral(uhid));
										}
										
										if (chestcomp != null) {
											model.addLiteral(unique, hasChestCompressionDuration,
													ResourceFactory.createTypedLiteral(chestcomp));
										}
										if (ph != null) {
											model.addLiteral(unique, hasCordGaspHValue,
													ResourceFactory.createTypedLiteral(ph));
										}
										if (be != null) {
											model.addLiteral(unique, hasBaseDeficitValue,
													ResourceFactory.createTypedLiteral(be));
										}
									
										model.addLiteral(unique, pressorGiven, ResourceFactory.createTypedLiteral(val));


									}
								}
							}
						}
					}
		      		System.out.println("2............................................................................................");							


					List<Rule> rules = Rule.rulesFromURL("ApolloGuideline.rules");
					GenericRuleReasoner reasoner = (GenericRuleReasoner) GenericRuleReasonerFactory.theInstance()
							.create(null);
					((FBRuleReasoner) reasoner).setRules(rules);
					((GenericRuleReasoner) reasoner).setMode(GenericRuleReasoner.FORWARD_RETE);
					reasoner.setDerivationLogging(true);
					
					  
					
					
					
					InfModel inf = ModelFactory.createInfModel(reasoner, model);
					 org.apache.jena.rdf.model.RDFWriter writer = model.getWriter("TTL");
					 File file= new File("D:\\CHIL\\Nutrition\\infmodel1");
					 writer.write(inf, new FileOutputStream(file), med);
					/* 
					 PrintWriter out = new PrintWriter(System.out);
					 for (StmtIterator i = inf.listStatements(); i.hasNext(); ) {
					     org.apache.jena.rdf.model.Statement s = i.nextStatement();
					     System.out.println("Statement is " + s);
					     for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
					         Derivation deriv = (Derivation) id.next();
					         deriv.printTrace(out, true);
					     }
					 }
					 out.flush();
					 */
					
					 String test ="PREFIX CHIL: <http://www.childhealthimprints.com/NutritionalGuidelines/> "
					 		+ " PREFIX type: <http://www.w3.org/2001/XMLSchema>"
					 		+ " PREFIX booleanv: <http://www.w3.org/2001/XMLSchema#boolean>"
					 		+"PREFIX gr: <http://www.w3.org/1999/02/22-rdf-syntax-ns#type>"

					 		+ " SELECT  DISTINCT ?Baby ?UHID  ?type   ?DayOfLife ?FeedVolume  ?FeedAdvancement ?FinalFeedVolume  ?FinalFeedAdvancement "
					 		+ " ?CHOInitialPNVolume ?CHOFinalPNVolume ?CHOInitialPNAdvance  ?CHOFinalPNAdvance ?ProteinInitialPNVolume  ?ProteinFinalPNVolume "
					 		+ " ?ProteinInitialPNAdvance  ?ProteinFinalPNAdvance ?feedint ?PotassiumInitialPNVolume  ?PotassiumFinalPNVolume "
					 		+ " ?PotassiumInitialPNAdvance  ?PotassiumFinalPNAdvance ?SodiumInitialPNVolume  ?SodiumFinalPNVolume ?SodiumInitialPNAdvance"
					 		+ " ?SodiumFinalPNAdvance ?CalciumInitialPNVolume  ?InitialEnergy  ?FinalEnergy  "
					 		+ " ?feedint ?vomitvol ?vomitcolor ?abdtender ?abddistension"
				     		+ " ?visiblebowel ?bloodstool ?metabolic ?Apnea  ?respDistress ?Lethargy  ?plateletcount  ?skindiscolor "
				     		+ " ?gastric  ?CFL ?central  ?techycardia  ?coldperi ?BP "
				     		+ " ?reverseendflow ?stoolpassed   ?absentdiastolic   ?resus  ?lactate  ?NEC "
					 		+"WHERE  { {?Baby        CHIL:hasBabyID  ?UHID}"
					 		+"OPTIONAL {?Baby        CHIL:hasDayOfLife  ?DayOfLife}. "
					 		+"OPTIONAL { ?Baby       CHIL:hasInitialFeedingVolume   ?FeedVolume} "
					 		+"OPTIONAL { ?Baby       CHIL:hasInitialFeedingAdvancement   ?FeedAdvancement}"
					 		+"OPTIONAL { ?Baby       CHIL:hasFinalFeedingVolume   ?FinalFeedVolume}"
					 		+"OPTIONAL { ?Baby       CHIL:hasFinalFeedingAdvancement   ?FinalFeedAdvancement}   "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingVolumeOfCHO   ?CHOInitialPNVolume} "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingVolumeOfCHO  ?CHOFinalPNVolume}   "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingAdvancementOfCHO   ?CHOInitialPNAdvance}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingAdvancementOfCHO      ?CHOFinalPNAdvance}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingVolumeOfProtein   ?ProteinInitialPNVolume} "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingVolumeOfProtein       ?ProteinFinalPNVolume}"
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingAdvancementOfProtein  ?ProteinInitialPNAdvance}"
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingAdvancementOfProtein      ?ProteinFinalPNAdvance} "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingVolumeOfSodium   ?SodiumInitialPNVolume}   "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingVolumeOfSodium       ?SodiumFinalPNVolume} "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingAdvancementOfSodium   ?SodiumInitialPNAdvance}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFinalFeedingAdvancementOfSodium      ?SodiumFinalPNAdvance} "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNInitialFeedingVolumeOfPotassium   ?PotassiumInitialPNVolume}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasPNFeedingVolumeOfCalcium   ?CalciumInitialPNVolume} "
					 		+"OPTIONAL { ?Baby       CHIL:hasInitialEnergyRequirement   ?InitialEnergy}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasFinalEnergyRequirement     ?FinalEneregy}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasFeedIntolerance   ?feedint} "
					 		+"OPTIONAL { ?Baby       CHIL:hasVomitVolume   ?vomitvol}"     	
						    +"OPTIONAL { ?Baby       CHIL:hasVomitColor   ?vomitcolor}  "
						    +"OPTIONAL { ?Baby       CHIL:hasAbdominalTenderness   ?abdtender} "
						    +"OPTIONAL { ?Baby       CHIL:hasAbdominalDistension   ?abddistension}  "
						    +"OPTIONAL { ?Baby       CHIL:hasVisibleBowelLoop   ?visiblebowel}  "
						    +"OPTIONAL { ?Baby       CHIL:bloodPresentInStool   ?bloodstool}  "
						    +"OPTIONAL { ?Baby       CHIL:hasMetabolicAcidosis   ?metabolic}  "
						    +"OPTIONAL { ?Baby       CHIL:hasApnea   ?Apnea}  "        		 
						    +"OPTIONAL { ?Baby       CHIL:hasRespiratoryDistress  ?respDistress}  "
						    +"OPTIONAL { ?Baby       CHIL:hasLethargy   ?Lethargy}  "
					        +"OPTIONAL { ?Baby       CHIL:haslowPlateletCount   ?plateletcount}  "
						    +"OPTIONAL { ?Baby       CHIL:hasSkinDiscoloration   ?skindiscolor}  "
						    +"OPTIONAL { ?Baby       CHIL:hasGastricAspirateAbnormalColor   ?gastric}  "
						    +"OPTIONAL { ?Baby       CHIL:hasLongCapillaryRefillTime   ?CFL}  "
						    +"OPTIONAL { ?Baby       CHIL:hasCentralPeripheralDifference  ?central}  "
						    +"OPTIONAL { ?Baby       CHIL:hasBloodPressureValue   ?blood}  "
						    +"OPTIONAL { ?Baby       CHIL:hasTechycardia   ?techycardia}  "
						    +"OPTIONAL { ?Baby       CHIL:hasColdPeripheries   ?coldperi}  "
					        +"OPTIONAL { ?Baby       CHIL:hasLowBloodpressure   ?BP}  "
					 		+"OPTIONAL { ?Baby       CHIL:hasReversedEndBloodFlow   ?reverseendflow} "
					 		+"OPTIONAL { ?Baby       CHIL:isStoolPassed  ?stoolpassed }"  
					 		+"OPTIONAL { ?Baby       CHIL:hasAbsentEndDiastolicFlow   ?absentdiastolic }" 
					 		+"OPTIONAL { ?Baby       CHIL:hasResuscitation   ?resus }" 
					 		+"OPTIONAL { ?Baby       CHIL:hasIncreasedLactate  ?lactate }" 
					 		+"OPTIONAL { ?Baby       CHIL:hasDiseaseNecrotizingEnterocolitis  ?NEC }" 
					 		+"OPTIONAL { ?Baby       CHIL:hasNPO  ?NPO }" 

					 		+ "}"
					 		+ " order by ?DayOfLife";
					
			        System.out.println("3............................................................................................");							

					Query query = QueryFactory.create(convert(data.get("test").toString(), uhid));
					QueryExecution qexec = QueryExecutionFactory.create(query, inf);
					ResultSet rs = qexec.execSelect();
				   //ResultSetFormatter.out(System.out, rs, query);

					List vars = rs.getResultVars();
					//System.out.println(vars+"vars.....");

					String deleteType = "delete from " + BasicConstants.SCHEMA_NAME
							+ ".nutritional_compliance where uhid = '" + uhid + "'";
					//System.out.println(conversion("deleteType",prop));
					settingDao.executeInsertQuery(convert(data.get("deleteType").toString(), uhid));

					while (rs.hasNext()) { // iterate over the result
						
		      			// print out the predicate, subject and object of each statement
		                String sign="";
		                String dis="";
		                String npo="";
		                String ftol="";
		                String resus1="";
		                String absentend="";
		                String inlac="";
						trackrules="";

						String uhidValue = "";
						int dolValue = 0;
                        int prevdolValue=100;
						Float CHOInitialPNVolume = null;
						Float CHOFinalPNVolume = null;
						Float CHOInitialPNAdvance = null;
						Float CHOFinalPNAdvance = null;

						Float ProteinInitialPNVolume = null;
						Float ProteinFinalPNVolume = null;
						Float ProteinInitialPNAdvance = null;
						Float ProteinFinalPNAdvance = null;
						
						Float SodiumInitialPNVolume = null;
						Float SodiumFinalPNVolume = null;
						Float SodiumInitialPNAdvance = null;
						Float SodiumFinalPNAdvance = null;
						
						Float PotassiumInitialPNVolume = null;
						Float PotassiumFinalPNVolume = null;
						Float PotassiumInitialPNAdvance = null;
						Float PotassiumFinalPNAdvance = null;
						
						Float CalciumInitialPNVolume = null;
						Float CalciumFinalPNVolume = null;
						Float CalciumInitialPNAdvance = null;
						Float CalciumFinalPNAdvance = null;
						
						Float InitialEnergy = null;
						Float FinalEnergy = null;
						
						String reason="";
						

						Float feedVolumeValue = null;
						Float feedIncrementValue = null;

						Float feedVolumeValuefinal = null;
						Float feedIncrementValuefinal = null;
						
						String details = "";

						int counter = 0;
						QuerySolution qs = rs.nextSolution();
						System.out.println("solu");
						String value = "";

						String abdominaltenderness = "";
						String abdominaldistension = "";
						String visiblebowel = "";
						String bloodstool = "";
						String metabolic = "";
						String apnea = "";
						String resus="";
						String respiratorydistress = "";
						String lethargy = "";
						String plateletcount = "";
						String skindiscolor = "";
						String feedintolerance = "";
						String gastricaspirate = "";
						String CFT = "";
						String central = "";
						String techycardia = "";
						String coldperi = "";
						String NEC = "";
						String BPvalue = "";
						String risk = "";
						String vomitvol = "";
						String feedint = "";
						String central_s = "";
						String vomitcolor_s = "";
						String abdominaldist_s = "";
						String visiblebowel_s = "";
						String abdtender_s1 = "";
						String abdtender_s11 = "";
						String abdominaltender_s = "";
						String visiblebowel_s1 = "";
						String bloodstool_s = "";
						String metabolic_s = "";
						String respdistress_s = "";
						String apnea_s = "";
						String lethargy_s = "";
						String plateletcount_s = "";
						String skindiscolor_s = "";
						String CFT_s = "";
						String gastricaspirate_s = "";
						String techycardia_s = "";
						String coldperi_s = "";
						String NEC_s = "";
						String bp_s = "";
						String vomitvolume = "";
						String vomitcolor = "";
						String Baby = "";
						String inlactate = "";
						String diastolic = "";
						String stool = "";
						String reverseendflow = "";
						String feedint_s="";
						String reverse="";
						for (int i = 0; i < vars.size(); i++) {

							String var = vars.get(i).toString();
							if (var.contentEquals("Baby")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (value == Baby) {
										continue;
									}
									// System.out.println("UHID:........"+ "" + value);
									Baby = value;
								}
							}

							if (var.contentEquals("UHID")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("UHID:........"+ "" + value);
									uhidValue = value;
								}
							}
							if (var.contentEquals("DayOfLife")) {
								RDFNode node = qs.get(var);
								value = node.toString().split("http")[0];
								value = value.replaceAll("[^a-zA-Z0-9]", "");
								int value1 = Integer.parseInt(value);
								if (value1 == dolValue) {
									continue;
								}
								dolValue = Integer.parseInt(value);
								
								// System.out.println("dolValue:........"+ "" + dolValue);
							}
							if (var.contentEquals("FeedVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									value = value.replaceAll("^^<http://www.w3.org/2001/XMLSchema#int>", "");
									String pattern ="-?\\d+";
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										feedVolumeValue = i2;
									}
									
								}
							} // ?PNVolume ?PNAdvance ?PNAdditive"
							if (var.contentEquals("FeedAdvancement")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									//value = value.replaceAll("^^<http://www.w3.org/2001/XMLSchema#int>", "");
									//System.out.println(value);
									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									String pattern ="-?\\d+";
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										feedIncrementValue = i2;
									}
									// System.out.println("FeedAdvancement........"+ "" + value);
									
								}
							}
							if (var.contentEquals("FinalFeedVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										feedVolumeValuefinal = i2;
									}									// System.out.println("FeedAdvancement........"+ "" + value);
									else {
										feedVolumeValuefinal=null;
									}
								}
							}
							if (var.contentEquals("FinalFeedAdvancement")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										feedIncrementValuefinal = i2;
									}
									else {
										feedIncrementValuefinal=null;
									}
										// System.out.println("FeedAdvancement........"+ "" + value);									
								}
							}
							if (var.contentEquals("CHOInitialPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNVolume........"+ "" + value);

									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										CHOInitialPNVolume = i2;
									}										
									// System.out.println("InitialPNVolume........"+ "" + InitialPNVolume);
								}
							}

							if (var.contentEquals("CHOFinalPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("FinalPNVolume........"+ "" + value);

									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");

									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										CHOFinalPNVolume = i2;
									}										
									// System.out.println("FinalPNVolume........"+ "" + FinalPNVolume);
								}
							}
							if (var.contentEquals("CHOInitialPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNAdvance........"+ "" + value);

									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										CHOInitialPNAdvance = i2;
									}									
									// System.out.println("InitialPNAdvance........"+ "" + InitialPNAdvance);
								}

							}
							if (var.contentEquals("CHOFinalPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("PFinalNAdvance........"+ "" + value);

									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										CHOFinalPNAdvance = i2;
									}										

									// System.out.println("PFinalNAdvance........"+ "" + FinalPNAdvance);
								}

							}

							if (var.contentEquals("ProteinInitialPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNVolume........"+ "" + value);

									//value = value.replaceAll(
									//		"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									//if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										ProteinInitialPNVolume = i2;
									//}										
									// System.out.println("InitialPNVolume........"+ "" + InitialPNVolume);
								}
							}

							if (var.contentEquals("ProteinFinalPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("FinalPNVolume........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");

									//if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i3 = Float.parseFloat(value);
										ProteinFinalPNVolume = i3;
									//}										
									// System.out.println("FinalPNVolume........"+ "" + FinalPNVolume);
								}
							}
							if (var.contentEquals("ProteinInitialPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNAdvance........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										ProteinInitialPNAdvance = i2;
									}										
									// System.out.println("InitialPNAdvance........"+ "" + InitialPNAdvance);
								}

							}
							if (var.contentEquals("ProteinFinalPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("PFinalNAdvance........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										ProteinFinalPNAdvance = i2;
									}										

									// System.out.println("PFinalNAdvance........"+ "" + FinalPNAdvance);
								}

							}
							
							if (var.contentEquals("SodiumInitialPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNVolume........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										SodiumInitialPNVolume = i2;
									}								
									// System.out.println("InitialPNVolume........"+ "" + InitialPNVolume);
								}
							}

							if (var.contentEquals("SodiumFinalPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("FinalPNVolume........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");

									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										SodiumFinalPNVolume = i2;
									}									
									// System.out.println("FinalPNVolume........"+ "" + FinalPNVolume);
								}
							}
							if (var.contentEquals("SodiumInitialPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNAdvance........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										SodiumInitialPNAdvance = i2;
									}									
									// System.out.println("InitialPNAdvance........"+ "" + InitialPNAdvance);
								}

							}
							if (var.contentEquals("SodiumFinalPNAdvance")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("PFinalNAdvance........"+ "" + value);

									//value = value.replaceAll(
											//"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										SodiumFinalPNAdvance = i2;
									}										

									// System.out.println("PFinalNAdvance........"+ "" + FinalPNAdvance);
								}

							}
							
							if (var.contentEquals("PotassiumInitialPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNVolume........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										PotassiumInitialPNVolume = i2;
									}									
									// System.out.println("InitialPNVolume........"+ "" + InitialPNVolume);
								}
							}

							
							
							if (var.contentEquals("CalciumInitialPNVolume")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("InitialPNVolume........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										CalciumInitialPNVolume = i2;
									}									
									// System.out.println("InitialPNVolume........"+ "" + InitialPNVolume);
								}
							}

							if (var.contentEquals("InitialEnergy")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("PFinalNAdvance........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										InitialEnergy = i2;
									}										

									// System.out.println("PFinalNAdvance........"+ "" + FinalPNAdvance);
								}

							}if (var.contentEquals("FinalEnergy")) {
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									// System.out.println("PFinalNAdvance........"+ "" + value);

									//value = value.replaceAll(
										//	"http://www.childhealthimprints.com/NutritionalGuidelines/", "");
									if(value.matches("-?\\d+")){ // any positive or negetive integer or not!
										float i2 = Float.parseFloat(value);
										FinalEnergy = i2;
									}										
									// System.out.println("PFinalNAdvance........"+ "" + FinalPNAdvance);
								}

							}
							

							if (var.contentEquals("central")) {
								central_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										central = value;
										central_s = "CentralPeripheralDifference";
										//reason=central_s;
									} else
										central_s = "";
									// System.out.println("centralperipheraldifference........"+ central);
								}
							}

							if (var.contentEquals("vomitcolor")) {
								vomitcolor_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										vomitcolor = value;
										vomitcolor_s = "  Vomitcolor:" + vomitcolor;
										reason=reason+vomitcolor_s;
									} else
										vomitcolor_s = "";
									// System.out.println("vomitcolor........"+ vomitcolor);
								}
							}
							if (var.contentEquals("abdtender")) {
								abdominaltender_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										abdominaltenderness = value;
										abdominaltender_s = "  Abdominaltenderness";
										reason=reason+abdominaltender_s;
									} else
										abdominaltender_s = "";
									// System.out.println("abdominaltender........"+ abdominaltenderness);
								}
							}

							if (var.contentEquals("abddistension")) {
								abdominaldist_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										abdominaldistension = value;
										abdominaldist_s = "  AbdominalDistension";
										reason=reason+abdominaldist_s;

									} else
										abdominaldistension = "";
									// System.out.println("abdominaldistension........"+ abdominaldistension);
								}
							}
							if (var.contentEquals("visiblebowel")) {
								visiblebowel_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										visiblebowel = value;
										visiblebowel_s = "  VisibleBowel";
										reason=reason+visiblebowel_s;

									} else
										visiblebowel = "";
									// System.out.println("visiblebowel........"+ visiblebowel);
								}
							}
							if (var.contentEquals("bloodstool")) {
								bloodstool_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										bloodstool = value;
										bloodstool_s = "  BloodinStool:";
										reason=reason+bloodstool_s;

									} else
										bloodstool_s = "";
									// System.out.println("bloodinstool........"+ bloodstool);
								}
							}
							if (var.contentEquals("metabolic")) {
								metabolic_s = "";
								RDFNode node = qs.get(var);
								// System.out.println("Node........"+ node);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										metabolic = value;
										metabolic_s = "  MetabolicAcidosis:";
										reason=reason+metabolic;

									} else
										metabolic_s = "";
									// System.out.println("metabolicacidosis......."+ details1);
								}
							}
							if (var.contentEquals("Apnea")) {
								apnea_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										apnea = value;
										apnea_s = "  Apnea";
										reason=reason+apnea_s;

									} else
										apnea_s = "";
								}
							}
							if (var.contentEquals("respDistress")) {
								respdistress_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										respiratorydistress = value;
										respdistress_s = "  RespiratoryDistress";
										reason=reason+respdistress_s;

									} else
										respdistress_s = "";
								}
							}
							if (var.contentEquals("Lethargy")) {
								lethargy_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										lethargy = value;
										lethargy_s = "  Lethargy";
										reason=reason+lethargy_s;

									} else
										lethargy_s = "";
									// System.out.println("haslethargy........"+ lethargy);
								}
							}

							if (var.contentEquals("plateletcount")) {
								plateletcount_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										plateletcount = value;
										plateletcount_s = "  hasplateletcount:" + plateletcount;
										reason=reason+plateletcount_s;

										
									} else
										plateletcount_s = "";
									// System.out.println("hasplateletcount........"+ plateletcount);
								}
							}

							if (var.contentEquals("skindiscolor")) {
								skindiscolor_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										skindiscolor = value;
										skindiscolor_s = "  SkinDiscolor";
										reason=reason+skindiscolor_s;

									} else
										skindiscolor_s = "";
									// System.out.println("hasskindiscolor........"+ skindiscolor);
								}
							}
							if (var.contentEquals("gastric")) {
								gastricaspirate_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										gastricaspirate = value;
										gastricaspirate_s = "  GastricAspirate";
										reason=reason+gastricaspirate_s;

										// System.out.println("gastricaspirate........"+ gastricaspirate);
									} else
										gastricaspirate_s = "";
								}
							}
							if (var.contentEquals("CFL")) {
								CFT_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										CFT = value;
										CFT_s = "  LongCapillaryRefillTime";
										reason=reason+CFT_s;

										// System.out.println("CFL........"+ CFT);
									} else
										CFT_s = "";
								}
							}

							if (var.contentEquals("techycardia")) {
								techycardia_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										techycardia = value;
										techycardia_s = "  Techycardia";
										reason=reason+techycardia_s;

									} else
										techycardia_s = "";
									// System.out.println("hastechycardia........"+ techycardia);
								}
							}
							if (var.contentEquals("coldperi")) {
								coldperi_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										coldperi = value;
										coldperi_s = "  ColdPeripheries";
										reason=reason+coldperi_s;

										// System.out.println("coldperi........"+ coldperi);
									} else
										coldperi_s = "";
								}
							}
							if (var.contentEquals("NEC")) {
								NEC_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										NEC = value;
										NEC_s = "  NecrotizingEnterocolitis";
										reason=reason+NEC_s;

									} else
										NEC_s = "";
									// System.out.println("NEC........"+ NEC);
								}
							}
							
							
							
							if (var.contentEquals("BP")) {
								bp_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										BPvalue = value;
										bp_s = "    hasBP:" + BPvalue;
										reason=reason+bp_s;

									} else
										bp_s = "";
									// System.out.println("hasBP........"+ BP);
								}
							}
							

							if (var.contentEquals("feedint")) {
								feedint_s = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										feedint_s = "  FeedIntolerance";
										reason=reason+feedint_s;

									} else
										feedint_s = "";
									// System.out.println("hasBP........"+ BP);
								}

						    }
							
							if (var.contentEquals("reverseendflow")) {
								reverse = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										reverse = "  ReverseEndDiastolicFlow ";
										reason=reason+reverse;

									} else
										reverse = "";
									// System.out.println("hasBP........"+ BP);
								}

							}
							if (var.contentEquals("stoolpassed")) {
								stool = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("true^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										stool = "  stool not passed";
										reason=reason+stool;
									} else
										stool = "";
									// System.out.println("hasBP........"+ BP);
								}

							}
							if (var.contentEquals("absentdiastolic ")) {
								diastolic = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("true^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										diastolic = " AEDF:";
										reason=reason+ diastolic;

									} else
										diastolic = "";
									// System.out.println("hasBP........"+ BP);
								}

							}
							
							if (var.contentEquals("lactate ")) {
								inlactate = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (!value
											.contentEquals("true^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										inlactate = "  IncreasedLactate";
										reason=reason+inlactate;

									} else
										inlactate = "";
									// System.out.println("hasBP........"+ BP);
								}

							}
							if (var.contentEquals("resus ")) {
								resus = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									if (!value.contentEquals("nil") & (value
											.contentEquals("true^^http://www.w3.org/2001/XMLSchema#boolean"))) {
										resus = " hasResuscitation";
										reason=reason+"and"+resus;

									} else
										resus = "";
									// System.out.println("hasBP........"+ BP);
								}

							}
							
							if (var.contentEquals("NPO")) {
								resus = "";
								RDFNode node = qs.get(var);
								if (node != null) {
									value = node.toString();
									
									trackrules="NPO";

									} else
										resus = "";
									// System.out.println("hasBP........"+ BP);
								}

							
							Hashtable<String, String> my_dict = new Hashtable<String, String>();

				      			
				      			for (StmtIterator i1 = inf.listStatements(); i1.hasNext(); ) {
				      				org.apache.jena.rdf.model.Statement s = i1.nextStatement();
				      			
				      				if(s.getSubject().toString().equalsIgnoreCase(med+uhid+"_"+dolValue) &&
				      				(
				      				(s.getPredicate().toString().contains("http://www.childhealthimprints.com/NutritionalGuidelines/hasFeedIntoleranceSign"))
				      				|| (s.getPredicate().toString().contains("http://www.childhealthimprints.com/NutritionalGuidelines/hasDiseaseNecrotizingEnterocolitis"))
				      				|| (s.getPredicate().toString().contains("http://www.childhealthimprints.com/NutritionalGuidelines/hasDiseaseNPO"))
				      				|| (s.getPredicate().toString().contains("http://www.childhealthimprints.com/NutritionalGuidelines/hasFeedIntolerance"))
				      				)
				      				)
				      				 		for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
						        	        Derivation deriv = (Derivation) id.next();  
											List<Rule> ruless=(reasoner.getRules());
											sign=(String) data.get(convert(deriv.toString(),uhid));
							      			
				      				 		}
				      			}
				      						      			
						}
						
						if(sign!=null) {if(trackrules.contains(sign)){} else {trackrules=trackrules+" "+(sign);}}
						
						//Shubham Code
						CaclulatorDeficitPOJO cuurentDeficitLast = new CaclulatorDeficitPOJO();
						Timestamp startingTimee = new Timestamp(creationTime.getTime());
						Timestamp endingTime = new Timestamp(nextTime.getTime());
						List<BabyfeedDetail> feedListObj = (List<BabyfeedDetail>) notesDoa.getListFromMappedObjNativeQuery(
								HqlSqlQueryConstants.getBabyfeedDetailList(obj[0].toString(), startingTimee, endingTime));
						
						//5768 to 5773
						HashMap<String,Float> map = new HashMap<String, Float>();
						map.put("feed",(float) 0);
						map.put("energy",(float) 0);
						if(!BasicUtils.isEmpty(feedListObj)) {
							BabyfeedDetail feed = feedListObj.get(0);
							gotFirstOrder = true;
							String nutritionCalculator = "select obj from RefNutritioncalculator obj";
							List<RefNutritioncalculator> nutritionList = notesDoa.getListFromMappedObjNativeQuery(nutritionCalculator);
							
							map = getDeficitFeedCalculatorInput(obj[0].toString(), feed, nutritionList, feed.getWorkingWeight() , creationTime,nextTime,"order");
						
						}
						
					
						// 5813 to 5828
						if(!firstLoop) {
							creationTime.setHours(8);
							creationTime.setMinutes(0);
							nextTime.setHours(8);
							nextTime.setMinutes(0);
						}
						if(!firstLoop) {
							if(isTodayEntry)
								creationTime = new Timestamp(creationTime.getTime());
							else
								creationTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
						}else {
							creationTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));
						}
						firstLoop = true;
						nextTime = new Timestamp(creationTime.getTime() + (1440 * 60 * 1000));

			      		System.out.println(creationTime+"............................................................................................");	
			      		
			      		
		    		
			      		//*************************************************************************MYCODE*****************************************************
		      		    String insertType = null;
       
			      	  if(prevdolValue!=dolValue) {
			      		if (!trackrules.isEmpty()) {
			      			System.out.println("potassiuminiyial"+PotassiumInitialPNVolume);
							System.out.println("calciuminitial"+CalciumInitialPNVolume);
							System.out.println("initialenergy"+InitialEnergy);
							System.out.println("finalenergy"+FinalEnergy);
							System.out.println("trackrules"+trackrules);

							insertType = "insert into " + BasicConstants.SCHEMA_NAME
									+ ".nutritional_compliance (uhid, dol, feed_volume,upper_feed_volume, feed_advancement, "
									+ " upper_feed_advancement,cho_lower_pn_feed_volume, cho_upper_pn_feed_volume, cho_lower_pn_feed_advancement, cho_upper_pn_feed_advancement,"
									+ "protein_lower_pn_feed_volume, protein_upper_pn_feed_volume, protein_lower_pn_feed_advancement, protein_upper_pn_feed_advancement, "
									+ "sodium_lower_pn_feed_volume, sodium_upper_pn_feed_volume,"
									+ "potassium_pn_feed_volume, calcium_pn_feed_volume, initial_energy, final_energy , details, given_feed, given_energy) VALUES ("
									+ "'" + uhidValue + "'," + dolValue + "," + feedVolumeValue + ","
									+ feedVolumeValuefinal + "," + feedIncrementValue + "," + feedIncrementValuefinal
									+ "," + CHOInitialPNVolume + "," + CHOFinalPNVolume + "," + CHOInitialPNAdvance
									+ "," + CHOFinalPNAdvance + "," + ProteinInitialPNVolume + ","
									+ ProteinFinalPNVolume + "," + ProteinInitialPNAdvance + "," + ProteinFinalPNAdvance+","
									+ SodiumInitialPNVolume + ","+ SodiumFinalPNVolume + "," + 
									+ PotassiumInitialPNVolume +  ","+CalciumInitialPNVolume 
									+ "," + InitialEnergy + "," + FinalEnergy
									+ ",'" + trackrules + "'," + map.get("feed") + "," + map.get("energy")
									
									+")";
							
							//String insertType1 =convert(data.get("insertType").toString(), uhid);
							settingDao.executeInsertQuery(insertType);
						}

			      		else if (trackrules.isEmpty()){
							System.out.println(PotassiumInitialPNVolume);
							System.out.println(CalciumInitialPNVolume);
							System.out.println(InitialEnergy);
							System.out.println(FinalEnergy);

							if(PotassiumInitialPNVolume == null)	PotassiumInitialPNVolume = 0f;
							if(CalciumInitialPNVolume == null)	CalciumInitialPNVolume = 0f;

							insertType =  
									"insert into " + BasicConstants.SCHEMA_NAME
									+ ".nutritional_compliance (uhid, dol, feed_volume,upper_feed_volume, feed_advancement, "
									+ " upper_feed_advancement, cho_lower_pn_feed_volume, cho_upper_pn_feed_volume, cho_lower_pn_feed_advancement, cho_upper_pn_feed_advancement,"
									+ "protein_lower_pn_feed_volume, protein_upper_pn_feed_volume, protein_lower_pn_feed_advancement, protein_upper_pn_feed_advancement,"
									+ "sodium_lower_pn_feed_volume, sodium_upper_pn_feed_volume," 
									+"potassium_pn_feed_volume,calcium_pn_feed_volume, initial_energy, final_energy, given_feed, given_energy) VALUES ("
									+ "'" + uhidValue + "'," + dolValue + "," + feedVolumeValue + ","
									+ feedVolumeValuefinal + "," + feedIncrementValue + "," + feedIncrementValuefinal
									+ "," + CHOInitialPNVolume + "," + CHOFinalPNVolume + "," + CHOInitialPNAdvance
									+ "," + CHOFinalPNAdvance + "," + ProteinInitialPNVolume + ","
									+ ProteinFinalPNVolume + "," + ProteinInitialPNAdvance + "," + ProteinFinalPNAdvance+","
									+ SodiumInitialPNVolume + ","+ SodiumFinalPNVolume + "," + 
									+ PotassiumInitialPNVolume + ","+ CalciumInitialPNVolume + "," + InitialEnergy + "," + FinalEnergy + "," + map.get("feed") + "," + map.get("energy")
									+ ")";
							//String insertType2 =convert(data.get("insertType1").toString(), uhid);
		
							settingDao.executeInsertQuery(insertType);	 
			      		}
			      		
			      	  }   
			      		prevdolValue=dolValue;

					} // end of while loop 

				} // end of uhid loop
			} 
			// end of try
	

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}




