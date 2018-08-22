package com.viewpoint.service.impl;

import com.viewpoint.dataobject.GoodsCategory;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.repository.GoodsCategoryRepository;
import com.viewpoint.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryRepository goodsCategoryRepository;

    @Override
    public Page<GoodsCategory> findAll(Pageable pageable) {
        return goodsCategoryRepository.findAll(pageable);
    }

    @Override
    public GoodsCategory findById(String goodsId) {
        Optional<GoodsCategory> goodsCategoryOptional = goodsCategoryRepository.findById(goodsId);
        return  goodsCategoryOptional.orElse(null);
    }

    @Override
    @Transactional
    public GoodsCategory save(GoodsCategory goodsCategory) {
        return goodsCategoryRepository.save(goodsCategory);
    }

    @Override
    public GoodsCategory updateSale(String goodsId, Integer enabled) {
        GoodsCategory goodsCategory = this.findById(goodsId);
        goodsCategory.setEnabled(enabled);
        return this.save(goodsCategory);
    }

    @Override
    public void delete(String goodsId) {
        goodsCategoryRepository.deleteById(goodsId);
    }

    @Override
    public List<GoodsCategory> findUpAll() {
        return goodsCategoryRepository.findByEnabled(StatusEnum.UP.getCode());
    }
}
