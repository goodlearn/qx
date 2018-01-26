<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点检卡内容表数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/spotCheckContent/">点检卡内容表数据列表</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>内容</th>
				<th>是否新内容</th>
				<th>检查结果</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:sc:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${scccList}" var="scsp">
				<tr>
					<td>
						${fns:getDictLabel(scsp.part, 'bussinesPart', '')}
					</td>
					<td>
						${scsp.context}
					</td>
					<td>
						${fns:getDictLabel(scsp.oldNew, 'yes_no', '')}
					</td>
					<td>
						${scsp.resultContent}
					</td>
					<td>
						<fmt:formatDate value="${scsp.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
						${scsp.remarks}
					</td>
					<shiro:hasPermission name="sys:sc:edit"><td>
    					<a href="${ctx}/sys/sc/addFormScc?scspId=${scsp.scspId}">添加新项</a>
				</td></shiro:hasPermission>
				</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>