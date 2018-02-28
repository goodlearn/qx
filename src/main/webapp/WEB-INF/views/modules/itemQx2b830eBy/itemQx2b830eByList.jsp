<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汽修二班830E保养责任分区管理</title>
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
		<li class="active"><a href="${ctx}/sys/itemQx2b830eBy/">汽修二班830E保养责任分区列表</a></li>
		<shiro:hasPermission name="sys:itemQx2b830eBy:edit"><li><a href="${ctx}/sys/itemQx2b830eBy/form">汽修二班830E保养责任分区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="itemQx2b830eBy" action="${ctx}/sys/itemQx2b830eBy/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部位</th>
				<th>序号</th>
				<th>检查内容</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:itemQx2b830eBy:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="itemQx2b830eBy">
			<tr>
				<td><a href="${ctx}/sys/itemQx2b830eBy/form?id=${itemQx2b830eBy.id}">
					${fns:getDictLabel(itemQx2b830eBy.part, 'itemQx2b830eBy', '')}
				</a></td>
				<td>
					${itemQx2b830eBy.number}
				</td>
				<td>
					${itemQx2b830eBy.checkContent}
				</td>
				<td>
					<fmt:formatDate value="${itemQx2b830eBy.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${itemQx2b830eBy.remarks}
				</td>
				<shiro:hasPermission name="sys:itemQx2b830eBy:edit"><td>
    				<a href="${ctx}/sys/itemQx2b830eBy/form?id=${itemQx2b830eBy.id}">修改</a>
					<a href="${ctx}/sys/itemQx2b830eBy/delete?id=${itemQx2b830eBy.id}" onclick="return confirmx('确认要删除该汽修二班830E保养责任分区吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>