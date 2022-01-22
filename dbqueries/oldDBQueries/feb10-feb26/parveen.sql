


-- SEQUENCE: apollo.custom_reports_seq

-- DROP SEQUENCE apollo.custom_reports_seq;

CREATE SEQUENCE apollo.custom_reports_seq
    INCREMENT 1
    START 14
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE apollo.custom_reports_seq
    OWNER TO postgres;

CREATE FUNCTION apollo.customreports_creationtime()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
 NEW.creation_time := current_timestamp;
 NEW.modification_time := current_timestamp;
 RETURN NEW;
 END;
$BODY$;

ALTER FUNCTION apollo.customreports_creationtime()
    OWNER TO postgres;

--



-- FUNCTION: apollo.customreports_modificationtime()

-- DROP FUNCTION apollo.customreports_modificationtime();

CREATE FUNCTION apollo.customreports_modificationtime()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF
AS $BODY$
 BEGIN
 NEW.modification_time := current_timestamp;
 RETURN NEW;
 END;
$BODY$;

ALTER FUNCTION apollo.customreports_modificationtime()
    OWNER TO postgres;
---

-- Table: apollo.custom_reports

-- DROP TABLE apollo.custom_reports;

CREATE TABLE apollo.custom_reports
(
    customreportsid bigint NOT NULL DEFAULT nextval('apollo.custom_reports_seq'::regclass),
    cr_name character varying(200) COLLATE pg_catalog."default",
    created_by character varying(50) COLLATE pg_catalog."default",
    creation_time timestamp without time zone NOT NULL,
    input_filters text COLLATE pg_catalog."default",
    output_filters text COLLATE pg_catalog."default",
    modification_time timestamp without time zone,
    CONSTRAINT custom_reports_pkey PRIMARY KEY (customreportsid)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE apollo.custom_reports
    OWNER to postgres;

-- Trigger: set_creation_time

-- DROP TRIGGER set_creation_time ON apollo.custom_reports;

CREATE TRIGGER set_creation_time
    BEFORE INSERT
    ON apollo.custom_reports
    FOR EACH ROW
    EXECUTE PROCEDURE apollo.customreports_creationtime();

-- Trigger: set_modification_time

-- DROP TRIGGER set_modification_time ON apollo.custom_reports;

CREATE TRIGGER set_modification_time
    BEFORE INSERT OR UPDATE
    ON apollo.custom_reports
    FOR EACH ROW
    EXECUTE PROCEDURE apollo.customreports_modificationtime();




ALTER TABLE apollo.ref_nursing_output_parameters
    ADD COLUMN dashboard_configurations text COLLATE pg_catalog."default";

update apollo.ref_nursing_output_parameters set dashboard_configurations = ' {  "babyName":"true",      "bedNumber":"true",      "babyRoom":"true",      "admitDate":"true",      "dob":"true",      "dayOfLife":"true",      "gestationWeeks":"true",      "gestationDays":"true",      "weightAtAdmission":"true",      "lastDayWeight":"true",      "currentDayWeight":"true",      "difference":"true",      "screening":"true",      "feedDetail":"true",      "antibioticsName":"true",      "ventMode":"true",      "diagnosis":"true",      "nursesName":"true",      "otherInfo":"true",      "stoolCount":"true",      "fluidBalance":"true",      "enteralStr":"true",      "lipidTotal":"true",      "totalparenteralAdditivevolume":"true"}';