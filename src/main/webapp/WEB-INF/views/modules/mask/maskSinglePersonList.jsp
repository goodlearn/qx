<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人负责人任务表管理</title>
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
		<li class="active"><a href="${ctx}/sys/maskSinglePerson/">个人负责人任务表列表</a></li>
		<shiro:hasPermission name="sys:maskSinglePerson:edit"><li><a href="${ctx}/sys/maskSinglePerson/form">个人负责人任务表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="maskSinglePerson" action="${ctx}/sys/maskSinglePerson/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>个人号：</label>
				<form:select path="workPersonId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllPersonList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
				<th>个人名字</th>
				<th>负责部位</th>
				<th>提交状态</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:maskSinglePerson:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="maskSinglePerson">
			<tr>
				<td><a href="${ctx}/sys/maskSinglePerson/form?id=${maskSinglePerson.id}">
					${maskSinglePerson.mmp.id}
				</a></td>
				<td>
					${maskSinglePerson.wp.name}
				</td>
				<td>
					${fns:getPartDictValue(maskSinglePerson.part, maskSinglePerson.id, '')}
				</td>
				<td>
					${fns:getDictLabel(maskSinglePerson.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${maskSinglePerson.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${maskSinglePerson.remarks}
				</td>
				<shiro:hasPermission name="sys:maskSinglePerson:edit"><td>
    				<a href="${ctx}/sys/maskSinglePerson/form?id=${maskSinglePerson.id}">修改</a>
					<a href="${ctx}/sys/maskSinglePerson/delete?id=${maskSinglePerson.id}" onclick="return confirmx('确认要删除该个人负责人任务表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>