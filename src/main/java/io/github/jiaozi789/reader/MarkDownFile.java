package io.github.jiaozi789.reader;

import io.github.jiaozi789.parse.MarkDownParser;
import io.github.jiaozi789.parse.RegexParserBuilder;
import io.github.jiaozi789.parse.StartEndParser;
import io.github.jiaozi789.parse.TitleFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author 廖敏
 * @Date 2019-02-20 11:33
 **/
public class MarkDownFile {
    private InputStream inputStream;
    private MarkDownReader markDownReader=null;
    private StringBuffer targetHtml=new StringBuffer();
    public static List<MarkDownParser> mdParserList=new ArrayList<MarkDownParser>();
    static{
        for(int i=1;i<=6;i++) {
            try {
                mdParserList.add(TitleFactory.getParser(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String,String> paramMap=new HashMap<>();
        paramMap.put("1","preText");
        paramMap.put("2","innerText");
        paramMap.put("3","suffixText");
        mdParserList.add(RegexParserBuilder.builder("(.*)==(.+)==(.*\r\n)",paramMap,"${preText}<mark>${innerText}</mark>${suffixText}"));
    }

    public MarkDownFile(InputStream inputStream) {
        this.inputStream = inputStream;
        markDownReader =new MarkDownReader(inputStream);
    }

    public static void addParser(StartEndParser mp){
        mdParserList.add(mp);
    }


    /**
     * 解析md文件
     * @throws IOException
     */
    public String process() throws IOException {
        String line=null;
        while((line=markDownReader.readMdLine())!=null){

            for (MarkDownParser mdParser:mdParserList) {
                if(mdParser.ifMatch(markDownReader)){
                    line=mdParser.replace(markDownReader);
                    markDownReader.replaceCurRow(line);
                }
            }

        }
        return markDownReader.getTargetHtml();
    }

    public void destroy() throws IOException {
        if(markDownReader!=null){
            markDownReader.close();
        }
    }


}
