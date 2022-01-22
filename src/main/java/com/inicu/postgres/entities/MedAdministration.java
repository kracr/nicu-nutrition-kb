package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the med_administration database table.
 * 
 */
@Entity
@Table(name="med_administration")
@NamedQuery(name="MedAdministration.findAll", query="SELECT m FROM MedAdministration m")
public class MedAdministration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long medadminstrationid;

	@Column(name="baby_presid")
	private Long babyPresid;

	private Timestamp creationtime;

	private String givenby;
	private String loggeduser;

	private Timestamp giventime;
	
	private Timestamp scheduletime;

	private Timestamp modificationtime;

	public MedAdministration() {
	}

	public Long getMedadminstrationid() {
		return this.medadminstrationid;
	}

	public void setMedadminstrationid(Long medadminstrationid) {
		this.medadminstrationid = medadminstrationid;
	}

	public Long getBabyPresid() {
		return this.babyPresid;
	}

	public void setBabyPresid(Long babyPresid) {
		this.babyPresid = babyPresid;
	}

	public Timestamp getCreationtime() {
		return this.creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public String getGivenby() {
		return this.givenby;
	}

	public void setGivenby(String givenby) {
		this.givenby = givenby;
	}

	public Timestamp getGiventime() {
		return this.giventime;
	}

	public void setGiventime(Timestamp giventime) {
		this.giventime = giventime;
	}

	public Timestamp getModificationtime() {
		return this.modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}
	public String getLoggedUser() {
		return loggeduser;
	}

	public void setLoggedUser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getScheduletime() {
		return scheduletime;
	}

	public void setScheduletime(Timestamp scheduletime) {
		this.scheduletime = scheduletime;
	}
	
	
}