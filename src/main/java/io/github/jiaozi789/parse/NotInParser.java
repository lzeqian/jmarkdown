package io.github.jiaozi789.parse;

import io.github.jiaozi789.conf.ParserConfigration;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.SystemUtils;

import java.util.List;

/**
 * 解析从某个类型的markdown文本开始到某个类型的文本结束中间部分
 * @Author 廖敏
 * @Date 2019-02-26 15:26
 **/
public abstract class NotInParser implements MarkDownParser {

    /**
     * 前一行的preType类型的集合 不满足满足这个条件
     * @return
     */
    public abstract List<Class>  notInType();
    public abstract List  currentType();
    public static NotInParser builder(final List<Class>  notInType,
                               final List<MarkDownParser>  currentType,
                                final String appendString){
        return new NotInParser(){


            @Override
            public List<Class> notInType() {
                return notInType;
            }

            @Override
            public List<MarkDownParser> currentType() {
                return currentType;
            }

            @Override
            public String appendString() {
                return appendString;
            }
        };
    }

    /**
     * 上一行尾部追加的字符串
     * @return
     */
    public abstract String appendString();

    @Override
    public boolean ifMatch(MarkDownReader reader) {
       return SystemUtils.typeNotIn(notInType(),currentType());
    }
    @Override
    public String replace(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            return string.trim()+appendString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
