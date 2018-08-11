package com.viewpoint.repository;

import com.viewpoint.dataobject.ActivityOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityOrderRepository extends JpaRepository<ActivityOrder,String> {

    List<ActivityOrder> findByBuyerOpenid(String buyerOpenid);

    long countByActivityId(String activityId);
}
