$(document).ready(function () {
    const lang = ['de', 'en', 'fr', 'ru'];
    const DEFAULT_LANG = 'en';
    const ACTION_URL = "/action/"

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

    if ($('#registerModal').length > 0) {
        $('#registerModal #registerEmail').off('input').on('input', function () {
            let email = $('#registerEmail').val();
            if (email.length > 0) {
                if (validateEmail(email)) {
                    $('#registerEmail').removeClass('is-invalid').addClass('is-valid')
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
                    $('#registerPassword').removeClass('is-invalid').addClass('is-valid')
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
                    $('#registerRepeatedPassword').removeClass('is-invalid').addClass('is-valid')
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
                    if(data.status === 'ok') {
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
                    $('#loginEmail').removeClass('is-invalid').addClass('is-valid')
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
                    $('#loginPassword').removeClass('is-invalid').addClass('is-valid')
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
                    if(data.status === 'ok') {
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

    if($('#logout').length > 0) {
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
                if(data.status === 'ok') {
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
            if (scroll_top > navbarHeight) {
                if (scroll_top < last_scroll_top) {
                    $('.smart-scroll').removeClass('scrolled-down').addClass('scrolled-up');
                } else {
                    $('.smart-scroll').removeClass('scrolled-up').addClass('scrolled-down');
                }
            }
            last_scroll_top = scroll_top;
        });
    }
});
