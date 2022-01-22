package com.inicu.models;

import java.sql.Timestamp;

public class TemplateJSON {
	private String templateid;
	private String templateheader;
	private String templatetype;
	private String templatebody;
	private String creationtime;
	private String modificationtime;
	boolean edit;
	String doctorId;
	
	public TemplateJSON() 
	{
		
	}
	public String gettemplateid()
	{
		return this.templateid;
	}
	public void settemplateid(String id)
	{
		this.templateid=id;
	}
	
	public String getTemplateBody()
	{
		return this.templatebody;
	}
	public void setTemplateBody(String templatebody)
	{
		this.templatebody=templatebody;
	}
	
	public String getTemplateHeader()
	{
		return this.templateheader;
	}
	public void setTemplateHeader(String templateheader)
	{
		this.templateheader=templateheader;
	}
	
	
	public String getTemplateType()
	{
		return this.templatetype;
	}
   public void setTemplateType(String templatetype)
   {
	   this.templatetype=templatetype;
   }
   
   public String getModificationtime() {
		return this.modificationtime;
	}
   public void setModificationtime(String modificationtime) {
		this.modificationtime = modificationtime;
	}
   
   public String getCreationtime() {
		return this.creationtime;
	}
   
   public void setCreationTime(String creationtime) {
		this.creationtime = creationtime;
	}
   public boolean isEdit()
   {
	   return edit;
   }
   
   public void setEdit(boolean edit)
   {
	   this.edit = edit;
   }
   
   public String getDoctorId()
   {
	   return doctorId;
   }
   
   public void setDoctorId(String doctorId)
   {
	   this.doctorId=doctorId;
   }
}
