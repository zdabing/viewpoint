package com.viewpoint.service;

import com.viewpoint.dataobject.HistoryPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HistoryPersonService {

   Page<HistoryPerson> findAll(Pageable pageable);

   //上下架的更新
    HistoryPerson updateSale(Integer personId, Integer enabled);

    HistoryPerson findById(Integer personId);

    HistoryPerson save(HistoryPerson historyPerson);

    void deleteById(Integer personId);


}
