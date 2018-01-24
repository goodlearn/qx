<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务结果集表数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/businessResultAssemble/">业务结果集表数据列表</a></li>
		<shiro:hasPermission name="sys:businessResultAssemble:edit"><li><a href="${ctx}/sys/businessResultAssemble/form">业务结果集表数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="businessResultAssemble" action="${ctx}/sys/businessResultAssemble/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>结果集名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>结果集类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('businessResultType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>结果集名称</th>
				<th>结果集类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:businessResultAssemble:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="businessResultAssemble">
			<tr>
				<td><a href="${ctx}/sys/businessResultAssemble/form?id=${businessResultAssemble.id}">
					${businessResultAssemble.name}
				</a></td>
				<td>
					${fns:getDictLabel(businessResultAssemble.type, 'businessResultType', '')}
				</td>
				<td>
					<fmt:formatDate value="${businessResultAssemble.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${businessResultAssemble.remarks}
				</td>
				<shiro:hasPermission name="sys:businessResultAssemble:edit"><td>
    				<a href="${ctx}/sys/businessResultAssemble/form?id=${businessResultAssemble.id}">修改</a>
					<a href="${ctx}/sys/businessResultAssemble/delete?id=${businessResultAssemble.id}" onclick="return confirmx('确认要删除该业务结果集表数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>