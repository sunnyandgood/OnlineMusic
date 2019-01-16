package com.edu.servlet;

import com.edu.bean.SongtypeBean;
import com.edu.service.SongService;
import com.edu.service.SongTypeService;
import com.edu.service.impl.SongServiceImpl;
import com.edu.service.impl.SongTypeServiceImpl;
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
 * @Date: 2019/1/9 21:21
 */
public class SongTypeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongTypeService songTypeService = new SongTypeServiceImpl();
    //这里用了Gson来实现将List这个对象的集合转换成字符串
    private Gson gson = new Gson();
    private SongService songService = new SongServiceImpl();

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.listAll(request,response);
        }else if ("addToExcel".equals(state)){
            try {
                this.addToExcel(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            this.updateById(request,response);
        }else if ("addSongType".equals(state)){
            this.addSongType(request,response);
        }
    }

    private void addSongType(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        String type_name = request.getParameter("type_name");

        SongtypeBean songtypeBean = new SongtypeBean();
        songtypeBean.setType_name(type_name);

        Boolean flag = songTypeService.insert(songtypeBean);
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void updateById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        Integer type_id = Integer.parseInt(request.getParameter("type_id"));
        String type_name = request.getParameter("type_name");

        SongtypeBean songtypeBean = new SongtypeBean();
        songtypeBean.setType_id(type_id);
        songtypeBean.setType_name(type_name);

        Boolean flag = songTypeService.update(songtypeBean);
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String typeId = request.getParameter("typeId");
        List<SongtypeBean> songtypeBeanList = songTypeService.selectById(Integer.parseInt(typeId));

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/admin/update/songtype_update_jsp").forward(request,response);
    }

    private void deleteByIds(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String typeIds = request.getParameter("typeIds");
        String[] ids = typeIds.split(",");
        Boolean flag = false;
        for (String id : ids){
            Boolean delete = songTypeService.deleteById(Integer.parseInt(id));
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
        String typeId = request.getParameter("typeId");
        Boolean deleteSongFlag = songService.deleteByTypeId(Integer.parseInt(typeId));
        Boolean deleteTypeFlag = songTypeService.deleteById(Integer.parseInt(typeId));

        Boolean flag = false;
        if (deleteSongFlag && deleteTypeFlag){
            flag = true;
        }

        R r = R.modify(flag);

        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //获取数据
        List<SongtypeBean> list = songTypeService.listAll();

        //excel标题
        String[] title = {"id","歌曲类型"};

        //sheet文件名
        String sheetName = "歌曲类型信息";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][2];
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getType_id().toString();
            values[i][1] = list.get(i).getType_name();

        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/歌曲类型.xls");
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, values);
        workbook.write(fileOutputStream);
        fileOutputStream.close();

        R r = R.ok("导出成功！");
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
        List<SongtypeBean> songtypes = songTypeService.listAll();

        //将记录转换成json字符串
        String songJson = gson.toJson(songtypes);
        String json = "{\"total\":" + songtypes.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}
