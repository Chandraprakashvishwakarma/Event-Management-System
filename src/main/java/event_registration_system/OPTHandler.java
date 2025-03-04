/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package event_registration_system;

/**
 *
 * @author chandraprakash
 */
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class OPTHandler {

    static Connection connection = null;
    static PreparedStatement stmt = null;
    static ResultSet rs = null;

    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP(double size) {
        int otp = (int) Math.pow(10, size-1) + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    public static void storeOTP(String email, String otp) throws SQLException {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5); // OTP expires in 5 minutes

        try {
            connection = DatabaseConnection.getConnection();
            stmt = connection.prepareStatement(
                    "INSERT INTO otp_store (user_email, otp_code, expiry_time) VALUES (?, ?, ?) "
                    + "ON DUPLICATE KEY UPDATE otp_code = ?, expiry_time = ?");

            stmt.setString(1, email);
            stmt.setString(2, otp);
            stmt.setTimestamp(3, Timestamp.valueOf(expiryTime));
            stmt.setString(4, otp);
            stmt.setTimestamp(5, Timestamp.valueOf(expiryTime));
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static boolean validateOTP(String email, String enteredOTP) throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
            stmt = connection.prepareStatement(
                    "SELECT otp_code, expiry_time FROM otp_store WHERE user_email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String storedOTP = rs.getString("otp_code");
                LocalDateTime expiryTime = rs.getTimestamp("expiry_time").toLocalDateTime();

                if (LocalDateTime.now().isBefore(expiryTime) && storedOTP.equals(enteredOTP)) {
                    deleteOTP(email); // OTP is valid, delete it from DB
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return false; // OTP expired or incorrect
    }

    private static void deleteOTP(String email) throws SQLException {
        try {
            connection = DatabaseConnection.getConnection();
            stmt = connection.prepareStatement("DELETE FROM otp_store WHERE user_email = ?");
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
