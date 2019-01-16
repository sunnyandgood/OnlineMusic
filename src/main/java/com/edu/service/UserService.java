package com.edu.service;

import com.edu.bean.UserBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 10:03
 */
public interface UserService {
    List<UserBean> selectAll();
    Boolean deletById(int user_id);
    List<UserBean> selectById(int user_id);
    Boolean updateById(UserBean userBean);
    Boolean insert(UserBean userBean);
    List<UserBean> listAll();
}
