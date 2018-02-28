<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>108T卡车660小时保养单(机械部分)管理</title>
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
		<li class="active"><a href="${ctx}/sys/item108t660By/">108T卡车660小时保养单(机械部分)列表</a></li>
		<shiro:hasPermission name="sys:item108t660By:edit"><li><a href="${ctx}/sys/item108t660By/form">108T卡车660小时保养单(机械部分)添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="item108t660By" action="${ctx}/sys/item108t660By/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>序号</th>
				<th>保养项目</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:item108t660By:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item108t660By">
			<tr>
				<td><a href="${ctx}/sys/item108t660By/form?id=${item108t660By.id}">
					${fns:getDictLabel(item108t660By.part, 'item108t660By', '')}
				</a></td>
				<td>
					${item108t660By.number}
				</td>
				<td>
					${item108t660By.byItem}
				</td>
				<td>
					<fmt:formatDate value="${item108t660By.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${item108t660By.remarks}
				</td>
				<shiro:hasPermission name="sys:item108t660By:edit"><td>
    				<a href="${ctx}/sys/item108t660By/form?id=${item108t660By.id}">修改</a>
					<a href="${ctx}/sys/item108t660By/delete?id=${item108t660By.id}" onclick="return confirmx('确认要删除该108T卡车660小时保养单(机械部分)吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>