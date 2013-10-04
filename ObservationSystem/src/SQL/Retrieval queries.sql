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

--2. For each patient and each type of observation, show the number of such observations recorded by the patients.
SELECT pat.patientid,pat.fname,'bloodpressure' as "observation type",count(bpid)
FROM Patient pat
LEFT JOIN bloodpressure bp
ON pat.patientid = bp.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'contraction',count(contractionid)
FROM Patient pat
LEFT JOIN contraction con
ON con.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'diet',count(dietid)
FROM Patient pat
LEFT JOIN diet dt
ON dt.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'exercise',count(exerciseid)
FROM Patient pat
LEFT JOIN exercise exe
ON exe.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'exercisetolerance',count(etid)
FROM Patient pat
LEFT JOIN exercisetolerance exet
ON exet.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'mood',count(moodid)
FROM Patient pat
LEFT JOIN mood md
ON md.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'oxsaturation',count(oxid)
FROM Patient pat
LEFT JOIN oxsaturation oxs
ON oxs.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'pain',count(painid)
FROM Patient pat
LEFT JOIN pain pn
ON pn.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'temperature',count(tempid)
FROM Patient pat
LEFT JOIN temperature tmp
ON tmp.patientid = pat.patientid
group by pat.patientid,pat.fname
union
SELECT pat.patientid,pat.fname,'weight',count(weightid)
FROM Patient pat
LEFT JOIN weight wt
ON wt.patientid = pat.patientid
group by pat.patientid,pat.fname


--3. For each patient, and each of their healthfriends, list the number of lingering alerts of the healthfriend.
