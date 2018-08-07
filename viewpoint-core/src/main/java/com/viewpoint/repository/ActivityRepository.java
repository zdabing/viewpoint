package com.viewpoint.repository;

import com.viewpoint.dataobject.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity,String> {

    List<Activity> findByEnabledOrderByReleaseTimeDesc(Integer enabled);
}
