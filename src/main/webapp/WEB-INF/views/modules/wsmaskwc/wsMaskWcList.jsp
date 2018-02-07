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
		<shiro:hasPermission name="sys:wsMaskWc:edit"><li><a href="${ctx}/sys/wsMaskWc/form">车间任务班级关联数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="wsMaskWc" action="${ctx}/sys/wsMaskWc/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>车间任务号：</label>
				<form:select path="workShopMaskId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getReleaseWsmList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>班级号：</label>
				<form:select path="workClassId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllClassList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>车间任务号</th>
				<th>班级号</th>
				<th>提交状态</th>
				<th>运行时间</th>
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
					${wsMaskWc.runTime}
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
					<a href="${ctx}/sys/maskDispatch/maskDispatch?maskId=${wsMaskWc.id}">分配</a>
    				<a href="${ctx}/sys/wsMaskWc/form?id=${wsMaskWc.id}">修改</a>
					<a href="${ctx}/sys/wsMaskWc/delete?id=${wsMaskWc.id}" onclick="return confirmx('确认要删除该车间任务班级关联数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>