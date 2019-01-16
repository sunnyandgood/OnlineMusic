<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/11
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content" id="parentDiv">
                    <form  class="form-horizontal" method="post">
                        <c:forEach var="userBean" items="${userBeans }">
                            <input type="hidden" name="user_id" value="<c:out value="${userBean.user_id }" />" />

                            <div class="form-group">
                                <label class="col-sm-2 control-label">用户名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="user_name" readonly="readonly" value="<c:out value="${userBean.user_name}"/>"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">vip等级</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="vip" readonly="readonly" value="<c:out value="${userBean.vip}"/>"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">生日</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="user_birthday" readonly="readonly" value="<c:out value="${userBean.user_birthday}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">性别</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="user_gender" readonly="readonly" value="<c:out value="${userBean.user_gender}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">喜欢的歌曲类型</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="type_name" readonly="readonly" value="<c:out value="${userBean.type_name}"/>"/>
                                </div>
                            </div>

                        </c:forEach>
                    </form>

                    <div align="center">
                        <div class="btn btn-primary" onclick="updateUserInfo()" style="width: 110px">修改个人信息</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn btn-primary" onclick="updatePassword()" style="width: 110px">修改密码</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn btn-primary" onclick="uploadMusic()" style="width: 110px">上传音乐</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn btn-primary" onclick="recharge()" style="width: 110px">充值</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <div class="btn btn-primary" onclick="logOff()" style="width: 110px">注销</div>
                    </div>
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

    function updateUserInfo(){//修改个人信息

        window.location.href="${ctx}/UserUtilServlet?state=queryUserById&userId=" + $('[name=user_id]').val();
        <%--layer.open({--%>
            <%--type: 2,--%>
            <%--area: ['800px', '500px'],--%>
            <%--content: '${ctx}/UserUtilServlet?state=queryUserById&userId=' + $('[name=user_id]').val() //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']--%>
        <%--});--%>
    }

    function updatePassword(){//修改密码
        window.location.href="/page/user/user_updatePassword_jsp";
        <%--layer.open({--%>
            <%--type: 2,--%>
            <%--area: ['800px', '500px'],--%>
            <%--content: '${ctx}/user/user_updatePassword.jsp' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']--%>
        <%--});--%>
    }

    function uploadMusic(){//上传音乐
        // window.location.href="/UserUtilServlet?state=selectVipAndSongType&userId=" + $('[name=user_id]').val();

        layer.open({
            type: 2,
            area: ['800px', '450px'],
            content: '${ctx}/UserUtilServlet?state=selectVipAndSongType&userId=' + $('[name=user_id]').val() //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }

    function recharge(){//充值

        layer.open({
            type: 2,
            area: ['800px', '450px'],
            content: '${ctx}/SongUtilServlet?state=selectVip&userId=' + $('[name=user_id]').val() //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
        // window.location.href="/UserUtilServlet?state=selectVipAndSongType&userId=" + $('[name=user_id]').val();
        <%--$.post('${ctx}/UserUtilServlet?state=recharge&userId=' + $('[name=user_id]').val(),function (r) {--%>
            <%--parent.$('#table').bootstrapTable('refresh');--%>
            <%--var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引--%>
            <%--parent.layer.close(index); //再执行关闭--%>
            <%--layer.msg(r.message);--%>
        <%--});--%>
    }

    function logOff(){//注销
        $(document).ready(function () {
            $.ajax({
                type:"post",//提交方式
                dataType: "json",//预期服务器返回的数据类型
                url:"${ctx}/UserUtilServlet?state=deleteById&userId=" + $('[name=user_id]').val(),//传输路径
                success:function (msg) {
                    if (msg.code==200){
                        layer.msg(msg.message);
                        parent.location.reload();
                    } else {
                        alert(msg.message);
                        window.location.href="${ctx}/page/user/user_updatePassword_jsp";
                    }
                },
                error:function () {
                    alert("异常！");
                }
            });
        });

        // window.location.href="/UserUtilServlet?state=deleteById&userId=" + $('[name=user_id]').val();
        <%--$.post('${ctx}/UserUtilServlet?state=deleteById&userId=' + $('[name=user_id]').val(),function (r) {--%>
            <%--// location.replace(document.referrer);--%>
            <%--parent.location.reload();--%>
        <%--});--%>
    }


</script>
</html>

