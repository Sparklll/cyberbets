<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>


<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="/static/assets/favicon.ico">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
    <title>CYBERBETS | HOME</title>
</head>


<body class="d-flex flex-column min-vh-100">

<jsp:include page="general/navbar.jsp">
    <jsp:param name="auth" value="${auth}"/>
    <jsp:param name="role" value="${role}"/>
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

<div class="events container mb-5">
    <div class="row">
        <div class="col-12 wrapper">

            <nav>
                <div class="nav nav-tabs" id="eventsTab" role="tablist">
                    <button class="nav-link active" id="currentEventsTab" data-bs-toggle="tab"
                            data-bs-target="#currentEvents" type="button" role="tab" aria-selected="true">
                        <i class="fas fa-calendar-alt me-2"></i>
                        <span class="text-uppercase">Current events</span>
                    </button>
                    <button class="nav-link" id="pastEventsTab" data-bs-toggle="tab" data-bs-target="#pastEvents"
                            type="button" role="tab" aria-selected="false">
                        <i class="fas fa-calendar-check me-2"></i>
                        <span class="text-uppercase">Past events</span>
                    </button>
                </div>
            </nav>
            <div class="tab-content" id="eventsTabContent">
                <div class="tab-pane fade show active" id="currentEvents" role="tabpanel"
                     aria-labelledby="currentEventsTab">
                    <div class="event" data-id="">
                        Live/Upcoming
                    </div>
                </div>
                <div class="tab-pane fade" id="pastEvents" role="tabpanel" aria-labelledby="pastEventsTab">
                    Past
                </div>
            </div>
        </div>
    </div>
</div>


<jsp:include page="general/footer.jsp"/>

<script src="https://kit.fontawesome.com/4968ce6a0b.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
        crossorigin="anonymous"></script>
<script src="/resources/js/script.js"></script>
</body>
</html>
