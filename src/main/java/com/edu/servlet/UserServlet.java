package com.edu.servlet;

import com.edu.bean.SongtypeBean;
import com.edu.bean.UserBean;
import com.edu.bean.VipBean;
import com.edu.service.SongTypeService;
import com.edu.service.UserService;
import com.edu.service.VipService;
import com.edu.service.impl.SongTypeServiceImpl;
import com.edu.service.impl.UserServiceImpl;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 10:02
 */
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserServiceImpl();
    private VipService vipService = new VipServiceImpl();
    private SongTypeService songTypeService = new SongTypeServiceImpl();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //这里用了Gson来实现将List这个对象的集合转换成字符串
    private Gson gson = new Gson();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);

        if ("listAll".equals(state)){
            this.listAll(request,response);
        }else if ("addToExcel".equals(state)){
            this.addToExcel(request,response);
        }else if ("addFromExcel".equals(state)){
            this.addFromExcel(request,response);
        }else if ("deleteById".equals(state)){
            this.deleteById(request,response);
        }else if ("deleteByIds".equals(state)){
            this.deleteByIds(request,response);
        }else if ("selectById".equals(state)){
            this.selectById(request,response);
        }else if ("updateById".equals(state)){
            try {
                this.updateById(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if ("selectVipAndSongType".equals(state)){
            this.selectVipAndSongType(request,response);
        }else if ("addUser".equals(state)){
            try {
                this.addUser(request,response);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        String user_name = request.getParameter("user_name");
        String user_password = request.getParameter("user_password");
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));
        Date user_birthday = simpleDateFormat.parse(request.getParameter("user_birthday") + " 00:00:00");
        String user_gender = request.getParameter("user_gender");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));

        UserBean userBean = new UserBean();
        userBean.setUser_name(user_name);
        userBean.setUser_password(user_password);
        userBean.setVip_id(vip_id);
        userBean.setUser_birthday(user_birthday);
        userBean.setUser_gender(user_gender);
        userBean.setType_id(type_id);

        Boolean flag = userService.insert(userBean);
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectVipAndSongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<VipBean> vipBeanList = vipService.listAll();
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/admin/add/user_add_jsp").forward(request,response);
    }

    private void updateById(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();

        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        String user_name = request.getParameter("user_name");
        String user_password = request.getParameter("user_password");
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));
        Date user_birthday = simpleDateFormat.parse(request.getParameter("user_birthday") + " 00:00:00");
        String user_gender = request.getParameter("user_gender");
        Integer type_id = Integer.parseInt(request.getParameter("type_id"));

        UserBean userBean = new UserBean();
        userBean.setUser_id(user_id);
        userBean.setUser_name(user_name);
        userBean.setUser_password(user_password);
        userBean.setVip_id(vip_id);
        userBean.setUser_birthday(user_birthday);
        userBean.setUser_gender(user_gender);
        userBean.setType_id(type_id);

        Boolean flag = userService.updateById(userBean);
        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String userId = request.getParameter("userId");
        List<UserBean> userBeans = userService.selectById(Integer.parseInt(userId));

        List<VipBean> vipBeanList = vipService.listAll();
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("userBeans",userBeans);
        request.setAttribute("vipBeanList",vipBeanList);
        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/page/admin/update/user_update_jsp").forward(request,response);
    }

    private void deleteByIds(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String userIds = request.getParameter("userIds");
        String[] ids = userIds.split(",");
        Boolean flag = false;
        for (String id : ids){
            Boolean delete = userService.deletById(Integer.parseInt(id));
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

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        String userId = request.getParameter("userId");
        Boolean flag = userService.deletById(Integer.parseInt(userId));

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void addFromExcel(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //得到上传路径的硬盘路径
        String dir = request.getServletContext().getRealPath("/resources/upload/");
        String userPath = request.getParameter("userPath");

        String path = dir + userPath;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //读取文件
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //sheet文件名
        String sheetName = "Sheet1";
        //将excel中的数据读取到String数组中
        String[][] values = new String[0][];

        try {
            values = ExcelUtil.getValuesFromExcel(inputStream,sheetName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //将String数组中的数据封装到实体类
        boolean flag = false;
        for(int i=1;i<values.length;i++) {
            UserBean userBean = new UserBean();

            userBean.setUser_name(values[i][0]);
            userBean.setUser_password(values[i][1]);
            if(null!=values[i][2]) {
                userBean.setVip_id(Integer.parseInt(values[i][2]));
            }
            if (null!=values[i][3]){
                try {
                    userBean.setUser_birthday(dateFormat.parse(values[i][3] + " 00:00:00"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            userBean.setUser_gender(values[i][4]);

            if (null!=values[i][5]){
                userBean.setType_id(Integer.parseInt(values[i][5]));
            }

            Boolean insert = userService.insert(userBean);
            if (insert){
                flag = true;
            }else {
                flag = false;
            }
        }


        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void addToExcel(ServletRequest request, ServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        //获取数据
        List<UserBean> list = userService.selectAll();

        //excel标题
        String[] title = {"用户id","用户名","用户密码","vip等级","生日","性别","喜欢的歌曲类型"};

        //sheet文件名
        String sheetName = "用户";

        //将数据库中数据存到String数组中
        String[][] values = new String[list.size()][7];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=0;i<list.size();i++) {
            values[i][0] = list.get(i).getUser_id().toString();
            values[i][1] = list.get(i).getUser_name();
            values[i][2] = list.get(i).getUser_password();
            values[i][3] = list.get(i).getVip();
            values[i][4] = simpleDateFormat.format(list.get(i).getUser_birthday());
            values[i][5] = list.get(i).getUser_gender();
            values[i][6] = list.get(i).getType_name();
        }
        FileOutputStream fileOutputStream = new FileOutputStream("D:/用户.xls");
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
        List<UserBean> users = userService.selectAll();

        //将记录转换成json字符串
        String songJson = gson.toJson(users);
        String json = "{\"total\":" + users.size() + ",\"rows\":" + songJson + "}";

        printWriter.write(json);
    }
}
