<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间部门数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/workDepartment/">车间部门数据列表</a></li>
		<shiro:hasPermission name="sys:workDepartment:edit"><li><a href="${ctx}/sys/workDepartment/form">车间部门数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workDepartment" action="${ctx}/sys/workDepartment/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li>
				<label>所属车间：</label>
				<form:select path="workShopId">
						<form:options items="${fns:getAllWorkShopList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>部门名称</th>
				<th>所属车间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workDepartment:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workDepartment">
			<tr>
				<td><a href="${ctx}/sys/workDepartment/form?id=${workDepartment.id}">
					${workDepartment.name}
				</a></td>
				<td>
					${workDepartment.workShop.name}
				</td>
				<td>
					<fmt:formatDate value="${workDepartment.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workDepartment.remarks}
				</td>
				<shiro:hasPermission name="sys:workDepartment:edit"><td>
    				<a href="${ctx}/sys/workDepartment/form?id=${workDepartment.id}">修改</a>
					<a href="${ctx}/sys/workDepartment/delete?id=${workDepartment.id}" onclick="return confirmx('确认要删除该车间部门数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>