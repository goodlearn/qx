<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点检卡总负责人任务表数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/spotCheckMainPerson/">点检卡总负责人任务表数据列表</a></li>
		<shiro:hasPermission name="sys:spotCheckMainPerson:edit"><li><a href="${ctx}/sys/spotCheckMainPerson/form">点检卡总负责人任务表数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="spotCheckMainPerson" action="${ctx}/sys/spotCheckMainPerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>任务号：</label>
				<form:input path="wsMaskWcId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>个人号：</label>
				<form:input path="workPersonId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>运行时间数：</label>
				<form:input path="runTime" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>提交状态：</label>
				<form:select path="submitState" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务号</th>
				<th>个人号</th>
				<th>运行时间数</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:spotCheckMainPerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="spotCheckMainPerson">
			<tr>
				<td><a href="${ctx}/sys/spotCheckMainPerson/form?id=${spotCheckMainPerson.id}">
					${spotCheckMainPerson.wsm.id}
				</a></td>
				<td>
					${spotCheckMainPerson.wp.id}
				</td>
				<td>
					${spotCheckMainPerson.runTime}
				</td>
				<td>
					${fns:getDictLabel(spotCheckMainPerson.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${spotCheckMainPerson.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${spotCheckMainPerson.remarks}
				</td>
				<shiro:hasPermission name="sys:spotCheckMainPerson:edit"><td>
    				<a href="${ctx}/sys/spotCheckMainPerson/form?id=${spotCheckMainPerson.id}">修改</a>
					<a href="${ctx}/sys/spotCheckMainPerson/delete?id=${spotCheckMainPerson.id}" onclick="return confirmx('确认要删除该点检卡总负责人任务表数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>