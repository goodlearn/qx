<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间任务数据管理</title>
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
		<li class="active"><a href="${ctx}/sys/workShopMask/">车间任务数据列表</a></li>
		<shiro:hasPermission name="sys:workShopMask:edit"><li><a href="${ctx}/sys/workShopMask/form">车间任务数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="workShopMask" action="${ctx}/sys/workShopMask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>车间号：</label>
				<form:select path="workShopId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllWorkShopList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
	 <c:if test="${not empty repeatMessage}">
       <font color="red"> ${repeatMessage}</font>            
     </c:if>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>车型</th>
				<th>车间号</th>
				<th>部门号</th>
				<th>班级号</th>
				<th>业务集</th>
				<th>是否发布</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:workShopMask:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="workShopMask">
			<tr>
				<td><a href="${ctx}/sys/workShopMask/detalils?id=${workShopMask.id}">
					${workShopMask.name}
				</a></td>
				<td>
					${workShopMask.cmc.name}
				</td>
				<td>
					${workShopMask.ws.name}
				</td>
				<td>
					${workShopMask.wd.name}
				</td>
				<td>
					${workShopMask.wc.name}
				</td>
				<td>
					${workShopMask.ba.name}
				</td>
				<td>
					${fns:getDictLabel(workShopMask.releaseState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${workShopMask.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${workShopMask.remarks}
				</td>
				<shiro:hasPermission name="sys:workShopMask:edit"><td>
    				<a href="${ctx}/sys/workShopMask/allocation?wsmid=${workShopMask.id}">分配任务</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>