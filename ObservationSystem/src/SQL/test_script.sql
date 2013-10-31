/*
** Data seed script
*/


/*HEALTH SUPPORTERS*/
SET DEFINE OFF

INSERT INTO HEALTHSUPPORTER (SUPPORTERID, FNAME, LNAME, CLINIC, USERNAME, PASSW) 
VALUES (1.0, 'Altaf', 'Hussain', 'Dayview','ahussain','hussain123');

INSERT INTO HEALTHSUPPORTER (SUPPORTERID, FNAME, LNAME, CLINIC, USERNAME, PASSW)
VALUES (2.0, 'Manu', 'Joseph', 'Dayview','mjoseph','joseph123');

INSERT INTO HEALTHSUPPORTER (SUPPORTERID, FNAME, LNAME, CLINIC, USERNAME, PASSW)
VALUES (3.0, 'Shane', 'Lee', 'Huntington','slee','lee123');

INSERT INTO HEALTHSUPPORTER (SUPPORTERID, FNAME, LNAME, CLINIC, USERNAME, PASSW)
VALUES (4.0, 'Shyam', 'Prasad', 'Huntington','sprasad','prasad123');


/*PATIENT*/
INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (1.0, to_date('01/01/1986', 'MM/DD/YYYY'), 'Gary', 'George', '2806 Conifer Drive', 'Raleigh', 'NC', 27606.0, 'M', 'T', 1 ,'ggeorge','geo123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (2.0, to_date('01/02/1986', 'MM/DD/YYYY'), 'Adnan', 'Kazi', '1234 Capability Drive', ' Raleigh', 'NC', 27655.0, 'F', 'T', 1, 'akazi', 'kazi123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (3.0, to_date('01/03/1986', 'MM/DD/YYYY'), 'Neha', 'Shetty', '440 Sullivan Drive', ' Chapel Hill', 'NC', 27517.0, 'F', 'T', 1, 'nshetty', 'shetty123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (4.0, to_date('01/04/1986', 'MM/DD/YYYY'), 'Sheldon', 'Cooper', '2808 Avent Ferry Road', ' Raleigh', 'NC', 27616.0, 'F', 'T',1,'scooper','cooper123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (5.0, to_date('01/05/1986', 'MM/DD/YYYY'), 'Michael', 'Watson', '2222 Gorman Street', ' Raleigh', 'NC', 27678.0, 'M', 'T', 1, 'mwatson','watson123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (6.0, to_date('01/06/1986', 'MM/DD/YYYY'), 'Tom', 'Kerr', '1430 Collegeview Ave', ' Durham', 'NC', 27701.0, 'M', 'T',2, 'tkerr','tkerr123');

INSERT INTO PATIENT (PATIENTID, DOB, FNAME, LNAME, STREET, CITY, STATE, ZIP, GENDER, PUBLICSTATUS,SUPPORTERID, USERNAME,PASSW) 
VALUES (7.0, to_date('01/07/1986', 'MM/DD/YYYY'), 'Maya', 'Tran', '100 Brown Circle', ' Chapel Hill', 'NC', 27516.0, 'F', 'T',2, 'mtran','tran123');

/*PROBLEMS*/
SET DEFINE OFF

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (1.0, 1.0, 'HIV', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (2.0, 2.0, 'Obesity', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (3.0, 2.0, 'High Risk Pregnancy', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (4.0, 3.0, 'Obesity', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (5.0, 3.0, 'High Risk Pregnancy', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (6.0, 4.0, 'COPD', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (7.0, 4.0, 'HIV', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (8.0, 5.0, 'COPD', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (9.0, 6.0, 'COPD', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (10.0, 6.0, 'Obesity', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);

INSERT INTO PROBLEMS (PROBLEMID, PATIENTID, PNAME, DTTM, END_DTTM) 
VALUES (11.0, 7.0, 'High Risk Pregnancy', to_date('09/30/2013', 'MM/DD/YYYY'), NULL);


/*HEALTH FRIEND*/
INSERT INTO HEALTHFRIEND (PATIENTID, HEALTHFRIENDID, DTTM, END_DTTM) 
VALUES (6.0, 2.0, to_date('04/01/2013', ' MM/DD/YYYY HH:MI:SS'), to_date('', ' MM/DD/YYYY HH:MI:SS'));

INSERT INTO HEALTHFRIEND (PATIENTID, HEALTHFRIENDID, DTTM, END_DTTM) 
VALUES (6.0, 5.0, to_date('03/04/2011', ' MM/DD/YYYY HH:MI:SS'), to_date('', ' MM/DD/YYYY HH:MI:SS'));

INSERT INTO HEALTHFRIEND (PATIENTID, HEALTHFRIENDID, DTTM, END_DTTM) 
VALUES (4.0, 1.0, to_date('10/12/2012', ' MM/DD/YYYY HH:MI:SS'), to_date('', ' MM/DD/YYYY HH:MI:SS'));

INSERT INTO HEALTHFRIEND (PATIENTID, HEALTHFRIENDID, DTTM, END_DTTM) 
VALUES (4.0, 5.0, to_date('01/02/2013', ' MM/DD/YYYY HH:MI:SS'), to_date('', ' MM/DD/YYYY HH:MI:SS'));

INSERT INTO HEALTHFRIEND (PATIENTID, HEALTHFRIENDID, DTTM, END_DTTM) 
VALUES (4.0, 6.0, to_date('05/04/2011', ' MM/DD/YYYY HH:MI:SS'), to_date('', ' MM/DD/YYYY HH:MI:SS'));

/*BLOODPRESSURE*/
INSERT INTO BLOODPRESSURE (BPID, PATIENTID, SYSTOLIC, DIASTOLIC, DTTM, REC_DTTM) 
VALUES (1.0, 2.0, 150.0, 96.0, to_date('4/6/2013 7:50 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO BLOODPRESSURE (BPID, PATIENTID, SYSTOLIC, DIASTOLIC, DTTM, REC_DTTM) 
VALUES (2.0, 2.0, 170.0, 90.0, to_date('4/8/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/8/2013 8:05 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO BLOODPRESSURE (BPID, PATIENTID, SYSTOLIC, DIASTOLIC, DTTM, REC_DTTM) 
VALUES (3.0, 3.0, 162.0, 110.0, to_date('4/6/2013 7:50 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - DIET*/
INSERT INTO DIET (DIETID, PATIENTID, DESCRIPTION, QTY, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 'egg', 1.0, to_date('4/5/2013 8:15 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:40 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO DIET (DIETID, PATIENTID, DESCRIPTION, QTY, DTTM, REC_DTTM) 
VALUES (2.0, 4.0, 'orange', 0.5, to_date('4/5/2013 8:15 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:40 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO DIET (DIETID, PATIENTID, DESCRIPTION, QTY, DTTM, REC_DTTM) 
VALUES (3.0, 4.0, 'toast', 1.0, to_date('4/5/2013 8:15 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:40 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO DIET (DIETID, PATIENTID, DESCRIPTION, QTY, DTTM, REC_DTTM) 
VALUES (4.0, 4.0, 'margarine', 1.0, to_date('4/5/2013 8:15 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:40 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - EXERCISE*/
INSERT INTO EXERCISE (EXERCISEID, PATIENTID, DESCRIPTION, DURATION, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 'walking', 30.0, to_date('4/5/2013 6:30 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 7:35 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - EXERCISE TOLERANCE*/
INSERT INTO EXERCISETOLERANCE (ETID, PATIENTID, STEPS, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 20.0, to_date('4/5/2013 11:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 12:00 pm', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - MOOD*/
INSERT INTO MOOD (MOODID, PATIENTID, MOOD, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 'neutral', to_date('4/5/2013 9:00 pm', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 9:00 pm', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - OXSATURATION*/
INSERT INTO OXSATURATION (OXID, PATIENTID, AMOUNT, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 78.0, to_date('4/6/2013 10:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 10:10 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - PAIN*/
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (1.0, 7.0, 8.0, to_date('4/6/2013 1:00 pm', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 5:00 pm', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - TEMPERATURE*/
INSERT INTO TEMPERATURE (TEMPID, PATIENTID, TEMPERATURE, DTTM, REC_DTTM) 
VALUES (1.0, 4.0, 98.2, to_date('4/5/2013 6:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 6:10 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION - WEIGHT*/
INSERT INTO WEIGHT (WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM) 
VALUES (WEIGHT_SEQ.nextval, 4.0, 100.0, to_date('4/5/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:05 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO WEIGHT (WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM) 
VALUES (WEIGHT_SEQ.nextval, 4.0, 102.0, to_date('4/6/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 8:05 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO WEIGHT (WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM) 
VALUES (WEIGHT_SEQ.nextval, 1.0, 150.0, to_date('4/5/2013 7:50 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/5/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'));

INSERT INTO WEIGHT (WEIGHTID, PATIENTID, QTY, DTTM, REC_DTTM) 
VALUES (WEIGHT_SEQ.nextval, 1.0, 156.0, to_date('4/6/2013 7:50 am', 'MM/DD/YYYY HH:MI AM'), to_date('4/6/2013 8:00 am', 'MM/DD/YYYY HH:MI AM'));

/*OBSERVATION TYPES*/
/*Last column is a bit field for illness associationL HIV,COPD,High risk pregnancy,obesity'*/
INSERT INTO ObservationTypes VALUES(1.0, 'Behavioral','Diet','1111');
INSERT INTO ObservationTypes VALUES(2.0, 'Behavioral','Weight','1111');
INSERT INTO ObservationTypes VALUES(3.0, 'Behavioral','Exercise','1111');
INSERT INTO ObservationTypes VALUES(4.0, 'Physiological','BloodPressure','0011');
INSERT INTO ObservationTypes VALUES(5.0, 'Physiological','ExerciseTolerance','1000');
INSERT INTO ObservationTypes VALUES(6.0, 'Physiological','OxSaturation','1000');
INSERT INTO ObservationTypes VALUES(7.0, 'Physiological','Pain','0010');
INSERT INTO ObservationTypes VALUES(8.0, 'Psychological','Mood','1111');
INSERT INTO ObservationTypes VALUES(9.0, 'Physiological','Contraction','0010');
INSERT INTO ObservationTypes VALUES(10.0, 'Physiological','Temperature','1000');


/*CUSTOM OBSERVATIONS*/
INSERT INTO ObservationTypes VALUES(11.0, 'Physiological','Apples','1000');
INSERT INTO ObservationTypes VALUES(12.0, 'Physiological','Bananas','1000');

/*APPLES DATA*/
INSERT INTO APPLES VALUES(1, 4, 'red', CURRENT_TIMESTAMP(3), CURRENT_TIMESTAMP(3));

insert into messages values (messages_seq.nextval,4,1,sysdate,'hi there healthfriend. hope you are doing well','N');
insert into messages values (messages_seq.nextval,4,2,sysdate,'Happy Birthday!','N');
insert into messages values (messages_seq.nextval,4,3,sysdate,'Sheldon, remember to call your mother on your birthday.','N');

/*test alert for patient that is at risk. at risk = Display a list of existing HealthFriends who are at risk, 
i.e. who have more than five unviewed alerts and initiate an option to send message* to healthfriends who are at risk.*/
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 8.0, to_date('4/6/2013 1:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 7.0, to_date('4/6/2013 2:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 8.0, to_date('4/6/2013 3:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 8.0, to_date('4/6/2013 4:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 7.0, to_date('4/6/2013 5:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);
INSERT INTO PAIN (PAINID, PATIENTID, SCALE, DTTM, REC_DTTM) 
VALUES (pain_seq.nextval, 1, 8.0, to_date('4/6/2013 6:00 pm', 'MM/DD/YYYY HH:MI AM'), sysdate);


/*
/*Create two patients*/
/*INSERT INTO Patient VALUES (Patientids_seq.nextval, '02-Nov-92', 'John', 'Holmes', '123 Main Street', 'Ann Arbor', 'MI', 48105, 'M', 'T')*/
/*INSERT INTO Patient VALUES (Patientids_seq.nextval, '02-Nov-92', 'Adam', 'Holmes', '123 Main Street', 'Ann Arbor', 'MI', 48105, 'M', 'T')*/

/*Make Adam Holmes a friend of John Holmes*/
/*INSERT INTO HealthFriend VALUES (1,2, CURRENT_TIMESTAMP(3), NULL)*/

/*Test that a patient cannot add themselves*/
/*INSERT INTO HealthFriend VALUES (1,1, CURRENT_TIMESTAMP(3), NULL)*/

/*This construct necessary to retrieve seconds*/
/*SELECT TO_CHAR(dttm,'MM/DD/YYYY HH24:MI:SS')  FROM HealthFriend;*/

/*Create a problem for John Holmes*/
/*INSERT INTO Problems VALUES (Problems_seq.nextval, 1, 'COPD', CURRENT_TIMESTAMP(3), NULL)*/

COMMIT;