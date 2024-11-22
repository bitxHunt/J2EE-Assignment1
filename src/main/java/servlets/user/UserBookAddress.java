package servlets.user;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.address.Address;
import models.address.AddressDAO;

import java.io.IOException;

/**
 * Servlet implementation class UserBookAddress
 */
@WebServlet("/book/hsj")
public class UserBookAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBookAddress() {
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
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		try {
			System.out.println("Running address handler route");
			String selectedSlot = request.getParameter("selected_slot");
			if (selectedSlot != null) {
				session.setAttribute("selectedSlot", selectedSlot);
			}

			Integer userId = (Integer) session.getAttribute("userId");
			if (userId == null) {
				throw new IllegalStateException("User not logged in");
			}

			AddressDAO addDB = new AddressDAO();
			Address homeAddress = addDB.getAddressByUserId(userId, 1);
			Address officeAddress = addDB.getAddressByUserId(userId, 2);

			request.setAttribute("homeAddress", homeAddress);
			request.setAttribute("officeAddress", officeAddress);
			request.getRequestDispatcher("/user/bookAddress.jsp").forward(request, response);
		} catch (IllegalStateException e) {
			response.sendRedirect(request.getContextPath() + "/login");
		} catch (Exception e) {
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

}
