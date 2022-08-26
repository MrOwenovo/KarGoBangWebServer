package com.example.service;

import com.example.entity.data.HallInfoDetail;

public interface HallService {

    /**
     * 返回大厅房间信息
     * @return 大厅房间信息
     */
    HallInfoDetail[] getHallInfo();

}
