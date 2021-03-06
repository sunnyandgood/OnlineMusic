<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/9
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>歌曲类型信息修改</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form  class="form-horizontal" method="post" id="form1">
                        <c:forEach var="songtypeBean" items="${songtypeBeanList }">
                            <input type="hidden" name="type_id" value="<c:out value="${songtypeBean.type_id }" />" />

                            <div class="form-group">
                                <label class="col-sm-2 control-label">歌曲类型</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="type_name" value="<c:out value="${songtypeBean.type_name}"/>">
                                </div>
                            </div>
                        </c:forEach>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <div class="btn btn-primary" onclick="save()">保存内容</div>
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
    function save(){
        $(document).ready(function () {
            $.ajax({
                type:"post",//提交方式
                dataType: "json",//预期服务器返回的数据类型
                data:$('#form1').serialize(),//传输的数据
                url:"${ctx}/SongTypeServlet?state=updateById",//传输路径
                success:function (msg) {
                    if (msg.code==200){
                        layer.msg(msg.message);
                        parent.$('#table').bootstrapTable('refresh');
                        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
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
</script>
</html>
