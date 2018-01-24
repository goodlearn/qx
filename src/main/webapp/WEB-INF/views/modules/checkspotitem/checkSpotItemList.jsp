<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>点检项数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/checkSpotItem/">点检项数据列表</a></li>
		<shiro:hasPermission name="sys:checkSpotItem:edit"><li><a href="${ctx}/sys/checkSpotItem/form">点检项数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="checkSpotItem" action="${ctx}/sys/checkSpotItem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属业务集：</label>
				<form:select path="assembleId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getBaList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>项目：</label>
				<form:select path="item" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussinesItem')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>部位：</label>
				<form:select path="part" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussinesPart')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>名称</th>
				<th>所属业务集</th>
				<th>项目</th>
				<th>部位</th>
				<th>结果集</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:checkSpotItem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="checkSpotItem">
			<tr>
				<td><a href="${ctx}/sys/checkSpotItem/form?id=${checkSpotItem.id}">
					${checkSpotItem.name}
				</a></td>
				<td>
					${checkSpotItem.ba.name}
				</td>
				<td>
					${fns:getDictLabel(checkSpotItem.item, 'bussinesItem', '')}
				</td>
				<td>
					${fns:getDictLabel(checkSpotItem.part, 'bussinesPart', '')}
				</td>
				<td>
					<a href="${ctx}/sys/businessResultItem/listbra?assembleId=${checkSpotItem.bra.id}">
					${checkSpotItem.bra.name}
					</a>
				</td>
				<td>
					<fmt:formatDate value="${checkSpotItem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${checkSpotItem.remarks}
				</td>
				<shiro:hasPermission name="sys:checkSpotItem:edit"><td>
    				<a href="${ctx}/sys/checkSpotItem/form?id=${checkSpotItem.id}">修改</a>
					<a href="${ctx}/sys/checkSpotItem/delete?id=${checkSpotItem.id}" onclick="return confirmx('确认要删除该点检项数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>