DROP FUNCTION IF EXISTS searchPersons(
	INTEGER, s_User
) CASCADE;
DROP DOMAIN IF EXISTS a_Person CASCADE;
DROP TYPE IF EXISTS s_User CASCADE;
DROP TYPE IF EXISTS s_Person CASCADE;

CREATE TYPE s_User AS ( -- Der aktuelle Benutzer
	id		CHAR(10),
	name	VARCHAR(30),
	dept	VARCHAR
);

CREATE TYPE s_Person AS ( -- Eine Person
	id			BIGINT,
	name		VARCHAR(30),
	salary		DECIMAL,
	dateOfBirth	DATE
);

CREATE DOMAIN a_Person AS s_Person[]; -- Collection der Person

CREATE FUNCTION searchPersons( -- Suche Personen
	p_num		IN	INTEGER,
	p_user		IN	s_User,
	p_persons	OUT	a_Person
) AS
$BODY$  -- Beginn der PL/pgSQL Funktion
	DECLARE -- Deklarationsblock
		i			INTEGER;
		p			s_Person;
		dateOfBirth	DATE := current_date;
	BEGIN -- Ausführungsteil
		RAISE NOTICE 'Die Stored Procedure searchPersons wurde aufgerufen. User=%',
			p_user;
		FOR i IN 1..p_num
		LOOP -- Fake der Suche
			p := p_persons[i];
			p.id := i;
			p.name := p_user.name;
			p.dateOfBirth := dateOfBirth;
			p.salary := pi() * i * 100;
			p_persons[i] := p;
		END LOOP;
	END;
$BODY$ -- Ende der Funktion
LANGUAGE plpgsql; -- Die Sprache des Funktionskörpers
