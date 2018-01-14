<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>快递助手 -- 锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/qrcode.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
	<style type="text/css">
		body{
			background: #f4f4f4;
		}

		.userQRcodeCont{
			width: calc(90% - 10px);
			padding: 5px;
			margin: 0 auto;
			border: 1px solid #e1e1e1;
			border-radius: 8px;
			background: #fff;
		}
		.userQRcodeCont .userQRcodeTitle{
			margin: 0px;
			padding: 0px;
			font-size: 20px;
			line-height: 40px;
			font-weight: bolder;
			text-align: center;
			border-bottom: 1px solid #e1e1e1;
			color: #1f72ff;
		}
		#qrcode{
			width: 70%;
			margin-left: 15%;
			padding: 20px 0px;
		}
		.userQRcodeCont .userQRcodeDescTxt{
			font-size: 14px;
			text-align: center;
			color: #333333;
		}
		.userQRcodeCont .userInfoCont{
			border-top: 1px solid #e1e1e1;
			overflow: hidden;
			font-size: 14px;
		}
		.userQRcodeCont .userInfoCont p{
			margin: 0px;
			padding: 0px;
			line-height: 30px;
		}
		.userQRcodeCont .userInfoCont .userName{
			float: left;
			background: url(images/uname.png) no-repeat center left;
			background-size: 18px;
			padding-left: 23px;
		}
		.userQRcodeCont .userInfoCont .userPhone{
			float: right;
			background: url(images/uphone.png) no-repeat center left;
			background-size: 18px;
			padding-left: 23px;
		}

		.refreshBtn{
			width: 90%;
			margin: 10px auto 10px;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			background: #1f72ff;
			color: #fff;
			font-weight: bolder;
		}
	</style>
</head>
<body>
	<div class="content">
	<div class="headerNav">
		<div class="headerNavTop"><div class="headerNavIcon headerNavIconOut"><span></span><span></span></div></div>
		<div class="headerNavCont">
			<a href="./index.html">快递首页</a>
			<a href="./userhome.html">个人中心</a>
			<a href="./delivery.html">送货上门</a>
			<a href="./sendexpress.html">我要寄件</a>
			<a href="./lazyboard.html">懒人排行</a>
			<a href="./expassistant.html">快递助手</a>
		</div>
	</div>
	<input id="rTextData" type="hidden" value="${rTextData}" />
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<div class="userQRcodeCont">
		<div class="userQRcodeTitle">我的取货二维码</div>
		<div id="qrcode"></div>
		<p class="userQRcodeDescTxt">取快递时请出示该二维码，有效期为30分钟</p>
		<div class="userInfoCont">
			<p class="userName">${name}</p>
			<p class="userPhone">${phone}</p>
		</div>
	</div>

	<div class="refreshBtn">刷新二维码</div>
</div>
<script type="text/javascript">
	$(function() {
		var pageContextVal = $("#PageContext").val();
		var rTextData = $("#rTextData").val();

		var qrcodeWH = $(".userQRcodeCont").width() * 0.7;

		var setQRcode = function(textdata){
			// 设置参数方式
			var qrcode = new QRCode('qrcode', {
			  text: textdata,
			  width: qrcodeWH,
			  height: qrcodeWH,
			  colorDark : '#000000',
			  colorLight : '#ffffff',
			  correctLevel : QRCode.CorrectLevel.H
			});
		};

		setQRcode(rTextData);

		$(".refreshBtn").click(function(){
			window.location.reload();
		})
	});
</script>
</body>
</html>