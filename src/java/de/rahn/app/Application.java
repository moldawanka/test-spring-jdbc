package de.rahn.app;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.rahn.jdbc.call.dao.SearchPersonsDAO;
import de.rahn.jdbc.call.entity.Person;
import de.rahn.jdbc.call.entity.User;

/**
 * Die Anwendung zum Aufrufen des Taschenrechners.
 * @author Frank W. Rahn
 */
@Component
public class Application implements Runnable {

	private static final Logger logger = getLogger(Application.class);

	@Autowired(required = true)
	private SearchPersonsDAO searchPersonsDAO;

	/**
	 * {@inheritDoc}
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		User user = new User();
		user.setId("4711");
		user.setName(System.getProperty("user.name"));
		user.setDepartment("Development");

		// Aufruf des Taschenrechners
		Person[] persons = searchPersonsDAO.searchPersons(15, user);

		logger.info("Ergebnis = {}", (Object) persons);
	}

}