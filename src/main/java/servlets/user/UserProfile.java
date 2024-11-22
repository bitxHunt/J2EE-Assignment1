package servlets.user;

import models.user.User;
import models.user.UserDAO;
import models.address.Address;
import models.address.AddressDAO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class UserProfile
 */
@WebServlet("/profile")
public class UserProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserProfile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);	
		System.out.println("Session user: " + session.getAttribute("userId"));
		System.out.println("Session role: " + session.getAttribute("role"));
		if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
			Integer userId = (Integer) session.getAttribute("userId");

			UserDAO userDB = new UserDAO();
			AddressDAO addDB = new AddressDAO();
			try {
				User user = userDB.getUserById(userId);
				Address homeAddress = addDB.getAddressByUserId(userId, 1);
				Address officeAddress = addDB.getAddressByUserId(userId, 2);
				request.setAttribute("user", user);
				request.setAttribute("homeAddress", homeAddress);
				request.setAttribute("officeAddress", officeAddress);
				RequestDispatcher rd = request.getRequestDispatcher("/user/profile.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/user/login.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
