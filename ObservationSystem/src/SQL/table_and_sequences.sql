/** DROP TABLES */
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
DROP SEQUENCE PROBLEMS_seq               ;
DROP SEQUENCE HEALTHSUPPORTER_seq        ;
DROP SEQUENCE ALERTS_SEQ                ;
DROP SEQUENCE PATIENTIDS_seq                ;

DROP TRIGGER oxsat_trigger;
DROP TRIGGER pain_trigger;
DROP TRIGGER mood_trigger;
DROP TRIGGER contraction_trigger;
DROP TRIGGER temperature_trigger;
DROP TRIGGER diet_trigger;
DROP TRIGGER weight_trigger;
DROP TRIGGER exercise_trigger;
DROP TRIGGER bp_trigger;
DROP TRIGGER et_trigger;


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
username VARCHAR2(10),
passw VARCHAR2(10),
CONSTRAINT patientKey PRIMARY KEY(patientid),
CONSTRAINT patUsernameUnique UNIQUE(username)
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
username VARCHAR2(10),
passw VARCHAR2(10),
CONSTRAINT healthsupporterKey PRIMARY KEY(supporterid),
CONSTRAINT SupporterusernameUnique UNIQUE(username)
);

--Check that this username does not already exist in the HealthSupporter table
/*
CREATE OR REPLACE TRIGGER chk_user_hS_trigger
AFTER INSERT ON Patient
FOR EACH ROW
DECLARE 
  CONDITION_CHECK NUMBER;
BEGIN
  SELECT COUNT(*) 
    INTO CONDITION_CHECK 
    FROM HealthSupporter H
   WHERE H.username = :new.username; 
  IF CONDITION_CHECK >0 THEN
    RAISE_APPLICATION_ERROR (-20000, 'UPGRADE DENIED!');
  END IF;
END;
*/
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
description VARCHAR2(30) NOT NULL,
--T stands for threshhold alert, D stands for disengaged
type VARCHAR2(1) NOT NULL CHECK (type in ('T','D')),
CONSTRAINT alertKey PRIMARY KEY(alertid,patientid),
CONSTRAINT fk_alerts_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

--OBSERVATIONS

--BEGIN DIET

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

--Create an alert when Diet qty is above 1000 calories
CREATE OR REPLACE TRIGGER diet_trigger
  AFTER INSERT ON DIET
  FOR EACH ROW
WHEN (new.qty > 1000)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Calorie entry over 1000', 'T');	
END;
/
--END DIET

--BEGIN WEIGHT

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

--Create an alert when weight is above 200 LB
CREATE OR REPLACE TRIGGER weight_trigger
  AFTER INSERT ON WEIGHT
  FOR EACH ROW
WHEN (new.qty > 200)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Weight entry over 200 LBS','T');	
END;
/
--END WEIGHT

--BEGIN EXERCISE

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

--Create an alert when exercise entry is over 2 hours
CREATE OR REPLACE TRIGGER exercise_trigger
  AFTER INSERT ON Exercise
  FOR EACH ROW
WHEN (new.duration > 120)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Exercise entry over 120 minutes','T');	
END;
/
--END EXERCISE

--BEGIN BP
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

--Create an alert when BP entry has systolic over 120 or diastolic over 100
CREATE OR REPLACE TRIGGER bp_trigger
  AFTER INSERT ON BloodPressure
  FOR EACH ROW
WHEN (new.systolic > 120 OR new.diastolic > 100)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Blood Pressure 120','T');	
END;
/
--END BP

--BEGIN ET

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

--Create an alert when ET entry has steps under 100
CREATE OR REPLACE TRIGGER et_trigger
  AFTER INSERT ON ExerciseTolerance
  FOR EACH ROW
WHEN (new.steps < 100)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Exercise tolerance under 100 steps','T');	
END;
/
--END ET

--BEGIN OX_SAT

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

--Create an alert when OxSeq entry is under 95%
CREATE OR REPLACE TRIGGER oxsat_trigger
  AFTER INSERT ON OxSaturation
  FOR EACH ROW
WHEN (new.amount < 95)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'O2 saturation under 95%','T');	
END;
/
--END OX_SAT

--BEGIN PAIN

CREATE SEQUENCE Pain_seq
START WITH 1
INCREMENT BY 1
CACHE 20;

CREATE TABLE Pain
(
painid NUMBER(10),
patientid NUMBER(10),
scale NUMBER(2) NOT NULL CHECK(scale>0 and scale<=10),
dttm DATE NOT NULL,
rec_dttm DATE NOT NULL,
CONSTRAINT painKey PRIMARY KEY(painid,patientid),
CONSTRAINT fk_pain_patientid FOREIGN KEY (patientid) REFERENCES Patient
);

--Create an alert when Pain entry is above 5
CREATE OR REPLACE TRIGGER pain_trigger
  AFTER INSERT ON Pain
  FOR EACH ROW
WHEN (new.scale > 5)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Pain entry is above 5','T');	
END;
/
--END PAIN

--BEGIN MOOD

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

--Create an alert when Mood entry is [S]ad
CREATE OR REPLACE TRIGGER mood_trigger
  AFTER INSERT ON Mood
  FOR EACH ROW
WHEN (new.mood ='S')
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Mood entry is [S]ad','T');	
END;
/
--END MOOD

--BEGIN Contraction
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

--Create an alert when Contraction entry is >5 per hour
CREATE OR REPLACE TRIGGER contraction_trigger
  AFTER INSERT ON Contraction
  FOR EACH ROW
WHEN (new.frequency >5)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Contractions greater than 5 per hour','T');	
END;
/
--END CONTRACTION

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

--Create an alert when Temperature entry is >100degF
CREATE OR REPLACE TRIGGER temperature_trigger
  AFTER INSERT ON Temperature
  FOR EACH ROW
WHEN (new.temperature >100)
BEGIN
    INSERT INTO ALERTS VALUES(ALERTS_SEQ.nextval, :new.PATIENTID, 
                              CURRENT_TIMESTAMP(3), NULL, 'Temperature greater than 100 degF','T');	
END;
/
--END CONTRACTION

COMMIT;