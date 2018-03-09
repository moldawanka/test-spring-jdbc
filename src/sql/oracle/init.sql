SET SERVEROUTPUT ON

CREATE OR REPLACE TYPE s_User AS OBJECT ( -- Der aktuelle Benutzer
	id		CHAR(10),
	name	VARCHAR(30),
	dept	VARCHAR(254)
);
/

CREATE OR REPLACE TYPE s_Person AS OBJECT ( -- Eine Person
	id			NUMBER(19),
	name		VARCHAR(30),
	salary		DECIMAL,
	dateOfBirth	DATE
);
/

CREATE OR REPLACE TYPE a_Person AS TABLE OF s_Person; -- Collection der Person
/

CREATE OR REPLACE PROCEDURE searchPersons( -- Suche Personen
	p_num		IN	INTEGER,
	p_user		IN	s_User,
	p_persons	OUT	a_Person
) IS
	i			INTEGER;
	p			s_Person;
	dateOfBirth	DATE := CURRENT_DATE;
BEGIN -- Ausführungsteil
	DBMS_OUTPUT.PUT_LINE('Die Stored Procedure searchPersons wurde aufgerufen. User=' || p_user.id);

	p_persons := a_Person();

	FOR i IN 1..p_num
	LOOP -- Fake der Suche
		p := s_Person(
			i,						-- id
			p_user.name,			-- name
			ACOS(-1.0) * i * 100.0,	-- Salary
			dateOfBirth				-- dateOfBrith
		);
		p_persons.extend();
		p_persons(p_persons.LAST) := p;
	END LOOP;
END searchPersons;
/

QUIT