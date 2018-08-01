package com.viewpoint.service;

import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.dto.ExhibitsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExhibitsService {
    /**
     * 找出所有主节点
     * @param pageable
     * @return
     */
    Page<ExhibitsDTO> findAllMaster(Pageable pageable);

    void savaBatch(List<ExhibitsInfo> exhibitsInfoList);
}
