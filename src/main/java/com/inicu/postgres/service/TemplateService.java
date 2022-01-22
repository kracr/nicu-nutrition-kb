package com.inicu.postgres.service;

import java.util.List;

import org.springframework.stereotype.Repository;

//import com.oxyent.inicu.models.AddTemplate;


import com.inicu.models.DocTemplate;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.TemplateJSON;
import com.inicu.postgres.entities.DoctorNoteTemplates;
@Repository
public interface TemplateService {
	//boolean addTemplate(AddTemplate temp);
	public DocTemplate viewTemplate(String type1);
	public ResponseMessageObject addTemplate(TemplateJSON temp,String userId);
	public ResponseMessageObject deleteTemplate(String uhid,String loggeduser);
	public TemplateJSON getTemplateType(String id);
}
