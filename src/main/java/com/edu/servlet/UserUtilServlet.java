package com.edu.servlet;

import com.edu.bean.*;
import com.edu.service.UserService;
import com.edu.service.UtilService;
import com.edu.service.impl.UserServiceImpl;
import com.edu.service.impl.UtilServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/11 16:23
 */
public class UserUtilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UtilService utilService = new UtilServiceImpl();
    private UserService userService = new UserServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("selectSongType".equals(state)){
            this.selectSongType(request,response);
        }else if ("addUser".equals(state)){
            try {
                this.addUser(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("userInfo".equals(state)){
            this.userInfo(request,response);
        }else if ("signOut".equals(state)){
            this.signOut(request,response);
        }else if ("querySongType".equals(state)){
            this.querySongType(request,response);
        }else if ("querySongByTypeId".equals(state)){
            this.querySongByTypeId(request,response);
        }else if ("querySongs".equals(state)){
            this.querySongs(request,response);
        }else if ("query".equals(state)){
            this.query(request,response);
        }
    }

    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String queryInfo = request.getParameter("queryInfo");
        List<SongDisplayBean> songDisplayBeans = utilService.selectSongs(queryInfo);
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/song/query.jsp").forward(request,response);
    }

    private void querySongs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongDisplayBean> songDisplayBeans = utilService.selectSongs();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/song/song_index.jsp").forward(request,response);
    }

    private void querySongByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer typeId = Integer.parseInt(request.getParameter("typeId"));
        System.out.println(typeId);

        List<SongDisplayBean> songDisplayBeans = utilService.selectSongByTypeId(typeId);
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }

        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/song/song_bytype.jsp").forward(request,response);
    }

    private void querySongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        session.removeAttribute("userId");

        request.getRequestDispatcher("./index.jsp").forward(request,response);
    }

    private void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        System.out.println(userId);
        if (null!=userId){
            Integer user_id = (Integer) userId;
            List<UserDisplayBean> userDisplayBeans = utilService.selectByUserId(user_id);
            request.setAttribute("userDisplayBeans",userDisplayBeans);
            request.getRequestDispatcher("/user/user_index.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("/user/user_tips.jsp").forward(request,response);
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
        if (flag){
            request.getRequestDispatcher("./user/user_login.jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("./user/user_register.jsp").forward(request,response);
        }

    }

    private void selectSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/user/user_register.jsp").forward(request,response);
    }
}
