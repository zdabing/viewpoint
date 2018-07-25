package com.viewpoint.service.impl;

import com.viewpoint.dataobject.User;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.UserRepository;
import com.viewpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAdmin() {
        return userRepository.findByRole(StatusEnum.ADMIN.getCode());
    }

    @Override
    public User findByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null){
            throw new ViewpointException(ResultEnum.USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> findRegistered() {
        return userRepository.findByRole(StatusEnum.REGISTERED.getCode());
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
