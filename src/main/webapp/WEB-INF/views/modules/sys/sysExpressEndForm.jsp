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
		
		function checkBatchEnd(ids, formname) {
	        var flag = false;
	        for (i = 0; i < ids.length; i++) {
	            if (ids[i].checked) {
	                flag = true;
	                break;
	            }
	        }
	        if (!flag) {
	            alert("请选择要取货的快递！");
	            return false;
	        } else {
	            if (confirm("确定要取货吗？")) {
	                formname.submit();
	            }
	        }
	    }
		
		function CheckAll(elementsA, elementsB) {
	        for (i = 0; i < elementsA.length; i++) {
	            elementsA[i].checked = true;
	        }
	        if (elementsB.checked == false) {
	            for (j = 0; j < elementsA.length; j++) {
	                elementsA[j].checked = false;
	            }
	        }
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
		<ul class="ul-form" style="padding:10px 0px;">
			<li>
				<c:choose> 
				   <c:when test="${not empty searchValue}"> 
				      <input name="searchValue" type="text" value="${searchValue}" style="width:500px; font-weight:bold;  height:30px; margin:0;padding:0 0 0 5px;text-align:left;">
				   </c:when> 
				   <c:otherwise>  
				   	  <input name="searchValue" type="text" placeholder=
				   	  "身份证号/手机号/快递单号" value="" style="width:500px; font-weight:bold; height:30px; margin:0;padding:0 0 0 5px;text-align:left;">
				   </c:otherwise> 
				</c:choose>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<form:form id="batchForm" name="batchForm" modelAttribute="sysExpressIds" action="${ctx}/sys/sysExpress/endBatchForm" method="post" class="breadcrumb form-search">
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>取货</th>
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
					<td><input name="sysExpressIds" type="checkbox" value="${sysExpress.id}"></td>
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
						<a href="${ctx}/sys/sysExpress/endExpress?id=${sysExpress.id}" onclick="return confirmx('确认要取货吗？', this.href)">取货</a>
					</td></shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<li class="btns">
			<input name="checkbox" type="checkbox" 
                onClick="CheckAll(batchForm.sysExpressIds,batchForm.checkbox)"> [全选/反选] [
                <a style="color:red;cursor:pointer;" onClick="checkBatchEnd(batchForm.sysExpressIds,batchForm)">批量取货</a>]
            <div id="ch" style="display: none">
                <input name="delid" type="checkbox" value="0">
            </div>
		</li>
	</form:form>
	<div class="pagination">${page}</div>
</body>
</html>