package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StripeConfig {
	private static final Properties properties = new Properties();

	static {
		try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("stripe.properties")) {
			if (input == null) {
				System.out.println("Sorry, unable to find stripe.properties");
				System.exit(1);
			}

			// Load the properties file
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getStripeApiKey() {
		return properties.getProperty("stripe.apikey");
	}
}
