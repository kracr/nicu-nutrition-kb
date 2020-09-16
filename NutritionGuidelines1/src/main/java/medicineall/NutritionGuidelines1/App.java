package medicineall.NutritionGuidelines1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
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
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import org.apache.jena.query.QuerySolution;
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
import org.apache.jena.vocabulary.XSD;

import com.github.andrewoma.dexx.collection.ArrayList;
import com.github.andrewoma.dexx.collection.IndexedList;
import com.sun.tools.javac.code.Attribute.Enum;



/**
 * Hello world!
 *
 */
public class App 
{
static final String inputFileName = "D:\\CHIL\\Nutrition\\sampleOntology1.owl";
static Property hasGestationalAgeAtBirth;
static Property hasFeedIntolerance;
static Property hasDayOfLife;
static Property hasFeedingAdvancement;
static Property hasFeedingVolume;
static Property hasRiskFactor;
static Property givenFeedVolume;//
static Property hasBaseDeficitValue;//
static Property hasCordGaspHValue; //
static Property hasMonoTwinStatus;//
static Property pressorGiven;//
static Property hasSkinDiscoloration;//

static Individual ins;
static Individual unique;
static Individual unique2;

static long day[];
@SuppressWarnings("deprecation")

    public static void main( String[] args ) throws FileNotFoundException
    {
	
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
	 Property hasBabyID = model.getProperty(med,"hasBabyID");
	 hasMonoTwinStatus=model.getProperty(med,"hasMonoTwinStatus");   //
	hasCordGaspHValue=model.getProperty(med,"hasCordGaspHValue");   //
	hasBaseDeficitValue=model.getProperty(med,"hasBaseDeficitValue");    //
	 Property hasChestCompressionDuration = model.getProperty(med,"hasChestCompressionDuration");    //
	pressorGiven = model.getProperty(med,"pressorGiven");    //
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


    
	//****************************************************************
	Date datetime1 = null;
	Date doatoa = null;

	Connection con = null;

	Statement stmt = null;
	Statement stmt1 = null;

	java.sql.ResultSet rec1 = null;
	java.sql.ResultSet rec2 = null;
	java.sql.ResultSet rec4 = null;//
	java.sql.ResultSet rec5 = null;//
	java.sql.ResultSet rec6 = null;//
	java.sql.ResultSet rec7 = null;//
	java.sql.ResultSet rec8 = null;//
	java.sql.ResultSet rec9 = null;//
	java.sql.ResultSet rec10= null;//
	java.sql.ResultSet rec11= null;//
	java.sql.ResultSet rec12= null;//
	java.sql.ResultSet rec13= null;//
	java.sql.ResultSet rec14= null;//
	java.sql.ResultSet rec15= null;//
	java.sql.ResultSet rec16= null;//
	java.sql.ResultSet rec17= null;//
	java.sql.ResultSet rec18= null;//

	java.sql.ResultSet rec19= null;//
	java.sql.ResultSet rec20= null;//

	
	java.sql.ResultSet uhidrec = null;

	ResultSet rec3 = null;

	int dayoflife = 0; //this is a field
	Date entrydatetime = null;
	Date dobandtime = null;
	String gestationalweek = null;
	String totalfeed_ml_per_kg_day;
	Date dateofadmission;
	String timeofadmission;

	Double workingweight=(double) 1;
	Dictionary geek = new Hashtable(); 
	String extnum;
	String [] nextLine;
	String ph = null;    //
	String be = null;    //
   	String chest_comp_time = null;   //


	try {
		
		
		
		Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/inicudb","postgres", "postgres") ;
        stmt = con.createStatement();     
        
        String getUHID="select uhid from apollo.baby_detail"; 
        PreparedStatement ps = con.prepareStatement(getUHID);

        uhidrec = ps.executeQuery();

    while(uhidrec.next())
      {
     	String uhid=uhidrec.getString("uhid");
    //    String uhid="RSHI.0000022355";
       	 System.out.println(uhid+"......uhid.....");


        String query1="select  dateofadmission,timeofadmission,timeofbirth, dateofbirth, gestationweekbylmp, gestationdaysbylmp from apollo.baby_detail where uhid"+"="+"'"+uhid+"'"; 
      

        PreparedStatement ps1 = con.prepareStatement(query1);
       rec1 = ps1.executeQuery();

         while(rec1.next())
         {
   System.out.println(rec1.getString("dateofadmission")+" "+rec1.getString("timeofadmission")+" "+rec1.getString("timeofbirth")+" "+rec1.getString("dateofbirth")+" "+rec1.getString("gestationweekbylmp")+" "+rec1.getString("gestationdaysbylmp"));  
             
 String time1=rec1.getString("timeofbirth");
 gestationalweek=rec1.getString("gestationweekbylmp");
 String replacetime=time1.replaceFirst(",",":");
 String reptime=replacetime.replaceFirst(","," ");
 dateofadmission=rec1.getDate("dateofadmission");
 timeofadmission=rec1.getString("timeofadmission");
 
 
 Date dob=rec1.getDate("dateofbirth");
 
 
 
 SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm:ss");
 SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
 Date date = (Date) parseFormat.parse(reptime);
// System.out.println(parseFormat.format(date) + " = " + displayFormat.format(date));
 
 String startingDate = new SimpleDateFormat("yyyy-MM-dd").format((dob));

 String startingTime = new SimpleDateFormat("hh:mm:ss").format(date);
 
 String DateTime=startingDate+" "+startingTime;
 System.out.println(DateTime+"....datetime..");

 SimpleDateFormat parseFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  datetime1 = (Date) parseFormat1.parse(DateTime);
  System.out.println(datetime1+"....doame..");
  //******************************************************
 
 String replacetoa=timeofadmission.replaceFirst(",",":");
 String reptime_toa=replacetoa.replaceFirst(","," ");
 System.out.println(reptime_toa+"....time..");
 System.out.println(dateofadmission+"......doa.....");


 String startingDate1 = new SimpleDateFormat("yyyy-MM-dd").format((dateofadmission));
 Date date1 = (Date) parseFormat.parse(reptime_toa);

 String startingTime1 = new SimpleDateFormat("hh:mm:ss").format(date1);
 
 
 String DateTime1=startingDate1+" "+startingTime1;

 SimpleDateFormat parseFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  doatoa = (Date) parseFormat2.parse(DateTime1);   //dateofadmission and time of admission
  System.out.println(doatoa+"....doame..");
         } 

         
         
         
       //*********calculate DOL for current date
			 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  

		    Date newdate = new Date();  
		    String entry = formatter.format(newdate);
		 //   System.out.println(formatter.format(newdate));  

		  Date datetime2 = (Date) formatter.parse(entry);

          
          long currentdiff = datetime2.getTime()-datetime1.getTime();
          long doa_diff=doatoa.getTime()-datetime1.getTime();
          int doa_DOL = (int) TimeUnit.MILLISECONDS.toDays(doa_diff);

 		   System.out.println((doa_DOL)+"doa");  

           int currentDOL = (int) TimeUnit.MILLISECONDS.toDays(currentdiff);
  		    System.out.println((currentDOL)+"currentd");  

//*************************************************************************************************************
  		   
  	        String query4="select  uhid,chest_comp_time from apollo.birth_to_nicu WHERE uhid="+ "'"+uhid+"'"; 
  	      String chestcomp=null;
  		    //Risk factor
  	       Statement stmt4 = con.createStatement();    
 	        {
  	        rec4 = stmt4.executeQuery(query4);
         while(rec4.next())
             chestcomp = rec4.getString("chest_comp_time");  	          
   	      System.out.println("be..."); 
 	        }

 //********************************************************************************************************************** 	         
  	         
  	       String query5=" Select ph,be,uhid from apollo.nursing_bloodgas WHERE uhid="+"'"+uhid+"'"+"AND be is NOT NULL"; 
  	     Statement stmt5 = con.createStatement();     
  	    rec5 = stmt5.executeQuery(query5);
  	        while(rec5.next())
  	        { 	
  	        	ph=rec5.getString("ph");
  	        	be=rec5.getString("be");
  	      System.out.println("be..."+ph); 
	      System.out.println("be..."+be); 
  	         }

  	        
//***************************************************************************************************************  		    
	       String babytype = null;
boolean twin_status = false;
  	      String query6=" select baby_type,uhid from apollo.baby_detail WHERE uhid="+"'"+uhid+"'"; 
   	     Statement stmt6 = con.createStatement();     
   	     rec6 = stmt6.executeQuery(query6);
	     while(rec5.next())
	     {
   	 babytype=rec6.getString("baby_type");
		if(babytype.equalsIgnoreCase("Twins")||babytype.equalsIgnoreCase("Triplets"))
		{
			twin_status = true;
		}
		else
		{
			twin_status = false;
		}
		

	     }       
   	        
//**************************************************************************
  		    
 
   	        
   	        
   	   //***************************************************************************************************************  		    
 	       String pressor = null;
 	       Boolean val = null;

   	      String query7=" Select medicationtype,uhid from apollo.baby_prescription WHERE uhid="+"'"+uhid+"'"; 
    	     Statement stmt7 = con.createStatement();     
    	    rec7 = stmt7.executeQuery(query7);
    	    int i2=0;
    	    while(rec7.next())
	         {  
			pressor=rec7.getString("medicationtype");
			if(pressor=="TYPE0004")
			{
                 val = true;
			}
			else
			{
				val=false;
			}
	         }
	        
    	         
    	        
 //**************************************************************************
   		    
    	    Individual unique1;
          Date entry_timestamp;
          String vomit_color;


          String vomit_DOL = null;
          
    	      String query8=" select  vomit,entry_timestamp,vomit_color,uhid from apollo.nursing_intake_output WHERE uhid="+"'"+uhid+"'"+"AND (vomit_color='blood stained' OR vomit_color='bloody')"
    	      		+ "UNION select  vomit,entry_timestamp,uhid,vomit_color from apollo.nursing_intake_output WHERE uhid="+"'"+uhid+"'";     	      
    	      
    	      Statement stmt8 = con.createStatement();     
     	    rec8 = stmt8.executeQuery(query8);
     	    while(rec8.next())
 	         {  
 			entry_timestamp=rec8.getDate("entry_timestamp");
 			String vomit = rec8.getString("vomit");
  		   System.out.println(entry_timestamp+"entryyy");  
             vomit_color=rec8.getString("vomit_color");
  		 long cudiff = datetime2.getTime()-entry_timestamp.getTime();
         int nursingtime = (int) TimeUnit.MILLISECONDS.toDays(cudiff);
		   System.out.println(nursingtime+"nursing time");  
		   
		   
		     unique1 = (Individual) model.createIndividual(med+uhid+"_"+nursingtime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
		    	   	               
	             model.addLiteral(unique1,hasVomitColor,ResourceFactory.createTypedLiteral(vomit_color));
	             if(vomit!=null) {
	             model.addLiteral(unique1,hasVomitVolume,ResourceFactory.createTypedLiteral(vomit)); }
	             	               
		   System.out.println((nursingtime)+ vomit_DOL+"nursing time doa");  
      }
 	        
//**************************************************************************************************************************************    	         
  	    //**input ontology and define properties
		
     	   String query9="select uhid,assessment_time, abdominal_signs from apollo.sa_feed_intolerance    WHERE uhid="+"'"+uhid+"'"+"AND abdominal_signs is not null order by assessment_time";
     	    Statement stmt9 = con.createStatement();     
     	    rec9 = stmt9.executeQuery(query9);
     	    while(rec9.next())
 	         {  
     	   	Date	assessment_time=rec9.getDate("assessment_time");
 																																						
 		 String abdominSign = rec9.getString("abdominal_signs");
		 System.out.println(abdominSign+"abdomin"); 

  		// System.out.println(abdominSign+"abdentryyy"); 
  		long assess = datetime2.getTime()-assessment_time.getTime();
        int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
        
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_2 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
           
		    unique_2 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_2 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
  		 try {
  		if(abdominSign.contains("Erythema"))
  		{
			 System.out.println("yessss"); 
	            model.addLiteral(unique_2, hasAbdominalTenderness,ResourceFactory.createTypedLiteral("true"));

  		}
  		if(abdominSign.contains("Abdominal Distension"))
  		{
            model.addLiteral(unique_2, hasAbdominalDistension,ResourceFactory.createTypedLiteral("true"));

  			System.out.println("yessssabd"); 

  		}
  		 }
  		 
  		  catch (Exception e)
 	    {
 		System.out.println(e);
 		} 	      
 	    }
 	        
//**********************************************************************************************************

      	   String query10=" select uhid,assessment_time,abdominal_distinction_value from  apollo.sa_feed_intolerance  WHERE uhid="+"'"+uhid+"'"+"AND abdominal_distinction_value is not null order by assessment_time";
      	    Statement stmt10 = con.createStatement();     
      	    rec10 = stmt10.executeQuery(query10);
      	    while(rec10.next())
  	         {  
      	   	Date	assessment_time=rec10.getDate("assessment_time");
  																																						
  		 String abdomin_distinction = rec10.getString("abdominal_distinction_value");
 		 System.out.println(abdomin_distinction+"abdomindistinction"); 

   		// System.out.println(abdominSign+"abdentryyy"); 
   		long assess = datetime2.getTime()-assessment_time.getTime();
         int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
         
 		 System.out.println(assesstime+"assess time"); 
 		 Individual unique_3 = null; 
 	   
 	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
 	   {
            
 		    unique_3 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
 	   }

 	   else {
 		      unique_3 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
 	   }
 	   
   		 try 
   		 {
   		if(abdomin_distinction.contains("Visible Loops")||(abdomin_distinction.contentEquals("Visible Loops")))
   		{
            model.addLiteral(unique_3, hasVisibleBowelLoop,ResourceFactory.createTypedLiteral("true"));

 			 System.out.println("yessssvisible"); 

   		}
   	    }
   		 
   		  catch (Exception e)
  	    {
  		System.out.println(e);
  		} 	      
  	    }
  	        
     	    
     	    
//****************************************************************************************************************
      	    
      	    
      	    
      	    
      	  String query11=" select  uhid,entry_timestamp,stool  from apollo.nursing_intake_output WHERE uhid="+"'"+uhid+"'"+" AND stool='Blood' order by entry_timestamp";
    	    Statement stmt11 = con.createStatement();     
    	    rec11 = stmt11.executeQuery(query11);
    	    while(rec11.next())
	         {  
    	   	Date	assessment_time=rec11.getDate("entry_timestamp");
																																						
		 String blood_stool = rec11.getString("stool");
		 System.out.println(blood_stool+"blood in stool"); 

 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_4 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_4 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_4 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
 		 
 	
          model.addLiteral(unique_4,bloodPresentInStool,ResourceFactory.createTypedLiteral("true"));

			 System.out.println("yessssvisible");  			      
	    }
	        
//***************************************************************************************************************
    	    
        	  String query12=" Select be,entrydate,uhid from apollo.nursing_bloodgas  WHERE uhid="+"'"+uhid+"'"+" AND be is not null order by entrydate";
      	    Statement stmt12 = con.createStatement();     
      	    rec12 = stmt12.executeQuery(query12);
      	    while(rec12.next())
  	         {  
      	   	Date	assessment_time=rec12.getDate("entrydate");
  																																						
  		 float baseaccess = rec12.getFloat("be");
  		 System.out.println(baseaccess+"baseaccess"); 

   		long assess = datetime2.getTime()-assessment_time.getTime();
         int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
         
  		 System.out.println(assesstime+"assess time"); 
  		 Individual unique_5 = null; 
  	   
  	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
  	   {
            
  		    unique_5 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
  	   }

  	   else {
  		      unique_5 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
  	   }
  	   
  	   
   		 
   	       if(baseaccess<-6)
   	       {
               model.addLiteral(unique_5,hasMetabolicAcidosis,ResourceFactory.createTypedLiteral("true"));
    			 System.out.println("yessssmetabolic");  			      


   	       }

  	    }
      	    
//********************************************************************************************************   
      	    
      	    
      	  String query13=" Select uhid,modificationtime, apnea,apnea_duration,episodeid from apollo.nursing_episode WHERE uhid="+"'"+uhid+"'"+" AND apnea='t' order by modificationtime";
    	    Statement stmt13 = con.createStatement();     
    	    rec13 = stmt13.executeQuery(query13);
    	    while(rec13.next())
	         {  
    	   	Date	assessment_time=rec13.getDate("modificationtime");
																																						
		String apnea = rec13.getString("apnea");
		 System.out.println(apnea+"apnea"); 

 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_6 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_6 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_6 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
 		 
 	       {
             model.addLiteral(unique_6,hasApnea,ResourceFactory.createTypedLiteral("true"));
  			 System.out.println("yessssapne");  			      


 	       }

	    }   	    
      	    
//****************************************************************************************************   
    	    String query14=" Select resp_system_status,uhid,assessment_time from apollo.sa_resp_rds WHERE uhid="+"'"+uhid+"'"+" AND resp_system_status='Yes' order by assessment_time";
    	    Statement stmt14 = con.createStatement();     
    	    rec14 = stmt14.executeQuery(query14);
    	    while(rec14.next())
	         {  
    	   	Date	assessment_time=rec14.getDate("assessment_time");
																																						
		String resp_system = rec14.getString("resp_system_status");
		 System.out.println(resp_system+"resp_system"); 

 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_7 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_7 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_7 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
 		 
 	       {
             model.addLiteral(unique_7,hasRespiratoryDistress,ResourceFactory.createTypedLiteral("true"));
  			 System.out.println("yessssapne");  			      


 	       }

	    }   	    
      	        
     	    																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
 //*****************************************************************************************************
    	    
    	    
    	    String query15=" Select symptomatic_value,assessment_time,uhid from apollo.sa_infection_sepsis WHERE uhid="+"'"+uhid+"'"+" AND symptomatic_value is not null";
    	    Statement stmt15 = con.createStatement();     
    	    rec15 = stmt15.executeQuery(query15);
    	    while(rec15.next())
	         {  
    	   	Date	assessment_time=rec15.getDate("assessment_time");
																																						
		String symp = rec15.getString("symptomatic_value");
		
		 System.out.println(symp+"symptomatic"); 

 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_8 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_8 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_8 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
 		 if(symp.contains("Lethargy"))
 	       {
             model.addLiteral(unique_8,hasLethargy,ResourceFactory.createTypedLiteral("true"));
  			 System.out.println("yesslethargy");  			      


 	       }

	    }   	    
      	     
    	    
  //***********************************************************************************************  	    
    	    
    	    
    	  //****************************************************************************************************   
    	    String query16=" Select centraltemp,uhid,entrydate from apollo.nursing_vitalparameters WHERE uhid="+"'"+uhid+"'"+" AND centraltemp<36.5 or centraltemp>37.5 order by entrydate";
    	    Statement stmt16 = con.createStatement();     
    	    rec16 = stmt16.executeQuery(query16);
    	    Date newtime = null;
	    	int count = 0;

	    	Calendar cal = Calendar.getInstance();
	    	cal.add(Calendar.HOUR_OF_DAY, 10);
	    	
	    	
    	    while(rec16.next())
	         { 
    	   	Date assessment_time=rec16.getDate("entrydate");
    	   	Date assessment_time1=rec16.getTimestamp("entrydate");
    	   	
    	   
		/*																																			
 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
      
       if(newtime!=null)
       {
       long assess1 = assessment_time1.getTime()-newtime.getTime();

       int centraltime = (int) TimeUnit.MILLISECONDS.toHours(assess1);

		 System.out.println(assessment_time1+"assessment time1.........."); 
		 System.out.println(newtime+"new time1........."); 

			System.out.println(centraltime+"centraltmp........"); 

       if(centraltime<6 &centraltime>0)
       {
    	   count=count+1;
  		 System.out.println(count+"count value"); 

       }
       
       }
       
      
       
       
       
        newtime = assessment_time1;
       
        
		 //System.out.println(assesstime+"assess time"); 
		 Individual unique_8 = null; 
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_8= (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_8 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
	   if(count>1)
       
 	       {
             model.addLiteral(unique_8,hasTemperatureInStability,ResourceFactory.createTypedLiteral("true"));


 	       }
*/
	    }   	    
      	        
     	    																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																					
 //*****************************************************************************************************
    	    

    	    
    	    String query17=" Select prn,itemname,itemvalue,resultdate from apollo.test_result where prn="+"'"+uhid+"'" +"AND itemname='PLATELET COUNT' AND itemvalue is not null";
    	    Statement stmt17 = con.createStatement();     
    	    rec17 = stmt17.executeQuery(query17);
    
    	   
    	    	while(rec17.next())
	         {  
    	    	
    	   	Date	assessment_time=rec17.getDate("resultdate");
																																						
		int platelet = rec17.getInt("itemvalue");
		
		 System.out.println(platelet+"symptomatic"); 

 		long assess = datetime2.getTime()-assessment_time.getTime();
       int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
       
		 System.out.println(assesstime+"assess time"); 
		 Individual unique_10 = null; 
	         

	         
	   
	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
	   {
          
		    unique_10 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
	   }

	   else {
		      unique_10 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
	   }
	   
	   
 		 if(platelet<150000)
 	       {
             model.addLiteral(unique_10,haslowPlateletCount,ResourceFactory.createTypedLiteral("true"));
  			 System.out.println("yesspletelet");  			      


 	       }

	    }   	    
 	     
    	    
    	    
    	    
 //************************************************************************************************************************************   	    
    	
    	    	
    	    	
    	    	
    	    	
    	    	
    	    	  
        	    String query18=" select entrydate,uhid, baby_color from apollo.nursing_vitalparameters where uhid="+"'"+uhid+"'" +"AND  baby_color='Cyanosis'";
        	    Statement stmt18 = con.createStatement();     
        	    rec18 = stmt18.executeQuery(query18);
        
        	   
        	    	while(rec18.next())
    	         {  
        	    	
        	   	Date	assessment_time=rec18.getDate("entrydate");
    																																						
    		String discolor = rec18.getString("baby_color");
    		
    		 System.out.println(discolor+"discolor"); 

     		long assess = datetime2.getTime()-assessment_time.getTime();
           int assesstime = (int) TimeUnit.MILLISECONDS.toDays(assess);
           
    		 System.out.println(assesstime+"assess time"); 
    		 Individual unique_11 = null; 
    	         

    	         
    	   
    	   if(model.getIndividual(med+uhid+"_"+assesstime) != null)
    	   {
              
    		    unique_11 = (Individual) model.getIndividual(med+uhid+"_"+assesstime);
    	   }

    	   else {
    		      unique_11 = (Individual) model.createIndividual(med+uhid+"_"+assesstime,model.getOntClass("http://www.childhealthimprints.com/SampleOntology/Testing/#Neonate"));
    	   }
    	   
    	   
     		
     	       
                 model.addLiteral(unique_11,hasSkinDiscoloration,ResourceFactory.createTypedLiteral("true"));


     	       

    	    }   	    
     	     
        	    
        	    
//***********************************************************************************************************************    	    	
    	    	
    	    	
    	    	
    	    	
    	    	
    	    	
    	    	
OntClass uniqueid = null;


 {
 	 for(long p=doa_DOL;p<=currentDOL;p++)
 	 {    			ExtendedIterator classes = model.listClasses();
        		while (classes.hasNext())
        	  {
        		OntClass cls = (OntClass) classes.next();
        	//	System.out.print(cls.getLocalName());
        		for (Iterator i = cls.listSubClasses(true); i.hasNext();) 
        		{
            		OntClass c = (OntClass) i.next();
            		


            		if (c.getLocalName().contentEquals("Neonate"))

            		{
            	                	        
             			 ins = (Individual) model.createIndividual(med+uhid, c);
             		     unique = (Individual) model.createIndividual(med+uhid+"_"+p,c);

      /*       			 
             			String sql = "select entrydatetime,totalfeed_ml_per_kg_day,babyfeedid, working_weight from apollo.babyfeed_detail where uhid ="+"'"+uhid+"'";

             	         rec2 = stmt.executeQuery(sql);
             	        ResultSetMetaData rsmd = (ResultSetMetaData) rec2.getMetaData();
                        int numCols = rsmd.getColumnCount();
             	       
                        ArrayList <String> atmsend= new ArrayList<String>();
                       int q=0;
                       Set<String> hs = new HashSet<String>(); 
                          
             			 try {
`             		      while(rec2.next())
             	          {
             	         //     System.out.println(rec2.getString("entrydatetime")+"  "+rec2.getString("totalfeed_ml_per_kg_day")+" "+rec2.getString("babyfeedid")+" "+rec2.getDouble("working_weight"));  
             	             Date entrydatetime1=rec2.getDate("entrydatetime");            	             
             	            extnum=rec2.getString("entrydatetime");
             	            
             	           
            	             long diff = entrydatetime1.getTime() - datetime1.getTime();
            	             
            	              dayoflife =  (int) TimeUnit.MILLISECONDS.toDays(diff);
            	              
            	           System.out.println("Day of life is:");
            	             System.out.println(dayoflife);
                  			  model.add(ins, hasDayOfLife,ResourceFactory.createTypedLiteral(dayoflife));
                         		model.addLiteral(ins, hasGestationalAgeAtBirth,ResourceFactory.createTypedLiteral(gestationalweek,XSDDatatype.XSDdouble));
            	        	  	 model.addLiteral(ins, hasRiskFactor,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
            	            model.addLiteral(ins, hasFeedIntolerance,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
            	               model.addLiteral(ins, hasBabyID,ResourceFactory.createTypedLiteral(uhid));
            	               

             	            }             	                         	                      	        
             	          }
             		     catch(NullPointerException e) {
             	            System.out.println("Null pointer exception");
             	        }
             			 
           */  	 	
             			 //*******************************************\\\
             			
         	             if(currentDOL>=0&gestationalweek!=null&uhid!=null)
         	             {
               			  model.addLiteral(unique, hasDayOfLife,ResourceFactory.createTypedLiteral(p));
             	       		model.addLiteral(unique, hasGestationalAgeAtBirth,ResourceFactory.createTypedLiteral(gestationalweek,XSDDatatype.XSDdouble));
             	 //       	  	// model.addLiteral(unique, hasRiskFactor,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
             	            model.addLiteral(unique, hasFeedIntolerance,ResourceFactory.createTypedLiteral("false",XSDDatatype.XSDboolean));
             	               model.addLiteral(unique, hasBabyID,ResourceFactory.createTypedLiteral(uhid));
         
             	               
             	               if(chestcomp!=null)
             	            	   
             	               {
             	             model.addLiteral(unique, hasChestCompressionDuration,ResourceFactory.createTypedLiteral(chestcomp));
             	            	   }
             	              if(ph!=null)
            	            	   
            	               {
            	             model.addLiteral(unique,hasCordGaspHValue,ResourceFactory.createTypedLiteral(ph));
            	            	   }
             	               
             	             if(be!=null)
          	            	   
          	               {
          	             model.addLiteral(unique,hasBaseDeficitValue,ResourceFactory.createTypedLiteral(be));
          	            	   }
             	            
             	             if(babytype!=null)
          	            	   
          	               {
          	             model.addLiteral(unique,hasMonoTwinStatus,ResourceFactory.createTypedLiteral(twin_status));
          	            	   }
             	             
             	             if(val!=null)
   
           	               {
           	             model.addLiteral(unique,pressorGiven,ResourceFactory.createTypedLiteral(val));
           	            	   }
           	               
             	           
         	             }
         	             
         	             
         	           

         	  	         }
         	              
            		}
            		           		
            		          		
            		}
        		}

        		
 	 }
 {
 
 
	      
	      /*
	      if(rec5.getString("be")!=null)
	      {
			  model.addLiteral(unique, hasBaseDeficitValue,ResourceFactory.createTypedLiteral(rec5.getString("be")));

	         }*/
	        // }
            
            
 	 
        	 List rules = Rule.rulesFromURL("nutritionguideline.rules");
        	     GenericRuleReasoner reasoner = (GenericRuleReasoner)GenericRuleReasonerFactory.theInstance().create(null);
        	     ((FBRuleReasoner) reasoner).setRules(rules);
        	     ((GenericRuleReasoner) reasoner).setMode(GenericRuleReasoner.HYBRID);  
        	    reasoner.setDerivationLogging(true);

        	     InfModel inf = ModelFactory.createInfModel(reasoner, model);
 
        	     org.apache.jena.rdf.model.RDFWriter writer = model.getWriter("Turtle");
         		File file= new File("D:\\CHIL\\Nutrition\\infmodel.ttl");
            writer.write(inf, new FileOutputStream(file), med);
            
            
      
   
   String test="PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"
   		+ "SELECT  DISTINCT  ?FeedVolume ?FeedAdvancement ?DOL WHERE {{?Baby    CHIL:hasFeedingVolume   ?FeedVolume. ?Baby    CHIL:hasDayOfLife  ?DOL."
   		+ "?Baby CHIL:hasFeedingAdvancement   ?FeedAdvancement.} }";   
   	
   
   
   String test4="PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"
	   		+ "SELECT  DISTINCT ?DOL  ?FeedVolume WHERE{ { ?Baby    CHIL:hasDayOfLife  ?DOL} {?Baby   CHIL:hasFeedingVolume  ?FeedVolume}}order by ?DOL";
	   	
	   
	   
	     
   
   String test1= "PREFIX CHIL: <http://www.childhealthimprints.com/SampleOntology/Testing/>"

     		+ "SELECT  ?Baby ?FeedVolume   ?FeedAdvancement ?DayOfLife WHERE {"
     		+ " {?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasDayOfLife>  ?DayOfLife}" + 
     		 "{ ?Baby       CHIL:hasFeedingVolume   ?FeedVolume}" 
     		+ "{ ?Baby       CHIL:hasFeedingAdvancement   ?FeedAdvancement}  }";
     	
   
    
   String test2="PREFIX datatype:  <http://www.w3.org/2001/XMLSchema#>"
      		+ "SELECT ?UHID ?DayOfLife ?FeedVolume ?FeedAdvancement  ?feedintol    WHERE "
      		+ "{{ ?Baby  <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedingVolume> ?FeedVolume} "
      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedingAdvancement>  ?FeedAdvancement}"
      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasDayOfLife>  ?DayOfLife}         \r\n" + 
      		""

      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasBabyID>  ?UHID}"
      		+"OPTIONAL"
      		+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasFeedIntoleranceSign>  ?feedintol}"
     	//	+ "{?Baby <http://www.childhealthimprints.com/SampleOntology/Testing/hasLethargy>  ?VomitColor}"




      		+ "}orderby ?DayOfLife";   
      
	   Query query = QueryFactory.create(test2) ;
	        QueryExecution qexec = QueryExecutionFactory.create(query, inf) ;
	        ResultSet rs = qexec.execSelect() ;
	       ResultSetFormatter.out(System.out, rs, query);    
	       
   // Result to be saved: UHID,DOL, FeedVolume, FeedAdvancement 
   
  /* 
   Query query = QueryFactory.create(test2) ;
      QueryExecution qexec = QueryExecutionFactory.create(query, inf) ;
      ResultSet rs = qexec.execSelect() ;
      List vars=rs.getResultVars();

     // ResultSetFormatter.out(System.out, rs, query);  
   while (rs.hasNext()) {  // iterate over the result
       QuerySolution qs=rs.nextSolution();
     	  System.out.println("solu");
for(int i=0;i<vars.size();i++)
{ String var=vars.get(i).toString();
RDFNode node=qs.get(var);
System.out.println(var+"\t"+node.toString());

}
}
*/

        	    }
 

   	   }   	
	}

	     catch (Exception e)
	    {
		System.out.println(e);
		}
}

    
    }

