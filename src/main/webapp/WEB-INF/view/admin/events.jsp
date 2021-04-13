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
                <span class="ms-2 text-uppercase fw-bold">Events</span>
            </div>

            <div class="event-control-panel">
                <button type="button" class="add-event btn btn-primary btn-block text-uppercase mt-3"
                        data-bs-toggle="modal"
                        data-bs-target="#eventModal">
                    <i class="fas fa-plus me-1"></i>
                    Add Event
                </button>
            </div>

            <div class="events mt-2">
                <div id="eventsGrid"></div>
            </div>
        </div>
    </div>

    <jsp:include page="../general/admin-footer.jsp"/>
</main>

<div class="modal fade" id="eventModal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="eventModalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content w-100">
            <div class="card">
                <div class="card-header d-inline-flex align-items-center">
                    <h5 class="mt-3 text-uppercase fw-bold">Add Event</h5>
                    <a class="close ms-auto" role="button"
                       data-bs-dismiss="modal"
                       data-bs-toggle="modal">
                        <i class="fas fa-times"></i>
                    </a>
                </div>
                <div class="card-body p-3">
                    <div class="row">
                        <div class="event-preview">
                            <div class="text-center fw-bold mb-2">Preview</div>
                            <div class="event mb-3" data-id="">
                                <div class="event-header d-flex align-items-center">
                                    <span class="date ms-1">Date</span>
                                    <div class="d-flex justify-content-center align-items-center ms-auto">
                                        <img src="" class="league-icon me-1 my-1" style="display: none">
                                        <span class="league-name me-1">League</span>
                                    </div>
                                </div>
                                <div class="event-info d-flex">

                                    <div class="team team-left d-flex flex-column justify-content-center align-items-center col-4">
                                        <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                            <span class="team-name text-nowrap">Team 1</span>
                                            <span class="odds m-2"><i>x</i>1</span>
                                        </div>
                                        <div class="team-logo">
                                            <img src="" style="display: none">
                                        </div>
                                    </div>


                                    <div class="center d-flex flex-column col-4 justify-content-center align-items-center">
                                        <div class="left-percent d-flex col-4 justify-content-center">
                                            <span class="odds-percentage">50%</span>

                                        </div>

                                        <div class="event-format d-flex flex-column col-4 justify-content-center align-items-center">
                                            <img class="discipline-icon" src="" style="display: none">
                                            <span></span>
                                        </div>

                                        <div class="right-percent d-flex col-4 justify-content-center">
                                            <span class="odds-percentage">50%</span>
                                        </div>
                                    </div>


                                    <div class="team team-right d-flex flex-column justify-content-center align-items-center col-4">
                                        <div class="d-flex flex-column justify-content-center align-items-center col-6">
                                            <span class="team-name text-nowrap">Team 2</span>
                                            <span class="odds m-2"><i>x</i>1</span>
                                        </div>
                                        <div class="team-logo order-last">
                                            <img src="" style="display: none">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="eventStatus" class="d-flex justify-content-center mb-2">
                            <div class="btn-group" role="group">
                                <input type="radio" class="btn-check" id="pendingOption"
                                       name="eventStatus"
                                       value="1"
                                       autocomplete="off"
                                       checked/>
                                <label class="btn btn-secondary btn-sm text-uppercase" for="pendingOption">Pending</label>

                                <input type="radio" class="btn-check" id="liveOption"
                                       name="eventStatus"
                                       value="2"
                                       autocomplete="off"/>
                                <label class="btn btn-secondary btn-sm text-uppercase" for="liveOption">Live</label>

                                <input type="radio" class="btn-check" id="finishedOption"
                                       name="eventStatus"
                                       value="3"
                                       autocomplete="off"/>
                                <label class="btn btn-secondary btn-sm text-uppercase" for="finishedOption">Finished</label>

                                <input type="radio" class="btn-check" id="canceledOption"
                                       name="eventStatus"
                                       value="4"
                                       autocomplete="off"/>
                                <label class="btn btn-secondary btn-sm text-uppercase" for="canceledOption">Canceled</label>
                            </div>
                        </div>
                    </div>
                        <div class="accordion accordion-flush" id="eventAccordion">
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="eventInfoHeading">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#eventInfoCollapse" aria-expanded="true"
                                            aria-controls="collapseOne">
                                        Event Info
                                    </button>
                                </h2>
                                <div id="eventInfoCollapse" class="accordion-collapse collapse show">
                                    <div class="accordion-body">
                                        <form action="" method="POST" class="custom-controls row">
                                            <div class="mb-3 col-12">
                                                <label for="eventDisciplineSelect" class="form-label">Discipline</label>
                                                <select id="eventDisciplineSelect"
                                                        class="form-control selectpicker show-tick" data-size="5"
                                                        data-none-selected-text="⁣">
                                                    <option value="0" selected></option>
                                                    <option value="1">CS:GO</option>
                                                    <option value="2">DOTA2</option>
                                                    <option value="3">LEAGUE OF LEGENDS</option>
                                                    <option value="4">VALORANT</option>
                                                </select>
                                            </div>


                                            <div class="mb-3 col-12">
                                                <label for="eventLeagueSelect" class="form-label">League</label>
                                                <select id="eventLeagueSelect"
                                                        class="form-control selectpicker show-tick"
                                                        data-live-search="true" data-size="5"
                                                        data-none-selected-text="⁣"
                                                        disabled>
                                                    <option value="0" selected></option>
                                                </select>
                                            </div>

                                            <div class="mb-3 col-6">
                                                <label for="eventFirstTeamSelect" class="form-label">First Team</label>
                                                <select id="eventFirstTeamSelect"
                                                        class="form-control selectpicker show-tick" data-size="5"
                                                        data-none-selected-text="⁣"
                                                        disabled>
                                                    <option value="0" selected></option>
                                                </select>
                                            </div>

                                            <div class="mb-3 col-6">
                                                <label for="eventSecondTeamSelect" class="form-label">Second
                                                    Team</label>
                                                <select id="eventSecondTeamSelect"
                                                        class="form-control selectpicker show-tick" data-size="5"
                                                        data-none-selected-text="⁣"
                                                        disabled>
                                                    <option value="0" selected></option>
                                                </select>
                                            </div>

                                            <div class="mb-3 col-12">
                                                <label for="eventFormatSelect" class="form-label">Format</label>
                                                <select id="eventFormatSelect"
                                                        class="form-control selectpicker show-tick" data-size="5"
                                                        data-none-selected-text="⁣"
                                                        >
                                                    <option value="0" selected></option>
                                                    <option value="1">BO1</option>
                                                    <option value="2">BO2</option>
                                                    <option value="3">BO3</option>
                                                    <option value="4">BO5</option>
                                                </select>
                                            </div>


                                            <div class="mb-3 col-12">
                                                <label for="eventDatetimeStart" class="form-label">Start</label>
                                                <input id="eventDatetimeStart" class="form-control"
                                                       type="datetime-local">
                                            </div>

                                            <div class="mb-3 col-12">
                                                <label for="eventRoyalty" class="form-label">Royalty</label>
                                                <input id="eventRoyalty" class="form-control" type="number" value="5"
                                                       min="0" max="20" step="0.1">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <div class="accordion-item">
                                <h2 class="accordion-header" id="eventOutcomesHeading">
                                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                            data-bs-target="#eventOutcomeCollapse" aria-expanded="false"
                                            aria-controls="collapseTwo">
                                        Event Outcomes
                                    </button>
                                </h2>
                                <div id="eventOutcomeCollapse" class="accordion-collapse collapse">
                                    <template id="eventOutcomeTemplate">
                                        <div class="event-outcome d-flex flex-column justify-content-center mb-3" data-type="">
                                            <div class="d-flex justify-content-between mb-2">
                                                <span class="odds left-outcome-odds fw-bold text-success">
                                                        <i>x</i>
                                                        <span>1</span>
                                                    </span>
                                                <span class="outcome-type-name fw-bold text-truncate text-danger mx-2">Name</span>
                                                <span class="odds right-outcome-odds fw-bold text-success">
                                                        <i>x</i>
                                                        <span>1</span>
                                                    </span>
                                            </div>
                                            <div class="btn-group btn-group-sm" role="group">
                                                <input type="radio" class="btn-check" id="firstUpshot"
                                                       name=""
                                                       value="4"
                                                       autocomplete="off"/>
                                                <label class="btn btn-secondary btn-sm text-uppercase me-2 text-truncate"
                                                       for="firstUpshot" title="First Upshot" style="min-width: 100px; max-width: 100px;"></label>

                                                <input type="radio" class="btn-check" id="disableOption"
                                                       name=""
                                                       value="1"
                                                       autocomplete="off"
                                                       checked/>
                                                <label class="btn btn-secondary btn-sm text-uppercase d-flex  justify-content-center align-items-center"
                                                       for="disableOption" title="Disabled"><i class="fs-5 fas fa-ban fa-fw"></i></label>

                                                <input type="radio" class="btn-check" id="blockOption"
                                                       name=""
                                                       value="2"
                                                       autocomplete="off"/>
                                                <label class="btn btn-secondary btn-sm text-uppercase d-flex  justify-content-center align-items-center"
                                                       for="blockOption" title="Blocked"><i class="fs-5 fas fa-lock fa-fw"></i></label>

                                                <input type="radio" class="btn-check" id="unblockOption"
                                                       name=""
                                                       value="3"
                                                       autocomplete="off"/>
                                                <label class="btn btn-secondary btn-sm text-uppercase d-flex  justify-content-center align-items-center"
                                                       for="unblockOption" title="Unblocked"><i class="fs-5 fas fa-lock-open fa-fw"></i></label>

                                                <input type="radio" class="btn-check" id="secondUpshot"
                                                       name=""
                                                       value="5"
                                                       autocomplete="off"/>
                                                <label class="btn btn-secondary btn-sm text-uppercase ms-2 text-truncate"
                                                       for="secondUpshot" title="Second Upshot" style="min-width: 100px; max-width: 100px;"></label>
                                            </div>
                                        </div>
                                    </template>
                                    <div class="accordion-body">

                                    </div>
                                </div>
                            </div>
                            <div class="d-grid gap-1">
                                <button type="submit" id="eventModalSubmit"
                                        class="btn btn-primary btn-block text-uppercase mt-3">
                                    Save
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<div class="modal fade" id="confirmModal" data-bs-keyboard="false" tabindex="-1" aria-labelledby="confirmModal"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered justify-content-center">
        <div class="modal-content w-100">
            <div class="card">
                <div class="card-header d-inline-flex align-items-center">
                    <a class="close ms-auto" role="button"
                       data-bs-dismiss="modal"
                       data-bs-toggle="modal">
                        <i class="fas fa-times"></i>
                    </a>
                </div>
                <div class="card-body p-3">
                    <div class="row">
                        <div class="icon-box mb-4">
                            <i class="fas fa-times"></i>
                        </div>

                        <div class="text-center mb-3 fw-bold">
                            <h4 class="mb-3">Are you sure?</h4>
                            <p>Do you really want to delete this record? This process cannot be undone.</p>
                        </div>
                        <div class="d-flex justify-content-center">
                            <button id="cancelButton" type="button" class="btn btn-secondary mx-4" data-bs-dismiss="modal">Cancel</button>
                            <button id="deleteButton" type="button" class="btn btn-primary mx-4">Delete</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../general/html/admin-scripts.html" %>
</body>
</html>
