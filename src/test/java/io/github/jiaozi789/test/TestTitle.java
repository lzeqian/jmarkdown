package io.github.jiaozi789.test;

import io.github.jiaozi789.reader.MarkDownFile;
import io.github.jiaozi789.reader.MarkDownReader;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static junit.framework.TestCase.assertEquals;

public class TestTitle {
    @Test
    public void testSayHello() throws IOException {
        MarkDownFile mdf=new MarkDownFile(TestTitle.class.getResourceAsStream("/test.md"));
        System.out.println(mdf.process());
    }

    @Test
    public void testReader() throws Exception {
        MarkDownReader mdr=new MarkDownReader(TestTitle.class.getResourceAsStream("/test.md"));
        System.out.println(mdr.nextMdLine(7)[6]);
        System.out.println(mdr.getCurRowStartIdx()+"-"+mdr.getCurRowEndIdx()+"-"+mdr.isFirstRow()+"-"+mdr.isLastRow());
//        System.out.println(mdr.nextMdLine(1)[0]);
//        System.out.println(mdr.getCurRowStartIdx()+"-"+mdr.getCurRowEndIdx()+"-"+mdr.isFirstRow()+"-"+mdr.isLastRow());isLastRow
        System.out.println(mdr.prevMdLine(6)[5]);
        System.out.println(mdr.getCurRowStartIdx()+"-"+mdr.getCurRowEndIdx()+"-"+mdr.isFirstRow()+"-"+mdr.isLastRow());
    }
    @Test
    public void testRegexGroup(){
        Pattern pattern = Pattern.compile("^(.+)@(.+)");
        Matcher matcher = pattern.matcher("saa@bb.com");

        if (matcher.find()) {
            for(int i=0;i<matcher.groupCount();i++)
                System.out.println(matcher.group(i+1));
        }
    }
    @Test
    public void testRegexp(){
        System.out.println(Pattern.matches(".*==(.+)==(.*\r\n)","==aaa==bb\r\n"));


    }

}
