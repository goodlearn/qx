<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>月度计划车间任务管理</title>
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
		
		function checkBatchEnd(formname) {
			var checkboxAll = $("input[name='ids']");
	        var flag = false;
	        for (i = 0; i < checkboxAll.length; i++) {
	            if (checkboxAll[i].checked) {
	                flag = true;
	                break;
	            }
	        }
	        if (!flag) {
	            alert("请选择要发布的任务！");
	            return false;
	        } else {
	            if (confirm("确定要发布吗？")) {
	                formname.submit();
	            }
	        }
	    }
		
		function CheckAll() {
			var checkboxAll = $("input[name='ids']");
			if($("#selectAll").is(":checked")){
				for (i = 0; i < checkboxAll.length; i++) {
					checkboxAll[i].checked = true;
		        }
			} else {
				for (j = 0; j < checkboxAll.length; j++) {
					checkboxAll[j].checked = false;
	           }
			}
		
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/monthMaskWs/">月度计划车间任务列表</a></li>
		<shiro:hasPermission name="sys:monthMaskWs:edit"><li><a href="${ctx}/sys/monthMaskWs/form">月度计划车间任务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="monthMaskWs" action="${ctx}/sys/monthMaskWs/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>责任工种：</label>
				<form:select path="workKindId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllwk()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>结束时间：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${monthMaskWs.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
			<li><label>任务类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('monthMaskType')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<form:form id="batchForm" name="batchForm" action="${ctx}/sys/monthMaskWs//endBatchForm" method="post" class="breadcrumb form-search">
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>发布</th>
				<th>责任工种</th>
				<th>结束时间</th>
				<th>任务说明</th>
				<th>任务类型</th>
				<th>是否发布</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:monthMaskWs:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="monthMaskWs">
			<tr>
				<td><input id="ids" name="ids" type="checkbox" value="${monthMaskWs.id}"></td>
				<td><a href="${ctx}/sys/monthMaskWs/form?id=${monthMaskWs.id}">
					${monthMaskWs.workKind.name}
				</a></td>
				<td>
					<fmt:formatDate value="${monthMaskWs.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMaskWs.maskDesc}
				</td>
				<td>
					${fns:getDictLabel(monthMaskWs.type, 'monthMaskType', '')}
				</td>
				<td>
					${fns:getDictLabel(monthMaskWs.submitState, 'yes_no', '')}
				</td>
				<td>
					<fmt:formatDate value="${monthMaskWs.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${monthMaskWs.remarks}
				</td>
				<shiro:hasPermission name="sys:monthMaskWs:edit"><td>
					<a href="${ctx}/sys/monthMaskWs/release?id=${monthMaskWs.id}">发布</a>
    				<a href="${ctx}/sys/monthMaskWs/form?id=${monthMaskWs.id}">修改</a>
					<a href="${ctx}/sys/monthMaskWs/delete?id=${monthMaskWs.id}" onclick="return confirmx('确认要删除该月度计划车间任务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<li class="btns">
		<input id="selectAll" name="selectAllBtn" onClick="CheckAll()" type="checkbox">全选/反选
           <div class="funcBtn" onClick="checkBatchEnd(batchForm)">批量取货</div>
	</li>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>