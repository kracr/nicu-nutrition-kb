package com.inicu.postgres.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the ref_organisms database table.
 * 
 */
@Entity
@Table(name="ref_organisms")
@NamedQuery(name="RefOrganism.findAll", query="SELECT r FROM RefOrganism r")
public class RefOrganism implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String organismsid;

	private String description;

	private String organisms;

	//bi-directional many-to-one association to SaSepsi
	/*@OneToMany(mappedBy="refOrganism")
	private List<SaSepsi> saSepsis;
*/
	public RefOrganism() {
	}

	public String getOrganismsid() {
		return this.organismsid;
	}

	public void setOrganismsid(String organismsid) {
		this.organismsid = organismsid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganisms() {
		return this.organisms;
	}

	public void setOrganisms(String organisms) {
		this.organisms = organisms;
	}

	/*public List<SaSepsi> getSaSepsis() {
		return this.saSepsis;
	}

	public void setSaSepsis(List<SaSepsi> saSepsis) {
		this.saSepsis = saSepsis;
	}*/

	/*public SaSepsi addSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().add(saSepsi);
		saSepsi.setRefOrganism(this);

		return saSepsi;
	}

	public SaSepsi removeSaSepsi(SaSepsi saSepsi) {
		getSaSepsis().remove(saSepsi);
		saSepsi.setRefOrganism(null);

		return saSepsi;
	}
*/
}