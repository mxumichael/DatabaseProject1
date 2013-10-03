--query 1
SELECT P.fname, P.lname 
FROM WEIGHT W, PATIENT P
WHERE W.PATIENTID=P.PATIENTID AND W.QTY IN
(
SELECT MIN(QTY)
FROM WEIGHT, PROBLEMS
WHERE WEIGHT.PATIENTID=PROBLEMS.PATIENTID and PROBLEMS.PNAME='HIV');
--query 2. Of all Obesity and High Risk Patients, find patients with the highest blood pressure.
SELECT distinct pat.patientid,pat.fname,pat.lname,bp.systolic
FROM Patient pat
INNER JOIN problems pro
ON pro.patientid = pat.patientid
INNER JOIN bloodpressure bp
ON bp.patientid  = pat.patientid
WHERE pro.pname IN ('Obesity','High Risk Pregnancy')
AND bp.systolic  =
  (SELECT MAX(bp2.systolic)
  FROM problems pro2
  INNER JOIN bloodpressure bp2
  ON bp2.patientid  = pro2.patientid
  WHERE pro2.pname IN ('Obesity','High Risk Pregnancy')
    /* this subquery finds the highest systolic pressure for the obesity/high risk patients*/
  )
  
--query 4. Find patients who live in same city as healthfriend.
SELECT pat1.patientid,pat1.fname,pat2.patientid,pat2.fname,pat1.city,pat2.city,hf.dttm
FROM Patient pat1
INNER JOIN healthfriend hf
ON hf.patientid = pat1.patientid
INNER JOIN Patient pat2
ON pat2.patientid  = hf.healthfriendid where pat1.city = pat2.city

--query 5 /*LIST HEALTHFRIENDS FOR PATIENTX*/
SELECT HealthFriend.HealthFriendID
FROM HEALTHFRIEND
WHERE HEALTHFRIEND.PATIENTID=6
ORDER BY DTTM DESC

--reporting query 1. For each patient, find the number of healthfriends made in the last month.
SELECT * FROM healthfriend hf WHERE hf.dttm >=  sysdate - INTERVAL '1' month(1);

