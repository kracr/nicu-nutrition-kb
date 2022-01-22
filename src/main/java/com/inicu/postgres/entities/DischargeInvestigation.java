package com.inicu.postgres.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the discharge_investigation database table.
 * 
 */
@Entity
@Table(name="discharge_investigation")
@NamedQuery(name="DischargeInvestigation.findAll", query="SELECT d FROM DischargeInvestigation d")
public class DischargeInvestigation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long investigationid;

	@Column(name="cbc_ca_count")
	private String cbcCaCount;

	@Column(name="cbc_crp")
	private String cbcCrp;

	@Column(name="cbc_e_count")
	private String cbcECount;

	@Column(name="cbc_hb")
	private String cbcHb;

	@Column(name="cbc_l_count")
	private String cbcLCount;

	@Column(name="cbc_m_count")
	private String cbcMCount;

	@Column(name="cbc_p_count")
	private String cbcPCount;

	@Column(name="cbc_platelets")
	private String cbcPlatelets;

	@Column(name="cbc_wbc")
	private String cbcWbc;

	

	@Column(name="ct_mri")
	private String ctMri;

	private String eeg;

	@Column(name="lft_albumin")
	private String lftAlbumin;

	@Column(name="lft_alkphos")
	private String lftAlkphos;

	@Column(name="lft_ggtp")
	private String lftGgtp;

	@Column(name="lft_inr")
	private String lftInr;

	@Column(name="lft_pt")
	private String lftPt;

	@Column(name="lft_ptt")
	private String lftPtt;

	@Column(name="lft_sgpt")
	private String lftSgpt;

	@Column(name="lft_totalbilirubin")
	private String lftTotalbilirubin;

	@Column(name="lft_totalproteins")
	private String lftTotalproteins;

	private Timestamp creationtime;

	private Timestamp modificationtime;

	private String nbst;

	private String oae;

	@Column(name="rft_bun")
	private String rftBun;

	@Column(name="rft_creatintine")
	private String rftCreatintine;

	@Column(name="rop_screen")
	private String ropScreen;

	private String uhid;

	@Column(name="usg_head")
	private String usgHead;

	private String vaccinationsid;

	public DischargeInvestigation() {
	}

	public Long getInvestigationid() {
		return this.investigationid;
	}

	public void setInvestigationid(Long investigationid) {
		this.investigationid = investigationid;
	}

	public String getCbcCaCount() {
		return this.cbcCaCount;
	}

	public void setCbcCaCount(String cbcCaCount) {
		this.cbcCaCount = cbcCaCount;
	}

	public String getCbcCrp() {
		return this.cbcCrp;
	}

	public void setCbcCrp(String cbcCrp) {
		this.cbcCrp = cbcCrp;
	}

	public String getCbcECount() {
		return this.cbcECount;
	}

	public void setCbcECount(String cbcECount) {
		this.cbcECount = cbcECount;
	}

	public String getCbcHb() {
		return this.cbcHb;
	}

	public void setCbcHb(String cbcHb) {
		this.cbcHb = cbcHb;
	}

	public String getCbcLCount() {
		return this.cbcLCount;
	}

	public void setCbcLCount(String cbcLCount) {
		this.cbcLCount = cbcLCount;
	}

	public String getCbcMCount() {
		return this.cbcMCount;
	}

	public void setCbcMCount(String cbcMCount) {
		this.cbcMCount = cbcMCount;
	}

	public String getCbcPCount() {
		return this.cbcPCount;
	}

	public void setCbcPCount(String cbcPCount) {
		this.cbcPCount = cbcPCount;
	}

	public String getCbcPlatelets() {
		return this.cbcPlatelets;
	}

	public void setCbcPlatelets(String cbcPlatelets) {
		this.cbcPlatelets = cbcPlatelets;
	}

	public String getCbcWbc() {
		return this.cbcWbc;
	}

	public void setCbcWbc(String cbcWbc) {
		this.cbcWbc = cbcWbc;
	}

	

	public String getCtMri() {
		return this.ctMri;
	}

	public void setCtMri(String ctMri) {
		this.ctMri = ctMri;
	}

	public String getEeg() {
		return this.eeg;
	}

	public void setEeg(String eeg) {
		this.eeg = eeg;
	}

	public String getLftAlbumin() {
		return this.lftAlbumin;
	}

	public void setLftAlbumin(String lftAlbumin) {
		this.lftAlbumin = lftAlbumin;
	}

	public String getLftAlkphos() {
		return this.lftAlkphos;
	}

	public void setLftAlkphos(String lftAlkphos) {
		this.lftAlkphos = lftAlkphos;
	}

	public String getLftGgtp() {
		return this.lftGgtp;
	}

	public void setLftGgtp(String lftGgtp) {
		this.lftGgtp = lftGgtp;
	}

	public String getLftInr() {
		return this.lftInr;
	}

	public void setLftInr(String lftInr) {
		this.lftInr = lftInr;
	}

	public String getLftPt() {
		return this.lftPt;
	}

	public void setLftPt(String lftPt) {
		this.lftPt = lftPt;
	}

	public String getLftPtt() {
		return this.lftPtt;
	}

	public void setLftPtt(String lftPtt) {
		this.lftPtt = lftPtt;
	}

	public String getLftSgpt() {
		return this.lftSgpt;
	}

	public void setLftSgpt(String lftSgpt) {
		this.lftSgpt = lftSgpt;
	}

	public String getLftTotalbilirubin() {
		return this.lftTotalbilirubin;
	}

	public void setLftTotalbilirubin(String lftTotalbilirubin) {
		this.lftTotalbilirubin = lftTotalbilirubin;
	}

	public String getLftTotalproteins() {
		return this.lftTotalproteins;
	}

	public void setLftTotalproteins(String lftTotalproteins) {
		this.lftTotalproteins = lftTotalproteins;
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

	public String getNbst() {
		return this.nbst;
	}

	public void setNbst(String nbst) {
		this.nbst = nbst;
	}

	public String getOae() {
		return this.oae;
	}

	public void setOae(String oae) {
		this.oae = oae;
	}

	public String getRftBun() {
		return this.rftBun;
	}

	public void setRftBun(String rftBun) {
		this.rftBun = rftBun;
	}

	public String getRftCreatintine() {
		return this.rftCreatintine;
	}

	public void setRftCreatintine(String rftCreatintine) {
		this.rftCreatintine = rftCreatintine;
	}

	public String getRopScreen() {
		return this.ropScreen;
	}

	public void setRopScreen(String ropScreen) {
		this.ropScreen = ropScreen;
	}

	public String getUhid() {
		return this.uhid;
	}

	public void setUhid(String uhid) {
		this.uhid = uhid;
	}

	public String getUsgHead() {
		return this.usgHead;
	}

	public void setUsgHead(String usgHead) {
		this.usgHead = usgHead;
	}

	public String getVaccinationsid() {
		return this.vaccinationsid;
	}

	public void setVaccinationsid(String vaccinationsid) {
		this.vaccinationsid = vaccinationsid;
	}

}