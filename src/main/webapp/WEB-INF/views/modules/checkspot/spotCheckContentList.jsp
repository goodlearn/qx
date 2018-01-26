<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点检卡内容表数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/spotCheckContent/">点检卡内容表数据列表</a></li>
		<shiro:hasPermission name="sys:spotCheckContent:edit"><li><a href="${ctx}/sys/spotCheckContent/form">点检卡内容表数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="spotCheckContent" action="${ctx}/sys/spotCheckContent/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>父任务号：</label>
				<form:input path="scspId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>部位：</label>
				<form:select path="part" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussinesPart')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>新旧内容：</label>
				<form:select path="oldNew" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>父任务号</th>
				<th>新旧内容</th>
				<th>部位</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:spotCheckContent:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="spotCheckContent">
			<tr>
				<td><a href="${ctx}/sys/spotCheckContent/form?id=${spotCheckContent.id}">
					${spotCheckContent.scspId}
				</a></td>
				<td>
					${fns:getDictLabel(spotCheckContent.oldNew, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(spotCheckContent.part, 'bussinesPart', '')}
				</td>
				<td>
					<fmt:formatDate value="${spotCheckContent.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${spotCheckContent.remarks}
				</td>
				<shiro:hasPermission name="sys:spotCheckContent:edit"><td>
    				<a href="${ctx}/sys/spotCheckContent/form?id=${spotCheckContent.id}">修改</a>
					<a href="${ctx}/sys/spotCheckContent/delete?id=${spotCheckContent.id}" onclick="return confirmx('确认要删除该点检卡内容表数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>