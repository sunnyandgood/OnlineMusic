<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/11
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div align="center"><h3>音乐库管理系统</h3></div>
                    <form  class="form-horizontal" method="post" id="form1">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control"  name="user_name"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">用户密码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control"  name="user_password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">生日</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control"  name="user_birthday">
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">性别</label>
                        <div class="col-sm-10">
                            <label class="radio-inline">
                                <input type="radio" value="男"  name="user_gender">
                                男
                            </label>
                            <label class="radio-inline">
                                <input type="radio" value="女"  name="user_gender">
                                女
                            </label>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">喜欢的歌曲类型</label>
                        <div class="col-sm-10">
                            <c:forEach var="songtypeBean" items="${songtypeBeanList }">
                                <label class="radio-inline">
                                    <input type="radio" name="type_id" value="<c:out value="${songtypeBean.type_id }" />"/>
                                    <c:out value="${songtypeBean.type_name }" />
                                </label>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-2">
                            <div class="btn btn-primary" >
                                <input type="button" value="注册" onclick="register()" style="background: #1AB394;border: 0px;color: #FFF7FB;"/>
                            </div>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <div class="btn btn-primary" >
                                <a href="${ctx}/user_login.jsp" style="background: #1AB394;border: 0px;color: #FFF7FB;">登录</a>
                            </div>
                        </div>
                    </div>
                </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/resources/layout/_script.jsp"/>
<script src="${ctx}/resources/js/datapicker/bootstrap-datepicker.js"></script>
<script src="${ctx}/resources/js/datapicker/bootstrap-datepicker.zh-CN.min.js"></script>
<script>

    //datepicker:
    $('[name=user_birthday]').datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        orientation: "top left",
        autoclose: true,
        todayHighlight: true
    });

    function register(){
        if($('[name=user_name]').val()=='') {
            layer.msg("用户名不能为空");
        }else if($('[name=user_password]').val()==''){
            layer.msg("用户密码不能为空");
        }else if($('[name=user_birthday]').val()==''){
            layer.msg("生日不能为空");
        }else if($('[name=user_gender]').val()==''){
            layer.msg("性别不能为空");
        }else if($('[name=type_id]').val()==''){
            layer.msg("喜欢的歌曲类型不能为空");
        }else {
            $(document).ready(function () {
                $.ajax({
                    type:"post",//提交方式
                    dataType: "json",//预期服务器返回的数据类型
                    data:$('#form1').serialize(),//传输的数据
                    url:"${ctx}/UserUtilServlet?state=addUser",//传输路径
                    success:function (msg) {
                        if (msg.code==200){
                            layer.msg(msg.message);
                            window.location.href="${ctx}/user_login.jsp";
                        } else {
                            alert(msg.message);
                        }
                    },
                    error:function () {
                        alert("异常！");
                    }
                });
            });
        }
    }
</script>
</html>
