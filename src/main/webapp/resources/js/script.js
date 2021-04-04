$(document).ready(function () {
    const lang = ['de', 'en', 'fr', 'ru'];
    const DEFAULT_LANG = 'en';
    const ACTION_URL = "/action/"

    var disciplines = [
        {Name: "", Id: "0", Logo: ""},
        {Name: "CS:GO", Id: "1", Logo: "/resources/assets/disciplines/csgo_icon.png"},
        {Name: "DOTA 2", Id: "2", Logo: "/resources/assets/disciplines/dota2_icon.png"},
        {Name: "LEAGUE OF LEGENDS", Id: "3", Logo: "/resources/assets/disciplines/lol_icon.png"},
        {Name: "VALORANT", Id: "4", Logo: "/resources/assets/disciplines/valorant_icon.jpg"}
    ];

    var isEventEditing = false;
    var isTeamEditing = false;
    var isLeagueEditing = false;
    var editingItem = null;

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

    if ($('#eventsGrid').length > 0) {
        let eventFormat = [
            {Name: "", Id: "0"},
            {Name: "BO1", Id: "1"},
            {Name: "BO2", Id: "2"},
            {Name: "BO3", Id: "3"},
            {Name: "BO5", Id: "4"}
        ];

        let eventStatus = [
            {Name: "Pending", Id: "1", Logo: "/resources/assets/status/pending.png"},
            {Name: "Finished", Id: "2", Logo: "/resources/assets/status/finished.png"},
            {Name: "Canceled", Id: "3", Logo: "/resources/assets/status/canceled.png"},
        ];

        $('#eventsGrid').jsGrid({
            fields: [
                {name: "id", title: "Id", type: "number", width: 50, align: "center"},
                {
                    title: "Event",
                    type: "text",
                    width: 100,
                    align: "center",
                    itemTemplate: function (value, item) {
                        return `<div class="d-flex justify-content-center align-items-center">
                                    <div class="d-flex flex-column justify-content-center align-items-center me-auto ms-2">
                                        <span class="mb-2 fw-bold">${item.firstTeam.teamName}</span>
                                        <img src="${item.firstTeam.teamLogo.path}" width="50">
                                    </div>
                                   
                                   <i class="fw-bold">VS</i> 
                                   
                                   <div class="d-flex flex-column justify-content-center align-items-center ms-auto me-2">
                                        <span class="mb-2 fw-bold">${item.secondTeam.teamName}</span>
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
                    title: "League",
                    type: "text",
                    width: 100,
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
                    itemTemplate: function (value, item) {
                        return `<span class="mb-2">${dayjs.unix(value).format('ddd, DD MMM YYYY HH:mm')}</span>`;
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
                    deleteButton: false,
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
                            item.id = response.id;
                            d.resolve(item);
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
                            d.resolve(item);
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
                    return $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "deleteEvent"}, item))
                    }).done(function (response) {
                        notify('success', 'Success', 'Event was successfully deleted.');
                        d.resolve(item);
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error deleting the Event!');
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

                isEventEditing = true;
                editingItem = args.item;

                let timestamp = new Date().getTime();
                let queryString = "?t=" + timestamp;

                $('#eventModal').modal('show');
                $('#eventModal .card-header h5').text('Edit event');
                $('#eventModal #eventModalSubmit').text('Update');

                let event = args.item;
                $('#eventModal').data('id', event.id);
                $('#eventModal #eventDisciplineSelect').val(event.discipline);


                $('#eventModal #eventIconPreview').attr('src', league.leagueIcon.path + queryString).fadeIn(1000);
            },

            pageIndex: 1,
            pageSize: 10,
            pageButtonCount: 10,
        });
    }

    if ($('#eventModal').length > 0) {
        $('#eventModal').off('hidden.bs.modal').on('hidden.bs.modal', function () {
            // TODO: Add i18n
            $('#eventModal .card-header h5').text('Add Event');
            $('#eventModal #eventModalSubmit').text('Save');
            $('#eventModal .event-preview .event-header .date').text('Date');
            $('#eventModal .event-preview .event-header .league-icon').attr('src', '').hide();
            $('#eventModal .event-preview .event-header .league-name').text('League');

            $('#eventModal .event-preview .event-info .team .team-logo img').attr('src', '').hide();
            $('#eventModal .event-preview .event-info .team-left .team-name').text('Team 1');
            $('#eventModal .event-preview .event-info .team-right .team-name').text('Team 2');
            $('#eventModal .event-preview .event-info .team .odds').empty().append('<i>x</i>1');

            $('#eventModal .event-preview .event-info .center .odds-percentage').text('50%');
            $('#eventModal .event-preview .event-info .center .event-format span').empty();
            $('#eventModal .event-preview .event-info .center .event-format .discipline-icon').attr('src', '').hide();

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
                let disciplineLogo = disciplines.find(d => d.Id === disciplineId).Logo;
                $('#eventModal .event-preview .event-info .discipline-icon').hide().attr('src', disciplineLogo).fadeIn(1000);

                postData(ACTION_URL, {
                    action: 'loadLeague',
                    discipline: $('#eventDisciplineSelect').val()
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
                    discipline: $('#eventDisciplineSelect').val()
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
            if ($('#eventFormatSelect').val() > 0) {
                let eventFormatId = $('#eventFormatSelect').val();
                let eventFormatName = eventFormat.find(f => f.Id === eventFormatId).Name;
                $('#eventModal .event-preview .event-info .center .event-format span').text(eventFormatName);
            } else {
                $('#eventModal .event-preview .event-info .center .event-format span').empty();
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

            let disciplineSelectValue = $('#eventDisciplineSelect').val();
            let leagueSelectValue = $('#eventLeagueSelect').val();
            let firstTeamSelectValue = $('#eventFirstTeamSelect').val();
            let secondTeamSelectValue = $('#eventSecondTeamSelect').val();
            let formatSelectValue = $('#eventFormatSelect').val();
            let startDateValue = $('#eventDatetimeStart').val();
            let royalty = $('#eventRoyalty').val();


            if (disciplineSelectValue > 0
                && leagueSelectValue > 0
                && firstTeamSelectValue > 0
                && secondTeamSelectValue > 0
                && firstTeamSelectValue !== secondTeamSelectValue
                && formatSelectValue > 0
                && startDateValue
                && (royalty >= 0 && royalty <= 100)
            ) {
                let startDateTimestamp = dayjs($('#eventDatetimeStart').val()).unix();
                let eventStatusValue = $('#eventStatus input:radio:checked').val();

                let event = {
                    "disciplineId": parseFloat(disciplineSelectValue),
                    "leagueId": parseFloat(leagueSelectValue),
                    "firstTeamId": parseFloat(firstTeamSelectValue),
                    "secondTeamId": parseFloat(secondTeamSelectValue),
                    "formatId": parseFloat(formatSelectValue),
                    "startDate": startDateTimestamp,
                    "royalty": parseFloat(royalty),
                    "status": parseFloat(eventStatusValue)
                }

                if (isEventEditing) {
                    event.id = $('#eventModal').data('id');
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
                $('#leagueModal').data('id', league.id);
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
                    league.id = $('#leagueModal').data('id');
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

                $('#teamModal').data('id', team.id);
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
                    team.id = $('#teamModal').data('id');
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

    if ($('.discipline-filter').length > 0) {
        $('.discipline').off('click').click(function () {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
                if ($('.discipline-filter .discipline.active').length == 0) {
                    $('.discipline').first().addClass('active');
                }
            } else {
                $(this).addClass('active');
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
            .find("input,textarea,select")
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
