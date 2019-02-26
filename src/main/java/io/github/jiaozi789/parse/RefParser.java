package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.StringUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-22 14:59
 **/
public class RefParser extends MulLineParser {
    @Override
    public String endChar() {
        return System.lineSeparator();
    }

    @Override
    public String startChar() {
        return ">";
    }

    @Override
    public String replace(MarkDownReader reader) {
        try {
            String str = reader.readChar(super.blockStartIdx, super.blockEndIdx);
            str=str.toString().substring(1);
            String replaceStr=StringUtils.replaceStr(str,"\r\n","<br/>",1);
            return "<blockquote><p>"+replaceStr+"</p></blockquote>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
