package io.github.jiaozi789.conf;

import io.github.jiaozi789.parse.*;
import io.github.jiaozi789.reader.MarkDownReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-21 15:49
 **/
public class ParserConfigration {
    /**
     * 字符串是否匹配定义的解析规则
     * @param line 判断字符串
     * @return 是否匹配
     */
    public static List<MarkDownParser> match(String line){
        MarkDownReader markDownReader=new MarkDownReader(line);
        List<MarkDownParser> mdps=new ArrayList<>();
        for (MarkDownParser mdParser: ParserConfigration.mdParserList) {
            if(mdParser.ifMatch(markDownReader)){
                mdps.add(mdParser);
            }
        }
        return mdps;
    }
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
        //**加粗文本** __加粗文本__
        mdParserList.add(RegexParserBuilder.builder("(.*)\\*\\*(.+)\\*\\*(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<strong>${innerText}</strong>${suffixText}"));
        mdParserList.add(RegexParserBuilder.builder("(.*)__(.+)__(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<strong>${innerText}</strong>${suffixText}"));
        //~~删除文本~~
        mdParserList.add(RegexParserBuilder.builder("(.*)~~(.+)~~(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<s>${innerText}</s>${suffixText}"));



        paramMap=new HashMap<>();
        paramMap.put("1","preText");
        paramMap.put("2","atext");
        paramMap.put("3","href");
        paramMap.put("4","suffixText");
        mdParserList.add(RegexParserBuilder.builder("(.*)\\!\\[(.*)\\]\\((.*)\\)(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<img src=\"${href}\" alt=\"${atext}\">${suffixText}"));
        //超链接  [link](https://mp.csdn.net)
        mdParserList.add(RegexParserBuilder.builder("(.*)\\[(.*)\\]\\((.*)\\)(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<a href=\"${href}\">${atext}</a>${suffixText}"));

        mdParserList.add(new RefParser());
        mdParserList.add(new TableParser());
        mdParserList.add(new CodeParser());
        paramMap=new HashMap<>();
        paramMap.put("1","preText");
        paramMap.put("2","innerText");
        paramMap.put("3","suffixText");
        //*强调文本* _强调文本_
        //mdParserList.add(RegexParserBuilder.builder("(.*)_(.+)_(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));
        //mdParserList.add(RegexParserBuilder.builder("(.*)\\*(.+)\\*(.*|.*"+System.lineSeparator()+")",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));
        mdParserList.add(RegexParserBuilder.builder("(?!.*\".*_.+_.*\"(?:.*|.*"+System.lineSeparator()+"))(?:(.*)_(.+)_(.*|.*"+System.lineSeparator()+"))",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));
        mdParserList.add(RegexParserBuilder.builder("(?!.*\".*\\*.+\\*.*\"(?:.*|.*"+System.lineSeparator()+"))(?:(.*)\\*(.+)\\*(.*|.*"+System.lineSeparator()+"))",paramMap,"${preText}<em>${innerText}</em>${suffixText}"));




    }
    public static void addParser(StartEndParser mp){
        mdParserList.add(mp);
    }


}
