package com.edu.service;

import com.edu.bean.SongtypeBean;
import com.edu.bean.VipBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:09
 */
public interface UtilService {
    List<VipBean> selectVip();
    List<SongtypeBean> selectSongType();
}
