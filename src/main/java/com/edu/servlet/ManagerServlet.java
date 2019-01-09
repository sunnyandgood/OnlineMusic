package com.edu.servlet;

import com.edu.bean.ManagerBean;
import com.edu.service.ManagerService;
import com.edu.service.impl.ManagerServiceImpl;
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
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 19:18
 */
public class ManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ManagerService managerService = new ManagerServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.listAll(request,response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("addToExcel".equals(state)){
            try {
                this.addToExcel(request,response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("addManager".equals(state)){
            this.addManager(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            this.updateById(request,response);
        }

    }

    private void updateById(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        Integer manager_id = Integer.parseInt(request.getParameter("manager_id"));
        String manager_name = request.getParameter("manager_name");
        String manager_password = request.getParameter("manager_password");

        ManagerBean managerBean = new ManagerBean();
        managerBean.setManager_id(manager_id);
        managerBean.setManager_name(manager_name);
        managerBean.setManager_password(manager_password);

        managerService.update(managerBean);
    }

    private void selectById(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        String managerId = request.getParameter("managerId");
        List<ManagerBean> managerBeanList = managerService.selectById(Integer.parseInt(managerId));

        request.setAttribute("managerBeanList",managerBeanList);
        request.getRequestDispatcher("./admin/update/manager_update.jsp").forward(request, response);
    }

    private void addManager(ServletRequest request, ServletResponse response) {
        String manager_name = request.getParameter("manager_name");
        String manager_password = request.getParameter("manager_password");

        ManagerBean managerBean = new ManagerBean();
        managerBean.setManager_name(manager_name);
        managerBean.setManager_password(manager_password);

        managerService.insert(managerBean);
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws Exception {
        //获取数据
        List<ManagerBean> list = managerService.listAll();

        //excel标题
        String[] title = {"id","管理员名字","管理员密码"};

        //sheet文件名
        String sheetName = "管理员信息";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][3];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getManager_id().toString();
            values[i][1] = list.get(i).getManager_name();
            values[i][2] = list.get(i).getManager_password();

//            System.out.println(list.get(i));
        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/管理员.xls");
        HSSFWorkbook workbook = ExcelUtil.getHSSFWorkbook(sheetName, title, values);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }

    private void deleteByIds(ServletRequest request, ServletResponse response) {
        String managerIds = request.getParameter("managerIds");
        String[] ids = managerIds.split(",");
        for (String id : ids){
            managerService.deleteById(Integer.parseInt(id));
        }
    }

    private void deleteById(ServletRequest request, ServletResponse response) {
        Integer manager_id = Integer.parseInt(request.getParameter("managerId"));
        managerService.deleteById(manager_id);
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
        List<ManagerBean> managers = managerService.listAll();

        //这里用了Gson来实现将List这个对象的集合转换成字符串
        Gson gson = new Gson();
        //将记录转换成json字符串
        String songJson = gson.toJson(managers);
        String json = "{\"total\":" + managers.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }

}
