<%@ page import="ogr.user12043.lanShare.util.Utils" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Lan Share</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<h1 class="text-center text-primary">LAN SHARE</h1>

<table class="table table-bordered table-hover table-dark text-center">
    <thead class="bg-secondary">
    <tr>
        <td></td>
        <td>File Name</td>
        <td>Size</td>
        <td>Last Modified</td>
    </tr>
    </thead>
    <tbody>
    <%
        File[] files = (File[]) request.getAttribute("files");
        int count = 0;
        if (files != null) {
            for (File file : files) {
                if (!file.isDirectory()) {
                    String name = file.getName();
                    count++;
    %>
    <tr>
        <td>
            <%out.print(count + ". ");%>
        </td>
        <td>
            <%--File name text is encoding before requesting for special chars like '&'--%>
            <a href="#" onclick="location.href = ('file?fileName=' + encodeURIComponent(this.innerText));"><%
                out.print(name);%></a>
        </td>
        <td>
            <%
                long fileSpace = file.length();
                if (fileSpace < 1048576) {
                    out.print("<u>" + Math.round((fileSpace) / (1024d)) + "</u> <span class=\"text-warning\">KB</span>");
                } else {
                    out.print("<u>" + Math.round((fileSpace) / (1024d * 1024d)) + "</u> <span class=\"text-warning\">MB</span>");
                }
            %>
        </td>
        <td>
            <%out.print(Utils.getTimeAsString(new Date(file.lastModified())));%>
        </td>
    </tr>
    <%
                }
            }
        }
    %>
    </tbody>
</table>
<br><br><br>
<%--<form action="${pageContext.request.contextPath}/file" method="post" enctype="multipart/form-data"--%>
<%--onsubmit="return validateForm()">--%>
<h2>Upload</h2>
<table class="table table-active">
    <thead>

    </thead>
    <tbody>
    <tr>
        <td class="form-group">
            <input class="btn btn-dark form-control form-control-file" id="upFiles" name="upFiles" type="file"
                   multiple="multiple" aria-autocomplete="none">
        </td>
    </tr>
    <tr>
        <td class="form-group">
            <%--<progress class="form-control progress-bar progress-bar-animated" id="progressBar"></progress>--%>
            <div class="progress">
                <div id="progressBar" class="progress-bar progress-bar-striped"
                     role="progressbar"
                     aria-valuenow="0"
                     aria-valuemin="0" aria-valuemax="100" style="width:0">
                    %0
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <input id="submitButton" class="form-control btn btn-dark" type="submit" value="Submit"
                   onclick="submitFile();" disabled>
        </td>
    </tr>
    </tbody>
</table>

<%--</form>--%>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/scripts.js"></script>
</body>
</html>
