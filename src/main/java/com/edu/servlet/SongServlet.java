package com.edu.servlet;

import com.edu.bean.SongBean;
import com.edu.bean.SongDisplayBean;
import com.edu.service.SongService;
import com.edu.service.impl.SongServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:01
 */
public class SongServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongService songService = new SongServiceImpl();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.displayAll(request, response);
        }else if("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            try {
                this.updateById(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateById(ServletRequest request, ServletResponse response) throws ParseException, ServletException, IOException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        String song_name = request.getParameter("song_name");
        String song_singer = request.getParameter("song_singer");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));
        String song_size = request.getParameter("song_size");
        String song_url = request.getParameter("song_url");
        String song_format = request.getParameter("song_format");
        Integer song_clicks = Integer.parseInt(request.getParameter("song_clicks"));
        Integer song_download = Integer.parseInt(request.getParameter("song_download"));

        Date song_uptime = simpleDateFormat.parse(request.getParameter("song_uptime")+" 00:00:00");
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));

        SongBean songBean = new SongBean();
        songBean.setSong_id(song_id);
        songBean.setSong_name(song_name);
        songBean.setSong_singer(song_singer);
        songBean.setType_id(type_id);
        songBean.setSong_size(song_size);
        songBean.setSong_url(song_url);
        songBean.setSong_format(song_format);
        songBean.setSong_clicks(song_clicks);
        songBean.setSong_download(song_download);
        songBean.setSong_uptime(song_uptime);
        songBean.setVip_id(vip_id);

        Boolean flag = songService.updateById(songBean);
        request.setAttribute("updateByIdFlag",flag);
        request.getRequestDispatcher("./SongServlet?state=selectById&songId=" + song_id).forward(request,response);
    }

    private void selectById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer songId = Integer.parseInt(request.getParameter("songId"));
        List<SongBean> list = songService.selectById(songId);

        request.setAttribute("selectById",list);
        request.getRequestDispatcher("./admin/update/song_update.jsp").forward(request, response);
    }

    private void deleteById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer songId = Integer.parseInt(request.getParameter("songId"));
        Boolean flag = songService.deleteById(songId);

//        System.out.println(songId);
//        System.out.println(flag);
        String json = null;
        if (null!=flag){
            json = "删除成功";
        }else {
            json = "删除失败";
        }
        request.setAttribute("deleteByIdFlag",json);
        request.getRequestDispatcher("./admin/admin_song.jsp").forward(request, response);
    }

    private void displayAll(ServletRequest request, ServletResponse response) {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

//        System.out.println(pageSize);
//        System.out.println(pageNumber);

        PageHelper.startPage(pageNumber, pageSize);
        List<SongDisplayBean> songs = songService.selectAll();

        //这里用了Gson来实现将List这个对象的集合转换成字符串
        Gson gson = new Gson();
        //将记录转换成json字符串
        String songJson = gson.toJson(songs);
        String json = "{\"total\":" + songs.size() + ",\"rows\":" + songJson + "}";

//        System.out.println(json);
        printWriter.write(json);
    }
}
