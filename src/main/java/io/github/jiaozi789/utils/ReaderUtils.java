package io.github.jiaozi789.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 关于通用文件读取帮助类
 */
public class ReaderUtils {
    /**
     * 读取文件所有行
     * @param is 输入流
     * @return 返回读取所有内容
     * @throws IOException 读取io异常
     */
    public static String readAll(InputStream is) throws IOException {
        return new String(IOUtils.toCharArray(is));
    }

    /**
     * 从某个位置开始搜索一个字符串的位置
     * @param source
     * @param index
     * @param findStr
     * @return
     */
    public static int find(String source,int index,String findStr){
        return source.indexOf(findStr,index);
    }

    /**
     * 从某个字符串从index反向往前找到指定字符串
     * @param source 元字符串
     * @param index 开始搜索的下标
     * @param findStr 搜索的字符串
     * @return
     */
    public static int retFind(String source,int index,String findStr){
        if(index>source.length()-1){
            index=source.length()-1;
        }
        for(int i=index-findStr.length()+1;i>=0;i--){
            if(source.substring(i,i+findStr.length()).equals(findStr)){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(retFind("aasdfbbbsdf",100,"gg"));
    }

}
