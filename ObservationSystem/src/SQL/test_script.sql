/*Create two patients*/
INSERT INTO Patient VALUES (Patientids_seq.nextval, '02-Nov-92', 'John', 'Holmes', '123 Main Street', 'Ann Arbor', 'MI', 48105, 'M', 'T')
INSERT INTO Patient VALUES (Patientids_seq.nextval, '02-Nov-92', 'Adam', 'Holmes', '123 Main Street', 'Ann Arbor', 'MI', 48105, 'M', 'T')

/*Make Adam Holmes a friend of John Holmes*/
INSERT INTO HealthFriend VALUES (1,2, CURRENT_TIMESTAMP(3), NULL)

/*Test that a patient cannot add themselves*/
INSERT INTO HealthFriend VALUES (1,1, CURRENT_TIMESTAMP(3), NULL)

/*This construct necessary to retrieve seconds*/
SELECT TO_CHAR(dttm,'MM/DD/YYYY HH24:MI:SS')  FROM HealthFriend;

/*Create a problem for John Holmes*/
INSERT INTO Problems VALUES (Problems_seq.nextval, 1, 'COPD', CURRENT_TIMESTAMP(3), NULL)

SELECT * FROM Problems