package com.viewpoint.service.impl;

import com.viewpoint.dataobject.View;
import com.viewpoint.repository.WenMiaoRepository;
import com.viewpoint.service.WenMiaoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WenMiaoServiceImpl implements WenMiaoService {

    @Autowired
    private WenMiaoRepository wenMiaoRepository;

    @Override
    public View Save(View view,String viewCode) {
        view.setViewCode(viewCode);
        return wenMiaoRepository.save(view);
    }

    @Override
    public View getById(String viewCode) {
        Optional<View> view =  wenMiaoRepository.findById(viewCode);
        return view.orElse(null);
    }

    @Override
    public View getByViewCode(String viewCode) {
        return wenMiaoRepository.getByViewCode(viewCode);
    }
}
