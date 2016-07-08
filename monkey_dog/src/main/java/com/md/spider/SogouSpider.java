package com.md.spider;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.md.http.util.HttpClientUtils;
import com.md.spider.vo.GhVo;

public class SogouSpider extends BaseSpider {

    static String url_farmat = "http://weixin.sogou.com/weixin?type=%d&query=%s&ie=utf8&_sug_=n&_sug_type_=";
    static HttpClient httpClient = new HttpClient();

    public static List<GhVo> getList(int type, String query, int page) throws XPatherException, IOException {
        httpClient.getParams().setContentCharset("utf-8");
        String url = String.format(url_farmat, type, query, page);
        GetMethod method = new GetMethod(url);
        TagNode tagNode = HttpClientUtils.executeAndGetResponseAsTagNode(httpClient, method);
        String xpath = "//div[@uigs]";
        Object[] objects = tagNode.evaluateXPath(xpath);
        List<GhVo> ghList = new ArrayList<GhVo>();
        for (Object obj : objects) {
            TagNode node = (TagNode) obj;
            String href = evaluateXPath(node, "@href");
            String title = evaluateXPath(node, "//h3/text()");
            String hg = evaluateXPath(node, "//h4/text()").replace("微信号：", "").trim();
            String img = evaluateXPath(node, "//img/@src");
            GhVo gh = new GhVo(title, hg, img, href);
            ghList.add(gh);
        }
        return ghList;
    }

    public static String evaluateXPath(TagNode node, String xPathExpression) throws XPatherException {
        Object[] os = node.evaluateXPath(xPathExpression);
        if (os.length > 0) {
            return os[0].toString();
        }
        return "";
    }

    public static void main(String args[]) throws XPatherException, IOException {
        SogouSpider.getList(1, URLEncoder.encode("广州", "utf-8"), 1);

    }
}
