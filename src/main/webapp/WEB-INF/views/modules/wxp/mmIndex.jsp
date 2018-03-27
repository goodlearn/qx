<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>我的任务</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/datePicker.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	
	<style type="text/css">
		*{
			margin: 0px;
			padding: 0px;
			list-style-type: none;
		}
		body{
			background: #e8e8e8;
		}
		.content{
			max-width: 600px;
			margin: 0 auto;
			font-family: "Tahoma","Microsoft YaHei";
			overflow: auto;
		}
		.topdiv{
			width: 100%;
			height: 1px;
		}
		.pageDesc{
			width: 100%;
			line-height: 55px;
			background: #000; 
			color: #fff;
			text-align: center;
			font-weight: bolder;
			box-shadow: 0px 5px 5px #e1e1e1; 
		}
		.userInfo{
			background: #fff; 
			overflow: hidden;
			margin: 15px 0px;
			border-top:1px solid #dbdbdb;
			border-bottom:1px solid #dbdbdb;
			box-shadow: 0px 5px 5px #e1e1e1; 
		}
		.userInfoCont{
			overflow: hidden;
			width: 90%;
			padding: 15px 5%; 
			border-bottom:1px solid #f1f1f1;
		}
		.userInfo .userInfoCont .userImgDiv{
			width: 40px;
			height: 40px;
			float: left;
			overflow: hidden;
			border-radius: 20px;
		}
		.userInfo .userInfoCont .userName{
			float: left;
			width: calc(100% - 40px - 20px - 10px);
			margin-left: 20px;
			line-height: 40px;
			overflow: hidden;
			text-overflow:ellipsis;
			white-space: nowrap;
			font-size: 16px;
			font-weight: bolder;
		}
		.userInfo .userFunc{
			width: 100%;
			overflow: hidden;
		}
		.userInfo .userFunc ul li{
			width: calc((100% - 3px) / 4); 
			float: left;
			padding: 5px 0px;
		}
		.userInfo .userFunc ul li.leftborder{
			border-right: 1px solid #f1f1f1;
		}
		.userInfo .userFunc ul li .funcIcon{
			width: 100%;
			height: 40px;
		}
		.userInfo .userFunc ul li:nth-child(1) .funcIcon{
			background: url(../static/wx/wximages/taskexefocus.png) no-repeat center center;
			background-size: 24px;
		}
		.userInfo .userFunc ul li:nth-child(2) .funcIcon{
			background: url(../static/wx/wximages/taskpubunfocus.png) no-repeat center center;
			background-size: 24px;
		}
		.userInfo .userFunc ul li:nth-child(3) .funcIcon{
			background: url(../static/wx/wximages/taskanalunfocus.png) no-repeat center center;
			background-size: 24px;
		}
		.userInfo .userFunc ul li:nth-child(4) .funcIcon{
			background: url(../static/wx/wximages/taskwaitunfocus.png) no-repeat center center;
			background-size: 24px;
		}
		.userInfo .userFunc ul li .funcTxt{
			text-align: center;
			font-size: 12px;
			padding: 5px 0px;
		}

		.notask{
			width: 100%;
			margin-top: 40px;
			text-align: center; 
			font-size: 14px;
			color: #888888;
		}
		.notask .notaskimg{
			width: 70px;
			margin: 0 auto 20px;
		}

		.sliderCont{
			width: 100%;
			overflow: hidden; 
		}
		.sliderCont>ul{
			width: 400%;
			overflow: hidden;
		}
		.sliderCont>ul>li{
			width: 25%;
			float: left;
		}
		
		.funcDesc{
			width: 100%;
			line-height: 40px;
			text-align: center;
			font-size: 14px;
			color: #888888;
		}
		.taksInfoCont{
			box-shadow: 0px 5px 5px #e1e1e1; 
			margin-bottom: 10px;
			border-top:1px solid #dbdbdb;
			border-bottom:1px solid #dbdbdb;
		}
		.workTaskCont{
			width: 100%;
			background: #fff; 
			overflow: hidden;
		}
		.workTaskCont .taskType{
			width: 96%;
			padding: 0px 2%;
			overflow: hidden;
			border-bottom: 1px solid #ebebeb;
		}
		.workTaskCont .taskType .taskTypeTxt{
			float: left;
			padding-left: 27px;
			padding-top: 20px;
			padding-bottom: 20px;
			font-size: 16px;
			font-weight: bolder;
			color: #666666;
			background: url(../static/wx/wximages/tasktypeicon.png) no-repeat left center;
			background-size: 23px;
		}
		.workTaskCont .taskType .taskComplete{
			float: left;
			padding-left: 27px;
			padding-top: 20px;
			padding-bottom: 20px;
			font-size: 16px;
			font-weight: bolder;
			color: #666666;
			background: url(../static/wx/wximages/tasktypeicon.png) no-repeat left center;
			background-size: 23px;
		}
		.workTaskCont .taskType .taskCompleteState{
			background: url(../static/wx/wximages/completeicon.png) no-repeat left center;
			background-size: 23px;
		}
		.workTaskCont .taskType .taskUnCompleteState{
			background: url(../static/wx/wximages/uncompleteicon.png) no-repeat left center;
			background-size: 23px;
		}
		.workTaskCont .taskType .taskBtn{
			float: right;
			font-size: 14px;
			background: #1f72ff;
			padding: 5px 15px;
			margin: 15px 8px 0px 0px;
			color: #fff;
			border-radius: 5px;
		}
		.workTaskCont .taskType .taskInfoBtn{
			float: right;
			font-size: 14px;
			padding: 5px 15px;
			margin: 15px 8px 0px 0px;
			color: #fff;
			border-radius: 5px;
		}
		.workTaskCont .taskType .taskCompleteBtn{
			background: #1f72ff;
		}
		.workTaskCont .taskType .taskUnCompleteBtn{
			background: #888888;
		}
		.taskInfoBtn a:link,.taskInfoBtn a:visited,.taskInfoBtn a:hover{
			color: #fff;
			text-decoration: none;
		}

		.taskCont{
			width: 100%;
			display: none;
		}
		.taskCont ul li{
			display: block;
			width: 96%;
			padding: 0px 2%;
			overflow: hidden;
			border-bottom: 1px solid #ebebeb;
			background: url(../static/wx/wximages/arrowrighticon.png) no-repeat 97% center;
			background-size: 20px;
		}
		.taskCont ul li p{
			padding-top: 20px;
			padding-bottom: 20px;
			padding-left: 22px;
			font-size: 14px;
			font-weight: bold;
			color: #666666;
		}
		.taskCont ul li .uncomplete{
			background: url(../static/wx/wximages/uncompleteicon.png) no-repeat left center;
			background-size: 16px;
		}
		.taskCont ul li .complete{
			background: url(../static/wx/wximages/completeicon.png) no-repeat left center;
			background-size: 16px;
		}
		.taskCont ul a:hover,.taskCont ul a:visited,.taskCont ul a:link{
			text-decoration: none;
			color: #666666;
		}

	</style>
</head>
<body>
<div class="content">
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<div class="pageDesc">${fullName}</div>
	<div class="userInfo">
		<div class="userInfoCont">
			<div class="userImgDiv">
				<img src="../static/wx/wximages/headerimgicon.png" alt="" width="100%">
			</div>
			<div class="userName">${userName}</div>
		</div>
		<div class="userFunc">
			<ul>
			<c:forEach items="${navigaionList}" var="navigaion" varStatus="status">
					<li class="leftborder">
							<p class="funcIcon"></p>
							<p class="funcTxt">${navigaion}</p>
						</li>
				</c:forEach>
			</ul>
		</div>
	</div>

	<div class="sliderCont">
		<ul>
			<li><!-- 任务执行 -->
				<div class="funcDesc">当前待完成任务</div>
				<div class="taksInfoCont">
					<div class="workTaskCont">
						<div class="taskType">
							<div class="taskTypeTxt">任务一</div>
							<div class="taskBtn showtaskBtn">展开</div>
						</div>

						<div class="taskCont">
							<ul>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车1</p></li>
								</a>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车2</p></li>
								</a>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车3</p></li>
								</a>
							</ul>
						</div>
					</div>
					<div class="workTaskCont">
						<div class="taskType">
							<div class="taskTypeTxt">任务一</div>
							<div class="taskBtn showtaskBtn">展开</div>
						</div>

						<div class="taskCont">
							<ul>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车1</p></li>
								</a>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车2</p></li>
								</a>
								<a href="monthPlanTask.html">
									<li><p class="uncomplete">车3</p></li>
								</a>
							</ul>
						</div>
					</div>
				</div>
			</li>

			<li> <!-- 任务发布 -->
				<div class="funcDesc">月度计划任务列表</div>
				<c:if test = "${not empty mmwsList}">
					<c:forEach items="${mmwsList}" var="mmws" varStatus="status">
							<div class="taksInfoCont">
								<div class="workTaskCont">
									<div class="taskType">
										<div class="taskTypeTxt">${mmws.maskDesc}</div>
										<div id="${mmws.id}" class="taskBtn taskPubBtn">分配</div>
									</div>
								</div>
							</div>
					</c:forEach>
				</c:if>
				<c:if test = "${empty mmwsList}">
					无分配任务
				</c:if>
			</li>
		</ul>
	</div>
</div>

<script type="text/javascript">
	var pageContextVal = $("#PageContext").val();
	$(document).ready(function() {
		// task show
		$(".showtaskBtn").click(function(){
			if ($(this).parent().siblings(".taskCont").css('display') == 'none') {
				$(this).text("收起");
			} else {
				$(this).text("展开");
			}
			$(this).parent().siblings(".taskCont").slideToggle('fast');
		});

		// func solider 
		$(".userFunc ul li").click(function(){
			var contW = $(".sliderCont").width();
			var clickIndex = $(this).index();
			var $funclis = $(".userFunc ul li");

			switch(clickIndex){
				case 0 : 
					$funclis.eq(0).children('.funcIcon').css({
						'background-image' : 'url(../static/wx/wximages/taskexefocus.png)'
					});
					$funclis.eq(1).children('.funcIcon').css({
						'background-image' : 'url(../static/wx/wximages/taskpubunfocus.png)'
					});
					$(".sliderCont ul").animate({"margin-left": 0+'px'});
					break;
				case 1 :
					$funclis.eq(0).children('.funcIcon').css({
						'background-image' : 'url(../static/wx/wximages/taskexenufocus.png)'
					});
					$funclis.eq(1).children('.funcIcon').css({
						'background-image' : 'url(../static/wx/wximages/taskpubfocus.png)'
					});
					$(".sliderCont ul").animate({"margin-left": -contW+'px'});
					break;
				default :
					break;
			};
		});

		// task pub
		$(".taskPubBtn").click(function(){
			var  mmwsId =  $(this).attr("id");
			window.location.href =pageContextVal+'/mmc/allocationPage?mmwsId='+mmwsId;
		});
	});
</script>
</body>
</html>