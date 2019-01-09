package com.edu.service;

import com.edu.bean.ManagerBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 19:18
 */
public interface ManagerService {
    List<ManagerBean> listAll();
    Boolean deleteById(int manager_id);
    Boolean insert(ManagerBean managerBean);
    List<ManagerBean> selectById(int manager_id);
    Boolean update(ManagerBean managerBean);
}
