package com.viewpoint.service.impl;

import com.viewpoint.dataobject.HistoryFootprints;
import com.viewpoint.dataobject.HistoryLog;
import com.viewpoint.repository.HistoryFootprintsRepository;
import com.viewpoint.service.HistoryFootprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryFootprintsServiceImpl implements HistoryFootprintsService {

    @Autowired
    private HistoryFootprintsRepository historyFootprintsRepository;

    @Override
    public List<HistoryFootprints> findAll() {
        return historyFootprintsRepository.findAll();
    }

    @Override
    public HistoryFootprints save(HistoryFootprints historyFootprints) {
        return historyFootprintsRepository.save(historyFootprints);
    }

    @Override
    public HistoryFootprints findById(Integer historyId) {
        Optional<HistoryFootprints> historyFootprintsOptional = historyFootprintsRepository.findById(historyId);
        return historyFootprintsOptional.orElse(null);
    }

    @Override
    public void delete(Integer historyId) {
        historyFootprintsRepository.deleteById(historyId);
    }

    @Override
    public List<HistoryFootprints> orderByStartTime() {
        return historyFootprintsRepository.findByHistoryIdNotNullOrderByStartYearAsc();
    }

    @Override
    public List<HistoryFootprints> orderBySort() {
        return historyFootprintsRepository.findByHistoryIdNotNullOrderBySortAsc();
    }
}
