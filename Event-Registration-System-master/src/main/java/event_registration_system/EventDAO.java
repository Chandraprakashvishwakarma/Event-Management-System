package event_registration_system;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    // Methods for CRUD operations on the events table

    public static boolean checkEventNameExists(String eventName, String organizerID) throws SQLException{
        //  Prepare query to search database for user's event name
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM events WHERE eventName LIKE ? and organizerID = ?");
            preparedStatement.setString(1, eventName);
            preparedStatement.setString(2, organizerID);

            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // If found any, return to indicate true
                return resultSet.next();
            }
        } catch (SQLException e) {
            // If no event name found for given user, return false
            return false;
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public static int createEvent(Event event) throws ParseException,SQLException {
        if (checkEventNameExists(event.getEventName(), event.getOrganizerID())) {
            // -1 : User already created with same Event Name
            return -1;
        }

        //  Prepare query to create new event
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
             connection = DatabaseConnection.getConnection(); 
             preparedStatement = connection.prepareStatement(
                "INSERT INTO events (eventID, eventName, eventDate, eventTime, eventLocation, maxParticipant, shortDescription, longDescription, organizerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, event.getEventID());
            preparedStatement.setString(2, event.getEventName());
            preparedStatement.setString(3, event.getEventDate());
            preparedStatement.setString(4, event.getEventTime());
            preparedStatement.setString(5, event.getEventLocation());
            preparedStatement.setInt(6, event.getMaxParticipant());
            preparedStatement.setString(7, event.getShortDescription());
            preparedStatement.setString(8, event.getLongDescription());
            preparedStatement.setString(9, event.getOrganizerID());

            // Execute query
            int rowsAffected = preparedStatement.executeUpdate();

            // If there is no error, register User as an Host of the created event
            UserEventsDAO.createUserEvents(event.getOrganizerID(), event.getEventID(), "Hosting");

            // Return rowsAffected to indicate ">0"
            // >0 : Successfull registration
            return rowsAffected;
        } catch (SQLException e) {
            e.printStackTrace();
            // 0  : SQL query or database connection failed
            return 0;
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public static int deleteEvent(String eventID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // Prepare query to delete an event
        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement(
                "DELETE FROM events WHERE eventID = ?");

            preparedStatement.setString(1, eventID);

            // Execute query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return rowsAffected to indicate ">0"
            // >0 : Successful deletion
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
            // 0 : SQL query or database connection failed
            return 0;
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public static int updateEvent(Event event) throws SQLException {
        // Prepare query to update event information
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DatabaseConnection.getConnection(); 
            preparedStatement = connection.prepareStatement(
                "UPDATE events SET eventName = ?, eventDate = ?, eventTime = ?, eventLocation = ?, maxParticipant = ?, shortDescription = ?, longDescription = ? WHERE eventID = ?");
            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setString(2, event.getEventDate());
            preparedStatement.setString(3, event.getEventTime());
            preparedStatement.setString(4, event.getEventLocation());
            preparedStatement.setInt(5, event.getMaxParticipant());
            preparedStatement.setString(6, event.getShortDescription());
            preparedStatement.setString(7, event.getLongDescription());
            preparedStatement.setString(8, event.getEventID());

            // Execute query
            int rowsAffected = preparedStatement.executeUpdate();

            // Return rowsAffected to indicate ">0"
            // >0 : Successful update
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
            // 0 : SQL query or database connection failed
            return 0;
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public static List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Prepare query to select all events
        try {
            connection = DatabaseConnection.getConnection();  
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM events ORDER BY eventDate ASC, eventTime ASC"); ResultSet resultSet = preparedStatement.executeQuery();
            // Iterate through the result set and create Event objects
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getString("eventID"),
                        resultSet.getString("eventName"),
                        resultSet.getString("eventDate"),
                        resultSet.getString("eventTime"),
                        resultSet.getString("eventLocation"),
                        resultSet.getInt("maxParticipant"),
                        resultSet.getString("shortDescription"),
                        resultSet.getString("longDescription"),
                        resultSet.getString("organizerID")
                );

                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors or database connection issues
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }

        return events;
    }

    public static Event getEvent(String eventID) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        // Prepare query to search database for user with given userID
        try {
            connection = DatabaseConnection.getConnection(); 
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM events WHERE eventID = ?");
            preparedStatement.setString(1, eventID);

            // Execute query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // If user found, return User object
                    Event event = new Event(
                            resultSet.getString("eventID"),
                            resultSet.getString("eventName"),
                            resultSet.getString("eventDate"),
                            resultSet.getString("eventTime"),
                            resultSet.getString("eventLocation"),
                            resultSet.getInt("maxParticipant"),
                            resultSet.getString("shortDescription"),
                            resultSet.getString("longDescription"),
                            resultSet.getString("organizerID")
                    );
                    return event;
                } else {
                    return null; // Return null to indicate no event found
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
        }
    }

    public static List<Event> getAllEventsRandomly() throws SQLException {
        List<Event> events = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // Prepare query to select all events
        try {
            connection = DatabaseConnection.getConnection(); 
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM events ORDER BY RAND();"); 
            resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Event objects
            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getString("eventID"),
                        resultSet.getString("eventName"),
                        resultSet.getString("eventDate"),
                        resultSet.getString("eventTime"),
                        resultSet.getString("eventLocation"),
                        resultSet.getInt("maxParticipant"),
                        resultSet.getString("shortDescription"),
                        resultSet.getString("longDescription"),
                        resultSet.getString("organizerID")
                );

                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL errors or database connection issues
        }
        finally{
            if(connection != null) connection.close();
            if(preparedStatement != null) preparedStatement.close();
            if(resultSet != null) resultSet.close();
        }

        return events;
    }

    public static int getParticipant(String eventID) {
        return UserEventsDAO.getUserEventsByEventID(eventID, "Joined").size();

    }
}
