/***********************************************************
 * Name: Soe Zaw Aung, Scott
 * Class: DIT/FT/2B/01
 * Admin No: P2340474
 ************************************************************/
package servlets.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.category.CategoryDAO;

@WebServlet("/admin/delete-category/*")
public class DeleteCategoryById extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;

    public DeleteCategoryById() {
        super();
        categoryDAO = new CategoryDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getPathInfo().substring(1));
            boolean success = categoryDAO.deleteCategory(categoryId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                request.setAttribute("err", "Failed to delete category");
                request.getRequestDispatcher("/error/500").forward(request, response);
            }
        } catch (SQLException e) {
        	 // PostgreSQL foreign key violation has error code 23503
            if (e.getSQLState().equals("23503")) {
                Map<String, String> errors = new HashMap<>();
                errors.put("deleteError", 
                    "This ");

                HttpSession session = request.getSession();
                session.setAttribute("editBundleFormErrors", errors);
                response.sendRedirect(request.getContextPath() + "/admin/edit-bundle/" + 
                    request.getPathInfo().substring(1));
            } else {
                request.setAttribute("err", "Database error: " + e.getMessage());
                System.out.println(e.getMessage());
                request.getRequestDispatcher("/error/500").forward(request, response);
            }
            
        }
    }
}