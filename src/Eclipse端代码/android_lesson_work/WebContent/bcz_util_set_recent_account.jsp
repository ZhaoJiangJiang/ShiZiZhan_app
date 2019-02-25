<%@page import="uitl.recent_account_bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("设置最近账户");
	request.setCharacterEncoding("UTF-8");
	String recentAccount = request.getParameter("account");
	recent_account_bean bean = new recent_account_bean();
	bean.setRecentAccount(recentAccount);
%>