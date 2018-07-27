package com.viewpoint.dto;

import com.viewpoint.dataobject.ExhibitsInfo;
import lombok.Data;

import java.util.List;

@Data
public class ExhibitsDTO extends ExhibitsInfo{

    List<ExhibitsInfo> exhibitsItemList;

}
