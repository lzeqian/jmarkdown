package io.github.jiaozi789.parse;

import freemarker.template.utility.StringUtil;
import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.StringUtils;
import lombok.Data;

/**
 * @Author 廖敏
 * @Date 2019-02-22 10:43
 **/
@Data
public abstract class MulLineParser extends StartEndParser{
    protected int blockStartIdx;
    protected int blockEndIdx;

    @Override
    public String name() {
        return "多行解析器";
    }

    /**
     * 从当前行第一个字符开始，到某一行最后一个字符结束
     * @param reader
     * @return
     */
    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            if(string.trim().startsWith(">")){
                blockStartIdx=reader.getCurRowStartIdx();
                String line=null;
                while((line=reader.readMdLine())!=null){
                    if(line.equals(endChar()) || StringUtils.ltrim(line).equals(endChar())){
                        blockEndIdx=reader.getCurRowEndIdx();
                        return true;
                    }
                }
                blockEndIdx=reader.getCurRowEndIdx();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
