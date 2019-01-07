package com.edu.bean;

import lombok.Data;

/**
 * @Author: 王仁洪
 * @Date: 2019/1/7 16:33
 */
@Data
public class DownloadBean {
    private Integer download_id;
    private Integer user_id;
    private Integer song_id;
    private Integer download_times;
}
