package com.viewpoint.repository;

import com.viewpoint.dataobject.Activity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    public void findByEnabled() {
        List<Activity> activityList = activityRepository.findByEnabled(1);
        log.info("已上架活动，activityList={}",activityList);
    }
}