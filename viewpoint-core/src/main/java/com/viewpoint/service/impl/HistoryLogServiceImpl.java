package com.viewpoint.service.impl;

import com.viewpoint.dataobject.HistoryLog;
import com.viewpoint.repository.HistoryLogRepository;
import com.viewpoint.service.HistoryLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryLogServiceImpl implements HistoryLogService {

    @Autowired
    private HistoryLogRepository historyLogRepository;

    @Override
    public void save(String productId, String openid) {
        HistoryLog historyLog = new HistoryLog();
        historyLog.setMasterId(productId);
        historyLog.setOpenid(openid);
        historyLogRepository.save(historyLog);
    }

    @Override
    public List<HistoryLog> findByOpenid(String openid) {
        return historyLogRepository.findByOpenid(openid);
    }
}
