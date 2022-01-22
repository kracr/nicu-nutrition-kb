package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ref_urineculture database table.
 * 
 */
@Entity
@Table(name="ref_urineculture")
@NamedQuery(name="RefUrineculture.findAll", query="SELECT r FROM RefUrineculture r")
public class RefUrineculture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String urinecultureid;

	private String description;

	private String urineculture;

	//bi-directional many-to-one association to SaSepsi
	/*@OneToMany(mappedBy="refUrineculture")
	private List<SaSepsi> saSepsis;*/

	public RefUrineculture() {
	}

	public String getUrinecultureid() {
		return this.urinecultureid;
	}

	public void setUrinecultureid(String urinecultureid) {
		this.urinecultureid = urinecultureid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrineculture() {
		return this.urineculture;
	}

	public void setUrineculture(String urineculture) {
		this.urineculture = urineculture;
	}

	/*public List<SaSepsi> getSaSepsis() {
		return this.saSepsis;
	}

	public void setSaSepsis(List<SaSepsi> saSepsis) {
		this.saSepsis = saSepsis;
	}*/

	/*public SaSepsi addSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().add(saSepsi);
		saSepsi.setRefUrineculture(this);

		return saSepsi;
	}

	public SaSepsi removeSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().remove(saSepsi);
		saSepsi.setRefUrineculture(null);

		return saSepsi;
	}
*/
}