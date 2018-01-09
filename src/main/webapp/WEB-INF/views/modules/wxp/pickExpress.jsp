<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>取货助手 -- 锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<style type="text/css">
		.expPickCont{
			padding-bottom: 20px;
		}
		.expPickCont .expPickIcon{
			width: 15%;
			margin: 0 auto;
			text-align: center;
		}
		.expPickCont .expPicTitle{
			text-align: center;
			font-weight: bold;
		}

		.searchInfoBtn{
			width: 90%;
			margin: 0 auto;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			background: #1f72ff;
			color: #fff;
			font-weight: bolder;
		}

		.expUserInfoCont{
			width: 90%;
			margin: 20px auto 20px; 
			font-size: 14px;
		}
		.expUserInfoCont .expUserInfoDiv{
			width: 100%;
			background: #fff;
			border: 1px solid #f1f1f1;
			border-radius: 8px;
			margin-bottom: 10px;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo{
			width: 96%;
			overflow: hidden;
			padding: 8px 2%;
			border-bottom: 1px dotted #f1f1f1;
			position: relative;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo p{
			margin: 0px 0px 5px;
			padding: 0px;
			line-height: 20px;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo .userName{
			font-weight: bolder;
			background: url(../images/username.png) no-repeat center left;
			background-size: 15px;
			padding-left: 20px;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo .userPhone{
			background: url(../images/userphone.png) no-repeat center left;
			background-size: 15px;
			padding-left: 20px;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo .userAdress{
			background: url(../images/useradress.png) no-repeat center left;
			background-size: 15px;
			padding-left: 20px;
			color: #888888;
		}
		.expUserInfoCont .expUserInfoDiv .expUserInfo .expComp{
			position: absolute;
			top: 10px;
			right: 0px;
			width: 70px;
			padding: 0px 15px;
			line-height: 30px;
			background: #f8931d;
			color: #fff;
			border-top-left-radius: 15px;
			border-bottom-left-radius: 15px;
			box-shadow: 3px 3px 3px #888888;
		}
		.expUserInfoCont .expUserInfoDiv .expUserOrderInfo{
			width: 96%;
			overflow: hidden;
			padding: 0px 2%;
		}
		.expUserInfoCont .expUserInfoDiv .expUserOrderInfo .SendOrderDate{
			margin: 0px;
			padding: 0px;
			float: left;
			font-size: 12px;
			color: #888888;
			background: url(../images/userdate.png) no-repeat center left;
			background-size: 15px;
			padding-left: 20px;
			line-height: 40px;
		}
		.expUserInfoCont .expUserInfoDiv .expUserOrderInfo .SendOrderConfBtn{
			margin: 0px;
			padding: 0px 15px;
			float: right;
			background:#77ac05;
			color: #fff;
			border-radius: 5px;
			line-height: 26px;
			margin-top: 7px;
		}

		.expUserInfoNull{
			width: 100%;
			line-height: 150px;
			text-align: center;
			color: #888888;
			display: none;
		}
		
	</style>
</head>
<body>
<div class="content">
	<div class="headerNav">
		<div class="headerNavTop"><div class="headerNavIcon headerNavIconOut"><span></span><span></span></div></div>
		<div class="headerNavCont">
			<a href="../index.html">快递首页</a>
			<a href="../userhome.html">个人中心</a>
			<a href="../delivery.html">送货上门</a>
			<a href="../sendexpress.html">我要寄件</a>
			<a href="../lazyboard.html">懒人排行</a>
			<a href="../expassistant.html">快递助手</a>
		</div>
	</div>

	<div class="expPickCont">
		<div class="expPickIcon">
			<img src="${ctxStatic}/wx/wximages/pickexpicon.png" width="100%">
		</div>
		<p class="expPicTitle">确认取货</p>

		<div class="expPickInput">
			<form>
				<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
				<input type="hidden" id="openId" name="openId" value="${openId}">
				<div class="userInputCont">
					<div class="inputTypeCont">
						<div class="inputTitle">证件</div>
						<input type="text" id="expPickUserId" class="commonInputFunc" name="username" placeholder="请输入身份证号码...">
						<div class="commonFuncBtnScan"></div>
					</div>
				</div>
			</form>

			<div class="searchInfoBtn">查找信息</div>
		</div>
	</div>

	<div class="expUserInfoCont"> 
		<div class="expUserInfoNull">
			暂无查到相关信息
		</div>
	</div>

</div>
<script type="text/javascript">
	$(function() {
		var pageContextVal = $("#PageContext").val();
		function rzGetExpInfo(name,phone,expComp,address,expNum) {
			var $expUserInfoDiv = $("<div class='expUserInfoDiv'></div>");

			var $expUserInfo = $("<div class='expUserInfo'></div>");
			$expUserInfoDiv.append($expUserInfo);
			var $userName = $("<p class='userName'>"+name+"</p>");
			$expUserInfo.append($userName);
			var $userPhone = $("<p class='userPhone'>"+phone+"</p>");
			$expUserInfo.append($userPhone);
			var $userAddress = $("<p class='userAdress'>"+address+"</p>");
			$expUserInfo.append($userAddress);
			var $expComp = $("<div class='expComp'>"+expComp+"</div>");
			$expUserInfo.append($expComp);

			var $expUserOrderInfo = $("<div class='expUserOrderInfo'></div>")
			$expUserInfoDiv.append($expUserOrderInfo);
			var $SendOrderDate = $("<p class='SendOrderDate'>"+expNum+"</p>");
			$expUserOrderInfo.append($SendOrderDate);
			var $SendOrderConfBtn = $("<p class='SendOrderConfBtn'>确认收货</p>");
			$expUserOrderInfo.append($SendOrderConfBtn);

			$SendOrderConfBtn.bind("click",function(){
				var state = confirm("确认取货？");
				if(!state){
				   return false;
				}
				var expNum = $(this).siblings().first().text();
				var openId = $("#openId").val();
				 $.ajax({
				     type:'POST',
				     url:pageContextVal+'/wx/endExpress',
				     data:{'expNum':expNum,'openId':openId},
				     dataType: "json",
				     success:function(data){
				    	switch(data.code){
					    	case "1" : alert(data.message); break;
							case "0" : 
						     	alert("取货成功");
						     	$expUserInfoDiv.remove();
						     	//$(this).parent().parent().hide();
								break;
				    	}
				     },
				     error:function(){
				     	alert("操作失败");
				     }
				    
				 });
			});

			$(".expUserInfoCont").append($expUserInfoDiv);
		}

		$(".searchInfoBtn").click(function() {
			var expPickUserId = $("#expPickUserId").val();
			var openId = $("#openId").val();
			 $.ajax({
			     type:'POST',
			     url:pageContextVal+'/wx/queryExpress',
			     data:{'idCard':expPickUserId,'openId':openId},
				 dataType: "json",
			     success:function(data){
			    	    var jsontmp3 = data;
						$(".expUserInfoNull").hide();
						switch(jsontmp3.code) {
							case "1" : alert(jsontmp3.message); break;
							case "2" : alert(jsontmp3.message); break;
							case "0" : 
								$(".expUserInfoNull").hide();
								if (jsontmp3.num == 0) {
									$(".expUserInfoNull").show();
									return;
								}
								$(".expUserInfoCont").empty();
								for(var i=0; i < jsontmp3.num; i++){
									//alert(expInfo);
									var expInfo = jsontmp3.expressList[i];
									rzGetExpInfo(expInfo.name,expInfo.phone,expInfo.company,expInfo.address,expInfo.expressId);
								}
								break;
						}
			     },
			     error:function(){
			      
			     }
			 });
		});

	});
</script>
</body>
</html>