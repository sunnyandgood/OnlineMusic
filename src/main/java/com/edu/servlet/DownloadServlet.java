package com.edu.servlet;

import com.edu.bean.DownloadBean;
import com.edu.service.DownloadService;
import com.edu.service.impl.DownloadServiceImpl;
import com.edu.util.ExcelUtil;
import com.edu.util.R;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 16:59
 */
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DownloadService downloadService = new DownloadServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //这里用了Gson来实现将List这个对象的集合转换成字符串
    private Gson gson = new Gson();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.displayAll(request, response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)) {
            this.deleteByIds(request,response);
        }else if ("addToExcel".equals(state)){
            try {
                this.addToExcel(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //获取数据
        List<DownloadBean> list = downloadService.selectAll();

        //excel标题
        String[] title = {"下载id","用户名","歌曲名字","下载时间"};

        //sheet文件名
        String sheetName = "歌曲下载信息";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][4];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getDownload_id().toString();
            values[i][1] = list.get(i).getUser_name();
            values[i][2] = list.get(i).getSong_name();
            values[i][3] = simpleDateFormat.format(list.get(i).getDownload_date());
        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/下载.xls");
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, values);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        R r = R.ok("导出成功！");
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void deleteByIds(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String ids = request.getParameter("downloadIds");
        String[] downloadIds = ids.split(",");
        Boolean flag = false;
        for (String id : downloadIds){
            Boolean delete = downloadService.deleteById(Integer.parseInt(id));
            if (delete){
                flag = true;
            }else {
                flag = false;
            }
        }

        R r = R.modify(flag);

        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void deleteById(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        Integer download_id = Integer.parseInt(request.getParameter("downloadId"));
        Boolean flag = downloadService.deleteById(download_id);

        R r = R.modify(flag);

        String json = gson.toJson(r);
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

        PageHelper.startPage(pageNumber, pageSize);
        List<DownloadBean> songs = downloadService.selectAll();

        //将记录转换成json字符串
        String songJson = gson.toJson(songs);
        String json = "{\"total\":" + songs.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}
