package de.rahn.jdbc.call.mapper;

import static java.sql.Types.STRUCT;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.Date;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import de.rahn.jdbc.call.entity.Person;

/**
 * Mapping zwischen einer {@link Struct} und einem {@link Person}.
 * @author Frank W. Rahn
 */
@Component
public class PersonMapper extends SqlParameterMapper<Person, Struct> {

	/** Der SQL-Name des JDBC-Typnamens. */
	private static final String TYPE_NAME = "S_PERSON";

	/**
	 * {@inheritDoc}
	 * @see SqlParameterMapper#createObject(Object)
	 */
	@Override
	protected Person createObject(final Struct struct) throws SQLException {
		return new Person() {
			{
				Object[] attributes = struct.getAttributes();

				BigDecimal d = (BigDecimal) attributes[0];
				if (d != null) {
					setId(d.longValue());
				}

				setName((String) attributes[1]);
				setSalary((BigDecimal) attributes[2]);
				setDateOfBirth((Date) attributes[3]);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * @see SqlParameterMapper#createSqlValue(Connection, String, Object)
	 */
	@Override
	protected Struct createSqlValue(Connection con, Person person)
		throws SQLException {

		return con.createStruct(TYPE_NAME, new Object[] { person.getId(),
			person.getName(), person.getSalary(), person.getDateOfBirth() });
	}

	/**
	 * {@inheritDoc}
	 * @see SqlParameterMapper#createSqlParameter(String, boolean)
	 */
	@Override
	public SqlParameter createSqlParameter(String paramaterName,
		boolean outParameter) {
		if (outParameter) {
			return new SqlOutParameter(paramaterName, STRUCT, TYPE_NAME, this);
		} else {
			return new SqlParameter(paramaterName, STRUCT, TYPE_NAME);
		}
	}

}