package com.viewpoint.repository;

import com.viewpoint.dataobject.HistoryLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryLogRepository extends JpaRepository<HistoryLog,Integer> {

    List<HistoryLog> findByOpenidOrderByCreateTimeAsc(String openid);

    HistoryLog findByOpenidAndMasterId(String openid, String masterId);
}
