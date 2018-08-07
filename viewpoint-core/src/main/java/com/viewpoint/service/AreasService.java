package com.viewpoint.service;

import com.viewpoint.dataobject.Areas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface AreasService {

    /**
     * 增加景点
     * @return
     */
    Areas addArea(Areas areas);

    /**
     * 查询所有的景点
     * @return Page<Areas>
     */
    Page<Areas> findAll(Pageable pageable);

    /**
     * 通过Id查询景点
     * @return
     */
    Areas findByAreasId(Integer areasId);

    /**
     * 保存景点
     * @return
     */
    Areas save(Areas areas);

    /**
     * 删除景点
     * @return
     */
    void deleteByAreasId(Integer areasId);

    /**
     * 查询所有的景点
     * @return Page<Areas>
     */
    List<Areas> findAll();
}
