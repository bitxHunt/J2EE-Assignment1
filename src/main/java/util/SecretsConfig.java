/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SecretsConfig {
	private static final Properties properties = new Properties();

	static {
		try (InputStream input = SecretsConfig.class.getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				System.exit(1);
			}

			// Load the properties file
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDbUrl() {
		return properties.getProperty("db.url");
	}

	public static String getDbUsername() {
		return properties.getProperty("db.username");
	}

	public static String getDbPassword() {
		return properties.getProperty("db.password");
	}

	public static String getDbClass() {
		return properties.getProperty("db.class");
	}

	public static String getStripeApiKey() {
		return properties.getProperty("stripe.apikey");
	}
}