   var width;// 裁剪框的宽度
    var height;// 裁剪框的高度
    var x;// 相对于裁剪图片x左边
    var y;// 相对于裁剪图片y左边
// 创建变量(在这个生命周期)的API和图像大小
			var jcrop_api,
				boundx,// 原图的宽
				boundy,// 原图的高
				originalImage_realSrc,// 原图路径 IE7-IE9使用
				$preview2,
				$pcnt2,
				$pimg2,
				xsize2,// 预览窗口的宽
				ysize2,// 预览窗口的高
				scaleFactor;// 缩放比例
// 选择头像
$(function() {
	$("#headPic").on("change", function() {
		var objUrl = getObjectURL(this.files[0]);// 获取文件信息
		console.log("objUrl = " + objUrl);
		if (objUrl) {
			$("#headResult").attr("src",objUrl);
			originalImage_realSrc=objUrl;
			$("#previewImage2").attr("src",objUrl);
			// 图像裁剪
			// 获取预览窗格相关信息
			$preview2 = $('#preview-pane2'),
			$pimg2 = $('#previewImage2'),
			$pcnt2 = $('#previewImage_box2'),
			xsize2 = $pcnt2.width();
			ysize2 = $pcnt2.height();
			$('#headResult').Jcrop({
				onChange : updatePreview,// 选框改变时的事件
				// onSelect: updatePreview,//创建选框，参数格式为：[x,y,x2,y2]
				setSelect : [ 200, 200, 700, 700 ],// 创建默认选框，参数格式为：[x,y,x2,y2]
				aspectRatio : 1,// 选框宽高比。说明：width/height
				boxWidth : 400,// 画布宽度
				boxHeight : 400,// 画布高度
				minSize : [ 200, 200 ],// 选框最小尺寸 格式： [0,0]
				bgColor : "black",// 背景颜色 默认"black" 透明#00000000
				allowSelect : false,// 允许新选框
				allowResize : true// 允许选框缩放
			}, function() {
				// 使用API来获得真实的图像大小
				var bounds = this.getBounds();// 获取图片实际尺寸，格式为：[w,h]
				boundx = bounds[0];
				boundy = bounds[1];
				// 初始化预览图
				updatePreview(this.tellSelect());// tellSelect()获取选框的值（实际尺寸）。例子：console.log(jcrop_api.tellSelect())
				scaleFactor = this.getScaleFactor();// 获取图片缩放的比例，格式为：[w,h]
				// jcrop_api变量中存储API
				jcrop_api = this;
				// 预览进入jcrop容器css定位
				$preview2.appendTo(jcrop_api.ui.holder);
			});
		}
	});
})

/**
 * 更新预览
 * 
 * @param select
 *            选区 选框的值（实际尺寸）
 */
    function updatePreview(select){
		var browserVersion = window.navigator.userAgent.toUpperCase();
		if (browserVersion.indexOf("MSIE") > -1 && browserVersion.indexOf("MSIE 6") <= -1) {// IE7-IE9
			thumbnailPreview(select);
		}else{
			// 设置预览
			if (parseInt(select.w) > 0) {
				var rx2 = xsize2 / select.w;// select.w 代表选区的宽
				var ry2 = ysize2 / select.h;// select.h 代表选区的高
				$pimg2.css({
					width: Math.round(rx2 * boundx)/9.4 + 'px',
					height: Math.round(ry2 * boundy)/1.2+ 'px', 
					marginLeft: '-' + Math.round(rx2 * select.x)/9.8+ 'px',
					marginTop: '-' + Math.round(ry2 * select.y)/1.7+ 'px'
				});
			}
		}
		// 赋值
        x = select.x;  
		y = select.y;  
        width = select.w;  
        height = select.h;  
	};

	/**
	 * ie7+缩略图预览
	 * @param select 选区
	 * @param bounds 实际内容
	 */
	function thumbnailPreview(select){
	
		// 设置预览
		if (parseInt(select.w) > 0) {
			var rx2 = xsize2 / select.w;// select.w 代表选区的宽
			var ry2 = ysize2 / select.h;// select.h 代表选区的高
			
			$("#image_crop_preview_div2").css(
				"filter",
				"progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + originalImage_realSrc + "')"
			);
			$("#image_crop_preview_div2").css({
				width: Math.round(rx2 * boundx) + 'px',
				height: Math.round(ry2 * boundy) + 'px', 
				marginLeft: '-' + Math.round(rx2 * select.x) + 'px',
				marginTop: '-' + Math.round(ry2 * select.y) + 'px'
			});
		}
	}
	
// 自定义上传头像
function uploadHead(){
	// 获取文件节点验证
	var dom = document.getElementById("headPic").files[0];
	var fileSize = dom.size/(1024*1024);
	if(fileSize>10){
		alert("请上传不大于10M的图片");
		return false;
	}
	var file = $("#headPic").val();
	var point = file.lastIndexOf(".");
	var fileName = file.substr(point);
	if((fileName != ".jpg") && (fileName != ".jpeg") && (fileName != ".png") && (fileName != ".Jpg") && (fileName != ".JPG") && (fileName != ".Jpeg") && (fileName != ".JPEG") && (fileName != ".PNG")){
		alert("请上传正确的图片格式");
		return false;
	}
	// 用base64对头像进行压缩，赋值给《base64Form》，将值传到后台
	lrz(dom,{quality:0.6})
    .then(function (rst) {
    	//将需要的参数赋值给base64Form表
    	$("#base64").val(rst.base64);
    	$("#width").val(""+width);
    	$("#height").val(""+height);
    	$("#x").val(""+x);
    	$("#y").val(""+y);
    	$('#baseHeadForm').form('submit', {
    		url: 'person/uploadHeadImage',
    		success: function(){
    			location.href="person";
         	   	alert("上传成功！");
    		},
    		onLoadSuccess: function(data) {
    			alert(data);
    		},
    		onLoadError: function(){
    			alert("上传失败！");
    		}
    	});
    })
}

function getObjectURL(file) {
	var url = null;
	if (window.createObjectURL != undefined) {
		url = window.createObjectURL(file);
	} else if (window.URL != undefined) { // mozilla(firefox)
		url = window.URL.createObjectURL(file);
	} else if (window.webkitURL != undefined) { // webkit or chrome
		url = window.webkitURL.createObjectURL(file);
	}
	return url;
}

