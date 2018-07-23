package com.viewpoint.service.impl;

import com.viewpoint.dataobject.User;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.repository.UserRepository;
import com.viewpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAdmin() {
        return userRepository.findByRole(StatusEnum.ADMIN.getCode());
    }

    @Override
    public User save(User user) {
        return null;
    }
}
