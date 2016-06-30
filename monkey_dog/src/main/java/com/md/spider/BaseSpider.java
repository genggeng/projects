package com.md.spider;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.md.http.util.HttpClientUtils;

public class BaseSpider {

    public List<Object> list(HttpClient httpClient) {

        return null;
    }

    public Object getDetail(HttpClient httpClient) {

        return null;
    }

    public String postRespose(HttpClient httpClient, PostMethod method, Map<String, String> headerMap, Map<String, String> paramsMap) {
        httpClient.getParams().setContentCharset("utf-8");
        for (Entry<String, String> entry : headerMap.entrySet()) {
            method.setRequestHeader(entry.getKey(), entry.getValue());
        }
        for (Entry<String, String> entry : paramsMap.entrySet()) {
            method.setParameter(entry.getKey(), entry.getValue());
        }
        return HttpClientUtils.executeAndGetResponseAsString(httpClient, method);
    }
}
