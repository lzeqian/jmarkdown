package io.github.jiaozi789.reader;

import io.github.jiaozi789.conf.ParserConfigration;
import io.github.jiaozi789.parse.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author 廖敏
 * @Date 2019-02-20 11:33
 **/
public class MarkDownFile {
    private InputStream inputStream;
    private MarkDownReader markDownReader=null;
    private StringBuffer targetHtml=new StringBuffer();



    public MarkDownFile(InputStream inputStream) {
        this.inputStream = inputStream;
        markDownReader =new MarkDownReader(inputStream);
    }
    /**
     * 解析md文件
     * @throws IOException
     */
    public String process() throws IOException {
        String line=null;
        while((line=markDownReader.readMdLine())!=null){
            for (MarkDownParser mdParser: ParserConfigration.mdParserList) {
                if(mdParser.ifMatch(markDownReader)){
                    line=mdParser.replace(markDownReader);
                    markDownReader.replaceCurRow(line);
                }
            }
            //每一换行都需要加 <br/> +\r\r
            try {
                String string = markDownReader.readChar(markDownReader.getCurRowStartIdx(), markDownReader.getCurRowEndIdx());
                markDownReader.replaceCurRow(string.trim()+"<br/>"+System.lineSeparator());
            } catch (Exception e) {
                e.printStackTrace();
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
