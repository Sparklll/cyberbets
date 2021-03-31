$(document).ready(function () {
    const lang = ['de', 'en', 'fr', 'ru'];
    const DEFAULT_LANG = 'en';
    const ACTION_URL = "/action/"

    var isTeamEditing = false;
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

    function validateTeamRating(teamRating) {
        const ratingFormat = /^(([1-9][0-9]{0,5})|([0]))$/;
        return !isNaN(teamRating)
            && ratingFormat.test(teamRating);
    }

    function validateTeamName(teamName) {
        return teamName.length > 0 && teamName.length < 30
    }

    if ($('#leagueModal').length > 0) {

    }

    if ($('#teamsGrid').length > 0) {
        var disciplines = [
            {Name: "", Id: "0", Logo: ""},
            {Name: "CS:GO", Id: "1", Logo: "/resources/assets/disciplines/csgo_icon.png"},
            {Name: "DOTA 2", Id: "2", Logo: "/resources/assets/disciplines/dota2_icon.png"},
            {Name: "LEAGUE OF LEGENDS", Id: "3", Logo: "/resources/assets/disciplines/lol_icon.png"},
            {Name: "VALORANT", Id: "4", Logo: "/resources/assets/disciplines/valorant_icon.jpg"}
        ];

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
                                    <img src="${item.teamLogo.path + queryString}" width="60">
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
                        let disciplineName = disciplines.find(d => d.Id === value).Name;
                        let disciplineLogo = disciplines.find(d => d.Id === value).Logo;
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
                    return $.ajax({
                        type: "POST",
                        url: ACTION_URL,
                        data: JSON.stringify(Object.assign({}, {"action": "deleteTeam"}, item))
                    }).done(function (response) {
                        notify('success', 'Success', 'Team was successfully deleted.');
                    }).fail(function () {
                        notify('error', 'Error', 'There was an error deleting the team!');
                    });
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
                let teamId = team.id;

                $('#teamModal').data('id', teamId);
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

            if (validateTeamName(teamName)
                && validateTeamRating(teamRating)
                && disciplineValue > 0
                && (isImageSelected || isTeamEditing)) {

                let team = {
                    "teamName": teamName,
                    "teamRating": parseFloat(teamRating),
                    "discipline": disciplineValue,
                }

                if (isImageSelected) {
                    team.teamLogo = {
                        "path": teamLogoBase64
                    };
                } else {
                    team.teamLogo = {
                        "path": ""
                    };
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
            .find('*')
            .removeClass('is-invalid')
            .removeClass('is-valid')
    })
});
