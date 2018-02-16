<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>SF31904C卡车点检卡管理</title>
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
		<li class="active"><a href="${ctx}/sys/sf31904cCsItem/">SF31904C卡车点检卡列表</a></li>
		<shiro:hasPermission name="sys:sf31904cCsItem:edit"><li><a href="${ctx}/sys/sf31904cCsItem/form">SF31904C卡车点检卡添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="item220tZxBy" action="${ctx}/sys/sf31904cCsItem/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="sys:sf31904cCsItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sf31904cCsItem">
			<tr>
				<td><a href="${ctx}/sys/sf31904cCsItem/form?id=${sf31904cCsItem.id}">
					${fns:getDictLabel(sf31904cCsItem.part, 'sf31904cCsItem', '')}
				</a></td>
				<td>
					${sf31904cCsItem.number}
				</td>
				<td>
					${sf31904cCsItem.item}
				</td>
				<td>
					<fmt:formatDate value="${sf31904cCsItem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sf31904cCsItem.remarks}
				</td>
				<shiro:hasPermission name="sys:sf31904cCsItem:edit"><td>
    				<a href="${ctx}/sys/sf31904cCsItem/form?id=${sf31904cCsItem.id}">修改</a>
					<a href="${ctx}/sys/sf31904cCsItem/delete?id=${sf31904cCsItem.id}" onclick="return confirmx('确认要删除该SF31904C卡车点检卡吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>