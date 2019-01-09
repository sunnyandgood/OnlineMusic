package com.edu.service;

import com.edu.bean.SongtypeBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 21:21
 */
public interface SongTypeService {
    List<SongtypeBean> listAll();
    Boolean deleteById(int type_id);
    Boolean insert(SongtypeBean songtypeBean);
    List<SongtypeBean> selectById(int type_id);
    Boolean update(SongtypeBean songtypeBean);
}
