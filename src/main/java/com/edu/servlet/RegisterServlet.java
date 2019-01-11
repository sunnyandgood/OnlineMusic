package com.edu.servlet;

import com.edu.bean.SongtypeBean;
import com.edu.bean.UserBean;
import com.edu.bean.VipBean;
import com.edu.service.UserService;
import com.edu.service.UtilService;
import com.edu.service.impl.UserServiceImpl;
import com.edu.service.impl.UtilServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/11 16:23
 */
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilService utilService = new UtilServiceImpl();
    private UserService userService = new UserServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request,response);
        }else if ("addUser".equals(state)){
            try {
                this.addUser(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ParseException, ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<VipBean> vipBeanList = utilService.selectVip();

        String user_name = request.getParameter("user_name");
        String user_password = request.getParameter("user_password");
        Date user_birthday = simpleDateFormat.parse(request.getParameter("user_birthday") + " 00:00:00");
        String user_gender = request.getParameter("user_gender");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));

        UserBean userBean = new UserBean();

        userBean.setUser_name(user_name);
        userBean.setUser_password(user_password);
        for (VipBean vipBean : vipBeanList){
            if (vipBean.getVip().equals("普通用户")){
                userBean.setVip_id(vipBean.getVip_id());
                break;
            }
        }
        userBean.setUser_birthday(user_birthday);
        userBean.setUser_gender(user_gender);
        userBean.setType_id(type_id);

        Boolean flag = userService.insert(userBean);
        System.out.println(flag);
//        request.getRequestDispatcher("/user/user_login.jsp").forward(request,response);
        request.getRequestDispatcher("/user/user_login.jsp").forward(request,response);
        if (flag){

        }else {
            //request.getRequestDispatcher("/user/user_register.jsp").forward(request,response);
        }

    }

    private void selectVipAndSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/user/user_register.jsp").forward(request,response);
    }
}
