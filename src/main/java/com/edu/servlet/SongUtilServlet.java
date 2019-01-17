package com.edu.servlet;

import com.edu.bean.SongBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.UserBean;
import com.edu.bean.VipBean;
import com.edu.service.*;
import com.edu.service.impl.*;
import com.edu.util.R;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/16 11:07
 */
public class SongUtilServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SongService songService = new SongServiceImpl();
    private SongTypeService songTypeService = new SongTypeServiceImpl();
    private ClicksService clicksService = new ClicksServiceIpml();
    private UserService userService = new UserServiceImpl();
    private DownloadService downloadService = new DownloadServiceImpl();
    private VipService vipService = new VipServiceImpl();
    private Gson gson = new Gson();

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String state = request.getParameter("state");

        System.out.println(state);
        if ("querySongType".equals(state)){
            this.querySongType(request,response);
        }else if ("querySongs".equals(state)){
            this.querySongs(request,response);
        }else if ("querySongByTypeId".equals(state)){
            this.querySongByTypeId(request,response);
        }else if("hotSearch".equals(state)){
            this.hotSearch(request,response);
        }else if ("hotDownload".equals(state)){
            this.hotDownload(request,response);
        }else if ("click".equals(state)){
            this.click(request,response);
        }else if ("query".equals(state)){
            this.query(request,response);
        }else if ("listen".equals(state)){
            this.listen(request,response);
        }else if ("download".equals(state)){
            this.download(request,response);
        }else if ("purchase".equals(state)){
            this.purchase(request,response);
        }else if ("selectVip".equals(state)){
            this.selectVip(request,response);
        }else if ("recharge".equals(state)){
            this.recharge(request,response);
        }
    }

    private void recharge(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        Integer vip_id = Integer.parseInt(request.getParameter("vip_id"));
        Boolean flag = userService.recharge(user_id, vip_id);

        R r = R.modify(flag);
        String json = gson.toJson(r);
        printWriter.print(json);
    }

    private void selectVip(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        List<UserBean> userBeans = userService.selectById(userId);
        Integer vip_id = userBeans.get(0).getVip_id();
        List<VipBean> vipBeans = vipService.selectUpVip(vip_id);
        Double rate = 0.8;
        for (int i=0;i<vipBeans.size();i++){
            VipBean vipBean = vipBeans.get(i);
            //计算月数
            Integer monthNum = (i+1)*6;
            //得到vip类型
            String vip = vipBean.getVip();
            //计算金额
            Double money = monthNum * 8 * rate;
            //将金额格式化为小数点后保留两位数字
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String formatMoney = decimalFormat.format(money);

            vipBean.setVip(vip + monthNum + "个月共计" + formatMoney);
//            vipBeans.get(i).setVip(vipBeans.get(i).getVip() + ((i+1)*6) + "个月共计"
//                    + ((i+1)*6)*8*rate);
        }

        request.setAttribute("userId",userId);
        request.setAttribute("vipBeans",vipBeans);
        request.getRequestDispatcher("/page/user/recharge_jsp").forward(request,response);
    }

    private void downLoad(List<SongBean> songBeans,HttpServletResponse response) throws UnsupportedEncodingException {
        //获取所要下载的文件名称
        String song_name = songBeans.get(0).getSong_name();
        String song_format = songBeans.get(0).getSong_format();
        //下载文件所在目录
        String song_url = songBeans.get(0).getSong_url();

        String fileName = new String((song_name + "." + song_format).getBytes("utf-8"),"ISO8859-1");

//        System.out.println(fileName);

        try {
            // path是指欲下载的文件的路径。
            File file = new File(song_url);
            // 以流的形式下载文件。
            InputStream inputStream = new BufferedInputStream(new FileInputStream(song_url));
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void purchase(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        Integer user_id = Integer.parseInt(request.getParameter("user_id"));
        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        downloadService.download(user_id,song_id);
        List<SongBean> songBeans = songService.selectById(song_id);
        this.downLoad(songBeans,response);
    }

    private void download(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");

        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        List<SongBean> songBeans = songService.selectById(song_id);
        Integer songVipId = songBeans.get(0).getVip_id();

        HttpSession session = request.getSession();
        Object loginId = session.getAttribute("userLoginId");
        if (null==loginId){
            request.getRequestDispatcher("/page/user/user_tips_jsp").forward(request,response);
        }else {
            List<UserBean> userBeans = userService.selectById((Integer) loginId);
            Integer userVipId = userBeans.get(0).getVip_id();
            if (userVipId >= songVipId){
                //在下载（download）表插入一条下载记录，
                //将歌曲（song）表中与song_id对应的记录的下载次数加一
                downloadService.download((Integer)loginId,song_id);
                this.downLoad(songBeans,response);
            }else {
                String purchaseInfo = "您需支付￥" + (songVipId - 1)*2 + "元以购买该歌曲！";
                request.setAttribute("user_id",(Integer)loginId);
                request.setAttribute("song_id",song_id);
                request.setAttribute("purchaseInfo",purchaseInfo);
                request.getRequestDispatcher("/page/user/purchase_jsp").forward(request,response);
            }
        }
    }

    private void listen(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        Integer song_id = Integer.parseInt(request.getParameter("song_id"));
        List<SongBean> songBeans = songService.selectById(song_id);
        String song_url = songBeans.get(0).getSong_url();
        int index = song_url.indexOf("/resources");
        String substring = song_url.substring(index);
        String listenSongUrl = "." + substring;

        request.setAttribute("listenSongUrl",listenSongUrl);
        this.querySongs(request,response);
    }

    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryInfo = request.getParameter("queryInfo");

        List<SongBean> songDisplayBeans = songService.fuzzyQuery(queryInfo);
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }

//        System.out.println(queryInfo);
//        System.out.println(size);

        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/page/song/query_jsp").forward(request,response);
    }

    private void click(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter printWriter = response.getWriter();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userLoginId");
        if (null!=userId){
            Integer song_id = Integer.parseInt(request.getParameter("song_id"));
            //在点击（click）表插入一条点击记录，
            //将歌曲（song）表中与song_id对应的记录的点击次数加一
            clicksService.click((Integer) userId, song_id);

        }

        request.getRequestDispatcher("/SongUtilServlet?state=querySongs").forward(request,response);

    }

    private void hotDownload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongBean> songDisplayBeans = songService.hotDownload();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans2",songDisplayBeans);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void hotSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongBean> songDisplayBeans = songService.hotSearch();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans1",songDisplayBeans);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private void querySongByTypeId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer typeId = Integer.parseInt(request.getParameter("typeId"));
        System.out.println(typeId);

        List<SongBean> songDisplayBeans = songService.selectSongByTypeId(typeId);
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }

        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/page/song/song_byTypeId_jsp").forward(request,response);
    }

    private void querySongs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongBean> songDisplayBeans = songService.selectAll();
        Integer size = songDisplayBeans.size();
        for (int i=1;i<=size;i++){
            songDisplayBeans.get(i-1).setSong_url(i + "");
        }
        request.setAttribute("songDisplayBeans",songDisplayBeans);
        request.getRequestDispatcher("/page/song/song_index_jsp").forward(request,response);
    }

    private void querySongType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        List<SongtypeBean> songtypeBeanList = songTypeService.listAll();

        request.setAttribute("songtypeBeanList",songtypeBeanList);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
