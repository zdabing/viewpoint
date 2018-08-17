package com.viewpoint.service.impl;

import com.viewpoint.dataobject.HistoryPerson;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.repository.HistoryPersonRepository;
import com.viewpoint.service.HistoryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryPersonServiceImpl implements HistoryPersonService {

    @Autowired
    private HistoryPersonRepository historyPersonRepository;

    @Override
    public Page<HistoryPerson> findAll(Pageable pageable) {
       return historyPersonRepository.findAll(pageable);
    }

    //改变上下架状态
    @Override
    public HistoryPerson updateSale(Integer personId, Integer enabled) {
        Optional<HistoryPerson> historyPersonOptional = historyPersonRepository.findById(personId);
        HistoryPerson historyPerson = historyPersonOptional.orElse(null);
        if (historyPerson != null){
            historyPerson.setEnabled(enabled);
        }
        return historyPersonRepository.save(historyPerson);
    }

    //根据personId查询
    @Override
    public HistoryPerson findById(Integer personId) {
        Optional<HistoryPerson> historyPersonOptional = historyPersonRepository.findById(personId);
        return historyPersonOptional.orElse(null);
    }

    @Override
    public HistoryPerson save(HistoryPerson historyPerson) {
        return historyPersonRepository.save(historyPerson);
    }

    @Override
    public void deleteById(Integer personId) {
        historyPersonRepository.deleteById(personId);
    }

    @Override
    public List<HistoryPerson> findUpHistoryPersonList(String levelId) {

        return historyPersonRepository.findByLevelIdAndEnabled(levelId, StatusEnum.UP.getCode());
    }
}
