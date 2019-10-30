

$(function(){
    $(".mn-topbar-menu-ul > li > a").click(function(){
	     $(this).addClass("active").parents().siblings().find("a").removeClass("active");
		 $(this).parents().siblings().find(".mn-topbar-menu-ul-secoud").hide(300);
		 $(this).siblings(".mn-topbar-menu-ul-secoud").toggle(300);
		 $(this).parents().siblings().find(".mn-topbar-menu-ul-secoud > li > .mn-topbar-menu-ul-third").hide().parents().siblings().find(".mn-topbar-menu-ul-third-content").hide();
		
	})
	
    $(".mn-topbar-menu-ul-secoud > li > a").click(function(){
        $(this).addClass("sen_x").parents().siblings().find("a").removeClass("sen_x");
        $(this).parents().siblings().find(".mn-topbar-menu-ul-third").hide(300);
	    $(this).siblings(".mn-topbar-menu-ul-third").toggle(300);
	})

    $(".mn-topbar-menu-ul-third > li > a").click(function(){
	     $(this).addClass("xuan").parents().siblings().find("a").removeClass("xuan");
		 $(this).parents().siblings().find(".mn-topbar-menu-ul-third-content").hide();
	     $(this).siblings(".mn-topbar-menu-ul-third-content").toggle();
	})

	$('.mn-topbar-menu').click(function() {
        $(this).siblings(".mn-topbar-menu-list").toggle();
	});
})


 




























