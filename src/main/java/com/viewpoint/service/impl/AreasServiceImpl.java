package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Areas;
import com.viewpoint.repository.AreasRepository;
import com.viewpoint.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AreasServiceImpl implements AreasService {

    @Autowired
    private AreasRepository areasRepository;

    /**
     * 增加景点
     * @param areas
     * @return
     */
    @Override
    public Areas addArea(Areas areas) {
        return areasRepository.save(areas);
    }

    /**
     * 查询所有的景点
     * @return
     */
    @Override
    public Page<Areas> findAll(Pageable pageable) {
        return areasRepository.findAll(pageable);
    }

    /**
     * 通过Id查询景点
     * @return
     */
    @Override
    public Areas findByAreasId(Integer areasId) {
        Optional<Areas> areasOptional =  areasRepository.findById(areasId);
        return areasOptional.orElse(null);
    }

    /**
     * 保存景点
     * @return
     */
    @Override
    public Areas save(Areas areas) {
        //保存新的景点
        if(StringUtils.isEmpty(areas.getAreasId())){
            areas.setCreateTime(LocalDateTime.now());
            areas.setUpdateTime(LocalDateTime.now());
        }else{
            //修改景点
            areas.setUpdateTime(LocalDateTime.now());
        }
        return areasRepository.save(areas);
    }

    /**
     * 删除景点
     * @return
     */
    @Override
    public void deleteByAreasId(Integer areasId) {
       areasRepository.deleteById(areasId);
    }

    @Override
    public List<Areas> findAll() {
        List<Areas> areasList = areasRepository.findAll();
        return areasList;
    }
}
