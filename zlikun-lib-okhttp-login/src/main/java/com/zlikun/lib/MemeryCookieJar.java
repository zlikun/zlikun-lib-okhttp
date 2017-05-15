package com.zlikun.lib;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2017/5/15 21:49
 */
class MemeryCookieJar implements CookieJar {

    private Logger logger = LoggerFactory.getLogger(MemeryCookieJar.class) ;

    // 使用ConcurrentMap存储cookie信息，因为数据在内存中，所以只在程序运行阶段有效，程序结束后即清空
    private ConcurrentMap<String, List<Cookie>> storage = new ConcurrentHashMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        String host = url.host();
        if (cookies != null && !cookies.isEmpty()) {
            storage.put(host, cookies);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String host = url.host();
        List<Cookie> list = storage.get(host);
        return list == null ? new ArrayList<Cookie>() : list ;
    }
}