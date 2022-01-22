package com.inicu.postgres.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ref_presentationsymptoms database table.
 * 
 */
@Entity
@Table(name="ref_presentationsymptoms")
@NamedQuery(name="RefPresentationsymptom.findAll", query="SELECT r FROM RefPresentationsymptom r")
public class RefPresentationsymptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String presentationsymid;

	private String description;

	private String presentationsymptoms;

	//bi-directional many-to-one association to SaSepsi
	/*@OneToMany(mappedBy="refPresentationsymptom")
	private List<SaSepsi> saSepsis;
*/
	public RefPresentationsymptom() {
	}

	public String getPresentationsymid() {
		return this.presentationsymid;
	}

	public void setPresentationsymid(String presentationsymid) {
		this.presentationsymid = presentationsymid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPresentationsymptoms() {
		return this.presentationsymptoms;
	}

	public void setPresentationsymptoms(String presentationsymptoms) {
		this.presentationsymptoms = presentationsymptoms;
	}

	/*public List<SaSepsi> getSaSepsis() {
		return this.saSepsis;
	}

	public void setSaSepsis(List<SaSepsi> saSepsis) {
		this.saSepsis = saSepsis;
	}*/

	/*public SaSepsi addSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().add(saSepsi);
		saSepsi.setRefPresentationsymptom(this);

		return saSepsi;
	}

	public SaSepsi removeSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().remove(saSepsi);
		saSepsi.setRefPresentationsymptom(null);

		return saSepsi;
	}
*/
}