$(document).ready(function () {

    setTimezone()
    setLanguage();


    function createCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
            date = null;
        }
        document.cookie = name + "=" + value + expires + "; path=/;domain=." + window.location.hostname;
    }

    function getCookie(name) {
        var matches = document.cookie.match(new RegExp("(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    }

    function setLanguage() {
        if (0) {

        } else {
            var defaultActiveLang = $('.lang-select .dropdown-item.active').html();
            $('.lang-select .dropdown-toggle').html(defaultActiveLang);
        }
    }

    function setTimezone() {
        if (0) {

        } else {

        }
    }

    
    if($('.discipline-filter').length > 0) {
        $('.discipline').off('click').click(function () {
            if($(this).hasClass('active')) {
                $(this).removeClass('active');
                if($('.discipline-filter .discipline.active').length == 0){
                    $('.discipline').first().addClass('active');
                }
            } else {
                $(this).addClass('active');
            }
        });
    }

    if($('.timezone-select').length > 0) {
        $('.timezone-select .dropdown-menu li a').off('click').click(function () {
            $('.timezone-select .dropdown-menu li a').removeClass('active');
            $(this).addClass('active');
            var selectedItem = $(this).html();

            var textAfterIcon = $('.timezone-select .dropdown-toggle i').get(0).nextSibling;
            if (textAfterIcon != undefined) {
                textAfterIcon.remove();
            }
            $('.timezone-select .dropdown-toggle i').after(selectedItem);
        });
    }

    if($('.lang-select').length > 0) {
        $('.lang-select .dropdown-menu li a').off('click').click(function () {
            $('.lang-select .dropdown-menu li a').removeClass('active');
            $(this).addClass('active');
            var selectedItem = $(this).html();

            $('.lang-select .dropdown-toggle').html(selectedItem);
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
