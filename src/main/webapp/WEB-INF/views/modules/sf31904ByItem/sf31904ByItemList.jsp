<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>SF31904卡车保养单（电气部分）管理</title>
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
		<li class="active"><a href="${ctx}/sys/sf31904ByItem/">SF31904卡车保养单（电气部分）列表</a></li>
		<shiro:hasPermission name="sys:sf31904ByItem:edit"><li><a href="${ctx}/sys/sf31904ByItem/form">SF31904卡车保养单（电气部分）添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sf31904ByItem" action="${ctx}/sys/sf31904ByItem/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="sys:sf31904ByItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sf31904ByItem">
			<tr>
				<td><a href="${ctx}/sys/sf31904ByItem/form?id=${sf31904ByItem.id}">
					${fns:getDictLabel(sf31904ByItem.part, 'sf31904ByItem', '')}
				</a></td>
				<td>
					${sf31904ByItem.number}
				</td>
				<td>
					${sf31904ByItem.item}
				</td>
				<td>
					<fmt:formatDate value="${sf31904ByItem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sf31904ByItem.remarks}
				</td>
				<shiro:hasPermission name="sys:sf31904ByItem:edit"><td>
    				<a href="${ctx}/sys/sf31904ByItem/form?id=${sf31904ByItem.id}">修改</a>
					<a href="${ctx}/sys/sf31904ByItem/delete?id=${sf31904ByItem.id}" onclick="return confirmx('确认要删除该SF31904卡车保养单（电气部分）吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>