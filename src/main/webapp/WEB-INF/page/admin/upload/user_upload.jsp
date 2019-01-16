<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/10
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息上传</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/Huploadify/Huploadify.css">
</head>
<body>
<h1>上传用户信息</h1>
<form id="form1">
    <div id="userUpload" name="userPath"></div>
    <div class="btn btn-primary" onclick="add()">提交</div>
</form>
</body>
<jsp:include page="/resources/layout/_script.jsp"/>
<script src="${ctx}/resources/js/Huploadify/jquery.Huploadify.js"></script>
<script src="${ctx}/resources/js/my.js"></script>
<script>
    upload($('#userUpload'),false,'${ctx}/UtilServlet?state=upload','${ctx}');

    function add() {
        $(document).ready(function () {
            $.ajax({
                type:"post",//提交方式
                data:$('#form1').serialize(),//传输的数据
                dataType: "json",//预期服务器返回的数据类型
                url:"${ctx}/UserServlet?state=addFromExcel",//传输路径
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
        <%--$.post('${ctx}/UserServlet?state=addFromExcel',$('form').serialize(),function (r) {--%>
            <%--var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引--%>
            <%--parent.layer.close(index); //再执行关闭--%>
            <%--layer.msg("上传成功");--%>
        <%--});--%>
    }
</script>
</html>
