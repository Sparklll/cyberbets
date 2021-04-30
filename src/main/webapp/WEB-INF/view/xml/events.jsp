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

<%@ page import="by.epam.jwd.cyberbets.domain.EventFormat" %>
<%@ page import="by.epam.jwd.cyberbets.domain.Discipline" %>

<div class="tab-content" id="eventsTabContent">
    <div class="tab-pane fade show active" id="currentEvents" role="tabpanel"
         aria-labelledby="currentEventsTab">
        <div id="liveEvents" class="mb-4">
            <div class="live-label text-uppercase text-center mt-1 mb-1 p-2">
                <fmt:message key="events.container.live_label"/>
            </div>

            <div class="events">
                <c:forEach items="${liveEvents}" var="eventData">
                    <div class="event mb-3" data-id="${eventData.event.id}"
                         data-start="${eventData.event.startDate.getEpochSecond()}">
                        <div class="event-header d-flex align-items-center">
                            <span class="date ms-1">
                                <datetime:format value="${eventData.event.startDate}" pattern="EEE, dd MMM YYYY HH:mm"
                                                 locale="${locale}"/>
                            </span>
                            <img class="live-icon ms-2" src="/resources/assets/interface/live.png">
                            <img src="${eventData.event.league.iconResource.path}" class="league-icon ms-auto me-1">
                            <span class="league-name float-end me-1">${eventData.event.league.name}</span>
                        </div>
                        <div class="event-info d-flex">

                            <div class="team team-left d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name text-center">${eventData.event.firstTeam.name}</span>
                                    <span class="odds m-2">
                                        <i>x</i><fmt:formatNumber value="${eventData.totalCoefficients.firstUpshotOdds}"
                                                                  minIntegerDigits="1"
                                                                  minFractionDigits="2" groupingUsed="false"/>
                                    </span>
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
                                    <c:choose>
                                        <c:when test="${eventData.event.discipline eq Discipline.CSGO}">
                                            <img class="discipline-icon" src="${csgoIcon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.DOTA2}">
                                            <img class="discipline-icon" src="${dota2Icon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.LOL}">
                                            <img class="discipline-icon" src="${lolIcon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.VALORANT}">
                                            <img class="discipline-icon" src="${valorantIcon}">
                                        </c:when>
                                    </c:choose>

                                    <span>
                                        <c:choose>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO1}">
                                                BO1
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO2}">
                                                BO2
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO3}">
                                                BO3
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO5}">
                                                BO5
                                            </c:when>
                                        </c:choose>
                                    </span>
                                </div>

                                <div class="right-percent d-flex col-4 justify-content-center">
                                    <span class="odds-percentage">${eventData.totalCoefficients.secondUpshotPercent}%</span>
                                </div>
                            </div>

                            <div class="team team-right d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name text-center">${eventData.event.secondTeam.name}</span>
                                    <span class="odds m-2">
                                        <i>x</i><fmt:formatNumber
                                            value="${eventData.totalCoefficients.secondUpshotOdds}"
                                            minIntegerDigits="1"
                                            minFractionDigits="2" groupingUsed="false"/>
                                    </span>
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
                <c:forEach items="${upcomingEvents}" var="eventData">
                    <div class="event mb-3" data-id="${eventData.event.id}"
                         data-start="${eventData.event.startDate.getEpochSecond()}">
                        <div class="event-header d-flex align-items-center">
                            <span class="date ms-1">
                                <datetime:format value="${eventData.event.startDate}" pattern="EEE, dd MMM YYYY HH:mm"
                                                 locale="${locale}"/>
                            </span>
                            <img src="${eventData.event.league.iconResource.path}" class="league-icon ms-auto me-1">
                            <span class="league-name float-end me-1">${eventData.event.league.name}</span>
                        </div>
                        <div class="event-info d-flex">

                            <div class="team team-left d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name text-center">${eventData.event.firstTeam.name}</span>
                                    <span class="odds m-2">
                                        <i>x</i><fmt:formatNumber value="${eventData.totalCoefficients.firstUpshotOdds}"
                                                                  minIntegerDigits="1"
                                                                  minFractionDigits="2" groupingUsed="false"/>
                                    </span>
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
                                    <c:choose>
                                        <c:when test="${eventData.event.discipline eq Discipline.CSGO}">
                                            <img class="discipline-icon" src="${csgoIcon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.DOTA2}">
                                            <img class="discipline-icon" src="${dota2Icon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.LOL}">
                                            <img class="discipline-icon" src="${lolIcon}">
                                        </c:when>
                                        <c:when test="${eventData.event.discipline eq Discipline.VALORANT}">
                                            <img class="discipline-icon" src="${valorantIcon}">
                                        </c:when>
                                    </c:choose>

                                    <span>
                                        <c:choose>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO1}">
                                                BO1
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO2}">
                                                BO2
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO3}">
                                                BO3
                                            </c:when>
                                            <c:when test="${eventData.event.eventFormat eq EventFormat.BO5}">
                                                BO5
                                            </c:when>
                                        </c:choose>
                                    </span>
                                </div>

                                <div class="right-percent d-flex col-4 justify-content-center">
                                    <span class="odds-percentage">${eventData.totalCoefficients.secondUpshotPercent}%</span>
                                </div>
                            </div>

                            <div class="team team-right d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                                <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                    <span class="team-name text-center">${eventData.event.secondTeam.name}</span>
                                    <span class="odds m-2">
                                        <i>x</i><fmt:formatNumber
                                            value="${eventData.totalCoefficients.secondUpshotOdds}"
                                            minIntegerDigits="1"
                                            minFractionDigits="2" groupingUsed="false"/>
                                    </span>
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
    </div>
    <div class="tab-pane fade" id="pastEvents" role="tabpanel" aria-labelledby="pastEventsTab">
        <div class="events">
            <c:forEach items="${pastEvents}" var="eventData">
            <div class="event mb-3" data-id="${eventData.event.id}"
                 data-start="${eventData.event.startDate.getEpochSecond()}">
                <div class="event-header d-flex align-items-center">
                            <span class="date ms-1">
                                <datetime:format value="${eventData.event.startDate}" pattern="EEE, dd MMM YYYY HH:mm"
                                                 locale="${locale}"/>
                            </span>
                    <img src="${eventData.event.league.iconResource.path}" class="league-icon ms-auto me-1">
                    <span class="league-name float-end me-1">${eventData.event.league.name}</span>
                </div>

                <div class="event-info d-flex">
                    <div class="team team-left d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                        <div class="d-flex flex-column justify-content-center align-items-center col-6">
                            <span class="team-name text-center">${eventData.event.firstTeam.name}</span>
                            <span class="odds m-2">
                                    <i>x</i><fmt:formatNumber value="${eventData.totalCoefficients.firstUpshotOdds}"
                                                              minIntegerDigits="1"
                                                              minFractionDigits="2" groupingUsed="false"/>
                                </span>
                        </div>
                        <c:choose>
                            <c:when test="${eventData.totalCoefficients.result eq 'FIRST_UPSHOT'}">
                                <div class="team-logo left-winner ms-sm-2">
                                    <img src="${eventData.event.firstTeam.logoResource.path}">
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="team-logo ms-sm-2">
                                    <img src="${eventData.event.firstTeam.logoResource.path}">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="center d-flex flex-column flex-sm-row col-4 justify-content-center align-items-center">
                            <div class="left-percent d-flex col-4 justify-content-center">
                                <span class="odds-percentage">${eventData.totalCoefficients.firstUpshotPercent}%</span>
                            </div>

                            <div class="event-format d-flex flex-column col-4 justify-content-center align-items-center">
                                <c:choose>
                                    <c:when test="${eventData.event.discipline eq Discipline.CSGO}">
                                        <img class="discipline-icon" src="${csgoIcon}">
                                    </c:when>
                                    <c:when test="${eventData.event.discipline eq Discipline.DOTA2}">
                                        <img class="discipline-icon" src="${dota2Icon}">
                                    </c:when>
                                    <c:when test="${eventData.event.discipline eq Discipline.LOL}">
                                        <img class="discipline-icon" src="${lolIcon}">
                                    </c:when>
                                    <c:when test="${eventData.event.discipline eq Discipline.VALORANT}">
                                        <img class="discipline-icon" src="${valorantIcon}">
                                    </c:when>
                                </c:choose>

                                <span>
                                    <c:choose>
                                        <c:when test="${eventData.event.eventFormat eq EventFormat.BO1}">
                                            BO1
                                        </c:when>
                                        <c:when test="${eventData.event.eventFormat eq EventFormat.BO2}">
                                            BO2
                                        </c:when>
                                        <c:when test="${eventData.event.eventFormat eq EventFormat.BO3}">
                                            BO3
                                        </c:when>
                                        <c:when test="${eventData.event.eventFormat eq EventFormat.BO5}">
                                            BO5
                                        </c:when>
                                    </c:choose>
                                </span>
                            </div>

                            <div class="right-percent d-flex col-4 justify-content-center">
                                <span class="odds-percentage">${eventData.totalCoefficients.secondUpshotPercent}%</span>
                            </div>
                        </div>

                    <div class="team team-right d-flex flex-column flex-sm-row justify-content-center align-items-center col-4">
                        <div class="d-flex flex-column justify-content-center align-items-center col-6">
                            <span class="team-name text-center">${eventData.event.secondTeam.name}</span>
                            <span class="odds m-2">
                                <i>x</i><fmt:formatNumber value="${eventData.totalCoefficients.secondUpshotOdds}"
                                                          minIntegerDigits="1"
                                                          minFractionDigits="2" groupingUsed="false"/>
                            </span>
                        </div>
                        <c:choose>
                            <c:when test="${eventData.totalCoefficients.result eq 'SECOND_UPSHOT'}">
                                 <div class="team-logo right-winner order-last order-sm-first me-sm-2">
                                     <img src="${eventData.event.secondTeam.logoResource.path}">
                                 </div>
                            </c:when>
                            <c:otherwise>
                                <div class="team-logo order-last order-sm-first me-sm-2">
                                    <img src="${eventData.event.secondTeam.logoResource.path}">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
    </div>
</div>
