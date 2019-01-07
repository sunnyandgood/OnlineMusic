package com.edu.service.impl;

import com.edu.bean.SongBean;
import com.edu.dao.Dao;
import com.edu.service.SongService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:08
 */
public class SongServiceImpl implements SongService {
    @Override
    public List<SongBean> selectAll() {
        Dao dao = new Dao();
        String sql = "select * from song";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int song_id) {
        Dao dao = new Dao();
        String sql = "delete from song where song_id = '"+song_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }
}
