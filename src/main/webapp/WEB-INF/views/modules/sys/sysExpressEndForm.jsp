<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>取货管理</title>
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
		<li class="active">快递列表</li>
	</ul>
	<form:form id="searchForm" modelAttribute="sysExpress" action="${ctx}/sys/sysExpress/endFormList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<c:choose> 
				   <c:when test="${not empty searchValue}"> 
				      <input name="searchValue" type="text" value="${searchValue}" style="width:500px;margin:0;padding:0;text-align:center;">
				   </c:when> 
				   <c:otherwise>  
				   	  <input name="searchValue" type="text" value="身份证号/手机号/快递单号" style="width:500px;margin:0;padding:0;text-align:center;">
				   </c:otherwise> 
				</c:choose>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>快递单号</th>
				<th>手机</th>
				<th>快递状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th>上次操作人</th>
				<th>上次操作时间</th>
				<shiro:hasPermission name="sys:sysExpress:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysExpress">
			<tr>
				<td><a href="${ctx}/sys/sysExpress/detailForm?id=${sysExpress.id}">
					${sysExpress.expressId}
				</a></td>
				<td>
					${sysExpress.phone}
				</td>
				<td>
					${fns:getDictLabel(sysExpress.state, 'expressState', '0')}
				</td>
				<td>
					${sysExpress.createBy.name}
				</td>
				<td>
					<fmt:formatDate value="${sysExpress.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysExpress.updateBy.name}
				</td>
				<td>
					<fmt:formatDate value="${sysExpress.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:sysExpress:edit"><td>
    				<a href="${ctx}/sys/sysExpress/editForm?id=${sysExpress.id}">修改</a>
					<a href="${ctx}/sys/sysExpress/delete?id=${sysExpress.id}" onclick="return confirmx('确认要删除该快递表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>