package com.md.http.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class NRUtils {
    static String appkey = "?AppKey=joker&";

    public static String nonce() {
        String[] a = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
        String c = "";
        for (int d = 0; 9 > d; d++) {
            int e = (int) Math.floor(16 * Math.random());
            c += a[e];
        }
        System.out.println(c);
        return c;
    }

    public static String md5(String urlProfix, String nonce, Map<String, String> map) {
        String url = urlProfix + appkey;
        for (Entry<String, String> entry : map.entrySet()) {
            url += entry.getKey() + "=" + entry.getValue() + "&";
        }
        url += "nonce=" + nonce;
        System.out.println(url);
        return MD5.getMD5ofStr(url).toLowerCase();
    }

    public static void main(String args[]) {
        String nonce = NRUtils.nonce();
        Map<String, String> paramsMap = new TreeMap<>();
        paramsMap.put("end", "2016-06-28");
        paramsMap.put("rank_name", "民生");
        paramsMap.put("rank_name_group", "资讯");
        paramsMap.put("start", "2016-06-28");
        System.out.println(nonce);
        System.out.println(NRUtils.md5("/xdnphb/list/week/rank", nonce, paramsMap));
        System.out
                .println(MD5.getMD5ofStr("/xdnphb/list/day/rank?AppKey=joker&end=2016-06-28&rank_name=时事&rank_name_group=资讯&start=2016-06-28&nonce=d0fddeb72"));
    }
}
