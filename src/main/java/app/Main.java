/***********************************************************
* Name: Thiha Swan Htet, Harry
* Class: DIT/FT/2B/01
* Admin No: P2336671
************************************************************/

package app;

import util.DB;
import util.DataSeeding;

public class Main {
	public static void main(String[] args) {
		try {
			// Initialise Database model
			DB dbConfig = new DB();
			DataSeeding seed = new DataSeeding();

			// Reset the database
			dbConfig.resetDB();

			// Initialise the database tables
			dbConfig.initTables();

			// Seed the tables
			seed.seedCategory();
			seed.seedService();
			seed.seedBundle();
			seed.seedBundleService();
			seed.seedRole();
			seed.seedTimeSlot();
			seed.seedAddressType();
			seed.seedStatus();
			seed.seedEmailService();
			seed.seedUsers();
			seed.seedOrganization();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
