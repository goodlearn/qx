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
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	
	<style type="text/css">
		.taskInfo ul li{
			border-bottom: 1px solid #f1f1f1;
		}
		.taskInfo ul li .faultType{
			float: left;
			margin-left: 2%;
			padding-left: 5px;
			width: 80px;
		}
		.taskInfo ul li select{
			display: block;
			float: left;
			width: calc(100% - 2% - 15px - 80px);
			padding: 15px 5px;
			border:none;
			outline: none;
			background: #fff;
		}
		.taskInfo ul li input[type=text]{
			display: block;
			float: left;
			width: calc(100% - 2% - 15px - 80px);
			padding: 17px 5px;
			border:none;
			outline: none;
		}

		.dateSelector{
			width: 92%;
			margin: 0 auto 20px;
			padding: 0 2%;
			overflow: hidden;
			border:1px solid #dbdbdb;
			border-radius: 8px;
			position: relative;
			background: #fff;
		}
		.dateSelector input{
			width: 100%;
			height: 40px;
			padding: 5px 0px;
			border: none;
			outline: none;
			background: url(../static/wx/wximages/dateicon.png) no-repeat right center;
			background-size: 30px;
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
		.addOtherCont .checkOther .checkTitle{
			text-align: center;
			line-height: 30px;
			border-bottom: 1px solid #e1e1e1;
			font-size: 14px;
			color: #1f72ff;
			font-weight: bold;
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
				<p class="pageTxt">故障记录添加</p>
			</div>
		
			<form id="taksPubSubmit" method="POST" action="save">
				<div class="taskInfo">
				<ul>
					<li>
						<p class="faultType">部门：</p>
						<select name="workDepartmentId">
							<option value="0" selected>请选择部门...</option>
								<c:forEach items="${dps}" var="dp">
									<option value="${dp.id}" >${dp.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">工种：</p>
						<select name="workKindId">
							<option value="0" selected>请选择工种...</option>
								<c:forEach items="${wks}" var="wp">
									<option value="${wp.id}" >${wp.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">班组：</p>
						<select name="workClassId">
							<option value="0" selected>请选择班组...</option>
								<c:forEach items="${wcs}" var="wc">
									<option value="${wc.id}" >${wc.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">车型：</p>
						<select name="carMotorCycleId">
							<option value="0" selected>请选择车型...</option>
								<c:forEach items="${cmcs}" var="cmc">
									<option value="${cmc.id}" >${cmc.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">车号：</p>
						<select name="carWagonId">
							<option value="0" selected>请选择车号...</option>
								<c:forEach items="${cws}" var="cw">
									<option value="${cw.id}" >${cw.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">总负责人：</p>
						<select name="wpTotalId">
							<option value="0" selected>请选择总负责人...</option>
								<c:forEach items="${wps}" var="wp">
									<option value="${wp.id}" >${wp.name}</option>
								</c:forEach>
						</select>
					</li>
					<li>
						<p class="faultType">参与人员：</p>
						<input type="text" name="wpPartId" placeholder="请输入参与人员...">
					</li>
				</ul>
			</div>
		
			<div class="taskInfo">
				<ul>
					<li>
						<p class="faultType">使用配件：</p>
						<input type="text" name="userTool" placeholder="请输入使用配件...">
					</li>
					<li>
						<p class="faultType">故障时间：</p>
						<input type="text" id="dateInput" name="faultDate" readonly="true" placeholder="请选择时间...">
					</li>
					<li>
						<p class="faultType">运行时间：</p>
						<input type="text" name="runTime" placeholder="请输入时间...">
					</li>
					<li>
						<p class="faultType">故障类型：</p>
							<select name="type">
							<option value="0" selected>请选择类型...</option>
								<c:forEach items="${frTypes}" var="frType">
									<option value="${frType.value}" >${frType.label}</option>
								</c:forEach>
						</select>
					</li>
				</ul>
			</div>
			
			<div class="addOtherCont">
				<div class="checkOther">
					<p class="checkTitle">故障描述</p>
					<textarea name="description" placeholder="请输入故障描述..."></textarea>
				</div>
				<div class="checkOther">
					<p class="checkTitle">处理方法</p>
					<textarea name="dealMethod" placeholder="请输入处理方法..."></textarea>
				</div>
				<div class="checkOther">
					<p class="checkTitle">备注信息</p>
					<textarea name="remarks" placeholder="请输入备注信息..."></textarea>
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
			// $(".backBtn").click(function(){
			// 	history.back(-1);
			// });
	
			// calendar
			var nowDate = new Date();
			var dateString = nowDate.getFullYear()+"-" + (nowDate.getMonth() + 1 )+"-" + nowDate.getDate();
			$("#dateInput").val(dateString);
			
		    var calendar = new datePicker();
			calendar.init({
				'trigger': '#dateInput', /*按钮选择器，用于触发弹出插件*/
				'type': 'date',/*模式：date日期；datetime日期时间；time时间；ym年月；*/
				'minDate':'1900-1-1',/*最小日期*/
				'maxDate':dateString,/*最大日期*/
				'onSubmit':function(){/*确认时触发事件*/
					var theSelectData=calendar.value;
				},
				'onClose':function(){/*取消时触发事件*/
				}
			});
		});
	</script>
</body>
</html>