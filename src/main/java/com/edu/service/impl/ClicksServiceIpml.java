package com.edu.service.impl;

import com.edu.bean.ClicksBean;
import com.edu.dao.Dao;
import com.edu.service.ClicksService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 18:40
 */
public class ClicksServiceIpml implements ClicksService {
    private Dao dao = new Dao();

    @Override
    public List<ClicksBean> selectAll() {
        String sql = "select click_id,user_name,song_name,click_date from clicks,song,user " +
                "where clicks.user_id = user.user_id and clicks.song_id = song.song_id";
        List<ClicksBean> list = (List<ClicksBean>) dao.query(sql, ClicksBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int click_id) {
        String sql = "delete from clicks where click_id = '"+click_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }
}
