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
                <div class="ibox-content">
                    <form  class="form-horizontal" method="post">
                        <c:forEach var="userBean" items="${userDisplayBeans }">
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

                    <div class="hr-line-dashed"></div>
                    <div class="form-group">
                        <div class="col-sm-4 col-sm-offset-2">
                            <div class="btn btn-primary" onclick="">保存内容</div>
                        </div>
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

    function save(){
        $.post('${ctx}/UserServlet?state=updateById',$('form').serialize(),function (r) {
            parent.$('#table').bootstrapTable('refresh');
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
            layer.msg(r.message);
        });
    }


</script>
</html>

