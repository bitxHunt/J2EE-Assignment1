package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.service.ServiceDAO;

@WebServlet("/admin/delete-service/*")
@MultipartConfig
public class DeleteServiceById extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private ServiceDAO serviceDAO;

  public DeleteServiceById() {
    super();
    serviceDAO = new ServiceDAO();
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      try {
          int serviceId = Integer.parseInt(request.getPathInfo().substring(1));
          boolean success = serviceDAO.deleteService(serviceId);
          
          if (success) {
              response.sendRedirect(request.getContextPath() + "/admin");
          } else {
              request.setAttribute("err", "Failed to delete service");
              request.getRequestDispatcher("/error/500").forward(request, response);
          }
      } catch (SQLException e) {
          // PostgreSQL foreign key violation has error code 23503
          if (e.getSQLState().equals("23503")) {            
              Map<String, String> errors = new HashMap<>();
              errors.put("deleteError", "This service cannot be deleted because it is currently part of active bundles or has existing schedules or transactions. As an alternative, you can set the service as inactive to hide it from customers.");
              
              HttpSession session = request.getSession();
              session.setAttribute("editServiceFormErrors", errors);
              response.sendRedirect(request.getContextPath() + "/admin/edit-service/" + 
                  request.getPathInfo().substring(1));
          } else {
              // Handle other SQL exceptions
              request.setAttribute("err", "Database error: " + e.getMessage());
              System.out.println(e.getMessage());
              request.getRequestDispatcher("/error/500").forward(request, response);
          }
      }
  }
}