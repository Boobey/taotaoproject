package com.taotao.sso.controller;

import com.taotao.sso.pojo.User;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  注册
     *
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    /**
     *  检测数据是否可用
     *
     * @return
     */
    @RequestMapping(value = "{param}/{type}", method = RequestMethod.GET)
    public ResponseEntity<Boolean>check(@PathVariable("param") String param,
                                        @PathVariable("type") Integer type) {

        try {
            Boolean bool = this.userService.check(param, type);
            if (bool == null){
                // 参数有误
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            return ResponseEntity.ok(!bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     *  注册
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "doRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> doRegister(@Valid User user, BindingResult bindingResult){
        Map<String, Object> result = new HashMap<>();
        if (bindingResult.hasErrors()) {
            // 没有通过校验
            result.put("status", "400");

            // 获取错误信息
            List<String> msgs = new ArrayList<String>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String msg = objectError.getDefaultMessage();
                msgs.add(msg);
            }
            result.put("data", "参数有误！" + StringUtils.join(msgs, "|"));
            return result;
        }
        try {
            Boolean bool = this.userService.doRegister(user);
            if (bool) {
                result.put("status", "200");
            } else {
                result.put("status","500");
                result.put("data", "很抱歉");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status","500");
            result.put("data", "很抱歉");
        }
        return result;
    }
}
