package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.StringUtils;

/**
 * @Author 廖敏
 * @Date 2019-02-27 10:47
 **/
public class CodeParser extends MulLineParser {
    @Override
    public String endChar() {
        return "```"+System.lineSeparator();
    }

    @Override
    public String startChar() {
        return "```";
    }

    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            if(super.ifMatch(reader)){
                String str = reader.readChar(super.blockStartIdx, super.blockEndIdx);
                if(StringUtils.find(str,0,System.lineSeparator())>1){
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String replace(MarkDownReader reader) {
        try {
            String str = reader.readChar(super.blockStartIdx, super.blockEndIdx);
            MarkDownReader markDownReader=new MarkDownReader(str);
            int rowLength = markDownReader.getRowLength()-1;
            //去掉第一行
            markDownReader.nextMdLine(2);
            int startIdx=markDownReader.getCurRowStartIdx();
            markDownReader.nextMdLine(rowLength-3);
            int endIdx=markDownReader.getCurRowEndIdx();
            return "<pre><code>"+str.substring(startIdx,endIdx)+"</code></pre>";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
