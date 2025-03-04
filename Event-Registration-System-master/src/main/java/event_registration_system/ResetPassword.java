package event_registration_system;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ResetPassword")
public class ResetPassword extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ResetPassword api is executed");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate the user against the database using UserDAO
        String userId = UserDAO.checkEmailExists(email);
        System.out.println("user id for "+email +" is : "+userId);
        
        if (userId == null) {
            response.sendRedirect("Login/ResetPassword.jsp?emailNotExist=true");
        } else {
            User user = UserDAO.getUser(userId);
            String hashedPassword = Hash.hashPassword(password);
            user.setPassword(hashedPassword);
            UserDAO.updateUser(user);
            response.sendRedirect("Login/login.jsp");
        }
    }
}
