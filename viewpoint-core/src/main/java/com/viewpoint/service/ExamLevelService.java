package com.viewpoint.service;

import com.viewpoint.dataobject.ExamLevel;

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

    /**
     * 查询已上架的等级 排序按照Sort
     * @return
     */
    List<ExamLevel> findUpAllOrderBySort();

}
