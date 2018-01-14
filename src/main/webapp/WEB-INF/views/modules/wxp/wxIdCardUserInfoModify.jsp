<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>用户详情 -- 锡职快递服务平台</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/notice.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/regexp.js" type="text/javascript"></script>
	<style type="text/css">
		.userInfoEditCont{
			width: 100%;
		}
		.userInfoEditCont  .userInfoIcon{
			width: 20%;
			margin: 0px auto 10px;
		}
		.userInfoEditCont  .nickName{
			font-size: 14px;
			text-align: center;
			font-weight: bold;
		}

		/* -----  cover ----- */
		#coverCont{
			position: absolute;
			top: 0%;
			left: 0%;
			width: 100%;
			height: 100%;
			background-color: rgba(0,0,0,0.5);
			z-index: 1000;
			display: none;
		}
		.userTypeCont{
			position: absolute;
			top: 0px;
			left: 0px;
			z-index: 1001;
			width: 80%;
			background: #f4f4f4;
			border: 1px solid #f1f1f1;
			border-radius: 5px;
			padding: 20px 0px 30px;
			display: none;
		}
		.userTypeCont .userTypeTitle{
			text-align: center;
			font-size: 18px;
			font-weight: bold;
			padding: 10px 0px;
		}
		.userTypeCont .userType{
			width: 90%;
			margin: 5px auto;
			overflow: hidden;
		}
		.userTypeCont .userType .teacherType{
			width: 45%;
			padding: 10px 2%;
			float: left;
			border: 1px solid #f1f1f1;
			background: #fff;
		}
		.userTypeCont .userType .studentType{
			width: 45%;
			padding: 10px 2%;
			float: right;
			border: 1px solid #f1f1f1;
			background: #fff;
		}
		.userType .typeImg{
			width: 50%;
			margin: 0px auto 5px;
		}
		.userType .typeTitle{
			text-align: center;
			line-height: 30px;
			font-weight: bold;
			color: #333333;
		}
		.userType .typeDesc{
			font-size: 12px;
			color: #888888;
			line-height: 20px;
			text-align: center;
		}

		.submitBtn{
			width: 90%;
			margin: 0 auto 20px;
			text-align: center;
			line-height: 46px;
			border-radius: 23px;
			background: #888888;
			color: #fff;
			font-weight: bolder;
		}

		.userSelectCont{
			width: 100%; 
			background: #fff;
			font-size: 14px;
			overflow: hidden;
			position: fixed;
			left: 0px;
			bottom: 0px;
			display: none;
			z-index: 999;
		}
		.userSelectCont .userSelectTitle{
			width: 90%;
			padding: 0px 5%; 
			background: #fff;
			overflow: hidden;
			border-bottom: 1px solid #f1f1f1;
		}
		.userSelectCont .userSelectTitle .selectCancelBtn{
			float: left;
			color: #1f72ff;
			font-weight: bold;
			line-height: 35px;
		}
		.userSelectCont .userSelectTitle .selectOkBtn{
			float: right;
			color: #1f72ff;
			font-weight: bold;
			line-height: 35px;
		}
		.userSelectCont  .selectNumCont{
			width: 90%;
			padding: 0px 5%;
		}
		.userSelectCont  .selectNumCont .selectNum{
			overflow: hidden;
			padding: 10px 0px;
		}
		.userSelectCont  .selectNumCont .selectNum .selectTitle{
			float: left;
			width: 100px;
		}
		.userSelectCont  .selectNumCont .selectNum .selectNumDiv{
			float: left;
			width: calc(100% - 100px);
		}
		.userSelectCont  .selectNumCont .selectNum .selectNumDiv ul{
			list-style: none;
			margin: 0px;
			padding: 0px;
		}
		.userSelectCont  .selectNumCont .selectNum .selectNumDiv ul li{
			float: left;
			width: 24%;
			margin: 0px 0px 5px 1%;
			text-align: center;
			line-height: 25px;
		}

		.selectNum .selectNumDiv ul li.unselect{
			background: #f1f1f1;
			color: #000;
		}
		.selectNum .selectNumDiv ul li.select{
			background: #1f72ff;
			color: #fff;
		}

		.userNewPhoneDiv{
			display: none;
		}

		.gapText{
			text-align: center;
			font-size: 14px;
			color: #888888;
			margin-top: 10px;
		}
	</style>
</head>
<body>
<div class="content">
	<div class="headerNav">
		<div class="headerNavTop"><div class="headerNavIcon headerNavIconOut"><span></span><span></span></div></div>
		<div class="headerNavCont">
			<a href="./index.html">快递首页</a>
			<a href="./userhome.html">个人中心</a>
			<a href="./delivery.html">送货上门</a>
			<a href="./sendexpress.html">我要寄件</a>
			<a href="./lazyboard.html">懒人排行</a>
			<a href="./expassistant.html">快递助手</a>
		</div>
	</div>

	<div class="userInfoEditCont">
		<div class="userInfoIcon">
			<img src="${ctxStatic}/wx/wximages/userInfoIcon.png" width="100%">
		</div>
		<div class="nickName">${sysWxUser.name}</div>

		<form>
			<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
			<input id="wxCode" type="hidden" value="${wxCode}" />
			<input type="hidden" id="userId" name="userId" value="${sysWxUser.id}">
			<div class="userInputCont">
				<div class="inputTypeCont">
					<div class="inputTitle">姓名</div>
					<input type="text" class="commonInput" name="name" id="name" placeholder="请输入真实姓名..." value="${sysWxUser.name}" disabled>
				</div>
				<div class="inputTypeCont">
					<div class="inputTitle">手机</div>
					<input type="text" class="commonInputFunc userOldPhone" id="usernum" name="usernum" placeholder="请输入原手机号码..." value="${sysWxUser.phone}" disabled>
					<div class="commonFuncBtnModify userModifyPhone"></div>
				</div>
				<div class="inputTypeCont userNewPhoneDiv">
					<div class="inputTitle">新手机</div>
					<input type="text" class="commonInput" id="usernewPhone" name="usernewPhone" placeholder="请输入新手机号码...">
				</div>
				<div class="inputTypeCont userNewPhoneDiv">
					<div class="inputTitle">短信</div>
					<input type="text" class="verifiInput" id="username" name="username" placeholder="请输入验证码...">
					<input type="button" class="verifiBtn" value="发送验证码">
				</div>
			</div>
		</form>

		<div class="submitBtn">修改信息</div>
	</div>

	<div class="userSelectCont">
		<div class="userSelectTitle">
			<div class="selectCancelBtn">取消</div>
			<div class="selectOkBtn">确认</div>
		</div>
		<div class="selectNumCont">
			<div class="selectNum">
				<div class="selectTitle">请选择楼层</div>
				<div class="selectNumDiv userFloorNum">
					<ul>
						<li class="unselect">1层</li>
						<li class="unselect">2层</li>
						<li class="unselect">3层</li>
					</ul>
				</div>
			</div>
			<div class="selectNum">
				<div class="selectTitle">请选择宿舍</div>
				<div class="selectNumDiv userRoomNum">
					<ul>
						<li class="unselect">01</li>
						<li class="unselect">02</li>
						<li class="unselect">03</li>
						<li class="unselect">04</li>
						<li class="unselect">05</li>
						<li class="unselect">06</li>
						<li class="unselect">07</li>
						<li class="unselect">08</li>
						<li class="unselect">09</li>
						<li class="unselect">10</li>
						<li class="unselect">11</li>
						<li class="unselect">12</li>
						<li class="unselect">13</li>
						<li class="unselect">14</li>
						<li class="unselect">15</li>
						<li class="unselect">16</li>
						<li class="unselect">17</li>
						<li class="unselect">18</li>
						<li class="unselect">19</li>
						<li class="unselect">20</li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<!-- cover -->
	<!--
	<div id="coverCont"></div>
	<div class="userTypeCont">
		<div class="userTypeTitle">请选择用户类型</div>
		<div class="userType">
			<div class="teacherType">
				<div class="typeImg">
					<img src="images/teachertype.png" width="100%">
				</div>
				<div class="typeTitle">教师</div>
				<div class="typeDesc">需输入教师所属学院及办公室门牌号等</div>
			</div>
			<div class="studentType">
				<div class="typeImg">
					<img src="images/studenttype.png" width="100%">
				</div>
				<div class="typeTitle">学生</div>
				<div class="typeDesc">需输入学生学号、校区、宿舍门牌号等</div>
			</div>
		</div>
	</div>
	-->
</div>
<script type="text/javascript">
	$(function() {
		
		var pageContextVal = $("#PageContext").val();
		var wxCodeVal = $("#wxCode").val();

		 var SubmitUserInfo = function(){
			// 手机号码
			var usernum = $.trim($("#usernum").val());
			if (!CheckPhoneNum(usernum)) {
				rzAlert("操作提示","原手机号码格式不对！");
				return false;
			}

			var usernewPhone = $.trim($("#usernewPhone").val());
			if (!CheckPhoneNum(usernewPhone)) {
				rzAlert("操作提示","新手机号码格式不对！");
				return false;
			}
			
			var name = $("#name").val();
			var username = $("#username").val();
			var userId = $("#userId").val();
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/ul/modifyPersonUserInfo',
			    data:{'name':name,'usernum':usernum,'usernewPhone':usernewPhone,'username':username,'userId':userId,'code':wxCodeVal},
			    dataType: "json",
			    success:function(data){
			    	var prompt = "操作提示";
			    	var code = data.code;
			    	var message = data.message;
			    	if(code == "0"){
			    		rzAlert(prompt,message);
			    		window.location.href= pageContextVal+"/ul/userHome";
			    	}else if(code == "10"){
						rzAlert(prompt,message);
						$("#oldPhone").show();
			    	}else{
			    		rzAlert(prompt,message);
			    	}
		    	},
			    error:function(){
				      
			    }
			});
		 };
		
		//发送验证码
		$(".verifiBtn").click(function(){
			var usernum = $.trim($("#usernum").val());
			if (!CheckPhoneNum(usernum)) {
				rzAlert("操作提示","原手机号码格式不对！");
				return false;
			}

			var newPhone = $.trim($("#usernewPhone").val());
			if (!CheckPhoneNum(newPhone)) {
				rzAlert("操作提示","新手机号码格式不对！");
				return false;
			}
			var name = $("#name").val();
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/wx/sendWxPhoneMsgCodeModify',
			    data:{'name':name,'usernum':usernum,'usernewPhone':newPhone},
			    dataType: "json",
			    success:function(data){
			    	//var result = JSON.parse(data);
			    	if(data.code == "0"){
			    		rzAlert("操作提示",data.message);
			    		$(".verifiBtn").attr('disabled', 'disabled');
						$(".verifiBtn").css({"background":"#888888"});
						var countNum = 60;

						$(".verifiBtn").val("重发短信("+countNum+")");

						var timer = setInterval(function(){
							--countNum;
							if (countNum == 0) {
								$(".verifiBtn").attr('disabled', false);
								$(".verifiBtn").css({"background":"#1f72ff"});
								$(".verifiBtn").val("发送验证码");
								window.clearInterval(timer);
							} else { 
								$(".verifiBtn").val("重发短信("+countNum+")");
								console.log(countNum);
							}
						},1000);
			    	}else if(data.code == "1"){
			    		rzAlert("操作提示",data.message);
			    	}else if(data.code == "2"){
			    		rzAlert("操作提示",data.message);
			    	}
			    },
			    error:function(){
			      
			    }
			});
		});
		
		var initFunc = function(){
			var windowH = $(window).height();
			var windowW = $(window).width();
			var userTypeH = $(".userTypeCont").height();
			var userTypeTop = (windowH - userTypeH)/2 - 40;
			var userTypeLeft = windowW*0.2/2;

			$(".userTypeCont").css({"top":userTypeTop+"px","left":userTypeLeft+"px"});
		};

		initFunc();

		$(window).resize(function(){
			initFunc();
		});

		// 修改用户电话
		$(".userModifyPhone").click(function(){
			$(".userOldPhone").val("");
			$(".userOldPhone").attr("disabled",false);
			$(".userNewPhoneDiv").fadeIn(); 
			
			$(".submitBtn").css({"background":"#1f72ff"});
			$(".submitBtn").bind("click",SubmitUserInfo);
			
			$(".userModifyPhone").unbind("click");
		});

		var selectNum  = ['1','01'];

		$(".userSelectNumCont").click(function(){
			var numTmp = $(".selectNumInput").val();
			if (numTmp == null) {
				$(".selectNumDiv ul li").removeClass('select').addClass("unselect");
				selectNum  = ['0','0'];
			} else {
				var arrayTmp = numTmp.split("");
				selectNum[0] = arrayTmp[0];
				selectNum[1] = arrayTmp[1]+arrayTmp[2];

				// 宿舍号异常
				if (parseInt(selectNum[0]) > 3 || parseInt(selectNum[0]) < 0 || parseInt(selectNum[1]) > 20 || parseInt(selectNum[1]) < 0) {
					$(".selectNumDiv ul li").removeClass('select').addClass("unselect");
					$(".userSelectCont").slideDown();
					return;
				}

				$(".selectNumDiv ul li").removeClass('select').addClass("unselect");

				$(".userFloorNum li").eq(parseInt(selectNum[0])-1).removeClass('unselect').addClass("select");
				$(".userRoomNum li").eq(parseInt(selectNum[1])-1).removeClass('unselect').addClass("select");;
			}
			$(".userSelectCont").slideDown();
		});

		$(".selectOkBtn").click(function(){
			if (!$(".userFloorNum li").hasClass('select') || !$(".userRoomNum li").hasClass('select')) {
				rzAlert("操作提示","请选择楼层和宿舍号！");
				return;
			}
			var userSelectTxt = selectNum[0] + selectNum[1];
			$(".selectNumInput").val(userSelectTxt);
			$(".userSelectCont").slideUp();
		});

		$(".selectCancelBtn").click(function(){
			$(".userSelectCont").slideUp();
		});

		// 宿舍号选择
		$(".userFloorNum li").click(function(){
			if (!$(this).hasClass("select")) {
				$(this).addClass("select").siblings().removeClass("select").addClass('unselect');
			}
			selectNum[0] = $(this).index() + 1;
		});

		$(".userRoomNum li").click(function(){
			if (!$(this).hasClass("select")) {
				$(this).addClass("select").siblings().removeClass("select").addClass('unselect');
			}
			selectNum[1] = $(this).text();
			})
		})

</script>
</body>
</html>