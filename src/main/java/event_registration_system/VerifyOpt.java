/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event_registration_system;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VerifyOpt")
public class VerifyOpt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String otp = request.getParameter("verification");
        String email = request.getParameter("email");
        System.out.println("VerifyOpt api is executed" + otp);
        try {
            if (OPTHandler.validateOTP(email, otp)) {
                response.sendRedirect("Login/ResetPassword.jsp");
            } else {
                request.setAttribute("otpSent", null);
                response.sendRedirect("Login/ForgetPassword.jsp?error=wrongOtp");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
