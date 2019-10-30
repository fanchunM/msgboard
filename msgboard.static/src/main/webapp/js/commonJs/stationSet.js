function SelFromStation(obj,e) {
    var ths = obj;
    var dal = '<div class="_station"><span title="关闭" id="cColse" >×</span><ul id="_line" class="_station0"><li class="stationSel">线别</li><li>站点</li></ul><div id="_station0" class="_station1"></div><div style="display:none" id="_station1" class="_station1"></div></div>';
    Iput.show({ id: ths, event: e, content: dal,width:"470"});
    $("#cColse").click(function () {
        Iput.colse();
    });
    var tb_province = [];
    var b = province;
    for (var i = 0, len = b.length; i < len; i++) {
        tb_province.push('<a data-level="0" data-id="' + b[i]['id'] + '" data-name="' + b[i]['name']+ '" style="background-color: ' + b[i]['color']+ '" onmouseout="this.style.cssText=\'background-color:' + b[i]['color']+ '\'" onmouseover="this.style.cssText=\'background-color:' + b[i]['deepercolor'] + '\'">' + b[i]['name'] + '</a>');
    }
    $("#_station0").append(tb_province.join(""));
    $("#_station0 a").click(function () {
        var g = getStation($(this));
        $("#_station1 a").remove();
        $("#_station1").append(g);
        $("._station1").hide();
        $("._station1:eq(1)").show();
        $("#_station0 a,#_station1 a").removeClass("AreaS");
        $(this).addClass("AreaS");
        var lev = $(this).data("name");
        ths.value = $(this).data("name");
        if (document.getElementById("fromLine") == null) {
            var fromLines = $('<input>', {
                type: 'hidden',
                name: "fromLine",
                "data-id": $(this).data("id"),
                id: "fromLine",
                val: lev
				
            });
            $(ths).after(fromLines);
        }
        else {
            $("#fromLine").val(lev);
            $("#fromLine").attr("data-id", $(this).data("id"));
        }
        $("#_station1 a").click(function () {
            $("#_station1 a").removeClass("AreaS");
            $(this).addClass("AreaS");
            var lev =  $(this).data("id");
            if (document.getElementById("fromStation") == null) {
                var fromLines = $('<input>', {
                    type: 'hidden',
                    name: "fromStation",
                    "data-id": $(this).data("id"),
                    id: "fromStation",
                    val: lev
                });
                $(ths).after(fromLines);
            }
            else {
                $("#fromStation").attr("data-id", $(this).data("id"));
                $("#fromStation").val(lev);
            }
            var bc = $("#fromLine").val();
            ths.value = bc+ "-" + $(this).data("name");

          
            $("._station1").hide();
            $("._station1:eq(2)").show();

			Iput.colse();
            

        });
    });
    $("#_line li").click(function () {
        $("#_line li").removeClass("stationSel");
        $(this).addClass("stationSel");
        var s = $("#_line li").index(this);
        $("._station1").hide();
        $("._station1:eq(" + s + ")").show();
    });
}

function SelToStation(obj,e) {
    var ths = obj;
    var dal = '<div class="_station"><span title="关闭" id="cColse" >×</span><ul id="_line" class="_station0"><li class="stationSel">线别</li><li>站点</li></ul><div id="_station0" class="_station1"></div><div style="display:none" id="_station1" class="_station1"></div></div>';
    Iput.show({ id: ths, event: e, content: dal,width:"470"});
    $("#cColse").click(function () {
        Iput.colse();
    });
    var tb_province = [];
    var b = province;
    for (var i = 0, len = b.length; i < len; i++) {
        tb_province.push('<a data-level="0" data-id="' + b[i]['id'] + '" data-name="' + b[i]['name'] + '" style="background-color: ' + b[i]['color']+ '" onmouseout="this.style.cssText=\'background-color:' + b[i]['color']+ '\'" onmouseover="this.style.cssText=\'background-color:' + b[i]['deepercolor'] +'\'">' + b[i]['name'] + '</a>');
    }
    $("#_station0").append(tb_province.join(""));
    $("#_station0 a").click(function () {
        var g = getStation($(this));
        $("#_station1 a").remove();
        $("#_station1").append(g);
        $("._station1").hide();
        $("._station1:eq(1)").show();
        $("#_station0 a,#_station1 a").removeClass("AreaS");
        $(this).addClass("AreaS");
        var lev = $(this).data("name");
        ths.value = $(this).data("name");
        if (document.getElementById("toLine") == null) {
            var toLines = $('<input>', {
                type: 'hidden',
                name: "toLine",
                "data-id": $(this).data("id"),
                id: "toLine",
                val: lev
            });
            $(ths).after(toLines);
        }
        else {
            $("#toLine").val(lev);
            $("#toLine").attr("data-id", $(this).data("id"));
        }
        $("#_station1 a").click(function () {
            $("#_station1 a").removeClass("AreaS");
            $(this).addClass("AreaS");
            var lev =  $(this).data("id");
            if (document.getElementById("toStation") == null) {
                var toLines = $('<input>', {
                    type: 'hidden',
                    name: "toStation",
                    "data-id": $(this).data("id"),
                    id: "toStation",
                    val: lev
                });
                $(ths).after(toLines);
            }
            else {
                $("#toStation").attr("data-id", $(this).data("id"));
                $("#toStation").val(lev);
            }
            var bc = $("#toLine").val();
            ths.value = bc+ "-" + $(this).data("name");

          
            $("._station1").hide();
            $("._station1:eq(2)").show();

			Iput.colse();
            

        });
    });
    $("#_line li").click(function () {
        $("#_line li").removeClass("stationSel");
        $(this).addClass("stationSel");
        var s = $("#_line li").index(this);
        $("._station1").hide();
        $("._station1:eq(" + s + ")").show();
    });
}

function getStation(obj) {
    var c = obj.data('id');
    var e = province;
    var f;
    var stationColor;
    var stationDeeperColor;
    var g = '';
    for (var i = 0, plen = e.length; i < plen; i++) {
        if (e[i]['id'] == parseInt(c)) {
            f = e[i]['city'];
            stationColor=e[i]['color'];
            stationDeeperColor=e[i]['deepercolor'];
            break
        }
    }
    for (var j = 0, clen = f.length; j < clen; j++) {
        g += '<a data-level="1" data-id="' + f[j]['id'] + '" data-name="' + f[j]['name'] + '" title="' + f[j]['name'] + '" style="background-color: ' +stationColor+ '" onmouseout="this.style.cssText=\'background-color:' + stationColor+ '\'" onmouseover="this.style.cssText=\'background-color:' + stationDeeperColor+ '\'">' + f[j]['name'] + '</a>'
    }
    $("#_line li").removeClass("stationSel");
    $("#_line li:eq(1)").addClass("stationSel");
    return g;
}
