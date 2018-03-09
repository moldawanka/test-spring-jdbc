CREATE OR REPLACE FUNCTION get_user() RETURNS s_User AS
$$
	DECLARE
		u s_User;
	BEGIN
		u.id := 4711;
		u.name := 'Frank Rahn';
		u.dept := 'Entwicklung';
		RETURN u;
	END;
$$ LANGUAGE plpgsql;

SELECT * FROM searchPersons(5, get_user());

DROP FUNCTION IF EXISTS get_user();