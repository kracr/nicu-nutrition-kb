package com.inicu.postgres.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The persistent class for the screen_neurological database table.
 * 
 */
@Entity
@Table(name = "screen_neurological")
@NamedQuery(name = "ScreenNeurological.findAll", query = "SELECT s FROM ScreenNeurological s")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScreenNeurological implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long screen_neurological_id;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String uhid;

	private String episodeid;

	private String loggeduser;

	private Timestamp screening_time;

	private Integer cga_weeks;

	private Integer cga_days;

	private Integer pna_weeks;

	private Integer pna_days;

	private String posture_type;

	private String posture_value;

	private String arm_traction_type;

	private String arm_traction_value;

	private String leg_traction_type;

	private String leg_traction_value;

	private String head_extensor_type;

	private String head_extensor_value;

	private String head_flexor_type;

	private String head_flexor_value;

	private String head_lag_type;

	private String head_lag_value;

	private String ventral_type;

	private String ventral_value;

	private String body_type;

	private String body_value;

	private String tremor_startles_type;

	private String tremor_startles_value;

	private String moro_reflex_type;

	private String moro_reflex_value;

	private String auditory_type;

	private String auditory_value;

	private String visual_type;

	private String visual_value;

	private String alertness_type;

	private String alertness_value;

	@Column(columnDefinition = "bool")
	private Boolean suck;

	@Column(columnDefinition = "bool")
	private Boolean facial;

	@Column(columnDefinition = "bool")
	private Boolean eye;

	@Column(columnDefinition = "bool")
	private Boolean sunset;

	@Column(columnDefinition = "bool")
	private Boolean hand;

	@Column(columnDefinition = "bool")
	private Boolean clonus;

	private String reports;

	private String other_comments;
	
	@Transient
	private String screening_message;

	private String screening_type;

	private String neuro_posture_score;
	private String neuro_posture_value;

	private String neuro_arm_recoil_score;
	private String neuro_arm_recoil_value;

	private String neuro_arm_traction_score;
	private String neuro_arm_traction_value;

	private String neuro_leg_traction_score;
	private String neuro_leg_traction_value;

	private String neuro_leg_recoil_score;
	private String neuro_leg_recoil_value;

	private String popliteal_angle_value;
	private String popliteal_angle_score;

	private String head_control_1_score;
	private String head_control_1_value;

	private String head_control_2_score;
	private String head_control_2_value;

	private String neuro_head_lag_score;
	private String neuro_head_lag_value;

	private String ventral_suspension_score;
	private String ventral_suspension_value;

	// Tone Patterns items columns
	private String compare_flexor_tone_score;
	private String compare_flexor_tone_value;

	private String resting_flexor_tone_score;
	private String resting_flexor_tone_value;

	private String leg_tone_value;
	private String leg_tone_score;

	private String head_control_sitting_value;
	private String head_control_sitting_score;

	private String neck_axial_tone_value;
	private String neck_axial_tone_score;

	// Reflex items
	private String tendon_reflex_value;
	private String tendon_reflex_score;

	private String suck_gag_value;
	private String suck_gag_score;

	private String palmar_grasp_value;
	private String palmar_grasp_score;

	private String plantar_grasp_score;
	private String plantar_grasp_value;

	private String placing_value;
	private String placing_score;

	private String new_moro_reflex_value;
	private String new_moro_reflex_score;

	// Movements
	private String spontaneous_movement_quantity_value;
	private String spontaneous_movement_quantity_score;

	private String spontaneous_movement_quality_value;
	private String spontaneous_movement_quality_score;

	private String head_raising_score;
	private String head_raising_value;

	// Abnormal Signs
	private String toe_postures_value;
	private String toe_postures_score;

	private String tremor_value;
	private String tremor_score;

	private String startle_value;
	private String startle_score;

	// Behavioural Signs

	private String eye_apperance_value;
	private String eye_apperance_score;

	private String auditory_orientation_value;
	private String auditory_orientation_score;

	private String visual_orientation_value;
	private String visual_orientation_score;

	private String new_alertness_value;
	private String new_alertness_score;

	private String irritability_value;
	private String irritability_score;

	private String consolablility_value;
	private String consolablility_score;

	private String cry_value;
	private String cry_score;

	// HINE Screening Columns
	@Column(name ="facial_apperance_rest_score")
	private String facialAppearanceAtRestScore;

	@Column(name ="facial_apperance_rest_asymmetry",columnDefinition = "bool")
	private boolean facialAppearanceAtRestAsysmmetry;

	@Column(name ="facial_apperance_rest_comments")
	private String facialAppearanceAtRestComments;


	@Column(name ="eye_movement_score")
	private String eyeMovementScore;

	@Column(name ="eye_movement_asymmetry",columnDefinition = "bool")
	private boolean eyeMovementAsysmmetry;

	@Column(name ="eye_movement_comments")
	private String eyeMovementComments;

	@Column(name ="visual_response_test_score")
	private String visualResponseScore;

	@Column(name ="visual_response_test_asymmetry",columnDefinition = "bool")
	private boolean visualResponseAsysmmetry;

	@Column(name ="visual_response_test_comments")
	private String visualResponseComments;

	@Column(name ="auditory_response_test_score")
	private String auditoryResponseScore;

	@Column(name ="auditory_response_test_asymmetry",columnDefinition = "bool")
	private boolean auditoryResponseAsysmmetry;

	@Column(name ="auditory_response_test_comments")
	private String auditoryResponseComments;

	@Column(name ="sucking_swallowing_score")
	private String suckingSwallowingScore;

	@Column(name ="sucking_swallowing_asymmetry",columnDefinition = "bool")
	private boolean suckingSwallowingAsysmmetry;

	@Column(name ="sucking_swallowing_comments")
	private String suckingSwallowingComments;

	// Posture
	@Column(name ="head_posture_score")
	private String headPostureScore;

	@Column(name ="head_posture_asymmetry",columnDefinition = "bool")
	private boolean headPostureAsysmmetry;

	@Column(name ="head_posture_comments")
	private String headPostureComments;


	@Column(name ="trunk_posture_score")
	private String trunkPostureScore;

	@Column(name ="trunk_posture_asymmetry",columnDefinition = "bool")
	private boolean trunkPostureAsysmmetry;

	@Column(name ="trunk_posture_comments")
	private String trunkPostureComments;

	@Column(name ="arms_posture_score")
	private String armsPostureScore;

	@Column(name ="arms_posture_asymmetry",columnDefinition = "bool")
	private boolean armsPostureAsysmmetry;

	@Column(name ="arms_posture_comments")
	private String armsPostureComments;

	@Column(name ="hands_posture_score")
	private String handsPostureScore;

	@Column(name ="hands_posture_asymmetry",columnDefinition = "bool")
	private boolean handsPostureAsysmmetry;

	@Column(name ="hands_posture_comments")
	private String handsPostureComments;

	@Column(name ="legs_posture_score")
	private String legsPostureScore;

	@Column(name ="legs_posture_asymmetry",columnDefinition = "bool")
	private boolean legsPostureAsysmmetry;

	@Column(name ="legs_posture_comments")
	private String legsPostureComments;

	@Column(name ="feets_posture_score")
	private String feetsPostureScore;

	@Column(name ="feets_posture_asymmetry",columnDefinition = "bool")
	private boolean feetsPostureAsysmmetry;

	@Column(name ="feets_posture_comments")
	private String feetsPostureComments;

	// MOVEMENT

	@Column(name ="quantity_movement_score")
	private String quantityMovementScore;

	@Column(name ="quantity_movement_asymmetry",columnDefinition = "bool")
	private boolean quantityMovementAsysmmetry;

	@Column(name ="quantity_movement_comments")
	private String quantityMovementComments;

	@Column(name ="quality_movement_score")
	private String qualityMovementScore;

	@Column(name ="quality_movement_asymmetry",columnDefinition = "bool")
	private boolean qualityMovementAsysmmetry;

	@Column(name ="quality_movement_comments")
	private String qualityMovementComments;

	// TONE

	@Column(name ="Scarfsign_tone_score")
	private String ScarfsignToneScore;

	@Column(name ="Scarfsign_tone_asymmetry",columnDefinition = "bool")
	private boolean ScarfsignToneAsysmmetry;

	@Column(name ="Scarfsign_tone_comments")
	private String ScarfsignToneComments;

	@Column(name ="Passiveshoulder_tone_score")
	private String PassiveshoulderToneScore;

	@Column(name ="Passiveshoulder_tone_asymmetry",columnDefinition = "bool")
	private boolean PassiveshoulderToneAsysmmetry;

	@Column(name ="Passiveshoulder_tone_comments")
	private String PassiveshoulderToneComments;

	@Column(name ="Pronation_tone_score")
	private String PronationToneScore;

	@Column(name ="Pronation_tone_asymmetry",columnDefinition = "bool")
	private boolean PronationToneAsysmmetry;

	@Column(name ="Pronation_tone_comments")
	private String PronationToneComments;

	@Column(name ="Hipadductors_tone_score")
	private String HipadductorsToneScore;

	@Column(name ="Hipadductors_tone_asymmetry",columnDefinition = "bool")
	private boolean HipadductorsToneAsysmmetry;

	@Column(name ="Hipadductors_tone_comments")
	private String HipadductorsToneComments;

	@Column(name ="PoplitealAngle_tone_score")
	private String PoplitealAngleToneScore;

	@Column(name ="PoplitealAngle_tone_asymmetry",columnDefinition = "bool")
	private boolean PoplitealAngleToneAsysmmetry;

	@Column(name ="PoplitealAngle_tone_comments")
	private String PoplitealAngleToneComments;

	@Column(name ="AnkleDorsiflexion_tone_score")
	private String AnkleDorsiflexionToneScore;

	@Column(name ="AnkleDorsiflexion_tone_asymmetry",columnDefinition = "bool")
	private boolean AnkleDorsiflexionToneAsysmmetry;

	@Column(name ="AnkleDorsiflexion_tone_comments")
	private String AnkleDorsiflexionToneComments;

	@Column(name ="PullToSit_tone_score")
	private String PullToSitToneScore;

	@Column(name ="PullToSit_tone_asymmetry",columnDefinition = "bool")
	private boolean PullToSitToneAsysmmetry;

	@Column(name ="PullToSit_tone_comments")
	private String PullToSitToneComments;

	@Column(name ="VentralSuspension_tone_score")
	private String VentralSuspensionToneScore;

	@Column(name ="VentralSuspension_tone_asymmetry",columnDefinition = "bool")
	private boolean VentralSuspensionToneAsysmmetry;

	@Column(name ="VentralSuspension_tone_comments")
	private String VentralSuspensionToneComments;

	// REFLEXES
	@Column(name ="armProtection_reaction_score")
	private String armProtectionReactionScore;

	@Column(name ="armProtection_reaction_asymmetry",columnDefinition = "bool")
	private boolean armProtectionReactionAsysmmetry;

	@Column(name ="armProtection_reaction_comments")
	private String armProtectionReactionComments;

	@Column(name ="verticalSuspension_reaction_score")
	private String verticalSuspensionReactionScore;

	@Column(name ="verticalSuspension_reaction_asymmetry",columnDefinition = "bool")
	private boolean verticalSuspensionReactionAsysmmetry;

	@Column(name ="verticalSuspension_reaction_comments")
	private String verticalSuspensionReactionComments;

	@Column(name ="lateralTilting_reaction_score")
	private String lateralTiltingReactionScore;

	@Column(name ="lateralTilting_reaction_asymmetry",columnDefinition = "bool")
	private boolean lateralTiltingReactionAsysmmetry;

	@Column(name ="lateralTilting_reaction_comments")
	private String lateralTiltingReactionComments;

	@Column(name ="forwardParachute_reaction_score")
	private String forwardParachuteReactionScore;

	@Column(name ="forwardParachute_reaction_asymmetry",columnDefinition = "bool")
	private boolean forwardParachuteReactionAsysmmetry;

	@Column(name ="forwardParachute_reaction_comments")
	private String forwardParachuteReactionComments;

	@Column(name ="tendonReflexes_reaction_score")
	private String tendonReflexesReactionScore;

	@Column(name ="tendonReflexes_reaction_asymmetry",columnDefinition = "bool")
	private boolean tendonReflexesReactionAsysmmetry;

	@Column(name ="tendonReflexes_reaction_comments")
	private String tendonReflexesReactionComments;


	// MOTOR MILESTONE
	@Column(name ="headControl_value")
	private String headControlValue;

	@Column(name ="sitting_value")
	private String sittingValue;

	@Column(name ="sitting_reported_age")
	private String sittingReportedAge;

	@Column(name ="voluntaryGrasp_value")
	private String voluntaryGraspValue;

	@Column(name ="voluntaryGrasp_reported_age")
	private String voluntaryGraspReportedAge;

	@Column(name ="abilitytokick_value")
	private String abilitytokickValue;

	@Column(name ="abilitytokick_reported_age")
	private String abilitytokickReportedAge;

	@Column(name ="crawling_value")
	private String crawlingValue;

	@Column(name ="crawling_reported_age")
	private String crawlingReportedAge;

	@Column(name ="rolling_value")
	private String rollingValue;

	@Column(name ="rolling_reported_age")
	private String rollingReportedAge;

	@Column(name ="standing_value")
	private String standingValue;

	@Column(name ="standing_reported_age")
	private String standingReportedAge;

	@Column(name ="walking_value")
	private String walkingValue;

	@Column(name ="walking_reported_age")
	private String walkingReportedAge;

	// MOTOR MILESTONES

	@Column(name ="consciousState_value")
	private String consciousStateValue;

	@Column(name ="consciousState_comments")
	private String consciousStateComments;

	@Column(name ="emotionalState_value")
	private String emotionalStateValue;

	@Column(name ="emotionalState_comments")
	private String emotionalStateComments;

	@Column(name ="socialOrientation_value")
	private String socialOrientationValue;

	@Column(name ="socialOrientation_comments")
	private String socialOrientationComments;

	@Column(name ="hine_summary")
	private String hineSummary;

	@Column(name = "global_score")
	private String globalScore;

	@Column(name = "no_of_asymmetries")
	private String noOfAsymmetries;

	@Column(name = "behavioural_score")
	private String behaviouralScore;

	@Column(name = "cranial_nerve_score")
	private String cranialNerveScore;

	@Column(name = "total_posture_score")
	private String totalPostureScore;

	@Column(name = "total_movements_score")
	private String totalMovementsScore;

	@Column(name = "total_tone_score")
	private String totalToneScore;

	@Column(name = "total_reflexes_score")
	private String totalReflexesScore;

	@Column(name = "head_circumference")
	private String headCircumference;

	@Column(name ="hc_reading_time")
	private Timestamp hcReadingTime;

	public ScreenNeurological() {
		super();
	}

	public Long getScreen_neurological_id() {
		return screen_neurological_id;
	}

	public void setScreen_neurological_id(Long screen_neurological_id) {
		this.screen_neurological_id = screen_neurological_id;
	}

	public Timestamp getCreationtime() {
		return creationtime;
	}

	public void setCreationtime(Timestamp creationtime) {
		this.creationtime = creationtime;
	}

	public Timestamp getModificationtime() {
		return modificationtime;
	}

	public void setModificationtime(Timestamp modificationtime) {
		this.modificationtime = modificationtime;
	}

	public String getUhid() {
		return uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getEpisodeid() {
		return episodeid;
	}

	public void setEpisodeid(String episodeid) {
		this.episodeid = episodeid;
	}

	public String getLoggeduser() {
		return loggeduser;
	}

	public void setLoggeduser(String loggeduser) {
		this.loggeduser = loggeduser;
	}

	public Timestamp getScreening_time() {
		return screening_time;
	}

	public void setScreening_time(Timestamp screening_time) {
		this.screening_time = screening_time;
	}

	public Integer getCga_weeks() {
		return cga_weeks;
	}

	public void setCga_weeks(Integer cga_weeks) {
		this.cga_weeks = cga_weeks;
	}

	public Integer getCga_days() {
		return cga_days;
	}

	public void setCga_days(Integer cga_days) {
		this.cga_days = cga_days;
	}

	public Integer getPna_weeks() {
		return pna_weeks;
	}

	public void setPna_weeks(Integer pna_weeks) {
		this.pna_weeks = pna_weeks;
	}

	public Integer getPna_days() {
		return pna_days;
	}

	public void setPna_days(Integer pna_days) {
		this.pna_days = pna_days;
	}

	public String getPosture_type() {
		return posture_type;
	}

	public void setPosture_type(String posture_type) {
		this.posture_type = posture_type;
	}

	public String getPosture_value() {
		return posture_value;
	}

	public void setPosture_value(String posture_value) {
		this.posture_value = posture_value;
	}

	public String getArm_traction_type() {
		return arm_traction_type;
	}

	public void setArm_traction_type(String arm_traction_type) {
		this.arm_traction_type = arm_traction_type;
	}

	public String getArm_traction_value() {
		return arm_traction_value;
	}

	public void setArm_traction_value(String arm_traction_value) {
		this.arm_traction_value = arm_traction_value;
	}

	public String getLeg_traction_type() {
		return leg_traction_type;
	}

	public void setLeg_traction_type(String leg_traction_type) {
		this.leg_traction_type = leg_traction_type;
	}

	public String getLeg_traction_value() {
		return leg_traction_value;
	}

	public void setLeg_traction_value(String leg_traction_value) {
		this.leg_traction_value = leg_traction_value;
	}

	public String getHead_extensor_type() {
		return head_extensor_type;
	}

	public void setHead_extensor_type(String head_extensor_type) {
		this.head_extensor_type = head_extensor_type;
	}

	public String getHead_extensor_value() {
		return head_extensor_value;
	}

	public void setHead_extensor_value(String head_extensor_value) {
		this.head_extensor_value = head_extensor_value;
	}

	public String getHead_flexor_type() {
		return head_flexor_type;
	}

	public void setHead_flexor_type(String head_flexor_type) {
		this.head_flexor_type = head_flexor_type;
	}

	public String getHead_flexor_value() {
		return head_flexor_value;
	}

	public void setHead_flexor_value(String head_flexor_value) {
		this.head_flexor_value = head_flexor_value;
	}

	public String getHead_lag_type() {
		return head_lag_type;
	}

	public void setHead_lag_type(String head_lag_type) {
		this.head_lag_type = head_lag_type;
	}

	public String getHead_lag_value() {
		return head_lag_value;
	}

	public void setHead_lag_value(String head_lag_value) {
		this.head_lag_value = head_lag_value;
	}

	public String getVentral_type() {
		return ventral_type;
	}

	public void setVentral_type(String ventral_type) {
		this.ventral_type = ventral_type;
	}

	public String getVentral_value() {
		return ventral_value;
	}

	public void setVentral_value(String ventral_value) {
		this.ventral_value = ventral_value;
	}

	public String getBody_type() {
		return body_type;
	}

	public void setBody_type(String body_type) {
		this.body_type = body_type;
	}

	public String getBody_value() {
		return body_value;
	}

	public void setBody_value(String body_value) {
		this.body_value = body_value;
	}

	public String getTremor_startles_type() {
		return tremor_startles_type;
	}

	public void setTremor_startles_type(String tremor_startles_type) {
		this.tremor_startles_type = tremor_startles_type;
	}

	public String getTremor_startles_value() {
		return tremor_startles_value;
	}

	public void setTremor_startles_value(String tremor_startles_value) {
		this.tremor_startles_value = tremor_startles_value;
	}

	public String getMoro_reflex_type() {
		return moro_reflex_type;
	}

	public void setMoro_reflex_type(String moro_reflex_type) {
		this.moro_reflex_type = moro_reflex_type;
	}

	public String getMoro_reflex_value() {
		return moro_reflex_value;
	}

	public void setMoro_reflex_value(String moro_reflex_value) {
		this.moro_reflex_value = moro_reflex_value;
	}

	public String getAuditory_type() {
		return auditory_type;
	}

	public void setAuditory_type(String auditory_type) {
		this.auditory_type = auditory_type;
	}

	public String getAuditory_value() {
		return auditory_value;
	}

	public void setAuditory_value(String auditory_value) {
		this.auditory_value = auditory_value;
	}

	public String getVisual_type() {
		return visual_type;
	}

	public void setVisual_type(String visual_type) {
		this.visual_type = visual_type;
	}

	public String getVisual_value() {
		return visual_value;
	}

	public void setVisual_value(String visual_value) {
		this.visual_value = visual_value;
	}

	public String getAlertness_type() {
		return alertness_type;
	}

	public void setAlertness_type(String alertness_type) {
		this.alertness_type = alertness_type;
	}

	public String getAlertness_value() {
		return alertness_value;
	}

	public void setAlertness_value(String alertness_value) {
		this.alertness_value = alertness_value;
	}

	public Boolean getSuck() {
		return suck;
	}

	public void setSuck(Boolean suck) {
		this.suck = suck;
	}

	public Boolean getFacial() {
		return facial;
	}

	public void setFacial(Boolean facial) {
		this.facial = facial;
	}

	public Boolean getEye() {
		return eye;
	}

	public void setEye(Boolean eye) {
		this.eye = eye;
	}

	public Boolean getSunset() {
		return sunset;
	}

	public void setSunset(Boolean sunset) {
		this.sunset = sunset;
	}

	public Boolean getHand() {
		return hand;
	}

	public void setHand(Boolean hand) {
		this.hand = hand;
	}

	public Boolean getClonus() {
		return clonus;
	}

	public void setClonus(Boolean clonus) {
		this.clonus = clonus;
	}

	public String getReports() {
		return reports;
	}

	public void setReports(String reports) {
		this.reports = reports;
	}

	public String getOther_comments() {
		return other_comments;
	}

	public void setOther_comments(String other_comments) {
		this.other_comments = other_comments;
	}

	public String getScreening_message() {
		return screening_message;
	}

	public void setScreening_message(String screening_message) {
		this.screening_message = screening_message;
	}

	public String getScreening_type() {
		return screening_type;
	}

	public void setScreening_type(String screening_type) {
		this.screening_type = screening_type;
	}

	public String getNeuro_posture_score() {
		return neuro_posture_score;
	}

	public void setNeuro_posture_score(String neuro_posture_score) {
		this.neuro_posture_score = neuro_posture_score;
	}

	public String getNeuro_posture_value() {
		return neuro_posture_value;
	}

	public void setNeuro_posture_value(String neuro_posture_value) {
		this.neuro_posture_value = neuro_posture_value;
	}

	public String getNeuro_arm_recoil_score() {
		return neuro_arm_recoil_score;
	}

	public void setNeuro_arm_recoil_score(String neuro_arm_recoil_score) {
		this.neuro_arm_recoil_score = neuro_arm_recoil_score;
	}

	public String getNeuro_arm_recoil_value() {
		return neuro_arm_recoil_value;
	}

	public void setNeuro_arm_recoil_value(String neuro_arm_recoil_value) {
		this.neuro_arm_recoil_value = neuro_arm_recoil_value;
	}

	public String getNeuro_arm_traction_score() {
		return neuro_arm_traction_score;
	}

	public void setNeuro_arm_traction_score(String neuro_arm_traction_score) {
		this.neuro_arm_traction_score = neuro_arm_traction_score;
	}

	public String getNeuro_arm_traction_value() {
		return neuro_arm_traction_value;
	}

	public void setNeuro_arm_traction_value(String neuro_arm_traction_value) {
		this.neuro_arm_traction_value = neuro_arm_traction_value;
	}

	public String getNeuro_leg_traction_score() {
		return neuro_leg_traction_score;
	}

	public void setNeuro_leg_traction_score(String neuro_leg_traction_score) {
		this.neuro_leg_traction_score = neuro_leg_traction_score;
	}

	public String getNeuro_leg_traction_value() {
		return neuro_leg_traction_value;
	}

	public void setNeuro_leg_traction_value(String neuro_leg_traction_value) {
		this.neuro_leg_traction_value = neuro_leg_traction_value;
	}

	public String getNeuro_leg_recoil_score() {
		return neuro_leg_recoil_score;
	}

	public void setNeuro_leg_recoil_score(String neuro_leg_recoil_score) {
		this.neuro_leg_recoil_score = neuro_leg_recoil_score;
	}

	public String getNeuro_leg_recoil_value() {
		return neuro_leg_recoil_value;
	}

	public void setNeuro_leg_recoil_value(String neuro_leg_recoil_value) {
		this.neuro_leg_recoil_value = neuro_leg_recoil_value;
	}

	public String getPopliteal_angle_value() {
		return popliteal_angle_value;
	}

	public void setPopliteal_angle_value(String popliteal_angle_value) {
		this.popliteal_angle_value = popliteal_angle_value;
	}

	public String getPopliteal_angle_score() {
		return popliteal_angle_score;
	}

	public void setPopliteal_angle_score(String popliteal_angle_score) {
		this.popliteal_angle_score = popliteal_angle_score;
	}

	public String getHead_control_1_score() {
		return head_control_1_score;
	}

	public void setHead_control_1_score(String head_control_1_score) {
		this.head_control_1_score = head_control_1_score;
	}

	public String getHead_control_1_value() {
		return head_control_1_value;
	}

	public void setHead_control_1_value(String head_control_1_value) {
		this.head_control_1_value = head_control_1_value;
	}

	public String getHead_control_2_score() {
		return head_control_2_score;
	}

	public void setHead_control_2_score(String head_control_2_score) {
		this.head_control_2_score = head_control_2_score;
	}

	public String getHead_control_2_value() {
		return head_control_2_value;
	}

	public void setHead_control_2_value(String head_control_2_value) {
		this.head_control_2_value = head_control_2_value;
	}

	public String getNeuro_head_lag_score() {
		return neuro_head_lag_score;
	}

	public void setNeuro_head_lag_score(String neuro_head_lag_score) {
		this.neuro_head_lag_score = neuro_head_lag_score;
	}

	public String getNeuro_head_lag_value() {
		return neuro_head_lag_value;
	}

	public void setNeuro_head_lag_value(String neuro_head_lag_value) {
		this.neuro_head_lag_value = neuro_head_lag_value;
	}

	public String getVentral_suspension_score() {
		return ventral_suspension_score;
	}

	public void setVentral_suspension_score(String ventral_suspension_score) {
		this.ventral_suspension_score = ventral_suspension_score;
	}

	public String getVentral_suspension_value() {
		return ventral_suspension_value;
	}

	public void setVentral_suspension_value(String ventral_suspension_value) {
		this.ventral_suspension_value = ventral_suspension_value;
	}

	public String getCompare_flexor_tone_score() {
		return compare_flexor_tone_score;
	}

	public void setCompare_flexor_tone_score(String compare_flexor_tone_score) {
		this.compare_flexor_tone_score = compare_flexor_tone_score;
	}

	public String getCompare_flexor_tone_value() {
		return compare_flexor_tone_value;
	}

	public void setCompare_flexor_tone_value(String compare_flexor_tone_value) {
		this.compare_flexor_tone_value = compare_flexor_tone_value;
	}

	public String getResting_flexor_tone_score() {
		return resting_flexor_tone_score;
	}

	public void setResting_flexor_tone_score(String resting_flexor_tone_score) {
		this.resting_flexor_tone_score = resting_flexor_tone_score;
	}

	public String getResting_flexor_tone_value() {
		return resting_flexor_tone_value;
	}

	public void setResting_flexor_tone_value(String resting_flexor_tone_value) {
		this.resting_flexor_tone_value = resting_flexor_tone_value;
	}

	public String getLeg_tone_value() {
		return leg_tone_value;
	}

	public void setLeg_tone_value(String leg_tone_value) {
		this.leg_tone_value = leg_tone_value;
	}

	public String getLeg_tone_score() {
		return leg_tone_score;
	}

	public void setLeg_tone_score(String leg_tone_score) {
		this.leg_tone_score = leg_tone_score;
	}

	public String getHead_control_sitting_value() {
		return head_control_sitting_value;
	}

	public void setHead_control_sitting_value(String head_control_sitting_value) {
		this.head_control_sitting_value = head_control_sitting_value;
	}

	public String getHead_control_sitting_score() {
		return head_control_sitting_score;
	}

	public void setHead_control_sitting_score(String head_control_sitting_score) {
		this.head_control_sitting_score = head_control_sitting_score;
	}

	public String getTendon_reflex_value() {
		return tendon_reflex_value;
	}

	public void setTendon_reflex_value(String tendon_reflex_value) {
		this.tendon_reflex_value = tendon_reflex_value;
	}

	public String getTendon_reflex_score() {
		return tendon_reflex_score;
	}

	public void setTendon_reflex_score(String tendon_reflex_score) {
		this.tendon_reflex_score = tendon_reflex_score;
	}

	public String getSuck_gag_value() {
		return suck_gag_value;
	}

	public void setSuck_gag_value(String suck_gag_value) {
		this.suck_gag_value = suck_gag_value;
	}

	public String getSuck_gag_score() {
		return suck_gag_score;
	}

	public void setSuck_gag_score(String suck_gag_score) {
		this.suck_gag_score = suck_gag_score;
	}

	public String getPalmar_grasp_value() {
		return palmar_grasp_value;
	}

	public void setPalmar_grasp_value(String palmar_grasp_value) {
		this.palmar_grasp_value = palmar_grasp_value;
	}

	public String getPalmar_grasp_score() {
		return palmar_grasp_score;
	}

	public void setPalmar_grasp_score(String palmar_grasp_score) {
		this.palmar_grasp_score = palmar_grasp_score;
	}

	public String getPlantar_grasp_score() {
		return plantar_grasp_score;
	}

	public void setPlantar_grasp_score(String plantar_grasp_score) {
		this.plantar_grasp_score = plantar_grasp_score;
	}

	public String getPlantar_grasp_value() {
		return plantar_grasp_value;
	}

	public void setPlantar_grasp_value(String plantar_grasp_value) {
		this.plantar_grasp_value = plantar_grasp_value;
	}

	public String getPlacing_value() {
		return placing_value;
	}

	public void setPlacing_value(String placing_value) {
		this.placing_value = placing_value;
	}

	public String getPlacing_score() {
		return placing_score;
	}

	public void setPlacing_score(String placing_score) {
		this.placing_score = placing_score;
	}

	public String getNew_moro_reflex_value() {
		return new_moro_reflex_value;
	}

	public void setNew_moro_reflex_value(String new_moro_reflex_value) {
		this.new_moro_reflex_value = new_moro_reflex_value;
	}

	public String getNew_moro_reflex_score() {
		return new_moro_reflex_score;
	}

	public void setNew_moro_reflex_score(String new_moro_reflex_score) {
		this.new_moro_reflex_score = new_moro_reflex_score;
	}

	public String getSpontaneous_movement_quantity_value() {
		return spontaneous_movement_quantity_value;
	}

	public void setSpontaneous_movement_quantity_value(String spontaneous_movement_quantity_value) {
		this.spontaneous_movement_quantity_value = spontaneous_movement_quantity_value;
	}

	public String getSpontaneous_movement_quantity_score() {
		return spontaneous_movement_quantity_score;
	}

	public void setSpontaneous_movement_quantity_score(String spontaneous_movement_quantity_score) {
		this.spontaneous_movement_quantity_score = spontaneous_movement_quantity_score;
	}

	public String getSpontaneous_movement_quality_value() {
		return spontaneous_movement_quality_value;
	}

	public void setSpontaneous_movement_quality_value(String spontaneous_movement_quality_value) {
		this.spontaneous_movement_quality_value = spontaneous_movement_quality_value;
	}

	public String getSpontaneous_movement_quality_score() {
		return spontaneous_movement_quality_score;
	}

	public void setSpontaneous_movement_quality_score(String spontaneous_movement_quality_score) {
		this.spontaneous_movement_quality_score = spontaneous_movement_quality_score;
	}

	public String getHead_raising_score() {
		return head_raising_score;
	}

	public void setHead_raising_score(String head_raising_score) {
		this.head_raising_score = head_raising_score;
	}

	public String getHead_raising_value() {
		return head_raising_value;
	}

	public void setHead_raising_value(String head_raising_value) {
		this.head_raising_value = head_raising_value;
	}

	public String getToe_postures_value() {
		return toe_postures_value;
	}

	public void setToe_postures_value(String toe_postures_value) {
		this.toe_postures_value = toe_postures_value;
	}

	public String getToe_postures_score() {
		return toe_postures_score;
	}

	public void setToe_postures_score(String toe_postures_score) {
		this.toe_postures_score = toe_postures_score;
	}

	public String getTremor_value() {
		return tremor_value;
	}

	public void setTremor_value(String tremor_value) {
		this.tremor_value = tremor_value;
	}

	public String getTremor_score() {
		return tremor_score;
	}

	public void setTremor_score(String tremor_score) {
		this.tremor_score = tremor_score;
	}

	public String getStartle_value() {
		return startle_value;
	}

	public void setStartle_value(String startle_value) {
		this.startle_value = startle_value;
	}

	public String getStartle_score() {
		return startle_score;
	}

	public void setStartle_score(String startle_score) {
		this.startle_score = startle_score;
	}

	public String getEye_apperance_value() {
		return eye_apperance_value;
	}

	public void setEye_apperance_value(String eye_apperance_value) {
		this.eye_apperance_value = eye_apperance_value;
	}

	public String getEye_apperance_score() {
		return eye_apperance_score;
	}

	public void setEye_apperance_score(String eye_apperance_score) {
		this.eye_apperance_score = eye_apperance_score;
	}

	public String getNeck_axial_tone_value() {
		return neck_axial_tone_value;
	}

	public void setNeck_axial_tone_value(String neck_axial_tone_value) {
		this.neck_axial_tone_value = neck_axial_tone_value;
	}

	public String getNeck_axial_tone_score() {
		return neck_axial_tone_score;
	}

	public void setNeck_axial_tone_score(String neck_axial_tone_score) {
		this.neck_axial_tone_score = neck_axial_tone_score;
	}

	public String getAuditory_orientation_value() {
		return auditory_orientation_value;
	}

	public void setAuditory_orientation_value(String auditory_orientation_value) {
		this.auditory_orientation_value = auditory_orientation_value;
	}

	public String getAuditory_orientation_score() {
		return auditory_orientation_score;
	}

	public void setAuditory_orientation_score(String auditory_orientation_score) {
		this.auditory_orientation_score = auditory_orientation_score;
	}

	public String getVisual_orientation_value() {
		return visual_orientation_value;
	}

	public void setVisual_orientation_value(String visual_orientation_value) {
		this.visual_orientation_value = visual_orientation_value;
	}

	public String getVisual_orientation_score() {
		return visual_orientation_score;
	}

	public void setVisual_orientation_score(String visual_orientation_score) {
		this.visual_orientation_score = visual_orientation_score;
	}

	public String getNew_alertness_value() {
		return new_alertness_value;
	}

	public void setNew_alertness_value(String new_alertness_value) {
		this.new_alertness_value = new_alertness_value;
	}

	public String getNew_alertness_score() {
		return new_alertness_score;
	}

	public void setNew_alertness_score(String new_alertness_score) {
		this.new_alertness_score = new_alertness_score;
	}

	public String getIrritability_value() {
		return irritability_value;
	}

	public void setIrritability_value(String irritability_value) {
		this.irritability_value = irritability_value;
	}

	public String getIrritability_score() {
		return irritability_score;
	}

	public void setIrritability_score(String irritability_score) {
		this.irritability_score = irritability_score;
	}

	public String getConsolablility_value() {
		return consolablility_value;
	}

	public void setConsolablility_value(String consolablility_value) {
		this.consolablility_value = consolablility_value;
	}

	public String getConsolablility_score() {
		return consolablility_score;
	}

	public void setConsolablility_score(String consolablility_score) {
		this.consolablility_score = consolablility_score;
	}

	public String getCry_value() {
		return cry_value;
	}

	public void setCry_value(String cry_value) {
		this.cry_value = cry_value;
	}

	public String getCry_score() {
		return cry_score;
	}

	public void setCry_score(String cry_score) {
		this.cry_score = cry_score;
	}

	public String getFacialAppearanceAtRestScore() {
		return facialAppearanceAtRestScore;
	}

	public void setFacialAppearanceAtRestScore(String facialAppearanceAtRestScore) {
		this.facialAppearanceAtRestScore = facialAppearanceAtRestScore;
	}

	public boolean isFacialAppearanceAtRestAsysmmetry() {
		return facialAppearanceAtRestAsysmmetry;
	}

	public void setFacialAppearanceAtRestAsysmmetry(boolean facialAppearanceAtRestAsysmmetry) {
		this.facialAppearanceAtRestAsysmmetry = facialAppearanceAtRestAsysmmetry;
	}

	public String getFacialAppearanceAtRestComments() {
		return facialAppearanceAtRestComments;
	}

	public void setFacialAppearanceAtRestComments(String facialAppearanceAtRestComments) {
		this.facialAppearanceAtRestComments = facialAppearanceAtRestComments;
	}

	public String getEyeMovementScore() {
		return eyeMovementScore;
	}

	public void setEyeMovementScore(String eyeMovementScore) {
		this.eyeMovementScore = eyeMovementScore;
	}

	public boolean isEyeMovementAsysmmetry() {
		return eyeMovementAsysmmetry;
	}

	public void setEyeMovementAsysmmetry(boolean eyeMovementAsysmmetry) {
		this.eyeMovementAsysmmetry = eyeMovementAsysmmetry;
	}

	public String getEyeMovementComments() {
		return eyeMovementComments;
	}

	public void setEyeMovementComments(String eyeMovementComments) {
		this.eyeMovementComments = eyeMovementComments;
	}

	public String getVisualResponseScore() {
		return visualResponseScore;
	}

	public void setVisualResponseScore(String visualResponseScore) {
		this.visualResponseScore = visualResponseScore;
	}

	public boolean isVisualResponseAsysmmetry() {
		return visualResponseAsysmmetry;
	}

	public void setVisualResponseAsysmmetry(boolean visualResponseAsysmmetry) {
		this.visualResponseAsysmmetry = visualResponseAsysmmetry;
	}

	public String getVisualResponseComments() {
		return visualResponseComments;
	}

	public void setVisualResponseComments(String visualResponseComments) {
		this.visualResponseComments = visualResponseComments;
	}

	public String getAuditoryResponseScore() {
		return auditoryResponseScore;
	}

	public void setAuditoryResponseScore(String auditoryResponseScore) {
		this.auditoryResponseScore = auditoryResponseScore;
	}

	public boolean isAuditoryResponseAsysmmetry() {
		return auditoryResponseAsysmmetry;
	}

	public void setAuditoryResponseAsysmmetry(boolean auditoryResponseAsysmmetry) {
		this.auditoryResponseAsysmmetry = auditoryResponseAsysmmetry;
	}

	public String getAuditoryResponseComments() {
		return auditoryResponseComments;
	}

	public void setAuditoryResponseComments(String auditoryResponseComments) {
		this.auditoryResponseComments = auditoryResponseComments;
	}

	public String getSuckingSwallowingScore() {
		return suckingSwallowingScore;
	}

	public void setSuckingSwallowingScore(String suckingSwallowingScore) {
		this.suckingSwallowingScore = suckingSwallowingScore;
	}

	public boolean isSuckingSwallowingAsysmmetry() {
		return suckingSwallowingAsysmmetry;
	}

	public void setSuckingSwallowingAsysmmetry(boolean suckingSwallowingAsysmmetry) {
		this.suckingSwallowingAsysmmetry = suckingSwallowingAsysmmetry;
	}

	public String getSuckingSwallowingComments() {
		return suckingSwallowingComments;
	}

	public void setSuckingSwallowingComments(String suckingSwallowingComments) {
		this.suckingSwallowingComments = suckingSwallowingComments;
	}

	public String getHeadPostureScore() {
		return headPostureScore;
	}

	public void setHeadPostureScore(String headPostureScore) {
		this.headPostureScore = headPostureScore;
	}

	public boolean isHeadPostureAsysmmetry() {
		return headPostureAsysmmetry;
	}

	public void setHeadPostureAsysmmetry(boolean headPostureAsysmmetry) {
		this.headPostureAsysmmetry = headPostureAsysmmetry;
	}

	public String getHeadPostureComments() {
		return headPostureComments;
	}

	public void setHeadPostureComments(String headPostureComments) {
		this.headPostureComments = headPostureComments;
	}

	public String getTrunkPostureScore() {
		return trunkPostureScore;
	}

	public void setTrunkPostureScore(String trunkPostureScore) {
		this.trunkPostureScore = trunkPostureScore;
	}

	public boolean isTrunkPostureAsysmmetry() {
		return trunkPostureAsysmmetry;
	}

	public void setTrunkPostureAsysmmetry(boolean trunkPostureAsysmmetry) {
		this.trunkPostureAsysmmetry = trunkPostureAsysmmetry;
	}

	public String getTrunkPostureComments() {
		return trunkPostureComments;
	}

	public void setTrunkPostureComments(String trunkPostureComments) {
		this.trunkPostureComments = trunkPostureComments;
	}

	public String getArmsPostureScore() {
		return armsPostureScore;
	}

	public void setArmsPostureScore(String armsPostureScore) {
		this.armsPostureScore = armsPostureScore;
	}

	public boolean isArmsPostureAsysmmetry() {
		return armsPostureAsysmmetry;
	}

	public void setArmsPostureAsysmmetry(boolean armsPostureAsysmmetry) {
		this.armsPostureAsysmmetry = armsPostureAsysmmetry;
	}

	public String getArmsPostureComments() {
		return armsPostureComments;
	}

	public void setArmsPostureComments(String armsPostureComments) {
		this.armsPostureComments = armsPostureComments;
	}

	public String getHandsPostureScore() {
		return handsPostureScore;
	}

	public void setHandsPostureScore(String handsPostureScore) {
		this.handsPostureScore = handsPostureScore;
	}

	public boolean isHandsPostureAsysmmetry() {
		return handsPostureAsysmmetry;
	}

	public void setHandsPostureAsysmmetry(boolean handsPostureAsysmmetry) {
		this.handsPostureAsysmmetry = handsPostureAsysmmetry;
	}

	public String getHandsPostureComments() {
		return handsPostureComments;
	}

	public void setHandsPostureComments(String handsPostureComments) {
		this.handsPostureComments = handsPostureComments;
	}

	public String getLegsPostureScore() {
		return legsPostureScore;
	}

	public void setLegsPostureScore(String legsPostureScore) {
		this.legsPostureScore = legsPostureScore;
	}

	public boolean isLegsPostureAsysmmetry() {
		return legsPostureAsysmmetry;
	}

	public void setLegsPostureAsysmmetry(boolean legsPostureAsysmmetry) {
		this.legsPostureAsysmmetry = legsPostureAsysmmetry;
	}

	public String getLegsPostureComments() {
		return legsPostureComments;
	}

	public void setLegsPostureComments(String legsPostureComments) {
		this.legsPostureComments = legsPostureComments;
	}

	public String getFeetsPostureScore() {
		return feetsPostureScore;
	}

	public void setFeetsPostureScore(String feetsPostureScore) {
		this.feetsPostureScore = feetsPostureScore;
	}

	public boolean isFeetsPostureAsysmmetry() {
		return feetsPostureAsysmmetry;
	}

	public void setFeetsPostureAsysmmetry(boolean feetsPostureAsysmmetry) {
		this.feetsPostureAsysmmetry = feetsPostureAsysmmetry;
	}

	public String getFeetsPostureComments() {
		return feetsPostureComments;
	}

	public void setFeetsPostureComments(String feetsPostureComments) {
		this.feetsPostureComments = feetsPostureComments;
	}

	public String getQuantityMovementScore() {
		return quantityMovementScore;
	}

	public void setQuantityMovementScore(String quantityMovementScore) {
		this.quantityMovementScore = quantityMovementScore;
	}

	public boolean isQuantityMovementAsysmmetry() {
		return quantityMovementAsysmmetry;
	}

	public void setQuantityMovementAsysmmetry(boolean quantityMovementAsysmmetry) {
		this.quantityMovementAsysmmetry = quantityMovementAsysmmetry;
	}

	public String getQuantityMovementComments() {
		return quantityMovementComments;
	}

	public void setQuantityMovementComments(String quantityMovementComments) {
		this.quantityMovementComments = quantityMovementComments;
	}

	public String getQualityMovementScore() {
		return qualityMovementScore;
	}

	public void setQualityMovementScore(String qualityMovementScore) {
		this.qualityMovementScore = qualityMovementScore;
	}

	public boolean isQualityMovementAsysmmetry() {
		return qualityMovementAsysmmetry;
	}

	public void setQualityMovementAsysmmetry(boolean qualityMovementAsysmmetry) {
		this.qualityMovementAsysmmetry = qualityMovementAsysmmetry;
	}

	public String getQualityMovementComments() {
		return qualityMovementComments;
	}

	public void setQualityMovementComments(String qualityMovementComments) {
		this.qualityMovementComments = qualityMovementComments;
	}

	public String getScarfsignToneScore() {
		return ScarfsignToneScore;
	}

	public void setScarfsignToneScore(String scarfsignToneScore) {
		ScarfsignToneScore = scarfsignToneScore;
	}

	public boolean isScarfsignToneAsysmmetry() {
		return ScarfsignToneAsysmmetry;
	}

	public void setScarfsignToneAsysmmetry(boolean scarfsignToneAsysmmetry) {
		ScarfsignToneAsysmmetry = scarfsignToneAsysmmetry;
	}

	public String getScarfsignToneComments() {
		return ScarfsignToneComments;
	}

	public void setScarfsignToneComments(String scarfsignToneComments) {
		ScarfsignToneComments = scarfsignToneComments;
	}

	public String getPassiveshoulderToneScore() {
		return PassiveshoulderToneScore;
	}

	public void setPassiveshoulderToneScore(String passiveshoulderToneScore) {
		PassiveshoulderToneScore = passiveshoulderToneScore;
	}

	public boolean isPassiveshoulderToneAsysmmetry() {
		return PassiveshoulderToneAsysmmetry;
	}

	public void setPassiveshoulderToneAsysmmetry(boolean passiveshoulderToneAsysmmetry) {
		PassiveshoulderToneAsysmmetry = passiveshoulderToneAsysmmetry;
	}

	public String getPassiveshoulderToneComments() {
		return PassiveshoulderToneComments;
	}

	public void setPassiveshoulderToneComments(String passiveshoulderToneComments) {
		PassiveshoulderToneComments = passiveshoulderToneComments;
	}

	public String getPronationToneScore() {
		return PronationToneScore;
	}

	public void setPronationToneScore(String pronationToneScore) {
		PronationToneScore = pronationToneScore;
	}

	public boolean isPronationToneAsysmmetry() {
		return PronationToneAsysmmetry;
	}

	public void setPronationToneAsysmmetry(boolean pronationToneAsysmmetry) {
		PronationToneAsysmmetry = pronationToneAsysmmetry;
	}

	public String getPronationToneComments() {
		return PronationToneComments;
	}

	public void setPronationToneComments(String pronationToneComments) {
		PronationToneComments = pronationToneComments;
	}

	public String getHipadductorsToneScore() {
		return HipadductorsToneScore;
	}

	public void setHipadductorsToneScore(String hipadductorsToneScore) {
		HipadductorsToneScore = hipadductorsToneScore;
	}

	public boolean isHipadductorsToneAsysmmetry() {
		return HipadductorsToneAsysmmetry;
	}

	public void setHipadductorsToneAsysmmetry(boolean hipadductorsToneAsysmmetry) {
		HipadductorsToneAsysmmetry = hipadductorsToneAsysmmetry;
	}

	public String getHipadductorsToneComments() {
		return HipadductorsToneComments;
	}

	public void setHipadductorsToneComments(String hipadductorsToneComments) {
		HipadductorsToneComments = hipadductorsToneComments;
	}

	public String getPoplitealAngleToneScore() {
		return PoplitealAngleToneScore;
	}

	public void setPoplitealAngleToneScore(String poplitealAngleToneScore) {
		PoplitealAngleToneScore = poplitealAngleToneScore;
	}

	public boolean isPoplitealAngleToneAsysmmetry() {
		return PoplitealAngleToneAsysmmetry;
	}

	public void setPoplitealAngleToneAsysmmetry(boolean poplitealAngleToneAsysmmetry) {
		PoplitealAngleToneAsysmmetry = poplitealAngleToneAsysmmetry;
	}

	public String getPoplitealAngleToneComments() {
		return PoplitealAngleToneComments;
	}

	public void setPoplitealAngleToneComments(String poplitealAngleToneComments) {
		PoplitealAngleToneComments = poplitealAngleToneComments;
	}

	public String getAnkleDorsiflexionToneScore() {
		return AnkleDorsiflexionToneScore;
	}

	public void setAnkleDorsiflexionToneScore(String ankleDorsiflexionToneScore) {
		AnkleDorsiflexionToneScore = ankleDorsiflexionToneScore;
	}

	public boolean isAnkleDorsiflexionToneAsysmmetry() {
		return AnkleDorsiflexionToneAsysmmetry;
	}

	public void setAnkleDorsiflexionToneAsysmmetry(boolean ankleDorsiflexionToneAsysmmetry) {
		AnkleDorsiflexionToneAsysmmetry = ankleDorsiflexionToneAsysmmetry;
	}

	public String getAnkleDorsiflexionToneComments() {
		return AnkleDorsiflexionToneComments;
	}

	public void setAnkleDorsiflexionToneComments(String ankleDorsiflexionToneComments) {
		AnkleDorsiflexionToneComments = ankleDorsiflexionToneComments;
	}

	public String getPullToSitToneScore() {
		return PullToSitToneScore;
	}

	public void setPullToSitToneScore(String pullToSitToneScore) {
		PullToSitToneScore = pullToSitToneScore;
	}

	public boolean isPullToSitToneAsysmmetry() {
		return PullToSitToneAsysmmetry;
	}

	public void setPullToSitToneAsysmmetry(boolean pullToSitToneAsysmmetry) {
		PullToSitToneAsysmmetry = pullToSitToneAsysmmetry;
	}

	public String getPullToSitToneComments() {
		return PullToSitToneComments;
	}

	public void setPullToSitToneComments(String pullToSitToneComments) {
		PullToSitToneComments = pullToSitToneComments;
	}

	public String getVentralSuspensionToneScore() {
		return VentralSuspensionToneScore;
	}

	public void setVentralSuspensionToneScore(String ventralSuspensionToneScore) {
		VentralSuspensionToneScore = ventralSuspensionToneScore;
	}

	public boolean isVentralSuspensionToneAsysmmetry() {
		return VentralSuspensionToneAsysmmetry;
	}

	public void setVentralSuspensionToneAsysmmetry(boolean ventralSuspensionToneAsysmmetry) {
		VentralSuspensionToneAsysmmetry = ventralSuspensionToneAsysmmetry;
	}

	public String getVentralSuspensionToneComments() {
		return VentralSuspensionToneComments;
	}

	public void setVentralSuspensionToneComments(String ventralSuspensionToneComments) {
		VentralSuspensionToneComments = ventralSuspensionToneComments;
	}

	public String getArmProtectionReactionScore() {
		return armProtectionReactionScore;
	}

	public void setArmProtectionReactionScore(String armProtectionReactionScore) {
		this.armProtectionReactionScore = armProtectionReactionScore;
	}

	public boolean isArmProtectionReactionAsysmmetry() {
		return armProtectionReactionAsysmmetry;
	}

	public void setArmProtectionReactionAsysmmetry(boolean armProtectionReactionAsysmmetry) {
		this.armProtectionReactionAsysmmetry = armProtectionReactionAsysmmetry;
	}

	public String getArmProtectionReactionComments() {
		return armProtectionReactionComments;
	}

	public void setArmProtectionReactionComments(String armProtectionReactionComments) {
		this.armProtectionReactionComments = armProtectionReactionComments;
	}

	public String getVerticalSuspensionReactionScore() {
		return verticalSuspensionReactionScore;
	}

	public void setVerticalSuspensionReactionScore(String verticalSuspensionReactionScore) {
		this.verticalSuspensionReactionScore = verticalSuspensionReactionScore;
	}

	public boolean isVerticalSuspensionReactionAsysmmetry() {
		return verticalSuspensionReactionAsysmmetry;
	}

	public void setVerticalSuspensionReactionAsysmmetry(boolean verticalSuspensionReactionAsysmmetry) {
		this.verticalSuspensionReactionAsysmmetry = verticalSuspensionReactionAsysmmetry;
	}

	public String getVerticalSuspensionReactionComments() {
		return verticalSuspensionReactionComments;
	}

	public void setVerticalSuspensionReactionComments(String verticalSuspensionReactionComments) {
		this.verticalSuspensionReactionComments = verticalSuspensionReactionComments;
	}

	public String getLateralTiltingReactionScore() {
		return lateralTiltingReactionScore;
	}

	public void setLateralTiltingReactionScore(String lateralTiltingReactionScore) {
		this.lateralTiltingReactionScore = lateralTiltingReactionScore;
	}

	public boolean isLateralTiltingReactionAsysmmetry() {
		return lateralTiltingReactionAsysmmetry;
	}

	public void setLateralTiltingReactionAsysmmetry(boolean lateralTiltingReactionAsysmmetry) {
		this.lateralTiltingReactionAsysmmetry = lateralTiltingReactionAsysmmetry;
	}

	public String getLateralTiltingReactionComments() {
		return lateralTiltingReactionComments;
	}

	public void setLateralTiltingReactionComments(String lateralTiltingReactionComments) {
		this.lateralTiltingReactionComments = lateralTiltingReactionComments;
	}

	public String getForwardParachuteReactionScore() {
		return forwardParachuteReactionScore;
	}

	public void setForwardParachuteReactionScore(String forwardParachuteReactionScore) {
		this.forwardParachuteReactionScore = forwardParachuteReactionScore;
	}

	public boolean isForwardParachuteReactionAsysmmetry() {
		return forwardParachuteReactionAsysmmetry;
	}

	public void setForwardParachuteReactionAsysmmetry(boolean forwardParachuteReactionAsysmmetry) {
		this.forwardParachuteReactionAsysmmetry = forwardParachuteReactionAsysmmetry;
	}

	public String getForwardParachuteReactionComments() {
		return forwardParachuteReactionComments;
	}

	public void setForwardParachuteReactionComments(String forwardParachuteReactionComments) {
		this.forwardParachuteReactionComments = forwardParachuteReactionComments;
	}

	public String getTendonReflexesReactionScore() {
		return tendonReflexesReactionScore;
	}

	public void setTendonReflexesReactionScore(String tendonReflexesReactionScore) {
		this.tendonReflexesReactionScore = tendonReflexesReactionScore;
	}

	public boolean isTendonReflexesReactionAsysmmetry() {
		return tendonReflexesReactionAsysmmetry;
	}

	public void setTendonReflexesReactionAsysmmetry(boolean tendonReflexesReactionAsysmmetry) {
		this.tendonReflexesReactionAsysmmetry = tendonReflexesReactionAsysmmetry;
	}

	public String getTendonReflexesReactionComments() {
		return tendonReflexesReactionComments;
	}

	public void setTendonReflexesReactionComments(String tendonReflexesReactionComments) {
		this.tendonReflexesReactionComments = tendonReflexesReactionComments;
	}

	public String getHeadControlValue() {
		return headControlValue;
	}

	public void setHeadControlValue(String headControlValue) {
		this.headControlValue = headControlValue;
	}

	public String getSittingValue() {
		return sittingValue;
	}

	public void setSittingValue(String sittingValue) {
		this.sittingValue = sittingValue;
	}

	public String getSittingReportedAge() {
		return sittingReportedAge;
	}

	public void setSittingReportedAge(String sittingReportedAge) {
		this.sittingReportedAge = sittingReportedAge;
	}

	public String getVoluntaryGraspValue() {
		return voluntaryGraspValue;
	}

	public void setVoluntaryGraspValue(String voluntaryGraspValue) {
		this.voluntaryGraspValue = voluntaryGraspValue;
	}

	public String getVoluntaryGraspReportedAge() {
		return voluntaryGraspReportedAge;
	}

	public void setVoluntaryGraspReportedAge(String voluntaryGraspReportedAge) {
		this.voluntaryGraspReportedAge = voluntaryGraspReportedAge;
	}

	public String getAbilitytokickValue() {
		return abilitytokickValue;
	}

	public void setAbilitytokickValue(String abilitytokickValue) {
		this.abilitytokickValue = abilitytokickValue;
	}

	public String getAbilitytokickReportedAge() {
		return abilitytokickReportedAge;
	}

	public void setAbilitytokickReportedAge(String abilitytokickReportedAge) {
		this.abilitytokickReportedAge = abilitytokickReportedAge;
	}

	public String getCrawlingValue() {
		return crawlingValue;
	}

	public void setCrawlingValue(String crawlingValue) {
		this.crawlingValue = crawlingValue;
	}

	public String getCrawlingReportedAge() {
		return crawlingReportedAge;
	}

	public void setCrawlingReportedAge(String crawlingReportedAge) {
		this.crawlingReportedAge = crawlingReportedAge;
	}

	public String getRollingValue() {
		return rollingValue;
	}

	public void setRollingValue(String rollingValue) {
		this.rollingValue = rollingValue;
	}

	public String getRollingReportedAge() {
		return rollingReportedAge;
	}

	public void setRollingReportedAge(String rollingReportedAge) {
		this.rollingReportedAge = rollingReportedAge;
	}

	public String getStandingValue() {
		return standingValue;
	}

	public void setStandingValue(String standingValue) {
		this.standingValue = standingValue;
	}

	public String getStandingReportedAge() {
		return standingReportedAge;
	}

	public void setStandingReportedAge(String standingReportedAge) {
		this.standingReportedAge = standingReportedAge;
	}

	public String getWalkingValue() {
		return walkingValue;
	}

	public void setWalkingValue(String walkingValue) {
		this.walkingValue = walkingValue;
	}

	public String getWalkingReportedAge() {
		return walkingReportedAge;
	}

	public void setWalkingReportedAge(String walkingReportedAge) {
		this.walkingReportedAge = walkingReportedAge;
	}

	public String getConsciousStateValue() {
		return consciousStateValue;
	}

	public void setConsciousStateValue(String consciousStateValue) {
		this.consciousStateValue = consciousStateValue;
	}

	public String getConsciousStateComments() {
		return consciousStateComments;
	}

	public void setConsciousStateComments(String consciousStateComments) {
		this.consciousStateComments = consciousStateComments;
	}

	public String getEmotionalStateValue() {
		return emotionalStateValue;
	}

	public void setEmotionalStateValue(String emotionalStateValue) {
		this.emotionalStateValue = emotionalStateValue;
	}

	public String getEmotionalStateComments() {
		return emotionalStateComments;
	}

	public void setEmotionalStateComments(String emotionalStateComments) {
		this.emotionalStateComments = emotionalStateComments;
	}

	public String getSocialOrientationValue() {
		return socialOrientationValue;
	}

	public void setSocialOrientationValue(String socialOrientationValue) {
		this.socialOrientationValue = socialOrientationValue;
	}

	public String getSocialOrientationComments() {
		return socialOrientationComments;
	}

	public void setSocialOrientationComments(String socialOrientationComments) {
		this.socialOrientationComments = socialOrientationComments;
	}

	public String getHineSummary() {
		return hineSummary;
	}

	public void setHineSummary(String hineSummary) {
		this.hineSummary = hineSummary;
	}

	public String getGlobalScore() {
		return globalScore;
	}

	public void setGlobalScore(String globalScore) {
		this.globalScore = globalScore;
	}

	public String getNoOfAsymmetries() {
		return noOfAsymmetries;
	}

	public void setNoOfAsymmetries(String noOfAsymmetries) {
		this.noOfAsymmetries = noOfAsymmetries;
	}

	public String getBehaviouralScore() {
		return behaviouralScore;
	}

	public void setBehaviouralScore(String behaviouralScore) {
		this.behaviouralScore = behaviouralScore;
	}

	public String getCranialNerveScore() {
		return cranialNerveScore;
	}

	public void setCranialNerveScore(String cranialNerveScore) {
		this.cranialNerveScore = cranialNerveScore;
	}

	public String getTotalPostureScore() {
		return totalPostureScore;
	}

	public void setTotalPostureScore(String totalPostureScore) {
		this.totalPostureScore = totalPostureScore;
	}

	public String getTotalMovementsScore() {
		return totalMovementsScore;
	}

	public void setTotalMovementsScore(String totalMovementsScore) {
		this.totalMovementsScore = totalMovementsScore;
	}

	public String getTotalToneScore() {
		return totalToneScore;
	}

	public void setTotalToneScore(String totalToneScore) {
		this.totalToneScore = totalToneScore;
	}

	public String getTotalReflexesScore() {
		return totalReflexesScore;
	}

	public void setTotalReflexesScore(String totalReflexesScore) {
		this.totalReflexesScore = totalReflexesScore;
	}

	public String getHeadCircumference() {
		return headCircumference;
	}

	public void setHeadCircumference(String headCircumference) {
		this.headCircumference = headCircumference;
	}

	public Timestamp getHcReadingTime() {
		return hcReadingTime;
	}

	public void setHcReadingTime(Timestamp hcReadingTime) {
		this.hcReadingTime = hcReadingTime;
	}

	@Override
	public String toString() {
		return "ScreenNeurological [screen_neurological_id=" + screen_neurological_id + ", creationtime=" + creationtime
				+ ", modificationtime=" + modificationtime + ", uhid=" + uhid + ", episodeid=" + episodeid
				+ ", loggeduser=" + loggeduser + ", screening_time=" + screening_time + ", cga_weeks=" + cga_weeks
				+ ", cga_days=" + cga_days + ", pna_weeks=" + pna_weeks + ", pna_days=" + pna_days + ", posture_type="
				+ posture_type + ", posture_value=" + posture_value + ", arm_traction_type=" + arm_traction_type
				+ ", arm_traction_value=" + arm_traction_value + ", leg_traction_type=" + leg_traction_type
				+ ", leg_traction_value=" + leg_traction_value + ", head_extensor_type=" + head_extensor_type
				+ ", head_extensor_value=" + head_extensor_value + ", head_flexor_type=" + head_flexor_type
				+ ", head_flexor_value=" + head_flexor_value + ", head_lag_type=" + head_lag_type + ", head_lag_value="
				+ head_lag_value + ", ventral_type=" + ventral_type + ", ventral_value=" + ventral_value
				+ ", body_type=" + body_type + ", body_value=" + body_value + ", tremor_startles_type="
				+ tremor_startles_type + ", tremor_startles_value=" + tremor_startles_value + ", moro_reflex_type="
				+ moro_reflex_type + ", moro_reflex_value=" + moro_reflex_value + ", auditory_type=" + auditory_type
				+ ", auditory_value=" + auditory_value + ", visual_type=" + visual_type + ", visual_value="
				+ visual_value + ", alertness_type=" + alertness_type + ", alertness_value=" + alertness_value
				+ ", suck=" + suck + ", facial=" + facial + ", eye=" + eye + ", sunset=" + sunset + ", hand=" + hand
				+ ", clonus=" + clonus + ", reports=" + reports + ", other_comments=" + other_comments + "]";
	}

}
