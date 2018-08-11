package com.viewpoint.repository;

import com.viewpoint.dataobject.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    Page<User> findByRole(Integer role, Pageable pageable);

    User findByName(String name);

    User findByOpenid(String openid);
}
