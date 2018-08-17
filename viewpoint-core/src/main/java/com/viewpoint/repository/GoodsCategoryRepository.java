package com.viewpoint.repository;

import com.viewpoint.dataobject.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, String> {

    List<GoodsCategory> findByEnabled(Integer enabled);
}
