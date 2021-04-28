<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/admin-head.html" %>
    <title>CYBERBETS | ADMIN PANEL</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="../general/admin-sidebar.jsp"/>

<main class="d-flex flex-column min-vh-100">
    <jsp:include page="../general/admin-navbar.jsp"/>

    <div class="container-fluid wrapper my-3">
        <div class="row">
            <div class="header col-12 d-flex justify-content-start align-items-center">
                <i class="fas fa-angle-double-right"></i>
                <span class="ms-2 text-uppercase fw-bold">
                    <fmt:message key="admin_panel.navigation.dashboard" />
                </span>
            </div>
            <div id="dashboardContainer px-5">
                <div class="row mt-3">
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/users.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.accounts_number" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">${accountsNumber}</h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/registration.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.month_registrations" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">+ ${monthRegistrations}</h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/deposit.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.month_deposits" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">
                                    $ <fmt:formatNumber value="${monthDepositTransactionsAmount}"
                                                      minIntegerDigits="1"
                                                      minFractionDigits="2" groupingUsed="true"/>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/income.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.month_profit" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">
                                    $ <fmt:formatNumber value="${monthProfit}"
                                                        minIntegerDigits="1"
                                                        minFractionDigits="2" groupingUsed="false"/>
                                </h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/schedule.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.active_events_number" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">${activeEventsNumber}</h1>
                            </div>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-xl-4">
                        <div class="dashboard-card d-flex align-items-center">
                            <div class="circle d-flex justify-content-center align-items-center ms-2">
                                <img class="icon" src="/resources/assets/dashboard/coin.png">
                            </div>
                            <div class="d-flex flex-column justify-content-center mx-auto">
                                <span class="fw-bold text-center">
                                    <fmt:message key="dashboard_page.active_events_bets_amount" />
                                </span>
                                <h1 class="fw-bold text-center mt-2">
                                    $ <fmt:formatNumber value="${activeEventsBetsAmount}"
                                                        minIntegerDigits="1"
                                                        minFractionDigits="2" groupingUsed="false"/>
                                </h1>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../general/admin-footer.jsp"/>
</main>

<%@ include file="../general/html/admin-scripts.html" %>
</body>
</html>
