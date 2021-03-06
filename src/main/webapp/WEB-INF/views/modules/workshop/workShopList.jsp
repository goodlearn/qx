<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/workShop/">车间数据列表</a></li>
		<shiro:hasPermission name="sys:workShop:edit"><li><a href="${ctx}/sys/workShop/form">车间数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workShop" action="${ctx}/sys/workShop/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>车间名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车间名称</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workShop:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workShop">
			<tr>
				<td><a href="${ctx}/sys/workShop/form?id=${workShop.id}">
					${workShop.name}
				</a></td>
				<td>
					${workShop.remarks}
				</td>
				<shiro:hasPermission name="sys:workShop:edit"><td>
    				<a href="${ctx}/sys/workShop/form?id=${workShop.id}">修改</a>
					<a href="${ctx}/sys/workShop/delete?id=${workShop.id}" onclick="return confirmx('确认要删除该车间数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>