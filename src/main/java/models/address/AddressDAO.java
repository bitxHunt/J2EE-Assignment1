/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package models.address;

import util.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressDAO {

	public ArrayList<Address> getAddressByUserId(int userId, boolean status) throws SQLException {
		Connection conn = DB.connect();
		ArrayList<Address> addresses = new ArrayList<Address>();
		AddressType addType = new AddressType();

		try {
			String sqlStr = "SELECT * FROM address WHERE user_id = ? AND is_active = ?;";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			pstmt.setBoolean(2, status);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Address address = new Address();
				address.setId(Integer.parseInt(rs.getString("id")));
				address.setAddress(rs.getString("street"));
				address.setUnit(rs.getString("unit"));
				address.setPostalCode(rs.getInt("postal_code"));
				addType.setId(Integer.parseInt(rs.getString("address_type_id")));
				address.setAddType(addType);
				addresses.add(address);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return addresses;
	}

	public Address getAddressById(int addId) throws SQLException {
		Connection conn = DB.connect();
		Address address = new Address();
		AddressType addType = new AddressType();

		try {
			String sqlStr = "SELECT * FROM address WHERE id = ?;";

			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, addId);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				address.setId(Integer.parseInt(rs.getString("id")));
				address.setAddress(rs.getString("street"));
				address.setUnit(rs.getString("unit"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUserId(Integer.parseInt(rs.getString("user_id")));
				addType.setId(Integer.parseInt(rs.getString("address_type_id")));
				address.setAddType(addType);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return address;
	}

	// Changing the address status to inactive for soft update
	// If the address is updated using the UPDATE SET SQL, it will create
	// inconsistencies in the
	// booking history mapping to the wrong address

	public void updateAddressStatus(int userId, int addId, boolean status) throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "UPDATE address SET is_active = ? WHERE id = ? AND user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setBoolean(1, status);
			pstmt.setInt(2, addId);
			pstmt.setInt(3, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	// Used in conjunction with soft update and soft delete of the address

	public int createAddressByUserId(Address address, int userId, int addTypeId) throws SQLException {

		Connection conn = DB.connect();
		int rowsAffected = 0;

		try {
			String sqlStr = "INSERT INTO address (street, unit, postal_code, user_id, address_type_id) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);

			pstmt.setString(1, address.getAddress());
			pstmt.setString(2, address.getUnit());
			pstmt.setInt(3, address.getPostalCode());
			pstmt.setInt(4, userId);
			pstmt.setInt(5, addTypeId);

			rowsAffected = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return rowsAffected;
	}

	// Sample update of address if consistency were to be not considered {Not Used}
	public void updateAddressById(Integer userId, Integer addressId, String address, Integer postalCode, String unit)
			throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "UPDATE address SET address = ?, postal_code = ?, unit = ? WHERE address_id = ? AND user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setString(1, address);
			pstmt.setInt(2, postalCode);
			pstmt.setString(3, unit);
			pstmt.setInt(4, addressId);
			pstmt.setInt(5, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	// Sample deletion of address if consistency were to be not considered {Not
	// Used}
	public void deleteAddressById(Integer userId, Integer addressId) throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "DELETE FROM address WHERE address_id = ? AND user_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, addressId);
			pstmt.setInt(2, userId);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

	// Seeding Address Type Data
	public void createAddressType(AddressType addressType) throws SQLException {
		Connection conn = DB.connect();

		try {
			String sqlStr = "CALL seed_address_type(?);";
			CallableStatement stmt = conn.prepareCall(sqlStr);

			stmt.setString(1, addressType.getName());

			stmt.execute();

		} catch (Exception e) {
			System.out.println("Error Seeding Address Type Data.");
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}
}
