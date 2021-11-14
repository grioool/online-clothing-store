<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jwdt" uri="jwdTags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="ru"/>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="greeting" var="greeting"/>
<fmt:message bundle="${loc}" key="listusers" var="listusers"/>

<%@ page import="com.epam.jwd.dao.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<jwdt:welcomeText/>
<br>
<h1>${greeting}</h1>
<h1>${listusers}</h1>
<br>
<c:choose>
    <c:when test="${empty sessionScope.userName}">
        <a href="${pageContext.request.contextPath}/controller?command=show_login">Login page</a>
    </c:when>
    <c:otherwise>
        <c:if test="${sessionScope.userRole eq Role.ADMIN}">
            <p>Please click below to see all users:</p>
            <a href="${pageContext.request.contextPath}/controller?command=show_users">Users page</a>
            <br>
        </c:if>
        <a href="${pageContext.request.contextPath}/controller?command=logout">Logout</a>
    </c:otherwise>
</c:choose>
<jwdt:currentTime/>
</body>
</html>
