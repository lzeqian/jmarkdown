package io.github.jiaozi789.parse;

import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-21 16:40
 **/
public class RegexParserBuilder {
    /**
     * 通过参数构造一个正则解析器
     * @return
     */
    public static RegexParser builder(final String regex,Map<String, String> groupMapping,String templateString){
        return new RegexParser() {
            @Override
            public String regex() {
                return regex;
            }

            @Override
            public Map<String, String> groupMapping() {
                return groupMapping;
            }

            @Override
            public String templateString() {
                return templateString;
            }
        };
    }
}
