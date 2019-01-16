package com.edu.servlet;

import com.edu.bean.ClicksBean;
import com.edu.service.ClicksService;
import com.edu.service.impl.ClicksServiceIpml;
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
 * @Date: 2019/1/9 18:48
 */
public class ClicksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ClicksService clicksService = new ClicksServiceIpml();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //这里用了Gson来实现将List这个对象的集合转换成字符串
    private Gson gson = new Gson();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.listAll(request, response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)) {
            this.deleteSome(request,response);
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
        List<ClicksBean> list = clicksService.selectAll();

        //excel标题
        String[] title = {"点击id","用户名","歌曲名字","点击时间"};

        //sheet文件名
        String sheetName = "歌曲点击信息";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][4];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getClick_id().toString();
            values[i][1] = list.get(i).getUser_name();
            values[i][2] = list.get(i).getSong_name();
            values[i][3] = simpleDateFormat.format(list.get(i).getClick_date());

        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/点击.xls");
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, values);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        R r = R.ok("导出成功！");
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void deleteSome(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String ids = request.getParameter("clickIds");
        String[] clickIds = ids.split(",");
        Boolean flag = false;
        for (int i=0;i<clickIds.length;i++){
            Boolean delete = clicksService.deleteById(Integer.parseInt(clickIds[i]));
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
        Integer download_id = Integer.parseInt(request.getParameter("clickId"));
        Boolean flag = clicksService.deleteById(download_id);

        R r = R.modify(flag);

        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void listAll(ServletRequest request, ServletResponse response) {
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
        List<ClicksBean> clicks = clicksService.selectAll();

        //将记录转换成json字符串
        String songJson = gson.toJson(clicks);
        String json = "{\"total\":" + clicks.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}