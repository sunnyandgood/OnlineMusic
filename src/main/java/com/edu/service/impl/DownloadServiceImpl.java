package com.edu.service.impl;

import com.edu.bean.DownloadBean;
import com.edu.dao.Dao;
import com.edu.service.DownloadService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 17:00
 */
public class DownloadServiceImpl implements DownloadService {
    private Dao dao = new Dao();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public List<DownloadBean> selectAll() {
        String sql = "select download_id,user_name,song_name,download_date from download,song,user " +
                "where download.user_id = user.user_id and download.song_id = song.song_id";
        List<DownloadBean> list = (List<DownloadBean>) dao.query(sql, DownloadBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int download_id) {
        String sql = "delete from download where download_id = '"+download_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean deleteBySongId(Integer songId) {
        String selectSql = "select * from download where song_id = '"+songId+"'";
        List<DownloadBean> list = (List<DownloadBean>) dao.query(selectSql, DownloadBean.class);

        Boolean flag = false;
        if (list.size()>0){
            String sql = "delete from download where song_id = '"+songId+"'";
            flag = dao.addObj(sql);
        }else {
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean download(Integer userId, Integer songId) {
        String songSql = "update song set song_download = song_download + 1 where song_id = '"+songId+"'";
        String clickSql = "insert into download (user_id,song_id,download_date) " +
                "values ('"+userId+"','"+songId+"','"+simpleDateFormat.format(new Date())+"')";

        Boolean songFlag = dao.addObj(songSql);
        Boolean clickFlag = dao.addObj(clickSql);

        Boolean flag = false;
        if (songFlag&&clickFlag){
            flag = true;
        }
        return flag;
    }
}
