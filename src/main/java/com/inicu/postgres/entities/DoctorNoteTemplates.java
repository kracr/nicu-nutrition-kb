package com.inicu.postgres.entities;
import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the doctor_note_templates database table.
 * 
 */
@Entity
@Table(name="doctor_note_templates")
@NamedQuery(name="DoctorNoteTemplates.findAll", query="SELECT d FROM DoctorNoteTemplates d")
public class DoctorNoteTemplates implements Serializable {
	private static final long serialVersionUID = 5L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long doctemplateid;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	@Column(name="template_body")
	private String templateBody;

	@Column(name="template_header")
	private String templateHeader;

	@Column(name="template_type")
	private String templateType;
	
	private String loggeduser;

	public DoctorNoteTemplates() {
	}

	public Long getDoctemplateid() {
		return this.doctemplateid;
	}

	public void setDoctemplateid(Long doctemplateid) {
		this.doctemplateid = doctemplateid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp string) {
		this.creationtime = string;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getTemplateBody() {
		return this.templateBody;
	}

	public void setTemplateBody(String templateBody) {
		this.templateBody = templateBody;
	}

	public String getTemplateHeader() {
		return this.templateHeader;
	}

	public void setTemplateHeader(String templateHeader) {
		this.templateHeader = templateHeader;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}
}