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

<div id="eventsContainer" class="events container mb-5">
    <div class="row">
        <div class="col-12 wrapper">
            <nav>
                <div class="nav nav-tabs" id="eventsTab" role="tablist">
                    <button class="nav-link col-6 col-lg-3 active" id="currentEventsTab" data-bs-toggle="tab"
                            data-bs-target="#currentEvents" type="button" role="tab" aria-selected="true">
                        <i class="fas fa-calendar-alt me-2"></i>
                        <span class="text-uppercase">
                            <fmt:message key="events.container.tab.current_events"/>
                        </span>
                    </button>
                    <button class="nav-link col-6 col-lg-3" id="pastEventsTab" data-bs-toggle="tab"
                            data-bs-target="#pastEvents"
                            type="button" role="tab" aria-selected="false">
                        <i class="fas fa-calendar-check me-2"></i>
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

<%@ include file="general/html/scripts.html" %>
</body>
</html>
