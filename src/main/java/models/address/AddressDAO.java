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

public class AddressDAO {
	public Address getAddressByUserId(int userId, int addTypeId) throws SQLException {
		Connection conn = DB.connect();
		Address address = new Address();
		try {
			String sqlStr = "SELECT at.id, at.name, a.address_id, a.user_id, a.address, a.postal_code, a.unit FROM address a INNER JOIN address_type at ON a.type_id = at.type_id WHERE a.user_id = ? AND a.type_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, addTypeId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				AddressType addType = new AddressType();
				addType.setId(rs.getInt("id"));
				addType.setName(rs.getString("name"));
				;
				address.setId(rs.getInt("address_id"));
				address.setAddType(addType);
				address.setUserId(rs.getInt("user_id"));
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return address;
	}

//	public int createAddressByUserId(String street, String unit, int postalCode, int userId, int addTypeId)
//			throws SQLException {
//
//		Connection conn = DB.connect();
//		int rowsAffected = 0;
//
//		try {
//			String sqlStr = "INSERT INTO address (street, unit, postal_code, user_id, address_type_id) VALUES (?, ?, ?, ?, ?);";
//			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
//
//			pstmt.setString(1, street);
//			pstmt.setString(2, unit);
//			pstmt.setInt(3, postalCode);
//			pstmt.setInt(4, userId);
//			pstmt.setInt(5, addTypeId);
//
//			rowsAffected = pstmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			conn.close();
//		}
//		return rowsAffected;
//	}

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
