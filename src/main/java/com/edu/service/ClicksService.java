package com.edu.service;

import com.edu.bean.ClicksBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 18:39
 */
public interface ClicksService {
    List<ClicksBean> selectAll();
    Boolean deleteById(int click_id);
}
