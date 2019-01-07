package com.edu.servlet;

import com.edu.bean.SongBean;
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
        }
    }

    private void deleteById(ServletRequest request, ServletResponse response) {
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
        if (flag){
            json = "{\"code\":" + 200 + ",\"message\":" + "success" + "}";
        }else {
            json = "{\"code\":" + 500 + ",\"message\":" + "error" + "}";
        }
        printWriter.print(json);
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
        List<SongBean> songs = songService.selectAll();

        //这里用了Gson来实现将List这个对象的集合转换成字符串
        Gson gson = new Gson();
        //将记录转换成json字符串
        String songJson = gson.toJson(songs);
        String json = "{\"total\":" + songs.size() + ",\"rows\":" + songJson + "}";

//        System.out.println(json);
        printWriter.write(json);
    }
}
