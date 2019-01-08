<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/8
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>歌曲信息修改</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form  class="form-horizontal" method="post" action="${ctx}/SongServlet?state=updateById">
                        <c:forEach var="selectById" items="${selectById }">
                            <input type="hidden" name="song_id" value="<c:out value="${selectById.song_id }" />" />

                            <div class="form-group">
                                <label class="col-sm-2 control-label">歌曲名字</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_name" value="<c:out value="${selectById.song_name}"/>"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">歌手</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_singer" value="<c:out value="${selectById.song_singer}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">歌曲类型</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="type_id" value="<c:out value="${selectById.type_id}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">文件大小</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_size" value="<c:out value="${selectById.song_size}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">文件地址</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_url" value="<c:out value="${selectById.song_url}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">文件格式</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_format" value="<c:out value="${selectById.song_format}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">点击次数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_clicks" value="<c:out value="${selectById.song_clicks}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">下载次数</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_download" value="<c:out value="${selectById.song_download}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">上传时间</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="song_uptime" value="<c:out value="${selectById.song_uptime}"/>">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">vip等级</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="vip_id" value="<c:out value="${selectById.vip_id}"/>">
                                </div>
                            </div>

                        </c:forEach>
                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <input type="submit" class="btn btn-primary" value="保存内容">
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
    $('[name=song_uptime]').datepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        orientation: "top left",
        autoclose: true,
        todayHighlight: true
    });
</script>
</html>
