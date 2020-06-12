package com.youyue.reidscourse;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component("myKeyGenerator")
public class MyKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... objects) {
        //格式化缓存key字符串
        StringBuilder sb = new StringBuilder();
        //追加类名
        sb.append(o.getClass().getName());
        //追加方法名
        sb.append(method.getName());
        //遍历参数并且追加
//        for (Object obj : objects) {
//            sb.append(obj.toString());
//        }
        System.out.println("调用Redis缓存Key : " + sb.toString());
        return sb.toString();
    }
}
