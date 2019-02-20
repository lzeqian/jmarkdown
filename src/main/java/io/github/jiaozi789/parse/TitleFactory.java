package io.github.jiaozi789.parse;

/**
 * @Author 廖敏
 * @Date 2019-02-20 10:58
 **/
public class TitleFactory {
    /**
     * 一级标题解析类
     */
    public static class H1Parser extends TitleParser {
        public int level() {
            return 1;
        }
    }
    /**
     * 二级标题解析类
     */
    public static class H2Parser extends TitleParser {
        public int level() {
            return 2;
        }
    }
    /**
     * 三级标题解析类
     */
    public static class H3Parser extends TitleParser {
        public int level() {
            return 3;
        }
    }
    /**
     * 四级标题解析类
     */
    public static class H4Parser extends TitleParser {
        public int level() {
            return 4;
        }
    }
    /**
     * 五级标题解析类
     */
    public static class H5Parser extends TitleParser {
        public int level() {
            return 5;
        }
    }
    /**
     * 六级标题解析类
     */
    public static class H6Parser extends TitleParser {
        public int level() {
            return 6;
        }
    }
    public static TitleParser getParser(int level) throws Exception {
        if(level>6 && level<=0){
            throw new Exception("级别只支持1-6");
        }
        if(level==1)
            return new H1Parser();
        if(level==2)
            return new H2Parser();
        if(level==3)
            return new H3Parser();
        if(level==4)
            return new H4Parser();
        if(level==5)
            return new H5Parser();
        if(level==6)
            return new H6Parser();
        return null;
    }
}
