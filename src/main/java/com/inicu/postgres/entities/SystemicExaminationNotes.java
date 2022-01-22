package com.inicu.postgres.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the systemic_examination_notes database table.
 *
 * @author Umesh Kumar
 */

@Entity
@Table(name="systemic_examination_notes")
@NamedQuery(name = "SystemicExaminationNotes.findAll", query = "SELECT b FROM SystemicExaminationNotes b")
public class SystemicExaminationNotes implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String uhid;

    private String episodeid;

    @Column(name="progressnotes_resp_sys_rds")
    private String progressnotesRespSysRds;

    @Column(name="progressnotes_resp_sys_apnea")
    private String progressnotesRespSysApnea;

    @Column(name="progressnotes_resp_sys_pneumothorax")
    private String progressnotesRespSysPneumothorax;

    @Column(name="progressnotes_resp_sys_pphn")
    private String progressnotesRespSysPphn;

    @Column(name="progressnotes_jaundice")
    private String progressnotesJaundice;

    @Column(name="progressnotes_infect_sepsis")
    private String progressnotesInfectSepsis;

    @Column(name="progressnotes_infect_vap")
    private String progressnotesInfectVap;

    @Column(name="progressnotes_infect_clabsi")
    private String progressnotesInfectClabsi;

    @Column(name="progressnotes_infect_intrauterine")
    private String progressnotesInfectIntrauterine;

    @Column(name="progressnotes_infect_nec")
    private String progressnotesInfectNec;

    @Column(name="progressnotes_cns_asphyxia")
    private String progressnotesCnsAsphyxia;

    @Column(name="progressnotes_cns_seizures")
    private String progressnotesCnsSeizures;

    @Column(name="progressnotes_cns_encephalopathy")
    private String progressnotesCnsEncephalopathy;

    @Column(name="progressnotes_cns_neuromusculardisorder")
    private String progressnotesCnsNeuromusculardisorder;

    @Column(name="progressnotes_cns_ivh")
    private String progressnotesCnsIVH;

    @Column(name="progressnotes_cns_hydrocephalus")
    private String progressnotesCnsHydrocephalus;

    @Column(name="progressnotes_metabolic_hypoglycemia")
    private String progressnotesMetabolicHypoglycemia;

    @Column(name="progressnotes_renal")
    private String progressnotesRenal;

    @Column(name="progressnotes_gi_feedintolerance")
    private String progressnotesGiFeedintolerance;
    
    @Column(name="progressnotes_cvs_shock")
    private String progressnotesCvsShock;

    private Timestamp creationtime;
    private Timestamp modificationtime;

    public SystemicExaminationNotes(){
        this.uhid=null;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEpisodeid() {
        return episodeid;
    }

    public void setEpisodeid(String episodeid) {
        this.episodeid = episodeid;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getProgressnotesRespSysRds() {
        return progressnotesRespSysRds;
    }

    public void setProgressnotesRespSysRds(String progressnotesRespSysRds) {
        this.progressnotesRespSysRds = progressnotesRespSysRds;
    }

    public String getProgressnotesRespSysApnea() {
        return progressnotesRespSysApnea;
    }

    public void setProgressnotesRespSysApnea(String progressnotesRespSysApnea) {
        this.progressnotesRespSysApnea = progressnotesRespSysApnea;
    }

    public String getProgressnotesRespSysPneumothorax() {
        return progressnotesRespSysPneumothorax;
    }

    public void setProgressnotesRespSysPneumothorax(String progressnotesRespSysPneumothorax) {
        this.progressnotesRespSysPneumothorax = progressnotesRespSysPneumothorax;
    }

    public String getProgressnotesRespSysPphn() {
        return progressnotesRespSysPphn;
    }

    public void setProgressnotesRespSysPphn(String progressnotesRespSysPphn) {
        this.progressnotesRespSysPphn = progressnotesRespSysPphn;
    }

    public String getProgressnotesJaundice() {
        return progressnotesJaundice;
    }

    public void setProgressnotesJaundice(String progressnotesJaundice) {
        this.progressnotesJaundice = progressnotesJaundice;
    }

    public String getProgressnotesInfectSepsis() {
        return progressnotesInfectSepsis;
    }

    public void setProgressnotesInfectSepsis(String progressnotesInfectSepsis) {
        this.progressnotesInfectSepsis = progressnotesInfectSepsis;
    }

    public String getProgressnotesInfectVap() {
        return progressnotesInfectVap;
    }

    public void setProgressnotesInfectVap(String progressnotesInfectVap) {
        this.progressnotesInfectVap = progressnotesInfectVap;
    }

    public String getProgressnotesInfectClabsi() {
        return progressnotesInfectClabsi;
    }

    public void setProgressnotesInfectClabsi(String progressnotesInfectClabsi) {
        this.progressnotesInfectClabsi = progressnotesInfectClabsi;
    }

    public String getProgressnotesInfectIntrauterine() {
        return progressnotesInfectIntrauterine;
    }

    public void setProgressnotesInfectIntrauterine(String progressnotesInfectIntrauterine) {
        this.progressnotesInfectIntrauterine = progressnotesInfectIntrauterine;
    }

    public String getProgressnotesInfectNec() {
        return progressnotesInfectNec;
    }

    public void setProgressnotesInfectNec(String progressnotesInfectNec) {
        this.progressnotesInfectNec = progressnotesInfectNec;
    }

    public String getProgressnotesCnsAsphyxia() {
        return progressnotesCnsAsphyxia;
    }

    public void setProgressnotesCnsAsphyxia(String progressnotesCnsAsphyxia) {
        this.progressnotesCnsAsphyxia = progressnotesCnsAsphyxia;
    }

    public String getProgressnotesCnsSeizures() {
        return progressnotesCnsSeizures;
    }

    public void setProgressnotesCnsSeizures(String progressnotesCnsSeizures) {
        this.progressnotesCnsSeizures = progressnotesCnsSeizures;
    }

    public String getProgressnotesCnsEncephalopathy() {
        return progressnotesCnsEncephalopathy;
    }

    public void setProgressnotesCnsEncephalopathy(String progressnotesCnsEncephalopathy) {
        this.progressnotesCnsEncephalopathy = progressnotesCnsEncephalopathy;
    }

    public String getProgressnotesCnsNeuromusculardisorder() {
        return progressnotesCnsNeuromusculardisorder;
    }

    public void setProgressnotesCnsNeuromusculardisorder(String progressnotesCnsNeuromusculardisorder) {
        this.progressnotesCnsNeuromusculardisorder = progressnotesCnsNeuromusculardisorder;
    }

    public String getProgressnotesCnsIVH() {
        return progressnotesCnsIVH;
    }

    public void setProgressnotesCnsIVH(String progressnotesCnsIVH) {
        this.progressnotesCnsIVH = progressnotesCnsIVH;
    }

    public String getProgressnotesCnsHydrocephalus() {
        return progressnotesCnsHydrocephalus;
    }

    public void setProgressnotesCnsHydrocephalus(String progressnotesCnsHydrocephalus) {
        this.progressnotesCnsHydrocephalus = progressnotesCnsHydrocephalus;
    }

    public String getProgressnotesMetabolicHypoglycemia() {
        return progressnotesMetabolicHypoglycemia;
    }

    public void setProgressnotesMetabolicHypoglycemia(String progressnotesMetabolicHypoglycemia) {
        this.progressnotesMetabolicHypoglycemia = progressnotesMetabolicHypoglycemia;
    }

    public String getProgressnotesRenal() {
        return progressnotesRenal;
    }

    public void setProgressnotesRenal(String progressnotesRenal) {
        this.progressnotesRenal = progressnotesRenal;
    }

    public String getProgressnotesGiFeedintolerance() {
        return progressnotesGiFeedintolerance;
    }

    public void setProgressnotesGiFeedintolerance(String progressnotesGiFeedintolerance) {
        this.progressnotesGiFeedintolerance = progressnotesGiFeedintolerance;
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

	public String getProgressnotesCvsShock() {
		return progressnotesCvsShock;
	}

	public void setProgressnotesCvsShock(String progressnotesCvsShock) {
		this.progressnotesCvsShock = progressnotesCvsShock;
	}
    
}
