<%@page import="bcz.user.user_Bean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("getCutted成功...");
	// String Account = "18058431808";
	request.setCharacterEncoding("UTF-8");
	String Account = request.getParameter("account");
	user_Bean bean = new user_Bean();
	String res = bean.getCutted(Account);
	if (res==null || res.length()==0){
		out.println("null");
		return;
	}
	out.println("<bcz_vocb_table>");
	String[] infos = res.split("#");
	for (int i = 0, len = infos.length; i < len; i++){
		out.println("<vocabulary>");
		out.println("<English>"+ infos[i++] +"</English>");
		out.println("<Chinese>"+ infos[i] +"</Chinese>");		
		out.println("</vocabulary>");
	}
	out.println("</bcz_vocb_table>");
%>