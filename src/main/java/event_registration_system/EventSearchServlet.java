package event_registration_system;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/EventSearchServlet")
public class EventSearchServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String city = request.getParameter("city");
        String date = request.getParameter("date");

        List<Event> events = new ArrayList<>();
        List<Event> filteredEvents = new ArrayList<>();

        try {
            System.out.println("Name: " + name + " city: " + city + " date: " + date);
            events = EventDAO.getAllEvents();
            System.out.println("All events are: " + events);
            // needs to filter result based on the search option selected

            filteredEvents = events.stream()
                    .filter(event -> (name == null || name.isEmpty() || event.getEventName().toLowerCase().contains(name.toLowerCase())))
                    .filter(event -> (city == null || city.isEmpty() || event.getEventLocation().toLowerCase().contains(city.toLowerCase())))
                    .filter(event -> (date == null || date.isEmpty() || event.getEventDate().equals(date)))
                    .collect(Collectors.toList());

            System.out.println("Filtered events: " + filteredEvents);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response.isCommitted()) {
            System.out.println("Response already committed");
        }

        request.setAttribute("event", filteredEvents);
        request.getRequestDispatcher("/Search/SearchResults.jsp").forward(request, response);
    }
}
