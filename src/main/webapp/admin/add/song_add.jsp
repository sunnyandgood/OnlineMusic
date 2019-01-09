<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/9
  Time: 10:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加用户</title>
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
                        <div class="form-group">
                            <label class="col-sm-2 control-label">歌曲名字</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control"  name="song_name"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">歌手</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control"  name="song_singer">
                            </div>
                        </div>

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">歌曲类型</label>
                            <div class="col-sm-10">
                                <label class="radio-inline">
                                    <input type="radio" value="1"  name="type_id">
                                    很简单
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="2"  name="type_id">
                                    简单
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="3"  name="type_id">
                                    适中
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="4"  name="type_id">
                                    难
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" value="5"  name="type_id">
                                    很难
                                </label>
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

                        <div class="hr-line-dashed"></div>
                        <div class="form-group">
                            <div class="col-sm-4 col-sm-offset-2">
                                <div class="btn btn-primary" onclick="insert()">保存内容</div>
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

    function insert(){

        alert($('[name=adminName]').val());
        alert($('[name=adminPassword]').val());

        if($('[name=adminName]').val()=='') {
            layer.msg("用户名不能为空");
        }else if($('[name=adminPassword]').val()==''){
            layer.msg("密码不能为空");
        }else {
            $.post('${ctx}/admin/insert',$('form').serialize(),function (r) {
                if(r.code==200){
                    parent.$('#table').bootstrapTable('refresh');
                    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                    parent.layer.close(index); //再执行关闭
                }
                layer.msg(r.message);
            });
        }
    }
</script>
</html>
