SELECT country1.CCode AS CCode1, country1.StateAbb AS StateAbb1, country1.StateNme as StateName1,
country2.CCode AS CCode2, country2.StateAbb AS StateAbb2, country2.StateNme as StateName2
FROM country AS country1, contiguity, country AS country2
WHERE country1.CCode = state1no AND country2.CCode = state2no
AND conttype = 1;
