package com.taotao.web.controller;

import com.taotao.web.bean.Item;
import com.taotao.web.bean.Order;
import com.taotao.web.bean.User;
import com.taotao.web.interceptors.UserLoginHandlerInterceptor;
import com.taotao.web.service.ItemService;
import com.taotao.web.service.OrderService;
import com.taotao.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("order")
@Controller
public class OrderController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     *  订单确认页
     *
     * @param itemId
     * @return
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public ModelAndView toOrder(@PathVariable("itemId") Long itemId){
        ModelAndView mv = new ModelAndView("order");
        Item item = this.itemService.queryById(itemId);
        mv.addObject("item", item);
        return mv;
    }

    /**
     *  提交订单
     *
     * @param order
     * @return
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submit(Order order, @CookieValue(UserLoginHandlerInterceptor.COOKIE_NAME) String token){
        Map<String, Object> result = new HashMap<>();

        // 填充当前登录用户的信息
        User user = this.userService.queryByToken(token);
        order.setUserId(user.getId());
        order.setBuyerNick(user.getUsername());

        String orderId = this.orderService.submit(order);
        if (StringUtils.isEmpty(orderId)) {
            // 订单提交失败
            result.put("status", 500);
        } else {
            // 订单提交成功
            result.put("status", 200);
            result.put("data", orderId);
        }
        return result;
    }
}
