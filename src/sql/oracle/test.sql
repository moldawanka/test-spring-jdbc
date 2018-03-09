SET SERVEROUTPUT ON

CREATE OR REPLACE FUNCTION get_user
	RETURN s_User
IS
	u	s_User;
BEGIN
	u := s_User(
		4711,
		'Frank Rahn',
		'Entwicklung'
	);

	RETURN u;
END get_user;
/

DECLARE
	a	a_Person;
	i	INTEGER;
BEGIN
	searchPersons(5, get_user(), a);

	FOR i IN a.FIRST..a.LAST
	LOOP
		DBMS_OUTPUT.PUT_LINE('Person ' || i);
		DBMS_OUTPUT.PUT_LINE('id=' || a(i).id);
		DBMS_OUTPUT.PUT_LINE('name=' || a(i).name);
		DBMS_OUTPUT.PUT_LINE('salary=' || a(i).salary);
		DBMS_OUTPUT.PUT_LINE('dateOfBirth=' || a(i).dateOfBirth);
	END LOOP;
END;
/

DROP FUNCTION get_user;

QUIT