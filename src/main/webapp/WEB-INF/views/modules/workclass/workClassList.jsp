<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班组数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/workClass/">班组数据列表</a></li>
		<shiro:hasPermission name="sys:workClass:edit"><li><a href="${ctx}/sys/workClass/form">班组数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workClass" action="${ctx}/sys/workClass/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>班组名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属部门：</label>
				<form:select path="workKind.workDepartmentId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllDpList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>所属工种：</label>
				<form:select path="workKindId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllWorkKindList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>班组名称</th>
				<th>所属部门</th>
				<th>所属工种</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workClass:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workClass">
			<tr>
				<td><a href="${ctx}/sys/workClass/form?id=${workClass.id}">
					${workClass.name}
				</a></td>
				<td>
					${workClass.wd.name}
				</td>
				<td>
					${workClass.workKind.name}
				</td>
				<td>
					<fmt:formatDate value="${workClass.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workClass.remarks}
				</td>
				<shiro:hasPermission name="sys:workClass:edit"><td>
    				<a href="${ctx}/sys/workClass/form?id=${workClass.id}">修改</a>
					<a href="${ctx}/sys/workClass/delete?id=${workClass.id}" onclick="return confirmx('确认要删除该班组数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>