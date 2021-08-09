package com.hz.oauth2.server.service.impl;

import com.hz.oauth2.server.entity.Role;
import com.hz.oauth2.server.entity.User;
import com.hz.oauth2.server.entity.authority.SysUserDetails;
import com.hz.oauth2.server.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: authority-management-sys-2.0
 * @author: zgr
 * @create: 2021-08-03 09:58
 **/

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名{}查询用户信息", username);
        //此处实际可以从数据库中查找对应的用户名
        User user;
        if (username.equals("admin")) {
            user = User.builder()
                    .id(1)
                    .username("admin")
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .test("this is test")
                    .build();
        } else {
            throw new UsernameNotFoundException(username + "不存在");
        }
        Role role1 = Role.builder()
                .id(1)
                .name("READ")
                .build();
        Role role2 = Role.builder()
                .id(1)
                .name("WRITE")
                .build();

        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        //这里权限列表,这个为方便直接下（实际开发中查询用户时连表查询出权限）
        return new SysUserDetails(user.getId(), user.getUsername(), user.getPassword(), roles);
    }
}
