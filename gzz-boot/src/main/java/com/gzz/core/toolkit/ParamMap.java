package com.gzz.core.toolkit;

import java.util.HashMap;

/**
 * 参数。
 */
public class ParamMap extends HashMap<String, Object> {
    /**
     * 获取值
     * @param key
     * @return
     */
    public String getString(String key){
        return (String) this.get(key);
    }
    public String getString(String key, String defVal){
        return (String) this.getOrDefault(key, defVal);
    }
    /**
     * 获取值
     * @param key
     * @return
     */
    public Integer getInt(String key){
        return Integer.parseInt(this.get(key).toString());
    }

    /**
     *
     * @param key
     * @param defVal
     * @return
     */
    public Integer getInt(String key, int defVal){
        return Integer.parseInt(this.getOrDefault(key, defVal).toString());
    }
}
