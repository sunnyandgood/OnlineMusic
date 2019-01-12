package com.edu.servlet;

import com.edu.bean.ManagerBean;
import com.edu.bean.UserBean;
import com.edu.service.ManagerService;
import com.edu.service.UserService;
import com.edu.service.impl.ManagerServiceImpl;
import com.edu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/11 13:20
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ManagerService managerService = new ManagerServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("userLogin".equals(state)) {
            this.userLogin(request,response);
        }else if ("adminLogin".equals(state)){
            this.adminLogin(request,response);
        }
    }

    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();

        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        List<UserBean> list = userService.listAll();
        Boolean flag = false;
        for (UserBean userBean : list){
            if (userName.equals(userBean.getUser_name()) &&
                    userPassword.equals(userBean.getUser_password())){
                flag = true;
                session.setAttribute("userId",userBean.getUser_id());
                break;
            }
        }

        if (flag){
            request.getRequestDispatcher("./index.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("./user/user_login.jsp").forward(request,response);
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
