package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.service.ActivityService;
import com.viewpoint.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ActivityServiceImplTest {

    @Autowired
    private ActivityService activityService;

    @Test
    public void findAll() {
        Page<Activity> activityPage = activityService.findAll(PageRequest.of(0,10));
        Assert.assertNotEquals(0,activityPage.getTotalElements());
    }

    @Test
    public void findOne() {
        Activity activity = activityService.findOne("1531474711705682940");
        log.info("activity={}",activity);
    }

    @Test
    public void save() {
        Activity activity = new Activity();
        activity.setActivityId(KeyUtil.genUniqueKey());
        activity.setActivityDesc("不是很懂哇");
        activity.setActivityLogo("https://attach.bbs.miui.com/album/201805/24/125721cv2zbiii3ou2q4v4.png");
        activity.setActivityContent("打电话");
        activity.setActivityName("学习学习");
        activity.setActivityTag("旅游");
        activity.setStartTime(LocalDateTime.now());
        activity.setEndTime(LocalDateTime.now().plusDays(1));
        Activity activity1 = activityService.save(activity);
        log.info("activity1={}",activity1);
    }

    @Test
    public void delete() {
        activityService.delete("1531475322888136736");
    }

    @Test
    public void updateSale() {
        Activity activity = activityService.updateSale("1531907116453637601",0);
        System.out.println(activity.getEnabled());
    }
}