<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <%@ include file="general/html/head.html" %>
    <title>CYBERBETS | HOME</title>
</head>

<body class="d-flex flex-column min-vh-100">

<jsp:include page="general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
    <jsp:param name="balance" value="${balance}"/>
</jsp:include>

<div class="discipline-filter container my-5">
    <div class="row">
        <div class="col-6 col-md-3">
            <div class="discipline" data-discipline="csgo" role="checkbox">
                <img src="/resources/assets/disciplines/csgo_bg.jpg">
                <div class="discipline-logo-overlay">
                    <img src="/resources/assets/disciplines/csgo_logo.png">
                </div>
            </div>
        </div>
        <div class="col-6 col-md-3">
            <div class="discipline" data-discipline="dota2" role="checkbox">
                <img src="/resources/assets/disciplines/dota2_bg.jpg">
                <div class="discipline-logo-overlay">
                    <img src="/resources/assets/disciplines/dota2_logo.png">
                </div>
            </div>
        </div>
        <div class="col-6 col-md-3 mt-4 mt-md-0">
            <div class="discipline" data-discipline="lol" role="checkbox">
                <img src="/resources/assets/disciplines/lol_bg.jpg">
                <div class="discipline-logo-overlay">
                    <img src="/resources/assets/disciplines/lol_logo.png">
                </div>
            </div>
        </div>
        <div class="col-6 col-md-3 mt-4 mt-md-0">
            <div class="discipline" data-discipline="valorant" role="checkbox">
                <img src="/resources/assets/disciplines/valorant_bg.jpg">
                <div class="discipline-logo-overlay">
                    <img src="/resources/assets/disciplines/valorant_logo.png">
                </div>
            </div>
        </div>
    </div>
</div>

<div id="eventsContainer" class="events container mb-5" data-auth="${auth}">
    <div class="row">
        <div class="col-12 wrapper">
            <nav>
                <div class="nav nav-tabs" id="eventsTab" role="tablist">
                    <button class="nav-link col-6 col-lg-3 active" id="currentEventsTab" data-bs-toggle="tab"
                            data-bs-target="#currentEvents" type="button" role="tab" aria-selected="true">
                        <i class="fas fa-calendar-alt fa-lg me-2"></i>
                        <span class="text-uppercase">
                            <fmt:message key="events.container.tab.current_events"/>
                        </span>
                    </button>
                    <button class="nav-link col-6 col-lg-3" id="pastEventsTab" data-bs-toggle="tab"
                            data-bs-target="#pastEvents"
                            type="button" role="tab" aria-selected="false">
                        <i class="fas fa-calendar-check fa-lg me-2"></i>
                        <span class="text-uppercase">
                            <fmt:message key="events.container.tab.past_events"/>
                        </span>
                    </button>
                </div>
            </nav>
            <div class="tab-content" id="eventsTabContent">
                <div class="tab-pane fade show active" id="currentEvents" role="tabpanel"
                     aria-labelledby="currentEventsTab">
                    <div id="liveEvents" class="mb-4">
                        <div class="live-label text-uppercase text-center mt-1 mb-1 p-2">
                            <fmt:message key="events.container.live_label"/>
                        </div>
                        <div class="events" style="display: none">

                        </div>
                    </div>
                    <div id="upcomingEvents">
                        <div class="upcoming-label text-uppercase text-center mt-5 mb-1 p-2">
                            <fmt:message key="events.container.upcoming_label"/>
                        </div>

                        <div class="events" style="display: none;">

                        </div>
                    </div>
                </div>
                <div class="tab-pane fade" id="pastEvents" role="tabpanel" aria-labelledby="pastEventsTab">
                    <div class="events mt-1" style="display: none">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="general/footer.jsp"/>

<div class="modal fade" id="betModal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="betModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content w-100">
            <div class="card">
                <div class="card-header bet-header d-inline-flex align-items-center">
                    <img class="discipline-icon me-3" src="">
                    <div class="d-flex flex-column">
                        <span class="league-name"></span>
                        <span class="event-format"></span>
                    </div>
                    <a class="close ms-auto" role="button"
                       data-bs-dismiss="modal"
                       data-bs-toggle="modal">
                        <i class="fas fa-times"></i>
                    </a>
                </div>
                <div class="card-body p-3">
                    <div class="bet-preview row mx-3 mb-3">
                        <div class="team team-left d-flex flex-column justify-content-center align-items-center col-4">
                            <span class="team-name fw-bold text-truncate mb-2"></span>
                            <div class="team-logo">
                                <img src="">
                            </div>
                        </div>
                        <div class="center d-flex flex-column justify-content-center align-items-center col-4">
                            <h5 class="text-center text-uppercase fst-italic fw-bold">Vs</h5>
                            <img class="live-icon live-blink mb-1" src="/resources/assets/interface/live.png" style="display: none">
                            <span class="timer fw-bold text-center" data-start="">00:00:00</span>
                        </div>
                        <div class="team team-right d-flex flex-column justify-content-center align-items-center col-4">
                            <span class="team-name fw-bold text-truncate mb-2"></span>
                            <div class="team-logo order-last">
                                <img src="assets/team-1.png">
                            </div>
                        </div>
                    </div>

                    <div class="status-messages d-flex flex-column justify-content-center">
                        <span id="betPlaced " class="text-success text-center mb-2" style="display: none">Bet was successfully placed<i class="fas fa-check ms-1"></i></span>
                        <span id="betRefunded" class="text-success text-center mb-2" style="display: none">Bet was successfully refunded<i class="fas fa-check ms-1"></i></span>
                        <span id="betEdited" class="text-success text-center mb-2" style="display: none">Bet was successfully edited<i class="fas fa-check ms-1"></i></span>

                        <span id="betAmountFieldBlank" class="text-danger text-center mb-2" style="display: none">Don't leave the amount field blank<i class="fas fa-exclamation-triangle ms-1"></i></span>
                        <span id="betOperationError" class="text-danger text-center mb-2" style="display: none">Operation execution error<i class="fas fa-exclamation-triangle ms-1"></i></span>
                    </div>

                    <div class="row justify-content-center">
                        <div class="spinner-border" style="width: 4rem; height: 4rem;" role="status">
                            <span class="visually-hidden">Loading...</span>
                        </div>
                    </div>

                    <div class="outcomes">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="general/html/scripts.html" %>
</body>
</html>
