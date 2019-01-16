package com.edu.service.impl;

import com.edu.bean.ClicksBean;
import com.edu.dao.Dao;
import com.edu.service.ClicksService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 18:40
 */
public class ClicksServiceIpml implements ClicksService {
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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

    @Override
    public Boolean deleteBySongId(Integer songId) {
        String selectSql = "select * from clicks where song_id = '"+songId+"'";
        List<ClicksBean> list = (List<ClicksBean>) dao.query(selectSql, ClicksBean.class);
        Boolean flag = false;
        if (list.size()>0){
            String sql = "delete from clicks where song_id = '"+songId+"'";
            flag = dao.addObj(sql);
        }else {
            flag = true;
        }

        return flag;
    }

    @Override
    public Boolean click(Integer userId, Integer songId) {
        String songSql = "update song set song_clicks = song_clicks + 1 where song_id = '"+songId+"'";
        String clickSql = "insert into clicks (user_id,song_id,click_date) " +
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
