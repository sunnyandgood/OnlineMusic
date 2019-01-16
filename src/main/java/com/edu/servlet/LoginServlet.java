package com.edu.servlet;

import com.edu.bean.ManagerBean;
import com.edu.bean.UserBean;
import com.edu.service.ManagerService;
import com.edu.service.UserService;
import com.edu.service.impl.ManagerServiceImpl;
import com.edu.service.impl.UserServiceImpl;
import com.edu.util.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/11 13:20
 */
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson = new Gson();
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
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession();

        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        List<UserBean> list = userService.listAll();
        Boolean flag = false;
        for (UserBean userBean : list){
            if (userName.equals(userBean.getUser_name()) &&
                    userPassword.equals(userBean.getUser_password())){
                flag = true;
                session.setAttribute("userLoginId",userBean.getUser_id());
                break;
            }
        }

        R r = null;

        if (flag){
            r = R.ok();
        }else {
            r = R.error("登录失败！");
        }

        String json = gson.toJson(r);
        printWriter.print(json);

//        if (flag){
//            request.getRequestDispatcher("/UserUtilServlet?state=userInfo").forward(request,response);
//        }else {
//            request.getRequestDispatcher("./user/user_login.jsp").forward(request,response);
//        }
    }

    private void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession();

        String adminName = request.getParameter("adminName");
        String adminPassword = request.getParameter("adminPassword");

        System.out.println(adminName);
        System.out.println(adminPassword);

        List<ManagerBean> list = managerService.listAll();
        Boolean flag = false;
        for (ManagerBean managerBean : list){
            if (adminName.equals(managerBean.getManager_name()) &&
                    adminPassword.equals(managerBean.getManager_password())){
                flag = true;
                session.setAttribute("adminLoginId",managerBean.getManager_id());
                break;
            }
        }

        R r = null;

        if (flag){
            r = R.ok("登录成功！");
        }else {
            r = R.error("登录失败！");
        }

        String json = gson.toJson(r);
//        String json = "{\"code\":" + "\"200\"" + ",\"message\":" + "\"kjhik\"" + "}";
        System.out.println(json);
        printWriter.print(json);

//        if (flag){
//            request.getRequestDispatcher("./admin/admin_index.jsp").forward(request,response);
//        }else {
//            request.getRequestDispatcher("./admin_login.jsp").forward(request,response);
//        }
    }

}
