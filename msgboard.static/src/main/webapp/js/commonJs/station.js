var stationList = [
{
"name": "1号线", "stationList": [
		{ "name": "0140_木渎"},
		{ "name": "0141_金枫路"},
		{ "name": "0142_汾湖路"},		
		{ "name": "0143_玉山路"},		
		{ "name": "0144_苏州乐园"},		
		{ "name": "0145_塔园路"},		
		{ "name": "0146_滨河路"},		
		{ "name": "0147_西环路"},
		{ "name": "0148_桐泾北路"},
		{ "name": "s1|0149_广济南路"},		
		{ "name": "0150_养育巷"},		
		{ "name": "s3|0151_乐桥"},		
		{ "name": "0152_临顿路"},		
		{ "name": "0153_相门"},		
		{ "name": "0154_东环路"},
		{ "name": "0155_中央公园"},
		{ "name": "0156_星海广场"},		
		{ "name": "0157_东方之门"},		
		{ "name": "0158_文化博览中心"},		
		{ "name": "0159_时代广场"},		
		{ "name": "0160_星湖街"},		
		{ "name": "0161_南施街"},
		{ "name": "0162_星塘街"},
		{ "name": "0163_钟南街"}
    ]
},

{
"name": "2号线", "stationList": [

		{ "name": "0238_骑河"},
		{ "name": "0239_富翔路"},
		{ "name": "0240_高铁苏州北站"},		
		{ "name": "0241_大湾"},		
		{ "name": "0242_富元路"},		
		{ "name": "0243_蠡口"},		
		{ "name": "0244_徐图港"},		
		{ "name": "0245_阳澄湖中路"},
		{ "name": "0246_陆慕"},
		{ "name": "0247_平泷路东"},		
		{ "name": "0248_平河路"},		
		{ "name": "s2|0249_苏州火车站"},		
		{ "name": "0250_山塘街"},		
		{ "name": "0251_石路"},		
		{ "name": "s1|0252_广济南路"},
		{ "name": "0253_三香广场"},
		{ "name": "0254_劳动路"},		
		{ "name": "0255_胥江路"},		
		{ "name": "0256_桐泾公园"},		
		{ "name": "0257_友联"},		
		{ "name": "0258_盘蠡路"},		
		{ "name": "0259_新家桥"},
		{ "name": "s4|0260_石湖东路"},
		{ "name": "0261_宝带桥南"},
		{ "name": "0262_尹中路"},
		{ "name": "0263_郭巷"},
		{ "name": "0264_郭苑路"},
		{ "name": "0265_尹山湖"},
		{ "name": "0266_独墅湖南"},
		{ "name": "0267_独墅湖邻里中心"},
		{ "name": "0268_月亮湾"},
		{ "name": "0269_松涛街"},
		{ "name": "0270_金谷路"},
		{ "name": "0271_金尚路"},
		{ "name": "0272_桑田岛"}
	]
},

{
"name": "4号线", "stationList": [

		{ "name": "0430_龙道浜"},
		{ "name": "0431_张庄"},
		{ "name": "0432_姚祥"},		
		{ "name": "0433_活力岛"},		
		{ "name": "0434_孙武纪念园"},		
		{ "name": "0435_平泷路西"},		
		{ "name": "0436_苏锦"},		
		{ "name": "s2|0437_苏州火车站"},
		{ "name": "0438_北寺塔"},
		{ "name": "0439_察院场"},		
		{ "name": "s3|0440_乐桥"},		
		{ "name": "0441_三元坊"},		
		{ "name": "0442_南门"},		
		{ "name": "0443_人民桥南"},		
		{ "name": "0444_团结桥"},
		{ "name": "0445_宝带路"},
		{ "name": "s4|0446_石湖东路"},		
		{ "name": "s5|0447_红庄"},		
		{ "name": "0448_淸树湾"},		
		{ "name": "0449_花港"},		
		{ "name": "0450_江陵西路"},		
		{ "name": "0451_江兴西路"},
		{ "name": "0452_流虹路"},
		{ "name": "0453_笠泽路"},
		{ "name": "0454_顾家荡"},
		{ "name": "0455_苏州湾东"},
		{ "name": "0456_松陵大道"},
		{ "name": "0457_吴江人民广场"},
		{ "name": "0458_吴江汽车站"},
		{ "name": "0459_庞金路"},
		{ "name": "0460_同里"}
	]
},

{
"name": "4号线支线", "stationList": [

		{ "name": "s5|0729_红庄"},		
		{ "name": "0730_蠡墅"},		
		{ "name": "0731_石湖莫舍"},		
		{ "name": "0732_越溪"},		
		{ "name": "0733_文溪路"},
		{ "name": "0734_天鹅荡路"},
		{ "name": "0735_苏州湾北"},
		{ "name": "0736_木里"}
	]
}

];
var FromLines = document.createElement("select");
var FromStation = document.createElement("select");

FromLines.setAttribute("id","FromLine");
FromStation.setAttribute("id","FromStation");

var blankSpan = document.createElement("span");
blankSpan.innerHTML ="&nbsp;";
var divFrom = document.getElementById("divFrom");
divFrom.appendChild(FromLines);
divFrom.appendChild(blankSpan);
divFrom.appendChild(FromStation);



FromLines.options[0] = new Option("请选择线路");
FromStation.options[0] = new Option("请选择站点");


for (var i = 0; i < stationList.length; i++) {
	FromLines.options[FromLines.length] = new Option(stationList[i].name);
	
	FromLines.onchange = function(){
		FromStation.options.length = 0;
		FromStation.options[FromStation.length] = new Option("请选择站点");
		for (var j = 0; j < stationList[FromLines.selectedIndex-1].stationList.length; j++) {
			FromStation.options[FromStation.length] = new Option(stationList[FromLines.selectedIndex-1].stationList[j].name.split("_")[1])
		}
		
	}
	
};

FromStation.onchange = function(){

	var fromid = stationList[FromLines.selectedIndex-1].stationList[FromStation.selectedIndex-1].name.split("_")[0];
	var fromname = stationList[FromLines.selectedIndex-1].stationList[FromStation.selectedIndex-1].name.split("_")[1];
	$("#fromstationid").val(fromid); 
	$("#fromstationname").val(fromname); 
	
}


var ToLines = document.createElement("select");
ToLines.setAttribute("id","ToLine");

var ToStation = document.createElement("select");
ToStation.setAttribute("id","ToStation");

var blankSpan = document.createElement("span");
blankSpan.innerHTML ="&nbsp;";

var divTo = document.getElementById("divTo");
divTo.appendChild(ToLines);
divTo.appendChild(blankSpan);
divTo.appendChild(ToStation);

ToLines.options[0] = new Option("请选择线路");
ToStation.options[0] = new Option("请选择站点");


for (var i = 0; i < stationList.length; i++) {
	ToLines.options[ToLines.length] = new Option(stationList[i].name);
	
	ToLines.onchange = function(){
		ToStation.options.length = 0;
		ToStation.options[ToStation.length] = new Option("请选择站点");
		for (var j = 0; j < stationList[ToLines.selectedIndex-1].stationList.length; j++) {
			ToStation.options[ToStation.length] = new Option(stationList[ToLines.selectedIndex-1].stationList[j].name.split("_")[1])
		}
		
	}
	
};

ToStation.onchange = function(){
	
	var toid = stationList[ToLines.selectedIndex-1].stationList[ToStation.selectedIndex-1].name.split("_")[0];
	var toname = stationList[ToLines.selectedIndex-1].stationList[ToStation.selectedIndex-1].name.split("_")[1];
	$("#tostationid").val(toid); 
	$("#tostationname").val(toname);
	
}

function showBlock(i){
	
	var div;
	
	for (var j = 0; j < i; j++) {
		
		div = document.getElementById("block"+j);		
		if (div.style.display == "none"){
		   div.style.display = "block";
		   document.getElementById("dblock").innerHTML= "关闭详细路径";
		   
		}else{
		   div.style.display = "none";
		   document.getElementById("dblock"+name).innerHTML= "查看详细路径";
	   }
		
   
   }
	
		
}

function showBlocks(i){
	
	var div;
	
	for (var j = 0; j < i; j++) {
		
		div = document.getElementById("blocks"+j);		
		if (div.style.display == "none"){
		   div.style.display = "block";
		   document.getElementById("dblocks").innerHTML= "关闭详细路径";
		   
		}else{
		   div.style.display = "none";
		   document.getElementById("dblocks"+name).innerHTML= "查看详细路径";
	   }
		
   
   }
	
		
}


function queryStationIndex(line,Name){
	
	line = queryLineIndex(line);
	
	for (var j = 0; j < stationList[line].stationList.length; j++) {
		var rIndex = stationList[line].stationList[j].name.indexOf(Name);
		if ( rIndex > 0)
			return j;
	}
	return -1;
}

function queryStationName(id){
	
	for (var i = 0; i < stationList.length; i++) {
		
		
		for (var j = 0; j < stationList[i].stationList.length; j++) {
			var rIndex = stationList[i].stationList[j].name.indexOf(id);
			
			if ( rIndex >= 0)
				return stationList[i].stationList[j].name.split("_")[1];
			
		}
			
		
	}
	
	return -1;
}

function queryLineIndex(line){
		
	if(line == "1号线")
		line = 0;
	else if(line == "2号线")
		line = 1;
	else if(line == "4号线主线")
		line = 2;
	else if(line == "4号线支线")
		line = 3;
	else
		line = -1;
	
	return line;
}

function queryLineName(line){	
	
	if(line.indexOf("|")>0)	
		line = line.split("|")[1].substr(1,1);
	else
		line = line.substr(1,1);
	
	if(line == "1")
		line = "1号线";
	else if(line == "2")
		line = "2号线";
	else if(line == "4")
		line = "4号线";
	else if(line == "7")
		line = "4号线支线";
	else
		line = -1;
	
	return line;
}

function queryStation() {
	$("#wayChoice").css("display", "none");
	$("#resultInfo").css("display", "none");

	var ss = $("#fromstationid").val();
	var es = $("#tostationid").val();

	// if(ss != "" && es != "" && ss != es ){

	var ssName = $("#fromstationname").val();
	var esName = $("#tostationname").val();

	$("#fromStation").html(ssName);
	$("#toStation").html(esName);

	$("#DivQueryResult").empty();
	$("#DivQueryResult02").empty();

	$.ajax({
			type : "POST",
			url : '/admin/getStationInfo.do?ss=' + ss + '&es=' + es,
			success : function(msg) {
				var a = JSON.parse(msg);
				// var a =
				// JSON.parse('[{"time":"52分钟","road":"21488","paths":[{"sName":"中央公园","sPath":"木渎","sStart":"06:12","sEnd":"22:52","sId":"0155","sLine":"1号线"},{"sName":"乐桥","sPath":"同里","sStart":"06:00","sEnd":"22:46","sId":"0440","sLine":"4号线主线"},{"sName":"红庄","sPath":"木里","sStart":"06:10","sEnd":"22:35","sId":"0729","sLine":"4号线支线"},{"sName":"木里","sPath":null,"sStart":null,"sEnd":null,"sId":"0736","sLine":null}],"snum":"18","ticket":"5"},{"time":"52分钟","road":"21488","paths":[{"sName":"中央公园","sPath":"木渎","sStart":"06:12","sEnd":"22:52","sId":"0155","sLine":"1号线"},{"sName":"乐桥","sPath":"同里","sStart":"06:00","sEnd":"22:46","sId":"0440","sLine":"4号线主线"},{"sName":"红庄","sPath":"木里","sStart":"06:10","sEnd":"22:35","sId":"0729","sLine":"4号线支线"},{"sName":"木里","sPath":null,"sStart":null,"sEnd":null,"sId":"0736","sLine":null}],"snum":"18","ticket":"5"}]');
				if (JSON.stringify(a[0]) == JSON.stringify(a[1])) {
					$("#money").html(a[0].ticket);
					$("#TotalDistance").html(a[0].road / 1000);
					$("#snum").html(a[0].snum);
					$("#time").html(a[0].time);
					$("#trans").html(a[0].paths.length - 2);

					var nowItem = '';
					var paths = a[0].paths;

					for ( var i = 0; i < paths.length - 1; i++) {
						nowItem += '<li class="cls line'
								+ paths[i].sId.substr(1, 1) + '">';

						if (i == 0) {
							nowItem += '<p class="date">起点</p>';

						} else {
							nowItem += '<p class="date">换乘</p>';
						}

						nowItem += '<p class="intro">' + paths[i].sName
								+ '</p>';
						nowItem += '<p class="version">&nbsp;</p>';
						nowItem += '<div class="more">';
						nowItem += '<p style="padding-top:10px;">乘坐 <img src=/images/line'
								+ paths[i].sId.substr(1, 1)
								+ '.png> 往 '
								+ paths[i].sPath + ' 方向</p>';
						nowItem += '<p style="padding-top:10px;">';
						nowItem += '<img src=/images/a_24.png class=imgmiddle style="margin-right:10px;">'
								+ paths[i].sStart + '';
						nowItem += '<img src=/images/a_26.png class=imgmiddle style="margin-left:20px;margin-right:10px;">'
								+ paths[i].sEnd;
						nowItem += '</p>';

						nowItem += '</div>';
						nowItem += '</li>';

						// alert(queryStationIndex(paths[i].sLine,paths[i].sName));
						var fromStationIndex = queryStationIndex(
								paths[i].sLine, paths[i].sName);
						var toStationIndex = queryStationIndex(
								paths[i].sLine, paths[i + 1].sName);
						var stationIndex = queryLineIndex(paths[i].sLine);
						nowItem += '<div id=block' + i
								+ ' style="display:none">';
						if (fromStationIndex < toStationIndex) {
							for ( var j = fromStationIndex + 1; j < toStationIndex; j++) {
								nowItem += '<li  class="cls block">';
								nowItem += '<p class="date">&nbsp;</p>';
								nowItem += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem += '</li>';

							}
						} else {

							for ( var j = fromStationIndex - 1; j > toStationIndex; j--) {
								nowItem += '<li  class="cls block">';
								nowItem += '<p class="date">&nbsp;</p>';
								nowItem += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem += '</li>';
							}
						}
						nowItem += '</div>';
					}
					// 到达站点信息
					var last = paths[paths.length - 1];
					nowItem += '<li class="cls line'
							+ last.sId.substr(1, 1) + '">';
					nowItem += '<p class="date">终点</p>';
					nowItem += '<p class="intro">' + last.sName + '</p>';
					nowItem += '<p class="date">&nbsp;</p>';
					nowItem += '<p class="more" style="text-align:right"><br><a href=javascript:showBlock('
							+ eval(paths.length - 1)
							+ ');><span id=dblock>查看详细路径</span></a></p>';
					nowItem += '</li>';

					var tBody = $("#DivQueryResult");
					tBody.append(nowItem);
					$("#resultInfo").css("display", "block")
				} else {

					$("#money").html(a[0].ticket);
					$("#TotalDistance").html(a[0].road / 1000);
					$("#snum").html(a[0].snum);
					$("#time").html(a[0].time);
					$("#trans").html(a[0].paths.length - 2);

					$("#money02").html(a[1].ticket);
					$("#TotalDistance02").html(a[1].road / 1000);
					$("#snum02").html(a[1].snum);
					$("#time02").html(a[1].time);
					$("#trans02").html(a[1].paths.length - 2);

					var nowItem = '';
					var nowItem02 = '';
					var paths = a[0].paths;
					var paths02 = a[1].paths;
					for ( var i = 0; i < paths.length - 1; i++) {
						nowItem += '<li class="cls line'
								+ paths[i].sId.substr(1, 1) + '">';
						if (i == 0) {
							nowItem += '<p class="date">起点</p>';

						} else {
							nowItem += '<p class="date">换乘</p>';
						}

						nowItem += '<p class="intro">' + paths[i].sName
								+ '</p>';
						nowItem += '<p class="version">&nbsp;</p>';
						nowItem += '<div class="more">';
						nowItem += '<p style="padding-top:10px;">乘坐 <img src=/images/line'
								+ paths[i].sId.substr(1, 1)
								+ '.png> 往 '
								+ paths[i].sPath + ' 方向</p>';
						nowItem += '<p style="padding-top:10px;">';
						nowItem += '<img src=/images/a_24.png class=imgmiddle style="margin-right:10px;">'
								+ paths[i].sStart + '';
						nowItem += '<img src=/images/a_26.png class=imgmiddle style="margin-left:20px;margin-right:10px;">'
								+ paths[i].sEnd;
						nowItem += '</p>';

						nowItem += '</div>';
						nowItem += '</li>';

						// alert(queryStationIndex(paths[i].sLine,paths[i].sName));
						var fromStationIndex = queryStationIndex(
								paths[i].sLine, paths[i].sName);
						var toStationIndex = queryStationIndex(
								paths[i].sLine, paths[i + 1].sName);
						var stationIndex = queryLineIndex(paths[i].sLine);
						nowItem += '<div id=block' + i
								+ ' style="display:none">';
						if (fromStationIndex < toStationIndex) {
							for ( var j = fromStationIndex + 1; j < toStationIndex; j++) {
								nowItem += '<li  class="cls block">';
								nowItem += '<p class="date">&nbsp;</p>';
								nowItem += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem += '</li>';

							}
						} else {

							for ( var j = fromStationIndex - 1; j > toStationIndex; j--) {
								nowItem += '<li  class="cls block">';
								nowItem += '<p class="date">&nbsp;</p>';
								nowItem += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem += '</li>';
							}
						}
						nowItem += '</div>';
					}

					// 到达站点信息
					var last = paths[paths.length - 1];
					nowItem += '<li class="cls line'
							+ last.sId.substr(1, 1) + '">';
					nowItem += '<p class="date">终点</p>';
					nowItem += '<p class="intro">' + last.sName + '</p>';
					nowItem += '<p class="date">&nbsp;</p>';
					nowItem += '<p class="more" style="text-align:right"><br><a href=javascript:showBlock('
							+ eval(paths.length - 1)
							+ ');><span id=dblock>查看详细路径</span></a></p>';
					nowItem += '</li>';
					
					var tBody = $("#DivQueryResult");
					tBody.append(nowItem);

					// 第二种方案
					for ( var i = 0; i < paths02.length - 1; i++) {
						nowItem02 += '<li class="cls line'
								+ paths02[i].sId.substr(1, 1) + '">';

						if (i == 0) {
							nowItem02 += '<p class="date">起点</p>';

						} else {
							nowItem02 += '<p class="date">换乘</p>';
						}

						nowItem02 += '<p class="intro">' + paths02[i].sName
								+ '</p>';
						nowItem02 += '<p class="version">&nbsp;</p>';
						nowItem02 += '<div class="more">';
						nowItem02 += '<p style="padding-top:10px;">乘坐 <img src=/images/line'
								+ paths02[i].sId.substr(1, 1)
								+ '.png> 往 '
								+ paths02[i].sPath + ' 方向</p>';
						nowItem02 += '<p style="padding-top:10px;">';
						nowItem02 += '<img src=/images/a_24.png class=imgmiddle style="margin-right:10px;">'
								+ paths02[i].sStart + '';
						nowItem02 += '<img src=/images/a_26.png class=imgmiddle style="margin-left:20px;margin-right:10px;">'
								+ paths02[i].sEnd;
						nowItem02 += '</p>';

						nowItem02 += '</div>';
						nowItem02 += '</li>';

						// alert(queryStationIndex(paths02[i].sLine,paths02[i].sName));
						var fromStationIndex = queryStationIndex(
								paths02[i].sLine, paths02[i].sName);
						var toStationIndex = queryStationIndex(
								paths02[i].sLine, paths02[i + 1].sName);
						var stationIndex = queryLineIndex(paths02[i].sLine);
						nowItem02 += '<div id=blocks' + i
								+ ' style="display:none">';
						if (fromStationIndex < toStationIndex) {
							for ( var j = fromStationIndex + 1; j < toStationIndex; j++) {
								nowItem02 += '<li  class="cls block">';
								nowItem02 += '<p class="date">&nbsp;</p>';
								nowItem02 += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem02 += '</li>';

							}
						} else {

							for ( var j = fromStationIndex - 1; j > toStationIndex; j--) {
								nowItem02 += '<li  class="cls block">';
								nowItem02 += '<p class="date">&nbsp;</p>';
								nowItem02 += '<p class="intro">'
										+ stationList[stationIndex].stationList[j].name
												.split("_")[1] + '</p>';
								nowItem02 += '</li>';

							}
						}
						nowItem02 += '</div>';
					}
					//到达站点信息		
					var last = paths02[paths02.length - 1];
					nowItem02 += '<li class="cls line'
							+ last.sId.substr(1, 1) + '">';
					nowItem02 += '<p class="date">终点</p>';
					nowItem02 += '<p class="intro">' + last.sName + '</p>';
					nowItem02 += '<p class="date">&nbsp;</p>';
					nowItem02 += '<p class="more" style="text-align:right"><br><a href=javascript:showBlocks('
							+ eval(paths02.length - 1)
							+ ');><span id=dblocks>查看详细路径</span></a></p>';
					nowItem02 += '</li>';
						
					var tBody = $("#DivQueryResult02");
					tBody.append(nowItem02);
					$("#wayChoice").css("display", "block");
					$("#resultInfo").css("display", "block")
				}
			}
		});
}