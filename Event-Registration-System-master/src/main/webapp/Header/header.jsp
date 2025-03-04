<%@page import="event_registration_system.UserDAO"%>
<%@page import="event_registration_system.User"%>
<%@ page import="java.util.List" %>
<%@page import="event_registration_system.Event"%>

<%
    List<Event> events = (List<Event>) request.getAttribute("filteredEvents");
%>

<script>
    function closeSearchWindow() {
        document.getElementById("headerDiv").style.display = "block";
        document.getElementById("searchDiv").style.display = "none";
    }
    function openSearchWindow() {
        document.getElementById("headerDiv").style.display = "none";
        document.getElementById("searchDiv").style.display = "block";
    }
</script>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Header</title>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;300;400;700;900&display=swap" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap-icons.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/slick.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/tooplate-little-fashion.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg">
            <div class="container" id="headerDiv">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <%
                    User user = (User) session.getAttribute("user");
                    if (user == null) {
                        Cookie[] cookies = request.getCookies();
                        if (cookies != null) {
                            Cookie cookie;
                            for (int i = 0; i < cookies.length; i++) {
                                cookie = cookies[i];
                                if (cookie.getName().equals("userID")) {
                                    user = UserDAO.getUser(cookie.getValue());
                                    session.setAttribute("user", user);
                                }
                            }
                        }
                    }
                %>

                <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">
                    <strong><span>Event Registration</span> System</strong>
                </a>

                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav mx-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/index.jsp">Home</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Event/Events.jsp">Events</a>
                        </li>
                        <% if (user == null) {%>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/FAQ/faq.jsp">FAQ</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Privacy/privacy.jsp">Privacy</a>
                        </li>

                        <%} else {%>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Event/MyEvents.jsp">My Events</a>
                        </li>
                        <div>
                            <% if (user.getIsAdmin()) {%>
                            <li class="nav-item">
                                <a class="nav-link" href="${pageContext.request.contextPath}/Account/AdminPanel.jsp">Admin Panel</a>
                            </li>
                            <%}%>
                        </div>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Event/CreateEvent.jsp">Create Event</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Account/Account.jsp">Account Settings</a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link">Welcome <%out.println(user.getUsername() + "!");%></a>
                        </li>

                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/Account/LogOut.jsp">Log-Out</a>
                        </li>
                        <%}%>
                        <li class="nav-item">
                            <a class="nav-link" onclick="openSearchWindow()">Search</a>
                        </li>
                    </ul>
                    <%if (user == null) {%>
                    <div class="d-none d-lg-block">
                        <a href="${pageContext.request.contextPath}/Login/login.jsp" class="bi-person custom-icon me-3"></a>
                    </div>
                    <%}%>
                </div>
            </div>
            <div class="container" id="searchDiv" style="display: none">
                <form action="/Event-Registration-System/EventSearchServlet" method="GET">
                    <input type="text" name="name" placeholder="Event Name">
                    <input type="text" name="city" placeholder="City">
                    <input type="date" name="date">
                    <button class="btn custom-btn" style="padding-left: 40px;padding-right: 40px " type="submit">Search</button>
                    <span class="btn custom-btn" style="padding-left: 40px;padding-right: 40px; margin-left: 39%" id="close" onclick="closeSearchWindow()"> X </span>
                </form>
                

                <br>
                <% if (events != null) {%>
                <%--<%@include file="Search/SearchResults.jsp" %>--%>
                <jsp:include page="Search/SearchResults.jsp" />
                <%}%>
            </div>
        </nav>

    </body>
</html>