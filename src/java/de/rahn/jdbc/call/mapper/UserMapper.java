package de.rahn.jdbc.call.mapper;

import static java.sql.Types.STRUCT;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Component;

import de.rahn.jdbc.call.entity.User;

/**
 * Mapping zwischen einer {@link Struct} und einem {@link User}.
 * @author Frank W. Rahn
 */
@Component
public class UserMapper extends SqlParameterMapper<User, Struct> {

	/** Der SQL-Name des JDBC-Typnamens. */
	private static final String TYPE_NAME = "S_USER";

	/**
	 * {@inheritDoc}
	 * @see SqlParameterMapper#createObject(Object)
	 */
	@Override
	protected User createObject(final Struct struct) throws SQLException {
		return new User() {
			{
				Object[] attributes = struct.getAttributes();
				setId((String) attributes[0]);
				setName((String) attributes[1]);
				setDepartment((String) attributes[2]);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * @see SqlParameterMapper#createSqlValue(Connection, Object)
	 */
	@Override
	protected Struct createSqlValue(Connection con, User user)
		throws SQLException {

		return con
			.createStruct(
				TYPE_NAME,
				new Object[] { user.getId(), user.getName(),
					user.getDepartment() });
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