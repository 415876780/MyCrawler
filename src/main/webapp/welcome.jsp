<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page language="java" import="ccnu.computer.common.Constant,ccnu.computer.common.LoginInfo" %>
<%
  String basePath = request.getScheme() 
            + "://" 
		    + request.getServerName() 
		    + ":" 
		    + request.getServerPort()
            + request.getContextPath(); 
  LoginInfo loginInfo = (LoginInfo)session.getAttribute(Constant.Session_LoginInfo);
  if(loginInfo != null){
	  response.sendRedirect(basePath + "/system/index");
  }else{
	  response.sendRedirect(basePath + "/login/userlogin");
  }
%>