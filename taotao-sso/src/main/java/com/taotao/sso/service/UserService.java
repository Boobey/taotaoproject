package com.taotao.sso.service;

import com.mysql.fabric.xmlrpc.base.Param;
import com.taotao.sso.mapper.UserMapper;
import com.taotao.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public Boolean check(String param, Integer type) {
        User record = new User();

        switch (type) {
            case 1:
                record.setUsername(param);
                break;
            case 2:
                record.setPhone(param);
                break;
            case 3:
                record.setEmail(param);
                break;
            default:
                // 参数有误
                return null;
        }

        User user = this.userMapper.selectOne(record);
        return user == null;
    }

    public Boolean doRegister(User user) {
        // 初始化
        user.setId(null);
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        // 加密处理,MD5加密
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return this.userMapper.insert(user) == 1;
    }
}