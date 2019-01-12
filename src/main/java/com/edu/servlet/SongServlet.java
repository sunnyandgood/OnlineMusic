package com.edu.servlet;

import com.edu.bean.SongBean;
import com.edu.bean.SongDisplayBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.VipBean;
import com.edu.service.SongService;
import com.edu.service.UtilService;
import com.edu.service.impl.SongServiceImpl;
import com.edu.service.impl.UtilServiceImpl;
import com.edu.util.ExcelUtil;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
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
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request,response);
        }else if ("addSong".equals(state)){
            this.addSong(request,response);
        }else if ("addToExcel".equals(state)){
            try {
                this.addToExcel(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws Exception {
        //获取数据
        List<SongDisplayBean> list = songService.selectAll();

        //excel标题
        String[] title = {"歌曲id","歌曲名字","歌手","歌曲类型","文件大小","文件地址","歌曲格式",
                "点击次数","下载次数","上传时间","vip等级"};

        //sheet文件名
        String sheetName = "歌曲";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][11];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getSong_id().toString();
            values[i][1] = list.get(i).getSong_name();
            values[i][2] = list.get(i).getSong_singer();
            values[i][3] = list.get(i).getType_name();
            values[i][4] = list.get(i).getSong_size();
            values[i][5] = list.get(i).getSong_url();
            values[i][6] = list.get(i).getSong_format();
            values[i][7] = list.get(i).getSong_clicks().toString();
            values[i][8] = list.get(i).getSong_download().toString();
            values[i][9] = simpleDateFormat.format(list.get(i).getSong_uptime());
            values[i][10] = list.get(i).getVip();

//            System.out.println(list.get(i));
        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/歌曲.xls");
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, values);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    private void addSong(ServletRequest request, ServletResponse response) {
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

        String song_name = request.getParameter("song_name");
        String song_singer = request.getParameter("song_singer");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));
        String song_size = request.getParameter("song_size");
        String song_format = request.getParameter("song_format");
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));

        SongBean songBean = new SongBean();
        songBean.setSong_name(song_name);
        songBean.setSong_singer(song_singer);
        songBean.setType_id(type_id);
        songBean.setSong_size(song_size);
        songBean.setSong_url(newPath);
        songBean.setSong_format(song_format);
        songBean.setSong_clicks(0);
        songBean.setSong_download(0);
        songBean.setSong_uptime(new Date());
        songBean.setVip_id(vip_id);

        Boolean flag = songService.insert(songBean);

//        request.getRequestDispatcher("./SongServlet?state=selectVipAndSongType").forward(request,response);

    }

    private void selectVipAndSongType(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        UtilService utilService = new UtilServiceImpl();
        response.setCharacterEncoding("utf-8");
        List<VipBean> vipBeanList = utilService.selectVip();
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("./admin/add/song_add.jsp").forward(request, response);
    }

    private void deleteByIds(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        String songIds = request.getParameter("songIds");
        String[] ids = songIds.split(",");
        for (String id : ids){
            songService.deleteById(Integer.parseInt(id));
        }
        request.getRequestDispatcher("./admin/admin_song.jsp").forward(request, response);
    }

    private void updateById(ServletRequest request, ServletResponse response) throws ParseException, ServletException, IOException {
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
//        request.setAttribute("updateByIdFlag",flag);
//        request.getRequestDispatcher("./SongServlet?state=selectById&songId=" + song_id).forward(request,response);
    }

    private void selectById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        UtilService utilService = new UtilServiceImpl();
        response.setCharacterEncoding("utf-8");
        Integer songId = Integer.parseInt(request.getParameter("songId"));
        List<SongBean> list = songService.selectById(songId);

        List<VipBean> vipBeanList = utilService.selectVip();
        List<SongtypeBean> songtypeBeanList = utilService.selectSongType();

        request.setAttribute("selectById",list);
        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);

        request.getRequestDispatcher("./admin/update/song_update.jsp").forward(request, response);
    }

    private void deleteById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
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
