<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>个人信息</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/main.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
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
		.submitBtn{
			width: 90%;
			margin: 0 auto 20px;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			color: #20d6da;
			border: 2px solid #20d6da;
			font-weight: bolder;
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
				<div class="nickName">请填写个人信息</div>
			</div>
		</div>
		${message}
		<div class="solidCont">
			<div class="infoCheckEditCont">
				<form id="saveForm" class="form-signin" action="${pageContext.request.contextPath}/ul/savePersonUserInfo" method="post">
					<div class="userInputCont">
						<div class="inputTypeCont">
							<div class="inputTitle">姓名</div>
							<input type="text" class="commonInput" name="name" placeholder="请输入你的姓名...">
						</div>
						<div class="inputTypeCont">
							<div class="inputTitle">手机</div>
							<input type="text" class="commonInput" name="phone" placeholder="请输入你的手机号码...">
						</div>
						<div class="inputTypeCont">
							<div class="inputTitle">证件</div>
							<input type="text" class="commonInput" name="idCard" placeholder="请输入身份证号码...">
						</div>
					</div>
					<input class="submitBtn" type="submit" value="确认提交"/>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	$(function() {
		var windowW = $(window).width();
		if (windowW > 600) {
			windowW = 600;
		}
		$(".checkBtn").click(function(){
			$(".solidCont").animate({"margin-left":-windowW+"px"},"fast");
		});
	});
</script>
</body>
</html>