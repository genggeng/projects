package com.md.spider;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.md.http.util.NRUtils;
import com.md.spider.vo.NrghVo;

public class NewRankSpider extends BaseSpider {
    // 周榜：/xdnphb/list/week/rank
    // 日榜：/xdnphb/list/day/rank
    static String DEFAULT_HOST = "http://www.newrank.cn";
    static String DEFAULT_DAY_URL = "http://www.newrank.cn/xdnphb/list/day/rank";
    static String DEAULT_WEEK_URL = "http://www.newrank.cn/xdnphb/list/week/rank";
    HttpClient httpClient = new HttpClient();
    String[] groups = new String[] { "资讯=时事,民生,财富,科技,创业,汽车 ,楼市 ,职场 ,教育 ,学术 ,政务 ,企业", "生活=文化 ,百科 ,健康 ,时尚 ,美食 ,乐活 ,旅行 ,幽默 ,情感 ,体娱 ,美体 ,文摘" };

    /**
     * @param headerMap
     * @param paramsMap
     */
    public List<NrghVo> getList(String url, String start, String end, String rankName, String rankGroup) {
        Map<String, String> headerMap = Maps.newHashMap();
        Map<String, String> paramsMap = new TreeMap<String, String>();
        System.out.println("====" + rankName + "===" + rankGroup);
        paramsMap.put("end", end);
        paramsMap.put("rank_name", rankName.trim());
        paramsMap.put("rank_name_group", rankGroup.trim());
        paramsMap.put("start", start);
        String nonce = NRUtils.nonce();
        String xyz = NRUtils.md5(url.replace(DEFAULT_HOST, ""), nonce, paramsMap);
        paramsMap.put("nonce", nonce);
        paramsMap.put("xyz", xyz);
        String str = postRespose(httpClient, new PostMethod(url), headerMap, paramsMap);
        JSONObject jobj = (JSONObject) JSONObject.parse(str);
        String list = jobj.getString("value");
        List<NrghVo> nrgh = JSONArray.parseArray(list, NrghVo.class);
        return nrgh;
    }

    public Map<String, List<NrghVo>> getAllRank(String start, String end) {
        String url = DEAULT_WEEK_URL;
        if (start.equals(end)) {
            url = DEFAULT_DAY_URL;
        }
        Map<String, List<NrghVo>> allRank = Maps.newHashMap();
        for (String group : groups) {
            String[] strs = group.split("=");
            String groupName = strs[0];
            String[] gm = strs[1].split(",");
            for (String rankName : gm) {
                System.out.println(rankName + "-==" + groupName);
                List<NrghVo> list = getList(url, start, end, rankName, groupName);
                allRank.put(groupName + "=" + rankName, list);
            }
        }
        System.out.println(allRank);
        return allRank;
    }

    public static void main(String args[]) {

        NewRankSpider nr = new NewRankSpider();
        nr.getAllRank("2016-06-21", "2016-06-27");

    }
}
