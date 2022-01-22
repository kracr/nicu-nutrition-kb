package com.inicu.models;

import java.util.List;

public class DocTemplate {
	ResponseMessageObject response;
	
	TemplateJSON template;
	List<TemplateJSON> listTemplate;
	String userId;

	public ResponseMessageObject getResponse() {
		return response;
	}

	public void setResponse(ResponseMessageObject response) {
		this.response = response;
	}
	public TemplateJSON getTemplate() {
		return template;
	}

	public void setTemplate(TemplateJSON template) {
		this.template = template;
	} 

	public List<TemplateJSON> getListTemplate() {
		return listTemplate;
	}

	public void setListTemplate(List<TemplateJSON> listTemplate) {
		this.listTemplate = listTemplate;
	}
	public String getUserId()
	{
		return this.userId;
	}
	public void setUserId(String userid)
	{
		this.userId=userId;
	}
	

}
