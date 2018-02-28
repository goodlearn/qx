<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>汽修二班MT4400保养责任分区管理</title>
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
		<li class="active"><a href="${ctx}/sys/itemQx2bMt4400Dj/">汽修二班MT4400保养责任分区列表</a></li>
		<shiro:hasPermission name="sys:itemQx2bMt4400Dj:edit"><li><a href="${ctx}/sys/itemQx2bMt4400Dj/form">汽修二班MT4400保养责任分区添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="itemQx2bMt4400Dj" action="${ctx}/sys/itemQx2bMt4400Dj/" method="post" class="breadcrumb form-search">
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
				<shiro:hasPermission name="sys:itemQx2bMt4400Dj:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="itemQx2bMt4400Dj">
			<tr>
				<td><a href="${ctx}/sys/itemQx2bMt4400Dj/form?id=${itemQx2bMt4400Dj.id}">
					${fns:getDictLabel(itemQx2bMt4400Dj.part, 'itemQx2bMt4400Dj', '')}
				</a></td>
				<td>
					${itemQx2bMt4400Dj.number}
				</td>
				<td>
					${itemQx2bMt4400Dj.checkContent}
				</td>
				<td>
					<fmt:formatDate value="${itemQx2bMt4400Dj.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${itemQx2bMt4400Dj.remarks}
				</td>
				<shiro:hasPermission name="sys:itemQx2bMt4400Dj:edit"><td>
    				<a href="${ctx}/sys/itemQx2bMt4400Dj/form?id=${itemQx2bMt4400Dj.id}">修改</a>
					<a href="${ctx}/sys/itemQx2bMt4400Dj/delete?id=${itemQx2bMt4400Dj.id}" onclick="return confirmx('确认要删除该汽修二班MT4400保养责任分区吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>