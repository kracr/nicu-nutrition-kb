package com.inicu.postgres.nutrionalComplaince;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.inicu.postgres.dao.InicuDao;
import com.inicu.postgres.dao.PatientDao;
import com.inicu.postgres.dao.SettingDao;
import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyPrescription;
import com.inicu.postgres.entities.BirthToNicu;
import com.inicu.postgres.entities.DeviceBloodgasDetailDetail;
import com.inicu.postgres.entities.NursingBloodGas;
import com.inicu.postgres.entities.NursingEpisode;
import com.inicu.postgres.entities.NursingIntakeOutput;
import com.inicu.postgres.entities.NursingVitalparameter;
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

import antlr.StringUtils;
import scala.Int;

import org.apache.jena.query.QuerySolution;


/**
 * Hello world!
 *
 */

@Repository
public class NutritionalOntology extends Thread {

static final String inputFileName = "NutritionOntology.owl";
static Property hasGestationalAgeAtBirth;
static Property hasFeedIntolerance;
static Property hasDayOfLife;
static Property hasFeedingAdvancement;
static Property hasFeedingVolume;
static Property hasRiskFactor;
static Property givenFeedVolume;
static Individual ins;
static Individual unique;
static Property hasBaseDeficitValue;//
static Property hasCordGaspHValue; //
static Property hasMonoTwinStatus;//
static Property pressorGiven;//
//nursing_ventilaor-FI02
//PPHN:sa_resp_pphn
//ref_fluidtype


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
	SettingDao settingDao;
	
	private static final Logger logger = LoggerFactory.getLogger(LabReceiver.class);
	
	@Override
	public void run(){

	try {
		
		Date datetime1 = null;
		Connection con = null;

		int dayoflife = 0; //this is a field
		Date entrydatetime = null;
		Date dobandtime = null;
		String gestationalweek = null;
		String totalfeed_ml_per_kg_day;
		Double workingweight=(double) 1;
		Dictionary geek = new Hashtable(); 
		String extnum;
		Property hasBabyID;
		String [] nextLine;
		String ph = null;    //
		String be =null;    //
	   	String chest_comp_time = null;   //
  	
	  //**input ontology and define properties
		String queryCurrentUhidsList = "SELECT uhid from "+ BasicConstants.SCHEMA_NAME + ".baby_detail where admissionstatus='t' and gestationweekbylmp is not null";
		List<String>activeUhidsList = patientDao.getListFromNativeQuery(queryCurrentUhidsList);

        for(String uhid : activeUhidsList){
	       	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_LITE_MEM, null );
	 		// use the FileManager to find the input file
	 		InputStream in = new FileInputStream(inputFileName);
	 		model.read(in, "");		
	 		String med = "http://www.childhealthimprints.com/SampleOntology/Testing/";
	 		org.apache.jena.rdf.model.Resource r = model.getResource(med);	 		 
	 		hasDayOfLife=model.getProperty(med,"hasDayOfLife");	 
	 		hasFeedIntolerance=model.getProperty(med,"hasFeedIntolerance");
	 		hasGestationalAgeAtBirth=model.getProperty(med,"hasGestationalAgeAtBirth");      		 
	 		hasFeedingVolume=model.getProperty(med,"hasFeedingVolume");
	 		hasFeedingAdvancement=model.getProperty(med,"hasFeedingAdvancement");
	 		hasRiskFactor=model.getProperty(med,"hasRiskFactor");
	 		givenFeedVolume=model.getProperty(med,"givenFeedVolume");
	 		hasMonoTwinStatus=model.getProperty(med,"hasMonoTwinStatus");   //
	 		hasCordGaspHValue=model.getProperty(med,"hasCordGaspHValue");   //
	 		hasBaseDeficitValue=model.getProperty(med,"hasBaseDeficitValue");    //
	 		pressorGiven = model.getProperty(med,"pressorGiven");    //
	 		 
	 		Property hasChestCompressionDuration = model.getProperty(med,"hasChestCompressionDuration");    //
 		 	Property hasVomitColor = model.getProperty(med,"hasVomitColor");    //
 		 	Property hasVomitVolume = model.getProperty(med,"hasVomit");    //
 		 	Property hasAbdominalDistension=model.getProperty(med,"hasAbdominalDistension");
	 		Property hasSkinDiscoloration=model.getProperty(med,"hasSkinDiscoloration");
	 		Property hasAbdominalTenderness=model.getProperty(med,"hasAbdominalTenderness");
	 		Property hasVisibleBowelLoop=model.getProperty(med,"hasVisibleBowelLoop");
	 		Property bloodPresentInStool=model.getProperty(med,"bloodPresentInStool");
	 		Property hasMetabolicAcidosis=model.getProperty(med,"hasMetabolicAcidosis");
	 		Property hasApnea=model.getProperty(med,"hasApnea");
	 		Property hasRespiratoryDistress=model.getProperty(med,"hasRespiratoryDistress");
	 		Property hasLethargy=model.getProperty(med,"hasLethargy");
	 		Property hasTemperatureInStability=model.getProperty(med,"hasTemperatureInStability");
	 	    Property haslowPlateletCount=model.getProperty(med,"haslowPlateletCount");
	 	    Property hasGastricAspirate=model.getProperty(med,"hasGastricAspirate");
	 	    Property hasXRayAbdominStatus=model.getProperty(med,"hasX-RayAbdominStatus");
	 		Property hasGastricAspirateAbnormalColor=model.getProperty(med,"hasGastricAspirateAbnormalColor");
	 	    Property hasLongCapillaryRefillTime=model.getProperty(med,"hasLongCapillaryRefillTime");
	 		Property hasCentralPeripheralDifference=model.getProperty(med,"hasCentralPeripheralDifference");
	 		Property hasTechycardia=model.getProperty(med,"hasTechycardia");
	 		Property hasBloodPressure=model.getProperty(med,"hasBloodPressure");
	 		Property hasLowBloodPressure=model.getProperty(med,"hasLowBloodPressure");
	 		Property hasColdPeripheries=model.getProperty(med,"hasColdPeripheries");
	 		Property hasLactateLevel=model.getProperty(med,"hasLactateLevel");
	 		Property hasNecrotizingEnterocolitis=model.getProperty(med,"hasNecrotizingEnterocolitis");
	 		Property hasUniqueID=model.getProperty(med,"hasUniqueID");
	 		Property hasFeedIntoleranceSign=model.getProperty(med,"hasFeedInttoleranceSign");

	 		

	        String query1="select obj from BabyDetail as obj where uhid='"+uhid+"'"; 
	        List<BabyDetail> babyDetailList = patientDao.getListFromMappedObjNativeQuery(query1);

	        System.out.println(babyDetailList.get(0).getDateofadmission()+" "+babyDetailList.get(0).getTimeofadmission()+" "+babyDetailList.get(0).getTimeofbirth()+" "+babyDetailList.get(0).getDateofbirth()+" "+babyDetailList.get(0).getGestationweekbylmp()+" "+babyDetailList.get(0).getGestationdaysbylmp());  
	             
			String time1=babyDetailList.get(0).getTimeofbirth();
			gestationalweek=babyDetailList.get(0).getGestationweekbylmp().toString();
			String replacetime=time1.replaceFirst(",",":");
			String reptime=replacetime.replaceFirst(","," ");
			 
			Date dob=babyDetailList.get(0).getDateofbirth();
			
			String timeofadmission = babyDetailList.get(0).getTimeofadmission();
			Date dateofadmission = babyDetailList.get(0).getDateofadmission();
			 
			// System.out.println(time1+"....time..");
			 //System.out.println(dob+"......dob.....");
			
			
			SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
			Date date = (Date) parseFormat.parse(reptime);
			// System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
			 
			String startingDate = new SimpleDateFormat("yyyy-MM-dd").format((dob));
			
			String startingTime = new SimpleDateFormat("hh:mm:ss").format(date);
	 
			String DateTime=startingDate+" "+startingTime;
			 
			SimpleDateFormat parseFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			datetime1 = (Date) parseFormat1.parse(DateTime);
			
			
			String replacetoa=timeofadmission.replaceFirst(",",":");
			String reptime_toa=replacetoa.replaceFirst(","," ");
			System.out.println(reptime_toa+"....time..");
			System.out.println(dateofadmission+"......doa.....");


			String startingDate1 = new SimpleDateFormat("yyyy-MM-dd").format((dateofadmission));
			Date date1 = (Date) parseFormat.parse(reptime_toa);

			String startingTime1 = new SimpleDateFormat("hh:mm:ss").format(date1);
			 
			 
			String DateTime1=startingDate1+" "+startingTime1;

			SimpleDateFormat parseFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Date doatoa = (Date) parseFormat2.parse(DateTime1);   //dateofadmission and time of admission
			System.out.println(doatoa+"....doame..");
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

		    Date newdate = new Date();  
		    String entry = formatter.format(newdate);

		    Date datetime2 = (Date) formatter.parse(entry);

          
            long currentdiff = datetime2.getTime()-datetime1.getTime();
            long doa_diff=doatoa.getTime()-datetime1.getTime();
            int doa_DOL = (int) TimeUnit.MILLISECONDS.toDays(doa_diff);

 		    System.out.println((doa_DOL)+"doa");  

            int currentDOL = (int) TimeUnit.MILLISECONDS.toDays(currentdiff);
  		    System.out.println((currentDOL)+"currentd"); 
  		    Individual uniquen;
  		    for(long p=doa_DOL;p<=currentDOL;p++) {
  		    	if(model.getIndividual(med+uhid+"_"+p) != null) 
	   	   			uniquen = (Individual) model.getIndividual(med+uhid+"_"+p);
	   	   		else
	   	   			uniquen = (Individual) model.createIndividual(med+uhid+"_"+p,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	    		model.addLiteral(uniquen,hasVomitVolume,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasVomitColor,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasAbdominalTenderness,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasAbdominalDistension,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasVisibleBowelLoop,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,bloodPresentInStool,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasMetabolicAcidosis,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasApnea,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasRespiratoryDistress,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasLethargy,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,haslowPlateletCount,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasSkinDiscoloration,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasFeedIntoleranceSign,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasGastricAspirateAbnormalColor,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasLongCapillaryRefillTime,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasCentralPeripheralDifference,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasBloodPressure,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasTechycardia,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasColdPeripheries,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("nil"));
	    		model.addLiteral(uniquen,hasLowBloodPressure,ResourceFactory.createTypedLiteral("nil"));
  		    }
	 				    
  		    String query4="select  obj from BirthToNicu as obj  WHERE uhid="+ "'"+uhid+"'"; 
  		    List<BirthToNicu> birthNicuList = patientDao.getListFromMappedObjNativeQuery(query4);
  		    String chestcomp=null;
 	        
  	        if(!BasicUtils.isEmpty(birthNicuList) && !BasicUtils.isEmpty(birthNicuList.get(0).getChestCompTime()))
  	        	chestcomp = birthNicuList.get(0).getChestCompTime();          
 	        

 	         
  	         
  	        String query5=" Select obj from NursingBloodGas as obj  WHERE uhid="+"'"+uhid+"'"+"AND be is NOT NULL order by entrydate"; 
  	        List<NursingBloodGas> nursingBloodGasList = patientDao.getListFromMappedObjNativeQuery(query5);
  	        
  	        if(!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getPh()))
	        	ph = nursingBloodGasList.get(0).getPh();
  	     
  	        if(!BasicUtils.isEmpty(nursingBloodGasList) && !BasicUtils.isEmpty(nursingBloodGasList.get(0).getBe()))
	        	be = nursingBloodGasList.get(0).getBe();
  	        
  	        
  	     
  	        boolean twin_status = false;
	        if(!BasicUtils.isEmpty(babyDetailList) && !BasicUtils.isEmpty(babyDetailList.get(0).getMonoamniotic()) && babyDetailList.get(0).getMonoamniotic() == true)
	        	twin_status = true;      
  	   
	     
 	        boolean val = false;
 	       
 	        String  query51=" Select obj from BabyPrescription as obj  WHERE uhid="+"'"+uhid+"'"; 
 	        List<BabyPrescription> babyPrescriptionList = patientDao.getListFromMappedObjNativeQuery(query51);
	        
	        if(!BasicUtils.isEmpty(babyPrescriptionList) && !BasicUtils.isEmpty(babyPrescriptionList.get(0).getMedicationtype()) && babyPrescriptionList.get(0).getMedicationtype().equalsIgnoreCase("TYPE0004"))
	        	val = true;

	        
	        Individual unique1;
	        Date entry_timestamp = null;
	        String vomit_color = null;

            String vomit = null;
	        String vomit_DOL = null;
	          
	       
	        String query6=" select  nursingintakeobj from NursingIntakeOutput as nursingintakeobj WHERE uhid="+"'"+uhid+"'"+"order by entry_timestamp";
	        List<NursingIntakeOutput> NursingIntakeOutputList = patientDao.getListFromMappedObjNativeQuery(query6);
	        for(NursingIntakeOutput nursingintakeobj : NursingIntakeOutputList){
	        	
	        	if(!BasicUtils.isEmpty(nursingintakeobj.getVomit()))
			    	vomit = nursingintakeobj.getVomit();       

			    if(!BasicUtils.isEmpty(nursingintakeobj.getVomitColor()))
			    	vomit_color = nursingintakeobj.getVomitColor();  

			    if(!BasicUtils.isEmpty(nursingintakeobj.getEntry_timestamp()))
			    	entry_timestamp = nursingintakeobj.getEntry_timestamp();  

			    if(entry_timestamp!=null) {
			    	long cudiff = datetime2.getTime()-entry_timestamp.getTime();
			    	int nursingtime = (int) TimeUnit.MILLISECONDS.toDays(cudiff);
			    	System.out.println(nursingtime+"nursing time");  			   			   
			    	unique1 = (Individual) model.createIndividual(med+uhid+"_"+nursingtime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));	    
			    	if(vomit_color!=null) {
			    		if((vomit_color=="blood stained")||(vomit_color=="bloody")) {
			    			model.addLiteral(unique1,hasVomitColor,ResourceFactory.createTypedLiteral("true"));
			    		 }
			    	}
			    	else if(vomit_color==null){
		    			model.addLiteral(unique1,hasVomitColor,ResourceFactory.createTypedLiteral("nil"));
			    	}
			    	if(vomit!=null){
			    		model.addLiteral(unique1,hasVomitVolume,ResourceFactory.createTypedLiteral(vomit));
			    	}
			    }
	        }
    	          	  
   		    
			String query7="select feedintobj  from SaFeedIntolerance as feedintobj WHERE uhid="+"'"+uhid+"'"+" order by assessment_time";

			String abdominSign=null;
			Date assessment_time=null;
            List<SaFeedIntolerance> FeedIntoleranceList = patientDao.getListFromMappedObjNativeQuery(query7);
	        for(SaFeedIntolerance feedintobj : FeedIntoleranceList) {     
	        	if(!BasicUtils.isEmpty(feedintobj.getAssessmentTime()))
	        		assessment_time = feedintobj.getAssessmentTime();       
		      
	        	if(!BasicUtils.isEmpty(feedintobj.getAbdominalSigns()))
	        		abdominSign = feedintobj.getAbdominalSigns();
		    
	        	if(assessment_time!=null) {
	        		long assess = datetime2.getTime()-assessment_time.getTime();
	        		int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);      
	        		Individual unique_2 = null;   
	        		if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	        			unique_2 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);	
	        		else
	        			unique_2 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));  			
	        		if(abdominSign!=null) {
	        			if(abdominSign.contains("Erythema")){
	        				model.addLiteral(unique_2, hasAbdominalTenderness,ResourceFactory.createTypedLiteral("true"));
	        			}
	        			if(abdominSign.contains("Abdominal Distension")){
	        				model.addLiteral(unique_2, hasAbdominalDistension,ResourceFactory.createTypedLiteral("true"));
	        			}
	        		}
	        
	        		String abdomin_distinction = null;	  		 
	        		if(!BasicUtils.isEmpty(feedintobj.getAbdominalDistinctionValue()))
	        			abdomin_distinction =  feedintobj.getAbdominalDistinctionValue();	         
	        			Individual unique_3 = null; 
	        			if(model.getIndividual(med+uhid+"_"+assesstime) != null)    
	        				unique_3 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	        			else 
	        				unique_3 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));	   
	        			if(abdomin_distinction!=null) {
	        				if(abdomin_distinction.contains("Visible Loops")||(abdomin_distinction.contentEquals("Visible Loops")))
	        					model.addLiteral(unique_3, hasVisibleBowelLoop,ResourceFactory.createTypedLiteral("true"));
	        				}
	        	}
	   	   }
	        
	        
		   Date entrydate=null;
	       for(NursingBloodGas obj : nursingBloodGasList) {     
	    	   if(!BasicUtils.isEmpty(obj.getEntryDate()))
	    		   assessment_time = obj.getEntryDate();
	    	   if(assessment_time!=null) {
	    		   if(be!=null) {
	    			   float baseaccess=Float.parseFloat(be);
	    			   long assess1 = datetime2.getTime()-assessment_time.getTime();
	    			   int assesstime1 = (int) TimeUnit.MILLISECONDS.toDays(assess1);      
	    			   Individual unique_5 = null; 
	   
	    			   if(model.getIndividual(med+uhid+"_"+assesstime1) != null)
	    				   unique_5 = (Individual) model.getIndividual(med+uhid+"_"+assesstime1);
	    			   else
	    				   unique_5 = (Individual) model.createIndividual(med+uhid+"_"+assesstime1,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));   
	    			   if(baseaccess<-6){
	    				   model.addLiteral(unique_5,hasMetabolicAcidosis,ResourceFactory.createTypedLiteral("true"));
	    			   }
	    		   }
	    	   }
	       }
	       
       	  String query13=" Select nursingepiobj from NursingEpisode as nursingepiobj WHERE uhid="+"'"+uhid+"'"+"order by modificationtime";
       	  List<NursingEpisode> nursingepisodeList = patientDao.getListFromMappedObjNativeQuery(query13);
	      for(NursingEpisode nursingepiobj : nursingepisodeList) {     

	    	  if(!BasicUtils.isEmpty(nursingepiobj.getModificationtime()))
	    		  assessment_time = nursingepiobj.getModificationtime();   
	    	  	  Boolean apnea=null;
	    	  if (!BasicUtils.isEmpty(nursingepiobj.getApnea()))
	    		  apnea = nursingepiobj.getApnea(); 
	      
	    	  if((assessment_time!=null) && apnea!=null) {  
	    		  long assess11 = datetime2.getTime()-assessment_time.getTime();
	    		  int assesstime11 = (int) TimeUnit.MILLISECONDS.toDays(assess11);       
	    		  Individual unique_6 = null; 	   
	    		  if(model.getIndividual(med+uhid+"_"+assesstime11) != null)       
	    			  unique_6 = (Individual) model.getIndividual(med+uhid+"_"+assesstime11);
	    		  else 
	    			  unique_6 = (Individual) model.createIndividual(med+uhid+"_"+assesstime11,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	    		//  model.addLiteral(unique_6,hasApnea,ResourceFactory.createTypedLiteral(apnea));
	    		  if(apnea!=null) {
		    		  model.addLiteral(unique_6,hasApnea,ResourceFactory.createTypedLiteral(apnea));
	    		  }
	    	  }
	      }
	      
	     
 	     String query14=" Select saRdsobj from SaRespRds as saRdsobj WHERE uhid="+"'"+uhid+"'"+" order by assessment_time";
	     String resp_system = null; 	  
	     List<SaRespRds> SaRespRdsList = patientDao.getListFromMappedObjNativeQuery(query14);	
	     
	     for(SaRespRds saRdsobj : SaRespRdsList) {     
	    	 
	    	 if(!BasicUtils.isEmpty(saRdsobj.getAssessmentTime()))
	    		 assessment_time = saRdsobj.getAssessmentTime(); 
	 	 
	    	 if(!BasicUtils.isEmpty(saRdsobj.getRespSystemStatus()))
	    		 resp_system  = saRdsobj.getRespSystemStatus(); 
	 	 
	    	 if((assessment_time!=null)&&(resp_system!=null)) {
	    		 long assess = datetime2.getTime()-assessment_time.getTime();    
	    		 int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess); 
	    		 Individual unique_7 = null; 	 
	    		 if(model.getIndividual(med+uhid+"_"+assesstime) != null)	     	  
	    			 unique_7 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);	     	
	    		 else
	    			 unique_7 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));    
	    		 if(resp_system=="Yes")
	    			 model.addLiteral(unique_7,hasRespiratoryDistress,ResourceFactory.createTypedLiteral("true"));
	    	 } 	
	     }
	  
        String symp = null;	  
	    String query15=" Select sasepsisobj from SaSepsis as sasepsisobj WHERE uhid="+"'"+uhid+"'"+"order by assessment_time";  
	    List<SaSepsis> SaInfectionSepsisList = patientDao.getListFromMappedObjNativeQuery(query15);
	    for(SaSepsis sasepsisobj : SaInfectionSepsisList) {     
	       
	    	if(!BasicUtils.isEmpty(sasepsisobj.getAssessmentTime()))
	    		assessment_time = sasepsisobj.getAssessmentTime();  
	 	
	    	if(!BasicUtils.isEmpty(sasepsisobj.getSymptomaticValue()))
	    		symp = sasepsisobj.getSymptomaticValue();      
	 	
	    	if((assessment_time!=null) && (symp!=null)) {
	    		long assess15 = datetime2.getTime()-assessment_time.getTime();
	    		int assesstime15 = (int) TimeUnit.MILLISECONDS.toDays(assess15);  
	    		Individual unique_8 = null; 
	    		if(model.getIndividual(med+uhid+"_"+assesstime15) != null)       	
	    			unique_8 = (Individual) model.getIndividual(med+uhid+"_"+assesstime15);      	
	    		else
	    			unique_8 = (Individual) model.createIndividual(med+uhid+"_"+assesstime15,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	    		if(symp.contains("Lethargy")){
	    			model.addLiteral(unique_8,hasLethargy,ResourceFactory.createTypedLiteral("true"));
	    		}
	    	}
	    }
	    
	      
 	    String query17=" Select testitemobj from TestItemResult as testitemobj where prn="+"'"+uhid+"'" +"AND itemname='PLATELET COUNT' AND itemvalue is not null order by resultdate";           
        List<TestItemResult> testresultList = patientDao.getListFromMappedObjNativeQuery(query17);
		for(TestItemResult testitemobj : testresultList) {   
			
			if(!BasicUtils.isEmpty(testitemobj.getResultdate()))
				assessment_time =testitemobj.getResultdate();    	
     			String p = testitemobj.getItemvalue();       
     			Integer platelet=Integer.parseInt(p);
     		
     			if((assessment_time!=null)&&(platelet!=null)){
     				long assess = datetime2.getTime()-assessment_time.getTime();
     				int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
     				Individual unique_10 = null; 
     				if(model.getIndividual(med+uhid+"_"+assesstime) != null)       	
     					unique_10 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);        	
     				else
     					unique_10 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
       	
     				if(platelet<150000){
     					model.addLiteral(unique_10,haslowPlateletCount,ResourceFactory.createTypedLiteral("true"));
     				}
     			}   	    
		}
		
    	 String query18=" select nursingvitalobj from NursingVitalparameter as nursingvitalobj where uhid="+"'"+uhid+"'"+"order by entrydate";
         List<NursingVitalparameter> NursingVitalparameterList1 = patientDao.getListFromMappedObjNativeQuery(query18);
         for(NursingVitalparameter nursingvitalobj : NursingVitalparameterList1) {             
        	 String discolor = null;
        	 if(!BasicUtils.isEmpty(NursingVitalparameterList1))
        		 assessment_time = nursingvitalobj.getEntryDate();   
         
        	 if(!BasicUtils.isEmpty(NursingVitalparameterList1))
        		 discolor=nursingvitalobj.getBaby_color();  
         
        	 if((assessment_time!=null)&&(discolor!=null)) {
        		 long assess = datetime2.getTime()-assessment_time.getTime();
        		 int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
        		 Individual unique_11 = null; 
        		 if(model.getIndividual(med+uhid+"_"+assesstime) != null)       
        			 unique_11 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
        		 else
        			 unique_11 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));  
        		 if(discolor=="Cyanosis")
        			 model.addLiteral(unique_11,hasSkinDiscoloration,ResourceFactory.createTypedLiteral("true"));
        	 }
         }
 		
  	   
         Boolean gastric_aspirate;
         String feed_intol = null;
	     for(SaFeedIntolerance feedintobj : FeedIntoleranceList) {              

	    	 if(!BasicUtils.isEmpty(feedintobj.getAssessmentTime()));
  	    	 	assessment_time = feedintobj.getAssessmentTime();    
  	     
  	    	 if(!BasicUtils.isEmpty(feedintobj.getGastricAspirate()))
  	    		 gastric_aspirate =feedintobj.getGastricAspirate();      	
  	     
  	    	 if(!BasicUtils.isEmpty(feedintobj.getFeedIntoleranceStatus()))
  	    		 feed_intol = feedintobj.getFeedIntoleranceStatus();
  	     
  	    	 if((assessment_time!=null)&&(feed_intol!=null)) {
  	    		 long assess = datetime2.getTime()-assessment_time.getTime();
  	    		 int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);   
  	    		 Individual unique_12 = null; 	         
  	    		 if(model.getIndividual(med+uhid+"_"+assesstime) != null)     
  	    			 unique_12 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
  	    		 else
  	    			 unique_12 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));	   
  	    		 model.addLiteral(unique_12,hasGastricAspirate,ResourceFactory.createTypedLiteral("true"));
  	    		 if(feed_intol.contains("Yes")){    
  	    			 model.addLiteral(unique_12, hasFeedIntolerance,ResourceFactory.createTypedLiteral("true"));
  	    		 }  
  	    		 if(feed_intol.contains("No")){    
  	    			 model.addLiteral(unique_12, hasFeedIntolerance,ResourceFactory.createTypedLiteral("false"));
  	    		 }  
  	    		if(feed_intol.contains("Inactive")){    
 	    			 model.addLiteral(unique_12, hasFeedIntolerance,ResourceFactory.createTypedLiteral("false"));
 	    		 }
  	    	 } 
	     }
	     
	     
	    String gastric_aspirate1;
	    String aspiratecolor = null;
	    
	    List<NursingIntakeOutput> NursingIntakeOutputList1 = patientDao.getListFromMappedObjNativeQuery(query6);
        for(NursingIntakeOutput nursingintakeobj : NursingIntakeOutputList1){
	    
        	if(!BasicUtils.isEmpty(nursingintakeobj.getGastricAspirate()));
        		gastric_aspirate1 = nursingintakeobj.getGastricAspirate();   
		
        	if(!BasicUtils.isEmpty(nursingintakeobj.getEntry_timestamp()));
        		assessment_time = nursingintakeobj.getEntry_timestamp(); 
		
        	if(!BasicUtils.isEmpty(nursingintakeobj.getAspirateColor()));
				aspiratecolor = nursingintakeobj.getAspirateColor(); 
		
			if((assessment_time!=null) && (aspiratecolor!=null)) {
				long assess20 = datetime2.getTime()-assessment_time.getTime();
				int assesstime20 = (int) TimeUnit.MILLISECONDS.toDays(assess20);   
				Individual unique_13 = null;         
				if(model.getIndividual(med+uhid+"_"+assesstime20) != null)
					unique_13 = (Individual) model.getIndividual(med+uhid+"_"+assesstime20);
				else 
					unique_13 = (Individual) model.createIndividual(med+uhid+"_"+assesstime20,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
				if(aspiratecolor.contains("Milky"))
					model.addLiteral(unique_13,hasGastricAspirateAbnormalColor,ResourceFactory.createTypedLiteral("false")); 
				else 
					model.addLiteral(unique_13,hasGastricAspirateAbnormalColor,ResourceFactory.createTypedLiteral("true"));
			}
        }
        
        
        
        Float bp = null;
        String cft = null;
        float centraltemp = 0;
        float peripheraltemp = 0;
		float diff=0;
 		
        for(NursingVitalparameter nursingvitalobj : NursingVitalparameterList1) {    
 		    
        	if(!BasicUtils.isEmpty(nursingvitalobj.getEntryDate()))
        		assessment_time	 = nursingvitalobj.getEntryDate(); 

        		if(!BasicUtils.isEmpty(nursingvitalobj.getMeaniBp()))
        			bp	 = nursingvitalobj.getMeaniBp();       

        		if(!BasicUtils.isEmpty(nursingvitalobj.getCft()))
        			cft	 = nursingvitalobj.getCft();       

        		if(!BasicUtils.isEmpty(nursingvitalobj.getCentraltemp()))
        			centraltemp	 = nursingvitalobj.getCentraltemp();       

        		if(!BasicUtils.isEmpty(nursingvitalobj.getPeripheraltemp()))
        		peripheraltemp	 = nursingvitalobj.getPeripheraltemp();                	
        		diff=centraltemp-peripheraltemp;
        
        		if(assessment_time!=null) {
        			long assess21 = datetime2.getTime()-assessment_time.getTime();
        			int assesstime21 = (int) TimeUnit.MILLISECONDS.toDays(assess21);
        			Individual unique_14 = null; 
        			if(model.getIndividual(med+uhid+"_"+assesstime21) != null)        	
        				unique_14 = (Individual) model.getIndividual(med+uhid+"_"+assesstime21);        	
        			else
        				unique_14 = (Individual) model.createIndividual(med+uhid+"_"+assesstime21,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate")); 
        			if(cft!=null)
        				if(cft.equalsIgnoreCase("3")||(cft.equalsIgnoreCase(">3"))){   
        					model.addLiteral(unique_14,hasLongCapillaryRefillTime,ResourceFactory.createTypedLiteral("true"));
        				}
        			if(diff!=0){  
        				model.addLiteral(unique_14,hasCentralPeripheralDifference,ResourceFactory.createTypedLiteral("true"));
        			} 
        			if(bp!=null){
        				model.addLiteral(unique_14,hasBloodPressure,ResourceFactory.createTypedLiteral(bp));
        			}
        		}
        }
 		
        for(NursingEpisode  nursingepiobj : nursingepisodeList) {     
	    	
        	String techycardia = null;
        	if(!BasicUtils.isEmpty( nursingepiobj.getCreationtime()))
        		assessment_time =  nursingepiobj.getCreationtime();       
	  
        	if(!BasicUtils.isEmpty( nursingepiobj.getSymptomaticValue()))
        		techycardia =  nursingepiobj.getSymptomaticValue();
	     
        	if((assessment_time!=null) && (techycardia!=null)){
        		long assess22 = datetime2.getTime()-assessment_time.getTime();
        		int assesstime22 = (int) TimeUnit.MILLISECONDS.toDays(assess22);
        		Individual unique_15 = null;      
        		if(model.getIndividual(med+uhid+"_"+assesstime22) != null)
        			unique_15 = (Individual) model.getIndividual(med+uhid+"_"+assesstime22);
        		else 
        			unique_15 = (Individual) model.createIndividual(med+uhid+"_"+assesstime22,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
        		if(techycardia.contains("Tachycardia"))
        			model.addLiteral(unique_15,hasTechycardia,ResourceFactory.createTypedLiteral("true"));
             	}
        }
        
        
        
        
	    
	     String peripheries=null;
	     Float lactate=null;
	 
         String query23="select sashockobj from SaShock as sashockobj where uhid="+"'"+uhid+"'"+"order by assessment_time";
         List<SaShock> SaShockList = patientDao.getListFromMappedObjNativeQuery(query23); 
 	     for(SaShock sashockobj : SaShockList) {     

 	    	if(!BasicUtils.isEmpty(sashockobj.getAssessmentTime()))
 	    		assessment_time = sashockobj.getAssessmentTime();   
   	  
    		if(!BasicUtils.isEmpty(sashockobj.getPeripheries()))
    			peripheries = sashockobj.getPeripheries();   
	  
    		if(!BasicUtils.isEmpty(sashockobj.getLactate()))
    			lactate = sashockobj.getLactate();
     
    			if(assessment_time!=null) {
    				long assess23 = datetime2.getTime()-assessment_time.getTime();
    				int assesstime23 = (int) TimeUnit.MILLISECONDS.toDays(assess23);
    				Individual unique_16 = null; 
     
    				if(model.getIndividual(med+uhid+"_"+assesstime23) != null)
    					unique_16 = (Individual) model.getIndividual(med+uhid+"_"+assesstime23);
    				else
    					unique_16 = (Individual) model.createIndividual(med+uhid+"_"+assesstime23,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate")); 
    				if(peripheries!=null)
    					if(peripheries.contains("cold"))
    						model.addLiteral(unique_16,hasColdPeripheries,ResourceFactory.createTypedLiteral("true"));
    				if(lactate!=null)
    					model.addLiteral(unique_16,hasLactateLevel,ResourceFactory.createTypedLiteral("true"));
    			}
 	     }
 	    
       String query24="select saNecobj from SaNec as saNecobj where uhid="+"'"+uhid+"'" +"order by assessment_time";
       String eventstatusNEC = null;     
       List<SaNec> SainfectionnecList = patientDao.getListFromMappedObjNativeQuery(query24);    
	   for(SaNec saNecobj : SainfectionnecList) {     
		   if(!BasicUtils.isEmpty(saNecobj.getAssessmentTime()))
			   assessment_time = saNecobj.getAssessmentTime();   
  	   
		   if(!BasicUtils.isEmpty(saNecobj.getEventstatus()))
			   eventstatusNEC = saNecobj.getEventstatus(); 
  	   
		   if((assessment_time!=null) && (eventstatusNEC!=null)) {
  	   	   		long assess24 = datetime2.getTime()-assessment_time.getTime();
  	   	   
  	   	   		int assesstime25 = (int) TimeUnit.MILLISECONDS.toDays(assess24);
  	   	   		Individual unique_17 = null; 
  	   	   		if(model.getIndividual(med+uhid+"_"+assesstime25) != null) 
  	   	   			unique_17 = (Individual) model.getIndividual(med+uhid+"_"+assesstime25);
  	   	   		else
  	   	   			unique_17 = (Individual) model.createIndividual(med+uhid+"_"+assesstime25,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
  	   	   		if(eventstatusNEC.contains("yes")) 
  	   	   			model.addLiteral(unique_17,hasNecrotizingEnterocolitis,ResourceFactory.createTypedLiteral("true"));
  	   	  	}
	   }
	   
    
	     
	        
	        /*
	      	String query16=" Select obj from NursingVitalparameter as obj WHERE uhid="+"'"+uhid+"'"+" AND centraltemp<36.5 or centraltemp>37.5 order by entrydate";	   
		 	Date newtime = null;
	   	    int count = 0;
	   	    Calendar cal = Calendar.getInstance();
	   	    cal.add(Calendar.HOUR_OF_DAY, 10);
	        List<NursingVitalparameter> NursingVitalparameterList = patientDao.getListFromMappedObjNativeQuery(query16);
	    
	    	if(!BasicUtils.isEmpty(NursingVitalparameterList) && !BasicUtils.isEmpty(NursingVitalparameterList.get(0).getEntryDate()))
	    		assessment_time = NursingVitalparameterList.get(0).getEntryDate();       
	 	    */
	    
    
	 OntClass uniqueid = null;

			 hasBabyID=model.getProperty(med,"hasBabyID");{
				 
				 for(long p=doa_DOL;p<=currentDOL;p++) {
 	 
					 ExtendedIterator classes = model.listClasses();
					 while (classes.hasNext()){
						 OntClass cls = (OntClass) classes.next();
						 for (Iterator i = cls.listSubClasses(true); i.hasNext();) {
							 OntClass c = (OntClass) i.next();
							 if (c.getLocalName().contentEquals("Neonate")){
								 
								 if(model.getIndividual(med+uhid+"_"+p) != null) 
					  	   	   			unique = (Individual) model.getIndividual(med+uhid+"_"+p);
					  	   	   	 else
					  	   	   			unique = (Individual) model.createIndividual(med+uhid+"_"+p,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	         	             
								 if(currentDOL>=0&gestationalweek!=null&uhid!=null){
									 model.addLiteral(unique, hasDayOfLife,ResourceFactory.createTypedLiteral(p));
			             	       	 model.addLiteral(unique, hasGestationalAgeAtBirth,ResourceFactory.createTypedLiteral(gestationalweek,XSDDatatype.XSDdouble));
			             	         //model.addLiteral(unique, hasRiskFactor,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
			             	         //model.addLiteral(unique, hasFeedIntolerance,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
		             	             model.addLiteral(unique, hasBabyID,ResourceFactory.createTypedLiteral(uhid));

			             	         if(chestcomp!=null){
			             	        	 model.addLiteral(unique, hasChestCompressionDuration,ResourceFactory.createTypedLiteral(chestcomp));
		             	             }

		             	             if(ph!=null) {
		             	            	 model.addLiteral(unique,hasCordGaspHValue,ResourceFactory.createTypedLiteral(ph));
		            	             } 

		             	             if(be!=null) {
		             	            	 model.addLiteral(unique,hasBaseDeficitValue,ResourceFactory.createTypedLiteral(be));
		             	             }

		             	             model.addLiteral(unique,hasMonoTwinStatus,ResourceFactory.createTypedLiteral(twin_status));  
		             	             model.addLiteral(unique,pressorGiven,ResourceFactory.createTypedLiteral(val));
		             	      
		             	          
								 }
							 }
						 }
					 }
				 }

				 List rules = Rule.rulesFromURL("nutritionguideline.rules");
        	     GenericRuleReasoner reasoner = (GenericRuleReasoner)GenericRuleReasonerFactory.theInstance().create(null);
        	     ((FBRuleReasoner) reasoner).setRules(rules);
        	     ((GenericRuleReasoner) reasoner).setMode(GenericRuleReasoner.FORWARD_RETE);  
        	     reasoner.setDerivationLogging(true);
        	     InfModel inf = ModelFactory.createInfModel(reasoner, model);
 

   
				 String test="PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"
				   		+ "SELECT  DISTINCT  ?FeedVolume ?FeedAdvancement ?DOL WHERE {{?Baby    CHIL:hasFeedingVolume   ?FeedVolume. ?Baby    CHIL:hasDayOfLife  ?DOL."
				   		+ "?Baby CHIL:hasFeedingAdvancement   ?FeedAdvancement.} }";   
				   	

				 String test1= "PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"
						  		+"PREFIX type:   <http://www.w3.org/2001/XMLSchema>"
								+"PREFIX booleanv: <http://www.w3.org/2001/XMLSchema#boolean>"
						  		
					     		+ "SELECT  Distinct ?UHID   ?DayOfLife ?FeedVolume  ?FeedAdvancement "
					     		+ "?vomitvol ?vomitcolor ?abdtender ?abddistension"
					     		+ " ?visiblebowel ?bloodstool ?metabolic ?Apnea  ?respDistress ?Lethargy  ?plateletcount  ?skindiscolor "
					     		+ "?feedint ?gastric  ?CFL ?central  ?techycardia  ?coldperi  ?NEC ?BP "
					     		+"WHERE {"		
					      	    + "{?Baby   CHIL:hasBabyID  ?UHID}"
					     		+ " {?Baby    CHIL:hasDayOfLife  ?DayOfLife}"      		 
					     		 +"{ ?Baby       CHIL:hasFeedingVolume   ?FeedVolume}" 
					     		+ "{ ?Baby       CHIL:hasFeedingAdvancement   ?FeedAdvancement}  "    		
					     		 +"OPTIONAL"     		 
					     		+ "{ ?Baby       CHIL:hasVomitVolume   ?vomitvol}"     	
						         +"OPTIONAL"     		
					     		+ "{ ?Baby       CHIL:hasVomitColor   ?vomitcolor}  "
						         +"OPTIONAL"    		
					     		+ "{ ?Baby       CHIL:hasAbdominalTenderness   ?abdtender}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasAbdominalDistension   ?abddistension}  "
						         +"OPTIONAL"		
					         	+ "{ ?Baby       CHIL:hasVisibleBowelLoop   ?visiblebowel}  "
						         +"OPTIONAL"		     		
					     		+ "{ ?Baby       CHIL:bloodPresentInStool   ?bloodstool}  "
						         +"OPTIONAL"		
						        + "{ ?Baby       CHIL:hasMetabolicAcidosis   ?metabolic}  "
						         +"OPTIONAL"		
						        + "{ ?Baby       CHIL:hasApnea   ?Apnea}  "        		 
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasRespiratoryDistress  ?respDistress}  "
						         +"OPTIONAL"		
					      	      + "{ ?Baby       CHIL:hasLethargy   ?Lethargy}  "
					             +"OPTIONAL"		
					           + "{ ?Baby       CHIL:haslowPlateletCount   ?plateletcount}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasSkinDiscoloration   ?skindiscolor}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasFeedIntolerance   ?feedint}  "
						         +"OPTIONAL"			
					     		+ "{ ?Baby       CHIL:hasGastricAspirateAbnormalColor   ?gastric}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasLongCapillaryRefillTime   ?CFL}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasCentralPeripheralDifference  ?central}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasBloodPressure   ?blood}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasTechycardia   ?techycardia}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasColdPeripheries   ?coldperi}  "
						         +"OPTIONAL"		
					     		+ "{ ?Baby       CHIL:hasNecrotizingEnterocolitis   ?NEC}  "
					     		+ "OPTIONAL"
					     		+ "{ ?Baby       CHIL:hasLowBloodpressure   ?BP}  "
					     		
					     		+ "}orderby ?DayOfLife";
					     	
   
				 	String test11= "PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"
					  		+"PREFIX type:   <http://www.w3.org/2001/XMLSchema>"
							+"PREFIX booleanv: <http://www.w3.org/2001/XMLSchema#boolean>"
					  		
				     		+ "SELECT  Distinct ?UHID   ?DayOfLife ?FeedVolume  ?FeedAdvancement "
				     		
				     		+"WHERE {"		
				      	    + "{?Baby   CHIL:hasBabyID  ?UHID}"
				     		+ " {?Baby    CHIL:hasDayOfLife  ?DayOfLife}"      		 
				     		 +"{ ?Baby       CHIL:hasFeedingVolume   ?FeedVolume}" 
				     		+ "{ ?Baby       CHIL:hasFeedingAdvancement   ?FeedAdvancement}  "
				     	
				     		+ "}orderby ?DayOfLife";
				     	
    
					 String test2="PREFIX datatype:  <http://www.w3.org/2001/XMLSchema#>"
					      		+ "SELECT ?UHID ?DayOfLife ?FeedVolume ?FeedAdvancement    WHERE "
					      		+ "{{ ?Baby  <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedingVolume> ?FeedVolume} "
					      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedingAdvancement>  ?FeedAdvancement}"
					      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasDayOfLife>  ?DayOfLife}         \r\n" 
					      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasBabyID>  ?UHID}"

							//	+"OPTIONAL"

	                     //       + "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedIntolerance>  ?feedintol}"

					      		+ "} orderby ?DayOfLife ";   
					 
				 
						 
					 Query query = QueryFactory.create(test1) ;
					 QueryExecution qexec = QueryExecutionFactory.create(query, inf) ;
			   	     ResultSet rs = qexec.execSelect() ;

			   	     List vars=rs.getResultVars();
			   	     //   System.out.println(vars+"vars.....");

			   	     // String deleteType = "delete from " + BasicConstants.SCHEMA_NAME + ".nutritional_compliance where uhid = '" + uhid  +"';";
			   	     //System.out.println(deleteType);
			   	     //settingDao.executeInsertQuery(deleteType);
			   	    
		   	   
		   	     		 
			    	 String deleteType = "delete from " + BasicConstants.SCHEMA_NAME + ".nutritional_compliance where uhid = '" + uhid  +"'";
			    	 	//	+ " where uhid = '" + uhid  +"';";
			   	     System.out.println(deleteType);
			   	     settingDao.executeInsertQuery(deleteType);
		   	     
		   	  while (rs.hasNext()) {  // iterate over the result
		 
		   		     int counter = 0;		   	    	 
		   	    	 QuerySolution qs=rs.nextSolution();
		    	     System.out.println("solu");
		    	     String uhidValue = "";
		    	     Integer dolValue = 0;
		    	     Integer feedVolumeValue = 0;
		    	     Integer feedIncrementValue = 0;		    	    
		    	     String value="";    
		    	     String abdominaltenderness="";
		    	     String abdominaldistension="";
		    	     String visiblebowel="";
		    	     String bloodstool="";
		    	     String metabolic="";
		    	     String apnea="";
		    	     String respiratorydistress="";
		    	     String lethargy="";
		    	     String plateletcount="";
		    	     String skindiscolor="";
		    	     String feedintolerance="";
		    	     String gastricaspirate="";
		    	     String CFT="";
		    	     String central="";
		    	     String techycardia="";
		    	     String coldperi="";
		    	     String NEC="";
		    	     String details="";
		    	     String BPvalue="" ;
		    	     String risk="";
			   	     String vomitvol="";
			         String feedint="";
			         String central_s="";
			         String vomitcolor_s="";
			         String abdominaldist_s="";
			         String visiblebowel_s="";
			         String abdtender_s1="";
			         String abdtender_s11="";
			         String abdominaltender_s="";
			         String visiblebowel_s1="";
			         String bloodstool_s="";
			         String metabolic_s="";
			         String respdistress_s="";
			         String apnea_s="";
			         String lethargy_s="";
			         String plateletcount_s="";
			         String skindiscolor_s="";
			         String CFT_s="";
			         String gastricaspirate_s="";
			         String techycardia_s = "";
			         String coldperi_s="";
			         String NEC_s="";
			         String bp_s="";
			         String vomitvolume="";
		    	     String vomitcolor="";
		    	    
		 		     for(int i=0;i<vars.size();i++) { 
				    		String var=vars.get(i).toString();
				    		if(var.contentEquals("UHID")) {
				    			 RDFNode node=qs.get(var);	    		 
						    	 if(node!=null) {				    		 
						    		value = node.toString();
						    		//	System.out.println("UHID:........"+ "" + value);
							    	uhidValue = value;
						    	 } 
				    		 }
				    		 else if(var.contentEquals("DayOfLife")) {
				    			 RDFNode node=qs.get(var);	 
				    			 value=node.toString().split("http")[0];
				    			 value = value.replaceAll("[^a-zA-Z0-9]", ""); 
				    			 
				    			 dolValue = Integer.parseInt(value);	
				    			 // System.out.println("dolValue:........"+ "" + dolValue);
				    		 }
				    		 else if(var.contentEquals("FeedVolume")) {
				    			 RDFNode node=qs.get(var);	    		 
						    	 if(node!=null) {		    		 
						    		value = node.toString();
						    		int i1=Integer.parseInt(value);  
						    		//	System.out.println("FeedVolume:........"+ "" + value);
							    	feedVolumeValue = i1;    	
							    	}
				    		 }
				    		 else if(var.contentEquals("FeedAdvancement")){
				    			 RDFNode node=qs.get(var);	    		 
						    	 if(node!=null) {		    		 
						    		value = node.toString();
						    		int i2=Integer.parseInt(value);  
						    		//	System.out.println("FeedAdvancement........"+ "" + value);
						    		feedIncrementValue = i2;
						    	    }
				    		 }
				    		 else if(var.contentEquals("central")) {
				    			 central_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();	 
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    				 central=value;
				    				 central_s="CentralPeripheralDifference";
				    				 }
				    				 else
				    					 central_s="";
				    				 	//System.out.println("centralperipheraldifference........"+ central);
				    			     }
				    		 }else if(var.contentEquals("vomitcolor")) {
				    			 vomitcolor_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 vomitcolor=value;
				    				 vomitcolor_s="Vomitcolor:"+vomitcolor;
				    				 }
				    				 else
				    					 vomitcolor_s="";
				    				 	//	System.out.println("vomitcolor........"+ vomitcolor);
				    			 }
				    		 }
				    		 else if(var.contentEquals("abdtender")) {
				    			 abdominaltender_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 abdominaltenderness=value;
				    				 abdominaltender_s="Abdominaltenderness";
				    				 }
				    				 else
				    					 abdominaltender_s="";
				    				 	//	System.out.println("abdominaltender........"+ abdominaltenderness);
				    			 }
				    		 }			    		 
				    		 else if(var.contentEquals("abddistension")) {
				    			 abdominaldist_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 abdominaldistension=value;
				    				 abdominaldist_s="AbdominalDistension";
				    				 }
				    				 else
				    					 abdominaldistension="";
								    	//System.out.println("abdominaldistension........"+ abdominaldistension);
				    			 }
				    		 }
				    		 else if(var.contentEquals("visiblebowel")) {
				    			 visiblebowel_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 visiblebowel=value;
				    				 visiblebowel_s="VisibleBowel";
				    				 }
				    				 else
				    					 visiblebowel="";
								    	//System.out.println("visiblebowel........"+ visiblebowel);
				    			 }
				    		 }			    		  
				    		 else if(var.contentEquals("bloodstool")) {
				    			 bloodstool_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 bloodstool=value;
				    				 bloodstool_s="BloodinStool:";
				    				 }
				    				 else
				    					 bloodstool_s="";
								    //	System.out.println("bloodinstool........"+ bloodstool);
				    			 }
				    		 }					    	
				    		 else if(var.contentEquals("metabolic")) {
				    			 metabolic_s="";
				    			 RDFNode node=qs.get(var);
				    			 //	System.out.println("Node........"+ node);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 metabolic=value;
				    				  metabolic_s= "MetabolicAcidosis:";
				    				 }
				    				 else
				    					 metabolic_s="";
								     //System.out.println("metabolicacidosis......."+ details1);
				    			 }
				    		 }
				    		 else if(var.contentEquals("Apnea")) {
				    			 apnea_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 apnea=value;
				    				 apnea_s="Apnea";
				    				 }
				    				 else
				    					 apnea_s="";
								  //  	System.out.println("apnea........"+ apnea);
				    			 }
				    		 }
				     		 else if(var.contentEquals("respDistress")) {
				     			respdistress_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 respiratorydistress=value;
				    				 respdistress_s="RespiratoryDistress";
				    				 }
				    				 else
				    					 respdistress_s="";
				    			 }
				    		 }
				     		 else if(var.contentEquals("Lethargy")) {
								  	lethargy_s="";
								  	RDFNode node=qs.get(var);
				    			 	if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil")& (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 lethargy=value;
				    				 lethargy_s="Lethargy";
				    				 }
				    				 else
				    					 lethargy_s="";
								    	//System.out.println("haslethargy........"+ lethargy);
				    			 	}
				    		 }
				     		 else if(var.contentEquals("plateletcount")) {
				     			plateletcount_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 plateletcount=value;
				    				 plateletcount_s="hasplateletcount:"+plateletcount;
				    				 }
				    				 else
				    					 plateletcount_s="";
								    //	System.out.println("hasplateletcount........"+ plateletcount);
				    			 }
				    		 }
				     		 else if(var.contentEquals("skindiscolor")) {
				     			skindiscolor_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 skindiscolor=value;
				    				 skindiscolor_s="SkinDiscolor";
				    				 }
				    				 else
				    					 skindiscolor_s="";
								    	//System.out.println("hasskindiscolor........"+ skindiscolor);
				    			 }
				    		 }
				     		 else if(var.contentEquals("gastric")) {
				     			gastricaspirate_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 gastricaspirate=value;
				    				 gastricaspirate_s="GastricAspirate";
								    	//System.out.println("gastricaspirate........"+ gastricaspirate);
				    				 }
				    				 else
				    					 gastricaspirate_s="";
				    			 }
				    		 }
				     		 else if(var.contentEquals("CFL")) {
				     			CFT_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 CFT=value;
				    				 CFT_s="LongCapillaryRefillTime";
								    	//System.out.println("CFL........"+ CFT);
				    				 }
				    				 else
				    					 CFT_s="";
				    			 }
				    		 }
				     	
				     		 else if(var.contentEquals("techycardia")) {
				     			techycardia_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    				 techycardia=value;
				    				 techycardia_s="Techycardia";
				    				 }
				    				 else
				    					 techycardia_s="";
				    				 	//	System.out.println("hastechycardia........"+ techycardia);
				    			 }
				    		 }
				     		 else if(var.contentEquals("coldperi")) {
				     			coldperi_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 coldperi=value;
				    					 coldperi_s="ColdPeripheries";
				    					 // 	System.out.println("coldperi........"+ coldperi);
				    				 }
				    				 else
				    					 coldperi_s="";
				    			 }	
				     		 }
				    		 else if(var.contentEquals("NEC")) {
				    			 NEC_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 NEC=value;
				    					 NEC_s="NecrotizingEnterocolitis";
				    				 }
				    				 else
				    					 NEC_s="";
								    	//System.out.println("NEC........"+ NEC);
				    			 }
				    		 }
				    		 else if(var.contentEquals("BP")) {
				    			 bp_s="";
				    			 RDFNode node=qs.get(var);
				    			 if(node!=null) {
				    				 value = node.toString();
				    				 if(!value.contentEquals("nil") & (!value.contentEquals("false^^http://www.w3.org/2001/XMLSchema#boolean"))) {
				    					 BPvalue = value;  
				    					 bp_s="  hasBP:"+BPvalue;
				    				 }
				    				 else
				    					 bp_s="";
								    	//System.out.println("hasBP........"+ BP);
				    			 }
				    		 }
					    	// details=central_s+vomitcolor_s+visiblebowel_s+vomitvol+feedint+central_s+abdominaldist_s+abdominaltender_s+bloodstool_s+metabolic_s+respdistress_s+apnea_s+lethargy_s+plateletcount_s+skindiscolor_s+CFT_s+gastricaspirate_s+techycardia_s+coldperi_s+NEC_s+bp_s; 
					    	 String joined= Stream.of(vomitcolor_s,visiblebowel_s ,vomitvol, feedint,central_s,abdominaldist_s, abdominaltender_s,bloodstool_s,metabolic_s,respdistress_s,apnea_s,lethargy_s,plateletcount_s,skindiscolor_s,CFT_s,gastricaspirate_s,techycardia_s,coldperi_s,NEC_s,bp_s).filter(s -> s != null && !s.isEmpty()).collect(Collectors.joining(", "));					    	    
					    	 String details1 = joined.replaceAll("^^http://www.w3.org/2001/XMLSchema#boolean", "");
					    	 System.out.println("message....... = " + details1);
						     details = details1.replaceAll("true^^http://www.w3.org/2001/XMLSchema#boolean", "true");
					    	 
						     if(!details.isEmpty()) {	  

						    	 String  insertType = "insert into " + BasicConstants.SCHEMA_NAME + ".nutritional_compliance (uhid,dol,feed_volume,feed_advancement,details) VALUES ("+"'" + uhidValue
								+ "'," + dolValue + "," + feedVolumeValue + "," + feedIncrementValue  +",'" + details +"')";
						    	 System.out.println(insertType);
						    	 settingDao.executeInsertQuery(insertType); 
							   }
							   else {
								   String  insertType = "insert into " + BasicConstants.SCHEMA_NAME + ".nutritional_compliance (uhid,dol,feed_volume,feed_advancement) VALUES ("+"'" + uhidValue
											+ "'," + dolValue + "," + feedVolumeValue + "," + feedIncrementValue   +")";
					    	     System.out.println(insertType);
						    	 settingDao.executeInsertQuery(insertType); 
							   }
		 		     }//end of for loop 
		   	  }  // end of while loop
		   	   
		    	    
			 }//end of uhid loop
        } //end of try 
        
		
	}catch (Exception e){
				System.out.println(e);
			}
		}	    
	}

