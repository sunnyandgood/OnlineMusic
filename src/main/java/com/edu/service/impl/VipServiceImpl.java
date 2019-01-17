package com.edu.service.impl;

import com.edu.bean.VipBean;
import com.edu.dao.Dao;
import com.edu.service.VipService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 8:57
 */
public class VipServiceImpl implements VipService {
    private Dao dao = new Dao();

    @Override
    public List<VipBean> listAll() {
        String sql = "select * from vip";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public Boolean deletById(int vip_id) {
        String sql = "delete from vip where vip_id = '"+vip_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean insert(VipBean vipBean) {
        String sql = "insert into vip (vip) values ('"+vipBean.getVip()+"')";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<VipBean> selectById(int vip_id) {
        String sql = "select * from vip where vip_id = '"+vip_id+"'";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public Boolean update(VipBean vipBean) {
        String sql = "update vip set vip = '"+vipBean.getVip()+"' where vip_id = '"+vipBean.getVip_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<VipBean> selectVip(Integer vip_id) {
        String sql = "select * from vip where vip_id <= '"+vip_id+"'";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

    @Override
    public List<VipBean> selectUpVip(Integer vip_id) {
        String sql = "select * from vip where vip_id > '"+vip_id+"'";
        List<VipBean> list = (List<VipBean>) dao.query(sql, VipBean.class);
        return list;
    }

}
