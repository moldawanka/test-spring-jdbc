package de.rahn.jdbc.call.dao;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import de.rahn.jdbc.call.entity.Person;
import de.rahn.jdbc.call.entity.User;
import de.rahn.jdbc.call.mapper.PersonsMapper;
import de.rahn.jdbc.call.mapper.UserMapper;

/**
 * Das DAO für die Stored Procedure.
 * @author Frank W. Rahn
 */
@Repository
public class StandardSearchPersonsDAO implements SearchPersonsDAO {

	/** Name der Stored Procedure. */
	private static final String NAME = "searchPersons";

	/** 1. Parameter der Stored Procedure. */
	private static final String P_NUM = "p_num";

	/** 2. Parameter der Stored Procedure. */
	private static final String P_USER = "p_user";

	/** 3. Parameter der Stored Procedure. */
	private static final String P_PERSONS = "p_persons";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PersonsMapper personsMapper;

	private SimpleJdbcCall jdbcCall;

	/**
	 * Initialisiere das DAO.
	 */
	@PostConstruct
	public void initialize() {
		jdbcCall =
			new SimpleJdbcCall(dataSource).withProcedureName(NAME)
				.declareParameters(
					userMapper.createSqlParameter(P_USER, false),
					personsMapper.createSqlParameter(P_PERSONS, true));

		jdbcCall.compile();
	}

	/**
	 * {@inheritDoc}
	 * @see SearchPersonsDAO#searchPersons(int, User)
	 */
	@Override
	public Person[] searchPersons(int num, User user) {
		Map<String, Object> in = new HashMap<>();
		in.put(P_NUM, num);
		in.put(P_USER, userMapper.createSqlTypeValue(user));

		Map<String, Object> out = jdbcCall.execute(in);

		return (Person[]) out.get(P_PERSONS);
	}

}