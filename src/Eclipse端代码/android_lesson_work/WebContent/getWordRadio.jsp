<%@page import="radio.GetRadio"%>
<%@page import="radio.WordRadio"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
	GetRadio getRadio = new GetRadio();
	List<WordRadio> wordRadios = getRadio.getWordRadio();

	//封装成Json
	String yh = "\"";   //双引号
	out.print("[");
	Iterator<WordRadio> it = wordRadios.iterator();
	while(it.hasNext()){
		WordRadio wordRadio = it.next();
		out.print("{");
		out.print(yh + "english" + yh + ":" + yh + wordRadio.getEnglish() + yh + ",");
		out.print(yh + "chinese" + yh + ":" + yh + wordRadio.getChinese() + yh + ",");
		out.print(yh + "yinbiao" + yh + ":" + yh + wordRadio.getYinbiao() + yh); 
		out.print("}");
		if(it.hasNext()){
			out.println(",");
		}
	}
	out.println("]");
	System.out.println("getWordRadio成功");
%>    