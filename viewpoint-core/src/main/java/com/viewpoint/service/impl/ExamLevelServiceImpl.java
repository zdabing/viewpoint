package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Article;
import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.repository.ExamLevelRepository;
import com.viewpoint.service.ExamLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamLevelServiceImpl implements ExamLevelService {

    @Autowired
    private ExamLevelRepository examLevelRepository;

    @Override
    public List<ExamLevel> findAll() {
        return examLevelRepository.findAll();
    }

    //改变上下架状态
    @Override
    public ExamLevel updateSale(Integer levelId, Integer enabled) {
        Optional<ExamLevel> examLevelOptional = examLevelRepository.findById(levelId);
        ExamLevel examLevel = examLevelOptional.orElse(null);
        if (examLevel != null){
            examLevel.setEnabled(enabled);
        }
        return examLevelRepository.save(examLevel);
    }

    @Override
    public ExamLevel findById(Integer levelId) {
        Optional<ExamLevel> examLevelOptional = examLevelRepository.findById(levelId);
        return examLevelOptional.orElse(null);
    }

    //新增或修改
    @Override
    public ExamLevel save(ExamLevel examLevel) {
        return examLevelRepository.save(examLevel);
    }

    @Override
    public void delete(Integer levelId) {
        examLevelRepository.deleteById(levelId);
    }

}
