package io.github.jiaozi789.reader;

import io.github.jiaozi789.utils.ReaderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Md输入流构建类
 * @Author 廖敏
 * @Date 2019-02-20 10:22
 **/
public class LineMarkDownReader extends BufferedReader {
    public static final String CHARSET="UTF-8";
    private int curIndex=-1;
    private StringBuffer sb;
    public LineMarkDownReader(InputStream in) {
        super(new InputStreamReader(in, Charset.forName(CHARSET)));
    }

}
