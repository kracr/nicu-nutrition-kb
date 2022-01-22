--
-- TOC entry 173 (class 1259 OID 70688)
-- Name: acidosis_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE acidosis_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE acidosis_seq OWNER TO postgres;

--
-- TOC entry 174 (class 1259 OID 70690)
-- Name: admission_notes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE admission_notes_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE admission_notes_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 70692)
-- Name: admission_notes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE admission_notes (
    admission_notes_id bigint DEFAULT nextval('admission_notes_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    reason_admission text,
    antenatal_history text,
    birth_to_inicu text,
    status_at_admission text,
    gen_phy_exam text,
    systemic_exam text,
    diagnosis text,
    loggeduser character varying(50)
);


ALTER TABLE admission_notes OWNER TO postgres;

--
-- TOC entry 176 (class 1259 OID 70699)
-- Name: babydetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE babydetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE babydetail_seq OWNER TO postgres;

--
-- TOC entry 177 (class 1259 OID 70701)
-- Name: baby_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE baby_detail (
    babydetailid bigint DEFAULT nextval('babydetail_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    babyname character varying(50) NOT NULL,
    gender character varying(50) NOT NULL,
    dateofbirth date NOT NULL,
    timeofbirth character varying(50),
    dateofadmission date NOT NULL,
    timeofadmission character varying(50),
    birthweight real,
    birthlength real,
    birthheadcircumference real,
    obstetrician character varying(50),
    admittingdoctor character varying(50),
    iptagno character varying(50),
    typeofadmission character varying(50),
    inout_patient_status character varying(50),
    gestationweekbylmp integer,
    gestationdaysbylmp integer,
    actualgestationweek integer,
    actualgestationdays integer,
    bloodgroup character varying(10),
    nicubedno character varying(20),
    nicuroomno character varying(20),
    niculevelno character varying(10),
    criticalitylevel character varying(10),
    significantmaterialid character varying(50),
    historyid character varying(50),
    headtotoeid character varying(50),
    examinationid character varying(50),
    comments character varying(2000),
    activestatus boolean,
    babyshiftedto boolean,
    admissionstatus boolean,
    timeofcry character varying(100),
    cry character varying(20),
    loggeduser character varying(100) NOT NULL,
    dischargestatus character varying(50),
    dischargeddate date,
    dayoflife character varying(50),
    admission_spo2 character varying(50),
    admission_rr character varying(50),
    admission_pulserate character varying(50),
    admission_bp character varying(50),
    admission_temp character varying(50),
    birthmarks boolean,
    birthmarks_comments character varying(1000),
    birthinjuries boolean,
    birthinjuries_comments character varying(1000),
    refferedfrom character varying(100),
    courseinrefferinghospital character varying(50),
    modeoftransportation character varying(50),
    transportationalongwith character varying(100),
    reasonofreference character varying(1000),
    refferedby character varying(100),
    reffered_number character varying(50),
    sourceoftransportation character varying(50),
    weight_centile real,
    length_centile real,
    hc_centile real,
    bp_type character varying(50),
    bp_lll integer,
    bp_rll integer,
    bp_lul integer,
    bp_rul integer,
    spo2 integer,
    episodeid character varying(50) NOT NULL,
    consciousness character varying(50),
    weight_galevel character varying(5),
    length_galevel character varying(5),
    hc_galevel character varying(5),
    ponderal_index integer,
    is_fever boolean,
    hr integer,
    rr integer,
    bp_systolic integer,
    bp_diastolic integer,
    bp_mean integer,
    crt integer,
    downesscoreid character varying(50),
    central_temp real,
    peripheral_temp real,
    isassessmentsubmit boolean,
    baby_type character varying(10),
    baby_number character varying(10),
    admissionweight real
);


ALTER TABLE baby_detail OWNER TO postgres;

--
-- TOC entry 178 (class 1259 OID 70708)
-- Name: headtotoeexamination_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE headtotoeexamination_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE headtotoeexamination_seq OWNER TO postgres;

--
-- TOC entry 179 (class 1259 OID 70710)
-- Name: headtotoe_examination; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE headtotoe_examination (
    headtoeid bigint DEFAULT nextval('headtotoeexamination_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    head_value boolean,
    head_comments character varying(1000),
    heart_value boolean,
    heart_comments character varying(1000),
    ngpassed_value boolean,
    ngpassed_comments character varying(1000),
    cdh_value boolean,
    cdh_comments character varying(1000),
    ccd_value boolean,
    ccd_comments character varying(1000),
    mouth_value boolean,
    mouth_comments character varying(1000),
    finger_value boolean,
    finger_comments character varying(1000),
    back_value boolean,
    back_comments character varying(1000),
    anus_value boolean,
    anus_comments character varying(1000),
    anomaly_value boolean,
    anomaly_comments character varying(1000),
    uhid character varying(50) NOT NULL
);


ALTER TABLE headtotoe_examination OWNER TO postgres;


--
-- TOC entry 181 (class 1259 OID 70722)
-- Name: patientventilator_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE patientventilator_seq
    START WITH 190
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patientventilator_seq OWNER TO postgres;

--
-- TOC entry 182 (class 1259 OID 70724)
-- Name: device_ventilator_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE device_ventilator_detail (
    deviceventilatorid bigint DEFAULT nextval('patientventilator_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    beddeviceid character varying(50),
    pip character varying(50),
    peep character varying(50),
    map character varying(50),
    freqrate character varying(50),
    tidalvol character varying(50),
    minvol character varying(50),
    ti character varying(50),
    fio2 character varying(50),
    flowpermin character varying(50),
    noppm character varying(50),
    meanbp character varying(50),
    occpressure character varying(50),
    peakbp character varying(50),
    platpressure character varying(50),
    start_time timestamp with time zone
);


ALTER TABLE device_ventilator_detail OWNER TO postgres;



--
-- TOC entry 185 (class 1259 OID 70740)
-- Name: testresults_id_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE testresults_id_seq
    START WITH 4
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE testresults_id_seq OWNER TO postgres;

--
-- TOC entry 186 (class 1259 OID 70742)
-- Name: test_result; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE test_result (
    prn character varying(50),
    testid character varying(15),
    itemid character varying(10),
    itemname character varying(100),
    itemvalue character varying(50),
    itemunit character varying(50),
    normalrange character varying(50),
    regno bigint,
    id bigint DEFAULT nextval('testresults_id_seq'::regclass) NOT NULL,
    resultdate date,
    creationtime timestamp with time zone,
    lab_result_status character varying(15),
    lab_created_date timestamp without time zone,
    lab_updated_date timestamp without time zone,
    lab_collection_date date,
    lab_posted_date timestamp with time zone,
    lab_report_date timestamp without time zone,
    lab_updated_by character varying(20),
    lab_testresultid bigint,
    lab_testname character varying(40)
);


ALTER TABLE test_result OWNER TO postgres;



--
-- TOC entry 190 (class 1259 OID 70761)
-- Name: antenatal_history_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE antenatal_history_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE antenatal_history_seq OWNER TO postgres;

--
-- TOC entry 191 (class 1259 OID 70763)
-- Name: antenatal_history_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE antenatal_history_detail (
    antenatal_history_id bigint DEFAULT nextval('antenatal_history_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    g_score integer,
    a_score integer,
    p_score integer,
    l_score integer,
    conception_type character varying(30),
    conception_details text,
    pregnancy_type character varying(30),
    total_pregnancy integer,
    edd_by character varying(30),
    trimester_type character varying(20),
    lmp_timestamp timestamp with time zone,
    edd_timestamp timestamp with time zone,
    gestationby_lmp_weeks integer,
    gestationby_lmp_days integer,
    getstationby_usg_weeks integer,
    getstationby_usg_days integer,
    antenatel_checkup_status character varying(30),
    isthreeantenatalcheckupdone boolean,
    firsttrimesterscreening text,
    secondtrimesterscreening text,
    thirdtrimesterscreening text,
    umbilical_doppler character varying(30),
    abnormal_umbilical_doppler_type character varying(30),
    history_of_smoking boolean,
    history_of_alcohol boolean,
    gestational_hypertension boolean,
    hypertension boolean,
    diabetes boolean,
    gdm boolean,
    chronic_kidney_disease boolean,
    hypothyroidism boolean,
    hyperthyroidism boolean,
    fever boolean,
    uti boolean,
    history_of_infections boolean,
    prom boolean,
    pprom boolean,
    prematurity boolean,
    oligohydraminos boolean,
    polyhydraminos boolean,
    tetanus_toxoid boolean,
    tetanus_toxoid_doses integer,
    other_medications text,
    ishiv boolean,
    ishepb boolean,
    hepb_type character varying(20),
    vdrl boolean,
    other_meternal_investigations text,
    mother_blood_group_abo character varying(30),
    mother_blood_group_rh character varying(30),
    is_labour boolean,
    labour_type character varying(30),
    is_augmented boolean,
    presentation_type character varying(30),
    other_presentation_type text,
    mode_of_delivery character varying(30),
    is_lscs_elective boolean,
    anaesthesia_type character varying(30),
    chorioamniotis boolean,
    historyofivinfectiontext text,
    isothermeternalinvestigations boolean,
    otherriskfactors text,
    promtext text,
    isfirsttrimesterscreening boolean,
    issecondtrimesterscreening boolean,
    isthirdtrimesterscreening boolean,
    isantenatalsteroidgiven boolean,
    ict boolean,
    ict_titre integer,
    ict_date timestamp with time zone,
    torch boolean,
    anitd boolean,
    antid_firstdose integer,
    antid_secdose integer,
    episodeid character varying(50) NOT NULL,
    notknowntype character varying(100),
    torchtext text,
    hivtype text,
    vdrltype text,
    antenatalhistorytext text,
    iscourserepeated boolean,
    isresuscitationmedication boolean
);


ALTER TABLE antenatal_history_detail OWNER TO postgres;

--
-- TOC entry 192 (class 1259 OID 70770)
-- Name: antenatal_steroid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE antenatal_steroid_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE antenatal_steroid_seq OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 70772)
-- Name: antenatal_steroid_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE antenatal_steroid_detail (
    antenatal_steroid_id bigint DEFAULT nextval('antenatal_steroid_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    steroidname character varying(20),
    numberofdose integer,
    givendate timestamp with time zone,
    giventimehours character varying(50),
    giventimeminutes character varying(50),
    giventimeseconds integer,
    isbirthlastdosetextgt24 boolean,
    given_meridian character varying(10)
);


ALTER TABLE antenatal_steroid_detail OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 70776)
-- Name: hypercalcemia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hypercalcemia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hypercalcemia_seq OWNER TO postgres;

--
-- TOC entry 195 (class 1259 OID 70778)
-- Name: hyperglycemia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hyperglycemia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hyperglycemia_seq OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 70780)
-- Name: hyperkalemia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hyperkalemia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hyperkalemia_seq OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 70782)
-- Name: hypernatremia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hypernatremia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hypernatremia_seq OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 70784)
-- Name: hypocalcemia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hypocalcemia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hypocalcemia_seq OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 70786)
-- Name: hyponatremia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hyponatremia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hyponatremia_seq OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 70788)
-- Name: iem_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE iem_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iem_seq OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 70790)
-- Name: resp_apnea_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resp_apnea_seq
    START WITH 12
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resp_apnea_seq OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 70792)
-- Name: resppneumothorax_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resppneumothorax_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resppneumothorax_seq OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 70794)
-- Name: resppphn_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resppphn_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resppphn_seq OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 70796)
-- Name: resprds_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resprds_seq
    START WITH 14
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resprds_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 70798)
-- Name: sa_acidosis; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_acidosis (
    acidosisid bigint DEFAULT nextval('acidosis_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    ph character varying(20),
    hco3 character varying(20),
    be character varying(20),
    pco2 character varying(20),
    lactate character varying(20),
    urine_output character varying(20),
    urine_output_type character varying(20),
    treatmentaction character varying(200),
    treatment_other text,
    bolus_type character varying(20),
    bolus_feedmethod character varying(20),
    bolus_volume character varying(20),
    bolus_frequency character varying(20),
    total_bolusfeed character varying(20),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofacidosis character varying(500),
    causeofacidosis_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    sodium character varying,
    potassium character varying,
    chloride character varying,
    aniongap character varying
);


ALTER TABLE sa_acidosis OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 70805)
-- Name: sacnsasphyxia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sacnsasphyxia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sacnsasphyxia_seq OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 70807)
-- Name: sa_cns_asphyxia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_cns_asphyxia (
    sacnsasphyxiaid bigint DEFAULT nextval('sacnsasphyxia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    cns_system_status character varying(20),
    eventstatus character varying(20),
    history character varying(200),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    issensorium boolean,
    sensorium_type character varying(100),
    isrespiration boolean,
    isseizures boolean,
    isshock boolean,
    isacidosis boolean,
    treatmentaction character varying(500),
    othertreatment_comments text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeof_asphyxia character varying(500),
    causeofasphyxia_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    downesscoreid character varying(20),
    thompsonscoreid character varying(20),
    sarnatscoreid character varying(20),
    levenescoreid character varying(20),
    treatmentselectedtext text,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_cns_asphyxia OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 70814)
-- Name: sacnsivh_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sacnsivh_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sacnsivh_seq OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 70816)
-- Name: sa_cns_ivh; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_cns_ivh (
    sacnsivhid bigint DEFAULT nextval('sacnsivh_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    cns_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_value character varying(500),
    papilescore_id character varying(20),
    volpescore_id character varying(20),
    treatmentaction character varying(500),
    othertreatment_comments text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeof_ivh character varying(500),
    causeofivh_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    bloodtrans boolean
);


ALTER TABLE sa_cns_ivh OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 70823)
-- Name: sacnsseizures_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sacnsseizures_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sacnsseizures_seq OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 70825)
-- Name: sa_cns_seizures; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_cns_seizures (
    sacnsseizuresid bigint DEFAULT nextval('sacnsseizures_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    cns_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    ageatassesment integer,
    isageofassesmentinhours boolean,
    timeofassessment timestamp with time zone,
    noofseizures character varying(20),
    bloodsugarlevel character varying(20),
    calciumlevel character varying(20),
    treatmentaction character varying(500),
    othertreatment_comments text,
    resuscitation_comments text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeof_seizures character varying(500),
    causeofseizures_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    treatmentselectedtext text,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_cns_seizures OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 70832)
-- Name: sa_hypercalcemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hypercalcemia (
    hypercalcemiaid bigint DEFAULT nextval('hypercalcemia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    serum_calcium character varying(20),
    treatmentaction character varying(200),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhypercalcemia character varying(500),
    causeofhypercalcemia_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100)
);


ALTER TABLE sa_hypercalcemia OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 70839)
-- Name: sa_hyperglycemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hyperglycemia (
    hyperglycemiaid bigint DEFAULT nextval('hyperglycemia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    bloodsugar character varying(20),
    totalhyperglycemia_event character varying(10),
    total_symptomatic_event character varying(10),
    total_asymptomatic_event character varying(10),
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    treatmentaction character varying(200),
    treatment_other text,
    insulinbolus character varying(10),
    insulinfusionrate character varying(10),
    insulinfusionstatus character varying(20),
    max_gir character varying(10),
    min_gir character varying(10),
    decreased_gir character varying(10),
    max_gir_total character varying(10),
    min_gir_total character varying(10),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhyperglycemia character varying(200),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    girvalue character varying(10),
    insulin_route character varying(20),
    urine_output character varying(10),
    other_cause character varying(1000),
    insulin_count character varying(10),
    insulin_total character varying(10)
);


ALTER TABLE sa_hyperglycemia OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 70846)
-- Name: sa_hyperkalemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hyperkalemia (
    hyperkalemiaid bigint DEFAULT nextval('hyperkalemia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    abg_ph character varying(20),
    abg_pco2 character varying(20),
    abg_hco3 character varying(20),
    ecg_twave boolean,
    ventricular_tachycardia boolean,
    treatmentaction character varying(200),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhyperkalemia character varying(500),
    causeofhyperkalemia_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    serumsodium character varying(20),
    serumpotassium character varying(20),
    calcium_volume real,
    calcium_total real,
    ecg_reversion boolean
);


ALTER TABLE sa_hyperkalemia OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 70853)
-- Name: sa_hypernatremia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hypernatremia (
    hypernatremiaid bigint DEFAULT nextval('hypernatremia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    serumsodium character varying(20),
    last_serumsodium character varying(20),
    max_serumsodium character varying(20),
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    currentweight integer,
    weightdifference integer,
    isgainorloss boolean,
    gainpercent character varying(20),
    absoluteweightgain character varying(20),
    isleanweightloss boolean,
    losspercent character varying(20),
    total character varying(20),
    weightforcalculation integer,
    urineoutput integer,
    urineoutput_duration integer,
    fulid_rate character varying(20),
    deflcit character varying(20),
    insensible_loss character varying(20),
    treatmentaction character varying(200),
    treatment_other text,
    fluidintakeamount character varying(20),
    fluidintakeduration character varying(20),
    fluidstrength character varying(20),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhypernatremia character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    isbolus boolean,
    bolusdose character varying(10),
    bolus_rate character varying(10),
    bolus_strength character varying(10),
    cause_other text,
    deficit_day character varying(20),
    insensible_loss_day character varying(20),
    totalfluid character varying(20),
    orderfornurse boolean
);


ALTER TABLE sa_hypernatremia OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 70860)
-- Name: sa_hypocalcemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hypocalcemia (
    hypocalcemiaid bigint DEFAULT nextval('hypocalcemia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    magnesium_route character varying(20),
    magnesium_dose character varying(20),
    magnesium_frequency character varying(20),
    vitamind_route character varying(20),
    vitamind_dose character varying(20),
    vitamind_frequency character varying(20),
    treatmentaction character varying(200),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhypocalcemia character varying(500),
    causeofhypocalcemia_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    serum_calcium character varying(20)
);


ALTER TABLE sa_hypocalcemia OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 70867)
-- Name: sametabolic_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sametabolic_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sametabolic_seq OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 70869)
-- Name: sa_hypoglycemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hypoglycemia (
    hypoglycemiaid bigint DEFAULT nextval('sametabolic_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    min_bloodsugar character varying(20),
    hypoglycemia_event character varying(20),
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    treatmentaction character varying(200),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhypoglycemia character varying(200),
    progressnotes text,
    associatedevent character varying(100),
    girvalue character varying(10),
    totalfeed_ml_day real,
    totalfeedvolume character varying(50),
    counthypoglemicevents integer,
    countsymptomaticevents integer,
    countasymptomaticevents integer,
    min_gir character varying(50),
    max_gir character varying(50),
    isgirincreased boolean,
    girchange real,
    treatmentactionothertext character varying(1000),
    causeothertext character varying(1000),
    bolustype character varying(50),
    bolusvolume character varying(50),
    bolustotalfeed character varying(50)
);


ALTER TABLE sa_hypoglycemia OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 70876)
-- Name: sa_hyponatremia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hyponatremia (
    hyponatremiaid bigint DEFAULT nextval('hyponatremia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    serumsodium character varying(20),
    minimumserum_12hrs character varying(20),
    last_serumsodium character varying(20),
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    currentweight real,
    weightdifference real,
    isgainorloss boolean,
    isdiuresisdone boolean,
    urine_output character varying(20),
    duration character varying(20),
    diuresistype character varying(20),
    ishistoryofgi boolean,
    treatmentaction character varying(200),
    treatment_other text,
    totalfluidintake character varying(20),
    additional_sodium character varying(20),
    additional_na_duration character varying(20),
    na_duration_timetype character varying(20),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhyponatremia character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    giloss_total character varying,
    giloss_lastvisit character varying(80),
    giloss_gastricflag boolean,
    giloss_rectumflag boolean,
    giloss_stomaflag boolean,
    giloss_gastric character varying(20),
    giloss_stoma character varying(20),
    giloss_rectum character varying(20),
    giloss_24hrs character varying(80)
);


ALTER TABLE sa_hyponatremia OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 70883)
-- Name: sa_iem; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_iem (
    iemid bigint DEFAULT nextval('iem_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    pasthistory character varying(500),
    ph character varying(20),
    hco3 character varying(20),
    be character varying(20),
    pco2 character varying(20),
    lactate character varying(20),
    treatmentaction character varying(200),
    treatment_other text,
    bolus_type character varying(20),
    bolus_feedmethod character varying(20),
    bolus_volume character varying(20),
    bolus_frequency character varying(20),
    total_bolusfeed character varying(20),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofiem character varying(500),
    causeofiem_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    exchangetrans boolean
);


ALTER TABLE sa_iem OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 70890)
-- Name: sajaundice_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sajaundice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sajaundice_seq OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 70892)
-- Name: sa_jaundice; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_jaundice (
    sajaundiceid bigint DEFAULT nextval('sajaundice_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    ageofonset integer,
    durationofjaundice integer,
    causeofjaundice character varying(50),
    dct boolean,
    exchangetrans boolean,
    noofexchange character varying(10),
    phototherapy boolean,
    durphoto character varying(10),
    hemolysis boolean,
    recticct character varying(10),
    ivig boolean,
    maxbili character varying(10),
    loggeduser character varying(100) NOT NULL,
    comment character varying(1000),
    isageofonsetinhours boolean,
    isphotobelowthreshold character varying(50),
    actiontype character varying(50),
    otheractiontype character varying(100),
    actionduration integer,
    orderinvestigation character varying(100),
    jaundicestatus character varying(20),
    isinvestigationodered boolean,
    nursingcomment character varying(1400),
    ivigvalue character varying(1400),
    phototherapyvalue character varying(50),
    bindscoreid bigint,
    observationtype character varying(50),
    riskfactor character varying(50),
    treatmentother character varying(100),
    planother text,
    tcbortsb boolean,
    ageatassesment real,
    isageofassesmentinhours boolean,
    othercause text,
    associatedevent text,
    maxtsb real,
    isactiondurationinhours character varying(10),
    maxtcb real,
    tbcvalue real,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying,
    jaundicediagnosisby character varying(255)
);


ALTER TABLE sa_jaundice OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 70899)
-- Name: sa_resp_apnea; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_apnea (
    apneaid bigint DEFAULT nextval('resp_apnea_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    resp_system_status character varying(10),
    eventstatus character varying(10),
    ageatonset character varying(10),
    ageinhours boolean,
    totalnoofapnea integer,
    noofapnea_inaday integer,
    cyanosis boolean,
    actiontype character varying(50),
    caffeine_action character varying(50),
    caffeine_route character varying(50),
    caffeine_bolus_dose character varying(50),
    caffeine_maintenance_dose character varying(50),
    action_plan character varying(200),
    action_plan_time character varying(10),
    action_plan_timetype character varying(10),
    action_plan_comments character varying(1000),
    apnea_cause character varying(200),
    cummulative_apnea_on_caffeine integer,
    continuous_apnea_on_caffeine integer,
    apnea_free_days_after_caffeine integer,
    cummulative_number_of_episodes integer,
    cummulative_days_of_caffeine integer,
    apnea_comment character varying(1000),
    treatment_other text,
    causeofapnea_other character varying(500),
    timeofassessment timestamp with time zone,
    associatedevent text,
    isageofassesmentinhours boolean,
    ageatassesment integer,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_resp_apnea OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 70906)
-- Name: sa_resp_pneumothorax; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_pneumothorax (
    resppneumothoraxid bigint DEFAULT nextval('resppneumothorax_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    resp_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    downesscoreid character varying(20),
    treatmentaction character varying(200),
    sufactantname character varying(50),
    surfactant_dose character varying(50),
    isinsuredone boolean,
    action_after_surfactant character varying(100),
    transillumination boolean,
    lefttransillumination boolean,
    righttransillumination boolean,
    needle_aspiration boolean,
    aspiration_plan_time integer,
    aspiration_minhrsdays character varying(50),
    chesttube_insertion boolean,
    chesttube_plan_time integer,
    chesttube_minhrsdays character varying(50),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    silvermanscoreid character varying(50),
    causeofpneumothorax character varying(100),
    progressnotes text,
    associatedevent character varying(100),
    needle_aspiration_date date,
    needle_aspiration_time character varying(20),
    chesttube_time character varying(20),
    chesttube_date date,
    causeofpneumo_other character varying(500),
    treatment_other text,
    ageatassessmentinhoursdays boolean,
    ageatassesment bigint,
    surfactanttotaldose bigint,
    causeother text,
    needleaspirationright boolean,
    needleaspirationleft boolean,
    treatmentselectedtext text,
    pneumodiagnosisby character varying(50),
    statusofpneumothoraxright character varying(50),
    statusofpneumothoraxleft character varying(50),
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_resp_pneumothorax OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 70913)
-- Name: sa_resp_pphn; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_pphn (
    resppphnid bigint DEFAULT nextval('resppphn_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    resp_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    downesscoreid character varying(20),
    treatmentaction character varying(200),
    sufactantname character varying(50),
    surfactant_dose character varying(50),
    isinsuredone boolean,
    action_after_surfactant character varying(100),
    systolic_bp character varying(100),
    oxgenation_index character varying(100),
    lab_preductal character varying(100),
    lab_postductal character varying(100),
    labile_difference character varying(100),
    labile_oxygenation boolean,
    pulmonary_pressure character varying(100),
    pphn_ino character varying(100),
    methaemoglobin_level character varying(100),
    other_pulmonaryvasodialotrs character varying(100),
    pphnplan character varying(200),
    reassestime character varying(20),
    reassestime_type character varying(50),
    othercomments text,
    silvermanscoreid character varying(50),
    causeofpphn character varying(200),
    progressnotes text,
    associatedevent character varying(100),
    loggeduser character varying(100) NOT NULL,
    timeofassessment timestamp with time zone,
    treatment_other text,
    causeofpphn_other character varying(500),
    ageatassesment integer,
    isageofassesmentinhours boolean,
    treatmentselectedtext text,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_resp_pphn OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 70920)
-- Name: sa_resp_rds; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_rds (
    resprdsid bigint DEFAULT nextval('resprds_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    resp_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    downesscoreid character varying(20),
    treatmentaction character varying(200),
    sufactantname character varying(50),
    surfactant_dose character varying(50),
    isinsuredone boolean,
    action_after_surfactant character varying(100),
    medicine_prescribed character varying(500),
    rdsplan character varying(50),
    reassestime character varying(20),
    reasseshoursdays character varying(50),
    othercomments text,
    silvermanscoreid character varying(50),
    causeofrds character varying(100),
    progressnotes text,
    associatedevent character varying(100),
    loggeduser character varying(100) NOT NULL,
    treatment_other text,
    causeofrds_other character varying(500),
    timeofassessment timestamp with time zone,
    isageofassesmentinhours boolean,
    ageatassesment integer,
    surfactant_dose_ml character varying(20),
    riskfactor character varying(200),
    riskfactor_other text,
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_resp_rds OWNER TO postgres;





--
-- TOC entry 230 (class 1259 OID 70940)
-- Name: baby_addinfo; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE baby_addinfo (
    addinfoid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    doc_title character varying(100),
    doc_name character varying(100),
    isactive boolean,
    uhid character varying(100),
    documentkey character varying(100)
);


ALTER TABLE baby_addinfo OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 70943)
-- Name: baby_addinfo_addinfoid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE baby_addinfo_addinfoid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE baby_addinfo_addinfoid_seq OWNER TO postgres;

--
-- TOC entry 5260 (class 0 OID 0)
-- Dependencies: 231
-- Name: baby_addinfo_addinfoid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE baby_addinfo_addinfoid_seq OWNED BY baby_addinfo.addinfoid;


--
-- TOC entry 232 (class 1259 OID 70945)
-- Name: visit_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE visit_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE visit_seq OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 70947)
-- Name: baby_visit; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE baby_visit (
    visitid bigint DEFAULT nextval('visit_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    visitdate date,
    currentage character varying(50),
    nicuday character varying(50),
    ga_at_birth character varying(50),
    corrected_ga character varying(50),
    currentdateweight real,
    currentdateheight real,
    currentdateheadcircum real,
    workingweight real,
    loggeduser character varying(50) NOT NULL,
    surgery character varying(500),
    surgeon character varying(150),
    neonatologist character varying(150),
    episodeid character varying(50),
    admission_entry boolean,
    daysafterbirth character varying(100),
    gestationweek integer,
    gestationdays integer
);


ALTER TABLE baby_visit OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 70964)
-- Name: babyfeed_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE babyfeed_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE babyfeed_seq OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 70966)
-- Name: babyfeed_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE babyfeed_detail (
    babyfeedid bigint DEFAULT nextval('babyfeed_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    ebm boolean DEFAULT false,
    ebmperdayml real,
    lbfm boolean DEFAULT false,
    lbfmperdayml real,
    tfm boolean DEFAULT false,
    tfmperdayml real,
    hmf boolean DEFAULT false,
    hmfperdayml real,
    hmf_ebm boolean DEFAULT false,
    hmf_ebm_perdayml real,
    productmilk boolean DEFAULT false,
    productmilkperdayml real,
    feedmethod character varying(50),
    feedvolume real,
    feedduration integer,
    totalfeed_ml_day real,
    ivfluidml_perhr real,
    ivfluidtype character varying(200),
    total_ivfluids real,
    totalfluid_ml_day real,
    formulamilk boolean,
    formulamilkperdayml character varying(20),
    tfi character varying(50),
    feedtype character varying(50),
    ivfluidrate character varying(50),
    dilution character varying(50),
    total_intake character varying(50),
    is_normal_tpn boolean,
    loggeduser character varying(100),
    girvalue character varying(10),
    totalivfluid_mlkgday real,
    current_dextrose_concentration real,
    dextrose_conc_low character varying(50),
    dextrose_conc_high character varying(50),
    dextrose_conc_lowvolume real,
    dextrose_conc_highvolume real,
    bolustype character varying(200),
    bolusmethod character varying(200),
    bolusvolume real,
    bolusfrequency integer,
    bolustotalfeed real,
    enteral boolean,
    parenteral boolean,
    vitamind_volume real,
    vitamind_total real,
    calsyrup_volume real,
    calsyrup_total real,
    iron_volume real,
    iron_total real,
    vitamina_volume real,
    vitamina_total real,
    sodium_volume real,
    sodium_total real,
    calcium_volume real,
    calcium_total real,
    phosphorous_total real,
    phosphorous_volume real,
    potassium_total real,
    potassium_volume real,
    mvi_volume real,
    mvi_total real,
    hco3_volume real,
    hco3_total real,
    magnesium_volume real,
    magnesium_total real,
    aminoacid_pnvolume character varying(50),
    aminoacid_conc real,
    aminoacid_total real,
    lipid_pnvolume character varying(50),
    lipid_conc real,
    lipid_total real,
    isenternalgiven boolean,
    isparentalgiven boolean,
    additiveenteral boolean,
    isfeedgiven boolean,
    isivmedcinegiven boolean,
    totalenteralvolume real,
    totalparenteralvolume real,
    totalfeedvolumemlperday real,
    dextrosevolumemlperday real,
    working_weight real,
    totalparenteraladditivevolume real,
    totalenteraadditivelvolume real,
    dextrosenetconcentration real,
    osmolarity real,
    additiveparenteral boolean,
    isbolusgiven boolean,
    total_medvolume real,
    libbreastfeedother character varying(1000),
    libbreastfeed character varying,
    isadditiveinencalculation boolean,
    pnproteinenergyratio real,
    pncarbohydrateslipidsratio real,
    encarbohydrateslipidsratio real,
    enproteinenergyratio real,
    totalcarbohydrateslipidsratio real,
    totalproteinenergyratio real,
    feedtext text,
    isadlibgiven boolean,
    pastbolustotalfeed real,
    feedtypesecondary character varying(1500),
    enproteiningmsenergyratio real,
    pnproteiningmsenergyratio real,
    totalproteiningmsenergyratio real,
    isreadymadesolutiongiven boolean,
    readymadefluidrate real,
    readymadetotalfluidvolume real,
    readymadefluidvolume real,
    isivfluidgiven character varying(50),
    readymadefluidtype character varying(50),
    entrydatetime timestamp with time zone,
    remainingfluid_mlkg character varying(100),
    dextrose_conc character varying(100),
    remainingfluid character varying(100)
);


ALTER TABLE babyfeed_detail OWNER TO postgres;



--
-- TOC entry 247 (class 1259 OID 71022)
-- Name: doctornotes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE doctornotes_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE doctornotes_seq OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 71024)
-- Name: doctor_notes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE doctor_notes (
    doctornoteid bigint DEFAULT nextval('doctornotes_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    doctornotes character varying(4000),
    followupnotes character varying(4000),
    diagnosis character varying(3000),
    issues character varying(3000),
    plan character varying(3000),
    loggeduser character varying(100) NOT NULL,
    noteentrytime timestamp with time zone
);


ALTER TABLE doctor_notes OWNER TO postgres;



--
-- TOC entry 254 (class 1259 OID 71054)
-- Name: baby_prescription; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE baby_prescription (
    baby_presid bigint DEFAULT nextval('babydetail_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    medicinename character varying(50),
    route character varying(50),
    dose real,
    frequency character varying(50),
    startdate timestamp with time zone,
    starttime character varying(50),
    enddate timestamp with time zone,
    medicationtype character varying(30),
    comments character varying(500),
    loggeduser character varying(100) NOT NULL,
    endtime character varying(30),
    isactive boolean DEFAULT true,
    calculateddose character varying(50),
    eventid character varying(100),
    eventname character varying(100),
    bolus boolean,
    freq_type character varying(50),
    dilution_type character varying(50),
    dilution_volume real,
    rate real
);


ALTER TABLE baby_prescription OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 71062)
-- Name: baby_prescription_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE baby_prescription_seq
    START WITH 25
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE baby_prescription_seq OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 71064)
-- Name: babyfeedivmed_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE babyfeedivmed_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE babyfeedivmed_seq OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 71066)
-- Name: babyfeedivmed_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE babyfeedivmed_detail (
    babyfeedivmedid bigint DEFAULT nextval('babyfeedivmed_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    babyfeedid character varying(50),
    medicine_name character varying(50),
    medicine_dose character varying(50),
    medicine_frequency character varying(50),
    dillution character varying(50),
    total_medvolume character varying(50),
    seizures_cause character varying(50)
);


ALTER TABLE babyfeedivmed_detail OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 71070)
-- Name: babytpnfeed_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE babytpnfeed_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE babytpnfeed_seq OWNER TO postgres;

--
-- TOC entry 259 (class 1259 OID 71072)
-- Name: babytpnfeed_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE babytpnfeed_detail (
    babytpnfeedid bigint DEFAULT nextval('babytpnfeed_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    babyfeedid character varying(50),
    pn_aminoacid character varying(50),
    pn_gdr character varying(50),
    pn_carbohydrate character varying(50),
    pn_lipid character varying(50),
    vol_aminoacid character varying(50),
    vol_gdr character varying(50),
    vol_carbohydrate character varying(50),
    vol_lipid character varying(50),
    vol_calcium character varying(50),
    vol_phosphorous character varying(50),
    vol_sodium character varying(50),
    vol_potassium character varying(50),
    vol_vitamin character varying(50),
    vol_others character varying(50),
    tot_aminoacid character varying(50),
    tot_gdr character varying(50),
    tot_carbohydrate character varying(50),
    tot_lipid character varying(50),
    tot_calcium character varying(50),
    tot_phosphorous character varying(50),
    tot_sodium character varying(50),
    tot_potassium character varying(50),
    tot_vitamin character varying(50),
    tot_others character varying(50),
    cal_aminoacid character varying(50),
    cal_gdr character varying(50),
    cal_carbohydrate character varying(50),
    cal_lipid character varying(50),
    remainingfluid character varying(50),
    remainingfluid_mlkg character varying(50),
    dextrose_conc character varying(50),
    actual_conc character varying(50),
    total_protein character varying(50),
    target_protein character varying(50),
    total_carbohydrate character varying(50),
    target_carbohydrate character varying(50),
    total_fat character varying(50),
    target_fat character varying(50),
    total_calorie character varying(50),
    target_calorie character varying(50),
    tot_pnfluid character varying(50)
);


ALTER TABLE babytpnfeed_detail OWNER TO postgres;

--
-- TOC entry 260 (class 1259 OID 71079)
-- Name: beddevicedetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE beddevicedetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE beddevicedetail_seq OWNER TO postgres;

--
-- TOC entry 261 (class 1259 OID 71081)
-- Name: bed_device_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE bed_device_detail (
    beddeviceid bigint DEFAULT nextval('beddevicedetail_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    bedid character varying(50) NOT NULL,
    deviceid character varying(50) NOT NULL,
    bbox_device_id bigint NOT NULL,
    uhid character varying(50) NOT NULL,
    status boolean DEFAULT false NOT NULL,
    time_to timestamp with time zone
);


ALTER TABLE bed_device_detail OWNER TO postgres;

--
-- TOC entry 262 (class 1259 OID 71086)
-- Name: devicedetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE devicedetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE devicedetail_seq OWNER TO postgres;

--
-- TOC entry 263 (class 1259 OID 71088)
-- Name: device_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE device_detail (
    deviceid bigint DEFAULT nextval('devicedetail_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    devicetype character varying(50),
    devicebrand character varying(50),
    deviceserialno character varying(50),
    description character varying(200),
    available boolean,
    ipaddress character varying(50),
    portno character varying(50),
    domainid character varying(50),
    endtime timestamp with time zone,
    devicename character varying(50),
    inicu_deviceid bigint DEFAULT 1 NOT NULL
);


ALTER TABLE device_detail OWNER TO postgres;

--
-- TOC entry 265 (class 1259 OID 71101)
-- Name: birth_to_nicu_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE birth_to_nicu_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE birth_to_nicu_seq OWNER TO postgres;

--
-- TOC entry 266 (class 1259 OID 71103)
-- Name: birth_to_nicu; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE birth_to_nicu (
    birth_to_nicu_id bigint DEFAULT nextval('birth_to_nicu_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    loggeduser character varying(50),
    inout_patient_status character varying(50),
    transported_type character varying(50),
    transported_in character varying(50),
    transport_map character varying(50),
    transport_fio2 character varying(50),
    transport_mech_vent_type character varying(50),
    transport_flowrate character varying(50),
    transport_pip character varying(50),
    transport_peep character varying(50),
    transport_it character varying(50),
    transport_et character varying(50),
    transport_tv character varying(50),
    transport_mv character varying(50),
    transport_breathrate character varying(50),
    transport_backuprate character varying(50),
    meconium boolean,
    meconium_hour integer,
    cried_immediately boolean,
    resuscitation boolean,
    initial_step boolean,
    resuscitation_o2 boolean,
    resuscitation_o2_duration character varying(50),
    resuscitation_ppv boolean,
    resuscitation_ppv_duration character varying(50),
    resuscitation_chesttube_compression boolean,
    resuscitation_chesttube_compression_duration character varying(50),
    resuscitation_medication boolean,
    resuscitation_medication_details text,
    rds boolean,
    rds_ageatonset integer,
    respsupport boolean,
    apgar_onemin integer,
    apgar_fivemin integer,
    apgar_tenmin integer,
    encephalopathy boolean,
    leveneid character varying(50),
    levene_grade character varying(50),
    seizures boolean,
    seizures_ageatonset integer,
    seizures_no_episode integer,
    seizures_type character varying(50),
    apnea boolean,
    apnea_ageatonset integer,
    apnea_no_episode integer,
    jaundice boolean,
    jaundice_ageatonset integer,
    jaundice_cause character varying(50),
    phototherapy boolean,
    phototherapy_duration integer,
    exchange_transfusion boolean,
    ivig boolean,
    ivig_dose character varying(50),
    ivig_no_dose character varying(50),
    passed_urine boolean,
    urine_output character varying(50),
    cong_malform boolean,
    cong_malform_details character varying(500),
    investigation_report text,
    other_details text,
    tbcvalue real,
    rds_ageinhoursdays boolean,
    seizures_ageinhoursdays boolean,
    apnea_ageinhoursdays boolean,
    jaundice_ageinhoursdays boolean,
    duration_o2_time character varying(50),
    ppv_time character varying(50),
    chest_comp_time character varying(50)
);


ALTER TABLE birth_to_nicu OWNER TO postgres;

--
-- TOC entry 267 (class 1259 OID 71110)
-- Name: bloodproducts_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE bloodproducts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE bloodproducts_seq OWNER TO postgres;

--
-- TOC entry 268 (class 1259 OID 71112)
-- Name: blood_products; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE blood_products (
    bloodproductsid bigint DEFAULT nextval('bloodproducts_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    bloodproduct character varying(50),
    dose character varying(50),
    duration character varying(50),
    bloodgroup character varying(1000),
    loggeduser character varying(100) NOT NULL,
    babyfeedid character varying(50),
    isbloodgiven boolean,
    entrydatetime timestamp with time zone
);


ALTER TABLE blood_products OWNER TO postgres;

--
-- TOC entry 269 (class 1259 OID 71119)
-- Name: brand; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE brand (
    id bigint NOT NULL,
    vaccineid bigint NOT NULL,
    brandname character varying(50),
    isactivebrand boolean DEFAULT true,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE brand OWNER TO postgres;

--
-- TOC entry 270 (class 1259 OID 71123)
-- Name: brand_id_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE brand_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE brand_id_seq OWNER TO postgres;

--
-- TOC entry 5261 (class 0 OID 0)
-- Dependencies: 270
-- Name: brand_id_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE brand_id_seq OWNED BY brand.id;


--
-- TOC entry 271 (class 1259 OID 71125)
-- Name: cardiac_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE cardiac_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cardiac_seq OWNER TO postgres;

--
-- TOC entry 272 (class 1259 OID 71127)
-- Name: central_line_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE central_line_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE central_line_seq OWNER TO postgres;

--
-- TOC entry 273 (class 1259 OID 71129)
-- Name: central_line; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE central_line (
    central_line_id bigint DEFAULT nextval('central_line_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    central_line_type character varying(50),
    brand text,
    size character varying(50),
    site character varying(50),
    side character varying(10),
    limb_site character varying(50),
    limb_site_other text,
    limb_side character varying(50),
    confirm_xray boolean,
    central_line_position character varying(50),
    adjusted boolean,
    adjust_comment text,
    insertion_timestamp timestamp with time zone,
    removal_timestamp timestamp with time zone,
    removal_reason character varying(50),
    loggeduser character varying(100),
    progressnotes text
);


ALTER TABLE central_line OWNER TO postgres;

--
-- TOC entry 274 (class 1259 OID 71136)
-- Name: certificate; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE certificate (
    id bigint DEFAULT nextval('babydetail_seq'::regclass) NOT NULL,
    certificate_name character varying(30) DEFAULT NULL::character varying,
    employee_id integer
);


ALTER TABLE certificate OWNER TO postgres;

--
-- TOC entry 275 (class 1259 OID 71141)
-- Name: clabsi_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE clabsi_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clabsi_seq OWNER TO postgres;

--
-- TOC entry 276 (class 1259 OID 71143)
-- Name: currentpregnancy_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE currentpregnancy_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE currentpregnancy_seq OWNER TO postgres;

--
-- TOC entry 277 (class 1259 OID 71145)
-- Name: patientdevicedetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE patientdevicedetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patientdevicedetail_seq OWNER TO postgres;

--
-- TOC entry 278 (class 1259 OID 71147)
-- Name: device_monitor_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE device_monitor_detail (
    devicemoniterid bigint DEFAULT nextval('patientdevicedetail_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    beddeviceid character varying(50),
    pulserate character varying(50),
    spo2 character varying(50),
    heartrate character varying(50),
    ecg_resprate character varying(50),
    co2_resprate character varying(50),
    etco2 character varying(50),
    sys_bp character varying(50),
    dia_bp character varying(50),
    mean_bp character varying(50),
    nbp_s character varying(50),
    nbp_d character varying(50),
    nbp_m character varying(50),
    starttime timestamp with time zone
);


ALTER TABLE device_monitor_detail OWNER TO postgres;

--
-- TOC entry 279 (class 1259 OID 71154)
-- Name: parentdetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE parentdetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE parentdetail_seq OWNER TO postgres;

--
-- TOC entry 280 (class 1259 OID 71156)
-- Name: parent_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE parent_detail (
    parentdetailid bigint DEFAULT nextval('parentdetail_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    motherid character varying(50) NOT NULL,
    mothername character varying(50),
    motherdob date,
    motherage integer,
    fathername character varying(50),
    fatherdob date,
    fatherage integer,
    primaryphonenumber character varying(50),
    secondaryphonenumber character varying(50),
    emailid character varying(50),
    address character varying(200),
    aadharcard character varying(12),
    villagename character varying(50),
    emergency_contactname character varying(50),
    emergency_contactno character varying(50),
    relationship character varying(50),
    mother_religion character varying(50),
    mother_uhid character varying(50),
    mother_aadhar character varying(50),
    mother_education character varying(50),
    mother_profession character varying(50),
    mother_income character varying(50),
    mother_phone character varying(50),
    mother_email character varying(50),
    father_religion character varying(50),
    father_aadhar character varying(50),
    father_education character varying(50),
    father_profession character varying(50),
    father_income character varying(50),
    father_phone character varying(50),
    father_email character varying(50),
    add_city character varying(50),
    add_state character varying(50),
    add_pin character varying(50),
    episodeid character varying(50),
    kuppuswamy_score integer,
    kuppuswamy_class character varying(50),
    is_mother_kuppuswamy boolean,
    add_district character varying(100),
    father_religion_other character varying(255),
    mother_religion_other character varying(255)
);


ALTER TABLE parent_detail OWNER TO postgres;

--
-- TOC entry 281 (class 1259 OID 71163)
-- Name: ref_bed; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_bed (
    bedid character varying(20) NOT NULL,
    bedname character varying(20) NOT NULL,
    roomid character varying(20) NOT NULL,
    status boolean,
    description character varying(50) NOT NULL,
    creationtime time without time zone,
    modificationtime time without time zone,
    isactive boolean DEFAULT true,
    loggeduser character varying(100),
    creationtimestamp timestamp with time zone,
    removaltimestamp timestamp with time zone
);


ALTER TABLE ref_bed OWNER TO postgres;

--
-- TOC entry 282 (class 1259 OID 71167)
-- Name: ref_criticallevel; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_criticallevel (
    crlevelid character varying(10) NOT NULL,
    levelname character varying(20) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_criticallevel OWNER TO postgres;

--
-- TOC entry 283 (class 1259 OID 71170)
-- Name: ref_level; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_level (
    levelid character varying(10) NOT NULL,
    levelname character varying(20) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_level OWNER TO postgres;

--
-- TOC entry 284 (class 1259 OID 71173)
-- Name: ref_room; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_room (
    roomid character varying(20) NOT NULL,
    roomname character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    isactive boolean DEFAULT true,
    loggeduser character varying(100)
);


ALTER TABLE ref_room OWNER TO postgres;



--
-- TOC entry 286 (class 1259 OID 71182)
-- Name: notification_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE notification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notification_seq OWNER TO postgres;

--
-- TOC entry 287 (class 1259 OID 71184)
-- Name: notifications; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE notifications (
    notificationid bigint DEFAULT nextval('notification_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    message text NOT NULL,
    messagetype character varying(50)
);


ALTER TABLE notifications OWNER TO postgres;

--
-- TOC entry 288 (class 1259 OID 71191)
-- Name: uhidnotification_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE uhidnotification_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE uhidnotification_seq OWNER TO postgres;

--
-- TOC entry 289 (class 1259 OID 71193)
-- Name: uhidnotification; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE uhidnotification (
    uhidnotificationid bigint DEFAULT nextval('uhidnotification_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    notificationid character varying(50) NOT NULL
);


ALTER TABLE uhidnotification OWNER TO postgres;



--
-- TOC entry 296 (class 1259 OID 71225)
-- Name: device_bloodgas_detail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE device_bloodgas_detail_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE device_bloodgas_detail_seq OWNER TO postgres;

--
-- TOC entry 297 (class 1259 OID 71227)
-- Name: device_bloodgas_detail_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE device_bloodgas_detail_detail (
    device_bloodgas_detail_id bigint DEFAULT nextval('device_bloodgas_detail_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    sample_id character varying(300) NOT NULL,
    hl7_message text,
    uhid character varying(50)
);


ALTER TABLE device_bloodgas_detail_detail OWNER TO postgres;

--
-- TOC entry 298 (class 1259 OID 71234)
-- Name: device_mapping_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE device_mapping_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE device_mapping_seq OWNER TO postgres;

--
-- TOC entry 299 (class 1259 OID 71236)
-- Name: discharge_advice_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_advice_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_advice_seq OWNER TO postgres;

--
-- TOC entry 300 (class 1259 OID 71238)
-- Name: discharge_advice_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_advice_detail (
    discharge_advice_id bigint DEFAULT nextval('discharge_advice_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    isedit boolean,
    isselected boolean,
    editedvalue text,
    fixedvalue text,
    advicetempid character varying(50)
);


ALTER TABLE discharge_advice_detail OWNER TO postgres;

--
-- TOC entry 301 (class 1259 OID 71245)
-- Name: discharge_aminophylline; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_aminophylline (
    aminophyllineid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    aminophylline timestamp with time zone,
    aminophylline_startdate date,
    aminophylline_enddate date,
    metabolicid bigint NOT NULL
);


ALTER TABLE discharge_aminophylline OWNER TO postgres;

--
-- TOC entry 302 (class 1259 OID 71248)
-- Name: discharge_aminophylline_aminophyllineid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_aminophylline_aminophyllineid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_aminophylline_aminophyllineid_seq OWNER TO postgres;

--
-- TOC entry 5262 (class 0 OID 0)
-- Dependencies: 302
-- Name: discharge_aminophylline_aminophyllineid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_aminophylline_aminophyllineid_seq OWNED BY discharge_aminophylline.aminophyllineid;


--
-- TOC entry 303 (class 1259 OID 71250)
-- Name: discharge_aminophylline_metabolicid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_aminophylline_metabolicid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_aminophylline_metabolicid_seq OWNER TO postgres;

--
-- TOC entry 5263 (class 0 OID 0)
-- Dependencies: 303
-- Name: discharge_aminophylline_metabolicid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_aminophylline_metabolicid_seq OWNED BY discharge_aminophylline.metabolicid;


--
-- TOC entry 304 (class 1259 OID 71252)
-- Name: dischargebirthdetail_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargebirthdetail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargebirthdetail_seq OWNER TO postgres;

--
-- TOC entry 305 (class 1259 OID 71254)
-- Name: discharge_birthdetail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_birthdetail (
    maternaldetailid bigint DEFAULT nextval('dischargebirthdetail_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    maternal_comments character varying(500),
    motherbloodgroup character varying(50),
    antenatal_risk character varying(100),
    deliverymode character varying(50),
    presentation character varying(100),
    liquor character varying(50),
    cry character varying(50),
    apgar_score_1min character varying(50),
    apgar_score_5min character varying(50),
    apgar_score_10min character varying(50),
    termorpreterm character varying(50),
    hr_rate character varying(50),
    rr_rate character varying(50),
    prefusion character varying(50),
    saturation character varying(50),
    rs_brief character varying(50),
    cvs_brief character varying(50),
    pa character varying(50),
    cns character varying(2000),
    probleminnicu character varying(4000)
);


ALTER TABLE discharge_birthdetail OWNER TO postgres;

--
-- TOC entry 306 (class 1259 OID 71261)
-- Name: discharge_caffeine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_caffeine (
    caffeineid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    caffeine_startdate date,
    caffeine_enddate date,
    metabolicid bigint NOT NULL
);


ALTER TABLE discharge_caffeine OWNER TO postgres;

--
-- TOC entry 307 (class 1259 OID 71264)
-- Name: discharge_caffeine_caffeineid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_caffeine_caffeineid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_caffeine_caffeineid_seq OWNER TO postgres;

--
-- TOC entry 5264 (class 0 OID 0)
-- Dependencies: 307
-- Name: discharge_caffeine_caffeineid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_caffeine_caffeineid_seq OWNED BY discharge_caffeine.caffeineid;


--
-- TOC entry 308 (class 1259 OID 71266)
-- Name: discharge_caffeine_metabolicid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_caffeine_metabolicid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_caffeine_metabolicid_seq OWNER TO postgres;

--
-- TOC entry 5265 (class 0 OID 0)
-- Dependencies: 308
-- Name: discharge_caffeine_metabolicid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_caffeine_metabolicid_seq OWNED BY discharge_caffeine.metabolicid;


--
-- TOC entry 309 (class 1259 OID 71268)
-- Name: discharge_cpap; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_cpap (
    cpapid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    cpap timestamp with time zone,
    cpap_startdate date,
    cpap_enddate date,
    metabolicid bigint NOT NULL
);


ALTER TABLE discharge_cpap OWNER TO postgres;

--
-- TOC entry 310 (class 1259 OID 71271)
-- Name: discharge_cpap_cpapid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_cpap_cpapid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_cpap_cpapid_seq OWNER TO postgres;

--
-- TOC entry 5266 (class 0 OID 0)
-- Dependencies: 310
-- Name: discharge_cpap_cpapid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_cpap_cpapid_seq OWNED BY discharge_cpap.cpapid;


--
-- TOC entry 311 (class 1259 OID 71273)
-- Name: discharge_cpap_metabolicid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_cpap_metabolicid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_cpap_metabolicid_seq OWNER TO postgres;

--
-- TOC entry 5267 (class 0 OID 0)
-- Dependencies: 311
-- Name: discharge_cpap_metabolicid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_cpap_metabolicid_seq OWNED BY discharge_cpap.metabolicid;


--
-- TOC entry 312 (class 1259 OID 71275)
-- Name: discharge_doctornotes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_doctornotes_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_doctornotes_seq OWNER TO postgres;

--
-- TOC entry 313 (class 1259 OID 71277)
-- Name: discharge_doctornotes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_doctornotes (
    dischargenotesid bigint DEFAULT nextval('discharge_doctornotes_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    dischargepatientid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    doctornotesid character varying(50),
    followupnotesid character varying(50),
    diagnosisid character varying(50),
    issuesid character varying(50),
    planid character varying(50)
);


ALTER TABLE discharge_doctornotes OWNER TO postgres;

--
-- TOC entry 314 (class 1259 OID 71281)
-- Name: dischargefeeding_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargefeeding_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargefeeding_seq OWNER TO postgres;

--
-- TOC entry 315 (class 1259 OID 71283)
-- Name: discharge_feeding; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_feeding (
    feedingid bigint DEFAULT nextval('dischargefeeding_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50),
    tpn_days character varying(50),
    ppn_days character varying(50),
    tubefeeds_days character varying(50),
    watispoonfeeds_days character varying(50),
    breastfeeds_days character varying(50)
);


ALTER TABLE discharge_feeding OWNER TO postgres;

--
-- TOC entry 316 (class 1259 OID 71287)
-- Name: dischargeinfection_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargeinfection_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargeinfection_seq OWNER TO postgres;

--
-- TOC entry 317 (class 1259 OID 71289)
-- Name: discharge_infection; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_infection (
    infectioninicuid bigint DEFAULT nextval('dischargeinfection_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    infection_date date,
    culture_report character varying(50),
    organism character varying(100),
    sensitivity character varying(100),
    antibiotics character varying(100),
    duration character varying(100),
    meningitis boolean,
    septic_shock character varying(100),
    inotropes character varying(100)
);


ALTER TABLE discharge_infection OWNER TO postgres;

--
-- TOC entry 318 (class 1259 OID 71296)
-- Name: dischargeinvestigation_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargeinvestigation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargeinvestigation_seq OWNER TO postgres;

--
-- TOC entry 319 (class 1259 OID 71298)
-- Name: discharge_investigation; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_investigation (
    investigationid bigint DEFAULT nextval('dischargeinvestigation_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50),
    cbc_hb character varying(50),
    cbc_wbc character varying(50),
    cbc_p_count character varying(50),
    cbc_l_count character varying(50),
    cbc_e_count character varying(50),
    cbc_m_count character varying(50),
    cbc_platelets character varying(50),
    cbc_crp character varying(50),
    cbc_ca_count character varying(50),
    lft_totalproteins character varying(50),
    lft_albumin character varying(50),
    lft_sgpt character varying(50),
    lft_totalbilirubin character varying(50),
    lft_pt character varying(50),
    lft_ptt character varying(50),
    lft_inr character varying(50),
    lft_alkphos character varying(50),
    lft_ggtp character varying(50),
    rft_creatintine character varying(50),
    rft_bun character varying(50),
    usg_head character varying(500),
    rop_screen character varying(500),
    ct_mri character varying(500),
    eeg character varying(500),
    nbst character varying(500),
    oae character varying(500),
    vaccinationsid character varying(50)
);


ALTER TABLE discharge_investigation OWNER TO postgres;

--
-- TOC entry 320 (class 1259 OID 71305)
-- Name: dischargemetabolic_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargemetabolic_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargemetabolic_seq OWNER TO postgres;

--
-- TOC entry 321 (class 1259 OID 71307)
-- Name: discharge_metabolic; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_metabolic (
    jaundiceinicuid bigint DEFAULT nextval('dischargemetabolic_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    neonatal_jaundice boolean,
    baby_bloodgroup character varying(50),
    dct character varying(50),
    g6pd character varying(50),
    max_bilirubin character varying(50),
    discharge_bilirubin character varying(50),
    phototherapy boolean,
    phototherapyid character varying(50),
    exchangetrans boolean,
    apnea_prematurity boolean,
    caffeine boolean,
    caffeineid character varying(50),
    aminophylline boolean,
    aminophyllineid character varying(50),
    cpap boolean,
    cpapid boolean,
    anemia_prematurity boolean,
    prbc_transfusion boolean,
    nooftransfusion character varying(20),
    platelet_transfusion boolean,
    ffp_transfusion boolean,
    ivig_transfusion boolean
);


ALTER TABLE discharge_metabolic OWNER TO postgres;

--
-- TOC entry 322 (class 1259 OID 71311)
-- Name: dischargenicucourse_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargenicucourse_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargenicucourse_seq OWNER TO postgres;

--
-- TOC entry 323 (class 1259 OID 71313)
-- Name: discharge_nicucourse; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_nicucourse (
    courseinicuid bigint DEFAULT nextval('dischargenicucourse_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    respsystem_detail character varying(2000),
    cvs_detail character varying(2000),
    gi_detail character varying(2000),
    cns_detail character varying(2000),
    infection_detail character varying(2000),
    gu_detail character varying(2000),
    problem_innicu character varying(2000)
);


ALTER TABLE discharge_nicucourse OWNER TO postgres;

--
-- TOC entry 324 (class 1259 OID 71320)
-- Name: discharge_notes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_notes_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_notes_seq OWNER TO postgres;

--
-- TOC entry 325 (class 1259 OID 71322)
-- Name: discharge_notes_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_notes_detail (
    discharge_notes_id bigint DEFAULT nextval('discharge_notes_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    antenatal_details text,
    birth_details text,
    status_at_admission text,
    resp_system text,
    jaundice text,
    cns text,
    infection text,
    treatment text,
    isantenatalhistory boolean,
    isbirthdetails boolean,
    isstatusatadmission boolean,
    istreatment boolean,
    isrespiratorysystem boolean,
    isjaundice boolean,
    iscns boolean,
    isinfection boolean,
    isstablenotes boolean,
    stablenotes text
);


ALTER TABLE discharge_notes_detail OWNER TO postgres;

--
-- TOC entry 326 (class 1259 OID 71329)
-- Name: dischargeoutcomeid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargeoutcomeid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargeoutcomeid_seq OWNER TO postgres;

--
-- TOC entry 327 (class 1259 OID 71331)
-- Name: discharge_outcome; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_outcome (
    discharge_outcome_id bigint DEFAULT nextval('dischargeoutcomeid_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50),
    isedit boolean,
    outcome_type character varying(20),
    make_summary_flag boolean,
    episodeid character varying(50),
    entrytime timestamp with time zone
);


ALTER TABLE discharge_outcome OWNER TO postgres;

--
-- TOC entry 328 (class 1259 OID 71335)
-- Name: discharge_phototherapy; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_phototherapy (
    phototherapyid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    phototherapy_type boolean,
    phototherapy_startdate date,
    phototherapy_enddate date,
    metabolicid bigint NOT NULL
);


ALTER TABLE discharge_phototherapy OWNER TO postgres;

--
-- TOC entry 329 (class 1259 OID 71338)
-- Name: discharge_phototherapy_metabolicid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_phototherapy_metabolicid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_phototherapy_metabolicid_seq OWNER TO postgres;

--
-- TOC entry 5268 (class 0 OID 0)
-- Dependencies: 329
-- Name: discharge_phototherapy_metabolicid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_phototherapy_metabolicid_seq OWNED BY discharge_phototherapy.metabolicid;


--
-- TOC entry 330 (class 1259 OID 71340)
-- Name: discharge_phototherapy_phototherapyid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_phototherapy_phototherapyid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_phototherapy_phototherapyid_seq OWNER TO postgres;

--
-- TOC entry 5269 (class 0 OID 0)
-- Dependencies: 330
-- Name: discharge_phototherapy_phototherapyid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_phototherapy_phototherapyid_seq OWNED BY discharge_phototherapy.phototherapyid;


--
-- TOC entry 331 (class 1259 OID 71342)
-- Name: dischargesummary_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargesummary_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargesummary_seq OWNER TO postgres;

--
-- TOC entry 332 (class 1259 OID 71344)
-- Name: discharge_summary; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_summary (
    babydischargeid bigint DEFAULT nextval('dischargesummary_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    babyname character varying(150),
    gender character varying(20),
    dateofbirth date,
    dateofadmission date,
    inout_patient_status character varying(20),
    ageatdischarge character varying(50),
    gestationageatbirth character varying(50),
    actualgestation character varying(50),
    lmp date,
    edd date,
    weightatbirth character varying(50),
    lengthatbirth character varying(50),
    hcircumatbirth character varying(50),
    weightonadmission character varying(50),
    lengthonadmission character varying(50),
    hcircumonadmission character varying(50),
    weightondischarge character varying(50),
    lengthondischarge character varying(50),
    hcircumondischarge character varying(50),
    dischargestatus character varying(50),
    finaldiagnosis character varying(4000),
    discharge_hr character varying(2000),
    discharge_rr character varying(50),
    discharge_perfusion character varying(50),
    discharge_saturation character varying(50),
    discharge_rs character varying(50),
    discharge_cvs character varying(50),
    discharge_pa character varying(50),
    discharge_cns character varying(2000),
    treatment_on_discharge character varying(4000),
    followup_date date,
    followup_doctor character varying(100),
    followup_bera character varying(500),
    dateofdischarge date
);


ALTER TABLE discharge_summary OWNER TO postgres;

--
-- TOC entry 333 (class 1259 OID 71351)
-- Name: dischargetreatment_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargetreatment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargetreatment_seq OWNER TO postgres;

--
-- TOC entry 334 (class 1259 OID 71353)
-- Name: discharge_treatment; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_treatment (
    treatmentid bigint DEFAULT nextval('dischargetreatment_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50),
    ivfluids boolean,
    injvitamink boolean,
    ivantiniotics boolean,
    ivcalgluconate boolean,
    ivaminophylline boolean,
    ivphenobarbitone boolean,
    ivphenytoin boolean,
    ivlorazepam boolean,
    sypcalcium boolean,
    multivitamindrops boolean,
    irondrops boolean
);


ALTER TABLE discharge_treatment OWNER TO postgres;

--
-- TOC entry 335 (class 1259 OID 71357)
-- Name: dischargeventcourse_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargeventcourse_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargeventcourse_seq OWNER TO postgres;

--
-- TOC entry 336 (class 1259 OID 71359)
-- Name: discharge_ventcourse; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_ventcourse (
    ventcourseid bigint DEFAULT nextval('dischargeventcourse_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    vent_startdate date,
    vent_enddate date,
    initial_ventmode character varying(50),
    initial_fio2 character varying(50),
    initial_pip character varying(50),
    initial_peep character varying(50),
    initial_rate character varying(50),
    initial_tv character varying(50),
    initial_map character varying(50),
    maximum_ventmode character varying(50),
    maximum_fio2 character varying(50),
    maximum_pip character varying(50),
    maximum_peep character varying(50),
    maximum_rate character varying(50),
    maximum_tv character varying(50),
    maximum_map character varying(50),
    nicuventillationid bigint NOT NULL
);


ALTER TABLE discharge_ventcourse OWNER TO postgres;

--
-- TOC entry 337 (class 1259 OID 71366)
-- Name: discharge_ventcourse_nicuventillationid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE discharge_ventcourse_nicuventillationid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE discharge_ventcourse_nicuventillationid_seq OWNER TO postgres;

--
-- TOC entry 5270 (class 0 OID 0)
-- Dependencies: 337
-- Name: discharge_ventcourse_nicuventillationid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE discharge_ventcourse_nicuventillationid_seq OWNED BY discharge_ventcourse.nicuventillationid;


--
-- TOC entry 338 (class 1259 OID 71368)
-- Name: dischargeventilation_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargeventilation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargeventilation_seq OWNER TO postgres;

--
-- TOC entry 339 (class 1259 OID 71370)
-- Name: discharge_ventilation; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE discharge_ventilation (
    nicuventillationid bigint DEFAULT nextval('dischargeventilation_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    indication character varying(1000),
    ageatvent character varying(50),
    setting character varying(1000),
    ventcourseid character varying(50),
    reventilation boolean,
    total_vent_duration character varying(50),
    surfactantname character varying(100),
    surfacttant_age character varying(50),
    surfactant_doses character varying(50),
    complications character varying(500),
    complication_extubated character varying(50),
    cpap boolean,
    hoodoxygen boolean,
    nasal_prongs_oxygen boolean,
    cpap_startdate date,
    cpap_enddate date,
    hoodoxygen_startdate date,
    hoodoxygen_enddate date,
    nasal_startdate date,
    nasal_enddate date,
    cpap_fio2 character varying(50),
    hoodoxygen_fio2 character varying(50),
    nasal_fio2 character varying(50),
    cpap_litero2 character varying(50),
    hoodoxygen_litero2 character varying(50),
    nasal_litero2 character varying(50),
    inotropes character varying(100),
    sedation character varying(100),
    reventilation_detail character varying(500)
);


ALTER TABLE discharge_ventilation OWNER TO postgres;

--
-- TOC entry 340 (class 1259 OID 71377)
-- Name: dischargepatient_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE dischargepatient_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dischargepatient_seq OWNER TO postgres;

--
-- TOC entry 341 (class 1259 OID 71379)
-- Name: dischargepatient_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE dischargepatient_detail (
    dischargepatientid bigint DEFAULT nextval('dischargepatient_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    baby_discharge_status character varying(50) NOT NULL,
    additional_doctor_notes character varying(1000),
    discharge_print_flag character varying(10) DEFAULT 'TEMP'::character varying NOT NULL,
    dischargedate timestamp without time zone
);


ALTER TABLE dischargepatient_detail OWNER TO postgres;

--
-- TOC entry 342 (class 1259 OID 71387)
-- Name: doctemplate_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE doctemplate_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE doctemplate_seq OWNER TO postgres;

--
-- TOC entry 343 (class 1259 OID 71389)
-- Name: doctor_note_templates; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE doctor_note_templates (
    doctemplateid bigint DEFAULT nextval('doctemplate_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    template_header character varying(50) NOT NULL,
    template_body character varying(2000) NOT NULL,
    template_type character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL
);


ALTER TABLE doctor_note_templates OWNER TO postgres;

--
-- TOC entry 344 (class 1259 OID 71396)
-- Name: et_intubation_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE et_intubation_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE et_intubation_seq OWNER TO postgres;

--
-- TOC entry 345 (class 1259 OID 71398)
-- Name: et_intubation; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE et_intubation (
    et_intubation_id bigint DEFAULT nextval('et_intubation_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    site character varying(20),
    size character varying(10),
    fixation character varying(10),
    confirm_xray boolean,
    reposition character varying(10),
    insertion_timestamp timestamp with time zone,
    removal_timestamp timestamp with time zone,
    removal_reason character varying(50),
    loggeduser character varying(100),
    progressnotes text
);


ALTER TABLE et_intubation OWNER TO postgres;

--
-- TOC entry 346 (class 1259 OID 71405)
-- Name: exceptionlist_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE exceptionlist_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE exceptionlist_seq OWNER TO postgres;

--
-- TOC entry 347 (class 1259 OID 71407)
-- Name: exceptionlist; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE exceptionlist (
    exceptionid bigint DEFAULT nextval('exceptionlist_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    exceptiontype character varying(250) NOT NULL,
    loggedinuser character varying(50) NOT NULL,
    uhid character varying(50) NOT NULL,
    exceptionmessage text NOT NULL
);


ALTER TABLE exceptionlist OWNER TO postgres;

--
-- TOC entry 348 (class 1259 OID 71414)
-- Name: feedgrowth_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE feedgrowth_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE feedgrowth_seq OWNER TO postgres;

--
-- TOC entry 349 (class 1259 OID 71416)
-- Name: followup_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE followup_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE followup_seq OWNER TO postgres;

--
-- TOC entry 350 (class 1259 OID 71418)
-- Name: gen_phy_exam_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE gen_phy_exam_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE gen_phy_exam_seq OWNER TO postgres;

--
-- TOC entry 351 (class 1259 OID 71420)
-- Name: gen_phy_exam; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE gen_phy_exam (
    gen_phy_exam_id bigint DEFAULT nextval('gen_phy_exam_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    apearance character varying(50),
    skin character varying(50),
    skin_other character varying(500),
    head_neck character varying(50),
    head_neck_other character varying(500),
    eyes character varying(50),
    eyes_other character varying(500),
    palate character varying(50),
    lip character varying(50),
    anal character varying(50),
    genitals character varying(50),
    genitals_other character varying(500),
    reflexes character varying(50),
    reflexes_other character varying(500),
    cong_malform character varying(50),
    cong_malform_other character varying(500),
    other character varying(500),
    loggeduser character varying(50),
    chest character varying(50),
    chest_other character varying(500),
    abdomen character varying(50),
    abdomen_other character varying(500),
    lip_cleft_side boolean,
    anal_other character varying(50)
);


ALTER TABLE gen_phy_exam OWNER TO postgres;

--
-- TOC entry 352 (class 1259 OID 71427)
-- Name: hl7_device_mapping; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE hl7_device_mapping (
    devicemappingid bigint DEFAULT nextval('device_mapping_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    inicu_bedid character varying(50),
    inicu_roomid character varying(50),
    hl7_bedid character varying(50),
    hl7_roomid character varying(50),
    isactive boolean,
    time_to timestamp without time zone,
    uhid character varying(50)
);


ALTER TABLE hl7_device_mapping OWNER TO postgres;

--
-- TOC entry 353 (class 1259 OID 71431)
-- Name: hypokalemia_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE hypokalemia_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hypokalemia_seq OWNER TO postgres;

--
-- TOC entry 354 (class 1259 OID 71433)
-- Name: inicudueage; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE inicudueage (
    dueageid bigint NOT NULL,
    dueage character varying(50),
    isactivedueage boolean DEFAULT true,
    seq smallint,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE inicudueage OWNER TO postgres;

--
-- TOC entry 355 (class 1259 OID 71437)
-- Name: inicudueage_dueageid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE inicudueage_dueageid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE inicudueage_dueageid_seq OWNER TO postgres;

--
-- TOC entry 5271 (class 0 OID 0)
-- Dependencies: 355
-- Name: inicudueage_dueageid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE inicudueage_dueageid_seq OWNED BY inicudueage.dueageid;


--
-- TOC entry 356 (class 1259 OID 71439)
-- Name: iniculogs_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE iniculogs_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iniculogs_seq OWNER TO postgres;

--
-- TOC entry 357 (class 1259 OID 71441)
-- Name: iniculogs; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE iniculogs (
    logid bigint DEFAULT nextval('iniculogs_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    description character varying(4000) NOT NULL,
    action character varying(100) NOT NULL,
    loggeduser character varying(50) NOT NULL,
    pagename character varying(100) NOT NULL,
    patientdoctorid character varying(100)
);


ALTER TABLE iniculogs OWNER TO postgres;

--
-- TOC entry 358 (class 1259 OID 71448)
-- Name: user_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_seq OWNER TO postgres;

--
-- TOC entry 359 (class 1259 OID 71450)
-- Name: inicuuser; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE inicuuser (
    id bigint DEFAULT nextval('user_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    username character varying(100) NOT NULL,
    companyid character varying(100) NOT NULL,
    password character varying(100),
    emailaddress character varying(100),
    comments text,
    active smallint,
    deleted character varying(1) DEFAULT '0'::character varying,
    inactivedate date,
    firstname character varying(100),
    lastname character varying(50),
    designation character varying(50),
    mobile bigint,
    picture character varying(200),
    digitalsign character varying(200),
    reportingdoctor character varying(100),
    profilephoto bytea,
    signphoto bytea,
    loggeduser character varying(100)
);


ALTER TABLE inicuuser OWNER TO postgres;



--
-- TOC entry 361 (class 1259 OID 71462)
-- Name: investigationorder_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE investigationorder_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE investigationorder_seq OWNER TO postgres;

--
-- TOC entry 362 (class 1259 OID 71464)
-- Name: investigation_ordered; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE investigation_ordered (
    investigationorderid bigint DEFAULT nextval('investigationorder_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    assesment_type character varying(50),
    assesmentid character varying(50),
    uhid character varying(50),
    category character varying(50),
    testcode character varying(50),
    testname character varying(50),
    investigationorder_time timestamp with time zone,
    investigationorder_user character varying(50),
    senttolab_time timestamp with time zone,
    senttolab_user character varying(50),
    reportreceived_time timestamp with time zone,
    reportreceived_user character varying(50),
    order_status character varying(50),
    testslistid character varying(50),
    issamplesent boolean
);


ALTER TABLE investigation_ordered OWNER TO postgres;

--
-- TOC entry 363 (class 1259 OID 71471)
-- Name: logindetails_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE logindetails_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE logindetails_seq OWNER TO postgres;

--
-- TOC entry 364 (class 1259 OID 71473)
-- Name: logindetails; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE logindetails (
    loginid bigint DEFAULT nextval('logindetails_seq'::regclass) NOT NULL,
    userid character varying(25) NOT NULL,
    logintime timestamp without time zone NOT NULL,
    ipaddress character varying(25),
    logouttime timestamp without time zone
);


ALTER TABLE logindetails OWNER TO postgres;

--
-- TOC entry 365 (class 1259 OID 71477)
-- Name: lumbar_puncture_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE lumbar_puncture_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lumbar_puncture_seq OWNER TO postgres;

--
-- TOC entry 366 (class 1259 OID 71479)
-- Name: lumbar_puncture; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE lumbar_puncture (
    lumbar_puncture_id bigint DEFAULT nextval('lumbar_puncture_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    lumbar_space character varying(10),
    lumbar_size character varying(10),
    lumbar_csf character varying(50),
    lumbar_timestamp timestamp with time zone,
    loggeduser character varying(100),
    progressnotes text
);


ALTER TABLE lumbar_puncture OWNER TO postgres;

--
-- TOC entry 367 (class 1259 OID 71486)
-- Name: managerole_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE managerole_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE managerole_seq OWNER TO postgres;

--
-- TOC entry 368 (class 1259 OID 71488)
-- Name: master_address_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE master_address_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE master_address_seq OWNER TO postgres;

--
-- TOC entry 369 (class 1259 OID 71490)
-- Name: master_address; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE master_address (
    master_address_id bigint DEFAULT nextval('master_address_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    state character varying(100) NOT NULL,
    district character varying(100) NOT NULL,
    taluka character varying(100),
    city character varying(100) NOT NULL,
    pin_code character varying(10) NOT NULL
);


ALTER TABLE master_address OWNER TO postgres;

--
-- TOC entry 370 (class 1259 OID 71494)
-- Name: maternal_pasthistory; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE maternal_pasthistory (
    pasthistoryid bigint DEFAULT nextval('headtotoeexamination_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    past_delivery_year date,
    mode_of_delivery character varying(50),
    complications character varying(1000),
    outcomes character varying(1000),
    uhid character varying(50) NOT NULL
);


ALTER TABLE maternal_pasthistory OWNER TO postgres;

--
-- TOC entry 371 (class 1259 OID 71501)
-- Name: maternalpasthistory_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE maternalpasthistory_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE maternalpasthistory_seq OWNER TO postgres;

--
-- TOC entry 372 (class 1259 OID 71503)
-- Name: med_administration_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE med_administration_seq
    START WITH 89
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE med_administration_seq OWNER TO postgres;

--
-- TOC entry 373 (class 1259 OID 71505)
-- Name: med_administration; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE med_administration (
    medadminstrationid bigint DEFAULT nextval('med_administration_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    givenby character varying(150) NOT NULL,
    baby_presid bigint,
    giventime timestamp with time zone,
    loggeduser character varying(100) NOT NULL,
    scheduletime timestamp with time zone
);


ALTER TABLE med_administration OWNER TO postgres;

--
-- TOC entry 374 (class 1259 OID 71509)
-- Name: medicine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE medicine (
    medid character varying(20) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    userid character varying(50),
    medname character varying(200) NOT NULL,
    brand character varying(200),
    suspensiondoseunit character varying(10),
    isactive boolean,
    description character varying(1000),
    dosemultiplier real,
    dosemultiplierunit character varying(10),
    medicationtype character varying(200),
    suspensiondose real,
    frequency character varying(20) NOT NULL,
    formulaperdose boolean,
    forumulatperday boolean,
    othertype character varying(100)
);


ALTER TABLE medicine OWNER TO postgres;

--
-- TOC entry 375 (class 1259 OID 71515)
-- Name: misc_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE misc_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE misc_seq OWNER TO postgres;

--
-- TOC entry 376 (class 1259 OID 71517)
-- Name: module; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE module (
    moduleid bigint NOT NULL,
    module_name character varying(100),
    description text
);


ALTER TABLE module OWNER TO postgres;

--
-- TOC entry 377 (class 1259 OID 71523)
-- Name: mother_current_pregnancy; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE mother_current_pregnancy (
    currentpregnancyid bigint DEFAULT nextval('currentpregnancy_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    motherid character varying(50) NOT NULL,
    motherbloodgroup character varying(10),
    lmp date,
    edd date,
    g_score character varying(50) DEFAULT 0,
    a_score character varying(50) DEFAULT 0,
    p_score character varying(50) DEFAULT 0,
    l_score character varying(50) DEFAULT 0,
    deliverymode character varying(50),
    apgar_score_1min integer DEFAULT 0,
    apgar_score_5min integer DEFAULT 0,
    apgar_score_10min integer DEFAULT 0,
    antibodies_detected boolean,
    antigen character varying(50),
    antibody character varying(50),
    smoking_status boolean,
    cigarperday character varying(10),
    drinking_status boolean,
    unitperday character varying(10),
    immunized boolean,
    vitamink_status character varying(50),
    vitamink_dose real,
    vitamink_givenby character varying(10),
    antenatal_steroids character varying(50),
    anc character varying(50),
    flatus_tube character varying(50),
    resuscitation boolean,
    ippr_shifted boolean,
    cause character varying(2000),
    labor boolean,
    dateofonset date,
    timeofonset character varying(10),
    modeofonset character varying(20),
    modeofonset_comments character varying(200),
    induction_method character varying(20),
    others character varying(200),
    membrane_returned boolean,
    membrane_returned_time character varying(10),
    vaginal character varying(20),
    presentation character varying(20),
    csection character varying(20),
    labor_duration_1ststage integer,
    labor_duration_2ndstage integer,
    indication character varying(200),
    fetal_distress boolean,
    cordgas1 character varying(10),
    cordgas1_bex character varying(50),
    cordgas2 character varying(10),
    cordgas2_bex character varying(50),
    conium_cords boolean,
    ctg_normal boolean,
    surfactant_status boolean,
    surfactant_dose integer,
    freshme_corium boolean,
    timetofirstbreath character varying(10),
    timetoregularresps character varying(10),
    delivery_description character varying(500),
    delivery_outcome character varying(20),
    delivery_shiftedto character varying(20),
    resuscitation_comments character varying(2000),
    ctgnormal_comments character varying(2000),
    deliverymode_type character varying(50),
    breech_delivery boolean,
    antinatal_vaccinations boolean,
    antenatal_suplements boolean,
    maternal_illness character varying(500),
    maternal_medications character varying(2000)
);


ALTER TABLE mother_current_pregnancy OWNER TO postgres;

--
-- TOC entry 378 (class 1259 OID 71537)
-- Name: nursing_bloodgas_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_bloodgas_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_bloodgas_seq OWNER TO postgres;

--
-- TOC entry 379 (class 1259 OID 71539)
-- Name: nursing_bloodgas; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_bloodgas (
    nn_bloodgasid bigint DEFAULT nextval('nursing_bloodgas_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_bloodgas_time character varying(50) NOT NULL,
    ph character varying(20),
    pco2 character varying(20),
    hco2 character varying(20),
    po2 character varying(20),
    be character varying(20),
    loggeduser character varying(50) NOT NULL,
    comments character varying(1000),
    lactate character varying(50),
    spo2 character varying(20),
    na character varying(20),
    k character varying(20),
    cl character varying(20),
    glucose character varying(20),
    ionized_calcium character varying(20),
    regular_hco3 character varying(20),
    be_ecf character varying(20),
    userdate character varying(255)
);


ALTER TABLE nursing_bloodgas OWNER TO postgres;

--
-- TOC entry 380 (class 1259 OID 71546)
-- Name: nursing_bloodproducts_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_bloodproducts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_bloodproducts_seq OWNER TO postgres;

--
-- TOC entry 381 (class 1259 OID 71548)
-- Name: nursing_bloodproducts; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_bloodproducts (
    nn_bloodproductid bigint DEFAULT nextval('nursing_bloodproducts_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_blood_time character varying(50),
    bloodproduct character varying(50),
    dose character varying(50),
    duration character varying(50),
    bloodgroup character varying(1000),
    loggeduser character varying(100) NOT NULL,
    starttime character varying(50)
);


ALTER TABLE nursing_bloodproducts OWNER TO postgres;

--
-- TOC entry 382 (class 1259 OID 71555)
-- Name: nursing_bolus_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_bolus_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_bolus_seq OWNER TO postgres;

--
-- TOC entry 383 (class 1259 OID 71557)
-- Name: nursing_bolus; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_bolus (
    nn_bolusid bigint DEFAULT nextval('nursing_bolus_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_bolus_time character varying(50),
    bolustype character varying(50),
    dose character varying(50),
    duration character varying(50),
    loggeduser character varying(100) NOT NULL,
    starttime character varying(50)
);


ALTER TABLE nursing_bolus OWNER TO postgres;

--
-- TOC entry 384 (class 1259 OID 71561)
-- Name: nursing_catheters_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_catheters_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_catheters_seq OWNER TO postgres;

--
-- TOC entry 385 (class 1259 OID 71563)
-- Name: nursing_catheters; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_catheters (
    nn_cathetersid bigint DEFAULT nextval('nursing_catheters_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_catheters_time character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    catheters_line character varying(50),
    catheters_line_type character varying(50),
    catheters_day character varying(50),
    catheters_startdate date,
    catheters_enddate date,
    comments character varying(500)
);


ALTER TABLE nursing_catheters OWNER TO postgres;

--
-- TOC entry 386 (class 1259 OID 71570)
-- Name: nursing_dailyassesment_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_dailyassesment_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_dailyassesment_seq OWNER TO postgres;

--
-- TOC entry 387 (class 1259 OID 71572)
-- Name: nursing_dailyassesment; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_dailyassesment (
    nn_assesmentid bigint DEFAULT nextval('nursing_dailyassesment_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    dailyassesment_time character varying(50) NOT NULL,
    eye_care boolean,
    mother_care boolean,
    trach_etcare boolean,
    cuff_check boolean,
    chest_pt boolean,
    limb_pt boolean,
    back_care boolean,
    sponge_bath boolean,
    hair_care boolean,
    radial_pulse_left boolean,
    radial_pulse_right boolean,
    pedal_pulse_left boolean,
    pedal_pulse_right boolean,
    describe_sore character varying(100),
    action_sore character varying(100),
    skinintegrity character varying(100),
    pressuresite character varying(100),
    comments character varying(1000)
);


ALTER TABLE nursing_dailyassesment OWNER TO postgres;

--
-- TOC entry 388 (class 1259 OID 71579)
-- Name: nursing_drain_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_drain_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_drain_seq OWNER TO postgres;

--
-- TOC entry 389 (class 1259 OID 71581)
-- Name: nursing_episode_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_episode_seq
    START WITH 22
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_episode_seq OWNER TO postgres;

--
-- TOC entry 390 (class 1259 OID 71583)
-- Name: nursing_episode; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_episode (
    episodeid bigint DEFAULT nextval('nursing_episode_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    apnea boolean,
    bradycardia boolean,
    hr character varying(10),
    disaturation boolean,
    spo2 character varying(10),
    recovery character varying(50),
    nn_vitalparameter_time character varying(50),
    seizures boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    seizure_duration character varying(10),
    seizure_type character varying(200),
    tachycardia boolean
);


ALTER TABLE nursing_episode OWNER TO postgres;

--
-- TOC entry 391 (class 1259 OID 71590)
-- Name: nursing_intake_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_intake_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_intake_seq OWNER TO postgres;

--
-- TOC entry 392 (class 1259 OID 71592)
-- Name: nursing_intake; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_intake (
    nn_intakeid bigint DEFAULT nextval('nursing_intake_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_intake_time character varying(50) NOT NULL,
    feedmethod character varying(50),
    feedtype character varying(50),
    feedvolume real,
    hmfvalue character varying(50),
    ivtype character varying(50),
    ivperhr real,
    pnrate character varying(50),
    aaperhr real,
    lipidperhr real,
    ivtotal real,
    loggeduser character varying(50) NOT NULL,
    comments character varying(1000)
);


ALTER TABLE nursing_intake OWNER TO postgres;

--
-- TOC entry 393 (class 1259 OID 71599)
-- Name: nursing_intake_output; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_intake_output (
    nursing_intakeid bigint DEFAULT nextval('nursing_intake_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    babyfeedid character varying(50) NOT NULL,
    nursing_entry_time character varying(50) NOT NULL,
    primary_feed_type character varying(50),
    primary_feed_value real,
    formula_type character varying(50),
    formula_value real,
    lipid_total_volume real,
    lipid_delivered real,
    lipid_remaining real,
    tpn_total_volume real,
    tpn_delivered real,
    tpn_remaining real,
    abdomen_girth character varying(50),
    gastric_aspirate character varying(50),
    urine character varying(50),
    stool character varying(50),
    vomit character varying(50),
    drain character varying(500),
    loggeduser character varying(50) NOT NULL,
    actual_feed real,
    others text
);


ALTER TABLE nursing_intake_output OWNER TO postgres;

--
-- TOC entry 394 (class 1259 OID 71606)
-- Name: nursing_misc_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_misc_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_misc_seq OWNER TO postgres;

--
-- TOC entry 395 (class 1259 OID 71608)
-- Name: nursing_misc; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_misc (
    nn_miscid bigint DEFAULT nextval('nursing_misc_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_misc_time character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    abd_grith character varying(50),
    hgt character varying(50),
    phototherapy_start_date timestamp without time zone,
    phototherapy_end_date timestamp without time zone,
    comments character varying(1000),
    phototherapy_type character varying(100)
);


ALTER TABLE nursing_misc OWNER TO postgres;

--
-- TOC entry 396 (class 1259 OID 71615)
-- Name: nursing_neurovitals_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_neurovitals_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_neurovitals_seq OWNER TO postgres;

--
-- TOC entry 397 (class 1259 OID 71617)
-- Name: nursing_neurovitals; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_neurovitals (
    nn_neorovitalsid bigint DEFAULT nextval('nursing_neurovitals_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_neurovitals_time character varying(50) NOT NULL,
    sedation_score character varying(50),
    gcs character varying(50),
    icp integer,
    ccp integer,
    loggeduser character varying(50) NOT NULL,
    comments character varying(1000)
);


ALTER TABLE nursing_neurovitals OWNER TO postgres;

--
-- TOC entry 398 (class 1259 OID 71624)
-- Name: nursingnotes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingnotes_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingnotes_seq OWNER TO postgres;

--
-- TOC entry 399 (class 1259 OID 71626)
-- Name: nursing_notes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_notes (
    nursingnoteid bigint DEFAULT nextval('nursingnotes_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nursingnotestime character varying(50) NOT NULL,
    feedmethod character varying(50),
    feedtype character varying(50),
    feedvolume real,
    hmfvalue character varying(50),
    ivtype character varying(50),
    pnrate character varying(50),
    ivperhr real,
    aaperhr real,
    lipidperhr real,
    ivtotal real,
    aspire_ml real,
    urine_ml real,
    bo_colour character varying(50),
    episode character varying(10),
    passstool boolean,
    stool character varying(20),
    loggeduser character varying(100)
);


ALTER TABLE nursing_notes OWNER TO postgres;

--
-- TOC entry 400 (class 1259 OID 71633)
-- Name: nursing_output_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_output_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_output_seq OWNER TO postgres;

--
-- TOC entry 401 (class 1259 OID 71635)
-- Name: nursing_output; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_output (
    nn_outputid bigint DEFAULT nextval('nursing_output_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    nn_output_time character varying(50) NOT NULL,
    aspirate_quantity character varying(50),
    aspirate_type character varying(50),
    urine_mls character varying(50),
    urine_mlkg character varying(50),
    total_uo character varying(50),
    blood_letting character varying(50),
    bowel_type character varying(50),
    bowel_color character varying(50),
    comments character varying(1000),
    bowel_status boolean
);


ALTER TABLE nursing_output OWNER TO postgres;

--
-- TOC entry 402 (class 1259 OID 71642)
-- Name: nursing_outputdrain; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_outputdrain (
    nn_drainid bigint DEFAULT nextval('nursing_drain_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    nn_drain_time character varying(50) NOT NULL,
    drain1_input character varying(50),
    drain1_output character varying(50),
    drain2_input character varying(50),
    drain2_output character varying(50),
    drain3_input character varying(50),
    drain3_output character varying(50)
);


ALTER TABLE nursing_outputdrain OWNER TO postgres;

--
-- TOC entry 403 (class 1259 OID 71649)
-- Name: nursing_ventilaor_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_ventilaor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_ventilaor_seq OWNER TO postgres;

--
-- TOC entry 404 (class 1259 OID 71651)
-- Name: nursing_ventilaor; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_ventilaor (
    nn_ventilaorid bigint DEFAULT nextval('nursing_ventilaor_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_ventilaor_time character varying(50) NOT NULL,
    ventmode character varying(50),
    pip character varying(20),
    peep_cpap character varying(20),
    pressure_supply character varying(20),
    map character varying(20),
    freq_rate character varying(20),
    tidal_volume character varying(20),
    minute_volume character varying(20),
    ti character varying(20),
    fio2 character varying(20),
    flow_per_min character varying(20),
    no_ppm character varying(50),
    ett_quantity character varying(50),
    ett_color character varying(50),
    loggeduser character varying(50) NOT NULL,
    comments character varying(1000),
    userdate character varying(255)
);


ALTER TABLE nursing_ventilaor OWNER TO postgres;

--
-- TOC entry 405 (class 1259 OID 71658)
-- Name: nursing_vitalparameters_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursing_vitalparameters_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursing_vitalparameters_seq OWNER TO postgres;

--
-- TOC entry 406 (class 1259 OID 71660)
-- Name: nursing_vitalparameters; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursing_vitalparameters (
    nn_vitalparameterid bigint DEFAULT nextval('nursing_vitalparameters_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    nn_vitalparameter_time character varying(50) NOT NULL,
    vp_position character varying(50),
    hr_rate real,
    skintemp real,
    coretemp real,
    syst_bp character varying(20),
    diast_bp character varying(20),
    mean_bp character varying(20),
    cvp character varying(50),
    rr_rate real,
    lax character varying(20),
    rax character varying(20),
    spo2 character varying(20),
    etco2 character varying(20),
    loggeduser character varying(50) NOT NULL,
    comments character varying(1000),
    abd_grith character varying(50),
    rbs character varying(50),
    ivp character varying(20),
    hypoglycemia boolean,
    hyperglycemia boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    centraltemp real,
    peripheraltemp real,
    cft character varying(20),
    tempdifference real,
    systibp real,
    diastibp real,
    meanibp real,
    userdate character varying(255),
    entrydate timestamp without time zone
);


ALTER TABLE nursing_vitalparameters OWNER TO postgres;

--
-- TOC entry 407 (class 1259 OID 71667)
-- Name: nursingorderapnea_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingorderapnea_seq
    START WITH 26
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingorderapnea_seq OWNER TO postgres;

--
-- TOC entry 408 (class 1259 OID 71669)
-- Name: nursingorder_apnea; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursingorder_apnea (
    nursingorderapneaid bigint DEFAULT nextval('nursingorderapnea_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    apneaid character varying(50),
    uhid character varying(50),
    caffeine_actiontime timestamp with time zone,
    respiratory_support_actiontime timestamp with time zone,
    nursing_comments character varying(1000)
);


ALTER TABLE nursingorder_apnea OWNER TO postgres;

--
-- TOC entry 409 (class 1259 OID 71676)
-- Name: nursingorder_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingorder_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingorder_seq OWNER TO postgres;

--
-- TOC entry 410 (class 1259 OID 71678)
-- Name: nursingorder_assesment; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursingorder_assesment (
    nursingorderid bigint DEFAULT nextval('nursingorder_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    eventid character varying(50),
    eventname character varying(50),
    assessment_type character varying(50),
    uhid character varying(50),
    actiontype character varying(50),
    actionvalue character varying(200),
    actiontaken_time timestamp with time zone,
    loggeduser character varying(100),
    nursing_comments character varying(1000)
);


ALTER TABLE nursingorder_assesment OWNER TO postgres;

--
-- TOC entry 411 (class 1259 OID 71685)
-- Name: nursingorderjaundice_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingorderjaundice_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingorderjaundice_seq OWNER TO postgres;

--
-- TOC entry 412 (class 1259 OID 71687)
-- Name: nursingorder_jaundice; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursingorder_jaundice (
    nursingorderjaundiceid bigint DEFAULT nextval('nursingorderjaundice_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    sajaundiceid character varying(50),
    uhid character varying(50),
    actiontype character varying(50),
    phototherapyvalue character varying(50),
    exchangetrans boolean,
    ivigvalue character varying(50),
    phototherapy_actiontime timestamp with time zone,
    exchangetrans_actiontime timestamp with time zone,
    ivigvalue_actiontime timestamp with time zone,
    nursing_comments character varying(1000)
);


ALTER TABLE nursingorder_jaundice OWNER TO postgres;

--
-- TOC entry 413 (class 1259 OID 71694)
-- Name: nursingorderrds_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingorderrds_seq
    START WITH 26
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingorderrds_seq OWNER TO postgres;

--
-- TOC entry 414 (class 1259 OID 71696)
-- Name: nursingorder_rds; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursingorder_rds (
    nursingorderrdsid bigint DEFAULT nextval('nursingorderrds_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    rdsid character varying(50),
    uhid character varying(50),
    surfactant_actiontime timestamp with time zone,
    respiratory_support_actiontime timestamp with time zone,
    medicine_actiontime timestamp with time zone,
    nursing_comments character varying(1000)
);


ALTER TABLE nursingorder_rds OWNER TO postgres;

--
-- TOC entry 415 (class 1259 OID 71703)
-- Name: nursingorderrds_medicine_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE nursingorderrds_medicine_seq
    START WITH 26
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE nursingorderrds_medicine_seq OWNER TO postgres;

--
-- TOC entry 416 (class 1259 OID 71705)
-- Name: nursingorder_rds_medicine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE nursingorder_rds_medicine (
    nursingorderrds_medicineid bigint DEFAULT nextval('nursingorderrds_medicine_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    rdsid character varying(50),
    uhid character varying(50),
    babypresc_id character varying(50),
    medicineid character varying(50),
    nursing_comments character varying(1000),
    medicine_actiontime timestamp with time zone
);


ALTER TABLE nursingorder_rds_medicine OWNER TO postgres;

--
-- TOC entry 417 (class 1259 OID 71712)
-- Name: oralfeed_detail; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE oralfeed_detail (
    oralfeedid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    babyfeedid character varying(50),
    feedtype_id character varying(50),
    feedvolume real,
    totalfeedvolume real,
    entrydatetime timestamp with time zone
);


ALTER TABLE oralfeed_detail OWNER TO postgres;

--
-- TOC entry 418 (class 1259 OID 71715)
-- Name: oralfeed_detail_oralfeedid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE oralfeed_detail_oralfeedid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE oralfeed_detail_oralfeedid_seq OWNER TO postgres;

--
-- TOC entry 5272 (class 0 OID 0)
-- Dependencies: 418
-- Name: oralfeed_detail_oralfeedid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE oralfeed_detail_oralfeedid_seq OWNED BY oralfeed_detail.oralfeedid;


--
-- TOC entry 419 (class 1259 OID 71717)
-- Name: outborn_medicine_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE outborn_medicine_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE outborn_medicine_seq OWNER TO postgres;

--
-- TOC entry 420 (class 1259 OID 71719)
-- Name: outborn_medicine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE outborn_medicine (
    outborn_medicine_id bigint DEFAULT nextval('outborn_medicine_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    medicine_name character varying(500),
    medicine_dose character varying(50),
    medicine_cummulative_dose character varying(50),
    loggeduser character varying(50)
);


ALTER TABLE outborn_medicine OWNER TO postgres;

--
-- TOC entry 421 (class 1259 OID 71726)
-- Name: patient_due_vaccine_dtls; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE patient_due_vaccine_dtls (
    patientduervaccineid bigint NOT NULL,
    uid character varying(50),
    vaccineinfoid bigint,
    duedate date,
    givendate date,
    administratedby character varying(50),
    brandid bigint,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE patient_due_vaccine_dtls OWNER TO postgres;

--
-- TOC entry 422 (class 1259 OID 71729)
-- Name: patient_due_vaccine_dtls_patientduervaccineid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE patient_due_vaccine_dtls_patientduervaccineid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE patient_due_vaccine_dtls_patientduervaccineid_seq OWNER TO postgres;

--
-- TOC entry 5274 (class 0 OID 0)
-- Dependencies: 422
-- Name: patient_due_vaccine_dtls_patientduervaccineid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE patient_due_vaccine_dtls_patientduervaccineid_seq OWNED BY patient_due_vaccine_dtls.patientduervaccineid;


--
-- TOC entry 423 (class 1259 OID 71731)
-- Name: patient_prescription; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE patient_prescription (
);


ALTER TABLE patient_prescription OWNER TO postgres;

--
-- TOC entry 5275 (class 0 OID 0)
-- Dependencies: 423
-- Name: TABLE patient_prescription; Type: COMMENT; Schema: kdah; Owner: postgres
--

COMMENT ON TABLE patient_prescription IS 'temporary table will modified by kabita';



--
-- TOC entry 428 (class 1259 OID 71754)
-- Name: peripheral_cannula_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE peripheral_cannula_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE peripheral_cannula_seq OWNER TO postgres;

--
-- TOC entry 429 (class 1259 OID 71756)
-- Name: peripheral_cannula; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE peripheral_cannula (
    peripheral_cannula_id bigint DEFAULT nextval('peripheral_cannula_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    site character varying(10) NOT NULL,
    limb_site character varying(10),
    limb_site_other text,
    limb_side character varying(10),
    size character varying(10),
    insertion_timestamp timestamp with time zone,
    removal_timestamp timestamp with time zone,
    removal_reason character varying(50),
    loggeduser character varying(100),
    progressnotes text
);


ALTER TABLE peripheral_cannula OWNER TO postgres;

--
-- TOC entry 430 (class 1259 OID 71763)
-- Name: pref_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE pref_seq
    START WITH 2
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pref_seq OWNER TO postgres;

--
-- TOC entry 431 (class 1259 OID 71765)
-- Name: preference; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE preference (
    id bigint DEFAULT nextval('pref_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    preference character varying(50),
    prefid character varying(10)
);


ALTER TABLE preference OWNER TO postgres;

--
-- TOC entry 432 (class 1259 OID 71769)
-- Name: procedure_chesttube_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE procedure_chesttube_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE procedure_chesttube_seq OWNER TO postgres;

--
-- TOC entry 433 (class 1259 OID 71771)
-- Name: procedure_chesttube; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE procedure_chesttube (
    id bigint DEFAULT nextval('procedure_chesttube_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    entrytime timestamp with time zone NOT NULL,
    uhid character varying(100) NOT NULL,
    episodeid character varying(100),
    loggeduser character varying(100),
    ischesttube_right boolean,
    ischesttube_left boolean,
    insertedfor character varying(100),
    size character varying(50),
    tubeposition character varying(100),
    sizeatfixation character varying(50),
    confirmedbyxray boolean,
    repositionat real,
    inserteddate timestamp with time zone,
    removaldate timestamp with time zone,
    reasonofremoval character varying(100),
    otherindication text
);


ALTER TABLE procedure_chesttube OWNER TO postgres;

--
-- TOC entry 434 (class 1259 OID 71778)
-- Name: procedure_other_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE procedure_other_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE procedure_other_seq OWNER TO postgres;

--
-- TOC entry 435 (class 1259 OID 71780)
-- Name: procedure_other; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE procedure_other (
    procedure_other_id bigint DEFAULT nextval('procedure_other_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    procedurename text,
    details text,
    loggeduser character varying(100)
);


ALTER TABLE procedure_other OWNER TO postgres;

--
-- TOC entry 436 (class 1259 OID 71787)
-- Name: pupilreactivity_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE pupilreactivity_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pupilreactivity_seq OWNER TO postgres;

--
-- TOC entry 437 (class 1259 OID 71789)
-- Name: pupil_reactivity; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE pupil_reactivity (
    pupilreactivityid bigint DEFAULT nextval('pupilreactivity_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    left_reactivity character varying(50),
    left_pupilsize character varying(50),
    equality character varying(50),
    comments character varying(1000),
    right_reactivity character varying(50),
    right_pupilsize character varying(50),
    pupil_time character varying(50)
);


ALTER TABLE pupil_reactivity OWNER TO postgres;

--
-- TOC entry 438 (class 1259 OID 71796)
-- Name: readmit_history_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE readmit_history_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE readmit_history_seq OWNER TO postgres;

--
-- TOC entry 439 (class 1259 OID 71798)
-- Name: readmit_history; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE readmit_history (
    readmitid bigint DEFAULT nextval('readmit_history_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    admissiondate date NOT NULL,
    dischargeddate date NOT NULL,
    nicubedno character varying(20),
    nicuroomno character varying(20),
    niculevelno character varying(10),
    criticalitylevel character varying(10),
    readmissiondate date NOT NULL
);


ALTER TABLE readmit_history OWNER TO postgres;



--
-- TOC entry 442 (class 1259 OID 71812)
-- Name: reason_admission_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE reason_admission_seq
    START WITH 201
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reason_admission_seq OWNER TO postgres;

--
-- TOC entry 443 (class 1259 OID 71814)
-- Name: reason_admission; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE reason_admission (
    reason_admission_id bigint DEFAULT nextval('reason_admission_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    cause_event character varying(100) NOT NULL,
    cause_value character varying(1000),
    loggeduser character varying(100) NOT NULL,
    episodeid character varying(50) NOT NULL,
    cause_value_other text
);


ALTER TABLE reason_admission OWNER TO postgres;

--
-- TOC entry 444 (class 1259 OID 71821)
-- Name: ref_antibiotics; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_antibiotics (
    antibioticsid character varying(10) NOT NULL,
    antibiotics character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_antibiotics OWNER TO postgres;

--
-- TOC entry 445 (class 1259 OID 71824)
-- Name: ref_apneacause; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_apneacause (
    apneacauseid character varying(10) NOT NULL,
    apneacause character varying(20) NOT NULL,
    description character varying(20) NOT NULL,
    seq integer
);


ALTER TABLE ref_apneacause OWNER TO postgres;

--
-- TOC entry 446 (class 1259 OID 71827)
-- Name: ref_assesment_treatment; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_assesment_treatment (
    assesmenttreatmentid character varying(10) NOT NULL,
    category character varying(100) NOT NULL,
    treatment character varying(100) NOT NULL,
    description character varying(100) NOT NULL
);


ALTER TABLE ref_assesment_treatment OWNER TO postgres;

--
-- TOC entry 447 (class 1259 OID 71830)
-- Name: ref_bellscore; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_bellscore (
    ref_bellscoreid bigint NOT NULL,
    clinical_status character varying(100) NOT NULL,
    clinical_sign character varying(500) NOT NULL,
    bellscore character varying(10) NOT NULL
);


ALTER TABLE ref_bellscore OWNER TO postgres;

--
-- TOC entry 448 (class 1259 OID 71836)
-- Name: ref_bellscore_ref_bellscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_bellscore_ref_bellscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_bellscore_ref_bellscoreid_seq OWNER TO postgres;

--
-- TOC entry 5276 (class 0 OID 0)
-- Dependencies: 448
-- Name: ref_bellscore_ref_bellscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_bellscore_ref_bellscoreid_seq OWNED BY ref_bellscore.ref_bellscoreid;


--
-- TOC entry 449 (class 1259 OID 71838)
-- Name: ref_bindscore; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_bindscore (
    ref_bindscoreid bigint NOT NULL,
    clinical_status character varying(100) NOT NULL,
    clinical_sign character varying(500) NOT NULL,
    bindscore integer NOT NULL
);


ALTER TABLE ref_bindscore OWNER TO postgres;

--
-- TOC entry 450 (class 1259 OID 71844)
-- Name: ref_bindscore_ref_bindscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_bindscore_ref_bindscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_bindscore_ref_bindscoreid_seq OWNER TO postgres;

--
-- TOC entry 5277 (class 0 OID 0)
-- Dependencies: 450
-- Name: ref_bindscore_ref_bindscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_bindscore_ref_bindscoreid_seq OWNED BY ref_bindscore.ref_bindscoreid;


--
-- TOC entry 451 (class 1259 OID 71846)
-- Name: ref_blood_product; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_blood_product (
    bpid character varying(10) NOT NULL,
    blood_product character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_blood_product OWNER TO postgres;

--
-- TOC entry 452 (class 1259 OID 71849)
-- Name: ref_cathetersline; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_cathetersline (
    catheterslineid character varying(10) NOT NULL,
    catheters_line character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_cathetersline OWNER TO postgres;

--
-- TOC entry 453 (class 1259 OID 71852)
-- Name: ref_causeofcns; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_causeofcns (
    causeofcnsid character varying(10) NOT NULL,
    causeofcns character varying(50) NOT NULL,
    event character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    seq integer
);


ALTER TABLE ref_causeofcns OWNER TO postgres;

--
-- TOC entry 454 (class 1259 OID 71855)
-- Name: ref_causeofinfection; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_causeofinfection (
    causeofinfectionid character varying(10) NOT NULL,
    causeofinfection character varying(50) NOT NULL,
    event character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    seq integer
);


ALTER TABLE ref_causeofinfection OWNER TO postgres;

--
-- TOC entry 455 (class 1259 OID 71858)
-- Name: ref_causeofjaundice; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_causeofjaundice (
    causeofjaundiceid character varying(10) NOT NULL,
    causeofjaundice character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    seq integer
);


ALTER TABLE ref_causeofjaundice OWNER TO postgres;

--
-- TOC entry 456 (class 1259 OID 71861)
-- Name: ref_causeofmetabolic; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_causeofmetabolic (
    causeofmetabolicid character varying(10) NOT NULL,
    causeofmetabolic character varying(50) NOT NULL,
    event character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    seq integer
);


ALTER TABLE ref_causeofmetabolic OWNER TO postgres;

--
-- TOC entry 457 (class 1259 OID 71864)
-- Name: ref_causeofrespiratory; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_causeofrespiratory (
    causeofrespid character varying(10) NOT NULL,
    causeofresp character varying(50) NOT NULL,
    event character varying(50) NOT NULL,
    description character varying(50) NOT NULL,
    seq integer
);


ALTER TABLE ref_causeofrespiratory OWNER TO postgres;

--
-- TOC entry 458 (class 1259 OID 71867)
-- Name: ref_chd; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_chd (
    chdid character varying(10) NOT NULL,
    chd character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_chd OWNER TO postgres;

--
-- TOC entry 459 (class 1259 OID 71870)
-- Name: ref_cldstage; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_cldstage (
    cldstageid character varying(10) NOT NULL,
    cldstage character varying(10) NOT NULL,
    description character varying(10) NOT NULL
);


ALTER TABLE ref_cldstage OWNER TO postgres;

--
-- TOC entry 460 (class 1259 OID 71873)
-- Name: ref_csfculture; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_csfculture (
    csfcultureid character varying(10) NOT NULL,
    csfculture character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_csfculture OWNER TO postgres;

--
-- TOC entry 461 (class 1259 OID 71876)
-- Name: ref_device_type; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_device_type (
    devicetypeid bigint NOT NULL,
    device_type character varying(50) NOT NULL,
    description character varying(50)
);


ALTER TABLE ref_device_type OWNER TO postgres;

--
-- TOC entry 462 (class 1259 OID 71879)
-- Name: ref_ettsecsize; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_ettsecsize (
    sizeid character varying(10) NOT NULL,
    ettsize character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_ettsecsize OWNER TO postgres;

--
-- TOC entry 463 (class 1259 OID 71882)
-- Name: ref_examination; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_examination (
    exid character varying(10) NOT NULL,
    examination character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_examination OWNER TO postgres;

--
-- TOC entry 464 (class 1259 OID 71885)
-- Name: ref_fenton; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_fenton (
    id bigint NOT NULL,
    gender character varying(10),
    age character varying(10),
    percentile character varying(10),
    weight real,
    height real,
    headcircum real
);


ALTER TABLE ref_fenton OWNER TO postgres;

--
-- TOC entry 465 (class 1259 OID 71888)
-- Name: ref_fenton_id_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_fenton_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_fenton_id_seq OWNER TO postgres;

--
-- TOC entry 5278 (class 0 OID 0)
-- Dependencies: 465
-- Name: ref_fenton_id_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_fenton_id_seq OWNED BY ref_fenton.id;


--
-- TOC entry 466 (class 1259 OID 71890)
-- Name: ref_gestation; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_gestation (
    gesid integer NOT NULL,
    gestation integer NOT NULL,
    description character varying(50)
);


ALTER TABLE ref_gestation OWNER TO postgres;

--
-- TOC entry 467 (class 1259 OID 71893)
-- Name: ref_headtotoe; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_headtotoe (
    htid character varying(10) NOT NULL,
    headtotoe character varying(50) NOT NULL,
    description character varying(50)
);


ALTER TABLE ref_headtotoe OWNER TO postgres;

--
-- TOC entry 468 (class 1259 OID 71896)
-- Name: ref_history; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_history (
    hisid character varying(10) NOT NULL,
    history character varying(50) NOT NULL
);


ALTER TABLE ref_history OWNER TO postgres;

--
-- TOC entry 469 (class 1259 OID 71899)
-- Name: ref_inicu_bbox; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_inicu_bbox (
    bbox_id bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    bboxname character varying(50) NOT NULL,
    active boolean
);


ALTER TABLE ref_inicu_bbox OWNER TO postgres;

--
-- TOC entry 470 (class 1259 OID 71902)
-- Name: ref_inicu_bbox_boards; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_inicu_bbox_boards (
    board_id bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    bboard_macid character varying(50),
    bboard_name character varying(50),
    bboxid bigint,
    active boolean
);


ALTER TABLE ref_inicu_bbox_boards OWNER TO postgres;

--
-- TOC entry 471 (class 1259 OID 71905)
-- Name: ref_inicu_devices; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_inicu_devices (
    deviceid bigint NOT NULL,
    devicename character varying(50),
    brandname character varying(50),
    devicetypeid bigint
);


ALTER TABLE ref_inicu_devices OWNER TO postgres;

--
-- TOC entry 472 (class 1259 OID 71908)
-- Name: ref_inotropes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_inotropes (
    inotropeid character varying(10) NOT NULL,
    inotrope character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_inotropes OWNER TO postgres;

--
-- TOC entry 473 (class 1259 OID 71911)
-- Name: ref_jaundiceriskfactor; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_jaundiceriskfactor (
    riskfactorid character varying(10) NOT NULL,
    riskfactor character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_jaundiceriskfactor OWNER TO postgres;

--
-- TOC entry 474 (class 1259 OID 71914)
-- Name: ref_levenescore; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_levenescore (
    ref_levenescoreid bigint NOT NULL,
    clinical_status character varying(100) NOT NULL,
    clinical_sign character varying(500) NOT NULL,
    levenescore integer NOT NULL
);


ALTER TABLE ref_levenescore OWNER TO postgres;

--
-- TOC entry 475 (class 1259 OID 71920)
-- Name: ref_levenescore_ref_levenescoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_levenescore_ref_levenescoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_levenescore_ref_levenescoreid_seq OWNER TO postgres;

--
-- TOC entry 5279 (class 0 OID 0)
-- Dependencies: 475
-- Name: ref_levenescore_ref_levenescoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_levenescore_ref_levenescoreid_seq OWNED BY ref_levenescore.ref_levenescoreid;


--
-- TOC entry 476 (class 1259 OID 71922)
-- Name: ref_masteraspirate; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_masteraspirate (
    aspirateid character varying(10) NOT NULL,
    aspiratecolor character varying(20) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_masteraspirate OWNER TO postgres;

--
-- TOC entry 477 (class 1259 OID 71925)
-- Name: ref_masterbo; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_masterbo (
    masterboid character varying(10) NOT NULL,
    masterboname character varying(20) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_masterbo OWNER TO postgres;

--
-- TOC entry 478 (class 1259 OID 71928)
-- Name: ref_masterfeedmethod; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_masterfeedmethod (
    feedmethodid character varying(10) NOT NULL,
    feedmethodname character varying(20) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_masterfeedmethod OWNER TO postgres;

--
-- TOC entry 479 (class 1259 OID 71931)
-- Name: ref_masterfeedtype; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_masterfeedtype (
    feedtypeid character varying(10) NOT NULL,
    feedtypename character varying(20) NOT NULL,
    description character varying(50) NOT NULL,
    feedtype boolean
);


ALTER TABLE ref_masterfeedtype OWNER TO postgres;

--
-- TOC entry 480 (class 1259 OID 71934)
-- Name: ref_medfrequency; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_medfrequency (
    freqid character varying(10) NOT NULL,
    freqvalue character varying(100) NOT NULL,
    frequency_int real
);


ALTER TABLE ref_medfrequency OWNER TO postgres;

--
-- TOC entry 481 (class 1259 OID 71937)
-- Name: ref_medicine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_medicine (
    medid character varying(10) NOT NULL,
    medname character varying(20) NOT NULL,
    formuladose character varying(500) NOT NULL,
    frequency integer NOT NULL,
    isactive boolean,
    description character varying(50),
    dosemultiplier real,
    medicationtype character varying(200),
    route character varying(100),
    brand character varying(50)
);


ALTER TABLE ref_medicine OWNER TO postgres;

--
-- TOC entry 482 (class 1259 OID 71943)
-- Name: ref_medtype; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_medtype (
    typeid character varying(10) NOT NULL,
    typevalue character varying(50) NOT NULL
);


ALTER TABLE ref_medtype OWNER TO postgres;

--
-- TOC entry 483 (class 1259 OID 71946)
-- Name: ref_metabolic_treatment; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_metabolic_treatment (
    treatmentid character varying(10) NOT NULL,
    treatmentused character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_metabolic_treatment OWNER TO postgres;

--
-- TOC entry 484 (class 1259 OID 71949)
-- Name: ref_metabolicsymptoms; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_metabolicsymptoms (
    metabolicsymid character varying(10) NOT NULL,
    metabolicsymptoms character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_metabolicsymptoms OWNER TO postgres;

--
-- TOC entry 485 (class 1259 OID 71952)
-- Name: ref_nutritioncalculator; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_nutritioncalculator (
    nutritionid bigint NOT NULL,
    feedtype_id character varying(10),
    energy real,
    protein real,
    fat real,
    vitamina real,
    vitamind real,
    calcium real,
    phosphorus real,
    iron real
);


ALTER TABLE ref_nutritioncalculator OWNER TO postgres;

--
-- TOC entry 486 (class 1259 OID 71955)
-- Name: ref_nutritioncalculator_nutritionid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_nutritioncalculator_nutritionid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_nutritioncalculator_nutritionid_seq OWNER TO postgres;

--
-- TOC entry 5280 (class 0 OID 0)
-- Dependencies: 486
-- Name: ref_nutritioncalculator_nutritionid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_nutritioncalculator_nutritionid_seq OWNED BY ref_nutritioncalculator.nutritionid;


--
-- TOC entry 487 (class 1259 OID 71957)
-- Name: ref_orderinvestigation; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_orderinvestigation (
    orderinvestigationid character varying(10) NOT NULL,
    orderinvestigation character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_orderinvestigation OWNER TO postgres;

--
-- TOC entry 488 (class 1259 OID 71960)
-- Name: ref_organisms; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_organisms (
    organismsid character varying(10) NOT NULL,
    organisms character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_organisms OWNER TO postgres;

--
-- TOC entry 489 (class 1259 OID 71963)
-- Name: ref_painscore; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_painscore (
    ref_painscoreid bigint NOT NULL,
    clinical_status character varying(100) NOT NULL,
    clinical_sign character varying(500) NOT NULL,
    painscore integer NOT NULL
);


ALTER TABLE ref_painscore OWNER TO postgres;

--
-- TOC entry 490 (class 1259 OID 71969)
-- Name: ref_painscore_ref_painscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE ref_painscore_ref_painscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE ref_painscore_ref_painscoreid_seq OWNER TO postgres;

--
-- TOC entry 5281 (class 0 OID 0)
-- Dependencies: 490
-- Name: ref_painscore_ref_painscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE ref_painscore_ref_painscoreid_seq OWNED BY ref_painscore.ref_painscoreid;


--
-- TOC entry 491 (class 1259 OID 71971)
-- Name: ref_presentationeos; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_presentationeos (
    presentationeosid character varying(10) NOT NULL,
    presentationeos character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_presentationeos OWNER TO postgres;

--
-- TOC entry 492 (class 1259 OID 71974)
-- Name: ref_presentationsymptoms; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_presentationsymptoms (
    presentationsymid character varying(10) NOT NULL,
    presentationsymptoms character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_presentationsymptoms OWNER TO postgres;

--
-- TOC entry 493 (class 1259 OID 71977)
-- Name: ref_pupilreactivity; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_pupilreactivity (
    reactivityid character varying(10) NOT NULL,
    reactivity character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_pupilreactivity OWNER TO postgres;

--
-- TOC entry 494 (class 1259 OID 71980)
-- Name: ref_rdscause; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_rdscause (
    rdscauseid character varying(10) NOT NULL,
    rdscause character varying(20) NOT NULL,
    description character varying(20) NOT NULL,
    seq integer
);


ALTER TABLE ref_rdscause OWNER TO postgres;

--
-- TOC entry 495 (class 1259 OID 71983)
-- Name: ref_rdsriskfactor; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_rdsriskfactor (
    riskfactorid character varying(10) NOT NULL,
    riskfactor character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_rdsriskfactor OWNER TO postgres;

--
-- TOC entry 496 (class 1259 OID 71986)
-- Name: ref_reasonofmv; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_reasonofmv (
    mvreasonid character varying(10) NOT NULL,
    reasonofmv character varying(20) NOT NULL,
    description character varying(20) NOT NULL
);


ALTER TABLE ref_reasonofmv OWNER TO postgres;

--
-- TOC entry 497 (class 1259 OID 71989)
-- Name: ref_ropstage; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_ropstage (
    ropstageid character varying(10) NOT NULL,
    ropstage character varying(10) NOT NULL,
    description character varying(10) NOT NULL
);


ALTER TABLE ref_ropstage OWNER TO postgres;

--
-- TOC entry 498 (class 1259 OID 71992)
-- Name: ref_seizures_cause; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_seizures_cause (
    seizurescauseid character varying(10) NOT NULL,
    seizures_cause character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_seizures_cause OWNER TO postgres;

--
-- TOC entry 499 (class 1259 OID 71995)
-- Name: ref_seizures_medication; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_seizures_medication (
    seizuresmediid character varying(10) NOT NULL,
    seizures_medication character varying(20) NOT NULL,
    description character varying(20) NOT NULL
);


ALTER TABLE ref_seizures_medication OWNER TO postgres;

--
-- TOC entry 500 (class 1259 OID 71998)
-- Name: ref_seizures_type; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_seizures_type (
    seizurestypeid character varying(10) NOT NULL,
    seizures_type character varying(20) NOT NULL,
    description character varying(20) NOT NULL
);


ALTER TABLE ref_seizures_type OWNER TO postgres;

--
-- TOC entry 501 (class 1259 OID 72001)
-- Name: ref_significantmaterial; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_significantmaterial (
    smid character varying(10) NOT NULL,
    material character varying(20) NOT NULL
);


ALTER TABLE ref_significantmaterial OWNER TO postgres;

--
-- TOC entry 502 (class 1259 OID 72004)
-- Name: ref_testitemhelp; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_testitemhelp (
    itemid character varying(10) NOT NULL,
    testid character varying(8),
    testcode character varying(20),
    itemname character varying(100),
    testvalue text,
    unit character varying(20),
    normalrange character varying(20)
);


ALTER TABLE ref_testitemhelp OWNER TO postgres;

--
-- TOC entry 503 (class 1259 OID 72010)
-- Name: ref_testslist; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_testslist (
    testid character varying(50) NOT NULL,
    testcode character varying(50),
    testname character varying(100),
    assesment_category character varying(100),
    isactive boolean
);


ALTER TABLE ref_testslist OWNER TO postgres;

--
-- TOC entry 504 (class 1259 OID 72013)
-- Name: ref_unit; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_unit (
    unitid character varying(10) NOT NULL,
    unitvalue character varying(10) NOT NULL
);


ALTER TABLE ref_unit OWNER TO postgres;

--
-- TOC entry 505 (class 1259 OID 72016)
-- Name: ref_urineculture; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_urineculture (
    urinecultureid character varying(10) NOT NULL,
    urineculture character varying(50) NOT NULL,
    description character varying(50) NOT NULL
);


ALTER TABLE ref_urineculture OWNER TO postgres;

--
-- TOC entry 506 (class 1259 OID 72019)
-- Name: ref_ventilationmode; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE ref_ventilationmode (
    ventmodeid character varying(10) NOT NULL,
    ventilationmode character varying(10) NOT NULL,
    description character varying(10) NOT NULL,
    ventilation_type character varying(50)
);


ALTER TABLE ref_ventilationmode OWNER TO postgres;

--
-- TOC entry 507 (class 1259 OID 72022)
-- Name: renal_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE renal_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE renal_seq OWNER TO postgres;

--
-- TOC entry 508 (class 1259 OID 72024)
-- Name: resp_bpd_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resp_bpd_seq
    START WITH 51
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resp_bpd_seq OWNER TO postgres;

--
-- TOC entry 509 (class 1259 OID 72026)
-- Name: resp_others_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE resp_others_seq
    START WITH 51
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE resp_others_seq OWNER TO postgres;

--
-- TOC entry 510 (class 1259 OID 72028)
-- Name: respsupport_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE respsupport_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE respsupport_seq OWNER TO postgres;

--
-- TOC entry 511 (class 1259 OID 72030)
-- Name: respsupport; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE respsupport (
    respsupportid bigint DEFAULT nextval('respsupport_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    eventid character varying(50) NOT NULL,
    eventname character varying(50) NOT NULL,
    uhid character varying(50) NOT NULL,
    rs_vent_type character varying(100),
    rs_flow_rate character varying(50),
    rs_fio2 character varying(50),
    rs_map character varying(50),
    rs_mech_vent_type character varying(20),
    rs_pip character varying(50),
    rs_peep character varying(50),
    rs_it character varying(50),
    rs_et character varying(50),
    rs_tv character varying(50),
    rs_mv character varying(50),
    rs_amplitude character varying(50),
    rs_frequency character varying(50),
    rs_rate character varying,
    rs_isendotracheal boolean,
    rs_tubesize character varying,
    rs_fixation character varying,
    rs_spo2 character varying,
    rs_pco2 character varying,
    isactive boolean,
    rs_backuprate character varying(50)
);


ALTER TABLE respsupport OWNER TO postgres;

--
-- TOC entry 512 (class 1259 OID 72037)
-- Name: respsystem_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE respsystem_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE respsystem_seq OWNER TO postgres;

--
-- TOC entry 513 (class 1259 OID 72039)
-- Name: role; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE role (
    roleid character varying(100) NOT NULL,
    rolename character varying(100),
    description text
);


ALTER TABLE role OWNER TO postgres;

--
-- TOC entry 514 (class 1259 OID 72045)
-- Name: role_manager; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE role_manager (
    rolemanagerid bigint DEFAULT nextval('managerole_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    moduleid bigint,
    roleid bigint,
    statusid bigint
);


ALTER TABLE role_manager OWNER TO postgres;

--
-- TOC entry 515 (class 1259 OID 72049)
-- Name: role_status; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE role_status (
    statusid bigint NOT NULL,
    status_name character varying(100),
    description text
);


ALTER TABLE role_status OWNER TO postgres;


--
-- TOC entry 518 (class 1259 OID 72065)
-- Name: sa_cardiac; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_cardiac (
    cardiacid bigint DEFAULT nextval('cardiac_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    uhid character varying(50),
    cardiactype character varying(50),
    chd character varying(50),
    othercardiacdysfn character varying(50),
    otherchd character varying(50),
    shock boolean,
    needforinotropes boolean,
    durationofinotropes character varying(50),
    typeofinotropes1 character varying(50),
    loggeduser character varying(100) NOT NULL,
    typeofinotropes2 character varying(100),
    comment character varying(1000)
);


ALTER TABLE sa_cardiac OWNER TO postgres;

--
-- TOC entry 519 (class 1259 OID 72072)
-- Name: sacns_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sacns_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sacns_seq OWNER TO postgres;

--
-- TOC entry 520 (class 1259 OID 72074)
-- Name: sa_cns; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_cns (
    cnspid bigint DEFAULT nextval('sacns_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    posture boolean,
    cthead boolean,
    neurexamdisc boolean,
    eeg boolean,
    nnr boolean,
    feeding boolean,
    tone character varying(50),
    hie boolean,
    stagehie integer,
    ivh boolean,
    gradeivh integer,
    seizures boolean,
    ageofonsetseizuresdays character varying(20),
    seizures_type character varying(50),
    seizures_cause character varying(50),
    seizures_medi character varying(50),
    mrihead character varying(50),
    loggeduser character varying(100) NOT NULL,
    bera boolean,
    vep boolean,
    comment character varying(1000)
);


ALTER TABLE sa_cns OWNER TO postgres;

--
-- TOC entry 521 (class 1259 OID 72081)
-- Name: sa_feedgrowth; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_feedgrowth (
    feedgrowthid bigint DEFAULT nextval('feedgrowth_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    uhid character varying(50),
    tpn boolean,
    tropicfeeds boolean,
    feedingintolerance boolean,
    ageatfullfeed character varying(50),
    feedstoday character varying(50),
    minimumweight character varying(50),
    centileatbirth character varying(50),
    centileatdischarge character varying(50),
    feedstartdays character varying(50),
    loggeduser character varying(100) NOT NULL,
    comment character varying(1000)
);


ALTER TABLE sa_feedgrowth OWNER TO postgres;

--
-- TOC entry 522 (class 1259 OID 72088)
-- Name: sa_followup; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_followup (
    followupid bigint DEFAULT nextval('followup_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    uhid character varying(50),
    pca_at_discharge_wks character varying(50),
    date_3mo_coa character varying(50),
    date_6mo_coa character varying(50),
    date_1yr_coa character varying(50),
    wt_40weeks_gms character varying(50),
    wt_3mo_coa character varying(50),
    wt_6mo_coa character varying(50),
    wt_1yr_coa character varying(50),
    ofc_40weeks_gms character varying(50),
    ofc_3mo_coa character varying(50),
    ofc_6mo_coa character varying(50),
    ofc_1yr_coa character varying(50),
    len_40weeks_gms character varying(50),
    len_3mo_coa character varying(50),
    len_6mo_coa character varying(50),
    len_1yr_coa character varying(50),
    mdi_3mo character varying(50),
    mdi_6mo character varying(50),
    mdi_1yr character varying(50),
    pdi_3mo character varying(50),
    pdi_6mo character varying(50),
    pdi_1yr character varying(50),
    loggeduser character varying(100) NOT NULL
);


ALTER TABLE sa_followup OWNER TO postgres;

--
-- TOC entry 523 (class 1259 OID 72095)
-- Name: sa_hypokalemia; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_hypokalemia (
    hypokalemiaid bigint DEFAULT nextval('hypokalemia_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    metabolic_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    abg_ph character varying(20),
    abg_hco3 character varying(20),
    ecg_uwave boolean,
    treatmentaction character varying(200),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofhypokalemia character varying(500),
    causeofhypokalemia_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    abg_pco2 character varying(20),
    serumpotassium character varying(20),
    potassium_total real,
    potassium_volume real
);


ALTER TABLE sa_hypokalemia OWNER TO postgres;

--
-- TOC entry 524 (class 1259 OID 72102)
-- Name: sa_infection_clabsi; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_infection_clabsi (
    saclabsiid bigint DEFAULT nextval('clabsi_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    infection_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    isclabsiavailable boolean,
    treatmentaction character varying(500),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    progressnotes text,
    ageatassesment integer,
    isageofassesmentinhours boolean,
    timeofassessment timestamp with time zone
);


ALTER TABLE sa_infection_clabsi OWNER TO postgres;

--
-- TOC entry 525 (class 1259 OID 72109)
-- Name: sepsis_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sepsis_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sepsis_seq OWNER TO postgres;

--
-- TOC entry 526 (class 1259 OID 72111)
-- Name: sa_infection_sepsis; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_infection_sepsis (
    sasepsisid bigint DEFAULT nextval('sepsis_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    infection_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    symptomatic_status boolean,
    symptomatic_value character varying(200),
    downescoreid character varying(20),
    shock boolean,
    shock_cft character varying(20),
    shock_bp character varying(20),
    tachycardia_status boolean,
    tachycardia_value character varying(20),
    temprature_status boolean,
    temprature_value character varying(100),
    desaturation_status boolean,
    desaturation_value character varying(100),
    oxygenreq_status boolean,
    oxygenreq_value character varying(100),
    abdominal_status boolean,
    abdominal_value character varying(100),
    treatmentaction character varying(500),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    causeofsepsis character varying(500),
    causeofsepsis_other character varying(500),
    progressnotes text,
    associatedevent character varying(100),
    associatedeventid character varying(100),
    temperature_celsius character varying(10),
    bonejoint_status boolean,
    meningitis_status boolean,
    ventriculitis_status boolean,
    ageatassesment integer,
    isageofassesmentinhours boolean,
    timeofassessment timestamp with time zone,
    riskfactor character varying(200),
    riskfactor_other character varying(100),
    shock_diastbp character varying(20),
    shock_systbp character varying(20),
    poorpulses boolean,
    cold_extremities boolean,
    nec_status boolean,
    nec_stage character varying(20),
    hypothermia_value character varying(20),
    fever_value character varying(20),
    central_peripheral_value character varying(20),
    heartrate character varying(20),
    isfeverhypo boolean,
    bellscoreid character varying(10),
    assessment_date date,
    assessment_meridiem boolean,
    assessment_time timestamp with time zone,
    assessment_hour character varying,
    assessment_min character varying
);


ALTER TABLE sa_infection_sepsis OWNER TO postgres;

--
-- TOC entry 527 (class 1259 OID 72118)
-- Name: vap_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE vap_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vap_seq OWNER TO postgres;

--
-- TOC entry 528 (class 1259 OID 72120)
-- Name: sa_infection_vap; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_infection_vap (
    savapid bigint DEFAULT nextval('vap_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    infection_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    isvapavailable boolean,
    treatmentaction character varying(500),
    treatment_other text,
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassestime_type character varying(50),
    otherplan_comments text,
    progressnotes text,
    ageatassesment integer,
    isageofassesmentinhours boolean,
    timeofassessment timestamp with time zone
);


ALTER TABLE sa_infection_vap OWNER TO postgres;

--
-- TOC entry 529 (class 1259 OID 72127)
-- Name: sa_metabolic; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_metabolic (
    sametabolicid bigint DEFAULT nextval('sametabolic_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    hypoglycemia boolean,
    minimumbs character varying(50),
    dayhypoglycemia character varying(50),
    durationhypoglycemia character varying(50),
    maxgdr character varying(50),
    treatmentused character varying(50),
    comments character varying(500),
    loggeduser character varying(100) NOT NULL,
    maximumbs character varying(50),
    insulinlevel character varying(50),
    tms boolean,
    gcms boolean,
    symptoms character varying(100)
);


ALTER TABLE sa_metabolic OWNER TO postgres;

--
-- TOC entry 530 (class 1259 OID 72134)
-- Name: sa_misc; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_misc (
    miscid bigint DEFAULT nextval('misc_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    rop boolean,
    ropstage character varying(50),
    oaeleft character varying(50),
    oaeright character varying(50),
    metabolicscreensent boolean,
    vaccination_discharge boolean,
    beraadvised character varying(50),
    tsh character varying(50),
    other character varying(50),
    report character varying(50),
    loggeduser character varying(100) NOT NULL,
    cchd boolean,
    cdh boolean,
    comments character varying(500)
);


ALTER TABLE sa_misc OWNER TO postgres;

--
-- TOC entry 531 (class 1259 OID 72141)
-- Name: sa_renalfailure; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_renalfailure (
    renalid bigint DEFAULT nextval('renal_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    renalfailure boolean,
    minimumurineoutput character varying(50),
    maxbun character varying(50),
    maxscreatetine character varying(50),
    causeofrf character varying(50),
    minweight character varying(50),
    loggeduser character varying(100) NOT NULL,
    stageofaki character varying(10),
    sepsis boolean,
    iugr boolean,
    possibledrugtoxicity boolean,
    rrt boolean,
    comment character varying(1000)
);


ALTER TABLE sa_renalfailure OWNER TO postgres;

--
-- TOC entry 532 (class 1259 OID 72148)
-- Name: sa_resp_bpd; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_bpd (
    respbpdid bigint DEFAULT nextval('resp_bpd_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    resp_system_status character varying(10),
    eventstatus character varying(10),
    ageatassesment character varying(10),
    gestationalage character varying(10),
    cumulativedaysonoxygen character varying(10),
    cumulativedaysonventilator character varying(10),
    respiratorystatus character varying(100),
    severityofbpd character varying(50),
    treatmentplan character varying(100),
    medicaltreatment character varying(100),
    antibiotic character varying(100),
    othertreatment character varying(1000),
    bpd_plan character varying(50),
    reassestime character varying(10),
    reassesstimetype character varying(10),
    progressnotes text,
    otherplan_comments text
);


ALTER TABLE sa_resp_bpd OWNER TO postgres;

--
-- TOC entry 533 (class 1259 OID 72155)
-- Name: sa_resp_chesttube; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_chesttube (
    respchesttubeid bigint NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    resppneumothoraxid character varying(50) NOT NULL,
    chesttube_left_value character varying(50),
    left_clampremovestatus character varying(50),
    left_clampremove_plantime timestamp with time zone,
    chesttube_right_value character varying(50),
    right_clampremove_plantime timestamp with time zone,
    right_clampremovestatus character varying(50),
    isleftchesttube boolean,
    istubereadytoadjust boolean,
    istuberemoved boolean,
    chesttube_value character varying(50),
    chesttube_adjusted_value character varying(50),
    isactive boolean DEFAULT true NOT NULL,
    removetime timestamp with time zone,
    ispasttube boolean,
    clampstatus character varying(50)
);


ALTER TABLE sa_resp_chesttube OWNER TO postgres;

--
-- TOC entry 534 (class 1259 OID 72159)
-- Name: sa_resp_chesttube_respchesttubeid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE sa_resp_chesttube_respchesttubeid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sa_resp_chesttube_respchesttubeid_seq OWNER TO postgres;

--
-- TOC entry 5282 (class 0 OID 0)
-- Dependencies: 534
-- Name: sa_resp_chesttube_respchesttubeid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE sa_resp_chesttube_respchesttubeid_seq OWNED BY sa_resp_chesttube.respchesttubeid;


--
-- TOC entry 535 (class 1259 OID 72161)
-- Name: sa_resp_others; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_resp_others (
    respotherid bigint DEFAULT nextval('resp_others_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    loggeduser character varying(100) NOT NULL,
    resp_system_status character varying(10),
    eventstatus character varying(10),
    ageatonset character varying(10),
    ageinhours boolean,
    pulmonaryhaemorrhage boolean,
    pulmonaryemphysema boolean,
    congenitallungsmalformations boolean,
    miscellaneous character varying(100),
    pulmonaryhaemorrhage_comments character varying(1000),
    pulmonaryemphysema_comments character varying(1000),
    congenitallungsmalformations_comments character varying(1000),
    miscellaneous_comments character varying(1000),
    treatmentaction character varying(200),
    sufactantname character varying(50),
    sufactantdose character varying(50),
    isinsuredone boolean,
    action_after_surfactant character varying(100),
    treatmentplan character varying(50),
    reassestime character varying(20),
    reassesstime_type character varying(50),
    othercomments text,
    causeofdisease character varying(100),
    progressnotes text,
    treatment_other text,
    surfactant_dose_ml character varying(20),
    associatedevent character varying(100),
    ageatassesment integer,
    isageofassesmentinhours boolean,
    timeofassessment timestamp with time zone
);


ALTER TABLE sa_resp_others OWNER TO postgres;

--
-- TOC entry 536 (class 1259 OID 72168)
-- Name: sa_respsystem; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_respsystem (
    respid bigint DEFAULT nextval('respsystem_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    rdscause character varying(20),
    lowestspo2 character varying(20),
    spo2 character varying(20),
    maxrr character varying(20),
    apnea boolean,
    maxfio2 character varying(20),
    maxbili character varying(20),
    mechvent boolean,
    ventilationmode character varying(20),
    ageatvent character varying(20),
    ventduration character varying(20),
    surfactant boolean,
    noofdoses character varying(20),
    ageatsurfactant character varying(20),
    cld boolean,
    cldstage character varying(20),
    pphn boolean,
    pulmhaem boolean,
    reasonofmv character varying(20),
    cpap character varying(20),
    daysofcpap character varying(20),
    airvo character varying(20),
    niv character varying(20),
    loggeduser character varying(100) NOT NULL,
    caffeine_startage character varying(50),
    caffeine_stopage character varying(50),
    ventilation_type character varying(50),
    comment character varying(1000),
    resp_system_status character varying(20),
    eventstatus character varying(20),
    ageatonset character varying(20),
    ageinhoursdays boolean,
    downesscoreid character varying(20),
    treatmentaction character varying(200),
    sufactantstatus character varying(50),
    respirattorystatus character varying(100),
    respirattorytime character varying(50),
    respinhoursdays boolean,
    ventbehaviour character varying(50),
    medicineid character varying(100),
    rdsplan character varying(50),
    reassestime character varying(20),
    reasseshoursdays character varying(50),
    silvermanscoreid character varying(50),
    progressnotes text,
    surfactant_dose character varying(50),
    rds_plan_others character varying(1000),
    isventmode_upgrade boolean,
    isinsuredone boolean,
    rs_insure_type character varying(100),
    rs_mech_vent_type character varying(20),
    systolic_bp character varying(100),
    oxgenation_index character varying(100),
    lab_preductal character varying(100),
    lab_postductal character varying(100),
    labile_difference character varying(100),
    labile_oxygenation boolean,
    pulmonary_pressure character varying(100),
    transillumination boolean,
    pphn_ino character varying(100),
    methaemoglobin_level character varying(100),
    sildenafil character varying(100),
    needle_aspiration boolean,
    chesttube_insertion boolean,
    aspiration_plan_time integer,
    aspiration_minhrsdays character varying(50),
    chesttube_plan_time integer,
    chesttube_minhrsdays character varying(50),
    inotropes character varying(200),
    pphn_eventstatus character varying(10),
    phenumothorax_eventstatus character varying(10)
);


ALTER TABLE sa_respsystem OWNER TO postgres;

--
-- TOC entry 537 (class 1259 OID 72175)
-- Name: sa_sepsis; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE sa_sepsis (
    sepsisid bigint DEFAULT nextval('sepsis_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone NOT NULL,
    modificationtime timestamp without time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    onsetsepsis character varying(50),
    riskfactor character varying(50),
    presentationeos character varying(50),
    ageatonset character varying(50),
    symptoms text,
    urineculture boolean,
    csfculture boolean,
    urine_antibiotics text,
    tlc character varying(50),
    crp boolean,
    maxcrp character varying(50),
    sepsisscreen boolean,
    bloodculture boolean,
    urine_organisms character varying(50),
    otherorganisms character varying(50),
    uti boolean,
    meningtis boolean,
    boneinfection boolean,
    pneumonia boolean,
    durationoftreatment character varying(50),
    loggeduser character varying(100) NOT NULL,
    blood_organisms character varying(50),
    blood_antibiotics text,
    csf_organisms character varying(50),
    csf_antibiotics text,
    other_foci character varying(100),
    comment character varying(1000)
);


ALTER TABLE sa_sepsis OWNER TO postgres;

--
-- TOC entry 538 (class 1259 OID 72182)
-- Name: score_apgar; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_apgar (
    apgarscoreid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    appearance integer,
    pulse integer,
    grimace integer,
    activity integer,
    respiration integer,
    uhid character varying(50) NOT NULL,
    apgar_time integer,
    apgar_total_score integer
);


ALTER TABLE score_apgar OWNER TO postgres;

--
-- TOC entry 539 (class 1259 OID 72185)
-- Name: score_apgar_apgarscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_apgar_apgarscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_apgar_apgarscoreid_seq OWNER TO postgres;

--
-- TOC entry 5283 (class 0 OID 0)
-- Dependencies: 539
-- Name: score_apgar_apgarscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_apgar_apgarscoreid_seq OWNED BY score_apgar.apgarscoreid;


--
-- TOC entry 540 (class 1259 OID 72187)
-- Name: score_ballard; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_ballard (
    ballardscoreid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    maturity character varying(50),
    posture integer,
    square_window integer,
    arm_recoi integer,
    popliteal_angle integer,
    scarf_sign integer,
    healtoear integer,
    skin integer,
    lanugo integer,
    plantar_surface integer,
    breast integer,
    eye_ear integer,
    genital_male integer,
    genital_female integer,
    nero_score integer,
    physical_score integer,
    total_score integer,
    gestation_ballard integer,
    uhid character varying(50) NOT NULL
);


ALTER TABLE score_ballard OWNER TO postgres;

--
-- TOC entry 541 (class 1259 OID 72190)
-- Name: score_ballard_ballardscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_ballard_ballardscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_ballard_ballardscoreid_seq OWNER TO postgres;

--
-- TOC entry 5284 (class 0 OID 0)
-- Dependencies: 541
-- Name: score_ballard_ballardscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_ballard_ballardscoreid_seq OWNED BY score_ballard.ballardscoreid;


--
-- TOC entry 542 (class 1259 OID 72192)
-- Name: score_bellstage; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_bellstage (
    bellstagescoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    systemsign character varying(20),
    intestinalsign character varying(20),
    radiologicalsign character varying(20),
    systemsignscore integer,
    intestinalsignscore integer,
    radiologicalsignscore integer,
    bellstagescore integer,
    treatmentsign character varying(20),
    treatmentscore integer
);


ALTER TABLE score_bellstage OWNER TO postgres;

--
-- TOC entry 543 (class 1259 OID 72195)
-- Name: score_bellstage_bellstagescoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_bellstage_bellstagescoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_bellstage_bellstagescoreid_seq OWNER TO postgres;

--
-- TOC entry 5285 (class 0 OID 0)
-- Dependencies: 543
-- Name: score_bellstage_bellstagescoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_bellstage_bellstagescoreid_seq OWNED BY score_bellstage.bellstagescoreid;


--
-- TOC entry 544 (class 1259 OID 72197)
-- Name: score_bind; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_bind (
    bindscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    mentalstatus character varying(50),
    mentalstatusscore integer,
    muscletone character varying(50),
    muscletonescore integer,
    crypattern character varying(50),
    crypatternscore integer,
    oculomotor character varying(50),
    oculomotorscore integer,
    bindscore integer
);


ALTER TABLE score_bind OWNER TO postgres;

--
-- TOC entry 545 (class 1259 OID 72200)
-- Name: score_bind_bindscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_bind_bindscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_bind_bindscoreid_seq OWNER TO postgres;

--
-- TOC entry 5286 (class 0 OID 0)
-- Dependencies: 545
-- Name: score_bind_bindscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_bind_bindscoreid_seq OWNED BY score_bind.bindscoreid;


--
-- TOC entry 546 (class 1259 OID 72202)
-- Name: score_downes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_downes (
    downesscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    cynosis integer,
    retractions integer,
    grunting integer,
    airentry integer,
    respiratoryrate integer,
    downesscore integer,
    admission_entry boolean
);


ALTER TABLE score_downes OWNER TO postgres;

--
-- TOC entry 547 (class 1259 OID 72205)
-- Name: score_downes_downesscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_downes_downesscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_downes_downesscoreid_seq OWNER TO postgres;

--
-- TOC entry 5287 (class 0 OID 0)
-- Dependencies: 547
-- Name: score_downes_downesscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_downes_downesscoreid_seq OWNED BY score_downes.downesscoreid;


--
-- TOC entry 548 (class 1259 OID 72207)
-- Name: score_hie; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_hie (
    hiescoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    consiousness_level integer,
    muscle_tone integer,
    posture integer,
    tendon_reflex integer,
    myoclonus integer,
    more_reflex integer,
    pupils integer,
    seizures integer,
    eeg integer,
    duration integer,
    outcome integer,
    hiescore integer
);


ALTER TABLE score_hie OWNER TO postgres;

--
-- TOC entry 549 (class 1259 OID 72210)
-- Name: score_hie_hiescoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_hie_hiescoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_hie_hiescoreid_seq OWNER TO postgres;

--
-- TOC entry 5288 (class 0 OID 0)
-- Dependencies: 549
-- Name: score_hie_hiescoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_hie_hiescoreid_seq OWNED BY score_hie.hiescoreid;


--
-- TOC entry 550 (class 1259 OID 72212)
-- Name: score_ivh; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_ivh (
    ivhscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    grade1 integer,
    grade2 integer,
    grade3 integer,
    grade4 integer,
    ivhscore integer
);


ALTER TABLE score_ivh OWNER TO postgres;

--
-- TOC entry 551 (class 1259 OID 72215)
-- Name: score_ivh_ivhscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_ivh_ivhscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_ivh_ivhscoreid_seq OWNER TO postgres;

--
-- TOC entry 5289 (class 0 OID 0)
-- Dependencies: 551
-- Name: score_ivh_ivhscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_ivh_ivhscoreid_seq OWNED BY score_ivh.ivhscoreid;


--
-- TOC entry 552 (class 1259 OID 72217)
-- Name: score_levene; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_levene (
    levenescoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    consciousness character varying(50),
    consciousnessscore integer,
    tone character varying(50),
    tonescore integer,
    seizures character varying(50),
    seizuresscore integer,
    suckingrespiration character varying(50),
    suckingrespirationscore integer,
    levenescore integer,
    admission_entry boolean
);


ALTER TABLE score_levene OWNER TO postgres;

--
-- TOC entry 553 (class 1259 OID 72220)
-- Name: score_levene_levenescoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_levene_levenescoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_levene_levenescoreid_seq OWNER TO postgres;

--
-- TOC entry 5290 (class 0 OID 0)
-- Dependencies: 553
-- Name: score_levene_levenescoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_levene_levenescoreid_seq OWNED BY score_levene.levenescoreid;


--
-- TOC entry 554 (class 1259 OID 72222)
-- Name: score_pain; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_pain (
    painscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    gestationalage character varying(20),
    gestationalagescore integer,
    behaviouralstate character varying(20),
    behaviouralstatescore integer,
    heartrate character varying(20),
    heartratescore integer,
    oxygensat character varying(20),
    oxygensatscore integer,
    browbulge character varying(20),
    browbulgescore integer,
    eyesqueeze character varying(20),
    eyesqueezescore integer,
    painscore integer
);


ALTER TABLE score_pain OWNER TO postgres;

--
-- TOC entry 555 (class 1259 OID 72225)
-- Name: score_pain_painscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_pain_painscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_pain_painscoreid_seq OWNER TO postgres;

--
-- TOC entry 5291 (class 0 OID 0)
-- Dependencies: 555
-- Name: score_pain_painscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_pain_painscoreid_seq OWNED BY score_pain.painscoreid;


--
-- TOC entry 556 (class 1259 OID 72227)
-- Name: score_papile; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_papile (
    papilescoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    papilescore integer
);


ALTER TABLE score_papile OWNER TO postgres;

--
-- TOC entry 557 (class 1259 OID 72230)
-- Name: score_papile_papilescoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_papile_papilescoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_papile_papilescoreid_seq OWNER TO postgres;

--
-- TOC entry 5292 (class 0 OID 0)
-- Dependencies: 557
-- Name: score_papile_papilescoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_papile_papilescoreid_seq OWNED BY score_papile.papilescoreid;


--
-- TOC entry 558 (class 1259 OID 72232)
-- Name: score_sarnat; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_sarnat (
    sarnatscoreid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    level_of_consiousness integer,
    muscle_tone integer,
    posture integer,
    tendon_reflexes integer,
    myoclonus integer,
    more_reflex integer,
    pupils integer,
    seizures integer,
    ecg integer,
    duration integer,
    outcome integer,
    sarnat_score integer
);


ALTER TABLE score_sarnat OWNER TO postgres;

--
-- TOC entry 559 (class 1259 OID 72235)
-- Name: score_sarnat_sarnatscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_sarnat_sarnatscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_sarnat_sarnatscoreid_seq OWNER TO postgres;

--
-- TOC entry 5293 (class 0 OID 0)
-- Dependencies: 559
-- Name: score_sarnat_sarnatscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_sarnat_sarnatscoreid_seq OWNED BY score_sarnat.sarnatscoreid;


--
-- TOC entry 560 (class 1259 OID 72237)
-- Name: score_sepsis; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_sepsis (
    sepsisscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    total_wbccount integer,
    total_pmncount integer,
    immature_pmncount integer,
    it_pmnratio integer,
    im_pmnratio integer,
    degenerative_pmn integer,
    plateletcount integer,
    sepsisscore integer
);


ALTER TABLE score_sepsis OWNER TO postgres;

--
-- TOC entry 561 (class 1259 OID 72240)
-- Name: score_sepsis_sepsisscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_sepsis_sepsisscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_sepsis_sepsisscoreid_seq OWNER TO postgres;

--
-- TOC entry 5294 (class 0 OID 0)
-- Dependencies: 561
-- Name: score_sepsis_sepsisscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_sepsis_sepsisscoreid_seq OWNED BY score_sepsis.sepsisscoreid;


--
-- TOC entry 562 (class 1259 OID 72242)
-- Name: score_silverman; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_silverman (
    silvermanscoreid bigint NOT NULL,
    creationtime timestamp with time zone,
    modificationtime timestamp with time zone,
    uhid character varying(50),
    upperchest integer,
    lowerchest integer,
    xiphoidretract integer,
    narasdilat integer,
    expirgrunt integer,
    silvermanscore integer
);


ALTER TABLE score_silverman OWNER TO postgres;

--
-- TOC entry 563 (class 1259 OID 72245)
-- Name: score_silverman_silvermanscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_silverman_silvermanscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_silverman_silvermanscoreid_seq OWNER TO postgres;

--
-- TOC entry 5295 (class 0 OID 0)
-- Dependencies: 563
-- Name: score_silverman_silvermanscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_silverman_silvermanscoreid_seq OWNED BY score_silverman.silvermanscoreid;


--
-- TOC entry 564 (class 1259 OID 72247)
-- Name: score_thompson; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE score_thompson (
    thompsonscoreid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    tone integer,
    loc integer,
    fits integer,
    posture integer,
    moro integer,
    grasp integer,
    suck integer,
    respiration integer,
    frontanelle integer,
    thompson_score integer
);


ALTER TABLE score_thompson OWNER TO postgres;

--
-- TOC entry 565 (class 1259 OID 72250)
-- Name: score_thompson_thompsonscoreid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE score_thompson_thompsonscoreid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_thompson_thompsonscoreid_seq OWNER TO postgres;

--
-- TOC entry 5296 (class 0 OID 0)
-- Dependencies: 565
-- Name: score_thompson_thompsonscoreid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE score_thompson_thompsonscoreid_seq OWNED BY score_thompson.thompsonscoreid;


--
-- TOC entry 566 (class 1259 OID 72252)
-- Name: settingref_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE settingref_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE settingref_seq OWNER TO postgres;

--
-- TOC entry 567 (class 1259 OID 72254)
-- Name: setting_reference; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE setting_reference (
    settingid bigint DEFAULT nextval('settingref_seq'::regclass) NOT NULL,
    category character varying(100),
    refvalue character varying(100),
    refid character varying(10),
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE setting_reference OWNER TO postgres;

--
-- TOC entry 568 (class 1259 OID 72258)
-- Name: stable_notes_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE stable_notes_seq
    START WITH 767
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stable_notes_seq OWNER TO postgres;

--
-- TOC entry 569 (class 1259 OID 72260)
-- Name: stable_notes; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE stable_notes (
    id bigint DEFAULT nextval('stable_notes_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    hr real,
    bp real,
    temp real,
    rr real,
    spo2 real,
    activity character varying(20),
    stool_status boolean,
    stool_type character varying(20),
    urine_status boolean,
    urine_volume real,
    notes text,
    entrytime timestamp with time zone,
    babyfeedid bigint,
    diastolic_bp real
);


ALTER TABLE stable_notes OWNER TO postgres;


--
-- TOC entry 572 (class 1259 OID 72276)
-- Name: systemic_exam_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE systemic_exam_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE systemic_exam_seq OWNER TO postgres;

--
-- TOC entry 573 (class 1259 OID 72278)
-- Name: systemic_exam; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE systemic_exam (
    systemic_exam_id bigint DEFAULT nextval('systemic_exam_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    episodeid character varying(50) NOT NULL,
    loggeduser character varying(100),
    jaundice boolean,
    rds boolean,
    apnea boolean,
    pphn boolean,
    pneumothorax boolean,
    sepsis boolean,
    vap boolean,
    clabsi boolean,
    asphyxia boolean,
    seizures boolean,
    ivh boolean
);


ALTER TABLE systemic_exam OWNER TO postgres;



--
-- TOC entry 575 (class 1259 OID 72286)
-- Name: test_result_inicu; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE test_result_inicu (
    testresultid bigint NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    uhid character varying(50),
    testid character varying(6),
    itemid character varying(10),
    itemname character varying(100),
    itemvalue character varying(50),
    itemunit character varying(50),
    normalrange character varying(50),
    resultdate date NOT NULL,
    loggeduser character varying(50)
);


ALTER TABLE test_result_inicu OWNER TO postgres;

--
-- TOC entry 576 (class 1259 OID 72289)
-- Name: test_result_inicu_testresultid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE test_result_inicu_testresultid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE test_result_inicu_testresultid_seq OWNER TO postgres;

--
-- TOC entry 5297 (class 0 OID 0)
-- Dependencies: 576
-- Name: test_result_inicu_testresultid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE test_result_inicu_testresultid_seq OWNED BY test_result_inicu.testresultid;


--
-- TOC entry 577 (class 1259 OID 72291)
-- Name: usergroups_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE usergroups_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE usergroups_seq OWNER TO postgres;

--
-- TOC entry 578 (class 1259 OID 72293)
-- Name: userrole_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE userrole_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE userrole_seq OWNER TO postgres;

--
-- TOC entry 579 (class 1259 OID 72295)
-- Name: users_roles; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE users_roles (
    userroleid bigint DEFAULT nextval('userrole_seq'::regclass) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone,
    username character varying(100) NOT NULL,
    roleid character varying(100) NOT NULL,
    userid character varying(50)
);


ALTER TABLE users_roles OWNER TO postgres;

--
-- TOC entry 580 (class 1259 OID 72299)
-- Name: users_usergroups; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE users_usergroups (
    id bigint DEFAULT nextval('usergroups_seq'::regclass) NOT NULL,
    userid character varying(100) NOT NULL,
    usergroupid character varying(100) NOT NULL,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE users_usergroups OWNER TO postgres;

--
-- TOC entry 581 (class 1259 OID 72303)
-- Name: vaccine; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE vaccine (
    vaccineid bigint NOT NULL,
    vaccinename character varying(50),
    vaccine_type character varying(50),
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE vaccine OWNER TO postgres;

--
-- TOC entry 582 (class 1259 OID 72306)
-- Name: vaccine_info; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE vaccine_info (
    vaccineinfoid bigint NOT NULL,
    vaccineid bigint,
    dueageid bigint NOT NULL,
    isactivevaccine boolean DEFAULT true,
    creationtime timestamp without time zone,
    modificationtime timestamp without time zone
);


ALTER TABLE vaccine_info OWNER TO postgres;

--
-- TOC entry 583 (class 1259 OID 72310)
-- Name: vaccine_info_vaccineinfoid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE vaccine_info_vaccineinfoid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vaccine_info_vaccineinfoid_seq OWNER TO postgres;

--
-- TOC entry 5298 (class 0 OID 0)
-- Dependencies: 583
-- Name: vaccine_info_vaccineinfoid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE vaccine_info_vaccineinfoid_seq OWNED BY vaccine_info.vaccineinfoid;


--
-- TOC entry 584 (class 1259 OID 72312)
-- Name: vaccine_vaccineid_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE vaccine_vaccineid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vaccine_vaccineid_seq OWNER TO postgres;

--
-- TOC entry 5299 (class 0 OID 0)
-- Dependencies: 584
-- Name: vaccine_vaccineid_seq; Type: SEQUENCE OWNED BY; Schema: kdah; Owner: postgres
--

ALTER SEQUENCE vaccine_vaccineid_seq OWNED BY vaccine.vaccineid;


--
-- TOC entry 585 (class 1259 OID 72314)
-- Name: vtap_seq; Type: SEQUENCE; Schema: kdah; Owner: postgres
--

CREATE SEQUENCE vtap_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE vtap_seq OWNER TO postgres;

--
-- TOC entry 586 (class 1259 OID 72316)
-- Name: vtap; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE vtap (
    vtap_id bigint DEFAULT nextval('vtap_seq'::regclass) NOT NULL,
    creationtime timestamp with time zone NOT NULL,
    modificationtime timestamp with time zone NOT NULL,
    uhid character varying(50) NOT NULL,
    vtap_site character varying(10),
    vtap_size character varying(10),
    vtap_csf character varying(50),
    vtap_timestamp timestamp with time zone,
    loggeduser character varying(100),
    progressnotes text
);


ALTER TABLE vtap OWNER TO postgres;

