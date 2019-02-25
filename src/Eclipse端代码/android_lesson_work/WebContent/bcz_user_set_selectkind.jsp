<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	// String Account = "18058431808";
	// String selectKind = "四级单词";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	String selectKind = request.getParameter("selectkind");
	user_Bean bean = new user_Bean();
	int res = bean.setSelectKind(selectKind, Account);
	out.print(res);
%>