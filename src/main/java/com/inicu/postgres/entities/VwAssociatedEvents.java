
package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

//@Entity
@Table(name="associate_assesment_final")
@NamedQuery(name="VwAssociatedEvents.findAll", query="SELECT v FROM VwAssociatedEvents v")
public class VwAssociatedEvents implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private String uhid;
	
	@Transient private String jaundice;
	
	@Transient private String rds;
	
	@Transient private String apnea;
	
	private String pphn;
	
 private String pneumothorax;

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getJaundice() {
		return jaundice;
	}

	public void setJaundice(String jaundice) {
		this.jaundice = jaundice;
	}

	public String getRds() {
		return rds;
	}

	public void setRds(String rds) {
		this.rds = rds;
	}

	public String getApnea() {
		return apnea;
	}

	public void setApnea(String apnea) {
		this.apnea = apnea;
	}

	public String getPphn() {
		return pphn;
	}

	public void setPphn(String pphn) {
		this.pphn = pphn;
	}

	public String getPneumothorax() {
		return pneumothorax;
	}

	public void setPneumothorax(String pneumothorax) {
		this.pneumothorax = pneumothorax;
	}
}
