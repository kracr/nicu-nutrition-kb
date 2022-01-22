package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ref_history database table.
 * 
 */
@Entity
@Table(name="ref_history")
@NamedQuery(name="RefHistory.findAll", query="SELECT r FROM RefHistory r")
public class RefHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String hisid;

	private String history;

	public RefHistory() {
	}

	public String getHisid() {
		return this.hisid;
	}

	public void setHisid(String hisid) {
		this.hisid = hisid;
	}

	public String getHistory() {
		return this.history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

}