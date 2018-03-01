<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>任务数据</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/wsMaskWc/">任务数据</a></li>
	</ul><br/>
	<form:form class="form-horizontal">		
		<sys:message content="${message}"/>		
			<c:if test = "${not empty wsMaskWc }">
				<fieldset>
						<legend>任务信息</legend>
						<table class="table-form">
							<tr>
								<td class="tit">任务名称</td>
								<td colspan="2">${wsMaskWc.wsm.name}</td>
								<td class="tit">表格名称</td>
								<td colspan="2">${wsMaskWc.wsm.ba.name}</td>
								<td class="tit">车间</td>
								<td colspan="1">${wsMaskWc.wsm.ws.name}</td>
								<td class="tit">部门</td>
								<td colspan="1">${wsMaskWc.wsm.wd.name}</td>
							</tr>
							<tr>
								<td class="tit">班级</td>
								<td colspan="2">${wsMaskWc.wsm.wc.name}</td>
								<td class="tit">车型</td>
								<td colspan="2">${wsMaskWc.wsm.cmc.name}</td>
								<td class="tit">车号</td>
								<td colspan="4">${wsMaskWc.wsm.cw.name}</td>
							</tr>
						</table>
						<legend>表格信息</legend>
						<table class="table-form">
							<c:forEach items="${wsMaskWc.mmpList}" var="mmp" varStatus="status">
								<tr>
									<td class="tit">总负责人</td>
									<td colspan="2">${mmp.wp.name}</td>
									<td class="tit">提交状态</td>
									<td colspan="3">${fns:getDictLabel(mmp.submitState,'yes_no','')}</td>
								</tr>
								<c:forEach items="${mmp.mspList}" var="msp" varStatus="status">
									<tr>
										<td class="tit">单负责人</td>
										<td colspan="2">${msp.wp.name}</td>
										<td class="tit">提交状态</td>
										<td colspan="3">${fns:getDictLabel(msp.submitState,'yes_no','')}</td>
									</tr>
									<tr>
										<td class="tit">负责部位</td>
										<td colspan="6">${msp.partName}</td>
									</tr>
									<c:forEach items="${msp.mcList}" var="mc" varStatus="status">
										<c:if test = "${empty mc.tc}">
												<tr style="color:#FF0000">
													<td class="tit">补充问题</td>
													<td colspan="6">${mc.remarks}</td>
												</tr>
										</c:if>
										<c:if test = "${not empty mc.tc}">
											<c:if test = "${mc.problem == '0'}">
													<tr style="color:#FF0000">
														<td class="tit">检查内容</td>
														<td colspan="4">${mc.tc.item}</td>
														<td class="tit">有无问题</td>
														<td  colspan="2">${fns:getDictLabel(mc.problem,'have_no','')}</td>
													</tr>
											</c:if>
											<c:if test = "${mc.problem == '1'}">
													<tr>
														<td class="tit">检查内容</td>
														<td colspan="4">${mc.tc.item}</td>
														<td class="tit">有无问题</td>
														<td  colspan="2">${fns:getDictLabel(mc.problem,'have_no','')}</td>
													</tr>
											</c:if>
										</c:if>
									</c:forEach>
								</c:forEach>
							</c:forEach>
						</table>
					</fieldset>
			</c:if>
			<c:if test = "${empty wsMaskWc}">
				暂无详细数据
			</c:if>
	</form:form>
</body>
</html>