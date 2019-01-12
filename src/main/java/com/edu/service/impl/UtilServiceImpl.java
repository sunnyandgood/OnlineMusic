package com.edu.service.impl;

import com.edu.bean.SongDisplayBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.UserDisplayBean;
import com.edu.bean.VipBean;
import com.edu.dao.Dao;
import com.edu.service.UtilService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:09
 */
public class UtilServiceImpl implements UtilService {
    private Dao dao = new Dao();
    @Override
    public List<VipBean> selectVip() {
        String sql = "select * from vip";
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
}
