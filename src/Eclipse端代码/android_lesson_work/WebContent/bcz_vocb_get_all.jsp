<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="bcz.vocb.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("android访问成功...");
	vocb_Bean bean = new vocb_Bean();
	List<vocb_Info> data = bean.getAll();
	Iterator<vocb_Info> iterator = data.iterator();
	out.println("<bcz_vocb_table>");
	while (iterator.hasNext()) {
		vocb_Info info = iterator.next();
		out.println("<vocabulary>");
		out.println("<English>"+ info.getEnglish() +"</English>");
		out.println("<Chinese>"+ info.getChinese() +"</Chinese>");		
		out.println("</vocabulary>");
	}
	out.println("</bcz_vocb_table>");
%>