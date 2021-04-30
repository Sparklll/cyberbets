<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="../general/html/head.html" %>
    <title>CYBERBETS | MY BETS</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="../general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>

<c:set var="csgoIcon" value="/resources/assets/disciplines/csgo_icon.png"/>
<c:set var="dota2Icon" value="/resources/assets/disciplines/dota2_icon.png"/>
<c:set var="lolIcon" value="/resources/assets/disciplines/lol_icon.png"/>
<c:set var="valorantIcon" value="/resources/assets/disciplines/valorant_icon.jpg"/>

<%@ page import="by.epam.jwd.cyberbets.domain.EventFormat" %>
<%@ page import="by.epam.jwd.cyberbets.domain.Discipline" %>
<%@ page import="by.epam.jwd.cyberbets.domain.ResultStatus" %>
<%@ page import="by.epam.jwd.cyberbets.domain.EventOutcomeType" %>

<div class="container wrapper my-5">
    <div class="row">
        <div class="header col-12 d-flex justify-content-start align-items-center">
            <i class="fas fa-angle-double-right"></i>
            <span class="ms-2 text-uppercase fw-bold">
                <fmt:message key="page.user.mybets.header"/>
            </span>
        </div>

        <c:choose>
            <c:when test="${empty betsData}">
                <div class="general-container d-flex justify-content-center align-items-center col">
                    <h1 class="text-secondary fw-bold"><fmt:message key="label.no_data"/></h1>
                </div>
            </c:when>
            <c:otherwise>
                <div class="general-container mybets-container col">
                    <div class="general-container-header mb-3 text-uppercase fw-bold">
                        <div class="row d-flex justify-content-center align-items-center">
                            <span class="col-4">
                                <fmt:message key="mybets_page.event" />
                            </span>
                            <span class="text-center col-4">
                                <fmt:message key="mybets_page.bet" />
                            </span>
                            <span class="text-center col-4">
                                <fmt:message key="mybets_page.result" />
                            </span>
                        </div>
                    </div>

                    <c:forEach items="${betsData}" var="betData">
                    <div class="custom-table-row">
                        <div class="row d-flex justify-content-center align-items-center">
                            <div class="d-flex flex-column col-4">
                                <div class="event-info-wrapper d-flex align-items-center">
                                    <div class="discipline-icon-wrapper me-3">
                                        <c:choose>
                                            <c:when test="${betData.event.discipline eq Discipline.CSGO}">
                                                <img class="discipline-icon" src="${csgoIcon}">
                                            </c:when>
                                            <c:when test="${betData.event.discipline eq Discipline.DOTA2}">
                                                <img class="discipline-icon" src="${dota2Icon}">
                                            </c:when>
                                            <c:when test="${betData.event.discipline eq Discipline.LOL}">
                                                <img class="discipline-icon" src="${lolIcon}">
                                            </c:when>
                                            <c:when test="${betData.event.discipline eq Discipline.VALORANT}">
                                                <img class="discipline-icon" src="${valorantIcon}">
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <div class="d-flex justify-content-center align-items-center">
                                        <img src="${betData.event.firstTeam.logoResource.path}" class="team me-1">
                                        <span>${betData.event.firstTeam.name}</span>
                                    </div>
                                    <span class="fw-bold fst-italic mx-3">VS</span>
                                    <div class="d-flex justify-content-center align-items-center">
                                        <img src="${betData.event.secondTeam.logoResource.path}" class="team me-1">
                                        <span>${betData.event.secondTeam.name}</span>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center mt-2">
                                    <div class="d-flex justify-content-center align-items-center">
                                        <c:choose>
                                            <c:when test="${betData.event.eventFormat eq EventFormat.BO1}">
                                                <span class="me-1">BO1 •</span>
                                            </c:when>
                                            <c:when test="${betData.event.eventFormat eq EventFormat.BO2}">
                                                <span class="me-1">BO2 •</span>
                                            </c:when>
                                            <c:when test="${betData.event.eventFormat eq EventFormat.BO3}">
                                                <span class="me-1">BO3 •</span>
                                            </c:when>
                                            <c:when test="${betData.event.eventFormat eq EventFormat.BO5}">
                                                <span class="me-1">BO5 •</span>
                                            </c:when>
                                        </c:choose>
                                        <span>${betData.event.league.name}</span>
                                    </div>
                                </div>
                                <div class="mt-2">
                                    <i class="fas fa-calendar-day me-1"></i>
                                    <span>
                                        <datetime:format value="${betData.bet.date}"
                                                         pattern="dd.MM.yyyy HH:mm"/>
                                    </span>
                                </div>
                            </div>


                            <span class="d-flex flex-column justify-content-center align-items-center col-4">
                                <span>
                                     <i class="fas fa-dollar-sign me-1"></i>
                                    <fmt:formatNumber value="${betData.bet.amount}"
                                                      minIntegerDigits="1"
                                                      minFractionDigits="2" groupingUsed="false"/>
                                </span>
                                <span class="text-center mt-2">
                                    <c:choose>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.TOTAL_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.total_winner" />
                                        </c:when>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.MAP1_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.map1_winner" />
                                        </c:when>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.MAP2_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.map2_winner" />
                                        </c:when>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.MAP3_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.map3_winner" />
                                        </c:when>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.MAP4_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.map4_winner" />
                                        </c:when>
                                        <c:when test="${betData.eventResult.eventOutcomeType eq EventOutcomeType.MAP5_WINNER}">
                                            <fmt:message key="bet_modal.event_outcome.map5_winner" />
                                        </c:when>
                                    </c:choose>
                                </span>
                                <span class="bet-upshot text-center mt-2">
                                    <c:choose>
                                        <c:when test="${betData.bet.upshot eq 'FIRST_UPSHOT'}">
                                            ${betData.event.firstTeam.name}
                                        </c:when>
                                        <c:when test="${betData.bet.upshot eq 'SECOND_UPSHOT'}">
                                            ${betData.event.secondTeam.name}
                                        </c:when>
                                    </c:choose>
                                </span>
                            </span>

                            <span class="text-center col-4">
                                <c:choose>
                                    <c:when test="${betData.eventResult.resultStatus ne ResultStatus.FIRST_UPSHOT
                                                    and betData.eventResult.resultStatus ne ResultStatus.SECOND_UPSHOT}">
                                        <img src="/resources/assets/bet-status/wait.png" width="30">
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${(betData.bet.upshot eq 'FIRST_UPSHOT' and betData.eventResult.resultStatus eq ResultStatus.FIRST_UPSHOT)
                                                            or (betData.bet.upshot eq 'SECOND_UPSHOT' and betData.eventResult.resultStatus eq ResultStatus.SECOND_UPSHOT)}">
                                                <span class="text-success">
                                                    <i class="fas fa-plus me-1"></i>
                                                    <i class="fas fa-dollar-sign"></i>
                                                    <fmt:formatNumber value="${betData.bet.amount * betData.betCoefficient}"
                                                                      minIntegerDigits="1"
                                                                      minFractionDigits="2"
                                                                      maxFractionDigits="2"
                                                                      groupingUsed="false" />
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text-danger">
                                                <i class="fas fa-minus"></i>
                                                <i class="fas fa-dollar-sign"></i>
                                                <fmt:formatNumber value="${betData.bet.amount}"
                                                                  minIntegerDigits="1"
                                                                  minFractionDigits="2" groupingUsed="false" />
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
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
