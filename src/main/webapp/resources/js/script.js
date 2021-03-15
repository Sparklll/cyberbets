$(document).ready(function () {
    const lang = ['de', 'en', 'fr', 'ru'];
    const defaultLang = 'en';


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
            $(`.lang-select .dropdown-menu li a[data-id=${defaultLang}]`).addClass('active');
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
        });
    }

    if ($('.smart-scroll').length > 0) {
        var navbarHeight = $('.navbar').outerHeight();
        $('body').css('padding-top', navbarHeight + 'px')
        var last_scroll_top = 0;
        $(window).on('scroll', function () {
            scroll_top = $(this).scrollTop();
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
