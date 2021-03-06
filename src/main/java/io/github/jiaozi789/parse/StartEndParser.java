package io.github.jiaozi789.parse;

import io.github.jiaozi789.obj.Title;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.FtlUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:24
 **/
public abstract class StartEndParser extends StartParser {
    /**
     * md对象结束的判断字符 比如  # abc  应该返回 \r\n
     * @return 判断对象结束的字符
     */
    public abstract String endChar();
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
