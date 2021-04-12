<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://cyberbets.com/jsp/tlds/datetime" prefix="datetime" %>
<%@ page session="false" %>

<c:set var="lang" value="${cookie['lang'].getValue()}"/>
<fmt:setLocale value="${empty lang ? 'default' : lang}" scope="request"/>

<c:set var="csgoIcon" value="/resources/assets/disciplines/csgo_icon.png"/>
<c:set var="dota2Icon" value="/resources/assets/disciplines/dota2_icon.png"/>
<c:set var="lolIcon" value="/resources/assets/disciplines/lol_icon.png"/>
<c:set var="valorantIcon" value="/resources/assets/disciplines/valorant_icon.jpg"/>


<div class="tab-content" id="eventsTabContent">
    <div class="tab-pane fade show active" id="currentEvents" role="tabpanel"
         aria-labelledby="currentEventsTab">
        <div id="liveEvents" class="mb-4">
            <div class="live-label text-uppercase text-center mt-1 mb-1 p-2">
                <fmt:message key="events.container.live_label"/>
            </div>

            <div class="events">
                <c:forEach items="${liveEvents}" var="eventData">
                    <div class="event mb-3" data-id="${eventData.event.id}">
                        <div class="event-header d-flex align-items-center">
                            <span class="date ms-1">
                                <datetime:format value="${eventData.event.startDate}" pattern="EEE, dd MMM YYYY HH:mm" locale="${locale}"/>
                            </span>
                            <img class="ms-2" src="/resources/assets/live.png" width="50">
                            <img src="${eventData.event.league.iconResource.path}" class="league-icon ms-auto me-1">
                            <span class="league-name float-end me-1">${eventData.event.league.name}</span>
                        </div>
                        <div class="event-info d-flex">

                            <div class="team team-left d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name">${eventData.event.firstTeam.name}</span>
                                    <span class="odds m-2"><i>x</i>${eventData.totalCoefficients.firstUpshotOdds}</span>
                                </div>
                                <div class="team-logo ms-sm-2">
                                    <img src="${eventData.event.firstTeam.logoResource.path}">
                                </div>
                            </div>


                            <div class="center d-flex flex-column flex-sm-row col-4 justify-content-center align-items-center">
                                <div class="left-percent d-flex col-4 justify-content-center">
                                    <span class="odds-percentage">${eventData.totalCoefficients.firstUpshotPercent}%</span>

                                </div>

                                <div class="event-format d-flex flex-column col-4 justify-content-center align-items-center">
                                    <c:if test="${eventData.event.discipline.id eq 1}">
                                        <img class="discipline-icon" src="${csgoIcon}">
                                    </c:if>
                                    <c:if test="${eventData.event.discipline.id eq 2}">
                                        <img class="discipline-icon" src="${dota2Icon}">
                                    </c:if>
                                    <c:if test="${eventData.event.discipline.id eq 3}">
                                        <img class="discipline-icon" src="${lolIcon}">
                                    </c:if>
                                    <c:if test="${eventData.event.discipline.id eq 4}">
                                        <img class="discipline-icon" src="${valorantIcon}">
                                    </c:if>

                                    <span>
                                    <c:if test="${eventData.event.eventFormat.id eq 1}">
                                        BO1
                                    </c:if>
                                    <c:if test="${eventData.event.eventFormat.id eq 2}">
                                        BO2
                                    </c:if>
                                    <c:if test="${eventData.event.eventFormat.id eq 3}">
                                        BO3
                                    </c:if>
                                    <c:if test="${eventData.event.eventFormat.id eq 4}">
                                        BO5
                                    </c:if>
                                    </span>
                                </div>

                                <div class="right-percent d-flex col-4 justify-content-center">
                                    <span class="odds-percentage">${eventData.totalCoefficients.secondUpshotPercent}%</span>
                                </div>
                            </div>


                            <div class="team team-right d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name">${eventData.event.secondTeam.name}</span>
                                    <span class="odds m-2"><i>x</i>${eventData.totalCoefficients.secondUpshotOdds}</span>
                                </div>
                                <div class="team-logo order-last order-sm-first me-sm-2">
                                    <img src="${eventData.event.secondTeam.logoResource.path}">
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <div id="upcomingEvents">
            <div class="upcoming-label text-uppercase text-center mt-5 mb-1 p-2">
                <fmt:message key="events.container.upcoming_label"/>
            </div>

            <div class="events">
                <c:forEach items="${pastEvents}" var="eventData">

                </c:forEach>
            </div>
        </div>
    </div>
    <div class="tab-pane fade" id="pastEvents" role="tabpanel" aria-labelledby="pastEventsTab">
        <div class="events">
            <c:forEach items="${pastEvents}" var="eventData">

            </c:forEach>
        </div>
    </div>
</div>
