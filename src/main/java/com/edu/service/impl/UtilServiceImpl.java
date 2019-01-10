package com.edu.service.impl;

import com.edu.bean.SongtypeBean;
import com.edu.bean.VipBean;
import com.edu.dao.Dao;
import com.edu.service.UtilService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:09
 */
public class UtilServiceImpl implements UtilService {
    @Override
    public List<VipBean> selectVip() {
        Dao dao = new Dao();
        String sql = "select * from vip";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public List<SongtypeBean> selectSongType() {
        Dao dao = new Dao();
        String sql = "select * from songtype";
        List<SongtypeBean> list = (List<SongtypeBean>) dao.query(sql, SongtypeBean.class);
        return list;
    }
}
