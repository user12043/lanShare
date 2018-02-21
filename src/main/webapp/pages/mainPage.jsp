<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Lan Share</title>
</head>
<body>
Files:
<ul>
    <% for (File file : (File[]) request.getAttribute("files")) {
        String name = file.getName();%>
    <li><a href="${pageContext.request.contextPath}/file?fileName=<%out.print(name);%>"><%
        out.print(name);%></a>
    </li>
    <%}%>
</ul>
<br><br><br>
<form action="${pageContext.request.contextPath}/file" method="post" enctype="multipart/form-data"
      onsubmit="return validateForm()">
    <table border="3">
        <tr>
            <td><label for="upFile">Select File: </label></td>
            <td><input id="upFile" name="upFile" type="file"></td>
        </tr>
        <tr style="text-align: center">
            <td colspan="2"><input type="submit" value="Submit"></td>
        </tr>
    </table>
</form>
<script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
</body>
</html>
