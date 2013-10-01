/* DROP TABLES */
DROP table PAIN                   ;
DROP table MOOD                   ;
DROP table CONTRACTION            ;
DROP table TEMPERATURE            ;
DROP table DIET                   ;
DROP table WEIGHT                 ;
DROP table EXERCISE               ;
DROP table BLOODPRESSURE          ;
DROP table EXERCISETOLERANCE      ;
DROP table OXSATURATION           ;
DROP table HEALTHFRIEND           ;
DROP table PROBLEMS               ;
DROP table HEALTHSUPPORTER        ;
DROP table ALERTS                 ;
DROP table PATIENT                ;

/*DROP sequences*/
DROP SEQUENCE Ox_seq                   ;
DROP SEQUENCE PAIN_seq                   ;
DROP SEQUENCE MOOD_seq                   ;
DROP SEQUENCE CONTRACTION_seq            ;
DROP SEQUENCE TEMPERATURE_seq            ;
DROP SEQUENCE DIET_seq                   ;
DROP SEQUENCE WEIGHT_seq                 ;
DROP SEQUENCE EXERCISE_seq               ;
DROP SEQUENCE BLOODPRESSURE_seq          ;
DROP SEQUENCE EXERCISETOLERANCE_seq      ;
DROP SEQUENCE OXSATURATION_seq           ;
DROP SEQUENCE HEALTHFRIEND_seq           ;
DROP SEQUENCE PROBLEMS_seq               ;
DROP SEQUENCE HEALTHSUPPORTER_seq        ;
DROP SEQUENCE ALERTS_SEQ                ;
DROP SEQUENCE PATIENTIDS_seq                ;



CREATE SEQUENCE Patientids_seq
START WITH 1
INCREMENT BY 1
CACHE 20;


CREATE TABLE Patient
(
patientid NUMBER(10),
dob DATE,
fname VARCHAR2(1024),
lname VARCHAR2(1024),
street VARCHAR2(1024),
city VARCHAR2(1024),
state VARCHAR2(2),
zip NUMBER(5),
gender VARCHAR2(1) CHECK (gender in ('M','F','U')),
publicStatus VARCHAR2(1) CHECK (publicStatus in ('T','F')),
CONSTRAINT patientKey PRIMARY KEY(patientid)
);

CREATE TABLE HealthFriend
(
patientid NUMBER(10),
healthfriendid NUMBER(10),
dttm DATE NOT NULL,
end_dttm DATE,
CHECK (healthfriendid !=patientid),
CONSTRAINT healthfriendKey PRIMARY KEY(patientid,healthfriendid),
CONSTRAINT fk_healthfriend_patientid FOREIGN KEY (patientid) REFERENCES Patient,
CONSTRAINT fk_healthfriend_healthfriendid FOREIGN KEY (healthfriendid) REFERENCES Patient
);

CREATE SEQUENCE Problems_seq
START WITH 1
INCREMENT BY 1
CACHE 20;


CREATE TABLE Problems
(
problemid NUMBER(10),
patientid NUMBER(10),
pname VARCHAR2(30) NOT NULL,
dttm DATE NOT NULL,
end_dttm DATE,
CONSTRAINT problemsKey PRIMARY KEY(problemid, patientid),
CONSTRAINT fk_problems_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE HealthSupporter_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE HealthSupporter
(
supporterid NUMBER(10),
fname VARCHAR2(30),
lname VARCHAR2(30),
clinic VARCHAR2(30),
CONSTRAINT healthsupporterKey PRIMARY KEY(supporterid)
);

CREATE SEQUENCE Alerts_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Alerts
(
alertid NUMBER(10),
patientid NUMBER(10),
dttm DATE NOT NULL,
end_dttm DATE,
type VARCHAR2(30) NOT NULL,
CONSTRAINT alertKey PRIMARY KEY(alertid,patientid),
CONSTRAINT fk_alerts_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Diet_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Diet
(
dietid NUMBER(10),
patientid NUMBER(10),
description VARCHAR2(1024) NOT NULL,
qty NUMBER(10) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT dietKey PRIMARY KEY(dietid,patientid),
CONSTRAINT fk_diet_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Weight_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Weight
(
weightid NUMBER(10),
patientid NUMBER(10),
qty NUMBER(3) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT weightKey PRIMARY KEY(weightid,patientid),
CONSTRAINT fk_weight_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Exercise_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Exercise
(
exerciseid NUMBER(10),
patientid NUMBER(10),
description VARCHAR2(1024) NOT NULL,
duration NUMBER(10) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT exerciseKey PRIMARY KEY(exerciseid,patientid),
CONSTRAINT fk_exercise_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE BloodPressure_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE BloodPressure
(
bpid NUMBER(10),
patientid NUMBER(10),
systolic NUMBER(10) NOT NULL,
diastolic NUMBER(10) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT bpKey PRIMARY KEY(bpid,patientid),
CONSTRAINT fk_bloodpressure_patientid FOREIGN KEY (patientid) REFERENCES Patient
);


CREATE SEQUENCE ExerciseTolerance_seq
START WITH 1
INCREMENT BY 1
CACHE 20;


CREATE TABLE ExerciseTolerance
(
etid NUMBER(10),
patientid NUMBER(10),
steps NUMBER(10) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT etKey PRIMARY KEY(etid,patientid),
CONSTRAINT fk_et_patientid FOREIGN KEY (patientid) REFERENCES Patient
);


CREATE SEQUENCE Ox_seq
START WITH 1
INCREMENT BY 1
CACHE 20;


CREATE TABLE OxSaturation
(
oxid NUMBER(10),
patientid NUMBER(10),
amount NUMBER(10),
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT oxKey PRIMARY KEY(oxid,patientid),
CONSTRAINT fk_ox_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Pain_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Pain
(
painid NUMBER(10),
patientid NUMBER(10),
scale NUMBER(2) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT painKey PRIMARY KEY(painid,patientid),
CONSTRAINT fk_pain_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Mood_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Mood
(
moodid NUMBER(10),
patientid NUMBER(10),
mood VARCHAR2(1) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT moodKey PRIMARY KEY(moodid,patientid),
CONSTRAINT fk_mood_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Contraction_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Contraction
(
contractionid NUMBER(10),
patientid NUMBER(10),
frequency NUMBER(10) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT contractionKey PRIMARY KEY(contractionid,patientid),
CONSTRAINT fk_contraction_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

CREATE SEQUENCE Temperature_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Temperature
(
tempid NUMBER(10),
patientid NUMBER(10),
temperature NUMBER(3) NOT NULL,
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT temperatureKey PRIMARY KEY(tempid,patientid),
CONSTRAINT fk_temperature_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

