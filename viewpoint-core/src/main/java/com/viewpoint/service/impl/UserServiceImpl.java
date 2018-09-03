package com.viewpoint.service.impl;

import com.viewpoint.dataobject.User;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.UserRepository;
import com.viewpoint.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
        User user1 = userRepository.findByOpenid(user.getOpenid());
        if (user1 != null) {
            return user1;
        }
         return userRepository.save(user);
    }

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findByRole(Integer role,Pageable pageable) {
        return userRepository.findByRole(role,pageable);
    }

    @Override
    public User findByOpenid(String openid) {
        return userRepository.findByOpenid(openid);
    }
}
