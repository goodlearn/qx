<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月度任务表管理</title>
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
		<li class="active"><a href="${ctx}/sys/monthMask/">月度任务表列表</a></li>
		<shiro:hasPermission name="sys:monthMask:edit"><li><a href="${ctx}/sys/monthMask/form">月度任务表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="monthMask" action="${ctx}/sys/monthMask/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>责任人：</label>
				<form:select path="workPersonId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${wps}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>车型：</label>
				<form:select path="carMotorCycleId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllCmcList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>车号：</label>
				<form:select path="carWagonId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getCwList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>任务说明：</label>
				<form:input path="mmws.maskDesc" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>任务类型：</label>
				<form:select path="mmws.type" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('monthMaskType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>结束时间：</label>
				<input name="mmws.endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${monthMask.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>班组任务</th>
				<th>责任人</th>
				<th>车型</th>
				<th>车号</th>
				<th>运行时间数</th>
				<th>情况说明</th>
				<th>检查时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:monthMask:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthMask">
			<tr>
				<td>
					${monthMask.mmws.maskDesc}
				</td>
				<td>
					${monthMask.wp.name}
				</td>
				<td>
					${monthMask.cmc.name}
				</td>
				<td>
					${monthMask.cw.name}
				</td>
				<td>
					${monthMask.runTime}
				</td>
				<td>
					${monthMask.checkResult}
				</td>
				<td>
					<fmt:formatDate value="${monthMask.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${monthMask.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMask.remarks}
				</td>
				<shiro:hasPermission name="sys:monthMask:edit"><td>
    				<a href="${ctx}/sys/monthMask/form?id=${monthMask.id}">修改</a>
					<a href="${ctx}/sys/monthMask/delete?id=${monthMask.id}" onclick="return confirmx('确认要删除该月度任务表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>