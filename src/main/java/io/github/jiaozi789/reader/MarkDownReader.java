package io.github.jiaozi789.reader;

import io.github.jiaozi789.utils.ReaderUtils;
import io.github.jiaozi789.utils.StringUtils;

import java.io.*;
import java.nio.charset.Charset;

/**
 * markdown文件是文本文件，用于编写脚本 不会特别大
 * Md输入流构建类 一次性加载 内存操作
 * @Author 廖敏
 * @Date 2019-02-20 10:22
 **/
public class MarkDownReader extends BufferedReader {
    public static final String CHARSET="UTF-8";
    /**
     * 当前读取数据的坐标
     */
    private int curIndex=-1;
    /**
     * 当前行的开始的下标 包含
     */
    private int curRowStartIdx=-1;
    /**
     * 当前行的结束的下标 不包含
     */
    private int curRowEndIdx=-1;
    /**
     * 当前行的索引 从0开始
     */
    private int curRowIdx=-1;
    /**
     * 是否是第一行
     */
    private boolean firstRow=false;
    /**
     * 是否是最后一行
     */
    private boolean lastRow=false;
    /**
     * 总行数
     */
    private int rowLength=0;
    private StringBuffer sb;
    public MarkDownReader(InputStream in,Charset charset) {
        super(new InputStreamReader(in, Charset.forName(CHARSET)));
        sb=new StringBuffer();
        try {
            sb.append(ReaderUtils.readAll(in));
            rowLength=StringUtils.findCount(sb.toString(),System.lineSeparator())+1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public MarkDownReader(String text){
        super(new InputStreamReader(new ByteArrayInputStream(new byte[]{}), Charset.forName(CHARSET)));
        sb=new StringBuffer();
        sb.append(text);
        rowLength=StringUtils.findCount(sb.toString(),System.lineSeparator())+1;
    }
    public MarkDownReader(InputStream in) {
        this(in,Charset.forName(CHARSET));
    }

    public String[] prevMdLine(int line) throws Exception {
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
    public String prevMdLine() throws Exception {
        if(curIndex==-1){
            return null;
        }
        String lineSeparator = System.lineSeparator();
        //找到上一个\r\n
        //最后一行没有 /r/n
        int index=-1;
        if(isLastRow()){
            index= StringUtils.retFind(sb.toString(),curIndex,lineSeparator);
        }else{
            index=StringUtils.retFind(sb.toString(),curIndex-2,lineSeparator);
        }
        //上面没有\r\n已经是第一行 所以直接报错 没有上一行了
        if(index==-1){
            throw new Exception("目前是第一行 无法再往前");
        }
        //不是第一行
        else{
            curIndex=index+1;
            curRowEndIdx=curIndex+1;
            int index2=StringUtils.retFind(sb.toString(),index-1,lineSeparator);
            if(index2==-1){
                curRowStartIdx=0;
            }else{
                curRowStartIdx=index2+2;
            }
            curRowIdx--;
            return sb.substring(curRowStartIdx,curRowEndIdx);
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
        curRowStartIdx=curIndex;
        String lineSeparator = System.lineSeparator();
        int index=StringUtils.find(sb.toString(),curIndex,lineSeparator);
        String rtnString=null;
        //最后一行
        if(index==-1){
            rtnString=sb.substring(curIndex);
            curIndex=length()-1;
        }else {
            rtnString = sb.substring(curIndex, index) + lineSeparator;
            curIndex = (index + lineSeparator.length() - 1);
        }
        curRowEndIdx=curIndex+1;
        curRowIdx++;
        return rtnString;
    }
    public void replaceByLoc(int sidx,int seidx,String replaceText){
        sb.delete(sidx,seidx);
        sb.insert(sidx,replaceText);
        this.curRowEndIdx=sidx+replaceText.length();
        this.curIndex=this.curRowEndIdx-1;
    }
    public void replaceCurRow(String replaceText){
        int sidx=this.getCurRowStartIdx();
        int seidx=this.getCurRowEndIdx();
        replaceByLoc(sidx,seidx,replaceText);
    }
    public int length(){
        return sb.length();
    }
    public int getRowLength(){
        return rowLength;
    }
    public int getCurIndex(){
        return curIndex;
    }
    public int getCurRowStartIdx(){
        return curRowStartIdx;
    }

    public void setCurRowStartIdx(int curRowStartIdx) {
        this.curRowStartIdx = curRowStartIdx;
    }

    public void setCurRowEndIdx(int curRowEndIdx) {
        this.curRowEndIdx = curRowEndIdx;
    }

    public int getCurRowEndIdx(){
        return curRowEndIdx;
    }
    public boolean isFirstRow() {
        return curRowIdx==0;
    }
    public boolean isLastRow() {
        return curRowIdx==rowLength-1;
    }

    public int getCurRowIdx() {
        return curRowIdx;
    }

    public String getTargetHtml(){return this.sb.toString();}
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
