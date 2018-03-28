<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>审核提交</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/jweixin-1.2.0.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/common.css" type="text/css" rel="stylesheet" />
	

	<style type="text/css">
		.checkCont{
			width: calc(96% - 2px);
			margin: 15px 2%;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
		}
		.checkCont ul li{
			display: block;
			width: 96%;
			padding: 0px 2%;
			display: block;
			font-size: 14px;
			font-weight: 100;
		}
		.checkCont ul li input[type=text]{
			display: none;
		}
		.checkCont ul li p{
			width: calc(100% - 25px);
			padding: 20px 0px 20px 25px;
			font-size: 14px;
			font-weight: bolder;
			color: #333333;
		}
		.checkCont ul li .checkTxt{
			background: url(../static/wx/wximages/selecticon.png) no-repeat left center;
			background-size: 18px;
		}
		.checkCont ul li .unCheckTxt{
			background: url(../static/wx/wximages/selecticon1.png) no-repeat left center;
			background-size: 18px;
		}
		.checkCont ul li textarea{
			width: 96%;
			height: 40px;
			padding: 10px 2%;
			font-family: "Microsoft YaHei";
			color: #666666;
			font-size: 13px;
			font-weight: 100;
			outline: none;
			border: none;
			resize: none;
			background: #f1f1f1;
			border-radius: 5px;
		}
		
		.otherProb{
			width: calc(96% - 2px);
			margin: 15px 2%;
			background: #fff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow:hidden;
		}
		.otherProb ul li{
			width: 96%;
			padding: 10px 2%;
			font-size: 14px;
			font-family: "Microsoft YaHei";
			line-height: 25px;
			color: #333333;
		}
		
		.checkCont textarea{
			width: 96%;
			padding: 10px 2%;
			font-family: "Microsoft YaHei";
			font-size: 14px;
			outline: none;
			border: none;
			resize: none;
		}
		
		/* userIdImg css */
		.userIdImgUpload{
			width: 96%; 
			margin: 0 auto 20px;
			overflow: hidden;
			box-shadow: 0px 5px 5px #d1d1d1; 
			border-radius: 8px;
		}
		.userIdImgUpload .userIdImgCont{
			width: 100%;
			overflow: hidden;
			
			border: 1px solid #e1e1e1;
			background: #fff;
		}
		.userIdImgUpload .userIdImgCont img{
			width: 100%;
			display: block;
			margin: 0 auto;
		}
		.userIdImgUpload .userIdImgCont .userIdImgUploadDesc{
			margin: 0px; 
			line-height: 30px;
			font-size: 14px;
			color: #999999;
			font-weight: bold;
			text-align: center;
		}

		.submitBtn{
			display: block;
			width: calc(96% - 2px);
			margin: 0 auto 15px;
			background: #1f72ff;
			border:1px solid #d8d8d8;
			border-radius: 8px;
			box-shadow: 0px 5px 5px #d1d1d1; 
			overflow: hidden;
			line-height: 50px;
			color: #fff;
			font-size: 14px;
			font-weight: bolder;
			outline: none;
		}
	</style>	
</head>
<body>
<div class="content">
	<input id="appId" type="hidden" value="${appId}"/>
	<input id="timestamp" type="hidden" value="${timestamp}" />
    <input id="noncestr" type="hidden" value="${nonceStr}" />
    <input id="signature" type="hidden" value="${signature}" />
	<div class="pageNav">
		<p class="pageTxt">审核提交</p>
		<p class="backBtn">&lt; 返回</p>
	</div>
	
		<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
		<input id="wmwId" type="hidden" value="${maskInfo.wsMaskWcId}" />
		<div class="taskInfo">
			<ul>
				<li class="borderButtom"><p class="taskType">${maskInfo.workShopMaskName}</p></li>
			</ul>
		</div>
		<c:forEach items="${maskInfo.mspList}" var="msp" varStatus="status">
			<div class="taskInfo">
				<ul>
					<li class="borderButtom"><p class="taskProject">${msp.partName}</p></li>
					<li><p class="checkPerson">${msp.wp.name}</p></li>
				</ul>
			</div>
			<div class="checkCont">
				<ul>
					<c:if test = "${msp.submitState == '0'}">
						<li>
							<input type="text" value="fdsafd" name="" readonly="true">
							<p class="unCheckTxt">负责人还未提交任务</p>
						</li>
					</c:if>
					<c:if test = "${msp.submitState == '1'}">
							<c:forEach items="${msp.mcList}" var="mc" varStatus="mcstatus">
								<c:if test = "${not empty mc.tc}">
									<c:if test = "${mc.problem == '0'}">
										<li class="borderButtom">
											<input type="text" value="fdsafd" name="" readonly="true">
											<p class="checkTxt">${mc.tc.item}</p>
											<c:if test = "${mc.remarks!='-1'}">
												<textarea readonly="true">${mc.remarks}</textarea>
											</c:if>
										</li>
									</c:if>
								</c:if>
							</c:forEach>
					</c:if>
				</ul>
			</div>
			<!-- 其他问题展示 -->
			<div class="otherProb">
				<c:if test = "${msp.submitState == '1'}">
						<c:if test = "${msp.desc != ''}">
							<ul>
								<li class="borderButtom">
									${msp.desc}		
								</li>
							</ul>
						</c:if>
				</c:if>
			</div>
		</c:forEach>
		
		<div class="userIdImgUpload">
			<div class="userIdImgCont" id="userIdImgPositive">
				<img src="${ctxStatic}/wx/wximages/defaultimage.jpg" alt="图片加载中...">
				<p class="userIdImgUploadDesc">点击上传照片</p>
			</div>
		</div>
		
		<button class="submitBtn">确认提交</button>
	
</div>

<script type="text/javascript">
	var pageContextVal = $("#PageContext").val();
	var wmwId = $("#wmwId").val();
	$(function() {
		var u = navigator.userAgent;
	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
	    if (isAndroid == true) {
	    	$(".taskInfo ul li p").css({
	    		"padding-top": "15px",
	    		"padding-bottom": "12px"
	    	});
	    	$(".checkCont ul li p").css({
	    		"padding": "20px 0px 17px 25px"
	    	})
	    }
	
	
		// backNav
		$(".backBtn").click(function(){
			history.back(-1);
		});
	
		// submit
		$(".submitBtn").click(function(){
			var state = confirm("提交后不可修改，确认提交？");
			if(!state){
			   return false;
			}
			$.ajax({
			    type:'POST',
			    url:pageContextVal+'/wmw/submitMask',
			    data:{'wmwId':wmwId},
			    dataType: "json",
			    success:function(data){
					switch(data.code) {
						case "0" : 
							alert("提交成功!");
							window.location.href= pageContextVal+"/wi/indexInfo";
							break;
						case "1" : alert(data.message); break;
					}
			    },
			    error:function(){
			      alert("未知错误");
			    }
			});
		});
	});
	
	// 用户上传图片函数
	// JSSDK
	var appId = $("#appId").val();
	var timestamp = $("#timestamp").val();//时间戳
    var nonceStr = $("#noncestr").val();//随机串
    var signature = $("#signature").val();//签名
    wx.config({
        debug : true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
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
            jsApiList : ['chooseImage','previewImage','uploadImage','downloadImage'],  
            success : function(res) {  

            }  
        });  

        //扫描二维码  
        document.querySelector('#userIdImgPositive').onclick = function() {  
            wx.chooseImage({
				count: 1, // 默认9
				sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有
				sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
				success: function (res) {
					var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片

					wx.uploadImage({
						localId: localIds[0], // 需要上传的图片的本地ID，由chooseImage接口获得
						isShowProgressTips: 1, // 默认为1，显示进度提示
						success: function (res) {
							var serverId = res.serverId; // 返回图片的服务器端ID
							$.ajax({
							    type: 'POST',
							    url: 'upMaskImage',
							    data: {'serverId':serverId,'wmwId':wmwId},
							    success:function(data){
							    	var prompt = "操作提示";
							    	var code = data.code;
							    	var message = data.message;
							    	if(code == "1"){
							    		rzAlert(prompt,message);
							    		alert("图片上传成功！");  // 需要返回一个图片连接
								    	var imgaddr = data;
								    	$("userIdImgCont image").attr("src",imgaddr);
							    	}else{
							    		rzAlert(prompt,message);
							    	}
							    },
							    error:function(){
							    	
							    }
							    
							});
						}
					});
				}
			});
        };//end_document_scanQRCode  
        
          
    });//end_ready 
</script>
</body>
</html>