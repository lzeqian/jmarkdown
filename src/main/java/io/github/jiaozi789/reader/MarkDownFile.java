package io.github.jiaozi789.reader;

import io.github.jiaozi789.parse.MdParser;
import io.github.jiaozi789.parse.TitleFactory;
import io.github.jiaozi789.utils.ReaderUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 廖敏
 * @Date 2019-02-20 11:33
 **/
public class MarkDownFile {
    private InputStream inputStream;
    private MarkDownReader markDownReader=null;
    public static List<MdParser> mdParserList=new ArrayList<MdParser>();
    static{
        for(int i=1;i<=6;i++) {
            try {
                mdParserList.add(TitleFactory.getParser(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MarkDownFile(InputStream inputStream) {
        this.inputStream = inputStream;
        markDownReader =new MarkDownReader(inputStream);

    }

    public static void addParser(MdParser mp){
        mdParserList.add(mp);
    }

    /**
     * 判断某个位置是否是和某个语法相同
     * @param index
     * @return
     */
    private MdParser ifMarkDownSyntax(int index){
        for (MdParser mdParser:mdParserList) {
            int parseLength=mdParser.startChar().length();
            try {
                if(index+parseLength<markDownReader.length()) {
                    String readChar = markDownReader.readLengthChar(index, parseLength);
                    if (readChar.equals(mdParser.startChar())) {
                        return mdParser;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     *
     * @param mdParser
     * @return
     */
    public int getEndIndex(MdParser mdParser){
        int parseLength=mdParser.endChar().length();
        for(int start=markDownReader.getCurIndex();;start++){

        }
    }

    public void processLine() throws IOException {
        String line=null;
        while((line=markDownReader.readMdLine())!=null){
            System.out.print(line);
        }
    }
    public void process(){
        try {
            markDownReader.readChar();
            while(!markDownReader.ifEof()){
                MdParser mdParser=ifMarkDownSyntax(markDownReader.getCurIndex());
                if(mdParser!=null) {
                    System.out.println(mdParser);
                    if (mdParser.startChar().length() > 1) {
                        markDownReader.skip(mdParser.startChar().length());
                    }
                }
                if(!markDownReader.ifEof())
                    markDownReader.readChar();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy() throws IOException {
        if(markDownReader!=null){
            markDownReader.close();
        }
    }


}
