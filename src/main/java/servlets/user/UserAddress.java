package servlets.user;

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
 * Servlet implementation class UserAddress
 */
@WebServlet("/address")
public class UserAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserAddress() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);	

		if (session != null && session.getAttribute("userId") != null && session.getAttribute("role") != null) {
			Integer userId = (Integer) session.getAttribute("userId");
			String strAddId = request.getParameter("address_type");
			Integer addressId = Integer.parseInt(strAddId);

			AddressDAO addressDB = new AddressDAO();
			try {
				Address address = addressDB.getAddressByUserId(userId, addressId);

				request.setAttribute("address", address);
				RequestDispatcher rd = request.getRequestDispatcher("/user/booking.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("errMsg", "Please log in or register");
			RequestDispatcher rd = request.getRequestDispatcher("/user/booking.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
