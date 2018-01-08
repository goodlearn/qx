<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>实名认证 -- 锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<style type="text/css">
		.content{
			overflow: hidden;
		}
		.userCheckCont{
			width: 100%;
		}
		.userInfoCont{
			overflow: hidden;
			padding: 40px 0px 20px;
			background-image: linear-gradient(to top,#1bb7c3,#f4f4f4);
			box-shadow: 0px 3px 3px #c1c1c1;
		}
		.userInfoCont  .userInfoIcon{
			width: 18%;
			margin: 0px auto 10px;
		}
		.userInfoCont  .nickName{
			font-size: 14px;
			text-align: center;
			font-weight: bold;
			color: #fff;
		}

		.solidCont{
			width: 200%;
			overflow: hidden;
		}
		.checkState{
			width: 50%;
			margin:30px 0px;
			float: left;
		}
		.checkState .stateIcon{
			width: 30px;
			margin: 0 auto;
		}
		.checkState .checkStateTxt{
			text-align: center;
			font-size: 14px;
			padding: 5px 0px;
			color: #333333;
			font-weight: bold;
			margin-bottom: 50px;
		}
		.checkState .checkBtn{
			width: 80%;
			line-height: 40px;
			margin: 5px auto;
			font-weight: bold;
			text-align: center;
			color: #20d6da;
			border: 2px solid #20d6da;
			border-radius: 20px;
		}
		.checkState .checkprompt{
			font-size: 14px;
			text-align: center;
			color: #888888;
			margin: 8px 0px 0px;
		}

		.infoCheckEditCont{
			width: 50%;
			float: left;
			padding-top: 20px;
		}

		.submitBtn,.backBtn{
			width: 90%;
			margin: 0 auto 20px;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			color: #20d6da;
			border: 2px solid #20d6da;
			font-weight: bolder;
		}

		#oldPhone{
			display: none;
		}

	</style>
</head>
<body>
	<div class="content">
	<div class="userCheckCont">
		<div class="userInfoCont">
			<div class="userInfoIcon">
				<img src="${ctxStatic}/wx/wximages/userInfoIcon.png" width="100%">
			</div>
			<div class="nickName">用户认证</div>
		</div>
	</div>
	
	<div class="solidCont">
		<div class="checkState">
			<div class="stateIcon">
				<img src="${ctxStatic}/wx/wximages/uncheckIcon.png" width="100%">
			</div>
			<div class="checkStateTxt">未认证</div>
			
			<!-- 按钮组 -->
			<div class="checkBtn userRegBtn">新用户认证</div>
			<div class="checkprompt">认证通过开启信息化校园快递</div>
		</div>

		<div class="infoCheckEditCont" id="newUserReg">
			<form>
				<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
				<input id="openId" type="hidden" value="${openId}" />
				<div class="userInputCont">
					<div class="inputTypeCont">
						<div class="inputTitle">姓名</div>
						<input type="text" id="name" class="commonInput" name="name" placeholder="请输入你的姓名...">
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">证件</div>
						<input type="text" id="idCard" class="commonInput" name="idCard" placeholder="请输入身份证号码...">
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">手机</div>
						<input type="text" id="phone" class="commonInput" name="phone" placeholder="请输入你的手机号码...">
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">短信</div>
						<input type="text" id="msg" class="verifiInput" name="msg" placeholder="请输入验证码...">
						<input type="button" class="verifiBtn" value="发送验证码">
					</div>
					<div class="inputTypeCont" id="oldPhone">
						<div class="inputTitle">原手机</div>
						<input type="text" id="originPhone" class="commonInput" name="oldPhone" placeholder="请输入你绑定的原手机号码...">
					</div>
				</div>
			</form>

			<div id = "userRegSubmitBtn" class="submitBtn userRegSubmitBtn">确认提交</div>
			<div class="backBtn">点我返回</div>
		</div>
	</div>

</div>

<script type="text/javascript">
	$(function() {
		var pageContextVal = $("#PageContext").val();
		var windowW = $(window).width();
		if (windowW > 600) {
			windowW = 600;
		}
		$(".solidCont").css({"margin-left":0+"px"});

		$(".userRegBtn").click(function(){
			$(".solidCont").animate({"margin-left":-windowW+"px"},"fast");
		});

		$(".backBtn").click(function(){
			$(".solidCont").animate({"margin-left":0+"px"},"fast");
		});

		$(".userRegSubmitBtn").click(function(){
			//$(".msgcover").fadeIn();
			//$(".coverMsgCont").fadeIn();
			var name = $("#name").val();
			var idCard = $("#idCard").val();
			var phone = $("#phone").val();
			var msg = $("#msg").val();
			var openId = $("#openId").val();
			var oldPhone = $("#originPhone").val();
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/wx/savePersonUserInfo',
			    data:{'name':name,'idCard':idCard,'phone':phone,'msg':msg,'openId':openId,'oldPhone':oldPhone},
			    dataType: "json",
			    success:function(data){
			    	var prompt = "操作提示";
			    	var code = data.code;
			    	var message = data.message;
			    	if(code == "0"){
			    		rzAlert(prompt,message);
			    		window.location.href= pageContextVal+"/wx/reqPersonIndex?openId="+openId;
			    	}else if(code == "10"){
						rzAlert(prompt,message);
						$("#oldPhone").show();
			    	}else{
			    		rzAlert(prompt,message);
			    	}
		    	},
			    error:function(){
				      
			    }
			});
		});

		$(".verifiBtn").click(function(){
			var phone = $("#phone").val();
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/wx/sendWxPhoneMsgCode',
			    data:{'phone':phone},
			    dataType: "json",
			    success:function(data){
			    	//var result = JSON.parse(data);
			    	if(data.code == "0"){
			    		alert(data.message);
			    		$(".verifiBtn").attr('disabled', 'disabled');
			    		$(".verifiBtn").css({"background":"#888888"});
						var countNum = 60;
						$(".verifiBtn").val("重发短信("+countNum+")");
			    		var timer = setInterval(function(){
							--countNum;
							if (countNum == 0) {
								$(".verifiBtn").attr('disabled', false);
								$(".verifiBtn").css({"background":"#1f72ff"});
								$(".verifiBtn").val("发送验证码");
								window.clearInterval(timer);
							} else {
								$(".verifiBtn").val("重发短信("+countNum+")");
							}
						},1000);
			    	}else if(data.code == "1"){
			    		alert(data.message);
			    	}else if(data.code == "2"){
			    		alert(data.message);
			    	}
			    },
			    error:function(){
			      
			    }
			});
			
		})
	});
</script>
</body>
</html>