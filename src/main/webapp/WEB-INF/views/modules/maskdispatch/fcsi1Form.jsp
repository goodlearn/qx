<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>发动机点检单任务分配</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/wsMaskWc/wsmlist">车间任务数据</a></li>
	</ul>
		<input id="PageContext" type="hidden" value="${ctx}" />
		<input id="maskId" type="hidden" value="${maskId}" />
		<table class="table table-striped table-bordered" style="width:70%;" id="mssiTable">
			<thead>
				<tr>
					<th>点检项目</th>
					<th>分配人员</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fcsis}" var="var" varStatus="status">
					<tr>
						<td title="${var.value}">${var.label}</td>
						<td>
							<select style="width:200px;">
								<option value="0" selected>请选择人员...</option>
								<c:forEach items="${wp}" var="wpvar">
									<option value="${wpvar.no}">${wpvar.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	<button id="mcsiSubmit">确认提交</button>
	
<script type="text/javascript">
 $(function(){
	$("#mcsiSubmit").click(function(){
		var pageContextVal = $("#PageContext").val();
		var maskId = $("#maskId").val();
		var mcsijson = {
				"viewMcsi1":[]
		};
		var trArr = $("#mssiTable tbody tr");
		
		for(var i=0; i<trArr.length; i++){
			var empno = trArr.eq(i).children("td:nth-child(2)").find("option:selected").val();
			if(empno == 0) {
				alert("还有未分配人员的项目...");
				return false;
			}
		}
		
		for(var i=0; i<trArr.length; i++){  
			var empname = trArr.eq(i).children("td:nth-child(1)").attr("title");
			var empno = trArr.eq(i).children("td:nth-child(2)").find("option:selected").val();
			var empd = {"name":empname,"empno":empno,"maskId":maskId};
			mcsijson.viewMcsi1.push(empd);
		}
		
		 $.ajax({
		     type:'POST',
		     url:pageContextVal+'/sys/fitterCheckSpotItem1/allocation',
		     data:JSON.stringify(mcsijson.viewMcsi1),
			 dataType: "json",
			 contentType:"application/json",
		     success:function(data){
		    	 alert(data.message);
		     },
		     error:function(){
		    	 alert("未知失败");
		     }
		 });
	});
 });
</script>
</body>
</html>