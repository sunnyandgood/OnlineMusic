package com.edu.servlet;

import com.edu.bean.SongBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.VipBean;
import com.edu.service.*;
import com.edu.service.impl.*;
import com.edu.util.ExcelUtil;
import com.edu.util.R;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
 * @Date: 2019/1/14 22:39
 */
public class SongServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongService songService = new SongServiceImpl();
    private VipService vipService = new VipServiceImpl();
    private SongTypeService songTypeService = new SongTypeServiceImpl();
    private ClicksService clicksService = new ClicksServiceIpml();
    private DownloadService downloadService = new DownloadServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Gson gson = new Gson();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("utf-8");
        String state = request.getParameter("state");
        if ("listAll".equals(state)){
            this.listAll(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            try {
                this.updateById(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request,response);
        }else if ("addSong".equals(state)){
            this.addSong(request,response);
        }else if ("addToExcel".equals(state)){
            this.addToExcel(request,response);
        }


    }

    private void addToExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //获取数据
        List<SongBean> list = songService.selectAll();

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

        R r = R.ok("导出成功！");
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void addSong(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
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
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectVipAndSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<VipBean> vipBeanList = vipService.listAll();
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/admin/add/song_add_jsp").forward(request, response);
    }

    private void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String songIds = request.getParameter("songIds");
        String[] ids = songIds.split(",");
        Boolean flag = false;
        for (String id : ids){
            Integer songId = Integer.parseInt(id);
            List<SongBean> songBeans = songService.selectById(songId);
            flag = this.delete(songBeans, songId);
        }

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        Integer songId = Integer.parseInt(request.getParameter("songId"));

        List<SongBean> songBeans = songService.selectById(songId);

        Boolean flag = this.delete(songBeans, songId);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private Boolean delete(List<SongBean> songBeans,Integer songId){
        String song_url = songBeans.get(0).getSong_url();
        File file = new File(song_url);
        Boolean fileDelete = false;
        if (!file.exists()){
            System.out.println("删除文件失败:" + song_url + "不存在！");
            fileDelete = true;
        }else {
            if (file.isFile()){
                fileDelete = file.delete();
                if (fileDelete){
                    System.out.println("删除文件成功:" + song_url);
                }
            }
        }

        Boolean clickFlag = clicksService.deleteBySongId(songId);
        Boolean downloadFlag = downloadService.deleteBySongId(songId);

        Boolean songFlag = songService.deleteById(songId);

        Boolean flag = false;
        if (fileDelete && clickFlag && downloadFlag && songFlag){
            flag = true;
        }

        return flag;
    }

    private void updateById(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        PrintWriter printWriter = response.getWriter();

        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        String song_name = request.getParameter("song_name");
        String song_singer = request.getParameter("song_singer");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));
        String song_size = request.getParameter("song_size");
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
        songBean.setSong_format(song_format);
        songBean.setSong_clicks(song_clicks);
        songBean.setSong_download(song_download);
        songBean.setSong_uptime(song_uptime);
        songBean.setVip_id(vip_id);

        Boolean flag = songService.updateById(songBean);

        R r = null;
        if (flag){
            r = R.ok("修改成功！");
        }else {
            r = R.error("修改失敗！");
        }
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer songId = Integer.parseInt(request.getParameter("songId"));
        List<SongBean> list = songService.selectById(songId);

        List<VipBean> vipBeanList = vipService.listAll();
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("selectById",list);
        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);

        request.getRequestDispatcher("/page/admin/update/song_update_jsp").forward(request, response);
    }

    private void listAll(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));

        PageHelper.startPage(pageNumber, pageSize);
        List<SongBean> songs = songService.selectAll();

        //这里用了Gson来实现将List这个对象的集合转换成字符串
        Gson gson = new Gson();
        //将记录转换成json字符串
        String songJson = gson.toJson(songs);
        String json = "{\"total\":" + songs.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}
