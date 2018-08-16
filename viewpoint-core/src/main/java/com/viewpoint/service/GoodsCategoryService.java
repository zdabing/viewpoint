package com.viewpoint.service;

import com.viewpoint.dataobject.GoodsCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsCategoryService {

    Page<GoodsCategory> findAll(Pageable pageable);

    GoodsCategory findById(String goodsId);

    GoodsCategory save(GoodsCategory goodsCategory);

    GoodsCategory updateSale(String goodsId,Integer enabled);

    void delete(String goodsId);
}
