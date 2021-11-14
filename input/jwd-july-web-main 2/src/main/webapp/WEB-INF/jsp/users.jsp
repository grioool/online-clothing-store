<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:setLocale value="ru"/>
<%--value="${language}"--%>
<fmt:setBundle basename="locale" var="loc"/>

<fmt:message bundle="${loc}" key="listusers" var="listusers"/>
<fmt:message bundle="${loc}" key="password" var="password"/>

<html>
<head>
    <title>Users Page</title>
</head>
<body>
<h2>${listusers}</h2>
<h2>${password}</h2>
<c:if test="${not empty requestScope.users}">
    <ul>
        <c:forEach var="user" items="${requestScope.users}">
            <li>${user.name}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>
