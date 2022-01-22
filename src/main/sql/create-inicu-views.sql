--
-- TOC entry 180 (class 1259 OID 70717)
-- Name: birth_mod; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW birth_mod AS
 SELECT p.uhid,
    p.gestationweekbylmp,
    p.gestationdaysbylmp,
    p.birthweight,
    p.gender,
    p.dateofbirth,
    p.timeofbirth,
        CASE
            WHEN (p.timeofbirth IS NULL) THEN (((p.dateofbirth || ' '::text) || '00:00:00'::time without time zone))::timestamp without time zone
            ELSE (((p.dateofbirth || ' '::text) || (replace((p.timeofbirth)::text, ','::text, ':'::text))::time without time zone))::timestamp without time zone
        END AS newbirthtime,
    a.anomaly_value
   FROM (baby_detail p
     LEFT JOIN headtotoe_examination a ON (((p.uhid)::text = (a.uhid)::text)));


ALTER TABLE birth_mod OWNER TO postgres;

--
-- TOC entry 183 (class 1259 OID 70731)
-- Name: device_v1; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW device_v1 AS
 SELECT p.uhid,
    p.gestationweekbylmp,
    p.gestationdaysbylmp,
    p.birthweight,
    p.gender,
    p.dateofbirth,
    p.timeofbirth,
    p.newbirthtime,
    p.anomaly_value,
    b.fio2,
    b.start_time,
    abs((date_part('epoch'::text, (b.start_time - (p.newbirthtime)::timestamp with time zone)) / (3600)::double precision)) AS device_hours
   FROM birth_mod p,
    device_ventilator_detail b
  WHERE ((p.uhid)::text = (b.uhid)::text);


ALTER TABLE device_v1 OWNER TO postgres;

--
-- TOC entry 184 (class 1259 OID 70736)
-- Name: device_12; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW device_12 AS
 SELECT device_v1.uhid,
    device_v1.gestationweekbylmp,
    device_v1.gestationdaysbylmp,
    device_v1.birthweight,
    device_v1.gender,
    device_v1.dateofbirth,
    device_v1.timeofbirth,
    device_v1.newbirthtime,
    device_v1.anomaly_value,
    device_v1.fio2,
    device_v1.start_time,
    device_v1.device_hours
   FROM device_v1
  GROUP BY device_v1.uhid, device_v1.gestationweekbylmp, device_v1.gestationdaysbylmp, device_v1.birthweight, device_v1.gender, device_v1.dateofbirth, device_v1.timeofbirth, device_v1.newbirthtime, device_v1.anomaly_value, device_v1.fio2, device_v1.start_time, device_v1.device_hours
 HAVING (device_v1.device_hours <= (12.0)::double precision);


ALTER TABLE device_12 OWNER TO postgres;


--
-- TOC entry 187 (class 1259 OID 70746)
-- Name: lab_v1; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW lab_v1 AS
 SELECT p.uhid,
    p.gestationweekbylmp,
    p.gestationdaysbylmp,
    p.birthweight,
    p.gender,
    p.dateofbirth,
    p.timeofbirth,
    p.newbirthtime,
    p.anomaly_value,
    c.itemid,
    c.itemname,
    c.itemvalue,
    c.itemunit,
    c.resultdate,
    c.creationtime,
    abs((date_part('epoch'::text, (c.creationtime - (p.newbirthtime)::timestamp with time zone)) / (3600)::double precision)) AS lab_hours
   FROM birth_mod p,
    test_result c
  WHERE (((p.uhid)::text = (c.prn)::text) AND ((c.itemid)::text = '521006'::text));


ALTER TABLE lab_v1 OWNER TO postgres;

--
-- TOC entry 188 (class 1259 OID 70751)
-- Name: lab_12; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW lab_12 AS
 SELECT lab_v1.uhid,
    lab_v1.gestationweekbylmp,
    lab_v1.gestationdaysbylmp,
    lab_v1.birthweight,
    lab_v1.gender,
    lab_v1.dateofbirth,
    lab_v1.timeofbirth,
    lab_v1.newbirthtime,
    lab_v1.anomaly_value,
    lab_v1.itemid,
    lab_v1.itemname,
    lab_v1.itemvalue,
    lab_v1.itemunit,
    lab_v1.resultdate,
    lab_v1.creationtime,
    lab_v1.lab_hours
   FROM lab_v1
  GROUP BY lab_v1.uhid, lab_v1.gestationweekbylmp, lab_v1.gestationdaysbylmp, lab_v1.birthweight, lab_v1.gender, lab_v1.dateofbirth, lab_v1.timeofbirth, lab_v1.newbirthtime, lab_v1.anomaly_value, lab_v1.itemid, lab_v1.itemname, lab_v1.itemvalue, lab_v1.itemunit, lab_v1.resultdate, lab_v1.creationtime, lab_v1.lab_hours
 HAVING (lab_v1.lab_hours <= (12.0)::double precision);


ALTER TABLE lab_12 OWNER TO postgres;


--
-- TOC entry 189 (class 1259 OID 70756)
-- Name: analytics_crib; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW analytics_crib AS
 SELECT p.uhid,
    p.gestationweekbylmp,
    p.gestationdaysbylmp,
    p.birthweight,
    p.gender,
    p.dateofbirth,
    p.timeofbirth,
    p.anomaly_value,
    b.fio2,
    b.start_time,
    b.device_hours,
    c.itemid,
    c.itemname,
    c.itemvalue,
    c.itemunit,
    c.resultdate,
    c.creationtime,
    c.lab_hours
   FROM ((birth_mod p
     LEFT JOIN device_12 b ON (((p.uhid)::text = (b.uhid)::text)))
     LEFT JOIN lab_12 c ON (((p.uhid)::text = (c.uhid)::text)));


ALTER TABLE analytics_crib OWNER TO postgres;


--
-- TOC entry 227 (class 1259 OID 70927)
-- Name: associate_assesment_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW associate_assesment_raw AS
 SELECT j.uhid,
    count(j.uhid) AS eventcount,
    'Jaundice'::text AS sa_event,
    (j.creationtime)::date AS creationtime
   FROM sa_jaundice j
  WHERE ((j.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY j.uhid, (j.creationtime)::date
UNION ALL
 SELECT r.uhid,
    count(r.uhid) AS eventcount,
    'RDS'::text AS sa_event,
    (r.creationtime)::date AS creationtime
   FROM sa_resp_rds r
  WHERE ((r.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY r.uhid, (r.creationtime)::date
UNION ALL
 SELECT a.uhid,
    count(a.uhid) AS eventcount,
    'Apnea'::text AS sa_event,
    (a.creationtime)::date AS creationtime
   FROM sa_resp_apnea a
  WHERE ((a.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY a.uhid, (a.creationtime)::date
UNION ALL
 SELECT p.uhid,
    count(p.uhid) AS eventcount,
    'PPHN'::text AS sa_event,
    (p.creationtime)::date AS creationtime
   FROM sa_resp_pphn p
  WHERE ((p.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY p.uhid, (p.creationtime)::date
UNION ALL
 SELECT n.uhid,
    count(n.uhid) AS eventcount,
    'Pneumothorax'::text AS sa_event,
    (n.creationtime)::date AS creationtime
   FROM sa_resp_pneumothorax n
  WHERE ((n.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY n.uhid, (n.creationtime)::date
UNION ALL
 SELECT go.uhid,
    count(go.uhid) AS eventcount,
    'Hypoglycemia'::text AS sa_event,
    (go.creationtime)::date AS creationtime
   FROM sa_hypoglycemia go
  WHERE ((go.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY go.uhid, (go.creationtime)::date
UNION ALL
 SELECT gy.uhid,
    count(gy.uhid) AS eventcount,
    'Hyperglycemia'::text AS sa_event,
    (gy.creationtime)::date AS creationtime
   FROM sa_hyperglycemia gy
  WHERE ((gy.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY gy.uhid, (gy.creationtime)::date
UNION ALL
 SELECT co.uhid,
    count(co.uhid) AS eventcount,
    'Hypocalcemia'::text AS sa_event,
    (co.creationtime)::date AS creationtime
   FROM sa_hypocalcemia co
  WHERE ((co.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY co.uhid, (co.creationtime)::date
UNION ALL
 SELECT cr.uhid,
    count(cr.uhid) AS eventcount,
    'Hypercalcemia'::text AS sa_event,
    (cr.creationtime)::date AS creationtime
   FROM sa_hypercalcemia cr
  WHERE ((cr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY cr.uhid, (cr.creationtime)::date
UNION ALL
 SELECT ko.uhid,
    count(ko.uhid) AS eventcount,
    'Hypokalemia'::text AS sa_event,
    (ko.creationtime)::date AS creationtime
   FROM sa_hyperkalemia ko
  WHERE ((ko.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ko.uhid, (ko.creationtime)::date
UNION ALL
 SELECT kr.uhid,
    count(kr.uhid) AS eventcount,
    'Hyperkalemia'::text AS sa_event,
    (kr.creationtime)::date AS creationtime
   FROM sa_hyperkalemia kr
  WHERE ((kr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY kr.uhid, (kr.creationtime)::date
UNION ALL
 SELECT no.uhid,
    count(no.uhid) AS eventcount,
    'Hyponatremia'::text AS sa_event,
    (no.creationtime)::date AS creationtime
   FROM sa_hyponatremia no
  WHERE ((no.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY no.uhid, (no.creationtime)::date
UNION ALL
 SELECT nr.uhid,
    count(nr.uhid) AS eventcount,
    'Hypernatremia'::text AS sa_event,
    (nr.creationtime)::date AS creationtime
   FROM sa_hypernatremia nr
  WHERE ((nr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY nr.uhid, (nr.creationtime)::date
UNION ALL
 SELECT ac.uhid,
    count(ac.uhid) AS eventcount,
    'Acidosis'::text AS sa_event,
    (ac.creationtime)::date AS creationtime
   FROM sa_acidosis ac
  WHERE ((ac.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ac.uhid, (ac.creationtime)::date
UNION ALL
 SELECT ie.uhid,
    count(ie.uhid) AS eventcount,
    'IEM'::text AS sa_event,
    (ie.creationtime)::date AS creationtime
   FROM sa_iem ie
  WHERE ((ie.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ie.uhid, (ie.creationtime)::date
UNION ALL
 SELECT asp.uhid,
    count(asp.uhid) AS eventcount,
    'Asphyxia'::text AS sa_event,
    (asp.creationtime)::date AS creationtime
   FROM sa_cns_asphyxia asp
  WHERE ((asp.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY asp.uhid, (asp.creationtime)::date
UNION ALL
 SELECT sei.uhid,
    count(sei.uhid) AS eventcount,
    'Seizure'::text AS sa_event,
    (sei.creationtime)::date AS creationtime
   FROM sa_cns_seizures sei
  WHERE ((sei.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY sei.uhid, (sei.creationtime)::date
UNION ALL
 SELECT ivh.uhid,
    count(ivh.uhid) AS eventcount,
    'IVH'::text AS sa_event,
    (ivh.creationtime)::date AS creationtime
   FROM sa_cns_ivh ivh
  WHERE ((ivh.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ivh.uhid, (ivh.creationtime)::date;


ALTER TABLE associate_assesment_raw OWNER TO postgres;


--
-- TOC entry 228 (class 1259 OID 70932)
-- Name: associate_assesment_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW associate_assesment_control AS
 SELECT associate_assesment_raw.uhid,
    associate_assesment_raw.eventcount,
    associate_assesment_raw.sa_event,
    associate_assesment_raw.creationtime
   FROM associate_assesment_raw
  ORDER BY associate_assesment_raw.creationtime DESC;


ALTER TABLE associate_assesment_control OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 70936)
-- Name: associate_assesment_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW associate_assesment_final AS
 SELECT DISTINCT associate_assesment_control.uhid,
        CASE
            WHEN (associate_assesment_control.eventcount > 0) THEN (((associate_assesment_control.sa_event || ' ('::text) || associate_assesment_control.creationtime) || ') '::text)
            ELSE ','::text
        END AS associated_event,
    associate_assesment_control.creationtime
   FROM associate_assesment_control
  ORDER BY associate_assesment_control.creationtime DESC;


ALTER TABLE associate_assesment_final OWNER TO postgres;



--
-- TOC entry 234 (class 1259 OID 70954)
-- Name: baby_admissionweight_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_admissionweight_control AS
 SELECT b.uhid,
    (v.creationtime)::date AS creationtime,
    b.dateofadmission,
    v.currentdateweight AS weightatadmission
   FROM (baby_visit v
     JOIN baby_detail b ON (((b.uhid)::text = (v.uhid)::text)))
  WHERE ((v.visitdate = b.dateofadmission) AND (b.admissionstatus = true));


ALTER TABLE baby_admissionweight_control OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 70959)
-- Name: baby_currentdayweight_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_currentdayweight_control AS
 SELECT b.uhid,
    (v.creationtime)::date AS creationtime,
    v.visitdate,
    v.currentdateweight AS currentdayweight
   FROM (baby_visit v
     JOIN baby_detail b ON (((b.uhid)::text = (v.uhid)::text)))
  WHERE ((v.visitdate = (now())::date) AND (b.admissionstatus = true));


ALTER TABLE baby_currentdayweight_control OWNER TO postgres;




--
-- TOC entry 238 (class 1259 OID 70979)
-- Name: baby_feeddetail_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_feeddetail_final AS
 SELECT t.uhid,
    t.feedtext
   FROM (babyfeed_detail t
     JOIN ( SELECT u.uhid,
            max(u.creationtime) AS maxdate
           FROM babyfeed_detail u
          GROUP BY u.uhid) tm ON (((((t.uhid)::text = (tm.uhid)::text) AND ((t.creationtime)::date = ('now'::text)::date)) AND (t.creationtime = tm.maxdate))))
  WHERE ((t.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  ORDER BY t.creationtime DESC;


ALTER TABLE baby_feeddetail_final OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 70984)
-- Name: baby_lastdayweight_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_lastdayweight_control AS
 SELECT b.uhid,
    (v.creationtime)::date AS creationtime,
    v.visitdate,
    v.currentdateweight AS lastdayweight
   FROM (baby_visit v
     JOIN baby_detail b ON (((b.uhid)::text = (v.uhid)::text)))
  WHERE ((v.visitdate = ((now())::date - 1)) AND (b.admissionstatus = true));


ALTER TABLE baby_lastdayweight_control OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 70989)
-- Name: vw_sa_assesment_count_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_raw AS
 SELECT j.uhid,
    count(j.uhid) AS eventcount,
    'jaundice'::text AS sa_event,
    (j.creationtime)::date AS creationtime
   FROM sa_jaundice j
  WHERE ((j.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY j.uhid, (j.creationtime)::date
UNION ALL
 SELECT r.uhid,
    count(r.uhid) AS eventcount,
    'rds'::text AS sa_event,
    (r.creationtime)::date AS creationtime
   FROM sa_resp_rds r
  WHERE ((r.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY r.uhid, (r.creationtime)::date
UNION ALL
 SELECT a.uhid,
    count(a.uhid) AS eventcount,
    'apnea'::text AS sa_event,
    (a.creationtime)::date AS creationtime
   FROM sa_resp_apnea a
  WHERE ((a.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY a.uhid, (a.creationtime)::date
UNION ALL
 SELECT p.uhid,
    count(p.uhid) AS eventcount,
    'pphn'::text AS sa_event,
    (p.creationtime)::date AS creationtime
   FROM sa_resp_pphn p
  WHERE ((p.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY p.uhid, (p.creationtime)::date
UNION ALL
 SELECT n.uhid,
    count(n.uhid) AS eventcount,
    'pneumothorax'::text AS sa_event,
    (n.creationtime)::date AS creationtime
   FROM sa_resp_pneumothorax n
  WHERE ((n.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY n.uhid, (n.creationtime)::date
UNION ALL
 SELECT go.uhid,
    count(go.uhid) AS eventcount,
    'hypoglycemia'::text AS sa_event,
    (go.creationtime)::date AS creationtime
   FROM sa_hypoglycemia go
  WHERE ((go.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY go.uhid, (go.creationtime)::date
UNION ALL
 SELECT gy.uhid,
    count(gy.uhid) AS eventcount,
    'hyperglycemia'::text AS sa_event,
    (gy.creationtime)::date AS creationtime
   FROM sa_hyperglycemia gy
  WHERE ((gy.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY gy.uhid, (gy.creationtime)::date
UNION ALL
 SELECT co.uhid,
    count(co.uhid) AS eventcount,
    'hypocalcemia'::text AS sa_event,
    (co.creationtime)::date AS creationtime
   FROM sa_hypocalcemia co
  WHERE ((co.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY co.uhid, (co.creationtime)::date
UNION ALL
 SELECT cr.uhid,
    count(cr.uhid) AS eventcount,
    'hypercalcemia'::text AS sa_event,
    (cr.creationtime)::date AS creationtime
   FROM sa_hypercalcemia cr
  WHERE ((cr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY cr.uhid, (cr.creationtime)::date
UNION ALL
 SELECT ko.uhid,
    count(ko.uhid) AS eventcount,
    'hypokalemia'::text AS sa_event,
    (ko.creationtime)::date AS creationtime
   FROM sa_hyperkalemia ko
  WHERE ((ko.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ko.uhid, (ko.creationtime)::date
UNION ALL
 SELECT kr.uhid,
    count(kr.uhid) AS eventcount,
    'hyperkalemia'::text AS sa_event,
    (kr.creationtime)::date AS creationtime
   FROM sa_hyperkalemia kr
  WHERE ((kr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY kr.uhid, (kr.creationtime)::date
UNION ALL
 SELECT no.uhid,
    count(no.uhid) AS eventcount,
    'hyponatremia'::text AS sa_event,
    (no.creationtime)::date AS creationtime
   FROM sa_hyponatremia no
  WHERE ((no.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY no.uhid, (no.creationtime)::date
UNION ALL
 SELECT nr.uhid,
    count(nr.uhid) AS eventcount,
    'hypernatremia'::text AS sa_event,
    (nr.creationtime)::date AS creationtime
   FROM sa_hypernatremia nr
  WHERE ((nr.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY nr.uhid, (nr.creationtime)::date
UNION ALL
 SELECT ac.uhid,
    count(ac.uhid) AS eventcount,
    'acidosis'::text AS sa_event,
    (ac.creationtime)::date AS creationtime
   FROM sa_acidosis ac
  WHERE ((ac.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ac.uhid, (ac.creationtime)::date
UNION ALL
 SELECT ie.uhid,
    count(ie.uhid) AS eventcount,
    'iem'::text AS sa_event,
    (ie.creationtime)::date AS creationtime
   FROM sa_iem ie
  WHERE ((ie.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ie.uhid, (ie.creationtime)::date
UNION ALL
 SELECT asp.uhid,
    count(asp.uhid) AS eventcount,
    'asphyxia'::text AS sa_event,
    (asp.creationtime)::date AS creationtime
   FROM sa_cns_asphyxia asp
  WHERE ((asp.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY asp.uhid, (asp.creationtime)::date
UNION ALL
 SELECT sei.uhid,
    count(sei.uhid) AS eventcount,
    'seizure'::text AS sa_event,
    (sei.creationtime)::date AS creationtime
   FROM sa_cns_seizures sei
  WHERE ((sei.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY sei.uhid, (sei.creationtime)::date
UNION ALL
 SELECT ivh.uhid,
    count(ivh.uhid) AS eventcount,
    'ivh'::text AS sa_event,
    (ivh.creationtime)::date AS creationtime
   FROM sa_cns_ivh ivh
  WHERE ((ivh.uhid)::text IN ( SELECT baby_detail.uhid
           FROM baby_detail
          WHERE (baby_detail.admissionstatus = true)))
  GROUP BY ivh.uhid, (ivh.creationtime)::date;


ALTER TABLE vw_sa_assesment_count_raw OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 70994)
-- Name: vw_sa_assesment_count_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_control AS
 SELECT s.uhid,
    s.creationtime,
    max(
        CASE s.sa_event
            WHEN 'jaundice'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS jaundice,
    max(
        CASE s.sa_event
            WHEN 'rds'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS rds,
    max(
        CASE s.sa_event
            WHEN 'apnea'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS apnea,
    max(
        CASE s.sa_event
            WHEN 'pphn'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS pphn,
    max(
        CASE s.sa_event
            WHEN 'pneumothorax'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS pneumothorax,
    max(
        CASE s.sa_event
            WHEN 'hypoglycemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hypoglycemia,
    max(
        CASE s.sa_event
            WHEN 'hyperglycemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hyperglycemia,
    max(
        CASE s.sa_event
            WHEN 'hypocalcemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hypocalcemia,
    max(
        CASE s.sa_event
            WHEN 'hypercalcemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hypercalcemia,
    max(
        CASE s.sa_event
            WHEN 'hypokalemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hypokalemia,
    max(
        CASE s.sa_event
            WHEN 'hyperkalemia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hyperkalemia,
    max(
        CASE s.sa_event
            WHEN 'hyponatremia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hyponatremia,
    max(
        CASE s.sa_event
            WHEN 'hypernatremia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS hypernatremia,
    max(
        CASE s.sa_event
            WHEN 'acidosis'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS acidosis,
    max(
        CASE s.sa_event
            WHEN 'iem'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS iem,
    max(
        CASE s.sa_event
            WHEN 'asphyxia'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS asphyxia,
    max(
        CASE s.sa_event
            WHEN 'seizure'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS seizure,
    max(
        CASE s.sa_event
            WHEN 'ivh'::text THEN (s.eventcount)::text
            ELSE '0'::text
        END) AS ivh
   FROM ( SELECT r.uhid,
            r.sa_event,
            r.eventcount,
            r.creationtime
           FROM vw_sa_assesment_count_raw r
          GROUP BY r.uhid, r.sa_event, r.eventcount, r.creationtime) s
  GROUP BY s.uhid, s.creationtime
  ORDER BY s.creationtime;


ALTER TABLE vw_sa_assesment_count_control OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 70999)
-- Name: vw_sa_assesment_count_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_rule AS
 SELECT DISTINCT r.uhid,
        CASE
            WHEN ((r.jaundice)::integer > 0) THEN 'Jaundice'::text
            ELSE ','::text
        END AS jaundice,
        CASE
            WHEN ((r.rds)::integer > 0) THEN 'RDS'::text
            ELSE ','::text
        END AS rds,
        CASE
            WHEN ((r.apnea)::integer > 0) THEN 'Apnea'::text
            ELSE ','::text
        END AS apnea,
        CASE
            WHEN ((r.pphn)::integer > 0) THEN 'PPHN'::text
            ELSE ','::text
        END AS pphn,
        CASE
            WHEN ((r.pneumothorax)::integer > 0) THEN 'Pneumothorax'::text
            ELSE ','::text
        END AS pneumothorax,
        CASE
            WHEN ((r.hypoglycemia)::integer > 0) THEN 'Hypoglycemia'::text
            ELSE ','::text
        END AS hypoglycemia,
        CASE
            WHEN ((r.hyperglycemia)::integer > 0) THEN 'Hyperglycemia'::text
            ELSE ','::text
        END AS hyperglycemia,
        CASE
            WHEN ((r.hypocalcemia)::integer > 0) THEN 'Hypocalcemia'::text
            ELSE ','::text
        END AS hypocalcemia,
        CASE
            WHEN ((r.hypercalcemia)::integer > 0) THEN 'Hypercalcemia'::text
            ELSE ','::text
        END AS hypercalcemia,
        CASE
            WHEN ((r.hypokalemia)::integer > 0) THEN 'Hypokalemia'::text
            ELSE ','::text
        END AS hypokalemia,
        CASE
            WHEN ((r.hyperkalemia)::integer > 0) THEN 'Hyperkalemia'::text
            ELSE ','::text
        END AS hyperkalemia,
        CASE
            WHEN ((r.hyponatremia)::integer > 0) THEN 'Hyponatremia'::text
            ELSE ','::text
        END AS hyponatremia,
        CASE
            WHEN ((r.hypernatremia)::integer > 0) THEN 'Hypernatremia'::text
            ELSE ','::text
        END AS hypernatremia,
        CASE
            WHEN ((r.acidosis)::integer > 0) THEN 'Acidosis'::text
            ELSE ','::text
        END AS acidosis,
        CASE
            WHEN ((r.iem)::integer > 0) THEN 'IEM'::text
            ELSE ','::text
        END AS iem,
        CASE
            WHEN ((r.asphyxia)::integer > 0) THEN 'Asphyxia'::text
            ELSE ','::text
        END AS asphyxia,
        CASE
            WHEN ((r.seizure)::integer > 0) THEN 'Seizure'::text
            ELSE ','::text
        END AS seizure,
        CASE
            WHEN ((r.ivh)::integer > 0) THEN 'IVH'::text
            ELSE ','::text
        END AS ivh
   FROM vw_sa_assesment_count_control r;


ALTER TABLE vw_sa_assesment_count_rule OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 71004)
-- Name: vw_sa_assesment_count_rule1; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_rule1 AS
 SELECT DISTINCT r.uhid,
    btrim(((((((((((((((((((((((((((((((((((r.jaundice || ','::text) || r.rds) || ','::text) || r.apnea) || ','::text) || r.pphn) || ','::text) || r.pneumothorax) || ','::text) || r.hypoglycemia) || ','::text) || r.hyperglycemia) || ','::text) || r.hypocalcemia) || ','::text) || r.hypercalcemia) || ','::text) || r.hypokalemia) || ','::text) || r.hyperkalemia) || ','::text) || r.hyponatremia) || ','::text) || r.hypernatremia) || ','::text) || r.acidosis) || ','::text) || r.iem) || ','::text) || r.asphyxia) || ','::text) || r.seizure) || ','::text) || r.ivh), ','::text) AS eventlist
   FROM vw_sa_assesment_count_rule r;


ALTER TABLE vw_sa_assesment_count_rule1 OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 71009)
-- Name: vw_sa_assesment_count_rule2; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_rule2 AS
 SELECT r.uhid,
    btrim(string_agg(r.eventlist, ', '::text), ','::text) AS eventlist
   FROM vw_sa_assesment_count_rule1 r
  GROUP BY r.uhid;


ALTER TABLE vw_sa_assesment_count_rule2 OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 71013)
-- Name: vw_sa_assesment_count_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_assesment_count_final AS
 SELECT DISTINCT r.uhid,
    btrim(regexp_replace(r.eventlist, '(,){2,}'::text, ','::text, 'g'::text)) AS diagnosis
   FROM vw_sa_assesment_count_rule2 r;


ALTER TABLE vw_sa_assesment_count_final OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 71017)
-- Name: baby_dashboard_listview_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_dashboard_listview_final AS
 SELECT b.uhid,
    b.isassessmentsubmit,
    b.episodeid,
    b.babyname,
    b.nicubedno,
    b.dateofbirth,
    b.dateofadmission,
    ((((now())::date - b.dateofbirth) || ' '::text) || 'days'::text) AS dayoflife,
    ac.weightatadmission,
    lc.lastdayweight,
    cc.currentdayweight,
    (cc.currentdayweight - lc.lastdayweight) AS difference,
    bf.feedtext AS feed_detail,
    bd.diagnosis
   FROM (((((baby_detail b
     LEFT JOIN baby_admissionweight_control ac ON (((b.uhid)::text = (ac.uhid)::text)))
     LEFT JOIN baby_lastdayweight_control lc ON (((b.uhid)::text = (lc.uhid)::text)))
     LEFT JOIN baby_currentdayweight_control cc ON (((b.uhid)::text = (cc.uhid)::text)))
     LEFT JOIN vw_sa_assesment_count_final bd ON (((b.uhid)::text = (bd.uhid)::text)))
     LEFT JOIN baby_feeddetail_final bf ON (((b.uhid)::text = (bf.uhid)::text)))
  WHERE (b.admissionstatus = true)
  ORDER BY b.nicubedno;


ALTER TABLE baby_dashboard_listview_final OWNER TO postgres;



--
-- TOC entry 249 (class 1259 OID 71031)
-- Name: baby_diagnosis_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_diagnosis_raw AS
 SELECT t.uhid,
    t.diagnosis,
    t.creationtime
   FROM (doctor_notes t
     JOIN ( SELECT doctor_notes.uhid,
            max(doctor_notes.creationtime) AS maxdate
           FROM doctor_notes
          GROUP BY doctor_notes.uhid) tm ON ((((t.uhid)::text = (tm.uhid)::text) AND (t.creationtime = tm.maxdate))))
  ORDER BY t.creationtime DESC;


ALTER TABLE baby_diagnosis_raw OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 71036)
-- Name: baby_diagnosis_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_diagnosis_control AS
 SELECT t.uhid,
    t.diagnosis,
    t.creationtime
   FROM baby_diagnosis_raw t;


ALTER TABLE baby_diagnosis_control OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 71040)
-- Name: baby_feeddetail_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_feeddetail_raw AS
 SELECT babyfeed_detail.uhid,
    (babyfeed_detail.creationtime)::date AS creationtime,
        CASE
            WHEN (babyfeed_detail.tfi IS NULL) THEN ' '::character varying
            ELSE babyfeed_detail.tfi
        END AS tfi,
        CASE
            WHEN (babyfeed_detail.total_intake IS NULL) THEN ''::character varying
            ELSE babyfeed_detail.total_intake
        END AS total_intake,
        CASE
            WHEN (babyfeed_detail.feedmethod IS NULL) THEN ''::character varying
            ELSE babyfeed_detail.feedmethod
        END AS feedmethod,
        CASE
            WHEN (babyfeed_detail.feedtype IS NULL) THEN ''::character varying
            ELSE babyfeed_detail.feedtype
        END AS feedtype,
        CASE
            WHEN ((babyfeed_detail.totalenteraadditivelvolume)::character varying IS NULL) THEN (0)::real
            ELSE babyfeed_detail.totalenteraadditivelvolume
        END AS totalenteraadditivelvolume,
        CASE
            WHEN ((babyfeed_detail.totalparenteraladditivevolume)::character varying IS NULL) THEN (0)::real
            ELSE babyfeed_detail.totalparenteraladditivevolume
        END AS totalparenteraladditivevolume
   FROM babyfeed_detail;


ALTER TABLE baby_feeddetail_raw OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 71045)
-- Name: baby_feeddetail_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_feeddetail_control AS
 SELECT t.uhid,
    t.creationtime,
    t.tfi,
    t.total_intake,
    t.feedmethod,
    t.feedtype,
    t.totalenteraadditivelvolume,
    t.totalparenteraladditivevolume
   FROM (baby_feeddetail_raw t
     JOIN ( SELECT baby_feeddetail_raw.uhid,
            max(baby_feeddetail_raw.creationtime) AS maxdate
           FROM baby_feeddetail_raw
          GROUP BY baby_feeddetail_raw.uhid) tm ON ((((t.uhid)::text = (tm.uhid)::text) AND (t.creationtime = tm.maxdate))))
  ORDER BY t.creationtime DESC;


ALTER TABLE baby_feeddetail_control OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 71050)
-- Name: baby_feeddetail_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW baby_feeddetail_rule AS
 SELECT t.uhid,
    t.creationtime,
    t.tfi,
    t.total_intake,
    t.feedmethod,
    t.feedtype,
    t.totalenteraadditivelvolume,
    t.totalparenteraladditivevolume
   FROM baby_feeddetail_control t
  ORDER BY t.creationtime DESC;


ALTER TABLE baby_feeddetail_rule OWNER TO postgres;


--
-- TOC entry 264 (class 1259 OID 71096)
-- Name: bed_device_history; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW bed_device_history AS
 SELECT b.uhid,
    bd.bedid,
    bd.deviceid,
    bd.creationtime,
    dd.devicetype,
    dd.devicebrand,
    dd.deviceserialno,
    dd.description,
    dd.available,
    dd.ipaddress,
    dd.portno,
    dd.domainid,
    dd.endtime
   FROM ((baby_detail b
     JOIN bed_device_detail bd ON (((b.nicubedno)::text = (bd.bedid)::text)))
     LEFT JOIN device_detail dd ON (((bd.deviceid)::text = (dd.deviceid)::text)));


ALTER TABLE bed_device_history OWNER TO postgres;


--
-- TOC entry 285 (class 1259 OID 71177)
-- Name: dashboarddevicedetail_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW dashboarddevicedetail_raw AS
 SELECT bd.uhid,
    bd.babyname,
    bd.baby_type,
    bd.baby_number,
    rb.status AS isavailable,
    rc.levelname AS condition,
    bd.dateofadmission AS admitdate,
    bd.timeofadmission AS admittime,
    bd.gender,
    bd.dateofbirth,
    bd.timeofbirth,
    bd.birthweight,
        CASE
            WHEN (bd.actualgestationdays IS NULL) THEN ((bd.actualgestationweek)::text || ' Weeks'::text)
            ELSE ((((bd.actualgestationweek)::text || ' Weeks '::text) || (bd.actualgestationdays)::text) || ' Days'::text)
        END AS gestation,
    rb.bedid,
    rb.bedname AS bedno,
    rr.roomid,
    rr.roomname,
    rl.levelname AS patientlevel,
    pd.pulserate,
    pd.spo2,
    pd.heartrate,
    pd.ecg_resprate,
    pd.co2_resprate,
    pd.etco2,
    pd.sys_bp,
    pd.dia_bp,
    pd.mean_bp,
    bd.comments,
        CASE
            WHEN (bd.admissionstatus IS TRUE) THEN 'TRUE'::text
            ELSE 'FALSE'::text
        END AS admissionstatus,
    pd.nbp_s,
    pd.nbp_d,
    pd.nbp_m,
    pd.creationtime,
    pd.starttime,
    vt.fio2,
    vt.pip,
    vt.peep,
    vt.start_time,
    concat((parentdetail.address)::text, ', ', (parentdetail.add_state)::text, ', ', (parentdetail.add_district)::text, ', ', (parentdetail.add_city)::text, ', ', ', ', (parentdetail.add_pin)::text) AS address
   FROM (((((((baby_detail bd
     JOIN ref_bed rb ON (((bd.nicubedno)::text = (rb.bedid)::text)))
     JOIN ref_room rr ON (((bd.nicuroomno)::text = (rr.roomid)::text)))
     LEFT JOIN ref_level rl ON (((bd.niculevelno)::text = (rl.levelid)::text)))
     LEFT JOIN ref_criticallevel rc ON (((bd.criticalitylevel)::text = (rc.crlevelid)::text)))
     LEFT JOIN device_monitor_detail pd ON ((((bd.uhid)::text = (pd.uhid)::text) AND (pd.starttime = ( SELECT max(lp.starttime) AS max
           FROM device_monitor_detail lp
          WHERE ((lp.uhid)::text = (bd.uhid)::text))))))
     LEFT JOIN device_ventilator_detail vt ON ((((bd.uhid)::text = (vt.uhid)::text) AND (vt.start_time = ( SELECT max(v.start_time) AS max
           FROM device_ventilator_detail v
          WHERE ((v.uhid)::text = (bd.uhid)::text))))))
     JOIN parent_detail parentdetail ON (((bd.uhid)::text = (parentdetail.uhid)::text)))
  WHERE (bd.admissionstatus = true)
  ORDER BY bd.creationtime DESC;


ALTER TABLE dashboarddevicedetail_raw OWNER TO postgres;



--
-- TOC entry 290 (class 1259 OID 71197)
-- Name: notification_detail; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW notification_detail AS
 SELECT u.uhid,
    u.notificationid,
    n.message,
    n.messagetype,
    (u.creationtime)::date AS notificationdate
   FROM (notifications n
     JOIN uhidnotification u ON (((u.notificationid)::bigint = n.notificationid)));


ALTER TABLE notification_detail OWNER TO postgres;

--
-- TOC entry 291 (class 1259 OID 71201)
-- Name: notificationcount_detail; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW notificationcount_detail AS
 SELECT r.uhid,
    count(r.notificationid) AS notifcount
   FROM notification_detail r
  GROUP BY r.uhid;


ALTER TABLE notificationcount_detail OWNER TO postgres;

--
-- TOC entry 292 (class 1259 OID 71205)
-- Name: dashboarddevicedetail_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW dashboarddevicedetail_control AS
 SELECT r.uhid,
    n.notifcount AS messagecount
   FROM (dashboarddevicedetail_raw r
     LEFT JOIN notificationcount_detail n ON (((r.uhid)::text = (n.uhid)::text)));


ALTER TABLE dashboarddevicedetail_control OWNER TO postgres;

--
-- TOC entry 293 (class 1259 OID 71210)
-- Name: dashboarddevicedetail_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW dashboarddevicedetail_final AS
 SELECT r.uhid,
    r.address,
    r.babyname,
    r.baby_type,
    r.baby_number,
    r.isavailable,
    r.condition,
    c.messagecount,
    r.admitdate,
    r.admittime,
    r.gender,
    r.dateofbirth,
    r.timeofbirth,
    r.birthweight,
    r.gestation,
    r.bedid,
    r.bedno,
    r.roomid,
    r.roomname,
    r.patientlevel,
    r.pulserate,
    r.spo2,
    r.heartrate,
    r.ecg_resprate,
    r.co2_resprate,
    r.etco2,
    r.sys_bp,
    r.dia_bp,
    r.mean_bp,
    r.comments,
    r.admissionstatus,
    r.nbp_s,
    r.nbp_d,
    r.nbp_m,
    r.creationtime,
    r.starttime,
    r.fio2,
    r.pip,
    r.peep,
    r.start_time
   FROM (dashboarddevicedetail_raw r
     JOIN dashboarddevicedetail_control c ON (((r.uhid)::text = (c.uhid)::text)))
  WHERE (r.admissionstatus = 'TRUE'::text)
  ORDER BY r.admitdate DESC;


ALTER TABLE dashboarddevicedetail_final OWNER TO postgres;

--
-- TOC entry 294 (class 1259 OID 71215)
-- Name: dashboardbeddetail_master; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW dashboardbeddetail_master AS
 SELECT r.bedid AS bedno,
    r.bedname,
    rr.roomid,
    rr.roomname,
    r.status AS isavailable,
    f.uhid
   FROM ((ref_bed r
     LEFT JOIN dashboarddevicedetail_final f ON ((((r.bedname)::text = (f.bedno)::text) AND ((r.roomid)::text = (f.roomid)::text))))
     LEFT JOIN ref_room rr ON (((rr.roomid)::text = (r.roomid)::text)))
  WHERE (r.isactive = true);


ALTER TABLE dashboardbeddetail_master OWNER TO postgres;

--
-- TOC entry 295 (class 1259 OID 71220)
-- Name: dashboard_finalview; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW dashboard_finalview AS
 SELECT r.uhid,
    r.address,
    r.baby_type,
    r.baby_number,
    lv.isassessmentsubmit,
    lv.episodeid,
    r.babyname,
    m.isavailable,
        CASE
            WHEN (r.condition IS NULL) THEN 'NA'::character varying
            ELSE r.condition
        END AS condition,
    r.messagecount,
    r.admitdate,
    r.admittime,
    r.gender,
    r.dateofbirth,
    r.timeofbirth,
    r.birthweight,
    r.gestation,
    m.bedno AS bedid,
    m.bedname AS bedno,
    m.roomid,
    m.roomname,
    r.patientlevel,
    r.pulserate,
    r.spo2,
    r.heartrate,
    r.ecg_resprate,
    r.co2_resprate,
    r.etco2,
    r.sys_bp,
    r.dia_bp,
    r.mean_bp,
    r.comments,
    r.admissionstatus,
    r.nbp_s,
    r.nbp_d,
    r.nbp_m,
    r.creationtime,
    r.starttime,
    lv.dayoflife,
    lv.weightatadmission,
    lv.lastdayweight,
    lv.currentdayweight,
    lv.difference,
    lv.feed_detail,
    lv.diagnosis,
    r.fio2,
    r.pip,
    r.peep,
    r.start_time
   FROM ((dashboardbeddetail_master m
     LEFT JOIN dashboarddevicedetail_final r ON ((((m.bedname)::text = (r.bedno)::text) AND ((r.roomid)::text = (m.roomid)::text))))
     LEFT JOIN baby_dashboard_listview_final lv ON (((r.uhid)::text = (lv.uhid)::text)))
  ORDER BY m.bedno;


ALTER TABLE dashboard_finalview OWNER TO postgres;



--
-- TOC entry 360 (class 1259 OID 71458)
-- Name: insulin_dose_count_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW insulin_dose_count_final AS
 SELECT v.uhid,
    '1'::text AS insulincount
   FROM sa_hyperglycemia v;


ALTER TABLE insulin_dose_count_final OWNER TO postgres;



--
-- TOC entry 424 (class 1259 OID 71734)
-- Name: patientdischarge_medicalhistory; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW patientdischarge_medicalhistory AS
 SELECT ds.uhid,
    ds.dischargepatientid,
    bp.medicinename,
    bp.route,
    bp.dose,
    bp.frequency,
        CASE
            WHEN (bp.starttime IS NULL) THEN (bp.startdate)::text
            ELSE (((bp.startdate)::text || ' '::text) || (bp.starttime)::text)
        END AS startdate,
        CASE
            WHEN (bp.endtime IS NULL) THEN (bp.enddate)::text
            ELSE (((bp.enddate)::text || ' '::text) || (bp.endtime)::text)
        END AS enddate,
    bp.medicationtype,
    bp.comments
   FROM (dischargepatient_detail ds
     JOIN baby_prescription bp ON (((ds.uhid)::text = (bp.uhid)::text)));


ALTER TABLE patientdischarge_medicalhistory OWNER TO postgres;

--
-- TOC entry 425 (class 1259 OID 71739)
-- Name: patientdischarge_page1; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW patientdischarge_page1 AS
 SELECT bd.uhid,
    ds.dischargepatientid,
    ds.baby_discharge_status,
    ds.additional_doctor_notes,
    ds.discharge_print_flag,
    ds.dischargedate,
    bd.babyname,
    bd.gender,
    bd.dateofbirth,
    bd.timeofbirth,
    bd.dateofadmission,
    bd.timeofadmission,
    bd.birthweight,
    bd.birthlength,
    bd.birthheadcircumference,
    bd.obstetrician,
    bd.admittingdoctor,
    bd.iptagno,
    bd.inout_patient_status,
        CASE
            WHEN ((bd.gestationdaysbylmp IS NULL) OR (bd.gestationdaysbylmp = 0)) THEN ((bd.gestationweekbylmp)::text || ' Weeks'::text)
            ELSE (((bd.gestationweekbylmp || ' Weeks '::text) || bd.gestationdaysbylmp) || ' Days'::text)
        END AS gestationbylmp,
        CASE
            WHEN ((bd.actualgestationdays IS NULL) OR (bd.actualgestationdays = 0)) THEN ((bd.actualgestationweek)::text || ' Weeks'::text)
            ELSE (((bd.actualgestationweek || ' Weeks '::text) || bd.actualgestationdays) || ' Days'::text)
        END AS actualgestation,
    bd.bloodgroup,
    bd.nicubedno,
    bd.nicuroomno,
    bd.niculevelno,
    bd.criticalitylevel,
    bd.significantmaterialid,
    bd.headtotoeid,
    bd.comments,
    bd.activestatus,
    bd.admissionstatus,
    bd.cry,
    bd.timeofcry,
    bd.dischargestatus,
    bd.dischargeddate,
    pd.mothername,
    pd.motherdob,
    pd.motherage,
    pd.fathername,
    pd.fatherdob,
    pd.fatherage,
    pd.primaryphonenumber,
    pd.secondaryphonenumber,
    pd.emailid,
    pd.address,
    pd.aadharcard,
    pd.villagename,
    cd.lmp,
    cd.edd,
    cd.g_score,
    cd.a_score,
    cd.p_score,
    cd.l_score,
    cd.deliverymode,
    cd.apgar_score_1min,
    cd.apgar_score_5min,
    cd.immunized,
    cd.vitamink_status,
    cd.antenatal_steroids,
    cd.anc,
    cd.flatus_tube,
    cd.resuscitation,
    bv.currentdateweight,
    bv.currentdateheight,
    bv.currentdateheadcircum
   FROM ((((baby_detail bd
     JOIN dischargepatient_detail ds ON (((bd.uhid)::text = (ds.uhid)::text)))
     LEFT JOIN parent_detail pd ON (((bd.uhid)::text = (pd.uhid)::text)))
     LEFT JOIN mother_current_pregnancy cd ON (((bd.uhid)::text = (cd.uhid)::text)))
     LEFT JOIN baby_visit bv ON ((((bd.uhid)::text = (bv.uhid)::text) AND ((bv.creationtime)::date = ('now'::text)::date))));


ALTER TABLE patientdischarge_page1 OWNER TO postgres;

--
-- TOC entry 426 (class 1259 OID 71744)
-- Name: patientdischarge_page2; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW patientdischarge_page2 AS
 SELECT ds.uhid,
    ds.dischargepatientid,
    dd.doctornotesid,
    dn.doctornotes,
    dd.followupnotesid,
    dn.followupnotes,
    dd.diagnosisid,
        CASE
            WHEN (dd.diagnosisid IS NULL) THEN '-'::character varying
            ELSE dn.diagnosis
        END AS diagnosis,
    dd.issuesid,
        CASE
            WHEN (dd.issuesid IS NULL) THEN '-'::character varying
            ELSE dn.issues
        END AS issues,
    dd.planid,
        CASE
            WHEN (dd.planid IS NULL) THEN '-'::character varying
            ELSE dn.plan
        END AS plan
   FROM ((patientdischarge_page1 ds
     JOIN discharge_doctornotes dd ON (((ds.dischargepatientid)::text = (dd.dischargepatientid)::text)))
     JOIN doctor_notes dn ON ((((ds.uhid)::text = (dn.uhid)::text) AND (((dd.doctornotesid)::text = (dn.doctornoteid)::text) OR ((dd.followupnotesid)::text = (dn.doctornoteid)::text)))));


ALTER TABLE patientdischarge_page2 OWNER TO postgres;

--
-- TOC entry 427 (class 1259 OID 71749)
-- Name: patientdischarge_page_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW patientdischarge_page_raw AS
 SELECT bd.uhid,
    ds.dischargepatientid,
    ds.baby_discharge_status,
    ds.additional_doctor_notes,
    ds.discharge_print_flag,
    ds.dischargedate,
    bd.babyname,
    bd.gender,
    bd.dateofbirth,
    bd.timeofbirth,
    bd.dateofadmission,
    bd.timeofadmission,
    bd.birthweight,
    bd.birthlength,
    bd.birthheadcircumference,
    bd.obstetrician,
    bd.admittingdoctor,
    bd.iptagno,
    bd.inout_patient_status,
        CASE
            WHEN ((bd.gestationdaysbylmp IS NULL) OR (bd.gestationdaysbylmp = 0)) THEN ((bd.gestationweekbylmp)::text || ' Weeks'::text)
            ELSE (((bd.gestationweekbylmp || ' Weeks '::text) || bd.gestationdaysbylmp) || ' Days'::text)
        END AS gestationbylmp,
        CASE
            WHEN ((bd.actualgestationdays IS NULL) OR (bd.actualgestationdays = 0)) THEN ((bd.actualgestationweek)::text || ' Weeks'::text)
            ELSE (((bd.actualgestationweek || ' Weeks '::text) || bd.actualgestationdays) || ' Days'::text)
        END AS actualgestation,
    bd.bloodgroup,
    bd.nicubedno,
    bd.nicuroomno,
    bd.niculevelno,
    bd.criticalitylevel,
    bd.significantmaterialid,
    bd.headtotoeid,
    bd.comments,
    bd.activestatus,
    bd.admissionstatus,
    bd.cry,
    bd.timeofcry,
    bd.dischargestatus,
    bd.dischargeddate,
    pd.mothername,
    pd.motherdob,
    pd.motherage,
    pd.fathername,
    pd.fatherdob,
    pd.fatherage,
    pd.primaryphonenumber,
    pd.secondaryphonenumber,
    pd.emailid,
    pd.address,
    pd.aadharcard,
    pd.villagename,
    cd.lmp,
    cd.edd,
    cd.g_score,
    cd.a_score,
    cd.p_score,
    cd.l_score,
    cd.deliverymode,
    cd.apgar_score_1min,
    cd.apgar_score_5min,
    cd.immunized,
    cd.vitamink_status,
    cd.antenatal_steroids,
    cd.anc,
    cd.flatus_tube,
    cd.resuscitation,
    bv.currentdateweight,
    bv.currentdateheight,
    bv.currentdateheadcircum
   FROM ((((baby_detail bd
     JOIN dischargepatient_detail ds ON (((bd.uhid)::text = (ds.uhid)::text)))
     LEFT JOIN parent_detail pd ON (((bd.uhid)::text = (pd.uhid)::text)))
     LEFT JOIN mother_current_pregnancy cd ON (((bd.uhid)::text = (cd.uhid)::text)))
     LEFT JOIN baby_visit bv ON ((((bd.uhid)::text = (bv.uhid)::text) AND ((bv.creationtime)::date = ('now'::text)::date))));


ALTER TABLE patientdischarge_page_raw OWNER TO postgres;


--
-- TOC entry 440 (class 1259 OID 71802)
-- Name: readmitpatient_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW readmitpatient_raw AS
 SELECT r.uhid,
    r.babyname,
    r.isavailable,
    r.condition,
    c.messagecount,
    r.admitdate,
    r.admittime,
    r.gender,
    r.dateofbirth,
    r.timeofbirth,
    r.birthweight,
    r.gestation,
    r.bedid,
    r.bedno,
    r.roomid,
    r.roomname,
    r.patientlevel,
    r.pulserate,
    r.spo2,
    r.heartrate,
    r.ecg_resprate,
    r.co2_resprate,
    r.etco2,
    r.sys_bp,
    r.dia_bp,
    r.mean_bp,
    r.comments,
    r.admissionstatus,
    r.nbp_s,
    r.nbp_d,
    r.nbp_m,
    r.creationtime
   FROM (dashboarddevicedetail_raw r
     JOIN dashboarddevicedetail_control c ON (((r.uhid)::text = (c.uhid)::text)))
  ORDER BY r.admitdate DESC;


ALTER TABLE readmitpatient_raw OWNER TO postgres;

--
-- TOC entry 441 (class 1259 OID 71807)
-- Name: readmitpatient_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW readmitpatient_final AS
 SELECT r.uhid,
    r.babyname,
    m.isavailable,
        CASE
            WHEN (r.condition IS NULL) THEN 'NA'::character varying
            ELSE r.condition
        END AS condition,
    r.messagecount,
    r.admitdate,
    r.admittime,
    r.gender,
    r.dateofbirth,
    r.timeofbirth,
    r.birthweight,
    r.gestation,
    m.bedno AS bedid,
    m.bedname AS bedno,
    m.roomid,
    m.roomname,
    r.patientlevel,
    r.pulserate,
    r.spo2,
    r.heartrate,
    r.ecg_resprate,
    r.co2_resprate,
    r.etco2,
    r.sys_bp,
    r.dia_bp,
    r.mean_bp,
    r.comments,
    r.admissionstatus,
    r.nbp_s,
    r.nbp_d,
    r.nbp_m,
    r.creationtime
   FROM (dashboardbeddetail_master m
     LEFT JOIN readmitpatient_raw r ON ((((m.bedname)::text = (r.bedno)::text) AND ((r.roomid)::text = (m.roomid)::text))))
  ORDER BY m.bedno;


ALTER TABLE readmitpatient_final OWNER TO postgres;


--
-- TOC entry 516 (class 1259 OID 72055)
-- Name: rolemanagement_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW rolemanagement_raw AS
 SELECT m.module_name,
    m.description AS module_desc,
    r.description AS rolename,
    rs.status_name AS status_desc
   FROM (((module m
     JOIN role_manager rm ON ((m.moduleid = rm.moduleid)))
     LEFT JOIN role r ON (((r.roleid)::text = (rm.roleid)::text)))
     LEFT JOIN role_status rs ON (((rm.statusid)::text = (rs.statusid)::text)))
  ORDER BY m.module_name;


ALTER TABLE rolemanagement_raw OWNER TO postgres;

--
-- TOC entry 517 (class 1259 OID 72060)
-- Name: rolemanagement_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW rolemanagement_final AS
 SELECT s.module_desc,
    max(
        CASE s.rolename
            WHEN 'Super User'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "Super User",
    max(
        CASE s.rolename
            WHEN 'Administrator'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "Administrator",
    max(
        CASE s.rolename
            WHEN 'Senior Doctor'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "SrDoc",
    max(
        CASE s.rolename
            WHEN 'Junior Doctor'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "JrDoc",
    max(
        CASE s.rolename
            WHEN 'Nurse'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "Nurse",
    max(
        CASE s.rolename
            WHEN 'Front Desk'::text THEN (s.status_desc)::text
            ELSE NULL::text
        END) AS "Front Desk"
   FROM ( SELECT r.rolename,
            r.module_desc,
            r.status_desc
           FROM rolemanagement_raw r
          GROUP BY r.rolename, r.module_desc, r.status_desc) s
  GROUP BY s.module_desc;


ALTER TABLE rolemanagement_final OWNER TO postgres;


--
-- TOC entry 570 (class 1259 OID 72267)
-- Name: vw_surfactantdose_calc_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_surfactantdose_calc_raw AS
 SELECT DISTINCT d.uhid,
    count(d.uhid) AS surfactantcount
   FROM sa_resp_rds d
  GROUP BY d.uhid
UNION
 SELECT DISTINCT d.uhid,
    count(d.uhid) AS surfactantcount
   FROM sa_resp_pphn d
  GROUP BY d.uhid
UNION
 SELECT DISTINCT d.uhid,
    count(d.uhid) AS surfactantcount
   FROM sa_resp_pneumothorax d
  GROUP BY d.uhid
UNION
 SELECT DISTINCT d.uhid,
    count(d.uhid) AS surfactantcount
   FROM sa_resp_others d
  GROUP BY d.uhid;


ALTER TABLE vw_surfactantdose_calc_raw OWNER TO postgres;

--
-- TOC entry 571 (class 1259 OID 72272)
-- Name: surfactant_dose_count_view; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW surfactant_dose_count_view AS
 SELECT vw_surfactantdose_calc_raw.uhid,
    sum(vw_surfactantdose_calc_raw.surfactantcount) AS count
   FROM vw_surfactantdose_calc_raw
  GROUP BY vw_surfactantdose_calc_raw.uhid;


ALTER TABLE surfactant_dose_count_view OWNER TO postgres;


--
-- TOC entry 574 (class 1259 OID 72282)
-- Name: tempview; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW tempview AS
 SELECT p.uhid,
    count(p.uhid) AS prescription,
    0 AS doctornotes,
    (p.creationtime)::date AS creationtime
   FROM baby_prescription p
  GROUP BY p.uhid, (p.creationtime)::date
UNION
 SELECT d.uhid,
    0 AS prescription,
    count(d.uhid) AS doctornotes,
    (d.creationtime)::date AS creationtime
   FROM doctor_notes d
  GROUP BY d.uhid, (d.creationtime)::date;


ALTER TABLE tempview OWNER TO postgres;



--
-- TOC entry 587 (class 1259 OID 72323)
-- Name: vw_assesment_cns_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_cns_raw AS
 SELECT sa_cns_ivh.uhid,
    sa_cns_ivh.creationtime,
    'CNS'::text AS category,
    sa_cns_ivh.cns_system_status,
    'IVH'::text AS event,
    (((sa_cns_ivh.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_cns_ivh.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_cns_ivh.progressnotes,
    sa_cns_ivh.eventstatus
   FROM sa_cns_ivh
UNION
 SELECT sa_cns_seizures.uhid,
    sa_cns_seizures.assessment_time AS creationtime,
    'CNS'::text AS category,
    sa_cns_seizures.cns_system_status,
    'Seizures'::text AS event,
    (((sa_cns_seizures.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_cns_seizures.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_cns_seizures.progressnotes,
    sa_cns_seizures.eventstatus
   FROM sa_cns_seizures
UNION
 SELECT sa_cns_asphyxia.uhid,
    sa_cns_asphyxia.assessment_time AS creationtime,
    'CNS'::text AS category,
    sa_cns_asphyxia.cns_system_status,
    'Asphyxia'::text AS event,
    (((sa_cns_asphyxia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_cns_asphyxia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_cns_asphyxia.progressnotes,
    sa_cns_asphyxia.eventstatus
   FROM sa_cns_asphyxia;


ALTER TABLE vw_assesment_cns_raw OWNER TO postgres;

--
-- TOC entry 588 (class 1259 OID 72328)
-- Name: vw_assesment_cns_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_cns_final AS
 SELECT v.uhid,
    v.creationtime,
    v.category,
    v.cns_system_status,
    v.event,
    v.duration,
    v.progressnotes,
    v.eventstatus
   FROM vw_assesment_cns_raw v
  ORDER BY v.creationtime DESC;


ALTER TABLE vw_assesment_cns_final OWNER TO postgres;

--
-- TOC entry 589 (class 1259 OID 72332)
-- Name: vw_assesment_infection_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_infection_raw AS
 SELECT sa_infection_sepsis.uhid,
    sa_infection_sepsis.assessment_time AS creationtime,
    'Infection'::text AS category,
    sa_infection_sepsis.infection_system_status,
    'Sepsis'::text AS event,
    (((sa_infection_sepsis.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_infection_sepsis.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_infection_sepsis.progressnotes,
    sa_infection_sepsis.eventstatus
   FROM sa_infection_sepsis;


ALTER TABLE vw_assesment_infection_raw OWNER TO postgres;

--
-- TOC entry 590 (class 1259 OID 72337)
-- Name: vw_assesment_metabolic_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_metabolic_raw AS
 SELECT sa_hypoglycemia.uhid,
    sa_hypoglycemia.creationtime,
    'Metabolic'::text AS category,
    sa_hypoglycemia.metabolic_system_status,
    'Hypoglycemia'::text AS event,
    (((sa_hypoglycemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hypoglycemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hypoglycemia.progressnotes
   FROM sa_hypoglycemia
UNION
 SELECT sa_hyperglycemia.uhid,
    sa_hyperglycemia.creationtime,
    'Metabolic'::text AS category,
    sa_hyperglycemia.metabolic_system_status,
    'Hyperglycemia'::text AS event,
    (((sa_hyperglycemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hyperglycemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hyperglycemia.progressnotes
   FROM sa_hyperglycemia
UNION
 SELECT sa_hypernatremia.uhid,
    sa_hypernatremia.creationtime,
    'Metabolic'::text AS category,
    sa_hypernatremia.metabolic_system_status,
    'Hypernatremia'::text AS event,
    (((sa_hypernatremia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hypernatremia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hypernatremia.progressnotes
   FROM sa_hypernatremia
UNION
 SELECT sa_hyponatremia.uhid,
    sa_hyponatremia.creationtime,
    'Metabolic'::text AS category,
    sa_hyponatremia.metabolic_system_status,
    'Hyponatremia'::text AS event,
    (((sa_hyponatremia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hyponatremia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hyponatremia.progressnotes
   FROM sa_hyponatremia
UNION
 SELECT sa_hypokalemia.uhid,
    sa_hypokalemia.creationtime,
    'Metabolic'::text AS category,
    sa_hypokalemia.metabolic_system_status,
    'Hypokalemia'::text AS event,
    (((sa_hypokalemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hypokalemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hypokalemia.progressnotes
   FROM sa_hypokalemia
UNION
 SELECT sa_hyperkalemia.uhid,
    sa_hyperkalemia.creationtime,
    'Metabolic'::text AS category,
    sa_hyperkalemia.metabolic_system_status,
    'Hyperkalemia'::text AS event,
    (((sa_hyperkalemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hyperkalemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hyperkalemia.progressnotes
   FROM sa_hyperkalemia
UNION
 SELECT sa_hypocalcemia.uhid,
    sa_hypocalcemia.creationtime,
    'Metabolic'::text AS category,
    sa_hypocalcemia.metabolic_system_status,
    'Hypocalcemia'::text AS event,
    (((sa_hypocalcemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hypocalcemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hypocalcemia.progressnotes
   FROM sa_hypocalcemia
UNION
 SELECT sa_hypercalcemia.uhid,
    sa_hypercalcemia.creationtime,
    'Metabolic'::text AS category,
    sa_hypercalcemia.metabolic_system_status,
    'Hypercalcemia'::text AS event,
    (((sa_hypercalcemia.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_hypercalcemia.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_hypercalcemia.progressnotes
   FROM sa_hypercalcemia
UNION
 SELECT sa_acidosis.uhid,
    sa_acidosis.creationtime,
    'Metabolic'::text AS category,
    sa_acidosis.metabolic_system_status,
    'Acidosis'::text AS event,
    (((sa_acidosis.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_acidosis.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_acidosis.progressnotes
   FROM sa_acidosis
UNION
 SELECT sa_iem.uhid,
    sa_iem.creationtime,
    'Metabolic'::text AS category,
    sa_iem.metabolic_system_status,
    'IEM'::text AS event,
    (((sa_iem.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_iem.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_iem.progressnotes
   FROM sa_iem;


ALTER TABLE vw_assesment_metabolic_raw OWNER TO postgres;

--
-- TOC entry 591 (class 1259 OID 72342)
-- Name: vw_assesment_metabolic_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_metabolic_final AS
 SELECT vw_assesment_metabolic_raw.uhid,
    vw_assesment_metabolic_raw.creationtime,
    vw_assesment_metabolic_raw.category,
    vw_assesment_metabolic_raw.metabolic_system_status,
    vw_assesment_metabolic_raw.event,
    vw_assesment_metabolic_raw.duration,
    vw_assesment_metabolic_raw.progressnotes
   FROM vw_assesment_metabolic_raw
  ORDER BY vw_assesment_metabolic_raw.creationtime DESC;


ALTER TABLE vw_assesment_metabolic_final OWNER TO postgres;

--
-- TOC entry 592 (class 1259 OID 72346)
-- Name: vw_assesment_respsystem_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_respsystem_raw AS
 SELECT sa_resp_rds.uhid,
    sa_resp_rds.assessment_time AS creationtime,
    'Respiratory System'::text AS category,
    sa_resp_rds.resp_system_status,
        CASE
            WHEN ((sa_resp_rds.eventstatus)::text = 'Yes'::text) THEN 'Active'::text
            WHEN ((sa_resp_rds.eventstatus)::text = 'No'::text) THEN 'Passive'::text
            WHEN ((sa_resp_rds.eventstatus)::text = 'Inactive'::text) THEN 'Inactive'::text
            ELSE NULL::text
        END AS eventstatus,
    'RDS'::text AS event,
    (((sa_resp_rds.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_resp_rds.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_resp_rds.causeofrds AS cause,
    sa_resp_rds.progressnotes
   FROM sa_resp_rds
UNION
 SELECT sa_resp_apnea.uhid,
    sa_resp_apnea.assessment_time AS creationtime,
    'Respiratory System'::text AS category,
    sa_resp_apnea.resp_system_status,
        CASE
            WHEN ((sa_resp_apnea.eventstatus)::text = 'Yes'::text) THEN 'Active'::text
            WHEN ((sa_resp_apnea.eventstatus)::text = 'No'::text) THEN 'Passive'::text
            WHEN ((sa_resp_apnea.eventstatus)::text = 'Inactive'::text) THEN 'Inactive'::text
            ELSE NULL::text
        END AS eventstatus,
    'Apnea'::text AS event,
    (((sa_resp_apnea.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_resp_apnea.ageinhours IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_resp_apnea.apnea_cause AS cause,
    sa_resp_apnea.apnea_comment AS progressnotes
   FROM sa_resp_apnea
UNION
 SELECT sa_resp_pphn.uhid,
    sa_resp_pphn.assessment_time AS creationtime,
    'Respiratory System'::text AS category,
    sa_resp_pphn.resp_system_status,
        CASE
            WHEN ((sa_resp_pphn.eventstatus)::text = 'Yes'::text) THEN 'Active'::text
            WHEN ((sa_resp_pphn.eventstatus)::text = 'No'::text) THEN 'Passive'::text
            WHEN ((sa_resp_pphn.eventstatus)::text = 'Inactive'::text) THEN 'Inactive'::text
            ELSE NULL::text
        END AS eventstatus,
    'PPHN'::text AS event,
    (((sa_resp_pphn.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_resp_pphn.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_resp_pphn.causeofpphn AS cause,
    sa_resp_pphn.progressnotes
   FROM sa_resp_pphn
UNION
 SELECT sa_resp_pneumothorax.uhid,
    sa_resp_pneumothorax.assessment_time AS creationtime,
    'Respiratory System'::text AS category,
    sa_resp_pneumothorax.resp_system_status,
        CASE
            WHEN ((sa_resp_pneumothorax.eventstatus)::text = 'Yes'::text) THEN 'Active'::text
            WHEN ((sa_resp_pneumothorax.eventstatus)::text = 'No'::text) THEN 'Passive'::text
            WHEN ((sa_resp_pneumothorax.eventstatus)::text = 'Inactive'::text) THEN 'Inactive'::text
            ELSE NULL::text
        END AS eventstatus,
    'Pneumothorax'::text AS event,
    (((sa_resp_pneumothorax.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_resp_pneumothorax.ageinhoursdays IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_resp_pneumothorax.causeofpneumothorax AS cause,
    sa_resp_pneumothorax.progressnotes
   FROM sa_resp_pneumothorax
UNION
 SELECT sa_resp_others.uhid,
    sa_resp_others.creationtime,
    'Respiratory System'::text AS category,
    sa_resp_others.resp_system_status,
        CASE
            WHEN ((sa_resp_others.eventstatus)::text = 'Yes'::text) THEN 'Active'::text
            WHEN ((sa_resp_others.eventstatus)::text = 'No'::text) THEN 'Passive'::text
            WHEN ((sa_resp_others.eventstatus)::text = 'Inactive'::text) THEN 'Inactive'::text
            ELSE NULL::text
        END AS eventstatus,
    'Others'::text AS event,
    (((sa_resp_others.ageatonset)::text || ' '::text) ||
        CASE
            WHEN (sa_resp_others.ageinhours IS TRUE) THEN 'hrs'::text
            ELSE 'days'::text
        END) AS duration,
    sa_resp_others.causeofdisease AS cause,
    sa_resp_others.progressnotes
   FROM sa_resp_others;


ALTER TABLE vw_assesment_respsystem_raw OWNER TO postgres;

--
-- TOC entry 593 (class 1259 OID 72351)
-- Name: vw_assesment_respsystem_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesment_respsystem_final AS
 SELECT vw_assesment_respsystem_raw.uhid,
    vw_assesment_respsystem_raw.creationtime,
    vw_assesment_respsystem_raw.category,
    vw_assesment_respsystem_raw.resp_system_status,
    vw_assesment_respsystem_raw.event,
    vw_assesment_respsystem_raw.eventstatus,
    vw_assesment_respsystem_raw.duration,
    vw_assesment_respsystem_raw.cause,
    vw_assesment_respsystem_raw.progressnotes,
    vw_assesment_respsystem_raw.progressnotes AS comment
   FROM vw_assesment_respsystem_raw
  ORDER BY vw_assesment_respsystem_raw.creationtime DESC;


ALTER TABLE vw_assesment_respsystem_final OWNER TO postgres;

--
-- TOC entry 594 (class 1259 OID 72355)
-- Name: vw_assesments_usage_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesments_usage_raw AS
 SELECT f.uhid,
    'sa_cardiac'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_cardiac f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_cns'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_cns f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_feedgrowth'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_feedgrowth f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_jaundice'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_jaundice f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_metabolic'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_metabolic f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_misc'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_misc f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_renalfailure'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_renalfailure f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_respsystem'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_respsystem f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'sa_sepsis'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM sa_sepsis f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser;


ALTER TABLE vw_assesments_usage_raw OWNER TO postgres;

--
-- TOC entry 595 (class 1259 OID 72360)
-- Name: vw_assesments_usage_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assesments_usage_rule AS
 SELECT r.uhid,
    r.creationtime,
    count(r.entrycount) AS entrycount,
    r.nicuuser
   FROM vw_assesments_usage_raw r
  GROUP BY r.uhid, r.creationtime, r.nicuuser
  ORDER BY r.uhid, r.creationtime, r.nicuuser;


ALTER TABLE vw_assesments_usage_rule OWNER TO postgres;

--
-- TOC entry 596 (class 1259 OID 72364)
-- Name: vw_assessmentcause_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assessmentcause_raw AS
 SELECT 'RDS'::text AS event,
    ref_rdscause.rdscauseid AS eventid,
    ref_rdscause.rdscause AS causes,
    ref_rdscause.seq AS sequence
   FROM ref_rdscause
UNION
 SELECT 'Apnea'::text AS event,
    ref_apneacause.apneacauseid AS eventid,
    ref_apneacause.apneacause AS causes,
    ref_apneacause.seq AS sequence
   FROM ref_apneacause
UNION
 SELECT 'Jaundice'::text AS event,
    ref_causeofjaundice.causeofjaundiceid AS eventid,
    ref_causeofjaundice.causeofjaundice AS causes,
    ref_causeofjaundice.seq AS sequence
   FROM ref_causeofjaundice
  WHERE ((ref_causeofjaundice.causeofjaundiceid)::text <> 'JAU0006'::text)
UNION
 SELECT ref_causeofmetabolic.event,
    ref_causeofmetabolic.causeofmetabolicid AS eventid,
    ref_causeofmetabolic.causeofmetabolic AS causes,
    ref_causeofmetabolic.seq AS sequence
   FROM ref_causeofmetabolic
UNION
 SELECT ref_causeofinfection.event,
    ref_causeofinfection.causeofinfectionid AS eventid,
    ref_causeofinfection.causeofinfection AS causes,
    ref_causeofinfection.seq AS sequence
   FROM ref_causeofinfection
UNION
 SELECT ref_causeofcns.event,
    ref_causeofcns.causeofcnsid AS eventid,
    ref_causeofcns.causeofcns AS causes,
    ref_causeofcns.seq AS sequence
   FROM ref_causeofcns
UNION
 SELECT ref_causeofrespiratory.event,
    ref_causeofrespiratory.causeofrespid AS eventid,
    ref_causeofrespiratory.causeofresp AS causes,
    ref_causeofrespiratory.seq AS sequence
   FROM ref_causeofrespiratory;


ALTER TABLE vw_assessmentcause_raw OWNER TO postgres;

--
-- TOC entry 597 (class 1259 OID 72369)
-- Name: vw_assessmentcause_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_assessmentcause_final AS
 SELECT vw_assessmentcause_raw.event,
    vw_assessmentcause_raw.eventid,
    vw_assessmentcause_raw.causes,
    vw_assessmentcause_raw.sequence
   FROM vw_assessmentcause_raw
  ORDER BY vw_assessmentcause_raw.event, vw_assessmentcause_raw.sequence;


ALTER TABLE vw_assessmentcause_final OWNER TO postgres;

--
-- TOC entry 598 (class 1259 OID 72373)
-- Name: vw_baby_prescription; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_baby_prescription AS
 SELECT bp.baby_presid,
    bp.uhid,
    bp.medicinename,
    bp.route,
    bp.dose,
    bp.frequency,
    bp.startdate,
    bp.starttime,
    bp.enddate,
    bp.medicationtype,
    bp.comments,
    bp.loggeduser,
    bp.isactive,
    bp.calculateddose
   FROM baby_prescription bp
  ORDER BY bp.creationtime;


ALTER TABLE vw_baby_prescription OWNER TO postgres;

--
-- TOC entry 599 (class 1259 OID 72377)
-- Name: vw_baby_stay_duration; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_baby_stay_duration AS
 SELECT baby_detail.uhid,
    baby_detail.episodeid,
        CASE
            WHEN (baby_detail.dischargeddate IS NULL) THEN ceil(((date_part('epoch'::text, now()) - date_part('epoch'::text, baby_detail.dateofadmission)) / (86400)::double precision))
            ELSE ceil(((date_part('epoch'::text, baby_detail.dischargeddate) - date_part('epoch'::text, baby_detail.dateofadmission)) / (86400)::double precision))
        END AS stay_duration
   FROM baby_detail;


ALTER TABLE vw_baby_stay_duration OWNER TO postgres;

--
-- TOC entry 600 (class 1259 OID 72382)
-- Name: vw_babyfeeddetail; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_babyfeeddetail AS
 SELECT bd.babyfeedid,
    (bd.creationtime)::date AS creationdate,
    bd.creationtime,
    bd.modificationtime,
    bd.uhid,
    bd.ebm,
    bd.ebmperdayml,
    bd.lbfm,
    bd.lbfmperdayml,
    bd.tfm,
    bd.tfmperdayml,
    bd.hmf,
    bd.hmfperdayml,
    bd.hmf_ebm,
    bd.hmf_ebm_perdayml,
    bd.productmilk,
    bd.productmilkperdayml,
    bd.feedmethod,
    bd.feedvolume,
    bd.feedduration,
    bd.totalfeed_ml_day,
    bd.ivfluidml_perhr,
    bd.ivfluidtype,
    bd.total_ivfluids,
    bd.totalfluid_ml_day
   FROM babyfeed_detail bd
  ORDER BY bd.creationtime DESC;


ALTER TABLE vw_babyfeeddetail OWNER TO postgres;

--
-- TOC entry 601 (class 1259 OID 72387)
-- Name: vw_blood_product; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_blood_product AS
 SELECT bp.bloodproductsid,
    bp.creationtime,
    bp.modificationtime,
    bp.uhid,
    bp.bloodproduct,
    bp.dose,
    bp.duration,
    bp.bloodgroup
   FROM blood_products bp
  ORDER BY bp.creationtime;


ALTER TABLE vw_blood_product OWNER TO postgres;

--
-- TOC entry 602 (class 1259 OID 72391)
-- Name: vw_discharged_patients_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_discharged_patients_raw AS
 SELECT b1.babydetailid,
    b1.creationtime,
    b1.modificationtime,
    b1.uhid,
    b1.babyname,
    b1.gender,
    b1.dateofbirth,
    b1.timeofbirth,
    b1.dateofadmission,
    b1.timeofadmission,
    b1.birthweight,
    b1.birthlength,
    b1.birthheadcircumference,
    b1.obstetrician,
    b1.admittingdoctor,
    b1.iptagno,
    b1.typeofadmission,
    b1.inout_patient_status,
    b1.gestationweekbylmp,
    b1.gestationdaysbylmp,
    b1.actualgestationweek,
    b1.actualgestationdays,
    b1.bloodgroup,
    b1.nicubedno,
    b1.nicuroomno,
    b1.niculevelno,
    b1.criticalitylevel,
    b1.significantmaterialid,
    b1.historyid,
    b1.headtotoeid,
    b1.examinationid,
    b1.comments,
    b1.activestatus,
    b1.babyshiftedto,
    b1.admissionstatus,
    b1.timeofcry,
    b1.cry,
    b1.loggeduser,
    b1.dischargestatus,
    b1.dischargeddate,
    b1.dayoflife,
    b1.admission_spo2,
    b1.admission_rr,
    b1.admission_pulserate,
    b1.admission_bp,
    b1.admission_temp,
    b1.birthmarks,
    b1.birthmarks_comments,
    b1.birthinjuries,
    b1.birthinjuries_comments,
    b1.refferedfrom,
    b1.courseinrefferinghospital,
    b1.modeoftransportation,
    b1.transportationalongwith,
    b1.reasonofreference,
    b1.refferedby,
    b1.reffered_number,
    b1.sourceoftransportation,
    b1.weight_centile,
    b1.length_centile,
    b1.hc_centile,
    b1.bp_type,
    b1.bp_lll,
    b1.bp_rll,
    b1.bp_lul,
    b1.bp_rul,
    b1.spo2,
    b1.episodeid,
    b1.consciousness,
    b1.weight_galevel,
    b1.length_galevel,
    b1.hc_galevel,
    b1.ponderal_index,
    b1.is_fever,
    b1.hr,
    b1.rr,
    b1.bp_systolic,
    b1.bp_diastolic,
    b1.bp_mean,
    b1.crt,
    b1.downesscoreid,
    b1.central_temp,
    b1.peripheral_temp,
    b1.isassessmentsubmit,
    b1.baby_type,
    b1.baby_number,
    b1.admissionweight
   FROM (baby_detail b1
     JOIN baby_detail b2 ON ((((b1.babydetailid <> b2.babydetailid) AND ((b1.uhid)::text = (b2.uhid)::text)) AND (b1.admissionstatus = true))));


ALTER TABLE vw_discharged_patients_raw OWNER TO postgres;

--
-- TOC entry 603 (class 1259 OID 72396)
-- Name: vw_discharged_patients_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_discharged_patients_final AS
 SELECT b.babydetailid,
    b.creationtime,
    b.modificationtime,
    b.uhid,
    b.babyname,
    b.gender,
    b.dateofbirth,
    b.timeofbirth,
    b.dateofadmission,
    b.timeofadmission,
    b.birthweight,
    b.birthlength,
    b.birthheadcircumference,
    b.obstetrician,
    b.admittingdoctor,
    b.iptagno,
    b.typeofadmission,
    b.inout_patient_status,
    b.gestationweekbylmp,
    b.gestationdaysbylmp,
    b.actualgestationweek,
    b.actualgestationdays,
    b.bloodgroup,
    b.nicubedno,
    b.nicuroomno,
    b.niculevelno,
    b.criticalitylevel,
    b.significantmaterialid,
    b.historyid,
    b.headtotoeid,
    b.examinationid,
    b.comments,
    b.activestatus,
    b.babyshiftedto,
    b.admissionstatus,
    b.timeofcry,
    b.cry,
    b.loggeduser,
    b.dischargestatus,
    b.dischargeddate,
    b.dayoflife,
    b.admission_spo2,
    b.admission_rr,
    b.admission_pulserate,
    b.admission_bp,
    b.admission_temp,
    b.birthmarks,
    b.birthmarks_comments,
    b.birthinjuries,
    b.birthinjuries_comments,
    b.refferedfrom,
    b.courseinrefferinghospital,
    b.modeoftransportation,
    b.transportationalongwith,
    b.reasonofreference,
    b.refferedby,
    b.reffered_number,
    b.sourceoftransportation,
    b.weight_centile,
    b.length_centile,
    b.hc_centile,
    b.bp_type,
    b.bp_lll,
    b.bp_rll,
    b.bp_lul,
    b.bp_rul,
    b.spo2,
    b.episodeid,
    b.consciousness,
    b.weight_galevel,
    b.length_galevel,
    b.hc_galevel,
    b.ponderal_index,
    b.is_fever,
    b.hr,
    b.rr,
    b.bp_systolic,
    b.bp_diastolic,
    b.bp_mean,
    b.crt,
    b.downesscoreid,
    b.central_temp,
    b.peripheral_temp,
    b.isassessmentsubmit,
    b.baby_type,
    b.baby_number,
    b.admissionweight,
        CASE
            WHEN ((b.uhid)::text IN ( SELECT vw_discharged_patients_raw.uhid
               FROM vw_discharged_patients_raw)) THEN true
            ELSE false
        END AS isbabyininicu
   FROM baby_detail b
  WHERE (b.admissionstatus = false);


ALTER TABLE vw_discharged_patients_final OWNER TO postgres;

--
-- TOC entry 604 (class 1259 OID 72401)
-- Name: vw_doctornotes; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes AS
 SELECT dn.doctornoteid,
    (dn.creationtime)::date AS creationdate,
    dn.creationtime,
    dn.modificationtime,
    dn.uhid,
    dn.doctornotes,
    dn.followupnotes,
    dn.diagnosis,
    dn.issues,
    dn.plan
   FROM doctor_notes dn
  ORDER BY dn.creationtime;


ALTER TABLE vw_doctornotes OWNER TO postgres;

--
-- TOC entry 605 (class 1259 OID 72405)
-- Name: vw_pupil_reactivity; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_pupil_reactivity AS
 SELECT n.nn_neorovitalsid,
    p.uhid,
    p.creationtime,
    p.left_reactivity AS leftreactivity,
    p.left_pupilsize AS leftpupilsize,
    p.right_reactivity AS rightreactivity,
    p.right_pupilsize AS rightpupilsize,
    p.equality,
    p.pupil_time,
    n.nn_neurovitals_time,
    n.sedation_score,
    n.gcs,
    n.icp,
    n.ccp,
    p.comments
   FROM (pupil_reactivity p
     JOIN nursing_neurovitals n ON (((((p.uhid)::text = (n.uhid)::text) AND ((p.pupil_time)::text = (n.nn_neurovitals_time)::text)) AND ((p.creationtime)::date = (n.creationtime)::date))));


ALTER TABLE vw_pupil_reactivity OWNER TO postgres;

--
-- TOC entry 606 (class 1259 OID 72410)
-- Name: vw_doctornotes_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_final AS
 SELECT n.doctornoteid,
    n.creationtime,
    n.noteentrytime,
    (n.creationtime)::date AS creationdate,
    n.modificationtime,
    bd.uhid,
    bd.babyname,
    bd.gestationdaysbylmp,
    bd.gestationweekbylmp,
    bd.admittingdoctor,
    bd.gender,
    bd.dateofadmission,
    f.ebm,
    f.ebmperdayml,
    f.lbfm,
    f.lbfmperdayml,
    f.tfm,
    f.tfmperdayml,
    f.hmf,
    f.hmfperdayml,
    f.hmf_ebm,
    f.hmf_ebm_perdayml,
    f.productmilk,
    f.productmilkperdayml,
    f.formulamilk,
    f.formulamilkperdayml,
    f.feedmethod,
    f.feedvolume,
    f.feedduration,
    f.totalfeed_ml_day,
    f.ivfluidml_perhr,
    f.ivfluidtype,
    f.total_ivfluids,
    f.totalfluid_ml_day,
    n.doctornotes,
    n.followupnotes,
        CASE
            WHEN ((n.doctornotes IS NULL) AND (n.followupnotes IS NOT NULL)) THEN 'followupnotes'::text
            WHEN ((n.doctornotes IS NOT NULL) AND (n.followupnotes IS NULL)) THEN 'doctornotes'::text
            ELSE NULL::text
        END AS notesflag,
    n.diagnosis,
    n.issues,
    n.plan,
    pr.leftreactivity,
    pr.leftpupilsize,
    pr.rightreactivity,
    pr.rightpupilsize
   FROM (((baby_detail bd
     JOIN doctor_notes n ON (((bd.uhid)::text = (n.uhid)::text)))
     LEFT JOIN babyfeed_detail f ON ((((n.uhid)::text = (f.uhid)::text) AND (to_char(n.creationtime, 'YYYY-MM-DD HH24:MI'::text) = to_char(f.creationtime, 'YYYY-MM-DD HH24:MI'::text)))))
     LEFT JOIN vw_pupil_reactivity pr ON ((((n.uhid)::text = (pr.uhid)::text) AND (to_char(n.creationtime, 'YYYY-MM-DD HH24:MI'::text) = to_char(pr.creationtime, 'YYYY-MM-DD HH24:MI'::text)))))
  ORDER BY to_char(n.creationtime, 'YYYY-MM-DD HH12:MI'::text);


ALTER TABLE vw_doctornotes_final OWNER TO postgres;

--
-- TOC entry 607 (class 1259 OID 72415)
-- Name: vw_doctornotes_height_contol; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_height_contol AS
 SELECT baby_visit.uhid,
    baby_visit.visitdate,
    baby_visit.currentdateheight,
    (baby_visit.currentdateheight - lag(baby_visit.currentdateheight) OVER (PARTITION BY baby_visit.uhid ORDER BY baby_visit.visitdate)) AS diffheight
   FROM baby_visit;


ALTER TABLE vw_doctornotes_height_contol OWNER TO postgres;

--
-- TOC entry 608 (class 1259 OID 72419)
-- Name: vw_doctornotes_list_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_list_raw AS
 SELECT j.sajaundiceid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Jaundice'::text AS sa_event,
    j.actiontype AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || j.actionduration) || ' '::text) || (j.isactiondurationinhours)::text) AS plan,
    i.testname,
    j.comment AS progressnotes
   FROM (sa_jaundice j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.sajaundiceid)::text) AND (lower((i.assesment_type)::text) = 'jaundice'::text))))
UNION
 SELECT j.resprdsid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'RDS'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reasseshoursdays)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_resp_rds j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.resprdsid)::text) AND (lower((i.assesment_type)::text) = 'respiratory'::text))))
UNION
 SELECT j.resppphnid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'PPHN'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_resp_pphn j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.resppphnid)::text) AND (lower((i.assesment_type)::text) = 'pphn'::text))))
UNION
 SELECT j.resppneumothoraxid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Pneumothorax'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_resp_pneumothorax j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.resppneumothoraxid)::text) AND (lower((i.assesment_type)::text) = 'pneumothorax'::text))))
UNION
 SELECT j.apneaid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Apnea'::text AS sa_event,
    j.actiontype AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.action_plan_time)::text) || ' '::text) || (j.action_plan_timetype)::text) AS plan,
    i.testname,
    j.apnea_comment AS progressnotes
   FROM (sa_resp_apnea j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.apneaid)::text) AND (lower((i.assesment_type)::text) = 'apnea'::text))))
UNION
 SELECT j.sacnsasphyxiaid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Asphyxia'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_cns_asphyxia j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.sacnsasphyxiaid)::text) AND (lower((i.assesment_type)::text) = 'asphyxia'::text))))
UNION
 SELECT j.sacnsseizuresid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Seizures'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_cns_seizures j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.sacnsseizuresid)::text) AND (lower((i.assesment_type)::text) = 'seizures'::text))))
UNION
 SELECT j.sacnsivhid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'IVH'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_cns_ivh j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.sacnsivhid)::text) AND (lower((i.assesment_type)::text) = 'ivh'::text))))
UNION
 SELECT j.sasepsisid AS assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    'Sepsis'::text AS sa_event,
    j.treatmentaction AS treatment,
    (((('Plan is to reasses after'::text || ' '::text) || (j.reassestime)::text) || ' '::text) || (j.reassestime_type)::text) AS plan,
    i.testname,
    j.progressnotes
   FROM (sa_infection_sepsis j
     LEFT JOIN investigation_ordered i ON ((((i.assesmentid)::text = (j.sasepsisid)::text) AND (lower((i.assesment_type)::text) = 'sepsis'::text))))
UNION
 SELECT sn.id AS assesmentid,
    sn.uhid,
    sn.entrytime AS creationtime,
    sn.modificationtime,
    'Stable Notes'::text AS sa_event,
    'NA'::text AS treatment,
    'NA'::text AS plan,
    'NA'::text AS testname,
    sn.notes AS progressnotes
   FROM stable_notes sn;


ALTER TABLE vw_doctornotes_list_raw OWNER TO postgres;

--
-- TOC entry 609 (class 1259 OID 72424)
-- Name: vw_doctornotes_list_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_list_control AS
 SELECT j.assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    j.sa_event,
    j.treatment,
    j.plan,
    j.testname,
    j.progressnotes
   FROM vw_doctornotes_list_raw j
  ORDER BY j.uhid, j.creationtime DESC;


ALTER TABLE vw_doctornotes_list_control OWNER TO postgres;

--
-- TOC entry 610 (class 1259 OID 72428)
-- Name: vw_doctornotes_list_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_list_rule AS
 SELECT j.assesmentid,
    string_agg((j.testname)::text, ', '::text) AS testname
   FROM vw_doctornotes_list_control j
  GROUP BY j.assesmentid;


ALTER TABLE vw_doctornotes_list_rule OWNER TO postgres;

--
-- TOC entry 611 (class 1259 OID 72432)
-- Name: vw_doctornotes_list_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_list_final AS
 SELECT DISTINCT j.assesmentid,
    j.uhid,
    j.creationtime,
    j.modificationtime,
    j.sa_event,
    j.treatment,
    j.plan,
    r.testname,
    j.progressnotes
   FROM (vw_doctornotes_list_control j
     LEFT JOIN vw_doctornotes_list_rule r ON ((j.assesmentid = r.assesmentid)))
  ORDER BY j.uhid, j.creationtime DESC;


ALTER TABLE vw_doctornotes_list_final OWNER TO postgres;

--
-- TOC entry 612 (class 1259 OID 72436)
-- Name: vw_doctornotes_weight_contol; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_weight_contol AS
 SELECT baby_visit.uhid,
    baby_visit.visitdate,
    baby_visit.currentdateweight,
    (baby_visit.currentdateweight - lag(baby_visit.currentdateweight) OVER (PARTITION BY baby_visit.uhid ORDER BY baby_visit.visitdate)) AS diffweight
   FROM baby_visit
  ORDER BY baby_visit.uhid DESC;


ALTER TABLE vw_doctornotes_weight_contol OWNER TO postgres;

--
-- TOC entry 613 (class 1259 OID 72440)
-- Name: vw_doctornotes_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_raw AS
 SELECT n.doctornoteid,
    n.creationtime,
    (n.creationtime)::date AS creationdate,
    n.modificationtime,
    bd.uhid,
    v.visitdate,
    ((v.visitdate - bd.dateofbirth))::text AS currentage,
    bd.gender,
    bd.dateofadmission,
    v.currentdateweight,
    vw.diffweight,
    v.currentdateheight,
    vh.diffheight,
    f.ebm,
    f.ebmperdayml,
    f.lbfm,
    f.lbfmperdayml,
    f.tfm,
    f.tfmperdayml,
    f.hmf,
    f.hmfperdayml,
    f.hmf_ebm,
    f.hmf_ebm_perdayml,
    f.productmilk,
    f.productmilkperdayml,
    f.feedmethod,
    f.feedvolume,
    f.feedduration,
    f.totalfeed_ml_day,
    f.ivfluidml_perhr,
    f.ivfluidtype,
    f.total_ivfluids,
    f.totalfluid_ml_day,
    n.doctornotes,
    n.followupnotes,
        CASE
            WHEN ((n.doctornotes IS NULL) AND (n.followupnotes IS NOT NULL)) THEN 'followupnotes'::text
            WHEN ((n.doctornotes IS NOT NULL) AND (n.followupnotes IS NULL)) THEN 'doctornotes'::text
            ELSE NULL::text
        END AS notesflag,
    n.diagnosis,
    n.issues,
    n.plan,
    pr.leftreactivity,
    pr.leftpupilsize,
    pr.rightreactivity,
    pr.rightpupilsize
   FROM ((((((baby_detail bd
     LEFT JOIN baby_visit v ON (((bd.uhid)::text = (v.uhid)::text)))
     LEFT JOIN doctor_notes n ON ((((bd.uhid)::text = (n.uhid)::text) AND (v.visitdate = (n.creationtime)::date))))
     LEFT JOIN babyfeed_detail f ON ((((v.uhid)::text = (f.uhid)::text) AND (v.visitdate = (f.creationtime)::date))))
     LEFT JOIN vw_doctornotes_weight_contol vw ON ((((bd.uhid)::text = (vw.uhid)::text) AND (v.visitdate = vw.visitdate))))
     LEFT JOIN vw_doctornotes_height_contol vh ON ((((bd.uhid)::text = (vh.uhid)::text) AND (v.visitdate = vh.visitdate))))
     LEFT JOIN vw_pupil_reactivity pr ON ((((v.uhid)::text = (pr.uhid)::text) AND (v.visitdate = pr.creationtime))))
  ORDER BY v.visitdate;


ALTER TABLE vw_doctornotes_raw OWNER TO postgres;

--
-- TOC entry 614 (class 1259 OID 72445)
-- Name: vw_doctornotes_usage_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_usage_raw AS
 SELECT p.uhid,
    'prescription'::text AS type,
    count(p.uhid) AS entrycount,
    (p.creationtime)::date AS creationtime,
    p.loggeduser AS nicuuser
   FROM baby_prescription p
  GROUP BY p.uhid, (p.creationtime)::date, p.loggeduser
UNION
 SELECT d.uhid,
    'doctornotes'::text AS type,
    count(d.uhid) AS entrycount,
    (d.creationtime)::date AS creationtime,
    d.loggeduser AS nicuuser
   FROM doctor_notes d
  GROUP BY d.uhid, (d.creationtime)::date, d.loggeduser
UNION
 SELECT f.uhid,
    'babyfeed'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser AS nicuuser
   FROM babyfeed_detail f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'assesments'::text AS type,
    f.entrycount,
    f.creationtime,
    f.nicuuser
   FROM vw_assesments_usage_rule f;


ALTER TABLE vw_doctornotes_usage_raw OWNER TO postgres;

--
-- TOC entry 615 (class 1259 OID 72450)
-- Name: vw_doctornotes_usage_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_usage_rule AS
 SELECT s.uhid,
    s.creationtime,
    max(
        CASE s.type
            WHEN 'doctornotes'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS doctornotes,
    max(
        CASE s.type
            WHEN 'babyfeed'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS babyfeed,
    max(
        CASE s.type
            WHEN 'prescription'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS prescription,
    max(
        CASE s.type
            WHEN 'assesments'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS assesments,
    s.nicuuser
   FROM ( SELECT r.uhid,
            r.type,
            r.entrycount,
            r.creationtime,
            r.nicuuser
           FROM vw_doctornotes_usage_raw r
          GROUP BY r.uhid, r.type, r.entrycount, r.creationtime, r.nicuuser) s
  GROUP BY s.uhid, s.creationtime, s.nicuuser
  ORDER BY s.uhid, s.creationtime;


ALTER TABLE vw_doctornotes_usage_rule OWNER TO postgres;

--
-- TOC entry 616 (class 1259 OID 72455)
-- Name: vw_doctornotes_usage_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_usage_control AS
 SELECT b.uhid,
    b.babyname,
    b.niculevelno,
    b.criticalitylevel,
    s.creationtime,
    s.doctornotes,
    s.babyfeed,
    s.prescription,
    s.assesments,
    s.nicuuser
   FROM (baby_detail b
     LEFT JOIN vw_doctornotes_usage_rule s ON (((b.uhid)::text = (s.uhid)::text)))
  ORDER BY b.uhid, s.creationtime;


ALTER TABLE vw_doctornotes_usage_control OWNER TO postgres;

--
-- TOC entry 617 (class 1259 OID 72460)
-- Name: vw_doctornotes_usage_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_doctornotes_usage_final AS
 SELECT r.uhid,
    r.babyname,
    rl.levelname AS niculevel,
    rc.levelname AS criticality,
    r.creationtime,
        CASE
            WHEN (r.doctornotes IS NULL) THEN '0'::text
            ELSE r.doctornotes
        END AS doctornotes,
        CASE
            WHEN (r.babyfeed IS NULL) THEN '0'::text
            ELSE r.babyfeed
        END AS babyfeed,
        CASE
            WHEN (r.prescription IS NULL) THEN '0'::text
            ELSE r.prescription
        END AS prescription,
        CASE
            WHEN (r.assesments IS NULL) THEN '0'::text
            ELSE r.assesments
        END AS assesments,
    r.nicuuser
   FROM ((vw_doctornotes_usage_control r
     LEFT JOIN ref_level rl ON (((r.niculevelno)::text = (rl.levelid)::text)))
     LEFT JOIN ref_criticallevel rc ON (((r.criticalitylevel)::text = (rc.crlevelid)::text)))
  ORDER BY r.uhid, r.creationtime;


ALTER TABLE vw_doctornotes_usage_final OWNER TO postgres;

--
-- TOC entry 618 (class 1259 OID 72465)
-- Name: vw_growthchat; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_growthchat AS
 SELECT baby_visit.visitid,
    baby_visit.uhid,
    baby_visit.currentdateweight,
    baby_visit.currentdateheight,
    baby_visit.currentdateheadcircum,
    baby_visit.visitdate
   FROM baby_visit
  ORDER BY baby_visit.visitdate;


ALTER TABLE vw_growthchat OWNER TO postgres;

--
-- TOC entry 619 (class 1259 OID 72469)
-- Name: vw_notification_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_notification_raw AS
 SELECT sa_jaundice.uhid,
    sa_jaundice.creationtime,
        CASE
            WHEN (lower((sa_jaundice.jaundicestatus)::text) = 'yes'::text) THEN 'Jaundice'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_jaundice
  WHERE ((lower((sa_jaundice.jaundicestatus)::text) = 'yes'::text) AND (sa_jaundice.sajaundiceid IN ( SELECT max(sa_jaundice_1.sajaundiceid) AS max
           FROM sa_jaundice sa_jaundice_1
          GROUP BY sa_jaundice_1.uhid)))
UNION ALL
 SELECT sa_resp_rds.uhid,
    sa_resp_rds.creationtime,
        CASE
            WHEN (lower((sa_resp_rds.eventstatus)::text) = 'yes'::text) THEN 'RDS'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_resp_rds
  WHERE ((lower((sa_resp_rds.eventstatus)::text) = 'yes'::text) AND (sa_resp_rds.resprdsid IN ( SELECT max(sa_resp_rds_1.resprdsid) AS max
           FROM sa_resp_rds sa_resp_rds_1
          GROUP BY sa_resp_rds_1.uhid)))
UNION ALL
 SELECT sa_resp_pphn.uhid,
    sa_resp_pphn.creationtime,
        CASE
            WHEN (lower((sa_resp_pphn.eventstatus)::text) = 'yes'::text) THEN 'PPHN'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_resp_pphn
  WHERE ((lower((sa_resp_pphn.eventstatus)::text) = 'yes'::text) AND (sa_resp_pphn.resppphnid IN ( SELECT max(sa_resp_pphn_1.resppphnid) AS max
           FROM sa_resp_pphn sa_resp_pphn_1
          GROUP BY sa_resp_pphn_1.uhid)))
UNION ALL
 SELECT sa_resp_apnea.uhid,
    sa_resp_apnea.creationtime,
        CASE
            WHEN (lower((sa_resp_apnea.eventstatus)::text) = 'yes'::text) THEN 'Apnea'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_resp_apnea
  WHERE ((lower((sa_resp_apnea.eventstatus)::text) = 'yes'::text) AND (sa_resp_apnea.apneaid IN ( SELECT max(sa_resp_apnea_1.apneaid) AS max
           FROM sa_resp_apnea sa_resp_apnea_1
          GROUP BY sa_resp_apnea_1.uhid)))
UNION ALL
 SELECT sa_resp_pneumothorax.uhid,
    sa_resp_pneumothorax.creationtime,
        CASE
            WHEN (lower((sa_resp_pneumothorax.eventstatus)::text) = 'yes'::text) THEN 'Pneumothorax '::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_resp_pneumothorax
  WHERE ((lower((sa_resp_pneumothorax.eventstatus)::text) = 'yes'::text) AND (sa_resp_pneumothorax.resppneumothoraxid IN ( SELECT max(sa_resp_pneumothorax_1.resppneumothoraxid) AS max
           FROM sa_resp_pneumothorax sa_resp_pneumothorax_1
          GROUP BY sa_resp_pneumothorax_1.uhid)))
UNION ALL
 SELECT sa_hypoglycemia.uhid,
    sa_hypoglycemia.creationtime,
        CASE
            WHEN (lower((sa_hypoglycemia.eventstatus)::text) = 'yes'::text) THEN 'Hypoglycemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hypoglycemia
  WHERE ((lower((sa_hypoglycemia.eventstatus)::text) = 'yes'::text) AND (sa_hypoglycemia.hypoglycemiaid IN ( SELECT max(sa_hypoglycemia_1.hypoglycemiaid) AS max
           FROM sa_hypoglycemia sa_hypoglycemia_1
          GROUP BY sa_hypoglycemia.uhid)))
UNION ALL
 SELECT sa_hyperglycemia.uhid,
    sa_hyperglycemia.creationtime,
        CASE
            WHEN (lower((sa_hyperglycemia.eventstatus)::text) = 'yes'::text) THEN 'Hyperglycemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hyperglycemia
  WHERE ((lower((sa_hyperglycemia.eventstatus)::text) = 'yes'::text) AND (sa_hyperglycemia.hyperglycemiaid IN ( SELECT max(sa_hyperglycemia_1.hyperglycemiaid) AS max
           FROM sa_hyperglycemia sa_hyperglycemia_1
          GROUP BY sa_hyperglycemia.uhid)))
UNION ALL
 SELECT sa_hypocalcemia.uhid,
    sa_hypocalcemia.creationtime,
        CASE
            WHEN (lower((sa_hypocalcemia.eventstatus)::text) = 'yes'::text) THEN 'Hypocalcemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hypocalcemia
  WHERE ((lower((sa_hypocalcemia.eventstatus)::text) = 'yes'::text) AND (sa_hypocalcemia.hypocalcemiaid IN ( SELECT max(sa_hypocalcemia_1.hypocalcemiaid) AS max
           FROM sa_hypocalcemia sa_hypocalcemia_1
          GROUP BY sa_hypocalcemia.uhid)))
UNION ALL
 SELECT sa_hypercalcemia.uhid,
    sa_hypercalcemia.creationtime,
        CASE
            WHEN (lower((sa_hypercalcemia.eventstatus)::text) = 'yes'::text) THEN 'Hypercalcemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hypercalcemia
  WHERE ((lower((sa_hypercalcemia.eventstatus)::text) = 'yes'::text) AND (sa_hypercalcemia.hypercalcemiaid IN ( SELECT max(sa_hypercalcemia_1.hypercalcemiaid) AS max
           FROM sa_hypercalcemia sa_hypercalcemia_1
          GROUP BY sa_hypercalcemia.uhid)))
UNION ALL
 SELECT sa_hypokalemia.uhid,
    sa_hypokalemia.creationtime,
        CASE
            WHEN (lower((sa_hypokalemia.eventstatus)::text) = 'yes'::text) THEN 'Hypokalemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hypokalemia
  WHERE ((lower((sa_hypokalemia.eventstatus)::text) = 'yes'::text) AND (sa_hypokalemia.hypokalemiaid IN ( SELECT max(sa_hypokalemia_1.hypokalemiaid) AS max
           FROM sa_hypokalemia sa_hypokalemia_1
          GROUP BY sa_hypokalemia.uhid)))
UNION ALL
 SELECT sa_hyperkalemia.uhid,
    sa_hyperkalemia.creationtime,
        CASE
            WHEN (lower((sa_hyperkalemia.eventstatus)::text) = 'yes'::text) THEN 'Hyperkalemia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hyperkalemia
  WHERE ((lower((sa_hyperkalemia.eventstatus)::text) = 'yes'::text) AND (sa_hyperkalemia.hyperkalemiaid IN ( SELECT max(sa_hyperkalemia_1.hyperkalemiaid) AS max
           FROM sa_hyperkalemia sa_hyperkalemia_1
          GROUP BY sa_hyperkalemia.uhid)))
UNION ALL
 SELECT sa_hyponatremia.uhid,
    sa_hyponatremia.creationtime,
        CASE
            WHEN (lower((sa_hyponatremia.eventstatus)::text) = 'yes'::text) THEN 'Hyponatremia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hyponatremia
  WHERE ((lower((sa_hyponatremia.eventstatus)::text) = 'yes'::text) AND (sa_hyponatremia.hyponatremiaid IN ( SELECT max(sa_hyponatremia_1.hyponatremiaid) AS max
           FROM sa_hyponatremia sa_hyponatremia_1
          GROUP BY sa_hyponatremia.uhid)))
UNION ALL
 SELECT sa_hypernatremia.uhid,
    sa_hypernatremia.creationtime,
        CASE
            WHEN (lower((sa_hypernatremia.eventstatus)::text) = 'yes'::text) THEN 'Hypernatremia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_hypernatremia
  WHERE ((lower((sa_hypernatremia.eventstatus)::text) = 'yes'::text) AND (sa_hypernatremia.hypernatremiaid IN ( SELECT max(sa_hypernatremia_1.hypernatremiaid) AS max
           FROM sa_hypernatremia sa_hypernatremia_1
          GROUP BY sa_hypernatremia.uhid)))
UNION ALL
 SELECT sa_acidosis.uhid,
    sa_acidosis.creationtime,
        CASE
            WHEN (lower((sa_acidosis.eventstatus)::text) = 'yes'::text) THEN 'acidosis'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_acidosis
  WHERE ((lower((sa_acidosis.eventstatus)::text) = 'yes'::text) AND (sa_acidosis.acidosisid IN ( SELECT max(sa_acidosis_1.acidosisid) AS max
           FROM sa_acidosis sa_acidosis_1
          GROUP BY sa_acidosis.uhid)))
UNION ALL
 SELECT sa_iem.uhid,
    sa_iem.creationtime,
        CASE
            WHEN (lower((sa_iem.eventstatus)::text) = 'yes'::text) THEN 'IEM'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_iem
  WHERE ((lower((sa_iem.eventstatus)::text) = 'yes'::text) AND (sa_iem.iemid IN ( SELECT max(sa_iem_1.iemid) AS max
           FROM sa_iem sa_iem_1
          GROUP BY sa_iem.uhid)))
UNION ALL
 SELECT sa_cns_asphyxia.uhid,
    sa_cns_asphyxia.creationtime,
        CASE
            WHEN (lower((sa_cns_asphyxia.eventstatus)::text) = 'yes'::text) THEN 'Asphyxia'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_cns_asphyxia
  WHERE ((lower((sa_cns_asphyxia.eventstatus)::text) = 'yes'::text) AND (sa_cns_asphyxia.sacnsasphyxiaid IN ( SELECT max(sa_cns_asphyxia_1.sacnsasphyxiaid) AS max
           FROM sa_cns_asphyxia sa_cns_asphyxia_1
          GROUP BY sa_cns_asphyxia.uhid)))
UNION ALL
 SELECT sa_cns_seizures.uhid,
    sa_cns_seizures.creationtime,
        CASE
            WHEN (lower((sa_cns_seizures.eventstatus)::text) = 'yes'::text) THEN 'Seizure'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_cns_seizures
  WHERE ((lower((sa_cns_seizures.eventstatus)::text) = 'yes'::text) AND (sa_cns_seizures.sacnsseizuresid IN ( SELECT max(sa_cns_seizures_1.sacnsseizuresid) AS max
           FROM sa_cns_seizures sa_cns_seizures_1
          GROUP BY sa_cns_seizures.uhid)))
UNION ALL
 SELECT sa_cns_ivh.uhid,
    sa_cns_ivh.creationtime,
        CASE
            WHEN (lower((sa_cns_ivh.eventstatus)::text) = 'yes'::text) THEN 'Seizure'::text
            ELSE NULL::text
        END AS eventstatus
   FROM sa_cns_ivh
  WHERE ((lower((sa_cns_ivh.eventstatus)::text) = 'yes'::text) AND (sa_cns_ivh.sacnsivhid IN ( SELECT max(sa_cns_ivh_1.sacnsivhid) AS max
           FROM sa_cns_ivh sa_cns_ivh_1
          GROUP BY sa_cns_ivh.uhid)));


ALTER TABLE vw_notification_raw OWNER TO postgres;

--
-- TOC entry 620 (class 1259 OID 72474)
-- Name: vw_notification_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_notification_rule AS
 SELECT vw_notification_raw.uhid,
    vw_notification_raw.creationtime,
    vw_notification_raw.eventstatus,
    now() AS now,
    ceil(((date_part('epoch'::text, now()) - date_part('epoch'::text, vw_notification_raw.creationtime)) / (3600)::double precision)) AS differenceinhrs
   FROM vw_notification_raw;


ALTER TABLE vw_notification_rule OWNER TO postgres;

--
-- TOC entry 621 (class 1259 OID 72478)
-- Name: vw_notification_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_notification_final AS
 SELECT vw_notification_rule.uhid,
    vw_notification_rule.creationtime AS eventtime,
    vw_notification_rule.eventstatus,
    vw_notification_rule.differenceinhrs
   FROM vw_notification_rule
  WHERE (vw_notification_rule.differenceinhrs > (12)::double precision);


ALTER TABLE vw_notification_final OWNER TO postgres;

--
-- TOC entry 622 (class 1259 OID 72482)
-- Name: vw_nursingnotes_heightweight; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_heightweight AS
 SELECT v.uhid,
    v.visitdate,
    bd.dateofbirth,
    ((v.visitdate - bd.dateofbirth))::text AS currentage,
    v.currentdateweight,
        CASE
            WHEN (v.visitdate = bd.dateofadmission) THEN (0)::real
            ELSE (v.currentdateweight - lag(v.currentdateweight) OVER (PARTITION BY v.uhid ORDER BY v.visitdate))
        END AS diffweight,
    v.currentdateheight,
        CASE
            WHEN (v.visitdate = bd.dateofadmission) THEN (0)::real
            ELSE (v.currentdateheight - lag(v.currentdateheight) OVER (PARTITION BY v.uhid ORDER BY v.visitdate))
        END AS diffheight
   FROM (baby_visit v
     LEFT JOIN baby_detail bd ON (((v.uhid)::text = (bd.uhid)::text)))
  ORDER BY bd.uhid, v.visitdate;


ALTER TABLE vw_nursingnotes_heightweight OWNER TO postgres;

--
-- TOC entry 623 (class 1259 OID 72487)
-- Name: vw_nursingnotes_intake_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_intake_raw AS
 SELECT i.nn_intakeid,
    i.uhid,
    i.creationtime,
    i.nn_intake_time,
    i.feedmethod,
    i.feedtype,
        CASE
            WHEN (i.feedvolume IS NULL) THEN (0)::real
            ELSE i.feedvolume
        END AS feedvolume,
    i.hmfvalue,
    i.ivtype,
        CASE
            WHEN (i.ivperhr IS NULL) THEN (0)::real
            ELSE i.ivperhr
        END AS ivperhr,
    i.pnrate,
        CASE
            WHEN (i.aaperhr IS NULL) THEN (0)::real
            ELSE i.aaperhr
        END AS aaperhr,
        CASE
            WHEN (i.lipidperhr IS NULL) THEN (0)::real
            ELSE i.lipidperhr
        END AS lipidperhr,
        CASE
            WHEN (i.ivtotal IS NULL) THEN (0)::real
            ELSE i.ivtotal
        END AS ivtotal,
    b.nn_bolus_time,
    b.bolustype,
        CASE
            WHEN (b.dose IS NULL) THEN (0)::real
            ELSE (b.dose)::real
        END AS bolus_dose,
    b.duration AS bolus_duration,
    b.starttime AS bolus_starttime,
    bl.nn_blood_time,
    bl.bloodproduct,
        CASE
            WHEN (bl.dose IS NULL) THEN (0)::real
            ELSE (bl.dose)::real
        END AS blood_dose,
    bl.duration AS blood_duration,
    bl.starttime AS blood_starttime,
    bl.bloodgroup
   FROM ((nursing_intake i
     LEFT JOIN nursing_bolus b ON (((((i.uhid)::text = (b.uhid)::text) AND ((i.creationtime)::date = (b.creationtime)::date)) AND ((i.nn_intake_time)::text = (b.nn_bolus_time)::text))))
     LEFT JOIN nursing_bloodproducts bl ON (((((i.uhid)::text = (bl.uhid)::text) AND ((i.creationtime)::date = (bl.creationtime)::date)) AND ((i.nn_intake_time)::text = (bl.nn_blood_time)::text))));


ALTER TABLE vw_nursingnotes_intake_raw OWNER TO postgres;

--
-- TOC entry 624 (class 1259 OID 72492)
-- Name: vw_nursingnotes_intake_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_intake_control AS
 SELECT r.nn_intakeid,
    r.uhid,
    r.creationtime,
    r.nn_intake_time,
    r.feedmethod,
    r.feedtype,
    r.feedvolume,
    r.hmfvalue,
    r.ivtype,
    r.ivperhr,
    r.pnrate,
    r.aaperhr,
    r.lipidperhr,
    r.ivtotal,
    r.nn_bolus_time,
        CASE
            WHEN (r.nn_bolus_time IS NOT NULL) THEN r.bolustype
            WHEN (r.nn_blood_time IS NOT NULL) THEN 'BLOOD'::character varying
            ELSE '-'::character varying
        END AS bolustype,
        CASE
            WHEN ((r.bolus_dose)::text IS NOT NULL) THEN ((r.bolus_dose)::text)::character varying
            WHEN ((r.blood_dose)::text IS NOT NULL) THEN ((r.blood_dose)::text)::character varying
            ELSE '0'::character varying
        END AS dose,
        CASE
            WHEN (r.bolus_duration IS NOT NULL) THEN r.bolus_duration
            WHEN (r.blood_duration IS NOT NULL) THEN r.blood_duration
            ELSE '-'::character varying
        END AS duration,
        CASE
            WHEN (r.bolus_starttime IS NOT NULL) THEN r.bolus_starttime
            WHEN (r.blood_starttime IS NOT NULL) THEN r.blood_starttime
            ELSE '-'::character varying
        END AS starttime,
    r.bloodproduct,
    r.bloodgroup
   FROM vw_nursingnotes_intake_raw r;


ALTER TABLE vw_nursingnotes_intake_control OWNER TO postgres;

--
-- TOC entry 625 (class 1259 OID 72497)
-- Name: vw_nursingnotes_intake_rule1; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_intake_rule1 AS
 SELECT r.nn_intakeid,
    r.uhid,
    r.creationtime,
    r.nn_intake_time,
    r.feedmethod,
    r.feedtype,
    r.feedvolume,
    r.hmfvalue,
    r.ivtype,
    r.ivperhr,
    r.pnrate,
    r.aaperhr,
    r.lipidperhr,
    r.ivtotal,
    r.nn_bolus_time,
    r.bolustype,
    r.bolus_dose,
    r.bolus_duration,
    r.bolus_starttime,
    r.nn_blood_time,
    r.bloodproduct,
    r.blood_dose,
    r.blood_duration,
    r.blood_starttime,
    r.bloodgroup,
    ((((r.feedvolume)::integer + (r.ivtotal)::integer) + (r.blood_dose)::integer))::text AS hourly_intake,
    ((((r.feedvolume)::integer + (r.ivtotal)::integer) + (r.blood_dose)::integer))::text AS total_intake
   FROM vw_nursingnotes_intake_raw r;


ALTER TABLE vw_nursingnotes_intake_rule1 OWNER TO postgres;

--
-- TOC entry 626 (class 1259 OID 72502)
-- Name: vw_nursingnotes_intake_final2; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_intake_final2 AS
 SELECT r.nn_intakeid,
    r.uhid,
    r.creationtime,
    r.nn_intake_time,
    r.feedmethod,
    r.feedtype,
    r.feedvolume,
    r.hmfvalue,
    r.ivtype,
    r.ivperhr,
    r.pnrate,
    r.aaperhr,
    r.lipidperhr,
    r.ivtotal,
    r.nn_bolus_time,
    r.bolustype,
    (r.bolus_dose)::text AS bolus_dose,
    r.bolus_duration,
    r.bolus_starttime,
    r.nn_blood_time,
    r.bloodproduct,
    (r.blood_dose)::text AS blood_dose,
    r.blood_duration,
    r.blood_starttime,
    r.bloodgroup,
    r.hourly_intake,
    r.total_intake
   FROM vw_nursingnotes_intake_rule1 r;


ALTER TABLE vw_nursingnotes_intake_final2 OWNER TO postgres;

--
-- TOC entry 627 (class 1259 OID 72506)
-- Name: vw_nursingnotes_output_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_output_raw AS
 SELECT i.nn_outputid AS outputid,
    i.uhid,
    i.creationtime,
    i.nn_output_time,
    i.aspirate_quantity,
    i.aspirate_type,
        CASE
            WHEN (i.urine_mls IS NULL) THEN '0'::character varying
            ELSE i.urine_mls
        END AS urine_mls,
        CASE
            WHEN (i.urine_mlkg IS NULL) THEN '0'::character varying
            ELSE i.urine_mlkg
        END AS urine_mlkg,
        CASE
            WHEN (i.total_uo IS NULL) THEN '0'::character varying
            ELSE i.total_uo
        END AS total_uo,
        CASE
            WHEN (i.blood_letting IS NULL) THEN '0'::character varying
            ELSE i.blood_letting
        END AS blood_letting,
    i.bowel_status,
    i.bowel_type,
    i.bowel_color,
    d.nn_drain_time,
    d.drain1_input,
    d.drain2_input,
    d.drain3_input,
        CASE
            WHEN (d.drain1_output IS NULL) THEN '0'::character varying
            ELSE d.drain1_output
        END AS drain1_output,
        CASE
            WHEN (d.drain2_output IS NULL) THEN '0'::character varying
            ELSE d.drain2_output
        END AS drain2_output,
        CASE
            WHEN (d.drain3_output IS NULL) THEN '0'::character varying
            ELSE d.drain3_output
        END AS drain3_output
   FROM (nursing_output i
     LEFT JOIN nursing_outputdrain d ON (((((i.uhid)::text = (d.uhid)::text) AND ((i.creationtime)::date = (d.creationtime)::date)) AND ((i.nn_output_time)::text = (d.nn_drain_time)::text))));


ALTER TABLE vw_nursingnotes_output_raw OWNER TO postgres;

--
-- TOC entry 628 (class 1259 OID 72511)
-- Name: vw_nursingnotes_output_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_output_control AS
 SELECT r.outputid,
    r.uhid,
    r.creationtime,
    r.nn_output_time,
    r.aspirate_quantity,
    r.aspirate_type,
    r.urine_mls,
    r.urine_mlkg,
    r.total_uo,
    r.blood_letting,
    r.bowel_status,
    r.bowel_type,
    r.bowel_color,
    r.nn_drain_time,
    r.drain1_input,
    r.drain2_input,
    r.drain3_input,
    r.drain1_output,
    r.drain2_output,
    r.drain3_output,
    (((r.drain1_output)::integer + (r.drain2_output)::integer) + (r.drain3_output)::integer) AS hourly_drain_output,
    (((r.drain1_output)::integer + (r.drain2_output)::integer) + (r.drain3_output)::integer) AS total_drain_output,
    (((((r.total_uo)::integer + (r.blood_letting)::integer) + (r.drain1_output)::integer) + (r.drain2_output)::integer) + (r.drain3_output)::integer) AS hourly_output,
    (((((r.total_uo)::integer + (r.blood_letting)::integer) + (r.drain1_output)::integer) + (r.drain2_output)::integer) + (r.drain3_output)::integer) AS total_output
   FROM vw_nursingnotes_output_raw r;


ALTER TABLE vw_nursingnotes_output_control OWNER TO postgres;

--
-- TOC entry 629 (class 1259 OID 72516)
-- Name: vw_nursingnotes_output_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_output_final AS
 SELECT r.outputid,
    r.uhid,
    r.creationtime,
    r.nn_output_time,
    r.aspirate_quantity,
    r.aspirate_type,
    r.urine_mls,
    r.urine_mlkg,
    r.total_uo,
    r.blood_letting,
    r.bowel_status,
    r.bowel_type,
    r.bowel_color,
    r.nn_drain_time,
    r.drain1_input,
    r.drain1_output,
    r.drain2_input,
    r.drain2_output,
    r.drain3_input,
    r.drain3_output,
    r.hourly_drain_output,
    r.total_drain_output,
    r.hourly_output,
    r.total_output
   FROM vw_nursingnotes_output_control r;


ALTER TABLE vw_nursingnotes_output_final OWNER TO postgres;

--
-- TOC entry 630 (class 1259 OID 72520)
-- Name: vw_nursingnotes_totalinput; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_totalinput AS
 SELECT n.uhid,
    (n.creationtime)::date AS nursingdate,
    sum((n.hourly_intake)::integer) AS totalinput
   FROM vw_nursingnotes_intake_final2 n
  GROUP BY n.uhid, (n.creationtime)::date
  ORDER BY n.uhid, (n.creationtime)::date;


ALTER TABLE vw_nursingnotes_totalinput OWNER TO postgres;

--
-- TOC entry 631 (class 1259 OID 72524)
-- Name: vw_nursingnotes_totaloutput; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_totaloutput AS
 SELECT n.uhid,
    (n.creationtime)::date AS nursingdate,
    sum(n.hourly_output) AS totaloutput
   FROM vw_nursingnotes_output_final n
  GROUP BY n.uhid, (n.creationtime)::date
  ORDER BY n.uhid, (n.creationtime)::date;


ALTER TABLE vw_nursingnotes_totaloutput OWNER TO postgres;

--
-- TOC entry 632 (class 1259 OID 72528)
-- Name: vw_nursingnotes_totalinputoutput; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_totalinputoutput AS
 SELECT ni.uhid,
    ni.nursingdate AS creationdate,
    ni.totalinput,
    no.totaloutput
   FROM (vw_nursingnotes_totalinput ni
     LEFT JOIN vw_nursingnotes_totaloutput no ON (((ni.nursingdate = no.nursingdate) AND ((no.uhid)::text = (ni.uhid)::text))))
  ORDER BY ni.nursingdate;


ALTER TABLE vw_nursingnotes_totalinputoutput OWNER TO postgres;

--
-- TOC entry 633 (class 1259 OID 72532)
-- Name: vw_nusingnotes_usage_raw; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nusingnotes_usage_raw AS
 SELECT f.uhid,
    'baby_visit'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM baby_visit f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_bloodgas'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_bloodgas f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_bloodproducts'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_bloodproducts f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_catheters'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_catheters f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_dailyassesment'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_dailyassesment f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_intake'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_intake f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_misc'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_misc f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_neurovitals'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_neurovitals f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_output'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_output f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_ventilaor'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_ventilaor f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT f.uhid,
    'nursing_vitalparameters'::text AS type,
    count(f.uhid) AS entrycount,
    (f.creationtime)::date AS creationtime,
    f.loggeduser
   FROM nursing_vitalparameters f
  GROUP BY f.uhid, (f.creationtime)::date, f.loggeduser
UNION
 SELECT p.uhid,
    'med_administration'::text AS type,
    count(p.uhid) AS entrycount,
    (m.creationtime)::date AS creationtime,
    m.loggeduser
   FROM (baby_prescription p
     JOIN med_administration m ON ((p.baby_presid = m.baby_presid)))
  GROUP BY p.uhid, (m.creationtime)::date, m.loggeduser;


ALTER TABLE vw_nusingnotes_usage_raw OWNER TO postgres;

--
-- TOC entry 634 (class 1259 OID 72537)
-- Name: vw_nursingnotes_usage_rule; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_usage_rule AS
 SELECT s.uhid,
    s.creationtime,
    s.loggeduser,
    max(
        CASE s.type
            WHEN 'baby_visit'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS baby_visit,
    max(
        CASE s.type
            WHEN 'nursing_bloodgas'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS bloodgas,
    max(
        CASE s.type
            WHEN 'nursing_bloodproducts'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS bloodproducts,
    max(
        CASE s.type
            WHEN 'nursing_catheters'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS catheters,
    max(
        CASE s.type
            WHEN 'nursing_dailyassesment'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS dailyassesment,
    max(
        CASE s.type
            WHEN 'nursing_intake'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS intake,
    max(
        CASE s.type
            WHEN 'nursing_misc'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS nursingmisc,
    max(
        CASE s.type
            WHEN 'nursing_neurovitals'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS neurovitals,
    max(
        CASE s.type
            WHEN 'nursing_output'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS output,
    max(
        CASE s.type
            WHEN 'nursing_ventilaor'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS ventilaor,
    max(
        CASE s.type
            WHEN 'nursing_vitalparameters'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS vitalparameters,
    max(
        CASE s.type
            WHEN 'med_administration'::text THEN (s.entrycount)::text
            ELSE NULL::text
        END) AS med_administration
   FROM ( SELECT r.uhid,
            r.type,
            r.entrycount,
            r.creationtime,
            r.loggeduser
           FROM vw_nusingnotes_usage_raw r
          GROUP BY r.uhid, r.type, r.entrycount, r.creationtime, r.loggeduser) s
  GROUP BY s.uhid, s.creationtime, s.loggeduser;


ALTER TABLE vw_nursingnotes_usage_rule OWNER TO postgres;

--
-- TOC entry 635 (class 1259 OID 72542)
-- Name: vw_nursingnotes_usage_control; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_usage_control AS
 SELECT b.uhid,
    b.babyname,
    b.niculevelno,
    b.criticalitylevel,
    s.creationtime,
    s.loggeduser,
    s.baby_visit,
    s.vitalparameters,
    s.ventilaor,
    s.neurovitals,
    s.bloodgas,
    s.dailyassesment,
    s.bloodproducts,
    s.catheters,
    s.intake,
    s.output,
    s.nursingmisc,
    s.med_administration
   FROM (baby_detail b
     LEFT JOIN vw_nursingnotes_usage_rule s ON (((b.uhid)::text = (s.uhid)::text)))
  ORDER BY b.uhid, s.creationtime;


ALTER TABLE vw_nursingnotes_usage_control OWNER TO postgres;

--
-- TOC entry 636 (class 1259 OID 72547)
-- Name: vw_nursingnotes_usage_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_nursingnotes_usage_final AS
 SELECT r.uhid,
    r.babyname,
    rl.levelname AS niculevel,
    rc.levelname AS criticality,
    r.creationtime,
    r.loggeduser,
        CASE
            WHEN (r.baby_visit IS NULL) THEN '0'::text
            ELSE r.baby_visit
        END AS weight_mesaurement,
        CASE
            WHEN (r.vitalparameters IS NULL) THEN '0'::text
            ELSE r.vitalparameters
        END AS vitalparameters,
        CASE
            WHEN (r.ventilaor IS NULL) THEN '0'::text
            ELSE r.ventilaor
        END AS ventilaor,
        CASE
            WHEN (r.neurovitals IS NULL) THEN '0'::text
            ELSE r.neurovitals
        END AS neurovitals,
        CASE
            WHEN (r.bloodgas IS NULL) THEN '0'::text
            ELSE r.bloodgas
        END AS bloodgas,
        CASE
            WHEN (r.dailyassesment IS NULL) THEN '0'::text
            ELSE r.dailyassesment
        END AS dailyassesment,
        CASE
            WHEN (r.bloodproducts IS NULL) THEN '0'::text
            ELSE r.bloodproducts
        END AS bloodproducts,
        CASE
            WHEN (r.catheters IS NULL) THEN '0'::text
            ELSE r.catheters
        END AS catheters,
        CASE
            WHEN (r.intake IS NULL) THEN '0'::text
            ELSE r.intake
        END AS intake,
        CASE
            WHEN (r.output IS NULL) THEN '0'::text
            ELSE r.output
        END AS output,
        CASE
            WHEN (r.nursingmisc IS NULL) THEN '0'::text
            ELSE r.nursingmisc
        END AS nursingmisc,
        CASE
            WHEN (r.med_administration IS NULL) THEN '0'::text
            ELSE r.med_administration
        END AS medadministration
   FROM ((vw_nursingnotes_usage_control r
     LEFT JOIN ref_level rl ON (((r.niculevelno)::text = (rl.levelid)::text)))
     LEFT JOIN ref_criticallevel rc ON (((r.criticalitylevel)::text = (rc.crlevelid)::text)))
  ORDER BY r.uhid, r.creationtime;


ALTER TABLE vw_nursingnotes_usage_final OWNER TO postgres;

--
-- TOC entry 637 (class 1259 OID 72552)
-- Name: vw_respiratory_usage_raw; Type: TABLE; Schema: kdah; Owner: postgres; Tablespace: 
--

CREATE TABLE vw_respiratory_usage_raw (
    respsupportid bigint,
    creationtime timestamp with time zone,
    uhid character varying(50),
    eventid character varying(50),
    eventname character varying(50),
    rs_vent_type character varying(100),
    isactive boolean,
    endtime timestamp with time zone
);

ALTER TABLE ONLY vw_respiratory_usage_raw REPLICA IDENTITY NOTHING;


ALTER TABLE vw_respiratory_usage_raw OWNER TO postgres;

--
-- TOC entry 638 (class 1259 OID 72555)
-- Name: vw_respiratory_usage_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_respiratory_usage_final AS
 SELECT vw_respiratory_usage_raw.uhid,
    vw_respiratory_usage_raw.creationtime,
    vw_respiratory_usage_raw.eventid,
    vw_respiratory_usage_raw.eventname,
    vw_respiratory_usage_raw.rs_vent_type,
    vw_respiratory_usage_raw.isactive,
    vw_respiratory_usage_raw.endtime,
    ceil(((date_part('epoch'::text, vw_respiratory_usage_raw.endtime) - date_part('epoch'::text, vw_respiratory_usage_raw.creationtime)) / (3600)::double precision)) AS differenceinhrs
   FROM vw_respiratory_usage_raw;


ALTER TABLE vw_respiratory_usage_final OWNER TO postgres;

--
-- TOC entry 639 (class 1259 OID 72559)
-- Name: vw_sa_cns; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_cns AS
 SELECT c.cnspid,
    c.creationtime,
    b.cry,
    c.uhid,
    c.posture,
    c.cthead,
    c.neurexamdisc,
    c.eeg,
    c.nnr,
    c.feeding,
    c.tone,
    c.hie,
    c.stagehie,
    c.ivh,
    c.gradeivh,
    c.seizures,
    c.ageofonsetseizuresdays,
    rt.seizurestypeid,
    rc.seizurescauseid,
    rm.seizuresmediid AS seizures_medi,
    c.mrihead,
    c.seizures_cause,
    c.seizures_type,
    c.bera,
    c.vep,
    c.comment
   FROM ((((sa_cns c
     JOIN baby_detail b ON (((b.uhid)::text = (c.uhid)::text)))
     LEFT JOIN ref_seizures_type rt ON (((c.seizures_type)::text = (rt.seizurestypeid)::text)))
     LEFT JOIN ref_seizures_cause rc ON (((c.seizures_cause)::text = (rc.seizurescauseid)::text)))
     LEFT JOIN ref_seizures_medication rm ON (((c.seizures_medi)::text = (rm.seizuresmediid)::text)))
  ORDER BY c.creationtime DESC;


ALTER TABLE vw_sa_cns OWNER TO postgres;





--
-- TOC entry 640 (class 1259 OID 72564)
-- Name: vw_sa_jaundice; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_jaundice AS
 SELECT j.sajaundiceid,
    b.uhid,
    j.creationtime,
    j.ageofonset,
    j.durationofjaundice,
    rj.causeofjaundice,
    j.dct,
    j.exchangetrans,
    j.noofexchange,
    j.phototherapy,
    j.durphoto,
    j.hemolysis,
    j.recticct,
    j.ivig,
    j.maxbili,
    j.comment
   FROM ((baby_detail b
     LEFT JOIN sa_jaundice j ON (((b.uhid)::text = (j.uhid)::text)))
     LEFT JOIN ref_causeofjaundice rj ON (((j.causeofjaundice)::text = (rj.causeofjaundiceid)::text)))
  ORDER BY j.creationtime DESC;


ALTER TABLE vw_sa_jaundice OWNER TO postgres;

--
-- TOC entry 641 (class 1259 OID 72569)
-- Name: vw_sa_metabolic; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_metabolic AS
 SELECT m.sametabolicid,
    b.uhid,
    m.creationtime,
    m.hypoglycemia,
    m.minimumbs,
    m.dayhypoglycemia,
    m.durationhypoglycemia,
    m.maxgdr,
    rm.treatmentused,
    m.comments,
    m.maximumbs,
    m.insulinlevel,
    m.tms,
    m.gcms,
    m.symptoms
   FROM ((baby_detail b
     LEFT JOIN sa_metabolic m ON (((b.uhid)::text = (m.uhid)::text)))
     LEFT JOIN ref_metabolic_treatment rm ON (((m.treatmentused)::text = (rm.treatmentid)::text)))
  ORDER BY m.creationtime DESC;


ALTER TABLE vw_sa_metabolic OWNER TO postgres;

--
-- TOC entry 642 (class 1259 OID 72574)
-- Name: vw_sa_respsystem; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sa_respsystem AS
 SELECT r.respid,
    r.creationtime,
    r.uhid,
    rd.rdscauseid,
    rd.rdscause,
    r.lowestspo2,
    r.spo2,
    r.maxrr,
    r.apnea,
    r.maxfio2,
    r.maxbili,
    r.mechvent,
    rv.ventmodeid,
    r.ageatvent,
    r.ventduration,
    r.surfactant,
    r.noofdoses,
    r.ageatsurfactant,
    r.cld,
    rc.cldstageid,
    rc.cldstage,
    r.pphn,
    r.pulmhaem,
    rr.mvreasonid,
    rr.reasonofmv,
    r.cpap,
    r.daysofcpap,
    r.airvo,
    r.niv,
    r.ventilationmode,
    r.caffeine_startage,
    r.caffeine_stopage,
    r.ventilation_type,
    r.comment
   FROM ((((sa_respsystem r
     LEFT JOIN ref_rdscause rd ON (((r.rdscause)::text = (rd.rdscauseid)::text)))
     LEFT JOIN ref_ventilationmode rv ON (((r.ventilationmode)::text = (rv.ventmodeid)::text)))
     LEFT JOIN ref_reasonofmv rr ON (((r.reasonofmv)::text = (rr.mvreasonid)::text)))
     LEFT JOIN ref_cldstage rc ON (((r.cldstage)::text = (rc.cldstageid)::text)))
  ORDER BY r.creationtime DESC;


ALTER TABLE vw_sa_respsystem OWNER TO postgres;

--
-- TOC entry 643 (class 1259 OID 72579)
-- Name: vw_sepsis_earlyonset_final; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_sepsis_earlyonset_final AS
 SELECT DISTINCT sa_infection_sepsis.uhid,
    min(
        CASE
            WHEN (sa_infection_sepsis.ageinhoursdays IS NULL) THEN (sa_infection_sepsis.ageatonset)::integer
            WHEN (sa_infection_sepsis.ageinhoursdays IS TRUE) THEN (sa_infection_sepsis.ageatonset)::integer
            WHEN (sa_infection_sepsis.ageinhoursdays IS FALSE) THEN ((sa_infection_sepsis.ageatonset)::integer * 24)
            ELSE NULL::integer
        END) AS ageonsethours
   FROM sa_infection_sepsis
  GROUP BY sa_infection_sepsis.uhid;


ALTER TABLE vw_sepsis_earlyonset_final OWNER TO postgres;

--
-- TOC entry 644 (class 1259 OID 72584)
-- Name: vw_vaccinations_brand; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_vaccinations_brand AS
 SELECT b.vaccineid,
    v.vaccinename,
    b.id,
    b.brandname
   FROM (brand b
     LEFT JOIN vaccine v ON ((v.vaccineid = b.vaccineid)))
  WHERE (b.isactivebrand = true)
  ORDER BY v.vaccineid;


ALTER TABLE vw_vaccinations_brand OWNER TO postgres;

--
-- TOC entry 645 (class 1259 OID 72588)
-- Name: vw_vaccinations_patient; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_vaccinations_patient AS
 SELECT pdvd.patientduervaccineid,
    pdvd.uid,
    d.dueage,
    pdvd.vaccineinfoid,
    v.vaccineid,
    v.vaccinename,
    pdvd.duedate,
    pdvd.givendate,
    pdvd.administratedby,
    pdvd.brandid,
    b.brandname,
    pdvd.creationtime
   FROM ((((patient_due_vaccine_dtls pdvd
     LEFT JOIN vaccine_info vi ON ((vi.vaccineinfoid = pdvd.vaccineinfoid)))
     LEFT JOIN vaccine v ON ((v.vaccineid = vi.vaccineid)))
     LEFT JOIN brand b ON ((b.id = pdvd.brandid)))
     LEFT JOIN inicudueage d ON ((d.dueageid = vi.dueageid)))
  ORDER BY d.dueageid, pdvd.vaccineinfoid;


ALTER TABLE vw_vaccinations_patient OWNER TO postgres;

--
-- TOC entry 646 (class 1259 OID 72593)
-- Name: vw_vaccinations_populate; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vw_vaccinations_populate AS
 SELECT d.dueage,
    vi.vaccineinfoid
   FROM ((vaccine_info vi
     JOIN vaccine v ON ((v.vaccineid = vi.vaccineid)))
     JOIN inicudueage d ON ((d.dueageid = vi.dueageid)))
  WHERE ((vi.isactivevaccine = true) AND (d.isactivedueage = true))
  ORDER BY d.seq;


ALTER TABLE vw_vaccinations_populate OWNER TO postgres;

--
-- TOC entry 647 (class 1259 OID 72597)
-- Name: vwuserinfo; Type: VIEW; Schema: kdah; Owner: postgres
--

CREATE VIEW vwuserinfo AS
 SELECT u.username,
    u.companyid,
    u.password,
    u.emailaddress,
    u.active AS status,
        CASE
            WHEN (u.firstname IS NULL) THEN (u.lastname)::text
            WHEN (u.lastname IS NULL) THEN (u.firstname)::text
            ELSE (((u.firstname)::text || ' '::text) || (u.lastname)::text)
        END AS hospempname,
    u.designation,
    u.mobile AS contactno,
    u.picture AS profilepic,
    u.digitalsign AS signature,
    r.rolename,
    u.reportingdoctor
   FROM ((inicuuser u
     JOIN users_roles ur ON (((u.username)::text = (ur.username)::text)))
     JOIN role r ON (((r.roleid)::text = (ur.roleid)::text)))
  WHERE ((r.rolename)::text <> 'Admin'::text)
  ORDER BY u.creationtime;
  
  
  









