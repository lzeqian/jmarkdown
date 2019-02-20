package io.github.jiaozi789.reader;

import io.github.jiaozi789.utils.ReaderUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Md输入流构建类
 * @Author 廖敏
 * @Date 2019-02-20 10:22
 **/
public class MarkDownReader extends BufferedReader {
    public static final String CHARSET="UTF-8";
    private int curIndex=-1;
    private StringBuffer sb;
    public MarkDownReader(InputStream in) {
        super(new InputStreamReader(in, Charset.forName(CHARSET)));
        sb=new StringBuffer();
        try {
            sb.append(ReaderUtils.readAll(in));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] prevMdLine(int line){
        String[] lines=new String[line];
        for (int i = 0; i <line ; i++) {
            lines[i]=prevMdLine();
        }
        return lines;
    }
    /**
     * 读取上一行
     * @return
     */
    public String prevMdLine(){
        if(curIndex==-1){
            return null;
        }
        String lineSeparator = System.lineSeparator();
        int index=ReaderUtils.retFind(sb.toString(),curIndex-2,lineSeparator);

        //第一行
        if(index==-1){
            curIndex=-1;
            return sb.substring(0,curIndex+1);

        }
        //不是第一行
        else{
            int index1=ReaderUtils.retFind(sb.toString(),index-1,lineSeparator);
            curIndex=index1+1;
            return sb.substring(index1+2,index);

        }
    }

    /**
     * 读取当前行后面多少行
     * @return
     */
    public String[] nextMdLine(int line){
        String[] lines=new String[line];
        for (int i = 0; i <line ; i++) {
            lines[i]=readMdLine();
        }
        return lines;
    }
    public String readMdLine(){
        if(ifEof()){
            return null;
        }
        curIndex++;
        String lineSeparator = System.lineSeparator();
        int index=ReaderUtils.find(sb.toString(),curIndex,lineSeparator);
        String rtnString=null;
        //最后一行
        if(index==-1){
            rtnString=sb.substring(curIndex);
            curIndex=length()-1;
        }else {
            rtnString = sb.substring(curIndex, index) + lineSeparator;
            curIndex = (index + lineSeparator.length() - 1);
        }
        return rtnString;
    }
    public int length(){
        return sb.length();
    }
    public int getCurIndex(){
        return curIndex;
    }
    /**
     * 从当前位置继续读取下一个字符
     * @return 下一个字符
     * @throws IOException
     */
    public char readChar() throws IOException {
        curIndex++;
        char chr=sb.charAt(curIndex);
        return chr;
    }

    /**
     * 跳过n个字符
     * @param n
     */
    public void skip(int n){
        curIndex+=n;
    }
    public boolean ifEof(){
        if(curIndex==length()-1){
            return true;
        }
        return false;
    }

    /**
     * 从某个字符开始读取到字符结束 不包含offsetEnd
     * @param offsetStart 开始位置
     * @param offsetEnd 结束位置
     * @return 指定字符串
     * @throws IOException
     */
    public String readChar(int offsetStart,int offsetEnd) throws Exception {
        if(offsetStart>offsetEnd)
            throw new Exception("开始位置必须《=结束位置");
        if(offsetEnd<=sb.length()){
            return sb.substring(offsetStart,offsetEnd);
        }
        throw new IndexOutOfBoundsException("最大"+sb.length()+",当前:"+offsetEnd);
    }

    /**
     * 读取从某个位置开始到指定长度的char
     * @param offsetStart 开始位置
     * @param length 指定长度
     * @return 指定字符串
     * @throws IOException
     */
    public String readLengthChar(int offsetStart,int length) throws Exception {
        return readChar(offsetStart,offsetStart+length);
    }
    /**
     * 读取某个下标对应的字符
     * @param index 下标
     * @return 返回对应下标字符
     * @throws IOException 读取可能涉及io流异常
     */
    public char indexOf(int index) throws IOException {
        if(index<sb.length()){
            return sb.charAt(index);
        }
        throw new IndexOutOfBoundsException("最大"+sb.length()+",当前:"+index);
    }

    @Override
    public void close() throws IOException {
        super.close();
        sb=null;
    }
}
