package com.viewpoint.service;

import com.viewpoint.dataobject.HistoryFootprints;

import java.util.List;

public interface HistoryFootprintsService {

    List<HistoryFootprints> findAll();

    HistoryFootprints save(HistoryFootprints historyFootprints);

    HistoryFootprints findById(Integer historyId);

    void delete(Integer historyId);

    List<HistoryFootprints> orderByStartTime();

    List<HistoryFootprints> orderBySort();
}
