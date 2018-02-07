<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发动机点检单一管理</title>
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
		<li class="active"><a href="${ctx}/sys/motorCheckSpotItem1/">发动机点检单一列表</a></li>
		<shiro:hasPermission name="sys:motorCheckSpotItem1:edit"><li><a href="${ctx}/sys/motorCheckSpotItem1/form">发动机点检单一添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>序号</th>
				<th>项目</th>
				<th>标准</th>
				<th>工具</th>
				<th>人数</th>
				<th>所属业务集</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:motorCheckSpotItem1:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="motorCheckSpotItem1">
			<tr>
				<td><a href="${ctx}/sys/motorCheckSpotItem1/form?id=${motorCheckSpotItem1.id}">
					${fns:getDictLabel(motorCheckSpotItem1.part, 'motorCsItem1', '')}</a>
				</td>
				<td>
					${motorCheckSpotItem1.number}
				</td>
				<td>
					${motorCheckSpotItem1.item}
				</td>
				<td>
					${motorCheckSpotItem1.standard}
				</td>
				<td>
					${motorCheckSpotItem1.tool}
				</td>
				<td>
					${motorCheckSpotItem1.person}
				</td>
				<td>
					${motorCheckSpotItem1.ba.name}
				</td>
				<td>
					<fmt:formatDate value="${motorCheckSpotItem1.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${motorCheckSpotItem1.remarks}
				</td>
				<shiro:hasPermission name="sys:motorCheckSpotItem1:edit"><td>
    				<a href="${ctx}/sys/motorCheckSpotItem1/form?id=${motorCheckSpotItem1.id}">修改</a>
					<a href="${ctx}/sys/motorCheckSpotItem1/delete?id=${motorCheckSpotItem1.id}" onclick="return confirmx('确认要删除该发动机点检单一吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>