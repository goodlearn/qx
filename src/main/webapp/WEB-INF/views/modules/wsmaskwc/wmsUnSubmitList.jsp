<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间任务班级关联数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/wsMaskWc/">车间任务班级关联数据列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车间任务号</th>
				<th>班级号</th>
				<th>提交状态</th>
				<th>结束时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:wsMaskWc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="wsMaskWc">
			<tr>
				<td><a href="${ctx}/sys/wsMaskWc/form?id=${wsMaskWc.id}">
					${wsMaskWc.wsm.name}
				</a></td>
				<td>
					${wsMaskWc.wc.name}
				</td>
				<td>
					${fns:getDictLabel(wsMaskWc.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${wsMaskWc.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${wsMaskWc.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${wsMaskWc.remarks}
				</td>
				<shiro:hasPermission name="sys:wsMaskWc:edit"><td>
    				<a href="${ctx}/sys/wsMaskWc/endMask?id=${wsMaskWc.id}">强制结束</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>