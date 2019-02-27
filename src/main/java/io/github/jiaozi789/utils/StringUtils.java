package io.github.jiaozi789.utils;

/**
 * @Author 廖敏
 * @Date 2019-02-22 15:09
 **/
public class StringUtils {
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

    /**
     * 找到某个字符串在另一个字符串出现的次数
     * @param source 源字符串
     * @param findStr 目标字符串
     * @return 出现次数
     */
    public static int findCount(String source,String findStr){
        int startIdx=0;
        int count=0;
        while(true){
            int index=find(source,startIdx,findStr);
            if(index>=0){
                count++;
                startIdx=index+findStr.length();
            }else{
                break;
            }
        }
        return count;
    }

    /**
     * 替换掉所有的字符 只有最后reverseCount不替换
     * @param source
     * @param findStr
     * @param destStr
     * @param reverseCount
     * @return
     */
    public static String replaceStr(String source,String findStr,String destStr,int reverseCount){
        int startIdx=0;
        int count=0;
        StringBuffer targetStr=new StringBuffer();
        int findCount=findCount(source,findStr);
        while(true){
            int index=find(source,startIdx,findStr);
            if(index>=0){
                if(count==findCount-reverseCount){
                    targetStr.append(source.substring(startIdx));
                    break;
                }
                targetStr.append(source.substring(startIdx,index));
                targetStr.append(destStr);
                startIdx=index+findStr.length();
                count++;
            }else{
                targetStr.append(source.substring(startIdx));
                break;
            }
        }
        return targetStr.toString();
    }
    public static String ltrim(String str){
        return str.replaceAll("^\\s+","");
    }
    public static String rtrim(String str){
        return str.replaceAll("\\s+$","");
    }
    public static int indexOf(String src,String dest,int count) {
        int curIndex = 0;
        int rcount=1;
        while ((curIndex = src.indexOf(dest, curIndex)) != -1) {
            if(rcount==count){
                return curIndex;
            }
            curIndex=curIndex+dest.length();
            rcount++;
        }
        return -1;
    }

    /**
     * 判断某个字符串是否为空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        if(obj==null){
            return true;
        }else{
            if(obj.toString()==null || "".equals(obj.toString())){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int index=indexOf("hello_dd_dfdf_ccc","_",4);
        System.out.println(index);
    }
}
