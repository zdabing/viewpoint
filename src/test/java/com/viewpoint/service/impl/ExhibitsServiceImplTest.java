package com.viewpoint.service.impl;

import com.viewpoint.dto.ExhibitsDTO;
import com.viewpoint.service.ExhibitsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

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
}