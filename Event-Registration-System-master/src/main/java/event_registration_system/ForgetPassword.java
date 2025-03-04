package event_registration_system;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ForgetPassword")
public class ForgetPassword extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String EMAIL = "ytsportsrepo@gmail.com";
    private static final String PASSWORD = "nvgj zebb rogo lwbg";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String recipientEmail = request.getParameter("email");
        System.out.println("Forget password api is executed with email " + recipientEmail);

        // Validate the user against the database using UserDAO
        String userId = UserDAO.checkEmailExists(recipientEmail);
        System.out.println("user id for " + recipientEmail + " is : " + userId);

        if (userId == null) {
            System.out.println("null check worked");
//            response.sendRedirect("Login/ForgetPassword.jsp?emailNotExist=true");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // Set 404 NOT FOUND
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User not found with Email: "+recipientEmail);
            return;
        }

        System.out.println("after code");
        String subject = "Verification code for EMS";

        String otp = OPTHandler.generateOTP(6);
        try {
            OPTHandler.storeOTP(recipientEmail, otp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sendOTP(recipientEmail, otp);
        request.setAttribute("otpSent", true);
        response.sendRedirect("Login/ForgetPassword.jsp");
    }

    public void sendOTP(String recipientEmail, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Your OTP Code");
            message.setText("Your OTP for password reset is: " + otp + "\nIt expires in 5 minutes.");

            Transport.send(message);
            System.out.println("OTP sent successfully to " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
