package com.viewpoint.repository;

import com.viewpoint.dataobject.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    List<User> findByRole(Integer role);
}
