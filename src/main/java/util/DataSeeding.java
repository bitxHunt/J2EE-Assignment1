package util;

import java.io.*;

public class DataSeeding {
	private static final String serviceData = "src/main/webapp/WEB-INF/data/services.csv";

	public void loadServiceData() {
		File file = new File(serviceData);
		StripeConnection stripe = new StripeConnection();

		if (!file.exists()) {
			System.out.println("File not found: " + file.getAbsolutePath());
			return;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.readLine(); // Skip header
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				stripe.createProduct(values[1], values[2], true, Integer.parseInt(values[3]), values[4]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DataSeeding seeder = new DataSeeding();
		seeder.loadServiceData();
	}
}