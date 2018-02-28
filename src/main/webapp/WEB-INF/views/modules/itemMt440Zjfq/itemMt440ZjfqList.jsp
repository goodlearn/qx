<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>MT4400卡车钳工周检分区管理</title>
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
		<li class="active"><a href="${ctx}/sys/itemMt440Zjfq/">MT4400卡车钳工周检分区列表</a></li>
		<shiro:hasPermission name="sys:itemMt440Zjfq:edit"><li><a href="${ctx}/sys/itemMt440Zjfq/form">MT4400卡车钳工周检分区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="itemMt440Zjfq" action="${ctx}/sys/itemMt440Zjfq/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>序号</th>
				<th>检查内容</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:itemMt440Zjfq:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="itemMt440Zjfq">
			<tr>
				<td><a href="${ctx}/sys/itemMt440Zjfq/form?id=${itemMt440Zjfq.id}">
					${fns:getDictLabel(itemMt440Zjfq.part, 'itemMt440Zjfq', '')}
				</a></td>
				<td>
					${itemMt440Zjfq.number}
				</td>
				<td>
					${itemMt440Zjfq.checkContent}
				</td>
				<td>
					<fmt:formatDate value="${itemMt440Zjfq.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${itemMt440Zjfq.remarks}
				</td>
				<shiro:hasPermission name="sys:itemMt440Zjfq:edit"><td>
    				<a href="${ctx}/sys/itemMt440Zjfq/form?id=${itemMt440Zjfq.id}">修改</a>
					<a href="${ctx}/sys/itemMt440Zjfq/delete?id=${itemMt440Zjfq.id}" onclick="return confirmx('确认要删除该MT4400卡车钳工周检分区吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>