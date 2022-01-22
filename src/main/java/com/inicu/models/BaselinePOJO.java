package com.inicu.models;

public class BaselinePOJO {


	public String Count;
	public String Gestation;
	public String BirthWeight;
	public String LOS;
	public String AdmissionWeight;
	public String Male;
	public String Female;
	public String APGAROne;
	public String APGARFive;
	public String NVD;
	public String LSCS;
	public String Inborn;
	public String Outborn;
	public String JaundiceTrue;
	public String JaundiceFalse;
	public String SepsisTrue;
	public String SepsisFalse;
	public String RDSTrue;
	public String RDSFalse;
	public String Single;
	public String Multiple;
	public String AntenatalSteroids;
	public String NoAntenatalSteroids;
	
	
	public void setCount(String count) {
		Count = count;
	}


	public void setGestation(String gestation) {
		Gestation = gestation;
	}


	public void setBirthWeight(String birthWeight) {
		BirthWeight = birthWeight;
	}


	public void setLOS(String lOS) {
		LOS = lOS;
	}


	public void setAdmissionWeight(String admissionWeight) {
		AdmissionWeight = admissionWeight;
	}


	public void setMale(String male) {
		Male = male;
	}


	public void setFemale(String female) {
		Female = female;
	}


	public void setAPGAROne(String aPGAROne) {
		APGAROne = aPGAROne;
	}


	public void setAPGARFive(String aPGARFive) {
		APGARFive = aPGARFive;
	}


	public void setNVD(String nVD) {
		NVD = nVD;
	}


	public void setLSCS(String lSCS) {
		LSCS = lSCS;
	}


	public void setInborn(String inborn) {
		Inborn = inborn;
	}


	public void setOutborn(String outborn) {
		Outborn = outborn;
	}


	public void setJaundiceTrue(String jaundiceTrue) {
		JaundiceTrue = jaundiceTrue;
	}


	public void setJaundiceFalse(String jaundiceFalse) {
		JaundiceFalse = jaundiceFalse;
	}


	public void setSepsisTrue(String sepsisTrue) {
		SepsisTrue = sepsisTrue;
	}


	public void setSepsisFalse(String sepsisFalse) {
		SepsisFalse = sepsisFalse;
	}


	public void setRDSTrue(String rDSTrue) {
		RDSTrue = rDSTrue;
	}


	public void setRDSFalse(String rDSFalse) {
		RDSFalse = rDSFalse;
	}


	public void setSingle(String single) {
		Single = single;
	}


	public void setMultiple(String multiple) {
		Multiple = multiple;
	}


	public void setAntenatalSteroids(String antenatalSteroids) {
		AntenatalSteroids = antenatalSteroids;
	}


	public void setNoAntenatalSteroids(String noAntenatalSteroids) {
		NoAntenatalSteroids = noAntenatalSteroids;
	}


	@Override
	public String toString() {
		return "BaselinePOJO [Count=" + Count + ", Gestation=" + Gestation + ", BirthWeight=" + BirthWeight + ", LOS="
				+ LOS + ", AdmissionWeight=" + AdmissionWeight + ", Male=" + Male + ", Female=" + Female + ", APGAROne="
				+ APGAROne + ", APGARFive=" + APGARFive + ", NVD=" + NVD + ", LSCS=" + LSCS + ", Inborn=" + Inborn
				+ ", Outborn=" + Outborn + ", JaundiceTrue=" + JaundiceTrue + ", JaundiceFalse=" + JaundiceFalse
				+ ", SepsisTrue=" + SepsisTrue + ", SepsisFalse=" + SepsisFalse + ", RDSTrue=" + RDSTrue + ", RDSFalse="
				+ RDSFalse + ", Single=" + Single + ", Multiple=" + Multiple + ", AntenatalSteroids="
				+ AntenatalSteroids + ", NoAntenatalSteroids=" + NoAntenatalSteroids + "]";
	}


}
