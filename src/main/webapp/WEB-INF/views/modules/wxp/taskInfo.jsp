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
		.checkCont{
			width: calc(96% - 2px);
			margin: 15px 2%;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
		}
		.checkCont ul li{
			display: block;
			width: 96%;
			padding: 0px 2%;
			display: block;
			font-size: 14px;
			font-weight: 100;
		}
		.checkCont ul li input[type=text]{
			display: none;
		}
		.checkCont ul li p{
			width: calc(100% - 25px);
			padding: 20px 0px 20px 25px;
			font-size: 14px;
			font-weight: bolder;
			color: #333333;
		}
		.checkCont ul li .checkTxt{
			background: url(../static/wx/wximages/selecticon.png) no-repeat left center;
			background-size: 18px;
		}
		.checkCont ul li .unCheckTxt{
			background: url(../static/wx/wximages/selecticon1.png) no-repeat left center;
			background-size: 18px;
		}
		.checkCont ul li textarea{
			width: 96%;
			height: 40px;
			padding: 10px 2%;
			font-family: "Microsoft YaHei";
			color: #666666;
			font-size: 13px;
			font-weight: 100;
			outline: none;
			border: none;
			resize: none;
			background: #f1f1f1;
			border-radius: 5px;
		}

		.otherProb{
			width: calc(96% - 2px);
			margin: 15px 2%;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow:hidden;
		}
		.otherProb ul li{
			width: 96%;
			padding: 10px 2%;
			font-size: 14px;
			font-family: "Microsoft YaHei";
			line-height: 25px;
			color: #333333;
		}
		
		.checkCont textarea{
			width: 96%;
			padding: 10px 2%;
			font-family: "Microsoft YaHei";
			font-size: 14px;
			outline: none;
			border: none;
			resize: none;
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
		<p class="pageTxt">审核提交</p>
		<p class="backBtn">&lt; 返回</p>
	</div>
	
	<div class="taskInfo">
		<ul>
			<li class="borderButtom"><p class="taskType">${maskInfo.workShopMaskName}</p></li>
		</ul>
	</div>
	<c:forEach items="${maskInfo.mspList}" var="msp" varStatus="status">
		<div class="taskInfo">
			<ul>
				<li class="borderButtom"><p class="taskProject">${msp.partName}</p></li>
				<li><p class="checkPerson">${msp.wp.name}</p></li>
			</ul>
		</div>
		<div class="checkCont">
			<ul>
				<c:forEach items="${msp.mcList}" var="mc" varStatus="mcstatus">
					<c:if test = "${not empty mc.tc}">
						<li class="borderButtom">
						<input type="text" value="fdsafd" name="" readonly="true">
						<p class="checkTxt">${mc.tc.item}</p>
						<textarea readonly="true">${mc.remarks}</textarea>
					</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
		<!-- 其他问题展示 -->
		<div class="otherProb">
			<ul>
				<li class="borderButtom">
					${msp.desc}		
				</li>
			</ul>
		</div>
	</c:forEach>
</div>

<script type="text/javascript">
	$(function() {
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    if (isAndroid == true) {
	    	$(".taskInfo ul li p").css({
	    		"padding-top": "15px",
	    		"padding-bottom": "12px"
	    	});
	    	$(".checkCont ul li p").css({
	    		"padding": "20px 0px 17px 25px"
	    	})
	    }


		// backNav
		$(".backBtn").click(function(){
			history.back(-1);
		});

	});
</script>
</body>
</html>