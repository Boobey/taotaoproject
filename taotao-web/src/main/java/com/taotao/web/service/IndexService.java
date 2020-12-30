package com.taotao.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class IndexService {

    @Autowired
    private ApiService apiService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String queryIndexAD1() {
        try {
            String url = "http://manage.taotao.com/rest/api/content?categoryId=33&page=1&rows=6";
            String jsonData = this.apiService.doGet(url);
            if(StringUtils.isEmpty(jsonData)){
                return null;
            }
            // 解析json数据
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            ArrayNode rows = (ArrayNode) jsonNode.get("rows");
            List<Map<String, Object>> result = new ArrayList<>();
            for (JsonNode row : rows) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("srcB", row.get("pic").asText());
                map.put("height", 240);
                map.put("alt", row.get("title").asText());
                map.put("width", 670);
                map.put("src", row.get("pic").asText());
                map.put("widthB", 550);
                map.put("href", row.get("url").asText());
                map.put("heightB", 240);
                result.add(map);
            }
            return MAPPER.writeValueAsString(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
