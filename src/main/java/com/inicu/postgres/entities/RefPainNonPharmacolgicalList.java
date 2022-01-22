package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="ref_pain_nonpharmacological_list")
@NamedQuery(name="RefPainNonPharmacolgicalList.findAll", query="SELECT r FROM RefPainNonPharmacolgicalList r")
public class RefPainNonPharmacolgicalList {
	
	 	@Id
	    @Column(name="pain_nonpharmacological_id")
	    private String painNonpharmacologicalId;

	    @Column(name="measure_name")
	    private String measureName;

		public String getPainNonpharmacologicalId() {
			return painNonpharmacologicalId;
		}

		public void setPainNonpharmacologicalId(String painNonpharmacologicalId) {
			this.painNonpharmacologicalId = painNonpharmacologicalId;
		}

		public String getMeasureName() {
			return measureName;
		}

		public void setMeasureName(String measureName) {
			this.measureName = measureName;
		}

}
