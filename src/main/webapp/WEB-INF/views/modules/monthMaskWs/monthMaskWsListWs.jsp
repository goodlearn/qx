<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月度计划车间任务管理</title>
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
		<li class="active"><a href="${ctx}/sys/monthMaskWs/">月度计划车间任务列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>责任工种</th>
				<th>结束时间</th>
				<th>任务说明</th>
				<th>任务类型</th>
				<th>是否发布</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthMaskWs">
			<tr>
				<td><a href="${ctx}/sys/monthMaskWs/form?id=${monthMaskWs.id}">
					${monthMaskWs.workKind.name}
				</a></td>
				<td>
					<fmt:formatDate value="${monthMaskWs.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMaskWs.maskDesc}
				</td>
				<td>
					${fns:getDictLabel(monthMaskWs.type, 'monthMaskType', '')}
				</td>
				<td>
					${fns:getDictLabel(monthMaskWs.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${monthMaskWs.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMaskWs.remarks}
				</td>
				<td>
					<a href="${ctx}/sys/monthMaskWc/allocation?mmwsId=${monthMaskWs.id}">
						分配
					</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>