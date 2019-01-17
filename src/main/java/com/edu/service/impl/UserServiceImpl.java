package com.edu.service.impl;

import com.edu.bean.UserBean;
import com.edu.dao.Dao;
import com.edu.service.UserService;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 10:03
 */
public class UserServiceImpl implements UserService {
    private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Dao dao = new Dao();

    @Override
    public Boolean recharge(Integer user_id, Integer vip_id) {
        String sql = "update user set vip_id = '"+vip_id+"' where user_id = '"+user_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<UserBean> selectAll() {
        String sql = "select user_id,user_name,user_password,vip,user_birthday,user_gender,type_name " +
                "from user,vip,songtype " +
                "where user.vip_id = vip.vip_id and user.type_id = songtype.type_id";
        List<UserBean> list = (List<UserBean>) dao.query(sql, UserBean.class);
        return list;
    }

    @Override
    public Boolean deletById(int user_id) {
        String sql = "delete from user where user_id = '"+user_id+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<UserBean> selectById(int user_id) {
        String sql = "select *,vip,type_name from user,vip,songtype where user_id = '"+user_id+"' " +
                "and user.vip_id = vip.vip_id and user.type_id = songtype.type_id";
        List<UserBean> list = (List<UserBean>) dao.query(sql, UserBean.class);
        return list;
    }

    @Override
    public Boolean updateById(UserBean userBean) {
        String sql = "update user set user_name = '"+userBean.getUser_name()+"' , " +
                "user_password = '"+userBean.getUser_password()+"' , vip_id = '"+userBean.getVip_id()+"' ," +
                "user_birthday = '"+simpleDateFormat.format(userBean.getUser_birthday())+"' , " +
                "user_gender = '"+userBean.getUser_gender()+"' , type_id = '"+userBean.getType_id()+"' " +
                "where user_id = '"+userBean.getUser_id()+"'";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public Boolean insert(UserBean userBean) {
        String sql = "insert into user (user_name,user_password,vip_id,user_birthday,user_gender,type_id) " +
                "values ('"+userBean.getUser_name()+"','"+userBean.getUser_password()+"'," +
                "'"+userBean.getVip_id()+"','"+simpleDateFormat.format(userBean.getUser_birthday())+"'," +
                "'"+userBean.getUser_gender()+"','"+userBean.getType_id()+"')";
        Boolean flag = dao.addObj(sql);
        return flag;
    }

    @Override
    public List<UserBean> listAll() {
        String sql = "select * from user";
        List<UserBean> list = (List<UserBean>) dao.query(sql, UserBean.class);
        return list;
    }
}
