package com.md.http.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

public class HttpClientUtils {
    private static Logger logger = Logger.getLogger(HttpClientUtils.class);
    private static HtmlCleaner htmlCleaner = new HtmlCleaner();

    public static InputStream executeAndGetResponseAsStream(HttpClient httpClient, HttpMethod method) {
        return executeAndGetResponseAsStream(httpClient, method, 200);
    }

    public static InputStream executeAndGetResponseAsStream(HttpClient httpClient, HttpMethod method, int rightStatus) {
        try {
            method.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.94 Safari/537.4");
            int status = httpClient.executeMethod(method);
            if (status == rightStatus) {
                return method.getResponseBodyAsStream();
            }
        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String executeAndGetResponseAsString(HttpClient httpClient, HttpMethod method) {
        return executeAndGetResponseAsString(httpClient, method, 200);
    }

    public static String executeAndGetResponseAsString(HttpClient httpClient, HttpMethod method, int rightStatus) {
        try {
            method.setRequestHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
            int status = httpClient.executeMethod(method);
            if (status == rightStatus) {
                return method.getResponseBodyAsString();
            }
        } catch (HttpException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static TagNode executeAndGetResponseAsTagNode(HttpClient httpClient, HttpMethod method) {
        return executeAndGetResponseAsTagNode(httpClient, method, 200);
    }

    public static TagNode executeAndGetResponseAsTagNode(HttpClient httpClient, HttpMethod method, int rightStatus) {
        return executeAndGetResponseAsTagNode(httpClient, method, rightStatus, "utf-8");
    }

    public static TagNode executeAndGetResponseAsTagNode(HttpClient httpClient, HttpMethod method, int rightStatus, String encoding) {
        try {
            InputStream reponse = executeAndGetResponseAsStream(httpClient, method, rightStatus);
            if (reponse != null) {
                return htmlCleaner.clean(reponse, encoding);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param node
     * @param xpath
     * @return
     */
    public static String evaluateXPathForString(TagNode node, String xpath) {
        if (node == null) {
            return "";
        }
        Object[] objs;
        try {
            objs = node.evaluateXPath(xpath);
            if (objs.length > 0) {
                return objs[0].toString();
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从HttpClient对象中取到coolie信息
     *
     * @param httpClient
     * @return
     */
    public static String getHttpClientCookies(HttpClient httpClient) {
        Cookie[] cookies = httpClient.getState().getCookies();// 取出登陆成功后，服务器返回的cookies信息，里面保存了服务器端给的“临时证”
        String tmpcookies = "";
        for (Cookie c : cookies) {
            tmpcookies = tmpcookies + c.toString() + ";";
        }
        return tmpcookies;
    }

    /**
     * @param node
     * @param xpath
     * @return
     */
    public static TagNode evaluateXPathForNode(TagNode node, String xpath) {
        if (node == null) {
            return null;
        }
        try {
            Object[] objs = node.evaluateXPath(xpath);
            if (objs.length > 0) {
                return (TagNode) objs[0];
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }
}
