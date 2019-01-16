package com.edu.servlet;

import com.edu.bean.SongBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.UserBean;
import com.edu.bean.VipBean;
import com.edu.service.SongService;
import com.edu.service.SongTypeService;
import com.edu.service.UserService;
import com.edu.service.VipService;
import com.edu.service.impl.SongServiceImpl;
import com.edu.service.impl.SongTypeServiceImpl;
import com.edu.service.impl.UserServiceImpl;
import com.edu.service.impl.VipServiceImpl;
import com.edu.util.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/15 11:04
 */
public class UserUtilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();
    private SongTypeService songTypeService = new SongTypeServiceImpl();
    private VipService vipService = new VipServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Gson gson = new Gson();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("userInfo".equals(state)){
            this.userInfo(request,response);
        }else if ("selectSongType".equals(state)){
            this.selectSongType(request,response);
        }else if ("addUser".equals(state)){
            try {
                this.addUser(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if ("signOut".equals(state)){
            this.signOut(request,response);
        }else if ("queryUserById".equals(state)){
            this.queryUserById(request,response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("updatePassword".equals(state)){
            this.updatePassword(request,response);
        }else if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request,response);
        }else if ("addSong".equals(state)){
            this.addSong(request,response);
        }


    }

    private void addSong(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        SongService songService = new SongServiceImpl();
        //得到上传路径的硬盘路径
        String dir = request.getServletContext().getRealPath("/resources/upload/");
        String songPath = request.getParameter("songPath");

        String path = dir + songPath;
        String newPath = "";

        String[] split = path.split("\\\\");
        for (int i=0;i<split.length-1;i++){
            newPath += split[i];
            newPath += "/";
        }
        newPath += split[split.length-1];

        System.out.println(path);
        System.out.println(newPath);

        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        List<UserBean> userBeans = userService.selectById(user_id);
        String user_name = userBeans.get(0).getUser_name();

        String song_name = request.getParameter("song_name");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));
        String song_size = request.getParameter("song_size");
        String song_format = request.getParameter("song_format");
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));

        SongBean songBean = new SongBean();
        songBean.setSong_name(song_name);
        songBean.setSong_singer(user_name);
        songBean.setType_id(type_id);
        songBean.setSong_size(song_size);
        songBean.setSong_url(newPath);
        songBean.setSong_format(song_format);
        songBean.setSong_clicks(0);
        songBean.setSong_download(0);
        songBean.setSong_uptime(new Date());
        songBean.setVip_id(vip_id);

        Boolean flag = songService.insert(songBean);
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectVipAndSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer userId = Integer.parseInt(request.getParameter("userId"));

        List<UserBean> userBeans = userService.selectById(userId);
        Integer vip_id = userBeans.get(0).getVip_id();

        List<VipBean> vipBeanList = vipService.selectVip(vip_id);
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("user_id",userId);
        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/user/user_addSong_jsp").forward(request, response);
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        String userName = request.getParameter("userName");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        Boolean flag = false;

        List<UserBean> list = userService.listAll();
        for (UserBean userBean : list){
            if (userName.equals(userBean.getUser_name()) &&
                    oldPassword.equals(userBean.getUser_password())){
                userBean.setUser_password(newPassword);
                flag = userService.updateById(userBean);
                break;
            }
        }

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println(userId);

        HttpSession session = request.getSession();
        session.removeAttribute("userLoginId");

        Boolean flag = userService.deletById(userId);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);

//        if (flag){
//            request.getRequestDispatcher("/SongUtilServlet?state=querySongs").forward(request,response);
//        }else {
//            request.getRequestDispatcher("/page/user/user_updatePassword_jsp").forward(request,response);
//        }
    }


    private void queryUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        List<UserBean> userBeans = userService.selectById(userId);

        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.setAttribute("userBeans",userBeans);
        request.getRequestDispatcher("/page/user/user_update_jsp").forward(request,response);
    }

    private void signOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        session.removeAttribute("userLoginId");

        request.getRequestDispatcher("./index.jsp").forward(request,response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        List<VipBean> vipBeanList = vipService.listAll();

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

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/user/user_register_jsp").forward(request,response);
    }

    private void userInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userLoginId");

        System.out.println(userId);

        if (null!=userId){
            Integer user_id = (Integer) userId;
            List<UserBean> userBeans = userService.selectById(user_id);
            request.setAttribute("userBeans",userBeans);
            request.getRequestDispatcher("/page/user/user_index_jsp").forward(request,response);
        }else {
            request.getRequestDispatcher("/page/user/user_tips_jsp").forward(request,response);
        }
    }
}
