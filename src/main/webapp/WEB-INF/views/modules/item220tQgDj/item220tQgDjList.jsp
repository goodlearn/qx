<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>220T卡车钳工点检分区管理</title>
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
		<li class="active"><a href="${ctx}/sys/item220tQgDj/">220T卡车钳工点检分区列表</a></li>
		<shiro:hasPermission name="sys:item220tQgDj:edit"><li><a href="${ctx}/sys/item220tQgDj/form">220T卡车钳工点检分区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="item220tQgDj" action="${ctx}/sys/item220tQgDj/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部分</th>
				<th>检查内容</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:item220tQgDj:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item220tQgDj">
			<tr>
				<td><a href="${ctx}/sys/item220tQgDj/form?id=${item220tQgDj.id}">
					${fns:getDictLabel(item220tQgDj.part, 'item220tQgDj', '')}
				</a></td>
				<td>
					${item220tQgDj.checkContent}
				</td>
				<td>
					<fmt:formatDate value="${item220tQgDj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${item220tQgDj.remarks}
				</td>
				<shiro:hasPermission name="sys:item220tQgDj:edit"><td>
    				<a href="${ctx}/sys/item220tQgDj/form?id=${item220tQgDj.id}">修改</a>
					<a href="${ctx}/sys/item220tQgDj/delete?id=${item220tQgDj.id}" onclick="return confirmx('确认要删除该220T卡车钳工点检分区吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>