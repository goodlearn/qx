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
		.checkCont .taskBtn{
			float: right;
			font-size: 12px;
			background: #1f72ff;
			padding: 5px 15px;
			margin: 16px 0px 0px 0px;
			color: #fff;
			border-radius: 5px;
		}
		.checkCont ul li{
			display: block;
			width: 96%;
			padding: 0px 2%;
			font-size: 14px;
			font-weight: 100;
			overflow: hidden;
		}
		.checkCont ul li input[type=checkbox]{
			display: none;
		}
		.checkCont ul li label{
			display: block;
			float: left;
			padding: 20px 0px 20px 30px;
			width:calc(100% - 30px);
			overflow: hidden;
		}
		.checkCont ul li label.unselect{
			background: url(../static/wx/wximages/unselecticon.png) no-repeat left center;
			background-size: 20px;
		}
		.checkCont ul li label.select{
			background: url(../static/wx/wximages/selecticon.png) no-repeat left center;
			background-size: 20px;
		}
		.checkCont ul li textarea{
			width: 96%;
			padding: 10px 2%;
			height: 40px;
			font-family: "Microsoft YaHei";
			color: #666666;
			font-size: 14px;
			font-weight: 100;
			outline: none;
			border: none;
			resize: none;
			background: #f1f1f1;
			border-radius: 5px;
		}

		.addOtherBtn{
			width: calc(92% - 2px);
			margin: 15px 2%;
			padding: 0px 2%;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			line-height: 55px;
			font-size: 15px;
			font-weight: bolder;
			color: #1f72ff;
			background: url(../static/wx/wximages/addicon.png) #fff no-repeat right center;
			background-size: 20px;
			background-origin: content-box;
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
			height: 40px;
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

		.checkName{
			width: calc(96% - 2px);
			margin: 0 auto 15px;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow: hidden;
		}
		.checkName .checkNameTxt{
			float: left;
			font-size: 14px;
			text-align: center;
			color: #666666;
			padding: 15px 0px;
			border-right:1px solid #d8d8d8;
			width: 60px;
		}
		.checkName .checkUserName{
			display: block;
			float: left;
			width: calc(96% - 61px - 10px);
			margin-left: 5px;
			padding: 15px 0px;
			outline: none;
			border: none;
			font-size: 14px;
			font-weight: 100;
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
		<p class="pageTxt">任务检查</p>
		<p class="backBtn">&lt; 返回</p>
	</div>

	<div class="taskInfo">
		<ul>
			<li><p class="taskType borderButtom">${maskName}</p></li>
			<li><p class="taskProject">${msp.partName}</p></li>
		</ul>
	</div>
	
	<input id="submitMspId" type="hidden" value="${msp.id}" />
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	
	<form id="checkForm" action="${pageContext.request.contextPath}/wmw/utSubmit" method="post">
		<div class="checkCont">
			<ul>
				<c:forEach items="${mcList}" var="mc" varStatus="status">
					<c:if test = "${not empty mc.tc}">
						<li class="borderButtom">
							<input type="checkbox" id="check${status.count}" value="${mc.id}" name="chb">
							<label class="unselect" for="check${status.count}" id="${mc.id}" >${mc.tc.item}</label>
						</li>
					</c:if>
				</c:forEach>
			</ul>
			
		</div>

	<div class="addOtherBtn">添加其他问题</div>

	<div class="addOtherCont">
		<!-- 
		<div class="checkOther">
			<textarea placeholder="在此输入其他问题，多条请添加多次..."></textarea>
			<div class="removeContBtn">删除此条问题</div>
		</div>
		<div class="checkOther">
			<textarea placeholder="在此输入其他问题，多条请添加多次..."></textarea>
			<div class="removeContBtn">删除此条问题</div>
		</div>
		-->
	</div>
	</form>
	<button class="submitBtn">提交审核</button>
</div>

<script type="text/javascript">
$(function() {
		var submitMspId = $("#submitMspId").val();
		var pageContextVal = $("#PageContext").val();
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
	
		// addDescrib
		var addBtnFun = function(){
			var btnH = $(".taskBtn").outerHeight();
			var liH = $(".taskBtn").siblings('label').outerHeight();
			$(".taskBtn").css({'margin-top':(liH - btnH)/2 + "px"});
		}
		
	
		// checkSlect
		var initCheck = function(){
			var checkArray = $(".checkCont li input[type=checkbox]:checked");
			var unCheckArray = $(".checkCont li input[type=checkbox]:not(:checked)");
			for (var i = 0; i < checkArray.length; i++) {
				checkArray.eq(i).siblings('label').removeClass('unselect').addClass('select');
				checkArray.eq(i).siblings('label').css({'width':"calc(100% - 30px - 60px)"});
				var $addBtn = $("<div class='taskBtn addTxtBtn'>描述</div>");
				$addBtn.bind("click",function(){
					if ($(this).siblings('textarea').length == 0) {
						$(this).text("删除");
						$(this).css({"background":"#d81f07"});
						var $txtarea = $("<textarea placeholder='在此输入对该问题的描述...'></textarea>");
						$(this).parent().append($txtarea);
					} else {
						$(this).text("描述");
						$(this).css({"background":"#1f72ff"});
						$(this).siblings('textarea').remove();
					}
					
				});
				checkArray.eq(i).parent().append($addBtn);
			}
			for (var i = 0; i < unCheckArray.length; i++) {
				unCheckArray.eq(i).siblings('label').removeClass('select').addClass('unselect');
				unCheckArray.eq(i).siblings('taskBtn').remove();
				unCheckArray.eq(i).siblings('label').css({'width':"calc(100% - 30px)"});
			}
			
		};
	
		initCheck();
	
		$(".checkCont ul li input[type=checkbox]").change(function(){
	
			if ($(this).is(":checked")) {
				$(this).siblings('label').removeClass('unselect').addClass('select');
				$(this).siblings('label').css({'width':"calc(100% - 30px - 60px)"});
				var $addBtn = $("<div class='taskBtn addTxtBtn'>描述</div>");
				$addBtn.bind("click",function(){
					if ($(this).siblings('textarea').length == 0) {
						$(this).text("删除");
						$(this).css({"background":"#d81f07"});
						var $txtarea = $("<textarea placeholder='在此输入对该问题的描述...'></textarea>");
						$(this).parent().append($txtarea);
					} else {
						$(this).text("描述");
						$(this).css({"background":"#1f72ff"});
						$(this).siblings('textarea').remove();
					}
					
				});
				$(this).parent().append($addBtn);
	
				var btnH = $addBtn.outerHeight();
				var liH = $addBtn.siblings('label').outerHeight();
				$addBtn.css({'margin-top':(liH - btnH)/2 + "px"});
				//addBtnFun();
			} else {
				$(this).siblings('label').removeClass('select').addClass('unselect');
				$(this).siblings('.taskBtn').remove();
				$(this).siblings('textarea').remove();
				$(this).siblings('label').css({'width':"calc(100% - 30px)"});
			}
			//initCheck();
		});
	
		// addOther
		$(".addOtherBtn").click(function(){
			var $checkOther = $("<div class='checkOther'></div>");
			var $checkarea = $("<textarea placeholder='在此输入其他问题，多条请添加多次...'></textarea>");
			$checkOther.append($checkarea);
			var $removeBtn = $("<div class='removeContBtn'>删除此条问题</div>");
			$removeBtn.bind("click",function(){
				var state = confirm("确认删除？");
				if(!state){
				   return false;
				}
				$checkOther.remove();
			})
			$checkOther.append($removeBtn);
	
			$(".addOtherCont").prepend($checkOther);
		});
	
		// submit
		$(".submitBtn").click(function(){
			var areaArr = $(".checkOther textarea");
			if (areaArr.length != 0) {
				var areaTxt;
				for(var i = 0; i < areaArr.length; i++) {
					areaTxt = $.trim(areaArr.eq(i).val());
					if (areaTxt == "") {
						alert("手写输入框为空，没问题请删除再提交");
						return false;
					}
				}
			}
			
			var state = confirm("确认提交审核？");
			if(!state){
			   return false;
			}
			//$("#checkForm").submit();
	
			var dataJson = {
				"checkdata" : [],
				"otherdata" : [],
				"username" : ""
			};
			var checkArray = $(".checkCont li input[type=checkbox]:checked");
			for (var i = 0; i < checkArray.length; i++) {
				var tmpData = {"probTxt":"-1","probDesc":"-1"};
				tmpData.probTxt = checkArray.eq(i).siblings('label').attr("id");
				if (checkArray.eq(i).siblings('textarea').length > 0 ) {
					tmpData.probDesc = checkArray.eq(i).siblings('textarea').val();
				}
				dataJson.checkdata.push(tmpData);
			}
	
			var otherProbArr = $(".addOtherCont textarea");
			for (var i = 0; i < otherProbArr.length; i++){
				var tmpProb = otherProbArr.eq(i).val();
				dataJson.otherdata.push(tmpProb);
			}
	
	
			var transfData = {
					"checkdata" : "",
					"otherdata" : "",
					"submitMspId" : ""
			};
			transfData.checkdata = JSON.stringify(dataJson.checkdata);
			transfData.otherdata = JSON.stringify(dataJson.otherdata);
			transfData.submitMspId = submitMspId;
			 $.ajax({
			     type:'POST',
			     url:pageContextVal+'/wmw/utSubmit',
			     data:JSON.stringify(transfData),
				 dataType: "json",
				 contentType:"application/json",
			     success:function(data){
			    	 alert("提交成功");
			    	 window.location.href= pageContextVal+"/wi/indexInfo";
			     },
			     error:function(){
			    	 alert("未知失败");
			     }
			 });
		});
	});	
</script>
</body>
</html>