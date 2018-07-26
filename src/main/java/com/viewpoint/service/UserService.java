package com.viewpoint.service;

import com.viewpoint.dataobject.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User findByName(String name);

    User save(User user);

    User findById(Integer id);

    void delete(Integer id);

    Page<User> findByRole(Integer role, Pageable pageable);
}
