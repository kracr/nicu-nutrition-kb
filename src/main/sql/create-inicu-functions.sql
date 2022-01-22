--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.5
-- Dumped by pg_dump version 9.4.5
-- Started on 2017-09-11 10:18:42 IST
SET search_path TO inicudb;
SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 70403)
-- Name: kdah; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA kdah;


ALTER SCHEMA kdah OWNER TO postgres;

--
-- TOC entry 648 (class 3079 OID 12123)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 5259 (class 0 OID 0)
-- Dependencies: 648
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = kdah, pg_catalog;

--
-- TOC entry 649 (class 1255 OID 70404)
-- Name: acidosis_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION acidosis_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END; ';


ALTER FUNCTION kdah.acidosis_creationtime() OWNER TO postgres;

--
-- TOC entry 662 (class 1255 OID 70405)
-- Name: acidosis_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION acidosis_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.acidosis_modificationtime() OWNER TO postgres;

--
-- TOC entry 663 (class 1255 OID 70406)
-- Name: addinfo_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION addinfo_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.addinfo_creationtime() OWNER TO postgres;

--
-- TOC entry 664 (class 1255 OID 70407)
-- Name: addinfo_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION addinfo_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.addinfo_modificationtime() OWNER TO postgres;

--
-- TOC entry 665 (class 1255 OID 70408)
-- Name: admission_notes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION admission_notes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.admission_notes_creationtime() OWNER TO postgres;

--
-- TOC entry 666 (class 1255 OID 70409)
-- Name: admission_notes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION admission_notes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.admission_notes_modificationtime() OWNER TO postgres;

--
-- TOC entry 667 (class 1255 OID 70410)
-- Name: antenatal_history_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION antenatal_history_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.antenatal_history_creationtime() OWNER TO postgres;

--
-- TOC entry 668 (class 1255 OID 70411)
-- Name: antenatal_history_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION antenatal_history_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.antenatal_history_modificationtime() OWNER TO postgres;

--
-- TOC entry 669 (class 1255 OID 70412)
-- Name: antenatal_steroid_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION antenatal_steroid_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.antenatal_steroid_creationtime() OWNER TO postgres;

--
-- TOC entry 670 (class 1255 OID 70413)
-- Name: antenatal_steroid_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION antenatal_steroid_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.antenatal_steroid_modificationtime() OWNER TO postgres;

--
-- TOC entry 671 (class 1255 OID 70414)
-- Name: apgarscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION apgarscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.apgarscore_creationtime() OWNER TO postgres;

--
-- TOC entry 672 (class 1255 OID 70415)
-- Name: apgarscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION apgarscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.apgarscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 673 (class 1255 OID 70416)
-- Name: baby_prescription_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION baby_prescription_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.baby_prescription_creationtime() OWNER TO postgres;

--
-- TOC entry 674 (class 1255 OID 70417)
-- Name: baby_prescription_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION baby_prescription_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.baby_prescription_modificationtime() OWNER TO postgres;

--
-- TOC entry 675 (class 1255 OID 70418)
-- Name: babydetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babydetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babydetail_creationtime() OWNER TO postgres;

--
-- TOC entry 676 (class 1255 OID 70419)
-- Name: babydetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babydetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babydetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 677 (class 1255 OID 70420)
-- Name: babydetails_episodeid(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babydetails_episodeid() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.episodeid := NEW.uhid||''_''||replace(NEW.dateofadmission::text,''-'','''');
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babydetails_episodeid() OWNER TO postgres;

--
-- TOC entry 678 (class 1255 OID 70421)
-- Name: babyfeed_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babyfeed_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.entrydatetime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babyfeed_creationtime() OWNER TO postgres;

--
-- TOC entry 679 (class 1255 OID 70422)
-- Name: babyfeed_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babyfeed_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babyfeed_modificationtime() OWNER TO postgres;

--
-- TOC entry 680 (class 1255 OID 70423)
-- Name: babyfeedivmed_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babyfeedivmed_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babyfeedivmed_creationtime() OWNER TO postgres;

--
-- TOC entry 681 (class 1255 OID 70424)
-- Name: babyfeedivmed_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babyfeedivmed_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babyfeedivmed_modificationtime() OWNER TO postgres;

--
-- TOC entry 682 (class 1255 OID 70425)
-- Name: babytpnfeed_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babytpnfeed_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babytpnfeed_creationtime() OWNER TO postgres;

--
-- TOC entry 683 (class 1255 OID 70426)
-- Name: babytpnfeed_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION babytpnfeed_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.babytpnfeed_modificationtime() OWNER TO postgres;

--
-- TOC entry 684 (class 1255 OID 70427)
-- Name: ballardscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ballardscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ballardscore_creationtime() OWNER TO postgres;

--
-- TOC entry 685 (class 1255 OID 70428)
-- Name: ballardscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ballardscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ballardscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 686 (class 1255 OID 70429)
-- Name: beddevicedetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION beddevicedetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.beddevicedetail_creationtime() OWNER TO postgres;

--
-- TOC entry 687 (class 1255 OID 70430)
-- Name: beddevicedetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION beddevicedetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.beddevicedetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 688 (class 1255 OID 70431)
-- Name: bellstagescore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bellstagescore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bellstagescore_creationtime() OWNER TO postgres;

--
-- TOC entry 689 (class 1255 OID 70432)
-- Name: bellstagescore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bellstagescore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bellstagescore_modificationtime() OWNER TO postgres;

--
-- TOC entry 690 (class 1255 OID 70433)
-- Name: bindscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bindscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bindscore_creationtime() OWNER TO postgres;

--
-- TOC entry 691 (class 1255 OID 70434)
-- Name: bindscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bindscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bindscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 692 (class 1255 OID 70435)
-- Name: birth_to_nicu_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION birth_to_nicu_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.birth_to_nicu_creationtime() OWNER TO postgres;

--
-- TOC entry 693 (class 1255 OID 70436)
-- Name: birth_to_nicu_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION birth_to_nicu_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.birth_to_nicu_modificationtime() OWNER TO postgres;

--
-- TOC entry 694 (class 1255 OID 70437)
-- Name: bloodproducts_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bloodproducts_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.entrydatetime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bloodproducts_creationtime() OWNER TO postgres;

--
-- TOC entry 695 (class 1255 OID 70438)
-- Name: bloodproducts_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION bloodproducts_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.bloodproducts_modificationtime() OWNER TO postgres;

--
-- TOC entry 696 (class 1255 OID 70439)
-- Name: cardiac_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION cardiac_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.cardiac_creationtime() OWNER TO postgres;

--
-- TOC entry 697 (class 1255 OID 70440)
-- Name: cardiac_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION cardiac_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.cardiac_modificationtime() OWNER TO postgres;

--
-- TOC entry 698 (class 1255 OID 70441)
-- Name: central_line_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION central_line_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.central_line_creationtime() OWNER TO postgres;

--
-- TOC entry 699 (class 1255 OID 70442)
-- Name: central_line_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION central_line_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.central_line_modificationtime() OWNER TO postgres;

--
-- TOC entry 700 (class 1255 OID 70443)
-- Name: clabsi_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION clabsi_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.clabsi_creationtime() OWNER TO postgres;

--
-- TOC entry 701 (class 1255 OID 70444)
-- Name: clabsi_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION clabsi_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.clabsi_modificationtime() OWNER TO postgres;

--
-- TOC entry 702 (class 1255 OID 70445)
-- Name: currentpregnancy_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION currentpregnancy_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.currentpregnancy_creationtime() OWNER TO postgres;

--
-- TOC entry 703 (class 1255 OID 70446)
-- Name: currentpregnancy_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION currentpregnancy_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.currentpregnancy_modificationtime() OWNER TO postgres;

--
-- TOC entry 704 (class 1255 OID 70447)
-- Name: device_bloodgas_detail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION device_bloodgas_detail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.device_bloodgas_detail_creationtime() OWNER TO postgres;

--
-- TOC entry 705 (class 1255 OID 70448)
-- Name: device_bloodgas_detail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION device_bloodgas_detail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.device_bloodgas_detail_modificationtime() OWNER TO postgres;

--
-- TOC entry 706 (class 1255 OID 70449)
-- Name: device_ventilator_detail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION device_ventilator_detail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.device_ventilator_detail_creationtime() OWNER TO postgres;

--
-- TOC entry 707 (class 1255 OID 70450)
-- Name: devicedetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION devicedetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.devicedetail_creationtime() OWNER TO postgres;

--
-- TOC entry 708 (class 1255 OID 70451)
-- Name: devicedetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION devicedetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.devicedetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 709 (class 1255 OID 70452)
-- Name: devicemapping_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION devicemapping_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.devicemapping_creationtime() OWNER TO postgres;

--
-- TOC entry 710 (class 1255 OID 70453)
-- Name: devicemapping_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION devicemapping_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
         NEW.modificationtime := current_timestamp;
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.devicemapping_modificationtime() OWNER TO postgres;

--
-- TOC entry 711 (class 1255 OID 70454)
-- Name: discharge_advice_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_advice_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_advice_creationtime() OWNER TO postgres;

--
-- TOC entry 712 (class 1255 OID 70455)
-- Name: discharge_advice_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_advice_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_advice_modificationtime() OWNER TO postgres;

--
-- TOC entry 713 (class 1255 OID 70456)
-- Name: discharge_doctornotes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_doctornotes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_doctornotes_creationtime() OWNER TO postgres;

--
-- TOC entry 714 (class 1255 OID 70457)
-- Name: discharge_doctornotes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_doctornotes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_doctornotes_modificationtime() OWNER TO postgres;

--
-- TOC entry 715 (class 1255 OID 70458)
-- Name: discharge_notes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_notes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_notes_creationtime() OWNER TO postgres;

--
-- TOC entry 716 (class 1255 OID 70459)
-- Name: discharge_notes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION discharge_notes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.discharge_notes_modificationtime() OWNER TO postgres;

--
-- TOC entry 717 (class 1255 OID 70460)
-- Name: dischargeaminophylline_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeaminophylline_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeaminophylline_creationtime() OWNER TO postgres;

--
-- TOC entry 718 (class 1255 OID 70461)
-- Name: dischargeaminophylline_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeaminophylline_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeaminophylline_modificationtime() OWNER TO postgres;

--
-- TOC entry 720 (class 1255 OID 70462)
-- Name: dischargebirthdetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargebirthdetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargebirthdetail_creationtime() OWNER TO postgres;

--
-- TOC entry 721 (class 1255 OID 70463)
-- Name: dischargebirthdetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargebirthdetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargebirthdetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 722 (class 1255 OID 70464)
-- Name: dischargecaffeine_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargecaffeine_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargecaffeine_creationtime() OWNER TO postgres;

--
-- TOC entry 723 (class 1255 OID 70465)
-- Name: dischargecaffeine_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargecaffeine_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargecaffeine_modificationtime() OWNER TO postgres;

--
-- TOC entry 724 (class 1255 OID 70466)
-- Name: dischargecpap_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargecpap_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargecpap_creationtime() OWNER TO postgres;

--
-- TOC entry 725 (class 1255 OID 70467)
-- Name: dischargecpap_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargecpap_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargecpap_modificationtime() OWNER TO postgres;

--
-- TOC entry 726 (class 1255 OID 70468)
-- Name: dischargefeeding_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargefeeding_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargefeeding_creationtime() OWNER TO postgres;

--
-- TOC entry 727 (class 1255 OID 70469)
-- Name: dischargefeeding_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargefeeding_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargefeeding_modificationtime() OWNER TO postgres;

--
-- TOC entry 728 (class 1255 OID 70470)
-- Name: dischargeinfection_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeinfection_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeinfection_creationtime() OWNER TO postgres;

--
-- TOC entry 729 (class 1255 OID 70471)
-- Name: dischargeinfection_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeinfection_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeinfection_modificationtime() OWNER TO postgres;

--
-- TOC entry 730 (class 1255 OID 70472)
-- Name: dischargeinvestigation_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeinvestigation_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeinvestigation_creationtime() OWNER TO postgres;

--
-- TOC entry 731 (class 1255 OID 70473)
-- Name: dischargeinvestigation_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeinvestigation_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeinvestigation_modificationtime() OWNER TO postgres;

--
-- TOC entry 732 (class 1255 OID 70474)
-- Name: dischargemetabolic_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargemetabolic_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargemetabolic_creationtime() OWNER TO postgres;

--
-- TOC entry 733 (class 1255 OID 70475)
-- Name: dischargemetabolic_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargemetabolic_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargemetabolic_modificationtime() OWNER TO postgres;

--
-- TOC entry 734 (class 1255 OID 70476)
-- Name: dischargenicucourse_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargenicucourse_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargenicucourse_creationtime() OWNER TO postgres;

--
-- TOC entry 735 (class 1255 OID 70477)
-- Name: dischargenicucourse_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargenicucourse_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargenicucourse_modificationtime() OWNER TO postgres;

--
-- TOC entry 736 (class 1255 OID 70478)
-- Name: dischargeoutcome_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeoutcome_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeoutcome_creationtime() OWNER TO postgres;

--
-- TOC entry 737 (class 1255 OID 70479)
-- Name: dischargeoutcome_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeoutcome_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeoutcome_modificationtime() OWNER TO postgres;

--
-- TOC entry 738 (class 1255 OID 70480)
-- Name: dischargepatient_detail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargepatient_detail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargepatient_detail_creationtime() OWNER TO postgres;

--
-- TOC entry 739 (class 1255 OID 70481)
-- Name: dischargepatient_detail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargepatient_detail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargepatient_detail_modificationtime() OWNER TO postgres;

--
-- TOC entry 740 (class 1255 OID 70482)
-- Name: dischargephototherapy_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargephototherapy_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargephototherapy_creationtime() OWNER TO postgres;

--
-- TOC entry 741 (class 1255 OID 70483)
-- Name: dischargephototherapy_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargephototherapy_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargephototherapy_modificationtime() OWNER TO postgres;

--
-- TOC entry 742 (class 1255 OID 70484)
-- Name: dischargesummary_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargesummary_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargesummary_creationtime() OWNER TO postgres;

--
-- TOC entry 743 (class 1255 OID 70485)
-- Name: dischargesummary_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargesummary_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargesummary_modificationtime() OWNER TO postgres;

--
-- TOC entry 744 (class 1255 OID 70486)
-- Name: dischargetreatment_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargetreatment_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargetreatment_creationtime() OWNER TO postgres;

--
-- TOC entry 745 (class 1255 OID 70487)
-- Name: dischargetreatment_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargetreatment_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargetreatment_modificationtime() OWNER TO postgres;

--
-- TOC entry 746 (class 1255 OID 70488)
-- Name: dischargeventcourse_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeventcourse_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeventcourse_creationtime() OWNER TO postgres;

--
-- TOC entry 747 (class 1255 OID 70489)
-- Name: dischargeventcourse_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeventcourse_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeventcourse_modificationtime() OWNER TO postgres;

--
-- TOC entry 748 (class 1255 OID 70490)
-- Name: dischargeventilation_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeventilation_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeventilation_creationtime() OWNER TO postgres;

--
-- TOC entry 749 (class 1255 OID 70491)
-- Name: dischargeventilation_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION dischargeventilation_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.dischargeventilation_modificationtime() OWNER TO postgres;

--
-- TOC entry 750 (class 1255 OID 70492)
-- Name: doctemplate_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION doctemplate_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.doctemplate_creationtime() OWNER TO postgres;

--
-- TOC entry 751 (class 1255 OID 70493)
-- Name: doctemplate_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION doctemplate_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.doctemplate_modificationtime() OWNER TO postgres;

--
-- TOC entry 752 (class 1255 OID 70494)
-- Name: doctornotes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION doctornotes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.doctornotes_creationtime() OWNER TO postgres;

--
-- TOC entry 753 (class 1255 OID 70495)
-- Name: doctornotes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION doctornotes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.doctornotes_modificationtime() OWNER TO postgres;

--
-- TOC entry 754 (class 1255 OID 70496)
-- Name: downesscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION downesscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.downesscore_creationtime() OWNER TO postgres;

--
-- TOC entry 755 (class 1255 OID 70497)
-- Name: downesscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION downesscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.downesscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 756 (class 1255 OID 70498)
-- Name: et_intubation_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION et_intubation_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.et_intubation_creationtime() OWNER TO postgres;

--
-- TOC entry 757 (class 1255 OID 70499)
-- Name: et_intubation_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION et_intubation_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.et_intubation_modificationtime() OWNER TO postgres;

--
-- TOC entry 758 (class 1255 OID 70500)
-- Name: exceptionlist_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION exceptionlist_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.exceptionlist_creationtime() OWNER TO postgres;

--
-- TOC entry 759 (class 1255 OID 70501)
-- Name: exceptionlist_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION exceptionlist_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.exceptionlist_modificationtime() OWNER TO postgres;

--
-- TOC entry 760 (class 1255 OID 70502)
-- Name: feedgrowth_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION feedgrowth_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.feedgrowth_creationtime() OWNER TO postgres;

--
-- TOC entry 761 (class 1255 OID 70503)
-- Name: feedgrowth_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION feedgrowth_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.feedgrowth_modificationtime() OWNER TO postgres;

--
-- TOC entry 762 (class 1255 OID 70504)
-- Name: followup_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION followup_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.followup_creationtime() OWNER TO postgres;

--
-- TOC entry 763 (class 1255 OID 70505)
-- Name: followup_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION followup_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.followup_modificationtime() OWNER TO postgres;

--
-- TOC entry 764 (class 1255 OID 70506)
-- Name: gen_phy_exam_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION gen_phy_exam_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.gen_phy_exam_creationtime() OWNER TO postgres;

--
-- TOC entry 765 (class 1255 OID 70507)
-- Name: gen_phy_exam_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION gen_phy_exam_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.gen_phy_exam_modificationtime() OWNER TO postgres;

--
-- TOC entry 766 (class 1255 OID 70508)
-- Name: headtotoe_examination_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION headtotoe_examination_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.headtotoe_examination_creationtime() OWNER TO postgres;

--
-- TOC entry 767 (class 1255 OID 70509)
-- Name: headtotoe_examination_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION headtotoe_examination_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.headtotoe_examination_modificationtime() OWNER TO postgres;

--
-- TOC entry 768 (class 1255 OID 70510)
-- Name: hiescore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hiescore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hiescore_creationtime() OWNER TO postgres;

--
-- TOC entry 769 (class 1255 OID 70511)
-- Name: hiescore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hiescore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hiescore_modificationtime() OWNER TO postgres;

--
-- TOC entry 770 (class 1255 OID 70512)
-- Name: hypercalcemia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypercalcemia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypercalcemia_creationtime() OWNER TO postgres;

--
-- TOC entry 771 (class 1255 OID 70513)
-- Name: hypercalcemia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypercalcemia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypercalcemia_modificationtime() OWNER TO postgres;

--
-- TOC entry 772 (class 1255 OID 70514)
-- Name: hyperglycemia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyperglycemia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyperglycemia_creationtime() OWNER TO postgres;

--
-- TOC entry 773 (class 1255 OID 70515)
-- Name: hyperglycemia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyperglycemia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyperglycemia_modificationtime() OWNER TO postgres;

--
-- TOC entry 774 (class 1255 OID 70516)
-- Name: hyperkalemia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyperkalemia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyperkalemia_creationtime() OWNER TO postgres;

--
-- TOC entry 775 (class 1255 OID 70517)
-- Name: hyperkalemia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyperkalemia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyperkalemia_modificationtime() OWNER TO postgres;

--
-- TOC entry 776 (class 1255 OID 70518)
-- Name: hypernatremia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypernatremia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypernatremia_creationtime() OWNER TO postgres;

--
-- TOC entry 777 (class 1255 OID 70519)
-- Name: hypernatremia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypernatremia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypernatremia_modificationtime() OWNER TO postgres;

--
-- TOC entry 778 (class 1255 OID 70520)
-- Name: hypocalcemia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypocalcemia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypocalcemia_creationtime() OWNER TO postgres;

--
-- TOC entry 779 (class 1255 OID 70521)
-- Name: hypocalcemia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypocalcemia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypocalcemia_modificationtime() OWNER TO postgres;

--
-- TOC entry 780 (class 1255 OID 70522)
-- Name: hypokalemia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypokalemia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypokalemia_creationtime() OWNER TO postgres;

--
-- TOC entry 781 (class 1255 OID 70523)
-- Name: hypokalemia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hypokalemia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hypokalemia_modificationtime() OWNER TO postgres;

--
-- TOC entry 782 (class 1255 OID 70524)
-- Name: hyponatremia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyponatremia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyponatremia_creationtime() OWNER TO postgres;

--
-- TOC entry 783 (class 1255 OID 70525)
-- Name: hyponatremia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION hyponatremia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.hyponatremia_modificationtime() OWNER TO postgres;

--
-- TOC entry 784 (class 1255 OID 70526)
-- Name: iem_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION iem_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.iem_creationtime() OWNER TO postgres;

--
-- TOC entry 785 (class 1255 OID 70527)
-- Name: iem_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION iem_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.iem_modificationtime() OWNER TO postgres;

--
-- TOC entry 786 (class 1255 OID 70528)
-- Name: inicu_bbox_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION inicu_bbox_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.inicu_bbox_creationtime() OWNER TO postgres;

--
-- TOC entry 787 (class 1255 OID 70529)
-- Name: inicu_bbox_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION inicu_bbox_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.inicu_bbox_modificationtime() OWNER TO postgres;

--
-- TOC entry 788 (class 1255 OID 70530)
-- Name: iniculogs_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION iniculogs_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.iniculogs_creationtime() OWNER TO postgres;

--
-- TOC entry 789 (class 1255 OID 70531)
-- Name: investigationorder_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION investigationorder_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.investigationorder_creationtime() OWNER TO postgres;

--
-- TOC entry 790 (class 1255 OID 70532)
-- Name: investigationorder_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION investigationorder_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.investigationorder_modificationtime() OWNER TO postgres;

--
-- TOC entry 791 (class 1255 OID 70533)
-- Name: ivhscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ivhscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ivhscore_creationtime() OWNER TO postgres;

--
-- TOC entry 792 (class 1255 OID 70534)
-- Name: ivhscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ivhscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ivhscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 793 (class 1255 OID 70535)
-- Name: levenescore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION levenescore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.levenescore_creationtime() OWNER TO postgres;

--
-- TOC entry 794 (class 1255 OID 70536)
-- Name: levenescore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION levenescore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.levenescore_modificationtime() OWNER TO postgres;

--
-- TOC entry 795 (class 1255 OID 70537)
-- Name: logindetails_logintime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION logindetails_logintime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.logintime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.logindetails_logintime() OWNER TO postgres;

--
-- TOC entry 796 (class 1255 OID 70538)
-- Name: lumbar_puncture_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION lumbar_puncture_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.lumbar_puncture_creationtime() OWNER TO postgres;

--
-- TOC entry 797 (class 1255 OID 70539)
-- Name: lumbar_puncture_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION lumbar_puncture_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.lumbar_puncture_modificationtime() OWNER TO postgres;

--
-- TOC entry 798 (class 1255 OID 70540)
-- Name: managerole_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION managerole_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.managerole_creationtime() OWNER TO postgres;

--
-- TOC entry 799 (class 1255 OID 70541)
-- Name: managerole_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION managerole_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.managerole_modificationtime() OWNER TO postgres;

--
-- TOC entry 800 (class 1255 OID 70542)
-- Name: master_address_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION master_address_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.master_address_creationtime() OWNER TO postgres;

--
-- TOC entry 801 (class 1255 OID 70543)
-- Name: master_address_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION master_address_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.master_address_modificationtime() OWNER TO postgres;

--
-- TOC entry 802 (class 1255 OID 70544)
-- Name: maternalpasthistory_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION maternalpasthistory_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.maternalpasthistory_creationtime() OWNER TO postgres;

--
-- TOC entry 803 (class 1255 OID 70545)
-- Name: maternalpasthistory_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION maternalpasthistory_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.maternalpasthistory_modificationtime() OWNER TO postgres;

--
-- TOC entry 804 (class 1255 OID 70546)
-- Name: med_administration_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION med_administration_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.med_administration_creationtime() OWNER TO postgres;

--
-- TOC entry 805 (class 1255 OID 70547)
-- Name: med_administration_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION med_administration_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.med_administration_modificationtime() OWNER TO postgres;

--
-- TOC entry 806 (class 1255 OID 70548)
-- Name: medicine_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION medicine_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.medicine_creationtime() OWNER TO postgres;

--
-- TOC entry 807 (class 1255 OID 70549)
-- Name: medicine_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION medicine_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.medicine_modificationtime() OWNER TO postgres;

--
-- TOC entry 808 (class 1255 OID 70550)
-- Name: misc_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION misc_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.misc_creationtime() OWNER TO postgres;

--
-- TOC entry 809 (class 1255 OID 70551)
-- Name: misc_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION misc_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.misc_modificationtime() OWNER TO postgres;

--
-- TOC entry 810 (class 1255 OID 70552)
-- Name: notifications_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION notifications_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.notifications_creationtime() OWNER TO postgres;

--
-- TOC entry 811 (class 1255 OID 70553)
-- Name: notifications_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION notifications_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.notifications_modificationtime() OWNER TO postgres;

--
-- TOC entry 812 (class 1255 OID 70554)
-- Name: nursing_bloodgas_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bloodgas_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bloodgas_creationtime() OWNER TO postgres;

--
-- TOC entry 813 (class 1255 OID 70555)
-- Name: nursing_bloodgas_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bloodgas_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bloodgas_modificationtime() OWNER TO postgres;

--
-- TOC entry 814 (class 1255 OID 70556)
-- Name: nursing_bloodproducts_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bloodproducts_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bloodproducts_creationtime() OWNER TO postgres;

--
-- TOC entry 815 (class 1255 OID 70557)
-- Name: nursing_bloodproducts_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bloodproducts_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bloodproducts_modificationtime() OWNER TO postgres;

--
-- TOC entry 816 (class 1255 OID 70558)
-- Name: nursing_bolus_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bolus_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bolus_creationtime() OWNER TO postgres;

--
-- TOC entry 817 (class 1255 OID 70559)
-- Name: nursing_bolus_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_bolus_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_bolus_modificationtime() OWNER TO postgres;

--
-- TOC entry 818 (class 1255 OID 70560)
-- Name: nursing_catheters_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_catheters_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_catheters_creationtime() OWNER TO postgres;

--
-- TOC entry 819 (class 1255 OID 70561)
-- Name: nursing_catheters_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_catheters_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_catheters_modificationtime() OWNER TO postgres;

--
-- TOC entry 820 (class 1255 OID 70562)
-- Name: nursing_dailyassesment_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_dailyassesment_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_dailyassesment_creationtime() OWNER TO postgres;

--
-- TOC entry 821 (class 1255 OID 70563)
-- Name: nursing_dailyassesment_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_dailyassesment_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_dailyassesment_modificationtime() OWNER TO postgres;

--
-- TOC entry 822 (class 1255 OID 70564)
-- Name: nursing_drain_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_drain_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_drain_creationtime() OWNER TO postgres;

--
-- TOC entry 823 (class 1255 OID 70565)
-- Name: nursing_drain_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_drain_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
          NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_drain_modificationtime() OWNER TO postgres;

--
-- TOC entry 824 (class 1255 OID 70566)
-- Name: nursing_episode_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_episode_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_episode_creationtime() OWNER TO postgres;

--
-- TOC entry 825 (class 1255 OID 70567)
-- Name: nursing_episode_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_episode_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_episode_modificationtime() OWNER TO postgres;

--
-- TOC entry 826 (class 1255 OID 70568)
-- Name: nursing_intake_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_intake_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_intake_creationtime() OWNER TO postgres;

--
-- TOC entry 827 (class 1255 OID 70569)
-- Name: nursing_intake_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_intake_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_intake_modificationtime() OWNER TO postgres;

--
-- TOC entry 828 (class 1255 OID 70570)
-- Name: nursing_misc_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_misc_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_misc_creationtime() OWNER TO postgres;

--
-- TOC entry 829 (class 1255 OID 70571)
-- Name: nursing_misc_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_misc_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_misc_modificationtime() OWNER TO postgres;

--
-- TOC entry 830 (class 1255 OID 70572)
-- Name: nursing_neurovitals_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_neurovitals_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_neurovitals_creationtime() OWNER TO postgres;

--
-- TOC entry 831 (class 1255 OID 70573)
-- Name: nursing_neurovitals_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_neurovitals_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_neurovitals_modificationtime() OWNER TO postgres;

--
-- TOC entry 832 (class 1255 OID 70574)
-- Name: nursing_output_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_output_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_output_creationtime() OWNER TO postgres;

--
-- TOC entry 833 (class 1255 OID 70575)
-- Name: nursing_output_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_output_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
         NEW.modificationtime := current_timestamp;
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_output_modificationtime() OWNER TO postgres;

--
-- TOC entry 834 (class 1255 OID 70576)
-- Name: nursing_ventilaor_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_ventilaor_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_ventilaor_creationtime() OWNER TO postgres;

--
-- TOC entry 835 (class 1255 OID 70577)
-- Name: nursing_ventilaor_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_ventilaor_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_ventilaor_modificationtime() OWNER TO postgres;

--
-- TOC entry 836 (class 1255 OID 70578)
-- Name: nursing_vitalparameters_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_vitalparameters_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_vitalparameters_creationtime() OWNER TO postgres;

--
-- TOC entry 837 (class 1255 OID 70579)
-- Name: nursing_vitalparameters_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursing_vitalparameters_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursing_vitalparameters_modificationtime() OWNER TO postgres;

--
-- TOC entry 838 (class 1255 OID 70580)
-- Name: nursingnotes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingnotes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursingnotes_creationtime() OWNER TO postgres;

--
-- TOC entry 839 (class 1255 OID 70581)
-- Name: nursingnotes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingnotes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursingnotes_modificationtime() OWNER TO postgres;

--
-- TOC entry 840 (class 1255 OID 70582)
-- Name: nursingorder_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorder_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursingorder_creationtime() OWNER TO postgres;

--
-- TOC entry 841 (class 1255 OID 70583)
-- Name: nursingorder_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorder_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursingorder_modificationtime() OWNER TO postgres;

--
-- TOC entry 842 (class 1255 OID 70584)
-- Name: nursingorderapnea_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderapnea_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '    BEGIN        NEW.creationtime := current_timestamp;        
NEW.modificationtime := current_timestamp;        RETURN NEW;    END;
';


ALTER FUNCTION kdah.nursingorderapnea_creationtime() OWNER TO postgres;

--
-- TOC entry 843 (class 1255 OID 70585)
-- Name: nursingorderapnea_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderapnea_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '    BEGIN        NEW.modificationtime := current_timestamp;        RETURN NEW;    END;
';


ALTER FUNCTION kdah.nursingorderapnea_modificationtime() OWNER TO postgres;

--
-- TOC entry 844 (class 1255 OID 70586)
-- Name: nursingorderjaundice_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderjaundice_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.nursingorderjaundice_modificationtime() OWNER TO postgres;

--
-- TOC entry 845 (class 1255 OID 70587)
-- Name: nursingorderrds_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderrds_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.creationtime := current_timestamp;

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.nursingorderrds_creationtime() OWNER TO postgres;

--
-- TOC entry 846 (class 1255 OID 70588)
-- Name: nursingorderrds_medicine_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderrds_medicine_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.creationtime := current_timestamp;

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.nursingorderrds_medicine_creationtime() OWNER TO postgres;

--
-- TOC entry 847 (class 1255 OID 70589)
-- Name: nursingorderrds_medicine_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderrds_medicine_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.nursingorderrds_medicine_modificationtime() OWNER TO postgres;

--
-- TOC entry 848 (class 1255 OID 70590)
-- Name: nursingorderrds_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION nursingorderrds_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.nursingorderrds_modificationtime() OWNER TO postgres;

--
-- TOC entry 849 (class 1255 OID 70591)
-- Name: oralfeed_detail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION oralfeed_detail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.entrydatetime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.oralfeed_detail_creationtime() OWNER TO postgres;

--
-- TOC entry 850 (class 1255 OID 70592)
-- Name: oralfeed_detail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION oralfeed_detail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.oralfeed_detail_modificationtime() OWNER TO postgres;

--
-- TOC entry 851 (class 1255 OID 70593)
-- Name: outborn_medicine_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION outborn_medicine_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.outborn_medicine_creationtime() OWNER TO postgres;

--
-- TOC entry 852 (class 1255 OID 70594)
-- Name: outborn_medicine_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION outborn_medicine_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.outborn_medicine_modificationtime() OWNER TO postgres;

--
-- TOC entry 853 (class 1255 OID 70595)
-- Name: painscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION painscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.painscore_creationtime() OWNER TO postgres;

--
-- TOC entry 854 (class 1255 OID 70596)
-- Name: painscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION painscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.painscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 855 (class 1255 OID 70597)
-- Name: papilescore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION papilescore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.papilescore_creationtime() OWNER TO postgres;

--
-- TOC entry 856 (class 1255 OID 70598)
-- Name: papilescore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION papilescore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.papilescore_modificationtime() OWNER TO postgres;

--
-- TOC entry 857 (class 1255 OID 70599)
-- Name: parentdetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION parentdetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.parentdetail_creationtime() OWNER TO postgres;

--
-- TOC entry 719 (class 1255 OID 70600)
-- Name: parentdetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION parentdetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.parentdetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 858 (class 1255 OID 70601)
-- Name: parentdetail_motherid(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION parentdetail_motherid() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.motherid := NEW.uhid||''_''||NEW.parentdetailid;
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.parentdetail_motherid() OWNER TO postgres;

--
-- TOC entry 859 (class 1255 OID 70602)
-- Name: patient_ventilator_data_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION patient_ventilator_data_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.patient_ventilator_data_modificationtime() OWNER TO postgres;

--
-- TOC entry 860 (class 1255 OID 70603)
-- Name: patientdevicedetail_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION patientdevicedetail_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.patientdevicedetail_creationtime() OWNER TO postgres;

--
-- TOC entry 861 (class 1255 OID 70604)
-- Name: patientdevicedetail_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION patientdevicedetail_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.patientdevicedetail_modificationtime() OWNER TO postgres;

--
-- TOC entry 862 (class 1255 OID 70605)
-- Name: peripheral_cannula_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION peripheral_cannula_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.peripheral_cannula_creationtime() OWNER TO postgres;

--
-- TOC entry 863 (class 1255 OID 70606)
-- Name: peripheral_cannula_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION peripheral_cannula_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.peripheral_cannula_modificationtime() OWNER TO postgres;

--
-- TOC entry 864 (class 1255 OID 70607)
-- Name: pref_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION pref_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.pref_creationtime() OWNER TO postgres;

--
-- TOC entry 865 (class 1255 OID 70608)
-- Name: pref_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION pref_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
         NEW.modificationtime := current_timestamp;
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.pref_modificationtime() OWNER TO postgres;

--
-- TOC entry 866 (class 1255 OID 70609)
-- Name: procedure_chesttube_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION procedure_chesttube_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.procedure_chesttube_creationtime() OWNER TO postgres;

--
-- TOC entry 867 (class 1255 OID 70610)
-- Name: procedure_chesttube_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION procedure_chesttube_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.procedure_chesttube_modificationtime() OWNER TO postgres;

--
-- TOC entry 868 (class 1255 OID 70611)
-- Name: procedure_other_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION procedure_other_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.procedure_other_creationtime() OWNER TO postgres;

--
-- TOC entry 869 (class 1255 OID 70612)
-- Name: procedure_other_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION procedure_other_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.procedure_other_modificationtime() OWNER TO postgres;

--
-- TOC entry 870 (class 1255 OID 70613)
-- Name: pupilreactivity_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION pupilreactivity_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.pupilreactivity_creationtime() OWNER TO postgres;

--
-- TOC entry 871 (class 1255 OID 70614)
-- Name: pupilreactivity_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION pupilreactivity_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.pupilreactivity_modificationtime() OWNER TO postgres;

--
-- TOC entry 872 (class 1255 OID 70615)
-- Name: readmithistory_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION readmithistory_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.readmithistory_creationtime() OWNER TO postgres;

--
-- TOC entry 873 (class 1255 OID 70616)
-- Name: readmithistory_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION readmithistory_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.readmithistory_modificationtime() OWNER TO postgres;

--
-- TOC entry 874 (class 1255 OID 70617)
-- Name: reason_admission_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION reason_admission_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.reason_admission_creationtime() OWNER TO postgres;

--
-- TOC entry 875 (class 1255 OID 70618)
-- Name: reason_admission_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION reason_admission_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.reason_admission_modificationtime() OWNER TO postgres;

--
-- TOC entry 876 (class 1255 OID 70619)
-- Name: ref_inicu_bbox_boards_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ref_inicu_bbox_boards_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ref_inicu_bbox_boards_creationtime() OWNER TO postgres;

--
-- TOC entry 877 (class 1255 OID 70620)
-- Name: ref_inicu_bbox_boards_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION ref_inicu_bbox_boards_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.ref_inicu_bbox_boards_modificationtime() OWNER TO postgres;

--
-- TOC entry 878 (class 1255 OID 70621)
-- Name: refbed_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION refbed_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        NEW.creationtimestamp := current_timestamp;        
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.refbed_creationtime() OWNER TO postgres;

--
-- TOC entry 879 (class 1255 OID 70622)
-- Name: refbed_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION refbed_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.refbed_modificationtime() OWNER TO postgres;

--
-- TOC entry 880 (class 1255 OID 70623)
-- Name: refbed_removaltimestamp(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION refbed_removaltimestamp() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.removaltimestamp := current_timestamp;        
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.refbed_removaltimestamp() OWNER TO postgres;

--
-- TOC entry 881 (class 1255 OID 70624)
-- Name: refroom_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION refroom_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.refroom_creationtime() OWNER TO postgres;

--
-- TOC entry 882 (class 1255 OID 70625)
-- Name: refroom_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION refroom_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.refroom_modificationtime() OWNER TO postgres;

--
-- TOC entry 883 (class 1255 OID 70626)
-- Name: renal_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION renal_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.renal_creationtime() OWNER TO postgres;

--
-- TOC entry 884 (class 1255 OID 70627)
-- Name: renal_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION renal_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.renal_modificationtime() OWNER TO postgres;

--
-- TOC entry 885 (class 1255 OID 70628)
-- Name: respbpd_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respbpd_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respbpd_creationtime() OWNER TO postgres;

--
-- TOC entry 886 (class 1255 OID 70629)
-- Name: respbpd_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respbpd_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respbpd_modificationtime() OWNER TO postgres;

--
-- TOC entry 887 (class 1255 OID 70630)
-- Name: respothers_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respothers_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respothers_creationtime() OWNER TO postgres;

--
-- TOC entry 888 (class 1255 OID 70631)
-- Name: respothers_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respothers_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respothers_modificationtime() OWNER TO postgres;

--
-- TOC entry 889 (class 1255 OID 70632)
-- Name: resppneumothorax_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resppneumothorax_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resppneumothorax_creationtime() OWNER TO postgres;

--
-- TOC entry 890 (class 1255 OID 70633)
-- Name: resppneumothorax_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resppneumothorax_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resppneumothorax_modificationtime() OWNER TO postgres;

--
-- TOC entry 891 (class 1255 OID 70634)
-- Name: resppphn_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resppphn_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resppphn_creationtime() OWNER TO postgres;

--
-- TOC entry 892 (class 1255 OID 70635)
-- Name: resppphn_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resppphn_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resppphn_modificationtime() OWNER TO postgres;

--
-- TOC entry 893 (class 1255 OID 70636)
-- Name: resprds_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resprds_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resprds_creationtime() OWNER TO postgres;

--
-- TOC entry 894 (class 1255 OID 70637)
-- Name: resprds_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION resprds_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.resprds_modificationtime() OWNER TO postgres;

--
-- TOC entry 895 (class 1255 OID 70638)
-- Name: respsupport_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respsupport_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respsupport_creationtime() OWNER TO postgres;

--
-- TOC entry 896 (class 1255 OID 70639)
-- Name: respsupport_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respsupport_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respsupport_modificationtime() OWNER TO postgres;

--
-- TOC entry 897 (class 1255 OID 70640)
-- Name: respsystem_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respsystem_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respsystem_creationtime() OWNER TO postgres;

--
-- TOC entry 898 (class 1255 OID 70641)
-- Name: respsystem_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION respsystem_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.respsystem_modificationtime() OWNER TO postgres;

--
-- TOC entry 899 (class 1255 OID 70642)
-- Name: sa_resp_apnea_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sa_resp_apnea_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.creationtime := current_timestamp;

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.sa_resp_apnea_creationtime() OWNER TO postgres;

--
-- TOC entry 900 (class 1255 OID 70643)
-- Name: sa_resp_apnea_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sa_resp_apnea_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.modificationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.sa_resp_apnea_modificationtime() OWNER TO postgres;

--
-- TOC entry 901 (class 1255 OID 70644)
-- Name: sacns_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacns_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacns_creationtime() OWNER TO postgres;

--
-- TOC entry 902 (class 1255 OID 70645)
-- Name: sacns_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacns_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacns_modificationtime() OWNER TO postgres;

--
-- TOC entry 903 (class 1255 OID 70646)
-- Name: sacnsasphyxia_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsasphyxia_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsasphyxia_creationtime() OWNER TO postgres;

--
-- TOC entry 904 (class 1255 OID 70647)
-- Name: sacnsasphyxia_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsasphyxia_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsasphyxia_modificationtime() OWNER TO postgres;

--
-- TOC entry 905 (class 1255 OID 70648)
-- Name: sacnsivh_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsivh_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsivh_creationtime() OWNER TO postgres;

--
-- TOC entry 906 (class 1255 OID 70649)
-- Name: sacnsivh_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsivh_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsivh_modificationtime() OWNER TO postgres;

--
-- TOC entry 907 (class 1255 OID 70650)
-- Name: sacnsseizures_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsseizures_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsseizures_creationtime() OWNER TO postgres;

--
-- TOC entry 908 (class 1255 OID 70651)
-- Name: sacnsseizures_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sacnsseizures_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sacnsseizures_modificationtime() OWNER TO postgres;

--
-- TOC entry 909 (class 1255 OID 70652)
-- Name: sajaundice_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sajaundice_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sajaundice_creationtime() OWNER TO postgres;

--
-- TOC entry 910 (class 1255 OID 70653)
-- Name: sajaundice_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sajaundice_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sajaundice_modificationtime() OWNER TO postgres;

--
-- TOC entry 911 (class 1255 OID 70654)
-- Name: sametabolic_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sametabolic_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sametabolic_creationtime() OWNER TO postgres;

--
-- TOC entry 912 (class 1255 OID 70655)
-- Name: sametabolic_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sametabolic_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sametabolic_modificationtime() OWNER TO postgres;

--
-- TOC entry 913 (class 1255 OID 70656)
-- Name: sarnatscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sarnatscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sarnatscore_creationtime() OWNER TO postgres;

--
-- TOC entry 914 (class 1255 OID 70657)
-- Name: sarnatscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sarnatscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sarnatscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 915 (class 1255 OID 70658)
-- Name: sepsis_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sepsis_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sepsis_creationtime() OWNER TO postgres;

--
-- TOC entry 916 (class 1255 OID 70659)
-- Name: sepsis_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sepsis_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sepsis_modificationtime() OWNER TO postgres;

--
-- TOC entry 917 (class 1255 OID 70660)
-- Name: sepsisscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sepsisscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sepsisscore_creationtime() OWNER TO postgres;

--
-- TOC entry 918 (class 1255 OID 70661)
-- Name: sepsisscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION sepsisscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.sepsisscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 919 (class 1255 OID 70662)
-- Name: setting_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION setting_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.setting_creationtime() OWNER TO postgres;

--
-- TOC entry 920 (class 1255 OID 70663)
-- Name: setting_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION setting_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
         NEW.modificationtime := current_timestamp;
         RETURN NEW;
    END;
';


ALTER FUNCTION kdah.setting_modificationtime() OWNER TO postgres;

--
-- TOC entry 921 (class 1255 OID 70664)
-- Name: silvermanscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION silvermanscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.silvermanscore_creationtime() OWNER TO postgres;

--
-- TOC entry 922 (class 1255 OID 70665)
-- Name: silvermanscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION silvermanscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.silvermanscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 923 (class 1255 OID 70666)
-- Name: stable_notes_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION stable_notes_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
     BEGIN
  NEW.creationtime := current_timestamp;
  NEW.modificationtime := current_timestamp;
  RETURN NEW;
     END;
 ';


ALTER FUNCTION kdah.stable_notes_creationtime() OWNER TO postgres;

--
-- TOC entry 924 (class 1255 OID 70667)
-- Name: stable_notes_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION stable_notes_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
     BEGIN
  NEW.modificationtime := current_timestamp;
  RETURN NEW;
     END;
 ';


ALTER FUNCTION kdah.stable_notes_modificationtime() OWNER TO postgres;

--
-- TOC entry 925 (class 1255 OID 70668)
-- Name: systemic_exam_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION systemic_exam_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.systemic_exam_creationtime() OWNER TO postgres;

--
-- TOC entry 926 (class 1255 OID 70669)
-- Name: systemic_exam_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION systemic_exam_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.systemic_exam_modificationtime() OWNER TO postgres;

--
-- TOC entry 927 (class 1255 OID 70670)
-- Name: test_result_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION test_result_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '

    BEGIN

        NEW.creationtime := current_timestamp;

        RETURN NEW;

    END;

';


ALTER FUNCTION kdah.test_result_creationtime() OWNER TO postgres;

--
-- TOC entry 928 (class 1255 OID 70671)
-- Name: test_result_inicu_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION test_result_inicu_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.test_result_inicu_creationtime() OWNER TO postgres;

--
-- TOC entry 929 (class 1255 OID 70672)
-- Name: thompsonscore_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION thompsonscore_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.thompsonscore_creationtime() OWNER TO postgres;

--
-- TOC entry 930 (class 1255 OID 70673)
-- Name: thompsonscore_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION thompsonscore_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.thompsonscore_modificationtime() OWNER TO postgres;

--
-- TOC entry 931 (class 1255 OID 70674)
-- Name: uhidnotification_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION uhidnotification_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.uhidnotification_creationtime() OWNER TO postgres;

--
-- TOC entry 932 (class 1255 OID 70675)
-- Name: uhidnotification_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION uhidnotification_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.uhidnotification_modificationtime() OWNER TO postgres;

--
-- TOC entry 933 (class 1255 OID 70676)
-- Name: user_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION user_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.user_creationtime() OWNER TO postgres;

--
-- TOC entry 934 (class 1255 OID 70677)
-- Name: user_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION user_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.user_modificationtime() OWNER TO postgres;

--
-- TOC entry 935 (class 1255 OID 70678)
-- Name: usergroups_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION usergroups_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.usergroups_creationtime() OWNER TO postgres;

--
-- TOC entry 936 (class 1255 OID 70679)
-- Name: usergroups_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION usergroups_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.usergroups_modificationtime() OWNER TO postgres;

--
-- TOC entry 937 (class 1255 OID 70680)
-- Name: userrole_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION userrole_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.userrole_creationtime() OWNER TO postgres;

--
-- TOC entry 938 (class 1255 OID 70681)
-- Name: userrole_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION userrole_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.userrole_modificationtime() OWNER TO postgres;

--
-- TOC entry 939 (class 1255 OID 70682)
-- Name: vap_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION vap_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.vap_creationtime() OWNER TO postgres;

--
-- TOC entry 940 (class 1255 OID 70683)
-- Name: vap_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION vap_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.vap_modificationtime() OWNER TO postgres;

--
-- TOC entry 941 (class 1255 OID 70684)
-- Name: visit_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION visit_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.visit_creationtime() OWNER TO postgres;

--
-- TOC entry 942 (class 1255 OID 70685)
-- Name: visit_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION visit_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.visit_modificationtime() OWNER TO postgres;

--
-- TOC entry 943 (class 1255 OID 70686)
-- Name: vtap_creationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION vtap_creationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.creationtime := current_timestamp;
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.vtap_creationtime() OWNER TO postgres;

--
-- TOC entry 944 (class 1255 OID 70687)
-- Name: vtap_modificationtime(); Type: FUNCTION; Schema: kdah; Owner: postgres
--

CREATE FUNCTION vtap_modificationtime() RETURNS trigger
    LANGUAGE plpgsql
    AS '
    BEGIN
        NEW.modificationtime := current_timestamp;
        RETURN NEW;
    END;
';


ALTER FUNCTION kdah.vtap_modificationtime() OWNER TO postgres;

