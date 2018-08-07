package com.viewpoint.repository;

import com.viewpoint.dataobject.ActivityOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityOrderRepository extends JpaRepository<ActivityOrder,String> {

    Page<ActivityOrder> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
