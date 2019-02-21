package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:24
 **/
public abstract class StartParser implements   MarkDownParser {
    /**
     * md对象初始的判断字符 比如  # abc  应该返回 #
     * @return 判断对象开始的字符
     */
    public abstract String startChar();

    /**
     * 表示该对象名称 可以返回空
     * @return
     */
    public abstract String name();
    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            if(string.trim().startsWith(startChar())){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
