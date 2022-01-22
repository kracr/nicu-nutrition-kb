-- Table: kdah.custom_reports

-- DROP TABLE kdah.custom_reports;

CREATE TABLE kdah.custom_reports
(
    customreportsid bigint NOT NULL DEFAULT nextval('kdah.custom_reports_seq'::regclass),
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

ALTER TABLE kdah.custom_reports
    OWNER to postgres;

-- Trigger: set_creation_time

-- DROP TRIGGER set_creation_time ON kdah.custom_reports;

CREATE TRIGGER set_creation_time
    BEFORE INSERT
    ON kdah.custom_reports
    FOR EACH ROW
    EXECUTE PROCEDURE kdah.customreports_creationtime();

-- Trigger: set_modification_time

-- DROP TRIGGER set_modification_time ON kdah.custom_reports;

CREATE TRIGGER set_modification_time
    BEFORE INSERT OR UPDATE
    ON kdah.custom_reports
    FOR EACH ROW
    EXECUTE PROCEDURE kdah.customreports_modificationtime();


-- SEQUENCE: kdah.custom_reports_seq

-- DROP SEQUENCE kdah.custom_reports_seq;

CREATE SEQUENCE kdah.custom_reports_seq
    INCREMENT 1
    START 14
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE kdah.custom_reports_seq
    OWNER TO postgres;