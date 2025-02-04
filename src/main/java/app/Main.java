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
			seed.seedRole();
			seed.seedTimeSlot();
			seed.seedAddressType();
			seed.seedStatus();
			seed.seedFeature();
			seed.seedUsers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
