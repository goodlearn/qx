<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>220T自卸卡车保养单（电气部分）管理</title>
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
		<li class="active"><a href="${ctx}/sys/item220tZxBy/">220T自卸卡车保养单（电气部分）列表</a></li>
		<shiro:hasPermission name="sys:item220tZxBy:edit"><li><a href="${ctx}/sys/item220tZxBy/form">220T自卸卡车保养单（电气部分）添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="item220tZxBy" action="${ctx}/sys/item220tZxBy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>序号</th>
				<th>检查项目</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:item220tZxBy:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item220tZxBy">
			<tr>
				<td><a href="${ctx}/sys/item220tZxBy/form?id=${item220tZxBy.id}">
					${fns:getDictLabel(item220tZxBy.part, 'item220tZxBy', '')}
				</a></td>
				<td>
					${item220tZxBy.number}
				</td>
				<td>
					${item220tZxBy.item}
				</td>
				<td>
					<fmt:formatDate value="${item220tZxBy.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${item220tZxBy.remarks}
				</td>
				<shiro:hasPermission name="sys:item220tZxBy:edit"><td>
    				<a href="${ctx}/sys/item220tZxBy/form?id=${item220tZxBy.id}">修改</a>
					<a href="${ctx}/sys/item220tZxBy/delete?id=${item220tZxBy.id}" onclick="return confirmx('确认要删除该220T自卸卡车保养单（电气部分）吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>