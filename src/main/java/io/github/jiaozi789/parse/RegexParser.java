package io.github.jiaozi789.parse;

import io.github.jiaozi789.reader.MarkDownReader;
import io.github.jiaozi789.utils.FtlUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author 廖敏
 * @Date 2019-02-21 15:49
 **/
public abstract class RegexParser implements   MarkDownParser{
    /**
     *
     *       正则表达式
     *       比如正则表达式
     *        # (hello张三)
     *        hello张三就是 $1 依次类推  $0表示参数个数
     *
     * @return 映射的正则表达式
     */
    public abstract String regex();

    /**
     * 每个组对应的名字 用于替换模板引擎变量 是一个map
     * {
     *    "1","innerText"
     * }
     *
     * 假设模板
     * <h${level}>${innerText}</h{level}>
     * @return 映射关系
     */
    public abstract Map<String,String> groupMapping();

    /**
     * 模板字符串
     * @return
     */
    public abstract String templateString();

    @Override
    public boolean ifMatch(MarkDownReader reader) {
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            return Pattern.matches(regex(),string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String replace(MarkDownReader reader) {
        Map<String,String> map=new HashMap<>();
        try {
            String string = reader.readChar(reader.getCurRowStartIdx(), reader.getCurRowEndIdx());
            Pattern pattern = Pattern.compile(regex());
            Matcher matcher = pattern.matcher(string);

            if (Pattern.matches(regex(),string) && matcher.find()) {
                for(int i=0;i<matcher.groupCount();i++) {
                    String key = groupMapping().get(i+1 + "");
                    map.put(key, matcher.group(i+1));
                }
            }
            return  FtlUtils.genernateByString(templateString(),map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
