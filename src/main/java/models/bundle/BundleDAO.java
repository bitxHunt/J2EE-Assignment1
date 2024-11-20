package models.bundle;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

import util.DB;

public class BundleDAO {
	public boolean createBundle(Bundle bundle, List<Integer> serviceIds) throws SQLException {
		Connection conn = DB.connect();
		try {
			String serviceIdsStr = String.join(",",
					serviceIds.stream().map(String::valueOf).collect(Collectors.toList()));

			// Modified to use PostgreSQL's CALL syntax exactly
			CallableStatement cstmt = conn.prepareCall("CALL sp_create_bundle(?, ?, ?)");

			// Set the IN parameters
			cstmt.setString(1, bundle.getBundleName());
			cstmt.setInt(2, bundle.getDiscountPercent());
			cstmt.setString(3, serviceIdsStr);


			// Execute the procedure
			cstmt.execute();


			// Close the statement
			cstmt.close();

			return true;

		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}