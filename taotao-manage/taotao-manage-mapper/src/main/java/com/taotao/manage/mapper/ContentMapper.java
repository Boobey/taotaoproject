package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;

import java.util.List;

public interface ContentMapper extends Mapper<Content> {

    /**
     *  根据categoryId查询内容，并且按照更新时间倒序排序
     *
     * @return
     */
    public List<Content> queryContentList(Long categoryId);

}
