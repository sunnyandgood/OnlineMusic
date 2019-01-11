package com.edu.servlet;

import com.edu.bean.ManagerBean;
import com.edu.service.ManagerService;
import com.edu.service.UserService;
import com.edu.service.impl.ManagerServiceImpl;
import com.edu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/11 13:20
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ManagerService managerService = new ManagerServiceImpl();
    private UserService userService = new UserServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("adminLogin".equals(state)) {
            this.adminLogin(request,response);
        }
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        String adminName = request.getParameter("adminName");
        String adminPassword = request.getParameter("adminPassword");

        List<ManagerBean> list = managerService.listAll();
        Boolean flag = false;
        for (ManagerBean managerBean : list){
            if (adminName.equals(managerBean.getManager_name()) &&
                    adminPassword.equals(managerBean.getManager_password())){
                flag = true;
                break;
            }
        }

        if (flag){
            request.getRequestDispatcher("./admin/admin_index.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("./admin_login.jsp").forward(request,response);
        }
    }

}
