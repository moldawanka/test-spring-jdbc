package de.rahn.jdbc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgresql.jdbc4.Jdbc4Connection;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Dieser Test testet den Verbindungsaufbau zur PostgreSQL-Datenbank.
 * @author Frank W. Rahn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("connectionTest.xml")
@ActiveProfiles("PostgreSQL")
public class PostgreSQLConnectionTest extends AbstractConnectionTest {

	// Spezielle Tests nur für PostgreSQL

	/**
	 * Im Driver ist die Methode
	 * {@link Jdbc4Connection#createStruct(String, Object[])} noch nicht
	 * implementiert.
	 * @see de.rahn.jdbc.AbstractConnectionTest#testDatabaseConnectionPerApi()
	 */
	@Override
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testDatabaseConnectionPerApi() {
		super.testDatabaseConnectionPerApi();
	}

	/**
	 * Teste die Datenbankverbindung über ein SQL.
	 */
	@Test
	public void testDatabaseConnectionPerSql() {
		String result = template.execute(new ConnectionCallback<String>() {

			@Override
			public String doInConnection(Connection con) throws SQLException {
				Statement stmt = con.createStatement();
				try {
					ResultSet result = stmt.executeQuery("SELECT fn_info()");
					try {
						if (result.next()) {
							return result.getString(1);
						} else {
							return null;
						}
					} finally {
						result.close();
					}
				} finally {
					stmt.close();
				}
			}

		});

		assertThat("Die Datenbankaktion nicht das richtige Ergebnis", result,
			equalTo("Eine einfache Funktion"));
	}

}
