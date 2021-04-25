<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>

<%@ page import="by.epam.jwd.cyberbets.domain.TransactionType" %>

<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/head.html" %>
    <title>CYBERBETS | TRANSACTIONS</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="../general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>


<div class="container wrapper my-5">
    <div class="row">
        <div class="header col-12 d-flex justify-content-start align-items-center">
            <i class="fas fa-angle-double-right"></i>
            <span class="ms-2 text-uppercase fw-bold">
                <fmt:message key="page.user.transactions.header"/>
            </span>
        </div>

        <c:choose>
            <c:when test="${empty accountTransactions}">
                <div class="general-container d-flex justify-content-center align-items-center col">
                    <h1 class="text-secondary fw-bold"><fmt:message key="transaction_page.no_data"/></h1>
                </div>
            </c:when>
            <c:otherwise>
                <div class="general-container col">
                    <div class="transaction-container-header mb-3 text-uppercase fw-bold">
                        <div class="row d-flex justify-content-center align-items-center">
                            <span class="col-5"><fmt:message key="transaction_page.container_header.date"/></span>
                            <span class="text-center col-3"><fmt:message
                                    key="transaction_page.container_header.type"/></span>
                            <span class="text-center col-4"><fmt:message
                                    key="transaction_page.container_header.amount"/></span>
                        </div>
                    </div>

                    <c:forEach items="${accountTransactions}" var="transaction">
                        <div class="transaction">
                            <div class="row d-flex justify-content-center align-items-center">
                            <span class="col-5"><datetime:format value="${transaction.date}"
                                                                 pattern="dd.MM.yyyy HH:mm"/></span>
                                <span class="d-flex justify-content-center align-items-center col-3">
                                <c:choose>
                                    <c:when test="${transaction.transactionType eq TransactionType.DEPOSIT}">
                                        <span class="me-2 d-none d-sm-block"><fmt:message key="transaction_page.deposit"/></span>
                                        <img class="transacion-type-icon" src="/resources/assets/interface/plus.png">
                                    </c:when>
                                    <c:when test="${transaction.transactionType eq TransactionType.WITHDRAW}">
                                        <span class="me-2 d-none d-sm-block"><fmt:message key="transaction_page.widthdraw"/></span>
                                        <img class="transacion-type-icon" src="/resources/assets/interface/minus.png">
                                    </c:when>
                                </c:choose>
                        </span>
                        <span class="text-center col-4">
                            <i class="fas fa-dollar-sign"></i>
                            <span class="amount">
                                <fmt:formatNumber value="${transaction.amount}"
                                                  minIntegerDigits="1"
                                                  minFractionDigits="2" groupingUsed="false"/>
                            </span>
                        </span>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../general/footer.jsp"/>

<%@ include file="../general/html/scripts.html" %>
</body>
</html>
