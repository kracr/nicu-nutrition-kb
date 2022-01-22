

ALTER TABLE apollo.ref_nursing_output_parameters
    ADD COLUMN dashboard_configurations text COLLATE pg_catalog."default";


update apollo.ref_nursing_output_parameters set dashboard_configurations = ' {  "babyName":"true",      "bedNumber":"true",      "babyRoom":"true",      "admitDate":"true",      "dob":"true",      "dayOfLife":"true",      "gestationWeeks":"true",      "gestationDays":"true",      "weightAtAdmission":"true",      "lastDayWeight":"true",      "currentDayWeight":"true",      "difference":"true",      "screening":"true",      "feedDetail":"true",      "antibioticsName":"true",      "ventMode":"true",      "diagnosis":"true",      "nursesName":"true",      "otherInfo":"true",      "stoolCount":"true",      "fluidBalance":"true",      "enteralStr":"true",      "lipidTotal":"true",      "totalparenteralAdditivevolume":"true"}';



alter table apollo.nursing_vitalparameters add column rbsstatus varchar(255);

ALTER TABLE apollo.ref_nursing_output_parameters
    ADD COLUMN rbsstatus boolean;

update apollo.ref_nursing_output_parameters set rbsstatus = true;


ALTER TABLE apollo.sa_jaundice
    ADD COLUMN iseditednotes boolean DEFAULT false;


ALTER TABLE apollo.sa_resp_rds
    ADD COLUMN iseditednotes boolean DEFAULT false;


ALTER TABLE apollo.sa_resp_apnea
    ADD COLUMN iseditednotes boolean DEFAULT false;


ALTER TABLE apollo.sa_resp_pphn
    ADD COLUMN iseditednotes boolean DEFAULT false;



ALTER TABLE apollo.sa_resp_pneumothorax
    ADD COLUMN iseditednotes boolean DEFAULT false;


ALTER TABLE apollo.procedure_other
    ADD COLUMN othername character varying(255) COLLATE pg_catalog."default";

ALTER TABLE apollo.procedure_other
    ADD COLUMN other_type character varying(50) COLLATE pg_catalog."default";


ALTER TABLE apollo.procedure_other
    ADD COLUMN other_value real;


