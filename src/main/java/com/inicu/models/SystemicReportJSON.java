package com.inicu.models;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class SystemicReportJSON {

	private String uhid;
	private String name;
	private float gestation_weeks;
	private float gestation_days;
	private Integer disch_gestation_weeks;
	private Integer disch_gestation_days;
	private float birth_weight;
	private String gender;
	private Integer antenata_Steroids;
	private String doseName;
	private Integer numberOfDose;	
	private String chorioamniotis;
	private Integer pprom_Risk_Factor;
	private String mgso4;
	private String mode_Of_Delivery;
	private Integer onemin_APGAR;
	private Integer fivemin_APGAR;
	private String ppv_asphyxia;
	private Integer surfactant;
	private String surfactantType;
	private Long cpapDaysVLBW;
	private Long invasiveDaysVLBW;
	private Long lowFlowDaysVLBW;
	private Long highFlowDaysVLBW;
	private Long nimvDaysVLBW;
	private Integer patient_stay;
	private String probableSepsis;
	private String provenSepsis;
	private String outcome;
	private Float discharge_weight;

	
	private String hoursAtAdmission;
	private String IUGR;
	private String suspectedSepsis;

	
	private long medicationTotalOrders;
	private long medicationTotalExecutions;
	private long nutritionTotalOrders;
	private long nutritionTotalExecutions;
	private String medicationsGiven;

	private String Registration_date;
	private String Initial_assessment_date;
	private String First_anthropometry_date;
	
	private float Registration_initialassessment_difference;
	private float Registration_anthropometry_difference;
	private String MEDICATION_HRS;
	private String MEDICATION_TOTAL;
	private String MEDICATION_FINAL;
	private String NUTRITION_HRS;
	private String NUTRITION_TOTAL;
	private String NUTRITION_FINAL;
	
	private Integer ropCount;
	private Integer usgCount;
	private Integer neurologicalCount;
	private Integer hearingCount;
	private Integer metabolicCount;
	
	private Date ropDueDate;
	private Date usgDueDate;
	
	private Integer peripheralCount;
	private Integer centralLineCount;
	private Integer lumbarPunctureCount;
	private Integer vTapCount;
	private Integer etIntubationCount;
	private Integer etSuctionCount;
	private Integer chestTubeCount;
	private Integer exchangeTransfusionCount;
	private Integer peritonealDialysisCount;

	
	
	
	
	
	private String growthVelocity;
	private String totalFluidLimit;
	private String Calorie_Intake;
	private String Protein_Intake;
	private String Calcium_Intake;
	private String Phosphorus_Intake;
	private String VitaminD_Intake;
	private String ageAtFullFeeds;
	private String ageAtFeed;
	private String humanMilkDays;
	private String feedIncrement;
	private String respiratoryDaysVLBW;
	private String antibioticDaysVLBW;
	private String umbilicalDopplerVLBW;
	private String umbilicalDopplerIncreasedVLBW;
	private String umbilicalDopplerReverseVLBW;
	private String umbilicalDopplerAbsentVLBW;
	
	private String cpapCountVLBW;
	private String HHHFNNCCountVLBW;
	private String invasiveCountVLBW;
	private Long HHHFNNCDaysVLBW;

	
	
	
	
	private String promInvasive;
	private String surfactantTimingInvasive;
	private String tvInvasive;

	private Integer EONS;
	private Integer LONS;
	
	
	private String crp;
	private String tlc;
	private String neotrophil;

	
	private String riskFactorJaundice;

	private Integer respDuration;

	private String cpapTime;
	private String mapSurfactant;
	private String fio2Surfactant;
	private String surfactantTime;
	
	private String surfactantNurseTime;
	private String insure;
	private String sepsisMech;
	private String pressureControl;
	private String volumeControl;
	private String tv;
	private String peep;
	private String antibioticDaysSepsis;
	private Integer RiskFactors;
	private Integer Symptoms;
	private Integer antibioticLine1;
	private Integer antibioticLine2;
	private Integer antibioticLine3;
	private Integer antibioticLine4;
	private Integer antibioticLine5;
	private String phototherapyDuration;
	private String tcb;
	private String weightLoss;
	private String babyBloodGroup;
	
	private String jaundiceOccured;


	private float firsttcb;
	private float secondtcb;
	private String rdsIncidence;
	private String rdsAntenatalSteroid;
	private String rdsSurfactant;
	private String rdsInvasiveVentilationDays;


	private String surfactantDose;

	private String firstDownes;
	
	private String bpd;
	private String conception;
	private String kmcDuration;
	
	private Integer antibioticInEpisode;
	
	
	private int anthropometryAdmission;
	private String initialAbdGirth;
	private String initalDateAbd;
	private float initialEnteralVol;

	private String finalAbdGirth;
	private String finalDateAbd;
	private float finalEnteralVol;
	private String initialGastricAsp;
	
	private String finalGastricAsp;
	
	
	private String dob;
	private String tob;
	private String doa;
	private String dod;
	private String se_status;


	private String patient_type; // inborn outborn
	
	
	private Integer Prematurity_Reason;
	
	
	private Integer Jaundice_Reason;
	
	
	private Integer Respiratory_Distress_Reason;
	
	
	private Integer Apnea_Reason;
	
	
	private Integer PPHN_Reason;
	
	
	private Integer Pneumothorax_Reason;
	
	
	private Integer Sepsis_Reason;
	
	
	private Integer Asphyxia_Reason;
	
	
	private Integer Seizures_Reason;
	
	
	private Integer IVH_Reason;
	
	
	private Integer Hypoglycemia_Reason;
	
	
	private Integer Hyperglycemia_Reason;
	
	
	private Integer Hyponatremia_Reason;
	
	
	private Integer Hypernatremia_Reason;
	
	
	private Integer Hypocalcemia_Reason;
	
	
	private Integer Hypercalcemia_Reason;
	
	
	private Integer Hypokalemia_Reason;
	
	
	private Integer Hyperkalemia_Reason;
	
	
	private Integer Acidosis_Reason;
	
	
	private Integer IEM_Reason;
	
	
	private Integer Feed_Intolerance_Reason;
	
	
	private Integer Other_Reason;
	
	
	private Integer Single_Pregnancy;
	
	
	private Integer Twins;
	
	
	private Integer Triplets;
	
	
	private Integer Quadruplets;
	
	
	private Integer less_than_26_week_Gestation;
	
	
	private Integer Gestation_26_28_week;
	
	
	private Integer Gestation_28_32_week;
	
	
	private Integer Gestation_32_36_week ;
	
	
	private Integer Greater_than_36_week_Gestation;

	
	private Integer G;
	
	
	private Integer P;
	
	
	private Integer A;
	
	
	private Integer L;
	
	
	private Integer Smoking_Risk_Factor;
	
	
	private Integer Alcoholism_Risk_Factor;
	
	
	private Integer Hypertension_Risk_Factor;
	
	
	private Integer Gestational_Hypertension_Risk_Factor;
	
	
	private Integer Diabetes_Risk_Factor;
	
	
	private Integer Gestational_Diabetes_Milleteus_Risk_Factor;
	
	
	private Integer Chronic_Kidney_Disease_Risk_Factor;
	
	
	private Integer Hypothyroidism_Risk_Factor;
	
	
	private Integer Hyperthyroidism_Risk_Factor;
	
	
	private Integer Fever_Risk_Factor;
	
	
	private Integer UTI_Risk_Factor;
	
	
	private Integer Infections_Risk_Factor;
	
	
	private Integer PROM_Risk_Factor;
	
	
	
	
	
	private Integer Prematurity_Risk_Factor;
	
	
	private Integer Chorioamniotis_Risk_Factor;
	
	
	private Integer Oligohydraminos_Risk_Factor;
	
	
	private Integer Polyhydraminos_Risk_Factor;
	
	
	private Integer HIV;
	
	
	private Integer Hep_B;
	
	
	private Integer VDRL;

	
	private String Mother_Blood_Group;
	
	
	private Integer TORCH;
	 
	
	private Integer Tetanus_Toxoid;
	
	

	
	private Integer Fivemin_APGAR_asphyxia;
	
	private String ph_asphyxia;
	
	private String sensorium_asphyxia;
	
	
	
	private float admission_weight;
	


	private Integer BW_less_than_500;
	
	
	private Integer BW_between_500_999;
	
	
	private Integer BW_bewtween_1000_1499;
	
	
	private Integer BW_between_1500_1999;
	
	
	private Integer BW_greater_than_2000;
	
	private Float discharge_hc;
	
	private Float discharge_length;

	private String birth_weight_level;
	
	private Float Weight_Centile;
	
	private Float birth_hc;
	
	private Float Head_Circumference_Centile;
	private Float birth_length;
	
	private Float Length_Centile;
	
	private Integer ponderal_index;

	
	
	private String General_Apearance;
	
	private String NEC;
	private String Apnea;
	private Integer Chest;
	 
	
	private Integer Head_and_Neck;
	
	
	private Integer Abdomen;
	
	
	private Integer Palate;
	
	
	private String Anal_opening;
	
	
	private Integer Lips;
	
	
	private Integer Genitals;
	
	
	private Integer Eyes;
	
	
	private Integer Reflexes;
	
	
	private Integer Skin;
	
	
	private Integer Apparent_Congenital_malformation;
	
	
	private Integer Peripheral_Cannula;
	
	
	private Integer Central_Line;
	
	
	private Integer Lumbar_Puncture;
	
	
	private Integer VTap;
	
	
	private Integer Chest_Tube;
	
	
	private Integer ET_Intubation;
	
	
	private Integer ET_Suction;
	
	
	private Integer Exchange_Transfusion;
	
	
	private Integer First_Downes;
	
	
	private Integer Last_Downes;
	
	
	private Integer Highest_Downes;
	
	
	
	
	
	private Integer Cummulative_Apnea_On_Caffeine;
	
	
	private Integer Continuous_Apnea_On_Caffeine;
	
	
	private Integer Apnea_Free_Days_After_Caffeine;
	
	
	private Integer Cummulative_Number_Of_Episodes;
	
	
	private Integer Cummulative_Days_Of_Caffeine;
	
	
	private Integer Enteral_Duration;
	
	
	private Integer Calcium_EN_Additive;
	
	
	private Integer Iron_EN_Additive;
	
	
	private Integer VitaminA_EN_Additive;
	
	
	private Integer VitaminD_EN_Additive;
	
	
	private Integer MCT_Oil_Additive;
	
	
	private Integer EBM_Duration;
	
	
	private Integer Parenteral_Duration;
	
	
	private Integer Bolus_Count;
	
	
	private Integer Amino_Acid_Duration;
	
	
	private Integer Lipid_Duration;
	
	
	private Integer PN_Additives_Duration;
	
	
	private Integer Dextrose_Duration;
	
	
	private Integer Antibiotics;
	
	
	private Integer Analgesic;
	
	
	private Integer Pulmonary_Vasodilators;
	
	
	private Integer Sedative;
	
	
	private Integer Inotropes;
	
	
	private Integer Blood_Product;

	
	private Boolean is_jaundice;
	private Integer no_jaundice;
	private List<JaundiceReportJSON> jaundiceList;

	private Boolean is_rds;
	private Integer no_rds;
	private List<RdsReportJSON> rdsList;

	private Boolean is_sepsis;
	private Integer no_sepsis;
	private List<SepsisReportJSON> sepsisList;
	
	private Boolean is_pphn;
	private Integer no_pphn;
	private List<PPHNReportJSON> pphnList;
	
	private Boolean is_pneumothorax;
	private Integer no_pneumothorax;
	private List<PneumothoraxReportJSON> pneumothoraxList;
	
	private Boolean is_seizures;
	private Integer no_seizures;
	private List<SeizuresReportJSON> seizuresList;
	
	private Boolean is_asphyxia;
	private Integer no_asphyxia;
	private List<AsphyxiaReportJSON> asphyxiaList;

	public SystemicReportJSON() {
		super();
		this.no_jaundice = 0;
		this.is_jaundice = false;
		this.jaundiceList = new ArrayList<JaundiceReportJSON>();

		this.no_rds = 0;
		this.is_rds = false;
		this.rdsList = new ArrayList<RdsReportJSON>();

		this.no_sepsis = 0;
		this.is_sepsis = false;
		this.sepsisList = new ArrayList<SepsisReportJSON>();
		
		this.no_pneumothorax = 0;
		this.is_pneumothorax = false;
		this.pneumothoraxList = new ArrayList<PneumothoraxReportJSON>();
		
		this.no_pphn = 0;
		this.is_pphn = false;
		this.pphnList = new ArrayList<PPHNReportJSON>();
		
		this.no_seizures = 0;
		this.is_seizures = false;
		this.seizuresList = new ArrayList<SeizuresReportJSON>();
		
		this.no_asphyxia = 0;
		this.is_asphyxia = false;
		this.asphyxiaList = new ArrayList<AsphyxiaReportJSON>();
	}

}
