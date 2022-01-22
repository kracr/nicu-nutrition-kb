package com.inicu.models;

public class SystemicReportApneaJSON {
	
	private Integer SNo;
	private String Name;
	private String UHID;
	private String Center;
	private String DOB;
	private String TOB;
	private String DOA;
	private String TOA;
	private String DOD;
	private String Gestation_Weeks;
	private String Gestation_Days;
	private String Birth_Weight_Grams;
	private String Gender;
	private String Caffine_Given;
	private String Date_when_caffeine_started;
	private String Loading_Dose_mg_per_kg;
	private String Maintenance_Dose_mg_per_kg;
	private String Date_when_dose_was_escalated_1;
	private String Escalated_Dose_1_mg_per_kg;
	private String Date_when_dose_was_escalated_2;
	private String Escalated_Dose_2_mg_per_kg;
	private String Date_when_dose_was_escalated_3;
	private String Escalated_Dose_3_mg_per_kg;
	private String Date_when_caffine_stopped;
	private String Date_when_caffine_actually_stopped;
	private String Apnea;
	private String Date_when_first_Apnea_noted;
	private String Date_when_Apnea_stopped;
	private Integer Number_of_Apneic_episodes_before_starting_caffine;
	private Integer Number_of_Apneic_episodes_between_start_and_end_day_of_caffine;
	private Integer Number_of_Apneic_episodes_after_caffine;
	
	private Integer Number_of_Apneic_episodes_before_starting_caffine_device;
	private Integer Number_of_Apneic_episodes_between_start_and_end_day_of_caffine_device;
	private Integer Number_of_Apneic_episodes_after_caffine_device;
	
	private Integer Number_of_Recovered_Spontaneously;
	private Integer Number_of_Physical_Stimulation;
	private Integer Number_of_PPV;
	private String Caffine_restarting_after_stopping;
	private String Hypoxic;
	private String Date_when_first_Hypoxic_noted;
	private String Date_when_Hypoxic_stopped;
	private Integer Number_of_Hypoxic_episodes_before_starting_caffeine;
	private Integer Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine;
	private Integer Number_of_Hypoxic_episodes_after_caffeine;
	
	private Integer Number_of_Hypoxic_episodes_before_starting_caffeine_device;
	private Integer Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device;
	private Integer Number_of_Hypoxic_episodes_after_caffeine_device;
	
	private String Bradycardia;
	private String Date_when_first_Bradycardia_noted;
	private String Date_when_Bradycardia_stopped;
	private Integer Number_of_Bradycardia_episodes_before_starting_caffeine;
	private Integer Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine;
	private Integer Number_of_Bradycardia_episodes_after_caffeine;
	
	private Integer Number_of_Bradycardia_episodes_before_starting_caffeine_device;
	private Integer Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device;
	private Integer Number_of_Bradycardia_episodes_after_caffeine_device;
	
	private String Mean_HR_Before_starting_caffeine;
	private String Mean_HR_During_Caffeine;
	private String Mean_HR_After_stopping_caffeine;
	
	private Integer numberOfTimesCaffeineStopped;
	private Integer totalNumberOfDaysCaffeineGiven;
	
	private String Outcome;
	private String BPD_Stage;
	private String ROP;
	private String ROP_Stage;
	private String PDA;
	private String PDA_Treatment_Required;
	
	public String getPDA_Treatment_Required() {
		return PDA_Treatment_Required;
	}
	public void setPDA_Treatment_Required(String pDA_Treatment_Required) {
		PDA_Treatment_Required = pDA_Treatment_Required;
	}
	public String getROP() {
		return ROP;
	}
	public void setROP(String rOP) {
		ROP = rOP;
	}
	public Integer getSNo() {
		return SNo;
	}
	public void setSNo(Integer sNo) {
		SNo = sNo;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getUHID() {
		return UHID;
	}
	public void setUHID(String uHID) {
		UHID = uHID;
	}
	public String getCenter() {
		return Center;
	}
	public void setCenter(String center) {
		Center = center;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getTOB() {
		return TOB;
	}
	public void setTOB(String tOB) {
		TOB = tOB;
	}
	public String getDOA() {
		return DOA;
	}
	public void setDOA(String dOA) {
		DOA = dOA;
	}
	public String getTOA() {
		return TOA;
	}
	public void setTOA(String tOA) {
		TOA = tOA;
	}
	public String getGestation_Weeks() {
		return Gestation_Weeks;
	}
	public void setGestation_Weeks(String gestation_Weeks) {
		Gestation_Weeks = gestation_Weeks;
	}
	public String getGestation_Days() {
		return Gestation_Days;
	}
	public void setGestation_Days(String gestation_Days) {
		Gestation_Days = gestation_Days;
	}
	public String getBirth_Weight_Grams() {
		return Birth_Weight_Grams;
	}
	public void setBirth_Weight_Grams(String birth_Weight_Grams) {
		Birth_Weight_Grams = birth_Weight_Grams;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	
	public String getDate_when_caffeine_started() {
		return Date_when_caffeine_started;
	}
	public void setDate_when_caffeine_started(String date_when_caffeine_started) {
		Date_when_caffeine_started = date_when_caffeine_started;
	}
	
	public String getDate_when_dose_was_escalated_1() {
		return Date_when_dose_was_escalated_1;
	}
	public void setDate_when_dose_was_escalated_1(String date_when_dose_was_escalated_1) {
		Date_when_dose_was_escalated_1 = date_when_dose_was_escalated_1;
	}
	
	public String getDate_when_dose_was_escalated_2() {
		return Date_when_dose_was_escalated_2;
	}
	public void setDate_when_dose_was_escalated_2(String date_when_dose_was_escalated_2) {
		Date_when_dose_was_escalated_2 = date_when_dose_was_escalated_2;
	}
	
	public String getDate_when_dose_was_escalated_3() {
		return Date_when_dose_was_escalated_3;
	}
	public void setDate_when_dose_was_escalated_3(String date_when_dose_was_escalated_3) {
		Date_when_dose_was_escalated_3 = date_when_dose_was_escalated_3;
	}
	
	public String getApnea() {
		return Apnea;
	}
	public void setApnea(String apnea) {
		Apnea = apnea;
	}
	public String getDate_when_first_Apnea_noted() {
		return Date_when_first_Apnea_noted;
	}
	public void setDate_when_first_Apnea_noted(String date_when_first_Apnea_noted) {
		Date_when_first_Apnea_noted = date_when_first_Apnea_noted;
	}
	public String getDate_when_Apnea_stopped() {
		return Date_when_Apnea_stopped;
	}
	public void setDate_when_Apnea_stopped(String date_when_Apnea_stopped) {
		Date_when_Apnea_stopped = date_when_Apnea_stopped;
	}
	public Integer getNumber_of_Recovered_Spontaneously() {
		return Number_of_Recovered_Spontaneously;
	}
	public void setNumber_of_Recovered_Spontaneously(Integer number_of_Recovered_Spontaneously) {
		Number_of_Recovered_Spontaneously = number_of_Recovered_Spontaneously;
	}
	public Integer getNumber_of_Physical_Stimulation() {
		return Number_of_Physical_Stimulation;
	}
	public void setNumber_of_Physical_Stimulation(Integer number_of_Physical_Stimulation) {
		Number_of_Physical_Stimulation = number_of_Physical_Stimulation;
	}
	public Integer getNumber_of_PPV() {
		return Number_of_PPV;
	}
	public void setNumber_of_PPV(Integer number_of_PPV) {
		Number_of_PPV = number_of_PPV;
	}
	public String getCaffine_Given() {
		return Caffine_Given;
	}
	public void setCaffine_Given(String caffine_Given) {
		Caffine_Given = caffine_Given;
	}
	public String getDate_when_caffine_stopped() {
		return Date_when_caffine_stopped;
	}
	public void setDate_when_caffine_stopped(String date_when_caffine_stopped) {
		Date_when_caffine_stopped = date_when_caffine_stopped;
	}
	public Integer getNumber_of_Apneic_episodes_before_starting_caffine() {
		return Number_of_Apneic_episodes_before_starting_caffine;
	}
	public void setNumber_of_Apneic_episodes_before_starting_caffine(
			Integer number_of_Apneic_episodes_before_starting_caffine) {
		Number_of_Apneic_episodes_before_starting_caffine = number_of_Apneic_episodes_before_starting_caffine;
	}
	public Integer getNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine() {
		return Number_of_Apneic_episodes_between_start_and_end_day_of_caffine;
	}
	public void setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine(
			Integer number_of_Apneic_episodes_between_start_and_end_day_of_caffine) {
		Number_of_Apneic_episodes_between_start_and_end_day_of_caffine = number_of_Apneic_episodes_between_start_and_end_day_of_caffine;
	}
	public String getCaffine_restarting_after_stopping() {
		return Caffine_restarting_after_stopping;
	}
	public void setCaffine_restarting_after_stopping(String caffine_restarting_after_stopping) {
		Caffine_restarting_after_stopping = caffine_restarting_after_stopping;
	}
	public String getHypoxic() {
		return Hypoxic;
	}
	public void setHypoxic(String hypoxic) {
		Hypoxic = hypoxic;
	}
	public String getDate_when_first_Hypoxic_noted() {
		return Date_when_first_Hypoxic_noted;
	}
	public void setDate_when_first_Hypoxic_noted(String date_when_first_Hypoxic_noted) {
		Date_when_first_Hypoxic_noted = date_when_first_Hypoxic_noted;
	}
	public String getDate_when_Hypoxic_stopped() {
		return Date_when_Hypoxic_stopped;
	}
	public void setDate_when_Hypoxic_stopped(String date_when_Hypoxic_stopped) {
		Date_when_Hypoxic_stopped = date_when_Hypoxic_stopped;
	}
	public Integer getNumber_of_Hypoxic_episodes_before_starting_caffeine() {
		return Number_of_Hypoxic_episodes_before_starting_caffeine;
	}
	public void setNumber_of_Hypoxic_episodes_before_starting_caffeine(
			Integer number_of_Hypoxic_episodes_before_starting_caffeine) {
		Number_of_Hypoxic_episodes_before_starting_caffeine = number_of_Hypoxic_episodes_before_starting_caffeine;
	}
	public Integer getNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine() {
		return Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine;
	}
	public void setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine(
			Integer number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine) {
		Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine = number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine;
	}
	public String getMean_HR_Before_starting_caffeine() {
		return Mean_HR_Before_starting_caffeine;
	}
	public void setMean_HR_Before_starting_caffeine(String mean_HR_Before_starting_caffeine) {
		Mean_HR_Before_starting_caffeine = mean_HR_Before_starting_caffeine;
	}
	public String getMean_HR_During_Caffeine() {
		return Mean_HR_During_Caffeine;
	}
	public void setMean_HR_During_Caffeine(String mean_HR_During_Caffeine) {
		Mean_HR_During_Caffeine = mean_HR_During_Caffeine;
	}
	public String getMean_HR_After_stopping_caffeine() {
		return Mean_HR_After_stopping_caffeine;
	}
	public void setMean_HR_After_stopping_caffeine(String mean_HR_After_stopping_caffeine) {
		Mean_HR_After_stopping_caffeine = mean_HR_After_stopping_caffeine;
	}
	public String getOutcome() {
		return Outcome;
	}
	public void setOutcome(String outcome) {
		Outcome = outcome;
	}
	public String getBPD_Stage() {
		return BPD_Stage;
	}
	public void setBPD_Stage(String bPD_Stage) {
		BPD_Stage = bPD_Stage;
	}
	public String getROP_Stage() {
		return ROP_Stage;
	}
	public void setROP_Stage(String rOP_Stage) {
		ROP_Stage = rOP_Stage;
	}
	public String getPDA() {
		return PDA;
	}
	public void setPDA(String pDA) {
		PDA = pDA;
	}
	public String getDOD() {
		return DOD;
	}
	public void setDOD(String dOD) {
		DOD = dOD;
	}
	public String getBradycardia() {
		return Bradycardia;
	}
	public void setBradycardia(String bradycardia) {
		Bradycardia = bradycardia;
	}
	public String getDate_when_first_Bradycardia_noted() {
		return Date_when_first_Bradycardia_noted;
	}
	public void setDate_when_first_Bradycardia_noted(String date_when_first_Bradycardia_noted) {
		Date_when_first_Bradycardia_noted = date_when_first_Bradycardia_noted;
	}
	public String getDate_when_Bradycardia_stopped() {
		return Date_when_Bradycardia_stopped;
	}
	public void setDate_when_Bradycardia_stopped(String date_when_Bradycardia_stopped) {
		Date_when_Bradycardia_stopped = date_when_Bradycardia_stopped;
	}
	public Integer getNumber_of_Bradycardia_episodes_before_starting_caffeine() {
		return Number_of_Bradycardia_episodes_before_starting_caffeine;
	}
	public void setNumber_of_Bradycardia_episodes_before_starting_caffeine(
			Integer number_of_Bradycardia_episodes_before_starting_caffeine) {
		Number_of_Bradycardia_episodes_before_starting_caffeine = number_of_Bradycardia_episodes_before_starting_caffeine;
	}
	public Integer getNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine() {
		return Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine;
	}
	public void setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine(
			Integer number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine) {
		Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine = number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine;
	}
	public String getLoading_Dose_mg_per_kg() {
		return Loading_Dose_mg_per_kg;
	}
	public void setLoading_Dose_mg_per_kg(String loading_Dose_mg_per_kg) {
		Loading_Dose_mg_per_kg = loading_Dose_mg_per_kg;
	}
	public String getMaintenance_Dose_mg_per_kg() {
		return Maintenance_Dose_mg_per_kg;
	}
	public void setMaintenance_Dose_mg_per_kg(String maintenance_Dose_mg_per_kg) {
		Maintenance_Dose_mg_per_kg = maintenance_Dose_mg_per_kg;
	}
	public String getEscalated_Dose_1_mg_per_kg() {
		return Escalated_Dose_1_mg_per_kg;
	}
	public void setEscalated_Dose_1_mg_per_kg(String escalated_Dose_1_mg_per_kg) {
		Escalated_Dose_1_mg_per_kg = escalated_Dose_1_mg_per_kg;
	}
	public String getEscalated_Dose_2_mg_per_kg() {
		return Escalated_Dose_2_mg_per_kg;
	}
	public void setEscalated_Dose_2_mg_per_kg(String escalated_Dose_2_mg_per_kg) {
		Escalated_Dose_2_mg_per_kg = escalated_Dose_2_mg_per_kg;
	}
	public String getEscalated_Dose_3_mg_per_kg() {
		return Escalated_Dose_3_mg_per_kg;
	}
	public void setEscalated_Dose_3_mg_per_kg(String escalated_Dose_3_mg_per_kg) {
		Escalated_Dose_3_mg_per_kg = escalated_Dose_3_mg_per_kg;
	}
	public Integer getNumber_of_Apneic_episodes_before_starting_caffine_device() {
		return Number_of_Apneic_episodes_before_starting_caffine_device;
	}
	public void setNumber_of_Apneic_episodes_before_starting_caffine_device(
			Integer number_of_Apneic_episodes_before_starting_caffine_device) {
		Number_of_Apneic_episodes_before_starting_caffine_device = number_of_Apneic_episodes_before_starting_caffine_device;
	}
	public Integer getNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine_device() {
		return Number_of_Apneic_episodes_between_start_and_end_day_of_caffine_device;
	}
	public void setNumber_of_Apneic_episodes_between_start_and_end_day_of_caffine_device(
			Integer number_of_Apneic_episodes_between_start_and_end_day_of_caffine_device) {
		Number_of_Apneic_episodes_between_start_and_end_day_of_caffine_device = number_of_Apneic_episodes_between_start_and_end_day_of_caffine_device;
	}
	public Integer getNumber_of_Hypoxic_episodes_before_starting_caffeine_device() {
		return Number_of_Hypoxic_episodes_before_starting_caffeine_device;
	}
	public void setNumber_of_Hypoxic_episodes_before_starting_caffeine_device(
			Integer number_of_Hypoxic_episodes_before_starting_caffeine_device) {
		Number_of_Hypoxic_episodes_before_starting_caffeine_device = number_of_Hypoxic_episodes_before_starting_caffeine_device;
	}
	public Integer getNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device() {
		return Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device;
	}
	public void setNumber_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device(
			Integer number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device) {
		Number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device = number_of_Hypoxic_episodes_between_start_and_end_day_of_caffeine_device;
	}
	public Integer getNumber_of_Bradycardia_episodes_before_starting_caffeine_device() {
		return Number_of_Bradycardia_episodes_before_starting_caffeine_device;
	}
	public void setNumber_of_Bradycardia_episodes_before_starting_caffeine_device(
			Integer number_of_Bradycardia_episodes_before_starting_caffeine_device) {
		Number_of_Bradycardia_episodes_before_starting_caffeine_device = number_of_Bradycardia_episodes_before_starting_caffeine_device;
	}
	public Integer getNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device() {
		return Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device;
	}
	public void setNumber_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device(
			Integer number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device) {
		Number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device = number_of_Bradycardia_episodes_between_start_and_end_day_of_caffeine_device;
	}
	public Integer getNumber_of_Apneic_episodes_after_caffine() {
		return Number_of_Apneic_episodes_after_caffine;
	}
	public void setNumber_of_Apneic_episodes_after_caffine(Integer number_of_Apneic_episodes_after_caffine) {
		Number_of_Apneic_episodes_after_caffine = number_of_Apneic_episodes_after_caffine;
	}
	public Integer getNumber_of_Apneic_episodes_after_caffine_device() {
		return Number_of_Apneic_episodes_after_caffine_device;
	}
	public void setNumber_of_Apneic_episodes_after_caffine_device(Integer number_of_Apneic_episodes_after_caffine_device) {
		Number_of_Apneic_episodes_after_caffine_device = number_of_Apneic_episodes_after_caffine_device;
	}
	public Integer getNumber_of_Hypoxic_episodes_after_caffeine() {
		return Number_of_Hypoxic_episodes_after_caffeine;
	}
	public void setNumber_of_Hypoxic_episodes_after_caffeine(Integer number_of_Hypoxic_episodes_after_caffeine) {
		Number_of_Hypoxic_episodes_after_caffeine = number_of_Hypoxic_episodes_after_caffeine;
	}
	public Integer getNumber_of_Hypoxic_episodes_after_caffeine_device() {
		return Number_of_Hypoxic_episodes_after_caffeine_device;
	}
	public void setNumber_of_Hypoxic_episodes_after_caffeine_device(
			Integer number_of_Hypoxic_episodes_after_caffeine_device) {
		Number_of_Hypoxic_episodes_after_caffeine_device = number_of_Hypoxic_episodes_after_caffeine_device;
	}
	public Integer getNumber_of_Bradycardia_episodes_after_caffeine() {
		return Number_of_Bradycardia_episodes_after_caffeine;
	}
	public void setNumber_of_Bradycardia_episodes_after_caffeine(Integer number_of_Bradycardia_episodes_after_caffeine) {
		Number_of_Bradycardia_episodes_after_caffeine = number_of_Bradycardia_episodes_after_caffeine;
	}
	public Integer getNumber_of_Bradycardia_episodes_after_caffeine_device() {
		return Number_of_Bradycardia_episodes_after_caffeine_device;
	}
	public void setNumber_of_Bradycardia_episodes_after_caffeine_device(
			Integer number_of_Bradycardia_episodes_after_caffeine_device) {
		Number_of_Bradycardia_episodes_after_caffeine_device = number_of_Bradycardia_episodes_after_caffeine_device;
	}
	public String getDate_when_caffine_actually_stopped() {
		return Date_when_caffine_actually_stopped;
	}
	public void setDate_when_caffine_actually_stopped(String date_when_caffine_actually_stopped) {
		Date_when_caffine_actually_stopped = date_when_caffine_actually_stopped;
	}
	public Integer getNumberOfTimesCaffeineStopped() {
		return numberOfTimesCaffeineStopped;
	}
	public void setNumberOfTimesCaffeineStopped(Integer numberOfTimesCaffeineStopped) {
		this.numberOfTimesCaffeineStopped = numberOfTimesCaffeineStopped;
	}
	public Integer getTotalNumberOfDaysCaffeineGiven() {
		return totalNumberOfDaysCaffeineGiven;
	}
	public void setTotalNumberOfDaysCaffeineGiven(Integer totalNumberOfDaysCaffeineGiven) {
		this.totalNumberOfDaysCaffeineGiven = totalNumberOfDaysCaffeineGiven;
	}

}
