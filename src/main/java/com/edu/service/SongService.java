package com.edu.service;

import com.edu.bean.SongBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 19:08
 */
public interface SongService {
    List<SongBean> selectAll();
    Boolean deleteById(int song_id);
    List<SongBean> selectById(int song_id);
    Boolean updateById(SongBean songBean);
}