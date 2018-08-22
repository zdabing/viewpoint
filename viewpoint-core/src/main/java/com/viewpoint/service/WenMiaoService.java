package com.viewpoint.service;

import com.viewpoint.dataobject.View;

public interface WenMiaoService {

    View Save(View view,String viewCode);

    View getById(String viewCode);

    View getByViewCode(String viewCode);
}
