<%@ page import="java.util.List" %>
<%@page import="event_registration_system.Event"%>
<%
    List<Event> events = (List<Event>) request.getAttribute("filteredEvents");
%>

<!DOCTYPE html>
<html>
    <body>
        <div class="mt-10">
            <h2 class="">Search Events</h2>


            <form action="/Event-Registration-System/EventSearchServlet" method="GET">
                <input type="text" name="name" placeholder="Event Name">
                <input type="text" name="city" placeholder="City">
                <input type="date" name="date">
                <button type="submit">Search</button>
            </form>

            <br>
            <% if (events != null) {%>
            <%--<%@include file="Search/SearchResults.jsp" %>--%>
            <jsp:include page="Search/SearchResults.jsp" />
            <%}%>
        </div>
    </body>
</html>
