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
		<p class="pageTxt">任务发布</p>
		<p class="backBtn">&lt; 返回</p>
	</div>
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="maskId" type="hidden" value="${maskId}" />
	<form id="taksPubSubmit">
		<div class="taskInfo">
			<ul>
				<li class="borderButtom"><p class="taskType">${wsmName}</p></li>
			</ul>
		</div>
		<c:forEach items="${parts}" var="var" varStatus="status">
			<div class="taskInfo">
				<ul>
					<li class="borderButtom">
						<p id="${var.value}" class="taskProject">
							<input id="${var.value}" type="hidden" />
						</p>
					</li>
					<li>
						<p class="checkPerson">负责人员：</p>
						<select>
							<option value="0" selected>请选择人员...</option>
							<c:forEach items="${wp}" var="wpvar">
								<option value="${wpvar.no}">${wpvar.name}</option>
							</c:forEach>
						</select>
					</li>
				</ul>
			</div>
		</c:forEach>
		<button class="submitBtn">确认发布</button>
	</form>
</div>

<script type="text/javascript">
	$(function() {
		//var spanW = $(".checkPerson span").width();
		//alert(spanW);
		var pageContextVal = $("#PageContext").val();
		var maskId = $("#maskId").val();
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    if (isAndroid == true) {
	    	$(".taskInfo ul li p span").css({
	    		"padding-top": "15px",
	    		"padding-bottom": "12px"
	    	});
	    	$(".checkCont ul li p").css({
	    		"padding": "20px 0px 17px 25px"
	    	});
	    }


		// backNav
		$(".backBtn").click(function(){
			history.back(-1);
		});

		// submit
		$(".submitBtn").click(function(){
			
			var mcsijson = {
					"viewMcsi1":[]
			};
			
			var $liselects = $(".taskInfo select");
			
			for (var i = 0; i < $liselects.length; i++) {
				var tmpVal = $liselects.eq(i).val();
				if (tmpVal == "0") {
					alert("还有负责人未分配");
					return false;
				}
				var $partNames = $(".taskProject input").eq(i).attr("id");
				var empd = {"name":$partNames,"empno":tmpVal,"maskId":maskId};
				mcsijson.viewMcsi1.push(empd);
			}

			var state = confirm("提交后不可修改，确认提交？");
			if(!state){
			   return false;
			}
			
			$.ajax({
			     type:'POST',
			     url:pageContextVal+'/wmw/allocation',
			     data:JSON.stringify(mcsijson.viewMcsi1),
				 dataType: "json",
				 contentType:"application/json",
				 success:function(data){
					 alert(data.message);
					 window.location.href= pageContextVal+"/wi/indexInfo"; 
			     },
			     error:function(){
			      	alert("未知错误");
			     }
			 });
			
			 return false;
		});
	});
</script>
</body>
</html>