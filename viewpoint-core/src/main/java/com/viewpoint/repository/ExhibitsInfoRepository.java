package com.viewpoint.repository;

import com.viewpoint.dataobject.ExhibitsInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExhibitsInfoRepository extends JpaRepository<ExhibitsInfo,String> {

    void deleteByParentId(String patentId);

    List<ExhibitsInfo> findByParentId(String patentId);

    Page<ExhibitsInfo> findByParentId(String patentId, Pageable pageable);

    List<ExhibitsInfo> findByAreasId(String areasId);
}
