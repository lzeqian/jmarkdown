package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:24
 **/
public interface MdParser {
    /**
     * md对象初始的判断字符 比如  # abc  应该返回 #
     * @return 判断对象开始的字符
     */
    public String startChar();
    /**
     * md对象结束的判断字符 比如  # abc  应该返回 \r\n
     * @return 判断对象结束的字符
     */
    public String endChar();

    /**
     * 表示该对象名称 可以返回空
     * @return
     */
    public String name();
    /**
     * 自己实现替换逻辑
     * @param reader  md文件读取流
     * @param offsetStart
     * @param offsetEnd
     * @return
     */
    public String replace(MarkDownReader reader, int offsetStart, int offsetEnd);
}
