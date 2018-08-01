package com.viewpoint.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 景点的坐标
 */
@Data
public class AreasCoordinateForm {

    @NotEmpty
    private Double left;

    @NotEmpty
    private Double top;
}
