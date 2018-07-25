package com.viewpoint.service;

import com.viewpoint.dataobject.User;

import java.util.List;

public interface UserService {

    /** 管理员 */
    List<User> findAdmin();

    User findByName(String name);

    User save(User user);

    User findById(Integer id);

    /** 已注册 */
    List<User> findRegistered();

    void delete(Integer id);
}
