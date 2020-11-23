package com.taotao.manage.controller;

import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import com.taotao.manage.service.ItemDescService;
import com.taotao.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("item")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemDescService itemDescService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam("desc") String desc) {
        try {
            if (StringUtils.isEmpty(item.getTitle())) {
                // 参数有误，400
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            // 初始值
            item.setStatus(1);
            item.setId(null); // 处于安全考虑，强制设置id为null，通过数据库自增长得到
            this.itemService.save(item);

            ItemDesc itemDesc = new ItemDesc();
            itemDesc.setItemId(item.getId());
            itemDesc.setItemDesc(desc);
            this.itemDescService.save(itemDesc);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}