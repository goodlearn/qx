<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>总负责人任务表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/maskMainPerson/">任务表内容</a></li>
	</ul><br/>
	<form:form class="form-horizontal">
		<sys:message content="${message}"/>		
		<fieldset>
			<legend>内容信息</legend>
			<table class="table-form">
				<tr>
					<td class="tit">ID</td>
					<td colspan="2">${result.id}</td>
					<td class="tit">提交状态</td>
					<td colspan="2">${fns:getDictLabel(result.submitState, 'yes_no', '')}</td>
					<td class="tit">总负责人</td>
					<td colspan="2">${result.wp.name}</td>
					<td class="tit">运行时间数</td>
					<td colspan="2">${result.wmw.runTime}</td>
					<td class="tit">结束时间</td>
					<td colspan="2">${result.wmw.endDate}</td>
					<td class="tit">班组任务提交状态</td>
					<td colspan="2">${fns:getDictLabel(result.wmw.submitState, 'yes_no', '')}</td>
				</tr>
				<c:forEach items="${result.mspList}" var="msp" varStatus="status">
					<tr>
						<td class="tit">ID</td>
						<td colspan="2">${msp.id}</td>
						<td class="tit">提交状态</td>
						<td colspan="2">${fns:getDictLabel(msp.submitState, 'yes_no', '')}</td>
						<td class="tit">处理部位</td>
						<td colspan="2">${msp.partName}</td>
					</tr>
					<c:forEach items="${msp.mcList}" var="mc" varStatus="mcstatus">
						<tr>
							<td class="tit">ID</td>
							<td colspan="2">${mc.id}</td>
							<td class="tit">模板号</td>
							<td colspan="2">${mc.templateId}</td>
							<td class="tit">问题状态</td>
							<td colspan="2">${mc.problem}</td>
							<td class="tit">描述</td>
							<td colspan="2">${mc.remarks}</td>
						</tr>
					</c:forEach>
				</c:forEach>
			</table>
		</fieldset>
	</form:form>
</body>
</html>