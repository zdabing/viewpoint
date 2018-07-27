package com.viewpoint.converter;

import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.dto.ExhibitsDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ExhibitsInfo2ExhibitsDTO {

    public static ExhibitsDTO convert(ExhibitsInfo exhibitsInfo){
        ExhibitsDTO exhibits = new ExhibitsDTO();
        BeanUtils.copyProperties(exhibitsInfo,exhibits);
        return exhibits;
    }

    public static List<ExhibitsDTO> convert(List<ExhibitsInfo> exhibitsInfoList) {
        return exhibitsInfoList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
