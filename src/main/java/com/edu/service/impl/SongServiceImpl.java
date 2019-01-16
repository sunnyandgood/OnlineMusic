package com.edu.service.impl;

import com.edu.bean.SongBean;
import com.edu.dao.Dao;
import com.edu.service.SongService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:08
 */
public class SongServiceImpl implements SongService {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Dao dao = new Dao();
    @Override
    public List<SongBean> selectAll() {
        String sql = "select song_id,song_name,song_singer,type_name,song_size,song_url,song_format," +
                "song_clicks,song_download,song_uptime,vip from song,vip,songtype " +
                "where song.vip_id = vip.vip_id and song.type_id = songtype.type_id";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int song_id) {
        String sql = "delete from song where song_id = '"+song_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<SongBean> selectById(int song_id) {
        String sql = "select * from song where song_id = '"+song_id+"'";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

    @Override
    public Boolean updateById(SongBean songBean) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sql = "update song set song_name = '"+songBean.getSong_name()+"' , song_singer = '"+songBean.getSong_singer()+"' ," +
                "type_id = '"+songBean.getType_id()+"' , song_size = '"+songBean.getSong_size()+"' ," +
                "song_format = '"+songBean.getSong_format()+"' ," +
                "song_clicks = '"+songBean.getSong_clicks()+"' , song_download = '"+songBean.getSong_download()+"' ," +
                "song_uptime = '"+simpleDateFormat.format(songBean.getSong_uptime())+"' , vip_id = '"+songBean.getVip_id()+"'" +
                "where song_id = '"+songBean.getSong_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean insert(SongBean songBean) {
        String sql = "insert into song (song_name,song_singer,type_id,song_size,song_url,song_format," +
                "song_clicks,song_download,song_uptime,vip_id) " +
                "values ('"+songBean.getSong_name()+"','"+songBean.getSong_singer()+"','"+songBean.getType_id()+"'," +
                "'"+songBean.getSong_size()+"','"+songBean.getSong_url()+"','"+songBean.getSong_format()+"'," +
                "'"+songBean.getSong_clicks()+"','"+songBean.getSong_download()+"'," +
                "'"+simpleDateFormat.format(songBean.getSong_uptime())+"','"+songBean.getVip_id()+"')";

        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean deleteByTypeId(Integer type_id) {
        String sql = "delete from song where type_id = '"+type_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<SongBean> selectSongByTypeId(Integer type_id) {
        String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                "from song,songtype where song.type_id = songtype.type_id and songtype.type_id = '"+type_id+"'";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

    @Override
    public List<SongBean> hotSearch() {
        String clicksSql = "select song_id from clicks group by song_id order by count(*) desc limit 10";
        List<SongBean> songBeans = (List<SongBean>) dao.query(clicksSql, SongBean.class);

        List<SongBean> list = new ArrayList<>();
        for (SongBean songBean : songBeans){
            String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                    "from song,songtype where song.type_id = songtype.type_id and " +
                    "song_id = '"+songBean.getSong_id()+"'";
            List<SongBean> songDisplayBeans = (List<SongBean>) dao.query(sql, SongBean.class);
            list.add(songDisplayBeans.get(0));
        }

        return list;
    }

    @Override
    public List<SongBean> hotDownload() {
        String clicksSql = "select song_id from download group by song_id order by count(*) desc limit 10";
        List<SongBean> songBeans = (List<SongBean>) dao.query(clicksSql, SongBean.class);

        List<SongBean> list = new ArrayList<>();
        for (SongBean songBean : songBeans){
            String sql = "select song_id,song_name,song_singer,type_name,song_clicks,song_download,song_uptime " +
                    "from song,songtype where song.type_id = songtype.type_id and " +
                    "song_id = '"+songBean.getSong_id()+"'";
            List<SongBean> songDisplayBeans = (List<SongBean>) dao.query(sql, SongBean.class);
            list.add(songDisplayBeans.get(0));
        }

        return list;
    }

    @Override
    public List<SongBean> fuzzyQuery(String queryInfo) {
        String sql = "select * from  ( select song_id,song_name,song_singer,type_name,song_clicks," +
                "song_download,song_uptime from song,songtype where song.type_id = songtype.type_id ) " +
                "as query where query.song_name like '%"+queryInfo+"%' or " +
                "query.song_singer like '%"+queryInfo+"%' or query.type_name like '%"+queryInfo+"%'";
        List<SongBean> list = (List<SongBean>) dao.query(sql, SongBean.class);
        return list;
    }

//    @Override
//    public Boolean insert(SongBean songBean) {
//        String sql = "insert into song (song_name,song_singer,type_id,song_size,song_url,song_format," +
//                "song_clicks,song_download,song_uptime,vip_id) " +
//                "values ('"+songBean.getSong_name()+"','"+songBean.getSong_singer()+"','"+songBean.getType_id()+"'," +
//                "'"+songBean.getSong_size()+"','"+songBean.getSong_url()+"','"+songBean.getSong_format()+"'," +
//                "'"+songBean.getSong_clicks()+"','"+songBean.getSong_download()+"'," +
//                "'"+simpleDateFormat.format(songBean.getSong_uptime())+"','"+songBean.getVip_id()+"')";
//        Boolean flag = dao.addObj(sql);
//        return flag;
//    }
}
