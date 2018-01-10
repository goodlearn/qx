<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>录入快递 -- 锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/regexp.js" type="text/javascript"></script>
	<style type="text/css">
		.expEnterCont{
			padding-bottom: 20px;
		}
		.expEnterCont .expEnterIcon{
			width: 20%;
			margin: 0 auto;
		}
		.expEnterCont .expEnterIcon p{
			text-align: center;
			font-weight: bold;
		}

		.submitBtn{
			width: 90%;
			margin: 0 auto;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			background: #1f72ff;
			color: #fff;
			font-weight: bolder;
		}
	</style>
</head>
<body>
<div class="content">
	<div class="headerNav">
		<div class="headerNavTop"><div class="headerNavIcon headerNavIconOut"><span></span><span></span></div></div>
		<div class="headerNavCont">
			<a href="../index.html">快递首页</a>
			<a href="../userhome.html">个人中心</a>
			<a href="../delivery.html">送货上门</a>
			<a href="../sendexpress.html">我要寄件</a>
			<a href="../lazyboard.html">懒人排行</a>
			<a href="../expassistant.html">快递助手</a>
		</div>
	</div>

	<div class="expEnterCont">
		<div class="expEnterIcon">
			<img src="${ctxStatic}/wx/wximages/expentericon.png" width="100%">
			<p>录入快递</p>
		</div>
		${message}
		<div class="expEnterInput">
			<form>
				<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
				<input id="wxCode" type="hidden" value="${wxCode}" />
				<div class="userInputCont">
					<div class="inputTypeCont">
						<div class="inputTitle">单号</div>
						<input type="text" id="expressId" class="commonInputFunc" name="expressId" placeholder="请输入快递单号...">
						<div class="commonFuncBtnScan"></div>
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">手机</div>
						<input type="text" id="phone" class="commonInputFunc" name="phone" placeholder="请输入收件人号码...">
						<div class="commonFuncBtnScan"></div>
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">取货码</div>
						<input type="text" id="pickUpCode" class="commonInputFunc" name="pickUpCode" placeholder="请输入取货码">
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">公司</div>
						<select id="company" name="company">  
                             <c:forEach var="dict" items="${fns:getDictList('expressCompany')}">  
                                <option value="${dict.value}">  
                                ${dict.label}  
                                </option>  
                              </c:forEach>  
                         </select>  
					</div>
				</div>
			</form>
			<div class="submitBtn">录入信息</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		
		$(".submitBtn").click(function(){
			
			//检测快递单号
			var expressId = $.trim($("#expressId").val());
			if (!CheckExpNum(expressId)) {
				rzAlert("操作提示","快递单号格式不正确！");
				return false;
			}

			// 手机号码
			var phone = $.trim($("#phone").val());
			if (!CheckPhoneNum(phone)) {
				rzAlert("操作提示","手机号码格式不对！");
				return false;
			}
			
			//$(".msgcover").fadeIn();
			//$(".coverMsgCont").fadeIn();
			var pageContextVal = $("#PageContext").val();
			var company = $("#company").val();
			var wxCode = $("#wxCode").val();
			var pickUpCode = $("#pickUpCode").val();
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/ul/saveExpress',
			    data:{'expressId':expressId,'phone':phone,'company':company,'pickUpCode':pickUpCode,'code':wxCode},
			    dataType: "json",
			    success:function(data){
			    	var prompt = "操作提示";
			    	var code = data.code;
			    	var message = data.message;
			    	if(code == "0"){
			    		rzAlert(prompt,message);
			    		$("#expressId").val("");
			    		$("#phone").val("");
			    	}else{
			    		rzAlert(prompt,message);
			    	}
		    	},
			    error:function(){
				      
			    }
			});
		});
		
		var initFun = function(){
			var windowW = $(window).width();
			
		};

		initFun();

		$(window).resize(function(){
			initFun();
		});
	});
</script>
</body>
</html>