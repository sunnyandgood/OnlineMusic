package com.edu.service.impl;

import com.edu.bean.DownloadBean;
import com.edu.dao.Dao;
import com.edu.service.DownloadService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 17:00
 */
public class DownloadServiceImpl implements DownloadService {
    private Dao dao = new Dao();

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
}
