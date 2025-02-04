package util;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import models.category.*;
import models.service.*;
import models.timeSlot.*;
import models.user.*;
import models.address.*;
import models.bundle.*;
import models.role.*;
import models.feature.*;
import models.status.*;

public class DataSeeding {
	private static final String categoryData = "src/main/webapp/WEB-INF/data/category.csv";
	private static final String serviceData = "src/main/webapp/WEB-INF/data/service.csv";
	private static final String bundleData = "src/main/webapp/WEB-INF/data/bundle.csv";
	private static final String roleData = "src/main/webapp/WEB-INF/data/role.csv";
	private static final String addressTypeData = "src/main/webapp/WEB-INF/data/address_type.csv";
	private static final String timeSlotData = "src/main/webapp/WEB-INF/data/time_slot.csv";
	private static final String userData = "src/main/webapp/WEB-INF/data/user.csv";
	private static final String featureData = "src/main/webapp/WEB-INF/data/feature.csv";
	private static final String statusData = "src/main/webapp/WEB-INF/data/status.csv";

	public ArrayList<ArrayList<String>> loadData(String filePath) {
		File file = new File(filePath);
		ArrayList<ArrayList<String>> dataArray = new ArrayList<>();

		if (!file.exists()) {
			System.out.println("File not found: " + file.getAbsolutePath());
			return dataArray; // Return empty dataArray instead of void
		}

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			br.readLine(); // Skip header
			String line;
			while ((line = br.readLine()) != null) {
				// Split the line into values and create a new row
				String[] values = line.split(",");
				ArrayList<String> row = new ArrayList<>();
				for (String data : values) {
					row.add(data.trim()); // Add trimmed data to the row
				}
				dataArray.add(row); // Add the row to the dataArray
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return dataArray; // Return the populated dataArray
	}

	public void seedCategory() throws SQLException {
		try {
			Category category = new Category();
			CategoryDAO categoryDB = new CategoryDAO();
			ArrayList<ArrayList<String>> dataArray = loadData(categoryData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				category.setCategoryName(row.get(1));
				category.setCategoryDescription(row.get(2));

				// Pass the model to seed data
				categoryDB.seedData(category);
			}

		} catch (Exception e) {
			System.out.println("Error Seeding Category Table.");
			e.printStackTrace();
		}
	}

	public void seedService() throws SQLException {
		try {
			Service service = new Service();
			ServiceDAO serviceDB = new ServiceDAO();
			ArrayList<ArrayList<String>> dataArray = loadData(serviceData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				service.setServiceName(row.get(1));
				service.setServiceDescription(row.get(2));
				service.setPrice(Float.parseFloat(row.get(3)));
				service.setImageUrl(row.get(4));
				service.setCategoryId(Integer.parseInt(row.get(5)));

				// Pass the model to seed data
				serviceDB.seedData(service);
			}

		} catch (NumberFormatException e) {
			System.out.println("Error Converting String to Integer or Float");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error Seeding Category Table.");
			e.printStackTrace();
		}
	}

	public void seedBundle() throws SQLException {
		try {
			Bundle bundle = new Bundle();
			BundleDAO bundleDB = new BundleDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(bundleData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				bundle.setBundleName(row.get(1));
				bundle.setBundleDescription(row.get(2));
				bundle.setDiscountPercent(Integer.parseInt(row.get(3)));
				bundle.setImageUrl(row.get(4));

				// Pass the model to seed data
				bundleDB.seedData(bundle);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Bundle Table.");
			e.printStackTrace();
		}
	}

	public void seedRole() throws SQLException {
		try {
			Role role = new Role();
			RoleDAO roleDB = new RoleDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(roleData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				role.setRoleName(row.get(1));

				// Pass the model to seed data
				roleDB.seedData(role);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Role Table.");
			e.printStackTrace();
		}
	}

	public void seedTimeSlot() throws SQLException {
		try {
			TimeSlot timeSlot = new TimeSlot();
			TimeSlotDAO timeSlotDB = new TimeSlotDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(timeSlotData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				timeSlot.setStartTime(LocalTime.parse(row.get(1)));
				timeSlot.setEndTime(LocalTime.parse(row.get(2)));

				// Pass the model to seed data
				timeSlotDB.seedData(timeSlot);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Time Slot Table.");
			e.printStackTrace();
		}
	}

	public void seedAddressType() throws SQLException {
		try {
			AddressType addressType = new AddressType();
			AddressDAO AddressDB = new AddressDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(addressTypeData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				addressType.setName(row.get(1));

				// Pass the model to seed data
				AddressDB.createAddressType(addressType);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Address Type Table.");
			e.printStackTrace();
		}
	}

	public void seedUsers() throws SQLException {
		try {
			User user = new User();
			UserDAO userDB = new UserDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(userData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				user.setCustomerId(row.get(1));
				user.setFirstName(row.get(2));
				user.setLastName(row.get(3));
				user.setEmail(row.get(4));
				user.setPassword(row.get(5));
				user.setPhoneNo(row.get(6));
				user.setRole(Integer.parseInt(row.get(9)));

				// Pass the model to seed data
				userDB.seedData(user);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Users Table.");
			e.printStackTrace();
		}
	}
	
	public void seedFeature() throws SQLException {
		try {
			Feature feature = new Feature();
			FeatureDAO featureDB = new FeatureDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(featureData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				feature.setName(row.get(1));

				// Pass the model to seed data
				featureDB.seedData(feature);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Feature Table.");
			e.printStackTrace();
		}
	}
	
	public void seedStatus() throws SQLException {
		try {
			Status status = new Status();
			StatusDAO statusDB = new StatusDAO();

			ArrayList<ArrayList<String>> dataArray = loadData(statusData);

			for (ArrayList<String> row : dataArray) {

				// Row of data
				status.setName(row.get(1));

				// Pass the model to seed data
				statusDB.seedData(status);
			}
		} catch (Exception e) {
			System.out.println("Error Seeding Status Table.");
			e.printStackTrace();
		}
	}
}