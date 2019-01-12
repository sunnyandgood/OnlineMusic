package com.edu.service;

import com.edu.bean.SongDisplayBean;
import com.edu.bean.SongtypeBean;
import com.edu.bean.UserDisplayBean;
import com.edu.bean.VipBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/10 20:09
 */
public interface UtilService {
    List<VipBean> selectVip();
    List<VipBean> selectVip(Integer vip_id);
    List<SongtypeBean> selectSongType();
    List<UserDisplayBean> selectByUserId(Integer user_id);
    List<SongDisplayBean> selectSongByTypeId(Integer type_id);
    List<SongDisplayBean> selectSongs();
    List<SongDisplayBean> selectSongs(String queryInfo);
}
