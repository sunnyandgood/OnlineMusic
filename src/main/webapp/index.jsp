<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/11
  Time: 20:54
  To change this template use File | Settings | File Templates.
--%>
<%@page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">


    <title>音乐库管理系统</title>

    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <jsp:include page="/resources/layout/_css.jsp"/>
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="${ctx}/resources/img/user/profile_small.jpg" /></span>
                    </div>
                    <div class="logo-element">H+</div>
                </li>

                <li>
                    <a href="#">
                        <i class="fa fa-user"></i>
                        <span class="nav-label">用户</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${ctx}/UserUtilServlet?state=userInfo" data-index="0">用户信息</a>
                        </li>
                    </ul>
                </li>

                <li>
                    <a href="#">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">音乐库</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="${ctx}/SongUtilServlet?state=querySongType">音乐类别</a>
                        </li>
                        <c:forEach var="songtypeBean" items="${songtypeBeanList }">
                            <li>
                                <a class="J_menuItem" href="${ctx}/SongUtilServlet?state=querySongByTypeId&typeId=<c:out value="${songtypeBean.type_id }" />"   data-index="0"><c:out value="${songtypeBean.type_name }" /></a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题&排行榜
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>

            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="${ctx}/SongUtilServlet?state=querySongs">首页</a>
                </div>
            </nav>

            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>

            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${ctx}/UserUtilServlet?state=signOut" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${ctx}/SongUtilServlet?state=querySongs" frameborder="0" data-id="${ctx}/SongUtilServlet?state=querySongs" seamless>

            </iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2018-2020 <a href="https://sunnyandgood.github.io/" target="_blank">wrh</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">

            <ul class="nav nav-tabs navs-3">

                <li class="active">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#tab-2">
                        热搜排行榜
                    </a>
                </li>
                <li>
                    <a data-toggle="tab" href="#tab-3">
                        下载排行榜
                    </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                                <span>
                        固定宽度
                    </span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                        </div>
                    </div>
                </div>

                <div id="tab-2" class="tab-pane">
                    <div>
                        <a href="/SongUtilServlet?state=hotSearch">热搜</a>
                        <c:forEach var="songDisplayBean" items="${songDisplayBeans1 }">
                            <div class="sidebar-message">
                                <div class="pull-left text-center">
                                    <div class="img-circle message-avatar">
                                        <td align="center"><c:out value="${songDisplayBean.song_url }" /></td>
                                    </div>

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    <div align="center">
                                        <h5><c:out value="${songDisplayBean.song_name }" /></h5>
                                    </div>
                                    <div align="center">
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
                                    </div>
                                    <br>
                                    <small class="text-muted">
                                        歌手:<c:out value="${songDisplayBean.song_singer }" />
                                    </small>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <div id="tab-3" class="tab-pane">
                    <div>
                        <a href="/SongUtilServlet?state=hotDownload">热载</a>
                        <c:forEach var="songDisplayBean" items="${songDisplayBeans2 }">
                            <div class="sidebar-message">
                                <div class="pull-left text-center">
                                    <div class="img-circle message-avatar">
                                        <td align="center"><c:out value="${songDisplayBean.song_url }" /></td>
                                    </div>

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    <div align="center">
                                        <h5><c:out value="${songDisplayBean.song_name }" /></h5>
                                    </div>
                                    <div align="center">
                                        <a href="/SongUtilServlet?state=click&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                            <i class="fa fa-heart-o"></i>
                                        </a>
                                        <%--<input type="hidden" name="song_id" value="<c:out value="${songDisplayBean.song_id }" />"/>--%>
                                        <%--<div onclick="click()">--%>
                                            <%--<i class="fa fa-heart-o"></i>--%>
                                        <%--</div>--%>
                                        &nbsp;&nbsp;
                                        <a href="/SongUtilServlet?state=listen&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                            <i class="fa fa-headphones"></i>
                                        </a>
                                        &nbsp;&nbsp;
                                        <a href="/SongUtilServlet?state=download&song_id=<c:out value="${songDisplayBean.song_id }" />">
                                            <i class="fa fa-download"></i>
                                        </a>
                                    </div>
                                    <br>
                                    <small class="text-muted">
                                        歌手:<c:out value="${songDisplayBean.song_singer }" />
                                    </small>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <!--右侧边栏结束-->
</div>

<jsp:include page="/resources/layout/_script.jsp"/>

</body>

</html>
