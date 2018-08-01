package com.viewpoint.service.impl;

import com.viewpoint.converter.ExhibitsInfo2ExhibitsDTO;
import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.dto.ExhibitsDTO;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.ExhibitsInfoRepository;
import com.viewpoint.service.ExhibitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExhibitsServiceImpl implements ExhibitsService {

    @Autowired
    private ExhibitsInfoRepository exhibitsInfoRepository;

    @Override
    public Page<ExhibitsDTO> findAllMaster(Pageable pageable) {
        // 找出所有的展品
        Page<ExhibitsInfo> exhibitsInfoPage = exhibitsInfoRepository.findAll(pageable);
        List<ExhibitsInfo> exhibitsInfoList = exhibitsInfoPage.getContent();
        // 主展品
        List<ExhibitsDTO> exhibitsDTOList = exhibitsInfoList.stream().filter(e -> StringUtils.isEmpty(e.getParentId())).map(e -> ExhibitsInfo2ExhibitsDTO.convert(e)).collect(Collectors.toList());
        // 附属展品
        exhibitsDTOList.forEach(e ->{
            e.setExhibitsItemList(exhibitsInfoList.stream().filter(a -> e.getExhibitsId().equals(a.getParentId()) ).collect(Collectors.toList()));
        });
        Page<ExhibitsDTO> exhibitsDTOPage = new PageImpl<>(exhibitsDTOList);
        return exhibitsDTOPage;
    }

    @Override
    public void savaBatch(List<ExhibitsInfo> exhibitsInfoList) {
        exhibitsInfoRepository.saveAll(exhibitsInfoList);
    }

    @Override
    @Transactional
    public void save(ExhibitsInfo exhibitsInfo) {
        exhibitsInfoRepository.save(exhibitsInfo);
    }

    @Override
    @Transactional
    public void delBatch(String exhibitsId) {
        exhibitsInfoRepository.deleteById(exhibitsId);
        exhibitsInfoRepository.deleteByParentId(exhibitsId);
    }

    @Override
    @Transactional
    public void updateSale(String exhibitsId, Integer exhibitsStatus) {
        ExhibitsInfo exhibitsInfo = exhibitsInfoRepository.findById(exhibitsId).orElse(null);
        if (exhibitsInfo == null){
            throw new ViewpointException(ResultEnum.EXHIBITS_NOT_EXIST);
        }
        exhibitsInfoRepository.save(exhibitsInfo);
        List<ExhibitsInfo> exhibitsInfoList = exhibitsInfoRepository.findByParentId(exhibitsId);
        exhibitsInfoRepository.saveAll(exhibitsInfoList);
    }

    @Override
    public Page<ExhibitsInfo> findByParentId(String patentId, Pageable pageable) {
        Page<ExhibitsInfo> exhibitsInfoPage = exhibitsInfoRepository.findByParentId(patentId,pageable);
        return exhibitsInfoPage;
    }

    @Override
    public List<ExhibitsInfo> findByParentId(String patentId) {
        List<ExhibitsInfo> exhibitsInfoList = exhibitsInfoRepository.findByParentId(patentId);
        return exhibitsInfoList;
    }

    @Override
    public ExhibitsInfo findOne(String exhibitsId) {
        ExhibitsInfo exhibitsInfo = exhibitsInfoRepository.findById(exhibitsId).orElse(null);
        return exhibitsInfo;
    }
}
