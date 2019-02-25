<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	request.setCharacterEncoding("UTF-8");
	String queryAccount = request.getParameter("account");
	user_Bean bean = new user_Bean();
	int isAuto = bean.getAutoLoginByAccount(queryAccount);
	out.print(isAuto);
%>