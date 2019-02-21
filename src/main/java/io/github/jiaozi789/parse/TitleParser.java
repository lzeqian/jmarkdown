package io.github.jiaozi789.parse;

import io.github.jiaozi789.obj.Title;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.FtlUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:29
 **/
public abstract class TitleParser extends StartEndParser {
    /**
     * 留给子类指定标题级别
     * @return
     */
    public abstract int level();
    public String startChar() {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<level();i++){
            sb.append("#");
        }
        return sb.toString()+" ";
    }

    public String endChar() {
        return System.lineSeparator();
    }

    public String name() {
        return "标题处理类";
    }

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

    /**
     * 需要自己实现替换逻辑
     * @param reader  md文件读取流
     * @return
     */
    public String replace(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            Title title=new Title();
            title.setLevel(level());
            String innerText=string.substring(startChar().length(),string.length()-endChar().length());
            title.setInnerText(innerText);
            String replaceText=FtlUtils.genernate("/title.ftl",title)+endChar();
            return replaceText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
