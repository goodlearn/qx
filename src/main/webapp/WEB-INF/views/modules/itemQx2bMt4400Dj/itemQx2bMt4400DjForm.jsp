<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汽修二班MT4400保养责任分区管理</title>
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
		<li><a href="${ctx}/sys/itemQx2bMt4400Dj/">汽修二班MT4400保养责任分区列表</a></li>
		<li class="active"><a href="${ctx}/sys/itemQx2bMt4400Dj/form?id=${itemQx2bMt4400Dj.id}">汽修二班MT4400保养责任分区<shiro:hasPermission name="sys:itemQx2bMt4400Dj:edit">${not empty itemQx2bMt4400Dj.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:itemQx2bMt4400Dj:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="itemQx2bMt4400Dj" action="${ctx}/sys/itemQx2bMt4400Dj/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">表格名称：</label>
			<div class="controls">
				<form:select path="assembleId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getBaList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>				
		<div class="control-group">
			<label class="control-label">部位：</label>
			<div class="controls">
				<form:select path="part" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('itemQx2bMt4400Dj')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">序号：</label>
			<div class="controls">
				<form:input path="number" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">检查内容：</label>
			<div class="controls">
				<form:textarea path="checkContent" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:itemQx2bMt4400Dj:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>