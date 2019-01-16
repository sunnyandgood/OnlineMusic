package com.edu.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:02
 */
public class UtilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        if ("upload".equals(state)){
            this.upload(request,response);
        }
    }

    private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        //得到上传路径的硬盘路径
        String savePath = request.getServletContext().getRealPath("/resources/upload/");

        File fl = new File(savePath);
        System.out.println(savePath);
        //如果文件不存在,就新建一个
        if (!fl.exists()) {
            fl.mkdirs();
        }
        //这个是文件上传需要的类
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
        servletFileUpload.setHeaderEncoding("utf-8");
        List fileList = null;
        try {
            fileList = servletFileUpload.parseRequest(request);
        } catch (FileUploadException ex) {
            return;
        }
        //迭代器,搜索前端发送过来的文件
        Iterator<FileItem> it = fileList.iterator();
        String name = "";
        String extName = "";
        while (it.hasNext()) {
            FileItem item = it.next();
            //判断该表单项是否是普通类型
            if (!item.isFormField()) {
                name = item.getName();
                long size = item.getSize();
                String type = item.getContentType();

                System.out.println(size + " " + type);

                if (name == null || name.trim().equals("")) {
                    continue;
                }
                // 扩展名格式： extName就是文件的后缀,例如 .txt
                if (name.lastIndexOf(".") >= 0) {
                    extName = name.substring(name.lastIndexOf("."));
                }
                File file = null;
                do {
                    // 生成文件名：
                    name = UUID.randomUUID().toString();
                    file = new File(savePath + name + extName);
                } while (file.exists());
                File saveFile = new File(savePath + name + extName);
                try {
                    item.write(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.getWriter().print(name + extName);
    }
}
