
ALTER TABLE vwuserinfo OWNER TO postgres;

--
-- TOC entry 4148 (class 2604 OID 72602)
-- Name: addinfoid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY baby_addinfo ALTER COLUMN addinfoid SET DEFAULT nextval('baby_addinfo_addinfoid_seq'::regclass);


--
-- TOC entry 4169 (class 2604 OID 72603)
-- Name: id; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY brand ALTER COLUMN id SET DEFAULT nextval('brand_id_seq'::regclass);


--
-- TOC entry 4181 (class 2604 OID 72604)
-- Name: aminophyllineid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_aminophylline ALTER COLUMN aminophyllineid SET DEFAULT nextval('discharge_aminophylline_aminophyllineid_seq'::regclass);


--
-- TOC entry 4182 (class 2604 OID 72605)
-- Name: metabolicid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_aminophylline ALTER COLUMN metabolicid SET DEFAULT nextval('discharge_aminophylline_metabolicid_seq'::regclass);


--
-- TOC entry 4184 (class 2604 OID 72606)
-- Name: caffeineid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_caffeine ALTER COLUMN caffeineid SET DEFAULT nextval('discharge_caffeine_caffeineid_seq'::regclass);


--
-- TOC entry 4185 (class 2604 OID 72607)
-- Name: metabolicid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_caffeine ALTER COLUMN metabolicid SET DEFAULT nextval('discharge_caffeine_metabolicid_seq'::regclass);


--
-- TOC entry 4186 (class 2604 OID 72608)
-- Name: cpapid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_cpap ALTER COLUMN cpapid SET DEFAULT nextval('discharge_cpap_cpapid_seq'::regclass);


--
-- TOC entry 4187 (class 2604 OID 72609)
-- Name: metabolicid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_cpap ALTER COLUMN metabolicid SET DEFAULT nextval('discharge_cpap_metabolicid_seq'::regclass);


--
-- TOC entry 4196 (class 2604 OID 72610)
-- Name: phototherapyid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_phototherapy ALTER COLUMN phototherapyid SET DEFAULT nextval('discharge_phototherapy_phototherapyid_seq'::regclass);


--
-- TOC entry 4197 (class 2604 OID 72611)
-- Name: metabolicid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_phototherapy ALTER COLUMN metabolicid SET DEFAULT nextval('discharge_phototherapy_metabolicid_seq'::regclass);


--
-- TOC entry 4201 (class 2604 OID 72612)
-- Name: nicuventillationid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY discharge_ventcourse ALTER COLUMN nicuventillationid SET DEFAULT nextval('discharge_ventcourse_nicuventillationid_seq'::regclass);


--
-- TOC entry 4211 (class 2604 OID 72613)
-- Name: dueageid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY inicudueage ALTER COLUMN dueageid SET DEFAULT nextval('inicudueage_dueageid_seq'::regclass);


--
-- TOC entry 4249 (class 2604 OID 72614)
-- Name: oralfeedid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY oralfeed_detail ALTER COLUMN oralfeedid SET DEFAULT nextval('oralfeed_detail_oralfeedid_seq'::regclass);


--
-- TOC entry 4251 (class 2604 OID 72615)
-- Name: patientduervaccineid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY patient_due_vaccine_dtls ALTER COLUMN patientduervaccineid SET DEFAULT nextval('patient_due_vaccine_dtls_patientduervaccineid_seq'::regclass);


--
-- TOC entry 4259 (class 2604 OID 72616)
-- Name: ref_bellscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_bellscore ALTER COLUMN ref_bellscoreid SET DEFAULT nextval('ref_bellscore_ref_bellscoreid_seq'::regclass);


--
-- TOC entry 4260 (class 2604 OID 72617)
-- Name: ref_bindscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_bindscore ALTER COLUMN ref_bindscoreid SET DEFAULT nextval('ref_bindscore_ref_bindscoreid_seq'::regclass);


--
-- TOC entry 4261 (class 2604 OID 72618)
-- Name: id; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_fenton ALTER COLUMN id SET DEFAULT nextval('ref_fenton_id_seq'::regclass);


--
-- TOC entry 4262 (class 2604 OID 72619)
-- Name: ref_levenescoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_levenescore ALTER COLUMN ref_levenescoreid SET DEFAULT nextval('ref_levenescore_ref_levenescoreid_seq'::regclass);


--
-- TOC entry 4263 (class 2604 OID 72620)
-- Name: nutritionid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_nutritioncalculator ALTER COLUMN nutritionid SET DEFAULT nextval('ref_nutritioncalculator_nutritionid_seq'::regclass);


--
-- TOC entry 4264 (class 2604 OID 72621)
-- Name: ref_painscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_painscore ALTER COLUMN ref_painscoreid SET DEFAULT nextval('ref_painscore_ref_painscoreid_seq'::regclass);


--
-- TOC entry 4280 (class 2604 OID 72622)
-- Name: respchesttubeid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_resp_chesttube ALTER COLUMN respchesttubeid SET DEFAULT nextval('sa_resp_chesttube_respchesttubeid_seq'::regclass);


--
-- TOC entry 4284 (class 2604 OID 72623)
-- Name: apgarscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_apgar ALTER COLUMN apgarscoreid SET DEFAULT nextval('score_apgar_apgarscoreid_seq'::regclass);


--
-- TOC entry 4285 (class 2604 OID 72624)
-- Name: ballardscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_ballard ALTER COLUMN ballardscoreid SET DEFAULT nextval('score_ballard_ballardscoreid_seq'::regclass);


--
-- TOC entry 4286 (class 2604 OID 72625)
-- Name: bellstagescoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_bellstage ALTER COLUMN bellstagescoreid SET DEFAULT nextval('score_bellstage_bellstagescoreid_seq'::regclass);


--
-- TOC entry 4287 (class 2604 OID 72626)
-- Name: bindscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_bind ALTER COLUMN bindscoreid SET DEFAULT nextval('score_bind_bindscoreid_seq'::regclass);


--
-- TOC entry 4288 (class 2604 OID 72627)
-- Name: downesscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_downes ALTER COLUMN downesscoreid SET DEFAULT nextval('score_downes_downesscoreid_seq'::regclass);


--
-- TOC entry 4289 (class 2604 OID 72628)
-- Name: hiescoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_hie ALTER COLUMN hiescoreid SET DEFAULT nextval('score_hie_hiescoreid_seq'::regclass);


--
-- TOC entry 4290 (class 2604 OID 72629)
-- Name: ivhscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_ivh ALTER COLUMN ivhscoreid SET DEFAULT nextval('score_ivh_ivhscoreid_seq'::regclass);


--
-- TOC entry 4291 (class 2604 OID 72630)
-- Name: levenescoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_levene ALTER COLUMN levenescoreid SET DEFAULT nextval('score_levene_levenescoreid_seq'::regclass);


--
-- TOC entry 4292 (class 2604 OID 72631)
-- Name: painscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_pain ALTER COLUMN painscoreid SET DEFAULT nextval('score_pain_painscoreid_seq'::regclass);


--
-- TOC entry 4293 (class 2604 OID 72632)
-- Name: papilescoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_papile ALTER COLUMN papilescoreid SET DEFAULT nextval('score_papile_papilescoreid_seq'::regclass);


--
-- TOC entry 4294 (class 2604 OID 72633)
-- Name: sarnatscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_sarnat ALTER COLUMN sarnatscoreid SET DEFAULT nextval('score_sarnat_sarnatscoreid_seq'::regclass);


--
-- TOC entry 4295 (class 2604 OID 72634)
-- Name: sepsisscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_sepsis ALTER COLUMN sepsisscoreid SET DEFAULT nextval('score_sepsis_sepsisscoreid_seq'::regclass);


--
-- TOC entry 4296 (class 2604 OID 72635)
-- Name: silvermanscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_silverman ALTER COLUMN silvermanscoreid SET DEFAULT nextval('score_silverman_silvermanscoreid_seq'::regclass);


--
-- TOC entry 4297 (class 2604 OID 72636)
-- Name: thompsonscoreid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY score_thompson ALTER COLUMN thompsonscoreid SET DEFAULT nextval('score_thompson_thompsonscoreid_seq'::regclass);


--
-- TOC entry 4301 (class 2604 OID 72637)
-- Name: testresultid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY test_result_inicu ALTER COLUMN testresultid SET DEFAULT nextval('test_result_inicu_testresultid_seq'::regclass);


--
-- TOC entry 4304 (class 2604 OID 72638)
-- Name: vaccineid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY vaccine ALTER COLUMN vaccineid SET DEFAULT nextval('vaccine_vaccineid_seq'::regclass);


--
-- TOC entry 4306 (class 2604 OID 72639)
-- Name: vaccineinfoid; Type: DEFAULT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY vaccine_info ALTER COLUMN vaccineinfoid SET DEFAULT nextval('vaccine_info_vaccineinfoid_seq'::regclass);


--
-- TOC entry 4357 (class 2606 OID 72895)
-- Name: addinfo_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY baby_addinfo
    ADD CONSTRAINT addinfo_pk PRIMARY KEY (addinfoid);


--
-- TOC entry 4309 (class 2606 OID 72897)
-- Name: admission_notes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY admission_notes
    ADD CONSTRAINT admission_notes_pkey PRIMARY KEY (admission_notes_id);


--
-- TOC entry 4319 (class 2606 OID 72899)
-- Name: antenatal_history_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY antenatal_history_detail
    ADD CONSTRAINT antenatal_history_pkey PRIMARY KEY (antenatal_history_id);


--
-- TOC entry 4321 (class 2606 OID 72901)
-- Name: antenatal_steroid_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY antenatal_steroid_detail
    ADD CONSTRAINT antenatal_steroid_pkey PRIMARY KEY (antenatal_steroid_id);


--
-- TOC entry 4692 (class 2606 OID 72903)
-- Name: apgarscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_apgar
    ADD CONSTRAINT apgarscore_pk PRIMARY KEY (apgarscoreid);


--
-- TOC entry 4311 (class 2606 OID 72905)
-- Name: baby_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY baby_detail
    ADD CONSTRAINT baby_detail_pkey PRIMARY KEY (babydetailid);


--
-- TOC entry 4365 (class 2606 OID 72907)
-- Name: baby_prescription_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY baby_prescription
    ADD CONSTRAINT baby_prescription_pkey PRIMARY KEY (baby_presid);


--
-- TOC entry 4359 (class 2606 OID 72909)
-- Name: baby_visit_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY baby_visit
    ADD CONSTRAINT baby_visit_pkey PRIMARY KEY (visitid);


--
-- TOC entry 4361 (class 2606 OID 72911)
-- Name: babyfeed_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY babyfeed_detail
    ADD CONSTRAINT babyfeed_detail_pkey PRIMARY KEY (babyfeedid);


--
-- TOC entry 4367 (class 2606 OID 72913)
-- Name: babyfeedivmed_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY babyfeedivmed_detail
    ADD CONSTRAINT babyfeedivmed_detail_pkey PRIMARY KEY (babyfeedivmedid);


--
-- TOC entry 4369 (class 2606 OID 72915)
-- Name: babytpnfeed_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY babytpnfeed_detail
    ADD CONSTRAINT babytpnfeed_detail_pkey PRIMARY KEY (babytpnfeedid);


--
-- TOC entry 4694 (class 2606 OID 72917)
-- Name: ballard_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_ballard
    ADD CONSTRAINT ballard_pk PRIMARY KEY (ballardscoreid);


--
-- TOC entry 4371 (class 2606 OID 72919)
-- Name: bed_device_detail_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY bed_device_detail
    ADD CONSTRAINT bed_device_detail_pk PRIMARY KEY (beddeviceid);


--
-- TOC entry 4696 (class 2606 OID 72921)
-- Name: bellstagescore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_bellstage
    ADD CONSTRAINT bellstagescore_pk PRIMARY KEY (bellstagescoreid);


--
-- TOC entry 4698 (class 2606 OID 72923)
-- Name: bindscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_bind
    ADD CONSTRAINT bindscore_pk PRIMARY KEY (bindscoreid);


--
-- TOC entry 4375 (class 2606 OID 72925)
-- Name: birth_to_nicu_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY birth_to_nicu
    ADD CONSTRAINT birth_to_nicu_pkey PRIMARY KEY (birth_to_nicu_id);


--
-- TOC entry 4377 (class 2606 OID 72927)
-- Name: blood_products_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY blood_products
    ADD CONSTRAINT blood_products_pkey PRIMARY KEY (bloodproductsid);


--
-- TOC entry 4379 (class 2606 OID 72929)
-- Name: brand_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY brand
    ADD CONSTRAINT brand_pkey PRIMARY KEY (id);


--
-- TOC entry 4381 (class 2606 OID 72931)
-- Name: central_line_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY central_line
    ADD CONSTRAINT central_line_pkey PRIMARY KEY (central_line_id);


--
-- TOC entry 4383 (class 2606 OID 72933)
-- Name: certificate_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY certificate
    ADD CONSTRAINT certificate_pkey PRIMARY KEY (id);


--
-- TOC entry 4401 (class 2606 OID 72935)
-- Name: device_bloodgas_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_bloodgas_detail_detail
    ADD CONSTRAINT device_bloodgas_detail_pkey PRIMARY KEY (device_bloodgas_detail_id);


--
-- TOC entry 4373 (class 2606 OID 72937)
-- Name: device_detail_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_detail
    ADD CONSTRAINT device_detail_pk PRIMARY KEY (deviceid);


--
-- TOC entry 4385 (class 2606 OID 72939)
-- Name: device_monitor_detail_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_monitor_detail
    ADD CONSTRAINT device_monitor_detail_pk PRIMARY KEY (devicemoniterid);


--
-- TOC entry 4315 (class 2606 OID 72941)
-- Name: device_ventilator_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY device_ventilator_detail
    ADD CONSTRAINT device_ventilator_detail_pkey PRIMARY KEY (deviceventilatorid);


--
-- TOC entry 4449 (class 2606 OID 72943)
-- Name: devicemappingkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hl7_device_mapping
    ADD CONSTRAINT devicemappingkey PRIMARY KEY (devicemappingid);


--
-- TOC entry 4403 (class 2606 OID 72945)
-- Name: discharge_advice_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_advice_detail
    ADD CONSTRAINT discharge_advice_pkey PRIMARY KEY (discharge_advice_id);


--
-- TOC entry 4407 (class 2606 OID 72947)
-- Name: discharge_birthdetail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_birthdetail
    ADD CONSTRAINT discharge_birthdetail_pkey PRIMARY KEY (maternaldetailid);


--
-- TOC entry 4423 (class 2606 OID 72949)
-- Name: discharge_nicucourse_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_nicucourse
    ADD CONSTRAINT discharge_nicucourse_pkey PRIMARY KEY (courseinicuid);


--
-- TOC entry 4425 (class 2606 OID 72951)
-- Name: discharge_notes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_notes_detail
    ADD CONSTRAINT discharge_notes_pkey PRIMARY KEY (discharge_notes_id);


--
-- TOC entry 4427 (class 2606 OID 72953)
-- Name: discharge_outcome_id_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_outcome
    ADD CONSTRAINT discharge_outcome_id_pkey PRIMARY KEY (discharge_outcome_id);


--
-- TOC entry 4431 (class 2606 OID 72955)
-- Name: discharge_summary_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_summary
    ADD CONSTRAINT discharge_summary_pkey PRIMARY KEY (babydischargeid);


--
-- TOC entry 4405 (class 2606 OID 72957)
-- Name: dischargeaminophylline_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_aminophylline
    ADD CONSTRAINT dischargeaminophylline_pkey PRIMARY KEY (aminophyllineid);


--
-- TOC entry 4409 (class 2606 OID 72959)
-- Name: dischargecaffeine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_caffeine
    ADD CONSTRAINT dischargecaffeine_pkey PRIMARY KEY (caffeineid);


--
-- TOC entry 4411 (class 2606 OID 72961)
-- Name: dischargecpap_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_cpap
    ADD CONSTRAINT dischargecpap_pkey PRIMARY KEY (cpapid);


--
-- TOC entry 4415 (class 2606 OID 72963)
-- Name: dischargefeeding_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_feeding
    ADD CONSTRAINT dischargefeeding_pkey PRIMARY KEY (feedingid);


--
-- TOC entry 4417 (class 2606 OID 72965)
-- Name: dischargeinfection_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_infection
    ADD CONSTRAINT dischargeinfection_pkey PRIMARY KEY (infectioninicuid);


--
-- TOC entry 4419 (class 2606 OID 72967)
-- Name: dischargeinvestigation_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_investigation
    ADD CONSTRAINT dischargeinvestigation_pkey PRIMARY KEY (investigationid);


--
-- TOC entry 4421 (class 2606 OID 72969)
-- Name: dischargemetabolic_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_metabolic
    ADD CONSTRAINT dischargemetabolic_pkey PRIMARY KEY (jaundiceinicuid);


--
-- TOC entry 4413 (class 2606 OID 72971)
-- Name: dischargenotes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_doctornotes
    ADD CONSTRAINT dischargenotes_pkey PRIMARY KEY (dischargenotesid);


--
-- TOC entry 4439 (class 2606 OID 72973)
-- Name: dischargepatient_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY dischargepatient_detail
    ADD CONSTRAINT dischargepatient_pkey PRIMARY KEY (dischargepatientid);


--
-- TOC entry 4429 (class 2606 OID 72975)
-- Name: dischargephototherapy_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_phototherapy
    ADD CONSTRAINT dischargephototherapy_pkey PRIMARY KEY (phototherapyid);


--
-- TOC entry 4433 (class 2606 OID 72977)
-- Name: dischargetreatment_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_treatment
    ADD CONSTRAINT dischargetreatment_pkey PRIMARY KEY (treatmentid);


--
-- TOC entry 4435 (class 2606 OID 72979)
-- Name: dischargeventcourse_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_ventcourse
    ADD CONSTRAINT dischargeventcourse_pkey PRIMARY KEY (ventcourseid);


--
-- TOC entry 4437 (class 2606 OID 72981)
-- Name: dischargeventilation_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY discharge_ventilation
    ADD CONSTRAINT dischargeventilation_pkey PRIMARY KEY (nicuventillationid);


--
-- TOC entry 4363 (class 2606 OID 72983)
-- Name: doctor_notes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY doctor_notes
    ADD CONSTRAINT doctor_notes_pkey PRIMARY KEY (doctornoteid);


--
-- TOC entry 4441 (class 2606 OID 72985)
-- Name: doctor_template_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY doctor_note_templates
    ADD CONSTRAINT doctor_template_pkey PRIMARY KEY (doctemplateid);


--
-- TOC entry 4700 (class 2606 OID 72987)
-- Name: downesscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_downes
    ADD CONSTRAINT downesscore_pk PRIMARY KEY (downesscoreid);


--
-- TOC entry 4451 (class 2606 OID 72989)
-- Name: dueage_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY inicudueage
    ADD CONSTRAINT dueage_pkey PRIMARY KEY (dueageid);


--
-- TOC entry 4443 (class 2606 OID 72991)
-- Name: et_intubation_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY et_intubation
    ADD CONSTRAINT et_intubation_pkey PRIMARY KEY (et_intubation_id);


--
-- TOC entry 4445 (class 2606 OID 72993)
-- Name: exceptionlist_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY exceptionlist
    ADD CONSTRAINT exceptionlist_pkey PRIMARY KEY (exceptionid);


--
-- TOC entry 4447 (class 2606 OID 72995)
-- Name: gen_phy_exam_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY gen_phy_exam
    ADD CONSTRAINT gen_phy_exam_pkey PRIMARY KEY (gen_phy_exam_id);


--
-- TOC entry 4313 (class 2606 OID 72997)
-- Name: headtotoe_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY headtotoe_examination
    ADD CONSTRAINT headtotoe_pk PRIMARY KEY (headtoeid);


--
-- TOC entry 4702 (class 2606 OID 72999)
-- Name: hiescore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_hie
    ADD CONSTRAINT hiescore_pk PRIMARY KEY (hiescoreid);


--
-- TOC entry 4568 (class 2606 OID 73001)
-- Name: inicu_device_type_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_device_type
    ADD CONSTRAINT inicu_device_type_pkey PRIMARY KEY (devicetypeid);


--
-- TOC entry 4586 (class 2606 OID 73003)
-- Name: inicu_devices_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_inicu_devices
    ADD CONSTRAINT inicu_devices_pkey PRIMARY KEY (deviceid);


--
-- TOC entry 4454 (class 2606 OID 73005)
-- Name: iniculogs_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY iniculogs
    ADD CONSTRAINT iniculogs_pkey PRIMARY KEY (logid);


--
-- TOC entry 4458 (class 2606 OID 73007)
-- Name: investigationorder_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY investigation_ordered
    ADD CONSTRAINT investigationorder_pkey PRIMARY KEY (investigationorderid);


--
-- TOC entry 4642 (class 2606 OID 73009)
-- Name: itemid_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_testitemhelp
    ADD CONSTRAINT itemid_pkey PRIMARY KEY (itemid);


--
-- TOC entry 4704 (class 2606 OID 73011)
-- Name: ivhscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_ivh
    ADD CONSTRAINT ivhscore_pk PRIMARY KEY (ivhscoreid);


--
-- TOC entry 4706 (class 2606 OID 73013)
-- Name: levenescore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_levene
    ADD CONSTRAINT levenescore_pk PRIMARY KEY (levenescoreid);


--
-- TOC entry 4460 (class 2606 OID 73015)
-- Name: logindetails_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY logindetails
    ADD CONSTRAINT logindetails_pkey PRIMARY KEY (loginid);


--
-- TOC entry 4462 (class 2606 OID 73017)
-- Name: lumbar_puncture_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY lumbar_puncture
    ADD CONSTRAINT lumbar_puncture_pkey PRIMARY KEY (lumbar_puncture_id);


--
-- TOC entry 4656 (class 2606 OID 73019)
-- Name: manage_role_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY role_manager
    ADD CONSTRAINT manage_role_pk PRIMARY KEY (rolemanagerid);


--
-- TOC entry 4464 (class 2606 OID 73021)
-- Name: master_address_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY master_address
    ADD CONSTRAINT master_address_pkey PRIMARY KEY (master_address_id);


--
-- TOC entry 4468 (class 2606 OID 73023)
-- Name: med_administration_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY med_administration
    ADD CONSTRAINT med_administration_pkey PRIMARY KEY (medadminstrationid);


--
-- TOC entry 4470 (class 2606 OID 73025)
-- Name: medicine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY medicine
    ADD CONSTRAINT medicine_pkey PRIMARY KEY (medid);


--
-- TOC entry 4472 (class 2606 OID 73027)
-- Name: module_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY module
    ADD CONSTRAINT module_pk PRIMARY KEY (moduleid);


--
-- TOC entry 4474 (class 2606 OID 73029)
-- Name: mother_current_pregnancy_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mother_current_pregnancy
    ADD CONSTRAINT mother_current_pregnancy_pkey PRIMARY KEY (currentpregnancyid);


--
-- TOC entry 4397 (class 2606 OID 73031)
-- Name: notifications_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notifications
    ADD CONSTRAINT notifications_pkey PRIMARY KEY (notificationid);


--
-- TOC entry 4484 (class 2606 OID 73033)
-- Name: nursing_assesment_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_dailyassesment
    ADD CONSTRAINT nursing_assesment_pkey PRIMARY KEY (nn_assesmentid);


--
-- TOC entry 4476 (class 2606 OID 73035)
-- Name: nursing_bloodgas_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_bloodgas
    ADD CONSTRAINT nursing_bloodgas_pkey PRIMARY KEY (nn_bloodgasid);


--
-- TOC entry 4478 (class 2606 OID 73037)
-- Name: nursing_bloodproducts_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_bloodproducts
    ADD CONSTRAINT nursing_bloodproducts_pkey PRIMARY KEY (nn_bloodproductid);


--
-- TOC entry 4480 (class 2606 OID 73039)
-- Name: nursing_bolus_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_bolus
    ADD CONSTRAINT nursing_bolus_pkey PRIMARY KEY (nn_bolusid);


--
-- TOC entry 4482 (class 2606 OID 73041)
-- Name: nursing_catheters_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_catheters
    ADD CONSTRAINT nursing_catheters_pkey PRIMARY KEY (nn_cathetersid);


--
-- TOC entry 4500 (class 2606 OID 73043)
-- Name: nursing_drain_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_outputdrain
    ADD CONSTRAINT nursing_drain_pkey PRIMARY KEY (nn_drainid);


--
-- TOC entry 4486 (class 2606 OID 73045)
-- Name: nursing_episode_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_episode
    ADD CONSTRAINT nursing_episode_pkey PRIMARY KEY (episodeid);


--
-- TOC entry 4488 (class 2606 OID 73047)
-- Name: nursing_intake_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_intake
    ADD CONSTRAINT nursing_intake_pkey PRIMARY KEY (nn_intakeid);


--
-- TOC entry 4490 (class 2606 OID 73049)
-- Name: nursing_intakeoutput_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_intake_output
    ADD CONSTRAINT nursing_intakeoutput_pkey PRIMARY KEY (nursing_intakeid);


--
-- TOC entry 4492 (class 2606 OID 73051)
-- Name: nursing_misc_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_misc
    ADD CONSTRAINT nursing_misc_pkey PRIMARY KEY (nn_miscid);


--
-- TOC entry 4494 (class 2606 OID 73053)
-- Name: nursing_neurovitals_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_neurovitals
    ADD CONSTRAINT nursing_neurovitals_pkey PRIMARY KEY (nn_neorovitalsid);


--
-- TOC entry 4496 (class 2606 OID 73055)
-- Name: nursing_notes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_notes
    ADD CONSTRAINT nursing_notes_pkey PRIMARY KEY (nursingnoteid);


--
-- TOC entry 4498 (class 2606 OID 73057)
-- Name: nursing_output_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_output
    ADD CONSTRAINT nursing_output_pkey PRIMARY KEY (nn_outputid);


--
-- TOC entry 4502 (class 2606 OID 73059)
-- Name: nursing_ventilaor_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_ventilaor
    ADD CONSTRAINT nursing_ventilaor_pkey PRIMARY KEY (nn_ventilaorid);


--
-- TOC entry 4504 (class 2606 OID 73061)
-- Name: nursing_vitalparameters_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursing_vitalparameters
    ADD CONSTRAINT nursing_vitalparameters_pkey PRIMARY KEY (nn_vitalparameterid);


--
-- TOC entry 4512 (class 2606 OID 73063)
-- Name: nursingorder_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursingorder_rds
    ADD CONSTRAINT nursingorder_pkey PRIMARY KEY (nursingorderrdsid);


--
-- TOC entry 4514 (class 2606 OID 73065)
-- Name: nursingorder_rds_medicine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursingorder_rds_medicine
    ADD CONSTRAINT nursingorder_rds_medicine_pkey PRIMARY KEY (nursingorderrds_medicineid);


--
-- TOC entry 4506 (class 2606 OID 73067)
-- Name: nursingorderapnea_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursingorder_apnea
    ADD CONSTRAINT nursingorderapnea_pkey PRIMARY KEY (nursingorderapneaid);


--
-- TOC entry 4508 (class 2606 OID 73069)
-- Name: nursingorderassesment_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursingorder_assesment
    ADD CONSTRAINT nursingorderassesment_pkey PRIMARY KEY (nursingorderid);


--
-- TOC entry 4612 (class 2606 OID 73071)
-- Name: nutritioncalculator_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_nutritioncalculator
    ADD CONSTRAINT nutritioncalculator_pkey PRIMARY KEY (nutritionid);


--
-- TOC entry 4510 (class 2606 OID 73073)
-- Name: nvestigationorder_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nursingorder_jaundice
    ADD CONSTRAINT nvestigationorder_pkey PRIMARY KEY (nursingorderjaundiceid);


--
-- TOC entry 4516 (class 2606 OID 73075)
-- Name: oralfeed_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY oralfeed_detail
    ADD CONSTRAINT oralfeed_detail_pkey PRIMARY KEY (oralfeedid);


--
-- TOC entry 4518 (class 2606 OID 73077)
-- Name: outborn_medicine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY outborn_medicine
    ADD CONSTRAINT outborn_medicine_pkey PRIMARY KEY (outborn_medicine_id);


--
-- TOC entry 4708 (class 2606 OID 73079)
-- Name: painscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_pain
    ADD CONSTRAINT painscore_pk PRIMARY KEY (painscoreid);


--
-- TOC entry 4710 (class 2606 OID 73081)
-- Name: papilescore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_papile
    ADD CONSTRAINT papilescore_pk PRIMARY KEY (papilescoreid);


--
-- TOC entry 4387 (class 2606 OID 73083)
-- Name: parent_detail_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parent_detail
    ADD CONSTRAINT parent_detail_pkey PRIMARY KEY (parentdetailid);


--
-- TOC entry 4466 (class 2606 OID 73085)
-- Name: pasthistory_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY maternal_pasthistory
    ADD CONSTRAINT pasthistory_pk PRIMARY KEY (pasthistoryid);


--
-- TOC entry 4520 (class 2606 OID 73087)
-- Name: patient_due_vaccine_dtls_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY patient_due_vaccine_dtls
    ADD CONSTRAINT patient_due_vaccine_dtls_pkey PRIMARY KEY (patientduervaccineid);


--
-- TOC entry 4522 (class 2606 OID 73089)
-- Name: peripheral_cannula_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY peripheral_cannula
    ADD CONSTRAINT peripheral_cannula_pkey PRIMARY KEY (peripheral_cannula_id);


--
-- TOC entry 4574 (class 2606 OID 73091)
-- Name: pk_ref_fenton; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_fenton
    ADD CONSTRAINT pk_ref_fenton PRIMARY KEY (id);


--
-- TOC entry 4524 (class 2606 OID 73093)
-- Name: preferencekey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY preference
    ADD CONSTRAINT preferencekey PRIMARY KEY (id);


--
-- TOC entry 4526 (class 2606 OID 73095)
-- Name: preferenceunique; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY preference
    ADD CONSTRAINT preferenceunique UNIQUE (preference);


--
-- TOC entry 4528 (class 2606 OID 73097)
-- Name: procedure_chesttube_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY procedure_chesttube
    ADD CONSTRAINT procedure_chesttube_pkey PRIMARY KEY (id);


--
-- TOC entry 4530 (class 2606 OID 73099)
-- Name: procedure_other_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY procedure_other
    ADD CONSTRAINT procedure_other_pkey PRIMARY KEY (procedure_other_id);


--
-- TOC entry 4532 (class 2606 OID 73101)
-- Name: pupil_reactivity_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pupil_reactivity
    ADD CONSTRAINT pupil_reactivity_pkey PRIMARY KEY (pupilreactivityid);


--
-- TOC entry 4534 (class 2606 OID 73103)
-- Name: readmit_history_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY readmit_history
    ADD CONSTRAINT readmit_history_pkey PRIMARY KEY (readmitid);


--
-- TOC entry 4536 (class 2606 OID 73105)
-- Name: reason_admission_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY reason_admission
    ADD CONSTRAINT reason_admission_pkey PRIMARY KEY (reason_admission_id);


--
-- TOC entry 4538 (class 2606 OID 73107)
-- Name: ref_antibiotics_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_antibiotics
    ADD CONSTRAINT ref_antibiotics_pkey PRIMARY KEY (antibioticsid);


--
-- TOC entry 4540 (class 2606 OID 73109)
-- Name: ref_apneacause_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_apneacause
    ADD CONSTRAINT ref_apneacause_pkey PRIMARY KEY (apneacauseid);


--
-- TOC entry 4542 (class 2606 OID 73111)
-- Name: ref_assesmenttreatment_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_assesment_treatment
    ADD CONSTRAINT ref_assesmenttreatment_pkey PRIMARY KEY (assesmenttreatmentid);


--
-- TOC entry 4544 (class 2606 OID 73113)
-- Name: ref_bellscore_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_bellscore
    ADD CONSTRAINT ref_bellscore_pkey PRIMARY KEY (ref_bellscoreid);


--
-- TOC entry 4546 (class 2606 OID 73115)
-- Name: ref_bindscore_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_bindscore
    ADD CONSTRAINT ref_bindscore_pkey PRIMARY KEY (ref_bindscoreid);


--
-- TOC entry 4550 (class 2606 OID 73117)
-- Name: ref_cathetersline_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_cathetersline
    ADD CONSTRAINT ref_cathetersline_pkey PRIMARY KEY (catheterslineid);


--
-- TOC entry 4552 (class 2606 OID 73119)
-- Name: ref_causeofcns_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_causeofcns
    ADD CONSTRAINT ref_causeofcns_pkey PRIMARY KEY (causeofcnsid);


--
-- TOC entry 4554 (class 2606 OID 73121)
-- Name: ref_causeofinfection_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_causeofinfection
    ADD CONSTRAINT ref_causeofinfection_pkey PRIMARY KEY (causeofinfectionid);


--
-- TOC entry 4556 (class 2606 OID 73123)
-- Name: ref_causeofjaundice_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_causeofjaundice
    ADD CONSTRAINT ref_causeofjaundice_pkey PRIMARY KEY (causeofjaundiceid);


--
-- TOC entry 4558 (class 2606 OID 73125)
-- Name: ref_causeofmetabolic_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_causeofmetabolic
    ADD CONSTRAINT ref_causeofmetabolic_pkey PRIMARY KEY (causeofmetabolicid);


--
-- TOC entry 4560 (class 2606 OID 73127)
-- Name: ref_causeofresp_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_causeofrespiratory
    ADD CONSTRAINT ref_causeofresp_pkey PRIMARY KEY (causeofrespid);


--
-- TOC entry 4562 (class 2606 OID 73129)
-- Name: ref_chd_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_chd
    ADD CONSTRAINT ref_chd_pkey PRIMARY KEY (chdid);


--
-- TOC entry 4564 (class 2606 OID 73131)
-- Name: ref_cldstage_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_cldstage
    ADD CONSTRAINT ref_cldstage_pkey PRIMARY KEY (cldstageid);


--
-- TOC entry 4566 (class 2606 OID 73133)
-- Name: ref_csfculture_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_csfculture
    ADD CONSTRAINT ref_csfculture_pkey PRIMARY KEY (csfcultureid);


--
-- TOC entry 4570 (class 2606 OID 73135)
-- Name: ref_ettsecsize_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_ettsecsize
    ADD CONSTRAINT ref_ettsecsize_pkey PRIMARY KEY (sizeid);


--
-- TOC entry 4576 (class 2606 OID 73137)
-- Name: ref_gestation_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_gestation
    ADD CONSTRAINT ref_gestation_pk PRIMARY KEY (gesid, gestation);


--
-- TOC entry 4584 (class 2606 OID 73139)
-- Name: ref_inicu_bbox_boards_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_inicu_bbox_boards
    ADD CONSTRAINT ref_inicu_bbox_boards_pkey PRIMARY KEY (board_id);


--
-- TOC entry 4582 (class 2606 OID 73141)
-- Name: ref_inicu_bbox_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_inicu_bbox
    ADD CONSTRAINT ref_inicu_bbox_pkey PRIMARY KEY (bbox_id);


--
-- TOC entry 4588 (class 2606 OID 73143)
-- Name: ref_inotropes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_inotropes
    ADD CONSTRAINT ref_inotropes_pkey PRIMARY KEY (inotropeid);


--
-- TOC entry 4590 (class 2606 OID 73145)
-- Name: ref_jaundiceriskfactor_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_jaundiceriskfactor
    ADD CONSTRAINT ref_jaundiceriskfactor_pkey PRIMARY KEY (riskfactorid);


--
-- TOC entry 4592 (class 2606 OID 73147)
-- Name: ref_levenescore_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_levenescore
    ADD CONSTRAINT ref_levenescore_pkey PRIMARY KEY (ref_levenescoreid);


--
-- TOC entry 4594 (class 2606 OID 73149)
-- Name: ref_masteraspirate_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_masteraspirate
    ADD CONSTRAINT ref_masteraspirate_pkey PRIMARY KEY (aspirateid);


--
-- TOC entry 4602 (class 2606 OID 73151)
-- Name: ref_medfrequency_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_medfrequency
    ADD CONSTRAINT ref_medfrequency_pkey PRIMARY KEY (freqid);


--
-- TOC entry 4606 (class 2606 OID 73153)
-- Name: ref_medtype_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_medtype
    ADD CONSTRAINT ref_medtype_pkey PRIMARY KEY (typeid);


--
-- TOC entry 4608 (class 2606 OID 73155)
-- Name: ref_metabolic_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_metabolic_treatment
    ADD CONSTRAINT ref_metabolic_pkey PRIMARY KEY (treatmentid);


--
-- TOC entry 4610 (class 2606 OID 73157)
-- Name: ref_metabolicsymptoms_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_metabolicsymptoms
    ADD CONSTRAINT ref_metabolicsymptoms_pkey PRIMARY KEY (metabolicsymid);


--
-- TOC entry 4614 (class 2606 OID 73159)
-- Name: ref_orderinvestigation_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_orderinvestigation
    ADD CONSTRAINT ref_orderinvestigation_pkey PRIMARY KEY (orderinvestigationid);


--
-- TOC entry 4616 (class 2606 OID 73161)
-- Name: ref_organisms_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_organisms
    ADD CONSTRAINT ref_organisms_pkey PRIMARY KEY (organismsid);


--
-- TOC entry 4618 (class 2606 OID 73163)
-- Name: ref_painscore_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_painscore
    ADD CONSTRAINT ref_painscore_pkey PRIMARY KEY (ref_painscoreid);


--
-- TOC entry 4620 (class 2606 OID 73165)
-- Name: ref_presentationeos_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_presentationeos
    ADD CONSTRAINT ref_presentationeos_pkey PRIMARY KEY (presentationeosid);


--
-- TOC entry 4622 (class 2606 OID 73167)
-- Name: ref_presentationsymptoms_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_presentationsymptoms
    ADD CONSTRAINT ref_presentationsymptoms_pkey PRIMARY KEY (presentationsymid);


--
-- TOC entry 4624 (class 2606 OID 73169)
-- Name: ref_pupilreactivity_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_pupilreactivity
    ADD CONSTRAINT ref_pupilreactivity_pkey PRIMARY KEY (reactivityid);


--
-- TOC entry 4626 (class 2606 OID 73171)
-- Name: ref_rdscause_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_rdscause
    ADD CONSTRAINT ref_rdscause_pkey PRIMARY KEY (rdscauseid);


--
-- TOC entry 4628 (class 2606 OID 73173)
-- Name: ref_rdsriskfactor_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_rdsriskfactor
    ADD CONSTRAINT ref_rdsriskfactor_pkey PRIMARY KEY (riskfactorid);


--
-- TOC entry 4630 (class 2606 OID 73175)
-- Name: ref_reasonofmv_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_reasonofmv
    ADD CONSTRAINT ref_reasonofmv_pkey PRIMARY KEY (mvreasonid);


--
-- TOC entry 4632 (class 2606 OID 73177)
-- Name: ref_ropstage_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_ropstage
    ADD CONSTRAINT ref_ropstage_pkey PRIMARY KEY (ropstageid);


--
-- TOC entry 4634 (class 2606 OID 73179)
-- Name: ref_seizures_cause_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_seizures_cause
    ADD CONSTRAINT ref_seizures_cause_pkey PRIMARY KEY (seizurescauseid);


--
-- TOC entry 4636 (class 2606 OID 73181)
-- Name: ref_seizures_med_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_seizures_medication
    ADD CONSTRAINT ref_seizures_med_pkey PRIMARY KEY (seizuresmediid);


--
-- TOC entry 4638 (class 2606 OID 73183)
-- Name: ref_seizures_type_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_seizures_type
    ADD CONSTRAINT ref_seizures_type_pkey PRIMARY KEY (seizurestypeid);


--
-- TOC entry 4646 (class 2606 OID 73185)
-- Name: ref_unit_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_unit
    ADD CONSTRAINT ref_unit_pkey PRIMARY KEY (unitid);


--
-- TOC entry 4648 (class 2606 OID 73187)
-- Name: ref_urineculture_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_urineculture
    ADD CONSTRAINT ref_urineculture_pkey PRIMARY KEY (urinecultureid);


--
-- TOC entry 4650 (class 2606 OID 73189)
-- Name: ref_ventmode_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_ventilationmode
    ADD CONSTRAINT ref_ventmode_pkey PRIMARY KEY (ventmodeid);


--
-- TOC entry 4389 (class 2606 OID 73191)
-- Name: refbed_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_bed
    ADD CONSTRAINT refbed_pkey PRIMARY KEY (bedid);


--
-- TOC entry 4548 (class 2606 OID 73193)
-- Name: refbp_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_blood_product
    ADD CONSTRAINT refbp_pkey PRIMARY KEY (bpid);


--
-- TOC entry 4391 (class 2606 OID 73195)
-- Name: refcrlevel_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_criticallevel
    ADD CONSTRAINT refcrlevel_pkey PRIMARY KEY (crlevelid);


--
-- TOC entry 4572 (class 2606 OID 73197)
-- Name: refexamination_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_examination
    ADD CONSTRAINT refexamination_pkey PRIMARY KEY (exid);


--
-- TOC entry 4598 (class 2606 OID 73199)
-- Name: reffeedmethod_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_masterfeedmethod
    ADD CONSTRAINT reffeedmethod_pkey PRIMARY KEY (feedmethodid);


--
-- TOC entry 4600 (class 2606 OID 73201)
-- Name: reffeedtype_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_masterfeedtype
    ADD CONSTRAINT reffeedtype_pkey PRIMARY KEY (feedtypeid);


--
-- TOC entry 4578 (class 2606 OID 73203)
-- Name: refheadtotoe_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_headtotoe
    ADD CONSTRAINT refheadtotoe_pkey PRIMARY KEY (htid);


--
-- TOC entry 4580 (class 2606 OID 73205)
-- Name: refhistory_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_history
    ADD CONSTRAINT refhistory_pkey PRIMARY KEY (hisid);


--
-- TOC entry 4393 (class 2606 OID 73207)
-- Name: reflevel_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_level
    ADD CONSTRAINT reflevel_pkey PRIMARY KEY (levelid);


--
-- TOC entry 4596 (class 2606 OID 73209)
-- Name: refmasterbo_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_masterbo
    ADD CONSTRAINT refmasterbo_pkey PRIMARY KEY (masterboid);


--
-- TOC entry 4604 (class 2606 OID 73211)
-- Name: refmedicine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_medicine
    ADD CONSTRAINT refmedicine_pkey PRIMARY KEY (medid);


--
-- TOC entry 4395 (class 2606 OID 73213)
-- Name: refroom_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_room
    ADD CONSTRAINT refroom_pkey PRIMARY KEY (roomid);


--
-- TOC entry 4640 (class 2606 OID 73215)
-- Name: refsign_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_significantmaterial
    ADD CONSTRAINT refsign_pkey PRIMARY KEY (smid);


--
-- TOC entry 4652 (class 2606 OID 73217)
-- Name: respsupport_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY respsupport
    ADD CONSTRAINT respsupport_pkey PRIMARY KEY (respsupportid);


--
-- TOC entry 4654 (class 2606 OID 73219)
-- Name: role_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (roleid);


--
-- TOC entry 4658 (class 2606 OID 73221)
-- Name: role_status_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY role_status
    ADD CONSTRAINT role_status_pk PRIMARY KEY (statusid);


--
-- TOC entry 4323 (class 2606 OID 73223)
-- Name: sa_acidosis_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_acidosis
    ADD CONSTRAINT sa_acidosis_pkey PRIMARY KEY (acidosisid);


--
-- TOC entry 4660 (class 2606 OID 73225)
-- Name: sa_cardiac_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_cardiac
    ADD CONSTRAINT sa_cardiac_pkey PRIMARY KEY (cardiacid);


--
-- TOC entry 4662 (class 2606 OID 73227)
-- Name: sa_cns_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_cns
    ADD CONSTRAINT sa_cns_pkey PRIMARY KEY (cnspid);


--
-- TOC entry 4664 (class 2606 OID 73229)
-- Name: sa_feedgrowth_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_feedgrowth
    ADD CONSTRAINT sa_feedgrowth_pkey PRIMARY KEY (feedgrowthid);


--
-- TOC entry 4666 (class 2606 OID 73231)
-- Name: sa_followup_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_followup
    ADD CONSTRAINT sa_followup_pkey PRIMARY KEY (followupid);


--
-- TOC entry 4331 (class 2606 OID 73233)
-- Name: sa_hypercalcemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hypercalcemia
    ADD CONSTRAINT sa_hypercalcemia_pkey PRIMARY KEY (hypercalcemiaid);


--
-- TOC entry 4333 (class 2606 OID 73235)
-- Name: sa_hyperglycemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hyperglycemia
    ADD CONSTRAINT sa_hyperglycemia_pkey PRIMARY KEY (hyperglycemiaid);


--
-- TOC entry 4335 (class 2606 OID 73237)
-- Name: sa_hyperkalemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hyperkalemia
    ADD CONSTRAINT sa_hyperkalemia_pkey PRIMARY KEY (hyperkalemiaid);


--
-- TOC entry 4337 (class 2606 OID 73239)
-- Name: sa_hypernatremia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hypernatremia
    ADD CONSTRAINT sa_hypernatremia_pkey PRIMARY KEY (hypernatremiaid);


--
-- TOC entry 4339 (class 2606 OID 73241)
-- Name: sa_hypocalcemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hypocalcemia
    ADD CONSTRAINT sa_hypocalcemia_pkey PRIMARY KEY (hypocalcemiaid);


--
-- TOC entry 4341 (class 2606 OID 73243)
-- Name: sa_hypoglycemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hypoglycemia
    ADD CONSTRAINT sa_hypoglycemia_pkey PRIMARY KEY (hypoglycemiaid);


--
-- TOC entry 4668 (class 2606 OID 73245)
-- Name: sa_hypokalemia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hypokalemia
    ADD CONSTRAINT sa_hypokalemia_pkey PRIMARY KEY (hypokalemiaid);


--
-- TOC entry 4343 (class 2606 OID 73247)
-- Name: sa_hyponatremia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_hyponatremia
    ADD CONSTRAINT sa_hyponatremia_pkey PRIMARY KEY (hyponatremiaid);


--
-- TOC entry 4345 (class 2606 OID 73249)
-- Name: sa_iem_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_iem
    ADD CONSTRAINT sa_iem_pkey PRIMARY KEY (iemid);


--
-- TOC entry 4347 (class 2606 OID 73251)
-- Name: sa_jaundice_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_jaundice
    ADD CONSTRAINT sa_jaundice_pkey PRIMARY KEY (sajaundiceid);


--
-- TOC entry 4676 (class 2606 OID 73253)
-- Name: sa_metabolic_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_metabolic
    ADD CONSTRAINT sa_metabolic_pkey PRIMARY KEY (sametabolicid);


--
-- TOC entry 4678 (class 2606 OID 73255)
-- Name: sa_misc_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_misc
    ADD CONSTRAINT sa_misc_pkey PRIMARY KEY (miscid);


--
-- TOC entry 4680 (class 2606 OID 73257)
-- Name: sa_renal_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_renalfailure
    ADD CONSTRAINT sa_renal_pkey PRIMARY KEY (renalid);


--
-- TOC entry 4349 (class 2606 OID 73259)
-- Name: sa_resp_apnea_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_apnea
    ADD CONSTRAINT sa_resp_apnea_pkey PRIMARY KEY (apneaid);


--
-- TOC entry 4682 (class 2606 OID 73261)
-- Name: sa_resp_bpd_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_bpd
    ADD CONSTRAINT sa_resp_bpd_pkey PRIMARY KEY (respbpdid);


--
-- TOC entry 4686 (class 2606 OID 73263)
-- Name: sa_resp_others_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_others
    ADD CONSTRAINT sa_resp_others_pkey PRIMARY KEY (respotherid);


--
-- TOC entry 4684 (class 2606 OID 73265)
-- Name: sa_respchesttube_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_chesttube
    ADD CONSTRAINT sa_respchesttube_pkey PRIMARY KEY (respchesttubeid);


--
-- TOC entry 4351 (class 2606 OID 73267)
-- Name: sa_resppneumothorax_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_pneumothorax
    ADD CONSTRAINT sa_resppneumothorax_pkey PRIMARY KEY (resppneumothoraxid);


--
-- TOC entry 4353 (class 2606 OID 73269)
-- Name: sa_resppphn_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_pphn
    ADD CONSTRAINT sa_resppphn_pkey PRIMARY KEY (resppphnid);


--
-- TOC entry 4355 (class 2606 OID 73271)
-- Name: sa_resprds_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_resp_rds
    ADD CONSTRAINT sa_resprds_pkey PRIMARY KEY (resprdsid);


--
-- TOC entry 4688 (class 2606 OID 73273)
-- Name: sa_respsystem_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_respsystem
    ADD CONSTRAINT sa_respsystem_pkey PRIMARY KEY (respid);


--
-- TOC entry 4670 (class 2606 OID 73275)
-- Name: sa_saclabsi_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_infection_clabsi
    ADD CONSTRAINT sa_saclabsi_pkey PRIMARY KEY (saclabsiid);


--
-- TOC entry 4672 (class 2606 OID 73277)
-- Name: sa_sasepsis_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_infection_sepsis
    ADD CONSTRAINT sa_sasepsis_pkey PRIMARY KEY (sasepsisid);


--
-- TOC entry 4674 (class 2606 OID 73279)
-- Name: sa_savap_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_infection_vap
    ADD CONSTRAINT sa_savap_pkey PRIMARY KEY (savapid);


--
-- TOC entry 4690 (class 2606 OID 73281)
-- Name: sa_sepsis_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_sepsis
    ADD CONSTRAINT sa_sepsis_pkey PRIMARY KEY (sepsisid);


--
-- TOC entry 4325 (class 2606 OID 73283)
-- Name: sacnsasphyxia_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_cns_asphyxia
    ADD CONSTRAINT sacnsasphyxia_pkey PRIMARY KEY (sacnsasphyxiaid);


--
-- TOC entry 4327 (class 2606 OID 73285)
-- Name: sacnsivh_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_cns_ivh
    ADD CONSTRAINT sacnsivh_pkey PRIMARY KEY (sacnsivhid);


--
-- TOC entry 4329 (class 2606 OID 73287)
-- Name: sacnsseizures_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sa_cns_seizures
    ADD CONSTRAINT sacnsseizures_pkey PRIMARY KEY (sacnsseizuresid);


--
-- TOC entry 4712 (class 2606 OID 73289)
-- Name: sarnatscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_sarnat
    ADD CONSTRAINT sarnatscore_pk PRIMARY KEY (sarnatscoreid);


--
-- TOC entry 4714 (class 2606 OID 73291)
-- Name: sepsisscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_sepsis
    ADD CONSTRAINT sepsisscore_pk PRIMARY KEY (sepsisscoreid);


--
-- TOC entry 4720 (class 2606 OID 73293)
-- Name: setting_reference_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY setting_reference
    ADD CONSTRAINT setting_reference_pkey PRIMARY KEY (settingid);


--
-- TOC entry 4716 (class 2606 OID 73295)
-- Name: silvermanscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_silverman
    ADD CONSTRAINT silvermanscore_pk PRIMARY KEY (silvermanscoreid);


--
-- TOC entry 4722 (class 2606 OID 73297)
-- Name: stable_notes_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY stable_notes
    ADD CONSTRAINT stable_notes_pkey PRIMARY KEY (id);


--
-- TOC entry 4724 (class 2606 OID 73299)
-- Name: systemic_exam_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY systemic_exam
    ADD CONSTRAINT systemic_exam_pkey PRIMARY KEY (systemic_exam_id);


--
-- TOC entry 4644 (class 2606 OID 73301)
-- Name: testid_primarykey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY ref_testslist
    ADD CONSTRAINT testid_primarykey PRIMARY KEY (testid);


--
-- TOC entry 4317 (class 2606 OID 73303)
-- Name: testresult_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY test_result
    ADD CONSTRAINT testresult_pk PRIMARY KEY (id);


--
-- TOC entry 4726 (class 2606 OID 73305)
-- Name: testresultid_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY test_result_inicu
    ADD CONSTRAINT testresultid_pk PRIMARY KEY (testresultid);


--
-- TOC entry 4718 (class 2606 OID 73307)
-- Name: thompsonscore_pk; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY score_thompson
    ADD CONSTRAINT thompsonscore_pk PRIMARY KEY (thompsonscoreid);


--
-- TOC entry 4399 (class 2606 OID 73309)
-- Name: uidnotification_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY uhidnotification
    ADD CONSTRAINT uidnotification_pkey PRIMARY KEY (uhidnotificationid);


--
-- TOC entry 4456 (class 2606 OID 73311)
-- Name: user_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY inicuuser
    ADD CONSTRAINT user_pkey PRIMARY KEY (username);


--
-- TOC entry 4728 (class 2606 OID 73313)
-- Name: users_roles_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users_roles
    ADD CONSTRAINT users_roles_pkey PRIMARY KEY (userroleid);


--
-- TOC entry 4730 (class 2606 OID 73315)
-- Name: users_usergroups_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY users_usergroups
    ADD CONSTRAINT users_usergroups_pkey PRIMARY KEY (userid, usergroupid);


--
-- TOC entry 4736 (class 2606 OID 73317)
-- Name: vaccine_info_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vaccine_info
    ADD CONSTRAINT vaccine_info_pkey PRIMARY KEY (vaccineinfoid);


--
-- TOC entry 4732 (class 2606 OID 73319)
-- Name: vaccine_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vaccine
    ADD CONSTRAINT vaccine_pkey PRIMARY KEY (vaccineid);


--
-- TOC entry 4739 (class 2606 OID 73321)
-- Name: vtap_pkey; Type: CONSTRAINT; Schema: kdah; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vtap
    ADD CONSTRAINT vtap_pkey PRIMARY KEY (vtap_id);


--
-- TOC entry 4740 (class 2606 OID 73612)
-- Name: bed_device_detail_bbox_device_id_fkey; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY bed_device_detail
    ADD CONSTRAINT bed_device_detail_bbox_device_id_fkey FOREIGN KEY (bbox_device_id) REFERENCES ref_inicu_bbox(bbox_id);


--
-- TOC entry 4743 (class 2606 OID 73617)
-- Name: inicu_devices_devicetypeid_fkey; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_inicu_devices
    ADD CONSTRAINT inicu_devices_devicetypeid_fkey FOREIGN KEY (devicetypeid) REFERENCES ref_device_type(devicetypeid);


--
-- TOC entry 4741 (class 2606 OID 73622)
-- Name: med_administration_baby_presid_fkey; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY med_administration
    ADD CONSTRAINT med_administration_baby_presid_fkey FOREIGN KEY (baby_presid) REFERENCES baby_prescription(baby_presid);


--
-- TOC entry 4742 (class 2606 OID 73627)
-- Name: ref_inicu_bbox_boards_bboxid_fkey; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY ref_inicu_bbox_boards
    ADD CONSTRAINT ref_inicu_bbox_boards_bboxid_fkey FOREIGN KEY (bboxid) REFERENCES ref_inicu_bbox(bbox_id);


--
-- TOC entry 4744 (class 2606 OID 73632)
-- Name: sa_cns_f2; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_cns
    ADD CONSTRAINT sa_cns_f2 FOREIGN KEY (seizures_cause) REFERENCES ref_seizures_cause(seizurescauseid);


--
-- TOC entry 4745 (class 2606 OID 73637)
-- Name: sa_cns_fk1; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_cns
    ADD CONSTRAINT sa_cns_fk1 FOREIGN KEY (seizures_type) REFERENCES ref_seizures_type(seizurestypeid);


--
-- TOC entry 4746 (class 2606 OID 73642)
-- Name: sa_cns_fk3; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_cns
    ADD CONSTRAINT sa_cns_fk3 FOREIGN KEY (seizures_medi) REFERENCES ref_seizures_medication(seizuresmediid);


--
-- TOC entry 4747 (class 2606 OID 73647)
-- Name: sa_metabolic_fk1; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_metabolic
    ADD CONSTRAINT sa_metabolic_fk1 FOREIGN KEY (treatmentused) REFERENCES ref_metabolic_treatment(treatmentid);


--
-- TOC entry 4748 (class 2606 OID 73652)
-- Name: sa_misc_fk1; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_misc
    ADD CONSTRAINT sa_misc_fk1 FOREIGN KEY (ropstage) REFERENCES ref_ropstage(ropstageid);


--
-- TOC entry 4749 (class 2606 OID 73657)
-- Name: sepsis_fk1; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY sa_sepsis
    ADD CONSTRAINT sepsis_fk1 FOREIGN KEY (presentationeos) REFERENCES ref_presentationeos(presentationeosid);


--
-- TOC entry 4750 (class 2606 OID 73662)
-- Name: vaccineinfo_ibfk_1; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY vaccine_info
    ADD CONSTRAINT vaccineinfo_ibfk_1 FOREIGN KEY (vaccineid) REFERENCES vaccine(vaccineid);


--
-- TOC entry 4751 (class 2606 OID 73667)
-- Name: vaccineinfo_ibfk_2; Type: FK CONSTRAINT; Schema: kdah; Owner: postgres
--

ALTER TABLE ONLY vaccine_info
    ADD CONSTRAINT vaccineinfo_ibfk_2 FOREIGN KEY (dueageid) REFERENCES inicudueage(dueageid);


--
-- TOC entry 5258 (class 0 OID 0)
-- Dependencies: 7
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- TOC entry 5273 (class 0 OID 0)
-- Dependencies: 421
-- Name: patient_due_vaccine_dtls; Type: ACL; Schema: kdah; Owner: postgres
--

REVOKE ALL ON TABLE patient_due_vaccine_dtls FROM PUBLIC;
REVOKE ALL ON TABLE patient_due_vaccine_dtls FROM postgres;
GRANT ALL ON TABLE patient_due_vaccine_dtls TO postgres;
GRANT INSERT ON TABLE patient_due_vaccine_dtls TO PUBLIC;
