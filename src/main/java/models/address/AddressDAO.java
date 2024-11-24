package models.address;

import util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAO {
	public Address getAddressByUserId(Integer userId, Integer addTypeId) throws SQLException {
		Connection conn = DB.connect();
		Address address = new Address();
		try {
			String sqlStr = "SELECT at.address_type, a.address_id, a.user_id, a.address, a.postal_code, a.unit, a.is_active FROM address a INNER JOIN address_type at ON a.type_id = at.type_id WHERE a.user_id = ? AND a.type_id = ?;";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, addTypeId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				address.setAddType(rs.getString("address_type"));
				address.setId(rs.getInt("address_id"));
				address.setUserId(rs.getInt("user_id"));
				address.setAddress(rs.getString("address"));
				address.setPostalCode(rs.getInt("postal_code"));
				address.setUnit(rs.getString("unit"));
				address.setIsActive(rs.getBoolean("is_active"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
		return address;
	}

	public void createAddressByUserId(Integer userId, Integer addTypeId, String address, Integer postalCode, String unit)
			throws SQLException {
		Connection conn = DB.connect();
		try {
			String sqlStr = "INSERT INTO address (user_id, type_id, address, postal_code, unit) VALUES (?, ?, ?, ?, ?);";
			PreparedStatement pstmt = conn.prepareStatement(sqlStr);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, addTypeId);
			pstmt.setString(3, address);
			pstmt.setInt(4, postalCode);
			pstmt.setString(5, unit);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
		}
	}

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
}
