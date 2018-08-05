package com.viewpoint.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 5125039875205644519L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String password;

    private String openid;

    private String alias;

    private String icon;

    private Integer role = 0;
}
