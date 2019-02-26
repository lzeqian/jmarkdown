package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.StringUtils;
import io.github.jiaozi789.utils.SystemUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 连续行都以某个字符开始 比如表格的 |
 * @Author 廖敏
 * @Date 2019-02-26 17:04
 **/
public abstract class SeriesMulLineParser extends MulLineParser{
    /**
     * 至少连续行数
     * @return
     */
    public abstract int seriesCount();
    @Override
    public String endChar() {
        return null;
    }

    /**
     * 可以设置第几行必须满足某个正则表达式
     *  key value
     *  0  (|.+)+|$
     * @return
     */
    public Map<Integer,String> regex(){
        return null;
    }

    /**
     * 子类实现行检查逻辑
     * @param index 行号
     * @param reader 读取流
     * @return 是否检查通过
     */
    public abstract boolean check(int index,MarkDownReader reader);
    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            int rowIndex=0;
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            if(string.trim().startsWith(startChar())){
                String line=null;
                if(!SystemUtils.match(regex(),rowIndex,string) && !check(rowIndex,reader)){
                    return false;
                }
                blockStartIdx=reader.getCurRowStartIdx();
                while((line=reader.readMdLine())!=null){
                    rowIndex++;
                    //如果当前行是连续行，必须还要检查子类的检查函数和重写的正则
                    if(line.trim().startsWith(startChar())) {
                        if (!SystemUtils.match(regex(), rowIndex, string) && !check(rowIndex, reader)) {
                            return false;
                        }
                    }
                    if(!line.trim().startsWith(startChar())){
                        reader.prevMdLine();
                        blockEndIdx=reader.getCurRowEndIdx();
                        reader.readMdLine();
                        if(rowIndex>=seriesCount()){
                            return true;
                        }else{
                            return false;
                        }

                    }
                    if(reader.isLastRow()){
                        blockEndIdx=reader.getCurRowEndIdx();
                        if(rowIndex>=seriesCount()){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
