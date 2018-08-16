package com.viewpoint.repository;

import com.viewpoint.dataobject.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, String> {

}
