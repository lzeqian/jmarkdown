package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;

/**
 * @Author 廖敏
 * @Date 2019-02-21 15:54
 **/
public interface MarkDownParser {
    /**
     * 是否匹配
     * @param reader 当前行的读取流
     * @return 是否匹配
     */
    public boolean ifMatch(MarkDownReader reader);

    /**
     * 如何替换
     * @param reader 当前行的读取流
     * @return 替换的字符串
     */
    public String replace(MarkDownReader reader);
}
