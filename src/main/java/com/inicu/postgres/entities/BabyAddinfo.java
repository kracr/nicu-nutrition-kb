package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the baby_addinfo database table.
 * 
 */
@Entity
@Table(name="baby_addinfo")
@NamedQuery(name="BabyAddinfo.findAll", query="SELECT b FROM BabyAddinfo b")
public class BabyAddinfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long addinfoid;

	private Timestamp creationtime;

	@Column(name="doc_name")
	private String docName;

	@Column(name="doc_title")
	private String docTitle;

	private Timestamp modificationtime;
	
	@Column(columnDefinition="bool")
	private Boolean isactive;

	private String uhid;
	
	private String documentkey;
	
	public BabyAddinfo() {
	}

	public Long getAddinfoid() {
		return this.addinfoid;
	}

	public void setAddinfoid(Long addinfoid) {
		this.addinfoid = addinfoid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getDocName() {
		return this.docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocTitle() {
		return this.docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public Boolean getIsactive() {
		return isactive;
	}

	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getDocumentkey() {
		return documentkey;
	}

	public void setDocumentkey(String documentkey) {
		this.documentkey = documentkey;
	}

	
	

}