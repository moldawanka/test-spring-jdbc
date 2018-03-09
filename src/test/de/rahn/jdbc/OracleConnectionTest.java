package de.rahn.jdbc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Dieser Test testet den Verbindungsaufbau zur Oracle-Datenbank.
 * @author Frank W. Rahn
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("connectionTest.xml")
@ActiveProfiles("Oracle")
public class OracleConnectionTest extends AbstractConnectionTest {

	// Spezielle Tests nur für Oracle

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
					ResultSet result =
						stmt.executeQuery("SELECT fn_info() FROM dual");
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