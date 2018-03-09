package de.rahn.jdbc.call.dao;

import de.rahn.jdbc.call.entity.Person;
import de.rahn.jdbc.call.entity.User;

/**
 * Die Schnittstelle des DAO für die Stored Procedure "searchPersons".
 * @author Frank W. Rahn
 */
public interface SearchPersonsDAO {

	/**
	 * Suche Personen.
	 * @param num die Anzahl der zu lieferenden Personen
	 * @param user der aktuelle Benutzer
	 * @return die Personen
	 */
	Person[] searchPersons(int num, User user);

}