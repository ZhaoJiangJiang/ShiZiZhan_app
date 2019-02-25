
<%@page import="uitl.recent_account_bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	recent_account_bean bean = new recent_account_bean();
	String account = bean.getRecentAccount();
	out.print(account);
%>