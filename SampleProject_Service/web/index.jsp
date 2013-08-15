<%-- 
    Document   : testPage
    Created on : 2011/10/27, 下午 01:27:14
    Author     : leo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Page</title>
    </head>
    <body>
        <% String ContextPath = request.getContextPath(); %>
        <a href="<%=ContextPath%>/setData?oper=update&id=2&target=groups&name=manager">update</a>
        <br />
        <a href="<%=ContextPath%>/setData?oper=insert&target=groups&name=guest">insert</a>
        <br />
        <a href="<%=ContextPath%>/getData?oper=list&target=goodsInfo">show Goods Info</a>
    </body>
</html>
