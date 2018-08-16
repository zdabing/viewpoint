package com.viewpoint.repository;

import com.viewpoint.dataobject.HistoryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryPersonRepository extends JpaRepository<HistoryPerson, Integer> {

    List<HistoryPerson> findByLevelIdAndEnabled(String levelId ,Integer enabled);
}
