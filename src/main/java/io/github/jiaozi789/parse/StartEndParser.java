package io.github.jiaozi789.parse;

import io.github.jiaozi789.obj.Title;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.FtlUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:24
 **/
public abstract class StartEndParser implements   MarkDownParser {
    /**
     * md对象初始的判断字符 比如  # abc  应该返回 #
     * @return 判断对象开始的字符
     */
    public abstract String startChar();
    /**
     * md对象结束的判断字符 比如  # abc  应该返回 \r\n
     * @return 判断对象结束的字符
     */
    public abstract String endChar();

    /**
     * 表示该对象名称 可以返回空
     * @return
     */
    public abstract String name();
    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            if(string.trim().startsWith(startChar()) && string.endsWith(endChar())){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
