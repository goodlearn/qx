<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>我的任务</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	
	<style type="text/css">
		.taskInfo ul li .checkPerson{
			float: left;
			margin-left: 2%;
			padding-left: 25px;
		}
		.taskInfo ul li select{
			display: block;
			float: left;
			width: calc(100% - 2% - 35px - 80px);
			padding: 15px 5px;
			border:none;
			outline: none;
			background: #fff;
		}
		.taskInfo ul li .faultType{
			float: left;
			margin-left: 2%;
			padding-left: 5px;
			width: 80px;
		}
		.taskInfo ul li input[type=text]{
			display: block;
			float: left;
			width: calc(100% - 2% - 15px - 80px);
			padding: 17px 5px;
			border:none;
			outline: none;
		}
		.addOtherCont{
			width: 100%;
		}
		.addOtherCont .checkOther{
			width: calc(96% - 2px);
			margin: 0 auto 15px;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow: hidden;
		}
		.addOtherCont .checkOther textarea{
			width: 96%;
			padding: 10px 2%;
			height: 150px;
			font-size: 14px;
			font-weight: 100;
			outline: none;
			border: none;
			resize: none;
		}
		.addOtherCont .checkOther .removeContBtn{
			font-size: 14px;
			font-weight: bolder;
			color: #d81e06;
			text-align: center;
			line-height: 35px;
			border-top: 1px solid #f2f2f2;
		}

		.submitBtn{
			display: block;
			width: calc(96% - 2px);
			margin: 0 auto 15px;
			background: #1f72ff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow: hidden;
			line-height: 50px;
			color: #fff;
			font-size: 14px;
			font-weight: bolder;
			outline: none;
		}
	</style>
</head>
<body>
		<div class="content">
			<div class="pageNav">
				<p class="pageTxt">月度计划任务非车辆添加</p>
				<p class="backBtn">&lt; 返回</p>
			</div>
			<form id="taksPubSubmit" method="POST" action="saveCarMask">
			
				<input id="monthMaskWcId" name="monthMaskWcId" type="hidden" value="${mmwc.id}" />
							<input id="id" name="id" type="hidden" value="${mm.id}" />
			
			
				<div class="taskInfo">
					<ul>
						<li><p class="taskType borderButtom">${mmwc.mmws.maskDesc}</p></li>
					</ul>
				</div>
			
				<div class="addOtherCont">
					<div class="checkOther">
						<textarea name = "checkResult" placeholder="请输入检查描述...">${mm.checkResult}</textarea>
					</div>
				</div>
			
				<button class="submitBtn">提交检查</button>
			</form>
		</div>
		
		<script type="text/javascript">
			$(document).ready(function() {
				var u = navigator.userAgent;
			    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
			    if (isAndroid == true) {
			    	$(".taskInfo ul li p").css({
			    		"padding-top": "15px",
			    		"padding-bottom": "12px"
			    	});
			    	$(".checkCont ul li label").css({
			    		"padding":"20px 0px 17px 30px"
			    	});
			    	$(".checkName .checkNameTxt").css({
			    		"padding": "15px 0px 13px"
			    	});
			    	$(".checkName .checkUserName").css({
			    		"padding": "18px 0px 13px"
			    	});
			    	
			    }
		
			    // backNav
				$(".backBtn").click(function(){
					history.back(-1);
				});
			});
</script>
</body>
</html>