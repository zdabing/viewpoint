package com.viewpoint.service;

import com.viewpoint.dataobject.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ActivityService {

    /** 查询已发布的活动 */
    List<Activity> findUpAll();

    /** 查询所有活动 */
    Page<Activity> findAll(Pageable pageable);

    /** 查看活动 */
    Activity findOne(String activityId);

    /** 添加活动 */
    Activity save(Activity activity);

    /** 上架 */
    Activity onSale(String activityId);

    /** 上架 */
    Activity offSale(String activityId);

    void delete(String activityId);
}
