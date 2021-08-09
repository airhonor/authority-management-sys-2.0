package com.hz.oauth2.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这里可以看作数据库实体
 *
 * @author: yaohw
 * @create: 2019-10-12 16:15
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Integer id;

    private String username;

    private String password;

    private String test;
}
