function setTabSyn( i )
{
	if(i == 'ttxw'){
		document.getElementById("ttxw").style.display="block";
		document.getElementById("jsxx").style.display="none";
		document.getElementById("yydt").style.display="none";
		document.getElementById("FTttxw").style.color="#ffffff";
		document.getElementById("FTjsxx").style.color="#333333";
		document.getElementById("FTyydt").style.color="#333333";
		document.getElementById("TDttxw").style.backgroundImage  = 'url(/image/16.jpg)';
		document.getElementById("TDjsxx").style.backgroundImage  = 'url()';
		document.getElementById("TDyydt").style.backgroundImage  = 'url()';

		document.getElementById("NEWjsxx").className="topNews";
		document.getElementById("NEWyydt").className="topNews";
		
	}
	else if (i == 'jsxx'){
		document.getElementById("ttxw").style.display="none";
		document.getElementById("jsxx").style.display="block";
		document.getElementById("yydt").style.display="none";
		document.getElementById("FTttxw").style.color="#333333";
		document.getElementById("FTjsxx").style.color="#ffffff";
		document.getElementById("FTyydt").style.color="#333333";
		document.getElementById("TDttxw").style.backgroundImage  = 'url()';
		document.getElementById("TDjsxx").style.backgroundImage  = 'url(/image/16.jpg)';
		document.getElementById("TDyydt").style.backgroundImage  = 'url()';
		document.getElementById("NEWjsxx").className="topNews1";
		document.getElementById("NEWyydt").className="topNews";
		
	}
	else if (i == 'yydt'){
		document.getElementById("ttxw").style.display="none";
		document.getElementById("yydt").style.display="block";
		document.getElementById("jsxx").style.display="none";
		document.getElementById("FTttxw").style.color="#333333";
		document.getElementById("FTjsxx").style.color="#333333";
		document.getElementById("FTyydt").style.color="#ffffff";
		document.getElementById("TDttxw").style.backgroundImage  = 'url()';
		document.getElementById("TDjsxx").style.backgroundImage  = 'url()';
		document.getElementById("TDyydt").style.backgroundImage  = 'url(/image/16.jpg)';
		document.getElementById("NEWjsxx").className="topNews";
		document.getElementById("NEWyydt").className="topNews1";

	}
	else if (i == 'line2Schedule'){
		document.getElementById("line1Schedule").style.display="none";
		document.getElementById("line2Schedule").style.display="block";
		document.getElementById("line4Schedule").style.display="none";
		document.getElementById("line7Schedule").style.display="none";
				
	}
	else if (i == 'line1Schedule'){
		document.getElementById("line2Schedule").style.display="none";
		document.getElementById("line1Schedule").style.display="block";
		document.getElementById("line4Schedule").style.display="none";
		document.getElementById("line7Schedule").style.display="none";
				
	}
	else if (i == 'line4Schedule'){
		document.getElementById("line1Schedule").style.display="none";
		document.getElementById("line4Schedule").style.display="block";
		document.getElementById("line7Schedule").style.display="none";
		document.getElementById("line2Schedule").style.display="none";		
	}
	else if (i == 'line7Schedule'){
		document.getElementById("line2Schedule").style.display="none";
		document.getElementById("line7Schedule").style.display="block";
		document.getElementById("line4Schedule").style.display="none";
		document.getElementById("line1Schedule").style.display="none";
				
	}
	
}

function TopMenuToggle() {
			
		$('#menu-link').toggle();
}


function TopMenuCloseAll() {
	 $('#menu-link').hide(); 
	
}

function QueryResultClose() {

	
	 $('#query-result').hide(); 
	
}

//弹出窗口
function pop(id){


	QueryResultClose();
	
	$('#choose-box-wrapper').css("display","block");
	$('#choose-box-wrapper').css("position","absolute");
	
	if(id == 0){
		
		$(".station").unbind("click");
	
		$('.station').bind('click', function(){
				var item=$(this);
				var id = item.attr('id');		
				$('#fromstation_name').val(item.text());
				$('#fromstationid').val(id);
				
				hide();
			}
		);
	}else{
	
		$(".station").unbind("click");
	
		$('.station').bind('click', function(){
				var item=$(this);
				var id = item.attr('id');		
				$('#tostation_name').val(item.text());
				$('#tostationid').val(id);
				
				hide();
			}
		);
	}
}
//隐藏窗口
function hide()
{
	$('#choose-box-wrapper').css("display","none");
}