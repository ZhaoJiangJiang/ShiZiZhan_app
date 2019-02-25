<%@page import="uitl.feed_back_bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%
 	request.setCharacterEncoding("UTF-8");
 	// String text = "这是我的第二条意见！";
 	// String time = "2018年12月13日12:51";
	String text = request.getParameter("feed_back_text");
	String time = request.getParameter("feed_back_time");
	feed_back_bean bean = new feed_back_bean();
	int res = bean.setFeedBack(text, time);
	out.println(res);
 %>