<%@ page language="java"  pageEncoding="UTF-8"  %>
<%
    final String url = request.getContextPath() + "/index.do";
    response.sendRedirect(response.encodeURL(url));%>