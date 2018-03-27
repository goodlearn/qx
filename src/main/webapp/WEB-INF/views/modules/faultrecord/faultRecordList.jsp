<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>故障记录表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/faultRecord/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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
		<li class="active"><a href="${ctx}/sys/faultRecord/">故障记录表列表</a></li>
		<shiro:hasPermission name="sys:faultRecord:edit"><li><a href="${ctx}/sys/faultRecord/form">故障记录表添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="faultRecord" action="${ctx}/sys/faultRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>部门：</label>
				<form:select path="workDepartmentId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllDpList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>工种：</label>
				<form:select path="workKindId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllWorkKindList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>班组：</label>
				<form:select path="workClassId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllClassList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
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
			<li><label>总负责人：</label>
				<form:select path="wpTotalId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllPersonList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>故障时间：</label>
				<input name="faultDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${faultRecord.faultDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>部门</th>
				<th>工种</th>
				<th>班组</th>
				<th>车型</th>
				<th>车号</th>
				<th>总负责人</th>
				<th>参与人员</th>
				<th>运行时间</th>
				<th>故障类型</th>
				<th>故障描述</th>
				<th>处理方法</th>
				<th>故障时间</th>
				<th>更新时间</th>
				<th>使用配件</th>
				<shiro:hasPermission name="sys:faultRecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="faultRecord">
			<tr>
				<td>
					${faultRecord.wd.name}
				</td>
				<td>
					${faultRecord.wk.name}
				</td>
				<td>
					${faultRecord.wc.name}
				</td>
				<td>
					${faultRecord.cmc.name}
				</td>
				<td>
					${faultRecord.cw.name}
				</td>
				<td>
					${faultRecord.wpTotal.name}
				</td>
				<td>
					${faultRecord.wpPartId}
				</td>
				<td>
					${faultRecord.runTime}
				</td>
				<td>
					${fns:getDictLabel(faultRecord.type, 'frType', '')}
				</td>
				<td>
					${faultRecord.description}
				</td>
				<td>
					${faultRecord.dealMethod}
				</td>
				<td>
					<fmt:formatDate value="${faultRecord.faultDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${faultRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${faultRecord.userTool}
				</td>
				<shiro:hasPermission name="sys:faultRecord:edit"><td>
    				<a href="${ctx}/sys/faultRecord/form?id=${faultRecord.id}">修改</a>
					<a href="${ctx}/sys/faultRecord/delete?id=${faultRecord.id}" onclick="return confirmx('确认要删除该故障记录表吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>