package com.edu.service.impl;

import com.edu.bean.SongBean;
import com.edu.bean.SongDisplayBean;
import com.edu.dao.Dao;
import com.edu.service.SongService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:08
 */
public class SongServiceImpl implements SongService {
    @Override
    public List<SongDisplayBean> selectAll() {
        Dao dao = new Dao();
        String sql = "select song_id,song_name,song_singer,type_name,song_size,song_url,song_format,song_clicks,song_download,song_uptime,vip from song,vip,songtype where song.vip_id = vip.vip_id and song.type_id = songtype.type_id";
        List<SongDisplayBean> list = (List<SongDisplayBean>) dao.query(sql, SongDisplayBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int song_id) {
        Dao dao = new Dao();
        String sql = "delete from song where song_id = '"+song_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<SongBean> selectById(int song_id) {
        Dao dao = new Dao();
        String sql = "select * from song where song_id = '"+song_id+"'";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

    @Override
    public Boolean updateById(SongBean songBean) {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Dao dao = new Dao();
        String sql = "update song set song_name = '"+songBean.getSong_name()+"' , song_singer = '"+songBean.getSong_singer()+"' ," +
                "type_id = '"+songBean.getType_id()+"' , song_size = '"+songBean.getSong_size()+"' ," +
                "song_url = '"+songBean.getSong_url()+"' , song_format = '"+songBean.getSong_format()+"' ," +
                "song_clicks = '"+songBean.getSong_clicks()+"' , song_download = '"+songBean.getSong_download()+"' ," +
                "song_uptime = '"+simpleDateFormat.format(songBean.getSong_uptime())+"' , vip_id = '"+songBean.getVip_id()+"'" +
                "where song_id = '"+songBean.getSong_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }
}
