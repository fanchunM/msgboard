$(function(){
    /*图片轮播*/
    $('#owl-demo').owlCarousel({
        items: 1,
        singleItem: true,
        navigation: true,
        autoPlay: true,
        navigationText: ["上一个","下一个"],
        responsiveRefreshRate:200,
        stopOnHover: true
    });
    /*$('#resource-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });
    $('#wuye-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });*/
    $('#travel-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });
    //业务导航图片轮播
    $('#ywdh-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });
    //现场微信图片轮播
    $('#xcwx-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });

    //资源管理图片轮播
    $('#resourceManage-owl').owlCarousel({
        items: 2,
        singleItem: false,
        autoPlay: true,
        center: true,
        navigation: true
    });

    $("#openWindow").click(function(e){
        var fromId = $("#fromstationid").val();
        var toId = $("#tostationid").val();
        if(typeof(fromId)!="undefined" && typeof(toId)!="undefined" && fromId != toId ){
            window.open("/service/guide/search.html?"+fromId+"="+toId);
        }
    });
});