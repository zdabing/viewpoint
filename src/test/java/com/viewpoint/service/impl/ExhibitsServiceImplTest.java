package com.viewpoint.service.impl;

import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.dto.ExhibitsDTO;
import com.viewpoint.service.ExhibitsService;
import org.dom4j.tree.BaseElement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExhibitsServiceImplTest {

    @Autowired
    private ExhibitsService exhibitsService;

    @Test
    public void findAllMaster() {
        Page<ExhibitsDTO> exhibitsDTOPage = exhibitsService.findAllMaster(PageRequest.of(0,10));
        System.out.println(exhibitsDTOPage);
    }

    @Test
    public void savaBatch(){
        ExhibitsInfo exhibitsDTO = new ExhibitsInfo();
        exhibitsDTO.setExhibitsId("123456");
        exhibitsDTO.setExhibitsName("不懂");
        exhibitsDTO.setExhibitsIcon("12345");
        exhibitsDTO.setExhibitsIcon("12345");
        exhibitsDTO.setExhibitsDescription("儿童游泳池");
        exhibitsDTO.setExhibitsContent("斤斤计较军军军军");
        exhibitsDTO.setExhibitsLink("hhhhhhhhhhhhhh");
        ExhibitsInfo exhibitsInfo2 = new ExhibitsInfo();
        ExhibitsInfo exhibitsInfo3 = new ExhibitsInfo();
        BeanUtils.copyProperties(exhibitsDTO,exhibitsInfo2);
        exhibitsInfo2.setExhibitsId("321654");
        BeanUtils.copyProperties(exhibitsDTO,exhibitsInfo3);
        exhibitsInfo3.setExhibitsId("123");
        List<ExhibitsInfo> exhibitsInfoList = new ArrayList<>();
        exhibitsInfoList.add(exhibitsDTO);
        exhibitsInfoList.add(exhibitsInfo2);
        exhibitsInfoList.add(exhibitsInfo3);
        exhibitsService.savaBatch(exhibitsInfoList);
    }
}