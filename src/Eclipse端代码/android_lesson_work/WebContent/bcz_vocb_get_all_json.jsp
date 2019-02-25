<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="bcz.vocb.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	System.out.println("bcz_get_all_json访问成功...");
	vocb_Bean bean = new vocb_Bean();
	List<vocb_Info> data = bean.getAll();
	Iterator<vocb_Info> iterator = data.iterator();
	out.println("[");
	while (iterator.hasNext()) {
		vocb_Info info = iterator.next();
		out.println("{");
		out.println("\"English\":" +"\""+ info.getEnglish() +"\"" + ", ");
		out.println("\"Chinese\":" +"\""+ info.getChinese() +"\"");
		out.println("}");
		if (iterator.hasNext()){
			out.println(", ");
		}
	}
	out.println("]");
%>