<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务内容表管理</title>
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
		<li class="active"><a href="${ctx}/sys/maskContent/">任务内容表列表</a></li>
		<shiro:hasPermission name="sys:maskContent:edit"><li><a href="${ctx}/sys/maskContent/form">任务内容表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="maskContent" action="${ctx}/sys/maskContent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>任务编号：</label>
				<form:input path="id" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>有无问题：</label>
				<form:select path="problem" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('have_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>任务编号</th>
				<th>有无问题</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:maskContent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="maskContent">
			<tr>
				<td><a href="${ctx}/sys/maskContent/form?id=${maskContent.id}">
					${maskContent.id}
				</a></td>
				<td>
					${fns:getDictLabel(maskContent.problem, 'have_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${maskContent.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${maskContent.remarks}
				</td>
				<shiro:hasPermission name="sys:maskContent:edit"><td>
    				<a href="${ctx}/sys/maskContent/form?id=${maskContent.id}">修改</a>
					<a href="${ctx}/sys/maskContent/delete?id=${maskContent.id}" onclick="return confirmx('确认要删除该任务内容表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>