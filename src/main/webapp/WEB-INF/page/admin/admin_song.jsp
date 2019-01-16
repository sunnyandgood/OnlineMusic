<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/7
  Time: 17:25
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>音乐管理</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="${ctx}/resources/css/bootstrap-table/bootstrap-table.min.css"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="ibox float-e-margins">
            <div class="ibox-title">
                音乐展示
            </div>
            <div class="ibox-content">
                <table id="table">

                </table>
                <div class="btn btn-primary" onclick="removeAll()">批量删除</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <div class="btn btn-primary" onclick="add()">添加</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <div class="btn btn-primary" onclick="addToExcel()">导出到Excel</div>

            </div>
        </div>
    </div>
</div>
</body>
<jsp:include page="/resources/layout/_script.jsp"/>
<script src="${ctx}/resources/js/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx}/resources/js/bootstrap-table/locale/bootstrap-table-zh-CN.js"></script>
<script>
    //初始化表格,动态从服务器加载数据
    $('#table').bootstrapTable({
        //获取数据的Servlet地址
        url:'${ctx}/SongServlet?state=listAll',
        columns: [{
            checkbox:true
        }, {
            title: '序号',
            formatter:function(value,row,index){
                //return index+1; //序号正序排序从1开始
                var pageSize = $('#table').bootstrapTable('getOptions').pageSize;//通过表的#id 可以得到每页多少条
                var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;//通过表的#id 可以得到当前第几页
                return pageSize * (pageNumber - 1) + index + 1;    //返回每条的序号： 每页条数 * （当前页 - 1 ）+ 序号
            }
        }, {
            field: 'song_id',
            title: 'id'
        }, {
            field: 'song_name',
            title: '歌曲名字'
        }, {
            field: 'song_singer',
            title: '歌手'
        }, {
            field: 'type_name',
            title: '歌曲类型'
        },{
            field: 'song_size',
            title: '文件大小'
        },{
            field: 'song_url',
            title: '文件地址'
        },{
            field: 'song_format',
            title: '歌曲格式'
        },{
            field: 'song_clicks',
            title: '点击次数'
        },{
            field: 'song_download',
            title: '下载次数'
        },{
            field: 'song_uptime',
            title: '上传时间'
        },{
            field: 'vip',
            title: 'vip等级'
        },{
            field: 'caozuo',
            title: '操作',
            formatter:function(v1,v2,v3){
                return ['<a class="update" href="javascript:void(0)" title="Update">',
                    '<i class="fa fa-pencil"></i>',
                    '</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
                    '<a class="remove" href="javascript:void(0)" title="Remove">',
                    '<i class="glyphicon glyphicon-remove"></i>',
                    '</a>'].join('');
            },
            events:'caocuoEvents'
        }],
        pagination:true,//启动分页
        pageSize:5,//每页显示的记录数
        pageList:[5,10,15,20,25,30],//记录数可选列表
        search:true,//是否启用查询
        showRefresh:true,//显示刷新按钮
        showColumns:true,//显示下拉框勾选要显示的列
        clickToSelect:true,
        sidePagination:'server',//表示服务端请求
        queryParamsType:'',

        onLoadSuccess: function(){  //加载成功时执行
            layer.msg("加载成功");
        },
        onLoadError: function(){  //加载失败时执行
            layer.msg("加载数据失败", {time : 1500, icon : 2});
        }
    });
    window.caocuoEvents = {
        'click .update': function (e, value, row) {
            layer.open({
                type: 2,
                area: ['800px', '450px'],
                content: '${ctx}/SongServlet?state=selectById&songId=' + row['song_id'] //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
        },
        'click .remove': function (e, value, row) {
            if(confirm('是否删除')){
                $(document).ready(function () {
                    $.ajax({
                        type:"post",//提交方式
                        dataType: "json",//预期服务器返回的数据类型
                        url:"${ctx}/SongServlet?state=deleteById&songId=" + row['song_id'],//传输路径
                        success:function (msg) {
                            if (msg.code==200){
                                layer.msg(msg.message);
                                $('#table').bootstrapTable('refresh');
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
    };

    function removeAll() {
        if (confirm("全部删除？")){
            arr = $('#table').bootstrapTable('getSelections');
            str = "";
            for(i in arr){
                str += arr[i]['song_id'] + ",";
            }
            ids = str.substring(0,str.length-1);

            $(document).ready(function () {
                $.ajax({
                    type:"post",//提交方式
                    dataType: "json",//预期服务器返回的数据类型
                    url:"${ctx}/SongServlet?state=deleteByIds&songIds=" + ids,//传输路径
                    success:function (msg) {
                        if (msg.code==200){
                            layer.msg(msg.message);
                            $('#table').bootstrapTable('refresh');
                        } else {
                            alert(msg.message);
                        }
                    },
                    error:function () {
                        alert("异常！");
                    }
                });
            });

            <%--$.post('${ctx}/SongServlet?state=deleteByIds&songIds=' + ids,function (r) {--%>
                <%--// if(r.code==200){--%>
                    <%--$('#table').bootstrapTable('refresh');--%>
                <%--// }--%>
                <%--// layer.msg(r.message);--%>
            <%--});--%>
        }
    }

    function add(){
        layer.open({
            type: 2,
            area: ['800px', '450px'],
            content: '${ctx}/SongServlet?state=selectVipAndSongType' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
        });
    }

    function addToExcel() {
        $.post('${ctx}/SongServlet?state=addToExcel',function (r) {
            // layer.msg(r.message);
            layer.msg("导出成功！");
        });
    }
</script>
</html>
