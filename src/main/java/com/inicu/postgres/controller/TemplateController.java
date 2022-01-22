package com.inicu.postgres.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inicu.models.DocTemplate;
import com.inicu.models.ResponseMessageObject;
import com.inicu.models.TemplateJSON;
import com.inicu.postgres.service.TemplateService;
import com.inicu.postgres.utility.BasicUtils;

@RestController
public class TemplateController {
	
	@Autowired
	TemplateService templatePanel;
	
	
	
	@RequestMapping(value = "/inicu/doctor-notes/template/{value}", method = RequestMethod.POST)
	public ResponseEntity<DocTemplate> getTemplate(@PathVariable("value") String value){
		DocTemplate json = null;
		List<TemplateJSON> obj=null;
		if(!(BasicUtils.isEmpty(value)))
		{
			json = templatePanel.viewTemplate(value);
			obj=json.getListTemplate();
		}
		else
		{
			System.out.println("empty string accepted as plan");
		}
		return new ResponseEntity<DocTemplate>(json,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/inicu/doctor-notes/add-template/", method = RequestMethod.POST)
	public ResponseEntity<DocTemplate> addTemplate(@RequestBody DocTemplate template ){
		DocTemplate json = null;
		ResponseMessageObject obj = null;
		if(!BasicUtils.isEmpty(template)){
			obj = templatePanel.addTemplate(template.getTemplate(),template.getTemplate().getDoctorId());
			json = templatePanel.viewTemplate(template.getTemplate().getTemplateType());
			json.setResponse(obj);
		}
		return new ResponseEntity<DocTemplate>(json,HttpStatus.OK);
	}
	@RequestMapping(value = "/inicu/doctor-notes/delete-template/{templateId}/{loggeduser}", method = RequestMethod.POST)
	public ResponseEntity<DocTemplate> deleteTemplate(@PathVariable("templateId") String templateId,@PathVariable("loggeduser") String loggeduser){
		ResponseMessageObject obj = null;
		DocTemplate json=null;
		TemplateJSON tempJSON=new TemplateJSON();
		if(!BasicUtils.isEmpty(templateId)){
			tempJSON=templatePanel.getTemplateType(templateId);
			obj = templatePanel.deleteTemplate(templateId,loggeduser);
			System.out.println(tempJSON.getTemplateType());
			json = templatePanel.viewTemplate(tempJSON.getTemplateType());
			json.setResponse(obj);
		}
		return new ResponseEntity<DocTemplate>(json,HttpStatus.OK);
	}
	
	
	
	
}
