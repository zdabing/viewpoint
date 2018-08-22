package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class View implements Serializable {

    private static final long serialVersionUID = 5125039875205644519L;

    @Id
    private String viewCode;

    private String viewDes;

    private String viewIcon;

    private String viewContent;

    private String viewMp4;
}
