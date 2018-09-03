package com.viewpoint.service;

import com.viewpoint.dataobject.HistoryLog;

import java.util.List;

public interface HistoryLogService {
    /**
     * 生成展品历史记录
     * @param productId 展品id
     * @param openid 微信openid
     */
    void save(String productId, String openid);

    /**
     * 根据openid查询历史记录
     * @param openid
     * @return
     */
    List<HistoryLog> findByOpenid(String openid);

}
