$.fn.megamenu = function (e) {
    function r() {
        $(".megamenu").find("li, a").unbind();
        /*if (window.innerWidth <= 768) {
            o();
            s();
            if (n == 0) {
                $(".megamenu > li:not(.showhide)").hide(0)
            }
        } else {*/
            u();
            i()
        // }
    }

    function i() {
        $(".megamenu li").bind("mouseover", function () {
            //$(this).children(".dropdown, .megapanel").stop().fadeIn(t.interval)
            $(this).children(".dropdown, .megapanel").show();
        }).bind("mouseleave", function () {
            //$(this).children(".dropdown, .megapanel").stop().fadeOut(t.interval)
            $(this).children(".dropdown, .megapanel").hide();
        })
    }

    function s() {
        $(".megamenu > li > a").bind("click", function (e) {
            if ($(this).siblings(".dropdown, .megapanel").css("display") == "none") {
                $(this).siblings(".dropdown, .megapanel").slideDown();
                $(this).siblings(".dropdown").find("ul").slideDown();
                n = 1
            } else {
                $(this).siblings(".dropdown, .megapanel").slideUp()
            }
        })
    }

    function o() {
        $(".megamenu > li.showhide").show(0);
        $(".megamenu > li.showhide").bind("click", function () {
            if ($(".megamenu > li").is(":hidden")) {
                $(".megamenu > li").slideDown(300)
            } else {
                $(".megamenu > li:not(.showhide)").slideUp(300);
                $(".megamenu > li.showhide").show(0)
            }
        })
    }

    function u() {
        $(".megamenu > li").show(0);
        $(".megamenu > li.showhide").hide(0)
    }

    var t = {interval: 250};
    var n = 0;
    /*$(".megamenu").prepend("<li class='showhide'><span class='title'>MENU</span><span class='icon1'></span><span class='icon2'></span></li>");*/
    r();
    $(window).resize(function () {
        r()
    })
}

$(document).ready(function () {
    $(".megamenu").megamenu();
});