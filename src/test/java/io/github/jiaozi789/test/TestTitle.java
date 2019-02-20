package io.github.jiaozi789.test;

import io.github.jiaozi789.reader.MarkDownFile;
import io.github.jiaozi789.reader.MarkDownReader;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class TestTitle {
    @Test
    public void testSayHello() throws IOException {
        MarkDownFile mdf=new MarkDownFile(TestTitle.class.getResourceAsStream("/test.md"));
        mdf.processLine();
    }

    @Test
    public void testReader() throws IOException {
        MarkDownReader mdr=new MarkDownReader(TestTitle.class.getResourceAsStream("/test.md"));
        System.out.println(mdr.nextMdLine(1));
        System.out.println(mdr.nextMdLine(1));
        System.out.println(mdr.nextMdLine(1));
        System.out.println(mdr.prevMdLine(3));
    }

}
