package com.viewpoint.repository;

import com.viewpoint.dataobject.ExamLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamLevelRepository extends JpaRepository<ExamLevel,Integer> {
    List<ExamLevel> findByeEnabledOrderBySortDesc(Integer enabled);
    List<ExamLevel> findByLevelIdNotNullOrderBySortAsc();

    ExamLevel findBySort(Integer sort);
}
