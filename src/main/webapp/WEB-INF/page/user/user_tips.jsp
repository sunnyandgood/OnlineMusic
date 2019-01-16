<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/12
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提示信息</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>

<body style="padding-top: 150px">
    <div align="center"><h1>您还未登录！</h1></div>
    <div class="hr-line-dashed"></div>
    <div class="form-group">
        <div align="center">
            <div class="btn btn-primary" >
                <a href="${ctx}/UserUtilServlet?state=selectSongType"  style="background: #1AB394;border: 0px;color: #FFF7FB;">注册</a>
            </div>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div class="btn btn-primary" >
                <a href="${ctx}/user_login.jsp" style="background: #1AB394;border: 0px;color: #FFF7FB;">登录</a>
            </div>
        </div>
    </div>
</body>
</html>
