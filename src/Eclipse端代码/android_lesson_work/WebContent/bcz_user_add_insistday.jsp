<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("add_insist_day成功...");
	// String Account = "18058431808";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	System.out.println(Account);
	user_Bean bean = new user_Bean();
	int res = bean.addInsistDay(Account);
	out.print(res);
%>