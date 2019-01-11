<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/11
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/userlogin/normalize.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/userlogin/demo.css" />
    <!--必要样式-->
    <link rel="stylesheet" type="text/css" href="${ctx}/resources/css/userlogin/component.css" />
    <!--[if IE]>
    <!--<script src="js/html5.js"></script>-->
    <![endif]-->
</head>
<body>
<div class="container demo-1">
    <div class="content">
        <div id="large-header" class="large-header">
            <canvas id="demo-canvas"></canvas>
            <div class="logo_box">
                <h3>音乐库管理系统</h3>
                <form action="/LoginServlet?state=userLogin" name="f" method="post">
                    <div class="input_outer">
                        <span class="u_user"></span>
                        <input name="userName" class="text" style="color: #FFFFFF !important" type="text" placeholder="请输入账户">
                    </div>
                    <div class="input_outer">
                        <span class="us_uer"></span>
                        <input name="userPassword" class="text" style="color: #FFFFFF !important; position:absolute; z-index:100;"value="" type="password" placeholder="请输入密码">
                    </div>
                    <div class="act-but submit" href="javascript:;" style="color: #FFFFFF">
                        <input type="submit" value="登录" style="background: #0096E6;border: 0px;color: #FFF7FB;"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div><!-- /container -->
</body>
<script src="${ctx}/resources/js/userlogin/TweenLite.min.js"></script>
<script src="${ctx}/resources/js/userlogin/EasePack.min.js"></script>
<script src="${ctx}/resources/js/userlogin/rAF.js"></script>
<script src="${ctx}/resources/js/userlogin/demo-1.js"></script>
<script src="${ctx}/resources/js/jquery/jquery-3.2.1.js"></script>
<script>
</script>
</html>
