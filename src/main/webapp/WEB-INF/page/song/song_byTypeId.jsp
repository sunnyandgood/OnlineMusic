<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/12
  Time: 13:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>歌曲信息</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <table border="1" cellspacing="0" width="100%">
                        <tr>
                            <td></td>
                            <td align="center">操作</td>
                            <td>歌曲标题</td>
                            <td>歌手</td>
                            <td align="center">歌曲类型</td>
                            <td align="center">点击次数</td>
                            <td align="center">下载次数</td>
                            <td align="center">上传时间</td>
                        </tr>
                        <c:forEach var="songDisplayBean" items="${songDisplayBeans }">
                            <tr>
                                <td align="center"><c:out value="${songDisplayBean.song_url }" /></td>
                                <td align="center">
                                    <a href="/SongUtilServlet?state=click&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                        <i class="fa fa-heart-o"></i>
                                    </a>
                                    &nbsp;&nbsp;
                                    <a href="/SongUtilServlet?state=listen&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                        <i class="fa fa-headphones"></i>
                                    </a>
                                    &nbsp;&nbsp;
                                    <a href="/SongUtilServlet?state=download&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                        <i class="fa fa-download"></i>
                                    </a>
                                </td>
                                <td><c:out value="${songDisplayBean.song_name }" /></td>
                                <td><c:out value="${songDisplayBean.song_singer }" /></td>
                                <td align="center"><c:out value="${songDisplayBean.type_name }" /></td>
                                <td align="center"><c:out value="${songDisplayBean.song_clicks }" /></td>
                                <td align="center"><c:out value="${songDisplayBean.song_download }" /></td>
                                <td align="center"><c:out value="${songDisplayBean.song_uptime }" /></td>
                            </tr>
                        </c:forEach>
                    </table>
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


</script>
</html>
