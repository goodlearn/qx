<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>车间任务班级关联数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/item108t2000hBy/export");
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
	<form:form id="searchForm" modelAttribute="wsMaskWc" action="${ctx}/sys/wsMaskWc/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建日期：</label>
				<input name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${assist.beginApplydate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
			</li>
			<li class="btns"><input id="btnExport" class="btn btn-primary" type="button" value="导出"/></li>
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