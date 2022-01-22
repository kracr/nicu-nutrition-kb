export class BloodProductObj {

  doctor_blood_products_id?: any;
  creationtime?: any;
  modificationtime?: any;
  uhid?: any;
  status?: any;
  assessment_time?: any;
  blood_product?: any;
  indication_hb?: any;
  indication_resp: string;
  apneic_spell: boolean;
  apnea_count?: any;
  platelet_count?: any;
  bleeding?: any;
  surgery: boolean;
  ptt_value?: any;
  aptt_value?: any;
  collection_date?: any;
  expiry_date?: any;
  bag_number?: any;
  blood_group?: any;
  bag_volume?: any;
  checked_by?: any;
  blood_volume_kg?: any;
  total_volume?: any;
  infusion_time?: any;
  infusion_rate?: any;
  venous_access: string;
  plan_test?: any;
  test_time?: any;
  test_time_type: string;
  vital_time?: any;
  vital_time_type: string;
  cause?: any;
  progress_notes?: any;
  loggeduser?: any;

  constructor() {
		this.apneic_spell = false;
		this.surgery = false;
		this.indication_resp = "";
		this.venous_access = "peripheral";
		this.test_time_type = "min";
		this.vital_time_type = "min";
  }
}
