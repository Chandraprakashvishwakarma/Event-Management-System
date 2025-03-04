<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.Enumeration"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>log out</title>
    </head>
    <body>
        <%
            HttpSession currentSession = request.getSession();

            if (currentSession != null) {
                Enumeration<String> attributeNames = currentSession.getAttributeNames();
                while (attributeNames.hasMoreElements()) {
                    String attributeName = attributeNames.nextElement();
                    currentSession.removeAttribute(attributeName);
                }
                currentSession.invalidate();
            }

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/Event-Registration-System");
                    response.addCookie(cookie);
                }
            }

            response.sendRedirect("../index.jsp");
        %>
    </body>
</html>
