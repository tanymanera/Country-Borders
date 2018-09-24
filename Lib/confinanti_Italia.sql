SELECT c2.StateNme FROM country AS  c1, contiguity, country AS c2 
WHERE c1.CCode = contiguity.state1no AND c2.CCode = contiguity.state2no 
AND c1.stateNme = 'Italy'
AND contiguity.conttype = 1;
SELECT c1.StateNme FROM country AS  c1, contiguity, country AS c2 
WHERE c1.CCode = contiguity.state1no AND c2.CCode = contiguity.state2no 
AND  c2.StateNme = 'Italy'
AND contiguity.conttype = 1;