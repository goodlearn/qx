<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务总表管理</title>
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
		<li class="active"><a href="${ctx}/sys/businessAssemble/">总表数据列表</a></li>
		<shiro:hasPermission name="sys:businessAssemble:edit"><li><a href="${ctx}/sys/businessAssemble/form">总表数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessAssemble" action="${ctx}/sys/businessAssemble/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>表格名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>表格类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussinessType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>表格名称</th>
				<th>表格类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:businessAssemble:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessAssemble">
			<tr>
				<td><a href="${ctx}/sys/businessAssemble/form?id=${businessAssemble.id}">
					${businessAssemble.name}
				</a></td>
				<td>
					${fns:getDictLabel(businessAssemble.type, 'bussinessType', '')}
				</td>
				<td>
					<fmt:formatDate value="${businessAssemble.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessAssemble.remarks}
				</td>
				<shiro:hasPermission name="sys:businessAssemble:edit"><td>
    				<a href="${ctx}/sys/businessAssemble/form?id=${businessAssemble.id}">修改</a>
					<a href="${ctx}/sys/businessAssemble/delete?id=${businessAssemble.id}" onclick="return confirmx('确认要删除该业务集数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>