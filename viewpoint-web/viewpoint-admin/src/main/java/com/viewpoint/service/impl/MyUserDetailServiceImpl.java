package com.viewpoint.service.impl;


import com.viewpoint.exception.ValidateCodeException;
import com.viewpoint.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component("userDetailsService")
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查找用户
        com.viewpoint.dataobject.User user = userRepository.findByName(username);
        if (user == null){
            //抛出异常，会根据配置跳到登录失败页面
            throw new ValidateCodeException("账户不存在！");
        }
        //GrantedAuthority是security提供的权限类 可以放权限进去
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        //String pass = passwordEncoder.encode("123456");
        return new User(username,user.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

}
