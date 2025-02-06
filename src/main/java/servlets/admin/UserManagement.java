/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.role.Role;
import models.role.RoleDAO;
import models.user.User;
import models.user.UserDAO;
import util.org.mindrot.jbcrypt.BCrypt;

@WebServlet("/admin/manage-users")
public class UserManagement extends HttpServlet {
	private static final int USERS_PER_PAGE = 10;
	private UserDAO userDAO = new UserDAO();
	private RoleDAO roleDAO = new RoleDAO();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int page = 1;
			String pageStr = request.getParameter("page");
			if (pageStr != null && !pageStr.isEmpty()) {
				page = Integer.parseInt(pageStr);
			}

			int totalUsers = userDAO.getTotalUserCount();
			int totalPages = (int) Math.ceil((double) totalUsers / USERS_PER_PAGE);

			if (page > totalPages) {
				response.sendRedirect(request.getContextPath() + "/error/404ErrorPage.jsp");
				return;
			}

			ArrayList<User> users = userDAO.getUsersWithPagination(page, USERS_PER_PAGE);
			ArrayList<Role> roles = roleDAO.getAllRoles();

			request.setAttribute("users", users);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("roles", roles);

			request.getRequestDispatcher("userManagement.jsp").forward(request, response);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/error/400ErrorPage.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		try {
			switch (action) {
			case "add":
				handleAddUser(request, response);
				break;
			case "edit":
				handleEditUser(request, response);
				break;
			case "delete":
				handleDeleteUser(request, response);
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	private void handleAddUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String phoneNo = request.getParameter("phoneNo");
		int roleId;

		try {
			roleId = Integer.parseInt(request.getParameter("role"));
		} catch (NumberFormatException e) {	
			request.getSession().setAttribute("userManagementErrorMsg", "Invalid role selected.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		}

		// Input validation
		if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()
				|| email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()
				|| phoneNo == null || phoneNo.trim().isEmpty()) {
			HttpSession session = request.getSession();
			session.setAttribute("userManagementMsg", "Registration failed. All fields are required.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		}

		try {
			// Hash password
			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));

			// Create UserDAO instance
			UserDAO userDAO = new UserDAO();

			// Register the user with role_id
			Integer result = userDAO.registerUserWithRole(firstName.trim(), lastName.trim(), email.trim(),
					hashedPassword, phoneNo.trim(), roleId);

			if (result > 0) {
				// Registration successful
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Registration Successful.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			} else {
				// Registration failed
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Registration failed. Please try again.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			}

		} catch (SQLException e) {
			// PostgreSQL unique constraint violation has error code 23505
			if (e.getSQLState().equals("23505")) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Registeration failed. Email already exists.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			} else {
				request.setAttribute("err", "Database error: " + e.getMessage());
				System.out.println(e.getMessage());
				request.getRequestDispatcher("/error/500").forward(request, response);
			}
		}
	}

	private void handleEditUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		if (request.getParameter("userId") == null || request.getParameter("userId").trim().isEmpty()
				|| request.getParameter("currentRoleId") == null
				|| request.getParameter("currentRoleId").trim().isEmpty() || request.getParameter("editRoleId") == null
				|| request.getParameter("editRoleId").trim().isEmpty()) {

			HttpSession session = request.getSession();
			session.setAttribute("userManagementMsg", "All fields are required.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		}
		int userId, currentRoleId, editRoleId;

		try {
			userId = Integer.parseInt(request.getParameter("userId"));
			currentRoleId = Integer.parseInt(request.getParameter("currentRoleId"));
			editRoleId = Integer.parseInt(request.getParameter("editRoleId"));

			// Basic validation
			if (userId <= 0 || currentRoleId <= 0 || editRoleId <= 0) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Invalid ID values provided.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			}

			// Prevent changing admin role
			if (currentRoleId == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Cannot modify admin role.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			}
			boolean success = userDAO.updateUserRole(userId, editRoleId);

			if (success) {
				// Edit Role successful
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Role updated Successfully.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			} else {
				// Edit Role failed
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Role update failed. Please try again.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			}
		} catch (NumberFormatException e) {
			HttpSession session = request.getSession();
			session.setAttribute("userManagementMsg", "Invalid input format for IDs.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			System.out.println(e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}

	}

	private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		// Check for required parameters
		if (request.getParameter("userId") == null || request.getParameter("userId").trim().isEmpty()
				|| request.getParameter("currentRoleId") == null
				|| request.getParameter("currentRoleId").trim().isEmpty()) {

			HttpSession session = request.getSession();
			session.setAttribute("userManagementMsg", "All fields are required.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		}

		int userId, currentRoleId;

		try {
			userId = Integer.parseInt(request.getParameter("userId"));
			currentRoleId = Integer.parseInt(request.getParameter("currentRoleId"));

			// Basic validation
			if (userId <= 0 || currentRoleId <= 0) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Invalid ID values provided.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			}

			// Prevent deleting admin
			if (currentRoleId == 1) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Cannot delete admin user.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
				return;
			}

			boolean success = userDAO.deleteUser(userId);

			if (success) {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "User deleted successfully.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			} else {
				HttpSession session = request.getSession();
				session.setAttribute("userManagementMsg", "Delete failed. Please try again.");
				response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			}

		} catch (NumberFormatException e) {
			HttpSession session = request.getSession();
			session.setAttribute("userManagementMsg", "Invalid input format for IDs.");
			response.sendRedirect(request.getContextPath() + "/admin/manage-users");
			return;
		} catch (SQLException e) {
			request.setAttribute("err", "Database error: " + e.getMessage());
			System.out.println(e.getMessage());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}
}