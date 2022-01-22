package com.inicu.postgres.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.inicu.postgres.entities.BabyDetail;
import com.inicu.postgres.entities.BabyVisit;
import com.inicu.postgres.entities.BedDeviceDetail;
import com.inicu.postgres.entities.DeviceDetail;
import com.inicu.postgres.entities.HeadtotoeExamination;
import com.inicu.postgres.entities.MaternalPasthistory;
import com.inicu.postgres.entities.MotherCurrentPregnancy;
import com.inicu.postgres.entities.ParentDetail;
import com.inicu.postgres.entities.RefBed;
import com.inicu.postgres.entities.RefCriticallevel;
import com.inicu.postgres.entities.RefExamination;
import com.inicu.postgres.entities.RefHeadtotoe;
import com.inicu.postgres.entities.RefHistory;
import com.inicu.postgres.entities.RefLevel;
import com.inicu.postgres.entities.RefRoom;
import com.inicu.postgres.entities.RefSignificantmaterial;

@Repository
public interface PatientDao {
	public Boolean AdmitPatient(BabyDetail babyDetails, ParentDetail parentDetails, MotherCurrentPregnancy motherCurrentPregnancy, BabyVisit babyFirstVisit, HeadtotoeExamination headtotoeExamination, List<MaternalPasthistory> list, String operationFrom);

	public List getListFromMappedObjNativeQuery(String queryStr);

	public void updateOrDeleteQuery(String query);
	
	//native update del query
	public void updateOrDeleteNativeQuery(String query);

	public Object saveObject(Object obj) throws Exception;
	
	public void executeInsertQuery(String query);
	
	public List getListFromNativeQuery(String queryStr);
	
	public void updateObject(Object obj) throws Exception;
}
