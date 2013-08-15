<%--
    Document   : newjspgetInfo
    Created on : 2010/5/26, 下午 11:52:03
    Author     : leo
--%>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>

<% String ContextPath = request.getContextPath(); %>
<%
            int setResult = (Integer) request.getAttribute("setResult");
            String oper = (String) request.getAttribute("oper");
%>

<reply>
    <oper>
        <%=oper%>
    </oper>
    <setResult>
        <%=setResult%>
    </setResult>
</reply>