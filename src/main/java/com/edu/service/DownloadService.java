package com.edu.service;

import com.edu.bean.DownloadBean;

import java.util.List;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/9 17:00
 */
public interface DownloadService {
    List<DownloadBean> selectAll();
    Boolean deleteById(int download_id);
}
