package com.edu.servlet;

import com.edu.bean.VipBean;
import com.edu.service.VipService;
import com.edu.service.impl.VipServiceImpl;
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
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 8:56
 */
public class VipServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VipService vipService = new VipServiceImpl();
    //这里用了Gson来实现将List这个对象的集合转换成字符串
    private Gson gson = new Gson();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.listAll(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            this.updateById(request,response);
        }else if ("addVip".equals(state)){
            this.addVip(request,response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("addToExcel".equals(state)){
            this.addToExcel(request,response);
        }
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //获取数据
        List<VipBean> list = vipService.listAll();

        //excel标题
        String[] title = {"id","vip类型"};

        //sheet文件名
        String sheetName = "vip类型信息";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][2];
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getVip_id().toString();
            values[i][1] = list.get(i).getVip();

        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/vip类型.xls");
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
        String vipIds = request.getParameter("vipIds");
        String[] ids = vipIds.split(",");
        Boolean flag = false;
        for (String id :ids){
            Boolean delete = vipService.deletById(Integer.parseInt(id));
            if (delete.booleanValue()){
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
        Integer vipId = Integer.parseInt(request.getParameter("vipId"));
        Boolean flag = vipService.deletById(vipId);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void addVip(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String vip = request.getParameter("vip");

        VipBean vipBean = new VipBean();
        vipBean.setVip(vip);

        Boolean flag = vipService.insert(vipBean);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void updateById(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));
        String vip = request.getParameter("vip");

        VipBean vipBean = new VipBean();
        vipBean.setVip_id(vip_id);
        vipBean.setVip(vip);
        Boolean flag = vipService.update(vipBean);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer vipId = Integer.parseInt(request.getParameter("vipId"));
        List<VipBean> vipBeanList = vipService.selectById(vipId);
        request.setAttribute("vipBeanList",vipBeanList);
        request.getRequestDispatcher("/page/admin/update/vip_update_jsp").forward(request,response);
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
        List<VipBean> vips = vipService.listAll();

        //将记录转换成json字符串
        String songJson = gson.toJson(vips);
        String json = "{\"total\":" + vips.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}
