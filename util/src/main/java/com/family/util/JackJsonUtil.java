package com.family.util;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

/**
 * json对象映射工具类之jackson封装
 * 
 * @author ljh
 * 
 */
public class JackJsonUtil {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JackJsonUtil() {
        // 设置默认日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        //提供其它默认设置
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,    false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));

    }

    /**
     * 将对象转换成json字符串格式（默认将转换所有的属性）
     * 
     * @param o
     * @return
     */
    public String toJsonStr(Object value) throws JsonGenerationException, JsonMappingException, IOException {
        return objectMapper.writeValueAsString(value);
    }


    /**
     * 将对象json格式直接写出到流对象中（默认将转换所有的属性）
     * 
     * @param o
     * @return
     */
    public void writeJsonStr(OutputStream out, Object value)
            throws JsonGenerationException, JsonMappingException, IOException {
        objectMapper.writeValue(out, value);
    }
    
    

    /**
     * 得到jackson原始ObjectMapper进行本类未开放api的调用
     * 
     * @return
     */
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
