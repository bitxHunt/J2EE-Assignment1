package app;

import util.DB;

public class Main {
	public static void main(String[] args) {
		try {
			// Initialise Database model
			DB dbConfig = new DB();
			
			// Reset the database
			dbConfig.resetDB();
			
			// Initialise the database tables
			dbConfig.initTables();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
