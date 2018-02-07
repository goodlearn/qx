<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>钳工周检点检卡管理</title>
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
		<li class="active"><a href="${ctx}/sys/fitterCheckSpotItem1/">钳工周检点检卡列表</a></li>
		<shiro:hasPermission name="sys:fitterCheckSpotItem1:edit"><li><a href="${ctx}/sys/fitterCheckSpotItem1/form">钳工周检点检卡添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>点检项目</th>
				<th>检查内容</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:fitterCheckSpotItem1:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fitterCheckSpotItem1">
			<tr>
				<td><a href="${ctx}/sys/fitterCheckSpotItem1/form?id=${fitterCheckSpotItem1.id}">
					${fitterCheckSpotItem1.number}</a>
				</td>
				<td>
					${fns:getDictLabel(fitterCheckSpotItem1.part, 'fitterCsItem1', '')}
				</td>
				<td>
					${fitterCheckSpotItem1.content}
				</td>
				<td>
					<fmt:formatDate value="${fitterCheckSpotItem1.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${fitterCheckSpotItem1.remarks}
				</td>
				<shiro:hasPermission name="sys:fitterCheckSpotItem1:edit"><td>
    				<a href="${ctx}/sys/fitterCheckSpotItem1/form?id=${fitterCheckSpotItem1.id}">修改</a>
					<a href="${ctx}/sys/fitterCheckSpotItem1/delete?id=${fitterCheckSpotItem1.id}" onclick="return confirmx('确认要删除该钳工周检点检卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>