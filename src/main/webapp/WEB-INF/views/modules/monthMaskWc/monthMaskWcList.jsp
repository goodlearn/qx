<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班组任务月度表管理</title>
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
		<li class="active"><a href="${ctx}/sys/monthMaskWc/">班组任务月度表列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务类型</th>
				<th>任务说明</th>
				<th>责任人</th>
				<th>任务结束时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:monthMaskWc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthMaskWc">
			<tr>
				<td>
					${fns:getDictLabel(monthMaskWc.mmws.type, 'monthMaskType', '')}
				</td>
				<td>
					${monthMaskWc.mmws.maskDesc}
				</td>
				<td>
					${monthMaskWc.wp.name}
				</td>
				<td>
					<fmt:formatDate value="${monthMaskWc.mmws.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMaskWc.remarks}
				</td>
				<shiro:hasPermission name="sys:monthMaskWc:edit"><td>
    				<a href="${ctx}/sys/monthMaskWc/form?id=${monthMaskWc.id}">修改</a>
					<a href="${ctx}/sys/monthMaskWc/delete?id=${monthMaskWc.id}" onclick="return confirmx('确认要删除该班组任务月度表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>