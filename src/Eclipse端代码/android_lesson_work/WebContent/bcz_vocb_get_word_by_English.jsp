<%@page import="bcz.vocb.*"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String EngStr = request.getParameter("English");
	if (EngStr == null || EngStr.equals("") || EngStr.length()==0){
		System.out.println("传入值为空");
		return;
	}
	System.out.println("bcz_vocb_get_word_by_English...->"+EngStr);
	vocb_Bean bean = new vocb_Bean();
	List<vocb_Info> data = bean.getWordByEnglish(EngStr);
	Iterator<vocb_Info> iterator = data.iterator();
	out.println("[");
	while (iterator.hasNext()) {
		vocb_Info info = iterator.next();
		out.println("{");
		out.println("\"English\":" +"\""+ info.getEnglish() +"\"" + ", ");
		out.println("\"Chinese\":" +"\""+ info.getChinese() +"\"" + ", ");
		out.println("\"vocbroot\":" +"\""+ info.getVocbRoot() +"\"" + ", ");
		out.println("\"sentence\":" +"\""+ info.getSentence() +"\"" + ", ");
		out.println("\"sentenceChinese\":" +"\""+ info.getSentenceChinese() +"\"" + ", ");
		out.println("\"need\":" +"\""+ info.getNeed() +"\"");
		out.println("}");
		System.out.println(info.toString());
		if (iterator.hasNext()){
			out.println(", ");
		}
	}
	out.println("]");
%>