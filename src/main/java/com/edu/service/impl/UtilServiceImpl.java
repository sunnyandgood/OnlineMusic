package com.edu.service.impl;

import com.edu.bean.*;
import com.edu.dao.Dao;
import com.edu.service.UtilService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:09
 */
public class UtilServiceImpl implements UtilService {
    private Dao dao = new Dao();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public List<VipBean> selectVip() {
        String sql = "select * from vip";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public List<VipBean> selectVip(Integer vip_id) {
        String sql = "select * from vip where vip_id <= '"+vip_id+"'";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public List<SongtypeBean> selectSongType() {
        String sql = "select * from songtype";
        List<SongtypeBean> list = (List<SongtypeBean>) dao.query(sql, SongtypeBean.class);
        return list;
    }

    @Override
    public List<UserDisplayBean> selectByUserId(Integer user_id) {
        String sql = "select user_id,user_name,vip,user_birthday,user_gender,type_name " +
                "from user,vip,songtype where user.vip_id = vip.vip_id and " +
                "user.type_id = songtype.type_id and user_id = '"+user_id+"'";
        List<UserDisplayBean> list = (List<UserDisplayBean>) dao.query(sql, UserDisplayBean.class);
        return list;
    }

    @Override
    public List<SongDisplayBean> selectSongByTypeId(Integer type_id) {
        String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                "from song,songtype where song.type_id = songtype.type_id and songtype.type_id = '"+type_id+"'";
        List<SongDisplayBean> list = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
        return list;
    }

    @Override
    public List<SongDisplayBean> selectSongs() {
        String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                "from song,songtype where song.type_id = songtype.type_id ";
        List<SongDisplayBean> list = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
        return list;
    }

    @Override
    public List<SongDisplayBean> selectSongs(String queryInfo) {
        String sql = "select * from  ( select song_id,song_name,song_singer,type_name,song_clicks," +
                "song_download,song_uptime from song,songtype where song.type_id = songtype.type_id ) " +
                "as query where query.song_name like '%"+queryInfo+"%' or " +
                "query.song_singer like '%"+queryInfo+"%' or query.type_name like '%"+queryInfo+"%'";
        List<SongDisplayBean> list = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
        return list;
    }

    @Override
    public List<SongDisplayBean> hotSearch() {
        String clicksSql = "select song_id from clicks group by song_id order by count(*) desc limit 10";
        List<SongBean> songBeans = (List<SongBean>) dao.query(clicksSql, SongBean.class);

        List<SongDisplayBean> list = new ArrayList<>();
        for (SongBean songBean : songBeans){
            String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                    "from song,songtype where song.type_id = songtype.type_id and " +
                    "song_id = '"+songBean.getSong_id()+"'";
            List<SongDisplayBean> songDisplayBeans = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
            list.add(songDisplayBeans.get(0));
        }

        return list;
    }

    @Override
    public List<SongDisplayBean> hotDownload() {
        String clicksSql = "select song_id from download group by song_id order by count(*) desc limit 10";
        List<SongBean> songBeans = (List<SongBean>) dao.query(clicksSql, SongBean.class);

        List<SongDisplayBean> list = new ArrayList<>();
        for (SongBean songBean : songBeans){
            String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                    "from song,songtype where song.type_id = songtype.type_id and " +
                    "song_id = '"+songBean.getSong_id()+"'";
            List<SongDisplayBean> songDisplayBeans = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
            list.add(songDisplayBeans.get(0));
        }

        return list;
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
