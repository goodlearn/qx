<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>SF31904卡车电工周点检卡（电气部分）管理</title>
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
		<li class="active"><a href="${ctx}/sys/itemSf31904KcDgDj/">SF31904卡车电工周点检卡（电气部分）列表</a></li>
		<shiro:hasPermission name="sys:itemSf31904KcDgDj:edit"><li><a href="${ctx}/sys/itemSf31904KcDgDj/form">SF31904卡车电工周点检卡（电气部分）添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="itemSf31904KcDgDj" action="${ctx}/sys/itemSf31904KcDgDj/" method="post" class="breadcrumb form-search">
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
				<th>检查标准</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:itemSf31904KcDgDj:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="itemSf31904KcDgDj">
			<tr>
				<td><a href="${ctx}/sys/itemSf31904KcDgDj/form?id=${itemSf31904KcDgDj.id}">
					${fns:getDictLabel(itemSf31904KcDgDj.part, 'itemSf31904KcDgDj', '')}
				</a></td>
				<td>
					${itemSf31904KcDgDj.number}
				</td>
				<td>
					${itemSf31904KcDgDj.item}
				</td>
				<td>
					${itemSf31904KcDgDj.checkStandard}
				</td>
				<td>
					<fmt:formatDate value="${itemSf31904KcDgDj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${itemSf31904KcDgDj.remarks}
				</td>
				<shiro:hasPermission name="sys:itemSf31904KcDgDj:edit"><td>
    				<a href="${ctx}/sys/itemSf31904KcDgDj/form?id=${itemSf31904KcDgDj.id}">修改</a>
					<a href="${ctx}/sys/itemSf31904KcDgDj/delete?id=${itemSf31904KcDgDj.id}" onclick="return confirmx('确认要删除该SF31904卡车电工周点检卡（电气部分）吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>