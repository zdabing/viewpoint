package com.viewpoint.repository;

import com.viewpoint.dataobject.HistoryFootprints;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface HistoryFootprintsRepository extends JpaRepository<HistoryFootprints, Integer> {


    List<HistoryFootprints> findByHistoryIdNotNullOrderByStartYearAsc();

    List<HistoryFootprints> findByHistoryIdNotNullOrderBySortAsc();

    HistoryFootprints findBySort(Integer sort);
}
