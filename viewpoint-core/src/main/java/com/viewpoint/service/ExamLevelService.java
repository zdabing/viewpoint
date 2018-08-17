package com.viewpoint.service;

import com.viewpoint.dataobject.ExamLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExamLevelService {

    /**
     *查询所有的等级
     */
    List<ExamLevel> findAll();

    //更新上下架操作
    ExamLevel updateSale(Integer levelId, Integer enabled);

    ExamLevel findById(Integer levelId);

    ExamLevel save(ExamLevel examLevel);

    void delete(Integer levelId);

    List<ExamLevel> findAllBySort();

    ExamLevel findBySort(Integer sort);
}
