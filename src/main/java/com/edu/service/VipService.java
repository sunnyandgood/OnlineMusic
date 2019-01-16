package com.edu.service;

import com.edu.bean.VipBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 8:57
 */
public interface VipService {
    List<VipBean> listAll();
    Boolean deletById(int vip_id);
    Boolean insert(VipBean vipBean);
    List<VipBean> selectById(int vip_id);
    Boolean update(VipBean vipBean);
    List<VipBean> selectVip(Integer vip_id);
}
