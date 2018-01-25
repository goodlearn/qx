<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点检卡个人负责人任务表数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/spotCheckSinglePerson/">点检卡个人负责人任务表数据列表</a></li>
		<shiro:hasPermission name="sys:spotCheckSinglePerson:edit"><li><a href="${ctx}/sys/spotCheckSinglePerson/form">点检卡个人负责人任务表数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="spotCheckSinglePerson" action="${ctx}/sys/spotCheckSinglePerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>父任务号：</label>
				<form:input path="scmpId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>个人号：</label>
				<form:input path="workPersonId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>提交状态：</label>
				<form:select path="submitState" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>父任务号</th>
				<th>个人号</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:spotCheckSinglePerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="spotCheckSinglePerson">
			<tr>
				<td><a href="${ctx}/sys/spotCheckSinglePerson/form?id=${spotCheckSinglePerson.id}">
					${spotCheckSinglePerson.scmpId}
				</a></td>
				<td>
					${spotCheckSinglePerson.workPersonId}
				</td>
				<td>
					${fns:getDictLabel(spotCheckSinglePerson.submitState, '', '')}
				</td>
				<td>
					<fmt:formatDate value="${spotCheckSinglePerson.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${spotCheckSinglePerson.remarks}
				</td>
				<shiro:hasPermission name="sys:spotCheckSinglePerson:edit"><td>
    				<a href="${ctx}/sys/spotCheckSinglePerson/form?id=${spotCheckSinglePerson.id}">修改</a>
					<a href="${ctx}/sys/spotCheckSinglePerson/delete?id=${spotCheckSinglePerson.id}" onclick="return confirmx('确认要删除该点检卡个人负责人任务表数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>