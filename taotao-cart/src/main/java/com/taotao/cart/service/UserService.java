package com.taotao.cart.service;

import com.taotao.sso.query.api.UserQueryService;
import com.taotao.sso.query.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {


//    @Value("${TAOTAO_SSO_URL}")
//    public String TAOTAO_SSO_URL;

    @Autowired
    private UserQueryService userQueryService;

    /**
     *  根据token查询用户信息
     *
     * @param token
     * @return
     */
    public User queryByToken(String token){
        return this.userQueryService.queryUserByToken(token);
    }
}
