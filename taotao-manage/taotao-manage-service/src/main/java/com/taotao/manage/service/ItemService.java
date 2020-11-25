package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.bean.EasyUIResult;
import com.taotao.manage.mapper.ItemMapper;
import com.taotao.manage.pojo.Item;
import com.taotao.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends BaseService<Item> {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescService itemDescService;

    public Boolean saveItem(Item item, String desc) {
        // 初始值
        item.setStatus(1);
        item.setId(null); // 处于安全考虑，强制设置id为null，通过数据库自增长得到
        Integer count1 = super.save(item);

        // 保存商品描述数据
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        Integer count2 = this.itemDescService.save(itemDesc);

        return count1.intValue() == 1 && count2.intValue() == 1;
    }

    public EasyUIResult queryItemList(Integer page, Integer rows) {

        // 设置分列参数
        PageHelper.startPage(page, rows);

        Example example = new Example(Item.class);
        // 按照创建时间排序
        example.setOrderByClause("created DESC");
        List<Item> items = this.itemMapper.selectByExample(example);

        PageInfo<Item> pageInfo = new PageInfo<Item>(items);

        return new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());

    }
}
