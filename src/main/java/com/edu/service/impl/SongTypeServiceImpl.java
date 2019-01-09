package com.edu.service.impl;

import com.edu.bean.SongtypeBean;
import com.edu.dao.Dao;
import com.edu.service.SongTypeService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 21:22
 */
public class SongTypeServiceImpl implements SongTypeService {
    private Dao dao = new Dao();

    @Override
    public List<SongtypeBean> listAll() {
        String sql = "select * from songtype";
        List<SongtypeBean> list = (List<SongtypeBean>) dao.query(sql, SongtypeBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int type_id) {
        String sql = "delete from songtype where type_id = '"+type_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean insert(SongtypeBean songtypeBean) {
        String sql = "insert into songtype (type_name) values ('"+songtypeBean.getType_name()+"')";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean update(SongtypeBean songtypeBean) {
        String sql = "update songtype set type_name = '"+songtypeBean.getType_name()+"' " +
                "where type_id = '"+songtypeBean.getType_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<SongtypeBean> selectById(int type_id) {
        String sql = "select * from songtype where type_id = '"+type_id+"'";
        List<SongtypeBean> list = (List<SongtypeBean>) dao.query(sql, SongtypeBean.class);
        return list;
    }
}
