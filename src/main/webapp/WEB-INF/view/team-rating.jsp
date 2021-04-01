<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="general/head.html" %>
    <title>CYBERBETS | HOME</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>


<div class="support container wrapper my-5">
    <div class="row">
        <div class="header col-12 d-flex justify-content-start align-items-center">
            <i class="fas fa-angle-double-right"></i>
            <span class="ms-2 text-uppercase fw-bold">
                <fmt:message key="page.team_rating.header"/>
            </span>
        </div>
    </div>
</div>


<jsp:include page="general/footer.jsp"/>

<%@ include file="general/scripts.html" %>
</body>
</html>
