<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	// String Account = "18058431808";
	// String planNum = "123";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	String planNum = request.getParameter("plannum");
	user_Bean bean = new user_Bean();
	int res = bean.setPlanNum(planNum, Account);
	out.print(res);
%>