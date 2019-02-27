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
    public void testProcess() throws Exception {
        MarkDownFile mdf=new MarkDownFile(TestTitle.class.getResourceAsStream("/test.md"));
        System.out.println(mdf.process());
    }
    @Test
    public void testProcessStyle() throws Exception {
        MarkDownFile mdf=new MarkDownFile(TestTitle.class.getResourceAsStream("/test1.md"));
        System.out.println(mdf.processStyle());
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
        Pattern pattern = Pattern.compile("(?!.*\".*_.+_.*\"(?:.*|.*"+System.lineSeparator()+"))(?:(.*)_(.+)_(.*|.*"+System.lineSeparator()+"))");
        Matcher matcher = pattern.matcher("hello_a_zs");
        Pattern pattern1 = Pattern.compile("(?!.*\".*_.+_.*\"(.*|.*"+System.lineSeparator()+"))((.*)_(.+)_(.*|.*"+System.lineSeparator()+"))");
        Matcher matcher1 = pattern1.matcher("\"ttt\"hello_a_zs");
        if (matcher1.matches() && matcher.find()) {
            for(int i=0;i<matcher.groupCount();i++)
                System.out.println(matcher.group(i+1));
        }
    }
    @Test
    public void testRegexp(){
        System.out.println(Pattern.matches("(?!.*\".*_.*\".*)(.*_.*)","aa\"a_b\"vv"));
    }

}
