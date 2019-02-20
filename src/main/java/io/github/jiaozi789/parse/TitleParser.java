package io.github.jiaozi789.parse;

import io.github.jiaozi789.obj.Title;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.FtlUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:29
 **/
public abstract class TitleParser implements MdParser {
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
        return "\r\n";
    }

    public String name() {
        return "标题处理类";
    }
    /**
     * 需要自己实现替换逻辑
     * @param reader  md文件读取流
     * @param offsetStart 开始位置
     * @param offsetEnd 结束位置
     * @return
     */
    public String replace(MarkDownReader reader, int offsetStart, int offsetEnd) {
        try {
            String string = reader.readChar(offsetStart, offsetEnd);
            Title title=new Title();
            title.setLevel(level());
            String innerText=string.substring(startChar().length(),string.length()-endChar().length());
            title.setInnerText(innerText);
           return FtlUtils.genernate("/title.ftl",title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
