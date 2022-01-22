--
-- TOC entry 4452 (class 1259 OID 73322)
-- Name: dueageid; Type: INDEX; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE INDEX dueageid ON inicudueage USING btree (dueageid);


--
-- TOC entry 4733 (class 1259 OID 73323)
-- Name: id_index8; Type: INDEX; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE INDEX id_index8 ON vaccine_info USING btree (vaccineinfoid);


--
-- TOC entry 4734 (class 1259 OID 73324)
-- Name: id_index9; Type: INDEX; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE INDEX id_index9 ON vaccine_info USING btree (dueageid);


--
-- TOC entry 4737 (class 1259 OID 73325)
-- Name: vaccineinfoid; Type: INDEX; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE INDEX vaccineinfoid ON vaccine_info USING btree (vaccineinfoid);


--
-- TOC entry 5251 (class 2618 OID 73326)
-- Name: _RETURN; Type: RULE; Schema: kdah; Owner: postgres
--

CREATE RULE "_RETURN" AS
    ON SELECT TO vw_respiratory_usage_raw DO INSTEAD  SELECT respsupport.respsupportid,
    respsupport.creationtime,
    respsupport.uhid,
    respsupport.eventid,
    respsupport.eventname,
    respsupport.rs_vent_type,
    respsupport.isactive,
    lead(respsupport.creationtime, 1) OVER (PARTITION BY respsupport.uhid) AS endtime
   FROM respsupport
  GROUP BY respsupport.uhid, respsupport.creationtime, respsupport.respsupportid,respsupport.eventid,respsupport.eventname,respsupport.rs_vent_type,respsupport.isactive
  ORDER BY respsupport.uhid, respsupport.creationtime, respsupport.respsupportid;


--
-- TOC entry 4888 (class 2620 OID 73327)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON inicuuser FOR EACH ROW EXECUTE PROCEDURE user_creationtime();


--
-- TOC entry 5033 (class 2620 OID 73328)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON users_usergroups FOR EACH ROW EXECUTE PROCEDURE usergroups_creationtime();


--
-- TOC entry 5031 (class 2620 OID 73329)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON users_roles FOR EACH ROW EXECUTE PROCEDURE userrole_creationtime();


--
-- TOC entry 4962 (class 2620 OID 73330)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON role_manager FOR EACH ROW EXECUTE PROCEDURE managerole_creationtime();


--
-- TOC entry 4825 (class 2620 OID 73331)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON parent_detail FOR EACH ROW EXECUTE PROCEDURE parentdetail_creationtime();


--
-- TOC entry 4754 (class 2620 OID 73332)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON baby_detail FOR EACH ROW EXECUTE PROCEDURE babydetail_creationtime();


--
-- TOC entry 4833 (class 2620 OID 73333)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON notifications FOR EACH ROW EXECUTE PROCEDURE notifications_creationtime();


--
-- TOC entry 4813 (class 2620 OID 73334)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON bed_device_detail FOR EACH ROW EXECUTE PROCEDURE beddevicedetail_creationtime();


--
-- TOC entry 4835 (class 2620 OID 73335)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON uhidnotification FOR EACH ROW EXECUTE PROCEDURE uhidnotification_creationtime();


--
-- TOC entry 4892 (class 2620 OID 73336)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON logindetails FOR EACH ROW EXECUTE PROCEDURE logindetails_logintime();


--
-- TOC entry 4790 (class 2620 OID 73337)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_jaundice FOR EACH ROW EXECUTE PROCEDURE sajaundice_creationtime();


--
-- TOC entry 4980 (class 2620 OID 73338)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_metabolic FOR EACH ROW EXECUTE PROCEDURE sametabolic_creationtime();


--
-- TOC entry 4903 (class 2620 OID 73339)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON mother_current_pregnancy FOR EACH ROW EXECUTE PROCEDURE currentpregnancy_creationtime();


--
-- TOC entry 4992 (class 2620 OID 73340)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_respsystem FOR EACH ROW EXECUTE PROCEDURE respsystem_creationtime();


--
-- TOC entry 4881 (class 2620 OID 73341)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON exceptionlist FOR EACH ROW EXECUTE PROCEDURE exceptionlist_creationtime();


--
-- TOC entry 4966 (class 2620 OID 73342)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_cns FOR EACH ROW EXECUTE PROCEDURE sacns_creationtime();


--
-- TOC entry 4994 (class 2620 OID 73343)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsis_creationtime();


--
-- TOC entry 4964 (class 2620 OID 73344)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_cardiac FOR EACH ROW EXECUTE PROCEDURE cardiac_creationtime();


--
-- TOC entry 4984 (class 2620 OID 73345)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_renalfailure FOR EACH ROW EXECUTE PROCEDURE renal_creationtime();


--
-- TOC entry 4970 (class 2620 OID 73346)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_followup FOR EACH ROW EXECUTE PROCEDURE followup_creationtime();


--
-- TOC entry 4982 (class 2620 OID 73347)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_misc FOR EACH ROW EXECUTE PROCEDURE misc_creationtime();


--
-- TOC entry 4968 (class 2620 OID 73348)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_feedgrowth FOR EACH ROW EXECUTE PROCEDURE feedgrowth_creationtime();


--
-- TOC entry 4877 (class 2620 OID 73349)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON doctor_note_templates FOR EACH ROW EXECUTE PROCEDURE doctemplate_creationtime();


--
-- TOC entry 4887 (class 2620 OID 73350)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON iniculogs FOR EACH ROW EXECUTE PROCEDURE iniculogs_creationtime();


--
-- TOC entry 4805 (class 2620 OID 73351)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON doctor_notes FOR EACH ROW EXECUTE PROCEDURE doctornotes_creationtime();


--
-- TOC entry 4951 (class 2620 OID 73352)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON pupil_reactivity FOR EACH ROW EXECUTE PROCEDURE pupilreactivity_creationtime();


--
-- TOC entry 4922 (class 2620 OID 73353)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_notes FOR EACH ROW EXECUTE PROCEDURE nursingnotes_creationtime();


--
-- TOC entry 4819 (class 2620 OID 73354)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON blood_products FOR EACH ROW EXECUTE PROCEDURE bloodproducts_creationtime();


--
-- TOC entry 4807 (class 2620 OID 73355)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON baby_prescription FOR EACH ROW EXECUTE PROCEDURE baby_prescription_creationtime();


--
-- TOC entry 4899 (class 2620 OID 73356)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON med_administration FOR EACH ROW EXECUTE PROCEDURE med_administration_creationtime();


--
-- TOC entry 4831 (class 2620 OID 73357)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON ref_room FOR EACH ROW EXECUTE PROCEDURE refroom_creationtime();


--
-- TOC entry 4828 (class 2620 OID 73358)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON ref_bed FOR EACH ROW EXECUTE PROCEDURE refbed_creationtime();


--
-- TOC entry 4901 (class 2620 OID 73359)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON medicine FOR EACH ROW EXECUTE PROCEDURE medicine_creationtime();


--
-- TOC entry 4920 (class 2620 OID 73360)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_neurovitals FOR EACH ROW EXECUTE PROCEDURE nursing_neurovitals_creationtime();


--
-- TOC entry 4915 (class 2620 OID 73361)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_intake FOR EACH ROW EXECUTE PROCEDURE nursing_intake_creationtime();


--
-- TOC entry 4823 (class 2620 OID 73362)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON device_monitor_detail FOR EACH ROW EXECUTE PROCEDURE patientdevicedetail_creationtime();


--
-- TOC entry 4908 (class 2620 OID 73363)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_bolus FOR EACH ROW EXECUTE PROCEDURE nursing_bolus_creationtime();


--
-- TOC entry 4912 (class 2620 OID 73364)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_dailyassesment FOR EACH ROW EXECUTE PROCEDURE nursing_dailyassesment_creationtime();


--
-- TOC entry 4924 (class 2620 OID 73365)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_output FOR EACH ROW EXECUTE PROCEDURE nursing_output_creationtime();


--
-- TOC entry 4926 (class 2620 OID 73366)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_outputdrain FOR EACH ROW EXECUTE PROCEDURE nursing_drain_creationtime();


--
-- TOC entry 4815 (class 2620 OID 73367)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON device_detail FOR EACH ROW EXECUTE PROCEDURE devicedetail_creationtime();


--
-- TOC entry 4906 (class 2620 OID 73368)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_bloodproducts FOR EACH ROW EXECUTE PROCEDURE nursing_bloodproducts_creationtime();


--
-- TOC entry 4910 (class 2620 OID 73369)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_catheters FOR EACH ROW EXECUTE PROCEDURE nursing_catheters_creationtime();


--
-- TOC entry 4918 (class 2620 OID 73370)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursing_misc FOR EACH ROW EXECUTE PROCEDURE nursing_misc_creationtime();


--
-- TOC entry 4875 (class 2620 OID 73371)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON dischargepatient_detail FOR EACH ROW EXECUTE PROCEDURE dischargepatient_detail_creationtime();


--
-- TOC entry 4849 (class 2620 OID 73372)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_doctornotes FOR EACH ROW EXECUTE PROCEDURE discharge_doctornotes_creationtime();


--
-- TOC entry 4811 (class 2620 OID 73373)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON babytpnfeed_detail FOR EACH ROW EXECUTE PROCEDURE babytpnfeed_creationtime();


--
-- TOC entry 4809 (class 2620 OID 73374)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON babyfeedivmed_detail FOR EACH ROW EXECUTE PROCEDURE babyfeedivmed_creationtime();


--
-- TOC entry 4885 (class 2620 OID 73375)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON hl7_device_mapping FOR EACH ROW EXECUTE PROCEDURE devicemapping_creationtime();


--
-- TOC entry 4841 (class 2620 OID 73376)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_aminophylline FOR EACH ROW EXECUTE PROCEDURE dischargeaminophylline_creationtime();


--
-- TOC entry 4843 (class 2620 OID 73377)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_birthdetail FOR EACH ROW EXECUTE PROCEDURE dischargebirthdetail_creationtime();


--
-- TOC entry 4845 (class 2620 OID 73378)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_caffeine FOR EACH ROW EXECUTE PROCEDURE dischargecaffeine_creationtime();


--
-- TOC entry 4847 (class 2620 OID 73379)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_cpap FOR EACH ROW EXECUTE PROCEDURE dischargecpap_creationtime();


--
-- TOC entry 4851 (class 2620 OID 73380)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_feeding FOR EACH ROW EXECUTE PROCEDURE dischargefeeding_creationtime();


--
-- TOC entry 4853 (class 2620 OID 73381)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_infection FOR EACH ROW EXECUTE PROCEDURE dischargeinfection_creationtime();


--
-- TOC entry 4855 (class 2620 OID 73382)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_investigation FOR EACH ROW EXECUTE PROCEDURE dischargeinvestigation_creationtime();


--
-- TOC entry 4857 (class 2620 OID 73383)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_metabolic FOR EACH ROW EXECUTE PROCEDURE dischargemetabolic_creationtime();


--
-- TOC entry 4859 (class 2620 OID 73384)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_nicucourse FOR EACH ROW EXECUTE PROCEDURE dischargenicucourse_creationtime();


--
-- TOC entry 4865 (class 2620 OID 73385)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_phototherapy FOR EACH ROW EXECUTE PROCEDURE dischargephototherapy_creationtime();


--
-- TOC entry 4867 (class 2620 OID 73386)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_summary FOR EACH ROW EXECUTE PROCEDURE dischargesummary_creationtime();


--
-- TOC entry 4869 (class 2620 OID 73387)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_treatment FOR EACH ROW EXECUTE PROCEDURE dischargetreatment_creationtime();


--
-- TOC entry 4871 (class 2620 OID 73388)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_ventcourse FOR EACH ROW EXECUTE PROCEDURE dischargeventcourse_creationtime();


--
-- TOC entry 4873 (class 2620 OID 73389)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_ventilation FOR EACH ROW EXECUTE PROCEDURE dischargeventilation_creationtime();


--
-- TOC entry 4945 (class 2620 OID 73390)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON preference FOR EACH ROW EXECUTE PROCEDURE pref_creationtime();


--
-- TOC entry 5024 (class 2620 OID 73391)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON setting_reference FOR EACH ROW EXECUTE PROCEDURE setting_creationtime();


--
-- TOC entry 4757 (class 2620 OID 73392)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON headtotoe_examination FOR EACH ROW EXECUTE PROCEDURE headtotoe_examination_creationtime();


--
-- TOC entry 4897 (class 2620 OID 73393)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON maternal_pasthistory FOR EACH ROW EXECUTE PROCEDURE maternalpasthistory_creationtime();


--
-- TOC entry 4800 (class 2620 OID 73394)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON baby_addinfo FOR EACH ROW EXECUTE PROCEDURE addinfo_creationtime();


--
-- TOC entry 4998 (class 2620 OID 73395)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_ballard FOR EACH ROW EXECUTE PROCEDURE ballardscore_creationtime();


--
-- TOC entry 4996 (class 2620 OID 73396)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_apgar FOR EACH ROW EXECUTE PROCEDURE apgarscore_creationtime();


--
-- TOC entry 5020 (class 2620 OID 73397)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_silverman FOR EACH ROW EXECUTE PROCEDURE silvermanscore_creationtime();


--
-- TOC entry 5004 (class 2620 OID 73398)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_downes FOR EACH ROW EXECUTE PROCEDURE downesscore_creationtime();


--
-- TOC entry 5018 (class 2620 OID 73399)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsisscore_creationtime();


--
-- TOC entry 5006 (class 2620 OID 73400)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_hie FOR EACH ROW EXECUTE PROCEDURE hiescore_creationtime();


--
-- TOC entry 5008 (class 2620 OID 73401)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_ivh FOR EACH ROW EXECUTE PROCEDURE ivhscore_creationtime();


--
-- TOC entry 5002 (class 2620 OID 73402)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_bind FOR EACH ROW EXECUTE PROCEDURE bindscore_creationtime();


--
-- TOC entry 5012 (class 2620 OID 73403)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_pain FOR EACH ROW EXECUTE PROCEDURE painscore_creationtime();


--
-- TOC entry 5000 (class 2620 OID 73404)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_bellstage FOR EACH ROW EXECUTE PROCEDURE bellstagescore_creationtime();


--
-- TOC entry 4890 (class 2620 OID 73405)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON investigation_ordered FOR EACH ROW EXECUTE PROCEDURE investigationorder_creationtime();


--
-- TOC entry 4957 (class 2620 OID 73406)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON ref_inicu_bbox FOR EACH ROW EXECUTE PROCEDURE inicu_bbox_creationtime();


--
-- TOC entry 4959 (class 2620 OID 73407)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON ref_inicu_bbox_boards FOR EACH ROW EXECUTE PROCEDURE ref_inicu_bbox_boards_creationtime();


--
-- TOC entry 5030 (class 2620 OID 73408)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON test_result_inicu FOR EACH ROW EXECUTE PROCEDURE test_result_inicu_creationtime();


--
-- TOC entry 4953 (class 2620 OID 73409)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON readmit_history FOR EACH ROW EXECUTE PROCEDURE readmithistory_creationtime();


--
-- TOC entry 4761 (class 2620 OID 73410)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON test_result FOR EACH ROW EXECUTE PROCEDURE test_result_creationtime();


--
-- TOC entry 4935 (class 2620 OID 73411)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursingorder_rds FOR EACH ROW EXECUTE PROCEDURE nursingorderrds_creationtime();


--
-- TOC entry 4937 (class 2620 OID 73412)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursingorder_rds_medicine FOR EACH ROW EXECUTE PROCEDURE nursingorderrds_medicine_creationtime();


--
-- TOC entry 4930 (class 2620 OID 73413)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursingorder_apnea FOR EACH ROW EXECUTE PROCEDURE nursingorderapnea_creationtime();


--
-- TOC entry 4792 (class 2620 OID 73414)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_apnea FOR EACH ROW EXECUTE PROCEDURE sa_resp_apnea_creationtime();


--
-- TOC entry 4986 (class 2620 OID 73415)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_bpd FOR EACH ROW EXECUTE PROCEDURE respbpd_creationtime();


--
-- TOC entry 4798 (class 2620 OID 73416)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_rds FOR EACH ROW EXECUTE PROCEDURE resprds_creationtime();


--
-- TOC entry 4990 (class 2620 OID 73417)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_others FOR EACH ROW EXECUTE PROCEDURE respothers_creationtime();


--
-- TOC entry 4796 (class 2620 OID 73418)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_pphn FOR EACH ROW EXECUTE PROCEDURE resppphn_creationtime();


--
-- TOC entry 4794 (class 2620 OID 73419)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_pneumothorax FOR EACH ROW EXECUTE PROCEDURE resppneumothorax_creationtime();


--
-- TOC entry 4988 (class 2620 OID 73420)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_resp_chesttube FOR EACH ROW EXECUTE PROCEDURE resppneumothorax_creationtime();


--
-- TOC entry 4784 (class 2620 OID 73421)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hypoglycemia FOR EACH ROW EXECUTE PROCEDURE sametabolic_creationtime();


--
-- TOC entry 4776 (class 2620 OID 73422)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hyperglycemia FOR EACH ROW EXECUTE PROCEDURE hyperglycemia_creationtime();


--
-- TOC entry 4786 (class 2620 OID 73423)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hyponatremia FOR EACH ROW EXECUTE PROCEDURE hyponatremia_creationtime();


--
-- TOC entry 4780 (class 2620 OID 73424)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hypernatremia FOR EACH ROW EXECUTE PROCEDURE hypernatremia_creationtime();


--
-- TOC entry 4932 (class 2620 OID 73425)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON nursingorder_assesment FOR EACH ROW EXECUTE PROCEDURE nursingorder_creationtime();


--
-- TOC entry 4774 (class 2620 OID 73426)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hypercalcemia FOR EACH ROW EXECUTE PROCEDURE hypercalcemia_creationtime();


--
-- TOC entry 4782 (class 2620 OID 73427)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hypocalcemia FOR EACH ROW EXECUTE PROCEDURE hypocalcemia_creationtime();


--
-- TOC entry 4778 (class 2620 OID 73428)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hyperkalemia FOR EACH ROW EXECUTE PROCEDURE hyperkalemia_creationtime();


--
-- TOC entry 4972 (class 2620 OID 73429)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_hypokalemia FOR EACH ROW EXECUTE PROCEDURE hypokalemia_creationtime();


--
-- TOC entry 4788 (class 2620 OID 73430)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_iem FOR EACH ROW EXECUTE PROCEDURE iem_creationtime();


--
-- TOC entry 4766 (class 2620 OID 73431)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_acidosis FOR EACH ROW EXECUTE PROCEDURE acidosis_creationtime();


--
-- TOC entry 4768 (class 2620 OID 73432)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_cns_asphyxia FOR EACH ROW EXECUTE PROCEDURE sacnsasphyxia_creationtime();


--
-- TOC entry 5022 (class 2620 OID 73433)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_thompson FOR EACH ROW EXECUTE PROCEDURE thompsonscore_creationtime();


--
-- TOC entry 4770 (class 2620 OID 73434)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_cns_ivh FOR EACH ROW EXECUTE PROCEDURE sacnsivh_creationtime();


--
-- TOC entry 5016 (class 2620 OID 73435)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_sarnat FOR EACH ROW EXECUTE PROCEDURE sarnatscore_creationtime();


--
-- TOC entry 5010 (class 2620 OID 73436)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_levene FOR EACH ROW EXECUTE PROCEDURE levenescore_creationtime();


--
-- TOC entry 5014 (class 2620 OID 73437)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON score_papile FOR EACH ROW EXECUTE PROCEDURE papilescore_creationtime();


--
-- TOC entry 4976 (class 2620 OID 73438)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_infection_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsis_creationtime();


--
-- TOC entry 4759 (class 2620 OID 73439)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON device_ventilator_detail FOR EACH ROW EXECUTE PROCEDURE device_ventilator_detail_creationtime();


--
-- TOC entry 4772 (class 2620 OID 73440)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_cns_seizures FOR EACH ROW EXECUTE PROCEDURE sacnsseizures_creationtime();


--
-- TOC entry 4974 (class 2620 OID 73441)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_infection_clabsi FOR EACH ROW EXECUTE PROCEDURE clabsi_creationtime();


--
-- TOC entry 4978 (class 2620 OID 73442)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON sa_infection_vap FOR EACH ROW EXECUTE PROCEDURE vap_creationtime();


--
-- TOC entry 4762 (class 2620 OID 73443)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON antenatal_history_detail FOR EACH ROW EXECUTE PROCEDURE antenatal_history_creationtime();


--
-- TOC entry 4955 (class 2620 OID 73444)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON reason_admission FOR EACH ROW EXECUTE PROCEDURE reason_admission_creationtime();


--
-- TOC entry 4764 (class 2620 OID 73445)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON antenatal_steroid_detail FOR EACH ROW EXECUTE PROCEDURE antenatal_steroid_creationtime();


--
-- TOC entry 4883 (class 2620 OID 73446)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON gen_phy_exam FOR EACH ROW EXECUTE PROCEDURE gen_phy_exam_creationtime();


--
-- TOC entry 4837 (class 2620 OID 73447)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON device_bloodgas_detail_detail FOR EACH ROW EXECUTE PROCEDURE device_bloodgas_detail_creationtime();


--
-- TOC entry 4861 (class 2620 OID 73448)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_notes_detail FOR EACH ROW EXECUTE PROCEDURE discharge_notes_creationtime();


--
-- TOC entry 4817 (class 2620 OID 73449)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON birth_to_nicu FOR EACH ROW EXECUTE PROCEDURE birth_to_nicu_creationtime();


--
-- TOC entry 4752 (class 2620 OID 73450)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON admission_notes FOR EACH ROW EXECUTE PROCEDURE admission_notes_creationtime();


--
-- TOC entry 4839 (class 2620 OID 73451)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_advice_detail FOR EACH ROW EXECUTE PROCEDURE discharge_advice_creationtime();


--
-- TOC entry 4941 (class 2620 OID 73452)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON outborn_medicine FOR EACH ROW EXECUTE PROCEDURE outborn_medicine_creationtime();


--
-- TOC entry 4895 (class 2620 OID 73453)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON master_address FOR EACH ROW EXECUTE PROCEDURE master_address_creationtime();


--
-- TOC entry 4803 (class 2620 OID 73454)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON babyfeed_detail FOR EACH ROW EXECUTE PROCEDURE babyfeed_creationtime();


--
-- TOC entry 4939 (class 2620 OID 73455)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON oralfeed_detail FOR EACH ROW EXECUTE PROCEDURE oralfeed_detail_creationtime();


--
-- TOC entry 4943 (class 2620 OID 73456)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON peripheral_cannula FOR EACH ROW EXECUTE PROCEDURE peripheral_cannula_creationtime();


--
-- TOC entry 4821 (class 2620 OID 73457)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON central_line FOR EACH ROW EXECUTE PROCEDURE central_line_creationtime();


--
-- TOC entry 4893 (class 2620 OID 73458)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON lumbar_puncture FOR EACH ROW EXECUTE PROCEDURE lumbar_puncture_creationtime();


--
-- TOC entry 5035 (class 2620 OID 73459)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON vtap FOR EACH ROW EXECUTE PROCEDURE vtap_creationtime();


--
-- TOC entry 4879 (class 2620 OID 73460)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON et_intubation FOR EACH ROW EXECUTE PROCEDURE et_intubation_creationtime();


--
-- TOC entry 4863 (class 2620 OID 73461)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON discharge_outcome FOR EACH ROW EXECUTE PROCEDURE dischargeoutcome_creationtime();


--
-- TOC entry 5026 (class 2620 OID 73462)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON stable_notes FOR EACH ROW EXECUTE PROCEDURE stable_notes_creationtime();


--
-- TOC entry 4949 (class 2620 OID 73463)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON procedure_other FOR EACH ROW EXECUTE PROCEDURE procedure_other_creationtime();


--
-- TOC entry 5028 (class 2620 OID 73464)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON systemic_exam FOR EACH ROW EXECUTE PROCEDURE systemic_exam_creationtime();


--
-- TOC entry 4947 (class 2620 OID 73465)
-- Name: set_creationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_creationtime BEFORE INSERT ON procedure_chesttube FOR EACH ROW EXECUTE PROCEDURE procedure_chesttube_creationtime();


--
-- TOC entry 4755 (class 2620 OID 73466)
-- Name: set_episodeid; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_episodeid BEFORE INSERT ON baby_detail FOR EACH ROW EXECUTE PROCEDURE babydetails_episodeid();


--
-- TOC entry 4889 (class 2620 OID 73467)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON inicuuser FOR EACH ROW EXECUTE PROCEDURE user_modificationtime();


--
-- TOC entry 5034 (class 2620 OID 73468)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON users_usergroups FOR EACH ROW EXECUTE PROCEDURE usergroups_modificationtime();


--
-- TOC entry 5032 (class 2620 OID 73469)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON users_roles FOR EACH ROW EXECUTE PROCEDURE userrole_modificationtime();


--
-- TOC entry 4963 (class 2620 OID 73470)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON role_manager FOR EACH ROW EXECUTE PROCEDURE managerole_modificationtime();


--
-- TOC entry 4826 (class 2620 OID 73471)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON parent_detail FOR EACH ROW EXECUTE PROCEDURE parentdetail_modificationtime();


--
-- TOC entry 4756 (class 2620 OID 73472)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON baby_detail FOR EACH ROW EXECUTE PROCEDURE babydetail_modificationtime();


--
-- TOC entry 4834 (class 2620 OID 73473)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON notifications FOR EACH ROW EXECUTE PROCEDURE notifications_modificationtime();


--
-- TOC entry 4814 (class 2620 OID 73474)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON bed_device_detail FOR EACH ROW EXECUTE PROCEDURE beddevicedetail_modificationtime();


--
-- TOC entry 4836 (class 2620 OID 73475)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE INSERT ON uhidnotification FOR EACH ROW EXECUTE PROCEDURE uhidnotification_modificationtime();


--
-- TOC entry 4791 (class 2620 OID 73476)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_jaundice FOR EACH ROW EXECUTE PROCEDURE sajaundice_modificationtime();


--
-- TOC entry 4981 (class 2620 OID 73477)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_metabolic FOR EACH ROW EXECUTE PROCEDURE sametabolic_modificationtime();


--
-- TOC entry 4904 (class 2620 OID 73478)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON mother_current_pregnancy FOR EACH ROW EXECUTE PROCEDURE currentpregnancy_modificationtime();


--
-- TOC entry 4993 (class 2620 OID 73479)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_respsystem FOR EACH ROW EXECUTE PROCEDURE respsystem_modificationtime();


--
-- TOC entry 4882 (class 2620 OID 73480)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON exceptionlist FOR EACH ROW EXECUTE PROCEDURE exceptionlist_modificationtime();


--
-- TOC entry 4967 (class 2620 OID 73481)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_cns FOR EACH ROW EXECUTE PROCEDURE sacns_modificationtime();


--
-- TOC entry 4995 (class 2620 OID 73482)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsis_modificationtime();


--
-- TOC entry 4965 (class 2620 OID 73483)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_cardiac FOR EACH ROW EXECUTE PROCEDURE cardiac_modificationtime();


--
-- TOC entry 4985 (class 2620 OID 73484)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_renalfailure FOR EACH ROW EXECUTE PROCEDURE renal_modificationtime();


--
-- TOC entry 4971 (class 2620 OID 73485)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_followup FOR EACH ROW EXECUTE PROCEDURE followup_modificationtime();


--
-- TOC entry 4983 (class 2620 OID 73486)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_misc FOR EACH ROW EXECUTE PROCEDURE misc_modificationtime();


--
-- TOC entry 4969 (class 2620 OID 73487)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_feedgrowth FOR EACH ROW EXECUTE PROCEDURE feedgrowth_modificationtime();


--
-- TOC entry 4878 (class 2620 OID 73488)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON doctor_note_templates FOR EACH ROW EXECUTE PROCEDURE doctemplate_modificationtime();


--
-- TOC entry 4806 (class 2620 OID 73489)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON doctor_notes FOR EACH ROW EXECUTE PROCEDURE doctornotes_modificationtime();


--
-- TOC entry 4804 (class 2620 OID 73490)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON babyfeed_detail FOR EACH ROW EXECUTE PROCEDURE babyfeed_modificationtime();


--
-- TOC entry 4952 (class 2620 OID 73491)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON pupil_reactivity FOR EACH ROW EXECUTE PROCEDURE pupilreactivity_modificationtime();


--
-- TOC entry 4923 (class 2620 OID 73492)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_notes FOR EACH ROW EXECUTE PROCEDURE nursingnotes_modificationtime();


--
-- TOC entry 4820 (class 2620 OID 73493)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON blood_products FOR EACH ROW EXECUTE PROCEDURE bloodproducts_modificationtime();


--
-- TOC entry 4808 (class 2620 OID 73494)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON baby_prescription FOR EACH ROW EXECUTE PROCEDURE baby_prescription_modificationtime();


--
-- TOC entry 4900 (class 2620 OID 73495)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON med_administration FOR EACH ROW EXECUTE PROCEDURE med_administration_modificationtime();


--
-- TOC entry 4832 (class 2620 OID 73496)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON ref_room FOR EACH ROW EXECUTE PROCEDURE refroom_modificationtime();


--
-- TOC entry 4829 (class 2620 OID 73497)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON ref_bed FOR EACH ROW EXECUTE PROCEDURE refbed_modificationtime();


--
-- TOC entry 4902 (class 2620 OID 73498)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON medicine FOR EACH ROW EXECUTE PROCEDURE medicine_modificationtime();


--
-- TOC entry 4921 (class 2620 OID 73499)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_neurovitals FOR EACH ROW EXECUTE PROCEDURE nursing_neurovitals_modificationtime();


--
-- TOC entry 4905 (class 2620 OID 73500)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_bloodgas FOR EACH ROW EXECUTE PROCEDURE nursing_bloodgas_modificationtime();


--
-- TOC entry 4928 (class 2620 OID 73501)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_ventilaor FOR EACH ROW EXECUTE PROCEDURE nursing_ventilaor_modificationtime();


--
-- TOC entry 4929 (class 2620 OID 73502)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_vitalparameters FOR EACH ROW EXECUTE PROCEDURE nursing_vitalparameters_modificationtime();


--
-- TOC entry 4916 (class 2620 OID 73503)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_intake FOR EACH ROW EXECUTE PROCEDURE nursing_intake_modificationtime();


--
-- TOC entry 4824 (class 2620 OID 73504)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON device_monitor_detail FOR EACH ROW EXECUTE PROCEDURE patientdevicedetail_modificationtime();


--
-- TOC entry 4802 (class 2620 OID 73505)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON baby_visit FOR EACH ROW EXECUTE PROCEDURE visit_modificationtime();


--
-- TOC entry 4909 (class 2620 OID 73506)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_bolus FOR EACH ROW EXECUTE PROCEDURE nursing_bolus_modificationtime();


--
-- TOC entry 4913 (class 2620 OID 73507)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_dailyassesment FOR EACH ROW EXECUTE PROCEDURE nursing_dailyassesment_modificationtime();


--
-- TOC entry 4925 (class 2620 OID 73508)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_output FOR EACH ROW EXECUTE PROCEDURE nursing_output_modificationtime();


--
-- TOC entry 4927 (class 2620 OID 73509)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_outputdrain FOR EACH ROW EXECUTE PROCEDURE nursing_drain_modificationtime();


--
-- TOC entry 4816 (class 2620 OID 73510)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON device_detail FOR EACH ROW EXECUTE PROCEDURE devicedetail_modificationtime();


--
-- TOC entry 4907 (class 2620 OID 73511)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_bloodproducts FOR EACH ROW EXECUTE PROCEDURE nursing_bloodproducts_modificationtime();


--
-- TOC entry 4911 (class 2620 OID 73512)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_catheters FOR EACH ROW EXECUTE PROCEDURE nursing_catheters_modificationtime();


--
-- TOC entry 4919 (class 2620 OID 73513)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_misc FOR EACH ROW EXECUTE PROCEDURE nursing_misc_modificationtime();


--
-- TOC entry 4876 (class 2620 OID 73514)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON dischargepatient_detail FOR EACH ROW EXECUTE PROCEDURE dischargepatient_detail_modificationtime();


--
-- TOC entry 4850 (class 2620 OID 73515)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_doctornotes FOR EACH ROW EXECUTE PROCEDURE discharge_doctornotes_modificationtime();


--
-- TOC entry 4812 (class 2620 OID 73516)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON babytpnfeed_detail FOR EACH ROW EXECUTE PROCEDURE babytpnfeed_modificationtime();


--
-- TOC entry 4810 (class 2620 OID 73517)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON babyfeedivmed_detail FOR EACH ROW EXECUTE PROCEDURE babyfeedivmed_modificationtime();


--
-- TOC entry 4886 (class 2620 OID 73518)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON hl7_device_mapping FOR EACH ROW EXECUTE PROCEDURE devicemapping_modificationtime();


--
-- TOC entry 4842 (class 2620 OID 73519)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_aminophylline FOR EACH ROW EXECUTE PROCEDURE dischargeaminophylline_modificationtime();


--
-- TOC entry 4844 (class 2620 OID 73520)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_birthdetail FOR EACH ROW EXECUTE PROCEDURE dischargebirthdetail_modificationtime();


--
-- TOC entry 4846 (class 2620 OID 73521)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_caffeine FOR EACH ROW EXECUTE PROCEDURE dischargecaffeine_modificationtime();


--
-- TOC entry 4848 (class 2620 OID 73522)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_cpap FOR EACH ROW EXECUTE PROCEDURE dischargecpap_modificationtime();


--
-- TOC entry 4852 (class 2620 OID 73523)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_feeding FOR EACH ROW EXECUTE PROCEDURE dischargefeeding_modificationtime();


--
-- TOC entry 4854 (class 2620 OID 73524)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_infection FOR EACH ROW EXECUTE PROCEDURE dischargeinfection_modificationtime();


--
-- TOC entry 4856 (class 2620 OID 73525)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_investigation FOR EACH ROW EXECUTE PROCEDURE dischargeinvestigation_modificationtime();


--
-- TOC entry 4858 (class 2620 OID 73526)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_metabolic FOR EACH ROW EXECUTE PROCEDURE dischargemetabolic_modificationtime();


--
-- TOC entry 4860 (class 2620 OID 73527)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_nicucourse FOR EACH ROW EXECUTE PROCEDURE dischargenicucourse_modificationtime();


--
-- TOC entry 4866 (class 2620 OID 73528)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_phototherapy FOR EACH ROW EXECUTE PROCEDURE dischargephototherapy_modificationtime();


--
-- TOC entry 4868 (class 2620 OID 73529)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_summary FOR EACH ROW EXECUTE PROCEDURE dischargesummary_modificationtime();


--
-- TOC entry 4870 (class 2620 OID 73530)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_treatment FOR EACH ROW EXECUTE PROCEDURE dischargetreatment_modificationtime();


--
-- TOC entry 4872 (class 2620 OID 73531)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_ventcourse FOR EACH ROW EXECUTE PROCEDURE dischargeventcourse_modificationtime();


--
-- TOC entry 4874 (class 2620 OID 73532)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_ventilation FOR EACH ROW EXECUTE PROCEDURE dischargeventilation_modificationtime();


--
-- TOC entry 4946 (class 2620 OID 73533)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON preference FOR EACH ROW EXECUTE PROCEDURE pref_modificationtime();


--
-- TOC entry 5025 (class 2620 OID 73534)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON setting_reference FOR EACH ROW EXECUTE PROCEDURE setting_modificationtime();


--
-- TOC entry 4758 (class 2620 OID 73535)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON headtotoe_examination FOR EACH ROW EXECUTE PROCEDURE headtotoe_examination_modificationtime();


--
-- TOC entry 4898 (class 2620 OID 73536)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON maternal_pasthistory FOR EACH ROW EXECUTE PROCEDURE maternalpasthistory_modificationtime();


--
-- TOC entry 4801 (class 2620 OID 73537)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON baby_addinfo FOR EACH ROW EXECUTE PROCEDURE addinfo_modificationtime();


--
-- TOC entry 4999 (class 2620 OID 73538)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_ballard FOR EACH ROW EXECUTE PROCEDURE ballardscore_modificationtime();


--
-- TOC entry 4997 (class 2620 OID 73539)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_apgar FOR EACH ROW EXECUTE PROCEDURE apgarscore_modificationtime();


--
-- TOC entry 5021 (class 2620 OID 73540)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_silverman FOR EACH ROW EXECUTE PROCEDURE silvermanscore_modificationtime();


--
-- TOC entry 5005 (class 2620 OID 73541)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_downes FOR EACH ROW EXECUTE PROCEDURE downesscore_modificationtime();


--
-- TOC entry 5019 (class 2620 OID 73542)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsisscore_modificationtime();


--
-- TOC entry 5007 (class 2620 OID 73543)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_hie FOR EACH ROW EXECUTE PROCEDURE hiescore_modificationtime();


--
-- TOC entry 5009 (class 2620 OID 73544)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_ivh FOR EACH ROW EXECUTE PROCEDURE ivhscore_modificationtime();


--
-- TOC entry 5003 (class 2620 OID 73545)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_bind FOR EACH ROW EXECUTE PROCEDURE bindscore_modificationtime();


--
-- TOC entry 5013 (class 2620 OID 73546)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_pain FOR EACH ROW EXECUTE PROCEDURE painscore_modificationtime();


--
-- TOC entry 5001 (class 2620 OID 73547)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_bellstage FOR EACH ROW EXECUTE PROCEDURE bellstagescore_modificationtime();


--
-- TOC entry 4891 (class 2620 OID 73548)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON investigation_ordered FOR EACH ROW EXECUTE PROCEDURE investigationorder_modificationtime();


--
-- TOC entry 4934 (class 2620 OID 73549)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursingorder_jaundice FOR EACH ROW EXECUTE PROCEDURE nursingorderjaundice_modificationtime();


--
-- TOC entry 4958 (class 2620 OID 73550)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON ref_inicu_bbox FOR EACH ROW EXECUTE PROCEDURE inicu_bbox_modificationtime();


--
-- TOC entry 4960 (class 2620 OID 73551)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON ref_inicu_bbox_boards FOR EACH ROW EXECUTE PROCEDURE ref_inicu_bbox_boards_modificationtime();


--
-- TOC entry 4954 (class 2620 OID 73552)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON readmit_history FOR EACH ROW EXECUTE PROCEDURE readmithistory_modificationtime();


--
-- TOC entry 4936 (class 2620 OID 73553)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursingorder_rds FOR EACH ROW EXECUTE PROCEDURE nursingorderrds_modificationtime();


--
-- TOC entry 4938 (class 2620 OID 73554)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursingorder_rds_medicine FOR EACH ROW EXECUTE PROCEDURE nursingorderrds_medicine_modificationtime();


--
-- TOC entry 4931 (class 2620 OID 73555)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursingorder_apnea FOR EACH ROW EXECUTE PROCEDURE nursingorderapnea_modificationtime();


--
-- TOC entry 4914 (class 2620 OID 73556)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursing_episode FOR EACH ROW EXECUTE PROCEDURE nursing_episode_modificationtime();


--
-- TOC entry 4793 (class 2620 OID 73557)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_apnea FOR EACH ROW EXECUTE PROCEDURE sa_resp_apnea_modificationtime();


--
-- TOC entry 4987 (class 2620 OID 73558)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_bpd FOR EACH ROW EXECUTE PROCEDURE respbpd_modificationtime();


--
-- TOC entry 4799 (class 2620 OID 73559)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_rds FOR EACH ROW EXECUTE PROCEDURE resprds_modificationtime();


--
-- TOC entry 4991 (class 2620 OID 73560)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_others FOR EACH ROW EXECUTE PROCEDURE respothers_modificationtime();


--
-- TOC entry 4797 (class 2620 OID 73561)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_pphn FOR EACH ROW EXECUTE PROCEDURE resppphn_modificationtime();


--
-- TOC entry 4795 (class 2620 OID 73562)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_pneumothorax FOR EACH ROW EXECUTE PROCEDURE resppneumothorax_creationtime();


--
-- TOC entry 4989 (class 2620 OID 73563)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_resp_chesttube FOR EACH ROW EXECUTE PROCEDURE resppneumothorax_creationtime();


--
-- TOC entry 4785 (class 2620 OID 73564)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hypoglycemia FOR EACH ROW EXECUTE PROCEDURE sametabolic_modificationtime();


--
-- TOC entry 4961 (class 2620 OID 73565)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON respsupport FOR EACH ROW EXECUTE PROCEDURE respsupport_modificationtime();


--
-- TOC entry 4777 (class 2620 OID 73566)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hyperglycemia FOR EACH ROW EXECUTE PROCEDURE hyperglycemia_modificationtime();


--
-- TOC entry 4787 (class 2620 OID 73567)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hyponatremia FOR EACH ROW EXECUTE PROCEDURE hyponatremia_modificationtime();


--
-- TOC entry 4781 (class 2620 OID 73568)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hypernatremia FOR EACH ROW EXECUTE PROCEDURE hypernatremia_modificationtime();


--
-- TOC entry 4933 (class 2620 OID 73569)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON nursingorder_assesment FOR EACH ROW EXECUTE PROCEDURE nursingorder_modificationtime();


--
-- TOC entry 4775 (class 2620 OID 73570)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hypercalcemia FOR EACH ROW EXECUTE PROCEDURE hypercalcemia_modificationtime();


--
-- TOC entry 4783 (class 2620 OID 73571)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hypocalcemia FOR EACH ROW EXECUTE PROCEDURE hypocalcemia_modificationtime();


--
-- TOC entry 4779 (class 2620 OID 73572)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hyperkalemia FOR EACH ROW EXECUTE PROCEDURE hyperkalemia_modificationtime();


--
-- TOC entry 4973 (class 2620 OID 73573)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_hypokalemia FOR EACH ROW EXECUTE PROCEDURE hypokalemia_modificationtime();


--
-- TOC entry 4789 (class 2620 OID 73574)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_iem FOR EACH ROW EXECUTE PROCEDURE iem_modificationtime();


--
-- TOC entry 4767 (class 2620 OID 73575)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_acidosis FOR EACH ROW EXECUTE PROCEDURE acidosis_modificationtime();


--
-- TOC entry 4769 (class 2620 OID 73576)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_cns_asphyxia FOR EACH ROW EXECUTE PROCEDURE sacnsasphyxia_modificationtime();


--
-- TOC entry 5023 (class 2620 OID 73577)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_thompson FOR EACH ROW EXECUTE PROCEDURE thompsonscore_modificationtime();


--
-- TOC entry 4771 (class 2620 OID 73578)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_cns_ivh FOR EACH ROW EXECUTE PROCEDURE sacnsivh_modificationtime();


--
-- TOC entry 5017 (class 2620 OID 73579)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_sarnat FOR EACH ROW EXECUTE PROCEDURE sarnatscore_modificationtime();


--
-- TOC entry 5011 (class 2620 OID 73580)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_levene FOR EACH ROW EXECUTE PROCEDURE levenescore_modificationtime();


--
-- TOC entry 5015 (class 2620 OID 73581)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON score_papile FOR EACH ROW EXECUTE PROCEDURE papilescore_modificationtime();


--
-- TOC entry 4977 (class 2620 OID 73582)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_infection_sepsis FOR EACH ROW EXECUTE PROCEDURE sepsis_modificationtime();


--
-- TOC entry 4760 (class 2620 OID 73583)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON device_ventilator_detail FOR EACH ROW EXECUTE PROCEDURE patient_ventilator_data_modificationtime();


--
-- TOC entry 4773 (class 2620 OID 73584)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_cns_seizures FOR EACH ROW EXECUTE PROCEDURE sacnsseizures_modificationtime();


--
-- TOC entry 4975 (class 2620 OID 73585)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_infection_clabsi FOR EACH ROW EXECUTE PROCEDURE clabsi_modificationtime();


--
-- TOC entry 4979 (class 2620 OID 73586)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON sa_infection_vap FOR EACH ROW EXECUTE PROCEDURE vap_modificationtime();


--
-- TOC entry 4763 (class 2620 OID 73587)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON antenatal_history_detail FOR EACH ROW EXECUTE PROCEDURE antenatal_history_modificationtime();


--
-- TOC entry 4956 (class 2620 OID 73588)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON reason_admission FOR EACH ROW EXECUTE PROCEDURE reason_admission_modificationtime();


--
-- TOC entry 4765 (class 2620 OID 73589)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON antenatal_steroid_detail FOR EACH ROW EXECUTE PROCEDURE antenatal_steroid_modificationtime();


--
-- TOC entry 4884 (class 2620 OID 73590)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON gen_phy_exam FOR EACH ROW EXECUTE PROCEDURE gen_phy_exam_modificationtime();


--
-- TOC entry 4838 (class 2620 OID 73591)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON device_bloodgas_detail_detail FOR EACH ROW EXECUTE PROCEDURE device_bloodgas_detail_modificationtime();


--
-- TOC entry 4862 (class 2620 OID 73592)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_notes_detail FOR EACH ROW EXECUTE PROCEDURE discharge_notes_modificationtime();


--
-- TOC entry 4818 (class 2620 OID 73593)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON birth_to_nicu FOR EACH ROW EXECUTE PROCEDURE birth_to_nicu_modificationtime();


--
-- TOC entry 4753 (class 2620 OID 73594)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON admission_notes FOR EACH ROW EXECUTE PROCEDURE admission_notes_modificationtime();


--
-- TOC entry 4840 (class 2620 OID 73595)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_advice_detail FOR EACH ROW EXECUTE PROCEDURE discharge_advice_modificationtime();


--
-- TOC entry 4942 (class 2620 OID 73596)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON outborn_medicine FOR EACH ROW EXECUTE PROCEDURE outborn_medicine_modificationtime();


--
-- TOC entry 4896 (class 2620 OID 73597)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON master_address FOR EACH ROW EXECUTE PROCEDURE master_address_modificationtime();


--
-- TOC entry 4940 (class 2620 OID 73598)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON oralfeed_detail FOR EACH ROW EXECUTE PROCEDURE oralfeed_detail_modificationtime();


--
-- TOC entry 4917 (class 2620 OID 73599)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE INSERT OR UPDATE ON nursing_intake_output FOR EACH ROW EXECUTE PROCEDURE nursing_intake_modificationtime();


--
-- TOC entry 4944 (class 2620 OID 73600)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON peripheral_cannula FOR EACH ROW EXECUTE PROCEDURE peripheral_cannula_modificationtime();


--
-- TOC entry 4822 (class 2620 OID 73601)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON central_line FOR EACH ROW EXECUTE PROCEDURE central_line_modificationtime();


--
-- TOC entry 4894 (class 2620 OID 73602)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON lumbar_puncture FOR EACH ROW EXECUTE PROCEDURE lumbar_puncture_modificationtime();


--
-- TOC entry 4864 (class 2620 OID 73603)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON discharge_outcome FOR EACH ROW EXECUTE PROCEDURE dischargeoutcome_modificationtime();


--
-- TOC entry 5036 (class 2620 OID 73604)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON vtap FOR EACH ROW EXECUTE PROCEDURE vtap_modificationtime();


--
-- TOC entry 5027 (class 2620 OID 73605)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON stable_notes FOR EACH ROW EXECUTE PROCEDURE stable_notes_modificationtime();


--
-- TOC entry 4880 (class 2620 OID 73606)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON et_intubation FOR EACH ROW EXECUTE PROCEDURE et_intubation_modificationtime();


--
-- TOC entry 4950 (class 2620 OID 73607)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON procedure_other FOR EACH ROW EXECUTE PROCEDURE procedure_other_modificationtime();


--
-- TOC entry 5029 (class 2620 OID 73608)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON systemic_exam FOR EACH ROW EXECUTE PROCEDURE systemic_exam_modificationtime();


--
-- TOC entry 4948 (class 2620 OID 73609)
-- Name: set_modificationtime; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_modificationtime BEFORE UPDATE ON procedure_chesttube FOR EACH ROW EXECUTE PROCEDURE procedure_chesttube_modificationtime();


--
-- TOC entry 4827 (class 2620 OID 73610)
-- Name: set_motherid; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_motherid BEFORE INSERT ON parent_detail FOR EACH ROW EXECUTE PROCEDURE parentdetail_motherid();


--
-- TOC entry 4830 (class 2620 OID 73611)
-- Name: set_removaltimestamp; Type: TRIGGER; Schema: kdah; Owner: postgres
--

CREATE TRIGGER set_removaltimestamp BEFORE UPDATE OF isactive ON ref_bed FOR EACH ROW EXECUTE PROCEDURE refbed_removaltimestamp();



