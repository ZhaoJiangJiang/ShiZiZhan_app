<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("setStudied成功...");
	// String Account = "18058431808";
	// String English = "surprise";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	String English = request.getParameter("english");
	user_Bean bean = new user_Bean();
	bean.setStudied(Account, English);
%>