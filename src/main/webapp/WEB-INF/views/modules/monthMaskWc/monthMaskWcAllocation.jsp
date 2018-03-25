<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班组任务月度表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/monthMaskWc/">班组任务</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="monthMaskWc" action="${ctx}/sys/monthMaskWc/saveAllocation" method="post" class="form-horizontal">
		<form:hidden path="monthMaskWsId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">责任人：</label>
			<div class="controls">
				<form:select path="workPersonId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${wps}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test = "${not empty monthMaskWs}">
				<div class="control-group">
					<label class="control-label">任务说明：</label>
					<div class="controls">
						<textarea rows="3" cols="30" readonly="readonly">
							${monthMaskWs.maskDesc}
						</textarea>
					</div>
				</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:monthMaskWc:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>