<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	// String account = "18058431808";
	request.setCharacterEncoding("UTF-8");
	String account = request.getParameter("account");
	user_Bean bean = new user_Bean();
	int isExist = bean.isAccountExist(account);
	out.print(isExist);
	System.out.println(account+"->isExist->"+isExist);
%>