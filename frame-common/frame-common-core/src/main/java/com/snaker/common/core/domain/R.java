package com.snaker.common.core.domain;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class R extends HashMap<String, Object> {
    //
    private static final long serialVersionUID = -8157613083634272196L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R data(Object obj) {
        R r = new R();
        r.put("data", obj);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    //判断是否成功
    public boolean judgeSuccess() {
        int code = (int) this.get("code");
        return code == 0 ? true : false;
    }

    public void throwRuntimeExceptionIfError() {
        if (!judgeSuccess()) {
            throw new RuntimeException(getMsg());
        }
    }

    public <T> T getData(Class<T> clazz) {
        Object data = this.get("data");
        return data == null ? null : JSON.parseObject(JSON.toJSONString(data), clazz);
    }

    public <T> List<T> getListData(Class<T> clazz) {
        if (!judgeSuccess()) {
            throw new RuntimeException(getMsg());
        }
        Object data = this.get("data");
        if (data == null) {
            return new ArrayList<>();
        }
        List<T> ts = JSON.parseArray(JSON.toJSONString(data), clazz);
        return ts;
    }

    public String getMsg() {
        return (String) this.get("msg");
    }
}