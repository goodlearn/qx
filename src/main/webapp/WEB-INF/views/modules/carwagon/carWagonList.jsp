<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间车号管理</title>
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
		<li class="active"><a href="${ctx}/sys/carWagon/">车间车号列表</a></li>
		<shiro:hasPermission name="sys:carWagon:edit"><li><a href="${ctx}/sys/carWagon/form">车间车号添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="carWagon" action="${ctx}/sys/carWagon/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属车型：</label>
				<form:select path="carMotorCycleId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllCmcList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>名称</th>
				<th>所属车型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:carWagon:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="carWagon">
			<tr>
				<td><a href="${ctx}/sys/carWagon/form?id=${carWagon.id}">
					${carWagon.name}
				</a></td>
				<td>
					${carWagon.cmc.name}
				</td>
				<td>
					<fmt:formatDate value="${carWagon.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${carWagon.remarks}
				</td>
				<shiro:hasPermission name="sys:carWagon:edit"><td>
    				<a href="${ctx}/sys/carWagon/form?id=${carWagon.id}">修改</a>
					<a href="${ctx}/sys/carWagon/delete?id=${carWagon.id}" onclick="return confirmx('确认要删除该车间车号吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>