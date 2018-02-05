<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间工种数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/workKind/">车间工种数据列表</a></li>
		<shiro:hasPermission name="sys:workKind:edit"><li><a href="${ctx}/sys/workKind/form">车间工种数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workKind" action="${ctx}/sys/workKind/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>工种名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属部门：</label>
				<form:select path="workDepartmentId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllDpList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>工种名称</th>
				<th>所属部门</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workKind:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workKind">
			<tr>
				<td><a href="${ctx}/sys/workKind/form?id=${workKind.id}">
					${workKind.name}
				</a></td>
				<td>
					${workKind.workDepartment.name}
				</td>
				<td>
					<fmt:formatDate value="${workKind.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workKind.remarks}
				</td>
				<shiro:hasPermission name="sys:workKind:edit"><td>
    				<a href="${ctx}/sys/workKind/form?id=${workKind.id}">修改</a>
					<a href="${ctx}/sys/workKind/delete?id=${workKind.id}" onclick="return confirmx('确认要删除该车间工种数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>