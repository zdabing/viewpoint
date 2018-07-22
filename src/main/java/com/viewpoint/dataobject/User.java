package com.viewpoint.dataobject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {

    @Id
    private int id;

    private String name;

    private String password;

    private String openid;

    private String alias;

    private String icon;
}
