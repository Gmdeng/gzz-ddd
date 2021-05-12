package com.gzz.core.enumtype;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 *
 */
public class EnumSerializer implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
        if (o instanceof BaseEnum) {
            BaseEnum baseEnum = (BaseEnum) o;
            jsonSerializer.out.write(baseEnum.label());
        } else {
            jsonSerializer.out.writeEnum((Enum<?>) o);
        }

    }
}