<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: sunny
  Date: 2019/1/9
  Time: 20:50
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>管理员信息修改</title>
    <jsp:include page="/resources/layout/_css.jsp"/>
    <link rel="stylesheet" href="${ctx}/resources/css/datapicker/bootstrap-datepicker.css">
</head>
<body style="padding-top: 40px">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <form  class="form-horizontal" method="post" action="${ctx}/ManagerServlet?state=updateById">
                        <c:forEach var="managerBean" items="${managerBeanList }">
                            <input type="hidden" name="manager_id" value="<c:out value="${managerBean.manager_id }" />" />

                            <div class="form-group">
                                <label class="col-sm-2 control-label">管理员名字</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="manager_name" value="<c:out value="${managerBean.manager_name}"/>"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label">管理员密码</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control"  name="manager_password" value="<c:out value="${managerBean.manager_password}"/>">
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
</html>
