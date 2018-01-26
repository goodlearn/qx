<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>我的任务</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	
	<style type="text/css">
		*{
			margin: 0px;
			padding: 0px;
			list-style-type: none;
		}
		body{
			background: #e8e8e8;
		}
		.content{
			max-width: 600px;
			margin: 0 auto;
			font-family: "Tahoma","Microsoft YaHei";
		}
		.pageDesc{
			width: 100%;
			line-height: 55px;
			background: #000; 
			color: #fff;
			text-align: center;
			font-weight: bolder;
			box-shadow: 0px 5px 5px #e1e1e1; 
		}
		.userInfo{
			width: 90%;
			background: #fff; 
			padding: 15px 5%;
			overflow: hidden;
			margin: 15px 0px;
			border-top:1px solid #dbdbdb;
			border-bottom:1px solid #dbdbdb;
			box-shadow: 0px 5px 5px #e1e1e1; 
		}
		.userInfo .userImgDiv{
			width: 40px;
			height: 40px;
			float: left;
			overflow: hidden;
			border-radius: 20px;
		}
		.userInfo .userName{
			float: left;
			width: calc(100% - 40px - 20px - 10px);
			margin-left: 20px;
			line-height: 40px;
			overflow: hidden;
			text-overflow:ellipsis;
			white-space: nowrap;
			font-size: 16px;
			font-weight: bolder;
		}

		.notask{
			width: 100%;
			margin-top: 40px;
			text-align: center; 
			font-size: 14px;
			color: #888888;
			display: none;
		}
		.notask .notaskimg{
			width: 70px;
			margin: 0 auto 20px;
		}

		.workTaskCont{
			width: 100%;
			background: #fff; 
			overflow: hidden;
			margin-bottom: 10px;
			border-top:1px solid #dbdbdb;
			border-bottom:1px solid #dbdbdb;
			box-shadow: 0px 5px 5px #e1e1e1; 
		}
		.workTaskCont .taskType{
			width: 96%;
			padding: 0px 2%;
			overflow: hidden;
			border-bottom: 1px solid #ebebeb;
		}
		.workTaskCont .taskType .taskTypeTxt{
			float: left;
			padding-left: 27px;
			padding-top: 20px;
			padding-bottom: 20px;
			font-size: 16px;
			font-weight: bolder;
			color: #666666;
			background: url(./images/tasktypeicon.png) no-repeat left center;
			background-size: 23px;
		}
		.workTaskCont .taskType .taskSubmit{
			float: right;
			font-size: 14px;
			background: #1f72ff;
			padding: 5px 15px;
			margin-top: 15px;
			color: #fff;
			border-radius: 5px;
		}
		.taskCont{
			width: 100%;
		}
		.taskCont ul li{
			display: block;
			width: 96%;
			padding: 0px 2%;
			overflow: hidden;
			border-bottom: 1px solid #ebebeb;
			background: url(./images/arrowrighticon.png) no-repeat 97% center;
			background-size: 20px;
		}
		.taskCont ul li p{
			padding-top: 20px;
			padding-bottom: 20px;
			padding-left: 22px;
			font-size: 14px;
			font-weight: bold;
			color: #666666;
		}
		.taskCont ul li .uncomplete{
			background: url(./images/uncompleteicon.png) no-repeat left center;
			background-size: 16px;
		}
		.taskCont ul li .complete{
			background: url(./images/completeicon.png) no-repeat left center;
			background-size: 16px;
		}
		.taskCont a:hover,a:visited,a:link{
			text-decoration: none;
			color: #666666;
		}
	</style>
</head>
<body>
<div class="content">
	<div class="pageDesc">${wc.name}</div>
	<div class="userInfo">
		<div class="userImgDiv">
			<img src="images/headerimgicon.png" alt="" width="100%">
		</div>
		<div class="userName">${wp.name}</div>
	</div>

	<div class="expEnterCont">
		<div class="expEnterIcon">
			<img src="${ctxStatic}/wx/wximages/expentericon.png" width="100%">
			<p>录入快递</p>
		</div>
		${message}
		<div class="expEnterInput">
				<input id="timestamp" type="hidden" value="${timestamp}" />
				<input id="noncestr" type="hidden" value="${nonceStr}" />
				<input id="signature" type="hidden" value="${signature}" />
				<input id="appId" type="hidden" value="${appId}" />
			<form>
				<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
				<input id="wxCode" type="hidden" value="${wxCode}" />
				<div class="userInputCont">
					<div class="inputTypeCont">
						<div class="inputTitle">单号</div>
						<input type="text" id="expressId" class="commonInputFunc" name="expressId" placeholder="请输入快递单号...">
						<div class="commonFuncBtnScan" id="scanQRCodeBtn"></div>
					</div>
					<div class="inputTypeCont">
						<div class="inputTitle">手机</div>
						<input type="text" id="phone" class="commonInputFunc" name="phone" placeholder="请输入收件人号码...">
						<div class="commonFuncBtnVoice" id="voiceRecordBtn"></div>
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
	<!-- cover voice record -->
	<div class="stopvoicerecord">
		<div class="voiceRecordCont">
			<img src="${ctxStatic}/wx/wximages/voicerecordicon.png" alt="正在录音中...">
			<p class="voiceRecordState">正在录音中...</p>
			<div id="stopVoiceRecordBtn">完成录音并识别</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		
		var submitFunc = function(){
			$(".submitBtn").unbind("click");
			//检测快递单号
			var expressId = $.trim($("#expressId").val());
			if (!CheckExpNum(expressId)) {
				rzAlert("操作提示","快递单号格式不正确！");
				$(".submitBtn").bind("click",submitFunc);
				return false;
			}

			// 手机号码
			var phone = $.trim($("#phone").val());
			if (!CheckPhoneNum(phone)) {
				rzAlert("操作提示","手机号码格式不对！");
				$(".submitBtn").bind("click",submitFunc);
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
			    	$(".submitBtn").bind("click",submitFunc);
		    	},
			    error:function(){
			    	$(".submitBtn").bind("click",submitFunc);  
			    }
			});
		};
		
		$(".submitBtn").bind("click",submitFunc);
		
		var initFun = function(){
			var windowW = $(window).width();
			var windowH = $(window).height();
			$(".content").css({"height": windowH + "px"});
			
		};

		initFun();

		$(window).resize(function(){
			initFun();
		});
		
		// JSSDK
		var timestamp = $("#timestamp").val();//时间戳
        var nonceStr = $("#noncestr").val();//随机串
        var signature = $("#signature").val();//签名
        var appId = $("#appId").val();//签名
        wx.config({
            debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
            appId : appId, // 必填，公众号的唯一标识
            timestamp : timestamp, // 必填，生成签名的时间戳
            nonceStr : nonceStr, // 必填，生成签名的随机串
            signature : signature,// 必填，签名，见附录1
            jsApiList : [
            	'checkJsApi',
                'onMenuShareTimeline',
                'onMenuShareAppMessage',
                'onMenuShareQQ',
                'onMenuShareWeibo',
                'hideMenuItems',
                'showMenuItems',
                'hideAllNonBaseMenuItem',
                'showAllNonBaseMenuItem',
                'translateVoice',
                'startRecord',
                'stopRecord',
                'onRecordEnd',
                'playVoice',
                'pauseVoice',
                'stopVoice',
                'uploadVoice',
                'downloadVoice',
                'chooseImage',
                'previewImage',
                'uploadImage',
                'downloadImage',
                'getNetworkType',
                'openLocation',
                'getLocation',
                'hideOptionMenu',
                'showOptionMenu',
                'closeWindow',
                'scanQRCode',
                'chooseWXPay',
                'openProductSpecificView',
                'addCard',
                'chooseCard',
                'openCard'
            ]
        // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
        });

        wx.ready(function() {  
	        wx.checkJsApi({  
	            jsApiList : ['scanQRCode'],  
	            success : function(res) {  

	            }  
	        });  

	        //扫描二维码  
	        document.querySelector('#scanQRCodeBtn').onclick = function() {  
	            wx.scanQRCode({  
	                needResult : 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，  
	                scanType : [ "qrCode", "barCode" ], // 可以指定扫二维码还是一维码，默认二者都有  
	                success : function(res) {  
	                    //扫码后获取结果参数赋值给Input
	                    var url = res.resultStr;
	                    var qrCodenum = url.split(",");
	                    $("#expressId").val(qrCodenum[1]);
	                }  
	            });  
	        };//end_document_scanQRCode  

	     // 语音识别
	        document.querySelector('#voiceRecordBtn').onclick = function(){
	        	wx.startRecord();
	        	$(".stopvoicerecord").show();
	        };

	        $("#stopVoiceRecordBtn").click(function(){
	        	wx.stopRecord({
					success: function (res) {
						$(".stopvoicerecord").hide();
						var localId = res.localId;
						wx.translateVoice({
							localId: localId, // 需要识别的音频的本地Id，由录音相关接口获得
							isShowProgressTips: 1, // 默认为1，显示进度提示
							success: function (res) {
								var result = res.translateResult.replace(/[^0-9]/ig,"");
								$("#phone").val(result);
							}
						});
					},
					cancel: function() {
						alert("拒绝了就不能录音了哦！");
					}
				});
	        });
	        
	          
	    });//end_ready 
	})
</script>
</body>
</html>