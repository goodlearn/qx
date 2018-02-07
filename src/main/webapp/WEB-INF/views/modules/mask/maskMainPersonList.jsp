<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>总负责人任务表管理</title>
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
		<li class="active"><a href="${ctx}/sys/maskMainPerson/">总负责人任务表列表</a></li>
		<shiro:hasPermission name="sys:maskMainPerson:edit"><li><a href="${ctx}/sys/maskMainPerson/form">总负责人任务表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="maskMainPerson" action="${ctx}/sys/maskMainPerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>个人号：</label>
				<form:select path="workPersonId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllPersonList()}" itemLabel="id" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>提交状态：</label>
				<form:select path="submitState" class="input-medium">
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
				<th>所属任务</th>
				<th>总负责人</th>
				<th>运行时间数</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:maskMainPerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="maskMainPerson">
			<tr>
				<td><a href="${ctx}/sys/maskMainPerson/form?id=${maskMainPerson.id}">
					${maskMainPerson.wmw.id}
				</a></td>
				<td>
					${maskMainPerson.wp.name}
				</td>
				<td>
					${maskMainPerson.runTime}
				</td>
				<td>
					${fns:getDictLabel(maskMainPerson.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${maskMainPerson.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${maskMainPerson.remarks}
				</td>
				<shiro:hasPermission name="sys:maskMainPerson:edit"><td>
    				<a href="${ctx}/sys/maskMainPerson/form?id=${maskMainPerson.id}">修改</a>
					<a href="${ctx}/sys/maskMainPerson/delete?id=${maskMainPerson.id}" onclick="return confirmx('确认要删除该总负责人任务表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>