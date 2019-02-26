package io.github.jiaozi789.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author 廖敏
 * @Date 2019-02-26 16:20
 **/
public class SystemUtils {
    /**
     * 判断某个目标对应不在原始集合中
     * @param srcList 原始集合
     * @param target 目标对象
     * @return 是否不在
     */
    public static boolean notIn(List srcList,Object target){
        return notIn(srcList,Arrays.asList(target));
    }
    /**
     * 判断目标结合的任意一个元素都不在原始集合中
     * @param srcList 原始集合
     * @param destList 目标集合
     * @return 是否不在
     */
    public static boolean notIn(List srcList, List destList){
        boolean ifNotIn=true;
        for(Object obj:destList){
            for(Object obj1  :srcList){
                if(obj==obj1){
                    ifNotIn=false;
                }
            }
        }
        return ifNotIn;
    }
    public static boolean typeNotIn(List<Class> srcList, List destList){
        boolean ifNotIn=true;
        for(Object obj:destList){
            for(Class obj1  :srcList){
                if(obj.getClass()==obj1 || obj1.isAssignableFrom(obj.getClass())){
                    ifNotIn=false;
                }
            }
        }
        return ifNotIn;
    }
    public static  boolean match(Map<Integer,String> regex,Integer key,String targetValue){
        if(regex==null){
            return true;
        }else{
            String regexStr=regex.get(key);
            if(regexStr==null){
                return true;
            }else{
                return Pattern.matches(regexStr,targetValue);
            }
        }
    }
}
