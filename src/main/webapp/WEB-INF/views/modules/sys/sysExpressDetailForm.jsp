<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>快递信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysExpress/">快递列表</a></li>
	</ul><br/>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>	
		<fieldset>
			<legend>快递信息</legend>
			<table class="table-form">
				<tr>
					<td class="tit">姓名</td>
					<td colspan="2">${sysExpress.sysWxUser.name}</td>
					<td class="tit">身份证号</td>
					<td colspan="4">${sysExpress.sysWxUser.idCard}</td>
					<td class="tit">个人手机号</td>
					<td colspan="6">${sysExpress.sysWxUser.phone}</td>
				</tr>
					<td class="tit">快递单号</td>
					<td colspan="2">${sysExpress.expressId}</td>
					<td class="tit">快递状态</td>
					<td colspan="4">
						${fns:getDictLabel(sysExpress.state,'expressState','')}
					</td>
					<td class="tit">快递手机号</td>
					<td colspan="6">${sysExpress.phone}</td>
				<tr>
				</tr>
				<tr>
					<td class="tit">快递入库操作人</td>
					<td colspan="2">${sysExpress.createBy.name}</td>
					<td class="tit">快递入库时间</td>
					<td colspan="4">
						<fmt:formatDate value="${sysExpress.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="tit">上次快递信息变更操作人</td>
					<td colspan="2">${sysExpress.updateBy.name}</td>
					<td class="tit">上次快递信息变更时间</td>
					<td colspan="2">
						<fmt:formatDate value="${sysExpress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>				
				</tr>
			</table>
		</fieldset>	
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>