package com.gzz.core.util;

import com.gzz.core.toolkit.ParamMap;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class RequestUtils {
    /**
     *
     * @param request
     * @return
     */
    public static ParamMap toParamMap(HttpServletRequest request) {
        // 返回值Map
        ParamMap returnMap = new ParamMap();
        Iterator entries = request.getParameterMap().entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}
