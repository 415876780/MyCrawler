<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/structure.css">

<script type="text/javascript"	src="<%=basePath%>/content/scripts/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/content/scripts/md5.js"></script>
<%
     String username = "";
     //获取当前站点的所有Cookie
     Cookie[] cookies = request.getCookies();
     for (int i = 0; i < cookies.length; i++) {//对cookies中的数据进行遍历，找到用户名、密码的数据
         if ("username".equals(cookies[i].getName())) {
            username = cookies[i].getValue();
        } 
     }
%>
<script type="text/javascript">
window.onload=function(){  
	 //alert("你好，我是一个警告框！");
	//document.getElementById("bg").style.backgroundColor="#F90";
	}
	
function keydown(){
	   if (event.keyCode==13) {
		   $("#btn_submit").trigger("click");
	   }
}
		$(function() {
			if("<%=username%>" != ""){
				$("#ipt_username").val("<%=username%>");
			}
			$("#btn_submit").click(function() {
				
				var username = $("#ipt_username").val().trim();
				var password = $("#ipt_password").val().trim();

				if (username == '' || password == '') {
					$("#sp_err").html("<font color=red>用户或密码不能为空！</font>");
					if(username == ''){
						$("#ipt_username").focus();
					}else{
						$("#ipt_password").focus();
					}
					
					$("#sp_err").show();
					return false;
				}

				$.post("<%=basePath%>/login/dologin", {
					username : username,
					password : hex_md5(password),
					isRememberUser:$("#ck_rem").prop("checked")
				}, function(d) {
					//$("#sp_err").show();
					window.location = "<%=basePath%>/text/texts";
					//alert(d);
				});
			});
			
			$("#btn_reset").click(function() {
				$("#ipt_username").val("");
				$("#ipt_password").val("");
				$("#sp_err").hide();
				$("#ipt_username").focus();
			});
			
		});
	
</script> 
</head>
<body onkeydown="keydown()">
<!-- <form method="post">
	用户名：<input type="text" name="username"/><br>
	用户密码：<input type="password" name="password"/><br/>
	<input type="submit" value="用户登录">

</form> -->
<form class="box login" method="post">
<p><span id="sp_err" style="display:none;"><font color="red">提示：用户名或密码不正确</font></span></p>
	<fieldset class="boxBody">
	  <label>Username</label>
	  <input id="ipt_username" type="text" name="username" tabindex="1"  placeholder="PremiumPixel" required>
	  <label><a href="#" class="rLink" tabindex="5">Forget your password?</a>Password</label>
	  <input id="ipt_password" type="password"  name="password" tabindex="2" required>
	</fieldset>
	<footer>
	  <label><input type="checkbox" tabindex="3">Keep me logged in</label>
	  <input id="btn_submit" class="btnLogin" value="Login" tabindex="4">
	</footer>
</form>

</body>
</html>