<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no" />
    <script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
    <title>员工绑定</title>
    <meta charset="utf-8">
    <style type="text/css">
        .content{
            max-width: 650px;
            margin: 0 auto;
            font-family: 'Microsoft YaHei';
        }
        .logoimg{
            width: 70px;
            margin: 40px auto 0px;
        }
        .bannerimg{
            display: block;
            margin-top: 20px;
        }
        .inputinfo{
            width: 80%; 
            margin: 20px auto;
        }

        .form-group {
            position: relative;
            font-size: 15px;
            color: #666;
        }
        .form-group + .form-group {
            margin-top: 30px;
        }
        .form-group .form-label {
            position: absolute;
            z-index: 1;
            left: 0;
            top: 5px;
            -webkit-transition: 0.3s;
            transition: 0.3s;
        }
        .form-group .form-control {
            width: 100%;
            position: relative;
            z-index: 3;
            height: 35px;
            background: none;
            border: none;
            padding: 5px 0;
            -webkit-transition: 0.3s;
            transition: 0.3s;
            border-bottom: 1px solid #777;
        }
        .form-group .form-control:invalid {
            outline: none;
        }
        .form-group .form-control:focus, .form-group .form-control:valid {
            outline: none;
            color: #1f72ff;
            font-weight: bolder;
            box-shadow: 0 1px #1f72ff;
            border-color: #1f72ff;
        }
        .form-group .form-control:focus + .form-label, .form-group .form-control:valid + .form-label {
            font-size: 12px;
            -ms-transform: translateY(-15px);
            -webkit-transform: translateY(-15px);
            transform: translateY(-15px);
        }

        .submitBtn{
            display: block;
            width: 100%;
            margin: 35px auto 15px;
            background: #1f72ff;
            border:1px solid #d8d8d8;
            border-radius: 8px; 
            overflow: hidden;
            text-align: center;
            line-height: 40px;
            color: #fff;
            font-size: 14px;
            font-weight: bolder;
            outline: none;
        }
    </style>
</head>
<body class="content">
    <div class="logoimg">
        <img src="../static/wx/wximages/logo1.png" alt="图片加载中..." width="100%">
    </div>
    
    <img src="../static/wx/wximages/topimg1.jpg" class="bannerimg" alt="图片加载中..." width="100%">
    
    <div class="inputinfo">
        <div class="form-group">
            <input required="required" type="text" id="desc" class="form-control"/>
            <label class="form-label">姓名</label>
        </div>
        <div class="form-group">
            <input type="text" required="required" id="no" class="form-control" />
            <label class="form-label">工号</label>
        </div>

        <div class="submitBtn">绑定帐号</div>
    </div>
    
<script type="text/javascript">
    $(function() {
        $(".submitBtn").click(function(){
        	
        	var desc = $.trim($("#desc").val());
			if (desc.length == '' || desc == null) {
				alert("请输入姓名");
				return false;
			}
			
			var no = $.trim($("#no").val());
			if (no.length == '' || no == null) {
				alert("请输入工号");
				return false;
			}
        	
        	 $.ajax({
			     type:'POST',
			     url:pageContextVal+'/test/tieInfo',
			     data:{'desc':desc,'no':no},
			     dataType: "json",
			     success:function(data){
			    	switch(data.code){
				    	case "1" : alert(data.message); break;
						case "0" : 
					     	alert("绑定成功");
							break;
			    	}
			     },
			     error:function(){
			    	 alert("操作失败 未知错误");
			     }
			    
			 });
        })
    });
</script>

</body>
</html>