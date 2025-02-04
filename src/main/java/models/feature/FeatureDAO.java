package models.feature;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import util.DB;

public class FeatureDAO {
	// Seed Feature Data
	public void seedData(Feature feature) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_feature(?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, feature.getName());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Feature Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
