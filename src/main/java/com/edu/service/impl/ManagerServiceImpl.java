package com.edu.service.impl;

import com.edu.bean.ManagerBean;
import com.edu.dao.Dao;
import com.edu.service.ManagerService;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 19:18
 */
public class ManagerServiceImpl implements ManagerService {
    Dao dao = new Dao();

    @Override
    public List<ManagerBean> listAll() {
        String sql = "select * from manager";
        List<ManagerBean> list = (List<ManagerBean>) dao.query(sql, ManagerBean.class);
        return list;
    }

    @Override
    public Boolean deleteById(int manager_id) {
        String sql = "delete from manager where manager_id = '"+manager_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean insert(ManagerBean managerBean) {
        String sql = "insert into manager (manager_name,manager_password) " +
                "values ('"+managerBean.getManager_name()+"','"+managerBean.getManager_password()+"')";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<ManagerBean> selectById(int manager_id) {
        String sql = "select * from manager where manager_id = '"+manager_id+"'";
        List<ManagerBean> list = (List<ManagerBean>) dao.query(sql, ManagerBean.class);
        return list;
    }

    @Override
    public Boolean update(ManagerBean managerBean) {
        String sql = "update manager set manager_name = '"+managerBean.getManager_name()+"', " +
                "manager_password = '"+managerBean.getManager_password()+"' " +
                "where  manager_id = '"+managerBean.getManager_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }
}
