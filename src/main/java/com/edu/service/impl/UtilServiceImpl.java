package com.edu.service.impl;

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
}
