package io.github.jiaozi789.conf;

import io.github.jiaozi789.parse.MarkDownParser;
import io.github.jiaozi789.parse.RegexParserBuilder;
import io.github.jiaozi789.parse.StartEndParser;
import io.github.jiaozi789.parse.TitleFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-21 15:49
 **/
public class ParserConfigration {
    public static List<MarkDownParser> mdParserList=new ArrayList<MarkDownParser>();
    static{
        // 支持 标题1到标题6
        for(int i=1;i<=6;i++) {
            try {
                mdParserList.add(TitleFactory.getParser(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //支持 ==标记文本==
        Map<String,String> paramMap=new HashMap<>();
        paramMap.put("1","preText");
        paramMap.put("2","innerText");
        paramMap.put("3","suffixText");
        paramMap.put("4","lineSep");
        mdParserList.add(RegexParserBuilder.builder("(.*)==(.+)==(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<mark>${innerText}</mark>${suffixText}"+System.lineSeparator()));
        paramMap=new HashMap<>();
        paramMap.put("1","preText");
        paramMap.put("2","innerText");
        paramMap.put("3","suffixText");
        //*强调文本* _强调文本_
        mdParserList.add(RegexParserBuilder.builder("(.*)_(.+)_(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));
        mdParserList.add(RegexParserBuilder.builder("(.*)\\*(.+)\\*(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));

    }
    public static void addParser(StartEndParser mp){
        mdParserList.add(mp);
    }


}
