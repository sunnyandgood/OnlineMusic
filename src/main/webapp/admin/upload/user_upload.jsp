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
<form>
    <div id="userUpload" name="userPath"></div>
    <div class="btn btn-primary" onclick="add()">提交</div>
</form>
</body>
<jsp:include page="/resources/layout/_script.jsp"/>
<script src="${ctx}/resources/js/Huploadify/jquery.Huploadify.js"></script>
<script src="${ctx}/resources/js/my.js"></script>
<script>
    upload($('#userUpload'),false,'${ctx}/UserServlet?state=upload','${ctx}');

    function add() {
        $.post('${ctx}/UserServlet?state=addFromExcel',$('form').serialize(),function (r) {
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
            layer.msg("上传成功");
        });
    }
</script>
</html>
