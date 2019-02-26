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







}
