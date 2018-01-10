<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/main.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
</head>
<body>
	<div class="content">
	<div class="top autoH">
		<div class="logo autoH">
			<img src="${ctxStatic}/wx/wximages/logo.png" width="100%">
		</div>
	</div>
	<div class="middle">
		<form id="theForm">
			<div class="forminput">
				<input type="text" name="expressNum" placeholder="请输入快递单号...">
				<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
				<div class="submBtn"></div>
			</div>
		</form>
	</div>
	<div class="funContdiv">
		<div class="funCont">
			<div class="userFun">
				<p class="funIcon userFunColor1">节</p>
				<p class="funTxt">个人中心</p>
			</div>
			<div class="userFun">
				<p class="funIcon userFunColor2">日</p>
				<p class="funTxt">懒人排行</p>
			</div>
			<div class="userFun">
				<p class="funIcon userFunColor3">快</p>
				<p class="funTxt">送货上门</p>
			</div>
			<div class="userFun">
				<p class="funIcon userFunColor4">乐</p>
				<p class="funTxt">我要寄件</p>
			</div>
			<c:if test="${not empty isManager}">
				<div class="userFun">
					<p class="funIcon userFunColor4">啊</p>
					<p class="funTxt">快递助手</p>
				</div>
			</c:if>
		</div>
	</div>

	<div class="footer">
		<p class="copytxt">&copy;信息技术工程学院</p>
	</div>

	<!-- cover -->
	<div id="goodcover"></div>
	
	<div id="code">
			<div class="close1"><a href="javascript:void(0)" id="closebt"><img src="${ctxStatic}/wx/wximages/close.gif"></a></div>
			<div class="goodtxt">
				<div class="bindPhicon">
					<img src="${ctxStatic}/wx/wximages/bindphoneicon.png" width="30%">
					<p class="bindPhTitle">实名认证</p>
				</div>
				<form id="userInfoForm">
					<div class="regUserInfo">
						<div class="userInputDiv">
							<label for="userNameId" class="userNameLa"></label>
							<input type="text" id="userNameId" name="userName" placeholder="请输入真实姓名">
						</div>
						<div class="userInputDiv">
							<label for="userPhoneId" class="userPhoneLa"></label>
							<input type="text" id="userPhoneId" name="userPhone" placeholder="请输入手机号码">
						</div>
						<div class="userInputDiv">
							<label for="userVerifId" class="userVerifIdLa"></label>
							<input type="text" id="userVerifId" name="userPhone" placeholder="请输入身份证号码...">
						</div>
						<div class="userPromptTxt">***请携带身份证到快递服务中心确认审核</div>
						<!-- 
						<div class="userInputDiv">
							<label for="userPhVerifId" class="userPhVerifLa"></label>
							<input type="text" id="userPhVerifId" name="userPhone" placeholder="输入验证码">
							<div class="sendverifbt">获取验证码</div>
						</div> -->
						<p class="submitInfo">确&nbsp;&nbsp;认</p>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(function(){
		var pageContextVal = $("#PageContext").val();
		$(".userFun").click(function(){
			var clickNum = $(this).index();
			switch(clickNum){
				//个人中心
				case(0): 
					$.ajax({
					    type:'GET',
					    url:pageContextVal+'/wx/checkActive',
					    data:{'openId':"person"},
					    dataType: "json",
					    success:function(data){
					    	var prompt = "操作提示";
					    	var code = data.code;
					    	var message = data.message;
					    	if(code == "0"){
					    		window.location.href= pageContextVal+"/wx/userHome"; 
					    	}else if(code == "1"){
					    		window.location.href= pageContextVal+"/wx/reqUserCheckState";
					    	}else{
					    		rzAlert(prompt,message);
					    	}
				    	},
					    error:function(){
						      
					    }
					});
					break;
				case(1): window.location.href= ""; break;
				case(2): window.location.href="./delivery.html"; break;
				case(3): window.location.href="./sendexpress.html"; break;
				case(4): window.location.href= pageContextVal+"/wx/reqExpressAssist"; break;
				//快递分析
				//送货上门
				//我要寄件
				//快递助手
			}
		});

		var initFunc = function(){
			var windowH = $(document).height();
			var windowW = $(document).width();

			$(".content").css({"height": windowH+"px"});

			var contentW = $(".content").width();
			var contentH = $(".content").height();

			var contH = windowH / 3;
			$(".autoH").css({"height": contH+"px"});

			// userFunH  content
			var middleH = $(".middle").height() + 40;
			var userFunH = contentH - contH - middleH - 30 -20; //footer,userfun(margin)
			$(".funContdiv").css({"height":userFunH+"px"});

			var funIconH = $(".funIcon").width(); 
			$(".funIcon").css({"height": funIconH+"px","line-height": funIconH+"px","border-radius": funIconH/2 +"px","font-size":funIconH/2 +"px"});

			// cover
			$("#goodcover").css({"height":contentH+"px"});

			var codeH = $("#code").height(); //contentH*0.7;
			var codeW = contentW*0.7;
			var coverTop = windowH - codeH - 20; //code padding:10
			var coverLeft = windowW - codeW - 20;
			$("#code").css({"width":codeW+"px","left": coverLeft/2+"px","top":coverTop/2+"px"});

			// cover userinfo input
			//var userInDivW = codeH*0.7 ;
			var userInRightW = (codeH*0.7*0.9 - 40)*0.8;
			$("#userNameId").css({"width":userInRightW+"px"});
			$("#userPhoneId").css({"width":userInRightW+"px"});
			$("#userPhVerifId").css({"width":(userInRightW*0.5)+"px"});
		};

		initFunc();

		$(window).resize(function(){
			initFunc();
		});

		// input focus
		$(".middle .forminput input").focus(function(){
			$(".middle .forminput").css({"border":"1px solid rgba(31,114,255,.75)","box-shadow":"0 0 8px rgba(31,114,255,.5)"});
		});

		// input blur
		$(".middle .forminput input").blur(function(){
			$(".middle .forminput").css({"border":"1px solid #333333","box-shadow":"none"});
		});

		//submit arrow
		$(".submBtn").click(function(){
			if($(".forminput input[name=expressNum]").val() != ""){
				window.location.href="expressQuery.html";
			} else {
				// 微信用户没绑定手机号，提示绑定
				$("#goodcover").fadeIn();
				$("#code").show();
			}
		});

		// cover closebt
		$("#closebt").click(function(){
			$("#code").hide();
			$("#goodcover").fadeOut();
		});
	});
	</script>
</body>
</html>