$(document).ready(function () {
    const lang = ['de', 'en', 'fr', 'ru'];
    const discipline = ['csgo', 'dota2', 'lol', 'valorant'];
    const DEFAULT_LANG = 'en';
    const ACTION_URL = "/action/"

    var auth = $('#eventsContainer').data('auth');
    var isEventEditing = false;
    var isTeamEditing = false;
    var isLeagueEditing = false;
    var editingItem = null;

    var timer = setInterval(function() {
        let now = dayjs().unix();

        $('span.timer').each(function () {
            let startDate = $(this).attr('data-start');
            let diff = Math.abs(now - startDate);

            let duration = dayjs.duration(diff, 's');
            let days = duration.days();

            if(days != 0) {
                // TODO : add i18n day abbreviation
                $(this).text(days + "d " + duration.format('HH:mm:ss'));
            } else {
                $(this).text(duration.format('HH:mm:ss'));
            }
        })
    }, 1000);

    function setCookie(name, value, days) {
        if (days) {
            let date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));

            let cookie = name + "=" + value + ";";
            let expires = "expires=" + date.toUTCString() + ";";
            let path = "path=/;";
            //let domain = "domain=." + window.location.hostname + ";";
            document.cookie = cookie + expires + path;
        }
    }

    function getCookie(name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
        ));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

    function deleteCookie(name) {
        setCookie(name, "", 0);
    }

    function reloadPage() {
        window.location.href = window.location.pathname;
    }

    function reloadDisciplineFilter() {
        let disciplineCookie = getCookie('discipline_filter');
        $('.discipline').removeClass('active');

        if (disciplineCookie !== undefined) {
            let selectedDisciplines = disciplineCookie.split('|');
            selectedDisciplines.forEach(d => {
                if (discipline.includes(d)) {
                    $(`.discipline[data-discipline='${d}']`).addClass('active');
                }
            });
        } else {
            setCookie("discipline_filter", discipline.join('|'), 365)
            $('.discipline').each(function () {
                $(this).addClass('active');
            });
        }
    }

    function loadEventSection() {
        $.post(ACTION_URL, JSON.stringify({action: "loadEventSection"}))
            .done(function (responseXml) {
                $('#liveEvents .events').html($(responseXml).find('#liveEvents .events').html()).fadeIn(200);
                $('#upcomingEvents .events').html($(responseXml).find('#upcomingEvents .events').html()).fadeIn(200);
                $('#pastEvents .events').html($(responseXml).find('#pastEvents .events').html()).fadeIn(200);
            });
    }

    function reloadEventSection() {
        $.post(ACTION_URL, JSON.stringify({action: "loadEventSection"}))
            .done(function (responseXml) {
                $('#liveEvents .events').fadeOut(300, function () {
                    $(this).html($(responseXml).find('#liveEvents .events').html());
                }).fadeIn(300);
                $('#upcomingEvents .events').fadeOut(300, function () {
                    $(this).html($(responseXml).find('#upcomingEvents .events').html()).fadeIn(300);
                });
                $('#pastEvents .events').fadeOut(300, function () {
                    $(this).html($(responseXml).find('#pastEvents .events').html()).fadeIn(300);
                });
            });
    }

    async function postData(url = '', data = {}) {
        const response = await fetch(url, {
            method: 'POST',
            mode: 'same-origin',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        });
        return response;
    }

    function validateEmail(email) {
        const mailFormat = /^[-!#$%&'*+\/0-9=?A-Z^_a-z`{|}~](\.?[-!#$%&'*+\/0-9=?A-Z^_a-z`{|}~])*@[a-zA-Z0-9](-*\.?[a-zA-Z0-9])*\.[a-zA-Z](-?[a-zA-Z0-9])+$/;
        return !!email.match(mailFormat);
    }

    function validatePasswordLength(password) {
        return password.length >= 8;
    }

    function validatePasswordMatching(password1, password2) {
        return password1 === password2;
    }

    function validateName(name) {
        return name.length > 0 && name.length < 50
    }

    function validateTeamRating(teamRating) {
        const ratingFormat = /^(([1-9][0-9]{0,5})|([0]))$/;
        return !isNaN(teamRating)
            && ratingFormat.test(teamRating);
    }

    var disciplines = [
        {Name: "", Id: 0, Logo: ""},
        {Name: "CS:GO", Id: 1, Logo: "/resources/assets/disciplines/csgo_icon.png"},
        {Name: "DOTA 2", Id: 2, Logo: "/resources/assets/disciplines/dota2_icon.png"},
        {Name: "LEAGUE OF LEGENDS", Id: 3, Logo: "/resources/assets/disciplines/lol_icon.png"},
        {Name: "VALORANT", Id: 4, Logo: "/resources/assets/disciplines/valorant_icon.jpg"}
    ];

    var eventFormat = [
        {Name: "", Id: 0},
        {Name: "BO1", Id: 1},
        {Name: "BO2", Id: 2},
        {Name: "BO3", Id: 3},
        {Name: "BO5", Id: 4}
    ];

    var eventStatus = [
        {Name: "", Id: 0},
        {Name: "Pending", Id: 1, Logo: "/resources/assets/status/pending.png"},
        {Name: "Live", Id: 2, Logo: "/resources/assets/status/live.png"},
        {Name: "Finished", Id: 3, Logo: "/resources/assets/status/finished.png"},
        {Name: "Canceled", Id: 4, Logo: "/resources/assets/status/canceled.png"},
    ];

    var eventOutcome = [
        // TODO: i18n

        // general
        {Name: "Total winner", Discipline: "all", Format: "all", Id: 1},
        {Name: "[Map #1] Victory on the map", Discipline: "all", Format: 1, Id: 2},
        {Name: "[Map #2] Victory on the map", Discipline: "all", Format: 2, Id: 3},
        {Name: "[Map #3] Victory on the map", Discipline: "all", Format: 3, Id: 4},
        {Name: "[Map #4] Victory on the map", Discipline: "all", Format: 4, Id: 5},
        {Name: "[Map #5] Victory on the map", Discipline: "all", Format: 4, Id: 6},

        //Specific\\
        // csgo
        // dota2
        // lol
        // valorant
    ];

    if ($('#eventsGrid').length > 0) {
        $('#eventsGrid').jsGrid({
            fields: [
                {name: "id", title: "Id", type: "number", width: 50, align: "center"},
                {
                    name: "event",
                    title: "Event",
                    type: "text",
                    width: 125,
                    align: "center",
                    itemTemplate: function (value, item) {
                        return `<div class="d-flex justify-content-center align-items-center">
                                    <div class="d-flex flex-column justify-content-center align-items-center me-auto ms-2">
                                        <span class="mb-2 fw-bold text-truncate" style="width: 100px">${item.firstTeam.teamName}</span>
                                        <img src="${item.firstTeam.teamLogo.path}" width="50">
                                    </div>
                                   
                                   <i class="fw-bold">VS</i> 
                                   
                                   <div class="d-flex flex-column justify-content-center align-items-center ms-auto me-2">
                                        <span class="mb-2 fw-bold text-truncate" style="width: 100px">${item.secondTeam.teamName}</span>
                                        <img src="${item.secondTeam.teamLogo.path}" width="50">
                                    </div>
                                </div>`;
                    }
                },
                {
                    name: "eventFormat",
                    title: "Format",
                    type: "select",
                    items: eventFormat,
                    valueField: "Id",
                    textField: "Name",
                    width: 50,
                    itemTemplate: function (value, item) {
                        return eventFormat.find(f => f.Id == value).Name;
                    }
                },
                {
                    name: "leagueName",
                    title: "League",
                    type: "text",
                    width: 75,
                    align: "center",
                    itemTemplate: function (value, item) {
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2">${item.league.leagueName}</span>
                                    <img src="${item.league.leagueIcon.path}" width="30">
                                </div>`;
                    }
                },
                {
                    name: "discipline",
                    title: "Discipline",
                    type: "select",
                    items: disciplines,
                    valueField: "Id",
                    textField: "Name",
                    width: 100,
                    itemTemplate: function (value, item) {
                        let disciplineName = disciplines.find(d => d.Id == value).Name;
                        let disciplineLogo = disciplines.find(d => d.Id == value).Logo;
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2">${disciplineName}</span>
                                    <img src="${disciplineLogo}" width="30" style="border-radius: 5px">
                                </div>`;
                    }
                },
                {
                    name: "startDate",
                    title: "Start",
                    type: "",
                    width: 50,
                    align: "center",
                    filtering: false,
                    itemTemplate: function (value, item) {
                        return `<span class="mb-2 fw-bold text-info">${dayjs.unix(value).format('ddd, DD MMM YYYY HH:mm')}</span>`;
                    }
                },
                {
                    name: "royaltyPercentage",
                    title: "Royalty",
                    type: "number",
                    width: 50,
                    align: "center",
                    itemTemplate: function (value, item) {
                        return `<span class="mb-2">${value}%</span>`
                    }
                },
                {
                    name: "status",
                    title: "Status",
                    type: "select",
                    items: eventStatus,
                    valueField: "Id",
                    textField: "Name",
                    width: 50,
                    itemTemplate: function (value, item) {
                        let eventStatusIcon = eventStatus.find(s => s.Id == value).Logo;
                        return `<img src="${eventStatusIcon}" width="30" style="border-radius: 5px">`;
                    }
                },
                {
                    type: "control",
                    editButton: false,
                    deleteButton: true,
                    clearFilterButton: true,
                    modeSwitchButton: true,
                    width: 50,
                }
            ],

            autoload: true,
            controller: {
                loadData: function (filter) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "loadEvent"}, filter))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('info', 'Success', 'Events were successfully loaded.');
                            d.resolve(response.data);
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'Unable to load events from database');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'Unable to load events from database');
                        d.reject();
                    });
                    return d.promise();
                },

                insertItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "insertEvent"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'Event was successfully added.');
                            d.resolve(response.data);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error adding the event!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error adding the event!');
                        d.reject();
                    });
                    return d.promise();
                },

                updateItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "updateEvent"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'Event was successfully updated.');
                            d.resolve(response.data);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error updating the event!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error updating the event!');
                        d.reject();
                    });
                    return d.promise();
                },

                deleteItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "deleteEvent"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'Event was successfully deleted.');
                            d.resolve();
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error deleting the event!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error deleting the Event!');
                        d.reject();
                    });
                    return d.promise();
                }
            },

            width: "100%",
            height: "auto",

            heading: true,
            filtering: true,
            inserting: false,
            editing: true,
            selecting: true,
            sorting: true,
            paging: true,
            pageLoading: false,
            confirmDeleting: false,

            onItemDeleting: function (args) {
                if (!args.item.deleteConfirmed) {
                    args.cancel = true;
                    $('#confirmModal').modal('show');
                    $('#confirmModal #deleteButton').off('click').on('click', function () {
                        args.item.deleteConfirmed = true;
                        $('#eventsGrid').jsGrid('deleteItem', args.item).then(function () {
                            $('#confirmModal').modal('hide');
                        });
                    });
                }
            },

            rowClick: function (args) {
                // TODO: Add i18n

                isEventEditing = true;
                editingItem = args.item;

                $('#eventModal').modal('show');
                $('#eventModal .card-header h5').text('Edit event');
                $('#eventModal #eventModalSubmit').text('Update');

                let event = args.item;

                $('#eventModal .event-preview .event-header .date')
                    .text(dayjs.unix(event.startDate).format('ddd, DD MMM YYYY HH:mm'));
                $('#eventModal .event-preview .event-header .league-icon')
                    .attr('src', event.league.leagueIcon.path).fadeIn(500);

                $('#eventModal .event-preview .event-header .league-name').text(event.league.leagueName);

                $('#eventModal .event-preview .team-left .team-logo img')
                    .attr('src', event.firstTeam.teamLogo.path).fadeIn(500);
                $('#eventModal .event-preview .team-right .team-logo img')
                    .attr('src', event.secondTeam.teamLogo.path).fadeIn(500);

                $('#eventModal .event-preview .team-left .team-name').text(event.firstTeam.teamName);
                $('#eventModal .event-preview .team-right .team-name').text(event.secondTeam.teamName);

                $('#eventModal .event-preview .event-info .center .event-format span')
                    .text(eventFormat.find(e => e.Id == event.eventFormat).Name);
                $('#eventModal .event-preview .event-info .center .event-format .discipline-icon')
                    .hide().attr('src', disciplines.find(d => d.Id == event.discipline).Logo).fadeIn(1000);

                $(`#eventStatus input[value="${event.status}"]:radio`).prop('checked', true);


                $(`<option value="0"></option>`).appendTo($('#eventLeagueSelect, #eventSecondTeamSelect, #eventFirstTeamSelect'));
                $('#eventDisciplineSelect').val(event.discipline);
                $('#eventLeagueSelect option').val(event.league.id).data('icon', event.league.leagueIcon.path).text(event.league.leagueName);

                $('#eventFirstTeamSelect option').val(event.firstTeam.id).data('icon', event.firstTeam.teamLogo.path).text(event.firstTeam.teamName);
                $('#eventSecondTeamSelect option').val(event.secondTeam.id).data('icon', event.secondTeam.teamLogo.path).text(event.secondTeam.teamName);


                $('#eventFormatSelect').val(event.eventFormat);
                $('#eventDatetimeStart').val(dayjs.unix(event.startDate).format('YYYY-MM-DDTHH:mm'));
                $('#eventRoyalty').val(event.royaltyPercentage);

                $('.selectpicker').selectpicker('refresh');

                postData(ACTION_URL, {
                    "action": "loadEventResults",
                    "id": event.id
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (response) {
                    response.data.forEach(eventResult => {
                            let eventOutcomeTemplate = $($("#eventOutcomeTemplate").html());
                            let eventOutcomeType = eventResult.eventOutcomeType;

                            eventOutcomeTemplate.attr('data-type', eventOutcomeType);
                            eventOutcomeTemplate.find('label[for=firstUpshot]').text(event.firstTeam.teamName);
                            eventOutcomeTemplate.find('label[for=secondUpshot]').text(event.secondTeam.teamName);

                            eventOutcomeTemplate.find(':input:radio').each(function (index, item) {
                                $(item).attr({
                                    'id': $(this).attr('id') + eventOutcomeType,
                                    'name': eventOutcomeType,
                                });
                            });
                            eventOutcomeTemplate
                                .find(`:input:radio[value=${eventResult.resultStatus}]`)
                                .prop('checked', true);
                            eventOutcomeTemplate.find('label').each(function (index, item) {
                                $(item).attr('for', $(this).attr('for') + eventOutcomeType);
                            });
                            eventOutcomeTemplate.find('.outcome-type-name').text(
                                eventOutcome.find(eo => eo.Id == eventOutcomeType).Name
                            );

                            $('#eventModal #eventOutcomeCollapse .accordion-body').append(eventOutcomeTemplate);
                        }
                    );

                    postData(ACTION_URL, {
                        "action": "loadEventCoefficients",
                        "id": event.id
                    }).then((response) => {
                            if (response.ok) {
                                return response.json();
                            }
                            return Promise.reject(response);
                        }
                    ).then(function (response) {
                        if (response.data != null) {
                            let eventCoefficients = response.data;
                            eventCoefficients.forEach(c => {
                                if (c.eventOutcomeTypeId == 1) { // Total Winner, filling preview
                                    $('#eventModal .team-left .odds').empty().append(`<i>x</i>${c.firstUpshotOdds}`);
                                    $('#eventModal .team-right .odds').empty().append(`<i>x</i>${c.secondUpshotOdds}`);
                                    $('#eventModal .center .left-percent .odds-percentage').text(`${c.firstUpshotPercent}%`);
                                    $('#eventModal .center .right-percent .odds-percentage').text(`${c.secondUpshotPercent}%`);
                                }

                                let foundedEventOutcome = $('#eventOutcomeCollapse .accordion-body')
                                    .find(`.event-outcome[data-type=${c.eventOutcomeTypeId}]`);
                                if (foundedEventOutcome != null) {
                                    $(foundedEventOutcome).find('.left-outcome-odds').empty().append(`<i>x</i>${c.firstUpshotOdds}`);
                                    $(foundedEventOutcome).find('.right-outcome-odds').empty().append(`<i>x</i>${c.secondUpshotOdds}`);
                                }
                            });
                        }
                    }).catch((error) => console.log('Something went wrong.', error));

                }).catch((error) => console.log('Something went wrong.', error));
            },

            pageIndex: 1,
            pageSize: 10,
            pageButtonCount: 10,
        });
    }

    if ($('#eventModal').length > 0) {
        $('#eventModal').off('show.bs.modal').on('show.bs.modal', function () {
            $('#eventStatus input[value="1"]:radio').prop('checked', true);
            $('#eventRoyalty').val('5');
        });

        $('#eventModal').off('hidden.bs.modal').on('hidden.bs.modal', function () {
            // TODO: Add i18n
            $('#eventModal .card-header h5').text('Add Event');
            $('#eventModal #eventModalSubmit').text('Save');
            $('#eventModal .event-header .date').text('Date');
            $('#eventModal .event-header .league-icon').attr('src', '').hide();
            $('#eventModal .event-header .league-name').text('League');

            $('#eventModal .event-preview  .team-logo img').attr('src', '').hide();
            $('#eventModal .event-preview  .team-left .team-name').text('Team 1');
            $('#eventModal .event-preview  .team-right .team-name').text('Team 2');
            $('#eventModal .event-preview  .team .odds').empty().append('<i>x</i>1');

            $('#eventModal .event-preview .center .odds-percentage').text('50%');
            $('#eventModal .event-preview .center .event-format span').empty();
            $('#eventModal .event-preview .center .event-format .discipline-icon').attr('src', '').hide();

            $('#eventLeagueSelect option, #eventSecondTeamSelect option, #eventFirstTeamSelect option').remove();
            $('#eventLeagueSelect, #eventFirstTeamSelect, #eventSecondTeamSelect').attr('disabled', true);
            $('#eventOutcomeCollapse .accordion-body').empty();

            isEventEditing = false;
        });

        $('#eventDisciplineSelect').change(function () {
            $('#eventLeagueSelect option, #eventSecondTeamSelect option, #eventFirstTeamSelect option').remove();
            $(`<option value="0"></option>`).appendTo($('#eventLeagueSelect, #eventSecondTeamSelect, #eventFirstTeamSelect'));

            $('#eventFirstTeamSelect, #eventSecondTeamSelect').attr('disabled', true);

            $('#eventModal .event-preview .event-header .league-icon').attr('src', '').hide();
            $('#eventModal .event-preview .event-header .league-name').text('League');
            $('#eventModal .event-info .team .team-logo img').attr('src', '').hide();
            $('#eventModal .event-preview .event-info .team-left .team-name').text('Team 1');
            $('#eventModal .event-preview .event-info .team-right .team-name').text('Team 2');

            if ($('#eventDisciplineSelect').val() > 0) {
                $('#eventLeagueSelect').attr('disabled', false);

                let disciplineId = $('#eventDisciplineSelect').val();
                let disciplineLogo = disciplines.find(d => d.Id == disciplineId).Logo;
                $('#eventModal .event-preview .event-info .discipline-icon').hide().attr('src', disciplineLogo).fadeIn(1000);

                postData(ACTION_URL, {
                    action: 'loadLeague',
                    discipline: parseInt($('#eventDisciplineSelect').val())
                }).then((response) => {
                    if (response.ok) {
                        return response.json();
                    }
                    return Promise.reject(response);
                }).then(function (response) {
                    if (response.status === 'ok') {
                        response.data.forEach(league => {
                            $(`<option value="${league.id}" data-icon="${league.leagueIcon.path}">${league.leagueName}</option>`).appendTo($('#eventLeagueSelect'));
                        });
                        $('.selectpicker').selectpicker('refresh');
                    } else if (response.status === 'exception') {
                        notify('error', 'Error', 'There was an error loading the league list.');
                    }
                }).catch((error) => console.log('Something went wrong.', error));

            } else {
                $('#eventLeagueSelect').attr('disabled', true);
                $('#eventModal .event-preview .event-info .discipline-icon').attr('src', '').hide();
            }
            $('.selectpicker').selectpicker('refresh');
        });

        $('#eventLeagueSelect').change(function () {
            $('#eventFirstTeamSelect option, #eventSecondTeamSelect option').remove();
            $(`<option value="0"></option>`).appendTo($('#eventFirstTeamSelect, #eventSecondTeamSelect'));

            $('#eventModal .event-info .team .team-logo img').attr('src', '').hide();
            $('#eventModal .event-preview .event-info .team-left .team-name').text('Team 1');
            $('#eventModal .event-preview .event-info .team-right .team-name').text('Team 2');

            if ($('#eventLeagueSelect').val() > 0) {
                $('#eventFirstTeamSelect, #eventSecondTeamSelect').attr('disabled', false);

                let leagueId = $('#eventLeagueSelect').val();
                let leagueName = $(`#eventLeagueSelect option[value=${leagueId}]`).text();
                let leagueIcon = $(`#eventLeagueSelect option[value=${leagueId}]`).data('icon');
                $('#eventModal .event-preview .event-header .league-icon').hide().attr('src', leagueIcon).fadeIn(1000);
                $('#eventModal .event-preview .event-header .league-name').text(leagueName);

                postData(ACTION_URL, {
                    action: 'loadTeam',
                    discipline: parseInt($('#eventDisciplineSelect').val())
                }).then((response) => {
                    if (response.ok) {
                        return response.json();
                    }
                    return Promise.reject(response);
                }).then(function (response) {
                    if (response.status === 'ok') {
                        response.data.forEach(team => {
                            $(`<option value="${team.id}" data-logo="${team.teamLogo.path}">${team.teamName}</option>`).appendTo($('#eventFirstTeamSelect, #eventSecondTeamSelect'));
                        });
                        $('.selectpicker').selectpicker('refresh');
                    } else if (response.status === 'exception') {
                        notify('error', 'Error', 'There was an error loading the league list.');
                    }
                }).catch((error) => console.log('Something went wrong.', error));
            } else {
                $('#eventFirstTeamSelect, #eventSecondTeamSelect').attr('disabled', true);
                $('#eventModal .event-preview .event-header .league-name').text('League');
                $('#eventModal .event-preview .event-header .league-icon').attr('src', '').hide();
            }

            $('.selectpicker').selectpicker('refresh');
        });

        $('#eventFirstTeamSelect').change(function () {
            $('#eventFormatSelect').trigger('change');

            if ($('#eventFirstTeamSelect').val() > 0) {
                let firstTeamId = $('#eventFirstTeamSelect').val();
                let firstTeamName = $(`#eventFirstTeamSelect option[value=${firstTeamId}]`).text();
                let firstTeamLogo = $(`#eventFirstTeamSelect option[value=${firstTeamId}]`).data('logo');

                $('#eventModal .event-preview .event-info .team-left .team-name').text(firstTeamName);
                $('#eventModal .event-info .team-left .team-logo img').hide().attr('src', firstTeamLogo).fadeIn(1000);
            } else {
                $('#eventModal .event-preview .event-info .team-left .team-name').text('Team 1');
                $('#eventModal .event-info .team-left .team-logo img').attr('src', '').hide();
            }
        });

        $('#eventSecondTeamSelect').change(function () {
            $('#eventFormatSelect').trigger('change');

            if ($('#eventSecondTeamSelect').val() > 0) {
                let secondTeamId = $('#eventSecondTeamSelect').val();
                let secondTeamName = $(`#eventFirstTeamSelect option[value=${secondTeamId}]`).text();
                let secondTeamLogo = $(`#eventSecondTeamSelect option[value=${secondTeamId}]`).data('logo');

                $('#eventModal .event-preview .event-info .team-right .team-name').text(secondTeamName);
                $('#eventModal .event-info .team-right .team-logo img').hide().attr('src', secondTeamLogo).fadeIn(1000);
            } else {
                $('#eventModal .event-preview .event-info .team-right .team-name').text('Team 2');
                $('#eventModal .event-info .team-right .team-logo img').attr('src', '').hide();
            }
        });

        $('#eventFormatSelect').change(function () {
            $('#eventModal .event-preview .center .event-format span').empty();
            $('#eventOutcomeCollapse .accordion-body').empty();


            if ($('#eventFormatSelect').val() > 0) {
                let eventDisciplineId = $('#eventDisciplineSelect').val();
                let eventFormatId = $('#eventFormatSelect').val();
                let firstTeamId = $('#eventFirstTeamSelect').val();
                let secondTeamId = $('#eventSecondTeamSelect').val();
                let firstTeamName = $(`#eventFirstTeamSelect option[value=${firstTeamId}]`).text();
                let secondTeamName = $(`#eventSecondTeamSelect option[value=${secondTeamId}]`).text();

                let eventFormatName = eventFormat.find(f => f.Id == eventFormatId).Name;
                $('#eventModal .event-preview .center .event-format span').text(eventFormatName);

                eventOutcome.forEach(outcome => {
                        if (eventFormatId >= outcome.Format || outcome.Format == "all") {
                            if (eventDisciplineId == outcome.Discipline || outcome.Discipline == "all") {
                                let eventOutcomeTemplate = $($("#eventOutcomeTemplate").html());

                                eventOutcomeTemplate.attr('data-type', outcome.Id);
                                eventOutcomeTemplate.find('label[for=firstUpshot]').text(firstTeamName);
                                eventOutcomeTemplate.find('label[for=secondUpshot]').text(secondTeamName);

                                eventOutcomeTemplate.find(':input:radio').each(function (index, item) {
                                    $(item).attr({
                                        'id': $(this).attr('id') + outcome.Id,
                                        'name': outcome.Id,
                                    });
                                });
                                eventOutcomeTemplate.find('label').each(function (index, item) {
                                    $(item).attr('for', $(this).attr('for') + outcome.Id);
                                });
                                eventOutcomeTemplate.find('.outcome-type-name').text(outcome.Name);

                                $('#eventModal #eventOutcomeCollapse .accordion-body').append(eventOutcomeTemplate);
                            }
                        }
                    }
                );

            }
        });

        $('#eventDatetimeStart').change(function () {
            if ($('#eventDatetimeStart').val()) {
                let formattedDate = dayjs($('#eventDatetimeStart').val()).format('ddd, DD MMM YYYY HH:mm');
                $('#eventModal .event-preview .event-header .date').text(formattedDate);
            } else {
                $('#eventModal .event-preview .event-header .date').text('Date');
            }
        });

        $('#eventModal #eventModalSubmit').off('click').click(function (e) {
            e.preventDefault();

            function Result(eventOutcomeType, resultStatus) {
                this.eventOutcomeType = eventOutcomeType;
                this.resultStatus = resultStatus;
            }


            let eventStatus = $('#eventStatus input:radio:checked').val();
            let disciplineSelectValue = $('#eventDisciplineSelect').val();
            let leagueSelectValue = $('#eventLeagueSelect').val();
            let firstTeamSelectValue = $('#eventFirstTeamSelect').val();
            let secondTeamSelectValue = $('#eventSecondTeamSelect').val();
            let formatSelectValue = $('#eventFormatSelect').val();
            let startDateValue = $('#eventDatetimeStart').val();
            let royalty = $('#eventRoyalty').val();

            if (eventStatus > 0
                && disciplineSelectValue > 0
                && leagueSelectValue > 0
                && firstTeamSelectValue > 0
                && secondTeamSelectValue > 0
                && firstTeamSelectValue !== secondTeamSelectValue
                && formatSelectValue > 0
                && startDateValue
                && (royalty >= 0 && royalty <= 100)
            ) {

                let eventResults = [];
                $('#eventOutcomeCollapse .accordion-body .event-outcome').each(function () {
                    let eventOutcomeType = $(this).data('type').toString();
                    let resultStatus = $(this).find('input:radio:checked').val();
                    let result = new Result(eventOutcomeType, resultStatus);
                    if (isEventEditing) {
                        result.eventId = editingItem.id;
                    }
                    eventResults.push(result);
                });

                let startDateTimestamp = dayjs($('#eventDatetimeStart').val()).unix();
                let eventStatusValue = $('#eventStatus input:radio:checked').val();

                let event = {
                    "discipline": parseFloat(disciplineSelectValue),
                    "leagueId": parseFloat(leagueSelectValue),
                    "firstTeamId": parseFloat(firstTeamSelectValue),
                    "secondTeamId": parseFloat(secondTeamSelectValue),
                    "formatId": parseFloat(formatSelectValue),
                    "startDate": startDateTimestamp,
                    "royaltyPercentage": parseFloat(royalty),
                    "status": parseFloat(eventStatusValue),
                    "eventResults": eventResults
                }

                if (isEventEditing) {
                    $("#eventsGrid").jsGrid("updateItem", editingItem, event).then(function () {
                        $('#eventModal').modal('hide');
                    });
                } else {
                    $("#eventsGrid").jsGrid("insertItem", event).then(function () {
                        $('#eventModal').modal('hide');
                    });
                }
            } else {
                // TODO: add i18n
                notify('warning', 'Warning', 'The form has been filled out incorrectly. Please recheck.');
            }
        });
    }

    if ($('#leaguesGrid').length > 0) {
        $('#leaguesGrid').jsGrid({
            fields: [
                {name: "id", title: "Id", type: "number", width: 50, align: "center"},
                {
                    name: "leagueName",
                    title: "League",
                    type: "text",
                    width: 100,
                    align: "center",
                    itemTemplate: function (value, item) {
                        let timestamp = new Date().getTime();
                        let queryString = "?t=" + timestamp;
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2 fw-bold">${item.leagueName}</span>
                                    <img src="${item.leagueIcon.path + queryString}" width="60">
                                </div>`;
                    }
                },
                {
                    name: "discipline",
                    title: "Discipline",
                    type: "select",
                    items: disciplines,
                    valueField: "Id",
                    textField: "Name",
                    width: 100,
                    itemTemplate: function (value, item) {
                        let disciplineName = disciplines.find(d => d.Id == value).Name;
                        let disciplineLogo = disciplines.find(d => d.Id == value).Logo;
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2">${disciplineName}</span>
                                    <img src="${disciplineLogo}" width="30" style="border-radius: 5px">
                                </div>`;
                    }
                },
                {
                    type: "control",
                    editButton: false,
                    deleteButton: false,
                    clearFilterButton: true,
                    modeSwitchButton: true,
                }
            ],

            autoload: true,
            controller: {
                loadData: function (filter) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "loadLeague"}, filter))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('info', 'Success', 'Leagues were successfully loaded.');
                            d.resolve(response.data);
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'Unable to load leagues from database');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'Unable to load leagues from database');
                        d.reject();
                    });
                    return d.promise();
                },

                insertItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "insertLeague"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'League was successfully added.');
                            item.id = response.id;
                            item.leagueIcon = {
                                "path": response.path
                            };
                            d.resolve(item);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error adding the league!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error adding the league!');
                        d.reject();
                    });
                    return d.promise();
                },

                updateItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "updateLeague"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'League was successfully updated.');
                            item.leagueIcon = {
                                "path": response.path
                            };
                            d.resolve(item);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error updating the league!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error updating the league!');
                        d.reject();
                    });
                    return d.promise();
                },

                deleteItem: function (item) {
                    let d = $.Deferred();
                    return $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "deleteLeague"}, item))
                    }).done(function (response) {
                        notify('success', 'Success', 'League was successfully deleted.');
                        d.resolve(item);
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error deleting the league!');
                        d.reject();
                    });
                    return d.promise();
                },
            },

            width: "100%",
            height: "auto",

            heading: true,
            filtering: true,
            inserting: false,
            editing: true,
            selecting: true,
            sorting: true,
            paging: true,
            pageLoading: false,

            rowClick: function (args) {
                // TODO: Add i18n

                isLeagueEditing = true;
                editingItem = args.item;

                let timestamp = new Date().getTime();
                let queryString = "?t=" + timestamp;

                $('#leagueModal').modal('show');
                $('#leagueModal .card-header h5').text('Edit league');
                $('#leagueModal #leagueModalSubmit').text('Update');

                let league = args.item;
                $('#leagueModal #leagueDisciplineSelect').val(league.discipline);
                $('#leagueModal #leagueName').val(league.leagueName);
                $('#leagueModal #leagueIconPreview').attr('src', league.leagueIcon.path + queryString).fadeIn(1000);
            },

            pageIndex: 1,
            pageSize: 10,
            pageButtonCount: 10,
        });
    }

    if ($('#leagueModal').length > 0) {
        var leagueIconBase64;

        $('#leagueModal').off('hidden.bs.modal').on('hidden.bs.modal', function () {
            // TODO: Add i18n
            $('#leagueModal .card-header h5').text('Add league');
            $('#leagueModal #leagueModalSubmit').text('Save');
            $('#leagueModal #leagueIconPreview').attr('src', '').hide();
            isLeagueEditing = false;
        });

        $('#leagueModal #leagueDisciplineSelect').off('change').on('change', function () {
            let disciplineSelectValue = $('#leagueDisciplineSelect').val();
            if (disciplineSelectValue > 0) {
                $('#leagueDisciplineSelect').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#leagueDisciplineSelect').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#leagueModal #leagueName').off('input').on('input', function () {
            let leagueName = $('#leagueName').val();
            if (leagueName.length > 0) {
                $('#leagueName').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#leagueName').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#leagueModal #leagueIcon').off('change').on('change', function () {
            let fileLength = $('#leagueIcon').get(0).files.length;
            if (fileLength > 0) {
                let imageFile = $('#leagueIcon').get(0).files[0];

                let fileReader = new FileReader();
                fileReader.onload = function () {
                    $("#leagueIconPreview").attr('src', fileReader.result).fadeIn(1000);
                    leagueIconBase64 = fileReader.result;
                }
                fileReader.readAsDataURL(imageFile);
                $('#leagueIcon').removeClass('is-invalid').addClass('is-valid');
            } else {
                $("#leagueIconPreview").fadeOut(1000).queue(function () {
                    $("#leagueIconPreview").delay(1000).attr('src', '');
                    $(this).dequeue();
                });
                $('#leagueIcon').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#leagueModal #leagueModalSubmit').off('click').click(function (e) {
            e.preventDefault();

            let leagueName = $('#leagueName').val();
            let disciplineValue = $('#leagueDisciplineSelect').val();
            let isImageSelected = $('#leagueIcon').get(0).files.length > 0;

            if (validateName(leagueName)
                && disciplineValue > 0
                && (isImageSelected || isLeagueEditing)) {

                let league = {
                    "leagueName": leagueName,
                    "discipline": parseFloat(disciplineValue),
                    "leagueIcon": {
                        "path": isImageSelected ? leagueIconBase64 : ""
                    }
                }

                if (isLeagueEditing) {
                    $("#leaguesGrid").jsGrid("updateItem", editingItem, league).then(function () {
                        $('#leagueModal').modal('hide');
                    });
                } else {
                    $("#leaguesGrid").jsGrid("insertItem", league).then(function () {
                        $('#leagueModal').modal('hide');
                    });
                }
            } else {
                if (!$('#leagueName').val()) {
                    $('#leagueName').addClass('is-invalid');
                }
                if (!$('#leagueDisciplineSelect').val()) {
                    $('#leagueDisciplineSelect').addClass('is-invalid');
                }
                if (!$('#leagueIcon').val() && !isLeagueEditing) {
                    $('#leagueIcon').addClass('is-invalid');
                }
            }
        });
    }

    if ($('#teamsGrid').length > 0) {
        $('#teamsGrid').jsGrid({
            fields: [
                {name: "id", title: "Id", type: "number", width: 50, align: "center"},
                {
                    name: "teamName",
                    title: "Team",
                    type: "text",
                    width: 100,
                    align: "center",
                    itemTemplate: function (value, item) {
                        let timestamp = new Date().getTime();
                        let queryString = "?t=" + timestamp;
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2 fw-bold">${item.teamName}</span>
                                    <img src="${item.teamLogo.path + queryString}" width="60");>
                                </div>`;
                    }
                },
                {
                    name: "discipline",
                    title: "Discipline",
                    type: "select",
                    items: disciplines,
                    valueField: "Id",
                    textField: "Name",
                    width: 100,
                    itemTemplate: function (value, item) {
                        let disciplineName = disciplines.find(d => d.Id == value).Name;
                        let disciplineLogo = disciplines.find(d => d.Id == value).Logo;
                        return `<div class="d-flex flex-column justify-content-center align-items-center">
                                    <span class="mb-2">${disciplineName}</span>
                                    <img src="${disciplineLogo}" width="30" style="border-radius: 5px">
                                </div>`;
                    }
                },
                {name: "teamRating", title: "Rating", type: "number", width: 100, align: "center"},
                {
                    type: "control",
                    editButton: false,
                    deleteButton: false,
                    clearFilterButton: true,
                    modeSwitchButton: true,
                }
            ],

            autoload: true,
            controller: {
                loadData: function (filter) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "loadTeam"}, filter))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('info', 'Success', 'Teams were successfully loaded.');
                            d.resolve(response.data);
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'Unable to load teams from database');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'Unable to load teams from database');
                        d.reject();
                    });
                    return d.promise();
                },

                insertItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "insertTeam"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'Team was successfully added.');
                            item.id = response.id;
                            item.teamLogo = {
                                "path": response.path
                            };
                            d.resolve(item);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error adding the team!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error adding the team!');
                        d.reject();
                    });
                    return d.promise();
                },

                updateItem: function (item) {
                    let d = $.Deferred();
                    $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "updateTeam"}, item))
                    }).done(function (response) {
                        if (response.status === 'ok') {
                            notify('success', 'Success', 'Team was successfully updated.');
                            item.teamLogo = {
                                "path": response.path
                            };
                            d.resolve(item);
                        } else if (response.status === 'deny') {
                            notify('warning', 'Warning', 'Incorrect data was sent!');
                            d.reject();
                        } else if (response.status === 'exception') {
                            notify('error', 'Error', 'There was an error updating the team!');
                            d.reject();
                        }
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error updating the team!');
                        d.reject();
                    });
                    return d.promise();
                },

                deleteItem: function (item) {
                    let d = $.Deferred();
                    return $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "deleteTeam"}, item))
                    }).done(function (response) {
                        notify('success', 'Success', 'Team was successfully deleted.');
                        d.resolve(item);
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error deleting the team!');
                        d.reject();
                    });
                    return d.promise();
                },
            },

            width: "100%",
            height: "auto",

            heading: true,
            filtering: true,
            inserting: false,
            editing: true,
            selecting: true,
            sorting: true,
            paging: true,
            pageLoading: false,

            rowClick: function (args) {
                // TODO: Add i18n

                isTeamEditing = true;
                editingItem = args.item;

                let timestamp = new Date().getTime();
                let queryString = "?t=" + timestamp;

                $('#teamModal').modal('show');
                $('#teamModal .card-header h5').text('Edit team');
                $('#teamModal #teamModalSubmit').text('Update');

                let team = args.item;

                $('#teamModal #teamDisciplineSelect').val(team.discipline);
                $('#teamModal #teamName').val(team.teamName);
                $('#teamModal #teamRating').val(team.teamRating);
                $('#teamModal #teamLogoPreview').attr('src', team.teamLogo.path + queryString).fadeIn(1000);
            },

            pageIndex: 1,
            pageSize: 10,
            pageButtonCount: 10,
        });
    }

    if ($('#teamModal').length > 0) {
        var teamLogoBase64;

        $('#teamModal').off('hidden.bs.modal').on('hidden.bs.modal', function () {
            // TODO: Add i18n
            $('#teamModal .card-header h5').text('Add team');
            $('#teamModal #teamModalSubmit').text('Save');
            $('#teamModal #teamLogoPreview').attr('src', '').hide();
            isTeamEditing = false;
        });

        $('#teamModal #teamDisciplineSelect').off('change').on('change', function () {
            let disciplineSelectValue = $('#teamDisciplineSelect').val();
            if (disciplineSelectValue > 0) {
                $('#teamDisciplineSelect').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#teamDisciplineSelect').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#teamModal #teamName').off('input').on('input', function () {
            let teamName = $('#teamName').val();
            if (teamName.length > 0) {
                $('#teamName').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#teamName').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#teamModal #teamRating').off('input').on('input', function () {
            let teamRating = $('#teamRating').val();
            if (teamRating.length > 0) {
                if (validateTeamRating(teamRating)) {
                    $('#teamRating').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#teamRating').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#teamRating').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#teamModal #teamLogo').off('change').on('change', function () {
            let fileLength = $('#teamLogo').get(0).files.length;
            if (fileLength > 0) {
                let imageFile = $('#teamLogo').get(0).files[0];

                let fileReader = new FileReader();
                fileReader.onload = function () {
                    $("#teamLogoPreview").attr('src', fileReader.result).fadeIn(1000);
                    teamLogoBase64 = fileReader.result;
                }
                fileReader.readAsDataURL(imageFile);
                $('#teamLogo').removeClass('is-invalid').addClass('is-valid');
            } else {
                $("#teamLogoPreview").fadeOut(1000).queue(function () {
                    $("#teamLogoPreview").delay(1000).attr('src', '');
                    $(this).dequeue();
                });
                $('#teamLogo').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#teamModal #teamModalSubmit').off('click').click(function (e) {
            e.preventDefault();

            let teamName = $('#teamName').val();
            let teamRating = $('#teamRating').val();
            let disciplineValue = $('#teamDisciplineSelect').val();
            let isImageSelected = $('#teamLogo').get(0).files.length > 0;

            if (validateName(teamName)
                && validateTeamRating(teamRating)
                && disciplineValue > 0
                && (isImageSelected || isTeamEditing)) {

                let team = {
                    "teamName": teamName,
                    "teamRating": parseFloat(teamRating),
                    "discipline": parseFloat(disciplineValue),
                    "teamLogo": {
                        "path": isImageSelected ? teamLogoBase64 : ""
                    }
                }

                if (isTeamEditing) {
                    $("#teamsGrid").jsGrid("updateItem", editingItem, team).then(function () {
                        $('#teamModal').modal('hide');
                    });
                } else {
                    $("#teamsGrid").jsGrid("insertItem", team).then(function () {
                        $('#teamModal').modal('hide');
                    });
                }
            } else {
                if (!$('#teamName').val()) {
                    $('#teamName').addClass('is-invalid');
                }
                if (!$('#teamRating').val()) {
                    $('#teamRating').addClass('is-invalid');
                }
                if (!$('#teamDisciplineSelect').val()) {
                    $('#teamDisciplineSelect').addClass('is-invalid');
                }
                if (!$('#teamLogo').val() && !isTeamEditing) {
                    $('#teamLogo').addClass('is-invalid');
                }
            }
        });
    }

    if($('#dashboardContainer').length > 0) {
        var depositsChart = new Chart(
            document.getElementById('depositsChart'), {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        backgroundColor: 'rgb(136, 0, 255)',
                        borderColor: 'rgb(138,74,187)',
                        data: [],
                        cubicInterpolationMode: 'monotone',
                        tension: 0.4,
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: false,
                        },
                        title: {
                            color: 'rgb(255,255,255)',
                            display: true,
                            text: 'Deposit Statistics',
                            font: {
                                size:30
                            }
                        },
                        tooltip: {
                            titleFont: {
                                size: 15,
                            },
                            bodyFont: {
                                size: 20,
                            },
                            callbacks: {
                                label: function (context) {
                                    var label = context.dataset.label || '';

                                    if (label) {
                                        label += ': ';
                                    }
                                    if (context.parsed.y !== null) {
                                        label += new Intl.NumberFormat('en-US', {
                                            style: 'currency',
                                            currency: 'USD'
                                        }).format(context.parsed.y);
                                    }
                                    return label;
                                }
                            },
                        },
                    },
                    scales: {
                        y: {
                            grid: {
                                display:true,
                                color: 'rgb(80,80,80)',
                                borderColor: 'rgb(255,255,255)',
                            },
                            title: {
                                display: true,
                                text: 'Amount'
                            },
                            ticks: {
                                color: 'rgb(255,255,255)',
                                font: {
                                    size:15
                                }
                            }
                        },
                        x: {
                            grid: {
                                display:true,
                                color: 'rgb(80,80,80)',
                                borderColor: 'rgb(255,255,255)',
                            },
                            title: {
                                display: true,
                                text: 'Date'
                            },
                            ticks: {
                                color: 'rgb(255,255,255)',
                                font: {
                                    size:15
                                }
                            }
                        },
                    },
                    interaction: {
                        mode: 'index',
                        intersect: false,
                    },
                }
            }
        );

        var registrationsChart = new Chart(
            document.getElementById('registrationsChart'), {
                type: 'line',
                data: {
                    labels: [],
                    datasets: [{
                        backgroundColor: 'rgb(136, 0, 255)',
                        borderColor: 'rgb(138,74,187)',
                        data: [],
                        cubicInterpolationMode: 'monotone',
                        tension: 0.4,
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            display: false,
                        },
                        title: {
                            color: 'rgb(255,255,255)',
                            display: true,
                            text: 'Registration Statistics',
                            font: {
                                size:30
                            }
                        },
                        tooltip: {
                            titleFont: {
                                size: 15,
                            },
                            bodyFont: {
                              size: 20,
                            },
                            callbacks: {
                                label: function (context) {
                                    var label = context.dataset.label || '';

                                    if (label) {
                                        label += ': ';
                                    }
                                    if (context.parsed.y !== null) {
                                        label += context.parsed.y + ' user(s)'
                                    }
                                    return label;
                                }
                            },
                        },
                    },
                    scales: {
                        y: {
                            grid: {
                                display:true,
                                color: 'rgb(80,80,80)',
                                borderColor: 'rgb(255,255,255)',
                            },
                            title: {
                                display: true,
                                text: 'Registered Users'
                            },
                            ticks: {
                                stepSize: 1,
                                color: 'rgb(255,255,255)',
                                font: {
                                    size:15
                                }
                            }
                        },
                        x: {
                            grid: {
                                display:true,
                                color: 'rgb(80,80,80)',
                                borderColor: 'rgb(255,255,255)',
                            },
                            title: {
                                display: true,
                                text: 'Date'
                            },
                            ticks: {
                                color: 'rgb(255,255,255)',
                                font: {
                                    size:15
                                }
                            }
                        },
                    },
                    interaction: {
                        mode: 'index',
                        intersect: false,
                    },
                }
            }
        );

        postData(ACTION_URL, {
            "action": "loadChartDepositsData",
        }).then((response) => {
                if (response.ok) {
                    return response.json();
                }
                return Promise.reject(response);
            }
        ).then(function (response) {
            if (response.status === 'ok') {
                let data = JSON.parse(response.data);
                Object.keys(data).forEach(function (key) {
                    depositsChart.data.labels.push(key);
                    depositsChart.data.datasets[0].data.push(data[key]);
                });
                depositsChart.update();
            }
        }).catch((error) => console.log('Something went wrong.', error));

        postData(ACTION_URL, {
            "action": "loadChartRegistrationsData",
        }).then((response) => {
                if (response.ok) {
                    return response.json();
                }
                return Promise.reject(response);
            }
        ).then(function (response) {
            if (response.status === 'ok') {
                let data = JSON.parse(response.data);
                Object.keys(data).forEach(function (key) {
                    registrationsChart.data.labels.push(key);
                    registrationsChart.data.datasets[0].data.push(data[key]);
                });
                registrationsChart.update();
            }
        }).catch((error) => console.log('Something went wrong.', error));
    }


    if ($('#registerModal').length > 0) {
        $('#registerModal #registerEmail').off('input').on('input', function () {
            let email = $('#registerEmail').val();
            if (email.length > 0) {
                if (validateEmail(email)) {
                    $('#registerEmail').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#registerEmail').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#registerEmail').removeClass('is-valid is-invalid');
            }
        });

        $('#registerModal #registerPassword').off('input').on('input', function () {
            let password = $('#registerPassword').val();
            if (password.length > 0) {
                if (validatePasswordLength(password)) {
                    $('#registerPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#registerPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#registerPassword').removeClass('is-valid is-invalid');
            }
            $('#registerRepeatedPassword').trigger('input');
        });

        $('#registerModal #registerRepeatedPassword').off('input').on('input', function () {
            let password = $('#registerPassword').val();
            let repeatedPassword = $('#registerRepeatedPassword').val();
            if (repeatedPassword.length > 0) {
                if (validatePasswordMatching(password, repeatedPassword)) {
                    $('#registerRepeatedPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#registerRepeatedPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#registerRepeatedPassword').removeClass('is-valid is-invalid');
            }
        });

        $('#registerModal button.register').off('click').click(function (e) {
            e.preventDefault();
            let email = $('#registerEmail').val();
            let password = $('#registerPassword').val();
            let repeatedPassword = $('#registerRepeatedPassword').val();

            if (validateEmail(email)
                && validatePasswordLength(password)
                && validatePasswordMatching(password, repeatedPassword)) {

                postData(ACTION_URL, {
                    "action": "register",
                    "email": email,
                    "password": password,
                    "repeatedPassword": repeatedPassword
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (data) {
                    if (data.status === 'ok') {
                        reloadPage();
                    } else if (data.status === 'deny') {
                        $('#emailAlreadyExist').show(200).delay(3000).hide(200);
                    }
                }).catch((error) => console.log('Something went wrong.', error));

            } else {
                if (!$('#registerEmail').val()) {
                    $('#registerEmail').addClass('is-invalid');
                }
                if (!$('#registerPassword').val()) {
                    $('#registerPassword').addClass('is-invalid');
                }
                if (!$('#registerRepeatedPassword').val()) {
                    $('#registerRepeatedPassword').addClass('is-invalid');
                }
            }
        });
    }

    if ($('#loginModal').length > 0) {
        $('#loginModal #loginEmail').off('input').on('input', function () {
            let email = $('#loginEmail').val();
            if (email.length > 0) {
                if (validateEmail(email)) {
                    $('#loginEmail').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#loginEmail').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#loginEmail').removeClass('is-valid is-invalid');
            }
        });

        $('#loginModal #loginPassword').off('input').on('input', function () {
            let password = $('#loginPassword').val();
            if (password.length > 0) {
                if (validatePasswordLength(password)) {
                    $('#loginPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#loginPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#loginPassword').removeClass('is-valid is-invalid');
            }
        });

        $('#loginModal button.login').off('click').click(function (e) {
            e.preventDefault();
            let email = $('#loginEmail').val();
            let password = $('#loginPassword').val();

            if (validateEmail(email)
                && validatePasswordLength(password)) {

                postData(ACTION_URL, {
                    "action": "login",
                    "email": email,
                    "password": password,
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (data) {
                    if (data.status === 'ok') {
                        reloadPage();
                    } else if (data.status === 'deny') {
                        $('#wrongCredentials').show(200).delay(3000).hide(200);
                    }
                }).catch((error) => console.log('Something went wrong.', error));

            } else {
                if (!$('#loginEmail').val()) {
                    $('#loginEmail').addClass('is-invalid');
                }
                if (!$('#loginPassword').val()) {
                    $('#loginPassword').addClass('is-invalid');
                }
            }
        });
    }

    if ($('#logout').length > 0) {
        $('#logout').off('click').click(function (e) {
            e.preventDefault();

            postData(ACTION_URL, {
                "action": "logout"
            }).then((response) => {
                    if (response.ok) {
                        return response.json();
                    }
                    return Promise.reject(response);
                }
            ).then(function (data) {
                if (data.status === 'ok') {
                    reloadPage();
                }
            }).catch((error) => console.log('Something went wrong.', error));
        });
    }

    function reloadBetModal(eventId) {
        $('#betModal .bet-preview .team img').removeClass('team-logo-flicker');

        postData(ACTION_URL, {
            action: "loadBetModal",
            id: eventId
        }).then((response) => {
                if (response.ok) {
                    return response.text();
                }
                return Promise.reject(response);
            }
        ).then(function (response) {
            $('#betModal .outcomes')
                .html($(response).find('.outcomes').html());

            $('#betModal .spinner-border').hide();
        }).catch((error) => console.log('Something went wrong.', error));
    }

    if ($('#eventsContainer').length > 0) {
        loadEventSection();

        var eventId = null;
        $('#eventsContainer').on('click', '#currentEvents .team', function (event) {
            if (auth) {
                let eventInfo = $(this).closest('.event');

                eventId = eventInfo.data('id');
                let leagueName = $(eventInfo).find('.league-name').text();
                let disciplineIcon = $(eventInfo).find('.discipline-icon').attr('src');
                let eventFormat = $(eventInfo).find('.event-format span').text();
                let teamLeftName = $(eventInfo).find('.team-left .team-name').text();
                let teamLeftLogo = $(eventInfo).find('.team-left .team-logo img').attr('src');
                let teamRightName = $(eventInfo).find('.team-right .team-name').text();
                let teamRightLogo = $(eventInfo).find('.team-right .team-logo img').attr('src');
                let startDate = $(eventInfo).attr('data-start');

                $('#betModal .discipline-icon').attr('src', disciplineIcon);
                $('#betModal .league-name').text(leagueName);
                $('#betModal .event-format').text(eventFormat);
                $('#betModal .team-left .team-name').text(teamLeftName);
                $('#betModal .team-left .team-logo img').attr('src', teamLeftLogo);
                $('#betModal .team-right .team-name').text(teamRightName);
                $('#betModal .team-right .team-logo img').attr('src', teamRightLogo);

                $('#betModal .timer').attr('data-start', startDate);

                if ($(eventInfo).find('.live-icon').length > 0) {
                    $('#betModal .live-icon').show();
                } else {
                    $('#betModal .live-icon').hide();
                }

                $('#betModal .spinner-border').show();
                $('#betModal').modal('show');

                postData(ACTION_URL, {
                    action: "loadBetModal",
                    id: eventId
                }).then((response) => {
                        if (response.ok) {
                            return response.text();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (response) {
                    $('#betModal .outcomes')
                        .html($(response).find('.outcomes').html());

                    $('#betModal .spinner-border').hide();
                }).catch((error) => console.log('Something went wrong.', error));
            } else {
                $('#loginModal').modal('show');
            }
        });

        $('#betModal').on('click', '.flip-box-front .place-bet', function () {
            $(this).closest('.flip-box-inner')
                .find('.flip-box-back .bet-confirm')
                .attr('data-upshot', $(this).data('upshot'));

            if ($(this).data('upshot') == 1) {
                $('#betModal .team-left .team-logo img').addClass('team-logo-flicker');
            } else if ($(this).data('upshot') == 2) {
                $('#betModal .team-right .team-logo img').addClass('team-logo-flicker');
            }

            let buttonUpshot = $(this).data('upshot');
            let coefficient = 0;

            if (buttonUpshot == 1) {
                coefficient = $(this).siblings('.left-odds').find('span').text();
            } else if (buttonUpshot == 2) {
                coefficient = $(this).siblings('.right-odds').find('span').text();
            }
            $(this).closest('.flip-box-inner').find('.flip-box-back .odds').find('span').text(coefficient);
            if ($(this).find('.sum').length) {
                $(this).closest('.flip-box-inner').find('.flip-box-back .bet-amount').val($(this).find('.sum').text().trim());
                $(this).closest('.flip-box-inner').find('.flip-box-back .bet-amount').trigger('keypress');
            }

            $(this).closest('.flip-box-inner').css('transform', 'rotateY(180deg)');
        });

        $('#betModal').on('keypress keyup blur', '.flip-box-back .bet-amount', function (event) {
            $(this).val($(this).val().replace(/[^\d].+/, ""));
            if ((event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }

            if ($(this).val()) {
                let coefficient = $(this).closest('.flip-box-back').find('.odds span').text().trim();
                let potentialPrize = Number(($(this).val() * coefficient).toFixed(2));
                $(this).closest('.flip-box-back')
                    .find('.potential-prize')
                    .html(`<i class="fas fa-dollar-sign me-1">
                           </i><span class="fw-bold">${potentialPrize}</span>`);
            } else {
                $(this).closest('.flip-box-back').find('.potential-prize').text('~');
            }
        });

        $('#betModal').on('click', '.flip-box-back .cancel', function () {
            let currentUpshot = $(this).closest('.flip-box-back')
                .find('.bet-confirm')
                .attr('data-upshot');

            $(this).closest('.flip-box-inner').css('transform', 'rotateY(0deg)');
            $(this).closest('.flip-box-back').find('.bet-confirm').attr('data-upshot', '');

            let currentUpshotOpenedBoxCounter = 0;
            $('#betModal .flip-box-back .bet-confirm').each(function () {
                if ($(this).attr('data-upshot').length
                    && $(this).attr('data-upshot') == currentUpshot) {
                    currentUpshotOpenedBoxCounter++;
                }
            });
            if (currentUpshotOpenedBoxCounter == 0) {
                if (currentUpshot == 1) {
                    $('#betModal .bet-preview .team-left img').removeClass('team-logo-flicker');
                } else if (currentUpshot == 2) {
                    $('#betModal .bet-preview .team-right img').removeClass('team-logo-flicker');
                }
            }

            $(this).closest('.flip-box-back').find('.bet-amount').val('');
            $(this).closest('.flip-box-back').find('.potential-prize span').text('~');
        });

        $('#betModal').on('click', '.flip-box-back .place-bet', function () {
            let currentBox = $(this).closest('.flip-box-inner');

            let eventResultId = $(this).closest('.flip-box-inner').find('.flip-box-front .bet-outcome').attr('data-id');
            let upshotId = $(this).closest('.flip-box-back').find('.bet-confirm').attr('data-upshot');
            let amount = $(this).closest('.flip-box-back').find('.bet-amount').val();

            if (amount > 0) {
                postData(ACTION_URL, {
                    action: "placeBet",
                    "eventResultId": parseInt(eventResultId),
                    "upshotId": parseInt(upshotId),
                    "amount": parseInt(amount)
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (response) {
                    if (response.balance != null) {
                        $('#balance').fadeOut(200).text(parseFloat(response.balance).toFixed(2)).fadeIn(200);
                    }
                    $(currentBox).css('transform', 'rotateY(0deg)');
                    setTimeout(function () {
                        reloadBetModal(eventId);
                    }, 500);
                }).catch((error) => console.log('Something went wrong.', error));
            } else {
                $('#betAmountFieldBlank').show(200).delay(2000).hide(200);
            }
        });

        $('#betModal').on('click', '.flip-box-back .refund-bet', function () {
            let currentBox = $(this).closest('.flip-box-inner');
            let eventResultId = $(this).closest('.flip-box-inner').find('.flip-box-front .bet-outcome').attr('data-id');

            postData(ACTION_URL, {
                action: "refundBet",
                "eventResultId": parseInt(eventResultId),
            }).then((response) => {
                    if (response.ok) {
                        return response.json();
                    }
                    return Promise.reject(response);
                }
            ).then(function (response) {
                if (response.balance != null) {
                    $('#balance').fadeOut(200).text(parseFloat(response.balance).toFixed(2)).fadeIn(200);
                }
                $(currentBox).css('transform', 'rotateY(0deg)');
                setTimeout(function () {
                    reloadBetModal(eventId);
                }, 500);
            }).catch((error) => console.log('Something went wrong.', error));
        });

        $('#betModal').on('click', '.flip-box-back .edit-bet', function () {
            let currentBox = $(this).closest('.flip-box-inner');

            let eventResultId = $(this).closest('.flip-box-inner').find('.flip-box-front .bet-outcome').attr('data-id');
            let upshotId = $(this).closest('.flip-box-back').find('.bet-confirm').attr('data-upshot');
            let amount = $(this).closest('.flip-box-back').find('.bet-amount').val();

            if (amount > 0) {
                postData(ACTION_URL, {
                    action: "updateBet",
                    "eventResultId": parseInt(eventResultId),
                    "upshotId": parseInt(upshotId),
                    "amount": parseInt(amount)
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (response) {
                    if (response.balance != null) {
                        $('#balance').fadeOut(200).text(parseFloat(response.balance).toFixed(2)).fadeIn(200);
                    }
                    $(currentBox).css('transform', 'rotateY(0deg)');
                    setTimeout(function () {
                        reloadBetModal(eventId);
                    }, 500);
                }).catch((error) => console.log('Something went wrong.', error));
            } else {
                $('#betAmountFieldBlank').show(200).delay(2000).hide(200);
            }
        });

        $('#betModal').off('show.bs.modal').on('show.bs.modal', function () {
            $('#betModal .team-logo img').removeClass('team-logo-flicker');
            $('#betModal .timer').text('00:00:00');
        });
    }

    if ($('.discipline-filter').length > 0) {
        reloadDisciplineFilter();

        $('.discipline').off('click').click(function () {
            let disciplineCookie = getCookie('discipline_filter');
            let selectedDisciplines = disciplineCookie != null
                ? disciplineCookie.split('|').filter(e => e)
                : [];

            if ($(this).hasClass('active')) {
                let filterToRemove = $(this).attr('data-discipline');
                let filterToRemoveIndex = selectedDisciplines.indexOf(filterToRemove);

                if (filterToRemoveIndex != null) {
                    selectedDisciplines.splice(filterToRemoveIndex, 1);
                }

                if (selectedDisciplines.length == 0) {
                    let filterToAdd = $('.discipline').first().attr('data-discipline');
                    if (!selectedDisciplines.includes(filterToAdd)) {
                        selectedDisciplines = selectedDisciplines.concat(filterToAdd);
                    }
                }
            } else {
                let filterToAdd = $(this).attr('data-discipline');
                if (!selectedDisciplines.includes(filterToAdd)) {
                    selectedDisciplines = selectedDisciplines.concat(filterToAdd);
                }
            }
            setCookie("discipline_filter", selectedDisciplines.join('|'), 365);
            reloadDisciplineFilter();
            reloadEventSection();
        });
    }

    if ($('#depositContainer').length > 0) {
        $("#menu-toggle").click(function (e) {
            e.preventDefault();
            $("#wrapper").toggleClass("toggled");
        });

        $('#depositContainer .tabs').off('click').click(function () {
            $('#depositContainer  .tab-content').css('opacity', 0);
            $('#depositContainer  .tab-content').fadeTo(500, 1);
        });


        var ccnum = document.getElementById('cp_cardNumber'),
            expiry = document.getElementById('cp_cardExpiry'),
            cvc = document.getElementById('cp_cvv');

        payform.cardNumberInput(ccnum);
        payform.expiryInput(expiry);
        payform.cvcInput(cvc);

        $('#cp_cardNumber').off('input').on('input', function () {
            let cardNumber = $('#cp_cardNumber').val();
            if (payform.validateCardNumber(cardNumber)) {
                $('#cp_cardNumber').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#cp_cardNumber').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#cp_cardExpiry').off('input').on('input', function () {
            let cardExpiry = $('#cp_cardExpiry').val();
            let month, year;
            if (cardExpiry != null) {
                let monthAndYear = cardExpiry.split('/');
                if(monthAndYear.length == 2) {
                    month = monthAndYear[0].trim();
                    year = monthAndYear[1].trim();
                }
            }
            if (payform.validateCardExpiry(month, year)) {
                $('#cp_cardExpiry').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#cp_cardExpiry').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#cp_cvv').off('input').on('input', function () {
            let cvv = $('#cp_cvv').val();
            if (payform.validateCardCVC(cvv, null)) {
                $('#cp_cvv').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#cp_cvv').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#depositContainer .pay-input-group input').on('keypress keyup blur', function (event) {
            $(this).val($(this).val().replace(/[^\d].+/, ""));
            if ((event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }

            if ($(this).val()) {
                $('#depositContainer .payment-button-amount .sum').text($(this).val());
            } else {
                $('#depositContainer .payment-button-amount .sum').text('~');
            }
        });

        // validation
        $('#depositContainer .payment-button').off('click').click(function () {
            let isFormValid = null;
            let paySum = $('.pay-input-group input').val();

            if(paySum > 0) {
                if($('#bankTab').hasClass('active')) {
                    // stub
                    isFormValid = true;
                } else if ($('#cardTab').hasClass('active')) {
                    let cardHolder = $('#cp_cardHolder').val();
                    let cardNumber = $('#cp_cardNumber').val();
                    let cvv = $('#cp_cvv').val();
                    let cardExpiry = $('#cp_cardExpiry').val();
                    let month, year;
                    if (cardExpiry != null) {
                        let monthAndYear = cardExpiry.split('/');
                        if(monthAndYear.length == 2) {
                            month = monthAndYear[0].trim();
                            year = monthAndYear[1].trim();
                        }
                    }
                    if(payform.validateCardNumber(cardNumber)
                        && payform.validateCardExpiry(month, year)
                        && payform.validateCardCVC(cvv, null)
                        && cardHolder.length) {
                        isFormValid = true;
                    } else {
                        isFormValid = false;
                        $('#checkEnteredData').fadeIn(200).delay(2000).fadeOut(200);
                    }
                }

                if(isFormValid) {
                    postData(ACTION_URL, {
                        action: "deposit",
                        "amount": parseFloat(paySum)
                    }).then((response) => {
                            if (response.ok) {
                                return response.json();
                            }
                            return Promise.reject(response);
                        }
                    ).then(function (response) {
                        if (response.status == "ok") {
                            window.location.href = '/';
                        } else if(response.status == "deny") {
                            $('#paymentError').fadeIn(200).delay(2000).fadeOut(200);
                        }
                    }).catch((error) => console.log('Something went wrong.', error));
                }
            } else {
                $('#incorrectPaySum').fadeIn(200).delay(2000).fadeOut(200);
            }
        });
    }

    if ($('#withdrawContainer').length > 0) {
        $("#menu-toggle").click(function (e) {
            e.preventDefault();
            $("#wrapper").toggleClass("toggled");
        });

        $('#withdrawContainer .tabs').off('click').click(function () {
            $('#withdrawContainer  .tab-content').css('opacity', 0);
            $('#withdrawContainer  .tab-content').fadeTo(500, 1);
        });

        var ccnum = document.getElementById('cp_cardNumber');
        payform.cardNumberInput(ccnum);

        $('#cp_cardNumber').off('input').on('input', function () {
            let cardNumber = $('#cp_cardNumber').val();
            if (payform.validateCardNumber(cardNumber)) {
                $('#cp_cardNumber').removeClass('is-invalid').addClass('is-valid');
            } else {
                $('#cp_cardNumber').removeClass('is-valid').addClass('is-invalid');
            }
        });

        $('#withdrawContainer .pay-input-group input').on('keypress keyup blur', function (event) {
            $(this).val($(this).val().replace(/[^\d].+/, ""));
            if ((event.which < 48 || event.which > 57)) {
                event.preventDefault();
            }

            if ($(this).val()) {
                $('#withdrawContainer .payment-button-amount .sum').text($(this).val());
            } else {
                $('#withdrawContainer .payment-button-amount .sum').text('~');
            }
        });

        // validation
        $('#withdrawContainer .payment-button').off('click').click(function () {
            let isFormValid = null;
            let paySum = $('.pay-input-group input').val();

            if(paySum > 0) {
                if($('#bankTab').hasClass('active')) {
                    // stub
                    isFormValid = true;
                } else if ($('#cardTab').hasClass('active')) {
                    let cardHolder = $('#cp_cardHolder').val();
                    let cardNumber = $('#cp_cardNumber').val();

                    if(payform.validateCardNumber(cardNumber) && cardHolder.length) {
                        isFormValid = true;
                    } else {
                        isFormValid = false;
                        $('#checkEnteredData').fadeIn(200).delay(2000).fadeOut(200);
                    }
                }

                if(isFormValid) {
                    postData(ACTION_URL, {
                        action: "withdraw",
                        "amount": parseFloat(paySum)
                    }).then((response) => {
                            if (response.ok) {
                                return response.json();
                            }
                            return Promise.reject(response);
                        }
                    ).then(function (response) {
                        if (response.status === "ok") {
                            window.location.href = '/';
                        } else if(response.status === "deny" || response.status === "exception") {
                            $('#paymentError').fadeIn(200).delay(2000).fadeOut(200);
                        }
                    }).catch((error) => console.log('Something went wrong.', error));
                }
            } else {
                $('#incorrectPaySum').fadeIn(200).delay(2000).fadeOut(200);
            }
        });
    }

    if($('#settingsContainer').length > 0) {
        $('#currentPassword').off('input').on('input', function () {
            let currentPassword = $('#currentPassword').val();
            if (currentPassword.length > 0) {
                if (validatePasswordLength(currentPassword)) {
                    $('#currentPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#currentPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#currentPassword').removeClass('is-valid is-invalid');
            }
        });

        $('#newPassword').off('input').on('input', function () {
            let newPassword = $('#newPassword').val();
            if (newPassword.length > 0) {
                if (validatePasswordLength(newPassword)) {
                    $('#newPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#newPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#newPassword').removeClass('is-valid is-invalid');
            }
            $('#repeatedNewPassword').trigger('input');
        });

        $('#repeatedNewPassword').off('input').on('input', function () {
            let newPassword = $('#newPassword').val();
            let repeatedNewPassword = $('#repeatedNewPassword').val();
            if (repeatedNewPassword.length > 0) {
                if (validatePasswordMatching(newPassword, repeatedNewPassword)) {
                    $('#repeatedNewPassword').removeClass('is-invalid').addClass('is-valid');
                } else {
                    $('#repeatedNewPassword').removeClass('is-valid').addClass('is-invalid');
                }
            } else {
                $('#repeatedNewPassword').removeClass('is-valid is-invalid');
            }
        });

        var previousAvatar = $('.profile-avatar img').attr('src');
        var avatarBase64Input = null;
        $('#avatarInput').off('change').on('change', function () {
            let fileLength = $('#avatarInput').get(0).files.length;
            if (fileLength > 0) {
                let imageFile = $('#avatarInput').get(0).files[0];

                let fileReader = new FileReader();
                fileReader.onload = function () {
                    $('.profile-avatar img').css('opacity', 0)
                        .attr('src', fileReader.result)
                        .fadeTo(500, 1);
                    avatarBase64Input = fileReader.result
                }
                fileReader.readAsDataURL(imageFile);
            } else {
                avatarBase64Input = null;
                $('.profile-avatar img').css('opacity', 0)
                    .attr('src', previousAvatar)
                    .fadeTo(500, 1);
            }
        });

        $('#updateProfile').off('click').click(function (e) {
            e.preventDefault();
            let currentPassword = $('#currentPassword').val();
            let newPassword = $('#newPassword').val();
            let repeatedNewPassword = $('#repeatedNewPassword').val();

            if ((validatePasswordLength(currentPassword)
                && validatePasswordLength(newPassword)
                && validatePasswordMatching(newPassword, repeatedNewPassword)) || avatarBase64Input != null) {

                postData(ACTION_URL, {
                    "action": "updateAccount",
                    "currentPassword": currentPassword === "" ? null : currentPassword,
                    "newPassword": newPassword === "" ? null : newPassword,
                    "repeatedNewPassword": repeatedNewPassword === "" ? null : repeatedNewPassword,
                    "newAvatar": avatarBase64Input
                }).then((response) => {
                        if (response.ok) {
                            return response.json();
                        }
                        return Promise.reject(response);
                    }
                ).then(function (data) {
                    if (data.status === 'ok') {
                        window.location.href = '/';
                    } else if (data.status === 'deny' || data.status === "exception") {
                        $('#accountUpdateError').show(200).delay(3000).hide(200);
                    }
                }).catch((error) => console.log('Something went wrong.', error));

            } else {
                if (!$('#currentPassword').val()) {
                    $('#currentPassword').addClass('is-invalid');
                }
                if (!$('#newPassword').val()) {
                    $('#newPassword').addClass('is-invalid');
                }
                if (!$('#repeatedNewPassword').val()) {
                    $('#repeatedNewPassword').addClass('is-invalid');
                }
            }
        });
    }

    if ($('.timezone-select').length > 0) {
        $('.timezone-select .dropdown-menu li a').off('click').click(function (e) {
            e.preventDefault();

            $('.timezone-select .dropdown-menu li a').removeClass('active');
            $(this).addClass('active');
            let selectedItem = $(this).html();

            let textAfterIcon = $('.timezone-select .dropdown-toggle i').get(0).nextSibling;
            if (textAfterIcon != undefined) {
                textAfterIcon.remove();
            }
            $('.timezone-select .dropdown-toggle i').after(selectedItem);
        });
    }

    if ($('.lang-select').length > 0) {
        let langCookie = getCookie('lang');
        if (lang.includes(langCookie)) {
            $(`.lang-select .dropdown-menu li a[data-id=${langCookie}]`).addClass('active');
        } else {
            $(`.lang-select .dropdown-menu li a[data-id=${DEFAULT_LANG}]`).addClass('active');
        }
        $('.lang-select .dropdown-toggle').html(
            $('.lang-select .dropdown-menu li a.active').html()
        );

        $('.lang-select .dropdown-menu li a').off('click').click(function (e) {
            e.preventDefault();

            $('.lang-select .dropdown-menu li a').removeClass('active');
            $(this).addClass('active');
            let selectedItem = $(this).html();
            let selectedItemLang = $(this).data("id");

            $('.lang-select .dropdown-toggle').html(selectedItem);
            setCookie('lang', selectedItemLang, 365);
            reloadPage();
        });
    }

    if ($('.smart-scroll').length > 0) {
        var navbarHeight = $('.navbar').outerHeight();
        $('body').css('padding-top', navbarHeight + 'px')
        var last_scroll_top = 0;
        $(window).on('scroll', function () {
            let scroll_top = $(this).scrollTop();
            if (scroll_top == 0) {
                $('.smart-scroll').removeClass('scrolled-down').addClass('scrolled-up');
            } else if (scroll_top > navbarHeight) {
                if (scroll_top < last_scroll_top) {
                    $('.smart-scroll').removeClass('scrolled-down').addClass('scrolled-up');
                } else {
                    $('.smart-scroll').removeClass('scrolled-up').addClass('scrolled-down');
                }
            }
            last_scroll_top = scroll_top;
        });
    }

    $('.modal').on('hidden.bs.modal', function (e) {
        $(this)
            .find("input[type!=radio][type!=checkbox], textarea, select")
            .val('')
            .end()
            .find("input[type=checkbox], input[type=radio]")
            .prop('checked', '')
            .end()
            .find('.selectpicker')
            .selectpicker('refresh')
            .end()
            .find('*')
            .removeClass('is-invalid')
            .removeClass('is-valid')
            .end()
    });
});
