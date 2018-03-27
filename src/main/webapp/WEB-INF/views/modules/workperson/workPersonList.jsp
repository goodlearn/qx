<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间人员信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/workPerson/">车间人员信息列表</a></li>
		<shiro:hasPermission name="sys:workPerson:edit"><li><a href="${ctx}/sys/workPerson/form">车间人员信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workPerson" action="${ctx}/sys/workPerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>编号：</label>
				<form:input path="no" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属班组：</label>
				<form:select path="workClassId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllClassList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>级别：</label>
				<form:select path="level" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('workPersonLevel')}" itemLabel="label" itemValue="value" htmlEscape="false"/>				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>姓名</th>
				<th>编号</th>
				<th>所属部门</th>
				<th>所属班组</th>
				<th>级别</th>
				<th>是否绑定微信</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workPerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workPerson">
			<tr>
				<td><a href="${ctx}/sys/workPerson/form?id=${workPerson.id}">
					${workPerson.name}</a>
				</td>
				<td>
					${workPerson.no}
				</td>
				<td>
					${workPerson.wd.name}
				</td>
				<td>
					${workPerson.workClass.name}
				</td>
				<td>
					${fns:getDictLabel(workPerson.level, 'workPersonLevel', '')}
				</td>
				<td>
					${workPerson.tieWx}
				</td>
				<td>
					<fmt:formatDate value="${workPerson.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workPerson.remarks}
				</td>
				<shiro:hasPermission name="sys:workPerson:edit"><td>
    				<a href="${ctx}/sys/workPerson/updateForm?id=${workPerson.id}">修改</a>
					<a href="${ctx}/sys/workPerson/delete?id=${workPerson.id}" onclick="return confirmx('确认要删除该车间人员信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>