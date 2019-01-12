package com.edu.servlet;

import com.edu.bean.*;
import com.edu.service.SongService;
import com.edu.service.UserService;
import com.edu.service.UtilService;
import com.edu.service.impl.SongServiceImpl;
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
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("queryUserById".equals(state)){
            this.queryUserById(request,response);
        }else if ("updatePassword".equals(state)){
            this.updatePassword(request,response);
        }else if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request, response);
        }else if ("addSong".equals(state)){
            this.addSong(request,response);
        }else if ("hotSearch".equals(state)){
            this.hotSearch(request,response);
        }else if ("hotDownload".equals(state)){
            this.hotDownload(request,response);
        }else if ("click".equals(state)){
            this.click(request,response);
        }
    }

    private void click(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        if (null!=userId){
            Integer song_id = Integer.parseInt(request.getParameter("song_id"));
            Boolean flag = utilService.click((Integer) userId, song_id);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        }
    }

    private void hotDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongDisplayBean> songDisplayBeans = utilService.hotDownload();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans2",songDisplayBeans);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void hotSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongDisplayBean> songDisplayBeans = utilService.hotSearch();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans1",songDisplayBeans);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void addSong(HttpServletRequest request, HttpServletResponse response) {
        SongService songService = new SongServiceImpl();
        //得到上传路径的硬盘路径
        String dir = request.getServletContext().getRealPath("/resources/upload");
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
    }

    private void selectVipAndSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer userId = Integer.parseInt(request.getParameter("userId"));

        List<UserBean> userBeans = userService.selectById(userId);
        Integer vip_id = userBeans.get(0).getVip_id();

        List<VipBean> vipBeanList = utilService.selectVip(vip_id);
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("user_id",userId);
        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("./user/user_addSong.jsp").forward(request, response);
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        String userName = request.getParameter("userName");
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        List<UserBean> list = userService.listAll();
        for (UserBean userBean : list){
            if (userName.equals(userBean.getUser_name()) &&
                    oldPassword.equals(userBean.getUser_password())){
                userBean.setUser_password(newPassword);
                userService.updateById(userBean);
                break;
            }
        }
    }

    private void queryUserById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        List<UserBean> userBeans = userService.selectById(userId);

        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.setAttribute("userBeans",userBeans);
        request.getRequestDispatcher("/user/user_update.jsp").forward(request,response);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        System.out.println(userId);

        HttpSession session = request.getSession();
        session.removeAttribute("userId");

        Boolean flag = userService.deleteById(userId);
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
