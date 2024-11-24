/*
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
*/

package servlets.auth;

import models.user.User;
import models.user.UserDAO;
import util.org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
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
		if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
			response.sendRedirect(request.getContextPath() + "/profile");
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
		try {
			HttpSession session = request.getSession();
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			UserDAO userDB = new UserDAO();
			User user = userDB.loginUser(email);

			if (BCrypt.checkpw(password, user.getPassword())) {
				System.out.println("Password Matched");
			}

			if (user != null && BCrypt.checkpw(password, user.getPassword())) {
				System.out.println("Login Successful");
				session.setAttribute("userId", user.getId());
				session.setAttribute("role", user.getRole());
				session.setAttribute("profileImg", user.getImageURL());
				
				String imageUrl = (String) session.getAttribute("profileImg");
				System.out.println("Profile" + imageUrl);
				
				if (user.getRole() == 1) {
					response.sendRedirect(request.getContextPath() + "/admin");
				} else {
					response.sendRedirect(request.getContextPath() + "/profile");
				}
			} else {
				System.out.println("Invalid Password");
				request.setAttribute("err", "Invalid Email or Password");
				RequestDispatcher rd = request.getRequestDispatcher("/user/login.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("/user/login.jsp");
			rd.forward(request, response);
		}
	}

}
