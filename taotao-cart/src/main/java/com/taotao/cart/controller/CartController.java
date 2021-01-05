package com.taotao.cart.controller;

import com.taotao.cart.bean.User;
import com.taotao.cart.service.CartService;
import com.taotao.cart.threadlocal.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("cart")
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ModelAndView cartList() {
        ModelAndView mv= new ModelAndView("cart");
        return mv;
    }

    /**
     *  加入商品到购物车
     *
     */
    @RequestMapping(value = "{itemId}", method = RequestMethod.GET)
    public String addItemToCart(@PathVariable("itemId") Long itemId) {

        User user = UserThreadLocal.get();
        if (null == user) {
            // 未登录状态

        } else {
            // 登录状态
            this.cartService.addItemToCart(itemId);
        }

        // 重定向到购物车列表页面
        return "redirect:/cart/list.html";
    }

}
